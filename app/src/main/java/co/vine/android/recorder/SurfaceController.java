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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class SurfaceController implements TextureView.SurfaceTextureListener, SurfaceHolder.Callback {
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

/* 36 */        public SurfaceController(SurfaceListener listener) {
/* 38 */            this.mSurfaceListener = listener;
                }

/* 41 */        public void releaseCallbacks() {
/* 42 */            if (this.mSurfaceHolder != null) {
/* 43 */                SLog.m446d("releasing surface holder callback");
/* 44 */                this.mSurfaceHolder.removeCallback(this);
                    }
/* 46 */            if (this.mTextureView != null) {
/* 47 */                SLog.m446d("releasing surface holder callback");
/* 48 */                this.mTextureView.setSurfaceTextureListener(null);
                    }
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 53 */        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
/* 54 */            this.mSurfaceTexture = surface;
/* 55 */            onSurfaceCreated();
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 59 */        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
/* 60 */            this.mSurfaceTexture = surface;
/* 61 */            onSurfaceCreated();
/* 62 */            onSurfaceUpdated();
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 66 */        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
/* 67 */            onSurfaceDestroyed();
/* 68 */            return true;
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 72 */        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
/* 73 */            this.mSurfaceTexture = surface;
/* 74 */            onSurfaceUpdated();
                }

                @Override // android.view.SurfaceHolder.Callback
/* 78 */        public void surfaceCreated(SurfaceHolder holder) {
/* 79 */            this.mSurfaceHolder = holder;
/* 80 */            this.mSurface = holder.getSurface();
/* 81 */            onSurfaceCreated();
                }

                @Override // android.view.SurfaceHolder.Callback
/* 85 */        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
/* 86 */            this.mSurfaceHolder = holder;
/* 87 */            this.mSurface = holder.getSurface();
/* 88 */            onSurfaceCreated();
/* 89 */            onSurfaceUpdated();
                }

/* 92 */        public void switchSurfaceToTextureView(TextureView view) throws IOException {
/* 93 */            this.mTextureView = view;
/* 94 */            this.mTextureView.setSurfaceTextureListener(this);
/* 95 */            if (this.mTextureView.isAvailable()) {
/* 96 */                onSurfaceTextureAvailable(view.getSurfaceTexture(), view.getWidth(), view.getHeight());
                    }
                }

/* 100 */       private void switchSurfaceToSurfaceView(SurfaceView view) throws IOException {
/* 101 */           this.mSurfaceHolder = view.getHolder();
/* 102 */           this.mSurfaceHolder.addCallback(this);
                }

/* 105 */       public void setPreviewSurface(View previewView) throws IOException {
/* 106 */           if (previewView instanceof TextureView) {
/* 107 */               switchSurfaceToTextureView((TextureView) previewView);
                    } else {
/* 108 */               if (previewView instanceof SurfaceView) {
/* 109 */                   switchSurfaceToSurfaceView((SurfaceView) previewView);
/* 113 */                   return;
                        }
/* 111 */               throw new IllegalArgumentException("You can't pass in a previewView that's not SurfaceView or TextureView");
                    }
                }

                @Override // android.view.SurfaceHolder.Callback
/* 116 */       public void surfaceDestroyed(SurfaceHolder holder) {
/* 117 */           onSurfaceDestroyed();
                }

/* 120 */       public boolean isSurfaceReady() {
                    return (this.mSurfaceTexture == null && this.mSurface == null) ? false : true;
                }

/* 124 */       private void onSurfaceCreated() {
/* 125 */           SLog.m446d("Surface created...");
/* 126 */           this.mSurfaceListener.onSurfaceCreated(this.mSurfaceTexture, this.mSurface);
                }

/* 129 */       private void onSurfaceDestroyed() {
/* 130 */           this.mSurfaceListener.onSurfaceDestroyed(this.mSurfaceTexture, this.mSurface);
/* 131 */           this.mSurfaceTexture = null;
/* 132 */           this.mSurface = null;
                }

/* 135 */       public void assertValidSurfaces() {
/* 136 */           if (this.mTextureView == null && this.mSurfaceHolder == null) {
/* 137 */               throw new RuntimeException("You have to set a preview surface via switchSurface first.");
                    }
                }

/* 141 */       private void onSurfaceUpdated() {
/* 142 */           this.mSurfaceListener.onSurfaceUpdated(this.mSurfaceTexture, this.mSurface);
                }

/* 145 */       public boolean giveCameraPreview(Camera camera) throws IOException {
/* 146 */           if (this.mSurfaceHolder != null) {
/* 147 */               camera.setPreviewDisplay(this.mSurfaceHolder);
                    } else if (this.mTextureView != null) {
/* 150 */               camera.setPreviewTexture(this.mSurfaceTexture);
                    } else {
/* 152 */               SLog.m446d("Surface is not ready, wait...");
/* 153 */               return false;
                    }
/* 156 */           return true;
                }
            }
