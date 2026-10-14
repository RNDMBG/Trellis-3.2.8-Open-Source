            package android.support.v4.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.v4.view.AccessibilityDelegateCompat;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.ViewCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import java.lang.reflect.Field;
            import java.lang.reflect.Method;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 86 */    public class SlidingPaneLayout extends ViewGroup {
                private static final int DEFAULT_FADE_COLOR = -858993460;
                private static final int DEFAULT_OVERHANG_SIZE = 32;
                static final SlidingPanelLayoutImpl IMPL;
                private static final int MIN_FLING_VELOCITY = 400;
                private static final String TAG = "SlidingPaneLayout";
                private boolean mCanSlide;
                private int mCoveredFadeColor;
                private final ViewDragHelper mDragHelper;
                private boolean mFirstLayout;
                private float mInitialMotionX;
                private float mInitialMotionY;
                private boolean mIsUnableToDrag;
                private final int mOverhangSize;
                private PanelSlideListener mPanelSlideListener;
                private int mParallaxBy;
                private float mParallaxOffset;
                private final ArrayList<DisableLayerRunnable> mPostedRunnables;
                private boolean mPreservedOpenState;
                private Drawable mShadowDrawable;
                private float mSlideOffset;
                private int mSlideRange;
                private View mSlideableView;
                private int mSliderFadeColor;
                private final Rect mTmpRect;

                public interface PanelSlideListener {
                    void onPanelClosed(View view);

                    void onPanelOpened(View view);

                    void onPanelSlide(View view, float f);
                }

                interface SlidingPanelLayoutImpl {
                    void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view);
                }

/* 87 */        static /* synthetic */ boolean access$100(SlidingPaneLayout x0) {
/* 88 */            return x0.mIsUnableToDrag;
                }

/* 87 */        static /* synthetic */ ArrayList access$1000(SlidingPaneLayout x0) {
/* 88 */            return x0.mPostedRunnables;
                }

/* 87 */        static /* synthetic */ ViewDragHelper access$200(SlidingPaneLayout x0) {
/* 88 */            return x0.mDragHelper;
                }

/* 87 */        static /* synthetic */ float access$300(SlidingPaneLayout x0) {
/* 88 */            return x0.mSlideOffset;
                }

/* 87 */        static /* synthetic */ View access$400(SlidingPaneLayout x0) {
/* 88 */            return x0.mSlideableView;
                }

/* 87 */        static /* synthetic */ boolean access$502(SlidingPaneLayout x0, boolean x1) {
/* 88 */            x0.mPreservedOpenState = x1;
                    return x1;
                }

/* 87 */        static /* synthetic */ void access$600(SlidingPaneLayout x0, int x1) {
/* 88 */            x0.onPanelDragged(x1);
                }

/* 87 */        static /* synthetic */ int access$700(SlidingPaneLayout x0) {
/* 88 */            return x0.mSlideRange;
                }

/* 87 */        static /* synthetic */ void access$900(SlidingPaneLayout x0, View x1) {
/* 88 */            x0.invalidateChildRegion(x1);
                }

                static {
/* 192 */           int deviceVersion = Build.VERSION.SDK_INT;
/* 193 */           if (deviceVersion >= 17) {
/* 194 */               IMPL = new SlidingPanelLayoutImplJBMR1();
                    } else if (deviceVersion >= 16) {
/* 196 */               IMPL = new SlidingPanelLayoutImplJB();
                    } else {
/* 198 */               IMPL = new SlidingPanelLayoutImplBase();
                    }
                }

/* 232 */       public static class SimplePanelSlideListener implements PanelSlideListener {
                    @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
/* 233 */           public void onPanelSlide(View panel, float slideOffset) {
                    }

                    @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
/* 236 */           public void onPanelOpened(View panel) {
                    }

                    @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
/* 239 */           public void onPanelClosed(View panel) {
                    }
                }

/* 243 */       public SlidingPaneLayout(Context context) {
/* 244 */           this(context, null);
                }

/* 247 */       public SlidingPaneLayout(Context context, AttributeSet attrs) {
/* 248 */           this(context, attrs, 0);
                }

/* 251 */       public SlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
/* 252 */           super(context, attrs, defStyle);
/* 107 */           this.mSliderFadeColor = DEFAULT_FADE_COLOR;
/* 182 */           this.mFirstLayout = true;
/* 184 */           this.mTmpRect = new Rect();
/* 186 */           this.mPostedRunnables = new ArrayList<>();
/* 254 */           float density = context.getResources().getDisplayMetrics().density;
/* 255 */           this.mOverhangSize = (int) ((32.0f * density) + 0.5f);
/* 257 */           ViewConfiguration.get(context);
/* 259 */           setWillNotDraw(false);
/* 261 */           ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
/* 262 */           ViewCompat.setImportantForAccessibility(this, 1);
/* 264 */           this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback(this, null));
/* 265 */           this.mDragHelper.setEdgeTrackingEnabled(1);
/* 266 */           this.mDragHelper.setMinVelocity(400.0f * density);
                }

/* 276 */       public void setParallaxDistance(int parallaxBy) {
/* 277 */           this.mParallaxBy = parallaxBy;
/* 278 */           requestLayout();
                }

/* 286 */       public int getParallaxDistance() {
/* 287 */           return this.mParallaxBy;
                }

/* 295 */       public void setSliderFadeColor(int color) {
/* 296 */           this.mSliderFadeColor = color;
                }

/* 302 */       public int getSliderFadeColor() {
/* 303 */           return this.mSliderFadeColor;
                }

/* 312 */       public void setCoveredFadeColor(int color) {
/* 313 */           this.mCoveredFadeColor = color;
                }

/* 319 */       public int getCoveredFadeColor() {
/* 320 */           return this.mCoveredFadeColor;
                }

/* 323 */       public void setPanelSlideListener(PanelSlideListener listener) {
/* 324 */           this.mPanelSlideListener = listener;
                }

/* 327 */       void dispatchOnPanelSlide(View panel) {
/* 328 */           if (this.mPanelSlideListener != null) {
/* 329 */               this.mPanelSlideListener.onPanelSlide(panel, this.mSlideOffset);
                    }
                }

