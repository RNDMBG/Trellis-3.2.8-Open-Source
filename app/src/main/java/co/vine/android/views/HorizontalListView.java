            package co.vine.android.views;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.TransitionDrawable;
            import android.os.Handler;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.VelocityTracker;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.Adapter;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import android.widget.Scroller;
            import co.vine.android.dragsort.DragSortWidget;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public abstract class HorizontalListView extends AdapterView<ListAdapter> {
                public static final int NO_POSITION = -1;
                private static final int TOUCH_MODE_DONE_WAITING = 2;
                private static final int TOUCH_MODE_DOWN = 0;
                private static final int TOUCH_MODE_FLING = 4;
                private static final int TOUCH_MODE_REST = -1;
                private static final int TOUCH_MODE_SCROLL = 3;
                private static final int TOUCH_MODE_TAP = 1;
                private ListAdapter mAdapter;
                private int mChildFillWidth;
                private ChildWidthListener mChildWidthListener;
                private boolean mClipToPadding;
                private boolean mDataChanged;
                private DataSetObserver mDataSetObserver;
                private Drawable mDivider;
                private Paint mDividerPaint;
                private int mDividerWidth;
                private int mDownPosition;
                private final int mEdgePadding;
                private final float mFillWidthHeightRatio;
                private boolean mFingerScrollEnabled;
                private int mFirstVisiblePos;
                private final FlingRunnable mFlingRunnable;
                private int mHeightMeasureSpec;
                private final boolean[] mIsRecycled;
                private int mItemCount;
                private int mLastX;
                private int mLongPressMillis;
                private final int mMaximumFlingVelocity;
                private final int mMinimumFlingVelocity;
                private int mMotionPosition;
                private int mMotionX;
                private int mMotionY;
                private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
                private OnScrollListener mOnScrollListener;
                private CheckForLongPress mPendingCheckForLongPress;
                private CheckForTap mPendingCheckForTap;
                private PerformClick mPerformClick;
                private final Recycler mRecycler;
                private Drawable mSelector;
                private Rect mTouchFrame;
                private int mTouchMode;
                private Runnable mTouchModeReset;
                private final int mTouchSlop;
                private VelocityTracker mVelocityTracker;

                public interface ChildWidthListener {
                    void onChildWidthMeasured(int i);
                }

                public interface OnScrollListener {
                    void onScroll();
                }

                protected abstract int getEdgePadding(TypedArray typedArray);

                protected abstract float getFillWidthHeightRatio(TypedArray typedArray);

                protected abstract TypedArray getStylingArray(Context context, AttributeSet attributeSet, int i);

                protected abstract void initializeDividerStyles(TypedArray typedArray);

/* 46 */        static /* synthetic */ int access$000(HorizontalListView x0) {
/* 47 */            return x0.mTouchMode;
                }

/* 46 */        static /* synthetic */ int access$002(HorizontalListView x0, int x1) {
/* 47 */            x0.mTouchMode = x1;
                    return x1;
                }

/* 46 */        static /* synthetic */ boolean access$100(HorizontalListView x0) {
/* 47 */            return x0.mDataChanged;
                }

/* 46 */        static /* synthetic */ int access$1000(HorizontalListView x0) {
/* 47 */            return x0.mLongPressMillis;
                }

/* 46 */        static /* synthetic */ boolean access$102(HorizontalListView x0, boolean x1) {
/* 47 */            x0.mDataChanged = x1;
                    return x1;
                }

/* 46 */        static /* synthetic */ CheckForLongPress access$1100(HorizontalListView x0) {
/* 47 */            return x0.mPendingCheckForLongPress;
                }

/* 46 */        static /* synthetic */ CheckForLongPress access$1102(HorizontalListView x0, CheckForLongPress x1) {
/* 47 */            x0.mPendingCheckForLongPress = x1;
                    return x1;
                }

/* 46 */        static /* synthetic */ int access$1300(HorizontalListView x0) {
/* 47 */            return x0.getWindowAttachCount();
                }

/* 46 */        static /* synthetic */ int access$1400(HorizontalListView x0) {
/* 47 */            return x0.getWindowAttachCount();
                }

/* 46 */        static /* synthetic */ boolean access$1500(HorizontalListView x0, View x1, int x2, long x3) {
/* 47 */            return x0.performLongPress(x1, x2, x3);
                }

/* 46 */        static /* synthetic */ int access$1600(HorizontalListView x0) {
/* 47 */            return x0.getWindowAttachCount();
                }

/* 46 */        static /* synthetic */ int access$1700(HorizontalListView x0) {
/* 47 */            return x0.getWindowAttachCount();
                }

/* 46 */        static /* synthetic */ int access$1800(HorizontalListView x0) {
/* 47 */            return x0.getOverflowLeft();
                }

/* 46 */        static /* synthetic */ int access$1900(HorizontalListView x0) {
/* 47 */            return x0.getOverflowRight();
                }

/* 46 */        static /* synthetic */ int access$200(HorizontalListView x0) {
/* 47 */            return x0.mMotionPosition;
                }

/* 46 */        static /* synthetic */ int access$300(HorizontalListView x0) {
/* 47 */            return x0.mDownPosition;
                }

/* 46 */        static /* synthetic */ int access$400(HorizontalListView x0) {
/* 47 */            return x0.mItemCount;
                }

/* 46 */        static /* synthetic */ int access$402(HorizontalListView x0, int x1) {
/* 47 */            x0.mItemCount = x1;
                    return x1;
                }

/* 46 */        static /* synthetic */ ListAdapter access$500(HorizontalListView x0) {
/* 47 */            return x0.mAdapter;
                }

/* 46 */        static /* synthetic */ void access$600(HorizontalListView x0, View x1, boolean x2) {
/* 47 */            x0.removeDetachedView(x1, x2);
                }

/* 46 */        static /* synthetic */ void access$700(HorizontalListView x0, View x1, boolean x2) {
/* 47 */            x0.removeDetachedView(x1, x2);
                }

/* 46 */        static /* synthetic */ int access$800(HorizontalListView x0) {
/* 47 */            return x0.mFirstVisiblePos;
                }

/* 46 */        static /* synthetic */ Drawable access$900(HorizontalListView x0) {
/* 47 */            return x0.mSelector;
                }




/* 192 */       public HorizontalListView(Context context) {
/* 193 */           this(context, null);
                }

/* 196 */       public HorizontalListView(Context context, AttributeSet attrs) {
/* 197 */           this(context, attrs, 0);
                }

/* 200 */       public HorizontalListView(Context context, AttributeSet attrs, int defStyle) {
/* 201 */           super(context, attrs, defStyle);
/* 95 */            this.mTouchMode = -1;
/* 99 */            this.mRecycler = new Recycler();
/* 105 */           this.mIsRecycled = new boolean[1];
/* 110 */           this.mFirstVisiblePos = 0;
/* 126 */           this.mMotionX = Integer.MIN_VALUE;
/* 131 */           this.mMotionY = Integer.MIN_VALUE;
/* 178 */           this.mFingerScrollEnabled = true;
/* 183 */           this.mLastX = Integer.MIN_VALUE;
/* 187 */           this.mFlingRunnable = new FlingRunnable();
/* 189 */           this.mLongPressMillis = ViewConfiguration.getLongPressTimeout();
/* 203 */           TypedArray a = getStylingArray(context, attrs, defStyle);
/* 205 */           initializeDividerStyles(a);
/* 207 */           this.mEdgePadding = getEdgePadding(a);
/* 208 */           this.mFillWidthHeightRatio = getFillWidthHeightRatio(a);
/* 210 */           a.recycle();
/* 212 */           ViewConfiguration configuration = ViewConfiguration.get(context);
/* 213 */           this.mTouchSlop = configuration.getScaledTouchSlop();
/* 214 */           this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
/* 215 */           this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
                }

                @Override // android.widget.AdapterView
/* 227 */       public int getFirstVisiblePosition() {
/* 228 */           return this.mFirstVisiblePos;
                }

                @Override // android.widget.AdapterView
/* 232 */       public int getLastVisiblePosition() {
/* 233 */           return (this.mFirstVisiblePos + getChildCount()) - 1;
                }

                @Override // android.widget.AdapterView
/* 240 */       public ListAdapter getAdapter() {
/* 241 */           return this.mAdapter;
                }

                @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
/* 245 */       protected void onDetachedFromWindow() {
/* 246 */           super.onDetachedFromWindow();
/* 247 */           this.mRecycler.clear();
/* 248 */           if (this.mPendingCheckForTap != null) {
/* 249 */               removeCallbacks(this.mPendingCheckForTap);
                    }
                }

/* 257 */       public void setAdapter(ListAdapter listAdapter) {
/* 258 */           if (listAdapter != this.mAdapter) {
/* 259 */               if (this.mDataSetObserver == null) {
/* 260 */                   this.mDataSetObserver = new AdapterDataSetObserver();
                        }
/* 262 */               if (this.mAdapter != null) {
/* 263 */                   this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
                        }
/* 265 */               this.mAdapter = listAdapter;
/* 266 */               if (listAdapter != null) {
/* 267 */                   listAdapter.registerDataSetObserver(this.mDataSetObserver);
/* 268 */                   this.mItemCount = listAdapter.getCount();
                        } else {
/* 270 */                   this.mItemCount = 0;
                        }
                    }
                }

                @Override // android.widget.AdapterView
/* 280 */       public View getSelectedView() {
/* 281 */           return null;
                }

                @Override // android.widget.AdapterView
/* 288 */       public void setSelection(int position) {
/* 289 */           this.mFirstVisiblePos = position;
/* 290 */           detachAllViewsFromParent();
/* 291 */           layoutChildren();
/* 292 */           invalidate();
                }

                @Override // android.view.View
/* 296 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int childWidth;
                    int childHeight;
/* 297 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 298 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 299 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 300 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 305 */           this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
/* 306 */           if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
/* 308 */               View child = obtainView(0, this.mIsRecycled);
/* 310 */               measureChild(child, heightMeasureSpec);
/* 312 */               childWidth = child.getMeasuredWidth();
/* 313 */               childHeight = child.getMeasuredHeight();
/* 316 */               this.mRecycler.addRecycleView(child, -1);
                    } else {
/* 318 */               childWidth = getSuggestedMinimumWidth();
/* 319 */               childHeight = getSuggestedMinimumHeight();
                    }
