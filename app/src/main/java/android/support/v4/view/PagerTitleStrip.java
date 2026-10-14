            package android.support.v4.view;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.TextView;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class PagerTitleStrip extends ViewGroup implements ViewPager.Decor {
                private static final PagerTitleStripImpl IMPL;
                private static final float SIDE_ALPHA = 0.6f;
                private static final String TAG = "PagerTitleStrip";
                private static final int TEXT_SPACING = 16;
                TextView mCurrText;
                private int mGravity;
                private int mLastKnownCurrentPage;
                private float mLastKnownPositionOffset;
                TextView mNextText;
                private int mNonPrimaryAlpha;
                private final PageListener mPageListener;
                ViewPager mPager;
                TextView mPrevText;
                private int mScaledTextSpacing;
                int mTextColor;
                private boolean mUpdatingPositions;
                private boolean mUpdatingText;
                private WeakReference<PagerAdapter> mWatchingAdapter;
                private static final int[] ATTRS = {R.attr.textAppearance, R.attr.textSize, R.attr.textColor, R.attr.gravity};
                private static final int[] TEXT_ATTRS = {R.attr.textAllCaps};

                interface PagerTitleStripImpl {
                    void setSingleLineAllCaps(TextView textView);
                }

/* 44 */        static /* synthetic */ float access$100(PagerTitleStrip x0) {
/* 45 */            return x0.mLastKnownPositionOffset;
                }

                static {
/* 100 */           if (Build.VERSION.SDK_INT >= 14) {
/* 101 */               IMPL = new PagerTitleStripImplIcs();
                    } else {
/* 103 */               IMPL = new PagerTitleStripImplBase();
                    }
                }

/* 84 */        static class PagerTitleStripImplBase implements PagerTitleStripImpl {
/* 85 */            PagerTitleStripImplBase() {
                    }

                    @Override // android.support.v4.view.PagerTitleStrip.PagerTitleStripImpl
/* 87 */            public void setSingleLineAllCaps(TextView text) {
/* 88 */                text.setSingleLine();
                    }
                }

/* 90 */        static class PagerTitleStripImplIcs implements PagerTitleStripImpl {
/* 91 */            PagerTitleStripImplIcs() {
                    }

                    @Override // android.support.v4.view.PagerTitleStrip.PagerTitleStripImpl
/* 93 */            public void setSingleLineAllCaps(TextView text) {
/* 94 */                PagerTitleStripIcs.setSingleLineAllCaps(text);
                    }
                }

/* 107 */       private static void setSingleLineAllCaps(TextView text) {
/* 108 */           IMPL.setSingleLineAllCaps(text);
                }

/* 111 */       public PagerTitleStrip(Context context) {
/* 112 */           this(context, null);
                }

/* 115 */       public PagerTitleStrip(Context context, AttributeSet attrs) {
/* 116 */           super(context, attrs);
/* 53 */            this.mLastKnownCurrentPage = -1;
/* 54 */            this.mLastKnownPositionOffset = -1.0f;
/* 61 */            this.mPageListener = new PageListener(this, null);
/* 118 */           TextView textView = new TextView(context);
                    this.mPrevText = textView;
                    addView(textView);
/* 119 */           TextView textView2 = new TextView(context);
                    this.mCurrText = textView2;
                    addView(textView2);
/* 120 */           TextView textView3 = new TextView(context);
                    this.mNextText = textView3;
                    addView(textView3);
/* 122 */           TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
/* 123 */           int textAppearance = a.getResourceId(0, 0);
/* 124 */           if (textAppearance != 0) {
/* 125 */               this.mPrevText.setTextAppearance(context, textAppearance);
/* 126 */               this.mCurrText.setTextAppearance(context, textAppearance);
/* 127 */               this.mNextText.setTextAppearance(context, textAppearance);
                    }
/* 129 */           int textSize = a.getDimensionPixelSize(1, 0);
/* 130 */           if (textSize != 0) {
/* 131 */               setTextSize(0, textSize);
                    }
/* 133 */           if (a.hasValue(2)) {
/* 134 */               int textColor = a.getColor(2, 0);
/* 135 */               this.mPrevText.setTextColor(textColor);
/* 136 */               this.mCurrText.setTextColor(textColor);
/* 137 */               this.mNextText.setTextColor(textColor);
                    }
/* 139 */           this.mGravity = a.getInteger(3, 80);
/* 140 */           a.recycle();
/* 142 */           this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
/* 143 */           setNonPrimaryAlpha(SIDE_ALPHA);
/* 145 */           this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
/* 146 */           this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
/* 147 */           this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
/* 149 */           boolean allCaps = false;
/* 150 */           if (textAppearance != 0) {
/* 151 */               TypedArray ta = context.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
/* 152 */               allCaps = ta.getBoolean(0, false);
/* 153 */               ta.recycle();
                    }
/* 156 */           if (allCaps) {
/* 157 */               setSingleLineAllCaps(this.mPrevText);
/* 158 */               setSingleLineAllCaps(this.mCurrText);
/* 159 */               setSingleLineAllCaps(this.mNextText);
                    } else {
/* 161 */               this.mPrevText.setSingleLine();
/* 162 */               this.mCurrText.setSingleLine();
/* 163 */               this.mNextText.setSingleLine();
                    }
/* 166 */           float density = context.getResources().getDisplayMetrics().density;
/* 167 */           this.mScaledTextSpacing = (int) (16.0f * density);
                }

/* 175 */       public void setTextSpacing(int spacingPixels) {
/* 176 */           this.mScaledTextSpacing = spacingPixels;
/* 177 */           requestLayout();
                }

/* 183 */       public int getTextSpacing() {
/* 184 */           return this.mScaledTextSpacing;
                }

/* 192 */       public void setNonPrimaryAlpha(float alpha) {
/* 193 */           this.mNonPrimaryAlpha = ((int) (255.0f * alpha)) & 255;
/* 194 */           int transparentColor = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
/* 195 */           this.mPrevText.setTextColor(transparentColor);
/* 196 */           this.mNextText.setTextColor(transparentColor);
                }

/* 205 */       public void setTextColor(int color) {
/* 206 */           this.mTextColor = color;
/* 207 */           this.mCurrText.setTextColor(color);
/* 208 */           int transparentColor = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
/* 209 */           this.mPrevText.setTextColor(transparentColor);
/* 210 */           this.mNextText.setTextColor(transparentColor);
                }

/* 223 */       public void setTextSize(int unit, float size) {
/* 224 */           this.mPrevText.setTextSize(unit, size);
/* 225 */           this.mCurrText.setTextSize(unit, size);
/* 226 */           this.mNextText.setTextSize(unit, size);
                }

/* 235 */       public void setGravity(int gravity) {
/* 236 */           this.mGravity = gravity;
/* 237 */           requestLayout();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 241 */       protected void onAttachedToWindow() {
/* 242 */           super.onAttachedToWindow();
/* 244 */           ViewParent parent = getParent();
/* 245 */           if (!(parent instanceof ViewPager)) {
/* 246 */               throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
                    }
/* 250 */           ViewPager pager = (ViewPager) parent;
/* 251 */           PagerAdapter adapter = pager.getAdapter();
/* 253 */           pager.setInternalPageChangeListener(this.mPageListener);
/* 254 */           pager.setOnAdapterChangeListener(this.mPageListener);
/* 255 */           this.mPager = pager;
/* 256 */           updateAdapter(this.mWatchingAdapter != null ? this.mWatchingAdapter.get() : null, adapter);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 260 */       protected void onDetachedFromWindow() {
/* 261 */           super.onDetachedFromWindow();
/* 262 */           if (this.mPager != null) {
/* 263 */               updateAdapter(this.mPager.getAdapter(), null);
/* 264 */               this.mPager.setInternalPageChangeListener(null);
/* 265 */               this.mPager.setOnAdapterChangeListener(null);
/* 266 */               this.mPager = null;
                    }
                }

/* 270 */       void updateText(int currentItem, PagerAdapter adapter) {
/* 271 */           int itemCount = adapter != null ? adapter.getCount() : 0;
/* 272 */           this.mUpdatingText = true;
/* 274 */           CharSequence text = null;
/* 275 */           if (currentItem >= 1 && adapter != null) {
/* 276 */               text = adapter.getPageTitle(currentItem - 1);
                    }
/* 278 */           this.mPrevText.setText(text);
/* 280 */           this.mCurrText.setText((adapter == null || currentItem >= itemCount) ? null : adapter.getPageTitle(currentItem));
/* 283 */           CharSequence text2 = null;
/* 284 */           if (currentItem + 1 < itemCount && adapter != null) {
/* 285 */               text2 = adapter.getPageTitle(currentItem + 1);
                    }
/* 287 */           this.mNextText.setText(text2);
/* 290 */           int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
/* 291 */           int childHeight = (getHeight() - getPaddingTop()) - getPaddingBottom();
/* 292 */           int childWidthSpec = View.MeasureSpec.makeMeasureSpec((int) (width * 0.8f), Integer.MIN_VALUE);
/* 294 */           int childHeightSpec = View.MeasureSpec.makeMeasureSpec(childHeight, Integer.MIN_VALUE);
/* 295 */           this.mPrevText.measure(childWidthSpec, childHeightSpec);
/* 296 */           this.mCurrText.measure(childWidthSpec, childHeightSpec);
/* 297 */           this.mNextText.measure(childWidthSpec, childHeightSpec);
/* 299 */           this.mLastKnownCurrentPage = currentItem;
/* 301 */           if (!this.mUpdatingPositions) {
/* 302 */               updateTextPositions(currentItem, this.mLastKnownPositionOffset, false);
                    }
/* 305 */           this.mUpdatingText = false;
                }

                @Override // android.view.View, android.view.ViewParent
/* 309 */       public void requestLayout() {
/* 310 */           if (!this.mUpdatingText) {
/* 311 */               super.requestLayout();
                    }
                }

/* 315 */       void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
/* 316 */           if (oldAdapter != null) {
/* 317 */               oldAdapter.unregisterDataSetObserver(this.mPageListener);
/* 318 */               this.mWatchingAdapter = null;
                    }
/* 320 */           if (newAdapter != null) {
/* 321 */               newAdapter.registerDataSetObserver(this.mPageListener);
/* 322 */               this.mWatchingAdapter = new WeakReference<>(newAdapter);
                    }
/* 324 */           if (this.mPager != null) {
/* 325 */               this.mLastKnownCurrentPage = -1;
/* 326 */               this.mLastKnownPositionOffset = -1.0f;
/* 327 */               updateText(this.mPager.getCurrentItem(), newAdapter);
/* 328 */               requestLayout();
                    }
                }

/* 332 */       void updateTextPositions(int position, float positionOffset, boolean force) {
                    int prevTop;
                    int currTop;
                    int nextTop;
/* 333 */           if (position != this.mLastKnownCurrentPage) {
/* 334 */               updateText(position, this.mPager.getAdapter());
                    } else if (!force && positionOffset == this.mLastKnownPositionOffset) {
/* 418 */               return;
                    }
/* 339 */           this.mUpdatingPositions = true;
/* 341 */           int prevWidth = this.mPrevText.getMeasuredWidth();
/* 342 */           int currWidth = this.mCurrText.getMeasuredWidth();
/* 343 */           int nextWidth = this.mNextText.getMeasuredWidth();
/* 344 */           int halfCurrWidth = currWidth / 2;
/* 346 */           int stripWidth = getWidth();
/* 347 */           int stripHeight = getHeight();
/* 348 */           int paddingLeft = getPaddingLeft();
/* 349 */           int paddingRight = getPaddingRight();
/* 350 */           int paddingTop = getPaddingTop();
/* 351 */           int paddingBottom = getPaddingBottom();
/* 352 */           int textPaddedLeft = paddingLeft + halfCurrWidth;
/* 353 */           int textPaddedRight = paddingRight + halfCurrWidth;
/* 354 */           int contentWidth = (stripWidth - textPaddedLeft) - textPaddedRight;
/* 356 */           float currOffset = positionOffset + 0.5f;
                    if (currOffset > 1.0f) {
/* 358 */               currOffset -= 1.0f;
                    }
/* 360 */           int currCenter = (stripWidth - textPaddedRight) - ((int) (contentWidth * currOffset));
/* 361 */           int currLeft = currCenter - (currWidth / 2);
/* 362 */           int currRight = currLeft + currWidth;
/* 364 */           int prevBaseline = this.mPrevText.getBaseline();
/* 365 */           int currBaseline = this.mCurrText.getBaseline();
/* 366 */           int nextBaseline = this.mNextText.getBaseline();
/* 367 */           int maxBaseline = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
/* 368 */           int prevTopOffset = maxBaseline - prevBaseline;
/* 369 */           int currTopOffset = maxBaseline - currBaseline;
/* 370 */           int nextTopOffset = maxBaseline - nextBaseline;
/* 371 */           int alignedPrevHeight = prevTopOffset + this.mPrevText.getMeasuredHeight();
/* 372 */           int alignedCurrHeight = currTopOffset + this.mCurrText.getMeasuredHeight();
/* 373 */           int alignedNextHeight = nextTopOffset + this.mNextText.getMeasuredHeight();
/* 374 */           int maxTextHeight = Math.max(Math.max(alignedPrevHeight, alignedCurrHeight), alignedNextHeight);
/* 377 */           int vgrav = this.mGravity & 112;
                    switch (vgrav) {
                        case 16:
/* 390 */                   int paddedHeight = (stripHeight - paddingTop) - paddingBottom;
/* 391 */                   int centeredTop = (paddedHeight - maxTextHeight) / 2;
/* 392 */                   prevTop = centeredTop + prevTopOffset;
/* 393 */                   currTop = centeredTop + currTopOffset;
/* 394 */                   nextTop = centeredTop + nextTopOffset;
                            break;
                        case 80:
/* 397 */                   int bottomGravTop = (stripHeight - paddingBottom) - maxTextHeight;
/* 398 */                   prevTop = bottomGravTop + prevTopOffset;
/* 399 */                   currTop = bottomGravTop + currTopOffset;
/* 400 */                   nextTop = bottomGravTop + nextTopOffset;
                            break;
                        default:
/* 385 */                   prevTop = paddingTop + prevTopOffset;
/* 386 */                   currTop = paddingTop + currTopOffset;
/* 387 */                   nextTop = paddingTop + nextTopOffset;
                            break;
                    }
/* 404 */           this.mCurrText.layout(currLeft, currTop, currRight, this.mCurrText.getMeasuredHeight() + currTop);
/* 407 */           int prevLeft = Math.min(paddingLeft, (currLeft - this.mScaledTextSpacing) - prevWidth);
/* 408 */           this.mPrevText.layout(prevLeft, prevTop, prevLeft + prevWidth, this.mPrevText.getMeasuredHeight() + prevTop);
/* 411 */           int nextLeft = Math.max((stripWidth - paddingRight) - nextWidth, this.mScaledTextSpacing + currRight);
/* 413 */           this.mNextText.layout(nextLeft, nextTop, nextLeft + nextWidth, this.mNextText.getMeasuredHeight() + nextTop);
/* 416 */           this.mLastKnownPositionOffset = positionOffset;
/* 417 */           this.mUpdatingPositions = false;
                }

                @Override // android.view.View
/* 421 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 422 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 423 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 424 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 425 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 427 */           if (widthMode != 1073741824) {
/* 428 */               throw new IllegalStateException("Must measure with an exact width");
                    }
/* 432 */           int minHeight = getMinHeight();
/* 434 */           int padding = getPaddingTop() + getPaddingBottom();
/* 435 */           int childHeight = heightSize - padding;
/* 437 */           int childWidthSpec = View.MeasureSpec.makeMeasureSpec((int) (widthSize * 0.8f), Integer.MIN_VALUE);
/* 439 */           int childHeightSpec = View.MeasureSpec.makeMeasureSpec(childHeight, Integer.MIN_VALUE);
/* 441 */           this.mPrevText.measure(childWidthSpec, childHeightSpec);
/* 442 */           this.mCurrText.measure(childWidthSpec, childHeightSpec);
/* 443 */           this.mNextText.measure(childWidthSpec, childHeightSpec);
/* 445 */           if (heightMode == 1073741824) {
/* 446 */               setMeasuredDimension(widthSize, heightSize);
                    } else {
/* 448 */               int textHeight = this.mCurrText.getMeasuredHeight();
/* 449 */               setMeasuredDimension(widthSize, Math.max(minHeight, textHeight + padding));
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 454 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 455 */           if (this.mPager != null) {
/* 456 */               float offset = this.mLastKnownPositionOffset >= 0.0f ? this.mLastKnownPositionOffset : 0.0f;
/* 457 */               updateTextPositions(this.mLastKnownCurrentPage, offset, true);
                    }
                }

/* 461 */       int getMinHeight() {
/* 463 */           Drawable bg = getBackground();
/* 464 */           if (bg == null) {
/* 462 */               return 0;
                    }
/* 465 */           int minHeight = bg.getIntrinsicHeight();
/* 467 */           return minHeight;
                }

/* 468 */       private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
                    private int mScrollState;

/* 469 */           private PageListener() {
                    }

/* 469 */           /* synthetic */ PageListener(PagerTitleStrip x0, C00491 x1) {
/* 470 */               this();
                    }

                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 475 */           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if (positionOffset > 0.5f) {
/* 478 */                   position++;
                        }
/* 480 */               PagerTitleStrip.this.updateTextPositions(position, positionOffset, false);
                    }

                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 484 */           public void onPageSelected(int position) {
/* 485 */               if (this.mScrollState == 0) {
/* 487 */                   PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
/* 489 */                   float offset = PagerTitleStrip.access$100(PagerTitleStrip.this) >= 0.0f ? PagerTitleStrip.access$100(PagerTitleStrip.this) : 0.0f;
/* 490 */                   PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), offset, true);
                        }
                    }

                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 495 */           public void onPageScrollStateChanged(int state) {
/* 496 */               this.mScrollState = state;
                    }

                    @Override // android.support.v4.view.ViewPager.OnAdapterChangeListener
/* 500 */           public void onAdapterChanged(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
/* 501 */               PagerTitleStrip.this.updateAdapter(oldAdapter, newAdapter);
                    }

                    @Override // android.database.DataSetObserver
/* 505 */           public void onChanged() {
/* 506 */               PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
/* 508 */               float offset = PagerTitleStrip.access$100(PagerTitleStrip.this) >= 0.0f ? PagerTitleStrip.access$100(PagerTitleStrip.this) : 0.0f;
/* 509 */               PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), offset, true);
                    }
                }
            }
