            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import android.text.TextUtils;
            import co.vine.android.provider.Vine;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.chunk.Format;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.io.InputStream;
            import java.math.BigDecimal;
            import java.text.ParseException;
            import java.util.ArrayList;
            import java.util.Calendar;
            import java.util.GregorianCalendar;
            import java.util.List;
            import java.util.TimeZone;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.xml.sax.helpers.DefaultHandler;
            import org.xmlpull.v1.XmlPullParser;
            import org.xmlpull.v1.XmlPullParserException;
            import org.xmlpull.v1.XmlPullParserFactory;
            
            
    public class MediaPresentationDescriptionParser extends DefaultHandler {
                private final XmlPullParserFactory xmlParserFactory;
                private static final Pattern DURATION = Pattern.compile("^PT(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?$");
                private static final Pattern DATE_TIME_PATTERN = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)(\\.(\\d+))?([Zz]|((\\+|\\-)(\\d\\d):(\\d\\d)))?");

        public MediaPresentationDescriptionParser() {
                    try {
                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

        public MediaPresentationDescription parseMediaPresentationDescription(InputStream inputStream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
                    try {
                XmlPullParser xpp = this.xmlParserFactory.newPullParser();
                xpp.setInput(inputStream, inputEncoding);
                int eventType = xpp.next();
                if (eventType != 2 || !"MPD".equals(xpp.getName())) {
                    throw new ParserException("inputStream does not contain a valid media presentation description");
                        }
                return parseMediaPresentationDescription(xpp, contentId, baseUrl);
                    } catch (ParseException e) {
                throw new ParserException(e);
                    } catch (XmlPullParserException e2) {
                throw new ParserException(e2);
                    }
                }

       private MediaPresentationDescription parseMediaPresentationDescription(XmlPullParser xpp, String contentId, Uri baseUrl) throws XmlPullParserException, IOException, ParseException {
           long availabilityStartTime = parseDateTime(xpp, "availabilityStartTime", -1L);
           long durationMs = parseDurationMs(xpp, "mediaPresentationDuration");
           long minBufferTimeMs = parseDurationMs(xpp, "minBufferTime");
           String typeString = xpp.getAttributeValue(null, "type");
           boolean dynamic = typeString != null ? typeString.equals("dynamic") : false;
           long minUpdateTimeMs = dynamic ? parseDurationMs(xpp, "minimumUpdatePeriod", -1L) : -1L;
           long timeShiftBufferDepthMs = dynamic ? parseDurationMs(xpp, "timeShiftBufferDepth", -1L) : -1L;
           UtcTimingElement utcTiming = null;
           List<Period> periods = new ArrayList<>();
           do {
               xpp.next();
               if (isStartTag(xpp, "BaseURL")) {
                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "UTCTiming")) {
                   utcTiming = parseUtcTiming(xpp);
                        } else if (isStartTag(xpp, "Period")) {
                   periods.add(parsePeriod(xpp, contentId, baseUrl, durationMs));
                        }
           } while (!isEndTag(xpp, "MPD"));
           return new MediaPresentationDescription(availabilityStartTime, durationMs, minBufferTimeMs, dynamic, minUpdateTimeMs, timeShiftBufferDepthMs, utcTiming, periods);
                }

       private UtcTimingElement parseUtcTiming(XmlPullParser xpp) {
           String schemeIdUri = xpp.getAttributeValue(null, "schemeIdUri");
           String value = xpp.getAttributeValue(null, Vine.Settings.PROP_VALUE);
           return new UtcTimingElement(schemeIdUri, value);
                }

       private Period parsePeriod(XmlPullParser xpp, String contentId, Uri baseUrl, long mpdDurationMs) throws XmlPullParserException, IOException {
           String id = xpp.getAttributeValue(null, "id");
           long startMs = parseDurationMs(xpp, "start", 0L);
           long durationMs = parseDurationMs(xpp, "duration", mpdDurationMs);
           SegmentBase segmentBase = null;
           List<AdaptationSet> adaptationSets = new ArrayList<>();
           do {
               xpp.next();
               if (isStartTag(xpp, "BaseURL")) {
                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "AdaptationSet")) {
                   adaptationSets.add(parseAdaptationSet(xpp, contentId, baseUrl, startMs, durationMs, segmentBase));
                        } else if (isStartTag(xpp, "SegmentBase")) {
                   segmentBase = parseSegmentBase(xpp, baseUrl, null);
                        } else if (isStartTag(xpp, "SegmentList")) {
                   segmentBase = parseSegmentList(xpp, baseUrl, null, durationMs);
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
                   segmentBase = parseSegmentTemplate(xpp, baseUrl, null, durationMs);
                        }
           } while (!isEndTag(xpp, "Period"));
           return new Period(id, startMs, durationMs, adaptationSets);
                }

       private AdaptationSet parseAdaptationSet(XmlPullParser xpp, String contentId, Uri baseUrl, long periodStartMs, long periodDurationMs, SegmentBase segmentBase) throws XmlPullParserException, IOException {
           String mimeType = xpp.getAttributeValue(null, "mimeType");
           String language = xpp.getAttributeValue(null, "lang");
           int contentType = parseAdaptationSetTypeFromMimeType(mimeType);
           int id = -1;
           List<ContentProtection> contentProtections = null;
           List<Representation> representations = new ArrayList<>();
           do {
               xpp.next();
               if (isStartTag(xpp, "BaseURL")) {
                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "ContentProtection")) {
                   if (contentProtections == null) {
                       contentProtections = new ArrayList<>();
                            }
                   contentProtections.add(parseContentProtection(xpp));
                        } else if (isStartTag(xpp, "ContentComponent")) {
                   id = Integer.parseInt(xpp.getAttributeValue(null, "id"));
                   contentType = checkAdaptationSetTypeConsistency(contentType, parseAdaptationSetType(xpp.getAttributeValue(null, "contentType")));
                        } else if (isStartTag(xpp, "Representation")) {
                   Representation representation = parseRepresentation(xpp, contentId, baseUrl, periodStartMs, periodDurationMs, mimeType, language, segmentBase);
                   contentType = checkAdaptationSetTypeConsistency(contentType, parseAdaptationSetTypeFromMimeType(representation.format.mimeType));
                   representations.add(representation);
                        } else if (isStartTag(xpp, "SegmentBase")) {
                   segmentBase = parseSegmentBase(xpp, baseUrl, (SegmentBase.SingleSegmentBase) segmentBase);
                        } else if (isStartTag(xpp, "SegmentList")) {
                   segmentBase = parseSegmentList(xpp, baseUrl, (SegmentBase.SegmentList) segmentBase, periodDurationMs);
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
                   segmentBase = parseSegmentTemplate(xpp, baseUrl, (SegmentBase.SegmentTemplate) segmentBase, periodDurationMs);
                        }
           } while (!isEndTag(xpp, "AdaptationSet"));
           return new AdaptationSet(id, contentType, representations, contentProtections);
                }

       private int parseAdaptationSetType(String contentType) {
           if (TextUtils.isEmpty(contentType)) {
                        return -1;
                    }
                    if (MimeTypes.BASE_TYPE_AUDIO.equals(contentType)) {
                        return 1;
                    }
                    if (MimeTypes.BASE_TYPE_VIDEO.equals(contentType)) {
                        return 0;
                    }
                    return MimeTypes.BASE_TYPE_TEXT.equals(contentType) ? 2 : -1;
                }

       private int parseAdaptationSetTypeFromMimeType(String mimeType) {
           if (TextUtils.isEmpty(mimeType)) {
                        return -1;
                    }
                    if (MimeTypes.isAudio(mimeType)) {
                        return 1;
                    }
                    if (MimeTypes.isVideo(mimeType)) {
                        return 0;
                    }
                    return (MimeTypes.isText(mimeType) || MimeTypes.isTtml(mimeType)) ? 2 : -1;
                }

       private int checkAdaptationSetTypeConsistency(int firstType, int secondType) {
           if (firstType == -1) {
                        return secondType;
                    }
           if (secondType != -1) {
               Assertions.checkState(firstType == secondType);
               return firstType;
                    }
           return firstType;
                }

       protected ContentProtection parseContentProtection(XmlPullParser xpp) throws XmlPullParserException, IOException {
           String schemeUriId = xpp.getAttributeValue(null, "schemeUriId");
           return new ContentProtection(schemeUriId, null);
                }

       private Representation parseRepresentation(XmlPullParser xpp, String contentId, Uri baseUrl, long periodStartMs, long periodDurationMs, String mimeType, String language, SegmentBase segmentBase) throws XmlPullParserException, IOException {
           String id = xpp.getAttributeValue(null, "id");
           int bandwidth = parseInt(xpp, "bandwidth");
           int audioSamplingRate = parseInt(xpp, "audioSamplingRate");
           int width = parseInt(xpp, "width");
           int height = parseInt(xpp, "height");
           String mimeType2 = parseString(xpp, "mimeType", mimeType);
           int numChannels = -1;
           do {
                        int numChannels2 = numChannels;
               xpp.next();
               if (isStartTag(xpp, "BaseURL")) {
                   baseUrl = parseBaseUrl(xpp, baseUrl);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "AudioChannelConfiguration")) {
                   numChannels = Integer.parseInt(xpp.getAttributeValue(null, Vine.Settings.PROP_VALUE));
                        } else if (isStartTag(xpp, "SegmentBase")) {
                   segmentBase = parseSegmentBase(xpp, baseUrl, (SegmentBase.SingleSegmentBase) segmentBase);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "SegmentList")) {
                   segmentBase = parseSegmentList(xpp, baseUrl, (SegmentBase.SegmentList) segmentBase, periodDurationMs);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
                   segmentBase = parseSegmentTemplate(xpp, baseUrl, (SegmentBase.SegmentTemplate) segmentBase, periodDurationMs);
                            numChannels = numChannels2;
                        } else {
                            numChannels = numChannels2;
                        }
           } while (!isEndTag(xpp, "Representation"));
           Format format = new Format(id, mimeType2, width, height, numChannels, audioSamplingRate, bandwidth, language);
           return Representation.newInstance(periodStartMs, periodDurationMs, contentId, -1L, format, segmentBase);
                }

       private SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xpp, Uri baseUrl, SegmentBase.SingleSegmentBase parent) throws XmlPullParserException, IOException {
           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
           long indexStart = parent != null ? parent.indexStart : 0L;
           long indexLength = parent != null ? parent.indexLength : -1L;
           String indexRangeText = xpp.getAttributeValue(null, "indexRange");
           if (indexRangeText != null) {
               String[] indexRange = indexRangeText.split("-");
               indexStart = Long.parseLong(indexRange[0]);
               indexLength = (Long.parseLong(indexRange[1]) - indexStart) + 1;
                    }
           RangedUri initialization = parent != null ? parent.initialization : null;
           do {
               xpp.next();
               if (isStartTag(xpp, "Initialization")) {
                   initialization = parseInitialization(xpp, baseUrl);
                        }
           } while (!isEndTag(xpp, "SegmentBase"));
           return new SegmentBase.SingleSegmentBase(initialization, timescale, presentationTimeOffset, baseUrl, indexStart, indexLength);
                }

       private SegmentBase.SegmentList parseSegmentList(XmlPullParser xpp, Uri baseUrl, SegmentBase.SegmentList parent, long periodDuration) throws XmlPullParserException, IOException {
           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
           long duration = parseLong(xpp, "duration", parent != null ? parent.duration : -1L);
           int startNumber = parseInt(xpp, "startNumber", parent != null ? parent.startNumber : 0);
           RangedUri initialization = null;
           List<SegmentBase.SegmentTimelineElement> timeline = null;
           List<RangedUri> segments = null;
           do {
               xpp.next();
               if (isStartTag(xpp, "Initialization")) {
                   initialization = parseInitialization(xpp, baseUrl);
                        } else if (isStartTag(xpp, "SegmentTimeline")) {
                   timeline = parseSegmentTimeline(xpp);
                        } else if (isStartTag(xpp, "SegmentURL")) {
                   if (segments == null) {
                       segments = new ArrayList<>();
                            }
                   segments.add(parseSegmentUrl(xpp, baseUrl));
                        }
           } while (!isEndTag(xpp, "SegmentList"));
           if (parent != null) {
               if (initialization == null) {
                   initialization = parent.initialization;
                        }
               if (timeline == null) {
                   timeline = parent.segmentTimeline;
                        }
               if (segments == null) {
                   segments = parent.mediaSegments;
                        }
                    }
           return new SegmentBase.SegmentList(initialization, timescale, presentationTimeOffset, periodDuration, startNumber, duration, timeline, segments);
                }

       private SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xpp, Uri baseUrl, SegmentBase.SegmentTemplate parent, long periodDuration) throws XmlPullParserException, IOException {
           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
           long duration = parseLong(xpp, "duration", parent != null ? parent.duration : -1L);
           int startNumber = parseInt(xpp, "startNumber", parent != null ? parent.startNumber : 0);
           UrlTemplate mediaTemplate = parseUrlTemplate(xpp, "media", parent != null ? parent.mediaTemplate : null);
           UrlTemplate initializationTemplate = parseUrlTemplate(xpp, "initialization", parent != null ? parent.initializationTemplate : null);
           RangedUri initialization = null;
           List<SegmentBase.SegmentTimelineElement> timeline = null;
           do {
               xpp.next();
               if (isStartTag(xpp, "Initialization")) {
                   initialization = parseInitialization(xpp, baseUrl);
                        } else if (isStartTag(xpp, "SegmentTimeline")) {
                   timeline = parseSegmentTimeline(xpp);
                        }
           } while (!isEndTag(xpp, "SegmentTemplate"));
           if (parent != null) {
               if (initialization == null) {
                   initialization = parent.initialization;
                        }
               if (timeline == null) {
                   timeline = parent.segmentTimeline;
                        }
                    }
           return new SegmentBase.SegmentTemplate(initialization, timescale, presentationTimeOffset, periodDuration, startNumber, duration, timeline, initializationTemplate, mediaTemplate, baseUrl);
                }

       private List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xpp) throws XmlPullParserException, IOException {
           List<SegmentBase.SegmentTimelineElement> segmentTimeline = new ArrayList<>();
           long elapsedTime = 0;
           do {
               xpp.next();
               if (isStartTag(xpp, "S")) {
                   elapsedTime = parseLong(xpp, "t", elapsedTime);
                   long duration = parseLong(xpp, "d");
                   int count = parseInt(xpp, "r", 0) + 1;
                            for (int i = 0; i < count; i++) {
                       segmentTimeline.add(new SegmentBase.SegmentTimelineElement(elapsedTime, duration));
                       elapsedTime += duration;
                            }
                        }
           } while (!isEndTag(xpp, "SegmentTimeline"));
           return segmentTimeline;
                }

       private UrlTemplate parseUrlTemplate(XmlPullParser xpp, String name, UrlTemplate defaultValue) {
           String valueString = xpp.getAttributeValue(null, name);
           if (valueString != null) {
               UrlTemplate defaultValue2 = UrlTemplate.compile(valueString);
               return defaultValue2;
                    }
           return defaultValue;
                }

       private RangedUri parseInitialization(XmlPullParser xpp, Uri baseUrl) {
           return parseRangedUrl(xpp, baseUrl, "sourceURL", "range");
                }

       private RangedUri parseSegmentUrl(XmlPullParser xpp, Uri baseUrl) {
           return parseRangedUrl(xpp, baseUrl, "media", "mediaRange");
                }

       private RangedUri parseRangedUrl(XmlPullParser xpp, Uri baseUrl, String urlAttribute, String rangeAttribute) {
           String urlText = xpp.getAttributeValue(null, urlAttribute);
           long rangeStart = 0;
           long rangeLength = -1;
           String rangeText = xpp.getAttributeValue(null, rangeAttribute);
           if (rangeText != null) {
               String[] rangeTextArray = rangeText.split("-");
               rangeStart = Long.parseLong(rangeTextArray[0]);
               rangeLength = (Long.parseLong(rangeTextArray[1]) - rangeStart) + 1;
                    }
           return new RangedUri(baseUrl, urlText, rangeStart, rangeLength);
                }

       protected static boolean isEndTag(XmlPullParser xpp, String name) throws XmlPullParserException {
                    return xpp.getEventType() == 3 && name.equals(xpp.getName());
                }

       protected static boolean isStartTag(XmlPullParser xpp, String name) throws XmlPullParserException {
                    return xpp.getEventType() == 2 && name.equals(xpp.getName());
                }

       private static long parseDurationMs(XmlPullParser xpp, String name) {
           return parseDurationMs(xpp, name, -1L);
                }

       private static long parseDateTime(XmlPullParser xpp, String name, long defaultValue) throws ParseException {
           String value = xpp.getAttributeValue(null, name);
           if (value == null) {
               return defaultValue;
                    }
                    long defaultValue2 = parseDateTime(value);
           return defaultValue2;
                }

       static long parseDateTime(String value) throws ParseException {
                    int timezoneShift;
           Matcher matcher = DATE_TIME_PATTERN.matcher(value);
           if (!matcher.matches()) {
               throw new ParseException("Invalid date/time format: " + value, 0);
                    }
           if (matcher.group(9) == null || matcher.group(9).equalsIgnoreCase("Z")) {
               timezoneShift = 0;
                    } else {
               timezoneShift = (Integer.valueOf(matcher.group(12)).intValue() * 60) + Integer.valueOf(matcher.group(13)).intValue();
               if (matcher.group(11).equals("-")) {
                   timezoneShift *= -1;
                        }
                    }
           Calendar dateTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
           dateTime.clear();
           dateTime.set(Integer.valueOf(matcher.group(1)).intValue(), Integer.valueOf(matcher.group(2)).intValue() - 1, Integer.valueOf(matcher.group(3)).intValue(), Integer.valueOf(matcher.group(4)).intValue(), Integer.valueOf(matcher.group(5)).intValue(), Integer.valueOf(matcher.group(6)).intValue());
           if (!TextUtils.isEmpty(matcher.group(8))) {
               BigDecimal bd = new BigDecimal("0." + matcher.group(8));
               dateTime.set(14, bd.movePointRight(3).intValue());
                    }
           long time = dateTime.getTimeInMillis();
           if (timezoneShift != 0) {
               return time - ((long) (60000 * timezoneShift));
                    }
           return time;
                }

       private static long parseDurationMs(XmlPullParser xpp, String name, long defaultValue) {
           String value = xpp.getAttributeValue(null, name);
           if (value != null) {
               Matcher matcher = DURATION.matcher(value);
               if (matcher.matches()) {
                   String hours = matcher.group(2);
                   double durationSeconds = hours != null ? Double.parseDouble(hours) * 3600.0d : 0.0d;
                   String minutes = matcher.group(4);
                   double durationSeconds2 = durationSeconds + (minutes != null ? Double.parseDouble(minutes) * 60.0d : 0.0d);
                   String seconds = matcher.group(6);
                   long defaultValue2 = (long) (1000.0d * (durationSeconds2 + (seconds != null ? Double.parseDouble(seconds) : 0.0d)));
                   return defaultValue2;
                        }
               long defaultValue3 = (long) (Double.parseDouble(value) * 3600.0d * 1000.0d);
               return defaultValue3;
                    }
           return defaultValue;
                }

       protected static Uri parseBaseUrl(XmlPullParser xpp, Uri parentBaseUrl) throws XmlPullParserException, IOException {
           xpp.next();
           String newBaseUrlText = xpp.getText();
           Uri newBaseUri = Uri.parse(newBaseUrlText);
           if (!newBaseUri.isAbsolute()) {
               return Uri.withAppendedPath(parentBaseUrl, newBaseUrlText);
                    }
           return newBaseUri;
                }

       protected static int parseInt(XmlPullParser xpp, String name) {
           return parseInt(xpp, name, -1);
                }

       protected static int parseInt(XmlPullParser xpp, String name, int defaultValue) {
           String value = xpp.getAttributeValue(null, name);
           if (value == null) {
                        return defaultValue;
                    }
                    int defaultValue2 = Integer.parseInt(value);
                    return defaultValue2;
                }

       protected static long parseLong(XmlPullParser xpp, String name) {
           return parseLong(xpp, name, -1L);
                }

       protected static long parseLong(XmlPullParser xpp, String name, long defaultValue) {
           String value = xpp.getAttributeValue(null, name);
           if (value == null) {
                        return defaultValue;
                    }
                    long defaultValue2 = Long.parseLong(value);
                    return defaultValue2;
                }

       protected static String parseString(XmlPullParser xpp, String name, String defaultValue) {
           String value = xpp.getAttributeValue(null, name);
                    return value == null ? defaultValue : value;
                }
            }