/* 333 */       void dispatchOnPanelOpened(View panel) {
/* 334 */           if (this.mPanelSlideListener != null) {
/* 335 */               this.mPanelSlideListener.onPanelOpened(panel);
                    }
/* 337 */           sendAccessibilityEvent(32);
                }

/* 340 */       void dispatchOnPanelClosed(View panel) {
/* 341 */           if (this.mPanelSlideListener != null) {
/* 342 */               this.mPanelSlideListener.onPanelClosed(panel);
                    }
/* 344 */           sendAccessibilityEvent(32);
                }

/* 347 */       void updateObscuredViewsVisibility(View panel) {
                    int bottom;
                    int top;
                    int right;
                    int left;
                    int vis;
/* 348 */           int leftBound = getPaddingLeft();
/* 349 */           int rightBound = getWidth() - getPaddingRight();
/* 350 */           int topBound = getPaddingTop();
/* 351 */           int bottomBound = getHeight() - getPaddingBottom();
/* 356 */           if (panel != null && viewIsOpaque(panel)) {
/* 357 */               left = panel.getLeft();
/* 358 */               right = panel.getRight();
/* 359 */               top = panel.getTop();
/* 360 */               bottom = panel.getBottom();
                    } else {
/* 362 */               bottom = 0;
/* 362 */               top = 0;
/* 362 */               right = 0;
/* 362 */               left = 0;
                    }
                    int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 366 */               View child = getChildAt(i);
/* 368 */               if (child != panel) {
/* 373 */                   int clampedChildLeft = Math.max(leftBound, child.getLeft());
/* 374 */                   int clampedChildTop = Math.max(topBound, child.getTop());
/* 375 */                   int clampedChildRight = Math.min(rightBound, child.getRight());
/* 376 */                   int clampedChildBottom = Math.min(bottomBound, child.getBottom());
/* 378 */                   if (clampedChildLeft >= left && clampedChildTop >= top && clampedChildRight <= right && clampedChildBottom <= bottom) {
/* 380 */                       vis = 4;
                            } else {
/* 382 */                       vis = 0;
                            }
/* 384 */                   child.setVisibility(vis);
                        } else {
/* 386 */                   return;
                        }
                    }
                }

/* 388 */       void setAllChildrenVisible() {
                    int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 390 */               View child = getChildAt(i);
/* 391 */               if (child.getVisibility() == 4) {
/* 392 */                   child.setVisibility(0);
                        }
                    }
                }

