            package android.support.v4.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.support.v4.view.AccessibilityDelegateCompat;
            import android.support.v4.view.GravityCompat;
            import android.support.v4.view.KeyEventCompat;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.ViewCompat;
            import android.support.v4.view.ViewGroupCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.util.AttributeSet;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 71 */    public class DrawerLayout extends ViewGroup {
                private static final boolean ALLOW_EDGE_LOCK = false;
                private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
                private static final int DEFAULT_SCRIM_COLOR = -1728053248;
                private static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};
                public static final int LOCK_MODE_LOCKED_CLOSED = 1;
                public static final int LOCK_MODE_LOCKED_OPEN = 2;
                public static final int LOCK_MODE_UNLOCKED = 0;
                private static final int MIN_DRAWER_MARGIN = 64;
                private static final int MIN_FLING_VELOCITY = 400;
                private static final int PEEK_DELAY = 160;
                public static final int STATE_DRAGGING = 1;
                public static final int STATE_IDLE = 0;
                public static final int STATE_SETTLING = 2;
                private static final String TAG = "DrawerLayout";
                private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
                private boolean mChildrenCanceledTouch;
                private boolean mDisallowInterceptRequested;
                private int mDrawerState;
                private boolean mFirstLayout;
                private boolean mInLayout;
                private float mInitialMotionX;
                private float mInitialMotionY;
                private final ViewDragCallback mLeftCallback;
                private final ViewDragHelper mLeftDragger;
                private DrawerListener mListener;
                private int mLockModeLeft;
                private int mLockModeRight;
                private int mMinDrawerMargin;
                private final ViewDragCallback mRightCallback;
                private final ViewDragHelper mRightDragger;
                private int mScrimColor;
                private float mScrimOpacity;
                private Paint mScrimPaint;
                private Drawable mShadowLeft;
                private Drawable mShadowRight;
                private CharSequence mTitleLeft;
                private CharSequence mTitleRight;

                public interface DrawerListener {
                    void onDrawerClosed(View view);

                    void onDrawerOpened(View view);

                    void onDrawerSlide(View view, float f);

                    void onDrawerStateChanged(int i);
                }

/* 72 */        static /* synthetic */ int[] access$100() {
/* 73 */            return LAYOUT_ATTRS;
                }

/* 72 */        static /* synthetic */ View access$200(DrawerLayout x0) {
/* 73 */            return x0.findVisibleDrawer();
                }

/* 204 */       public static abstract class SimpleDrawerListener implements DrawerListener {
                    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 205 */           public void onDrawerSlide(View drawerView, float slideOffset) {
                    }

                    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 209 */           public void onDrawerOpened(View drawerView) {
                    }

                    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 213 */           public void onDrawerClosed(View drawerView) {
                    }

                    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 217 */           public void onDrawerStateChanged(int newState) {
                    }
                }

/* 221 */       public DrawerLayout(Context context) {
/* 222 */           this(context, null);
                }

/* 225 */       public DrawerLayout(Context context, AttributeSet attrs) {
/* 226 */           this(context, attrs, 0);
                }

/* 229 */       public DrawerLayout(Context context, AttributeSet attrs, int defStyle) {
/* 230 */           super(context, attrs, defStyle);
/* 137 */           this.mScrimColor = DEFAULT_SCRIM_COLOR;
/* 139 */           this.mScrimPaint = new Paint();
/* 147 */           this.mFirstLayout = true;
/* 232 */           float density = getResources().getDisplayMetrics().density;
/* 233 */           this.mMinDrawerMargin = (int) ((64.0f * density) + 0.5f);
/* 234 */           float minVel = 400.0f * density;
/* 236 */           this.mLeftCallback = new ViewDragCallback(3);
/* 237 */           this.mRightCallback = new ViewDragCallback(5);
/* 239 */           this.mLeftDragger = ViewDragHelper.create(this, 1.0f, this.mLeftCallback);
/* 240 */           this.mLeftDragger.setEdgeTrackingEnabled(1);
/* 241 */           this.mLeftDragger.setMinVelocity(minVel);
/* 242 */           this.mLeftCallback.setDragger(this.mLeftDragger);
/* 244 */           this.mRightDragger = ViewDragHelper.create(this, 1.0f, this.mRightCallback);
/* 245 */           this.mRightDragger.setEdgeTrackingEnabled(2);
/* 246 */           this.mRightDragger.setMinVelocity(minVel);
/* 247 */           this.mRightCallback.setDragger(this.mRightDragger);
/* 250 */           setFocusableInTouchMode(true);
/* 252 */           ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
/* 253 */           ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
                }

/* 269 */       public void setDrawerShadow(Drawable shadowDrawable, int gravity) {
/* 270 */           int absGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
/* 272 */           if ((absGravity & 3) == 3) {
/* 273 */               this.mShadowLeft = shadowDrawable;
/* 274 */               invalidate();
                    }
/* 276 */           if ((absGravity & 5) == 5) {
/* 277 */               this.mShadowRight = shadowDrawable;
/* 278 */               invalidate();
                    }
                }

/* 289 */       public void setDrawerShadow(int resId, int gravity) {
/* 290 */           setDrawerShadow(getResources().getDrawable(resId), gravity);
                }

/* 298 */       public void setScrimColor(int color) {
/* 299 */           this.mScrimColor = color;
/* 300 */           invalidate();
                }

/* 309 */       public void setDrawerListener(DrawerListener listener) {
/* 310 */           this.mListener = listener;
                }

/* 326 */       public void setDrawerLockMode(int lockMode) {
/* 327 */           setDrawerLockMode(lockMode, 3);
/* 328 */           setDrawerLockMode(lockMode, 5);
                }

