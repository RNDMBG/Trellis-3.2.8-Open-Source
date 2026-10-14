            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.LinearLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class LinearLayoutICS extends LinearLayout {
                private static final int SHOW_DIVIDER_BEGINNING = 1;
                private static final int SHOW_DIVIDER_END = 4;
                private static final int SHOW_DIVIDER_MIDDLE = 2;
                private static final int SHOW_DIVIDER_NONE = 0;
                private final Drawable mDivider;
                private final int mDividerHeight;
                private final int mDividerPadding;
                private final int mDividerWidth;
                private final int mShowDividers;

/* 43 */        public LinearLayoutICS(Context context, AttributeSet attrs) {
/* 44 */            super(context, attrs);
/* 46 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.LinearLayoutICS);
/* 48 */            this.mDivider = a.getDrawable(0);
/* 49 */            if (this.mDivider != null) {
/* 50 */                this.mDividerWidth = this.mDivider.getIntrinsicWidth();
/* 51 */                this.mDividerHeight = this.mDivider.getIntrinsicHeight();
                    } else {
/* 53 */                this.mDividerWidth = 0;
                        this.mDividerHeight = 0;
                    }
/* 56 */            this.mShowDividers = a.getInt(1, 0);
/* 57 */            this.mDividerPadding = a.getDimensionPixelSize(2, 0);
/* 59 */            a.recycle();
/* 61 */            setWillNotDraw(this.mDivider == null);
                }

/* 64 */        public int getSupportDividerWidth() {
/* 65 */            return this.mDividerWidth;
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 69 */        protected void onDraw(Canvas canvas) {
/* 70 */            if (this.mDivider != null) {
/* 74 */                if (getOrientation() == 1) {
/* 75 */                    drawSupportDividersVertical(canvas);
                        } else {
/* 77 */                    drawSupportDividersHorizontal(canvas);
                        }
                    }
                }

                @Override // android.view.ViewGroup
/* 84 */        protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
/* 85 */            if (this.mDivider != null) {
/* 86 */                int childIndex = indexOfChild(child);
/* 87 */                int count = getChildCount();
/* 88 */                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
/* 92 */                if (getOrientation() == 1) {
/* 93 */                    if (hasSupportDividerBeforeChildAt(childIndex)) {
/* 94 */                        params.topMargin = this.mDividerHeight;
                            } else if (childIndex == count - 1 && hasSupportDividerBeforeChildAt(count)) {
/* 96 */                        params.bottomMargin = this.mDividerHeight;
                            }
                        } else if (hasSupportDividerBeforeChildAt(childIndex)) {
/* 100 */                   params.leftMargin = this.mDividerWidth;
                        } else if (childIndex == count - 1 && hasSupportDividerBeforeChildAt(count)) {
/* 102 */                   params.rightMargin = this.mDividerWidth;
                        }
                    }
/* 107 */           super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
                }

/* 111 */       void drawSupportDividersVertical(Canvas canvas) {
                    int bottom;
/* 112 */           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
/* 114 */               View child = getChildAt(i);
/* 115 */               if (child != null && child.getVisibility() != 8 && hasSupportDividerBeforeChildAt(i)) {
/* 117 */                   LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
/* 118 */                   drawSupportHorizontalDivider(canvas, child.getTop() - lp.topMargin);
                        }
                    }
/* 122 */           if (hasSupportDividerBeforeChildAt(count)) {
/* 123 */               View child2 = getChildAt(count - 1);
/* 125 */               if (child2 == null) {
/* 126 */                   bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
                        } else {
/* 128 */                   bottom = child2.getBottom();
                        }
/* 130 */               drawSupportHorizontalDivider(canvas, bottom);
                    }
                }

/* 134 */       void drawSupportDividersHorizontal(Canvas canvas) {
                    int right;
/* 135 */           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
/* 137 */               View child = getChildAt(i);
/* 138 */               if (child != null && child.getVisibility() != 8 && hasSupportDividerBeforeChildAt(i)) {
/* 140 */                   LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
/* 141 */                   drawSupportVerticalDivider(canvas, child.getLeft() - lp.leftMargin);
                        }
                    }
/* 145 */           if (hasSupportDividerBeforeChildAt(count)) {
/* 146 */               View child2 = getChildAt(count - 1);
/* 148 */               if (child2 == null) {
/* 149 */                   right = (getWidth() - getPaddingRight()) - this.mDividerWidth;
                        } else {
/* 151 */                   right = child2.getRight();
                        }
/* 153 */               drawSupportVerticalDivider(canvas, right);
                    }
                }

/* 157 */       void drawSupportHorizontalDivider(Canvas canvas, int top) {
/* 158 */           this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
/* 160 */           this.mDivider.draw(canvas);
                }

/* 163 */       void drawSupportVerticalDivider(Canvas canvas, int left) {
/* 164 */           this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
/* 166 */           this.mDivider.draw(canvas);
                }

/* 175 */       protected boolean hasSupportDividerBeforeChildAt(int childIndex) {
/* 176 */           if (childIndex == 0) {
                        return (this.mShowDividers & 1) != 0;
                    }
/* 178 */           if (childIndex == getChildCount()) {
                        return (this.mShowDividers & 4) != 0;
                    }
/* 180 */           if ((this.mShowDividers & 2) == 0) {
                        return false;
                    }
/* 181 */           boolean hasVisibleViewBefore = false;
                    for (int i = childIndex - 1; i >= 0; i--) {
/* 183 */               if (getChildAt(i).getVisibility() != 8) {
/* 184 */                   hasVisibleViewBefore = true;
                            break;
                        }
                    }
                    return hasVisibleViewBefore;
                }
            }
