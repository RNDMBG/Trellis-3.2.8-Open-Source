            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Point;
            import android.graphics.SurfaceTexture;
            import android.opengl.GLES20;
            import co.vine.android.recorder.video.VideoData;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.opencv_core;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.util.ArrayList;
            import java.util.concurrent.LinkedBlockingDeque;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class TranscodingCodecOutputSurface extends CodecOutputSurface implements SurfaceTexture.OnFrameAvailableListener {
                protected final Canvas mBitmapCanvas;
                private Point mCropOrigin;
                private LinkedBlockingDeque<VideoFrame> mEncodingIplQueue;
                private final EncodingThread mEncodingThread;
                private boolean mHasGeneratedThumbnail;
                private Matrix mMatrix;
                protected Bitmap mOutputBitmap;
                private ByteBuffer mPixelBuf;
                protected Bitmap mScaledCroppedBitmap;
                private LinkedBlockingDeque<opencv_core.IplImage> mSourceIplQueue;
                private final String mThumbnailPath;

/* 34 */        static /* synthetic */ EncodingThread access$000(TranscodingCodecOutputSurface x0) {
/* 35 */            return x0.mEncodingThread;
                }

/* 34 */        static /* synthetic */ String access$300(TranscodingCodecOutputSurface x0) {
/* 35 */            return x0.mThumbnailPath;
                }

/* 47 */        public static class VideoFrame {
                    final opencv_core.IplImage image;
                    final long timestamp;

/* 48 */            public VideoFrame(opencv_core.IplImage image, long timestamp) {
/* 50 */                this.image = image;
/* 51 */                this.timestamp = timestamp;
                    }
                }

/* 71 */        public TranscodingCodecOutputSurface(int inWidth, int inHeight, Point cropOrigin, int orientation, int outSize, int frames, ExtractorTranscoder extractorTranscoder, int videoBufferMaxByteCount) {
/* 72 */            super(inWidth, inHeight, outSize, cropOrigin);
/* 42 */            this.mSourceIplQueue = new LinkedBlockingDeque<>();
/* 43 */            this.mEncodingIplQueue = new LinkedBlockingDeque<>();
/* 61 */            this.mHasGeneratedThumbnail = false;
/* 74 */            SwVineFrameRecorder recorder = extractorTranscoder.getRecorder();
/* 76 */            this.mThumbnailPath = recorder.getFilename() + System.currentTimeMillis() + ".jpg";
/* 78 */            this.mMatrix = new Matrix();
/* 79 */            this.mMatrix.postRotate(orientation, outSize / 2.0f, outSize / 2.0f);
/* 82 */            if (orientation == 180) {
/* 83 */                this.mCropOrigin.x = -((this.mInWidth - this.mCropOrigin.x) - outSize);
                    } else if (orientation == 270) {
/* 87 */                this.mCropOrigin.y = -((this.mInWidth - this.mCropOrigin.y) - outSize);
                    }
/* 90 */            System.nanoTime();
/* 91 */            frames = frames < 10 ? 10 : frames;
                    for (int i = 0; i < frames / 2; i++) {
/* 95 */                this.mSourceIplQueue.add(opencv_core.IplImage.create(outSize, outSize, 8, 4));
                    }
/* 100 */           this.mEncodingThread = new EncodingThread(recorder, this.mEncodingIplQueue, this.mSourceIplQueue, new C04871(extractorTranscoder, recorder), videoBufferMaxByteCount);
/* 117 */           this.mOutputBitmap = Bitmap.createBitmap(this.mInWidth, this.mInHeight, Bitmap.Config.ARGB_8888);
/* 118 */           this.mScaledCroppedBitmap = Bitmap.createBitmap(outSize, outSize, Bitmap.Config.ARGB_8888);
/* 119 */           this.mPixelBuf = ByteBuffer.allocateDirect(this.mInWidth * this.mInHeight * 4);
/* 120 */           this.mPixelBuf.order(ByteOrder.LITTLE_ENDIAN);
/* 121 */           this.mPaint.setAntiAlias(false);
/* 122 */           this.mPaint.setDither(false);
/* 123 */           this.mPaint.setFilterBitmap(false);
/* 125 */           this.mBitmapCanvas = new Canvas(this.mScaledCroppedBitmap);
/* 127 */           this.mEncodingThread.setPriority(10);
/* 128 */           this.mEncodingThread.start();
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.TranscodingCodecOutputSurface$1 */
/* 99 */        class C04871 implements EncodingThread.EncodingDoneListener {
                    final /* synthetic */ ExtractorTranscoder val$extractorTranscoder;
                    final /* synthetic */ SwVineFrameRecorder val$recorder;

/* 100 */           C04871(ExtractorTranscoder extractorTranscoder, SwVineFrameRecorder swVineFrameRecorder) {
                        this.val$extractorTranscoder = extractorTranscoder;
                        this.val$recorder = swVineFrameRecorder;
                    }

                    @Override // co.vine.android.recorder.TranscodingCodecOutputSurface.EncodingThread.EncodingDoneListener
/* 103 */           public void encodingProgressMade(long justProcessedPresentationTimestampUs) {
                        if (justProcessedPresentationTimestampUs > 0) {
/* 105 */                   this.val$extractorTranscoder.onEncodingProgressMade(justProcessedPresentationTimestampUs);
/* 114 */                   return;
                        }
                        try {
/* 108 */                   this.val$recorder.stop();
/* 109 */                   this.val$extractorTranscoder.onFinishedVideoEncoding(EncodingThread.access$100(TranscodingCodecOutputSurface.access$000(TranscodingCodecOutputSurface.this)), EncodingThread.access$200(TranscodingCodecOutputSurface.access$000(TranscodingCodecOutputSurface.this)), TranscodingCodecOutputSurface.access$300(TranscodingCodecOutputSurface.this), TranscodingCodecOutputSurface.this.mScaledCroppedBitmap, this.val$extractorTranscoder.getFrameRate());
                        } catch (SwVineFrameRecorder.Exception e) {
/* 111 */                   throw new RuntimeException(e);
                        }
                    }
                }

                @Override // co.vine.android.recorder.CodecOutputSurface
/* 132 */       protected void establishSize(int inWidth, int inHeight, int outSize, Point cropOrigin) {
/* 133 */           int smallestDimension = Math.min(inWidth, inHeight);
/* 134 */           float scale = outSize / (1.0f * smallestDimension);
/* 135 */           this.mInWidth = Math.round(inWidth * scale);
/* 136 */           this.mInHeight = Math.round(inHeight * scale);
/* 137 */           int scaledOriginX = Math.round(cropOrigin.x * scale);
/* 138 */           int scaledOriginY = Math.round(cropOrigin.y * scale);
/* 139 */           this.mCropOrigin = new Point(scaledOriginX, scaledOriginY);
                }

/* 142 */       public void done() throws FileNotFoundException {
/* 143 */           this.mEncodingThread.done();
/* 144 */           SLog.m446d("Done decoding, waiting for encode to be done.");
                    try {
/* 146 */               this.mEncodingThread.join();
                    } catch (InterruptedException e) {
/* 148 */               if (SLog.sLogsOn) {
/* 149 */                   e.printStackTrace();
                        }
                    }
                }

/* 154 */       public void saveFrame(long presentationTimestampUS) throws IOException {
/* 155 */           this.mPixelBuf.rewind();
/* 157 */           System.nanoTime();
/* 159 */           GLES20.glReadPixels(0, 0, this.mInWidth, this.mInHeight, 6408, 5121, this.mPixelBuf);
/* 163 */           this.mPixelBuf.rewind();
/* 165 */           System.nanoTime();
/* 166 */           this.mOutputBitmap.copyPixelsFromBuffer(this.mPixelBuf);
/* 169 */           System.nanoTime();
/* 170 */           this.mBitmapCanvas.save();
/* 171 */           this.mBitmapCanvas.translate(-this.mCropOrigin.x, -this.mCropOrigin.y);
/* 172 */           this.mBitmapCanvas.drawBitmap(this.mOutputBitmap, this.mMatrix, this.mPaint);
/* 173 */           this.mBitmapCanvas.restore();
                    try {
/* 178 */               opencv_core.IplImage image = this.mSourceIplQueue.take();
/* 183 */               this.mScaledCroppedBitmap.copyPixelsToBuffer(image.getByteBuffer());
/* 186 */               if (!this.mHasGeneratedThumbnail) {
/* 187 */                   this.mScaledCroppedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(this.mThumbnailPath));
/* 188 */                   this.mHasGeneratedThumbnail = true;
                        }
/* 191 */               this.mEncodingIplQueue.add(new VideoFrame(image, presentationTimestampUS));
                    } catch (InterruptedException e) {
/* 180 */               throw new RuntimeException(e);
                    }
                }

/* 192 */       public static class EncodingThread extends Thread {
                    private LinkedBlockingDeque<VideoFrame> mInQueue;
                    private EncodingDoneListener mListener;
                    private LinkedBlockingDeque<opencv_core.IplImage> mOutQueue;
                    private SwVineFrameRecorder mRecorder;
                    private byte[] mVideoBufferMax;
                    private ArrayList<VideoData> mVideoData = new ArrayList<>();
                    private boolean mDone = false;
                    private int mVideoBufferPosition = 0;

                    public interface EncodingDoneListener {
                        void encodingProgressMade(long j);
                    }

/* 193 */           static /* synthetic */ byte[] access$100(EncodingThread x0) {
/* 194 */               return x0.mVideoBufferMax;
                    }

/* 193 */           static /* synthetic */ ArrayList access$200(EncodingThread x0) {
/* 194 */               return x0.mVideoData;
                    }

/* 208 */           public EncodingThread(SwVineFrameRecorder recorder, LinkedBlockingDeque<VideoFrame> inQueue, LinkedBlockingDeque<opencv_core.IplImage> recycleQueue, EncodingDoneListener listener, int videoBufferMaxBytes) {
/* 210 */               this.mVideoBufferMax = new byte[videoBufferMaxBytes];
/* 211 */               this.mInQueue = inQueue;
/* 212 */               this.mOutQueue = recycleQueue;
/* 213 */               this.mRecorder = recorder;
/* 214 */               this.mListener = listener;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 218 */           public void run() {
                        while (true) {
/* 219 */                   if (this.mDone && this.mInQueue.isEmpty()) {
                                break;
                            }
                            try {
/* 221 */                       VideoFrame frame = this.mInQueue.take();
/* 222 */                       opencv_core.IplImage image = frame.image;
/* 223 */                       long presentationTimestampUS = frame.timestamp;
/* 225 */                       System.nanoTime();
/* 227 */                       VideoData data = new VideoData();
/* 228 */                       data.size = this.mRecorder.encode(image, this.mVideoBufferMax, this.mVideoBufferPosition);
/* 229 */                       data.encoded = true;
/* 230 */                       data.start = this.mVideoBufferPosition;
/* 231 */                       data.timestamp = presentationTimestampUS;
/* 232 */                       data.keyFrame = this.mRecorder.wasLastEncodedFrameKeyFrame();
/* 233 */                       this.mVideoData.add(data);
/* 234 */                       this.mVideoBufferPosition += data.size;
/* 238 */                       this.mOutQueue.add(image);
                                if (presentationTimestampUS > 0) {
/* 240 */                           this.mListener.encodingProgressMade(presentationTimestampUS);
                                }
                            } catch (SwVineFrameRecorder.Exception e) {
/* 244 */                       throw new RuntimeException(e);
                            } catch (InterruptedException e2) {
                            }
                        }
/* 250 */               if (this.mListener != null) {
/* 251 */                   this.mListener.encodingProgressMade(-1L);
                        }
                    }

/* 255 */           public void done() {
/* 256 */               this.mDone = true;
/* 257 */               interrupt();
                    }
                }
            }
