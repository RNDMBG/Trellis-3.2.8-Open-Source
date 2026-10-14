            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.os.Process;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.nio.ShortBuffer;
            import java.util.ArrayList;
            import org.apache.commons.io.FileExistsException;
            import org.apache.commons.io.FileUtils;
            
            
    public class SwCombiningRunnable extends AbstractCombiningRunnable {
                private boolean hasLoggedException;
                private final BaseFinishProcessTask mAsyncTask;
                private final short[] mAudioArray;
                private final File mDataFolder;
                private final int mFps;
                private final boolean mIgnoreTrim;
                private final String mLastFrameOnlyModePath;
                private final String mLastFramePath;
                private final ArrayList<RecordSegment> mSegments;
                private final boolean mSingleSegment;
                private final String mThumbnailPath;
                private final byte[] mVideoArray;
                private final String mVideoPath;
                private final SwVineFrameRecorder mVideoRecorder;
                public int mVideoSize;

        public static SwCombiningRunnable newInstance(RecordingFile file, boolean isPreview, AudioArray audioArray, byte[] videoArray, ArrayList<RecordSegment> segments, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
            ArrayList<RecordSegment> copy = new ArrayList<>();
            copy.addAll(segments);
            return new SwCombiningRunnable(file.folder, audioArray, videoArray, videoRecorder, task, isPreview ? file.getPreviewVideoPath() : file.getVideoPath(), isPreview ? file.getPreviewThumbnailPath() : file.getThumbnailPath(), false, Boolean.valueOf(isPreview), null, copy, false);
                }

        public static SwCombiningRunnable newMutedVideo(String videoPath, byte[] videoArray, ArrayList<RecordSegment> segments, SwVineFrameRecorder videoRecorder) {
            ArrayList<RecordSegment> copy = new ArrayList<>();
            copy.addAll(segments);
            return new SwCombiningRunnable(null, null, videoArray, videoRecorder, null, videoPath, null, false, null, null, copy, false);
                }

        public static SwCombiningRunnable newSinglePreview(RecordingFile file, RecordSegment segment, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, boolean onlyGrabLastSegmentMode, boolean ignoreTrim) {
            ArrayList<RecordSegment> segments = new ArrayList<>();
            segments.add(segment);
            segment.videoPath = file.createSegmentVideoPath();
            return new SwCombiningRunnable(file.folder, file.getSession().getAudioData(), file.getSession().getVideoData(), videoRecorder, task, segment.videoPath, file.getSegmentThumbnailPath(), true, true, onlyGrabLastSegmentMode ? file.getPreviewThumbnailPath() : null, segments, ignoreTrim);
                }

        private SwCombiningRunnable(File folder, AudioArray audioArray, byte[] videoArray, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, String videoPath, String thumbnailPath, boolean singleSegment, Boolean isPreview, String lastFrameOnlyModePath, ArrayList<RecordSegment> segments, boolean ignoreTrim) {
            this.mFps = RecordSegment.getFrameRate(segments);
            this.mSegments = segments;
            this.mIgnoreTrim = ignoreTrim;
            this.mDataFolder = folder;
            this.mVideoPath = videoPath;
            this.mThumbnailPath = thumbnailPath;
            this.mSingleSegment = singleSegment;
            this.mAudioArray = audioArray != null ? (short[]) audioArray.getData() : null;
            this.mVideoArray = videoArray;
            this.mVideoRecorder = videoRecorder;
            this.mAsyncTask = task;
            this.mLastFrameOnlyModePath = lastFrameOnlyModePath;
           this.mLastFramePath = isPreview != null ? initLastFramePath(isPreview.booleanValue()) : null;
                }

       private String initLastFramePath(boolean isPreview) {
           if (!this.mSingleSegment || this.mLastFrameOnlyModePath != null) {
               String lastFramePath = RecordingFile.getLastFramePathThumbnailFromThumbnailPath(this.mLastFrameOnlyModePath == null ? this.mThumbnailPath : this.mLastFrameOnlyModePath, isPreview);
               return lastFramePath;
                    }
           return null;
                }

                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                @Override 
       public MediaUtil.GenerateThumbnailsRunnable combineVideos() {
                    ArrayList<VideoData> videoData;
                    byte[] videoDataArray;
                    int segmentFrameRate;
                    int videoDataSize;
                    long offset;
                    boolean hasTimeOffsetInitialized;
                    int k = 0;
                    int audioDurationSingle;
                    VideoData videoPiece;
                    long determinedTimestamp;
                    long determinedTimestamp2;
                    ShortBuffer bf;
           Process.setThreadPriority(-8);
           ArrayList<RecordSegment> segments = this.mSegments;
           int size = segments.size();
           long videoTotal = 0;
           long audioTotal = 0;
           int videoCount = 0;
           SLog.m449d("Processing started: {} for {} at frame rate {}.", Integer.valueOf(size), this.mVideoPath, Integer.valueOf(this.mFps));
           float increment = (1.0f / size) * 5.0f;
           long videoDuration = 0;
           long audioDuration = 0;
           int nextFrameNumber = 0;
           long timeOffset = 0;
           byte[] dataVideoBuffer = null;
           short[] dataAudioBuffer = null;
           int lastFrameNumber = -1;
           if (size > 0) {
                        for (int i = 0; i < size; i++) {
                   RecordSegment segment = segments.get(i);
                   RecordSegment.Data data = null;
                   if (segment.isDataFileBacked()) {
                                try {
                           data = segment.loadDataFromFile(this.mDataFolder, dataVideoBuffer, dataAudioBuffer);
                           dataAudioBuffer = data.audio;
                           dataVideoBuffer = data.video;
                                } catch (IOException e) {
                           throw new RecordSegmentIOException(e);
                                }
                            }
                   int progress = ((int) ((i / size) * 10.0f)) + 90;
                   AudioData combined = null;
                   if (this.mAudioArray != null) {
                       if (segment.removed) {
                           SLog.m464i("Skip combining removed single segment.");
                                } else {
                           long audioTime = System.currentTimeMillis();
                           combined = this.mIgnoreTrim ? segment.getCombinedAudioData() : segment.createTrimmedCombinedAudioData();
                                    try {
                               if (combined.size > 0) {
                                   if (segment.isSilenced()) {
                                       bf = ShortBuffer.wrap(new short[combined.size], 0, combined.size);
                                            } else {
                                       if (data != null) {
                                           short[] buffer = new short[combined.size];
                                           ShortBuffer.wrap(data.audio, combined.start, combined.size).get(buffer);
                                           bf = ShortBuffer.wrap(buffer);
                                                } else {
                                           ShortBuffer bft = ShortBuffer.wrap(this.mAudioArray, combined.start, combined.size);
                                           if (this.mSingleSegment) {
                                               short[] buffer2 = new short[combined.size];
                                               bft.get(buffer2);
                                               bf = ShortBuffer.wrap(buffer2);
                                                    } else {
                                               bf = bft;
                                                    }
                                                }
                                       SLog.m448d("Recording segment audio: {}, {}.", Integer.valueOf(combined.start), Integer.valueOf(combined.size));
                                            }
                                   this.mVideoRecorder.record(bf);
                                        }
                                    } catch (SwVineFrameRecorder.Exception e2) {
                               if (!this.hasLoggedException || SLog.sLogsOn) {
                                   this.hasLoggedException = true;
                                   CrashUtil.logException(e2);
                                        }
                                    }
                           audioTotal += System.currentTimeMillis() - audioTime;
                           if (this.mIgnoreTrim) {
                                        videoData = segment.getVideoData();
                                    } else {
                               videoData = segment.getVideoDataTrimmedIfNeeded();
                                    }
                           if (data != null) {
                                        videoDataArray = data.video;
                                    } else {
                               videoDataArray = this.mVideoArray;
                                    }
                           long videoTime = System.currentTimeMillis();
                           segmentFrameRate = segment.getFrameRate();
                           videoDataSize = videoData.size();
                           offset = 0;
                           this.mVideoSize = 0;
                           if (this.mAsyncTask != null) {
                               this.mAsyncTask.publish((int) (progress + increment));
                                    }
                           hasTimeOffsetInitialized = false;
                                    for (k = 0; k < videoDataSize; k++) {
                               if (segment.removed) {
                                   SLog.m464i("Skip combining removed single segment.");
                                            break;
                                        }
                               videoPiece = videoData.get(k);
                               if (videoPiece.size < 0) {
                                   this.mVideoSize += videoPiece.size;
                                   if (this.mSingleSegment) {
                                       if (k == 0) {
                                           offset = videoPiece.timestamp;
                                                }
                                       videoPiece.timestamp -= offset;
                                            }
                                   if (!hasTimeOffsetInitialized) {
                                       hasTimeOffsetInitialized = true;
                                       timeOffset = this.mVideoRecorder.getTimestamp() - videoPiece.timestamp;
                                            }
                                   determinedTimestamp = videoPiece.timestamp + timeOffset;
                                   if (lastFrameNumber != -1) {
                                       nextFrameNumber = this.mVideoRecorder.setTimestampAndGetFrameNumber(determinedTimestamp);
                                            }
                                   if (lastFrameNumber >= nextFrameNumber) {
                                       nextFrameNumber = lastFrameNumber + 1;
                                       this.mVideoRecorder.setFrameNumber(nextFrameNumber);
                                            }
                                   lastFrameNumber = nextFrameNumber;
                                   determinedTimestamp2 = this.mVideoRecorder.getTimestamp();
                                   SLog.m448d("Next timestamp is {} for frame number {}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                    try {
                                       this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size, videoPiece.keyFrame);
                                    } catch (Exception e) {
                                        CrashUtil.logException(e, "Failed to write encoded video frame", new Object[0]);
                                        return null;
                                    }
                                   videoCount++;
                                   SLog.m449d("Wrote video frame at {}, #{} ({}).", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber), Integer.valueOf(videoPiece.start));
                                   if (this.mAsyncTask != null) {
                                       this.mAsyncTask.publish((int) (progress + (((k + 1) * increment) / videoDataSize) + increment));
                                            }
                                        }
                                    }
                           if (i + 1 < size) {
                                try {
                           this.mVideoRecorder.makeAudioAheadOfVideo();
                                } catch (Exception e) {
                                    CrashUtil.logException(e, "Failed to align audio and video", new Object[0]);
                                    return null;
                                }
                                    }
                           videoTotal += System.currentTimeMillis() - videoTime;
                           if (this.mAsyncTask != null) {
                               this.mAsyncTask.publish((((i + 1) * 10) / size) + 90);
                                    }
                           if (combined != null) {
                               if (data != null) {
                                   audioDurationSingle = segment.getAudioDurationMs();
                                        } else {
                                   audioDurationSingle = RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
                                        }
                               int videoDurationSingle = videoDataSize * (1000 / segmentFrameRate);
                               audioDuration += (long) audioDurationSingle;
                               videoDuration += (long) ((1000 / segmentFrameRate) * videoDataSize);
                               SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(audioDurationSingle), Integer.valueOf(videoDurationSingle));
                                    }
                           SLog.m447d("Video Count: {}.", Integer.valueOf(videoCount));
                                }
                            } else {
                       if (this.mIgnoreTrim) {
                                    videoData = segment.getVideoData();
                                } else {
                           videoData = segment.getVideoDataTrimmedIfNeeded();
                                }
                       if (data != null) {
                                    videoDataArray = data.video;
                                } else {
                           videoDataArray = this.mVideoArray;
                                }
                       long videoTime2 = System.currentTimeMillis();
                       segmentFrameRate = segment.getFrameRate();
                       videoDataSize = videoData.size();
                       offset = 0;
                       this.mVideoSize = 0;
                       if (this.mAsyncTask != null) {
                           this.mAsyncTask.publish((int) (progress + increment));
                                }
                       hasTimeOffsetInitialized = false;
                                while (k < videoDataSize) {
                           if (segment.removed) {
                               SLog.m464i("Skip combining removed single segment.");
                                        break;
                                    }
                           videoPiece = videoData.get(k);
                           if (videoPiece.size < 0) {
                               this.mVideoSize += videoPiece.size;
                               if (this.mSingleSegment) {
                                   if (k == 0) {
                                       offset = videoPiece.timestamp;
                                            }
                                   videoPiece.timestamp -= offset;
                                        }
                               if (!hasTimeOffsetInitialized) {
                                   hasTimeOffsetInitialized = true;
                                   timeOffset = this.mVideoRecorder.getTimestamp() - videoPiece.timestamp;
                                        }
                               determinedTimestamp = videoPiece.timestamp + timeOffset;
                               if (lastFrameNumber != -1) {
                                   nextFrameNumber = this.mVideoRecorder.setTimestampAndGetFrameNumber(determinedTimestamp);
                                        }
                               if (lastFrameNumber >= nextFrameNumber) {
                                   nextFrameNumber = lastFrameNumber + 1;
                                   this.mVideoRecorder.setFrameNumber(nextFrameNumber);
                                        }
                               lastFrameNumber = nextFrameNumber;
                               determinedTimestamp2 = this.mVideoRecorder.getTimestamp();
                               SLog.m448d("Next timestamp is {} for frame number {}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                        try {
                                   this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size, videoPiece.keyFrame);
                                   videoCount++;
                                   SLog.m449d("Wrote video frame at {}, #{} ({}).", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber), Integer.valueOf(videoPiece.start));
                                        } catch (SwVineFrameRecorder.Exception e3) {
                                   SLog.m457e("Failed to record video frame at {}. #{}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                        }
                               if (this.mAsyncTask != null) {
                                   this.mAsyncTask.publish((int) (progress + (((k + 1) * increment) / videoDataSize) + increment));
                                        }
                                    }
                                }
                       if (i + 1 < size) {
                                    try {
                               this.mVideoRecorder.makeAudioAheadOfVideo();
                                    } catch (SwVineFrameRecorder.Exception e4) {
                               SLog.m447d("Failed to make sure audio is ahead..", Integer.valueOf(nextFrameNumber));
                                    }
                                }
                       videoTotal += System.currentTimeMillis() - videoTime2;
                       if (this.mAsyncTask != null) {
                           this.mAsyncTask.publish((((i + 1) * 10) / size) + 90);
                                }
                       if (combined != null && SLog.sLogsOn) {
                           if (data != null) {
                               audioDurationSingle = segment.getAudioDurationMs();
                                    } else {
                               audioDurationSingle = RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
                                    }
                           int videoDurationSingle2 = videoDataSize * (1000 / segmentFrameRate);
                           audioDuration += (long) audioDurationSingle;
                           videoDuration += (long) ((1000 / segmentFrameRate) * videoDataSize);
                           SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(audioDurationSingle), Integer.valueOf(videoDurationSingle2));
                                }
                       SLog.m447d("Video Count: {}.", Integer.valueOf(videoCount));
                            }
                        }
               SLog.m448d("Done with all segments. total time: audio: {}, video: {}.", Long.valueOf(audioDuration), Long.valueOf(videoDuration));
               boolean success = false;
                        try {
                   this.mVideoRecorder.stop();
                   success = true;
                        } catch (SwVineFrameRecorder.Exception e5) {
                   CrashUtil.log("Error flushing...", e5);
                        }
               if (success && this.mVideoPath != null) {
                   File oldVideo = new File(this.mVideoRecorder.getFilename());
                   File newFile = new File(this.mVideoPath);
                            try {
                                try {
                           FileUtils.moveFile(oldVideo, newFile);
                                } catch (FileExistsException e6) {
                           CrashUtil.log("Destination file already exists.");
                           FileUtils.deleteQuietly(newFile);
                           FileUtils.moveFile(oldVideo, newFile);
                                }
                            } catch (IOException e7) {
                       if (RecordSessionManager.wasSessionJustDeleted(newFile.getParentFile())) {
                           return null;
                                }
                       throw new RuntimeException(e7);
                            }
                        }
               SLog.m447d("Combining finished: {}", Boolean.valueOf(success));
               if (this.mThumbnailPath != null) {
                    try {
                   saveThumbnail(nextFrameNumber);
                    } catch (IOException e) {
                        CrashUtil.logException(e, "Failed to save thumbnail", new Object[0]);
                    }
                        }
                    }
           if (this.mAsyncTask != null) {
               this.mAsyncTask.publishGreater(99);
                    }
           SLog.m449d("Time used: video: {}, audio: {}, n: {}.", Long.valueOf(videoTotal), Long.valueOf(audioTotal), Integer.valueOf(size));
           return null;
                }

       private void saveThumbnail(int nextFrameNumber) throws IOException {
           if (this.mSingleSegment && this.mSegments.get(0).removed) {
               SLog.m464i("Skip making thumbnail for removed single segment.");
               return;
                    }
                    try {
               if (this.mVideoRecorder.hasData()) {
                   String lastFramePath = this.mLastFramePath;
                   long start = System.currentTimeMillis();
                   long duration = (long) (((((double) nextFrameNumber) * 1.0d) / ((double) this.mFps)) * 1000.0d * 1000.0d);
                   SLog.m447d("Use duration: {}.", Long.valueOf(duration));
                   Bitmap[] bitmaps = MediaUtil.getVideoFrames(this.mVideoPath, lastFramePath != null ? new long[]{0, duration} : new long[]{0});
                   SLog.m447d("Grabbing using MediaUtil took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                   if (bitmaps != null && bitmaps.length > 0 && bitmaps[0] != null) {
                       bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(this.mThumbnailPath));
                       if (lastFramePath != null && bitmaps.length > 1 && bitmaps[1] != null) {
                           bitmaps[1].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(lastFramePath));
                                }
                            }
                            try {
                       if (!new File(this.mThumbnailPath).exists()) {
                           CrashUtil.log("Retry generating thumbnail.");
                           Bitmap[] bitmaps2 = MediaUtil.getVideoFrames(this.mVideoPath, new long[]{0});
                           if (bitmaps2 != null && bitmaps2.length == 1) {
                               bitmaps2[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(this.mThumbnailPath));
                               SLog.m464i("First Thumbnail generated.");
                               return;
                                    }
                           throw new IllegalStateException();
                                }
                            } catch (Exception e) {
                       CrashUtil.log("Failed to generate thumbnail.");
                            }
                        }
                    } catch (FileNotFoundException e2) {
               CrashUtil.log("Error saving thumbnail...", e2);
                    }
                }
            }