/* 322 */           if (widthMode == 0) {
/* 323 */               widthSize = getPaddingLeft() + getPaddingRight() + childWidth;
                    } else if (widthMode == Integer.MIN_VALUE) {
/* 326 */               widthSize = measureWidthOfChildren(heightMeasureSpec, 0, -1, widthSize, -1);
                    }
/* 329 */           if (heightMode == 0) {
/* 330 */               heightSize = getPaddingTop() + getPaddingBottom() + childHeight;
                    }
/* 333 */           this.mChildFillWidth = 0;
                    if (this.mFillWidthHeightRatio > 0.0f && this.mItemCount > 0) {
/* 335 */               int dividersWidth = this.mDividerWidth * (this.mItemCount - 1);
/* 336 */               int childFillWidth = ((widthSize - (this.mEdgePadding * 2)) - dividersWidth) / this.mItemCount;
/* 339 */               if (childFillWidth >= heightSize && childFillWidth <= this.mFillWidthHeightRatio * heightSize) {
/* 341 */                   this.mChildFillWidth = childFillWidth;
                        }
                    }
/* 344 */           if (this.mChildWidthListener != null) {
/* 345 */               this.mChildWidthListener.onChildWidthMeasured(this.mChildFillWidth);
                    }
/* 348 */           setMeasuredDimension(widthSize, heightSize);
/* 349 */           this.mHeightMeasureSpec = heightMeasureSpec;
                }

/* 352 */       private void measureChild(View child, int heightMeasureSpec) {
                    int childWidthSpec;
/* 353 */           LayoutParams p = (LayoutParams) child.getLayoutParams();
/* 354 */           if (p == null) {
/* 355 */               p = (LayoutParams) generateDefaultLayoutParams();
/* 356 */               child.setLayoutParams(p);
                    }
/* 359 */           int childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), p.height);
/* 361 */           int lpWidth = p.width;
/* 363 */           if (lpWidth > 0) {
/* 364 */               childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
                    } else {
/* 366 */               childWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                    }
/* 368 */           child.measure(childWidthSpec, childHeightSpec);
                }

/* 398 */       final int measureWidthOfChildren(int heightMeasureSpec, int startPosition, int endPosition, int maxWidth, int disallowPartialChildPosition) {
/* 399 */           ListAdapter adapter = this.mAdapter;
/* 400 */           if (adapter == null) {
/* 401 */               return getPaddingLeft() + getPaddingRight();
                    }
/* 405 */           int returnedWidth = getPaddingLeft() + getPaddingRight();
/* 408 */           int prevWidthWithoutPartialChild = 0;
/* 412 */           if (endPosition == -1) {
                        endPosition = adapter.getCount() - 1;
                    }
/* 413 */           Recycler recycleBin = this.mRecycler;
/* 414 */           boolean[] isRecycled = this.mIsRecycled;
/* 416 */           int i = startPosition;
                    while (i <= endPosition) {
/* 417 */               View child = obtainView(i, isRecycled);
/* 419 */               measureChild(child, heightMeasureSpec);
/* 422 */               recycleBin.addRecycleView(child, -1);
/* 424 */               returnedWidth += child.getMeasuredWidth();
/* 426 */               if (returnedWidth >= maxWidth) {
                            return (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevWidthWithoutPartialChild <= 0 || returnedWidth == maxWidth) ? maxWidth : prevWidthWithoutPartialChild;
                        }
/* 437 */               if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
/* 438 */                   prevWidthWithoutPartialChild = returnedWidth;
                        }
/* 416 */               i++;
                    }
                    int prevWidthWithoutPartialChild2 = returnedWidth;
/* 444 */           return prevWidthWithoutPartialChild2;
                }

/* 447 */       protected void onLayoutCustom(boolean changed) {
/* 448 */           if (changed) {
/* 449 */               int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 451 */                   getChildAt(i).forceLayout();
                        }
                    }
