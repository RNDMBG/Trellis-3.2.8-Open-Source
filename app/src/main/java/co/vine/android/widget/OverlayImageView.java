            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class OverlayImageView extends ImageView {
                private boolean mFramed;
                private int mImageHeight;
                private int mImageWidth;
                private Drawable mOverlayDrawable;

/* 28 */        public OverlayImageView(Context context) {
/* 29 */            super(context);
/* 30 */            init(context, null);
                }

/* 33 */        public OverlayImageView(Context context, AttributeSet attrs) {
/* 34 */            super(context, attrs);
/* 35 */            init(context, attrs);
                }

/* 38 */        public OverlayImageView(Context context, AttributeSet attrs, int defStyle) {
/* 39 */            super(context, attrs, defStyle);
/* 40 */            init(context, attrs);
                }

/* 43 */        private void init(Context context, AttributeSet attrs) {
/* 44 */            if (attrs != null) {
/* 48 */                TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.OverlayImageView);
/* 49 */                Drawable d = a.getDrawable(0);
/* 50 */                if (d != null) {
/* 51 */                    setOverlayDrawable(d);
                        }
/* 53 */                a.recycle();
                    }
                }

                @Override // android.widget.ImageView, android.view.View
/* 57 */        protected void onDraw(@NonNull Canvas canvas) {
/* 58 */            super.onDraw(canvas);
/* 59 */            Drawable d = this.mOverlayDrawable;
/* 60 */            if (d != null) {
/* 61 */                d.draw(canvas);
                    }
                }

                @Override // android.widget.ImageView, android.view.View
/* 66 */        protected void drawableStateChanged() {
/* 67 */            super.drawableStateChanged();
/* 68 */            Drawable d = this.mOverlayDrawable;
/* 69 */            if (d != null && d.isStateful()) {
/* 70 */                d.setState(getDrawableState());
                    }
                }

                @Override // android.widget.ImageView, android.view.View
/* 75 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 76 */            int imageWidth = this.mImageWidth;
/* 77 */            int imageHeight = this.mImageHeight;
/* 78 */            if (imageWidth > 0 && imageHeight > 0) {
/* 79 */                int width = getDefaultSize(imageWidth, widthMeasureSpec);
/* 80 */                int height = getDefaultSize(imageHeight, heightMeasureSpec);
/* 81 */                int widthScale = imageWidth * height;
/* 82 */                int heightScale = imageHeight * width;
/* 83 */                if (widthScale > heightScale) {
/* 84 */                    height = heightScale / imageWidth;
                        } else if (widthScale < heightScale) {
/* 86 */                    width = widthScale / imageHeight;
                        }
/* 88 */                setMeasuredDimension(width, height);
                    } else {
/* 90 */                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    }
/* 92 */            setDrawableBounds(this.mOverlayDrawable);
                }

/* 95 */        public void setOverlayDrawable(int resId) {
/* 96 */            setOverlayDrawable(getResources().getDrawable(resId));
                }

                @Override // android.widget.ImageView, android.view.View, android.graphics.drawable.Drawable.Callback
/* 100 */       public void invalidateDrawable(@NonNull Drawable dr) {
/* 101 */           if (dr == this.mOverlayDrawable) {
/* 102 */               invalidate();
                    } else {
/* 104 */               super.invalidateDrawable(dr);
                    }
                }

                @Override // android.widget.ImageView
/* 109 */       protected boolean setFrame(int l, int t, int r, int b) {
/* 110 */           boolean changed = super.setFrame(l, t, r, b);
/* 111 */           this.mFramed = true;
/* 112 */           setDrawableBounds(this.mOverlayDrawable);
/* 113 */           return changed;
                }

/* 116 */       private void setDrawableBounds(Drawable d) {
/* 117 */           if (this.mFramed && d != null) {
/* 118 */               int width = getMeasuredWidth();
/* 119 */               int height = getMeasuredHeight();
/* 120 */               d.setBounds(0, 0, width, height);
/* 121 */               invalidate();
                    }
                }

/* 125 */       public void setOverlayDrawable(Drawable d) {
/* 126 */           if (this.mOverlayDrawable != d) {
/* 127 */               if (this.mOverlayDrawable != null) {
/* 128 */                   this.mOverlayDrawable.setCallback(null);
/* 129 */                   unscheduleDrawable(this.mOverlayDrawable);
                        }
/* 131 */               this.mOverlayDrawable = d;
/* 132 */               if (d != null) {
/* 133 */                   d.setCallback(this);
/* 134 */                   if (d.isStateful()) {
/* 135 */                       d.setState(getDrawableState());
                            }
                        }
/* 138 */               requestLayout();
                    }
                }

/* 142 */       public void setImageSize(int width, int height) {
/* 143 */           int oldWidth = this.mImageWidth;
/* 144 */           int oldHeight = this.mImageHeight;
/* 145 */           if (oldWidth != width || oldHeight != height) {
/* 146 */               this.mImageWidth = width;
/* 147 */               this.mImageHeight = height;
/* 148 */               requestLayout();
                    }
                }
            }
