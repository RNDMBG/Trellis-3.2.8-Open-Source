            package co.vine.android.recorder;

            import android.graphics.SurfaceTexture;
            import android.view.Surface;
            import co.vine.android.recorder.camera.PreviewManager;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class BaseSurfaceListener implements SurfaceController.SurfaceListener {
                private final ParentHolder mParentHolder;
                private final RecordController mRecordController;
                private final RecordState mState;

/* 18 */        public BaseSurfaceListener(ParentHolder parentHolder, RecordController recordController, RecordState state) {
/* 20 */            this.mParentHolder = parentHolder;
/* 21 */            this.mRecordController = recordController;
/* 22 */            this.mState = state;
                }

                @Override // co.vine.android.recorder.SurfaceController.SurfaceListener
/* 26 */        public void onSurfaceUpdated(SurfaceTexture surfaceTexture, Surface surface) {
/* 27 */            PreviewManager previewManager = PreviewManager.getInstance();
/* 28 */            if (!this.mState.isStarted() && previewManager.isPreviewing()) {
/* 30 */                SLog.m446d("Surface updated. mCameraManager is previewing...");
                        try {
/* 32 */                    previewManager.stopPreview();
                        } catch (Exception e) {
                        }
/* 36 */                if (!previewManager.isPreviewing()) {
/* 37 */                    this.mRecordController.startPreview();
                        }
                    }
                }

                @Override // co.vine.android.recorder.SurfaceController.SurfaceListener
/* 44 */        public void onSurfaceCreated(SurfaceTexture surfaceTexture, Surface surface) {
/* 45 */            this.mParentHolder.getParent().onSurfaceReady(this.mRecordController);
                }

                @Override // co.vine.android.recorder.SurfaceController.SurfaceListener
/* 50 */        public void onSurfaceDestroyed(SurfaceTexture surfaceTexture, Surface surface) {
                }
            }
