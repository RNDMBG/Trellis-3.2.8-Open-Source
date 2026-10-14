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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class MediaPresentationDescriptionParser extends DefaultHandler {
                private final XmlPullParserFactory xmlParserFactory;
                private static final Pattern DURATION = Pattern.compile("^PT(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?$");
                private static final Pattern DATE_TIME_PATTERN = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)(\\.(\\d+))?([Zz]|((\\+|\\-)(\\d\\d):(\\d\\d)))?");

/* 62 */        public MediaPresentationDescriptionParser() {
                    try {
/* 65 */                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
/* 67 */                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

/* 86 */        public MediaPresentationDescription parseMediaPresentationDescription(InputStream inputStream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
                    try {
/* 87 */                XmlPullParser xpp = this.xmlParserFactory.newPullParser();
/* 88 */                xpp.setInput(inputStream, inputEncoding);
/* 89 */                int eventType = xpp.next();
/* 90 */                if (eventType != 2 || !"MPD".equals(xpp.getName())) {
/* 91 */                    throw new ParserException("inputStream does not contain a valid media presentation description");
                        }
/* 94 */                return parseMediaPresentationDescription(xpp, contentId, baseUrl);
                    } catch (ParseException e) {
/* 98 */                throw new ParserException(e);
                    } catch (XmlPullParserException e2) {
/* 96 */                throw new ParserException(e2);
                    }
                }

/* 103 */       private MediaPresentationDescription parseMediaPresentationDescription(XmlPullParser xpp, String contentId, Uri baseUrl) throws XmlPullParserException, IOException, ParseException {
/* 104 */           long availabilityStartTime = parseDateTime(xpp, "availabilityStartTime", -1L);
/* 105 */           long durationMs = parseDurationMs(xpp, "mediaPresentationDuration");
/* 106 */           long minBufferTimeMs = parseDurationMs(xpp, "minBufferTime");
/* 107 */           String typeString = xpp.getAttributeValue(null, "type");
/* 108 */           boolean dynamic = typeString != null ? typeString.equals("dynamic") : false;
/* 109 */           long minUpdateTimeMs = dynamic ? parseDurationMs(xpp, "minimumUpdatePeriod", -1L) : -1L;
/* 110 */           long timeShiftBufferDepthMs = dynamic ? parseDurationMs(xpp, "timeShiftBufferDepth", -1L) : -1L;
/* 111 */           UtcTimingElement utcTiming = null;
/* 113 */           List<Period> periods = new ArrayList<>();
/* 123 */           do {
/* 115 */               xpp.next();
/* 116 */               if (isStartTag(xpp, "BaseURL")) {
/* 117 */                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "UTCTiming")) {
/* 119 */                   utcTiming = parseUtcTiming(xpp);
                        } else if (isStartTag(xpp, "Period")) {
/* 121 */                   periods.add(parsePeriod(xpp, contentId, baseUrl, durationMs));
                        }
/* 123 */           } while (!isEndTag(xpp, "MPD"));
/* 125 */           return new MediaPresentationDescription(availabilityStartTime, durationMs, minBufferTimeMs, dynamic, minUpdateTimeMs, timeShiftBufferDepthMs, utcTiming, periods);
                }

/* 129 */       private UtcTimingElement parseUtcTiming(XmlPullParser xpp) {
/* 130 */           String schemeIdUri = xpp.getAttributeValue(null, "schemeIdUri");
/* 131 */           String value = xpp.getAttributeValue(null, Vine.Settings.PROP_VALUE);
/* 132 */           return new UtcTimingElement(schemeIdUri, value);
                }

/* 136 */       private Period parsePeriod(XmlPullParser xpp, String contentId, Uri baseUrl, long mpdDurationMs) throws XmlPullParserException, IOException {
/* 137 */           String id = xpp.getAttributeValue(null, "id");
/* 138 */           long startMs = parseDurationMs(xpp, "start", 0L);
/* 139 */           long durationMs = parseDurationMs(xpp, "duration", mpdDurationMs);
/* 140 */           SegmentBase segmentBase = null;
/* 141 */           List<AdaptationSet> adaptationSets = new ArrayList<>();
/* 156 */           do {
/* 143 */               xpp.next();
/* 144 */               if (isStartTag(xpp, "BaseURL")) {
/* 145 */                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "AdaptationSet")) {
/* 147 */                   adaptationSets.add(parseAdaptationSet(xpp, contentId, baseUrl, startMs, durationMs, segmentBase));
                        } else if (isStartTag(xpp, "SegmentBase")) {
/* 150 */                   segmentBase = parseSegmentBase(xpp, baseUrl, null);
                        } else if (isStartTag(xpp, "SegmentList")) {
/* 152 */                   segmentBase = parseSegmentList(xpp, baseUrl, null, durationMs);
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
/* 154 */                   segmentBase = parseSegmentTemplate(xpp, baseUrl, null, durationMs);
                        }
/* 156 */           } while (!isEndTag(xpp, "Period"));
/* 158 */           return new Period(id, startMs, durationMs, adaptationSets);
                }

