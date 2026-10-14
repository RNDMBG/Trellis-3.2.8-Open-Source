            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.media.MediaCodec;
            import android.media.MediaExtractor;
            import android.net.Uri;
            import android.support.annotation.NonNull;
            import co.vine.android.util.CommonUtil;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.MediaFormatHolder;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.SampleSource;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.Util;
            import java.io.File;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 57 */    public final class RepeatSampleSource implements SampleSource {
                private static final int DEFAULT_FILE_SIZE = 5242880;
                private static final int DOMS_CUT_US = 50000;
                private static final int MINIUM_FRAME_SIZE = 524288;
                private static final int TRACK_STATE_DISABLED = 0;
                private static final int TRACK_STATE_ENABLED = 1;
                private static final int TRACK_STATE_FORMAT_SENT = 2;
                private byte[] buffer;
                private final Context context;
                private long currentExtraTime;
                private int currentFrameIndex;
                private final int estimatedFileSize;
                private Frame[] frames;
                private long lastTimestamp;
                private boolean[] pendingDiscontinuities;
                private boolean prepared;
                private int remainingReleaseCount;
                private long seekTimeUs;
                private TrackInfoWithFormat[] trackInfos;
                private int[] trackStates;
                private final Uri uri;

                public static class FrameInfo {
                    public boolean doNotDisplay;
                    public boolean needsReadMore;
                }

/* 58 */        public RepeatSampleSource(Context context, Uri uri, int downstreamRendererCount) {
/* 60 */            Assertions.checkState(Util.SDK_INT >= 16);
/* 61 */            this.context = context;
/* 62 */            this.remainingReleaseCount = downstreamRendererCount;
/* 63 */            this.uri = uri;
/* 65 */            String scheme = uri.getScheme();
/* 66 */            if (scheme == null || scheme.equals(CommonUtil.FILE)) {
/* 67 */                this.estimatedFileSize = (int) (new File(uri.getPath()).length() * 1.5d);
                    } else {
/* 69 */                this.estimatedFileSize = DEFAULT_FILE_SIZE;
                    }
                }

/* 81 */        public static class Frame implements Comparable {
                    public final FrameInfo frameInfo = new FrameInfo();
                    public final MediaCodec.BufferInfo info;
                    public final int trackIndex;

/* 82 */            public Frame(int trackIndex, MediaCodec.BufferInfo info) {
/* 84 */                this.trackIndex = trackIndex;
/* 85 */                this.info = info;
                    }

                    @Override // java.lang.Comparable
/* 89 */            public int compareTo(@NonNull Object another) {
/* 90 */                return (int) (this.info.presentationTimeUs - ((Frame) another).info.presentationTimeUs);
                    }
                }

/* 98 */        public static class TrackInfoWithFormat extends TrackInfo {
                    final MediaFormat format;
                    final boolean isVideo;

/* 99 */            public TrackInfoWithFormat(String mimeType, long durationUs, MediaFormat format, boolean isVideo) {
/* 100 */               super(mimeType, durationUs);
/* 101 */               this.format = format;
/* 102 */               this.isVideo = isVideo;
                    }
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 107 */       public boolean prepare() throws IOException {
/* 108 */           if (!this.prepared) {
/* 109 */               ByteBuffer buffer = ByteBuffer.allocate((int) (((double) this.estimatedFileSize) * 1.5d));
/* 110 */               MediaExtractor extractor = new MediaExtractor();
/* 111 */               extractor.setDataSource(this.context, this.uri, (Map<String, String>) null);
/* 112 */               this.trackStates = new int[extractor.getTrackCount()];
/* 113 */               this.pendingDiscontinuities = new boolean[this.trackStates.length];
/* 114 */               this.trackInfos = new TrackInfoWithFormat[this.trackStates.length];
/* 115 */               int[] frameCounts = new int[this.trackStates.length];
                        for (int i = 0; i < this.trackStates.length; i++) {
/* 117 */                   extractor.selectTrack(i);
/* 118 */                   android.media.MediaFormat format = extractor.getTrackFormat(i);
/* 120 */                   String mime = format.getString("mime");
/* 119 */                   this.trackInfos[i] = new TrackInfoWithFormat(mime, -1L, MediaFormat.createFromFrameworkMediaFormatV16(format), mime.contains(MimeTypes.BASE_TYPE_VIDEO));
                        }
/* 126 */               SLog.m465i("Track count: {}", Integer.valueOf(this.trackStates.length));
/* 127 */               this.lastTimestamp = 0L;
/* 128 */               ArrayList<Frame> frames = new ArrayList<>();
/* 130 */               int offset = 0;
/* 131 */               while (extractor.getSampleTrackIndex() >= 0) {
/* 132 */                   MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
/* 133 */                   bufferInfo.offset = offset;
/* 134 */                   bufferInfo.size = extractor.readSampleData(buffer, offset);
/* 135 */                   if (bufferInfo.size < 0) {
/* 136 */                       bufferInfo.size = 0;
/* 137 */                       SLog.m464i("EOS reached? ");
                            } else {
/* 139 */                       offset += bufferInfo.size;
/* 140 */                       if (buffer.capacity() - offset < 524288) {
/* 141 */                           SLog.m477w("Processing large file?");
/* 142 */                           ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
/* 143 */                           buffer.position(0);
/* 144 */                           newBuffer.put(buffer);
/* 145 */                           buffer = newBuffer;
                                }
/* 147 */                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
/* 148 */                       bufferInfo.flags = extractor.getSampleFlags();
/* 149 */                       boolean syncSample = (bufferInfo.flags & 1) > 0;
/* 150 */                       int trackIndex = extractor.getSampleTrackIndex();
/* 151 */                       frames.add(new Frame(trackIndex, bufferInfo));
/* 152 */                       frameCounts[trackIndex] = frameCounts[trackIndex] + 1;
                                if (bufferInfo.presentationTimeUs > this.lastTimestamp) {
/* 154 */                           this.lastTimestamp = bufferInfo.presentationTimeUs;
                                }
/* 156 */                       extractor.advance();
/* 157 */                       SLog.m464i(trackIndex + " at " + bufferInfo.presentationTimeUs + " offset " + bufferInfo.offset + " size " + bufferInfo.size + " sync " + syncSample);
                            }
                        }
/* 163 */               extractor.release();
/* 164 */               this.currentFrameIndex = 0;
/* 165 */               this.buffer = buffer.array();
/* 166 */               this.frames = new Frame[frames.size()];
/* 167 */               frames.toArray(this.frames);
/* 168 */               decideFramesToCut(frames);
/* 169 */               SLog.m467i("Total data read: {} bytes, {} {}", Integer.valueOf(offset), Integer.valueOf(frameCounts[0]), Integer.valueOf(frameCounts[1]));
/* 170 */               this.prepared = true;
/* 172 */               return true;
                    }
/* 172 */           return true;
                }

/* 176 */       private void decideFramesToCut(ArrayList<Frame> frames) {
                    for (Frame frame : frames) {
                        if (frame.info.presentationTimeUs >= this.lastTimestamp - 50000 || frame.info.presentationTimeUs <= 50000) {
/* 180 */                   frame.frameInfo.doNotDisplay = true;
                        }
                        if (frame.info.presentationTimeUs >= this.lastTimestamp - 100000) {
/* 183 */                   frame.frameInfo.needsReadMore = true;
                        }
                    }
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 189 */       public int getTrackCount() {
/* 190 */           Assertions.checkState(this.prepared);
/* 191 */           return this.trackStates.length;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 195 */       public TrackInfo getTrackInfo(int track) {
/* 196 */           Assertions.checkState(this.prepared);
/* 197 */           return this.trackInfos[track];
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 201 */       public void enable(int track, long timeUs) {
/* 202 */           Assertions.checkState(this.prepared);
/* 203 */           Assertions.checkState(this.trackStates[track] == 0);
/* 204 */           this.trackStates[track] = 1;
/* 205 */           seekToUs(timeUs);
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 209 */       public boolean continueBuffering(long playbackPositionUs) {
/* 210 */           return true;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 215 */       public int readData(int track, long playbackPositionUs, MediaFormatHolder formatHolder, SampleHolder sampleHolder, boolean onlyReadDiscontinuity) {
/* 216 */           Assertions.checkState(this.prepared);
/* 217 */           Assertions.checkState(this.trackStates[track] != 0);
/* 218 */           if (this.pendingDiscontinuities[track]) {
/* 219 */               this.pendingDiscontinuities[track] = false;
/* 220 */               return -5;
                    }
/* 222 */           if (onlyReadDiscontinuity) {
                        return -2;
                    }
/* 225 */           if (this.trackStates[track] != 2) {
/* 226 */               formatHolder.format = this.trackInfos[track].format;
/* 227 */               formatHolder.drmInitData = null;
/* 228 */               this.trackStates[track] = 2;
/* 229 */               return -4;
                    }
/* 231 */           int extractorTrackIndex = this.frames[this.currentFrameIndex].trackIndex;
/* 232 */           if (extractorTrackIndex == track) {
/* 233 */               if (sampleHolder.data != null) {
/* 234 */                   int offset = sampleHolder.data.position();
/* 235 */                   sampleHolder.data.put(this.buffer, this.frames[this.currentFrameIndex].info.offset, this.frames[this.currentFrameIndex].info.size);
/* 236 */                   sampleHolder.size = this.frames[this.currentFrameIndex].info.size;
/* 237 */                   sampleHolder.data.position(sampleHolder.size + offset);
                        } else {
/* 239 */                   sampleHolder.size = 0;
                        }
/* 241 */               sampleHolder.timeUs = this.frames[this.currentFrameIndex].info.presentationTimeUs + this.currentExtraTime;
/* 242 */               sampleHolder.flags = this.frames[this.currentFrameIndex].info.flags;
/* 243 */               sampleHolder.decodeOnly = this.frames[this.currentFrameIndex].frameInfo.doNotDisplay;
/* 244 */               this.seekTimeUs = -1L;
/* 245 */               advance();
/* 246 */               return -3;
                    }
/* 248 */           return extractorTrackIndex < 0 ? -1 : -2;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 253 */       public void disable(int track) {
/* 254 */           Assertions.checkState(this.prepared);
/* 255 */           Assertions.checkState(this.trackStates[track] != 0);
/* 256 */           this.pendingDiscontinuities[track] = false;
/* 257 */           this.trackStates[track] = 0;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 261 */       public void seekToUs(long timeUs) {
/* 262 */           Assertions.checkState(this.prepared);
                    if (this.seekTimeUs != timeUs) {
/* 266 */               this.seekTimeUs = timeUs;
/* 267 */               seekToUsInternal(timeUs);
                        for (int i = 0; i < this.trackStates.length; i++) {
/* 269 */                   if (this.trackStates[i] != 0) {
/* 270 */                       this.pendingDiscontinuities[i] = true;
                            }
                        }
                    }
                }

/* 276 */       private void seekToUsInternal(long timeUs) {
                    if (timeUs > this.currentExtraTime) {
                        if (timeUs > this.currentExtraTime + this.lastTimestamp) {
/* 280 */                   this.currentExtraTime += this.lastTimestamp;
/* 281 */                   this.currentFrameIndex = 0;
/* 291 */                   return;
                        }
/* 291 */               return;
                    }
                    for (int i = 0; i < this.frames.length; i++) {
                        if (this.frames[i].info.presentationTimeUs + this.currentExtraTime >= timeUs) {
/* 286 */                   this.currentFrameIndex = i;
/* 291 */                   return;
                        }
                    }
                }

/* 293 */       private void advance() {
/* 294 */           this.currentFrameIndex++;
/* 295 */           if (this.currentFrameIndex >= this.frames.length) {
/* 296 */               this.currentFrameIndex = 0;
/* 297 */               this.currentExtraTime += this.lastTimestamp;
/* 298 */               SLog.m446d("Loop happened.");
                    }
/* 300 */           SLog.m447d("Sample source advanced to {}ms", Long.valueOf((this.frames[this.currentFrameIndex].info.presentationTimeUs + this.currentExtraTime) / 1000));
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 304 */       public long getBufferedPositionUs() {
/* 305 */           Assertions.checkState(this.prepared);
/* 306 */           return -3L;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 310 */       public void release() {
/* 311 */           Assertions.checkState(this.remainingReleaseCount > 0);
/* 312 */           int i = this.remainingReleaseCount - 1;
                    this.remainingReleaseCount = i;
                    if (i == 0) {
/* 313 */               this.buffer = null;
                    }
                }
            }
