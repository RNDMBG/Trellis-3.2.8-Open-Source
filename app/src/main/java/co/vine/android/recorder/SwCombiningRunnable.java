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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class SwCombiningRunnable extends AbstractCombiningRunnable {
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

/* 49 */        public static SwCombiningRunnable newInstance(RecordingFile file, boolean isPreview, AudioArray audioArray, byte[] videoArray, ArrayList<RecordSegment> segments, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task) {
/* 50 */            ArrayList<RecordSegment> copy = new ArrayList<>();
/* 51 */            copy.addAll(segments);
/* 52 */            return new SwCombiningRunnable(file.folder, audioArray, videoArray, videoRecorder, task, isPreview ? file.getPreviewVideoPath() : file.getVideoPath(), isPreview ? file.getPreviewThumbnailPath() : file.getThumbnailPath(), false, Boolean.valueOf(isPreview), null, copy, false);
                }

/* 61 */        public static SwCombiningRunnable newMutedVideo(String videoPath, byte[] videoArray, ArrayList<RecordSegment> segments, SwVineFrameRecorder videoRecorder) {
/* 62 */            ArrayList<RecordSegment> copy = new ArrayList<>();
/* 63 */            copy.addAll(segments);
/* 64 */            return new SwCombiningRunnable(null, null, videoArray, videoRecorder, null, videoPath, null, false, null, null, copy, false);
                }

/* 72 */        public static SwCombiningRunnable newSinglePreview(RecordingFile file, RecordSegment segment, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, boolean onlyGrabLastSegmentMode, boolean ignoreTrim) {
/* 73 */            ArrayList<RecordSegment> segments = new ArrayList<>();
/* 74 */            segments.add(segment);
/* 75 */            segment.videoPath = file.createSegmentVideoPath();
/* 76 */            return new SwCombiningRunnable(file.folder, file.getSession().getAudioData(), file.getSession().getVideoData(), videoRecorder, task, segment.videoPath, file.getSegmentThumbnailPath(), true, true, onlyGrabLastSegmentMode ? file.getPreviewThumbnailPath() : null, segments, ignoreTrim);
                }

/* 86 */        private SwCombiningRunnable(File folder, AudioArray audioArray, byte[] videoArray, SwVineFrameRecorder videoRecorder, BaseFinishProcessTask task, String videoPath, String thumbnailPath, boolean singleSegment, Boolean isPreview, String lastFrameOnlyModePath, ArrayList<RecordSegment> segments, boolean ignoreTrim) {
/* 88 */            this.mFps = RecordSegment.getFrameRate(segments);
/* 89 */            this.mSegments = segments;
/* 90 */            this.mIgnoreTrim = ignoreTrim;
/* 91 */            this.mDataFolder = folder;
/* 92 */            this.mVideoPath = videoPath;
/* 93 */            this.mThumbnailPath = thumbnailPath;
/* 94 */            this.mSingleSegment = singleSegment;
/* 95 */            this.mAudioArray = audioArray != null ? (short[]) audioArray.getData() : null;
/* 96 */            this.mVideoArray = videoArray;
/* 97 */            this.mVideoRecorder = videoRecorder;
/* 98 */            this.mAsyncTask = task;
/* 99 */            this.mLastFrameOnlyModePath = lastFrameOnlyModePath;
/* 100 */           this.mLastFramePath = isPreview != null ? initLastFramePath(isPreview.booleanValue()) : null;
                }

/* 104 */       private String initLastFramePath(boolean isPreview) {
/* 105 */           if (!this.mSingleSegment || this.mLastFrameOnlyModePath != null) {
/* 106 */               String lastFramePath = RecordingFile.getLastFramePathThumbnailFromThumbnailPath(this.mLastFrameOnlyModePath == null ? this.mThumbnailPath : this.mLastFrameOnlyModePath, isPreview);
/* 112 */               return lastFramePath;
                    }
/* 110 */           return null;
                }

                /* JADX WARN: Code duplicated, block: B:100:0x040a  */
                /* JADX WARN: Code duplicated, block: B:133:0x018e A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:145:0x0181 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:146:0x02cc A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:147:0x02cc A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:32:0x012c  */
                /* JADX WARN: Code duplicated, block: B:34:0x0132  */
                /* JADX WARN: Code duplicated, block: B:37:0x0156  */
                /* JADX WARN: Code duplicated, block: B:41:0x0179  */
                /* JADX WARN: Code duplicated, block: B:49:0x01a7  */
                /* JADX WARN: Code duplicated, block: B:54:0x01c4  */
                /* JADX WARN: Code duplicated, block: B:73:0x02ac  */
                /* JADX WARN: Code duplicated, block: B:74:0x02b2  */
                /* JADX WARN: Code duplicated, block: B:75:0x02ba  */
                /* JADX WARN: Code duplicated, block: B:78:0x02d0  */
                /* JADX WARN: Code duplicated, block: B:80:0x02ec A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:81:0x02ee  */
                /* JADX WARN: Code duplicated, block: B:84:0x0304  */
                /* JADX WARN: Code duplicated, block: B:87:0x0328  */
                /* JADX WARN: Code duplicated, block: B:90:0x033c  */
                /* JADX WARN: Code duplicated, block: B:95:0x03b8  */
                @Override // co.vine.android.recorder.AbstractCombiningRunnable
/* 119 */       public MediaUtil.GenerateThumbnailsRunnable combineVideos() {
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
/* 120 */           Process.setThreadPriority(-8);
/* 121 */           ArrayList<RecordSegment> segments = this.mSegments;
/* 122 */           int size = segments.size();
/* 123 */           long videoTotal = 0;
/* 124 */           long audioTotal = 0;
/* 125 */           int videoCount = 0;
/* 126 */           SLog.m449d("Processing started: {} for {} at frame rate {}.", Integer.valueOf(size), this.mVideoPath, Integer.valueOf(this.mFps));
/* 127 */           float increment = (1.0f / size) * 5.0f;
/* 128 */           long videoDuration = 0;
/* 129 */           long audioDuration = 0;
/* 130 */           int nextFrameNumber = 0;
/* 132 */           long timeOffset = 0;
/* 133 */           byte[] dataVideoBuffer = null;
/* 134 */           short[] dataAudioBuffer = null;
/* 135 */           int lastFrameNumber = -1;
/* 136 */           if (size > 0) {
                        for (int i = 0; i < size; i++) {
/* 138 */                   RecordSegment segment = segments.get(i);
/* 139 */                   RecordSegment.Data data = null;
/* 140 */                   if (segment.isDataFileBacked()) {
                                try {
/* 142 */                           data = segment.loadDataFromFile(this.mDataFolder, dataVideoBuffer, dataAudioBuffer);
/* 146 */                           dataAudioBuffer = data.audio;
/* 147 */                           dataVideoBuffer = data.video;
                                } catch (IOException e) {
/* 144 */                           throw new RecordSegmentIOException(e);
                                }
                            }
/* 149 */                   int progress = ((int) ((i / size) * 10.0f)) + 90;
/* 150 */                   AudioData combined = null;
/* 151 */                   if (this.mAudioArray != null) {
/* 152 */                       if (segment.removed) {
/* 153 */                           SLog.m464i("Skip combining removed single segment.");
                                } else {
/* 156 */                           long audioTime = System.currentTimeMillis();
/* 157 */                           combined = this.mIgnoreTrim ? segment.getCombinedAudioData() : segment.createTrimmedCombinedAudioData();
                                    try {
/* 161 */                               if (combined.size > 0) {
/* 163 */                                   if (segment.isSilenced()) {
/* 164 */                                       bf = ShortBuffer.wrap(new short[combined.size], 0, combined.size);
                                            } else {
/* 166 */                                       if (data != null) {
/* 167 */                                           short[] buffer = new short[combined.size];
/* 168 */                                           ShortBuffer.wrap(data.audio, combined.start, combined.size).get(buffer);
/* 169 */                                           bf = ShortBuffer.wrap(buffer);
                                                } else {
/* 171 */                                           ShortBuffer bft = ShortBuffer.wrap(this.mAudioArray, combined.start, combined.size);
/* 172 */                                           if (this.mSingleSegment) {
/* 173 */                                               short[] buffer2 = new short[combined.size];
/* 174 */                                               bft.get(buffer2);
/* 175 */                                               bf = ShortBuffer.wrap(buffer2);
                                                    } else {
/* 177 */                                               bf = bft;
                                                    }
                                                }
/* 180 */                                       SLog.m448d("Recording segment audio: {}, {}.", Integer.valueOf(combined.start), Integer.valueOf(combined.size));
                                            }
/* 182 */                                   this.mVideoRecorder.record(bf);
                                        }
                                    } catch (SwVineFrameRecorder.Exception e2) {
/* 185 */                               if (!this.hasLoggedException || SLog.sLogsOn) {
/* 186 */                                   this.hasLoggedException = true;
/* 187 */                                   CrashUtil.logException(e2);
                                        }
                                    }
/* 190 */                           audioTotal += System.currentTimeMillis() - audioTime;
/* 192 */                           if (this.mIgnoreTrim) {
                                        videoData = segment.getVideoData();
                                    } else {
/* 192 */                               videoData = segment.getVideoDataTrimmedIfNeeded();
                                    }
/* 195 */                           if (data != null) {
                                        videoDataArray = data.video;
                                    } else {
/* 195 */                               videoDataArray = this.mVideoArray;
                                    }
/* 196 */                           long videoTime = System.currentTimeMillis();
/* 197 */                           segmentFrameRate = segment.getFrameRate();
/* 198 */                           videoDataSize = videoData.size();
/* 200 */                           offset = 0;
/* 202 */                           this.mVideoSize = 0;
/* 204 */                           if (this.mAsyncTask != null) {
/* 205 */                               this.mAsyncTask.publish((int) (progress + increment));
                                    }
/* 208 */                           hasTimeOffsetInitialized = false;
                                    for (k = 0; k < videoDataSize; k++) {
/* 211 */                               if (segment.removed) {
/* 212 */                                   SLog.m464i("Skip combining removed single segment.");
                                            break;
                                        }
/* 216 */                               videoPiece = videoData.get(k);
/* 218 */                               if (videoPiece.size < 0) {
/* 222 */                                   this.mVideoSize += videoPiece.size;
/* 224 */                                   if (this.mSingleSegment) {
/* 225 */                                       if (k == 0) {
/* 226 */                                           offset = videoPiece.timestamp;
                                                }
/* 228 */                                       videoPiece.timestamp -= offset;
                                            }
/* 231 */                                   if (!hasTimeOffsetInitialized) {
/* 232 */                                       hasTimeOffsetInitialized = true;
/* 233 */                                       timeOffset = this.mVideoRecorder.getTimestamp() - videoPiece.timestamp;
                                            }
/* 236 */                                   determinedTimestamp = videoPiece.timestamp + timeOffset;
/* 238 */                                   if (lastFrameNumber != -1) {
/* 239 */                                       nextFrameNumber = this.mVideoRecorder.setTimestampAndGetFrameNumber(determinedTimestamp);
                                            }
/* 241 */                                   if (lastFrameNumber >= nextFrameNumber) {
/* 242 */                                       nextFrameNumber = lastFrameNumber + 1;
/* 243 */                                       this.mVideoRecorder.setFrameNumber(nextFrameNumber);
                                            }
/* 245 */                                   lastFrameNumber = nextFrameNumber;
/* 246 */                                   determinedTimestamp2 = this.mVideoRecorder.getTimestamp();
/* 248 */                                   SLog.m448d("Next timestamp is {} for frame number {}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                    try {
/* 252 */                                       this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size, videoPiece.keyFrame);
                                    } catch (Exception e) {
                                        CrashUtil.logException(e, "Failed to write encoded video frame", new Object[0]);
                                        return null;
                                    }
/* 254 */                                   videoCount++;
/* 255 */                                   SLog.m449d("Wrote video frame at {}, #{} ({}).", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber), Integer.valueOf(videoPiece.start));
/* 259 */                                   if (this.mAsyncTask != null) {
/* 260 */                                       this.mAsyncTask.publish((int) (progress + (((k + 1) * increment) / videoDataSize) + increment));
                                            }
                                        }
                                    }
/* 264 */                           if (i + 1 < size) {
                                try {
/* 265 */                           this.mVideoRecorder.makeAudioAheadOfVideo();
                                } catch (Exception e) {
                                    CrashUtil.logException(e, "Failed to align audio and video", new Object[0]);
                                    return null;
                                }
                                    }
/* 270 */                           videoTotal += System.currentTimeMillis() - videoTime;
/* 271 */                           if (this.mAsyncTask != null) {
/* 272 */                               this.mAsyncTask.publish((((i + 1) * 10) / size) + 90);
                                    }
/* 274 */                           if (combined != null) {
/* 276 */                               if (data != null) {
/* 277 */                                   audioDurationSingle = segment.getAudioDurationMs();
                                        } else {
/* 279 */                                   audioDurationSingle = RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
                                        }
/* 281 */                               int videoDurationSingle = videoDataSize * (1000 / segmentFrameRate);
/* 282 */                               audioDuration += (long) audioDurationSingle;
/* 283 */                               videoDuration += (long) ((1000 / segmentFrameRate) * videoDataSize);
/* 284 */                               SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(audioDurationSingle), Integer.valueOf(videoDurationSingle));
                                    }
/* 286 */                           SLog.m447d("Video Count: {}.", Integer.valueOf(videoCount));
                                }
                            } else {
/* 192 */                       if (this.mIgnoreTrim) {
                                    videoData = segment.getVideoData();
                                } else {
/* 192 */                           videoData = segment.getVideoDataTrimmedIfNeeded();
                                }
/* 195 */                       if (data != null) {
                                    videoDataArray = data.video;
                                } else {
/* 195 */                           videoDataArray = this.mVideoArray;
                                }
/* 196 */                       long videoTime2 = System.currentTimeMillis();
/* 197 */                       segmentFrameRate = segment.getFrameRate();
/* 198 */                       videoDataSize = videoData.size();
/* 200 */                       offset = 0;
/* 202 */                       this.mVideoSize = 0;
/* 204 */                       if (this.mAsyncTask != null) {
/* 205 */                           this.mAsyncTask.publish((int) (progress + increment));
                                }
/* 208 */                       hasTimeOffsetInitialized = false;
                                while (k < videoDataSize) {
/* 211 */                           if (segment.removed) {
/* 212 */                               SLog.m464i("Skip combining removed single segment.");
                                        break;
                                    }
/* 216 */                           videoPiece = videoData.get(k);
/* 218 */                           if (videoPiece.size < 0) {
/* 222 */                               this.mVideoSize += videoPiece.size;
/* 224 */                               if (this.mSingleSegment) {
/* 225 */                                   if (k == 0) {
/* 226 */                                       offset = videoPiece.timestamp;
                                            }
/* 228 */                                   videoPiece.timestamp -= offset;
                                        }
/* 231 */                               if (!hasTimeOffsetInitialized) {
/* 232 */                                   hasTimeOffsetInitialized = true;
/* 233 */                                   timeOffset = this.mVideoRecorder.getTimestamp() - videoPiece.timestamp;
                                        }
/* 236 */                               determinedTimestamp = videoPiece.timestamp + timeOffset;
/* 238 */                               if (lastFrameNumber != -1) {
/* 239 */                                   nextFrameNumber = this.mVideoRecorder.setTimestampAndGetFrameNumber(determinedTimestamp);
                                        }
/* 241 */                               if (lastFrameNumber >= nextFrameNumber) {
/* 242 */                                   nextFrameNumber = lastFrameNumber + 1;
/* 243 */                                   this.mVideoRecorder.setFrameNumber(nextFrameNumber);
                                        }
/* 245 */                               lastFrameNumber = nextFrameNumber;
/* 246 */                               determinedTimestamp2 = this.mVideoRecorder.getTimestamp();
/* 248 */                               SLog.m448d("Next timestamp is {} for frame number {}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                        try {
/* 252 */                                   this.mVideoRecorder.writeEncodedImage(videoDataArray, videoPiece.start, videoPiece.size, videoPiece.keyFrame);
/* 254 */                                   videoCount++;
/* 255 */                                   SLog.m449d("Wrote video frame at {}, #{} ({}).", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber), Integer.valueOf(videoPiece.start));
                                        } catch (SwVineFrameRecorder.Exception e3) {
/* 257 */                                   SLog.m457e("Failed to record video frame at {}. #{}.", Long.valueOf(determinedTimestamp2), Integer.valueOf(nextFrameNumber));
                                        }
/* 259 */                               if (this.mAsyncTask != null) {
/* 260 */                                   this.mAsyncTask.publish((int) (progress + (((k + 1) * increment) / videoDataSize) + increment));
                                        }
                                    }
                                }
/* 264 */                       if (i + 1 < size) {
                                    try {
/* 265 */                               this.mVideoRecorder.makeAudioAheadOfVideo();
                                    } catch (SwVineFrameRecorder.Exception e4) {
/* 268 */                               SLog.m447d("Failed to make sure audio is ahead..", Integer.valueOf(nextFrameNumber));
                                    }
                                }
/* 270 */                       videoTotal += System.currentTimeMillis() - videoTime2;
/* 271 */                       if (this.mAsyncTask != null) {
/* 272 */                           this.mAsyncTask.publish((((i + 1) * 10) / size) + 90);
                                }
/* 274 */                       if (combined != null && SLog.sLogsOn) {
/* 276 */                           if (data != null) {
/* 277 */                               audioDurationSingle = segment.getAudioDurationMs();
                                    } else {
/* 279 */                               audioDurationSingle = RecordConfigUtils.getTimeStampInMsFromSampleCounted(combined.size);
                                    }
/* 281 */                           int videoDurationSingle2 = videoDataSize * (1000 / segmentFrameRate);
/* 282 */                           audioDuration += (long) audioDurationSingle;
/* 283 */                           videoDuration += (long) ((1000 / segmentFrameRate) * videoDataSize);
/* 284 */                           SLog.m448d("Recorded segment with time: audio: {}, video: {}.", Integer.valueOf(audioDurationSingle), Integer.valueOf(videoDurationSingle2));
                                }
/* 286 */                       SLog.m447d("Video Count: {}.", Integer.valueOf(videoCount));
                            }
                        }
