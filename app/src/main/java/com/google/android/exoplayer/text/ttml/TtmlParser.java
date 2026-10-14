            package com.google.android.exoplayer.text.ttml;

            import android.util.Log;
            import co.vine.android.client.VineAPI;
            import com.google.android.exoplayer.text.Subtitle;
            import com.google.android.exoplayer.text.SubtitleParser;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.LinkedList;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.xmlpull.v1.XmlPullParser;
            import org.xmlpull.v1.XmlPullParserException;
            import org.xmlpull.v1.XmlPullParserFactory;
            
            
    public class TtmlParser implements SubtitleParser {
                private static final String ATTR_BEGIN = "begin";
                private static final String ATTR_DURATION = "dur";
                private static final String ATTR_END = "end";
                private static final int DEFAULT_FRAMERATE = 30;
                private static final int DEFAULT_SUBFRAMERATE = 1;
                private static final int DEFAULT_TICKRATE = 1;
                private static final String TAG = "TtmlParser";
                private final XmlPullParserFactory xmlParserFactory;
                private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
                private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");

        public TtmlParser() {
                    try {
                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

                @Override 
        public Subtitle parse(InputStream inputStream, String inputEncoding, long startTimeUs) throws IOException {
                    try {
                XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
                xmlParser.setInput(inputStream, inputEncoding);
                TtmlSubtitle ttmlSubtitle = null;
                LinkedList<TtmlNode> nodeStack = new LinkedList<>();
                int unsupportedTagDepth = 0;
                for (int eventType = xmlParser.getEventType(); eventType != 1; eventType = xmlParser.getEventType()) {
                    TtmlNode parent = nodeStack.peekLast();
                    if (unsupportedTagDepth == 0) {
                        String name = xmlParser.getName();
                        if (eventType == 2) {
                            if (!isSupportedTag(name)) {
                               Log.w(TAG, "Ignoring unsupported tag: " + xmlParser.getName());
                               unsupportedTagDepth++;
                                    } else {
                               TtmlNode node = parseNode(xmlParser, parent);
                               nodeStack.addLast(node);
                               if (parent != null) {
                                   parent.addChild(node);
                                        }
                                    }
                                } else if (eventType == 4) {
                           parent.addChild(TtmlNode.buildTextNode(xmlParser.getText()));
                                } else if (eventType == 3) {
                           if (xmlParser.getName().equals(TtmlNode.TAG_TT)) {
                               ttmlSubtitle = new TtmlSubtitle(nodeStack.getLast(), startTimeUs);
                                    }
                           nodeStack.removeLast();
                                }
                            } else if (eventType == 2) {
                       unsupportedTagDepth++;
                            } else if (eventType == 3) {
                       unsupportedTagDepth--;
                            }
                   xmlParser.next();
                        }
               return ttmlSubtitle;
                    } catch (XmlPullParserException xppe) {
               throw new IOException("Unable to parse source", xppe);
                    }
                }

                @Override 
       public boolean canParse(String mimeType) {
           return MimeTypes.APPLICATION_TTML.equals(mimeType);
                }

       private TtmlNode parseNode(XmlPullParser parser, TtmlNode parent) {
           long duration = 0;
           long startTime = -1;
           long endTime = -1;
           int attributeCount = parser.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
               String attr = parser.getAttributeName(i).replaceFirst("^.*:", "");
               String value = parser.getAttributeValue(i);
               if (attr.equals(ATTR_BEGIN)) {
                   startTime = parseTimeExpression(value, 30, 1, 1);
                        } else if (attr.equals(ATTR_END)) {
                   endTime = parseTimeExpression(value, 30, 1, 1);
                        } else if (attr.equals(ATTR_DURATION)) {
                   duration = parseTimeExpression(value, 30, 1, 1);
                        }
                    }
           if (parent != null && parent.startTimeUs != -1) {
                        if (startTime != -1) {
                   startTime += parent.startTimeUs;
                        }
                        if (endTime != -1) {
                   endTime += parent.startTimeUs;
                        }
                    }
                    if (endTime == -1) {
                        if (duration > 0) {
                   endTime = startTime + duration;
                        } else if (parent != null && parent.endTimeUs != -1) {
                   endTime = parent.endTimeUs;
                        }
                    }
           return TtmlNode.buildNode(parser.getName(), startTime, endTime);
                }

       private static boolean isSupportedTag(String tag) {
                    return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals(TtmlNode.TAG_BODY) || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals(TtmlNode.TAG_STYLE) || tag.equals(TtmlNode.TAG_STYLING) || tag.equals("layout") || tag.equals(TtmlNode.TAG_REGION) || tag.equals(TtmlNode.TAG_METADATA) || tag.equals(TtmlNode.TAG_SMPTE_IMAGE) || tag.equals(TtmlNode.TAG_SMPTE_DATA) || tag.equals(TtmlNode.TAG_SMPTE_INFORMATION);
                }

       private static long parseTimeExpression(String time, int frameRate, int subframeRate, int tickRate) {
           Matcher matcher = CLOCK_TIME.matcher(time);
           if (matcher.matches()) {
               String hours = matcher.group(1);
               double durationSeconds = Long.parseLong(hours) * 3600;
               String minutes = matcher.group(2);
               double durationSeconds2 = durationSeconds + (Long.parseLong(minutes) * 60);
               String seconds = matcher.group(3);
               double durationSeconds3 = durationSeconds2 + Long.parseLong(seconds);
               String fraction = matcher.group(4);
               double durationSeconds4 = durationSeconds3 + (fraction != null ? Double.parseDouble(fraction) : 0.0d);
               String frames = matcher.group(5);
               double durationSeconds5 = durationSeconds4 + (frames != null ? Long.parseLong(frames) / ((double) frameRate) : 0.0d);
               String subframes = matcher.group(6);
               return (long) (1000000.0d * (durationSeconds5 + (subframes != null ? (Long.parseLong(subframes) / ((double) subframeRate)) / ((double) frameRate) : 0.0d)));
                    }
           Matcher matcher2 = OFFSET_TIME.matcher(time);
           if (matcher2.matches()) {
               String timeValue = matcher2.group(1);
               double value = Double.parseDouble(timeValue);
               String unit = matcher2.group(2);
               if (unit.equals("h")) {
                   value *= 3.6E9d;
                        } else if (unit.equals("m")) {
                   value *= 6.0E7d;
                        } else if (unit.equals(VineAPI.RESOURCE_SHARED)) {
                   value *= 1000000.0d;
                        } else if (unit.equals("ms")) {
                   value *= 1000.0d;
                        } else if (unit.equals("f")) {
                   value = (value / ((double) frameRate)) * 1000000.0d;
                        } else if (unit.equals("t")) {
                   value = (value / ((double) tickRate)) * 1000000.0d;
                        }
               return (long) value;
                    }
           throw new NumberFormatException("Malformed time expression: " + time);
                }
            }
