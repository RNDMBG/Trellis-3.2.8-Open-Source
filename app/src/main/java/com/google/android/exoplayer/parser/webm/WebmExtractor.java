            package com.google.android.exoplayer.parser.webm;

            import android.annotation.TargetApi;
            import co.vine.android.util.AudioUtils;
            import com.flurry.android.Constants;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.parser.Extractor;
            import com.google.android.exoplayer.parser.SegmentIndex;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.LongArray;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.nio.ByteBuffer;
            import java.util.Arrays;
            import java.util.Map;
            import java.util.UUID;
            import java.util.concurrent.TimeUnit;
            
            
            @TargetApi(16)
   public final class WebmExtractor implements Extractor {
                private static final String CODEC_ID_VP9 = "V_VP9";
                private static final String DOC_TYPE_WEBM = "webm";
                private static final int ID_CLUSTER = 524531317;
                private static final int ID_CODEC_ID = 134;
                private static final int ID_CUES = 475249515;
                private static final int ID_CUE_CLUSTER_POSITION = 241;
                private static final int ID_CUE_POINT = 187;
                private static final int ID_CUE_TIME = 179;
                private static final int ID_CUE_TRACK_POSITIONS = 183;
                private static final int ID_DOC_TYPE = 17026;
                private static final int ID_DOC_TYPE_READ_VERSION = 17029;
                private static final int ID_DURATION = 17545;
                private static final int ID_EBML = 440786851;
                private static final int ID_EBML_READ_VERSION = 17143;
                private static final int ID_INFO = 357149030;
                private static final int ID_PIXEL_HEIGHT = 186;
                private static final int ID_PIXEL_WIDTH = 176;
                private static final int ID_SEGMENT = 408125543;
                private static final int ID_SIMPLE_BLOCK = 163;
                private static final int ID_TIMECODE_SCALE = 2807729;
                private static final int ID_TIME_CODE = 231;
                private static final int ID_TRACKS = 374648427;
                private static final int ID_TRACK_ENTRY = 174;
                private static final int ID_VIDEO = 224;
                private static final int LACING_EBML = 3;
                private static final int LACING_FIXED = 2;
                private static final int LACING_NONE = 0;
                private static final int LACING_XIPH = 1;
                private static final int READ_TERMINATING_RESULTS = 39;
                private static final int UNKNOWN = -1;
                private long clusterTimecodeUs;
                private LongArray cueClusterPositions;
                private LongArray cueTimesUs;
                private SegmentIndex cues;
                private long cuesSizeBytes;
                private long durationUs;
                private MediaFormat format;
                private int pixelHeight;
                private int pixelWidth;
                private int readResults;
                private final EbmlReader reader;
                private SampleHolder sampleHolder;
                private long segmentEndOffsetBytes;
                private long segmentStartOffsetBytes;
                private final byte[] simpleBlockTimecodeAndFlags;
                private long simpleBlockTimecodeUs;
                private long timecodeScale;

       public WebmExtractor() {
           this(new DefaultEbmlReader());
                }

       WebmExtractor(EbmlReader reader) {
            this.simpleBlockTimecodeAndFlags = new byte[3];
            this.segmentStartOffsetBytes = -1L;
            this.segmentEndOffsetBytes = -1L;
            this.timecodeScale = AudioUtils.MICROS_PER_SECOND;
            this.durationUs = -1L;
            this.pixelWidth = -1;
            this.pixelHeight = -1;
            this.cuesSizeBytes = -1L;
           this.clusterTimecodeUs = -1L;
           this.simpleBlockTimecodeUs = -1L;
           this.reader = reader;
           this.reader.setEventHandler(new InnerEbmlEventHandler(this, null));
                }

                @Override 
       public int read(NonBlockingInputStream inputStream, SampleHolder sampleHolder) {
           this.sampleHolder = sampleHolder;
           this.readResults = 0;
           while ((this.readResults & 39) == 0) {
               int ebmlReadResult = this.reader.read(inputStream);
               if (ebmlReadResult == 1) {
                   this.readResults |= 1;
                        } else if (ebmlReadResult == 2) {
                   this.readResults |= 2;
                        }
                    }
           this.sampleHolder = null;
           return this.readResults;
                }

                @Override 
       public boolean seekTo(long seekTimeUs, boolean allowNoop) {
                    int clusterIndex;
           if (allowNoop && this.cues != null && this.clusterTimecodeUs != -1 && this.simpleBlockTimecodeUs != -1 && seekTimeUs >= this.simpleBlockTimecodeUs && (clusterIndex = Arrays.binarySearch(this.cues.timesUs, this.clusterTimecodeUs)) >= 0 && seekTimeUs < this.clusterTimecodeUs + this.cues.durationsUs[clusterIndex]) {
               return false;
                    }
           this.clusterTimecodeUs = -1L;
           this.simpleBlockTimecodeUs = -1L;
           this.reader.reset();
           return true;
                }

                @Override 
       public SegmentIndex getIndex() {
           return this.cues;
                }

                @Override 
       public boolean hasRelativeIndexOffsets() {
           return false;
                }

                @Override 
       public MediaFormat getFormat() {
           return this.format;
                }

                @Override 
       public Map<UUID, byte[]> getPsshInfo() {
           return null;
                }

       int getElementType(int id) {
                    switch (id) {
                        case 134:
                        case ID_DOC_TYPE :
                   return 3;
                        case 163:
                   return 4;
                        case ID_TRACK_ENTRY :
                        case ID_CUE_TRACK_POSITIONS :
                        case ID_CUE_POINT :
                        case ID_VIDEO :
                        case ID_INFO :
                        case ID_TRACKS :
                        case ID_SEGMENT :
                        case ID_EBML :
                        case ID_CUES :
                        case ID_CLUSTER :
                   return 1;
                        case ID_PIXEL_WIDTH :
                        case ID_CUE_TIME :
                        case ID_PIXEL_HEIGHT :
                        case ID_TIME_CODE :
                        case ID_CUE_CLUSTER_POSITION :
                        case ID_DOC_TYPE_READ_VERSION :
                        case ID_EBML_READ_VERSION :
                        case ID_TIMECODE_SCALE :
                   return 2;
                        case ID_DURATION :
                   return 5;
                        default:
                   return 0;
                    }
                }

       boolean onMasterElementStart(int id, long elementOffsetBytes, int headerSizeBytes, long contentsSizeBytes) {
                    switch (id) {
                        case ID_SEGMENT :
                            if (this.segmentStartOffsetBytes != -1 || this.segmentEndOffsetBytes != -1) {
                       throw new IllegalStateException("Multiple Segment elements not supported");
                            }
                   this.segmentStartOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes;
                   this.segmentEndOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes + contentsSizeBytes;
                   return true;
                        case ID_CUES :
                   this.cuesSizeBytes = ((long) headerSizeBytes) + contentsSizeBytes;
                   this.cueTimesUs = new LongArray();
                   this.cueClusterPositions = new LongArray();
                   return true;
                        default:
                   return true;
                    }
                }

       boolean onMasterElementEnd(int id) {
                    switch (id) {
                        case ID_VIDEO :
                   buildFormat();
                            return true;
                        case ID_CUES :
                   buildCues();
                   return false;
                        default:
                            return true;
                    }
                }

       boolean onIntegerElement(int id, long value) {
                    switch (id) {
                        case ID_PIXEL_WIDTH :
                   this.pixelWidth = (int) value;
                   return true;
                        case ID_CUE_TIME :
                   this.cueTimesUs.add(scaleTimecodeToUs(value));
                   return true;
                        case ID_PIXEL_HEIGHT :
                   this.pixelHeight = (int) value;
                   return true;
                        case ID_TIME_CODE :
                   this.clusterTimecodeUs = scaleTimecodeToUs(value);
                   return true;
                        case ID_CUE_CLUSTER_POSITION :
                   this.cueClusterPositions.add(value);
                   return true;
                        case ID_DOC_TYPE_READ_VERSION :
                            if (value < 1 || value > 2) {
                       throw new IllegalArgumentException("DocTypeReadVersion " + value + " not supported");
                            }
                   return true;
                        case ID_EBML_READ_VERSION :
                            if (value != 1) {
                       throw new IllegalArgumentException("EBMLReadVersion " + value + " not supported");
                            }
                   return true;
                        case ID_TIMECODE_SCALE :
                   this.timecodeScale = value;
                   return true;
                        default:
                   return true;
                    }
                }

       boolean onFloatElement(int id, double value) {
           if (id == ID_DURATION) {
               this.durationUs = scaleTimecodeToUs((long) value);
               return true;
                    }
           return true;
                }

       boolean onStringElement(int id, String value) {
                    switch (id) {
                        case 134:
                   if (!CODEC_ID_VP9.equals(value)) {
                       throw new IllegalArgumentException("CodecID " + value + " not supported");
                            }
                   return true;
                        case ID_DOC_TYPE :
                   if (!"webm".equals(value)) {
                       throw new IllegalArgumentException("DocType " + value + " not supported");
                            }
                   return true;
                        default:
                   return true;
                    }
                }

       boolean onBinaryElement(int id, long elementOffsetBytes, int headerSizeBytes, int contentsSizeBytes, NonBlockingInputStream inputStream) {
           if (id == 163) {
               if (this.sampleHolder == null) {
                   this.readResults |= 32;
                   return false;
                        }
               this.reader.readVarint(inputStream);
               this.reader.readBytes(inputStream, this.simpleBlockTimecodeAndFlags, 3);
               int timecode = (this.simpleBlockTimecodeAndFlags[0] << 8) | (this.simpleBlockTimecodeAndFlags[1] & Constants.UNKNOWN);
               long timecodeUs = scaleTimecodeToUs(timecode);
               boolean keyframe = (this.simpleBlockTimecodeAndFlags[2] & 128) == 128;
               boolean invisible = (this.simpleBlockTimecodeAndFlags[2] & 8) == 8;
               int lacing = (this.simpleBlockTimecodeAndFlags[2] & 6) >> 1;
                        switch (lacing) {
                            case 0:
                       long elementEndOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes + ((long) contentsSizeBytes);
                       this.simpleBlockTimecodeUs = this.clusterTimecodeUs + timecodeUs;
                       this.sampleHolder.flags = keyframe ? 1 : 0;
                       this.sampleHolder.decodeOnly = invisible;
                       this.sampleHolder.timeUs = this.clusterTimecodeUs + timecodeUs;
                       this.sampleHolder.size = (int) (elementEndOffsetBytes - this.reader.getBytesRead());
                       ByteBuffer outputData = this.sampleHolder.data;
                       if (this.sampleHolder.allowDataBufferReplacement && (this.sampleHolder.data == null || this.sampleHolder.data.capacity() < this.sampleHolder.size)) {
                           outputData = ByteBuffer.allocate(this.sampleHolder.size);
                           this.sampleHolder.data = outputData;
                                }
                       if (outputData == null) {
                           this.reader.skipBytes(inputStream, this.sampleHolder.size);
                           this.sampleHolder.size = 0;
                                } else {
                           this.reader.readBytes(inputStream, outputData, this.sampleHolder.size);
                                }
                       this.readResults |= 4;
                                break;
                            default:
                       throw new IllegalStateException("Lacing mode " + lacing + " not supported");
                        }
                    }
           return true;
                }

       private long scaleTimecodeToUs(long unscaledTimecode) {
           return TimeUnit.NANOSECONDS.toMicros(this.timecodeScale * unscaledTimecode);
                }

       private void buildFormat() {
           if (this.pixelWidth != -1 && this.pixelHeight != -1 && (this.format == null || this.format.width != this.pixelWidth || this.format.height != this.pixelHeight)) {
               this.format = MediaFormat.createVideoFormat(MimeTypes.VIDEO_VP9, -1, this.pixelWidth, this.pixelHeight, null);
               this.readResults |= 8;
                    } else if (this.format == null) {
               throw new IllegalStateException("Unable to build format");
                    }
                }

       private void buildCues() {
                    if (this.segmentStartOffsetBytes == -1) {
               throw new IllegalStateException("Segment start/end offsets unknown");
                    }
                    if (this.durationUs == -1) {
               throw new IllegalStateException("Duration unknown");
                    }
                    if (this.cuesSizeBytes == -1) {
               throw new IllegalStateException("Cues size unknown");
                    }
           if (this.cueTimesUs == null || this.cueClusterPositions == null || this.cueTimesUs.size() == 0 || this.cueTimesUs.size() != this.cueClusterPositions.size()) {
               throw new IllegalStateException("Invalid/missing cue points");
                    }
           int cuePointsSize = this.cueTimesUs.size();
           int[] sizes = new int[cuePointsSize];
           long[] offsets = new long[cuePointsSize];
           long[] durationsUs = new long[cuePointsSize];
           long[] timesUs = new long[cuePointsSize];
                    for (int i = 0; i < cuePointsSize; i++) {
               timesUs[i] = this.cueTimesUs.get(i);
               offsets[i] = this.segmentStartOffsetBytes + this.cueClusterPositions.get(i);
                    }
                    for (int i2 = 0; i2 < cuePointsSize - 1; i2++) {
               sizes[i2] = (int) (offsets[i2 + 1] - offsets[i2]);
               durationsUs[i2] = timesUs[i2 + 1] - timesUs[i2];
                    }
           sizes[cuePointsSize - 1] = (int) (this.segmentEndOffsetBytes - offsets[cuePointsSize - 1]);
           durationsUs[cuePointsSize - 1] = this.durationUs - timesUs[cuePointsSize - 1];
           this.cues = new SegmentIndex((int) this.cuesSizeBytes, sizes, offsets, durationsUs, timesUs);
           this.cueTimesUs = null;
           this.cueClusterPositions = null;
           this.readResults |= 16;
                }

       private final class InnerEbmlEventHandler implements EbmlEventHandler {
           private InnerEbmlEventHandler() {
                    }

            InnerEbmlEventHandler(WebmExtractor x0, Object x1) {
               this();
                    }

                    @Override 
           public int getElementType(int id) {
               return WebmExtractor.this.getElementType(id);
                    }

                    @Override 
           public void onMasterElementStart(int id, long elementOffsetBytes, int headerSizeBytes, long contentsSizeBytes) {
               WebmExtractor.this.onMasterElementStart(id, elementOffsetBytes, headerSizeBytes, contentsSizeBytes);
                    }

                    @Override 
           public void onMasterElementEnd(int id) {
               WebmExtractor.this.onMasterElementEnd(id);
                    }

                    @Override 
           public void onIntegerElement(int id, long value) {
               WebmExtractor.this.onIntegerElement(id, value);
                    }

                    @Override 
           public void onFloatElement(int id, double value) {
               WebmExtractor.this.onFloatElement(id, value);
                    }

                    @Override 
           public void onStringElement(int id, String value) {
               WebmExtractor.this.onStringElement(id, value);
                    }

                    @Override 
           public boolean onBinaryElement(int id, long elementOffsetBytes, int headerSizeBytes, int contentsSizeBytes, NonBlockingInputStream inputStream) {
               return WebmExtractor.this.onBinaryElement(id, elementOffsetBytes, headerSizeBytes, contentsSizeBytes, inputStream);
                    }
                }
            }
