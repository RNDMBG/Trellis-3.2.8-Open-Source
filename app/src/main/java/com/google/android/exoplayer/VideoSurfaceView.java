            package com.google.android.exoplayer;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.SurfaceView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class VideoSurfaceView extends SurfaceView {
                private static final float MAX_ASPECT_RATIO_DEFORMATION_PERCENT = 0.01f;
                private float videoAspectRatio;

/* 40 */        public VideoSurfaceView(Context context) {
/* 41 */            super(context);
                }

/* 44 */        public VideoSurfaceView(Context context, AttributeSet attrs) {
/* 45 */            super(context, attrs);
                }

/* 53 */        public void setVideoWidthHeightRatio(float widthHeightRatio) {
                    if (this.videoAspectRatio != widthHeightRatio) {
/* 55 */                this.videoAspectRatio = widthHeightRatio;
/* 56 */                requestLayout();
                    }
                }

                @Override // android.view.SurfaceView, android.view.View
/* 61 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 62 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 63 */            int width = getMeasuredWidth();
/* 64 */            int height = getMeasuredHeight();
                    if (this.videoAspectRatio != 0.0f) {
/* 66 */                float viewAspectRatio = width / height;
/* 67 */                float aspectDeformation = (this.videoAspectRatio / viewAspectRatio) - 1.0f;
                        if (aspectDeformation > MAX_ASPECT_RATIO_DEFORMATION_PERCENT) {
/* 69 */                    height = (int) (width / this.videoAspectRatio);
                        } else if (aspectDeformation < -0.01f) {
/* 71 */                    width = (int) (height * this.videoAspectRatio);
                        }
                    }
/* 74 */            setMeasuredDimension(width, height);
                }
            }