/* 288 */               SLog.m448d("Done with all segments. total time: audio: {}, video: {}.", Long.valueOf(audioDuration), Long.valueOf(videoDuration));
/* 289 */               boolean success = false;
                        try {
/* 291 */                   this.mVideoRecorder.stop();
/* 292 */                   success = true;
                        } catch (SwVineFrameRecorder.Exception e5) {
/* 294 */                   CrashUtil.log("Error flushing...", e5);
                        }
/* 296 */               if (success && this.mVideoPath != null) {
/* 297 */                   File oldVideo = new File(this.mVideoRecorder.getFilename());
/* 298 */                   File newFile = new File(this.mVideoPath);
                            try {
                                try {
/* 301 */                           FileUtils.moveFile(oldVideo, newFile);
                                } catch (FileExistsException e6) {
/* 303 */                           CrashUtil.log("Destination file already exists.");
/* 304 */                           FileUtils.deleteQuietly(newFile);
/* 305 */                           FileUtils.moveFile(oldVideo, newFile);
                                }
                            } catch (IOException e7) {
/* 308 */                       if (RecordSessionManager.wasSessionJustDeleted(newFile.getParentFile())) {
/* 309 */                           return null;
                                }
/* 311 */                       throw new RuntimeException(e7);
                            }
                        }