/* 397 */       private static boolean viewIsOpaque(View v) {
                    Drawable bg;
/* 398 */           if (ViewCompat.isOpaque(v)) {
                        return true;
                    }
                    return Build.VERSION.SDK_INT < 18 && (bg = v.getBackground()) != null && bg.getOpacity() == -1;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 413 */       protected void onAttachedToWindow() {
/* 414 */           super.onAttachedToWindow();
/* 415 */           this.mFirstLayout = true;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 419 */       protected void onDetachedFromWindow() {
/* 420 */           super.onDetachedFromWindow();
/* 421 */           this.mFirstLayout = true;
                    int count = this.mPostedRunnables.size();
                    for (int i = 0; i < count; i++) {
/* 424 */               DisableLayerRunnable dlr = this.mPostedRunnables.get(i);
/* 425 */               dlr.run();
                    }
/* 427 */           this.mPostedRunnables.clear();
                }

                /* JADX WARN: Code duplicated, block: B:125:0x0079 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:37:0x00dc A[PHI: r21
  0x00dc: PHI (r21v2 'weightSum' float) = (r21v1 'weightSum' float), (r21v3 'weightSum' float) binds: [B:34:0x00ca, B:36:0x00da] A[DONT_GENERATE, DONT_INLINE]] */
                /* JADX WARN: Code duplicated, block: B:39:0x00f8  */
                /* JADX WARN: Code duplicated, block: B:42:0x010e  */
                /* JADX WARN: Code duplicated, block: B:49:0x0135  */
                /* JADX WARN: Code duplicated, block: B:52:0x0147  */
                /* JADX WARN: Code duplicated, block: B:53:0x014d  */
                /* JADX WARN: Code duplicated, block: B:55:0x015b  */
                /* JADX WARN: Code duplicated, block: B:56:0x0164  */
                /* JADX WARN: Code duplicated, block: B:57:0x0171  */
                /* JADX WARN: Code duplicated, block: B:59:0x017f  */
                /* JADX WARN: Code duplicated, block: B:60:0x018a  */
                /* JADX WARN: Code duplicated, block: B:61:0x0197  */
                @Override // android.view.View
/* 431 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int childHeightSpec;
                    int childHeightSpec2;
                    int horizontalMargin;
                    int childWidthSpec;
                    int childHeightSpec3;
                    int childHeight;
                    boolean z;
/* 432 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 433 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 434 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 435 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 437 */           if (widthMode != 1073741824) {
/* 438 */               if (isInEditMode()) {
/* 443 */                   if (widthMode != Integer.MIN_VALUE && widthMode == 0) {
/* 447 */                       widthSize = 300;
                            }
                        } else {
/* 450 */                   throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
                        }
                    } else if (heightMode == 0) {
/* 453 */               if (isInEditMode()) {
/* 457 */                   if (heightMode == 0) {
/* 458 */                       heightMode = Integer.MIN_VALUE;
/* 459 */                       heightSize = 300;
                            }
                        } else {
/* 462 */                   throw new IllegalStateException("Height must not be UNSPECIFIED");
                        }
                    }
/* 466 */           int layoutHeight = 0;
/* 467 */           int maxLayoutHeight = -1;
                    switch (heightMode) {
                        case Integer.MIN_VALUE:
/* 473 */                   maxLayoutHeight = (heightSize - getPaddingTop()) - getPaddingBottom();
                            break;
                        case 1073741824:
/* 470 */                   maxLayoutHeight = (heightSize - getPaddingTop()) - getPaddingBottom();
                            layoutHeight = maxLayoutHeight;
                            break;
                    }
/* 477 */           float weightSum = 0.0f;
/* 478 */           boolean canSlide = false;
/* 479 */           int widthRemaining = (widthSize - getPaddingLeft()) - getPaddingRight();
/* 480 */           int childCount = getChildCount();
/* 482 */           if (childCount > 2) {
/* 483 */               Log.e(TAG, "onMeasure: More than two child views are not supported.");
                    }
/* 487 */           this.mSlideableView = null;
                    for (int i = 0; i < childCount; i++) {
/* 492 */               View child = getChildAt(i);
/* 493 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 495 */               if (child.getVisibility() == 8) {
/* 496 */                   lp.dimWhenOffset = false;
                        } else if (lp.weight > 0.0f) {
/* 501 */                   weightSum += lp.weight;
/* 505 */                   if (lp.width != 0) {
/* 509 */                       horizontalMargin = lp.leftMargin + lp.rightMargin;
/* 510 */                       if (lp.width == -2) {
/* 511 */                           childWidthSpec = View.MeasureSpec.makeMeasureSpec(widthSize - horizontalMargin, Integer.MIN_VALUE);
                                } else if (lp.width == -1) {
/* 514 */                           childWidthSpec = View.MeasureSpec.makeMeasureSpec(widthSize - horizontalMargin, 1073741824);
                                } else {
/* 517 */                           childWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
                                }
/* 521 */                       if (lp.height == -2) {
/* 522 */                           childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                                } else if (lp.height == -1) {
/* 524 */                           childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                                } else {
/* 526 */                           childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
                                }
/* 529 */                       child.measure(childWidthSpec, childHeightSpec3);
/* 530 */                       int childWidth = child.getMeasuredWidth();
/* 531 */                       childHeight = child.getMeasuredHeight();
/* 533 */                       if (heightMode == Integer.MIN_VALUE && childHeight > layoutHeight) {
/* 534 */                           layoutHeight = Math.min(childHeight, maxLayoutHeight);
                                }
/* 537 */                       widthRemaining -= childWidth;
/* 538 */                       if (widthRemaining < 0) {
                                    z = true;
                                } else {
/* 538 */                           z = false;
                                }
                                lp.slideable = z;
                                canSlide |= z;
/* 539 */                       if (lp.slideable) {
/* 540 */                           this.mSlideableView = child;
                                }
                            }
                        } else {
/* 509 */                   horizontalMargin = lp.leftMargin + lp.rightMargin;
/* 510 */                   if (lp.width == -2) {
/* 511 */                       childWidthSpec = View.MeasureSpec.makeMeasureSpec(widthSize - horizontalMargin, Integer.MIN_VALUE);
                            } else if (lp.width == -1) {
/* 514 */                       childWidthSpec = View.MeasureSpec.makeMeasureSpec(widthSize - horizontalMargin, 1073741824);
                            } else {
/* 517 */                       childWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
                            }
/* 521 */                   if (lp.height == -2) {
/* 522 */                       childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                            } else if (lp.height == -1) {
/* 524 */                       childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                            } else {
/* 526 */                       childHeightSpec3 = View.MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
                            }
/* 529 */                   child.measure(childWidthSpec, childHeightSpec3);
/* 530 */                   int childWidth2 = child.getMeasuredWidth();
/* 531 */                   childHeight = child.getMeasuredHeight();
/* 533 */                   if (heightMode == Integer.MIN_VALUE) {
/* 534 */                       layoutHeight = Math.min(childHeight, maxLayoutHeight);
                            }
/* 537 */                   widthRemaining -= childWidth2;
/* 538 */                   if (widthRemaining < 0) {
                                z = true;
                            } else {
/* 538 */                       z = false;
                            }
                            lp.slideable = z;
                            canSlide |= z;
/* 539 */                   if (lp.slideable) {
/* 540 */                       this.mSlideableView = child;
                            }
                        }
                    }
/* 545 */           if (canSlide || weightSum > 0.0f) {
/* 546 */               int fixedPanelWidthLimit = widthSize - this.mOverhangSize;
                        for (int i2 = 0; i2 < childCount; i2++) {
/* 549 */                   View child2 = getChildAt(i2);
/* 551 */                   if (child2.getVisibility() != 8) {
/* 555 */                       LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
/* 557 */                       if (child2.getVisibility() != 8) {
/* 561 */                           boolean skippedFirstPass = lp2.width == 0 && lp2.weight > 0.0f;
/* 562 */                           int measuredWidth = skippedFirstPass ? 0 : child2.getMeasuredWidth();
/* 563 */                           if (!canSlide || child2 == this.mSlideableView) {
                                        if (lp2.weight > 0.0f) {
/* 591 */                                   if (lp2.width == 0) {
/* 593 */                                       if (lp2.height == -2) {
/* 594 */                                           childHeightSpec = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                                                } else if (lp2.height == -1) {
/* 597 */                                           childHeightSpec = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                                                } else {
/* 600 */                                           childHeightSpec = View.MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                                                }
                                            } else {
/* 604 */                                       childHeightSpec = View.MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                                            }
/* 608 */                                   if (canSlide) {
/* 611 */                                       int newWidth = widthSize - (lp2.leftMargin + lp2.rightMargin);
/* 612 */                                       int childWidthSpec2 = View.MeasureSpec.makeMeasureSpec(newWidth, 1073741824);
/* 614 */                                       if (measuredWidth != newWidth) {
/* 615 */                                           child2.measure(childWidthSpec2, childHeightSpec);
                                                }
                                            } else {
/* 619 */                                       int widthToDistribute = Math.max(0, widthRemaining);
/* 620 */                                       int addedWidth = (int) ((lp2.weight * widthToDistribute) / weightSum);
/* 621 */                                       int childWidthSpec3 = View.MeasureSpec.makeMeasureSpec(measuredWidth + addedWidth, 1073741824);
/* 623 */                                       child2.measure(childWidthSpec3, childHeightSpec);
                                            }
                                        }
                                    } else if (lp2.width < 0 && (measuredWidth > fixedPanelWidthLimit || lp2.weight > 0.0f)) {
/* 568 */                               if (skippedFirstPass) {
/* 571 */                                   if (lp2.height == -2) {
/* 572 */                                       childHeightSpec2 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                                            } else if (lp2.height == -1) {
/* 575 */                                       childHeightSpec2 = View.MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                                            } else {
/* 578 */                                       childHeightSpec2 = View.MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                                            }
                                        } else {
/* 582 */                                   childHeightSpec2 = View.MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                                        }
/* 585 */                               int childWidthSpec4 = View.MeasureSpec.makeMeasureSpec(fixedPanelWidthLimit, 1073741824);
/* 587 */                               child2.measure(childWidthSpec4, childHeightSpec2);
                                    }
                                }
                            }
                        }
                    }
/* 629 */           setMeasuredDimension(widthSize, layoutHeight);
/* 630 */           this.mCanSlide = canSlide;
/* 631 */           if (this.mDragHelper.getViewDragState() != 0 && !canSlide) {
/* 633 */               this.mDragHelper.abort();
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 639 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 640 */           int width = r - l;
/* 641 */           int paddingLeft = getPaddingLeft();
/* 642 */           int paddingRight = getPaddingRight();
/* 643 */           int paddingTop = getPaddingTop();
/* 645 */           int childCount = getChildCount();
/* 646 */           int xStart = paddingLeft;
/* 647 */           int nextXStart = xStart;
/* 649 */           if (this.mFirstLayout) {
/* 650 */               this.mSlideOffset = (this.mCanSlide && this.mPreservedOpenState) ? 1.0f : 0.0f;
                    }
                    for (int i = 0; i < childCount; i++) {
/* 654 */               View child = getChildAt(i);
/* 656 */               if (child.getVisibility() != 8) {
/* 660 */                   LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 662 */                   int childWidth = child.getMeasuredWidth();
/* 663 */                   int offset = 0;
/* 665 */                   if (lp.slideable) {
/* 666 */                       int margin = lp.leftMargin + lp.rightMargin;
/* 667 */                       int range = (Math.min(nextXStart, (width - paddingRight) - this.mOverhangSize) - xStart) - margin;
/* 669 */                       this.mSlideRange = range;
/* 670 */                       lp.dimWhenOffset = ((lp.leftMargin + xStart) + range) + (childWidth / 2) > width - paddingRight;
/* 672 */                       int pos = (int) (range * this.mSlideOffset);
/* 673 */                       xStart += lp.leftMargin + pos;
/* 674 */                       this.mSlideOffset = pos / this.mSlideRange;
                            } else if (this.mCanSlide && this.mParallaxBy != 0) {
/* 676 */                       offset = (int) ((1.0f - this.mSlideOffset) * this.mParallaxBy);
/* 677 */                       xStart = nextXStart;
                            } else {
/* 679 */                       xStart = nextXStart;
                            }
/* 682 */                   int childLeft = xStart - offset;
/* 683 */                   int childRight = childLeft + childWidth;
/* 684 */                   int childBottom = paddingTop + child.getMeasuredHeight();
/* 686 */                   child.layout(childLeft, paddingTop, childRight, childBottom);
/* 688 */                   nextXStart += child.getWidth();
                        }
                    }
/* 691 */           if (this.mFirstLayout) {
/* 692 */               if (this.mCanSlide) {
/* 693 */                   if (this.mParallaxBy != 0) {
/* 694 */                       parallaxOtherViews(this.mSlideOffset);
                            }
/* 696 */                   if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
/* 697 */                       dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                            }
                        } else {
                            for (int i2 = 0; i2 < childCount; i2++) {
/* 702 */                       dimChildView(getChildAt(i2), 0.0f, this.mSliderFadeColor);
                            }
                        }
/* 705 */               updateObscuredViewsVisibility(this.mSlideableView);
                    }
/* 708 */           this.mFirstLayout = false;
                }

                @Override // android.view.View
/* 712 */       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 713 */           super.onSizeChanged(w, h, oldw, oldh);
/* 715 */           if (w != oldw) {
/* 716 */               this.mFirstLayout = true;
                    }
                }

                @Override // android.view.ViewGroup, android.view.ViewParent