/* 166 */       private AdaptationSet parseAdaptationSet(XmlPullParser xpp, String contentId, Uri baseUrl, long periodStartMs, long periodDurationMs, SegmentBase segmentBase) throws XmlPullParserException, IOException {
/* 167 */           String mimeType = xpp.getAttributeValue(null, "mimeType");
/* 168 */           String language = xpp.getAttributeValue(null, "lang");
/* 169 */           int contentType = parseAdaptationSetTypeFromMimeType(mimeType);
/* 171 */           int id = -1;
/* 172 */           List<ContentProtection> contentProtections = null;
/* 173 */           List<Representation> representations = new ArrayList<>();
/* 201 */           do {
/* 175 */               xpp.next();
/* 176 */               if (isStartTag(xpp, "BaseURL")) {
/* 177 */                   baseUrl = parseBaseUrl(xpp, baseUrl);
                        } else if (isStartTag(xpp, "ContentProtection")) {
/* 179 */                   if (contentProtections == null) {
/* 180 */                       contentProtections = new ArrayList<>();
                            }
/* 182 */                   contentProtections.add(parseContentProtection(xpp));
                        } else if (isStartTag(xpp, "ContentComponent")) {
/* 184 */                   id = Integer.parseInt(xpp.getAttributeValue(null, "id"));
/* 185 */                   contentType = checkAdaptationSetTypeConsistency(contentType, parseAdaptationSetType(xpp.getAttributeValue(null, "contentType")));
                        } else if (isStartTag(xpp, "Representation")) {
/* 188 */                   Representation representation = parseRepresentation(xpp, contentId, baseUrl, periodStartMs, periodDurationMs, mimeType, language, segmentBase);
/* 190 */                   contentType = checkAdaptationSetTypeConsistency(contentType, parseAdaptationSetTypeFromMimeType(representation.format.mimeType));
/* 192 */                   representations.add(representation);
                        } else if (isStartTag(xpp, "SegmentBase")) {
/* 194 */                   segmentBase = parseSegmentBase(xpp, baseUrl, (SegmentBase.SingleSegmentBase) segmentBase);
                        } else if (isStartTag(xpp, "SegmentList")) {
/* 196 */                   segmentBase = parseSegmentList(xpp, baseUrl, (SegmentBase.SegmentList) segmentBase, periodDurationMs);
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
/* 198 */                   segmentBase = parseSegmentTemplate(xpp, baseUrl, (SegmentBase.SegmentTemplate) segmentBase, periodDurationMs);
                        }
/* 201 */           } while (!isEndTag(xpp, "AdaptationSet"));
/* 203 */           return new AdaptationSet(id, contentType, representations, contentProtections);
                }

