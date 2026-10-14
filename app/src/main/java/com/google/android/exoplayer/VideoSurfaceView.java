            package com.google.android.exoplayer;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.SurfaceView;
            
            
    public class VideoSurfaceView extends SurfaceView {
                private static final float MAX_ASPECT_RATIO_DEFORMATION_PERCENT = 0.01f;
                private float videoAspectRatio;

        public VideoSurfaceView(Context context) {
            super(context);
                }

        public VideoSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public void setVideoWidthHeightRatio(float widthHeightRatio) {
                    if (this.videoAspectRatio != widthHeightRatio) {
                this.videoAspectRatio = widthHeightRatio;
                requestLayout();
                    }
                }

                @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
                    if (this.videoAspectRatio != 0.0f) {
                float viewAspectRatio = width / height;
                float aspectDeformation = (this.videoAspectRatio / viewAspectRatio) - 1.0f;
                        if (aspectDeformation > MAX_ASPECT_RATIO_DEFORMATION_PERCENT) {
                    height = (int) (width / this.videoAspectRatio);
                        } else if (aspectDeformation < -0.01f) {
                    width = (int) (height * this.videoAspectRatio);
                        }
                    }
            setMeasuredDimension(width, height);
                }
            }