/* 721 */       public void requestChildFocus(View child, View focused) {
/* 722 */           super.requestChildFocus(child, focused);
/* 723 */           if (!isInTouchMode() && !this.mCanSlide) {
/* 724 */               this.mPreservedOpenState = child == this.mSlideableView;
                    }
                }

                @Override // android.view.ViewGroup
/* 729 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
                    View secondChild;
/* 730 */           int action = MotionEventCompat.getActionMasked(ev);
/* 733 */           if (!this.mCanSlide && action == 0 && getChildCount() > 1 && (secondChild = getChildAt(1)) != null) {
/* 737 */               this.mPreservedOpenState = !this.mDragHelper.isViewUnder(secondChild, (int) ev.getX(), (int) ev.getY());
                    }
/* 742 */           if (!this.mCanSlide || (this.mIsUnableToDrag && action != 0)) {
/* 743 */               this.mDragHelper.cancel();
/* 744 */               return super.onInterceptTouchEvent(ev);
                    }
/* 747 */           if (action == 3 || action == 1) {
/* 748 */               this.mDragHelper.cancel();
/* 749 */               return false;
                    }
/* 752 */           boolean interceptTap = false;
                    switch (action) {
                        case 0:
/* 756 */                   this.mIsUnableToDrag = false;
/* 757 */                   float x = ev.getX();
/* 758 */                   float y = ev.getY();
/* 759 */                   this.mInitialMotionX = x;
/* 760 */                   this.mInitialMotionY = y;
/* 762 */                   if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) x, (int) y) && isDimmed(this.mSlideableView)) {
/* 764 */                       interceptTap = true;
                            }
                            break;
                        case 2:
