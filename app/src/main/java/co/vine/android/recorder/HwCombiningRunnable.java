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
            
            
    public class HwCombiningRunnable extends AbstractCombiningRunnable {
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

        public HwCombiningRunnable(RecordingFile file, boolean isPreview, AudioArray audioArray, byte[] videoArray, ArrayList<RecordSegment> segments, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
            this(isPreview, (byte[]) audioArray.getData(), videoArray, videoRecorder, task);
            this.mSegments = segments;
            this.mVideoPath = isPreview ? file.getPreviewVideoPath() : file.getVideoPath();
            this.mThumbnailPath = isPreview ? file.getPreviewThumbnailPath() : file.getThumbnailPath();
                }

        public HwCombiningRunnable(RecordingFile file, RecordSegment segment, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, boolean onlyGrabLastSegmentMode) {
            this(true, (byte[]) file.getSession().getAudioData().getData(), file.getSession().getVideoData(), videoRecorder, task);
            this.mSegments = new ArrayList<>();
            this.mSegments.add(segment);
            this.mSingleSegment = true;
            this.mVideoPath = file.createSegmentVideoPath();
            segment.videoPath = this.mVideoPath;
            this.mThumbnailPath = file.getSegmentThumbnailPath();
            if (onlyGrabLastSegmentMode) {
                this.mLastFrameOnlyModePath = file.getPreviewThumbnailPath();
                    }
                }

        private HwCombiningRunnable(boolean isPreview, byte[] audioArray, byte[] videoArray, HwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
            this.mLastFrameOnlyModePath = null;
            this.mAudioArray = audioArray;
            this.mVideoArray = videoArray;
            this.mIsPreview = isPreview;
            this.mVideoRecorder = videoRecorder;
            this.mTimeOffset = 0L;
            this.mAsyncTask = task;
                }

                


                @Override 
        public MediaUtil.GenerateThumbnailsRunnable combineVideos() {
                    MediaUtil.GenerateThumbnailsRunnable runnableToReturn = null;
                    String lastFramePath;
            Process.setThreadPriority(-8);
            ArrayList<RecordSegment> segments = this.mSegments;
            int size = segments.size();
            long videoTotal = 0;
            long audioTotal = 0;
            SLog.m447d("Processing started: {}.", Integer.valueOf(size));
            long videoDuration = 0;
            long audioDuration = 0;
            if (size <= 0) {
                runnableToReturn = null;
                    } else {
                byte[] audioArray = this.mAudioArray;
                        for (int i = 0; i < size; i++) {
                    RecordSegment segment = segments.get(i);
                    long audioTime = System.currentTimeMillis();
                    AudioData combined = segment.getCombinedAudioData();
                            try {
                        if (combined.size > 0) {
                            SLog.m448d("Recording segment audio: {}, {}.", Integer.valueOf(combined.start), Integer.valueOf(combined.size));
                            this.mVideoRecorder.recordAudio(audioArray, combined.start, combined.size);
                                }
                            } catch (HwVineFrameRecorder.Exception e) {
                        if (!this.hasLoggedException || SLog.sLogsOn) {
                           this.hasLoggedException = true;
                           CrashUtil.logException(e);
                                }
                            }
                   audioTotal += System.currentTimeMillis() - audioTime;
                   ArrayList<VideoData> videoData = segment.getVideoData();
                   byte[] videoDataArray = this.mVideoArray;
                   long videoTime = System.currentTimeMillis();
                   int frameRate = segment.getFrameRate();
                   int videoDataSize = videoData.size();
                   long offset = 0;
                            for (int k = 0; k < videoDataSize; k++) {
                       VideoData videoPiece = videoData.get(k);
                       if (this.mSingleSegment) {
                           if (k == 0) {
                               offset = videoPiece.timestamp;
                                    }
                           videoPiece.timestamp -= offset;
                                }
                       if (videoPiece.size >= 0) {
                                    if (videoPiece.timestamp == 0) {
                               this.mTimeOffset = this.mVideoRecorder.getTimestamp();
                                    }
                           long determinedTimestamp = videoPiece.timestamp + this.mTimeOffset;
                           int nextFrameNumber = this.mVideoRecorder.getFrameNumber();
                           long nextTimestamp = this.mVideoRecorder.getTimestamp();
                                    if (determinedTimestamp - nextTimestamp > avutil.AV_TIME_BASE / frameRate) {
                               SLog.m446d("*******Skipped frame*******");
                               this.mVideoRecorder.setTimestamp(determinedTimestamp);
                               nextFrameNumber = this.mVideoRecorder.getFrameNumber();
                                    } else {
                               determinedTimestamp = nextTimestamp;
                                    }
                                    try {
                               this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size);
                               SLog.m448d("Successfully did video frame at {}, #{}.", Long.valueOf(determinedTimestamp), Integer.valueOf(nextFrameNumber));
                                    } catch (HwVineFrameRecorder.Exception e2) {
                               SLog.m457e("Failed to record video frame at {}. #{}.", Long.valueOf(determinedTimestamp), Integer.valueOf(nextFrameNumber));
                                    }
                                }
                            }
                   videoTotal += System.currentTimeMillis() - videoTime;
                   if (this.mAsyncTask != null) {
                       this.mAsyncTask.publish(((i * 10) / size) + 90);
                            }
                   if (SLog.sLogsOn) {
                       audioDuration += (long) RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
                       videoDuration = (long) (videoDuration + (((double) videoDataSize) * (1000.0d / ((double) frameRate))));
                       SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size)), Long.valueOf(videoDuration));
                            }
                        }
               SLog.m448d("Done with all segments. total time: audio: {}, video: {}.", Long.valueOf(audioDuration), Long.valueOf(videoDuration));
                        try {
                   this.mVideoRecorder.stopCombining();
                   SLog.m448d("Video file renamed: {}, {}.", Boolean.valueOf(new File(this.mVideoRecorder.getOutput()).renameTo(new File(this.mVideoPath))), this.mVideoPath);
                   if (this.mThumbnailPath == null || !this.mVideoRecorder.hasData()) {
                        runnableToReturn = null;
                            } else {
                       if (!this.mSingleSegment || this.mLastFrameOnlyModePath != null) {
                           lastFramePath = RecordingFile.getLastFramePathThumbnailFromThumbnailPath(this.mLastFrameOnlyModePath == null ? this.mThumbnailPath : this.mLastFrameOnlyModePath, this.mIsPreview);
                                } else {
                           lastFramePath = null;
                                }
                       runnableToReturn = new MediaUtil.GenerateThumbnailsRunnable(this.mVideoRecorder.getLastTimestamp(), this.mVideoPath, this.mThumbnailPath, lastFramePath);
                            }
                        } catch (HwVineFrameRecorder.Exception e3) {
                   CrashUtil.log("Error flushing...", e3);
                    runnableToReturn = null;
                        } catch (Exception e4) {
                   CrashUtil.log("Failed to generate thumbnail.");
                        }
                    }
           if (this.mAsyncTask != null) {
               this.mAsyncTask.publish(99);
                    }
           SLog.m444b("Time used: video: {}, audio: {}, n: {}.", new Object[]{Long.valueOf(videoTotal), Long.valueOf(audioTotal), Integer.valueOf(size)});
           return runnableToReturn;
                }
            }