/* 206 */       private int parseAdaptationSetType(String contentType) {
/* 207 */           if (TextUtils.isEmpty(contentType)) {
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

/* 214 */       private int parseAdaptationSetTypeFromMimeType(String mimeType) {
/* 215 */           if (TextUtils.isEmpty(mimeType)) {
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

/* 233 */       private int checkAdaptationSetTypeConsistency(int firstType, int secondType) {
/* 234 */           if (firstType == -1) {
                        return secondType;
                    }
/* 236 */           if (secondType != -1) {
/* 239 */               Assertions.checkState(firstType == secondType);
/* 240 */               return firstType;
                    }
/* 240 */           return firstType;
                }

/* 251 */       protected ContentProtection parseContentProtection(XmlPullParser xpp) throws XmlPullParserException, IOException {
/* 252 */           String schemeUriId = xpp.getAttributeValue(null, "schemeUriId");
/* 253 */           return new ContentProtection(schemeUriId, null);
                }

/* 260 */       private Representation parseRepresentation(XmlPullParser xpp, String contentId, Uri baseUrl, long periodStartMs, long periodDurationMs, String mimeType, String language, SegmentBase segmentBase) throws XmlPullParserException, IOException {
/* 261 */           String id = xpp.getAttributeValue(null, "id");
/* 262 */           int bandwidth = parseInt(xpp, "bandwidth");
/* 263 */           int audioSamplingRate = parseInt(xpp, "audioSamplingRate");
/* 264 */           int width = parseInt(xpp, "width");
/* 265 */           int height = parseInt(xpp, "height");
/* 266 */           String mimeType2 = parseString(xpp, "mimeType", mimeType);
/* 268 */           int numChannels = -1;
/* 283 */           do {
                        int numChannels2 = numChannels;
/* 270 */               xpp.next();
/* 271 */               if (isStartTag(xpp, "BaseURL")) {
/* 272 */                   baseUrl = parseBaseUrl(xpp, baseUrl);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "AudioChannelConfiguration")) {
/* 274 */                   numChannels = Integer.parseInt(xpp.getAttributeValue(null, Vine.Settings.PROP_VALUE));
                        } else if (isStartTag(xpp, "SegmentBase")) {
/* 276 */                   segmentBase = parseSegmentBase(xpp, baseUrl, (SegmentBase.SingleSegmentBase) segmentBase);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "SegmentList")) {
/* 278 */                   segmentBase = parseSegmentList(xpp, baseUrl, (SegmentBase.SegmentList) segmentBase, periodDurationMs);
                            numChannels = numChannels2;
                        } else if (isStartTag(xpp, "SegmentTemplate")) {
/* 280 */                   segmentBase = parseSegmentTemplate(xpp, baseUrl, (SegmentBase.SegmentTemplate) segmentBase, periodDurationMs);
                            numChannels = numChannels2;
                        } else {
                            numChannels = numChannels2;
                        }
/* 283 */           } while (!isEndTag(xpp, "Representation"));
/* 285 */           Format format = new Format(id, mimeType2, width, height, numChannels, audioSamplingRate, bandwidth, language);
/* 287 */           return Representation.newInstance(periodStartMs, periodDurationMs, contentId, -1L, format, segmentBase);
                }

/* 295 */       private SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xpp, Uri baseUrl, SegmentBase.SingleSegmentBase parent) throws XmlPullParserException, IOException {
/* 296 */           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
/* 297 */           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
/* 300 */           long indexStart = parent != null ? parent.indexStart : 0L;
/* 301 */           long indexLength = parent != null ? parent.indexLength : -1L;
/* 302 */           String indexRangeText = xpp.getAttributeValue(null, "indexRange");
/* 303 */           if (indexRangeText != null) {
/* 304 */               String[] indexRange = indexRangeText.split("-");
/* 305 */               indexStart = Long.parseLong(indexRange[0]);
/* 306 */               indexLength = (Long.parseLong(indexRange[1]) - indexStart) + 1;
                    }
/* 309 */           RangedUri initialization = parent != null ? parent.initialization : null;
/* 315 */           do {
/* 311 */               xpp.next();
/* 312 */               if (isStartTag(xpp, "Initialization")) {
/* 313 */                   initialization = parseInitialization(xpp, baseUrl);
                        }
/* 315 */           } while (!isEndTag(xpp, "SegmentBase"));
/* 317 */           return new SegmentBase.SingleSegmentBase(initialization, timescale, presentationTimeOffset, baseUrl, indexStart, indexLength);
                }

/* 323 */       private SegmentBase.SegmentList parseSegmentList(XmlPullParser xpp, Uri baseUrl, SegmentBase.SegmentList parent, long periodDuration) throws XmlPullParserException, IOException {
/* 324 */           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
/* 325 */           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
/* 327 */           long duration = parseLong(xpp, "duration", parent != null ? parent.duration : -1L);
/* 328 */           int startNumber = parseInt(xpp, "startNumber", parent != null ? parent.startNumber : 0);
/* 330 */           RangedUri initialization = null;
/* 331 */           List<SegmentBase.SegmentTimelineElement> timeline = null;
/* 332 */           List<RangedUri> segments = null;
/* 346 */           do {
/* 335 */               xpp.next();
/* 336 */               if (isStartTag(xpp, "Initialization")) {
/* 337 */                   initialization = parseInitialization(xpp, baseUrl);
                        } else if (isStartTag(xpp, "SegmentTimeline")) {
/* 339 */                   timeline = parseSegmentTimeline(xpp);
                        } else if (isStartTag(xpp, "SegmentURL")) {
/* 341 */                   if (segments == null) {
/* 342 */                       segments = new ArrayList<>();
                            }
/* 344 */                   segments.add(parseSegmentUrl(xpp, baseUrl));
                        }
/* 346 */           } while (!isEndTag(xpp, "SegmentList"));
/* 348 */           if (parent != null) {
/* 349 */               if (initialization == null) {
/* 349 */                   initialization = parent.initialization;
                        }
/* 350 */               if (timeline == null) {
/* 350 */                   timeline = parent.segmentTimeline;
                        }
/* 351 */               if (segments == null) {
/* 351 */                   segments = parent.mediaSegments;
                        }
                    }
/* 354 */           return new SegmentBase.SegmentList(initialization, timescale, presentationTimeOffset, periodDuration, startNumber, duration, timeline, segments);
                }

/* 360 */       private SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xpp, Uri baseUrl, SegmentBase.SegmentTemplate parent, long periodDuration) throws XmlPullParserException, IOException {
/* 361 */           long timescale = parseLong(xpp, "timescale", parent != null ? parent.timescale : 1L);
/* 362 */           long presentationTimeOffset = parseLong(xpp, "presentationTimeOffset", parent != null ? parent.presentationTimeOffset : 0L);
/* 364 */           long duration = parseLong(xpp, "duration", parent != null ? parent.duration : -1L);
/* 365 */           int startNumber = parseInt(xpp, "startNumber", parent != null ? parent.startNumber : 0);
/* 366 */           UrlTemplate mediaTemplate = parseUrlTemplate(xpp, "media", parent != null ? parent.mediaTemplate : null);
/* 368 */           UrlTemplate initializationTemplate = parseUrlTemplate(xpp, "initialization", parent != null ? parent.initializationTemplate : null);
/* 371 */           RangedUri initialization = null;
/* 372 */           List<SegmentBase.SegmentTimelineElement> timeline = null;
/* 381 */           do {
/* 375 */               xpp.next();
/* 376 */               if (isStartTag(xpp, "Initialization")) {
/* 377 */                   initialization = parseInitialization(xpp, baseUrl);
                        } else if (isStartTag(xpp, "SegmentTimeline")) {
/* 379 */                   timeline = parseSegmentTimeline(xpp);
                        }
/* 381 */           } while (!isEndTag(xpp, "SegmentTemplate"));
/* 383 */           if (parent != null) {
/* 384 */               if (initialization == null) {
/* 384 */                   initialization = parent.initialization;
                        }
/* 385 */               if (timeline == null) {
/* 385 */                   timeline = parent.segmentTimeline;
                        }
                    }
/* 388 */           return new SegmentBase.SegmentTemplate(initialization, timescale, presentationTimeOffset, periodDuration, startNumber, duration, timeline, initializationTemplate, mediaTemplate, baseUrl);
                }