/* 770 */                   float x2 = ev.getX();
/* 771 */                   float y2 = ev.getY();
/* 772 */                   float adx = Math.abs(x2 - this.mInitialMotionX);
/* 773 */                   float ady = Math.abs(y2 - this.mInitialMotionY);
/* 774 */                   int slop = this.mDragHelper.getTouchSlop();
                            if (adx > slop && ady > adx) {
/* 776 */                       this.mDragHelper.cancel();
/* 777 */                       this.mIsUnableToDrag = true;
/* 778 */                       return false;
                            }
                            break;
                    }
/* 783 */           boolean interceptForDrag = this.mDragHelper.shouldInterceptTouchEvent(ev);
                    return interceptForDrag || interceptTap;
                }

                @Override // android.view.View
/* 789 */       public boolean onTouchEvent(MotionEvent ev) {
/* 790 */           if (!this.mCanSlide) {
/* 791 */               return super.onTouchEvent(ev);
                    }
/* 794 */           this.mDragHelper.processTouchEvent(ev);
/* 796 */           int action = ev.getAction();
                    switch (action & 255) {
                        case 0:
/* 801 */                   float x = ev.getX();
/* 802 */                   float y = ev.getY();
/* 803 */                   this.mInitialMotionX = x;
/* 804 */                   this.mInitialMotionY = y;
/* 797 */                   return true;
                        case 1:
/* 809 */                   if (!isDimmed(this.mSlideableView)) {
/* 797 */                       return true;
                            }
/* 810 */                   float x2 = ev.getX();
/* 811 */                   float y2 = ev.getY();
/* 812 */                   float dx = x2 - this.mInitialMotionX;
/* 813 */                   float dy = y2 - this.mInitialMotionY;
/* 814 */                   int slop = this.mDragHelper.getTouchSlop();
                            if ((dx * dx) + (dy * dy) >= slop * slop || !this.mDragHelper.isViewUnder(this.mSlideableView, (int) x2, (int) y2)) {
/* 797 */                       return true;
                            }
/* 818 */                   closePane(this.mSlideableView, 0);
/* 797 */                   return true;
                        default:
/* 797 */                   return true;
                    }
                }

/* 829 */       private boolean closePane(View pane, int initialVelocity) {
/* 830 */           if (!this.mFirstLayout && !smoothSlideTo(0.0f, initialVelocity)) {
                        return false;
                    }
/* 831 */           this.mPreservedOpenState = false;
/* 832 */           return true;
                }

/* 837 */       private boolean openPane(View pane, int initialVelocity) {
/* 838 */           if (!this.mFirstLayout && !smoothSlideTo(1.0f, initialVelocity)) {
                        return false;
                    }
/* 839 */           this.mPreservedOpenState = true;
                    return true;
                }

                @Deprecated
/* 849 */       public void smoothSlideOpen() {
/* 850 */           openPane();
                }

/* 859 */       public boolean openPane() {
/* 860 */           return openPane(this.mSlideableView, 0);
                }

                @Deprecated
/* 867 */       public void smoothSlideClosed() {
/* 868 */           closePane();
                }

/* 877 */       public boolean closePane() {
/* 878 */           return closePane(this.mSlideableView, 0);
                }

/* 887 */       public boolean isOpen() {
                    return !this.mCanSlide || this.mSlideOffset == 1.0f;
                }

                @Deprecated
/* 896 */       public boolean canSlide() {
/* 897 */           return this.mCanSlide;
                }

/* 906 */       public boolean isSlideable() {
/* 907 */           return this.mCanSlide;
                }

/* 910 */       private void onPanelDragged(int newLeft) {
/* 911 */           if (this.mSlideableView == null) {
/* 913 */               this.mSlideOffset = 0.0f;
/* 930 */               return;
                    }
/* 917 */           LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
/* 918 */           int leftBound = getPaddingLeft() + lp.leftMargin;
/* 920 */           this.mSlideOffset = (newLeft - leftBound) / this.mSlideRange;
/* 922 */           if (this.mParallaxBy != 0) {
/* 923 */               parallaxOtherViews(this.mSlideOffset);
                    }
/* 926 */           if (lp.dimWhenOffset) {
/* 927 */               dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                    }
/* 929 */           dispatchOnPanelSlide(this.mSlideableView);
                }

/* 932 */       private void dimChildView(View v, float mag, int fadeColor) {
/* 933 */           LayoutParams lp = (LayoutParams) v.getLayoutParams();
                    if (mag > 0.0f && fadeColor != 0) {
/* 936 */               int baseAlpha = ((-16777216) & fadeColor) >>> 24;
/* 937 */               int imag = (int) (baseAlpha * mag);
/* 938 */               int color = (imag << 24) | (16777215 & fadeColor);
/* 939 */               if (lp.dimPaint == null) {
/* 940 */                   lp.dimPaint = new Paint();
                        }
/* 942 */               lp.dimPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_OVER));
/* 943 */               if (ViewCompat.getLayerType(v) != 2) {
/* 944 */                   ViewCompat.setLayerType(v, 2, lp.dimPaint);
                        }
/* 946 */               invalidateChildRegion(v);
/* 955 */               return;
                    }
/* 947 */           if (ViewCompat.getLayerType(v) != 0) {
/* 948 */               if (lp.dimPaint != null) {
/* 949 */                   lp.dimPaint.setColorFilter(null);
                        }
/* 951 */               DisableLayerRunnable dlr = new DisableLayerRunnable(v);
/* 952 */               this.mPostedRunnables.add(dlr);
/* 953 */               ViewCompat.postOnAnimation(this, dlr);
                    }
                }

                @Override // android.view.ViewGroup
/* 958 */       protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
                    boolean result;
/* 959 */           LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 961 */           int save = canvas.save(2);
/* 963 */           if (this.mCanSlide && !lp.slideable && this.mSlideableView != null) {
/* 965 */               canvas.getClipBounds(this.mTmpRect);
/* 966 */               this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
/* 967 */               canvas.clipRect(this.mTmpRect);
                    }
