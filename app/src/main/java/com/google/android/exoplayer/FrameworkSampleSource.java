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
            
            
            @TargetApi(16)
    public final class FrameworkSampleSource implements SampleSource {
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

        public FrameworkSampleSource(Context context, Uri uri, Map<String, String> headers, int downstreamRendererCount) {
            Assertions.checkState(Util.SDK_INT >= 16);
            this.context = context;
            this.uri = uri;
            this.headers = headers;
            this.remainingReleaseCount = downstreamRendererCount;
                }

                @Override 
        public boolean prepare() throws IOException {
            if (!this.prepared) {
                this.extractor = new MediaExtractor();
                this.extractor.setDataSource(this.context, this.uri, this.headers);
                this.trackStates = new int[this.extractor.getTrackCount()];
                this.pendingDiscontinuities = new boolean[this.trackStates.length];
                this.trackInfos = new TrackInfo[this.trackStates.length];
                        for (int i = 0; i < this.trackStates.length; i++) {
                    android.media.MediaFormat format = this.extractor.getTrackFormat(i);
                    long duration = format.containsKey("durationUs") ? format.getLong("durationUs") : -1L;
                    String mime = format.getString("mime");
                    this.trackInfos[i] = new TrackInfo(mime, duration);
                        }
                this.prepared = true;
                    }
                    return true;
                }

                @Override 
        public int getTrackCount() {
            Assertions.checkState(this.prepared);
            return this.trackStates.length;
                }

                @Override 
        public TrackInfo getTrackInfo(int track) {
            Assertions.checkState(this.prepared);
            return this.trackInfos[track];
                }

                @Override 
        public void enable(int track, long timeUs) {
            Assertions.checkState(this.prepared);
            Assertions.checkState(this.trackStates[track] == 0);
           this.trackStates[track] = 1;
           this.extractor.selectTrack(track);
           seekToUs(timeUs);
                }

                @Override 
       public boolean continueBuffering(long playbackPositionUs) {
           return true;
                }

                @Override 
       public int readData(int track, long playbackPositionUs, MediaFormatHolder formatHolder, SampleHolder sampleHolder, boolean onlyReadDiscontinuity) {
           Assertions.checkState(this.prepared);
           Assertions.checkState(this.trackStates[track] != 0);
           if (this.pendingDiscontinuities[track]) {
               this.pendingDiscontinuities[track] = false;
               return -5;
                    }
           if (onlyReadDiscontinuity) {
                        return -2;
                    }
           if (this.trackStates[track] != 2) {
               formatHolder.format = MediaFormat.createFromFrameworkMediaFormatV16(this.extractor.getTrackFormat(track));
               formatHolder.drmInitData = Util.SDK_INT >= 18 ? getPsshInfoV18() : null;
               this.trackStates[track] = 2;
               return -4;
                    }
           int extractorTrackIndex = this.extractor.getSampleTrackIndex();
           if (extractorTrackIndex == track) {
               if (sampleHolder.data != null) {
                   int offset = sampleHolder.data.position();
                   sampleHolder.size = this.extractor.readSampleData(sampleHolder.data, offset);
                   sampleHolder.data.position(sampleHolder.size + offset);
                        } else {
                   sampleHolder.size = 0;
                        }
               sampleHolder.timeUs = this.extractor.getSampleTime();
               sampleHolder.flags = this.extractor.getSampleFlags();
               if ((sampleHolder.flags & 2) != 0) {
                   sampleHolder.cryptoInfo.setFromExtractorV16(this.extractor);
                        }
               this.seekTimeUs = -1L;
               this.extractor.advance();
               return -3;
                    }
           return extractorTrackIndex < 0 ? -1 : -2;
                }

                @TargetApi(18)
       private Map<UUID, byte[]> getPsshInfoV18() {
           Map<UUID, byte[]> psshInfo = this.extractor.getPsshInfo();
           if (psshInfo == null || psshInfo.isEmpty()) {
                        return null;
                    }
                    return psshInfo;
                }

                @Override 
       public void disable(int track) {
           Assertions.checkState(this.prepared);
           Assertions.checkState(this.trackStates[track] != 0);
           this.extractor.unselectTrack(track);
           this.pendingDiscontinuities[track] = false;
           this.trackStates[track] = 0;
                }

                @Override 
       public void seekToUs(long timeUs) {
           Assertions.checkState(this.prepared);
                    if (this.seekTimeUs != timeUs) {
               this.seekTimeUs = timeUs;
               this.extractor.seekTo(timeUs, 0);
                        for (int i = 0; i < this.trackStates.length; i++) {
                   if (this.trackStates[i] != 0) {
                       this.pendingDiscontinuities[i] = true;
                            }
                        }
                    }
                }

                @Override 
       public long getBufferedPositionUs() {
           Assertions.checkState(this.prepared);
           long bufferedDurationUs = this.extractor.getCachedDuration();
                    if (bufferedDurationUs == -1) {
                        return -1L;
                    }
                    return this.extractor.getSampleTime() + bufferedDurationUs;
                }

                @Override 
       public void release() {
           Assertions.checkState(this.remainingReleaseCount > 0);
           int i = this.remainingReleaseCount - 1;
                    this.remainingReleaseCount = i;
                    if (i == 0) {
               this.extractor.release();
               this.extractor = null;
                    }
                }
            }
