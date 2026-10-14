            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.os.Build;
            import android.os.Process;
            import co.vine.android.recorder.buffered.BufferedPreviewManagerCallback;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.lifevibes.mediacoder.LVMediaCoder;
            import java.io.ByteArrayOutputStream;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.util.LinkedList;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class HwEncodingRunnable extends AbstractEncodingRunnable {
                private BaseFinishProcessTask mAsyncTask;
                private final ByteBufferQueue mByteBufferQueue;
                private final Context mContext;
                private final ConcurrentLinkedQueue<VideoData> mDataQueue;
                private HwVineFrameRecorder mEncoder;
                private final EncodingProcessRunnable mEncodingRunnable;
                private final Thread mEncodingThread;
                private Bitmap mMatrixBitmap;
                private Canvas mMatrixCanvas;
                private int mMaxEncodedSize;
                private final String mOutput;
                private final PictureConverter mPictureConverter;
                private final BufferedPreviewManagerCallback mPreviewCallback;
                private final RecordState mProducer;
                private final int mRecorderFrameRate;
                private volatile boolean mTerminateImmediately = false;
                private final Bitmap mThumbnailBitmap;
                private final Canvas mThumbnailCanvas;
                private final Matrix mThumbnailMatrix;
                private int mTotalProcessed;
                private final byte[] mVideoDataBufferMax;
                private int mVideoDataBufferPosition;
                private long timeToMatrix;
                private long timeToRGB;
                private long timeToRecord;

/* 27 */        static /* synthetic */ ByteBufferQueue access$000(HwEncodingRunnable x0) {
/* 28 */            return x0.mByteBufferQueue;
                }

/* 27 */        static /* synthetic */ RecordState access$100(HwEncodingRunnable x0) {
/* 28 */            return x0.mProducer;
                }

/* 27 */        static /* synthetic */ PictureConverter access$1000(HwEncodingRunnable x0) {
/* 28 */            return x0.mPictureConverter;
                }

/* 27 */        static /* synthetic */ long access$1114(HwEncodingRunnable x0, long x1) {
/* 28 */            long j = x0.timeToRGB + x1;
                    x0.timeToRGB = j;
                    return j;
                }

/* 27 */        static /* synthetic */ Canvas access$1200(HwEncodingRunnable x0) {
/* 28 */            return x0.mMatrixCanvas;
                }

/* 27 */        static /* synthetic */ long access$1314(HwEncodingRunnable x0, long x1) {
/* 28 */            long j = x0.timeToMatrix + x1;
                    x0.timeToMatrix = j;
                    return j;
                }

/* 27 */        static /* synthetic */ Bitmap access$1400(HwEncodingRunnable x0) {
/* 28 */            return x0.mMatrixBitmap;
                }

/* 27 */        static /* synthetic */ boolean access$1500(HwEncodingRunnable x0) {
/* 28 */            return x0.mTerminateImmediately;
                }

/* 27 */        static /* synthetic */ boolean access$1502(HwEncodingRunnable x0, boolean x1) {
/* 28 */            x0.mTerminateImmediately = x1;
                    return x1;
                }

/* 27 */        static /* synthetic */ Canvas access$1600(HwEncodingRunnable x0) {
/* 28 */            return x0.mThumbnailCanvas;
                }

/* 27 */        static /* synthetic */ Matrix access$1700(HwEncodingRunnable x0) {
/* 28 */            return x0.mThumbnailMatrix;
                }

/* 27 */        static /* synthetic */ String access$1800(HwEncodingRunnable x0) {
/* 28 */            return x0.mOutput;
                }

/* 27 */        static /* synthetic */ Bitmap access$1900(HwEncodingRunnable x0) {
/* 28 */            return x0.mThumbnailBitmap;
                }

/* 27 */        static /* synthetic */ BaseFinishProcessTask access$200(HwEncodingRunnable x0) {
/* 28 */            return x0.mAsyncTask;
                }

/* 27 */        static /* synthetic */ long access$2014(HwEncodingRunnable x0, long x1) {
/* 28 */            long j = x0.timeToRecord + x1;
                    x0.timeToRecord = j;
                    return j;
                }

/* 27 */        static /* synthetic */ byte[] access$300(HwEncodingRunnable x0) {
/* 28 */            return x0.mVideoDataBufferMax;
                }

/* 27 */        static /* synthetic */ int access$400(HwEncodingRunnable x0) {
/* 28 */            return x0.mVideoDataBufferPosition;
                }

/* 27 */        static /* synthetic */ int access$412(HwEncodingRunnable x0, int x1) {
/* 28 */            int i = x0.mVideoDataBufferPosition + x1;
                    x0.mVideoDataBufferPosition = i;
                    return i;
                }

/* 27 */        static /* synthetic */ HwVineFrameRecorder access$500(HwEncodingRunnable x0) {
/* 28 */            return x0.mEncoder;
                }

/* 27 */        static /* synthetic */ HwVineFrameRecorder access$502(HwEncodingRunnable x0, HwVineFrameRecorder x1) {
/* 28 */            x0.mEncoder = x1;
                    return x1;
                }

/* 27 */        static /* synthetic */ int access$600(HwEncodingRunnable x0) {
/* 28 */            return x0.mTotalProcessed;
                }

/* 27 */        static /* synthetic */ int access$602(HwEncodingRunnable x0, int x1) {
/* 28 */            x0.mTotalProcessed = x1;
                    return x1;
                }

/* 27 */        static /* synthetic */ int access$608(HwEncodingRunnable x0) {
/* 28 */            int i = x0.mTotalProcessed;
                    x0.mTotalProcessed = i + 1;
                    return i;
                }

/* 27 */        static /* synthetic */ int access$700(HwEncodingRunnable x0) {
/* 28 */            return x0.mMaxEncodedSize;
                }

/* 27 */        static /* synthetic */ int access$702(HwEncodingRunnable x0, int x1) {
/* 28 */            x0.mMaxEncodedSize = x1;
                    return x1;
                }

/* 27 */        static /* synthetic */ Context access$800(HwEncodingRunnable x0) {
/* 28 */            return x0.mContext;
                }

/* 27 */        static /* synthetic */ int access$900(HwEncodingRunnable x0) {
/* 28 */            return x0.mRecorderFrameRate;
                }

/* 63 */        public HwEncodingRunnable(Context context, ConcurrentLinkedQueue<VideoData> queue, byte[] videoDataBufferMax, RecordState producer, String output, int frameRate, ByteBuffer buffer, Bitmap bitmap, Bitmap thumbnailBitmap, PictureConverter pc, int videoBufferPosition, BufferedPreviewManagerCallback cb, ByteBufferQueue byteBufferQueue, Matrix thumbnailMatrix, Canvas thumbnailCanvas, Canvas matrixCanvas) {
/* 65 */            this.mContext = context;
/* 66 */            this.mDataQueue = queue;
/* 67 */            this.mProducer = producer;
/* 68 */            this.mMatrixBitmap = bitmap;
/* 69 */            this.mThumbnailBitmap = thumbnailBitmap;
/* 70 */            this.mThumbnailCanvas = thumbnailCanvas;
/* 71 */            this.mMatrixCanvas = matrixCanvas;
/* 72 */            this.mVideoDataBufferMax = videoDataBufferMax;
/* 73 */            this.mVideoDataBufferPosition = videoBufferPosition;
/* 74 */            this.mPictureConverter = pc;
/* 75 */            this.mPreviewCallback = cb;
/* 76 */            this.mRecorderFrameRate = frameRate;
/* 77 */            this.mOutput = output;
/* 78 */            this.mThumbnailMatrix = thumbnailMatrix;
/* 79 */            this.mByteBufferQueue = byteBufferQueue;
/* 80 */            this.mEncodingRunnable = new EncodingProcessRunnable(buffer);
/* 81 */            this.mEncodingThread = new Thread(this.mEncodingRunnable);
/* 82 */            if (!this.mByteBufferQueue.isFresh()) {
/* 83 */                throw new RuntimeException("Invalid buffer queue.");
                    }
                }

/* 98 */        class EncodingProcessRunnable implements Runnable, LVMediaCoder.LVMediaCoderICSEncoderProgressListener {
                    private LinkedList<VideoData> mBufferedVideoData;
                    private ByteBuffer mByteBuffer;
                    private int mErrorCount;
                    private VideoData mLast;
                    private RecordSegment mLastSegment;
                    private int mReceived;
                    public boolean mRun;
                    private boolean notFirst;
                    private final int[] LOCK = new int[0];
                    private int totalSizeToWait = -1;

/* 99 */            public EncodingProcessRunnable(ByteBuffer buffer) {
/* 101 */               this.mByteBuffer = buffer;
/* 102 */               makeNewEncoder();
/* 103 */               this.mBufferedVideoData = new LinkedList<>();
                    }

                    @Override // java.lang.Runnable
/* 107 */           public void run() {
/* 108 */               Process.setThreadPriority(19);
/* 109 */               this.mLastSegment = null;
/* 110 */               SLog.m446d("EncodingProcessRunnable started.");
/* 111 */               while (this.mRun) {
/* 112 */                   ByteBufferQueue.QueueItem data = HwEncodingRunnable.access$000(HwEncodingRunnable.this).get();
/* 113 */                   if (!HwEncodingRunnable.access$100(HwEncodingRunnable.this).isStarted() && HwEncodingRunnable.access$200(HwEncodingRunnable.this) != null) {
/* 115 */                       int size = Math.max(HwEncodingRunnable.access$000(HwEncodingRunnable.this).size(), 1);
/* 116 */                       if (this.totalSizeToWait == -1) {
/* 117 */                           this.totalSizeToWait = size;
/* 118 */                           Process.setThreadPriority(-8);
/* 119 */                           SLog.m465i("Size to wait: {}.", Integer.valueOf(this.totalSizeToWait));
                                }
/* 121 */                       HwEncodingRunnable.access$200(HwEncodingRunnable.this).publish(((this.totalSizeToWait - size) * 90) / this.totalSizeToWait);
                            }
/* 123 */                   if (data != null) {
/* 124 */                       SLog.m447d("Got data. {}", this);
/* 125 */                       VideoData next = (VideoData) data.tag;
/* 126 */                       next.data = data.bytes;
/* 127 */                       if (this.mLast != null) {
                                    try {
/* 129 */                               processVideoData(this.mLast, this.mLast.segment != next.segment);
/* 133 */                               this.mLast = null;
                                    } catch (Exception e) {
/* 131 */                               throw new RuntimeException(e);
                                    }
                                }
/* 135 */                       this.mLast = next;
                            } else if (HwEncodingRunnable.access$000(HwEncodingRunnable.this).isEndOfPut()) {
/* 138 */                       this.mRun = false;
                            } else {
                                try {
/* 141 */                           Thread.sleep(100L);
                                } catch (InterruptedException e2) {
/* 143 */                           SLog.m455e("Interrupted.");
                                }
                            }
                        }
/* 148 */               if (this.mLast != null) {
                            try {
/* 150 */                       processVideoData(this.mLast, true);
/* 154 */                       this.mLast = null;
                            } catch (Exception e3) {
/* 152 */                       throw new RuntimeException(e3);
                            }
                        }
/* 156 */               stopEncoder(0);
                    }

/* 159 */           private boolean doVideoFrameEncoded(ByteBuffer byteBuffer) throws HwVineFrameRecorder.Exception {
/* 160 */               VideoData data = this.mBufferedVideoData.getFirst();
/* 161 */               data.size = HwEncodingRunnable.access$500(HwEncodingRunnable.this).putEncodedData(byteBuffer, HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this));
/* 162 */               return putVideoFrame(data);
                    }

/* 165 */           private boolean doVideoFrameRaw(byte[] image, boolean newSegment, boolean lastFrame) throws HwVineFrameRecorder.Exception {
/* 166 */               if (HwEncodingRunnable.access$500(HwEncodingRunnable.this) == null) {
/* 167 */                   makeNewEncoder();
                        } else {
/* 169 */                   if (newSegment && this.notFirst) {
/* 170 */                       stopEncoder(1);
/* 171 */                       makeNewEncoder();
                            }
/* 173 */                   this.notFirst = true;
                        }
/* 175 */               HwEncodingRunnable.access$608(HwEncodingRunnable.this);
/* 177 */               if (Build.VERSION.SDK_INT < 16) {
/* 178 */                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).encode(ByteBuffer.wrap(image), HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this), lastFrame);
/* 179 */                   return false;
                        }
