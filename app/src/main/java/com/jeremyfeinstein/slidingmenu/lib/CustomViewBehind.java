            package com.jeremyfeinstein.slidingmenu.lib;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Color;
            import android.graphics.Paint;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.util.TypedValue;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class CustomViewBehind extends ViewGroup {
                private static final int MARGIN_THRESHOLD = 48;
                private static final String TAG = "CustomViewBehind";
                private boolean mChildrenEnabled;
                private View mContent;
                private float mFadeDegree;
                private boolean mFadeEnabled;
                private final Paint mFadePaint;
                private int mMarginThreshold;
                private int mMode;
                private float mScrollScale;
                private View mSecondaryContent;
                private Drawable mSecondaryShadowDrawable;
                private View mSelectedView;
                private Bitmap mSelectorDrawable;
                private boolean mSelectorEnabled;
                private Drawable mShadowDrawable;
                private int mShadowWidth;
                private int mTouchMode;
                private SlidingMenu.CanvasTransformer mTransformer;
                private CustomViewAbove mViewAbove;
                private int mWidthOffset;

/* 36 */        public CustomViewBehind(Context context) {
/* 37 */            this(context, null);
                }

/* 40 */        public CustomViewBehind(Context context, AttributeSet attrs) {
/* 41 */            super(context, attrs);
/* 25 */            this.mTouchMode = 0;
/* 152 */           this.mFadePaint = new Paint();
/* 386 */           this.mSelectorEnabled = true;
/* 42 */            this.mMarginThreshold = (int) TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics());
                }

/* 46 */        public void setCustomViewAbove(CustomViewAbove customViewAbove) {
/* 47 */            this.mViewAbove = customViewAbove;
                }

/* 50 */        public void setCanvasTransformer(SlidingMenu.CanvasTransformer t) {
/* 51 */            this.mTransformer = t;
                }

/* 54 */        public void setWidthOffset(int i) {
/* 55 */            this.mWidthOffset = i;
/* 56 */            requestLayout();
                }

/* 59 */        public void setMarginThreshold(int marginThreshold) {
/* 60 */            this.mMarginThreshold = marginThreshold;
                }

/* 63 */        public int getMarginThreshold() {
/* 64 */            return this.mMarginThreshold;
                }

/* 67 */        public int getBehindWidth() {
/* 68 */            return this.mContent.getWidth();
                }

/* 71 */        public void setContent(View v) {
/* 72 */            if (this.mContent != null) {
/* 73 */                removeView(this.mContent);
                    }
/* 74 */            this.mContent = v;
/* 75 */            addView(this.mContent);
                }

/* 78 */        public View getContent() {
/* 79 */            return this.mContent;
                }

/* 86 */        public void setSecondaryContent(View v) {
/* 87 */            if (this.mSecondaryContent != null) {
/* 88 */                removeView(this.mSecondaryContent);
                    }
/* 89 */            this.mSecondaryContent = v;
/* 90 */            addView(this.mSecondaryContent);
                }

/* 93 */        public View getSecondaryContent() {
/* 94 */            return this.mSecondaryContent;
                }

/* 97 */        public void setChildrenEnabled(boolean enabled) {
/* 98 */            this.mChildrenEnabled = enabled;
                }

                @Override // android.view.View
/* 102 */       public void scrollTo(int x, int y) {
/* 103 */           super.scrollTo(x, y);
/* 104 */           if (this.mTransformer != null) {
/* 105 */               invalidate();
                    }
                }

                @Override // android.view.ViewGroup
/* 109 */       public boolean onInterceptTouchEvent(MotionEvent e) {
                    return !this.mChildrenEnabled;
                }

                @Override // android.view.View
