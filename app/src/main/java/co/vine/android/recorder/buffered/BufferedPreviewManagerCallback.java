            package co.vine.android.recorder.buffered;

            import android.graphics.ImageFormat;
            import android.hardware.Camera;
            import android.support.annotation.NonNull;
            import co.vine.android.recorder.MasterPreviewCallback;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.recorder.camera.PreviewManager;
            import co.vine.android.recorder.camera.PreviewManagerCallback;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.lang.reflect.Array;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Set;
            import java.util.concurrent.ConcurrentHashMap;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class BufferedPreviewManagerCallback implements PreviewManagerCallback {
                private static BufferedPreviewManagerCallback INSTANCE;
                private static boolean hasPreallocateBuffers;

                @NonNull
                private static final MasterPreviewCallback sCallback = MasterPreviewCallback.getInstance();
                private int mFrameSize;
                private int mPreviewHeight;
                private int mPreviewWidth;
                private volatile boolean mRunAddBufferThread;
                private ByteBuffer mSharedByteBuffer;
                private StartPreviewThread mStartPreviewThread;
                private final Set<byte[]> mAddedBufferArray = Collections.newSetFromMap(new ConcurrentHashMap());
                private final ConcurrentLinkedQueue<byte[]> mBufferArray = new ConcurrentLinkedQueue<>();
                private final ConcurrentLinkedQueue<byte[]> mAddArray = new ConcurrentLinkedQueue<>();

/* 25 */        static /* synthetic */ boolean access$000(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.hasCamera();
                }

/* 25 */        static /* synthetic */ Set<byte[]> access$100(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mAddedBufferArray;
                }

/* 25 */        static /* synthetic */ Camera access$200(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.getCamera();
                }

/* 25 */        static /* synthetic */ ConcurrentLinkedQueue<byte[]> access$300(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mBufferArray;
                }

/* 25 */        static /* synthetic */ MasterPreviewCallback access$400() {
/* 26 */            return sCallback;
                }

/* 25 */        static /* synthetic */ boolean access$500() {
/* 26 */            return hasPreallocateBuffers;
                }

/* 25 */        static /* synthetic */ boolean access$502(boolean x0) {
/* 26 */            hasPreallocateBuffers = x0;
                    return x0;
                }

/* 25 */        static /* synthetic */ int access$600(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mFrameSize;
                }

/* 25 */        static /* synthetic */ ByteBuffer access$700(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mSharedByteBuffer;
                }

/* 25 */        static /* synthetic */ ByteBuffer access$702(BufferedPreviewManagerCallback x0, ByteBuffer x1) {
/* 26 */            x0.mSharedByteBuffer = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ boolean access$800(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mRunAddBufferThread;
                }

/* 25 */        static /* synthetic */ boolean access$802(BufferedPreviewManagerCallback x0, boolean x1) {
/* 26 */            x0.mRunAddBufferThread = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ ConcurrentLinkedQueue<byte[]> access$900(BufferedPreviewManagerCallback x0) {
/* 26 */            return x0.mAddArray;
                }

/* 42 */        public static synchronized BufferedPreviewManagerCallback getInstance() {
                    try {
                        if (INSTANCE == null) {
/* 44 */                    INSTANCE = new BufferedPreviewManagerCallback();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 46 */            return INSTANCE;
                }

/* 49 */        public static boolean isUsed() {
                    return INSTANCE != null;
                }

/* 52 */        private BufferedPreviewManagerCallback() {
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 59 */        public void preparePreviewParameters(Camera.Parameters parameters, RecordConfigUtils.RecordConfig config) throws PreviewManager.InvalidPreviewSizeException {
/* 60 */            CameraManager cm = CameraManager.getInstance();
/* 61 */            parameters.setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
/* 62 */            parameters.setPictureSize(this.mPreviewWidth, this.mPreviewHeight);
/* 63 */            String v = Integer.toString(this.mPreviewWidth) + "x" + Integer.toString(this.mPreviewHeight);
/* 64 */            parameters.set("video-size", v);
/* 65 */            if (RecordConfigUtils.RecordConfig.DISABLE_FACE_RECOGNITION) {
/* 66 */                parameters.set("face-detection", "off");
                    }
/* 68 */            boolean setRecordingHint = cm.addRecordingHintIfNeeded(parameters, config);
/* 69 */            CrashUtil.log("Set preview size: {} * {}, {}.", Integer.valueOf(this.mPreviewWidth), Integer.valueOf(this.mPreviewHeight), Boolean.valueOf(setRecordingHint));
/* 70 */            parameters.setPreviewFormat(17);
/* 71 */            cm.disableVideoStab(parameters);
                    try {
/* 73 */                getCamera().setParameters(parameters);
/* 77 */                if (SLog.sLogsOn) {
/* 78 */                    SLog.m447d("Starting params: {}.", getCamera().getParameters().flatten());
                        }
                    } catch (Exception e) {
/* 75 */                throw new PreviewManager.InvalidPreviewSizeException(e.getMessage());
                    }
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 83 */        public void cancelPendingPreviews() {
/* 84 */            StartPreviewThread previewThread = this.mStartPreviewThread;
/* 85 */            if (previewThread != null) {
/* 86 */                previewThread.cancel();
                    }
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 92 */        public void waitForPreviewToStart() {
                    try {
/* 93 */                if (this.mStartPreviewThread != null) {
/* 94 */                    long start = System.currentTimeMillis();
/* 95 */                    this.mStartPreviewThread.join();
/* 96 */                    SLog.m465i("Waiting for preview thread took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                        }
                    } catch (InterruptedException e) {
/* 99 */                SLog.m446d("Couldn't wait for preview to join.");
                    }
/* 101 */           this.mStartPreviewThread = null;
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 105 */       public void preReleaseCameraAndPreview() {
/* 106 */           StartPreviewThread startPreviewThread = this.mStartPreviewThread;
/* 107 */           if (startPreviewThread != null) {
/* 108 */               startPreviewThread.notifyReleasedAlready();
                    }
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 113 */       public void onReleaseCameraAndPreview(String tag, boolean releaseCallbackFromMaster) {
/* 114 */           CrashUtil.log("Camera was released from {}.", tag);
/* 115 */           sCallback.onReleaseFromCamera(getCamera(), releaseCallbackFromMaster);
                    try {
/* 117 */               getCamera().setPreviewCallback(null);
                    } catch (RuntimeException e) {
/* 119 */               CrashUtil.log("Failed to release callback, probably released: " + e.getMessage());
                    }
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 124 */       public void setPreviewSize(int previewWidth, int previewHeight) {
/* 125 */           this.mPreviewWidth = previewWidth;
/* 126 */           this.mPreviewHeight = previewHeight;
/* 127 */           this.mFrameSize = ((this.mPreviewWidth * this.mPreviewHeight) * ImageFormat.getBitsPerPixel(17)) / 8;
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 131 */       public int getPreviewWidth() {
/* 132 */           return this.mPreviewWidth;
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 136 */       public int getPreviewHeight() {
/* 137 */           return this.mPreviewHeight;
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 141 */       public void startPreview(CameraManager.CameraManagerController controller, int frameRate, int currentDurationMs, RecordConfigUtils.RecordConfig config) {
/* 142 */           this.mStartPreviewThread = new StartPreviewThread(controller, config, frameRate, currentDurationMs, this.mPreviewWidth, this.mPreviewHeight);
/* 144 */           this.mStartPreviewThread.start();
                }

/* 159 */       public class StartPreviewThread extends Thread {
                    private final RecordConfigUtils.RecordConfig config;
                    private final CameraManager.CameraManagerController controller;
                    private final long currentDurationMs;
                    private final int frameRate;
                    private final int heightUsed;
                    private boolean mCanceled;
                    private boolean releasedAlready;
                    private final int widthUsed;

/* 160 */           public StartPreviewThread(CameraManager.CameraManagerController controller, RecordConfigUtils.RecordConfig config, int frameRate, long currentDurationMs, int widthUsed, int heightUsed) {
/* 162 */               super("StartPreviewThread");
/* 163 */               this.frameRate = frameRate;
/* 164 */               this.currentDurationMs = currentDurationMs;
/* 165 */               this.config = config;
/* 166 */               this.controller = controller;
/* 167 */               this.widthUsed = widthUsed;
/* 168 */               this.heightUsed = heightUsed;
                    }

/* 171 */           public void notifyReleasedAlready() {
/* 172 */               this.releasedAlready = true;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 177 */           public void run() {
                        byte[][] buffers;
                        byte[] tempBuffer;
/* 178 */               AddBufferThread addThread = BufferedPreviewManagerCallback.this.new AddBufferThread();
/* 179 */               int maxDuration = this.config.maxDuration;
/* 180 */               int bufferCount = Math.max((int) ((this.config.bufferCount * (this.frameRate / this.config.targetFrameRate)) * ((((long) maxDuration) - this.currentDurationMs) / maxDuration)), 30) / 10;
/* 184 */               CrashUtil.log("Buffer count: {}.", Integer.valueOf(bufferCount));
/* 185 */               this.releasedAlready = false;
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
/* 187 */                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
                                    for (byte[] b : BufferedPreviewManagerCallback.access$100(BufferedPreviewManagerCallback.this)) {
/* 189 */                               BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).addCallbackBuffer(b);
/* 190 */                               bufferCount--;
                                    }
                                } else {
/* 193 */                           this.releasedAlready = true;
/* 194 */                           CrashUtil.log("Camera was released already.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 197 */               if (!this.releasedAlready) {
                            for (byte[] b2 : BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this)) {
/* 199 */                       BufferedPreviewManagerCallback.this.addCallbackBuffer(b2);
/* 200 */                       bufferCount--;
                            }
                        }
/* 203 */               CrashUtil.log("After reused buffer count: {}.", Integer.valueOf(bufferCount));
/* 204 */               long start = System.currentTimeMillis();
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
/* 206 */                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
/* 207 */                           BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).setPreviewCallbackWithBuffer(BufferedPreviewManagerCallback.access$400());
                                } else {
/* 209 */                           this.releasedAlready = true;
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
/* 212 */               System.gc();
/* 213 */               if (!this.releasedAlready) {
/* 214 */                   boolean oomPreAlloc = false;
/* 215 */                   if (this.config.preAllocateBuffer && !BufferedPreviewManagerCallback.access$500()) {
/* 216 */                       int n = (int) (((double) (BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this) * (bufferCount - BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this).size()))) * this.config.preAllocRatio);
/* 217 */                       CrashUtil.log("Pre-allocate buffer {}: {}.", Double.valueOf(this.config.preAllocRatio), Integer.valueOf(n));
/* 218 */                       BufferedPreviewManagerCallback.access$502(true);
                                try {
/* 221 */                           tempBuffer = new byte[n];
                                } catch (OutOfMemoryError e) {
/* 223 */                           tempBuffer = null;
/* 224 */                           oomPreAlloc = true;
/* 225 */                           CrashUtil.log("Pre-allocation failed with ratio {}, ignore pre-allocation step.", Double.valueOf(this.config.preAllocRatio));
                                }
/* 227 */                       BufferedPreviewManagerCallback.access$702(BufferedPreviewManagerCallback.this, ByteBuffer.wrap(new byte[BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this)]));
/* 228 */                       if (tempBuffer != null) {
/* 229 */                           tempBuffer[0] = 1;
                                }
/* 231 */                       byte[] tempBuffer2 = new byte[0];
/* 233 */                       System.gc();
                            }
/* 235 */                   if (oomPreAlloc && this.config.preAllocRatio <= 1.0d) {
                                bufferCount = (int) (((double) bufferCount) * 0.5d);
                            }
/* 240 */                   int bufferCount2 = Math.max(bufferCount, 0);
/* 242 */                   if (BufferedPreviewManagerCallback.access$700(BufferedPreviewManagerCallback.this) == null) {
                                buffers = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bufferCount2 + 1, BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this));
/* 244 */                       BufferedPreviewManagerCallback.access$702(BufferedPreviewManagerCallback.this, ByteBuffer.wrap(buffers[bufferCount2]));
                            } else {
                                buffers = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bufferCount2, BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this));
                            }
/* 248 */                   BufferedPreviewManagerCallback.access$802(BufferedPreviewManagerCallback.this, !this.releasedAlready);
/* 249 */                   addThread.start();
                            for (int i = 0; i < bufferCount2; i++) {
/* 251 */                       if (i % 15 == 1) {
/* 252 */                           CrashUtil.log("Add buffer {}.", Integer.valueOf(i));
                                }
/* 254 */                       BufferedPreviewManagerCallback.this.addBuffer(buffers[i], false);
                            }
                        }
/* 257 */               SLog.m446d("Waiting for adding to be done.");
/* 258 */               BufferedPreviewManagerCallback.access$802(BufferedPreviewManagerCallback.this, false);
/* 259 */               CrashUtil.log("Making buffer took {} ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 260 */               RuntimeException callbackException = null;
/* 261 */               if (this.mCanceled) {
/* 262 */                   CrashUtil.log("Preview was cancelled.");
/* 297 */                   return;
                        }
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
/* 266 */                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
/* 267 */                           BufferedPreviewManagerCallback.access$400().setCallbackWithBuffer(this.controller);
                                    try {
/* 269 */                               if (this.mCanceled) {
/* 270 */                                   CrashUtil.log("Preview was cancelled.");
/* 297 */                                   return;
                                        } else {
/* 273 */                                   BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).startPreview();
/* 274 */                                   CrashUtil.set("KnownPreviewConfig", String.valueOf(this.widthUsed + " x " + this.heightUsed));
                                        }
                                    } catch (RuntimeException e2) {
/* 277 */                               callbackException = e2;
                                    }
                                }
/* 282 */                       while (addThread.isAlive() && !this.releasedAlready) {
                                    try {
/* 283 */                               addThread.join(200L);
                                    } catch (InterruptedException e3) {
/* 286 */                               SLog.m455e("Couldn't wait for add thread to join.");
                                    }
                                }
/* 288 */                       if (this.mCanceled) {
/* 289 */                           CrashUtil.log("Preview was cancelled.");
                                } else if (callbackException == null) {
/* 293 */                           BufferedPreviewManagerCallback.access$400().onPreviewStarted();
                                } else {
/* 295 */                           BufferedPreviewManagerCallback.access$400().onPreviewError(callbackException);
                                }
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }

/* 299 */           public void cancel() {
/* 300 */               this.mCanceled = true;
                    }
                }

/* 305 */       public void removeBufferFromAvailableQueue(byte[] b) {
/* 306 */           if (b != null) {
/* 307 */               this.mAddedBufferArray.remove(b);
                    }
                }

/* 311 */       public int getAddedBufferArrayCount() {
/* 312 */           return this.mAddedBufferArray.size();
                }

/* 315 */       public void addBuffer(byte[] bytes, boolean isFlushing) {
/* 316 */           if (bytes != null) {
/* 317 */               if (isFlushing) {
/* 318 */                   this.mBufferArray.offer(bytes);
/* 331 */                   return;
                        }
/* 320 */               if (hasCamera()) {
/* 321 */                   if (!this.mRunAddBufferThread) {
/* 322 */                       addCallbackBuffer(bytes);
/* 331 */                       return;
                            } else {
/* 324 */                       this.mAddArray.offer(bytes);
/* 331 */                       return;
                            }
                        }
/* 327 */               this.mBufferArray.offer(bytes);
                    }
                }

/* 333 */       public void addCallbackBuffer(byte[] bytes) {
                    synchronized (CameraManager.CAMERA_LOCK) {
                        try {
/* 335 */                   if (hasCamera()) {
/* 336 */                       getCamera().addCallbackBuffer(bytes);
/* 337 */                       sCallback.onBufferAdded(getCamera());
/* 338 */                       this.mAddedBufferArray.add(bytes);
                            } else {
/* 340 */                       SLog.m455e("Buffer couldn't be added.");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 344 */       private class AddBufferThread extends Thread {
/* 345 */           public AddBufferThread() {
/* 347 */               super("AddBufferThread");
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 352 */           public void run() {
/* 353 */               while (BufferedPreviewManagerCallback.access$800(BufferedPreviewManagerCallback.this)) {
/* 354 */                   if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this)) {
/* 355 */                       byte[] bytes = (byte[]) BufferedPreviewManagerCallback.access$900(BufferedPreviewManagerCallback.this).poll();
/* 356 */                       if (bytes == null) {
                                    try {
/* 358 */                               Thread.sleep(50L);
                                    } catch (InterruptedException e) {
                                    }
                                } else {
/* 363 */                           BufferedPreviewManagerCallback.this.addCallbackBuffer(bytes);
                                }
                            }
                        }
/* 367 */               ArrayList<byte[]> unused = new ArrayList<>();
                        while (true) {
/* 368 */                   byte[] bytes2 = (byte[]) BufferedPreviewManagerCallback.access$900(BufferedPreviewManagerCallback.this).poll();
                            if (bytes2 != null) {
/* 369 */                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this)) {
/* 370 */                           BufferedPreviewManagerCallback.this.addCallbackBuffer(bytes2);
                                } else {
/* 372 */                           unused.add(bytes2);
                                }
                            } else {
/* 375 */                       BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this).addAll(unused);
/* 376 */                       return;
                            }
                        }
                    }
                }

/* 379 */       public ByteBuffer getSharedByteBuffer() {
/* 380 */           return this.mSharedByteBuffer;
                }

/* 383 */       public boolean isFrameAdded(byte[] data) {
/* 384 */           return this.mAddedBufferArray.contains(data);
                }

/* 387 */       public int getFrameSize() {
/* 388 */           return this.mFrameSize;
                }

                @Override // co.vine.android.recorder.camera.PreviewManagerCallback
/* 391 */       public void releaseResources() {
/* 392 */           this.mBufferArray.clear();
/* 393 */           this.mAddedBufferArray.clear();
/* 394 */           System.gc();
                }

/* 397 */       public static String getCallbackStateString() {
/* 398 */           return MasterPreviewCallback.getInstance().getCallbackStateString();
                }

/* 401 */       private boolean hasCamera() {
                    return getCamera() != null;
                }

/* 405 */       private Camera getCamera() {
/* 406 */           return CameraManager.getInstance().getCamera();
                }
            }