/* 181 */               VideoData data = this.mBufferedVideoData.getFirst();
/* 182 */               data.size = HwEncodingRunnable.access$500(HwEncodingRunnable.this).encode(ByteBuffer.wrap(image), HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this), lastFrame);
/* 183 */               SLog.m449d("onFrameAvailable Write video frame size {}. Total processed {}, last Frame? {}.", Integer.valueOf(data.size), Integer.valueOf(HwEncodingRunnable.access$600(HwEncodingRunnable.this)), Boolean.valueOf(lastFrame));
/* 184 */               return putVideoFrame(data);
                    }

/* 188 */           private boolean putVideoFrame(VideoData data) {
/* 189 */               if (data.size != -1) {
/* 190 */                   data.start = HwEncodingRunnable.access$400(HwEncodingRunnable.this);
/* 191 */                   HwEncodingRunnable.access$702(HwEncodingRunnable.this, Math.max(HwEncodingRunnable.access$700(HwEncodingRunnable.this), data.size));
/* 192 */                   HwEncodingRunnable.access$412(HwEncodingRunnable.this, data.size);
/* 193 */                   this.mBufferedVideoData.pop();
/* 194 */                   return true;
                        }
/* 196 */               SLog.m446d("******Image was buffered to the next frame.********");
/* 197 */               return false;
                    }