/* 350 */       public void setDrawerLockMode(int lockMode, int edgeGravity) {
/* 351 */           int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
/* 353 */           if (absGravity == 3) {
/* 354 */               this.mLockModeLeft = lockMode;
                    } else if (absGravity == 5) {
/* 356 */               this.mLockModeRight = lockMode;
                    }
/* 358 */           if (lockMode != 0) {
/* 360 */               ViewDragHelper helper = absGravity == 3 ? this.mLeftDragger : this.mRightDragger;
/* 361 */               helper.cancel();
                    }
                    switch (lockMode) {
                        case 1:
/* 371 */                   View toClose = findDrawerWithGravity(absGravity);
/* 372 */                   if (toClose != null) {
/* 373 */                       closeDrawer(toClose);
                            }
                            break;
                        case 2:
/* 365 */                   View toOpen = findDrawerWithGravity(absGravity);
/* 366 */                   if (toOpen != null) {
/* 367 */                       openDrawer(toOpen);
                            }
                            break;
                    }
                }

/* 398 */       public void setDrawerLockMode(int lockMode, View drawerView) {
/* 399 */           if (!isDrawerView(drawerView)) {
/* 400 */               throw new IllegalArgumentException("View " + drawerView + " is not a drawer with appropriate layout_gravity");
                    }
/* 403 */           int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;
/* 404 */           setDrawerLockMode(lockMode, gravity);
                }

/* 414 */       public int getDrawerLockMode(int edgeGravity) {
/* 415 */           int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
/* 417 */           if (absGravity == 3) {
/* 418 */               return this.mLockModeLeft;
                    }
/* 419 */           if (absGravity == 5) {
/* 420 */               return this.mLockModeRight;
                    }
/* 422 */           return 0;
                }

/* 432 */       public int getDrawerLockMode(View drawerView) {
/* 433 */           int absGravity = getDrawerViewAbsoluteGravity(drawerView);
/* 434 */           if (absGravity == 3) {
/* 435 */               return this.mLockModeLeft;
                    }
/* 436 */           if (absGravity == 5) {
/* 437 */               return this.mLockModeRight;
                    }
/* 439 */           return 0;
                }

/* 452 */       public void setDrawerTitle(int edgeGravity, CharSequence title) {
/* 453 */           int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
/* 455 */           if (absGravity == 3) {
/* 456 */               this.mTitleLeft = title;
                    } else if (absGravity == 5) {
/* 458 */               this.mTitleRight = title;
                    }
                }

/* 470 */       public CharSequence getDrawerTitle(int edgeGravity) {
/* 471 */           int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
/* 473 */           if (absGravity == 3) {
/* 474 */               return this.mTitleLeft;
                    }
/* 475 */           if (absGravity == 5) {
/* 476 */               return this.mTitleRight;
                    }
/* 478 */           return null;
                }

/* 485 */       void updateDrawerState(int forGravity, int activeState, View activeDrawer) {
                    int state;
/* 486 */           int leftState = this.mLeftDragger.getViewDragState();
/* 487 */           int rightState = this.mRightDragger.getViewDragState();
/* 490 */           if (leftState == 1 || rightState == 1) {
/* 491 */               state = 1;
                    } else if (leftState == 2 || rightState == 2) {
/* 493 */               state = 2;
                    } else {
/* 495 */               state = 0;
                    }
/* 498 */           if (activeDrawer != null && activeState == 0) {
/* 499 */               LayoutParams lp = (LayoutParams) activeDrawer.getLayoutParams();
                        if (lp.onScreen == 0.0f) {
/* 501 */                   dispatchOnDrawerClosed(activeDrawer);
                        } else if (lp.onScreen == 1.0f) {
/* 503 */                   dispatchOnDrawerOpened(activeDrawer);
                        }
                    }
/* 507 */           if (state != this.mDrawerState) {
/* 508 */               this.mDrawerState = state;
/* 510 */               if (this.mListener != null) {
/* 511 */                   this.mListener.onDrawerStateChanged(state);
                        }
                    }
                }

/* 516 */       void dispatchOnDrawerClosed(View drawerView) {
                    View rootView;
/* 517 */           LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
/* 518 */           if (lp.knownOpen) {
/* 519 */               lp.knownOpen = false;
/* 520 */               if (this.mListener != null) {
/* 521 */                   this.mListener.onDrawerClosed(drawerView);
                        }
/* 527 */               if (hasWindowFocus() && (rootView = getRootView()) != null) {
/* 530 */                   rootView.sendAccessibilityEvent(32);
                        }
                    }
                }

/* 536 */       void dispatchOnDrawerOpened(View drawerView) {
/* 537 */           LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
/* 538 */           if (!lp.knownOpen) {
/* 539 */               lp.knownOpen = true;
/* 540 */               if (this.mListener != null) {
/* 541 */                   this.mListener.onDrawerOpened(drawerView);
                        }
/* 543 */               sendAccessibilityEvent(32);
                    }
                }

/* 547 */       void dispatchOnDrawerSlide(View drawerView, float slideOffset) {
/* 548 */           if (this.mListener != null) {
/* 549 */               this.mListener.onDrawerSlide(drawerView, slideOffset);
                    }
                }

/* 553 */       void setDrawerViewOffset(View drawerView, float slideOffset) {
/* 554 */           LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
                    if (slideOffset != lp.onScreen) {
/* 559 */               lp.onScreen = slideOffset;
/* 560 */               dispatchOnDrawerSlide(drawerView, slideOffset);
                    }
                }

/* 563 */       float getDrawerViewOffset(View drawerView) {
/* 564 */           return ((LayoutParams) drawerView.getLayoutParams()).onScreen;
                }

/* 571 */       int getDrawerViewAbsoluteGravity(View drawerView) {
/* 572 */           int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;
/* 573 */           return GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
                }

/* 576 */       boolean checkDrawerViewAbsoluteGravity(View drawerView, int checkFor) {
/* 577 */           int absGravity = getDrawerViewAbsoluteGravity(drawerView);
                    return (absGravity & checkFor) == checkFor;
                }

/* 581 */       View findOpenDrawer() {
/* 582 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 584 */               View child = getChildAt(i);
/* 585 */               if (((LayoutParams) child.getLayoutParams()).knownOpen) {
/* 589 */                   return child;
                        }
                    }
/* 589 */           return null;
                }

