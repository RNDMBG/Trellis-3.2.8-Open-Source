            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.os.Process;
            import co.vine.android.recorder.buffered.BufferedPreviewManagerCallback;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ExceptionUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.opencv_core;
            import java.io.ByteArrayOutputStream;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.util.LinkedList;
            import java.util.concurrent.ConcurrentLinkedQueue;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class SwEncodingRunnable extends AbstractEncodingRunnable {
                private static int sRunningInstances;
                private BaseFinishProcessTask mAsyncTask;
                private final ByteBufferQueue mByteBufferQueue;
                private final ConcurrentLinkedQueue<VideoData> mDataQueue;
                private SwVineFrameRecorder mEncoder;
                private final opencv_core.IplImage mEncodingImage;
                private final String mEncodingTempPath;
                private final RecordProcessor.ProcessingErrorHandler mErrorhandler;
                private RecordSegment mLastSegment;
                private Bitmap mMatrixBitmap;
                private Canvas mMatrixCanvas;
                private int mMaxEncodedSize;
                private final String mOutput;
                private final PictureConverter mPictureConverter;
                private final BufferedPreviewManagerCallback mPreviewCallback;
                private final int mRecorderFrameRate;
                private final RecordState mState;
                private volatile boolean mTerminateImmediately = false;
                private final Bitmap mThumbnailBitmap;
                private final Canvas mThumbnailCanvas;
                private final Matrix mThumbnailMatrix;
                private int mTotalProcessed;
                private final boolean mUseMp4;
                private final byte[] mVideoDataBufferMax;
                private int mVideoDataBufferPosition;
                private long timeToMatrix;
                private long timeToRGB;
                private long timeToRecord;

/* 29 */        static /* synthetic */ void access$000(SwEncodingRunnable x0) {
/* 30 */            x0.makeNewEncoder();
                }

/* 29 */        static /* synthetic */ ByteBufferQueue access$100(SwEncodingRunnable x0) {
/* 30 */            return x0.mByteBufferQueue;
                }

/* 29 */        static /* synthetic */ RecordState access$200(SwEncodingRunnable x0) {
/* 30 */            return x0.mState;
                }

/* 29 */        static /* synthetic */ BaseFinishProcessTask access$300(SwEncodingRunnable x0) {
/* 30 */            return x0.mAsyncTask;
                }

/* 29 */        static /* synthetic */ void access$400(SwEncodingRunnable x0, Exception x1) {
/* 30 */            x0.onNotEnoughSpaceLeft(x1);
                }

/* 29 */        static /* synthetic */ void access$500(SwEncodingRunnable x0, LinkedList x1) {
/* 30 */            x0.stopEncoder(x1);
                }

/* 69 */        public SwEncodingRunnable(ConcurrentLinkedQueue<VideoData> queue, byte[] videoDataBufferMax, RecordState state, String output, int frameRate, opencv_core.IplImage image, Bitmap bitmap, Bitmap thumbnailBitmap, PictureConverter pc, int videoBufferPosition, BufferedPreviewManagerCallback cb, RecordProcessor.ProcessingErrorHandler errorhandler, ByteBufferQueue byteBufferQueue, Matrix thumbnailMatrix, Canvas thumbnailCanvas, Canvas matrixCanvas, boolean useMp4) {
/* 71 */            this.mEncodingImage = image;
/* 72 */            this.mUseMp4 = useMp4;
/* 73 */            this.mDataQueue = queue;
/* 74 */            this.mState = state;
/* 75 */            this.mPreviewCallback = cb;
/* 76 */            this.mMatrixBitmap = bitmap;
/* 77 */            this.mThumbnailBitmap = thumbnailBitmap;
/* 78 */            this.mThumbnailCanvas = thumbnailCanvas;
/* 79 */            this.mMatrixCanvas = matrixCanvas;
/* 80 */            this.mVideoDataBufferMax = videoDataBufferMax;
/* 81 */            this.mVideoDataBufferPosition = videoBufferPosition;
/* 82 */            this.mPictureConverter = pc;
/* 83 */            this.mRecorderFrameRate = frameRate;
/* 84 */            this.mOutput = output;
/* 85 */            this.mErrorhandler = errorhandler;
/* 86 */            this.mThumbnailMatrix = thumbnailMatrix;
/* 87 */            this.mEncodingTempPath = this.mOutput + ".encode";
/* 88 */            this.mByteBufferQueue = byteBufferQueue;
/* 89 */            if (this.mByteBufferQueue == null || !this.mByteBufferQueue.isFresh()) {
/* 90 */                throw new RuntimeException("Invalid buffer queue.");
                    }
                }

/* 100 */       static class EncodingProcessRunnable implements Runnable {
                    private LinkedList<VideoData> mBufferedVideoData;
                    private SwEncodingRunnable mParent;
                    public boolean mRun;
                    private final int[] LOCK = new int[0];
                    private int totalSizeToWait = -1;

/* 101 */           public EncodingProcessRunnable(SwEncodingRunnable parent) {
/* 103 */               this.mParent = parent;
/* 104 */               SwEncodingRunnable.access$000(this.mParent);
/* 105 */               this.mBufferedVideoData = new LinkedList<>();
                    }

                    @Override // java.lang.Runnable
/* 109 */           public void run() {
/* 110 */               Process.setThreadPriority(19);
/* 111 */               SLog.m465i("Started encoding process runnable, additional drawers: {}.", Integer.valueOf(this.mParent.mAdditionalDrawers.size()));
/* 112 */               while (this.mRun) {
/* 113 */                   ByteBufferQueue.QueueItem data = SwEncodingRunnable.access$100(this.mParent).get();
/* 114 */                   if (!SwEncodingRunnable.access$200(this.mParent).isStarted() && SwEncodingRunnable.access$300(this.mParent) != null) {
/* 116 */                       int size = Math.max(SwEncodingRunnable.access$100(this.mParent).size(), 1);
/* 117 */                       if (this.totalSizeToWait == -1) {
/* 118 */                           this.totalSizeToWait = size;
/* 119 */                           Process.setThreadPriority(-8);
/* 120 */                           SLog.m465i("Size to wait: {}.", Integer.valueOf(this.totalSizeToWait));
                                }
/* 122 */                       SwEncodingRunnable.access$300(this.mParent).publish(((this.totalSizeToWait - size) * 90) / this.totalSizeToWait);
                            }
/* 124 */                   if (data != null) {
/* 125 */                       VideoData next = (VideoData) data.tag;
/* 126 */                       if (next == null) {
/* 127 */                           CrashUtil.log("Ignored invalid data tag, segment may have been gone.");
                                } else {
/* 130 */                           next.data = data.bytes;
                                    try {
/* 132 */                               this.mParent.processVideoData(this.mBufferedVideoData, next);
                                    } catch (Exception e) {
/* 134 */                               if (e instanceof NotEnoughSpaceException) {
/* 135 */                                   SwEncodingRunnable.access$400(this.mParent, e);
/* 155 */                                   return;
                                        }
/* 138 */                               throw new RuntimeException(e);
                                    }
                                }
                            } else if (SwEncodingRunnable.access$100(this.mParent).isEndOfPut()) {
/* 143 */                       this.mRun = false;
                            } else {
                                try {
/* 146 */                           Thread.sleep(50L);
                                } catch (InterruptedException e2) {
/* 148 */                           SLog.m455e("Interrupted.");
                                }
                            }
                        }
/* 153 */               SwEncodingRunnable.access$500(this.mParent, this.mBufferedVideoData);
/* 154 */               this.mParent = null;
                    }

/* 157 */           public void add(VideoData next) {
                        synchronized (this.LOCK) {
                            try {
/* 159 */                       SwEncodingRunnable parent = this.mParent;
/* 160 */                       byte[] bytes = next.data;
/* 161 */                       if (bytes != null && parent != null && !SwEncodingRunnable.access$100(parent).put(new ByteBufferQueue.QueueItem(bytes, next))) {
/* 162 */                           CrashUtil.log("Not enough memory left. (BufferOverflow)");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 168 */       private void onNotEnoughSpaceLeft(Exception e) {
/* 169 */           if (this.mErrorhandler != null) {
/* 170 */               this.mErrorhandler.onNotEnoughSpaceLeft(e);
                    }
                }

/* 174 */       private void makeNewEncoder() {
/* 175 */           this.mEncoder = RecordConfigUtils.newVideoRecorder(this.mEncodingTempPath, this.mRecorderFrameRate, 480, this.mUseMp4);
                    try {
/* 177 */               this.mEncoder.start();
                    } catch (SwVineFrameRecorder.Exception e) {
/* 179 */               CrashUtil.logException(e, "Failed to start encoder", new Object[0]);
                    }
                }

/* 183 */       public void processVideoData(LinkedList<VideoData> bufferedVideoData, VideoData next) throws SwVineFrameRecorder.Exception, IOException {
/* 184 */           boolean newSegment = this.mLastSegment != next.segment;
/* 185 */           this.mLastSegment = next.segment;
/* 186 */           if (this.mLastSegment.removed) {
/* 187 */               SLog.m464i("Skipped processing data from deleted segments.");
/* 253 */               return;
                    }
                    synchronized (this.mPictureConverter.LOCK) {
                        try {
/* 191 */                   long startTime = System.currentTimeMillis();
/* 192 */                   CameraSetting cameraSetting = next.segment.getCameraSetting();
/* 193 */                   boolean hasChanged = this.mPictureConverter.updateSettingsIfNeeded(cameraSetting);
/* 195 */                   this.mPictureConverter.giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(cameraSetting.degrees, false);
/* 198 */                   this.mPictureConverter.convert(cameraSetting, next, hasChanged);
/* 200 */                   this.timeToRGB += System.currentTimeMillis() - startTime;
/* 201 */                   long startTime2 = System.currentTimeMillis();
/* 205 */                   this.mPictureConverter.draw(this.mMatrixCanvas);
                            for (AbstractEncodingRunnable.Drawer drawer : this.mAdditionalDrawers) {
/* 208 */                       drawer.draw(this.mMatrixCanvas, cameraSetting.frontFacing);
                            }
/* 211 */                   this.timeToMatrix += System.currentTimeMillis() - startTime2;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 215 */           this.mMatrixBitmap.copyPixelsToBuffer(this.mEncodingImage.getByteBuffer());
/* 217 */           if (newSegment && !this.mTerminateImmediately) {
/* 218 */               this.mPictureConverter.drawGeneric(this.mThumbnailCanvas, this.mMatrixBitmap, this.mThumbnailMatrix);
/* 219 */               String path = this.mOutput + "." + System.currentTimeMillis() + ".jpg";
/* 220 */               next.segment.setThumbnailPath(path);
/* 221 */               File f = new File(path);
/* 222 */               File parentFile = f.getParentFile();
                        try {
/* 224 */                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 225 */                   this.mThumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
/* 226 */                   byte[] compressed = bos.toByteArray();
/* 227 */                   FileOutputStream fos = new FileOutputStream(f);
/* 228 */                   fos.write(compressed);
/* 229 */                   fos.close();
                        } catch (Throwable e) {
/* 231 */                   boolean wasSessionJustDeleted = RecordSessionManager.wasSessionJustDeleted(parentFile);
/* 232 */                   if (wasSessionJustDeleted) {
/* 233 */                       CrashUtil.log("Session was just deleted, terminate encoder.");
/* 234 */                       this.mTerminateImmediately = true;
/* 235 */                       CrashUtil.logException(e);
                            } else {
/* 237 */                       if ((e instanceof Exception) && ExceptionUtil.isNoSpaceLeftException((Exception) e)) {
/* 238 */                           throw new NotEnoughSpaceException(e);
                                }
/* 240 */                       throw e;
                            }
                        }
                    }
/* 246 */           if (!this.mTerminateImmediately) {
/* 247 */               long startTime3 = System.currentTimeMillis();
/* 248 */               bufferedVideoData.add(next);
/* 249 */               doVideoFrame(bufferedVideoData, this.mEncodingImage, newSegment);
/* 250 */               this.timeToRecord += System.currentTimeMillis() - startTime3;
                    }
/* 252 */           next.data = null;
                }

/* 255 */       private boolean doVideoFrame(LinkedList<VideoData> bufferedVideoData, opencv_core.IplImage image, boolean newSegment) throws SwVineFrameRecorder.Exception {
/* 256 */           if (this.mEncoder == null) {
/* 257 */               makeNewEncoder();
                    } else if (newSegment) {
/* 260 */               stopEncoder(bufferedVideoData);
/* 261 */               makeNewEncoder();
                    }
/* 264 */           VideoData data = bufferedVideoData.getFirst();
/* 265 */           data.size = this.mEncoder.encode(image, this.mVideoDataBufferMax, this.mVideoDataBufferPosition);
/* 266 */           SLog.m448d("Encoded video frame size {}. Total processed {}.", Integer.valueOf(data.size), Integer.valueOf(this.mTotalProcessed));
/* 267 */           this.mTotalProcessed++;
/* 268 */           if (data.size != -1) {
/* 269 */               data.start = this.mVideoDataBufferPosition;
/* 270 */               data.keyFrame = this.mEncoder.wasLastEncodedFrameKeyFrame();
/* 271 */               data.encoded = true;
/* 272 */               this.mMaxEncodedSize = Math.max(this.mMaxEncodedSize, data.size);
/* 273 */               this.mVideoDataBufferPosition += data.size;
/* 274 */               bufferedVideoData.pop();
                        return true;
                    }
/* 277 */           SLog.m446d("******Image was buffered to the next frame.********");
/* 278 */           return false;
                }

/* 283 */       private void stopEncoder(LinkedList<VideoData> bufferedVideoData) {
            try {
                while (bufferedVideoData.size() > 0 && doVideoFrame(bufferedVideoData, null, false)) {
                }
                this.mEncoder.stopEncoding();
            } catch (Exception e) {
                SLog.m459e("Error stopping encoder", e);
            } finally {
                this.mEncoder.release();
            }
        }

                @Override // co.vine.android.recorder.AbstractEncodingRunnable
/* 293 */       public void setAsyncTask(BaseFinishProcessTask task) {
/* 294 */           this.mAsyncTask = task;
                }

                @Override // co.vine.android.recorder.AbstractEncodingRunnable
/* 297 */       public void terminate() {
/* 298 */           CrashUtil.log("Terminate without saving turned on.");
/* 299 */           this.mTerminateImmediately = true;
                }

                @Override // java.lang.Runnable
/* 303 */       public void run() {
/* 304 */           sRunningInstances++;
/* 305 */           CrashUtil.set("Encoder Count", sRunningInstances);
/* 306 */           Process.setThreadPriority(19);
/* 307 */           CrashUtil.log("[ML] Processing started: {}, running: {}", this.mOutput, Integer.valueOf(sRunningInstances));
/* 308 */           EncodingProcessRunnable encodingRunnable = new EncodingProcessRunnable(this);
/* 309 */           encodingRunnable.mRun = true;
/* 310 */           Thread encodingThread = new Thread(encodingRunnable);
/* 311 */           encodingThread.start();
/* 312 */           while (this.mState.isStarted() && !this.mTerminateImmediately) {
/* 313 */               process(encodingRunnable, true);
                    }
/* 315 */           CrashUtil.log("[ML] Flushing... {}, {}", Boolean.valueOf(this.mState.isStarted()), Boolean.valueOf(this.mTerminateImmediately));
/* 316 */           if (!this.mTerminateImmediately) {
/* 317 */               process(encodingRunnable, false);
                    } else {
/* 319 */               SLog.m446d("Terminate without saving....");
                    }
/* 321 */           this.mByteBufferQueue.endOfPut();
/* 322 */           SLog.m446d("[ML] Waiting for encoding prcess thread to end...");
                    try {
/* 324 */               encodingThread.join();
                    } catch (InterruptedException e) {
/* 326 */               SLog.m455e("Interrupted.");
                    }
/* 328 */           SLog.m446d("[ML] Encoding process thread ended");
/* 329 */           FileUtils.deleteQuietly(new File(this.mEncodingTempPath));
/* 330 */           if (this.mAsyncTask != null) {
/* 331 */               this.mAsyncTask.publish(90);
                    }
/* 333 */           SLog.m453d("Time used: rgb: {}, matrix: {}, encode: {}, n: {}, max: {}.", new Object[]{Long.valueOf(this.timeToRGB), Long.valueOf(this.timeToMatrix), Long.valueOf(this.timeToRecord), Integer.valueOf(this.mTotalProcessed), Integer.valueOf(this.mMaxEncodedSize)});
/* 335 */           this.mLastSegment = null;
/* 336 */           CrashUtil.log("[ML] Processing Ended: {}, {}.", this.mOutput, Boolean.valueOf(this.mTerminateImmediately));
/* 337 */           sRunningInstances--;
                }

/* 341 */       private void process(EncodingProcessRunnable runnable, boolean waitForMore) {
/* 342 */           while (!this.mTerminateImmediately) {
                        VideoData next = this.mDataQueue.poll();
                        if (next != null || this.mState.isStarted()) {
/* 343 */                   if (next == null) {
/* 344 */                       if (waitForMore) {
                                    try {
/* 346 */                               Thread.sleep(100L);
                                    } catch (InterruptedException e) {
/* 348 */                               SLog.m446d("Break signal reached.");
/* 361 */                               return;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
/* 353 */                       if (!next.segment.removed) {
/* 354 */                           runnable.add(next);
                                } else {
/* 356 */                           SLog.m464i("Skipped processing data from deleted segments.");
                                }
/* 358 */                       this.mPreviewCallback.addBuffer(next.data, !waitForMore);
                            }
                        } else {
/* 361 */                   return;
                        }
                    }
                }

/* 363 */       public String getOutput() {
/* 364 */           return this.mOutput;
                }
            }