/* 201 */           private void makeNewEncoder() {
/* 202 */               HwEncodingRunnable.access$602(HwEncodingRunnable.this, 0);
/* 203 */               this.mReceived = 0;
/* 204 */               HwEncodingRunnable.access$502(HwEncodingRunnable.this, RecordConfigUtils.newVideoRecorder(HwEncodingRunnable.access$800(HwEncodingRunnable.this), null, HwEncodingRunnable.access$900(HwEncodingRunnable.this)));
/* 205 */               if (Build.VERSION.SDK_INT < 16) {
/* 206 */                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).setDataListener(this);
                        }
                    }

/* 211 */           private void stopEncoder(int maxBufferCount) {
                        try {
/* 212 */                   SLog.m447d("Stopping encoder. Error count: {}.", Integer.valueOf(this.mErrorCount));
/* 213 */                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).stopEncoding();
/* 214 */                   SLog.m448d("Frames left? {}, allowed? {}.", Integer.valueOf(this.mBufferedVideoData.size()), Integer.valueOf(maxBufferCount));
/* 215 */                   while (this.mBufferedVideoData.size() > maxBufferCount) {
                                try {
/* 217 */                           Thread.sleep(10L);
                                } catch (InterruptedException e) {
/* 219 */                           if (SLog.sLogsOn) {
/* 220 */                               e.printStackTrace();
                                    }
                                }
                            }
/* 224 */                   this.mErrorCount = 0;
                        } catch (HwVineFrameRecorder.Exception e2) {
/* 226 */                   SLog.m459e("Error flushing...", (Throwable) e2);
                        }
                    }

