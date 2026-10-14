            package co.vine.android.recorder;

            import android.graphics.SurfaceTexture;
            import android.hardware.Camera;
            import android.view.Surface;
            import android.view.SurfaceHolder;
            import android.view.SurfaceView;
            import android.view.TextureView;
            import android.view.View;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            
            
    public class SurfaceController implements TextureView.SurfaceTextureListener, SurfaceHolder.Callback {
                private Surface mSurface;
                private SurfaceHolder mSurfaceHolder;
                private final SurfaceListener mSurfaceListener;
                private SurfaceTexture mSurfaceTexture;
                private TextureView mTextureView;

                public interface SurfaceListener {
                    void onSurfaceCreated(SurfaceTexture surfaceTexture, Surface surface);

                    void onSurfaceDestroyed(SurfaceTexture surfaceTexture, Surface surface);

                    void onSurfaceUpdated(SurfaceTexture surfaceTexture, Surface surface);
                }

        public SurfaceController(SurfaceListener listener) {
            this.mSurfaceListener = listener;
                }

        public void releaseCallbacks() {
            if (this.mSurfaceHolder != null) {
                SLog.m446d("releasing surface holder callback");
                this.mSurfaceHolder.removeCallback(this);
                    }
            if (this.mTextureView != null) {
                SLog.m446d("releasing surface holder callback");
                this.mTextureView.setSurfaceTextureListener(null);
                    }
                }

                @Override 
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            this.mSurfaceTexture = surface;
            onSurfaceCreated();
                }

                @Override 
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            this.mSurfaceTexture = surface;
            onSurfaceCreated();
            onSurfaceUpdated();
                }

                @Override 
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            onSurfaceDestroyed();
            return true;
                }

                @Override 
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            this.mSurfaceTexture = surface;
            onSurfaceUpdated();
                }

                @Override 
        public void surfaceCreated(SurfaceHolder holder) {
            this.mSurfaceHolder = holder;
            this.mSurface = holder.getSurface();
            onSurfaceCreated();
                }

                @Override 
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.mSurfaceHolder = holder;
            this.mSurface = holder.getSurface();
            onSurfaceCreated();
            onSurfaceUpdated();
                }

        public void switchSurfaceToTextureView(TextureView view) throws IOException {
            this.mTextureView = view;
            this.mTextureView.setSurfaceTextureListener(this);
            if (this.mTextureView.isAvailable()) {
                onSurfaceTextureAvailable(view.getSurfaceTexture(), view.getWidth(), view.getHeight());
                    }
                }

       private void switchSurfaceToSurfaceView(SurfaceView view) throws IOException {
           this.mSurfaceHolder = view.getHolder();
           this.mSurfaceHolder.addCallback(this);
                }

       public void setPreviewSurface(View previewView) throws IOException {
           if (previewView instanceof TextureView) {
               switchSurfaceToTextureView((TextureView) previewView);
                    } else {
               if (previewView instanceof SurfaceView) {
                   switchSurfaceToSurfaceView((SurfaceView) previewView);
                   return;
                        }
               throw new IllegalArgumentException("You can't pass in a previewView that's not SurfaceView or TextureView");
                    }
                }

                @Override 
       public void surfaceDestroyed(SurfaceHolder holder) {
           onSurfaceDestroyed();
                }

       public boolean isSurfaceReady() {
                    return (this.mSurfaceTexture == null && this.mSurface == null) ? false : true;
                }

       private void onSurfaceCreated() {
           SLog.m446d("Surface created...");
           this.mSurfaceListener.onSurfaceCreated(this.mSurfaceTexture, this.mSurface);
                }

       private void onSurfaceDestroyed() {
           this.mSurfaceListener.onSurfaceDestroyed(this.mSurfaceTexture, this.mSurface);
           this.mSurfaceTexture = null;
           this.mSurface = null;
                }

       public void assertValidSurfaces() {
           if (this.mTextureView == null && this.mSurfaceHolder == null) {
               throw new RuntimeException("You have to set a preview surface via switchSurface first.");
                    }
                }

       private void onSurfaceUpdated() {
           this.mSurfaceListener.onSurfaceUpdated(this.mSurfaceTexture, this.mSurface);
                }

       public boolean giveCameraPreview(Camera camera) throws IOException {
           if (this.mSurfaceHolder != null) {
               camera.setPreviewDisplay(this.mSurfaceHolder);
                    } else if (this.mTextureView != null) {
               camera.setPreviewTexture(this.mSurfaceTexture);
                    } else {
               SLog.m446d("Surface is not ready, wait...");
               return false;
                    }
           return true;
                }
            }
