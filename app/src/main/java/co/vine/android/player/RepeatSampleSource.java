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
            
            
            @TargetApi(16)
    public final class RepeatSampleSource implements SampleSource {
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

        public RepeatSampleSource(Context context, Uri uri, int downstreamRendererCount) {
            Assertions.checkState(Util.SDK_INT >= 16);
            this.context = context;
            this.remainingReleaseCount = downstreamRendererCount;
            this.uri = uri;
            String scheme = uri.getScheme();
            if (scheme == null || scheme.equals(CommonUtil.FILE)) {
                this.estimatedFileSize = (int) (new File(uri.getPath()).length() * 1.5d);
                    } else {
                this.estimatedFileSize = DEFAULT_FILE_SIZE;
                    }
                }

        public static class Frame implements Comparable {
                    public final FrameInfo frameInfo = new FrameInfo();
                    public final MediaCodec.BufferInfo info;
                    public final int trackIndex;

            public Frame(int trackIndex, MediaCodec.BufferInfo info) {
                this.trackIndex = trackIndex;
                this.info = info;
                    }

                    @Override 
            public int compareTo(@NonNull Object another) {
                return (int) (this.info.presentationTimeUs - ((Frame) another).info.presentationTimeUs);
                    }
                }

        public static class TrackInfoWithFormat extends TrackInfo {
                    final MediaFormat format;
                    final boolean isVideo;

            public TrackInfoWithFormat(String mimeType, long durationUs, MediaFormat format, boolean isVideo) {
               super(mimeType, durationUs);
               this.format = format;
               this.isVideo = isVideo;
                    }
                }

                @Override 
       public boolean prepare() throws IOException {
           if (!this.prepared) {
               ByteBuffer buffer = ByteBuffer.allocate((int) (((double) this.estimatedFileSize) * 1.5d));
               MediaExtractor extractor = new MediaExtractor();
               extractor.setDataSource(this.context, this.uri, (Map<String, String>) null);
               this.trackStates = new int[extractor.getTrackCount()];
               this.pendingDiscontinuities = new boolean[this.trackStates.length];
               this.trackInfos = new TrackInfoWithFormat[this.trackStates.length];
               int[] frameCounts = new int[this.trackStates.length];
                        for (int i = 0; i < this.trackStates.length; i++) {
                   extractor.selectTrack(i);
                   android.media.MediaFormat format = extractor.getTrackFormat(i);
                   String mime = format.getString("mime");
                   this.trackInfos[i] = new TrackInfoWithFormat(mime, -1L, MediaFormat.createFromFrameworkMediaFormatV16(format), mime.contains(MimeTypes.BASE_TYPE_VIDEO));
                        }
               SLog.m465i("Track count: {}", Integer.valueOf(this.trackStates.length));
               this.lastTimestamp = 0L;
               ArrayList<Frame> frames = new ArrayList<>();
               int offset = 0;
               while (extractor.getSampleTrackIndex() >= 0) {
                   MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                   bufferInfo.offset = offset;
                   bufferInfo.size = extractor.readSampleData(buffer, offset);
                   if (bufferInfo.size < 0) {
                       bufferInfo.size = 0;
                       SLog.m464i("EOS reached? ");
                            } else {
                       offset += bufferInfo.size;
                       if (buffer.capacity() - offset < 524288) {
                           SLog.m477w("Processing large file?");
                           ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                           buffer.position(0);
                           newBuffer.put(buffer);
                           buffer = newBuffer;
                                }
                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
                       bufferInfo.flags = extractor.getSampleFlags();
                       boolean syncSample = (bufferInfo.flags & 1) > 0;
                       int trackIndex = extractor.getSampleTrackIndex();
                       frames.add(new Frame(trackIndex, bufferInfo));
                       frameCounts[trackIndex] = frameCounts[trackIndex] + 1;
                                if (bufferInfo.presentationTimeUs > this.lastTimestamp) {
                           this.lastTimestamp = bufferInfo.presentationTimeUs;
                                }
                       extractor.advance();
                       SLog.m464i(trackIndex + " at " + bufferInfo.presentationTimeUs + " offset " + bufferInfo.offset + " size " + bufferInfo.size + " sync " + syncSample);
                            }
                        }
               extractor.release();
               this.currentFrameIndex = 0;
               this.buffer = buffer.array();
               this.frames = new Frame[frames.size()];
               frames.toArray(this.frames);
               decideFramesToCut(frames);
               SLog.m467i("Total data read: {} bytes, {} {}", Integer.valueOf(offset), Integer.valueOf(frameCounts[0]), Integer.valueOf(frameCounts[1]));
               this.prepared = true;
               return true;
                    }
           return true;
                }

       private void decideFramesToCut(ArrayList<Frame> frames) {
                    for (Frame frame : frames) {
                        if (frame.info.presentationTimeUs >= this.lastTimestamp - 50000 || frame.info.presentationTimeUs <= 50000) {
                   frame.frameInfo.doNotDisplay = true;
                        }
                        if (frame.info.presentationTimeUs >= this.lastTimestamp - 100000) {
                   frame.frameInfo.needsReadMore = true;
                        }
                    }
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
               formatHolder.format = this.trackInfos[track].format;
               formatHolder.drmInitData = null;
               this.trackStates[track] = 2;
               return -4;
                    }
           int extractorTrackIndex = this.frames[this.currentFrameIndex].trackIndex;
           if (extractorTrackIndex == track) {
               if (sampleHolder.data != null) {
                   int offset = sampleHolder.data.position();
                   sampleHolder.data.put(this.buffer, this.frames[this.currentFrameIndex].info.offset, this.frames[this.currentFrameIndex].info.size);
                   sampleHolder.size = this.frames[this.currentFrameIndex].info.size;
                   sampleHolder.data.position(sampleHolder.size + offset);
                        } else {
                   sampleHolder.size = 0;
                        }
               sampleHolder.timeUs = this.frames[this.currentFrameIndex].info.presentationTimeUs + this.currentExtraTime;
               sampleHolder.flags = this.frames[this.currentFrameIndex].info.flags;
               sampleHolder.decodeOnly = this.frames[this.currentFrameIndex].frameInfo.doNotDisplay;
               this.seekTimeUs = -1L;
               advance();
               return -3;
                    }
           return extractorTrackIndex < 0 ? -1 : -2;
                }

                @Override 
       public void disable(int track) {
           Assertions.checkState(this.prepared);
           Assertions.checkState(this.trackStates[track] != 0);
           this.pendingDiscontinuities[track] = false;
           this.trackStates[track] = 0;
                }

                @Override 
       public void seekToUs(long timeUs) {
           Assertions.checkState(this.prepared);
                    if (this.seekTimeUs != timeUs) {
               this.seekTimeUs = timeUs;
               seekToUsInternal(timeUs);
                        for (int i = 0; i < this.trackStates.length; i++) {
                   if (this.trackStates[i] != 0) {
                       this.pendingDiscontinuities[i] = true;
                            }
                        }
                    }
                }

       private void seekToUsInternal(long timeUs) {
                    if (timeUs > this.currentExtraTime) {
                        if (timeUs > this.currentExtraTime + this.lastTimestamp) {
                   this.currentExtraTime += this.lastTimestamp;
                   this.currentFrameIndex = 0;
                   return;
                        }
               return;
                    }
                    for (int i = 0; i < this.frames.length; i++) {
                        if (this.frames[i].info.presentationTimeUs + this.currentExtraTime >= timeUs) {
                   this.currentFrameIndex = i;
                   return;
                        }
                    }
                }

       private void advance() {
           this.currentFrameIndex++;
           if (this.currentFrameIndex >= this.frames.length) {
               this.currentFrameIndex = 0;
               this.currentExtraTime += this.lastTimestamp;
               SLog.m446d("Loop happened.");
                    }
           SLog.m447d("Sample source advanced to {}ms", Long.valueOf((this.frames[this.currentFrameIndex].info.presentationTimeUs + this.currentExtraTime) / 1000));
                }

                @Override 
       public long getBufferedPositionUs() {
           Assertions.checkState(this.prepared);
           return -3L;
                }

                @Override 
       public void release() {
           Assertions.checkState(this.remainingReleaseCount > 0);
           int i = this.remainingReleaseCount - 1;
                    this.remainingReleaseCount = i;
                    if (i == 0) {
               this.buffer = null;
                    }
                }
            }
