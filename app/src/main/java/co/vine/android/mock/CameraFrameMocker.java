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
            
            
    public class CameraFrameMocker {
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

        static  int access$1000(CameraFrameMocker x0) {
            return x0.mFrameTime;
                }

        static  boolean access$200(CameraFrameMocker x0) {
            return x0.mIsStarted;
                }

        static  boolean access$300(CameraFrameMocker x0) {
            return x0.mIsResumed;
                }

        static  MockPictureArtist access$400(CameraFrameMocker x0) {
            return x0.mArtist;
                }

        static  int access$508(CameraFrameMocker x0) {
            int i = x0.mCount;
                    x0.mCount = i + 1;
                    return i;
                }

        static  ConcurrentLinkedQueue access$600(CameraFrameMocker x0) {
            return x0.mBufferQueue;
                }

        static  Bitmap access$700(CameraFrameMocker x0) {
            return x0.mBitmap;
                }

        static  FrameReceiver access$800(CameraFrameMocker x0) {
            return x0.mFrameReceiver;
                }

        static  Handler access$900(CameraFrameMocker x0) {
            return x0.mHandler;
                }

        public enum ImageFormat {
                    RGB_565(Bitmap.Config.RGB_565),
                    ARGB_8888(Bitmap.Config.ARGB_8888),
                    NV21(null);

                    private final Bitmap.Config mConfig;

            static  Bitmap.Config access$000(ImageFormat x0) {
                return x0.mConfig;
                    }

            ImageFormat(Bitmap.Config config) {
                this.mConfig = config;
                    }
                }

        public CameraFrameMocker(ImageFormat imageFormat, int width, int height, int frameRate) {
            if (ImageFormat.access$000(imageFormat) == null) {
                throw new UnsupportedOperationException("Image format not supported.");
                    }
            this.mWidth = width;
            this.mHeight = height;
            this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, ImageFormat.access$000(imageFormat));
            this.mCanvas = new Canvas(this.mBitmap);
            this.mFrameTime = 1000 / frameRate;
            this.mBufferQueue = new ConcurrentLinkedQueue<>();
                }

        public Bitmap getBitmap() {
            return this.mBitmap;
                }

        public Canvas getCanvas() {
            return this.mCanvas;
                }

        public void addBuffer(ByteBuffer byteBuffer) {
            if (byteBuffer.remaining() < this.mBitmap.getByteCount()) {
                throw new IllegalArgumentException("Invalid buffer added");
                    }
            this.mBufferQueue.add(byteBuffer);
                }

        public void setup(@NonNull MockPictureArtist artist, @NonNull FrameReceiver frameReceiver) {
            this.mArtist = artist;
            this.mArtist.setupVideo(this.mCanvas, this.mWidth, this.mHeight);
            this.mFrameReceiver = frameReceiver;
                }

        public void start() {
            SLog.m446d("Started.");
            if (this.mArtist == null || this.mFrameReceiver == null) {
                throw new IllegalStateException("You must setup first with non-null arguments.");
                    }
            this.mCount = 0;
            this.mIsStarted = true;
            this.mIsResumed = true;
            this.mPostingThread = new HandlerThread("CameraMockPoster");
            this.mPostingThread.start();
            this.mHandler = new Handler(this.mPostingThread.getLooper());
            this.mDrawingThread = new Thread(new DrawingThread(this, null), "CameraMockDrawer");
            this.mDrawingThread.start();
                }

       public int getDrewCount() {
           return this.mCount;
                }

       private class DrawingThread extends Thread {
           private DrawingThread() {
                    }

            DrawingThread(CameraFrameMocker x0, Object x1) {
               this();
                    }

                    @Override 
           public void run() {
               while (CameraFrameMocker.access$200(CameraFrameMocker.this)) {
                   long startTime = System.currentTimeMillis();
                   if (CameraFrameMocker.access$300(CameraFrameMocker.this)) {
                       CameraFrameMocker.access$400(CameraFrameMocker.this).draw(startTime);
                       CameraFrameMocker.access$508(CameraFrameMocker.this);
                       ByteBuffer buffer = (ByteBuffer) CameraFrameMocker.access$600(CameraFrameMocker.this).poll();
                       if (buffer != null) {
                           int position = buffer.position();
                           CameraFrameMocker.access$700(CameraFrameMocker.this).copyPixelsToBuffer(buffer);
                           buffer.position(position);
                           CameraFrameMocker.access$900(CameraFrameMocker.this).post(new RunnableC03871(startTime, buffer));
                                } else {
                           SLog.m455e("Frame dropped because there are no buffer availiable.");
                                }
                            }
                   long endTime = System.currentTimeMillis();
                   long time = endTime - startTime;
                   SLog.m447d("Camera Loop took {}ms", Long.valueOf(time));
                            if (time < CameraFrameMocker.access$1000(CameraFrameMocker.this)) {
                                try {
                           Thread.sleep(((long) CameraFrameMocker.access$1000(CameraFrameMocker.this)) - time);
                                } catch (InterruptedException e) {
                           SLog.m446d("Interrupted");
                                }
                            }
                        }
                    }

                    
           class RunnableC03871 implements Runnable {
                        final  ByteBuffer val$buffer;
                        final  long val$startTime;

               RunnableC03871(long j, ByteBuffer byteBuffer) {
                            this.val$startTime = j;
                            this.val$buffer = byteBuffer;
                        }

                        @Override 
               public void run() {
                   CameraFrameMocker.access$800(CameraFrameMocker.this).onFrameReceived(this.val$startTime, this.val$buffer);
                        }
                    }
                }

       public void resume() {
           SLog.m446d("Resumed.");
           this.mIsResumed = true;
                }

       public void pause() {
           SLog.m446d("Paused.");
           this.mIsResumed = false;
                }

       public void stop() {
           SLog.m446d("Stopped.");
           this.mIsStarted = false;
                    try {
               this.mDrawingThread.join();
                    } catch (InterruptedException e) {
               this.mDrawingThread.interrupt();
                    }
           this.mPostingThread.quit();
                }
            }