/* 114 */       public boolean onTouchEvent(MotionEvent e) {
                    return !this.mChildrenEnabled;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 119 */       protected void dispatchDraw(Canvas canvas) {
/* 120 */           if (this.mTransformer != null) {
/* 121 */               canvas.save();
/* 122 */               this.mTransformer.transformCanvas(canvas, this.mViewAbove.getPercentOpen());
/* 123 */               super.dispatchDraw(canvas);
/* 124 */               canvas.restore();
/* 127 */               return;
                    }
/* 126 */           super.dispatchDraw(canvas);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 130 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 131 */           int width = r - l;
/* 132 */           int height = b - t;
/* 133 */           this.mContent.layout(0, 0, width - this.mWidthOffset, height);
/* 134 */           if (this.mSecondaryContent != null) {
/* 135 */               this.mSecondaryContent.layout(0, 0, width - this.mWidthOffset, height);
                    }
                }

                @Override // android.view.View
/* 139 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 140 */           int width = getDefaultSize(0, widthMeasureSpec);
/* 141 */           int height = getDefaultSize(0, heightMeasureSpec);
/* 142 */           setMeasuredDimension(width, height);
/* 143 */           int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width - this.mWidthOffset);
/* 144 */           int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
/* 145 */           this.mContent.measure(contentWidth, contentHeight);
/* 146 */           if (this.mSecondaryContent != null) {
/* 147 */               this.mSecondaryContent.measure(contentWidth, contentHeight);
                    }
                }

/* 159 */       public void setMode(int mode) {
/* 160 */           if (mode == 0 || mode == 1) {
/* 161 */               if (this.mContent != null) {
/* 162 */                   this.mContent.setVisibility(0);
                        }
/* 163 */               if (this.mSecondaryContent != null) {
/* 164 */                   this.mSecondaryContent.setVisibility(4);
                        }
                    }
/* 166 */           this.mMode = mode;
                }

/* 169 */       public int getMode() {
/* 170 */           return this.mMode;
                }

/* 173 */       public void setScrollScale(float scrollScale) {
/* 174 */           this.mScrollScale = scrollScale;
                }

/* 177 */       public float getScrollScale() {
/* 178 */           return this.mScrollScale;
                }

/* 181 */       public void setShadowDrawable(Drawable shadow) {
/* 182 */           this.mShadowDrawable = shadow;
/* 183 */           invalidate();
                }

/* 186 */       public void setSecondaryShadowDrawable(Drawable shadow) {
/* 187 */           this.mSecondaryShadowDrawable = shadow;
/* 188 */           invalidate();
                }

/* 191 */       public void setShadowWidth(int width) {
/* 192 */           this.mShadowWidth = width;
/* 193 */           invalidate();
                }

/* 196 */       public void setFadeEnabled(boolean b) {
/* 197 */           this.mFadeEnabled = b;
                }

/* 200 */       public void setFadeDegree(float degree) {
                    if (degree > 1.0f || degree < 0.0f) {
/* 202 */               throw new IllegalStateException("The BehindFadeDegree must be between 0.0f and 1.0f");
                    }
/* 203 */           this.mFadeDegree = degree;
                }

/* 206 */       public int getMenuPage(int page) {
/* 207 */           if (page > 1) {
                        page = 2;
                    } else if (page < 1) {
                        page = 0;
                    }
/* 208 */           if (this.mMode == 0 && page > 1) {
                        return 0;
                    }
/* 210 */           if (this.mMode != 1 || page >= 1) {
                        return page;
                    }
                    return 2;
                }

/* 217 */       public void scrollBehindTo(View content, int x, int y) {
/* 218 */           int vis = 0;
/* 219 */           if (this.mMode == 0) {
/* 220 */               vis = x >= content.getLeft() ? 4 : 0;
/* 221 */               scrollTo((int) ((getBehindWidth() + x) * this.mScrollScale), y);
                    } else if (this.mMode == 1) {
/* 223 */               vis = x <= content.getLeft() ? 4 : 0;
/* 224 */               scrollTo((int) ((getBehindWidth() - getWidth()) + ((x - getBehindWidth()) * this.mScrollScale)), y);
                    } else if (this.mMode == 2) {
/* 227 */               this.mContent.setVisibility(x >= content.getLeft() ? 4 : 0);
/* 228 */               this.mSecondaryContent.setVisibility(x <= content.getLeft() ? 4 : 0);
/* 229 */               vis = x == 0 ? 4 : 0;
/* 230 */               if (x <= content.getLeft()) {
/* 231 */                   scrollTo((int) ((getBehindWidth() + x) * this.mScrollScale), y);
                        } else {
/* 233 */                   scrollTo((int) ((getBehindWidth() - getWidth()) + ((x - getBehindWidth()) * this.mScrollScale)), y);
                        }
                    }
/* 237 */           if (vis == 4) {
/* 238 */               Log.v(TAG, "behind INVISIBLE");
                    }
/* 239 */           setVisibility(vis);
                }

