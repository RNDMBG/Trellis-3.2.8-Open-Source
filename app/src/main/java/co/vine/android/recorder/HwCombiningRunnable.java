            package co.vine.android.recorder;

            import android.os.Process;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avutil;
            import java.io.File;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class HwCombiningRunnable extends AbstractCombiningRunnable {
                private boolean hasLoggedException;
                private final BaseFinishProcessTask mAsyncTask;
                private final byte[] mAudioArray;
                private final boolean mIsPreview;
                private String mLastFrameOnlyModePath;
                private ArrayList<RecordSegment> mSegments;
                private boolean mSingleSegment;
                private String mThumbnailPath;
                private long mTimeOffset;
                private final byte[] mVideoArray;
                private String mVideoPath;
                private final HwVineFrameRecorder mVideoRecorder;

/* 41 */        public HwCombiningRunnable(RecordingFile file, boolean isPreview, AudioArray audioArray, byte[] videoArray, ArrayList<RecordSegment> segments, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
/* 42 */            this(isPreview, (byte[]) audioArray.getData(), videoArray, videoRecorder, task);
/* 43 */            this.mSegments = segments;
/* 44 */            this.mVideoPath = isPreview ? file.getPreviewVideoPath() : file.getVideoPath();
/* 45 */            this.mThumbnailPath = isPreview ? file.getPreviewThumbnailPath() : file.getThumbnailPath();
                }

/* 50 */        public HwCombiningRunnable(RecordingFile file, RecordSegment segment, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, boolean onlyGrabLastSegmentMode) {
/* 51 */            this(true, (byte[]) file.getSession().getAudioData().getData(), file.getSession().getVideoData(), videoRecorder, task);
/* 52 */            this.mSegments = new ArrayList<>();
/* 53 */            this.mSegments.add(segment);
/* 54 */            this.mSingleSegment = true;
/* 55 */            this.mVideoPath = file.createSegmentVideoPath();
/* 56 */            segment.videoPath = this.mVideoPath;
/* 57 */            this.mThumbnailPath = file.getSegmentThumbnailPath();
/* 58 */            if (onlyGrabLastSegmentMode) {
/* 59 */                this.mLastFrameOnlyModePath = file.getPreviewThumbnailPath();
                    }
                }

/* 64 */        private HwCombiningRunnable(boolean isPreview, byte[] audioArray, byte[] videoArray, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
/* 36 */            this.mLastFrameOnlyModePath = null;
/* 66 */            this.mAudioArray = audioArray;
/* 67 */            this.mVideoArray = videoArray;
/* 68 */            this.mIsPreview = isPreview;
/* 69 */            this.mVideoRecorder = videoRecorder;
/* 70 */            this.mTimeOffset = 0L;
/* 71 */            this.mAsyncTask = task;
                }

                /* JADX WARN: Code duplicated, block: B:72:0x0268 A[PHI: r14 r40
  0x0268: PHI (r14v1 'audioTotal' long) = (r14v0 'audioTotal' long), (r14v3 'audioTotal' long), (r14v3 'audioTotal' long), (r14v3 'audioTotal' long) binds: [B:3:0x0021, B:71:0x0263, B:48:0x01e0, B:50:0x01ea] A[DONT_GENERATE, DONT_INLINE]
  0x0268: PHI (r40v1 'videoTotal' long) = (r40v0 'videoTotal' long), (r40v3 'videoTotal' long), (r40v3 'videoTotal' long), (r40v3 'videoTotal' long) binds: [B:3:0x0021, B:71:0x0263, B:48:0x01e0, B:50:0x01ea] A[DONT_GENERATE, DONT_INLINE]] */
                @Override // co.vine.android.recorder.AbstractCombiningRunnable
/* 74 */        public MediaUtil.GenerateThumbnailsRunnable combineVideos() {
                    MediaUtil.GenerateThumbnailsRunnable runnableToReturn = null;
                    String lastFramePath;
/* 75 */            Process.setThreadPriority(-8);
/* 76 */            ArrayList<RecordSegment> segments = this.mSegments;
/* 77 */            int size = segments.size();
/* 78 */            long videoTotal = 0;
/* 79 */            long audioTotal = 0;
/* 80 */            SLog.m447d("Processing started: {}.", Integer.valueOf(size));
/* 81 */            long videoDuration = 0;
/* 82 */            long audioDuration = 0;
/* 85 */            if (size <= 0) {
/* 84 */                runnableToReturn = null;
                    } else {
/* 86 */                byte[] audioArray = this.mAudioArray;
                        for (int i = 0; i < size; i++) {
/* 90 */                    RecordSegment segment = segments.get(i);
/* 91 */                    long audioTime = System.currentTimeMillis();
/* 92 */                    AudioData combined = segment.getCombinedAudioData();
                            try {
/* 94 */                        if (combined.size > 0) {
/* 95 */                            SLog.m448d("Recording segment audio: {}, {}.", Integer.valueOf(combined.start), Integer.valueOf(combined.size));
/* 96 */                            this.mVideoRecorder.recordAudio(audioArray, combined.start, combined.size);
                                }
                            } catch (HwVineFrameRecorder.Exception e) {
/* 99 */                        if (!this.hasLoggedException || SLog.sLogsOn) {
/* 100 */                           this.hasLoggedException = true;
/* 101 */                           CrashUtil.logException(e);
                                }
                            }
/* 104 */                   audioTotal += System.currentTimeMillis() - audioTime;
/* 105 */                   ArrayList<VideoData> videoData = segment.getVideoData();
/* 106 */                   byte[] videoDataArray = this.mVideoArray;
/* 107 */                   long videoTime = System.currentTimeMillis();
/* 109 */                   int frameRate = segment.getFrameRate();
/* 110 */                   int videoDataSize = videoData.size();
/* 112 */                   long offset = 0;
                            for (int k = 0; k < videoDataSize; k++) {
/* 116 */                       VideoData videoPiece = videoData.get(k);
/* 118 */                       if (this.mSingleSegment) {
/* 119 */                           if (k == 0) {
/* 120 */                               offset = videoPiece.timestamp;
                                    }
/* 122 */                           videoPiece.timestamp -= offset;
                                }
/* 125 */                       if (videoPiece.size >= 0) {
                                    if (videoPiece.timestamp == 0) {
/* 130 */                               this.mTimeOffset = this.mVideoRecorder.getTimestamp();
                                    }
/* 134 */                           long determinedTimestamp = videoPiece.timestamp + this.mTimeOffset;
/* 136 */                           int nextFrameNumber = this.mVideoRecorder.getFrameNumber();
/* 137 */                           long nextTimestamp = this.mVideoRecorder.getTimestamp();
                                    if (determinedTimestamp - nextTimestamp > avutil.AV_TIME_BASE / frameRate) {
/* 143 */                               SLog.m446d("*******Skipped frame*******");
/* 144 */                               this.mVideoRecorder.setTimestamp(determinedTimestamp);
/* 145 */                               nextFrameNumber = this.mVideoRecorder.getFrameNumber();
                                    } else {
/* 147 */                               determinedTimestamp = nextTimestamp;
                                    }
                                    try {
/* 151 */                               this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size);
/* 152 */                               SLog.m448d("Successfully did video frame at {}, #{}.", Long.valueOf(determinedTimestamp), Integer.valueOf(nextFrameNumber));
                                    } catch (HwVineFrameRecorder.Exception e2) {
/* 154 */                               SLog.m457e("Failed to record video frame at {}. #{}.", Long.valueOf(determinedTimestamp), Integer.valueOf(nextFrameNumber));
                                    }
                                }
                            }
/* 157 */                   videoTotal += System.currentTimeMillis() - videoTime;
/* 158 */                   if (this.mAsyncTask != null) {
/* 159 */                       this.mAsyncTask.publish(((i * 10) / size) + 90);
                            }
/* 161 */                   if (SLog.sLogsOn) {
/* 162 */                       audioDuration += (long) RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
/* 163 */                       videoDuration = (long) (videoDuration + (((double) videoDataSize) * (1000.0d / ((double) frameRate))));
/* 164 */                       SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size)), Long.valueOf(videoDuration));
                            }
                        }
