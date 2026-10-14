            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.LinearLayout;
            
            
    public class LinearLayoutICS extends LinearLayout {
                private static final int SHOW_DIVIDER_BEGINNING = 1;
                private static final int SHOW_DIVIDER_END = 4;
                private static final int SHOW_DIVIDER_MIDDLE = 2;
                private static final int SHOW_DIVIDER_NONE = 0;
                private final Drawable mDivider;
                private final int mDividerHeight;
                private final int mDividerPadding;
                private final int mDividerWidth;
                private final int mShowDividers;

        public LinearLayoutICS(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.LinearLayoutICS);
            this.mDivider = a.getDrawable(0);
            if (this.mDivider != null) {
                this.mDividerWidth = this.mDivider.getIntrinsicWidth();
                this.mDividerHeight = this.mDivider.getIntrinsicHeight();
                    } else {
                this.mDividerWidth = 0;
                        this.mDividerHeight = 0;
                    }
            this.mShowDividers = a.getInt(1, 0);
            this.mDividerPadding = a.getDimensionPixelSize(2, 0);
            a.recycle();
            setWillNotDraw(this.mDivider == null);
                }

        public int getSupportDividerWidth() {
            return this.mDividerWidth;
                }

                @Override 
        protected void onDraw(Canvas canvas) {
            if (this.mDivider != null) {
                if (getOrientation() == 1) {
                    drawSupportDividersVertical(canvas);
                        } else {
                    drawSupportDividersHorizontal(canvas);
                        }
                    }
                }

                @Override 
        protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            if (this.mDivider != null) {
                int childIndex = indexOfChild(child);
                int count = getChildCount();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
                if (getOrientation() == 1) {
                    if (hasSupportDividerBeforeChildAt(childIndex)) {
                        params.topMargin = this.mDividerHeight;
                            } else if (childIndex == count - 1 && hasSupportDividerBeforeChildAt(count)) {
                        params.bottomMargin = this.mDividerHeight;
                            }
                        } else if (hasSupportDividerBeforeChildAt(childIndex)) {
                   params.leftMargin = this.mDividerWidth;
                        } else if (childIndex == count - 1 && hasSupportDividerBeforeChildAt(count)) {
                   params.rightMargin = this.mDividerWidth;
                        }
                    }
           super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
                }

       void drawSupportDividersVertical(Canvas canvas) {
                    int bottom;
           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
               View child = getChildAt(i);
               if (child != null && child.getVisibility() != 8 && hasSupportDividerBeforeChildAt(i)) {
                   LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                   drawSupportHorizontalDivider(canvas, child.getTop() - lp.topMargin);
                        }
                    }
           if (hasSupportDividerBeforeChildAt(count)) {
               View child2 = getChildAt(count - 1);
               if (child2 == null) {
                   bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
                        } else {
                   bottom = child2.getBottom();
                        }
               drawSupportHorizontalDivider(canvas, bottom);
                    }
                }

       void drawSupportDividersHorizontal(Canvas canvas) {
                    int right;
           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
               View child = getChildAt(i);
               if (child != null && child.getVisibility() != 8 && hasSupportDividerBeforeChildAt(i)) {
                   LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                   drawSupportVerticalDivider(canvas, child.getLeft() - lp.leftMargin);
                        }
                    }
           if (hasSupportDividerBeforeChildAt(count)) {
               View child2 = getChildAt(count - 1);
               if (child2 == null) {
                   right = (getWidth() - getPaddingRight()) - this.mDividerWidth;
                        } else {
                   right = child2.getRight();
                        }
               drawSupportVerticalDivider(canvas, right);
                    }
                }

       void drawSupportHorizontalDivider(Canvas canvas, int top) {
           this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
           this.mDivider.draw(canvas);
                }

       void drawSupportVerticalDivider(Canvas canvas, int left) {
           this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
           this.mDivider.draw(canvas);
                }

       protected boolean hasSupportDividerBeforeChildAt(int childIndex) {
           if (childIndex == 0) {
                        return (this.mShowDividers & 1) != 0;
                    }
           if (childIndex == getChildCount()) {
                        return (this.mShowDividers & 4) != 0;
                    }
           if ((this.mShowDividers & 2) == 0) {
                        return false;
                    }
           boolean hasVisibleViewBefore = false;
                    for (int i = childIndex - 1; i >= 0; i--) {
               if (getChildAt(i).getVisibility() != 8) {
                   hasVisibleViewBefore = true;
                            break;
                        }
                    }
                    return hasVisibleViewBefore;
                }
            }