/* 970 */           if (Build.VERSION.SDK_INT >= 11) {
/* 971 */               result = super.drawChild(canvas, child, drawingTime);
                    } else if (lp.dimWhenOffset && this.mSlideOffset > 0.0f) {
/* 974 */               if (!child.isDrawingCacheEnabled()) {
/* 975 */                   child.setDrawingCacheEnabled(true);
                        }
/* 977 */               Bitmap cache = child.getDrawingCache();
/* 978 */               if (cache != null) {
/* 979 */                   canvas.drawBitmap(cache, child.getLeft(), child.getTop(), lp.dimPaint);
/* 980 */                   result = false;
                        } else {
/* 982 */                   Log.e(TAG, "drawChild: child view " + child + " returned null drawing cache");
/* 983 */                   result = super.drawChild(canvas, child, drawingTime);
                        }
                    } else {
/* 986 */               if (child.isDrawingCacheEnabled()) {
/* 987 */                   child.setDrawingCacheEnabled(false);
                        }
/* 989 */               result = super.drawChild(canvas, child, drawingTime);
                    }
/* 993 */           canvas.restoreToCount(save);
/* 995 */           return result;
                }

/* 998 */       private void invalidateChildRegion(View v) {
/* 999 */           IMPL.invalidateChildRegion(this, v);
                }

/* 1008 */      boolean smoothSlideTo(float slideOffset, int velocity) {
/* 1009 */          if (!this.mCanSlide) {
                        return false;
                    }
/* 1014 */          LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
/* 1016 */          int leftBound = getPaddingLeft() + lp.leftMargin;
/* 1017 */          int x = (int) (leftBound + (this.mSlideRange * slideOffset));
/* 1019 */          if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, x, this.mSlideableView.getTop())) {
                        return false;
                    }
/* 1020 */          setAllChildrenVisible();
/* 1021 */          ViewCompat.postInvalidateOnAnimation(this);
/* 1022 */          return true;
                }

                @Override // android.view.View
/* 1028 */      public void computeScroll() {
/* 1029 */          if (this.mDragHelper.continueSettling(true)) {
/* 1030 */              if (!this.mCanSlide) {
/* 1031 */                  this.mDragHelper.abort();
                        } else {
/* 1035 */                  ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                }

/* 1045 */      public void setShadowDrawable(Drawable d) {
/* 1046 */          this.mShadowDrawable = d;
                }

/* 1055 */      public void setShadowResource(int resId) {
/* 1056 */          setShadowDrawable(getResources().getDrawable(resId));
                }

                @Override // android.view.View
/* 1060 */      public void draw(Canvas c) {
/* 1061 */          super.draw(c);
/* 1063 */          View shadowView = getChildCount() > 1 ? getChildAt(1) : null;
/* 1064 */          if (shadowView != null && this.mShadowDrawable != null) {
/* 1069 */              int shadowWidth = this.mShadowDrawable.getIntrinsicWidth();
/* 1070 */              int right = shadowView.getLeft();
/* 1071 */              int top = shadowView.getTop();
/* 1072 */              int bottom = shadowView.getBottom();
/* 1073 */              int left = right - shadowWidth;
/* 1074 */              this.mShadowDrawable.setBounds(left, top, right, bottom);
/* 1075 */              this.mShadowDrawable.draw(c);
                    }
                }

/* 1078 */      private void parallaxOtherViews(float slideOffset) {
/* 1079 */          LayoutParams slideLp = (LayoutParams) this.mSlideableView.getLayoutParams();
/* 1080 */          boolean dimViews = slideLp.dimWhenOffset && slideLp.leftMargin <= 0;
/* 1081 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1083 */              View v = getChildAt(i);
/* 1084 */              if (v != this.mSlideableView) {
/* 1086 */                  int oldOffset = (int) ((1.0f - this.mParallaxOffset) * this.mParallaxBy);
/* 1087 */                  this.mParallaxOffset = slideOffset;
/* 1088 */                  int newOffset = (int) ((1.0f - slideOffset) * this.mParallaxBy);
/* 1089 */                  int dx = oldOffset - newOffset;
/* 1091 */                  v.offsetLeftAndRight(dx);
/* 1093 */                  if (dimViews) {
/* 1094 */                      dimChildView(v, 1.0f - this.mParallaxOffset, this.mCoveredFadeColor);
                            }
                        }
                    }
                }

/* 1110 */      protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
/* 1111 */          if (v instanceof ViewGroup) {
/* 1112 */              ViewGroup group = (ViewGroup) v;
/* 1113 */              int scrollX = v.getScrollX();
/* 1114 */              int scrollY = v.getScrollY();
/* 1115 */              int count = group.getChildCount();
                        for (int i = count - 1; i >= 0; i--) {
/* 1120 */                  View child = group.getChildAt(i);
/* 1121 */                  if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
/* 1125 */                      return true;
                            }
                        }
                    }
                    return checkV && ViewCompat.canScrollHorizontally(v, -dx);
                }

/* 1133 */      boolean isDimmed(View child) {
/* 1134 */          if (child == null) {
                        return false;
                    }
/* 1137 */          LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    return this.mCanSlide && lp.dimWhenOffset && this.mSlideOffset > 0.0f;
                }

                @Override // android.view.ViewGroup
/* 1142 */      protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 1143 */          return new LayoutParams();
                }

                @Override // android.view.ViewGroup
/* 1147 */      protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
                    return p instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) p) : new LayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 1154 */      protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
                    return (p instanceof LayoutParams) && super.checkLayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 1159 */      public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 1160 */          return new LayoutParams(getContext(), attrs);
                }

                @Override // android.view.View
/* 1164 */      protected Parcelable onSaveInstanceState() {
/* 1165 */          Parcelable superState = super.onSaveInstanceState();
/* 1167 */          SavedState ss = new SavedState(superState);
/* 1168 */          ss.isOpen = isSlideable() ? isOpen() : this.mPreservedOpenState;
/* 1170 */          return ss;
                }

                @Override // android.view.View
/* 1174 */      protected void onRestoreInstanceState(Parcelable state) {
/* 1175 */          SavedState ss = (SavedState) state;
/* 1176 */          super.onRestoreInstanceState(ss.getSuperState());
/* 1178 */          if (ss.isOpen) {
/* 1179 */              openPane();
                    } else {
/* 1181 */              closePane();
                    }
/* 1183 */          this.mPreservedOpenState = ss.isOpen;
                }

