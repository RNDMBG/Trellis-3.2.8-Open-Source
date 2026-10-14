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
            
            
    public class BufferedPreviewManagerCallback implements PreviewManagerCallback {
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

        static  boolean access$000(BufferedPreviewManagerCallback x0) {
            return x0.hasCamera();
                }

        static  Set<byte[]> access$100(BufferedPreviewManagerCallback x0) {
            return x0.mAddedBufferArray;
                }

        static  Camera access$200(BufferedPreviewManagerCallback x0) {
            return x0.getCamera();
                }

        static  ConcurrentLinkedQueue<byte[]> access$300(BufferedPreviewManagerCallback x0) {
            return x0.mBufferArray;
                }

        static  MasterPreviewCallback access$400() {
            return sCallback;
                }

        static  boolean access$500() {
            return hasPreallocateBuffers;
                }

        static  boolean access$502(boolean x0) {
            hasPreallocateBuffers = x0;
                    return x0;
                }

        static  int access$600(BufferedPreviewManagerCallback x0) {
            return x0.mFrameSize;
                }

        static  ByteBuffer access$700(BufferedPreviewManagerCallback x0) {
            return x0.mSharedByteBuffer;
                }

        static  ByteBuffer access$702(BufferedPreviewManagerCallback x0, ByteBuffer x1) {
            x0.mSharedByteBuffer = x1;
                    return x1;
                }

        static  boolean access$800(BufferedPreviewManagerCallback x0) {
            return x0.mRunAddBufferThread;
                }

        static  boolean access$802(BufferedPreviewManagerCallback x0, boolean x1) {
            x0.mRunAddBufferThread = x1;
                    return x1;
                }

        static  ConcurrentLinkedQueue<byte[]> access$900(BufferedPreviewManagerCallback x0) {
            return x0.mAddArray;
                }

        public static synchronized BufferedPreviewManagerCallback getInstance() {
                    try {
                        if (INSTANCE == null) {
                    INSTANCE = new BufferedPreviewManagerCallback();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return INSTANCE;
                }

        public static boolean isUsed() {
                    return INSTANCE != null;
                }

        private BufferedPreviewManagerCallback() {
                }

                @Override 
        public void preparePreviewParameters(Camera.Parameters parameters, RecordConfigUtils.RecordConfig config) throws PreviewManager.InvalidPreviewSizeException {
            CameraManager cm = CameraManager.getInstance();
            parameters.setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
            parameters.setPictureSize(this.mPreviewWidth, this.mPreviewHeight);
            String v = Integer.toString(this.mPreviewWidth) + "x" + Integer.toString(this.mPreviewHeight);
            parameters.set("video-size", v);
            if (RecordConfigUtils.RecordConfig.DISABLE_FACE_RECOGNITION) {
                parameters.set("face-detection", "off");
                    }
            boolean setRecordingHint = cm.addRecordingHintIfNeeded(parameters, config);
            CrashUtil.log("Set preview size: {} * {}, {}.", Integer.valueOf(this.mPreviewWidth), Integer.valueOf(this.mPreviewHeight), Boolean.valueOf(setRecordingHint));
            parameters.setPreviewFormat(17);
            cm.disableVideoStab(parameters);
                    try {
                getCamera().setParameters(parameters);
                if (SLog.sLogsOn) {
                    SLog.m447d("Starting params: {}.", getCamera().getParameters().flatten());
                        }
                    } catch (Exception e) {
                throw new PreviewManager.InvalidPreviewSizeException(e.getMessage());
                    }
                }

                @Override 
        public void cancelPendingPreviews() {
            StartPreviewThread previewThread = this.mStartPreviewThread;
            if (previewThread != null) {
                previewThread.cancel();
                    }
                }

                @Override 
        public void waitForPreviewToStart() {
                    try {
                if (this.mStartPreviewThread != null) {
                    long start = System.currentTimeMillis();
                    this.mStartPreviewThread.join();
                    SLog.m465i("Waiting for preview thread took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                        }
                    } catch (InterruptedException e) {
                SLog.m446d("Couldn't wait for preview to join.");
                    }
           this.mStartPreviewThread = null;
                }

                @Override 
       public void preReleaseCameraAndPreview() {
           StartPreviewThread startPreviewThread = this.mStartPreviewThread;
           if (startPreviewThread != null) {
               startPreviewThread.notifyReleasedAlready();
                    }
                }

                @Override 
       public void onReleaseCameraAndPreview(String tag, boolean releaseCallbackFromMaster) {
           CrashUtil.log("Camera was released from {}.", tag);
           sCallback.onReleaseFromCamera(getCamera(), releaseCallbackFromMaster);
                    try {
               getCamera().setPreviewCallback(null);
                    } catch (RuntimeException e) {
               CrashUtil.log("Failed to release callback, probably released: " + e.getMessage());
                    }
                }

                @Override 
       public void setPreviewSize(int previewWidth, int previewHeight) {
           this.mPreviewWidth = previewWidth;
           this.mPreviewHeight = previewHeight;
           this.mFrameSize = ((this.mPreviewWidth * this.mPreviewHeight) * ImageFormat.getBitsPerPixel(17)) / 8;
                }

                @Override 
       public int getPreviewWidth() {
           return this.mPreviewWidth;
                }

                @Override 
       public int getPreviewHeight() {
           return this.mPreviewHeight;
                }

                @Override 
       public void startPreview(CameraManager.CameraManagerController controller, int frameRate, int currentDurationMs, RecordConfigUtils.RecordConfig config) {
           this.mStartPreviewThread = new StartPreviewThread(controller, config, frameRate, currentDurationMs, this.mPreviewWidth, this.mPreviewHeight);
           this.mStartPreviewThread.start();
                }

       public class StartPreviewThread extends Thread {
                    private final RecordConfigUtils.RecordConfig config;
                    private final CameraManager.CameraManagerController controller;
                    private final long currentDurationMs;
                    private final int frameRate;
                    private final int heightUsed;
                    private boolean mCanceled;
                    private boolean releasedAlready;
                    private final int widthUsed;

           public StartPreviewThread(CameraManager.CameraManagerController controller, RecordConfigUtils.RecordConfig config, int frameRate, long currentDurationMs, int widthUsed, int heightUsed) {
               super("StartPreviewThread");
               this.frameRate = frameRate;
               this.currentDurationMs = currentDurationMs;
               this.config = config;
               this.controller = controller;
               this.widthUsed = widthUsed;
               this.heightUsed = heightUsed;
                    }

           public void notifyReleasedAlready() {
               this.releasedAlready = true;
                    }

                    @Override 
           public void run() {
                        byte[][] buffers;
                        byte[] tempBuffer;
               AddBufferThread addThread = BufferedPreviewManagerCallback.this.new AddBufferThread();
               int maxDuration = this.config.maxDuration;
               int bufferCount = Math.max((int) ((this.config.bufferCount * (this.frameRate / this.config.targetFrameRate)) * ((((long) maxDuration) - this.currentDurationMs) / maxDuration)), 30) / 10;
               CrashUtil.log("Buffer count: {}.", Integer.valueOf(bufferCount));
               this.releasedAlready = false;
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
                                    for (byte[] b : BufferedPreviewManagerCallback.access$100(BufferedPreviewManagerCallback.this)) {
                               BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).addCallbackBuffer(b);
                               bufferCount--;
                                    }
                                } else {
                           this.releasedAlready = true;
                           CrashUtil.log("Camera was released already.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               if (!this.releasedAlready) {
                            for (byte[] b2 : BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this)) {
                       BufferedPreviewManagerCallback.this.addCallbackBuffer(b2);
                       bufferCount--;
                            }
                        }
               CrashUtil.log("After reused buffer count: {}.", Integer.valueOf(bufferCount));
               long start = System.currentTimeMillis();
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
                           BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).setPreviewCallbackWithBuffer(BufferedPreviewManagerCallback.access$400());
                                } else {
                           this.releasedAlready = true;
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
               System.gc();
               if (!this.releasedAlready) {
                   boolean oomPreAlloc = false;
                   if (this.config.preAllocateBuffer && !BufferedPreviewManagerCallback.access$500()) {
                       int n = (int) (((double) (BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this) * (bufferCount - BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this).size()))) * this.config.preAllocRatio);
                       CrashUtil.log("Pre-allocate buffer {}: {}.", Double.valueOf(this.config.preAllocRatio), Integer.valueOf(n));
                       BufferedPreviewManagerCallback.access$502(true);
                                try {
                           tempBuffer = new byte[n];
                                } catch (OutOfMemoryError e) {
                           tempBuffer = null;
                           oomPreAlloc = true;
                           CrashUtil.log("Pre-allocation failed with ratio {}, ignore pre-allocation step.", Double.valueOf(this.config.preAllocRatio));
                                }
                       BufferedPreviewManagerCallback.access$702(BufferedPreviewManagerCallback.this, ByteBuffer.wrap(new byte[BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this)]));
                       if (tempBuffer != null) {
                           tempBuffer[0] = 1;
                                }
                       byte[] tempBuffer2 = new byte[0];
                       System.gc();
                            }
                   if (oomPreAlloc && this.config.preAllocRatio <= 1.0d) {
                                bufferCount = (int) (((double) bufferCount) * 0.5d);
                            }
                   int bufferCount2 = Math.max(bufferCount, 0);
                   if (BufferedPreviewManagerCallback.access$700(BufferedPreviewManagerCallback.this) == null) {
                                buffers = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bufferCount2 + 1, BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this));
                       BufferedPreviewManagerCallback.access$702(BufferedPreviewManagerCallback.this, ByteBuffer.wrap(buffers[bufferCount2]));
                            } else {
                                buffers = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bufferCount2, BufferedPreviewManagerCallback.access$600(BufferedPreviewManagerCallback.this));
                            }
                   BufferedPreviewManagerCallback.access$802(BufferedPreviewManagerCallback.this, !this.releasedAlready);
                   addThread.start();
                            for (int i = 0; i < bufferCount2; i++) {
                       if (i % 15 == 1) {
                           CrashUtil.log("Add buffer {}.", Integer.valueOf(i));
                                }
                       BufferedPreviewManagerCallback.this.addBuffer(buffers[i], false);
                            }
                        }
               SLog.m446d("Waiting for adding to be done.");
               BufferedPreviewManagerCallback.access$802(BufferedPreviewManagerCallback.this, false);
               CrashUtil.log("Making buffer took {} ms.", Long.valueOf(System.currentTimeMillis() - start));
               RuntimeException callbackException = null;
               if (this.mCanceled) {
                   CrashUtil.log("Preview was cancelled.");
                   return;
                        }
                        synchronized (CameraManager.CAMERA_LOCK) {
                            try {
                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this) && !this.releasedAlready) {
                           BufferedPreviewManagerCallback.access$400().setCallbackWithBuffer(this.controller);
                                    try {
                               if (this.mCanceled) {
                                   CrashUtil.log("Preview was cancelled.");
                                   return;
                                        } else {
                                   BufferedPreviewManagerCallback.access$200(BufferedPreviewManagerCallback.this).startPreview();
                                   CrashUtil.set("KnownPreviewConfig", String.valueOf(this.widthUsed + " x " + this.heightUsed));
                                        }
                                    } catch (RuntimeException e2) {
                               callbackException = e2;
                                    }
                                }
                       while (addThread.isAlive() && !this.releasedAlready) {
                                    try {
                               addThread.join(200L);
                                    } catch (InterruptedException e3) {
                               SLog.m455e("Couldn't wait for add thread to join.");
                                    }
                                }
                       if (this.mCanceled) {
                           CrashUtil.log("Preview was cancelled.");
                                } else if (callbackException == null) {
                           BufferedPreviewManagerCallback.access$400().onPreviewStarted();
                                } else {
                           BufferedPreviewManagerCallback.access$400().onPreviewError(callbackException);
                                }
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }

           public void cancel() {
               this.mCanceled = true;
                    }
                }

       public void removeBufferFromAvailableQueue(byte[] b) {
           if (b != null) {
               this.mAddedBufferArray.remove(b);
                    }
                }

       public int getAddedBufferArrayCount() {
           return this.mAddedBufferArray.size();
                }

       public void addBuffer(byte[] bytes, boolean isFlushing) {
           if (bytes != null) {
               if (isFlushing) {
                   this.mBufferArray.offer(bytes);
                   return;
                        }
               if (hasCamera()) {
                   if (!this.mRunAddBufferThread) {
                       addCallbackBuffer(bytes);
                       return;
                            } else {
                       this.mAddArray.offer(bytes);
                       return;
                            }
                        }
               this.mBufferArray.offer(bytes);
                    }
                }

       public void addCallbackBuffer(byte[] bytes) {
                    synchronized (CameraManager.CAMERA_LOCK) {
                        try {
                   if (hasCamera()) {
                       getCamera().addCallbackBuffer(bytes);
                       sCallback.onBufferAdded(getCamera());
                       this.mAddedBufferArray.add(bytes);
                            } else {
                       SLog.m455e("Buffer couldn't be added.");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private class AddBufferThread extends Thread {
           public AddBufferThread() {
               super("AddBufferThread");
                    }

                    @Override 
           public void run() {
               while (BufferedPreviewManagerCallback.access$800(BufferedPreviewManagerCallback.this)) {
                   if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this)) {
                       byte[] bytes = (byte[]) BufferedPreviewManagerCallback.access$900(BufferedPreviewManagerCallback.this).poll();
                       if (bytes == null) {
                                    try {
                               Thread.sleep(50L);
                                    } catch (InterruptedException e) {
                                    }
                                } else {
                           BufferedPreviewManagerCallback.this.addCallbackBuffer(bytes);
                                }
                            }
                        }
               ArrayList<byte[]> unused = new ArrayList<>();
                        while (true) {
                   byte[] bytes2 = (byte[]) BufferedPreviewManagerCallback.access$900(BufferedPreviewManagerCallback.this).poll();
                            if (bytes2 != null) {
                       if (BufferedPreviewManagerCallback.access$000(BufferedPreviewManagerCallback.this)) {
                           BufferedPreviewManagerCallback.this.addCallbackBuffer(bytes2);
                                } else {
                           unused.add(bytes2);
                                }
                            } else {
                       BufferedPreviewManagerCallback.access$300(BufferedPreviewManagerCallback.this).addAll(unused);
                       return;
                            }
                        }
                    }
                }

       public ByteBuffer getSharedByteBuffer() {
           return this.mSharedByteBuffer;
                }

       public boolean isFrameAdded(byte[] data) {
           return this.mAddedBufferArray.contains(data);
                }

       public int getFrameSize() {
           return this.mFrameSize;
                }

                @Override 
       public void releaseResources() {
           this.mBufferArray.clear();
           this.mAddedBufferArray.clear();
           System.gc();
                }

       public static String getCallbackStateString() {
           return MasterPreviewCallback.getInstance().getCallbackStateString();
                }

       private boolean hasCamera() {
                    return getCamera() != null;
                }

       private Camera getCamera() {
           return CameraManager.getInstance().getCamera();
                }
            }