/* 242 */       public int getMenuLeft(View content, int page) {
/* 243 */           if (this.mMode == 0) {
                        switch (page) {
                            case 0:
/* 246 */                       return content.getLeft() - getBehindWidth();
                            case 2:
/* 248 */                       return content.getLeft();
                        }
                    }
/* 250 */           if (this.mMode == 1) {
                        switch (page) {
                            case 0:
/* 253 */                       return content.getLeft();
                            case 2:
/* 255 */                       return content.getLeft() + getBehindWidth();
                        }
                    }
/* 257 */           if (this.mMode == 2) {
                        switch (page) {
                            case 0:
/* 260 */                       return content.getLeft() - getBehindWidth();
                            case 2:
/* 262 */                       return content.getLeft() + getBehindWidth();
                        }
                    }
/* 265 */           return content.getLeft();
                }

/* 268 */       public int getAbsLeftBound(View content) {
/* 269 */           if (this.mMode == 0 || this.mMode == 2) {
/* 270 */               return content.getLeft() - getBehindWidth();
                    }
/* 271 */           if (this.mMode == 1) {
/* 272 */               return content.getLeft();
                    }
/* 274 */           return 0;
                }

/* 277 */       public int getAbsRightBound(View content) {
/* 278 */           if (this.mMode == 0) {
/* 279 */               return content.getLeft();
                    }
/* 280 */           if (this.mMode == 1 || this.mMode == 2) {
/* 281 */               return content.getLeft() + getBehindWidth();
                    }
/* 283 */           return 0;
                }

/* 286 */       public boolean marginTouchAllowed(View content, int x) {
/* 287 */           int left = content.getLeft();
/* 288 */           int right = content.getRight();
/* 289 */           if (this.mMode == 0) {
                        return x >= left && x <= this.mMarginThreshold + left;
                    }
/* 291 */           if (this.mMode == 1) {
                        return x <= right && x >= right - this.mMarginThreshold;
                    }
/* 293 */           if (this.mMode != 2) {
                        return false;
                    }
/* 294 */           if (x < left || x > this.mMarginThreshold + left) {
                        return x <= right && x >= right - this.mMarginThreshold;
                    }
                    return true;
                }

/* 300 */       public void setTouchMode(int i) {
/* 301 */           this.mTouchMode = i;
                }

/* 304 */       public boolean menuOpenTouchAllowed(View content, int currPage, float x) {
                    switch (this.mTouchMode) {
                        case 0:
/* 309 */                   return menuTouchInQuickReturn(content, currPage, x);
                        case 1:
/* 307 */                   return true;
                        default:
/* 311 */                   return false;
                    }
                }

/* 314 */       public boolean menuTouchInQuickReturn(View content, int currPage, float x) {
/* 315 */           if (this.mMode == 0 || (this.mMode == 2 && currPage == 0)) {
                        return x >= ((float) content.getLeft());
                    }
/* 317 */           if (this.mMode == 1 || (this.mMode == 2 && currPage == 2)) {
                        return x <= ((float) content.getRight());
                    }
                    return false;
                }

/* 323 */       public boolean menuClosedSlideAllowed(float dx) {
/* 324 */           if (this.mMode == 0) {
                        return dx > 0.0f;
                    }
/* 326 */           if (this.mMode == 1) {
                        return dx < 0.0f;
                    }
                    return this.mMode == 2;
                }

/* 334 */       public boolean menuOpenSlideAllowed(float dx) {
/* 335 */           if (this.mMode == 0) {
                        return dx < 0.0f;
                    }
/* 337 */           if (this.mMode == 1) {
                        return dx > 0.0f;
                    }
                    return this.mMode == 2;
                }