/* 230 */           public void add(VideoData next) {
                        synchronized (this.LOCK) {
                            try {
/* 232 */                       SLog.m446d("Added to the queue. " + this);
/* 233 */                       if (!HwEncodingRunnable.access$000(HwEncodingRunnable.this).put(new ByteBufferQueue.QueueItem(next.data, next))) {
/* 234 */                           CrashUtil.log("Not enough memory left. (BufferOverflow)");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 239 */           public byte[] processVideoData(VideoData next, boolean lastFrame) throws HwVineFrameRecorder.Exception, IOException {
/* 240 */               boolean newSegment = this.mLastSegment != next.segment;
/* 241 */               this.mLastSegment = next.segment;
                        synchronized (HwEncodingRunnable.access$1000(HwEncodingRunnable.this).LOCK) {
                            try {
/* 243 */                       long startTime = System.currentTimeMillis();
/* 244 */                       CameraSetting cameraSetting = next.segment.getCameraSetting();
/* 245 */                       boolean hasChanged = HwEncodingRunnable.access$1000(HwEncodingRunnable.this).updateSettingsIfNeeded(cameraSetting);
/* 247 */                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(cameraSetting.degrees, false);
/* 250 */                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).convert(cameraSetting, next, hasChanged);
/* 252 */                       HwEncodingRunnable.access$1114(HwEncodingRunnable.this, System.currentTimeMillis() - startTime);
/* 253 */                       long startTime2 = System.currentTimeMillis();
/* 257 */                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).draw(HwEncodingRunnable.access$1200(HwEncodingRunnable.this));
/* 259 */                       HwEncodingRunnable.access$1314(HwEncodingRunnable.this, System.currentTimeMillis() - startTime2);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 263 */               this.mByteBuffer.clear();
/* 264 */               HwEncodingRunnable.access$1400(HwEncodingRunnable.this).copyPixelsToBuffer(this.mByteBuffer);
/* 266 */               if (newSegment && !HwEncodingRunnable.access$1500(HwEncodingRunnable.this)) {
/* 267 */                   HwEncodingRunnable.access$1000(HwEncodingRunnable.this).drawGeneric(HwEncodingRunnable.access$1600(HwEncodingRunnable.this), HwEncodingRunnable.access$1400(HwEncodingRunnable.this), HwEncodingRunnable.access$1700(HwEncodingRunnable.this));
/* 268 */                   String path = HwEncodingRunnable.access$1800(HwEncodingRunnable.this) + "." + System.currentTimeMillis() + ".jpg";
/* 269 */                   next.segment.setThumbnailPath(path);
/* 270 */                   File f = new File(path);
/* 271 */                   File parentFile = f.getParentFile();
                            try {
/* 273 */                       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 274 */                       HwEncodingRunnable.access$1900(HwEncodingRunnable.this).compress(Bitmap.CompressFormat.JPEG, 100, bos);
/* 275 */                       byte[] compressed = bos.toByteArray();
/* 276 */                       FileOutputStream fos = new FileOutputStream(f);
/* 277 */                       fos.write(compressed);
/* 278 */                       fos.close();
                            } catch (Throwable e) {
/* 280 */                       boolean wasSessionJustDeleted = RecordSessionManager.wasSessionJustDeleted(parentFile);
/* 281 */                       if (wasSessionJustDeleted) {
/* 282 */                           HwEncodingRunnable.access$1502(HwEncodingRunnable.this, true);
/* 283 */                           CrashUtil.logException(e);
                                } else {
/* 285 */                           throw e;
                                }
                            }
                        }
/* 290 */               long startTime3 = System.currentTimeMillis();
/* 291 */               this.mBufferedVideoData.add(next);
/* 292 */               doVideoFrameRaw(this.mByteBuffer.array(), newSegment, lastFrame);
/* 293 */               HwEncodingRunnable.access$2014(HwEncodingRunnable.this, System.currentTimeMillis() - startTime3);
/* 294 */               byte[] data = next.data;
/* 295 */               next.data = null;
/* 296 */               return data;
                    }

                    @Override // com.lifevibes.mediacoder.LVMediaCoder.LVMediaCoderICSEncoderProgressListener
/* 300 */           public void onFrameAvailable(ByteBuffer byteBuffer) {
/* 301 */               this.mReceived++;
/* 302 */               SLog.m448d("Got Frame! Processed: {}, Received: {}.", Integer.valueOf(HwEncodingRunnable.access$600(HwEncodingRunnable.this)), Integer.valueOf(this.mReceived));
/* 303 */               doVideoFrameEncoded(byteBuffer);
                    }

                    @Override // com.lifevibes.mediacoder.LVMediaCoder.LVMediaCoderICSEncoderProgressListener
/* 308 */           public void onComplete() {
                    }

                    @Override // com.lifevibes.mediacoder.LVMediaCoder.LVMediaCoderICSEncoderProgressListener
/* 312 */           public void onError(Exception e, int code) {
/* 313 */               SLog.m451d("Encoding error: {}.", (Object) Integer.valueOf(code), (Throwable) e);
/* 314 */               this.mErrorCount++;
                    }
                }

                @Override // co.vine.android.recorder.AbstractEncodingRunnable
/* 318 */       public void setAsyncTask(BaseFinishProcessTask task) {
/* 319 */           this.mAsyncTask = task;
                }

                @Override // co.vine.android.recorder.AbstractEncodingRunnable
/* 322 */       public void terminate() {
/* 323 */           SLog.m446d("Terminate without saving turned on.");
/* 324 */           this.mTerminateImmediately = true;
                }

                @Override // java.lang.Runnable
/* 328 */       public void run() {
/* 329 */           Process.setThreadPriority(19);
/* 330 */           CrashUtil.log("Processing started: {}.", this.mOutput);
/* 331 */           this.mEncodingRunnable.mRun = true;
/* 332 */           this.mEncodingThread.start();
/* 333 */           while (this.mProducer.isStarted() && !this.mTerminateImmediately) {
/* 334 */               process(true);
                    }
/* 336 */           SLog.m446d("Flushing...");
/* 337 */           if (!this.mTerminateImmediately) {
/* 338 */               process(false);
                    } else {
/* 340 */               SLog.m446d("Terminate without saving....");
                    }
/* 342 */           this.mByteBufferQueue.endOfPut();
                    try {
/* 344 */               this.mEncodingThread.join();
                    } catch (InterruptedException e) {
/* 346 */               SLog.m455e("Interrupted.");
                    }
/* 348 */           if (this.mAsyncTask != null) {
/* 349 */               this.mAsyncTask.publish(90);
                    }
/* 351 */           SLog.m442b("Time used: rgb: {}ms", Long.valueOf(this.timeToRGB));
/* 352 */           SLog.m442b("Time used: matrix: {}ms.", Long.valueOf(this.timeToMatrix));
/* 353 */           SLog.m442b("Total Processed: {}.", Integer.valueOf(this.mTotalProcessed));
/* 354 */           SLog.m442b("Total Encoding time: {}ms.", Long.valueOf(this.timeToRecord));
/* 355 */           CrashUtil.log("Processing Ended: {}.", this.mOutput);
                }

/* 359 */       private void process(boolean waitForMore) {
/* 360 */           while (!this.mTerminateImmediately) {
                        VideoData next = this.mDataQueue.poll();
                        if (next != null || this.mProducer.isStarted()) {
/* 361 */                   if (next == null) {
/* 362 */                       if (waitForMore) {
                                    try {
/* 364 */                               Thread.sleep(100L);
                                    } catch (InterruptedException e) {
/* 366 */                               SLog.m446d("Break signal reached.");
/* 379 */                               return;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
/* 371 */                       if (!next.segment.removed) {
/* 372 */                           this.mEncodingRunnable.add(next);
                                } else {
/* 374 */                           SLog.m464i("Skipped processing data from deleted segments.");
                                }
/* 376 */                       this.mPreviewCallback.addBuffer(next.data, !waitForMore);
                            }
                        } else {
/* 379 */                   return;
                        }
                    }
                }

/* 381 */       public String getOutput() {
/* 382 */           return this.mOutput;
                }
            }