/* 454 */           layoutChildren();
                }

                @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
/* 458 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 459 */           super.onLayout(changed, left, top, right, bottom);
/* 460 */           onLayoutCustom(changed);
                }

/* 463 */       private void recycleVelocityTracker() {
/* 464 */           this.mVelocityTracker.recycle();
/* 465 */           this.mVelocityTracker = null;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:107:0x0375  */
                /* JADX WARN: Code duplicated, block: B:109:0x0385  */
                /* JADX WARN: Code duplicated, block: B:112:0x0399 A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:113:0x039b  */
                /* JADX WARN: Code duplicated, block: B:116:0x03b1  */
                /* JADX WARN: Code duplicated, block: B:119:0x03c1  */
                /* JADX WARN: Code duplicated, block: B:122:0x03da  */
                /* JADX WARN: Code duplicated, block: B:123:0x03e3  */
                @Override // android.view.View
/* 469 */       public boolean onTouchEvent(MotionEvent event) {
                    int deltaX;
                    int incrementalDeltaX;
                    Drawable d;
/* 470 */           if (this.mItemCount == 0) {
                        return event.getX() >= 0.0f && event.getX() <= ((float) getWidth()) && event.getY() >= 0.0f && event.getY() <= ((float) getHeight());
                    }
/* 475 */           if (this.mVelocityTracker == null) {
/* 476 */               this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 478 */           this.mVelocityTracker.addMovement(event);
/* 480 */           int action = event.getAction() & 255;
/* 481 */           int x = (int) event.getX();
/* 482 */           int y = (int) event.getY();
                    switch (action) {
                        case 0:
/* 486 */                   int motionPosition = pointToPosition(x, y);
/* 487 */                   this.mDownPosition = motionPosition;
/* 488 */                   if (!this.mDataChanged && this.mTouchMode != 4 && motionPosition >= 0 && this.mAdapter.isEnabled(motionPosition)) {
/* 493 */                       this.mTouchMode = 0;
/* 494 */                       if (this.mPendingCheckForTap == null) {
/* 495 */                           this.mPendingCheckForTap = new CheckForTap();
                                }
/* 497 */                       postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                            }
/* 502 */                   disallowParentIntercept(true);
/* 504 */                   this.mFlingRunnable.stop();
/* 506 */                   this.mMotionX = x;
/* 507 */                   this.mMotionY = y;
/* 508 */                   this.mLastX = Integer.MIN_VALUE;
/* 509 */                   this.mMotionPosition = motionPosition;
/* 672 */                   return true;
                        case 1:
                            switch (this.mTouchMode) {
                                case 0:
                                case 1:
                                case 2:
/* 517 */                           int motionPosition2 = this.mMotionPosition;
/* 518 */                           View child = getChildAt(motionPosition2 - this.mFirstVisiblePos);
/* 520 */                           boolean inList = x > getPaddingLeft() && x < getWidth() - getPaddingRight();
/* 523 */                           if (child != null && !child.hasFocusable() && inList) {
/* 524 */                               if (this.mTouchMode != 0) {
/* 525 */                                   child.setPressed(false);
                                        }
/* 528 */                               if (this.mPerformClick == null) {
/* 529 */                                   this.mPerformClick = new PerformClick();
                                        }
/* 532 */                               PerformClick performClick = this.mPerformClick;
/* 533 */                               performClick.clickMotionPosition = motionPosition2;
/* 534 */                               performClick.rememberWindowAttachCount();
/* 536 */                               if (this.mTouchMode == 0 || this.mTouchMode == 1) {
/* 537 */                                   Handler handler = getHandler();
/* 538 */                                   if (handler != null) {
/* 539 */                                       handler.removeCallbacks(this.mPendingCheckForTap);
                                            }
/* 541 */                                   if (!this.mDataChanged && this.mAdapter.isEnabled(motionPosition2)) {
/* 542 */                                       this.mTouchMode = 1;
/* 544 */                                       layoutChildren();
/* 545 */                                       child.setPressed(true);
/* 547 */                                       setPressed(true);
/* 548 */                                       if (this.mSelector != null && (d = this.mSelector.getCurrent()) != null && (d instanceof TransitionDrawable)) {
/* 551 */                                           ((TransitionDrawable) d).resetTransition();
                                                }
/* 554 */                                       if (this.mTouchModeReset != null) {
/* 555 */                                           removeCallbacks(this.mTouchModeReset);
                                                }
/* 557 */                                       this.mTouchModeReset = new RunnableC05431(child, performClick);
/* 567 */                                       postDelayed(this.mTouchModeReset, ViewConfiguration.getPressedStateDuration());
                                            } else {
/* 570 */                                       this.mTouchMode = -1;
                                            }
/* 572 */                                   return true;
                                        }
/* 573 */                               if (!this.mDataChanged && this.mDownPosition == this.mMotionPosition && this.mAdapter.isEnabled(motionPosition2)) {
/* 575 */                                   performClick.run();
                                        }
                                    }
/* 579 */                           this.mTouchMode = -1;
                                    break;
                                default:
/* 584 */                           VelocityTracker velocityTracker = this.mVelocityTracker;
/* 585 */                           velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
/* 586 */                           float velocityX = velocityTracker.getXVelocity();
                                    if (Math.abs(velocityX) > this.mMinimumFlingVelocity && this.mFingerScrollEnabled) {
/* 589 */                               this.mFlingRunnable.start((int) (-velocityX));
                                    }
                                    break;
                            }
/* 594 */                   Handler handler2 = getHandler();
/* 595 */                   if (handler2 != null) {
/* 596 */                       handler2.removeCallbacks(this.mPendingCheckForLongPress);
                            }
/* 598 */                   recycleVelocityTracker();
/* 599 */                   this.mMotionY = Integer.MIN_VALUE;
/* 600 */                   this.mMotionX = Integer.MIN_VALUE;
/* 601 */                   this.mLastX = Integer.MIN_VALUE;
/* 672 */                   return true;
                        case 2:
/* 605 */                   if (this.mMotionX != Integer.MIN_VALUE) {
/* 606 */                       if (getChildCount() == 0) {
/* 607 */                           this.mTouchMode = -1;
/* 608 */                           return super.onTouchEvent(event);
                                }
/* 610 */                       Handler handler3 = getHandler();
/* 611 */                       if (handler3 != null) {
/* 612 */                           handler3.removeCallbacks(this.mPendingCheckForLongPress);
                                }
/* 616 */                       int rawDeltaX = x - this.mMotionX;
/* 617 */                       if (this.mTouchMode != 3) {
/* 623 */                           if (this.mMotionY != Integer.MIN_VALUE) {
/* 624 */                               int rawDeltaY = y - this.mMotionY;
/* 625 */                               if (this.mTouchSlop <= Math.abs(rawDeltaY)) {
/* 626 */                                   disallowParentIntercept(false);
                                        } else if (this.mTouchSlop <= Math.abs(rawDeltaX)) {
/* 633 */                                   this.mTouchMode = 3;
/* 635 */                                   if (x != this.mLastX) {
/* 640 */                                       if (rawDeltaX > 0) {
/* 641 */                                           deltaX = rawDeltaX - this.mTouchSlop;
                                                } else {
/* 643 */                                           deltaX = rawDeltaX + this.mTouchSlop;
                                                }
/* 646 */                                       if (this.mLastX != Integer.MIN_VALUE) {
/* 647 */                                           incrementalDeltaX = x - this.mLastX;
                                                } else {
/* 649 */                                           incrementalDeltaX = deltaX;
                                                }
/* 651 */                                       if (this.mFingerScrollEnabled) {
/* 652 */                                           scroll(rawDeltaX, incrementalDeltaX);
                                                }
/* 654 */                                       this.mLastX = x;
/* 656 */                                       this.mMotionX = x;
/* 657 */                                       this.mMotionY = y;
                                            }
                                        }
                                    } else if (this.mTouchSlop <= Math.abs(rawDeltaX)) {
/* 633 */                               this.mTouchMode = 3;
/* 635 */                               if (x != this.mLastX) {
/* 640 */                                   if (rawDeltaX > 0) {
/* 641 */                                       deltaX = rawDeltaX - this.mTouchSlop;
                                            } else {
/* 643 */                                       deltaX = rawDeltaX + this.mTouchSlop;
                                            }
/* 646 */                                   if (this.mLastX != Integer.MIN_VALUE) {
/* 647 */                                       incrementalDeltaX = x - this.mLastX;
                                            } else {
/* 649 */                                       incrementalDeltaX = deltaX;
                                            }
/* 651 */                                   if (this.mFingerScrollEnabled) {
/* 652 */                                       scroll(rawDeltaX, incrementalDeltaX);
                                            }
/* 654 */                                   this.mLastX = x;
/* 656 */                                   this.mMotionX = x;
/* 657 */                                   this.mMotionY = y;
                                        }
                                    }
                                } else if (x != this.mLastX) {
/* 640 */                           if (rawDeltaX > 0) {
/* 641 */                               deltaX = rawDeltaX - this.mTouchSlop;
                                    } else {
/* 643 */                               deltaX = rawDeltaX + this.mTouchSlop;
                                    }
/* 646 */                           if (this.mLastX != Integer.MIN_VALUE) {
/* 647 */                               incrementalDeltaX = x - this.mLastX;
                                    } else {
/* 649 */                               incrementalDeltaX = deltaX;
                                    }
/* 651 */                           if (this.mFingerScrollEnabled) {
/* 652 */                               scroll(rawDeltaX, incrementalDeltaX);
                                    }
/* 654 */                           this.mLastX = x;
/* 656 */                           this.mMotionX = x;
/* 657 */                           this.mMotionY = y;
                                }
                            } else {
/* 656 */                       this.mMotionX = x;
/* 657 */                       this.mMotionY = y;
                            }
/* 672 */                   return true;
                        case 3:
/* 661 */                   recycleVelocityTracker();
/* 662 */                   this.mMotionX = Integer.MIN_VALUE;
/* 663 */                   this.mMotionY = Integer.MIN_VALUE;
/* 664 */                   this.mLastX = Integer.MIN_VALUE;
/* 665 */                   this.mTouchMode = -1;
/* 672 */                   return true;
                        default:
/* 672 */                   return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.views.HorizontalListView$1 */
/* 555 */       class RunnableC05431 implements Runnable {
                    final /* synthetic */ View val$child;
                    final /* synthetic */ PerformClick val$performClick;

/* 556 */           RunnableC05431(View view, PerformClick performClick) {
                        this.val$child = view;
                        this.val$performClick = performClick;
                    }

                    @Override // java.lang.Runnable
/* 558 */           public void run() {
/* 559 */               HorizontalListView.access$002(HorizontalListView.this, -1);
/* 560 */               this.val$child.setPressed(false);
/* 561 */               HorizontalListView.this.setPressed(false);
/* 562 */               if (!HorizontalListView.access$100(HorizontalListView.this) && HorizontalListView.access$200(HorizontalListView.this) == HorizontalListView.access$300(HorizontalListView.this)) {
/* 563 */                   this.val$performClick.run();
                        }
                    }
                }

/* 675 */       private void disallowParentIntercept(boolean disallow) {
/* 676 */           ViewParent parent = getParent();
/* 677 */           if (parent != null) {
/* 678 */               parent.requestDisallowInterceptTouchEvent(disallow);
                    }
                }

/* 691 */       public boolean scroll(int rawDeltaX, int incrementalDeltaX) {
                    int overflowSpace;
                    boolean shouldScroll;
/* 692 */           int childCount = getChildCount();
/* 695 */           boolean scrollRight = incrementalDeltaX < 0;
/* 696 */           int listLeft = getPaddingLeft();
/* 697 */           int listRight = getWidth() - getPaddingRight();
/* 698 */           int lastChildRight = getChildAt(childCount - 1).getRight();
/* 699 */           int firstChildLeft = getChildAt(0).getLeft();
/* 704 */           if (scrollRight) {
/* 705 */               overflowSpace = getOverflowRight();
                    } else {
/* 707 */               overflowSpace = getOverflowLeft();
                    }
/* 710 */           int firstVisiblePos = this.mFirstVisiblePos;
/* 711 */           if (scrollRight) {
/* 712 */               shouldScroll = firstVisiblePos + childCount < this.mItemCount || lastChildRight + rawDeltaX >= listRight - this.mEdgePadding;
                    } else {
/* 715 */               shouldScroll = firstVisiblePos > 0 || firstChildLeft + rawDeltaX <= this.mEdgePadding + listLeft;
                    }
/* 718 */           if (shouldScroll) {
/* 719 */               int start = 0;
/* 720 */               int count = 0;
/* 721 */               if (scrollRight) {
                            for (int i = 0; i < childCount; i++) {
/* 726 */                       View child = getChildAt(i);
/* 727 */                       if (child.getRight() + rawDeltaX >= listLeft) {
                                    break;
                                }
/* 730 */                       count++;
/* 731 */                       this.mRecycler.addRecycleView(child, firstVisiblePos + i);
                            }
                        } else {
                            for (int i2 = childCount - 1; i2 >= 0; i2--) {
/* 737 */                       View child2 = getChildAt(i2);
/* 738 */                       if (child2.getLeft() + rawDeltaX <= listRight) {
                                    break;
                                }
/* 741 */                       start = i2;
/* 742 */                       count++;
/* 743 */                       this.mRecycler.addRecycleView(child2, firstVisiblePos + i2);
                            }
                        }
/* 746 */               if (count > 0) {
/* 748 */                   detachViewsFromParent(start, count);
/* 752 */                   if (scrollRight) {
/* 753 */                       this.mFirstVisiblePos += count;
                            }
                        }
/* 756 */               int childCount2 = getChildCount();
                        for (int i3 = 0; i3 < childCount2; i3++) {
/* 758 */                   getChildAt(i3).offsetLeftAndRight(rawDeltaX);
                        }
/* 760 */               invalidate();
/* 765 */               if (overflowSpace < Math.abs(incrementalDeltaX)) {
/* 766 */                   fillGap(scrollRight);
                        }
/* 768 */               if (this.mOnScrollListener != null) {
/* 769 */                   this.mOnScrollListener.onScroll();
                        }
                    }
/* 772 */           return shouldScroll;
                }

/* 778 */       private int getOverflowRight() {
/* 779 */           int listRight = (getWidth() - getPaddingRight()) - this.mEdgePadding;
/* 780 */           int lastChildRight = getChildAt(getChildCount() - 1).getRight();
/* 781 */           return lastChildRight - listRight;
                }

/* 787 */       private int getOverflowLeft() {
/* 788 */           int listLeft = getPaddingLeft() + this.mEdgePadding;
/* 789 */           int firstChildLeft = getChildAt(0).getLeft();
/* 790 */           return listLeft - firstChildLeft;
                }

/* 795 */       private void fillGap(boolean right) {
                    int nextRight;
                    int nextLeft;
/* 796 */           int count = getChildCount();
/* 797 */           if (right) {
/* 799 */               if (count > 0) {
/* 800 */                   nextLeft = getChildAt(count - 1).getRight() + this.mDividerWidth;
                        } else {
/* 802 */                   nextLeft = getPaddingLeft();
                        }
/* 804 */               fillRight(this.mFirstVisiblePos + count, nextLeft);
/* 816 */               return;
                    }
/* 808 */           if (count > 0) {
/* 809 */               nextRight = getChildAt(0).getLeft() - this.mDividerWidth;
                    } else {
/* 811 */               nextRight = 0;
                    }
/* 813 */           fillLeft(this.mFirstVisiblePos - 1, nextRight);
                }

/* 818 */       private void fillLeft(int pos, int nextRight) {
/* 819 */           int childrenTop = getPaddingTop();
/* 820 */           int listLeft = getPaddingLeft();
/* 823 */           while (nextRight > listLeft && pos >= 0) {
/* 824 */               View v = attachChild(pos, nextRight, childrenTop, false);
/* 825 */               nextRight = v.getLeft() - this.mDividerWidth;
/* 826 */               pos--;
                    }
/* 828 */           if (this.mItemCount > 0 && pos == -1 && getOverflowLeft() < 0) {
/* 829 */               detachAllViewsFromParent();
/* 830 */               this.mFirstVisiblePos = 0;
/* 831 */               fillRight(0, getPaddingLeft());
/* 835 */               return;
                    }
/* 833 */           this.mFirstVisiblePos = pos + 1;
                }

/* 837 */       private void fillRight(int pos, int nextLeft) {
/* 838 */           int childrenTop = getPaddingTop();
/* 839 */           int listRight = getWidth() - getPaddingRight();
/* 841 */           while (nextLeft < listRight && pos < this.mItemCount) {
/* 842 */               View v = attachChild(pos, nextLeft, childrenTop, true);
/* 843 */               nextLeft = v.getRight() + this.mDividerWidth;
/* 844 */               pos++;
                    }
/* 846 */           int i = nextLeft - this.mDividerWidth;
/* 847 */           if (this.mItemCount <= 0 || pos != this.mItemCount || getOverflowRight() >= 0) {
/* 852 */               return;
                    }
                    if (this.mFirstVisiblePos > 0 || getOverflowLeft() > 0) {
/* 849 */               detachAllViewsFromParent();
/* 850 */               fillLeft(this.mItemCount - 1, listRight - this.mEdgePadding);
                    }
                }

/* 855 */       private View attachChild(int position, int x, int y, boolean right) {
                    View child;
/* 856 */           if (!this.mDataChanged && (child = this.mRecycler.getActiveView(position)) != null) {
/* 859 */               layoutChild(child, x, y, right, true);
                        return child;
                    }
/* 863 */           View child2 = obtainView(position, this.mIsRecycled);
/* 864 */           layoutChild(child2, x, y, right, this.mIsRecycled[0]);
                    return child2;
                }

/* 869 */       private View obtainView(int position, boolean[] isRecycled) {
/* 870 */           View convertView = this.mRecycler.removeRecycleView(position);
/* 871 */           if (convertView != null) {
/* 872 */               View child = this.mAdapter.getView(position, convertView, this);
/* 875 */               if (child != convertView) {
/* 876 */                   this.mRecycler.addRecycleView(convertView, position);
/* 883 */                   return child;
                        }
/* 878 */               isRecycled[0] = true;
/* 883 */               return child;
                    }
/* 881 */           return this.mAdapter.getView(position, null, this);
                }

/* 886 */       private void layoutChild(View child, int x, int y, boolean right, boolean recycled) {
                    int childWidthSpec;
/* 887 */           LayoutParams p = (LayoutParams) child.getLayoutParams();
/* 889 */           if (p == null) {
/* 890 */               p = (LayoutParams) generateDefaultLayoutParams();
/* 891 */               child.setLayoutParams(p);
                    }
/* 893 */           if (recycled) {
/* 894 */               attachViewToParent(child, right ? -1 : 0, p);
                    } else {
/* 896 */               addViewInLayout(child, right ? -1 : 0, p, true);
                    }
/* 899 */           boolean needToMeasure = !recycled || child.isLayoutRequested();
/* 900 */           if (needToMeasure) {
/* 901 */               int childHeightSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, getPaddingTop() + getPaddingBottom(), p.height);
/* 903 */               int lpWidth = this.mChildFillWidth > 0 ? this.mChildFillWidth : p.width;
/* 905 */               if (lpWidth > 0) {
/* 906 */                   childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
                        } else {
/* 908 */                   childWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        }
/* 910 */               child.measure(childWidthSpec, childHeightSpec);
                    } else {
/* 912 */               cleanupLayoutState(child);
                    }
/* 915 */           int w = child.getMeasuredWidth();
/* 916 */           int h = child.getMeasuredHeight();
/* 917 */           int childLeft = right ? x : x - w;
/* 919 */           if (needToMeasure) {
/* 920 */               int childRight = childLeft + w;
/* 921 */               int childBottom = y + h;
/* 922 */               child.layout(childLeft, y, childRight, childBottom);
                    } else {
/* 924 */               child.offsetLeftAndRight(childLeft - child.getLeft());
/* 925 */               child.offsetTopAndBottom(y - child.getTop());
                    }
                }

/* 929 */       protected void layoutChildren() {
/* 930 */           boolean dataChanged = this.mDataChanged;
/* 931 */           int firstPos = this.mFirstVisiblePos;
/* 932 */           Recycler recyler = this.mRecycler;
/* 933 */           int childCount = getChildCount();
/* 934 */           View prevFirst = getChildAt(0);
/* 936 */           if (dataChanged) {
                        for (int i = 0; i < childCount; i++) {
/* 939 */                   recyler.addRecycleView(getChildAt(i), firstPos + i);
                        }
                    } else {
/* 942 */               recyler.fillActiveViews(firstPos, childCount);
                    }
/* 945 */           detachAllViewsFromParent();
/* 947 */           if (prevFirst == null) {
/* 948 */               fillRight(firstPos, getPaddingLeft() + this.mEdgePadding);
                    } else {
/* 950 */               fillRight(firstPos, prevFirst.getLeft());
                    }
/* 952 */           recyler.recycleActiveViews();
/* 953 */           this.mDataChanged = false;
                }

                @Override // android.view.ViewGroup
/* 957 */       protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 958 */           return new LayoutParams(-2, -1);
                }

                @Override // android.view.ViewGroup
/* 962 */       protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
/* 963 */           return p instanceof LayoutParams;
                }

                @Override // android.view.ViewGroup
/* 967 */       public LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 968 */           return new LayoutParams(getContext(), attrs);
                }

                @Override // android.view.ViewGroup