/* 592 */       void moveDrawerToOffset(View drawerView, float slideOffset) {
/* 593 */           float oldOffset = getDrawerViewOffset(drawerView);
/* 594 */           int width = drawerView.getWidth();
/* 595 */           int oldPos = (int) (width * oldOffset);
/* 596 */           int newPos = (int) (width * slideOffset);
/* 597 */           int dx = newPos - oldPos;
/* 599 */           if (!checkDrawerViewAbsoluteGravity(drawerView, 3)) {
/* 599 */               dx = -dx;
                    }
                    drawerView.offsetLeftAndRight(dx);
/* 601 */           setDrawerViewOffset(drawerView, slideOffset);
                }

/* 610 */       View findDrawerWithGravity(int gravity) {
/* 611 */           int absHorizGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & 7;
/* 613 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 615 */               View child = getChildAt(i);
/* 616 */               int childAbsGravity = getDrawerViewAbsoluteGravity(child);
/* 617 */               if ((childAbsGravity & 7) == absHorizGravity) {
/* 621 */                   return child;
                        }
                    }
/* 621 */           return null;
                }

/* 630 */       static String gravityToString(int gravity) {
/* 631 */           if ((gravity & 3) == 3) {
/* 632 */               return "LEFT";
                    }
/* 634 */           if ((gravity & 5) == 5) {
/* 635 */               return "RIGHT";
                    }
/* 637 */           return Integer.toHexString(gravity);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 641 */       protected void onDetachedFromWindow() {
/* 642 */           super.onDetachedFromWindow();
/* 643 */           this.mFirstLayout = true;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 647 */       protected void onAttachedToWindow() {
/* 648 */           super.onAttachedToWindow();
/* 649 */           this.mFirstLayout = true;
                }

                @Override // android.view.View
/* 653 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 654 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 655 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 656 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 657 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 659 */           if (widthMode != 1073741824 || heightMode != 1073741824) {
/* 660 */               if (isInEditMode()) {
/* 665 */                   if (widthMode != Integer.MIN_VALUE && widthMode == 0) {
/* 669 */                       widthSize = 300;
                            }
/* 671 */                   if (heightMode != Integer.MIN_VALUE && heightMode == 0) {
/* 676 */                       heightSize = 300;
                            }
                        } else {
/* 679 */                   throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
                        }
                    }
/* 684 */           setMeasuredDimension(widthSize, heightSize);
/* 688 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 690 */               View child = getChildAt(i);
/* 692 */               if (child.getVisibility() != 8) {
/* 696 */                   LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 698 */                   if (isContentView(child)) {
/* 700 */                       int contentWidthSpec = View.MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, 1073741824);
/* 702 */                       int contentHeightSpec = View.MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, 1073741824);
/* 704 */                       child.measure(contentWidthSpec, contentHeightSpec);
                            } else if (isDrawerView(child)) {
/* 706 */                       int childGravity = getDrawerViewAbsoluteGravity(child) & 7;
/* 708 */                       if ((0 & childGravity) != 0) {
/* 709 */                           throw new IllegalStateException("Child drawer has absolute gravity " + gravityToString(childGravity) + " but this " + TAG + " already has a drawer view along that edge");
                                }
/* 713 */                       int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, this.mMinDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
/* 716 */                       int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height);
/* 719 */                       child.measure(drawerWidthSpec, drawerHeightSpec);
                            } else {
/* 721 */                       throw new IllegalStateException("Child " + child + " at index " + i + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                            }
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 729 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    int childLeft;
                    float newOffset;
/* 730 */           this.mInLayout = true;
/* 731 */           int width = r - l;
/* 732 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 734 */               View child = getChildAt(i);
/* 736 */               if (child.getVisibility() != 8) {
/* 740 */                   LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 742 */                   if (isContentView(child)) {
/* 743 */                       child.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + child.getMeasuredWidth(), lp.topMargin + child.getMeasuredHeight());
                            } else {
/* 747 */                       int childWidth = child.getMeasuredWidth();
/* 748 */                       int childHeight = child.getMeasuredHeight();
/* 752 */                       if (checkDrawerViewAbsoluteGravity(child, 3)) {
/* 753 */                           childLeft = (-childWidth) + ((int) (childWidth * lp.onScreen));
/* 754 */                           newOffset = (childWidth + childLeft) / childWidth;
                                } else {
/* 756 */                           childLeft = width - ((int) (childWidth * lp.onScreen));
/* 757 */                           newOffset = (width - childLeft) / childWidth;
                                }
/* 760 */                       boolean changeOffset = newOffset != lp.onScreen;
/* 762 */                       int vgrav = lp.gravity & 112;
                                switch (vgrav) {
                                    case 16:
/* 782 */                               int height = b - t;
/* 783 */                               int childTop = (height - childHeight) / 2;
/* 787 */                               if (childTop < lp.topMargin) {
/* 788 */                                   childTop = lp.topMargin;
                                        } else if (childTop + childHeight > height - lp.bottomMargin) {
/* 790 */                                   childTop = (height - lp.bottomMargin) - childHeight;
                                        }
/* 792 */                               child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                                        break;
                                    case 80:
/* 773 */                               int height2 = b - t;
/* 774 */                               child.layout(childLeft, (height2 - lp.bottomMargin) - child.getMeasuredHeight(), childLeft + childWidth, height2 - lp.bottomMargin);
                                        break;
                                    default:
/* 767 */                               child.layout(childLeft, lp.topMargin, childLeft + childWidth, lp.topMargin + childHeight);
                                        break;
                                }
/* 798 */                       if (changeOffset) {
/* 799 */                           setDrawerViewOffset(child, newOffset);
                                }
/* 802 */                       int newVisibility = lp.onScreen > 0.0f ? 0 : 4;
/* 803 */                       if (child.getVisibility() != newVisibility) {
/* 804 */                           child.setVisibility(newVisibility);
                                }
                            }
                        }
                    }
/* 808 */           this.mInLayout = false;
/* 809 */           this.mFirstLayout = false;
                }

                @Override // android.view.View, android.view.ViewParent
/* 813 */       public void requestLayout() {
/* 814 */           if (!this.mInLayout) {
/* 815 */               super.requestLayout();
                    }
                }

                @Override // android.view.View
