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
            
            
    public class SmoothStreamingManifestParser {
                private final XmlPullParserFactory xmlParserFactory;

        public SmoothStreamingManifestParser() {
                    try {
                this.xmlParserFactory = XmlPullParserFactory.newInstance();
                    } catch (XmlPullParserException e) {
                throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
                    }
                }

        public SmoothStreamingManifest parse(InputStream inputStream, String inputEncoding) throws IOException {
                    try {
                XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
                xmlParser.setInput(inputStream, inputEncoding);
                SmoothStreamMediaParser smoothStreamMediaParser = new SmoothStreamMediaParser(null);
                return (SmoothStreamingManifest) smoothStreamMediaParser.parse(xmlParser);
                    } catch (XmlPullParserException e) {
                throw new ParserException(e);
                    }
                }

        public static class MissingFieldException extends ParserException {
            public MissingFieldException(String fieldName) {
                super("Missing required field: " + fieldName);
                    }
                }

        private static abstract class ElementParser {
                    private final List<Pair<String, Object>> normalizedAttributes = new LinkedList();
                    private final ElementParser parent;
                    private final String tag;

                    protected abstract Object build();

            public ElementParser(String tag, ElementParser parent) {
                this.tag = tag;
               this.parent = parent;
                    }

           public final Object parse(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
               boolean foundStartTag = false;
               int skippingElementDepth = 0;
                        while (true) {
                   int eventType = xmlParser.getEventType();
                            switch (eventType) {
                                case 1:
                                    return null;
                                case 2:
                           String tagName = xmlParser.getName();
                           if (this.tag.equals(tagName)) {
                               foundStartTag = true;
                               parseStartTag(xmlParser);
                                    } else if (foundStartTag) {
                               if (skippingElementDepth > 0) {
                                   skippingElementDepth++;
                                        } else if (handleChildInline(tagName)) {
                                   parseStartTag(xmlParser);
                                        } else {
                                   ElementParser childElementParser = newChildParser(this, tagName);
                                   if (childElementParser == null) {
                                       skippingElementDepth = 1;
                                            } else {
                                       addChild(childElementParser.parse(xmlParser));
                                            }
                                        }
                                    }
                                    break;
                                case 3:
                           if (!foundStartTag) {
                                        continue;
                                    } else if (skippingElementDepth > 0) {
                               skippingElementDepth--;
                                    } else {
                               String tagName2 = xmlParser.getName();
                               parseEndTag(xmlParser);
                               if (!handleChildInline(tagName2)) {
                                   return build();
                                        }
                                    }
                                    break;
                                case 4:
                           if (foundStartTag && skippingElementDepth == 0) {
                               parseText(xmlParser);
                                    }
                                    break;
                            }
                   xmlParser.next();
                        }
                    }

           private ElementParser newChildParser(ElementParser parent, String name) {
               if (TrackElementParser.TAG.equals(name)) {
                   return new TrackElementParser(parent);
                        }
               if (ProtectionElementParser.TAG.equals(name)) {
                   return new ProtectionElementParser(parent);
                        }
               if (StreamElementParser.TAG.equals(name)) {
                   return new StreamElementParser(parent);
                        }
               return null;
                    }

           protected final void putNormalizedAttribute(String key, Object value) {
               this.normalizedAttributes.add(Pair.create(key, value));
                    }

           protected final Object getNormalizedAttribute(String key) {
                        for (int i = 0; i < this.normalizedAttributes.size(); i++) {
                   Pair<String, Object> pair = this.normalizedAttributes.get(i);
                   if (((String) pair.first).equals(key)) {
                       return pair.second;
                            }
                        }
               if (this.parent == null) {
                            return null;
                        }
                        return this.parent.getNormalizedAttribute(key);
                    }

           protected boolean handleChildInline(String tagName) {
               return false;
                    }

           protected void parseStartTag(XmlPullParser xmlParser) throws ParserException {
                    }

           protected void parseText(XmlPullParser xmlParser) throws ParserException {
                    }

           protected void parseEndTag(XmlPullParser xmlParser) throws ParserException {
                    }

           protected void addChild(Object parsedChild) {
                    }

           protected final String parseRequiredString(XmlPullParser parser, String key) throws MissingFieldException {
               String value = parser.getAttributeValue(null, key);
               if (value != null) {
                   return value;
                        }
               throw new MissingFieldException(key);
                    }

           protected final int parseInt(XmlPullParser parser, String key, int defaultValue) throws ParserException {
               String value = parser.getAttributeValue(null, key);
               if (value != null) {
                            try {
                       int defaultValue2 = Integer.parseInt(value);
                       return defaultValue2;
                            } catch (NumberFormatException e) {
                       throw new ParserException(e);
                            }
                        }
               return defaultValue;
                    }

           protected final int parseRequiredInt(XmlPullParser parser, String key) throws ParserException {
               String value = parser.getAttributeValue(null, key);
               if (value != null) {
                            try {
                       return Integer.parseInt(value);
                            } catch (NumberFormatException e) {
                       throw new ParserException(e);
                            }
                        }
               throw new MissingFieldException(key);
                    }

           protected final long parseLong(XmlPullParser parser, String key, long defaultValue) throws ParserException {
               String value = parser.getAttributeValue(null, key);
               if (value != null) {
                            try {
                       long defaultValue2 = Long.parseLong(value);
                       return defaultValue2;
                            } catch (NumberFormatException e) {
                       throw new ParserException(e);
                            }
                        }
               return defaultValue;
                    }

           protected final long parseRequiredLong(XmlPullParser parser, String key) throws ParserException {
               String value = parser.getAttributeValue(null, key);
               if (value != null) {
                            try {
                       return Long.parseLong(value);
                            } catch (NumberFormatException e) {
                       throw new ParserException(e);
                            }
                        }
               throw new MissingFieldException(key);
                    }
                }

       private static class SmoothStreamMediaParser extends ElementParser {
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

           public SmoothStreamMediaParser(ElementParser parent) {
               super(TAG, parent);
               this.lookAheadCount = -1;
               this.protectionElement = null;
               this.streamElements = new LinkedList();
                    }

                    @Override 
           public void parseStartTag(XmlPullParser parser) throws ParserException {
               this.majorVersion = parseRequiredInt(parser, KEY_MAJOR_VERSION);
               this.minorVersion = parseRequiredInt(parser, KEY_MINOR_VERSION);
               this.timeScale = parseLong(parser, KEY_TIME_SCALE, 10000000L);
               this.duration = parseRequiredLong(parser, KEY_DURATION);
               this.lookAheadCount = parseInt(parser, KEY_LOOKAHEAD_COUNT, -1);
               putNormalizedAttribute(KEY_TIME_SCALE, Long.valueOf(this.timeScale));
                    }

                    @Override 
           public void addChild(Object child) {
               if (child instanceof SmoothStreamingManifest.StreamElement) {
                   this.streamElements.add((SmoothStreamingManifest.StreamElement) child);
                        } else if (child instanceof SmoothStreamingManifest.ProtectionElement) {
                   Assertions.checkState(this.protectionElement == null);
                   this.protectionElement = (SmoothStreamingManifest.ProtectionElement) child;
                        }
                    }

                    @Override 
           public Object build() {
               SmoothStreamingManifest.StreamElement[] streamElementArray = new SmoothStreamingManifest.StreamElement[this.streamElements.size()];
               this.streamElements.toArray(streamElementArray);
               return new SmoothStreamingManifest(this.majorVersion, this.minorVersion, this.timeScale, this.duration, this.lookAheadCount, this.protectionElement, streamElementArray);
                    }
                }

       private static class ProtectionElementParser extends ElementParser {
                    public static final String KEY_SYSTEM_ID = "SystemID";
                    public static final String TAG = "Protection";
                    public static final String TAG_PROTECTION_HEADER = "ProtectionHeader";
                    private boolean inProtectionHeader;
                    private byte[] initData;
                    private UUID uuid;

           public ProtectionElementParser(ElementParser parent) {
               super(TAG, parent);
                    }

                    @Override 
           public boolean handleChildInline(String tag) {
               return TAG_PROTECTION_HEADER.equals(tag);
                    }

                    @Override 
           public void parseStartTag(XmlPullParser parser) {
               if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                   this.inProtectionHeader = true;
                   String uuidString = parser.getAttributeValue(null, KEY_SYSTEM_ID);
                   this.uuid = UUID.fromString(uuidString);
                        }
                    }

                    @Override 
           public void parseText(XmlPullParser parser) {
               if (this.inProtectionHeader) {
                   this.initData = Base64.decode(parser.getText(), 0);
                        }
                    }

                    @Override 
           public void parseEndTag(XmlPullParser parser) {
               if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                   this.inProtectionHeader = false;
                        }
                    }

                    @Override 
           public Object build() {
               return new SmoothStreamingManifest.ProtectionElement(this.uuid, this.initData);
                    }
                }

       private static class StreamElementParser extends ElementParser {
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

           public StreamElementParser(ElementParser parent) {
               super(TAG, parent);
               this.tracks = new LinkedList();
                    }

                    @Override 
           public boolean handleChildInline(String tag) {
               return TAG_STREAM_FRAGMENT.equals(tag);
                    }

                    @Override 
           public void parseStartTag(XmlPullParser parser) throws ParserException {
               if (TAG_STREAM_FRAGMENT.equals(parser.getName())) {
                   parseStreamFragmentStartTag(parser);
                        } else {
                   parseStreamElementStartTag(parser);
                        }
                    }

           private void parseStreamFragmentStartTag(XmlPullParser parser) throws ParserException {
               this.startTimes[this.chunkIndex] = parseLong(parser, KEY_FRAGMENT_START_TIME, -1L);
                        if (this.startTimes[this.chunkIndex] == -1) {
                   if (this.chunkIndex == 0) {
                       this.startTimes[this.chunkIndex] = 0;
                            } else if (this.previousChunkDuration != -1) {
                       this.startTimes[this.chunkIndex] = this.startTimes[this.chunkIndex - 1] + this.previousChunkDuration;
                            } else {
                       throw new ParserException("Unable to infer start time");
                            }
                        }
               this.previousChunkDuration = parseLong(parser, KEY_FRAGMENT_DURATION, -1L);
               this.chunkIndex++;
                    }

           private void parseStreamElementStartTag(XmlPullParser parser) throws ParserException {
               this.type = parseType(parser);
               putNormalizedAttribute(KEY_TYPE, Integer.valueOf(this.type));
               if (this.type == 2) {
                   this.subType = parseRequiredString(parser, KEY_SUB_TYPE);
                        } else {
                   this.subType = parser.getAttributeValue(null, KEY_SUB_TYPE);
                        }
               this.name = parser.getAttributeValue(null, KEY_NAME);
               this.qualityLevels = parseInt(parser, KEY_QUALITY_LEVELS, -1);
               this.url = parseRequiredString(parser, KEY_URL);
               this.maxWidth = parseInt(parser, KEY_MAX_WIDTH, -1);
               this.maxHeight = parseInt(parser, KEY_MAX_HEIGHT, -1);
               this.displayWidth = parseInt(parser, KEY_DISPLAY_WIDTH, -1);
               this.displayHeight = parseInt(parser, KEY_DISPLAY_HEIGHT, -1);
               this.language = parser.getAttributeValue(null, KEY_LANGUAGE);
               this.timeScale = parseInt(parser, KEY_TIME_SCALE, -1);
                        if (this.timeScale == -1) {
                   this.timeScale = ((Long) getNormalizedAttribute(KEY_TIME_SCALE)).longValue();
                        }
               this.startTimes = new long[parseRequiredInt(parser, KEY_CHUNKS)];
                    }

           private int parseType(XmlPullParser parser) throws ParserException {
               String value = parser.getAttributeValue(null, KEY_TYPE);
               if (value != null) {
                   if ("audio".equalsIgnoreCase(value)) {
                       return 0;
                            }
                   if ("video".equalsIgnoreCase(value)) {
                       return 1;
                            }
                   if ("text".equalsIgnoreCase(value)) {
                       return 2;
                            }
                   throw new ParserException("Invalid key value[" + value + "]");
                        }
               throw new MissingFieldException(KEY_TYPE);
                    }

                    @Override 
           public void addChild(Object child) {
               if (child instanceof SmoothStreamingManifest.TrackElement) {
                   this.tracks.add((SmoothStreamingManifest.TrackElement) child);
                        }
                    }

                    @Override 
           public Object build() {
               SmoothStreamingManifest.TrackElement[] trackElements = new SmoothStreamingManifest.TrackElement[this.tracks.size()];
               this.tracks.toArray(trackElements);
               return new SmoothStreamingManifest.StreamElement(this.type, this.subType, this.timeScale, this.name, this.qualityLevels, this.url, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, trackElements, this.startTimes);
                    }
                }

       private static class TrackElementParser extends ElementParser {
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

           public TrackElementParser(ElementParser parent) {
               super(TAG, parent);
               this.csd = new LinkedList();
                    }

                    @Override 
           public void parseStartTag(XmlPullParser parser) throws ParserException {
               int type = ((Integer) getNormalizedAttribute(KEY_TYPE)).intValue();
               this.content = null;
               this.index = parseInt(parser, KEY_INDEX, -1);
               this.bitrate = parseRequiredInt(parser, KEY_BITRATE);
               this.nalUnitLengthField = parseInt(parser, KEY_NAL_UNIT_LENGTH_FIELD, 4);
               if (type == 1) {
                   this.maxHeight = parseRequiredInt(parser, KEY_MAX_HEIGHT);
                   this.maxWidth = parseRequiredInt(parser, KEY_MAX_WIDTH);
                   this.fourCC = parseRequiredString(parser, KEY_FOUR_CC);
                        } else {
                   this.maxHeight = -1;
                   this.maxWidth = -1;
                   this.fourCC = parser.getAttributeValue(null, KEY_FOUR_CC);
                        }
               if (type == 0) {
                   this.samplingRate = parseRequiredInt(parser, KEY_SAMPLING_RATE);
                   this.channels = parseRequiredInt(parser, KEY_CHANNELS);
                   this.bitPerSample = parseRequiredInt(parser, KEY_BITS_PER_SAMPLE);
                   this.packetSize = parseRequiredInt(parser, KEY_PACKET_SIZE);
                   this.audioTag = parseRequiredInt(parser, KEY_AUDIO_TAG);
                        } else {
                   this.samplingRate = -1;
                   this.channels = -1;
                   this.bitPerSample = -1;
                   this.packetSize = -1;
                   this.audioTag = -1;
                        }
               String value = parser.getAttributeValue(null, KEY_CODEC_PRIVATE_DATA);
               if (value != null && value.length() > 0) {
                   byte[] codecPrivateData = hexStringToByteArray(value);
                   byte[][] split = CodecSpecificDataUtil.splitNalUnits(codecPrivateData);
                   if (split == null) {
                       this.csd.add(codecPrivateData);
                       return;
                            }
                            for (int i = 0; i < split.length; i++) {
                       Pair<Integer, Integer> spsParameters = CodecSpecificDataUtil.parseSpsNalUnit(split[i]);
                       if (spsParameters != null) {
                           this.profile = ((Integer) spsParameters.first).intValue();
                           this.level = ((Integer) spsParameters.second).intValue();
                                }
                       this.csd.add(split[i]);
                            }
                        }
                    }

           private byte[] hexStringToByteArray(String hexString) {
               int length = hexString.length();
               byte[] data = new byte[length / 2];
                        for (int i = 0; i < data.length; i++) {
                   int stringOffset = i * 2;
                   data[i] = (byte) ((Character.digit(hexString.charAt(stringOffset), 16) << 4) + Character.digit(hexString.charAt(stringOffset + 1), 16));
                        }
               return data;
                    }

                    @Override 
           public void parseText(XmlPullParser parser) {
               this.content = parser.getText();
                    }

                    @Override 
           public Object build() {
               byte[][] csdArray = (byte[][]) null;
               if (!this.csd.isEmpty()) {
                   csdArray = new byte[this.csd.size()][];
                   this.csd.toArray(csdArray);
                        }
               return new SmoothStreamingManifest.TrackElement(this.index, this.bitrate, this.fourCC, csdArray, this.profile, this.level, this.maxWidth, this.maxHeight, this.samplingRate, this.channels, this.packetSize, this.audioTag, this.bitPerSample, this.nalUnitLengthField, this.content);
                    }
                }
            }
