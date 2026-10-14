            package co.vine.android.recorder;

            import android.graphics.SurfaceTexture;
            import android.view.Surface;
            import co.vine.android.recorder.camera.PreviewManager;
            import com.edisonwang.android.slog.SLog;
            
            
    public class BaseSurfaceListener implements SurfaceController.SurfaceListener {
                private final ParentHolder mParentHolder;
                private final RecordController mRecordController;
                private final RecordState mState;

        public BaseSurfaceListener(ParentHolder parentHolder, RecordController recordController, RecordState state) {
            this.mParentHolder = parentHolder;
            this.mRecordController = recordController;
            this.mState = state;
                }

                @Override 
        public void onSurfaceUpdated(SurfaceTexture surfaceTexture, Surface surface) {
            PreviewManager previewManager = PreviewManager.getInstance();
            if (!this.mState.isStarted() && previewManager.isPreviewing()) {
                SLog.m446d("Surface updated. mCameraManager is previewing...");
                        try {
                    previewManager.stopPreview();
                        } catch (Exception e) {
                        }
                if (!previewManager.isPreviewing()) {
                    this.mRecordController.startPreview();
                        }
                    }
                }

                @Override 
        public void onSurfaceCreated(SurfaceTexture surfaceTexture, Surface surface) {
            this.mParentHolder.getParent().onSurfaceReady(this.mRecordController);
                }

                @Override 
        public void onSurfaceDestroyed(SurfaceTexture surfaceTexture, Surface surface) {
                }
            }
