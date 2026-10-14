            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.util.AttributeSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 19 */    public class StaticSizeVideoView extends SdkVideoView {
                private int mHeight;
                private int mWidth;

/* 20 */        public StaticSizeVideoView(Context context) {
/* 21 */            super(context);
/* 17 */            this.mWidth = 0;
/* 18 */            this.mHeight = 0;
                }

/* 24 */        public StaticSizeVideoView(Context context, AttributeSet attrs) {
/* 25 */            super(context, attrs);
/* 17 */            this.mWidth = 0;
/* 18 */            this.mHeight = 0;
                }

/* 28 */        public StaticSizeVideoView(Context context, AttributeSet attrs, int defStyle) {
/* 29 */            super(context, attrs, defStyle);
/* 17 */            this.mWidth = 0;
/* 18 */            this.mHeight = 0;
                }

/* 32 */        public void setSize(int width, int height) {
/* 33 */            this.mWidth = width;
/* 34 */            this.mHeight = height;
                }

                @Override // co.vine.android.player.SdkVideoView, android.view.View
/* 38 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 39 */            setMeasuredDimension(this.mWidth, this.mHeight);
                }
            }
