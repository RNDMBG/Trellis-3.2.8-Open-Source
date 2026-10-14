            package com.google.android.exoplayer.smoothstreaming;

            import android.util.Base64;
            import android.util.Pair;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.CodecSpecificDataUtil;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.UUID;
            import org.xmlpull.v1.XmlPullParser;
            import org.xmlpull.v1.XmlPullParserException;
            import org.xmlpull.v1.XmlPullParserFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class SmoothStreamingManifestParser {
                private final XmlPullParserFactory xmlParserFactory;

/* 47 */        public SmoothStreamingManifestParser() {
                    try {
/* 50 */                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
/* 52 */                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

/* 67 */        public SmoothStreamingManifest parse(InputStream inputStream, String inputEncoding) throws IOException {
                    try {
/* 68 */                XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
/* 69 */                xmlParser.setInput(inputStream, inputEncoding);
/* 70 */                SmoothStreamMediaParser smoothStreamMediaParser = new SmoothStreamMediaParser(null);
/* 71 */                return (SmoothStreamingManifest) smoothStreamMediaParser.parse(xmlParser);
                    } catch (XmlPullParserException e) {
/* 73 */                throw new ParserException(e);
                    }
                }

/* 81 */        public static class MissingFieldException extends ParserException {
/* 82 */            public MissingFieldException(String fieldName) {
/* 83 */                super("Missing required field: " + fieldName);
                    }
                }

/* 96 */        private static abstract class ElementParser {
                    private final List<Pair<String, Object>> normalizedAttributes = new LinkedList();
                    private final ElementParser parent;
                    private final String tag;

                    protected abstract Object build();

/* 97 */            public ElementParser(String tag, ElementParser parent) {
/* 99 */                this.tag = tag;
/* 100 */               this.parent = parent;
                    }

/* 106 */           public final Object parse(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
/* 107 */               boolean foundStartTag = false;
/* 108 */               int skippingElementDepth = 0;
                        while (true) {
/* 110 */                   int eventType = xmlParser.getEventType();
                            switch (eventType) {
                                case 1:
                                    return null;
                                case 2:
/* 113 */                           String tagName = xmlParser.getName();
/* 114 */                           if (this.tag.equals(tagName)) {
/* 115 */                               foundStartTag = true;
/* 116 */                               parseStartTag(xmlParser);
                                    } else if (foundStartTag) {
/* 118 */                               if (skippingElementDepth > 0) {
/* 119 */                                   skippingElementDepth++;
                                        } else if (handleChildInline(tagName)) {
/* 121 */                                   parseStartTag(xmlParser);
                                        } else {
/* 123 */                                   ElementParser childElementParser = newChildParser(this, tagName);
/* 124 */                                   if (childElementParser == null) {
/* 125 */                                       skippingElementDepth = 1;
                                            } else {
/* 127 */                                       addChild(childElementParser.parse(xmlParser));
                                            }
                                        }
                                    }
                                    break;
                                case 3:
/* 138 */                           if (!foundStartTag) {
                                        continue;
                                    } else if (skippingElementDepth > 0) {
/* 140 */                               skippingElementDepth--;
                                    } else {
/* 142 */                               String tagName2 = xmlParser.getName();
/* 143 */                               parseEndTag(xmlParser);
/* 144 */                               if (!handleChildInline(tagName2)) {
/* 145 */                                   return build();
                                        }
                                    }
                                    break;
                                case 4:
/* 133 */                           if (foundStartTag && skippingElementDepth == 0) {
/* 134 */                               parseText(xmlParser);
                                    }
                                    break;
                            }
/* 156 */                   xmlParser.next();
                        }
                    }

/* 160 */           private ElementParser newChildParser(ElementParser parent, String name) {
/* 161 */               if (TrackElementParser.TAG.equals(name)) {
/* 162 */                   return new TrackElementParser(parent);
                        }
/* 163 */               if (ProtectionElementParser.TAG.equals(name)) {
/* 164 */                   return new ProtectionElementParser(parent);
                        }
/* 165 */               if (StreamElementParser.TAG.equals(name)) {
/* 166 */                   return new StreamElementParser(parent);
                        }
/* 168 */               return null;
                    }

/* 182 */           protected final void putNormalizedAttribute(String key, Object value) {
/* 183 */               this.normalizedAttributes.add(Pair.create(key, value));
                    }

/* 193 */           protected final Object getNormalizedAttribute(String key) {
                        for (int i = 0; i < this.normalizedAttributes.size(); i++) {
/* 195 */                   Pair<String, Object> pair = this.normalizedAttributes.get(i);
/* 196 */                   if (((String) pair.first).equals(key)) {
/* 197 */                       return pair.second;
                            }
                        }
/* 200 */               if (this.parent == null) {
                            return null;
                        }
                        return this.parent.getNormalizedAttribute(key);
                    }

/* 209 */           protected boolean handleChildInline(String tagName) {
/* 210 */               return false;
                    }

/* 218 */           protected void parseStartTag(XmlPullParser xmlParser) throws ParserException {
                    }

/* 226 */           protected void parseText(XmlPullParser xmlParser) throws ParserException {
                    }

/* 234 */           protected void parseEndTag(XmlPullParser xmlParser) throws ParserException {
                    }

/* 241 */           protected void addChild(Object parsedChild) {
                    }

/* 247 */           protected final String parseRequiredString(XmlPullParser parser, String key) throws MissingFieldException {
/* 248 */               String value = parser.getAttributeValue(null, key);
/* 249 */               if (value != null) {
/* 250 */                   return value;
                        }
/* 252 */               throw new MissingFieldException(key);
                    }

/* 257 */           protected final int parseInt(XmlPullParser parser, String key, int defaultValue) throws ParserException {
/* 258 */               String value = parser.getAttributeValue(null, key);
/* 259 */               if (value != null) {
                            try {
/* 261 */                       int defaultValue2 = Integer.parseInt(value);
/* 266 */                       return defaultValue2;
                            } catch (NumberFormatException e) {
/* 263 */                       throw new ParserException(e);
                            }
                        }
/* 266 */               return defaultValue;
                    }

/* 270 */           protected final int parseRequiredInt(XmlPullParser parser, String key) throws ParserException {
/* 271 */               String value = parser.getAttributeValue(null, key);
/* 272 */               if (value != null) {
                            try {
/* 274 */                       return Integer.parseInt(value);
                            } catch (NumberFormatException e) {
/* 276 */                       throw new ParserException(e);
                            }
                        }
/* 279 */               throw new MissingFieldException(key);
                    }

/* 284 */           protected final long parseLong(XmlPullParser parser, String key, long defaultValue) throws ParserException {
/* 285 */               String value = parser.getAttributeValue(null, key);
/* 286 */               if (value != null) {
                            try {
/* 288 */                       long defaultValue2 = Long.parseLong(value);
/* 293 */                       return defaultValue2;
                            } catch (NumberFormatException e) {
/* 290 */                       throw new ParserException(e);
                            }
                        }
/* 293 */               return defaultValue;
                    }

/* 298 */           protected final long parseRequiredLong(XmlPullParser parser, String key) throws ParserException {
/* 299 */               String value = parser.getAttributeValue(null, key);
/* 300 */               if (value != null) {
                            try {
/* 302 */                       return Long.parseLong(value);
                            } catch (NumberFormatException e) {
/* 304 */                       throw new ParserException(e);
                            }
                        }
/* 307 */               throw new MissingFieldException(key);
                    }
                }

/* 330 */       private static class SmoothStreamMediaParser extends ElementParser {
                    private static final String KEY_DURATION = "Duration";
                    private static final String KEY_LOOKAHEAD_COUNT = "LookaheadCount";
                    private static final String KEY_MAJOR_VERSION = "MajorVersion";
                    private static final String KEY_MINOR_VERSION = "MinorVersion";
                    private static final String KEY_TIME_SCALE = "TimeScale";
                    public static final String TAG = "SmoothStreamingMedia";
                    private long duration;
                    private int lookAheadCount;
                    private int majorVersion;
                    private int minorVersion;
                    private SmoothStreamingManifest.ProtectionElement protectionElement;
                    private List<SmoothStreamingManifest.StreamElement> streamElements;
                    private long timeScale;

/* 331 */           public SmoothStreamMediaParser(ElementParser parent) {
/* 332 */               super(TAG, parent);
/* 333 */               this.lookAheadCount = -1;
/* 334 */               this.protectionElement = null;
/* 335 */               this.streamElements = new LinkedList();
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 339 */           public void parseStartTag(XmlPullParser parser) throws ParserException {
/* 340 */               this.majorVersion = parseRequiredInt(parser, KEY_MAJOR_VERSION);
/* 341 */               this.minorVersion = parseRequiredInt(parser, KEY_MINOR_VERSION);
/* 342 */               this.timeScale = parseLong(parser, KEY_TIME_SCALE, 10000000L);
/* 343 */               this.duration = parseRequiredLong(parser, KEY_DURATION);
/* 344 */               this.lookAheadCount = parseInt(parser, KEY_LOOKAHEAD_COUNT, -1);
/* 345 */               putNormalizedAttribute(KEY_TIME_SCALE, Long.valueOf(this.timeScale));
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 349 */           public void addChild(Object child) {
/* 350 */               if (child instanceof SmoothStreamingManifest.StreamElement) {
/* 351 */                   this.streamElements.add((SmoothStreamingManifest.StreamElement) child);
                        } else if (child instanceof SmoothStreamingManifest.ProtectionElement) {
/* 353 */                   Assertions.checkState(this.protectionElement == null);
/* 354 */                   this.protectionElement = (SmoothStreamingManifest.ProtectionElement) child;
                        }
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 359 */           public Object build() {
/* 360 */               SmoothStreamingManifest.StreamElement[] streamElementArray = new SmoothStreamingManifest.StreamElement[this.streamElements.size()];
/* 361 */               this.streamElements.toArray(streamElementArray);
/* 362 */               return new SmoothStreamingManifest(this.majorVersion, this.minorVersion, this.timeScale, this.duration, this.lookAheadCount, this.protectionElement, streamElementArray);
                    }
                }

/* 378 */       private static class ProtectionElementParser extends ElementParser {
                    public static final String KEY_SYSTEM_ID = "SystemID";
                    public static final String TAG = "Protection";
                    public static final String TAG_PROTECTION_HEADER = "ProtectionHeader";
                    private boolean inProtectionHeader;
                    private byte[] initData;
                    private UUID uuid;

/* 379 */           public ProtectionElementParser(ElementParser parent) {
/* 380 */               super(TAG, parent);
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 384 */           public boolean handleChildInline(String tag) {
/* 385 */               return TAG_PROTECTION_HEADER.equals(tag);
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 389 */           public void parseStartTag(XmlPullParser parser) {
/* 390 */               if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
/* 391 */                   this.inProtectionHeader = true;
/* 392 */                   String uuidString = parser.getAttributeValue(null, KEY_SYSTEM_ID);
/* 393 */                   this.uuid = UUID.fromString(uuidString);
                        }
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 398 */           public void parseText(XmlPullParser parser) {
/* 399 */               if (this.inProtectionHeader) {
/* 400 */                   this.initData = Base64.decode(parser.getText(), 0);
                        }
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 405 */           public void parseEndTag(XmlPullParser parser) {
/* 406 */               if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
/* 407 */                   this.inProtectionHeader = false;
                        }
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 412 */           public Object build() {
/* 413 */               return new SmoothStreamingManifest.ProtectionElement(this.uuid, this.initData);
                    }
                }

/* 459 */       private static class StreamElementParser extends ElementParser {
                    private static final String KEY_CHUNKS = "Chunks";
                    private static final String KEY_DISPLAY_HEIGHT = "DisplayHeight";
                    private static final String KEY_DISPLAY_WIDTH = "DisplayWidth";
                    private static final String KEY_FRAGMENT_DURATION = "d";
                    private static final String KEY_FRAGMENT_START_TIME = "t";
                    private static final String KEY_LANGUAGE = "Language";
                    private static final String KEY_MAX_HEIGHT = "MaxHeight";
                    private static final String KEY_MAX_WIDTH = "MaxWidth";
                    private static final String KEY_NAME = "Name";
                    private static final String KEY_QUALITY_LEVELS = "QualityLevels";
                    private static final String KEY_SUB_TYPE = "Subtype";
                    private static final String KEY_TIME_SCALE = "TimeScale";
                    private static final String KEY_TYPE = "Type";
                    private static final String KEY_TYPE_AUDIO = "audio";
                    private static final String KEY_TYPE_TEXT = "text";
                    private static final String KEY_TYPE_VIDEO = "video";
                    private static final String KEY_URL = "Url";
                    public static final String TAG = "StreamIndex";
                    private static final String TAG_STREAM_FRAGMENT = "c";
                    private int chunkIndex;
                    private int displayHeight;
                    private int displayWidth;
                    private String language;
                    private int maxHeight;
                    private int maxWidth;
                    private String name;
                    private long previousChunkDuration;
                    private int qualityLevels;
                    private long[] startTimes;
                    private String subType;
                    private long timeScale;
                    private final List<SmoothStreamingManifest.TrackElement> tracks;
                    private int type;
                    private String url;

/* 460 */           public StreamElementParser(ElementParser parent) {
/* 461 */               super(TAG, parent);
/* 462 */               this.tracks = new LinkedList();
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 466 */           public boolean handleChildInline(String tag) {
/* 467 */               return TAG_STREAM_FRAGMENT.equals(tag);
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 471 */           public void parseStartTag(XmlPullParser parser) throws ParserException {
/* 472 */               if (TAG_STREAM_FRAGMENT.equals(parser.getName())) {
/* 473 */                   parseStreamFragmentStartTag(parser);
                        } else {
/* 475 */                   parseStreamElementStartTag(parser);
                        }
                    }

/* 479 */           private void parseStreamFragmentStartTag(XmlPullParser parser) throws ParserException {
/* 480 */               this.startTimes[this.chunkIndex] = parseLong(parser, KEY_FRAGMENT_START_TIME, -1L);
                        if (this.startTimes[this.chunkIndex] == -1) {
/* 482 */                   if (this.chunkIndex == 0) {
/* 484 */                       this.startTimes[this.chunkIndex] = 0;
                            } else if (this.previousChunkDuration != -1) {
/* 487 */                       this.startTimes[this.chunkIndex] = this.startTimes[this.chunkIndex - 1] + this.previousChunkDuration;
                            } else {
/* 490 */                       throw new ParserException("Unable to infer start time");
                            }
                        }
/* 493 */               this.previousChunkDuration = parseLong(parser, KEY_FRAGMENT_DURATION, -1L);
/* 494 */               this.chunkIndex++;
                    }

/* 497 */           private void parseStreamElementStartTag(XmlPullParser parser) throws ParserException {
/* 498 */               this.type = parseType(parser);
/* 499 */               putNormalizedAttribute(KEY_TYPE, Integer.valueOf(this.type));
/* 500 */               if (this.type == 2) {
/* 501 */                   this.subType = parseRequiredString(parser, KEY_SUB_TYPE);
                        } else {
/* 503 */                   this.subType = parser.getAttributeValue(null, KEY_SUB_TYPE);
                        }
/* 505 */               this.name = parser.getAttributeValue(null, KEY_NAME);
/* 506 */               this.qualityLevels = parseInt(parser, KEY_QUALITY_LEVELS, -1);
/* 507 */               this.url = parseRequiredString(parser, KEY_URL);
/* 508 */               this.maxWidth = parseInt(parser, KEY_MAX_WIDTH, -1);
/* 509 */               this.maxHeight = parseInt(parser, KEY_MAX_HEIGHT, -1);
/* 510 */               this.displayWidth = parseInt(parser, KEY_DISPLAY_WIDTH, -1);
/* 511 */               this.displayHeight = parseInt(parser, KEY_DISPLAY_HEIGHT, -1);
/* 512 */               this.language = parser.getAttributeValue(null, KEY_LANGUAGE);
/* 513 */               this.timeScale = parseInt(parser, KEY_TIME_SCALE, -1);
                        if (this.timeScale == -1) {
/* 515 */                   this.timeScale = ((Long) getNormalizedAttribute(KEY_TIME_SCALE)).longValue();
                        }
/* 517 */               this.startTimes = new long[parseRequiredInt(parser, KEY_CHUNKS)];
                    }

/* 520 */           private int parseType(XmlPullParser parser) throws ParserException {
/* 521 */               String value = parser.getAttributeValue(null, KEY_TYPE);
/* 522 */               if (value != null) {
/* 523 */                   if ("audio".equalsIgnoreCase(value)) {
/* 524 */                       return 0;
                            }
/* 525 */                   if ("video".equalsIgnoreCase(value)) {
/* 526 */                       return 1;
                            }
/* 527 */                   if ("text".equalsIgnoreCase(value)) {
/* 528 */                       return 2;
                            }
/* 530 */                   throw new ParserException("Invalid key value[" + value + "]");
                        }
/* 533 */               throw new MissingFieldException(KEY_TYPE);
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 537 */           public void addChild(Object child) {
/* 538 */               if (child instanceof SmoothStreamingManifest.TrackElement) {
/* 539 */                   this.tracks.add((SmoothStreamingManifest.TrackElement) child);
                        }
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 544 */           public Object build() {
/* 545 */               SmoothStreamingManifest.TrackElement[] trackElements = new SmoothStreamingManifest.TrackElement[this.tracks.size()];
/* 546 */               this.tracks.toArray(trackElements);
/* 547 */               return new SmoothStreamingManifest.StreamElement(this.type, this.subType, this.timeScale, this.name, this.qualityLevels, this.url, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, trackElements, this.startTimes);
                    }
                }

/* 588 */       private static class TrackElementParser extends ElementParser {
                    private static final String KEY_AUDIO_TAG = "AudioTag";
                    private static final String KEY_BITRATE = "Bitrate";
                    private static final String KEY_BITS_PER_SAMPLE = "BitsPerSample";
                    private static final String KEY_CHANNELS = "Channels";
                    private static final String KEY_CODEC_PRIVATE_DATA = "CodecPrivateData";
                    private static final String KEY_FOUR_CC = "FourCC";
                    private static final String KEY_INDEX = "Index";
                    private static final String KEY_MAX_HEIGHT = "MaxHeight";
                    private static final String KEY_MAX_WIDTH = "MaxWidth";
                    private static final String KEY_NAL_UNIT_LENGTH_FIELD = "NALUnitLengthField";
                    private static final String KEY_PACKET_SIZE = "PacketSize";
                    private static final String KEY_SAMPLING_RATE = "SamplingRate";
                    private static final String KEY_TYPE = "Type";
                    public static final String TAG = "QualityLevel";
                    private int audioTag;
                    private int bitPerSample;
                    private int bitrate;
                    private int channels;
                    private String content;
                    private final List<byte[]> csd;
                    private String fourCC;
                    private int index;
                    private int level;
                    private int maxHeight;
                    private int maxWidth;
                    private int nalUnitLengthField;
                    private int packetSize;
                    private int profile;
                    private int samplingRate;

/* 589 */           public TrackElementParser(ElementParser parent) {
/* 590 */               super(TAG, parent);
/* 591 */               this.csd = new LinkedList();
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 595 */           public void parseStartTag(XmlPullParser parser) throws ParserException {
/* 596 */               int type = ((Integer) getNormalizedAttribute(KEY_TYPE)).intValue();
/* 597 */               this.content = null;
/* 600 */               this.index = parseInt(parser, KEY_INDEX, -1);
/* 601 */               this.bitrate = parseRequiredInt(parser, KEY_BITRATE);
/* 602 */               this.nalUnitLengthField = parseInt(parser, KEY_NAL_UNIT_LENGTH_FIELD, 4);
/* 604 */               if (type == 1) {
/* 605 */                   this.maxHeight = parseRequiredInt(parser, KEY_MAX_HEIGHT);
/* 606 */                   this.maxWidth = parseRequiredInt(parser, KEY_MAX_WIDTH);
/* 607 */                   this.fourCC = parseRequiredString(parser, KEY_FOUR_CC);
                        } else {
/* 609 */                   this.maxHeight = -1;
/* 610 */                   this.maxWidth = -1;
/* 611 */                   this.fourCC = parser.getAttributeValue(null, KEY_FOUR_CC);
                        }
/* 614 */               if (type == 0) {
/* 615 */                   this.samplingRate = parseRequiredInt(parser, KEY_SAMPLING_RATE);
/* 616 */                   this.channels = parseRequiredInt(parser, KEY_CHANNELS);
/* 617 */                   this.bitPerSample = parseRequiredInt(parser, KEY_BITS_PER_SAMPLE);
/* 618 */                   this.packetSize = parseRequiredInt(parser, KEY_PACKET_SIZE);
/* 619 */                   this.audioTag = parseRequiredInt(parser, KEY_AUDIO_TAG);
                        } else {
/* 621 */                   this.samplingRate = -1;
/* 622 */                   this.channels = -1;
/* 623 */                   this.bitPerSample = -1;
/* 624 */                   this.packetSize = -1;
/* 625 */                   this.audioTag = -1;
                        }
/* 628 */               String value = parser.getAttributeValue(null, KEY_CODEC_PRIVATE_DATA);
/* 629 */               if (value != null && value.length() > 0) {
/* 630 */                   byte[] codecPrivateData = hexStringToByteArray(value);
/* 631 */                   byte[][] split = CodecSpecificDataUtil.splitNalUnits(codecPrivateData);
/* 632 */                   if (split == null) {
/* 633 */                       this.csd.add(codecPrivateData);
/* 645 */                       return;
                            }
                            for (int i = 0; i < split.length; i++) {
/* 636 */                       Pair<Integer, Integer> spsParameters = CodecSpecificDataUtil.parseSpsNalUnit(split[i]);
/* 637 */                       if (spsParameters != null) {
/* 638 */                           this.profile = ((Integer) spsParameters.first).intValue();
/* 639 */                           this.level = ((Integer) spsParameters.second).intValue();
                                }
/* 641 */                       this.csd.add(split[i]);
                            }
                        }
                    }

/* 647 */           private byte[] hexStringToByteArray(String hexString) {
/* 648 */               int length = hexString.length();
/* 649 */               byte[] data = new byte[length / 2];
                        for (int i = 0; i < data.length; i++) {
/* 651 */                   int stringOffset = i * 2;
/* 652 */                   data[i] = (byte) ((Character.digit(hexString.charAt(stringOffset), 16) << 4) + Character.digit(hexString.charAt(stringOffset + 1), 16));
                        }
/* 655 */               return data;
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 659 */           public void parseText(XmlPullParser parser) {
/* 660 */               this.content = parser.getText();
                    }

                    @Override // com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser.ElementParser
/* 664 */           public Object build() {
/* 665 */               byte[][] csdArray = (byte[][]) null;
/* 666 */               if (!this.csd.isEmpty()) {
/* 667 */                   csdArray = new byte[this.csd.size()][];
/* 668 */                   this.csd.toArray(csdArray);
                        }
/* 670 */               return new SmoothStreamingManifest.TrackElement(this.index, this.bitrate, this.fourCC, csdArray, this.profile, this.level, this.maxWidth, this.maxHeight, this.samplingRate, this.channels, this.packetSize, this.audioTag, this.bitPerSample, this.nalUnitLengthField, this.content);
                    }
                }
            }
