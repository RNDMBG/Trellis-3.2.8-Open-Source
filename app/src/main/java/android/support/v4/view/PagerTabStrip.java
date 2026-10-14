            package android.support.v4.view;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 74 */    public class PagerTabStrip extends PagerTitleStrip {
                private static final int FULL_UNDERLINE_HEIGHT = 1;
                private static final int INDICATOR_HEIGHT = 3;
                private static final int MIN_PADDING_BOTTOM = 6;
                private static final int MIN_STRIP_HEIGHT = 32;
                private static final int MIN_TEXT_SPACING = 64;
                private static final int TAB_PADDING = 16;
                private static final int TAB_SPACING = 32;
                private static final String TAG = "PagerTabStrip";
                private boolean mDrawFullUnderline;
                private boolean mDrawFullUnderlineSet;
                private int mFullUnderlineHeight;
                private boolean mIgnoreTap;
                private int mIndicatorColor;
                private int mIndicatorHeight;
                private float mInitialMotionX;
                private float mInitialMotionY;
                private int mMinPaddingBottom;
                private int mMinStripHeight;
                private int mMinTextSpacing;
                private int mTabAlpha;
                private int mTabPadding;
                private final Paint mTabPaint;
                private final Rect mTempRect;
                private int mTouchSlop;

/* 75 */        public PagerTabStrip(Context context) {
/* 76 */            this(context, null);
                }

/* 79 */        public PagerTabStrip(Context context, AttributeSet attrs) {
/* 80 */            super(context, attrs);
/* 61 */            this.mTabPaint = new Paint();
/* 62 */            this.mTempRect = new Rect();
/* 64 */            this.mTabAlpha = 255;
/* 66 */            this.mDrawFullUnderline = false;
/* 67 */            this.mDrawFullUnderlineSet = false;
/* 82 */            this.mIndicatorColor = this.mTextColor;
/* 83 */            this.mTabPaint.setColor(this.mIndicatorColor);
/* 87 */            float density = context.getResources().getDisplayMetrics().density;
/* 88 */            this.mIndicatorHeight = (int) ((3.0f * density) + 0.5f);
/* 89 */            this.mMinPaddingBottom = (int) ((6.0f * density) + 0.5f);
/* 90 */            this.mMinTextSpacing = (int) (64.0f * density);
/* 91 */            this.mTabPadding = (int) ((16.0f * density) + 0.5f);
/* 92 */            this.mFullUnderlineHeight = (int) ((1.0f * density) + 0.5f);
/* 93 */            this.mMinStripHeight = (int) ((32.0f * density) + 0.5f);
/* 94 */            this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
/* 97 */            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
/* 98 */            setTextSpacing(getTextSpacing());
/* 100 */           setWillNotDraw(false);
/* 102 */           this.mPrevText.setFocusable(true);
/* 103 */           this.mPrevText.setOnClickListener(new ViewOnClickListenerC00471());
/* 110 */           this.mNextText.setFocusable(true);
/* 111 */           this.mNextText.setOnClickListener(new ViewOnClickListenerC00482());
/* 118 */           if (getBackground() == null) {
/* 119 */               this.mDrawFullUnderline = true;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.PagerTabStrip$1 */
/* 101 */       class ViewOnClickListenerC00471 implements View.OnClickListener {
/* 102 */           ViewOnClickListenerC00471() {
                    }

                    @Override // android.view.View.OnClickListener
/* 105 */           public void onClick(View v) {
/* 106 */               PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.PagerTabStrip$2 */
/* 109 */       class ViewOnClickListenerC00482 implements View.OnClickListener {
/* 110 */           ViewOnClickListenerC00482() {
                    }

                    @Override // android.view.View.OnClickListener
/* 113 */           public void onClick(View v) {
/* 114 */               PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() + 1);
                    }
                }

/* 128 */       public void setTabIndicatorColor(int color) {
/* 129 */           this.mIndicatorColor = color;
/* 130 */           this.mTabPaint.setColor(this.mIndicatorColor);
/* 131 */           invalidate();
                }

/* 139 */       public void setTabIndicatorColorResource(int resId) {
/* 140 */           setTabIndicatorColor(getContext().getResources().getColor(resId));
                }

/* 146 */       public int getTabIndicatorColor() {
/* 147 */           return this.mIndicatorColor;
                }

                @Override // android.view.View
/* 151 */       public void setPadding(int left, int top, int right, int bottom) {
/* 152 */           if (bottom < this.mMinPaddingBottom) {
/* 153 */               bottom = this.mMinPaddingBottom;
                    }
/* 155 */           super.setPadding(left, top, right, bottom);
                }

                @Override // android.support.v4.view.PagerTitleStrip
/* 159 */       public void setTextSpacing(int textSpacing) {
/* 160 */           if (textSpacing < this.mMinTextSpacing) {
/* 161 */               textSpacing = this.mMinTextSpacing;
                    }
/* 163 */           super.setTextSpacing(textSpacing);
                }

                @Override // android.view.View
/* 167 */       public void setBackgroundDrawable(Drawable d) {
/* 168 */           super.setBackgroundDrawable(d);
/* 169 */           if (!this.mDrawFullUnderlineSet) {
/* 170 */               this.mDrawFullUnderline = d == null;
                    }
                }

                @Override // android.view.View
/* 175 */       public void setBackgroundColor(int color) {
/* 176 */           super.setBackgroundColor(color);
/* 177 */           if (!this.mDrawFullUnderlineSet) {
/* 178 */               this.mDrawFullUnderline = ((-16777216) & color) == 0;
                    }
                }

                @Override // android.view.View
/* 183 */       public void setBackgroundResource(int resId) {
/* 184 */           super.setBackgroundResource(resId);
/* 185 */           if (!this.mDrawFullUnderlineSet) {
/* 186 */               this.mDrawFullUnderline = resId == 0;
                    }
                }

/* 196 */       public void setDrawFullUnderline(boolean drawFull) {
/* 197 */           this.mDrawFullUnderline = drawFull;
/* 198 */           this.mDrawFullUnderlineSet = true;
/* 199 */           invalidate();
                }

/* 209 */       public boolean getDrawFullUnderline() {
/* 210 */           return this.mDrawFullUnderline;
                }

                @Override // android.support.v4.view.PagerTitleStrip
/* 214 */       int getMinHeight() {
/* 215 */           return Math.max(super.getMinHeight(), this.mMinStripHeight);
                }

                @Override // android.view.View
/* 219 */       public boolean onTouchEvent(MotionEvent ev) {
/* 220 */           int action = ev.getAction();
/* 221 */           if (action != 0 && this.mIgnoreTap) {
                        return false;
                    }
/* 227 */           float x = ev.getX();
/* 228 */           float y = ev.getY();
                    switch (action) {
                        case 0:
/* 231 */                   this.mInitialMotionX = x;
/* 232 */                   this.mInitialMotionY = y;
/* 233 */                   this.mIgnoreTap = false;
                            break;
                        case 1:
                            if (x < this.mCurrText.getLeft() - this.mTabPadding) {
/* 245 */                       this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                            } else if (x > this.mCurrText.getRight() + this.mTabPadding) {
/* 247 */                       this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1);
                            }
                            break;
                        case 2:
                            if (Math.abs(x - this.mInitialMotionX) > this.mTouchSlop || Math.abs(y - this.mInitialMotionY) > this.mTouchSlop) {
/* 239 */                       this.mIgnoreTap = true;
                            }
                            break;
                    }
                    return true;
                }

                @Override // android.view.View
/* 256 */       protected void onDraw(Canvas canvas) {
/* 257 */           super.onDraw(canvas);
/* 259 */           int height = getHeight();
/* 261 */           int left = this.mCurrText.getLeft() - this.mTabPadding;
/* 262 */           int right = this.mCurrText.getRight() + this.mTabPadding;
/* 260 */           int top = height - this.mIndicatorHeight;
/* 265 */           this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
/* 260 */           canvas.drawRect(left, top, right, height, this.mTabPaint);
/* 268 */           if (this.mDrawFullUnderline) {
/* 269 */               this.mTabPaint.setColor((-16777216) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
/* 270 */               canvas.drawRect(getPaddingLeft(), height - this.mFullUnderlineHeight, getWidth() - getPaddingRight(), height, this.mTabPaint);
                    }
                }

                @Override // android.support.v4.view.PagerTitleStrip
/* 276 */       void updateTextPositions(int position, float positionOffset, boolean force) {
/* 277 */           Rect r = this.mTempRect;
/* 278 */           int bottom = getHeight();
/* 279 */           int left = this.mCurrText.getLeft() - this.mTabPadding;
/* 280 */           int right = this.mCurrText.getRight() + this.mTabPadding;
/* 281 */           int top = bottom - this.mIndicatorHeight;
/* 283 */           r.set(left, top, right, bottom);
/* 285 */           super.updateTextPositions(position, positionOffset, force);
/* 286 */           this.mTabAlpha = (int) (Math.abs(positionOffset - 0.5f) * 2.0f * 255.0f);
/* 288 */           int left2 = this.mCurrText.getLeft() - this.mTabPadding;
/* 289 */           int right2 = this.mCurrText.getRight() + this.mTabPadding;
/* 290 */           r.union(left2, top, right2, bottom);
/* 292 */           invalidate(r);
                }
            }
