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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 74 */    public class TtmlParser implements SubtitleParser {
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

/* 75 */        public TtmlParser() {
                    try {
/* 78 */                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
/* 80 */                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

                @Override // com.google.android.exoplayer.text.SubtitleParser
/* 87 */        public Subtitle parse(InputStream inputStream, String inputEncoding, long startTimeUs) throws IOException {
                    try {
/* 88 */                XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
/* 89 */                xmlParser.setInput(inputStream, inputEncoding);
/* 90 */                TtmlSubtitle ttmlSubtitle = null;
/* 91 */                LinkedList<TtmlNode> nodeStack = new LinkedList<>();
/* 92 */                int unsupportedTagDepth = 0;
/* 94 */                for (int eventType = xmlParser.getEventType(); eventType != 1; eventType = xmlParser.getEventType()) {
/* 95 */                    TtmlNode parent = nodeStack.peekLast();
/* 96 */                    if (unsupportedTagDepth == 0) {
/* 97 */                        String name = xmlParser.getName();
/* 98 */                        if (eventType == 2) {
/* 99 */                            if (!isSupportedTag(name)) {
/* 100 */                               Log.w(TAG, "Ignoring unsupported tag: " + xmlParser.getName());
/* 101 */                               unsupportedTagDepth++;
                                    } else {
/* 103 */                               TtmlNode node = parseNode(xmlParser, parent);
/* 104 */                               nodeStack.addLast(node);
/* 105 */                               if (parent != null) {
/* 106 */                                   parent.addChild(node);
                                        }
                                    }
                                } else if (eventType == 4) {
/* 110 */                           parent.addChild(TtmlNode.buildTextNode(xmlParser.getText()));
                                } else if (eventType == 3) {
/* 112 */                           if (xmlParser.getName().equals(TtmlNode.TAG_TT)) {
/* 113 */                               ttmlSubtitle = new TtmlSubtitle(nodeStack.getLast(), startTimeUs);
                                    }
/* 115 */                           nodeStack.removeLast();
                                }
                            } else if (eventType == 2) {
/* 119 */                       unsupportedTagDepth++;
                            } else if (eventType == 3) {
/* 121 */                       unsupportedTagDepth--;
                            }
/* 124 */                   xmlParser.next();
                        }
/* 127 */               return ttmlSubtitle;
                    } catch (XmlPullParserException xppe) {
/* 129 */               throw new IOException("Unable to parse source", xppe);
                    }
                }

                @Override // com.google.android.exoplayer.text.SubtitleParser
/* 134 */       public boolean canParse(String mimeType) {
/* 135 */           return MimeTypes.APPLICATION_TTML.equals(mimeType);
                }

/* 138 */       private TtmlNode parseNode(XmlPullParser parser, TtmlNode parent) {
/* 139 */           long duration = 0;
/* 140 */           long startTime = -1;
/* 141 */           long endTime = -1;
/* 142 */           int attributeCount = parser.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
/* 145 */               String attr = parser.getAttributeName(i).replaceFirst("^.*:", "");
/* 146 */               String value = parser.getAttributeValue(i);
/* 147 */               if (attr.equals(ATTR_BEGIN)) {
/* 148 */                   startTime = parseTimeExpression(value, 30, 1, 1);
                        } else if (attr.equals(ATTR_END)) {
/* 151 */                   endTime = parseTimeExpression(value, 30, 1, 1);
                        } else if (attr.equals(ATTR_DURATION)) {
/* 154 */                   duration = parseTimeExpression(value, 30, 1, 1);
                        }
                    }
/* 160 */           if (parent != null && parent.startTimeUs != -1) {
                        if (startTime != -1) {
/* 162 */                   startTime += parent.startTimeUs;
                        }
                        if (endTime != -1) {
/* 165 */                   endTime += parent.startTimeUs;
                        }
                    }
                    if (endTime == -1) {
                        if (duration > 0) {
/* 171 */                   endTime = startTime + duration;
                        } else if (parent != null && parent.endTimeUs != -1) {
/* 174 */                   endTime = parent.endTimeUs;
                        }
                    }
/* 177 */           return TtmlNode.buildNode(parser.getName(), startTime, endTime);
                }

/* 180 */       private static boolean isSupportedTag(String tag) {
                    return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals(TtmlNode.TAG_BODY) || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals(TtmlNode.TAG_STYLE) || tag.equals(TtmlNode.TAG_STYLING) || tag.equals("layout") || tag.equals(TtmlNode.TAG_REGION) || tag.equals(TtmlNode.TAG_METADATA) || tag.equals(TtmlNode.TAG_SMPTE_IMAGE) || tag.equals(TtmlNode.TAG_SMPTE_DATA) || tag.equals(TtmlNode.TAG_SMPTE_INFORMATION);
                }

/* 215 */       private static long parseTimeExpression(String time, int frameRate, int subframeRate, int tickRate) {
/* 216 */           Matcher matcher = CLOCK_TIME.matcher(time);
/* 217 */           if (matcher.matches()) {
/* 218 */               String hours = matcher.group(1);
/* 219 */               double durationSeconds = Long.parseLong(hours) * 3600;
/* 220 */               String minutes = matcher.group(2);
/* 221 */               double durationSeconds2 = durationSeconds + (Long.parseLong(minutes) * 60);
/* 222 */               String seconds = matcher.group(3);
/* 223 */               double durationSeconds3 = durationSeconds2 + Long.parseLong(seconds);
/* 224 */               String fraction = matcher.group(4);
/* 225 */               double durationSeconds4 = durationSeconds3 + (fraction != null ? Double.parseDouble(fraction) : 0.0d);
/* 226 */               String frames = matcher.group(5);
/* 227 */               double durationSeconds5 = durationSeconds4 + (frames != null ? Long.parseLong(frames) / ((double) frameRate) : 0.0d);
/* 228 */               String subframes = matcher.group(6);
/* 231 */               return (long) (1000000.0d * (durationSeconds5 + (subframes != null ? (Long.parseLong(subframes) / ((double) subframeRate)) / ((double) frameRate) : 0.0d)));
                    }
/* 233 */           Matcher matcher2 = OFFSET_TIME.matcher(time);
/* 234 */           if (matcher2.matches()) {
/* 235 */               String timeValue = matcher2.group(1);
/* 236 */               double value = Double.parseDouble(timeValue);
/* 237 */               String unit = matcher2.group(2);
/* 238 */               if (unit.equals("h")) {
/* 239 */                   value *= 3.6E9d;
                        } else if (unit.equals("m")) {
/* 241 */                   value *= 6.0E7d;
                        } else if (unit.equals(VineAPI.RESOURCE_SHARED)) {
/* 243 */                   value *= 1000000.0d;
                        } else if (unit.equals("ms")) {
/* 245 */                   value *= 1000.0d;
                        } else if (unit.equals("f")) {
/* 247 */                   value = (value / ((double) frameRate)) * 1000000.0d;
                        } else if (unit.equals("t")) {
/* 249 */                   value = (value / ((double) tickRate)) * 1000000.0d;
                        }
/* 251 */               return (long) value;
                    }
/* 253 */           throw new NumberFormatException("Malformed time expression: " + time);
                }
            }
