            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.util.AttributeSet;
            
            
            @TargetApi(14)
    public class StaticSizeVideoView extends SdkVideoView {
                private int mHeight;
                private int mWidth;

        public StaticSizeVideoView(Context context) {
            super(context);
            this.mWidth = 0;
            this.mHeight = 0;
                }

        public StaticSizeVideoView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mWidth = 0;
            this.mHeight = 0;
                }

        public StaticSizeVideoView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mWidth = 0;
            this.mHeight = 0;
                }

        public void setSize(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
                }

                @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(this.mWidth, this.mHeight);
                }
            }
