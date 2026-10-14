            package co.vine.android.recorder;

            import android.hardware.Camera;
            import android.support.annotation.NonNull;
            import co.vine.android.recorder.buffered.BufferedPreviewManagerCallback;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class MasterPreviewCallback implements CameraManager.CameraManagerController, Camera.PreviewCallback {
                private static MasterPreviewCallback INSTANCE;
                private int mCameraBufferCount;
                private boolean mFirstFrameReceived;
                private long mFramesReceived;
                private boolean mHasGivenFirstFrameToCurrentCallback;
                private boolean mHasPendingPreviewFrameError;
                private long mLastNotificationOfPreviewFrameError;
                private long mLastPreviewFrameTimestamp;
                private boolean mMismatchFromOnPreviewFrame;
                private boolean mMistMatchFromBufferAdding;
                private long mPreviewStartTime;

                @NonNull
                private WeakReference<CameraManager.CameraManagerController> mCallback = new WeakReference<>(null);

                @NonNull
                private WeakReference<Camera> mCamera = new WeakReference<>(null);
                private long mMaxtimeElapsed = -1;

/* 21 */        public static synchronized MasterPreviewCallback getInstance() {
                    try {
                        if (INSTANCE == null) {
/* 23 */                    INSTANCE = new MasterPreviewCallback();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 25 */            return INSTANCE;
                }

/* 46 */        public void setCallbackWithBuffer(CameraManager.CameraManagerController callback) {
/* 47 */            notifyWastedFrameError();
/* 48 */            if (callback != this.mCallback.get()) {
/* 49 */                this.mCallback = new WeakReference<>(callback);
/* 50 */                this.mHasGivenFirstFrameToCurrentCallback = false;
/* 51 */                CrashUtil.log("CameraManager callback state has changed: " + String.valueOf(callback));
                    }
                }

/* 55 */        public boolean hasGivenFirstFrameToCurrentCallback() {
/* 56 */            return this.mHasGivenFirstFrameToCurrentCallback;
                }

                @Override // android.hardware.Camera.PreviewCallback
/* 60 */        public void onPreviewFrame(byte[] data, Camera camera) {
                    Camera.PreviewCallback previewCallback;
/* 61 */            if (this.mCamera.get() != camera) {
/* 62 */                this.mMismatchFromOnPreviewFrame = true;
/* 63 */                CrashUtil.log("Frame is being given from a different camera? {} {}.", this.mCamera.get(), camera);
                    }
/* 65 */            if (!this.mFirstFrameReceived) {
/* 66 */                CrashUtil.log("First frame received for camera from master controller: " + camera + "Current callback {}", this.mCallback.get());
/* 68 */                this.mFirstFrameReceived = true;
                    }
/* 70 */            if (SLog.sLogsOn) {
/* 71 */                this.mFramesReceived++;
                        if (this.mLastPreviewFrameTimestamp != -1) {
/* 73 */                    long timeElapsed = System.currentTimeMillis() - this.mLastPreviewFrameTimestamp;
/* 74 */                    this.mMaxtimeElapsed = Math.max(timeElapsed, this.mMaxtimeElapsed);
                        }
/* 76 */                this.mLastPreviewFrameTimestamp = System.currentTimeMillis();
                    }
/* 78 */            this.mCameraBufferCount--;
/* 79 */            CameraManager.CameraManagerController cb = this.mCallback.get();
/* 81 */            if (cb != null) {
/* 82 */                previewCallback = cb.getCameraPreviewCallback();
                    } else {
/* 84 */                previewCallback = null;
                    }
/* 86 */            if (previewCallback != null) {
/* 87 */                notifyWastedFrameError();
/* 88 */                if (!this.mHasGivenFirstFrameToCurrentCallback) {
/* 89 */                    CrashUtil.log("Giving callback {} its first frame.", this.mCallback.get());
                        }
/* 91 */                this.mHasGivenFirstFrameToCurrentCallback = true;
/* 92 */                previewCallback.onPreviewFrame(data, camera);
/* 105 */               return;
                    }
                    if (System.currentTimeMillis() - this.mLastNotificationOfPreviewFrameError > 1000) {
/* 95 */                notifyWastedFrameError();
                    } else {
/* 97 */                this.mHasPendingPreviewFrameError = true;
                    }
/* 99 */            if (BufferedPreviewManagerCallback.isUsed()) {
/* 100 */               BufferedPreviewManagerCallback pc = BufferedPreviewManagerCallback.getInstance();
/* 101 */               pc.removeBufferFromAvailableQueue(data);
/* 102 */               pc.addCallbackBuffer(data);
                    }
                }

/* 107 */       private void notifyWastedFrameError() {
/* 108 */           if (this.mHasPendingPreviewFrameError) {
/* 109 */               CrashUtil.log("Got frame when callback was already released.");
/* 110 */               this.mHasPendingPreviewFrameError = false;
/* 111 */               this.mLastNotificationOfPreviewFrameError = System.currentTimeMillis();
                    }
                }

                @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 116 */       public Camera.PreviewCallback getCameraPreviewCallback() {
/* 117 */           return this.mCallback.get().getCameraPreviewCallback();
                }

                @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 121 */       public void onZoomUpdated(int zoom, boolean stopped) {
/* 122 */           CameraManager.CameraManagerController previewCallback = this.mCallback.get();
/* 123 */           if (previewCallback != null) {
/* 124 */               previewCallback.onZoomUpdated(zoom, stopped);
                    }
                }

                @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 129 */       public void onPreviewError(RuntimeException callbackException) {
/* 130 */           CameraManager.CameraManagerController previewCallback = this.mCallback.get();
/* 131 */           if (previewCallback != null) {
/* 132 */               previewCallback.onPreviewError(callbackException);
                    }
                }

/* 136 */       public String getCallbackStateString() {
/* 137 */           return "\nCallback : " + this.mCallback.get() + "\nCamera frame count: " + this.mCameraBufferCount + "\nMismatch from Buffer Adding? : " + this.mMistMatchFromBufferAdding + "\nMismatch from Preview Frame? : " + this.mMismatchFromOnPreviewFrame + "\nEver got a frame? : " + this.mFirstFrameReceived;
                }

/* 144 */       public void onBufferAdded(Camera camera) {
/* 145 */           if (this.mCamera.get() != camera) {
/* 146 */               this.mMistMatchFromBufferAdding = true;
/* 147 */               CrashUtil.log("Buffer is being added to a different camera? {} {}.", this.mCamera.get(), camera);
                    }
/* 149 */           this.mCameraBufferCount++;
                }

/* 152 */       public void onReleaseFromCamera(Camera camera, boolean releaseCallback) {
/* 153 */           CrashUtil.log("Releasing callback from {}, ever received a frame? {}.", camera, Boolean.valueOf(this.mFirstFrameReceived));
/* 154 */           if (releaseCallback) {
/* 155 */               setCallbackWithBuffer(null);
                    }
/* 157 */           this.mFirstFrameReceived = false;
/* 158 */           this.mCameraBufferCount = 0;
/* 159 */           this.mCamera = new WeakReference<>(null);
/* 160 */           int avgFrameRate = this.mFramesReceived > 0 ? (int) ((this.mFramesReceived * 1000) / (System.currentTimeMillis() - this.mPreviewStartTime)) : -1;
/* 161 */           if (avgFrameRate > 0) {
/* 162 */               SLog.m442b("Lowest frame rate: {}.", Long.valueOf(this.mMaxtimeElapsed > 0 ? 1000 / this.mMaxtimeElapsed : this.mMaxtimeElapsed));
/* 163 */               SLog.m443b("Average frame rate: {}.", Integer.valueOf(avgFrameRate), avgFrameRate > 0 && avgFrameRate < 15);
                    }
                }

/* 167 */       public void onCameraOpened(Camera camera) {
/* 168 */           this.mCamera = new WeakReference<>(camera);
/* 169 */           CrashUtil.log("Camera opened: " + camera);
/* 170 */           this.mFirstFrameReceived = false;
                }

/* 173 */       public void onPreviewStarted() {
/* 174 */           CrashUtil.log("Preview started.");
/* 175 */           this.mLastPreviewFrameTimestamp = -1L;
/* 176 */           this.mFramesReceived = 0L;
/* 177 */           this.mPreviewStartTime = System.currentTimeMillis();
                }

/* 180 */       public void onPreviewStopped() {
/* 181 */           CrashUtil.log("Preview stopped.");
/* 182 */           this.mLastPreviewFrameTimestamp = -1L;
                }
            }