/* 315 */               SLog.m447d("Combining finished: {}", Boolean.valueOf(success));
/* 316 */               if (this.mThumbnailPath != null) {
                    try {
/* 317 */                   saveThumbnail(nextFrameNumber);
                    } catch (IOException e) {
                        CrashUtil.logException(e, "Failed to save thumbnail", new Object[0]);
                    }
                        }
                    }
/* 320 */           if (this.mAsyncTask != null) {
/* 321 */               this.mAsyncTask.publishGreater(99);
                    }
/* 323 */           SLog.m449d("Time used: video: {}, audio: {}, n: {}.", Long.valueOf(videoTotal), Long.valueOf(audioTotal), Integer.valueOf(size));
/* 324 */           return null;
                }

/* 327 */       private void saveThumbnail(int nextFrameNumber) throws IOException {
/* 328 */           if (this.mSingleSegment && this.mSegments.get(0).removed) {
/* 329 */               SLog.m464i("Skip making thumbnail for removed single segment.");
/* 376 */               return;
                    }
                    try {
/* 333 */               if (this.mVideoRecorder.hasData()) {
/* 334 */                   String lastFramePath = this.mLastFramePath;
/* 335 */                   long start = System.currentTimeMillis();
/* 337 */                   long duration = (long) (((((double) nextFrameNumber) * 1.0d) / ((double) this.mFps)) * 1000.0d * 1000.0d);
/* 339 */                   SLog.m447d("Use duration: {}.", Long.valueOf(duration));
/* 341 */                   Bitmap[] bitmaps = MediaUtil.getVideoFrames(this.mVideoPath, lastFramePath != null ? new long[]{0, duration} : new long[]{0});
/* 346 */                   SLog.m447d("Grabbing using MediaUtil took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 348 */                   if (bitmaps != null && bitmaps.length > 0 && bitmaps[0] != null) {
/* 350 */                       bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(this.mThumbnailPath));
/* 351 */                       if (lastFramePath != null && bitmaps.length > 1 && bitmaps[1] != null) {
/* 353 */                           bitmaps[1].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(lastFramePath));
                                }
                            }
                            try {
/* 359 */                       if (!new File(this.mThumbnailPath).exists()) {
/* 360 */                           CrashUtil.log("Retry generating thumbnail.");
/* 361 */                           Bitmap[] bitmaps2 = MediaUtil.getVideoFrames(this.mVideoPath, new long[]{0});
/* 362 */                           if (bitmaps2 != null && bitmaps2.length == 1) {
/* 363 */                               bitmaps2[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(this.mThumbnailPath));
/* 364 */                               SLog.m464i("First Thumbnail generated.");
/* 376 */                               return;
                                    }
/* 366 */                           throw new IllegalStateException();
                                }
                            } catch (Exception e) {
/* 370 */                       CrashUtil.log("Failed to generate thumbnail.");
                            }
                        }
                    } catch (FileNotFoundException e2) {
/* 374 */               CrashUtil.log("Error saving thumbnail...", e2);
                    }
                }
            }