/* 393 */       private List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xpp) throws XmlPullParserException, IOException {
/* 394 */           List<SegmentBase.SegmentTimelineElement> segmentTimeline = new ArrayList<>();
/* 395 */           long elapsedTime = 0;
/* 407 */           do {
/* 397 */               xpp.next();
/* 398 */               if (isStartTag(xpp, "S")) {
/* 399 */                   elapsedTime = parseLong(xpp, "t", elapsedTime);
/* 400 */                   long duration = parseLong(xpp, "d");
/* 401 */                   int count = parseInt(xpp, "r", 0) + 1;
                            for (int i = 0; i < count; i++) {
/* 403 */                       segmentTimeline.add(new SegmentBase.SegmentTimelineElement(elapsedTime, duration));
/* 404 */                       elapsedTime += duration;
                            }
                        }
/* 407 */           } while (!isEndTag(xpp, "SegmentTimeline"));
/* 408 */           return segmentTimeline;
                }

/* 412 */       private UrlTemplate parseUrlTemplate(XmlPullParser xpp, String name, UrlTemplate defaultValue) {
/* 413 */           String valueString = xpp.getAttributeValue(null, name);
/* 414 */           if (valueString != null) {
/* 415 */               UrlTemplate defaultValue2 = UrlTemplate.compile(valueString);
/* 417 */               return defaultValue2;
                    }
/* 417 */           return defaultValue;
                }

