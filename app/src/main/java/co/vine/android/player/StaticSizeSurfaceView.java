            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.SurfaceView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 20 */    public class StaticSizeSurfaceView extends SurfaceView {
                private int mHeight;
                private int mWidth;

/* 21 */        public StaticSizeSurfaceView(Context context) {
/* 22 */            super(context);
/* 18 */            this.mWidth = 0;
/* 19 */            this.mHeight = 0;
                }

/* 25 */        public StaticSizeSurfaceView(Context context, AttributeSet attrs) {
/* 26 */            super(context, attrs);
/* 18 */            this.mWidth = 0;
/* 19 */            this.mHeight = 0;
                }

/* 29 */        public StaticSizeSurfaceView(Context context, AttributeSet attrs, int defStyle) {
/* 30 */            super(context, attrs, defStyle);
/* 18 */            this.mWidth = 0;
/* 19 */            this.mHeight = 0;
                }

/* 33 */        public void setSize(int width, int height) {
/* 34 */            this.mWidth = width;
/* 35 */            this.mHeight = height;
                }

                @Override // android.view.SurfaceView, android.view.View
/* 39 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 40 */            setMeasuredDimension(this.mWidth, this.mHeight);
                }
            }