/* 820 */       public void computeScroll() {
/* 821 */           int childCount = getChildCount();
/* 822 */           float scrimOpacity = 0.0f;
                    for (int i = 0; i < childCount; i++) {
/* 824 */               float onscreen = ((LayoutParams) getChildAt(i).getLayoutParams()).onScreen;
/* 825 */               scrimOpacity = Math.max(scrimOpacity, onscreen);
                    }
/* 827 */           this.mScrimOpacity = scrimOpacity;
/* 830 */           if (this.mLeftDragger.continueSettling(true) | this.mRightDragger.continueSettling(true)) {
/* 831 */               ViewCompat.postInvalidateOnAnimation(this);
                    }
                }

/* 835 */       private static boolean hasOpaqueBackground(View v) {
/* 836 */           Drawable bg = v.getBackground();
                    return bg != null && bg.getOpacity() == -1;
                }

                @Override // android.view.ViewGroup
/* 844 */       protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
/* 845 */           int height = getHeight();
/* 846 */           boolean drawingContent = isContentView(child);
/* 847 */           int clipLeft = 0;
                    int clipRight = getWidth();
/* 849 */           int restoreCount = canvas.save();
/* 850 */           if (drawingContent) {
/* 851 */               int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 853 */                   View v = getChildAt(i);
/* 854 */                   if (v != child && v.getVisibility() == 0 && hasOpaqueBackground(v) && isDrawerView(v) && v.getHeight() >= height) {
/* 860 */                       if (checkDrawerViewAbsoluteGravity(v, 3)) {
/* 861 */                           int vright = v.getRight();
/* 862 */                           if (vright > clipLeft) {
                                        clipLeft = vright;
                                    }
                                } else {
/* 864 */                           int vleft = v.getLeft();
/* 865 */                           if (vleft < clipRight) {
                                        clipRight = vleft;
                                    }
                                }
                            }
                        }
/* 868 */               canvas.clipRect(clipLeft, 0, clipRight, getHeight());
                    }
/* 870 */           boolean result = super.drawChild(canvas, child, drawingTime);
/* 871 */           canvas.restoreToCount(restoreCount);
                    if (this.mScrimOpacity > 0.0f && drawingContent) {
/* 874 */               int baseAlpha = (this.mScrimColor & (-16777216)) >>> 24;
/* 875 */               int imag = (int) (baseAlpha * this.mScrimOpacity);
/* 876 */               int color = (imag << 24) | (this.mScrimColor & ViewCompat.MEASURED_SIZE_MASK);
/* 877 */               this.mScrimPaint.setColor(color);
/* 879 */               canvas.drawRect(clipLeft, 0.0f, clipRight, getHeight(), this.mScrimPaint);
                    } else if (this.mShadowLeft != null && checkDrawerViewAbsoluteGravity(child, 3)) {
/* 881 */               int shadowWidth = this.mShadowLeft.getIntrinsicWidth();
/* 882 */               int childRight = child.getRight();
/* 883 */               int drawerPeekDistance = this.mLeftDragger.getEdgeSize();
/* 884 */               float alpha = Math.max(0.0f, Math.min(childRight / drawerPeekDistance, 1.0f));
/* 886 */               this.mShadowLeft.setBounds(childRight, child.getTop(), childRight + shadowWidth, child.getBottom());
/* 888 */               this.mShadowLeft.setAlpha((int) (255.0f * alpha));
/* 889 */               this.mShadowLeft.draw(canvas);
                    } else if (this.mShadowRight != null && checkDrawerViewAbsoluteGravity(child, 5)) {
/* 891 */               int shadowWidth2 = this.mShadowRight.getIntrinsicWidth();
/* 892 */               int childLeft = child.getLeft();
/* 893 */               int showing = getWidth() - childLeft;
/* 894 */               int drawerPeekDistance2 = this.mRightDragger.getEdgeSize();
/* 895 */               float alpha2 = Math.max(0.0f, Math.min(showing / drawerPeekDistance2, 1.0f));
/* 897 */               this.mShadowRight.setBounds(childLeft - shadowWidth2, child.getTop(), childLeft, child.getBottom());
/* 899 */               this.mShadowRight.setAlpha((int) (255.0f * alpha2));
/* 900 */               this.mShadowRight.draw(canvas);
                    }
/* 902 */           return result;
                }

/* 905 */       boolean isContentView(View child) {
                    return ((LayoutParams) child.getLayoutParams()).gravity == 0;
                }

/* 909 */       boolean isDrawerView(View child) {
/* 910 */           int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
/* 911 */           int absGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(child));
                    return (absGravity & 7) != 0;
                }

                @Override // android.view.ViewGroup
/* 917 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 918 */           int action = MotionEventCompat.getActionMasked(ev);
/* 921 */           boolean interceptForDrag = this.mLeftDragger.shouldInterceptTouchEvent(ev) | this.mRightDragger.shouldInterceptTouchEvent(ev);
/* 924 */           boolean interceptForTap = false;
                    switch (action) {
                        case 0:
/* 928 */                   float x = ev.getX();
/* 929 */                   float y = ev.getY();
/* 930 */                   this.mInitialMotionX = x;
/* 931 */                   this.mInitialMotionY = y;
                            if (this.mScrimOpacity > 0.0f && isContentView(this.mLeftDragger.findTopChildUnder((int) x, (int) y))) {
/* 934 */                       interceptForTap = true;
                            }
/* 936 */                   this.mDisallowInterceptRequested = false;
/* 937 */                   this.mChildrenCanceledTouch = false;
                            break;
                        case 1:
                        case 3:
/* 952 */                   closeDrawers(true);
/* 953 */                   this.mDisallowInterceptRequested = false;
/* 954 */                   this.mChildrenCanceledTouch = false;
                            break;
                        case 2:
/* 943 */                   if (this.mLeftDragger.checkTouchSlop(3)) {
/* 944 */                       this.mLeftCallback.removeCallbacks();
/* 945 */                       this.mRightCallback.removeCallbacks();
                            }
                            break;
                    }
                    return interceptForDrag || interceptForTap || hasPeekingDrawer() || this.mChildrenCanceledTouch;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View