/* 420 */       private RangedUri parseInitialization(XmlPullParser xpp, Uri baseUrl) {
/* 421 */           return parseRangedUrl(xpp, baseUrl, "sourceURL", "range");
                }

/* 424 */       private RangedUri parseSegmentUrl(XmlPullParser xpp, Uri baseUrl) {
/* 425 */           return parseRangedUrl(xpp, baseUrl, "media", "mediaRange");
                }

/* 429 */       private RangedUri parseRangedUrl(XmlPullParser xpp, Uri baseUrl, String urlAttribute, String rangeAttribute) {
/* 430 */           String urlText = xpp.getAttributeValue(null, urlAttribute);
/* 431 */           long rangeStart = 0;
/* 432 */           long rangeLength = -1;
/* 433 */           String rangeText = xpp.getAttributeValue(null, rangeAttribute);
/* 434 */           if (rangeText != null) {
/* 435 */               String[] rangeTextArray = rangeText.split("-");
/* 436 */               rangeStart = Long.parseLong(rangeTextArray[0]);
/* 437 */               rangeLength = (Long.parseLong(rangeTextArray[1]) - rangeStart) + 1;
                    }
/* 439 */           return new RangedUri(baseUrl, urlText, rangeStart, rangeLength);
                }

/* 444 */       protected static boolean isEndTag(XmlPullParser xpp, String name) throws XmlPullParserException {
                    return xpp.getEventType() == 3 && name.equals(xpp.getName());
                }

/* 449 */       protected static boolean isStartTag(XmlPullParser xpp, String name) throws XmlPullParserException {
                    return xpp.getEventType() == 2 && name.equals(xpp.getName());
                }

/* 453 */       private static long parseDurationMs(XmlPullParser xpp, String name) {
/* 454 */           return parseDurationMs(xpp, name, -1L);
                }

/* 458 */       private static long parseDateTime(XmlPullParser xpp, String name, long defaultValue) throws ParseException {
/* 459 */           String value = xpp.getAttributeValue(null, name);
/* 461 */           if (value == null) {
/* 464 */               return defaultValue;
                    }
                    long defaultValue2 = parseDateTime(value);
/* 464 */           return defaultValue2;
                }

