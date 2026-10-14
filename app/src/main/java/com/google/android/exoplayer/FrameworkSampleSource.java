            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.media.MediaExtractor;
            import android.net.Uri;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 54 */    public final class FrameworkSampleSource implements SampleSource {
                private static final int TRACK_STATE_DISABLED = 0;
                private static final int TRACK_STATE_ENABLED = 1;
                private static final int TRACK_STATE_FORMAT_SENT = 2;
                private final Context context;
                private MediaExtractor extractor;
                private final Map<String, String> headers;
                private boolean[] pendingDiscontinuities;
                private boolean prepared;
                private int remainingReleaseCount;
                private long seekTimeUs;
                private TrackInfo[] trackInfos;
                private int[] trackStates;
                private final Uri uri;

/* 55 */        public FrameworkSampleSource(Context context, Uri uri, Map<String, String> headers, int downstreamRendererCount) {
/* 57 */            Assertions.checkState(Util.SDK_INT >= 16);
/* 58 */            this.context = context;
/* 59 */            this.uri = uri;
/* 60 */            this.headers = headers;
/* 61 */            this.remainingReleaseCount = downstreamRendererCount;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 65 */        public boolean prepare() throws IOException {
/* 66 */            if (!this.prepared) {
/* 67 */                this.extractor = new MediaExtractor();
/* 68 */                this.extractor.setDataSource(this.context, this.uri, this.headers);
/* 69 */                this.trackStates = new int[this.extractor.getTrackCount()];
/* 70 */                this.pendingDiscontinuities = new boolean[this.trackStates.length];
/* 71 */                this.trackInfos = new TrackInfo[this.trackStates.length];
                        for (int i = 0; i < this.trackStates.length; i++) {
/* 73 */                    android.media.MediaFormat format = this.extractor.getTrackFormat(i);
/* 74 */                    long duration = format.containsKey("durationUs") ? format.getLong("durationUs") : -1L;
/* 76 */                    String mime = format.getString("mime");
/* 77 */                    this.trackInfos[i] = new TrackInfo(mime, duration);
                        }
/* 79 */                this.prepared = true;
                    }
                    return true;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 85 */        public int getTrackCount() {
/* 86 */            Assertions.checkState(this.prepared);
/* 87 */            return this.trackStates.length;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 91 */        public TrackInfo getTrackInfo(int track) {
/* 92 */            Assertions.checkState(this.prepared);
/* 93 */            return this.trackInfos[track];
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 97 */        public void enable(int track, long timeUs) {
/* 98 */            Assertions.checkState(this.prepared);
/* 99 */            Assertions.checkState(this.trackStates[track] == 0);
/* 100 */           this.trackStates[track] = 1;
/* 101 */           this.extractor.selectTrack(track);
/* 102 */           seekToUs(timeUs);
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 110 */       public boolean continueBuffering(long playbackPositionUs) {
/* 111 */           return true;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 116 */       public int readData(int track, long playbackPositionUs, MediaFormatHolder formatHolder, SampleHolder sampleHolder, boolean onlyReadDiscontinuity) {
/* 117 */           Assertions.checkState(this.prepared);
/* 118 */           Assertions.checkState(this.trackStates[track] != 0);
/* 119 */           if (this.pendingDiscontinuities[track]) {
/* 120 */               this.pendingDiscontinuities[track] = false;
/* 121 */               return -5;
                    }
/* 123 */           if (onlyReadDiscontinuity) {
                        return -2;
                    }
/* 126 */           if (this.trackStates[track] != 2) {
/* 127 */               formatHolder.format = MediaFormat.createFromFrameworkMediaFormatV16(this.extractor.getTrackFormat(track));
/* 129 */               formatHolder.drmInitData = Util.SDK_INT >= 18 ? getPsshInfoV18() : null;
/* 130 */               this.trackStates[track] = 2;
/* 131 */               return -4;
                    }
/* 133 */           int extractorTrackIndex = this.extractor.getSampleTrackIndex();
/* 134 */           if (extractorTrackIndex == track) {
/* 135 */               if (sampleHolder.data != null) {
/* 136 */                   int offset = sampleHolder.data.position();
/* 137 */                   sampleHolder.size = this.extractor.readSampleData(sampleHolder.data, offset);
/* 138 */                   sampleHolder.data.position(sampleHolder.size + offset);
                        } else {
/* 140 */                   sampleHolder.size = 0;
                        }
/* 142 */               sampleHolder.timeUs = this.extractor.getSampleTime();
/* 143 */               sampleHolder.flags = this.extractor.getSampleFlags();
/* 144 */               if ((sampleHolder.flags & 2) != 0) {
/* 145 */                   sampleHolder.cryptoInfo.setFromExtractorV16(this.extractor);
                        }
/* 147 */               this.seekTimeUs = -1L;
/* 148 */               this.extractor.advance();
/* 149 */               return -3;
                    }
/* 151 */           return extractorTrackIndex < 0 ? -1 : -2;
                }

                @TargetApi(18)
/* 156 */       private Map<UUID, byte[]> getPsshInfoV18() {
/* 157 */           Map<UUID, byte[]> psshInfo = this.extractor.getPsshInfo();
/* 158 */           if (psshInfo == null || psshInfo.isEmpty()) {
                        return null;
                    }
                    return psshInfo;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 162 */       public void disable(int track) {
/* 163 */           Assertions.checkState(this.prepared);
/* 164 */           Assertions.checkState(this.trackStates[track] != 0);
/* 165 */           this.extractor.unselectTrack(track);
/* 166 */           this.pendingDiscontinuities[track] = false;
/* 167 */           this.trackStates[track] = 0;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 171 */       public void seekToUs(long timeUs) {
/* 172 */           Assertions.checkState(this.prepared);
                    if (this.seekTimeUs != timeUs) {
/* 176 */               this.seekTimeUs = timeUs;
/* 177 */               this.extractor.seekTo(timeUs, 0);
                        for (int i = 0; i < this.trackStates.length; i++) {
/* 179 */                   if (this.trackStates[i] != 0) {
/* 180 */                       this.pendingDiscontinuities[i] = true;
                            }
                        }
                    }
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 187 */       public long getBufferedPositionUs() {
/* 188 */           Assertions.checkState(this.prepared);
/* 189 */           long bufferedDurationUs = this.extractor.getCachedDuration();
                    if (bufferedDurationUs == -1) {
                        return -1L;
                    }
                    return this.extractor.getSampleTime() + bufferedDurationUs;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 198 */       public void release() {
/* 199 */           Assertions.checkState(this.remainingReleaseCount > 0);
/* 200 */           int i = this.remainingReleaseCount - 1;
                    this.remainingReleaseCount = i;
                    if (i == 0) {
/* 201 */               this.extractor.release();
/* 202 */               this.extractor = null;
                    }
                }
            }