/* 962 */       public boolean onTouchEvent(MotionEvent ev) {
                    View openDrawer;
/* 963 */           this.mLeftDragger.processTouchEvent(ev);
/* 964 */           this.mRightDragger.processTouchEvent(ev);
/* 966 */           int action = ev.getAction();
                    switch (action & 255) {
                        case 0:
/* 971 */                   float x = ev.getX();
/* 972 */                   float y = ev.getY();
/* 973 */                   this.mInitialMotionX = x;
/* 974 */                   this.mInitialMotionY = y;
/* 975 */                   this.mDisallowInterceptRequested = false;
/* 976 */                   this.mChildrenCanceledTouch = false;
/* 967 */                   return true;
                        case 1:
/* 981 */                   float x2 = ev.getX();
/* 982 */                   float y2 = ev.getY();
/* 983 */                   boolean peekingOnly = true;
/* 984 */                   View touchedView = this.mLeftDragger.findTopChildUnder((int) x2, (int) y2);
/* 985 */                   if (touchedView != null && isContentView(touchedView)) {
/* 986 */                       float dx = x2 - this.mInitialMotionX;
/* 987 */                       float dy = y2 - this.mInitialMotionY;
/* 988 */                       int slop = this.mLeftDragger.getTouchSlop();
/* 992 */                       if ((dx * dx) + (dy * dy) < slop * slop && (openDrawer = findOpenDrawer()) != null) {
/* 993 */                           peekingOnly = getDrawerLockMode(openDrawer) == 2;
                                }
                            }
/* 997 */                   closeDrawers(peekingOnly);
/* 998 */                   this.mDisallowInterceptRequested = false;
/* 967 */                   return true;
                        case 2:
                        default:
/* 967 */                   return true;
                        case 3:
/* 1003 */                  closeDrawers(true);
/* 1004 */                  this.mDisallowInterceptRequested = false;
/* 1005 */                  this.mChildrenCanceledTouch = false;
/* 967 */                   return true;
                    }
                }

                @Override // android.view.ViewGroup, android.view.ViewParent
/* 1017 */      public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
/* 1018 */          super.requestDisallowInterceptTouchEvent(disallowIntercept);
/* 1020 */          this.mDisallowInterceptRequested = disallowIntercept;
/* 1021 */          if (disallowIntercept) {
/* 1022 */              closeDrawers(true);
                    }
                }

/* 1029 */      public void closeDrawers() {
/* 1030 */          closeDrawers(false);
                }

/* 1033 */      void closeDrawers(boolean peekingOnly) {
/* 1034 */          boolean needsInvalidate = false;
/* 1035 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1037 */              View child = getChildAt(i);
/* 1038 */              LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1040 */              if (isDrawerView(child) && (!peekingOnly || lp.isPeeking)) {
/* 1044 */                  int childWidth = child.getWidth();
/* 1046 */                  if (checkDrawerViewAbsoluteGravity(child, 3)) {
/* 1047 */                      needsInvalidate |= this.mLeftDragger.smoothSlideViewTo(child, -childWidth, child.getTop());
                            } else {
/* 1050 */                      needsInvalidate |= this.mRightDragger.smoothSlideViewTo(child, getWidth(), child.getTop());
                            }
/* 1054 */                  lp.isPeeking = false;
                        }
                    }
/* 1057 */          this.mLeftCallback.removeCallbacks();
/* 1058 */          this.mRightCallback.removeCallbacks();
/* 1060 */          if (needsInvalidate) {
/* 1061 */              invalidate();
                    }
                }

/* 1070 */      public void openDrawer(View drawerView) {
/* 1071 */          if (!isDrawerView(drawerView)) {
/* 1072 */              throw new IllegalArgumentException("View " + drawerView + " is not a sliding drawer");
                    }
/* 1075 */          if (this.mFirstLayout) {
/* 1076 */              LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
/* 1077 */              lp.onScreen = 1.0f;
/* 1078 */              lp.knownOpen = true;
                    } else if (checkDrawerViewAbsoluteGravity(drawerView, 3)) {
/* 1081 */              this.mLeftDragger.smoothSlideViewTo(drawerView, 0, drawerView.getTop());
                    } else {
/* 1083 */              this.mRightDragger.smoothSlideViewTo(drawerView, getWidth() - drawerView.getWidth(), drawerView.getTop());
                    }
/* 1087 */          invalidate();
                }

/* 1096 */      public void openDrawer(int gravity) {
/* 1097 */          View drawerView = findDrawerWithGravity(gravity);
/* 1098 */          if (drawerView == null) {
/* 1099 */              throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(gravity));
                    }
/* 1102 */          openDrawer(drawerView);
                }

/* 1110 */      public void closeDrawer(View drawerView) {
/* 1111 */          if (!isDrawerView(drawerView)) {
/* 1112 */              throw new IllegalArgumentException("View " + drawerView + " is not a sliding drawer");
                    }
/* 1115 */          if (this.mFirstLayout) {
/* 1116 */              LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
/* 1117 */              lp.onScreen = 0.0f;
/* 1118 */              lp.knownOpen = false;
                    } else if (checkDrawerViewAbsoluteGravity(drawerView, 3)) {
/* 1121 */              this.mLeftDragger.smoothSlideViewTo(drawerView, -drawerView.getWidth(), drawerView.getTop());
                    } else {
/* 1124 */              this.mRightDragger.smoothSlideViewTo(drawerView, getWidth(), drawerView.getTop());
                    }
/* 1127 */          invalidate();
                }

/* 1136 */      public void closeDrawer(int gravity) {
/* 1137 */          View drawerView = findDrawerWithGravity(gravity);
/* 1138 */          if (drawerView == null) {
/* 1139 */              throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(gravity));
                    }
/* 1142 */          closeDrawer(drawerView);
                }

/* 1155 */      public boolean isDrawerOpen(View drawer) {
/* 1156 */          if (!isDrawerView(drawer)) {
/* 1157 */              throw new IllegalArgumentException("View " + drawer + " is not a drawer");
                    }
/* 1159 */          return ((LayoutParams) drawer.getLayoutParams()).knownOpen;
                }

/* 1171 */      public boolean isDrawerOpen(int drawerGravity) {
/* 1172 */          View drawerView = findDrawerWithGravity(drawerGravity);
/* 1173 */          if (drawerView != null) {
/* 1174 */              return isDrawerOpen(drawerView);
                    }
                    return false;
                }