/* 972 */       protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 973 */           return new LayoutParams(p);
                }

/* 984 */       public int pointToPosition(int x, int y) {
/* 985 */           Rect frame = this.mTouchFrame;
/* 986 */           if (frame == null) {
/* 987 */               this.mTouchFrame = new Rect();
/* 988 */               frame = this.mTouchFrame;
                    }
/* 991 */           int count = getChildCount();
                    for (int i = count - 1; i >= 0; i--) {
/* 993 */               View child = getChildAt(i);
/* 994 */               child.getHitRect(frame);
/* 995 */               if (frame.contains(x, y)) {
/* 996 */                   return this.mFirstVisiblePos + i;
                        }
                    }
/* 999 */           return -1;
                }

                @Override // android.view.View
/* 1003 */      public void setPressed(boolean pressed) {
/* 1004 */          if (getParent() == null) {
/* 1005 */              super.setPressed(pressed);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1011 */      protected void dispatchDraw(Canvas canvas) {
                    int effectivePaddingLeft;
                    int effectivePaddingRight;
/* 1012 */          int dividerWidth = this.mDividerWidth;
/* 1013 */          boolean drawDividers = dividerWidth > 0 && this.mDivider != null;
/* 1015 */          if (drawDividers) {
/* 1017 */              Rect bounds = new Rect();
/* 1018 */              bounds.left = getPaddingLeft();
/* 1019 */              bounds.right = getWidth() - getPaddingRight();
/* 1021 */              int count = getChildCount();
/* 1022 */              int first = this.mFirstVisiblePos;
/* 1023 */              ListAdapter adapter = this.mAdapter;
/* 1024 */              int scrollX = getScrollX();
/* 1029 */              boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
/* 1031 */              if (fillForMissingDividers && this.mDividerPaint == null) {
/* 1032 */                  this.mDividerPaint = new Paint();
                        }
/* 1034 */              Paint paint = this.mDividerPaint;
/* 1037 */              if (this.mClipToPadding) {
/* 1038 */                  effectivePaddingLeft = getPaddingLeft();
/* 1039 */                  effectivePaddingRight = getPaddingRight();
                        } else {
/* 1041 */                  effectivePaddingLeft = 0;
/* 1042 */                  effectivePaddingRight = 0;
                        }
/* 1045 */              int listRight = (getWidth() + scrollX) - effectivePaddingRight;
                        for (int i = 0; i < count; i++) {
/* 1049 */                  View child = getChildAt(i);
/* 1050 */                  int left = child.getLeft();
/* 1052 */                  if (left > effectivePaddingLeft) {
/* 1053 */                      if (adapter.isEnabled(first + i) && (i == count - 1 || adapter.isEnabled(first + i + 1))) {
/* 1055 */                          bounds.left = left - dividerWidth;
/* 1056 */                          bounds.right = left;
/* 1061 */                          drawDivider(canvas, bounds);
                                } else if (fillForMissingDividers) {
/* 1063 */                          bounds.left = left - dividerWidth;
/* 1064 */                          bounds.right = left;
/* 1065 */                          canvas.drawRect(bounds, paint);
                                }
                            }
                        }
/* 1070 */              if (count > 0 && scrollX > 0 && drawDividers) {
/* 1071 */                  bounds.left = listRight;
/* 1072 */                  bounds.right = listRight + dividerWidth;
/* 1073 */                  drawDivider(canvas, bounds);
                        }
                    }
/* 1078 */          super.dispatchDraw(canvas);
                }

/* 1088 */      void drawDivider(Canvas canvas, Rect bounds) {
/* 1089 */          Drawable divider = this.mDivider;
/* 1090 */          divider.setBounds(bounds);
/* 1091 */          divider.draw(canvas);
                }

/* 1094 */      public void setDivider(Drawable divider) {
/* 1095 */          if (divider != null) {
/* 1096 */              this.mDividerWidth = divider.getIntrinsicWidth();
                    } else {
/* 1098 */              this.mDividerWidth = 0;
                    }
/* 1100 */          this.mDivider = divider;
/* 1101 */          requestLayout();
/* 1102 */          invalidate();
                }

/* 1105 */      public void setDividerWidth(int dividerWidth) {
/* 1106 */          this.mDividerWidth = dividerWidth;
/* 1107 */          requestLayout();
/* 1108 */          invalidate();
                }

                @Override // android.view.ViewGroup
/* 1112 */      public void setClipToPadding(boolean clipToPadding) {
/* 1113 */          super.setClipToPadding(clipToPadding);
/* 1114 */          this.mClipToPadding = clipToPadding;
                }

/* 1117 */      public void setOnScrollListener(DragSortWidget onScrollListener) {
/* 1118 */          this.mOnScrollListener = onScrollListener;
                }

/* 1124 */      public static class LayoutParams extends ViewGroup.LayoutParams {
                    public boolean recycled;
                    public int recycledPosition;

/* 1125 */          public LayoutParams(Context c, AttributeSet attrs) {
/* 1126 */              super(c, attrs);
                    }

/* 1129 */          public LayoutParams(ViewGroup.LayoutParams source) {
/* 1130 */              super(source);
                    }

/* 1133 */          public LayoutParams(int width, int height) {
/* 1134 */              super(width, height);
                    }
                }

/* 1137 */      private class AdapterDataSetObserver extends DataSetObserver {
/* 1138 */          public AdapterDataSetObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 1143 */          public void onChanged() {
/* 1144 */              HorizontalListView.access$402(HorizontalListView.this, HorizontalListView.access$500(HorizontalListView.this).getCount());
/* 1145 */              HorizontalListView.access$102(HorizontalListView.this, true);
/* 1146 */              HorizontalListView.this.requestLayout();
                    }

                    @Override // android.database.DataSetObserver
/* 1150 */          public void onInvalidated() {
/* 1151 */              HorizontalListView.access$402(HorizontalListView.this, 0);
/* 1152 */              HorizontalListView.access$102(HorizontalListView.this, true);
/* 1153 */              HorizontalListView.this.requestLayout();
                    }
                }

/* 1165 */      private class Recycler {
                    private View[] mActiveViews;
                    private int mFirstActivePos;
                    private final ArrayList<View> mRecycleViews = new ArrayList<>();

/* 1166 */          public Recycler() {
                    }

/* 1170 */          public View getActiveView(int position) {
/* 1171 */              if (this.mActiveViews != null) {
/* 1172 */                  int index = position - this.mFirstActivePos;
/* 1173 */                  View[] activeViews = this.mActiveViews;
/* 1174 */                  if (index >= 0 && index < activeViews.length) {
/* 1175 */                      View v = activeViews[index];
/* 1176 */                      activeViews[index] = null;
/* 1180 */                      return v;
                            }
                        }
                        return null;
                    }

/* 1183 */          public void recycleActiveViews() {
/* 1184 */              View[] activeViews = this.mActiveViews;
/* 1185 */              if (activeViews != null) {
/* 1188 */                  ArrayList<View> recycledViews = this.mRecycleViews;
/* 1189 */                  int count = activeViews.length;
                            for (int i = count - 1; i >= 0; i--) {
/* 1192 */                      View victim = activeViews[i];
/* 1193 */                      if (victim != null) {
/* 1194 */                          LayoutParams lp = (LayoutParams) victim.getLayoutParams();
/* 1195 */                          activeViews[i] = null;
/* 1197 */                          lp.recycledPosition = this.mFirstActivePos + i;
/* 1198 */                          recycledViews.add(victim);
                                }
                            }
/* 1202 */                  pruneRecycleViews();
                        }
                    }

/* 1205 */          private void pruneRecycleViews() {
/* 1206 */              int maxViews = this.mActiveViews.length;
/* 1207 */              ArrayList<View> recycledViews = this.mRecycleViews;
/* 1208 */              int size = recycledViews.size();
/* 1209 */              int sizeDiff = size - maxViews;
/* 1210 */              int size2 = size - 1;
                        for (int i = 0; i < sizeDiff; i++) {
/* 1212 */                  HorizontalListView.access$600(HorizontalListView.this, recycledViews.remove(size2), false);
/* 1213 */                  size2--;
                        }
                    }

/* 1217 */          public void fillActiveViews(int position, int childCount) {
/* 1218 */              View[] activeViews = this.mActiveViews;
/* 1219 */              if (activeViews == null || activeViews.length < childCount) {
/* 1220 */                  this.mActiveViews = new View[childCount];
/* 1221 */                  activeViews = this.mActiveViews;
                        }
/* 1223 */              this.mFirstActivePos = position;
                        for (int i = 0; i < childCount; i++) {
/* 1225 */                  activeViews[i] = HorizontalListView.this.getChildAt(i);
                        }
                    }

/* 1229 */          public View removeRecycleView(int position) {
/* 1230 */              ArrayList<View> recycledViews = this.mRecycleViews;
/* 1231 */              if (recycledViews.isEmpty()) {
/* 1232 */                  return null;
                        }
/* 1234 */              int size = recycledViews.size();
                        for (int i = 0; i < size; i++) {
/* 1237 */                  View view = recycledViews.get(i);
/* 1238 */                  if (((LayoutParams) view.getLayoutParams()).recycledPosition == position) {
/* 1239 */                      recycledViews.remove(i);
/* 1243 */                      return view;
                            }
                        }
/* 1243 */              return recycledViews.remove(size - 1);
                    }

/* 1247 */          public void addRecycleView(View view, int position) {
/* 1248 */              LayoutParams lp = (LayoutParams) view.getLayoutParams();
/* 1249 */              if (lp != null) {
/* 1253 */                  lp.recycledPosition = position;
/* 1254 */                  lp.recycled = true;
/* 1255 */                  this.mRecycleViews.add(view);
                        }
                    }

/* 1258 */          public void clear() {
/* 1259 */              ArrayList<View> recycledViews = this.mRecycleViews;
/* 1260 */              int size = recycledViews.size();
                        for (int i = size - 1; i >= 0; i--) {
/* 1262 */                  HorizontalListView.access$700(HorizontalListView.this, recycledViews.remove(i), false);
                        }
                    }
                }

/* 1265 */      final class CheckForTap implements Runnable {
/* 1266 */          CheckForTap() {
                    }

                    @Override // java.lang.Runnable
/* 1268 */          public void run() {
                        Drawable d;
                        Drawable d2;
/* 1269 */              if (HorizontalListView.access$000(HorizontalListView.this) == 0) {
/* 1270 */                  HorizontalListView.access$002(HorizontalListView.this, 1);
/* 1271 */                  View child = HorizontalListView.this.getChildAt(HorizontalListView.access$200(HorizontalListView.this) - HorizontalListView.access$800(HorizontalListView.this));
/* 1272 */                  if (child != null && !child.hasFocusable()) {
/* 1273 */                      if (!HorizontalListView.access$100(HorizontalListView.this)) {
/* 1274 */                          child.setPressed(true);
/* 1275 */                          HorizontalListView.this.setPressed(true);
/* 1276 */                          HorizontalListView.this.layoutChildren();
/* 1278 */                          HorizontalListView.this.refreshDrawableState();
/* 1280 */                          boolean longClickable = HorizontalListView.this.isLongClickable();
/* 1282 */                          if (HorizontalListView.access$900(HorizontalListView.this) != null && (d2 = HorizontalListView.access$900(HorizontalListView.this).getCurrent()) != null && (d2 instanceof TransitionDrawable)) {
/* 1285 */                              if (longClickable) {
/* 1286 */                                  ((TransitionDrawable) d2).startTransition(HorizontalListView.access$1000(HorizontalListView.this));
                                        } else {
/* 1288 */                                  ((TransitionDrawable) d2).resetTransition();
                                        }
                                    }
/* 1293 */                          if (longClickable) {
/* 1294 */                              if (HorizontalListView.access$1100(HorizontalListView.this) == null) {
/* 1295 */                                  HorizontalListView.access$1102(HorizontalListView.this, new CheckForLongPress(HorizontalListView.this, null));
                                        }
/* 1297 */                              HorizontalListView.access$1100(HorizontalListView.this).rememberWindowAttachCount();
/* 1298 */                              HorizontalListView.this.postDelayed(HorizontalListView.access$1100(HorizontalListView.this), HorizontalListView.access$1000(HorizontalListView.this));
                                    } else {
/* 1300 */                              HorizontalListView.access$002(HorizontalListView.this, 2);
                                    }
/* 1303 */                          if (HorizontalListView.access$900(HorizontalListView.this) != null && (d = HorizontalListView.access$900(HorizontalListView.this).getCurrent()) != null && (d instanceof TransitionDrawable)) {
/* 1306 */                              ((TransitionDrawable) d).resetTransition();
                                    }
                                }
/* 1310 */                      HorizontalListView.access$002(HorizontalListView.this, 2);
                            }
                        }
                    }
                }

/* 1314 */      private class CheckForLongPress implements Runnable {
                    private int mOriginalAttachCount;

/* 1315 */          private CheckForLongPress() {
                    }

/* 1315 */          /* synthetic */ CheckForLongPress(HorizontalListView x0, RunnableC05431 x1) {
/* 1316 */              this();
                    }

/* 1319 */          public void rememberWindowAttachCount() {
/* 1320 */              this.mOriginalAttachCount = HorizontalListView.access$1300(HorizontalListView.this);
                    }

/* 1323 */          public boolean sameWindow() {
                        return HorizontalListView.this.hasWindowFocus() && HorizontalListView.access$1400(HorizontalListView.this) == this.mOriginalAttachCount;
                    }

                    @Override // java.lang.Runnable
/* 1327 */          public void run() {
/* 1328 */              int motionPosition = HorizontalListView.access$200(HorizontalListView.this);
/* 1329 */              View child = HorizontalListView.this.getChildAt(motionPosition - HorizontalListView.access$800(HorizontalListView.this));
/* 1330 */              if (child != null) {
/* 1331 */                  int longPressPosition = HorizontalListView.access$200(HorizontalListView.this);
/* 1332 */                  long longPressId = HorizontalListView.access$500(HorizontalListView.this).getItemId(HorizontalListView.access$200(HorizontalListView.this));
/* 1334 */                  boolean handled = false;
/* 1335 */                  if (sameWindow() && !HorizontalListView.access$100(HorizontalListView.this)) {
/* 1336 */                      handled = HorizontalListView.access$1500(HorizontalListView.this, child, longPressPosition, longPressId);
                            }
/* 1338 */                  if (handled) {
/* 1339 */                      HorizontalListView.access$002(HorizontalListView.this, -1);
/* 1340 */                      HorizontalListView.this.setPressed(false);
/* 1341 */                      child.setPressed(false);
/* 1347 */                      return;
                            }
/* 1343 */                  HorizontalListView.access$002(HorizontalListView.this, 2);
                        }
                    }
                }

/* 1351 */      private boolean performLongPress(View child, int longPressPosition, long longPressId) {
/* 1352 */          boolean handled = false;
/* 1354 */          if (this.mOnItemLongClickListener != null) {
/* 1355 */              handled = this.mOnItemLongClickListener.onItemLongClick(this, child, longPressPosition, longPressId);
                    }
/* 1358 */          if (handled) {
/* 1359 */              performHapticFeedback(0);
                    }
/* 1361 */          return handled;
                }

/* 1367 */      private class PerformClick implements Runnable {
                    public int clickMotionPosition;
                    private int mOriginalAttachCount;

/* 1368 */          public PerformClick() {
                    }

/* 1372 */          public void rememberWindowAttachCount() {
/* 1373 */              this.mOriginalAttachCount = HorizontalListView.access$1600(HorizontalListView.this);
                    }

/* 1376 */          public boolean sameWindow() {
                        return HorizontalListView.this.hasWindowFocus() && HorizontalListView.access$1700(HorizontalListView.this) == this.mOriginalAttachCount;
                    }

                    @Override // java.lang.Runnable
/* 1382 */          public void run() {
                        View view;
/* 1383 */              if (!HorizontalListView.access$100(HorizontalListView.this)) {
/* 1387 */                  ListAdapter adapter = HorizontalListView.access$500(HorizontalListView.this);
/* 1388 */                  int motionPosition = this.clickMotionPosition;
/* 1389 */                  if (adapter != null && HorizontalListView.access$400(HorizontalListView.this) > 0 && motionPosition != -1 && motionPosition < adapter.getCount() && sameWindow() && (view = HorizontalListView.this.getChildAt(motionPosition - HorizontalListView.access$800(HorizontalListView.this))) != null) {
/* 1396 */                      HorizontalListView.this.performItemClick(view, motionPosition, adapter.getItemId(motionPosition));
                            }
                        }
                    }
                }

/* 1418 */      private class FlingRunnable implements Runnable {
                    private int mLastFlingX;
                    private int mScreenWidth;
                    private final Scroller mScroller;

/* 1419 */          public FlingRunnable() {
/* 1421 */              this.mScroller = new Scroller(HorizontalListView.this.getContext());
                    }

/* 1424 */          public void start(int initialVelocity) {
/* 1425 */              if (initialVelocity != 0) {
/* 1426 */                  HorizontalListView.access$002(HorizontalListView.this, 4);
/* 1427 */                  this.mScreenWidth = ((HorizontalListView.this.getWidth() - HorizontalListView.this.getPaddingLeft()) - HorizontalListView.this.getPaddingRight()) - 1;
/* 1430 */                  HorizontalListView.this.removeCallbacks(this);
/* 1432 */                  int initialX = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
/* 1433 */                  this.mLastFlingX = initialX;
/* 1434 */                  this.mScroller.fling(initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
/* 1436 */                  HorizontalListView.this.post(this);
                        }
                    }

/* 1439 */          public void stop() {
/* 1440 */              if (HorizontalListView.access$000(HorizontalListView.this) == 4) {
/* 1441 */                  HorizontalListView.access$002(HorizontalListView.this, -1);
/* 1443 */                  HorizontalListView.this.removeCallbacks(this);
/* 1444 */                  if (!this.mScroller.isFinished()) {
/* 1445 */                      this.mScroller.abortAnimation();
/* 1446 */                      HorizontalListView.this.invalidate();
                            }
                        }
                    }

                    @Override // java.lang.Runnable
/* 1451 */          public void run() {
                        int delta;
                        int delta2;
/* 1452 */              int childCount = HorizontalListView.this.getChildCount();
/* 1453 */              if (HorizontalListView.access$400(HorizontalListView.this) == 0 || childCount == 0) {
/* 1454 */                  stop();
/* 1493 */                  return;
                        }
/* 1458 */              Scroller scroller = this.mScroller;
/* 1459 */              boolean more = scroller.computeScrollOffset();
/* 1460 */              int x = scroller.getCurrX();
/* 1464 */              int delta3 = this.mLastFlingX - x;
/* 1467 */              if (delta3 > 0) {
/* 1468 */                  delta = Math.min(this.mScreenWidth, delta3);
                        } else {
/* 1470 */                  delta = Math.max(-this.mScreenWidth, delta3);
                        }
/* 1473 */              boolean scrolledWithinBounds = HorizontalListView.this.scroll(delta, delta);
/* 1475 */              if (more) {
/* 1476 */                  if (scrolledWithinBounds) {
/* 1477 */                      this.mLastFlingX = x;
/* 1478 */                      HorizontalListView.this.post(this);
/* 1493 */                      return;
                            }
/* 1482 */                  if (delta > 0) {
/* 1483 */                      delta2 = HorizontalListView.access$1800(HorizontalListView.this);
                            } else {
/* 1485 */                      delta2 = -HorizontalListView.access$1900(HorizontalListView.this);
                            }
/* 1487 */                  HorizontalListView.this.scroll(delta2, delta2);
/* 1488 */                  stop();
/* 1493 */                  return;
                        }
/* 1491 */              stop();
                    }
                }

/* 1496 */      public void setChildWidthListener(ChildWidthListener widthListener) {
/* 1497 */          this.mChildWidthListener = widthListener;
                }

                @Override // android.widget.AdapterView
/* 1505 */      public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
/* 1506 */          this.mOnItemLongClickListener = listener;
                }

/* 1509 */      public int getDividerWidth() {
/* 1510 */          return this.mDividerWidth;
                }

/* 1513 */      public void setLongPressDuration(int longPresMillis) {
/* 1514 */          this.mLongPressMillis = longPresMillis;
                }

/* 1522 */      public void setFingerScrollEnabled(boolean shouldScroll) {
/* 1523 */          this.mFingerScrollEnabled = shouldScroll;
                }
            }
