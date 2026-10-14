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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 106 */   public final class WebmExtractor implements Extractor {
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

/* 107 */       public WebmExtractor() {
/* 108 */           this(new DefaultEbmlReader());
                }

/* 110 */       WebmExtractor(EbmlReader reader) {
/* 88 */            this.simpleBlockTimecodeAndFlags = new byte[3];
/* 93 */            this.segmentStartOffsetBytes = -1L;
/* 94 */            this.segmentEndOffsetBytes = -1L;
/* 95 */            this.timecodeScale = AudioUtils.MICROS_PER_SECOND;
/* 96 */            this.durationUs = -1L;
/* 97 */            this.pixelWidth = -1;
/* 98 */            this.pixelHeight = -1;
/* 99 */            this.cuesSizeBytes = -1L;
/* 100 */           this.clusterTimecodeUs = -1L;
/* 101 */           this.simpleBlockTimecodeUs = -1L;
/* 112 */           this.reader = reader;
/* 113 */           this.reader.setEventHandler(new InnerEbmlEventHandler(this, null));
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 117 */       public int read(NonBlockingInputStream inputStream, SampleHolder sampleHolder) {
/* 118 */           this.sampleHolder = sampleHolder;
/* 119 */           this.readResults = 0;
/* 120 */           while ((this.readResults & 39) == 0) {
/* 121 */               int ebmlReadResult = this.reader.read(inputStream);
/* 122 */               if (ebmlReadResult == 1) {
/* 123 */                   this.readResults |= 1;
                        } else if (ebmlReadResult == 2) {
/* 125 */                   this.readResults |= 2;
                        }
                    }
/* 128 */           this.sampleHolder = null;
/* 129 */           return this.readResults;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 133 */       public boolean seekTo(long seekTimeUs, boolean allowNoop) {
                    int clusterIndex;
/* 134 */           if (allowNoop && this.cues != null && this.clusterTimecodeUs != -1 && this.simpleBlockTimecodeUs != -1 && seekTimeUs >= this.simpleBlockTimecodeUs && (clusterIndex = Arrays.binarySearch(this.cues.timesUs, this.clusterTimecodeUs)) >= 0 && seekTimeUs < this.clusterTimecodeUs + this.cues.durationsUs[clusterIndex]) {
/* 141 */               return false;
                    }
/* 144 */           this.clusterTimecodeUs = -1L;
/* 145 */           this.simpleBlockTimecodeUs = -1L;
/* 146 */           this.reader.reset();
/* 147 */           return true;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 151 */       public SegmentIndex getIndex() {
/* 152 */           return this.cues;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 156 */       public boolean hasRelativeIndexOffsets() {
/* 157 */           return false;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 161 */       public MediaFormat getFormat() {
/* 162 */           return this.format;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 167 */       public Map<UUID, byte[]> getPsshInfo() {
/* 168 */           return null;
                }

/* 171 */       int getElementType(int id) {
                    switch (id) {
                        case 134:
                        case ID_DOC_TYPE /* 17026 */:
/* 195 */                   return 3;
                        case 163:
/* 197 */                   return 4;
                        case ID_TRACK_ENTRY /* 174 */:
                        case ID_CUE_TRACK_POSITIONS /* 183 */:
                        case ID_CUE_POINT /* 187 */:
                        case ID_VIDEO /* 224 */:
                        case ID_INFO /* 357149030 */:
                        case ID_TRACKS /* 374648427 */:
                        case ID_SEGMENT /* 408125543 */:
                        case ID_EBML /* 440786851 */:
                        case ID_CUES /* 475249515 */:
                        case ID_CLUSTER /* 524531317 */:
/* 183 */                   return 1;
                        case ID_PIXEL_WIDTH /* 176 */:
                        case ID_CUE_TIME /* 179 */:
                        case ID_PIXEL_HEIGHT /* 186 */:
                        case ID_TIME_CODE /* 231 */:
                        case ID_CUE_CLUSTER_POSITION /* 241 */:
                        case ID_DOC_TYPE_READ_VERSION /* 17029 */:
                        case ID_EBML_READ_VERSION /* 17143 */:
                        case ID_TIMECODE_SCALE /* 2807729 */:
/* 192 */                   return 2;
                        case ID_DURATION /* 17545 */:
/* 199 */                   return 5;
                        default:
/* 201 */                   return 0;
                    }
                }

/* 206 */       boolean onMasterElementStart(int id, long elementOffsetBytes, int headerSizeBytes, long contentsSizeBytes) {
                    switch (id) {
                        case ID_SEGMENT /* 408125543 */:
                            if (this.segmentStartOffsetBytes != -1 || this.segmentEndOffsetBytes != -1) {
/* 210 */                       throw new IllegalStateException("Multiple Segment elements not supported");
                            }
/* 212 */                   this.segmentStartOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes;
/* 213 */                   this.segmentEndOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes + contentsSizeBytes;
/* 223 */                   return true;
                        case ID_CUES /* 475249515 */:
/* 216 */                   this.cuesSizeBytes = ((long) headerSizeBytes) + contentsSizeBytes;
/* 217 */                   this.cueTimesUs = new LongArray();
/* 218 */                   this.cueClusterPositions = new LongArray();
/* 223 */                   return true;
                        default:
/* 223 */                   return true;
                    }
                }

/* 226 */       boolean onMasterElementEnd(int id) {
                    switch (id) {
                        case ID_VIDEO /* 224 */:
/* 232 */                   buildFormat();
                            return true;
                        case ID_CUES /* 475249515 */:
/* 229 */                   buildCues();
/* 230 */                   return false;
                        default:
                            return true;
                    }
                }

/* 239 */       boolean onIntegerElement(int id, long value) {
                    switch (id) {
                        case ID_PIXEL_WIDTH /* 176 */:
/* 257 */                   this.pixelWidth = (int) value;
/* 274 */                   return true;
                        case ID_CUE_TIME /* 179 */:
/* 263 */                   this.cueTimesUs.add(scaleTimecodeToUs(value));
/* 274 */                   return true;
                        case ID_PIXEL_HEIGHT /* 186 */:
/* 260 */                   this.pixelHeight = (int) value;
/* 274 */                   return true;
                        case ID_TIME_CODE /* 231 */:
/* 269 */                   this.clusterTimecodeUs = scaleTimecodeToUs(value);
/* 274 */                   return true;
                        case ID_CUE_CLUSTER_POSITION /* 241 */:
/* 266 */                   this.cueClusterPositions.add(value);
/* 274 */                   return true;
                        case ID_DOC_TYPE_READ_VERSION /* 17029 */:
                            if (value < 1 || value > 2) {
/* 250 */                       throw new IllegalArgumentException("DocTypeReadVersion " + value + " not supported");
                            }
/* 274 */                   return true;
                        case ID_EBML_READ_VERSION /* 17143 */:
                            if (value != 1) {
/* 244 */                       throw new IllegalArgumentException("EBMLReadVersion " + value + " not supported");
                            }
/* 274 */                   return true;
                        case ID_TIMECODE_SCALE /* 2807729 */:
/* 254 */                   this.timecodeScale = value;
/* 274 */                   return true;
                        default:
/* 274 */                   return true;
                    }
                }

/* 277 */       boolean onFloatElement(int id, double value) {
/* 278 */           if (id == ID_DURATION) {
/* 279 */               this.durationUs = scaleTimecodeToUs((long) value);
/* 281 */               return true;
                    }
/* 281 */           return true;
                }

/* 284 */       boolean onStringElement(int id, String value) {
                    switch (id) {
                        case 134:
/* 294 */                   if (!CODEC_ID_VP9.equals(value)) {
/* 295 */                       throw new IllegalArgumentException("CodecID " + value + " not supported");
                            }
/* 301 */                   return true;
                        case ID_DOC_TYPE /* 17026 */:
/* 288 */                   if (!"webm".equals(value)) {
/* 289 */                       throw new IllegalArgumentException("DocType " + value + " not supported");
                            }
/* 301 */                   return true;
                        default:
/* 301 */                   return true;
                    }
                }

/* 306 */       boolean onBinaryElement(int id, long elementOffsetBytes, int headerSizeBytes, int contentsSizeBytes, NonBlockingInputStream inputStream) {
/* 307 */           if (id == 163) {
/* 312 */               if (this.sampleHolder == null) {
/* 313 */                   this.readResults |= 32;
/* 314 */                   return false;
                        }
/* 318 */               this.reader.readVarint(inputStream);
/* 321 */               this.reader.readBytes(inputStream, this.simpleBlockTimecodeAndFlags, 3);
/* 324 */               int timecode = (this.simpleBlockTimecodeAndFlags[0] << 8) | (this.simpleBlockTimecodeAndFlags[1] & Constants.UNKNOWN);
/* 326 */               long timecodeUs = scaleTimecodeToUs(timecode);
/* 329 */               boolean keyframe = (this.simpleBlockTimecodeAndFlags[2] & 128) == 128;
/* 330 */               boolean invisible = (this.simpleBlockTimecodeAndFlags[2] & 8) == 8;
/* 331 */               int lacing = (this.simpleBlockTimecodeAndFlags[2] & 6) >> 1;
                        switch (lacing) {
                            case 0:
/* 336 */                       long elementEndOffsetBytes = ((long) headerSizeBytes) + elementOffsetBytes + ((long) contentsSizeBytes);
/* 337 */                       this.simpleBlockTimecodeUs = this.clusterTimecodeUs + timecodeUs;
/* 338 */                       this.sampleHolder.flags = keyframe ? 1 : 0;
/* 339 */                       this.sampleHolder.decodeOnly = invisible;
/* 340 */                       this.sampleHolder.timeUs = this.clusterTimecodeUs + timecodeUs;
/* 341 */                       this.sampleHolder.size = (int) (elementEndOffsetBytes - this.reader.getBytesRead());
/* 350 */                       ByteBuffer outputData = this.sampleHolder.data;
/* 351 */                       if (this.sampleHolder.allowDataBufferReplacement && (this.sampleHolder.data == null || this.sampleHolder.data.capacity() < this.sampleHolder.size)) {
/* 353 */                           outputData = ByteBuffer.allocate(this.sampleHolder.size);
/* 354 */                           this.sampleHolder.data = outputData;
                                }
/* 357 */                       if (outputData == null) {
/* 358 */                           this.reader.skipBytes(inputStream, this.sampleHolder.size);
/* 359 */                           this.sampleHolder.size = 0;
                                } else {
/* 361 */                           this.reader.readBytes(inputStream, outputData, this.sampleHolder.size);
                                }
/* 363 */                       this.readResults |= 4;
                                break;
                            default:
/* 347 */                       throw new IllegalStateException("Lacing mode " + lacing + " not supported");
                        }
                    }
/* 365 */           return true;
                }

/* 368 */       private long scaleTimecodeToUs(long unscaledTimecode) {
/* 369 */           return TimeUnit.NANOSECONDS.toMicros(this.timecodeScale * unscaledTimecode);
                }

/* 379 */       private void buildFormat() {
/* 380 */           if (this.pixelWidth != -1 && this.pixelHeight != -1 && (this.format == null || this.format.width != this.pixelWidth || this.format.height != this.pixelHeight)) {
/* 382 */               this.format = MediaFormat.createVideoFormat(MimeTypes.VIDEO_VP9, -1, this.pixelWidth, this.pixelHeight, null);
/* 384 */               this.readResults |= 8;
                    } else if (this.format == null) {
/* 386 */               throw new IllegalStateException("Unable to build format");
                    }
                }

/* 396 */       private void buildCues() {
                    if (this.segmentStartOffsetBytes == -1) {
/* 398 */               throw new IllegalStateException("Segment start/end offsets unknown");
                    }
                    if (this.durationUs == -1) {
/* 400 */               throw new IllegalStateException("Duration unknown");
                    }
                    if (this.cuesSizeBytes == -1) {
/* 402 */               throw new IllegalStateException("Cues size unknown");
                    }
/* 403 */           if (this.cueTimesUs == null || this.cueClusterPositions == null || this.cueTimesUs.size() == 0 || this.cueTimesUs.size() != this.cueClusterPositions.size()) {
/* 405 */               throw new IllegalStateException("Invalid/missing cue points");
                    }
/* 407 */           int cuePointsSize = this.cueTimesUs.size();
/* 408 */           int[] sizes = new int[cuePointsSize];
/* 409 */           long[] offsets = new long[cuePointsSize];
/* 410 */           long[] durationsUs = new long[cuePointsSize];
/* 411 */           long[] timesUs = new long[cuePointsSize];
                    for (int i = 0; i < cuePointsSize; i++) {
/* 413 */               timesUs[i] = this.cueTimesUs.get(i);
/* 414 */               offsets[i] = this.segmentStartOffsetBytes + this.cueClusterPositions.get(i);
                    }
                    for (int i2 = 0; i2 < cuePointsSize - 1; i2++) {
/* 417 */               sizes[i2] = (int) (offsets[i2 + 1] - offsets[i2]);
/* 418 */               durationsUs[i2] = timesUs[i2 + 1] - timesUs[i2];
                    }
/* 420 */           sizes[cuePointsSize - 1] = (int) (this.segmentEndOffsetBytes - offsets[cuePointsSize - 1]);
/* 421 */           durationsUs[cuePointsSize - 1] = this.durationUs - timesUs[cuePointsSize - 1];
/* 422 */           this.cues = new SegmentIndex((int) this.cuesSizeBytes, sizes, offsets, durationsUs, timesUs);
/* 423 */           this.cueTimesUs = null;
/* 424 */           this.cueClusterPositions = null;
/* 425 */           this.readResults |= 16;
                }

/* 430 */       private final class InnerEbmlEventHandler implements EbmlEventHandler {
/* 431 */           private InnerEbmlEventHandler() {
                    }

/* 431 */           /* synthetic */ InnerEbmlEventHandler(WebmExtractor x0, Object x1) {
/* 432 */               this();
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 435 */           public int getElementType(int id) {
/* 436 */               return WebmExtractor.this.getElementType(id);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 441 */           public void onMasterElementStart(int id, long elementOffsetBytes, int headerSizeBytes, long contentsSizeBytes) {
/* 442 */               WebmExtractor.this.onMasterElementStart(id, elementOffsetBytes, headerSizeBytes, contentsSizeBytes);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 447 */           public void onMasterElementEnd(int id) {
/* 448 */               WebmExtractor.this.onMasterElementEnd(id);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 452 */           public void onIntegerElement(int id, long value) {
/* 453 */               WebmExtractor.this.onIntegerElement(id, value);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 457 */           public void onFloatElement(int id, double value) {
/* 458 */               WebmExtractor.this.onFloatElement(id, value);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 462 */           public void onStringElement(int id, String value) {
/* 463 */               WebmExtractor.this.onStringElement(id, value);
                    }

                    @Override // com.google.android.exoplayer.parser.webm.EbmlEventHandler
/* 469 */           public boolean onBinaryElement(int id, long elementOffsetBytes, int headerSizeBytes, int contentsSizeBytes, NonBlockingInputStream inputStream) {
/* 470 */               return WebmExtractor.this.onBinaryElement(id, elementOffsetBytes, headerSizeBytes, contentsSizeBytes, inputStream);
                    }
                }
            }
