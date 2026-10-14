            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class ColorPicker extends ViewGroup implements View.OnClickListener {
                private int mCircleWidth;
                private ColorClickListener mColorClickListener;
                private int mLineCount;
                private int mMinPadding;
                private ColorPickerCircle mSelected;

                public interface ColorClickListener {
                    void onColorClick(int i);
                }

/* 29 */        public ColorPicker(Context context) {
/* 30 */            this(context, null);
                }

/* 33 */        public ColorPicker(Context context, AttributeSet attrs) {
/* 34 */            this(context, attrs, 0);
                }

/* 37 */        public ColorPicker(Context context, AttributeSet attrs, int defStyle) {
/* 38 */            super(context, attrs, defStyle);
/* 39 */            Resources res = context.getResources();
/* 40 */            this.mMinPadding = res.getDimensionPixelOffset(C0257R.dimen.color_picker_min_padding);
                }

                @Override // android.view.View
/* 44 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 45 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 46 */            int count = Settings.PROFILE_BACKGROUND_COLORS.length;
/* 49 */            int lineCount = count / 2;
/* 50 */            this.mLineCount = lineCount;
/* 52 */            int thisWidth = getMeasuredWidth();
/* 53 */            int eachWidth = (thisWidth / lineCount) - (this.mMinPadding * 2);
/* 54 */            int height = (eachWidth * 2) + (this.mMinPadding * 4);
                    for (int i = 0; i < getChildCount(); i++) {
/* 57 */                View child = getChildAt(i);
/* 58 */                measureChild(child, View.MeasureSpec.makeMeasureSpec(eachWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(eachWidth, 1073741824));
                    }
/* 63 */            this.mCircleWidth = eachWidth;
/* 64 */            setMeasuredDimension(thisWidth, height);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 68 */        protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 69 */            int top = this.mMinPadding * 2;
/* 70 */            int width = getMeasuredWidth();
/* 73 */            int leftPadding = (width - (this.mLineCount * (this.mCircleWidth + this.mMinPadding))) / 2;
/* 74 */            int left = leftPadding;
/* 76 */            boolean newline = false;
                    for (int i = 0; i < getChildCount(); i++) {
/* 78 */                ColorPickerCircle circle = (ColorPickerCircle) getChildAt(i);
/* 79 */                circle.layout(left, top, this.mCircleWidth + left, this.mCircleWidth + top);
/* 80 */                circle.postIndexChange(i);
/* 81 */                circle.setOnClickListener(this);
/* 82 */                left += this.mCircleWidth + this.mMinPadding;
/* 84 */                if (i >= this.mLineCount - 1 && !newline) {
/* 85 */                    top += this.mCircleWidth + this.mMinPadding;
/* 86 */                    left = leftPadding;
/* 87 */                    newline = true;
                        }
                    }
                }

/* 92 */        public void setOnColorClickListener(ColorClickListener ccl) {
/* 93 */            this.mColorClickListener = ccl;
                }

                @Override // android.view.View.OnClickListener
/* 97 */        public void onClick(View v) {
/* 98 */            ColorPickerCircle c = (ColorPickerCircle) v;
/* 99 */            if (this.mSelected != null) {
/* 100 */               this.mSelected.setSelected(false);
                    }
/* 102 */           this.mSelected = c;
/* 103 */           this.mSelected.setSelected(true);
/* 105 */           if (this.mColorClickListener != null) {
/* 108 */               this.mColorClickListener.onColorClick(c.getIndex());
                    }
                }

/* 111 */       public void setColorIndex(int index) {
/* 112 */           if (index >= 0 && index < getChildCount()) {
                        for (int i = 0; i < getChildCount(); i++) {
/* 114 */                   if (index == i) {
/* 115 */                       View child = getChildAt(index);
/* 116 */                       child.setSelected(true);
/* 117 */                       this.mSelected = (ColorPickerCircle) child;
                            } else {
/* 119 */                       getChildAt(i).setSelected(false);
                            }
                        }
                    }
                }
            }
