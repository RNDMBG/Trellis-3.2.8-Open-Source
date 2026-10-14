            package co.vine.android.widget.trimControls;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.os.Handler;
            import android.view.View;
            import co.vine.android.C0257R;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class ThumbnailCarousel extends View {
                public static final int NUM_VISIBLE_THUMBNAILS = 8;
                private int mLeftPadding;
                private WidthMeasuredListener mListener;
                private int mThumbnailWidth;
                private ArrayList<Bitmap> mThumbnails;
                private Handler mUiHandler;
                private int mWidth;
                public int preferredHeight;

                public interface WidthMeasuredListener {
                    void onWidthMeasured(int i);
                }

/* 17 */        static /* synthetic */ ArrayList access$000(ThumbnailCarousel x0) {
/* 18 */            return x0.mThumbnails;
                }

/* 32 */        public ThumbnailCarousel(Context context, int paddingLeft) {
/* 33 */            super(context, null);
/* 34 */            init(context, paddingLeft);
                }

/* 37 */        private void init(Context context, int paddingLeft) {
/* 38 */            this.mThumbnails = new ArrayList<>();
/* 39 */            this.preferredHeight = getResources().getDimensionPixelSize(C0257R.dimen.carousel_height);
/* 40 */            this.mUiHandler = new Handler(context.getMainLooper());
/* 41 */            this.mLeftPadding = paddingLeft;
/* 42 */            setPadding(paddingLeft, 0, 0, 0);
                }

/* 45 */        public void updateLayout(int desiredThumbWidth, long durationMs, long maxDurationMs) {
/* 46 */            this.mThumbnailWidth = desiredThumbWidth;
/* 47 */            float thumbnailInterval = maxDurationMs / 8.0f;
/* 48 */            int numThumbnails = (int) (durationMs / thumbnailInterval);
/* 49 */            this.mWidth = (this.mThumbnailWidth * numThumbnails) + this.mLeftPadding;
/* 50 */            requestLayout();
                }

/* 53 */        public void setListener(WidthMeasuredListener listener) {
/* 54 */            this.mListener = listener;
                }

                @Override // android.view.View
/* 58 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 59 */            int widthSpec = this.mWidth > 0 ? View.MeasureSpec.makeMeasureSpec(this.mWidth + getPaddingRight(), 1073741824) : widthMeasureSpec;
/* 61 */            super.onMeasure(widthSpec, heightMeasureSpec);
/* 62 */            setMeasuredDimension(widthSpec, heightMeasureSpec);
/* 63 */            if (this.mWidth > 0 && this.mListener != null) {
/* 64 */                this.mListener.onWidthMeasured(this.mWidth);
                    }
                }

                @Override // android.view.View
/* 69 */        protected void onDraw(Canvas canvas) {
/* 70 */            super.onDraw(canvas);
/* 71 */            if (!this.mThumbnails.isEmpty()) {
/* 72 */                int counter = 0;
                        for (Bitmap thumbnail : this.mThumbnails) {
/* 74 */                    if (thumbnail != null && !thumbnail.isRecycled()) {
/* 75 */                        canvas.drawBitmap(thumbnail, getPaddingLeft() + (this.mThumbnailWidth * counter), getTop(), (Paint) null);
                            }
/* 77 */                    counter++;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.trimControls.ThumbnailCarousel$1 */
/* 81 */        class RunnableC05811 implements Runnable {
                    final /* synthetic */ Bitmap val$bitmap;

/* 82 */            RunnableC05811(Bitmap bitmap) {
                        this.val$bitmap = bitmap;
                    }

                    @Override // java.lang.Runnable
/* 85 */            public void run() {
/* 86 */                if (this.val$bitmap == null) {
/* 87 */                    if (ThumbnailCarousel.access$000(ThumbnailCarousel.this).size() > 0) {
/* 88 */                        Bitmap altBitmap = (Bitmap) ThumbnailCarousel.access$000(ThumbnailCarousel.this).get(ThumbnailCarousel.access$000(ThumbnailCarousel.this).size() - 1);
/* 89 */                        ThumbnailCarousel.access$000(ThumbnailCarousel.this).add(altBitmap);
                            }
                        } else {
/* 92 */                    ThumbnailCarousel.access$000(ThumbnailCarousel.this).add(this.val$bitmap);
                        }
/* 94 */                ThumbnailCarousel.this.invalidate();
                    }
                }

/* 82 */        public synchronized void addThumbnail(Bitmap bitmap) {
                    try {
                        this.mUiHandler.post(new RunnableC05811(bitmap));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 99 */        public void addScrolledViewPadding(int paddingLeft, int paddingRight) {
/* 100 */           setPadding(paddingLeft, 0, paddingRight, 0);
                }

/* 103 */       public void releaseBitmaps() {
                    for (Bitmap b : this.mThumbnails) {
/* 105 */               if (b != null && !b.isRecycled()) {
/* 106 */                   b.recycle();
                        }
                    }
                }

/* 111 */       public int getThumbnailStripWidth() {
/* 112 */           return this.mWidth;
                }
            }
