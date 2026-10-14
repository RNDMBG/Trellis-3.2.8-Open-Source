            package co.vine.android.recorder.camera;

            import android.hardware.Camera;
            import co.vine.android.recorder.RecordConfigUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PreviewManagerCallback {
                void cancelPendingPreviews();

                int getPreviewHeight();

                int getPreviewWidth();

                void onReleaseCameraAndPreview(String str, boolean z);

                void preReleaseCameraAndPreview();

                void preparePreviewParameters(Camera.Parameters parameters, RecordConfigUtils.RecordConfig recordConfig) throws PreviewManager.InvalidPreviewSizeException;

                void releaseResources();

                void setPreviewSize(int i, int i2);

                void startPreview(CameraManager.CameraManagerController cameraManagerController, int i, int i2, RecordConfigUtils.RecordConfig recordConfig);

                void waitForPreviewToStart();
            }
