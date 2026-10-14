            package co.vine.android.mock;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.support.annotation.NonNull;
            import co.vine.android.mock.artists.MockPictureArtist;
            import com.edisonwang.android.slog.SLog;
            import java.nio.ByteBuffer;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class CameraFrameMocker {
                private MockPictureArtist mArtist;
                private final Bitmap mBitmap;
                private ConcurrentLinkedQueue<ByteBuffer> mBufferQueue;
                private final Canvas mCanvas;
                private int mCount;
                private Thread mDrawingThread;
                private FrameReceiver mFrameReceiver;
                private final int mFrameTime;
                private Handler mHandler;
                private final int mHeight;
                private boolean mIsResumed;
                private boolean mIsStarted;
                private HandlerThread mPostingThread;
                private final int mWidth;

                public interface FrameReceiver {
                    void onFrameReceived(long j, ByteBuffer byteBuffer);
                }

/* 18 */        static /* synthetic */ int access$1000(CameraFrameMocker x0) {
/* 19 */            return x0.mFrameTime;
                }

/* 18 */        static /* synthetic */ boolean access$200(CameraFrameMocker x0) {
/* 19 */            return x0.mIsStarted;
                }

/* 18 */        static /* synthetic */ boolean access$300(CameraFrameMocker x0) {
/* 19 */            return x0.mIsResumed;
                }

/* 18 */        static /* synthetic */ MockPictureArtist access$400(CameraFrameMocker x0) {
/* 19 */            return x0.mArtist;
                }

/* 18 */        static /* synthetic */ int access$508(CameraFrameMocker x0) {
/* 19 */            int i = x0.mCount;
                    x0.mCount = i + 1;
                    return i;
                }

/* 18 */        static /* synthetic */ ConcurrentLinkedQueue access$600(CameraFrameMocker x0) {
/* 19 */            return x0.mBufferQueue;
                }

/* 18 */        static /* synthetic */ Bitmap access$700(CameraFrameMocker x0) {
/* 19 */            return x0.mBitmap;
                }

/* 18 */        static /* synthetic */ FrameReceiver access$800(CameraFrameMocker x0) {
/* 19 */            return x0.mFrameReceiver;
                }

/* 18 */        static /* synthetic */ Handler access$900(CameraFrameMocker x0) {
/* 19 */            return x0.mHandler;
                }

/* 38 */        public enum ImageFormat {
                    RGB_565(Bitmap.Config.RGB_565),
                    ARGB_8888(Bitmap.Config.ARGB_8888),
                    NV21(null);

                    private final Bitmap.Config mConfig;

/* 39 */            static /* synthetic */ Bitmap.Config access$000(ImageFormat x0) {
/* 40 */                return x0.mConfig;
                    }

/* 46 */            ImageFormat(Bitmap.Config config) {
/* 48 */                this.mConfig = config;
                    }
                }

/* 51 */        public CameraFrameMocker(ImageFormat imageFormat, int width, int height, int frameRate) {
/* 53 */            if (ImageFormat.access$000(imageFormat) == null) {
/* 54 */                throw new UnsupportedOperationException("Image format not supported.");
                    }
/* 56 */            this.mWidth = width;
/* 57 */            this.mHeight = height;
/* 58 */            this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, ImageFormat.access$000(imageFormat));
/* 59 */            this.mCanvas = new Canvas(this.mBitmap);
/* 60 */            this.mFrameTime = 1000 / frameRate;
/* 61 */            this.mBufferQueue = new ConcurrentLinkedQueue<>();
                }

/* 64 */        public Bitmap getBitmap() {
/* 65 */            return this.mBitmap;
                }

/* 68 */        public Canvas getCanvas() {
/* 69 */            return this.mCanvas;
                }

/* 72 */        public void addBuffer(ByteBuffer byteBuffer) {
/* 73 */            if (byteBuffer.remaining() < this.mBitmap.getByteCount()) {
/* 74 */                throw new IllegalArgumentException("Invalid buffer added");
                    }
/* 76 */            this.mBufferQueue.add(byteBuffer);
                }

/* 79 */        public void setup(@NonNull MockPictureArtist artist, @NonNull FrameReceiver frameReceiver) {
/* 80 */            this.mArtist = artist;
/* 81 */            this.mArtist.setupVideo(this.mCanvas, this.mWidth, this.mHeight);
/* 82 */            this.mFrameReceiver = frameReceiver;
                }