/* 469 */       static long parseDateTime(String value) throws ParseException {
                    int timezoneShift;
/* 470 */           Matcher matcher = DATE_TIME_PATTERN.matcher(value);
/* 471 */           if (!matcher.matches()) {
/* 472 */               throw new ParseException("Invalid date/time format: " + value, 0);
                    }
/* 476 */           if (matcher.group(9) == null || matcher.group(9).equalsIgnoreCase("Z")) {
/* 480 */               timezoneShift = 0;
                    } else {
/* 482 */               timezoneShift = (Integer.valueOf(matcher.group(12)).intValue() * 60) + Integer.valueOf(matcher.group(13)).intValue();
/* 484 */               if (matcher.group(11).equals("-")) {
/* 485 */                   timezoneShift *= -1;
                        }
                    }
/* 489 */           Calendar dateTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
/* 491 */           dateTime.clear();
/* 493 */           dateTime.set(Integer.valueOf(matcher.group(1)).intValue(), Integer.valueOf(matcher.group(2)).intValue() - 1, Integer.valueOf(matcher.group(3)).intValue(), Integer.valueOf(matcher.group(4)).intValue(), Integer.valueOf(matcher.group(5)).intValue(), Integer.valueOf(matcher.group(6)).intValue());
/* 499 */           if (!TextUtils.isEmpty(matcher.group(8))) {
/* 500 */               BigDecimal bd = new BigDecimal("0." + matcher.group(8));
/* 502 */               dateTime.set(14, bd.movePointRight(3).intValue());
                    }
/* 505 */           long time = dateTime.getTimeInMillis();
/* 506 */           if (timezoneShift != 0) {
/* 507 */               return time - ((long) (60000 * timezoneShift));
                    }
/* 510 */           return time;
                }

/* 513 */       private static long parseDurationMs(XmlPullParser xpp, String name, long defaultValue) {
/* 514 */           String value = xpp.getAttributeValue(null, name);
/* 515 */           if (value != null) {
/* 516 */               Matcher matcher = DURATION.matcher(value);
/* 517 */               if (matcher.matches()) {
/* 518 */                   String hours = matcher.group(2);
/* 519 */                   double durationSeconds = hours != null ? Double.parseDouble(hours) * 3600.0d : 0.0d;
/* 520 */                   String minutes = matcher.group(4);
/* 521 */                   double durationSeconds2 = durationSeconds + (minutes != null ? Double.parseDouble(minutes) * 60.0d : 0.0d);
/* 522 */                   String seconds = matcher.group(6);
/* 524 */                   long defaultValue2 = (long) (1000.0d * (durationSeconds2 + (seconds != null ? Double.parseDouble(seconds) : 0.0d)));
/* 529 */                   return defaultValue2;
                        }
/* 526 */               long defaultValue3 = (long) (Double.parseDouble(value) * 3600.0d * 1000.0d);
/* 529 */               return defaultValue3;
                    }
/* 529 */           return defaultValue;
                }

/* 533 */       protected static Uri parseBaseUrl(XmlPullParser xpp, Uri parentBaseUrl) throws XmlPullParserException, IOException {
/* 534 */           xpp.next();
/* 535 */           String newBaseUrlText = xpp.getText();
/* 536 */           Uri newBaseUri = Uri.parse(newBaseUrlText);
/* 537 */           if (!newBaseUri.isAbsolute()) {
/* 538 */               return Uri.withAppendedPath(parentBaseUrl, newBaseUrlText);
                    }
/* 540 */           return newBaseUri;
                }

/* 543 */       protected static int parseInt(XmlPullParser xpp, String name) {
/* 544 */           return parseInt(xpp, name, -1);
                }

/* 547 */       protected static int parseInt(XmlPullParser xpp, String name, int defaultValue) {
/* 548 */           String value = xpp.getAttributeValue(null, name);
/* 549 */           if (value == null) {
                        return defaultValue;
                    }
                    int defaultValue2 = Integer.parseInt(value);
                    return defaultValue2;
                }

/* 552 */       protected static long parseLong(XmlPullParser xpp, String name) {
/* 553 */           return parseLong(xpp, name, -1L);
                }

/* 556 */       protected static long parseLong(XmlPullParser xpp, String name, long defaultValue) {
/* 557 */           String value = xpp.getAttributeValue(null, name);
/* 558 */           if (value == null) {
                        return defaultValue;
                    }
                    long defaultValue2 = Long.parseLong(value);
                    return defaultValue2;
                }

/* 561 */       protected static String parseString(XmlPullParser xpp, String name, String defaultValue) {
/* 562 */           String value = xpp.getAttributeValue(null, name);
                    return value == null ? defaultValue : value;
                }
            }