/* 1184 */      private class DragHelperCallback extends ViewDragHelper.Callback {
/* 1185 */          private DragHelperCallback() {
                    }

/* 1185 */          /* synthetic */ DragHelperCallback(SlidingPaneLayout x0, C00721 x1) {
/* 1186 */              this();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1189 */          public boolean tryCaptureView(View child, int pointerId) {
/* 1190 */              if (SlidingPaneLayout.access$100(SlidingPaneLayout.this)) {
/* 1191 */                  return false;
                        }
                        return ((LayoutParams) child.getLayoutParams()).slideable;
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1198 */          public void onViewDragStateChanged(int state) {
/* 1199 */              if (SlidingPaneLayout.access$200(SlidingPaneLayout.this).getViewDragState() == 0) {
                            if (SlidingPaneLayout.access$300(SlidingPaneLayout.this) == 0.0f) {
/* 1201 */                      SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
/* 1202 */                      SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
/* 1203 */                      SlidingPaneLayout.access$502(SlidingPaneLayout.this, false);
                            } else {
/* 1205 */                      SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
/* 1206 */                      SlidingPaneLayout.access$502(SlidingPaneLayout.this, true);
                            }
                        }
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1213 */          public void onViewCaptured(View capturedChild, int activePointerId) {
/* 1214 */              SlidingPaneLayout.this.setAllChildrenVisible();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1218 */          public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
/* 1219 */              SlidingPaneLayout.access$600(SlidingPaneLayout.this, left);
/* 1220 */              SlidingPaneLayout.this.invalidate();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1224 */          public void onViewReleased(View releasedChild, float xvel, float yvel) {
/* 1225 */              LayoutParams lp = (LayoutParams) releasedChild.getLayoutParams();
/* 1226 */              int left = SlidingPaneLayout.this.getPaddingLeft() + lp.leftMargin;
                        if (xvel > 0.0f || (xvel == 0.0f && SlidingPaneLayout.access$300(SlidingPaneLayout.this) > 0.5f)) {
/* 1228 */                  left += SlidingPaneLayout.access$700(SlidingPaneLayout.this);
                        }
/* 1230 */              SlidingPaneLayout.access$200(SlidingPaneLayout.this).settleCapturedViewAt(left, releasedChild.getTop());
/* 1231 */              SlidingPaneLayout.this.invalidate();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1235 */          public int getViewHorizontalDragRange(View child) {
/* 1236 */              return SlidingPaneLayout.access$700(SlidingPaneLayout.this);
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1240 */          public int clampViewPositionHorizontal(View child, int left, int dx) {
/* 1241 */              LayoutParams lp = (LayoutParams) SlidingPaneLayout.access$400(SlidingPaneLayout.this).getLayoutParams();
/* 1242 */              int leftBound = SlidingPaneLayout.this.getPaddingLeft() + lp.leftMargin;
/* 1243 */              int rightBound = leftBound + SlidingPaneLayout.access$700(SlidingPaneLayout.this);
/* 1245 */              int newLeft = Math.min(Math.max(left, leftBound), rightBound);
/* 1247 */              return newLeft;
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1251 */          public void onEdgeDragStarted(int edgeFlags, int pointerId) {
/* 1252 */              SlidingPaneLayout.access$200(SlidingPaneLayout.this).captureChildView(SlidingPaneLayout.access$400(SlidingPaneLayout.this), pointerId);
                    }
                }

/* 1279 */      public static class LayoutParams extends ViewGroup.MarginLayoutParams {
                    private static final int[] ATTRS = {R.attr.layout_weight};
                    Paint dimPaint;
                    boolean dimWhenOffset;
                    boolean slideable;
                    public float weight;

/* 1280 */          public LayoutParams() {
/* 1281 */              super(-1, -1);
/* 1265 */              this.weight = 0.0f;
                    }

/* 1284 */          public LayoutParams(int width, int height) {
/* 1285 */              super(width, height);
/* 1265 */              this.weight = 0.0f;
                    }

/* 1288 */          public LayoutParams(ViewGroup.LayoutParams source) {
/* 1289 */              super(source);
/* 1265 */              this.weight = 0.0f;
                    }

/* 1292 */          public LayoutParams(ViewGroup.MarginLayoutParams source) {
/* 1293 */              super(source);
/* 1265 */              this.weight = 0.0f;
                    }

/* 1296 */          public LayoutParams(LayoutParams source) {
/* 1297 */              super((ViewGroup.MarginLayoutParams) source);
/* 1265 */              this.weight = 0.0f;
/* 1298 */              this.weight = source.weight;
                    }

/* 1301 */          public LayoutParams(Context c, AttributeSet attrs) {
/* 1302 */              super(c, attrs);
/* 1265 */              this.weight = 0.0f;
/* 1304 */              TypedArray a = c.obtainStyledAttributes(attrs, ATTRS);
/* 1305 */              this.weight = a.getFloat(0, 0.0f);
/* 1306 */              a.recycle();
                    }
                }

/* 1309 */      static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00731();
                    boolean isOpen;

/* 1310 */          /* synthetic */ SavedState(Parcel x0, C00721 x1) {
/* 1311 */              this(x0);
                    }

/* 1314 */          SavedState(Parcelable superState) {
/* 1315 */              super(superState);
                    }

/* 1318 */          private SavedState(Parcel in) {
/* 1319 */              super(in);
/* 1320 */              this.isOpen = in.readInt() != 0;
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 1324 */          public void writeToParcel(Parcel out, int flags) {
/* 1325 */              super.writeToParcel(out, flags);
/* 1326 */              out.writeInt(this.isOpen ? 1 : 0);
                    }

                    /* JADX INFO: renamed from: android.support.v4.widget.SlidingPaneLayout$SavedState$1 */
/* 1328 */          static class C00731 implements Parcelable.Creator<SavedState> {
/* 1329 */              C00731() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 1329 */              public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 1330 */                  return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 1329 */              public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 1330 */                  return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1331 */              public SavedState createFromParcel(Parcel in) {
/* 1332 */                  return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1335 */              public SavedState[] newArray(int size) {
/* 1336 */                  return new SavedState[size];
                        }
                    }
                }

/* 1343 */      static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
/* 1344 */          SlidingPanelLayoutImplBase() {
                    }

                    @Override // android.support.v4.widget.SlidingPaneLayout.SlidingPanelLayoutImpl
/* 1346 */          public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
/* 1347 */              ViewCompat.postInvalidateOnAnimation(parent, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    }
                }

/* 1362 */      static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
                    private Method mGetDisplayList;
                    private Field mRecreateDisplayList;

/* 1363 */          SlidingPanelLayoutImplJB() {
                        try {
/* 1366 */                  this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", (Class[]) null);
                        } catch (NoSuchMethodException e) {
/* 1368 */                  Log.e(SlidingPaneLayout.TAG, "Couldn't fetch getDisplayList method; dimming won't work right.", e);
                        }
                        try {
/* 1371 */                  this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
/* 1372 */                  this.mRecreateDisplayList.setAccessible(true);
                        } catch (NoSuchFieldException e2) {
/* 1374 */                  Log.e(SlidingPaneLayout.TAG, "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
                        }
                    }

                    @Override // android.support.v4.widget.SlidingPaneLayout.SlidingPanelLayoutImplBase, android.support.v4.widget.SlidingPaneLayout.SlidingPanelLayoutImpl
/* 1379 */          public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
/* 1380 */              if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
                            try {
/* 1382 */                      this.mRecreateDisplayList.setBoolean(child, true);
/* 1383 */                      this.mGetDisplayList.invoke(child, (Object[]) null);
                            } catch (Exception e) {
/* 1385 */                      Log.e(SlidingPaneLayout.TAG, "Error refreshing display list state", e);
                            }
/* 1392 */                  super.invalidateChildRegion(parent, child);
/* 1393 */                  return;
                        }
/* 1389 */              child.invalidate();
                    }
                }

/* 1394 */      static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
/* 1395 */          SlidingPanelLayoutImplJBMR1() {
                    }

                    @Override // android.support.v4.widget.SlidingPaneLayout.SlidingPanelLayoutImplBase, android.support.v4.widget.SlidingPaneLayout.SlidingPanelLayoutImpl
/* 1398 */          public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
/* 1399 */              ViewCompat.setLayerPaint(child, ((LayoutParams) child.getLayoutParams()).dimPaint);
                    }
                }

/* 1401 */      class AccessibilityDelegate extends AccessibilityDelegateCompat {
                    private final Rect mTmpRect = new Rect();

/* 1402 */          AccessibilityDelegate() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1407 */          public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
/* 1408 */              AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
/* 1409 */              super.onInitializeAccessibilityNodeInfo(host, superNode);
/* 1410 */              copyNodeInfoNoChildren(info, superNode);
/* 1411 */              superNode.recycle();
/* 1413 */              info.setClassName(SlidingPaneLayout.class.getName());
/* 1414 */              info.setSource(host);
/* 1416 */              Object parentForAccessibility = ViewCompat.getParentForAccessibility(host);
/* 1417 */              if (parentForAccessibility instanceof View) {
/* 1418 */                  info.setParent((View) parentForAccessibility);
                        }
/* 1423 */              int childCount = SlidingPaneLayout.this.getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1425 */                  View child = SlidingPaneLayout.this.getChildAt(i);
/* 1426 */                  if (!filter(child) && child.getVisibility() == 0) {
/* 1428 */                      ViewCompat.setImportantForAccessibility(child, 1);
/* 1430 */                      info.addChild(child);
                            }
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1436 */          public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 1437 */              super.onInitializeAccessibilityEvent(host, event);
/* 1439 */              event.setClassName(SlidingPaneLayout.class.getName());
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1444 */          public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 1445 */              if (filter(child)) {
                            return false;
                        }
/* 1446 */              return super.onRequestSendAccessibilityEvent(host, child, event);
                    }

/* 1451 */          public boolean filter(View child) {
/* 1452 */              return SlidingPaneLayout.this.isDimmed(child);
                    }

/* 1461 */          private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat dest, AccessibilityNodeInfoCompat src) {
/* 1462 */              Rect rect = this.mTmpRect;
/* 1464 */              src.getBoundsInParent(rect);
/* 1465 */              dest.setBoundsInParent(rect);
/* 1467 */              src.getBoundsInScreen(rect);
/* 1468 */              dest.setBoundsInScreen(rect);
/* 1470 */              dest.setVisibleToUser(src.isVisibleToUser());
/* 1471 */              dest.setPackageName(src.getPackageName());
/* 1472 */              dest.setClassName(src.getClassName());
/* 1473 */              dest.setContentDescription(src.getContentDescription());
/* 1475 */              dest.setEnabled(src.isEnabled());
/* 1476 */              dest.setClickable(src.isClickable());
/* 1477 */              dest.setFocusable(src.isFocusable());
/* 1478 */              dest.setFocused(src.isFocused());
/* 1479 */              dest.setAccessibilityFocused(src.isAccessibilityFocused());
/* 1480 */              dest.setSelected(src.isSelected());
/* 1481 */              dest.setLongClickable(src.isLongClickable());
/* 1483 */              dest.addAction(src.getActions());
/* 1485 */              dest.setMovementGranularities(src.getMovementGranularities());
                    }
                }

/* 1490 */      private class DisableLayerRunnable implements Runnable {
                    final View mChildView;

/* 1491 */          DisableLayerRunnable(View childView) {
/* 1493 */              this.mChildView = childView;
                    }

                    @Override // java.lang.Runnable
/* 1497 */          public void run() {
/* 1498 */              if (this.mChildView.getParent() == SlidingPaneLayout.this) {
/* 1499 */                  ViewCompat.setLayerType(this.mChildView, 0, null);
/* 1500 */                  SlidingPaneLayout.access$900(SlidingPaneLayout.this, this.mChildView);
                        }
/* 1502 */              SlidingPaneLayout.access$1000(SlidingPaneLayout.this).remove(this);
                    }
                }
            }
