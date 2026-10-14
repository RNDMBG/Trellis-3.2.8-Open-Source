            package co.vine.android.recorder.camera;

            import android.hardware.Camera;
            import co.vine.android.recorder.MasterPreviewCallback;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.SurfaceController;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class PreviewManager {
                private static PreviewManager INSTANCE;
                private volatile boolean isPreviewing;
                private PreviewManagerCallback mManagerCallback;

/* 23 */        public void setManagerCallback(PreviewManagerCallback cb) {
/* 24 */            this.mManagerCallback = cb;
                }

/* 27 */        public void cancelPendingPreviews() {
/* 28 */            PreviewManagerCallback cb = this.mManagerCallback;
/* 29 */            if (cb != null) {
/* 30 */                cb.cancelPendingPreviews();
                    }
                }

/* 34 */        private boolean hasCamera() {
                    return getCamera() != null;
                }

/* 38 */        private Camera getCamera() {
/* 39 */            return CameraManager.getInstance().getCamera();
                }

/* 42 */        public static PreviewManager getInstance() {
/* 43 */            if (INSTANCE == null) {
/* 44 */                INSTANCE = new PreviewManager();
                    }
/* 46 */            return INSTANCE;
                }

/* 50 */        public static class InvalidPreviewSizeException extends Exception {
/* 51 */            public InvalidPreviewSizeException(String message) {
/* 52 */                super(message);
                    }
                }

/* 57 */        private int getPreviewWidth() {
/* 58 */            return this.mManagerCallback.getPreviewWidth();
                }

/* 61 */        private int getPreviewHeight() {
/* 62 */            return this.mManagerCallback.getPreviewHeight();
                }

/* 65 */        public void startPreview(CameraManager.CameraManagerController controller, RecordConfigUtils.RecordConfig config, SurfaceController surfaceController, boolean flash, boolean autoFocus, int frameRate, int rotation, int currentDurationMs) throws InvalidPreviewSizeException {
                    synchronized (CameraManager.CAMERA_LOCK) {
                        try {
                            try {
/* 68 */                        if (this.isPreviewing) {
/* 69 */                            SLog.m446d("Do not preview again.");
/* 100 */                           return;
                                }
/* 72 */                        if (hasCamera()) {
/* 73 */                            SLog.m446d("2,3: Set preview display and start preview.");
/* 74 */                            if (surfaceController.giveCameraPreview(getCamera())) {
/* 75 */                                this.isPreviewing = true;
/* 76 */                                Camera.Parameters parameters = getCamera().getParameters();
/* 77 */                                CameraManager cm = CameraManager.getInstance();
/* 78 */                                int degree = cm.fixOrientation(rotation);
/* 79 */                                cm.prepareFocusMatrix(getPreviewWidth(), getPreviewHeight(), degree);
                                        Object[] objArr = new Object[1];
                                        objArr[0] = Boolean.valueOf(degree == 90 || degree == 270);
/* 80 */                                CrashUtil.log("Preview flip: {} ", objArr);
/* 81 */                                if (autoFocus) {
/* 82 */                                    cm.startContinuousAutoFocus(parameters);
                                        }
/* 84 */                                if (flash) {
/* 85 */                                    cm.changeFlashState(true);
                                        }
/* 87 */                                this.mManagerCallback.preparePreviewParameters(parameters, config);
/* 88 */                                this.mManagerCallback.startPreview(controller, frameRate, currentDurationMs, config);
                                    }
                                }
                            } catch (Exception e) {
/* 92 */                        this.isPreviewing = false;
/* 93 */                        CrashUtil.log("Failed to startPreview: {}.", e.getMessage());
/* 94 */                        SLog.m459e("Preview failed here.", (Throwable) e);
/* 95 */                        if (e instanceof InvalidPreviewSizeException) {
/* 96 */                            throw ((InvalidPreviewSizeException) e);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 102 */       public void waitForPreviewToStart() {
/* 103 */           this.mManagerCallback.waitForPreviewToStart();
                }

/* 106 */       public void releaseCameraAndPreview(String tag) {
/* 107 */           releaseCameraAndPreview(tag, true);
                }

/* 110 */       public void releaseCameraAndPreview(String tag, boolean releaseCallbackFromMaster) {
/* 111 */           this.mManagerCallback.preReleaseCameraAndPreview();
/* 112 */           waitForPreviewToStart();
                    synchronized (CameraManager.CAMERA_LOCK) {
                        try {
/* 114 */                   if (hasCamera()) {
/* 115 */                       SLog.m446d("6,7 RELEASE Camera and STOP Preview.");
/* 116 */                       if (this.isPreviewing) {
/* 117 */                           stopPreview();
                                }
/* 119 */                       this.mManagerCallback.onReleaseCameraAndPreview(tag, releaseCallbackFromMaster);
/* 120 */                       CameraManager.getInstance().releaseCamera();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 125 */       public boolean hasGivenBufferToCurrentCallback() {
/* 126 */           return MasterPreviewCallback.getInstance().hasGivenFirstFrameToCurrentCallback();
                }

/* 129 */       public static void releaseCallback() {
/* 130 */           setPreviewCallback(null);
                }

/* 133 */       public static void setPreviewCallback(CameraManager.CameraManagerController controller) {
/* 134 */           MasterPreviewCallback.getInstance().setCallbackWithBuffer(controller);
                }

/* 137 */       public void stopPreview() {
                    synchronized (CameraManager.CAMERA_LOCK) {
                        try {
/* 139 */                   getCamera().stopPreview();
/* 140 */                   MasterPreviewCallback.getInstance().onPreviewStopped();
/* 141 */                   this.isPreviewing = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 145 */       public void setPreviewSize(int previewWidth, int previewHeight) {
/* 146 */           this.mManagerCallback.setPreviewSize(previewWidth, previewHeight);
                }

/* 149 */       public boolean isPreviewing() {
/* 150 */           return this.isPreviewing;
                }

/* 153 */       public void releaseResources() {
/* 154 */           this.mManagerCallback.releaseResources();
                }
            }