/* 345 */       public void drawShadow(View content, Canvas canvas) {
/* 346 */           if (this.mShadowDrawable != null && this.mShadowWidth > 0) {
/* 347 */               int left = 0;
/* 348 */               if (this.mMode == 0) {
/* 349 */                   left = content.getLeft() - this.mShadowWidth;
                        } else if (this.mMode == 1) {
/* 351 */                   left = content.getRight();
                        } else if (this.mMode == 2) {
/* 353 */                   if (this.mSecondaryShadowDrawable != null) {
/* 354 */                       int left2 = content.getRight();
/* 355 */                       this.mSecondaryShadowDrawable.setBounds(left2, 0, this.mShadowWidth + left2, getHeight());
/* 356 */                       this.mSecondaryShadowDrawable.draw(canvas);
                            }
/* 358 */                   left = content.getLeft() - this.mShadowWidth;
                        }
/* 360 */               this.mShadowDrawable.setBounds(left, 0, this.mShadowWidth + left, getHeight());
/* 361 */               this.mShadowDrawable.draw(canvas);
                    }
                }

/* 364 */       public void drawFade(View content, Canvas canvas, float openPercent) {
/* 365 */           if (this.mFadeEnabled) {
/* 366 */               int alpha = (int) (this.mFadeDegree * 255.0f * Math.abs(1.0f - openPercent));
/* 367 */               this.mFadePaint.setColor(Color.argb(alpha, 0, 0, 0));
/* 368 */               int left = 0;
/* 369 */               int right = 0;
/* 370 */               if (this.mMode == 0) {
/* 371 */                   left = content.getLeft() - getBehindWidth();
/* 372 */                   right = content.getLeft();
                        } else if (this.mMode == 1) {
/* 374 */                   left = content.getRight();
/* 375 */                   right = content.getRight() + getBehindWidth();
                        } else if (this.mMode == 2) {
/* 377 */                   int left2 = content.getLeft() - getBehindWidth();
/* 378 */                   int right2 = content.getLeft();
/* 379 */                   canvas.drawRect(left2, 0.0f, right2, getHeight(), this.mFadePaint);
/* 380 */                   left = content.getRight();
/* 381 */                   right = content.getRight() + getBehindWidth();
                        }
/* 383 */               canvas.drawRect(left, 0.0f, right, getHeight(), this.mFadePaint);
                    }
                }

/* 390 */       public void drawSelector(View content, Canvas canvas, float openPercent) {
/* 391 */           if (this.mSelectorEnabled && this.mSelectorDrawable != null && this.mSelectedView != null) {
/* 393 */               String tag = (String) this.mSelectedView.getTag(C0257R.id.selected_view);
/* 394 */               if (tag.equals("CustomViewBehindSelectedView")) {
/* 395 */                   canvas.save();
/* 397 */                   int offset = (int) (this.mSelectorDrawable.getWidth() * openPercent);
/* 398 */                   if (this.mMode == 0) {
/* 399 */                       int right = content.getLeft();
/* 400 */                       int left = right - offset;
/* 401 */                       canvas.clipRect(left, 0, right, getHeight());
/* 402 */                       canvas.drawBitmap(this.mSelectorDrawable, left, getSelectorTop(), (Paint) null);
                            } else if (this.mMode == 1) {
/* 404 */                       int left2 = content.getRight();
/* 405 */                       int right2 = left2 + offset;
/* 406 */                       canvas.clipRect(left2, 0, right2, getHeight());
/* 407 */                       canvas.drawBitmap(this.mSelectorDrawable, right2 - this.mSelectorDrawable.getWidth(), getSelectorTop(), (Paint) null);
                            }
/* 409 */                   canvas.restore();
                        }
                    }
                }

/* 414 */       public void setSelectorEnabled(boolean b) {
/* 415 */           this.mSelectorEnabled = b;
                }

/* 418 */       public void setSelectedView(View v) {
/* 419 */           if (this.mSelectedView != null) {
/* 420 */               this.mSelectedView.setTag(C0257R.id.selected_view, null);
/* 421 */               this.mSelectedView = null;
                    }
/* 423 */           if (v != null && v.getParent() != null) {
/* 424 */               this.mSelectedView = v;
/* 425 */               this.mSelectedView.setTag(C0257R.id.selected_view, "CustomViewBehindSelectedView");
/* 426 */               invalidate();
                    }
                }

/* 430 */       private int getSelectorTop() {
/* 431 */           int y = this.mSelectedView.getTop();
/* 432 */           return y + ((this.mSelectedView.getHeight() - this.mSelectorDrawable.getHeight()) / 2);
                }

/* 436 */       public void setSelectorBitmap(Bitmap b) {
/* 437 */           this.mSelectorDrawable = b;
/* 438 */           refreshDrawableState();
                }
            }