/* 1187 */      public boolean isDrawerVisible(View drawer) {
/* 1188 */          if (isDrawerView(drawer)) {
                        return ((LayoutParams) drawer.getLayoutParams()).onScreen > 0.0f;
                    }
/* 1189 */          throw new IllegalArgumentException("View " + drawer + " is not a drawer");
                }

/* 1202 */      public boolean isDrawerVisible(int drawerGravity) {
/* 1203 */          View drawerView = findDrawerWithGravity(drawerGravity);
/* 1204 */          if (drawerView != null) {
/* 1205 */              return isDrawerVisible(drawerView);
                    }
                    return false;
                }

/* 1210 */      private boolean hasPeekingDrawer() {
/* 1211 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1213 */              LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
/* 1214 */              if (lp.isPeeking) {
/* 1215 */                  return true;
                        }
                    }
/* 1218 */          return false;
                }

                @Override // android.view.ViewGroup
/* 1222 */      protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 1223 */          return new LayoutParams(-1, -1);
                }

                @Override // android.view.ViewGroup
/* 1227 */      protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 1228 */          if (p instanceof LayoutParams) {
                        return new LayoutParams((LayoutParams) p);
                    }
                    return p instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) p) : new LayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 1236 */      protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
                    return (p instanceof LayoutParams) && super.checkLayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 1241 */      public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 1242 */          return new LayoutParams(getContext(), attrs);
                }

/* 1245 */      private boolean hasVisibleDrawer() {
                    return findVisibleDrawer() != null;
                }

/* 1249 */      private View findVisibleDrawer() {
/* 1250 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1252 */              View child = getChildAt(i);
/* 1253 */              if (isDrawerView(child) && isDrawerVisible(child)) {
/* 1257 */                  return child;
                        }
                    }
/* 1257 */          return null;
                }

/* 1261 */      void cancelChildViewTouch() {
/* 1262 */          if (!this.mChildrenCanceledTouch) {
/* 1263 */              long now = SystemClock.uptimeMillis();
/* 1264 */              MotionEvent cancelEvent = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
/* 1266 */              int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1268 */                  getChildAt(i).dispatchTouchEvent(cancelEvent);
                        }
/* 1270 */              cancelEvent.recycle();
/* 1271 */              this.mChildrenCanceledTouch = true;
                    }
                }

                @Override // android.view.View, android.view.KeyEvent.Callback
/* 1276 */      public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 1277 */          if (keyCode != 4 || !hasVisibleDrawer()) {
                        return super.onKeyDown(keyCode, event);
                    }
/* 1278 */          KeyEventCompat.startTracking(event);
/* 1279 */          return true;
                }

                @Override // android.view.View, android.view.KeyEvent.Callback
/* 1285 */      public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 1286 */          if (keyCode == 4) {
/* 1287 */              View visibleDrawer = findVisibleDrawer();
/* 1288 */              if (visibleDrawer != null && getDrawerLockMode(visibleDrawer) == 0) {
/* 1289 */                  closeDrawers();
                        }
                        return visibleDrawer != null;
                    }
/* 1293 */          return super.onKeyUp(keyCode, event);
                }

                @Override // android.view.View
/* 1297 */      protected void onRestoreInstanceState(Parcelable state) {
                    View toOpen;
/* 1298 */          SavedState ss = (SavedState) state;
/* 1299 */          super.onRestoreInstanceState(ss.getSuperState());
/* 1301 */          if (ss.openDrawerGravity != 0 && (toOpen = findDrawerWithGravity(ss.openDrawerGravity)) != null) {
/* 1304 */              openDrawer(toOpen);
                    }
/* 1308 */          setDrawerLockMode(ss.lockModeLeft, 3);
/* 1309 */          setDrawerLockMode(ss.lockModeRight, 5);
                }

                @Override // android.view.View
/* 1313 */      protected Parcelable onSaveInstanceState() {
/* 1314 */          Parcelable superState = super.onSaveInstanceState();
/* 1316 */          SavedState ss = new SavedState(superState);
/* 1318 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1320 */              View child = getChildAt(i);
/* 1321 */              if (isDrawerView(child)) {
/* 1325 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1326 */                  if (lp.knownOpen) {
/* 1327 */                      ss.openDrawerGravity = lp.gravity;
                                break;
                            }
                        }
                    }
/* 1333 */          ss.lockModeLeft = this.mLockModeLeft;
/* 1334 */          ss.lockModeRight = this.mLockModeRight;
/* 1336 */          return ss;
                }

/* 1346 */      protected static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00651();
                    int lockModeLeft;
                    int lockModeRight;
                    int openDrawerGravity;

/* 1347 */          public SavedState(Parcel in) {
/* 1348 */              super(in);
/* 1343 */              this.openDrawerGravity = 0;
/* 1344 */              this.lockModeLeft = 0;
/* 1345 */              this.lockModeRight = 0;
/* 1349 */              this.openDrawerGravity = in.readInt();
                    }