/* 85 */        public void start() {
/* 86 */            SLog.m446d("Started.");
/* 87 */            if (this.mArtist == null || this.mFrameReceiver == null) {
/* 88 */                throw new IllegalStateException("You must setup first with non-null arguments.");
                    }
/* 90 */            this.mCount = 0;
/* 91 */            this.mIsStarted = true;
/* 92 */            this.mIsResumed = true;
/* 93 */            this.mPostingThread = new HandlerThread("CameraMockPoster");
/* 94 */            this.mPostingThread.start();
/* 95 */            this.mHandler = new Handler(this.mPostingThread.getLooper());
/* 96 */            this.mDrawingThread = new Thread(new DrawingThread(this, null), "CameraMockDrawer");
/* 97 */            this.mDrawingThread.start();
                }

/* 100 */       public int getDrewCount() {
/* 101 */           return this.mCount;
                }

/* 102 */       private class DrawingThread extends Thread {
/* 103 */           private DrawingThread() {
                    }

/* 103 */           /* synthetic */ DrawingThread(CameraFrameMocker x0, Object x1) {
/* 104 */               this();
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 106 */           public void run() {
/* 107 */               while (CameraFrameMocker.access$200(CameraFrameMocker.this)) {
/* 108 */                   long startTime = System.currentTimeMillis();
/* 110 */                   if (CameraFrameMocker.access$300(CameraFrameMocker.this)) {
/* 111 */                       CameraFrameMocker.access$400(CameraFrameMocker.this).draw(startTime);
/* 112 */                       CameraFrameMocker.access$508(CameraFrameMocker.this);
/* 113 */                       ByteBuffer buffer = (ByteBuffer) CameraFrameMocker.access$600(CameraFrameMocker.this).poll();
/* 114 */                       if (buffer != null) {
/* 115 */                           int position = buffer.position();
/* 116 */                           CameraFrameMocker.access$700(CameraFrameMocker.this).copyPixelsToBuffer(buffer);
/* 117 */                           buffer.position(position);
/* 118 */                           CameraFrameMocker.access$900(CameraFrameMocker.this).post(new RunnableC03871(startTime, buffer));
                                } else {
/* 125 */                           SLog.m455e("Frame dropped because there are no buffer availiable.");
                                }
                            }
/* 128 */                   long endTime = System.currentTimeMillis();
/* 129 */                   long time = endTime - startTime;
/* 130 */                   SLog.m447d("Camera Loop took {}ms", Long.valueOf(time));
                            if (time < CameraFrameMocker.access$1000(CameraFrameMocker.this)) {
                                try {
/* 133 */                           Thread.sleep(((long) CameraFrameMocker.access$1000(CameraFrameMocker.this)) - time);
                                } catch (InterruptedException e) {
/* 135 */                           SLog.m446d("Interrupted");
                                }
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.mock.CameraFrameMocker$DrawingThread$1 */
/* 116 */           class RunnableC03871 implements Runnable {
                        final /* synthetic */ ByteBuffer val$buffer;
                        final /* synthetic */ long val$startTime;

/* 117 */               RunnableC03871(long j, ByteBuffer byteBuffer) {
                            this.val$startTime = j;
                            this.val$buffer = byteBuffer;
                        }

                        @Override // java.lang.Runnable
/* 120 */               public void run() {
/* 121 */                   CameraFrameMocker.access$800(CameraFrameMocker.this).onFrameReceived(this.val$startTime, this.val$buffer);
                        }
                    }
                }

/* 142 */       public void resume() {
/* 143 */           SLog.m446d("Resumed.");
/* 144 */           this.mIsResumed = true;
                }

/* 147 */       public void pause() {
/* 148 */           SLog.m446d("Paused.");
/* 149 */           this.mIsResumed = false;
                }

/* 152 */       public void stop() {
/* 153 */           SLog.m446d("Stopped.");
/* 154 */           this.mIsStarted = false;
                    try {
/* 156 */               this.mDrawingThread.join();
                    } catch (InterruptedException e) {
/* 158 */               this.mDrawingThread.interrupt();
                    }
/* 160 */           this.mPostingThread.quit();
                }
            }