/* 167 */               SLog.m448d("Done with all segments. total time: audio: {}, video: {}.", Long.valueOf(audioDuration), Long.valueOf(videoDuration));
                        try {
/* 169 */                   this.mVideoRecorder.stopCombining();
/* 171 */                   SLog.m448d("Video file renamed: {}, {}.", Boolean.valueOf(new File(this.mVideoRecorder.getOutput()).renameTo(new File(this.mVideoPath))), this.mVideoPath);
/* 172 */                   if (this.mThumbnailPath == null || !this.mVideoRecorder.hasData()) {
/* 84 */                        runnableToReturn = null;
                            } else {
/* 174 */                       if (!this.mSingleSegment || this.mLastFrameOnlyModePath != null) {
/* 175 */                           lastFramePath = RecordingFile.getLastFramePathThumbnailFromThumbnailPath(this.mLastFrameOnlyModePath == null ? this.mThumbnailPath : this.mLastFrameOnlyModePath, this.mIsPreview);
                                } else {
/* 179 */                           lastFramePath = null;
                                }
/* 181 */                       runnableToReturn = new MediaUtil.GenerateThumbnailsRunnable(this.mVideoRecorder.getLastTimestamp(), this.mVideoPath, this.mThumbnailPath, lastFramePath);
                            }
                        } catch (HwVineFrameRecorder.Exception e3) {
/* 184 */                   CrashUtil.log("Error flushing...", e3);
/* 84 */                    runnableToReturn = null;
                        } catch (Exception e4) {
/* 186 */                   CrashUtil.log("Failed to generate thumbnail.");
                        }
                    }
/* 189 */           if (this.mAsyncTask != null) {
/* 190 */               this.mAsyncTask.publish(99);
                    }
/* 192 */           SLog.m444b("Time used: video: {}, audio: {}, n: {}.", new Object[]{Long.valueOf(videoTotal), Long.valueOf(audioTotal), Integer.valueOf(size)});
/* 193 */           return runnableToReturn;
                }
            }