/* 1352 */          public SavedState(Parcelable superState) {
/* 1353 */              super(superState);
/* 1343 */              this.openDrawerGravity = 0;
/* 1344 */              this.lockModeLeft = 0;
/* 1345 */              this.lockModeRight = 0;
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 1357 */          public void writeToParcel(Parcel dest, int flags) {
/* 1358 */              super.writeToParcel(dest, flags);
/* 1359 */              dest.writeInt(this.openDrawerGravity);
                    }

                    /* JADX INFO: renamed from: android.support.v4.widget.DrawerLayout$SavedState$1 */
/* 1361 */          static class C00651 implements Parcelable.Creator<SavedState> {
/* 1362 */              C00651() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 1362 */              public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 1363 */                  return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 1362 */              public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 1363 */                  return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1365 */              public SavedState createFromParcel(Parcel source) {
/* 1366 */                  return new SavedState(source);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1370 */              public SavedState[] newArray(int size) {
/* 1371 */                  return new SavedState[size];
                        }
                    }
                }

/* 1374 */      private class ViewDragCallback extends ViewDragHelper.Callback {
                    private final int mAbsGravity;
                    private ViewDragHelper mDragger;
                    private final Runnable mPeekRunnable = new RunnableC00661();

/* 1375 */          static /* synthetic */ void access$000(ViewDragCallback x0) {
/* 1376 */              x0.peekDrawer();
                    }

                    /* JADX INFO: renamed from: android.support.v4.widget.DrawerLayout$ViewDragCallback$1 */
/* 1378 */          class RunnableC00661 implements Runnable {
/* 1379 */              RunnableC00661() {
                        }

                        @Override // java.lang.Runnable
/* 1381 */              public void run() {
/* 1382 */                  ViewDragCallback.access$000(ViewDragCallback.this);
                        }
                    }

/* 1385 */          public ViewDragCallback(int gravity) {
/* 1387 */              this.mAbsGravity = gravity;
                    }

/* 1390 */          public void setDragger(ViewDragHelper dragger) {
/* 1391 */              this.mDragger = dragger;
                    }

/* 1394 */          public void removeCallbacks() {
/* 1395 */              DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1401 */          public boolean tryCaptureView(View child, int pointerId) {
                        return DrawerLayout.this.isDrawerView(child) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(child, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(child) == 0;
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1407 */          public void onViewDragStateChanged(int state) {
/* 1408 */              DrawerLayout.this.updateDrawerState(this.mAbsGravity, state, this.mDragger.getCapturedView());
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1413 */          public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                        float offset;
/* 1414 */              int childWidth = changedView.getWidth();
/* 1417 */              if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(changedView, 3)) {
/* 1418 */                  offset = (childWidth + left) / childWidth;
                        } else {
/* 1420 */                  int width = DrawerLayout.this.getWidth();
/* 1421 */                  offset = (width - left) / childWidth;
                        }
/* 1423 */              DrawerLayout.this.setDrawerViewOffset(changedView, offset);
/* 1424 */              changedView.setVisibility(offset == 0.0f ? 4 : 0);
/* 1425 */              DrawerLayout.this.invalidate();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1429 */          public void onViewCaptured(View capturedChild, int activePointerId) {
/* 1430 */              LayoutParams lp = (LayoutParams) capturedChild.getLayoutParams();
/* 1431 */              lp.isPeeking = false;
/* 1433 */              closeOtherDrawer();
                    }

/* 1436 */          private void closeOtherDrawer() {
/* 1437 */              int otherGrav = this.mAbsGravity == 3 ? 5 : 3;
/* 1438 */              View toClose = DrawerLayout.this.findDrawerWithGravity(otherGrav);
/* 1439 */              if (toClose != null) {
/* 1440 */                  DrawerLayout.this.closeDrawer(toClose);
                        }
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1447 */          public void onViewReleased(View releasedChild, float xvel, float yvel) {
                        int left;
/* 1448 */              float offset = DrawerLayout.this.getDrawerViewOffset(releasedChild);
/* 1449 */              int childWidth = releasedChild.getWidth();
/* 1452 */              if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(releasedChild, 3)) {
/* 1453 */                  left = (xvel > 0.0f || (xvel == 0.0f && offset > 0.5f)) ? 0 : -childWidth;
                        } else {
/* 1455 */                  int width = DrawerLayout.this.getWidth();
                            left = (xvel < 0.0f || (xvel == 0.0f && offset > 0.5f)) ? width - childWidth : width;
                        }
/* 1459 */              this.mDragger.settleCapturedViewAt(left, releasedChild.getTop());
/* 1460 */              DrawerLayout.this.invalidate();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1464 */          public void onEdgeTouched(int edgeFlags, int pointerId) {
/* 1465 */              DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
                    }

/* 1470 */          private void peekDrawer() {
                        View toCapture;
                        int childLeft;
/* 1471 */              int peekDistance = this.mDragger.getEdgeSize();
/* 1472 */              boolean leftEdge = this.mAbsGravity == 3;
/* 1473 */              if (leftEdge) {
/* 1474 */                  toCapture = DrawerLayout.this.findDrawerWithGravity(3);
/* 1475 */                  childLeft = (toCapture != null ? -toCapture.getWidth() : 0) + peekDistance;
                        } else {
/* 1477 */                  toCapture = DrawerLayout.this.findDrawerWithGravity(5);
/* 1478 */                  childLeft = DrawerLayout.this.getWidth() - peekDistance;
                        }
/* 1481 */              if (toCapture != null) {
                            if (((leftEdge && toCapture.getLeft() < childLeft) || (!leftEdge && toCapture.getLeft() > childLeft)) && DrawerLayout.this.getDrawerLockMode(toCapture) == 0) {
/* 1484 */                      LayoutParams lp = (LayoutParams) toCapture.getLayoutParams();
/* 1485 */                      this.mDragger.smoothSlideViewTo(toCapture, childLeft, toCapture.getTop());
/* 1486 */                      lp.isPeeking = true;
/* 1487 */                      DrawerLayout.this.invalidate();
/* 1489 */                      closeOtherDrawer();
/* 1491 */                      DrawerLayout.this.cancelChildViewTouch();
                            }
                        }
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1503 */          public boolean onEdgeLock(int edgeFlags) {
/* 1504 */              return false;
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1509 */          public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                        View toCapture;
/* 1510 */              if ((edgeFlags & 1) == 1) {
/* 1511 */                  toCapture = DrawerLayout.this.findDrawerWithGravity(3);
                        } else {
/* 1513 */                  toCapture = DrawerLayout.this.findDrawerWithGravity(5);
                        }
/* 1516 */              if (toCapture != null && DrawerLayout.this.getDrawerLockMode(toCapture) == 0) {
/* 1517 */                  this.mDragger.captureChildView(toCapture, pointerId);
                        }
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1522 */          public int getViewHorizontalDragRange(View child) {
/* 1523 */              return child.getWidth();
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1527 */          public int clampViewPositionHorizontal(View child, int left, int dx) {
/* 1528 */              if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(child, 3)) {
/* 1529 */                  return Math.max(-child.getWidth(), Math.min(left, 0));
                        }
/* 1531 */              int width = DrawerLayout.this.getWidth();
/* 1532 */              return Math.max(width - child.getWidth(), Math.min(left, width));
                    }

                    @Override // android.support.v4.widget.ViewDragHelper.Callback
/* 1537 */          public int clampViewPositionVertical(View child, int top, int dy) {
/* 1538 */              return child.getTop();
                    }
                }

/* 1548 */      public static class LayoutParams extends ViewGroup.MarginLayoutParams {
                    public int gravity;
                    boolean isPeeking;
                    boolean knownOpen;
                    float onScreen;

/* 1549 */          public LayoutParams(Context c, AttributeSet attrs) {
/* 1550 */              super(c, attrs);
/* 1544 */              this.gravity = 0;
/* 1552 */              TypedArray a = c.obtainStyledAttributes(attrs, DrawerLayout.access$100());
/* 1553 */              this.gravity = a.getInt(0, 0);
/* 1554 */              a.recycle();
                    }

/* 1557 */          public LayoutParams(int width, int height) {
/* 1558 */              super(width, height);
/* 1544 */              this.gravity = 0;
                    }

/* 1561 */          public LayoutParams(int width, int height, int gravity) {
/* 1562 */              this(width, height);
/* 1563 */              this.gravity = gravity;
                    }

/* 1566 */          public LayoutParams(LayoutParams source) {
/* 1567 */              super((ViewGroup.MarginLayoutParams) source);
/* 1544 */              this.gravity = 0;
/* 1568 */              this.gravity = source.gravity;
                    }

/* 1571 */          public LayoutParams(ViewGroup.LayoutParams source) {
/* 1572 */              super(source);
/* 1544 */              this.gravity = 0;
                    }

/* 1575 */          public LayoutParams(ViewGroup.MarginLayoutParams source) {
/* 1576 */              super(source);
/* 1544 */              this.gravity = 0;
                    }
                }

/* 1578 */      class AccessibilityDelegate extends AccessibilityDelegateCompat {
                    private final Rect mTmpRect = new Rect();

/* 1579 */          AccessibilityDelegate() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1584 */          public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
/* 1585 */              AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
/* 1586 */              super.onInitializeAccessibilityNodeInfo(host, superNode);
/* 1588 */              info.setClassName(DrawerLayout.class.getName());
/* 1589 */              info.setSource(host);
/* 1590 */              Object parentForAccessibility = ViewCompat.getParentForAccessibility(host);
/* 1591 */              if (parentForAccessibility instanceof View) {
/* 1592 */                  info.setParent((View) parentForAccessibility);
                        }
/* 1594 */              copyNodeInfoNoChildren(info, superNode);
/* 1596 */              superNode.recycle();
/* 1598 */              addChildrenForAccessibility(info, (ViewGroup) host);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1602 */          public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 1603 */              super.onInitializeAccessibilityEvent(host, event);
/* 1605 */              event.setClassName(DrawerLayout.class.getName());
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1614 */          public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 1615 */              if (event.getEventType() != 32) {
                            return super.dispatchPopulateAccessibilityEvent(host, event);
                        }
/* 1616 */              List<CharSequence> eventText = event.getText();
/* 1617 */              View visibleDrawer = DrawerLayout.access$200(DrawerLayout.this);
/* 1618 */              if (visibleDrawer != null) {
/* 1619 */                  int edgeGravity = DrawerLayout.this.getDrawerViewAbsoluteGravity(visibleDrawer);
/* 1620 */                  CharSequence title = DrawerLayout.this.getDrawerTitle(edgeGravity);
/* 1621 */                  if (title != null) {
/* 1622 */                      eventText.add(title);
                            }
                        }
/* 1626 */              return true;
                    }

/* 1632 */          private void addChildrenForAccessibility(AccessibilityNodeInfoCompat info, ViewGroup v) {
/* 1633 */              int childCount = v.getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1635 */                  View child = v.getChildAt(i);
/* 1636 */                  if (!filter(child)) {
/* 1643 */                      int importance = ViewCompat.getImportantForAccessibility(child);
                                switch (importance) {
                                    case 0:
/* 1656 */                              ViewCompat.setImportantForAccessibility(child, 1);
                                        break;
                                    case 1:
                                        break;
                                    case 2:
/* 1650 */                              if (child instanceof ViewGroup) {
/* 1651 */                                  addChildrenForAccessibility(info, (ViewGroup) child);
                                        } else {
                                            continue;
                                        }
                                        break;
                                    case 3:
                                    case 4:
                                    default:
                                        continue;
                                }
/* 1659 */                      info.addChild(child);
                            }
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 1667 */          public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 1668 */              if (filter(child)) {
                            return false;
                        }
/* 1669 */              return super.onRequestSendAccessibilityEvent(host, child, event);
                    }

/* 1674 */          public boolean filter(View child) {
/* 1675 */              View openDrawer = DrawerLayout.this.findOpenDrawer();
                        return (openDrawer == null || openDrawer == child) ? false : true;
                    }

/* 1685 */          private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat dest, AccessibilityNodeInfoCompat src) {
/* 1686 */              Rect rect = this.mTmpRect;
/* 1688 */              src.getBoundsInParent(rect);
/* 1689 */              dest.setBoundsInParent(rect);
/* 1691 */              src.getBoundsInScreen(rect);
/* 1692 */              dest.setBoundsInScreen(rect);
/* 1694 */              dest.setVisibleToUser(src.isVisibleToUser());
/* 1695 */              dest.setPackageName(src.getPackageName());
/* 1696 */              dest.setClassName(src.getClassName());
/* 1697 */              dest.setContentDescription(src.getContentDescription());
/* 1699 */              dest.setEnabled(src.isEnabled());
/* 1700 */              dest.setClickable(src.isClickable());
/* 1701 */              dest.setFocusable(src.isFocusable());
/* 1702 */              dest.setFocused(src.isFocused());
/* 1703 */              dest.setAccessibilityFocused(src.isAccessibilityFocused());
/* 1704 */              dest.setSelected(src.isSelected());
/* 1705 */              dest.setLongClickable(src.isLongClickable());
/* 1707 */              dest.addAction(src.getActions());
                    }
                }
            }
