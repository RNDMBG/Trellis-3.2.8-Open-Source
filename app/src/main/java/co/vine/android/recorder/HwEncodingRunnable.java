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
            
            
    public class HwEncodingRunnable extends AbstractEncodingRunnable {
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

        static  ByteBufferQueue access$000(HwEncodingRunnable x0) {
            return x0.mByteBufferQueue;
                }

        static  RecordState access$100(HwEncodingRunnable x0) {
            return x0.mProducer;
                }

        static  PictureConverter access$1000(HwEncodingRunnable x0) {
            return x0.mPictureConverter;
                }

        static  long access$1114(HwEncodingRunnable x0, long x1) {
            long j = x0.timeToRGB + x1;
                    x0.timeToRGB = j;
                    return j;
                }

        static  Canvas access$1200(HwEncodingRunnable x0) {
            return x0.mMatrixCanvas;
                }

        static  long access$1314(HwEncodingRunnable x0, long x1) {
            long j = x0.timeToMatrix + x1;
                    x0.timeToMatrix = j;
                    return j;
                }

        static  Bitmap access$1400(HwEncodingRunnable x0) {
            return x0.mMatrixBitmap;
                }

        static  boolean access$1500(HwEncodingRunnable x0) {
            return x0.mTerminateImmediately;
                }

        static  boolean access$1502(HwEncodingRunnable x0, boolean x1) {
            x0.mTerminateImmediately = x1;
                    return x1;
                }

        static  Canvas access$1600(HwEncodingRunnable x0) {
            return x0.mThumbnailCanvas;
                }

        static  Matrix access$1700(HwEncodingRunnable x0) {
            return x0.mThumbnailMatrix;
                }

        static  String access$1800(HwEncodingRunnable x0) {
            return x0.mOutput;
                }

        static  Bitmap access$1900(HwEncodingRunnable x0) {
            return x0.mThumbnailBitmap;
                }

        static  BaseFinishProcessTask access$200(HwEncodingRunnable x0) {
            return x0.mAsyncTask;
                }

        static  long access$2014(HwEncodingRunnable x0, long x1) {
            long j = x0.timeToRecord + x1;
                    x0.timeToRecord = j;
                    return j;
                }

        static  byte[] access$300(HwEncodingRunnable x0) {
            return x0.mVideoDataBufferMax;
                }

        static  int access$400(HwEncodingRunnable x0) {
            return x0.mVideoDataBufferPosition;
                }

        static  int access$412(HwEncodingRunnable x0, int x1) {
            int i = x0.mVideoDataBufferPosition + x1;
                    x0.mVideoDataBufferPosition = i;
                    return i;
                }

        static  HwVineFrameRecorder access$500(HwEncodingRunnable x0) {
            return x0.mEncoder;
                }

        static  HwVineFrameRecorder access$502(HwEncodingRunnable x0, HwVineFrameRecorder x1) {
            x0.mEncoder = x1;
                    return x1;
                }

        static  int access$600(HwEncodingRunnable x0) {
            return x0.mTotalProcessed;
                }

        static  int access$602(HwEncodingRunnable x0, int x1) {
            x0.mTotalProcessed = x1;
                    return x1;
                }

        static  int access$608(HwEncodingRunnable x0) {
            int i = x0.mTotalProcessed;
                    x0.mTotalProcessed = i + 1;
                    return i;
                }

        static  int access$700(HwEncodingRunnable x0) {
            return x0.mMaxEncodedSize;
                }

        static  int access$702(HwEncodingRunnable x0, int x1) {
            x0.mMaxEncodedSize = x1;
                    return x1;
                }

        static  Context access$800(HwEncodingRunnable x0) {
            return x0.mContext;
                }

        static  int access$900(HwEncodingRunnable x0) {
            return x0.mRecorderFrameRate;
                }

        public HwEncodingRunnable(Context context, ConcurrentLinkedQueue<VideoData> queue, byte[] videoDataBufferMax, RecordState producer, String output, int frameRate, ByteBuffer buffer, Bitmap bitmap, Bitmap thumbnailBitmap, PictureConverter pc, int videoBufferPosition, BufferedPreviewManagerCallback cb, ByteBufferQueue byteBufferQueue, Matrix thumbnailMatrix, Canvas thumbnailCanvas, Canvas matrixCanvas) {
            this.mContext = context;
            this.mDataQueue = queue;
            this.mProducer = producer;
            this.mMatrixBitmap = bitmap;
            this.mThumbnailBitmap = thumbnailBitmap;
            this.mThumbnailCanvas = thumbnailCanvas;
            this.mMatrixCanvas = matrixCanvas;
            this.mVideoDataBufferMax = videoDataBufferMax;
            this.mVideoDataBufferPosition = videoBufferPosition;
            this.mPictureConverter = pc;
            this.mPreviewCallback = cb;
            this.mRecorderFrameRate = frameRate;
            this.mOutput = output;
            this.mThumbnailMatrix = thumbnailMatrix;
            this.mByteBufferQueue = byteBufferQueue;
            this.mEncodingRunnable = new EncodingProcessRunnable(buffer);
            this.mEncodingThread = new Thread(this.mEncodingRunnable);
            if (!this.mByteBufferQueue.isFresh()) {
                throw new RuntimeException("Invalid buffer queue.");
                    }
                }

        class EncodingProcessRunnable implements Runnable, LVMediaCoder.LVMediaCoderICSEncoderProgressListener {
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

            public EncodingProcessRunnable(ByteBuffer buffer) {
               this.mByteBuffer = buffer;
               makeNewEncoder();
               this.mBufferedVideoData = new LinkedList<>();
                    }

                    @Override 
           public void run() {
               Process.setThreadPriority(19);
               this.mLastSegment = null;
               SLog.m446d("EncodingProcessRunnable started.");
               while (this.mRun) {
                   ByteBufferQueue.QueueItem data = HwEncodingRunnable.access$000(HwEncodingRunnable.this).get();
                   if (!HwEncodingRunnable.access$100(HwEncodingRunnable.this).isStarted() && HwEncodingRunnable.access$200(HwEncodingRunnable.this) != null) {
                       int size = Math.max(HwEncodingRunnable.access$000(HwEncodingRunnable.this).size(), 1);
                       if (this.totalSizeToWait == -1) {
                           this.totalSizeToWait = size;
                           Process.setThreadPriority(-8);
                           SLog.m465i("Size to wait: {}.", Integer.valueOf(this.totalSizeToWait));
                                }
                       HwEncodingRunnable.access$200(HwEncodingRunnable.this).publish(((this.totalSizeToWait - size) * 90) / this.totalSizeToWait);
                            }
                   if (data != null) {
                       SLog.m447d("Got data. {}", this);
                       VideoData next = (VideoData) data.tag;
                       next.data = data.bytes;
                       if (this.mLast != null) {
                                    try {
                               processVideoData(this.mLast, this.mLast.segment != next.segment);
                               this.mLast = null;
                                    } catch (Exception e) {
                               throw new RuntimeException(e);
                                    }
                                }
                       this.mLast = next;
                            } else if (HwEncodingRunnable.access$000(HwEncodingRunnable.this).isEndOfPut()) {
                       this.mRun = false;
                            } else {
                                try {
                           Thread.sleep(100L);
                                } catch (InterruptedException e2) {
                           SLog.m455e("Interrupted.");
                                }
                            }
                        }
               if (this.mLast != null) {
                            try {
                       processVideoData(this.mLast, true);
                       this.mLast = null;
                            } catch (Exception e3) {
                       throw new RuntimeException(e3);
                            }
                        }
               stopEncoder(0);
                    }

           private boolean doVideoFrameEncoded(ByteBuffer byteBuffer) throws HwVineFrameRecorder.Exception {
               VideoData data = this.mBufferedVideoData.getFirst();
               data.size = HwEncodingRunnable.access$500(HwEncodingRunnable.this).putEncodedData(byteBuffer, HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this));
               return putVideoFrame(data);
                    }

           private boolean doVideoFrameRaw(byte[] image, boolean newSegment, boolean lastFrame) throws HwVineFrameRecorder.Exception {
               if (HwEncodingRunnable.access$500(HwEncodingRunnable.this) == null) {
                   makeNewEncoder();
                        } else {
                   if (newSegment && this.notFirst) {
                       stopEncoder(1);
                       makeNewEncoder();
                            }
                   this.notFirst = true;
                        }
               HwEncodingRunnable.access$608(HwEncodingRunnable.this);
               if (Build.VERSION.SDK_INT < 16) {
                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).encode(ByteBuffer.wrap(image), HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this), lastFrame);
                   return false;
                        }
               VideoData data = this.mBufferedVideoData.getFirst();
               data.size = HwEncodingRunnable.access$500(HwEncodingRunnable.this).encode(ByteBuffer.wrap(image), HwEncodingRunnable.access$300(HwEncodingRunnable.this), HwEncodingRunnable.access$400(HwEncodingRunnable.this), lastFrame);
               SLog.m449d("onFrameAvailable Write video frame size {}. Total processed {}, last Frame? {}.", Integer.valueOf(data.size), Integer.valueOf(HwEncodingRunnable.access$600(HwEncodingRunnable.this)), Boolean.valueOf(lastFrame));
               return putVideoFrame(data);
                    }

           private boolean putVideoFrame(VideoData data) {
               if (data.size != -1) {
                   data.start = HwEncodingRunnable.access$400(HwEncodingRunnable.this);
                   HwEncodingRunnable.access$702(HwEncodingRunnable.this, Math.max(HwEncodingRunnable.access$700(HwEncodingRunnable.this), data.size));
                   HwEncodingRunnable.access$412(HwEncodingRunnable.this, data.size);
                   this.mBufferedVideoData.pop();
                   return true;
                        }
               SLog.m446d("******Image was buffered to the next frame.********");
               return false;
                    }

           private void makeNewEncoder() {
               HwEncodingRunnable.access$602(HwEncodingRunnable.this, 0);
               this.mReceived = 0;
               HwEncodingRunnable.access$502(HwEncodingRunnable.this, RecordConfigUtils.newVideoRecorder(HwEncodingRunnable.access$800(HwEncodingRunnable.this), null, HwEncodingRunnable.access$900(HwEncodingRunnable.this)));
               if (Build.VERSION.SDK_INT < 16) {
                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).setDataListener(this);
                        }
                    }

           private void stopEncoder(int maxBufferCount) {
                        try {
                   SLog.m447d("Stopping encoder. Error count: {}.", Integer.valueOf(this.mErrorCount));
                   HwEncodingRunnable.access$500(HwEncodingRunnable.this).stopEncoding();
                   SLog.m448d("Frames left? {}, allowed? {}.", Integer.valueOf(this.mBufferedVideoData.size()), Integer.valueOf(maxBufferCount));
                   while (this.mBufferedVideoData.size() > maxBufferCount) {
                                try {
                           Thread.sleep(10L);
                                } catch (InterruptedException e) {
                           if (SLog.sLogsOn) {
                               e.printStackTrace();
                                    }
                                }
                            }
                   this.mErrorCount = 0;
                        } catch (HwVineFrameRecorder.Exception e2) {
                   SLog.m459e("Error flushing...", (Throwable) e2);
                        }
                    }

           public void add(VideoData next) {
                        synchronized (this.LOCK) {
                            try {
                       SLog.m446d("Added to the queue. " + this);
                       if (!HwEncodingRunnable.access$000(HwEncodingRunnable.this).put(new ByteBufferQueue.QueueItem(next.data, next))) {
                           CrashUtil.log("Not enough memory left. (BufferOverflow)");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           public byte[] processVideoData(VideoData next, boolean lastFrame) throws HwVineFrameRecorder.Exception, IOException {
               boolean newSegment = this.mLastSegment != next.segment;
               this.mLastSegment = next.segment;
                        synchronized (HwEncodingRunnable.access$1000(HwEncodingRunnable.this).LOCK) {
                            try {
                       long startTime = System.currentTimeMillis();
                       CameraSetting cameraSetting = next.segment.getCameraSetting();
                       boolean hasChanged = HwEncodingRunnable.access$1000(HwEncodingRunnable.this).updateSettingsIfNeeded(cameraSetting);
                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(cameraSetting.degrees, false);
                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).convert(cameraSetting, next, hasChanged);
                       HwEncodingRunnable.access$1114(HwEncodingRunnable.this, System.currentTimeMillis() - startTime);
                       long startTime2 = System.currentTimeMillis();
                       HwEncodingRunnable.access$1000(HwEncodingRunnable.this).draw(HwEncodingRunnable.access$1200(HwEncodingRunnable.this));
                       HwEncodingRunnable.access$1314(HwEncodingRunnable.this, System.currentTimeMillis() - startTime2);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               this.mByteBuffer.clear();
               HwEncodingRunnable.access$1400(HwEncodingRunnable.this).copyPixelsToBuffer(this.mByteBuffer);
               if (newSegment && !HwEncodingRunnable.access$1500(HwEncodingRunnable.this)) {
                   HwEncodingRunnable.access$1000(HwEncodingRunnable.this).drawGeneric(HwEncodingRunnable.access$1600(HwEncodingRunnable.this), HwEncodingRunnable.access$1400(HwEncodingRunnable.this), HwEncodingRunnable.access$1700(HwEncodingRunnable.this));
                   String path = HwEncodingRunnable.access$1800(HwEncodingRunnable.this) + "." + System.currentTimeMillis() + ".jpg";
                   next.segment.setThumbnailPath(path);
                   File f = new File(path);
                   File parentFile = f.getParentFile();
                            try {
                       ByteArrayOutputStream bos = new ByteArrayOutputStream();
                       HwEncodingRunnable.access$1900(HwEncodingRunnable.this).compress(Bitmap.CompressFormat.JPEG, 100, bos);
                       byte[] compressed = bos.toByteArray();
                       FileOutputStream fos = new FileOutputStream(f);
                       fos.write(compressed);
                       fos.close();
                            } catch (Throwable e) {
                       boolean wasSessionJustDeleted = RecordSessionManager.wasSessionJustDeleted(parentFile);
                       if (wasSessionJustDeleted) {
                           HwEncodingRunnable.access$1502(HwEncodingRunnable.this, true);
                           CrashUtil.logException(e);
                                } else {
                           throw e;
                                }
                            }
                        }
               long startTime3 = System.currentTimeMillis();
               this.mBufferedVideoData.add(next);
               doVideoFrameRaw(this.mByteBuffer.array(), newSegment, lastFrame);
               HwEncodingRunnable.access$2014(HwEncodingRunnable.this, System.currentTimeMillis() - startTime3);
               byte[] data = next.data;
               next.data = null;
               return data;
                    }

                    @Override 
           public void onFrameAvailable(ByteBuffer byteBuffer) {
               this.mReceived++;
               SLog.m448d("Got Frame! Processed: {}, Received: {}.", Integer.valueOf(HwEncodingRunnable.access$600(HwEncodingRunnable.this)), Integer.valueOf(this.mReceived));
               doVideoFrameEncoded(byteBuffer);
                    }

                    @Override 
           public void onComplete() {
                    }

                    @Override 
           public void onError(Exception e, int code) {
               SLog.m451d("Encoding error: {}.", (Object) Integer.valueOf(code), (Throwable) e);
               this.mErrorCount++;
                    }
                }

                @Override 
       public void setAsyncTask(BaseFinishProcessTask task) {
           this.mAsyncTask = task;
                }

                @Override 
       public void terminate() {
           SLog.m446d("Terminate without saving turned on.");
           this.mTerminateImmediately = true;
                }

                @Override 
       public void run() {
           Process.setThreadPriority(19);
           CrashUtil.log("Processing started: {}.", this.mOutput);
           this.mEncodingRunnable.mRun = true;
           this.mEncodingThread.start();
           while (this.mProducer.isStarted() && !this.mTerminateImmediately) {
               process(true);
                    }
           SLog.m446d("Flushing...");
           if (!this.mTerminateImmediately) {
               process(false);
                    } else {
               SLog.m446d("Terminate without saving....");
                    }
           this.mByteBufferQueue.endOfPut();
                    try {
               this.mEncodingThread.join();
                    } catch (InterruptedException e) {
               SLog.m455e("Interrupted.");
                    }
           if (this.mAsyncTask != null) {
               this.mAsyncTask.publish(90);
                    }
           SLog.m442b("Time used: rgb: {}ms", Long.valueOf(this.timeToRGB));
           SLog.m442b("Time used: matrix: {}ms.", Long.valueOf(this.timeToMatrix));
           SLog.m442b("Total Processed: {}.", Integer.valueOf(this.mTotalProcessed));
           SLog.m442b("Total Encoding time: {}ms.", Long.valueOf(this.timeToRecord));
           CrashUtil.log("Processing Ended: {}.", this.mOutput);
                }

       private void process(boolean waitForMore) {
           while (!this.mTerminateImmediately) {
                        VideoData next = this.mDataQueue.poll();
                        if (next != null || this.mProducer.isStarted()) {
                   if (next == null) {
                       if (waitForMore) {
                                    try {
                               Thread.sleep(100L);
                                    } catch (InterruptedException e) {
                               SLog.m446d("Break signal reached.");
                               return;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                       if (!next.segment.removed) {
                           this.mEncodingRunnable.add(next);
                                } else {
                           SLog.m464i("Skipped processing data from deleted segments.");
                                }
                       this.mPreviewCallback.addBuffer(next.data, !waitForMore);
                            }
                        } else {
                   return;
                        }
                    }
                }

       public String getOutput() {
           return this.mOutput;
                }
            }
