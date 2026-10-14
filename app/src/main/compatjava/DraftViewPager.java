            package android.support.v4.view;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.support.annotation.NonNull;
            import android.support.v4.app.DraftFragmentStatePagerAdapter;
            import android.support.v4.os.ParcelableCompat;
            import android.support.v4.os.ParcelableCompatCreatorCallbacks;
            import android.support.v4.widget.EdgeEffectCompat;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.FocusFinder;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.SoundEffectConstants;
            import android.view.VelocityTracker;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.animation.Interpolator;
            import android.widget.Scroller;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public class DraftViewPager extends ViewGroup {
                private static final boolean DEBUG = false;
                private static final int DEFAULT_OFFSCREEN_PAGES = 1;
                public static final int DRAFT_MARGIN = 6;
                private static final int INVALID_POINTER = -1;
                private static final int MAX_SETTLE_DURATION = 600;
                private static final int MIN_DISTANCE_FOR_FLING = 25;
                public static final int PREVIEW_RATIO = 85;
                public static final int SCROLL_STATE_DRAGGING = 1;
                public static final int SCROLL_STATE_IDLE = 0;
                public static final int SCROLL_STATE_SETTLING = 2;
                private static final String TAG = "ViewPager";
                private static final boolean USE_CACHE = false;
                private int mActivePointerId;
                private DraftPagerAdapter mAdapter;
                private OnAdapterChangeListener mAdapterChangeListener;
                private int mBottomPageBounds;
                private boolean mCalledSuper;
                private int mChildHeightMeasureSpec;
                private int mChildWidthMeasureSpec;
                private int mCurItem;
                private int mDecorChildCount;
                private int mDraftMargin;
                private long mFakeDragBeginTime;
                private boolean mFakeDragging;
                private boolean mFirstLayout;
                private int mFlingDistance;
                private boolean mInLayout;
                private float mInitialMotionX;
                private OnPageChangeListener mInternalPageChangeListener;
                private boolean mIsBeingDragged;
                private boolean mIsUnableToDrag;
                private final ArrayList<ItemInfo> mItems;
                private float mLastMotionX;
                private float mLastMotionY;
                private EdgeEffectCompat mLeftEdge;
                private Drawable mMarginDrawable;
                private int mMaximumVelocity;
                private int mMinimumVelocity;
                private PagerObserver mObserver;
                private int mOffscreenPageLimit;
                private OnPageChangeListener mOnPageChangeListener;
                private int mPageMargin;
                private boolean mPopulatePending;
                private float mPreviewRatio;
                private Parcelable mRestoredAdapterState;
                private ClassLoader mRestoredClassLoader;
                private int mRestoredCurItem;
                private EdgeEffectCompat mRightEdge;
                private int mScrollState;
                private Scroller mScroller;
                private boolean mScrolling;
                private boolean mScrollingCacheEnabled;
                private int mTopPageBounds;
                private int mTouchSlop;
                private VelocityTracker mVelocityTracker;
                private static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};
                private static final Comparator<ItemInfo> COMPARATOR = new C00431();
                private static final Interpolator sInterpolator = new InterpolatorC00442();

                interface Decor {
                }

                interface OnAdapterChangeListener {
                    void onAdapterChanged(DraftPagerAdapter draftPagerAdapter, DraftPagerAdapter draftPagerAdapter2);
                }

                public interface OnPageChangeListener {
                    void onPageScrollStateChanged(int i);

                    void onPageScrolled(int i, float f, int i2);

                    void onPageSelected(int i);
                }

/* 84 */        static /* synthetic */ int[] access$100() {
/* 85 */            return LAYOUT_ATTRS;
                }

/* 97 */        static class ItemInfo {
                    Object object;
                    int position;
                    boolean scrolling;

/* 98 */            ItemInfo() {
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.DraftViewPager$1 */
/* 103 */       static class C00431 implements Comparator<ItemInfo> {
/* 104 */           C00431() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 107 */           public int compare(ItemInfo lhs, ItemInfo rhs) {
/* 108 */               return lhs.position - rhs.position;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.DraftViewPager$2 */
/* 110 */       static class InterpolatorC00442 implements Interpolator {
/* 111 */           InterpolatorC00442() {
                    }

                    @Override // android.animation.TimeInterpolator
/* 113 */           public float getInterpolation(float t) {
/* 114 */               float t2 = t - 1.0f;
/* 115 */               return (t2 * t2 * t2 * t2 * t2) + 1.0f;
                    }
                }

/* 257 */       public DraftViewPager(Context c) {
/* 258 */           this(c, null);
                }

/* 261 */       public DraftViewPager(Context c, AttributeSet attrs) {
/* 262 */           super(c, attrs);
/* 119 */           this.mItems = new ArrayList<>();
/* 123 */           this.mRestoredCurItem = -1;
/* 124 */           this.mRestoredAdapterState = null;
/* 125 */           this.mRestoredClassLoader = null;
/* 142 */           this.mOffscreenPageLimit = 1;
/* 157 */           this.mActivePointerId = -1;
/* 178 */           this.mFirstLayout = true;
/* 204 */           this.mScrollState = 0;
/* 263 */           this.mPreviewRatio = 0.85f;
/* 264 */           this.mDraftMargin = 6;
/* 265 */           initViewPager();
                }

/* 268 */       void initViewPager() {
/* 269 */           setWillNotDraw(false);
/* 270 */           setDescendantFocusability(262144);
/* 271 */           setFocusable(true);
/* 272 */           Context context = getContext();
/* 273 */           this.mScroller = new Scroller(context, sInterpolator);
/* 274 */           ViewConfiguration configuration = ViewConfiguration.get(context);
/* 275 */           this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
/* 276 */           this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
/* 277 */           this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
/* 278 */           this.mLeftEdge = new EdgeEffectCompat(context);
/* 279 */           this.mRightEdge = new EdgeEffectCompat(context);
/* 281 */           float density = context.getResources().getDisplayMetrics().density;
/* 282 */           this.mFlingDistance = (int) (25.0f * density);
                }

/* 285 */       private void setScrollState(int newState) {
/* 286 */           if (this.mScrollState != newState) {
/* 290 */               this.mScrollState = newState;
/* 291 */               if (this.mOnPageChangeListener != null) {
/* 292 */                   this.mOnPageChangeListener.onPageScrollStateChanged(newState);
                        }
                    }
                }

/* 301 */       public void setAdapter(DraftFragmentStatePagerAdapter adapter) {
/* 302 */           if (this.mAdapter != null) {
/* 303 */               this.mAdapter.unregisterDataSetObserver(this.mObserver);
/* 304 */               this.mAdapter.startUpdate((ViewGroup) this);
                        for (int i = 0; i < this.mItems.size(); i++) {
/* 306 */                   ItemInfo ii = this.mItems.get(i);
/* 307 */                   this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
                        }
/* 309 */               this.mAdapter.finishUpdate((ViewGroup) this);
/* 310 */               this.mItems.clear();
/* 311 */               removeNonDecorViews();
/* 312 */               this.mCurItem = 0;
/* 313 */               scrollTo(0, 0);
                    }
/* 316 */           DraftPagerAdapter oldAdapter = this.mAdapter;
/* 317 */           this.mAdapter = adapter;
/* 319 */           if (this.mAdapter != null) {
/* 320 */               if (this.mObserver == null) {
/* 321 */                   this.mObserver = new PagerObserver();
                        }
/* 323 */               this.mAdapter.registerDataSetObserver(this.mObserver);
/* 324 */               this.mPopulatePending = false;
/* 325 */               if (this.mRestoredCurItem >= 0) {
/* 326 */                   this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
/* 327 */                   setCurrentItemInternal(this.mRestoredCurItem, false, true);
/* 328 */                   this.mRestoredCurItem = -1;
/* 329 */                   this.mRestoredAdapterState = null;
/* 330 */                   this.mRestoredClassLoader = null;
                        } else {
/* 332 */                   populate();
                        }
                    }
/* 336 */           if (this.mAdapterChangeListener != null && oldAdapter != adapter) {
/* 337 */               this.mAdapterChangeListener.onAdapterChanged(oldAdapter, adapter);
                    }
                }

/* 341 */       private void removeNonDecorViews() {
/* 342 */           int i = 0;
                    while (i < getChildCount()) {
/* 343 */               View child = getChildAt(i);
/* 344 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 345 */               if (!lp.isDecor) {
/* 346 */                   removeViewAt(i);
/* 347 */                   i--;
                        }
/* 342 */               i++;
                    }
                }

/* 357 */       public DraftPagerAdapter getAdapter() {
/* 358 */           return this.mAdapter;
                }

/* 361 */       void setOnAdapterChangeListener(OnAdapterChangeListener listener) {
/* 362 */           this.mAdapterChangeListener = listener;
                }

/* 372 */       public void setCurrentItem(int item) {
/* 373 */           this.mPopulatePending = false;
/* 374 */           setCurrentItemInternal(item, !this.mFirstLayout, false);
                }

/* 383 */       public void setCurrentItem(int item, boolean smoothScroll) {
/* 384 */           this.mPopulatePending = false;
/* 385 */           setCurrentItemInternal(item, smoothScroll, false);
                }

/* 388 */       public int getCurrentItem() {
/* 389 */           return this.mCurItem;
                }

/* 392 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
/* 393 */           setCurrentItemInternal(item, smoothScroll, always, 0);
                }

/* 396 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
/* 397 */           if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
/* 398 */               setScrollingCacheEnabled(false);
/* 443 */               return;
                    }
/* 401 */           if (!always && this.mCurItem == item && this.mItems.size() != 0) {
/* 402 */               setScrollingCacheEnabled(false);
/* 443 */               return;
                    }
/* 405 */           if (item < 0) {
/* 406 */               item = 0;
                    } else if (item >= this.mAdapter.getCount()) {
/* 408 */               item = this.mAdapter.getCount() - 1;
                    }
/* 410 */           int pageLimit = this.mOffscreenPageLimit;
/* 411 */           if (item > this.mCurItem + pageLimit || item < this.mCurItem - pageLimit) {
                        for (int i = 0; i < this.mItems.size(); i++) {
/* 416 */                   this.mItems.get(i).scrolling = true;
                        }
                    }
/* 419 */           boolean dispatchSelected = this.mCurItem != item;
/* 420 */           this.mCurItem = item;
/* 421 */           populate();
/* 423 */           int destX = getAdjustedXDestination(item);
/* 425 */           if (smoothScroll) {
/* 426 */               smoothScrollTo(item, destX, 0, velocity);
/* 427 */               if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 428 */                   this.mOnPageChangeListener.onPageSelected(item);
                        }
/* 430 */               if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 431 */                   this.mInternalPageChangeListener.onPageSelected(item);
/* 443 */                   return;
                        }
/* 443 */               return;
                    }
/* 434 */           if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 435 */               this.mOnPageChangeListener.onPageSelected(item);
                    }
/* 437 */           if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 438 */               this.mInternalPageChangeListener.onPageSelected(item);
                    }
/* 440 */           completeScroll();
/* 441 */           scrollTo(destX, 0);
                }

/* 451 */       private int getAdjustedXDestination(int item) {
/* 452 */           int base = ((int) (getWidth() * this.mPreviewRatio)) + (this.mDraftMargin * 2);
/* 453 */           return base * item;
                }

/* 463 */       public boolean isDrawn() {
                    return getWidth() > 0;
                }

/* 473 */       public void setOnPageChangeListener(OnPageChangeListener listener) {
/* 474 */           this.mOnPageChangeListener = listener;
                }

/* 483 */       OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener listener) {
/* 484 */           OnPageChangeListener oldListener = this.mInternalPageChangeListener;
/* 485 */           this.mInternalPageChangeListener = listener;
/* 486 */           return oldListener;
                }

/* 496 */       public int getOffscreenPageLimit() {
/* 497 */           return this.mOffscreenPageLimit;
                }

/* 517 */       public void setOffscreenPageLimit(int limit) {
/* 518 */           if (limit < 1) {
/* 519 */               Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to 1");
/* 521 */               limit = 1;
                    }
/* 523 */           if (limit != this.mOffscreenPageLimit) {
/* 524 */               this.mOffscreenPageLimit = limit;
/* 525 */               populate();
                    }
                }

/* 537 */       public void setPageMargin(int marginPixels) {
/* 538 */           int oldMargin = this.mPageMargin;
/* 539 */           this.mPageMargin = marginPixels;
/* 541 */           int width = getWidth();
/* 542 */           recomputeScrollPosition(width, width, marginPixels, oldMargin);
/* 544 */           requestLayout();
                }

/* 552 */       public int getPageMargin() {
/* 553 */           return this.mPageMargin;
                }

/* 561 */       public void setPageMarginDrawable(Drawable d) {
/* 562 */           this.mMarginDrawable = d;
/* 563 */           if (d != null) {
                        refreshDrawableState();
                    }
/* 564 */           setWillNotDraw(d == null);
/* 565 */           invalidate();
                }

/* 573 */       public void setPageMarginDrawable(int resId) {
/* 574 */           setPageMarginDrawable(getContext().getResources().getDrawable(resId));
                }

                @Override // android.view.View
/* 578 */       protected boolean verifyDrawable(Drawable who) {
                    return super.verifyDrawable(who) || who == this.mMarginDrawable;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 583 */       protected void drawableStateChanged() {
/* 584 */           super.drawableStateChanged();
/* 585 */           Drawable d = this.mMarginDrawable;
/* 586 */           if (d != null && d.isStateful()) {
/* 587 */               d.setState(getDrawableState());
                    }
                }

/* 595 */       float distanceInfluenceForSnapDuration(float f) {
/* 598 */           return (float) Math.sin((float) (((double) (f - 0.5f)) * 0.4712389167638204d));
                }

/* 608 */       void smoothScrollTo(int position, int x, int y, int velocity) {
                    int duration;
/* 609 */           if (getChildCount() == 0) {
/* 611 */               setScrollingCacheEnabled(false);
/* 648 */               return;
                    }
/* 615 */           int sx = getScrollX();
/* 616 */           int sy = getScrollY();
/* 617 */           int dx = x - sx;
/* 618 */           int dy = y - sy;
/* 620 */           if (dx == 0 && dy == 0) {
/* 621 */               completeScroll();
/* 622 */               setScrollState(0);
/* 648 */               return;
                    }
/* 626 */           setScrollingCacheEnabled(true);
/* 627 */           this.mScrolling = true;
/* 628 */           setScrollState(2);
/* 630 */           int width = getWidth();
/* 631 */           int halfWidth = width / 2;
/* 632 */           float distanceRatio = Math.min(1.0f, (1.0f * Math.abs(dx)) / width);
/* 633 */           float distance = halfWidth + (halfWidth * distanceInfluenceForSnapDuration(distanceRatio));
/* 637 */           int velocity2 = Math.abs(velocity);
/* 638 */           if (velocity2 > 0) {
/* 639 */               duration = Math.round(1000.0f * Math.abs(distance / velocity2)) * 4;
                    } else {
/* 641 */               float pageDelta = Math.abs(dx) / (this.mPageMargin + width);
/* 642 */               duration = (int) ((1.0f + pageDelta) * 100.0f);
                    }
/* 646 */           this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, MAX_SETTLE_DURATION));
/* 647 */           invalidate();
                }

/* 650 */       void addNewItem(int position, int index) {
/* 651 */           ItemInfo ii = new ItemInfo();
/* 652 */           ii.position = position;
/* 653 */           ii.object = this.mAdapter.instantiateItem((ViewGroup) this, position);
/* 654 */           if (index < 0) {
/* 655 */               this.mItems.add(ii);
                    } else {
/* 657 */               this.mItems.add(index, ii);
                    }
                }

/* 663 */       void dataSetChanged() {
/* 664 */           boolean needPopulate = this.mItems.size() < 3 && this.mItems.size() < this.mAdapter.getCount();
/* 665 */           int newCurrItem = -1;
/* 667 */           boolean isUpdating = false;
/* 668 */           int i = 0;
                    while (i < this.mItems.size()) {
/* 669 */               ItemInfo ii = this.mItems.get(i);
/* 670 */               int newPos = this.mAdapter.getItemPosition(ii.object);
/* 672 */               if (newPos != -1) {
/* 676 */                   if (newPos == -2) {
/* 677 */                       this.mItems.remove(i);
/* 678 */                       i--;
/* 680 */                       if (!isUpdating) {
/* 681 */                           this.mAdapter.startUpdate((ViewGroup) this);
/* 682 */                           isUpdating = true;
                                }
/* 685 */                       this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
/* 686 */                       needPopulate = true;
/* 688 */                       if (this.mCurItem == ii.position) {
/* 690 */                           newCurrItem = Math.max(0, Math.min(this.mCurItem, this.mAdapter.getCount() - 1));
                                }
                            } else if (ii.position != newPos) {
/* 696 */                       if (ii.position == this.mCurItem) {
/* 698 */                           newCurrItem = newPos;
                                }
/* 701 */                       ii.position = newPos;
/* 702 */                       needPopulate = true;
                            }
                        }
/* 668 */               i++;
                    }
/* 706 */           if (isUpdating) {
/* 707 */               this.mAdapter.finishUpdate((ViewGroup) this);
                    }
/* 710 */           Collections.sort(this.mItems, COMPARATOR);
/* 712 */           if (newCurrItem >= 0) {
/* 714 */               setCurrentItemInternal(newCurrItem, false, true);
/* 715 */               needPopulate = true;
                    }
/* 717 */           if (needPopulate) {
/* 718 */               populate();
/* 719 */               requestLayout();
                    }
                }

/* 723 */       public void populate() {
/* 724 */           if (this.mAdapter != null && !this.mPopulatePending && getWindowToken() != null) {
/* 744 */               this.mAdapter.startUpdate((ViewGroup) this);
/* 746 */               int pageLimit = this.mOffscreenPageLimit;
/* 747 */               int startPos = Math.max(0, this.mCurItem - pageLimit);
/* 748 */               int N = this.mAdapter.getCount();
/* 749 */               int endPos = Math.min(N - 1, this.mCurItem + pageLimit);
/* 754 */               int lastPos = -1;
/* 755 */               int i = 0;
                        while (i < this.mItems.size()) {
/* 756 */                   ItemInfo ii = this.mItems.get(i);
/* 757 */                   if ((ii.position < startPos || ii.position > endPos) && !ii.scrolling) {
/* 759 */                       this.mItems.remove(i);
/* 760 */                       i--;
/* 761 */                       this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
                            } else if (lastPos < endPos && ii.position > startPos) {
/* 766 */                       int lastPos2 = lastPos + 1;
/* 767 */                       if (lastPos2 < startPos) {
/* 768 */                           lastPos2 = startPos;
                                }
/* 770 */                       while (lastPos2 <= endPos && lastPos2 < ii.position) {
/* 772 */                           addNewItem(lastPos2, i);
/* 773 */                           lastPos2++;
/* 774 */                           i++;
                                }
                            }
/* 777 */                   lastPos = ii.position;
/* 755 */                   i++;
                        }
/* 781 */               int lastPos3 = this.mItems.size() > 0 ? this.mItems.get(this.mItems.size() - 1).position : -1;
/* 782 */               if (lastPos3 < endPos) {
/* 783 */                   int lastPos4 = lastPos3 + 1;
/* 784 */                   if (lastPos4 <= startPos) {
                                lastPos4 = startPos;
                            }
/* 785 */                   while (lastPos4 <= endPos) {
/* 787 */                       addNewItem(lastPos4, -1);
/* 788 */                       lastPos4++;
                            }
                        }
/* 799 */               ItemInfo curItem = null;
                        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
/* 801 */                   if (this.mItems.get(i2).position == this.mCurItem) {
/* 802 */                       ItemInfo curItem2 = this.mItems.get(i2);
                                curItem = curItem2;
                                break;
                            }
                        }
/* 806 */               this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, curItem != null ? curItem.object : null);
/* 808 */               this.mAdapter.finishUpdate((ViewGroup) this);
/* 810 */               if (hasFocus()) {
/* 811 */                   View currentFocused = findFocus();
/* 812 */                   ItemInfo ii2 = currentFocused != null ? infoForAnyChild(currentFocused) : null;
/* 813 */                   if (ii2 == null || ii2.position != this.mCurItem) {
                                for (int i3 = 0; i3 < getChildCount(); i3++) {
/* 815 */                           View child = getChildAt(i3);
/* 816 */                           ItemInfo ii3 = infoForChild(child);
/* 817 */                           if (ii3 != null && ii3.position == this.mCurItem && child.requestFocus(2)) {
/* 825 */                               return;
                                    }
                                }
                            }
                        }
                    }
                }

/* 837 */       public static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new C00451());
                    Parcelable adapterState;
                    ClassLoader loader;
                    int position;

/* 838 */           public SavedState(Parcelable superState) {
/* 839 */               super(superState);
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 843 */           public void writeToParcel(@NonNull Parcel out, int flags) {
/* 844 */               super.writeToParcel(out, flags);
/* 845 */               out.writeInt(this.position);
/* 846 */               out.writeParcelable(this.adapterState, flags);
                    }

/* 850 */           public String toString() {
/* 851 */               return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.DraftViewPager$SavedState$1 */
/* 855 */           static class C00451 implements ParcelableCompatCreatorCallbacks<SavedState> {
/* 856 */               C00451() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 859 */               public SavedState createFromParcel(Parcel in, ClassLoader loader) {
/* 860 */                   return new SavedState(in, loader);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 864 */               public SavedState[] newArray(int size) {
/* 865 */                   return new SavedState[size];
                        }
                    }

/* 869 */           SavedState(Parcel in, ClassLoader loader) {
/* 870 */               super(in);
/* 872 */               loader = loader == null ? getClass().getClassLoader() : loader;
/* 874 */               this.position = in.readInt();
/* 875 */               this.adapterState = in.readParcelable(loader);
/* 876 */               this.loader = loader;
                    }
                }

                @Override // android.view.View
/* 881 */       public Parcelable onSaveInstanceState() {
/* 882 */           Parcelable superState = super.onSaveInstanceState();
/* 883 */           SavedState ss = new SavedState(superState);
/* 884 */           ss.position = this.mCurItem;
/* 885 */           if (this.mAdapter != null) {
/* 886 */               ss.adapterState = this.mAdapter.saveState();
                    }
/* 888 */           return ss;
                }

                @Override // android.view.View
/* 892 */       public void onRestoreInstanceState(Parcelable state) {
/* 893 */           if (!(state instanceof SavedState)) {
/* 894 */               super.onRestoreInstanceState(state);
/* 909 */               return;
                    }
/* 898 */           SavedState ss = (SavedState) state;
/* 899 */           super.onRestoreInstanceState(ss.getSuperState());
/* 901 */           if (this.mAdapter != null) {
/* 902 */               this.mAdapter.restoreState(ss.adapterState, ss.loader);
/* 903 */               setCurrentItemInternal(ss.position, false, true);
                    } else {
/* 905 */               this.mRestoredCurItem = ss.position;
/* 906 */               this.mRestoredAdapterState = ss.adapterState;
/* 907 */               this.mRestoredClassLoader = ss.loader;
                    }
                }

                @Override // android.view.ViewGroup
/* 912 */       public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
/* 913 */           if (!checkLayoutParams(params)) {
/* 914 */               params = generateLayoutParams(params);
                    }
/* 916 */           LayoutParams lp = (LayoutParams) params;
/* 917 */           lp.isDecor |= child instanceof Decor;
/* 918 */           if (this.mInLayout) {
/* 919 */               if (lp != null && lp.isDecor) {
/* 920 */                   throw new IllegalStateException("Cannot add pager decor view during layout");
                        }
/* 922 */               addViewInLayout(child, index, params);
/* 923 */               child.measure(this.mChildWidthMeasureSpec, this.mChildHeightMeasureSpec);
/* 935 */               return;
                    }
/* 925 */           super.addView(child, index, params);
                }

/* 937 */       ItemInfo infoForChild(View child) {
                    for (int i = 0; i < this.mItems.size(); i++) {
/* 939 */               ItemInfo ii = this.mItems.get(i);
/* 940 */               if (this.mAdapter.isViewFromObject(child, ii.object)) {
/* 944 */                   return ii;
                        }
                    }
/* 944 */           return null;
                }

/* 948 */       ItemInfo infoForAnyChild(View child) {
                    while (true) {
/* 949 */               Object parent = child.getParent();
                        if (parent != this) {
/* 950 */                   if (parent == null || !(parent instanceof View)) {
/* 951 */                       return null;
                            }
/* 953 */                   child = (View) parent;
                        } else {
/* 955 */                   return infoForChild(child);
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 959 */       protected void onAttachedToWindow() {
/* 960 */           super.onAttachedToWindow();
/* 961 */           this.mFirstLayout = true;
                }

                @Override // android.view.View
/* 970 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    LayoutParams lp;
                    LayoutParams lp2;
/* 971 */           setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
/* 975 */           int childWidthSize = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
/* 976 */           int childHeightSize = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
/* 983 */           int size = getChildCount();
                    for (int i = 0; i < size; i++) {
/* 985 */               View child = getChildAt(i);
/* 986 */               if (child.getVisibility() != 8 && (lp2 = (LayoutParams) child.getLayoutParams()) != null && lp2.isDecor) {
/* 989 */                   int hgrav = lp2.gravity & 7;
/* 990 */                   int vgrav = lp2.gravity & 112;
/* 991 */                   Log.d(TAG, "gravity: " + lp2.gravity + " hgrav: " + hgrav + " vgrav: " + vgrav);
/* 992 */                   int widthMode = Integer.MIN_VALUE;
/* 993 */                   int heightMode = Integer.MIN_VALUE;
/* 994 */                   boolean consumeVertical = vgrav == 48 || vgrav == 80;
/* 995 */                   boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
/* 997 */                   if (consumeVertical) {
/* 998 */                       widthMode = 1073741824;
                            } else if (consumeHorizontal) {
/* 1000 */                      heightMode = 1073741824;
                            }
/* 1003 */                  int widthSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, widthMode);
/* 1004 */                  int heightSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, heightMode);
/* 1005 */                  child.measure(widthSpec, heightSpec);
/* 1007 */                  if (consumeVertical) {
/* 1008 */                      childHeightSize -= child.getMeasuredHeight();
                            } else if (consumeHorizontal) {
/* 1010 */                      childWidthSize -= child.getMeasuredWidth();
                            }
                        }
                    }
/* 1016 */          this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, 1073741824);
/* 1017 */          this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, 1073741824);
/* 1020 */          this.mInLayout = true;
/* 1021 */          populate();
/* 1022 */          this.mInLayout = false;
/* 1025 */          int size2 = getChildCount();
                    for (int i2 = 0; i2 < size2; i2++) {
/* 1027 */              View child2 = getChildAt(i2);
/* 1028 */              if (child2.getVisibility() != 8 && ((lp = (LayoutParams) child2.getLayoutParams()) == null || !lp.isDecor)) {
/* 1034 */                  child2.measure(this.mChildWidthMeasureSpec, this.mChildHeightMeasureSpec);
                        }
                    }
                }

                @Override // android.view.View
/* 1041 */      protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 1042 */          super.onSizeChanged(w, h, oldw, oldh);
/* 1045 */          if (w != oldw) {
/* 1046 */              recomputeScrollPosition(w, oldw, this.mPageMargin, this.mPageMargin);
                    }
                }

/* 1050 */      private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
/* 1051 */          int widthWithMargin = width + margin;
/* 1052 */          if (oldWidth > 0) {
/* 1053 */              int oldScrollPos = getScrollX();
/* 1054 */              int oldwwm = oldWidth + oldMargin;
/* 1055 */              int oldScrollItem = oldScrollPos / oldwwm;
/* 1056 */              float scrollOffset = (oldScrollPos % oldwwm) / oldwwm;
/* 1057 */              int scrollPos = (int) ((oldScrollItem + scrollOffset) * widthWithMargin);
/* 1058 */              scrollTo(scrollPos, getScrollY());
/* 1059 */              if (!this.mScroller.isFinished()) {
/* 1061 */                  int newDuration = this.mScroller.getDuration() - this.mScroller.timePassed();
/* 1062 */                  this.mScroller.startScroll(scrollPos, 0, this.mCurItem * widthWithMargin, 0, newDuration);
/* 1071 */                  return;
                        }
/* 1071 */              return;
                    }
/* 1065 */          int scrollPos2 = this.mCurItem * widthWithMargin;
/* 1066 */          if (scrollPos2 != getScrollX()) {
/* 1067 */              completeScroll();
/* 1068 */              scrollTo(scrollPos2, getScrollY());
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1074 */      protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    int childLeft;
                    int childTop;
/* 1075 */          int count = getChildCount();
/* 1077 */          this.mInLayout = true;
/* 1078 */          populate();
/* 1079 */          this.mInLayout = false;
/* 1081 */          int width = r - l;
/* 1082 */          int height = b - t;
/* 1083 */          int scrollX = getScrollX();
/* 1084 */          int paddingLeft = getPaddingLeft();
/* 1085 */          int paddingTop = getPaddingTop();
/* 1086 */          int paddingRight = getPaddingRight();
/* 1087 */          int paddingBottom = getPaddingBottom();
/* 1089 */          int decorCount = 0;
                    for (int i = 0; i < count; i++) {
/* 1092 */              View child = getChildAt(i);
/* 1093 */              if (child.getVisibility() != 8) {
/* 1094 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1098 */                  if (lp.isDecor) {
/* 1099 */                      int hgrav = lp.gravity & 7;
/* 1100 */                      int vgrav = lp.gravity & 112;
                                switch (hgrav) {
                                    case 1:
/* 1110 */                              childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                                        break;
                                    case 2:
                                    case 4:
                                    default:
/* 1103 */                              childLeft = paddingLeft;
                                        break;
                                    case 3:
/* 1106 */                              childLeft = paddingLeft;
/* 1107 */                              paddingLeft += child.getMeasuredWidth();
                                        break;
                                    case 5:
/* 1114 */                              childLeft = (width - paddingRight) - child.getMeasuredWidth();
/* 1115 */                              paddingRight += child.getMeasuredWidth();
                                        break;
                                }
                                switch (vgrav) {
                                    case 16:
/* 1127 */                              childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                                        break;
                                    case 48:
/* 1123 */                              childTop = paddingTop;
/* 1124 */                              paddingTop += child.getMeasuredHeight();
                                        break;
                                    case 80:
/* 1131 */                              childTop = (height - paddingBottom) - child.getMeasuredHeight();
/* 1132 */                              paddingBottom += child.getMeasuredHeight();
                                        break;
                                    default:
/* 1120 */                              childTop = paddingTop;
                                        break;
                                }
/* 1135 */                      int childLeft2 = childLeft + scrollX;
/* 1136 */                      decorCount++;
/* 1137 */                      child.layout(childLeft2, childTop, child.getMeasuredWidth() + childLeft2, child.getMeasuredHeight() + childTop);
                            } else {
/* 1140 */                      ItemInfo ii = infoForChild(child);
                                if (ii != null) {
/* 1141 */                          int loff = (this.mPageMargin + width) * ii.position;
/* 1142 */                          int childLeft3 = paddingLeft + loff;
/* 1143 */                          int childTop2 = paddingTop;
/* 1145 */                          int rawOffset = (int) (getWidth() * (1.0f - this.mPreviewRatio));
/* 1148 */                          int firstChildOffset = (int) (getWidth() * ((1.0f - this.mPreviewRatio) / 2.0f));
/* 1149 */                          if (ii.position > 0) {
/* 1150 */                              int offset = rawOffset * ii.position;
/* 1151 */                              int margin = this.mDraftMargin * 2 * ii.position;
/* 1153 */                              child.layout((childLeft3 - offset) + firstChildOffset + margin, childTop2, ((child.getMeasuredWidth() + childLeft3) - offset) + firstChildOffset + margin, child.getMeasuredHeight() + childTop2);
                                    } else {
/* 1157 */                              child.layout(childLeft3 + firstChildOffset, childTop2, child.getMeasuredWidth() + childLeft3 + firstChildOffset, child.getMeasuredHeight() + childTop2);
                                    }
                                }
                            }
                        }
                    }
/* 1166 */          this.mTopPageBounds = paddingTop;
/* 1167 */          this.mBottomPageBounds = height - paddingBottom;
/* 1168 */          this.mDecorChildCount = decorCount;
/* 1169 */          this.mFirstLayout = false;
                }

                @Override // android.view.View
/* 1174 */      public void computeScroll() {
/* 1175 */          if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
/* 1178 */              int oldX = getScrollX();
/* 1179 */              int oldY = getScrollY();
/* 1180 */              int x = this.mScroller.getCurrX();
/* 1181 */              int y = this.mScroller.getCurrY();
/* 1183 */              if (oldX != x || oldY != y) {
/* 1184 */                  scrollTo(x, y);
/* 1185 */                  pageScrolled(x);
                        }
/* 1189 */              invalidate();
/* 1196 */              return;
                    }
/* 1195 */          completeScroll();
                }

/* 1198 */      private void pageScrolled(int xpos) {
/* 1199 */          int widthWithMargin = getWidth() + this.mPageMargin;
/* 1200 */          int position = xpos / widthWithMargin;
/* 1201 */          int offsetPixels = xpos % widthWithMargin;
/* 1202 */          float offset = offsetPixels / widthWithMargin;
/* 1204 */          this.mCalledSuper = false;
/* 1205 */          onPageScrolled(position, offset, offsetPixels);
/* 1206 */          if (!this.mCalledSuper) {
/* 1207 */              throw new IllegalStateException("onPageScrolled did not call superclass implementation");
                    }
                }

/* 1225 */      protected void onPageScrolled(int position, float offset, int offsetPixels) {
                    int childLeft;
/* 1226 */          if (this.mDecorChildCount > 0) {
/* 1227 */              int scrollX = getScrollX();
/* 1228 */              int paddingLeft = getPaddingLeft();
/* 1229 */              int paddingRight = getPaddingRight();
/* 1230 */              int width = getWidth();
/* 1231 */              int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1233 */                  View child = getChildAt(i);
/* 1234 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1235 */                  if (lp.isDecor) {
/* 1237 */                      int hgrav = lp.gravity & 7;
                                switch (hgrav) {
                                    case 1:
/* 1248 */                              childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                                        break;
                                    case 2:
                                    case 4:
                                    default:
/* 1241 */                              childLeft = paddingLeft;
                                        break;
                                    case 3:
/* 1244 */                              childLeft = paddingLeft;
/* 1245 */                              paddingLeft += child.getWidth();
                                        break;
                                    case 5:
/* 1252 */                              childLeft = (width - paddingRight) - child.getMeasuredWidth();
/* 1253 */                              paddingRight += child.getMeasuredWidth();
                                        break;
                                }
/* 1258 */                      int childOffset = (childLeft + scrollX) - child.getLeft();
/* 1259 */                      if (childOffset != 0) {
/* 1260 */                          child.offsetLeftAndRight(childOffset);
                                }
                            }
                        }
                    }
/* 1265 */          if (this.mOnPageChangeListener != null) {
/* 1266 */              this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
/* 1268 */          if (this.mInternalPageChangeListener != null) {
/* 1269 */              this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
/* 1271 */          this.mCalledSuper = true;
                }

/* 1274 */      private void completeScroll() {
/* 1275 */          boolean needPopulate = this.mScrolling;
/* 1276 */          if (needPopulate) {
/* 1278 */              setScrollingCacheEnabled(false);
/* 1279 */              this.mScroller.abortAnimation();
/* 1280 */              int oldX = getScrollX();
/* 1281 */              int oldY = getScrollY();
/* 1282 */              int x = this.mScroller.getCurrX();
/* 1283 */              int y = this.mScroller.getCurrY();
/* 1284 */              if (oldX != x || oldY != y) {
/* 1285 */                  scrollTo(x, y);
                        }
/* 1287 */              setScrollState(0);
                    }
/* 1289 */          this.mPopulatePending = false;
/* 1290 */          this.mScrolling = false;
                    for (int i = 0; i < this.mItems.size(); i++) {
/* 1292 */              ItemInfo ii = this.mItems.get(i);
/* 1293 */              if (ii.scrolling) {
/* 1294 */                  needPopulate = true;
/* 1295 */                  ii.scrolling = false;
                        }
                    }
/* 1298 */          if (needPopulate) {
/* 1299 */              populate();
                    }
                }

                @Override // android.view.ViewGroup
/* 1310 */      public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 1311 */          int action = ev.getAction() & 255;
/* 1314 */          if (action == 3 || action == 1) {
/* 1317 */              this.mIsBeingDragged = false;
/* 1318 */              this.mIsUnableToDrag = false;
/* 1319 */              this.mActivePointerId = -1;
/* 1320 */              if (this.mVelocityTracker != null) {
/* 1321 */                  this.mVelocityTracker.recycle();
/* 1322 */                  this.mVelocityTracker = null;
                        }
/* 1324 */              return false;
                    }
/* 1329 */          if (action != 0) {
/* 1330 */              if (this.mIsBeingDragged) {
/* 1332 */                  return true;
                        }
/* 1334 */              if (this.mIsUnableToDrag) {
/* 1336 */                  return false;
                        }
                    }
                    switch (action) {
                        case 0:
/* 1395 */                  float x = ev.getX();
                            this.mInitialMotionX = x;
                            this.mLastMotionX = x;
/* 1396 */                  this.mLastMotionY = ev.getY();
/* 1397 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
/* 1399 */                  if (this.mScrollState == 2) {
/* 1401 */                      this.mIsBeingDragged = true;
/* 1402 */                      this.mIsUnableToDrag = false;
/* 1403 */                      setScrollState(1);
                            } else {
/* 1405 */                      completeScroll();
/* 1406 */                      this.mIsBeingDragged = false;
/* 1407 */                      this.mIsUnableToDrag = false;
                            }
                            break;
                        case 2:
/* 1351 */                  int activePointerId = this.mActivePointerId;
/* 1352 */                  if (activePointerId != -1) {
/* 1357 */                      int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
/* 1358 */                      float x2 = MotionEventCompat.getX(ev, pointerIndex);
/* 1359 */                      float dx = x2 - this.mLastMotionX;
/* 1360 */                      float xDiff = Math.abs(dx);
/* 1361 */                      float y = MotionEventCompat.getY(ev, pointerIndex);
/* 1362 */                      float yDiff = Math.abs(y - this.mLastMotionY);
/* 1365 */                      if (canScroll(this, false, (int) dx, (int) x2, (int) y)) {
/* 1367 */                          this.mLastMotionX = x2;
                                    this.mInitialMotionX = x2;
/* 1368 */                          this.mLastMotionY = y;
/* 1369 */                          return false;
                                }
                                if (xDiff > this.mTouchSlop && xDiff > yDiff) {
/* 1373 */                          this.mIsBeingDragged = true;
/* 1374 */                          setScrollState(1);
/* 1375 */                          this.mLastMotionX = x2;
/* 1376 */                          setScrollingCacheEnabled(true);
                                } else if (yDiff > this.mTouchSlop) {
/* 1384 */                          this.mIsUnableToDrag = true;
                                }
                            }
                            break;
                        case 6:
/* 1417 */                  onSecondaryPointerUp(ev);
                            break;
                    }
/* 1421 */          if (!this.mIsBeingDragged) {
/* 1424 */              if (this.mVelocityTracker == null) {
/* 1425 */                  this.mVelocityTracker = VelocityTracker.obtain();
                        }
/* 1427 */              this.mVelocityTracker.addMovement(ev);
                    }
/* 1434 */          return this.mIsBeingDragged;
                }

                @Override // android.view.View
/* 1438 */      public boolean onTouchEvent(@NonNull MotionEvent ev) {
/* 1439 */          if (this.mFakeDragging) {
/* 1443 */              return true;
                    }
/* 1446 */          if ((ev.getAction() == 0 && ev.getEdgeFlags() != 0) || this.mAdapter == null || this.mAdapter.getCount() == 0) {
/* 1454 */              return false;
                    }
/* 1457 */          if (this.mVelocityTracker == null) {
/* 1458 */              this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 1460 */          this.mVelocityTracker.addMovement(ev);
/* 1462 */          int action = ev.getAction();
/* 1463 */          boolean needsInvalidate = false;
                    switch (action & 255) {
                        case 0:
/* 1471 */                  completeScroll();
/* 1474 */                  float x = ev.getX();
                            this.mInitialMotionX = x;
                            this.mLastMotionX = x;
/* 1475 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                            break;
                        case 1:
/* 1534 */                  if (this.mIsBeingDragged) {
/* 1535 */                      VelocityTracker velocityTracker = this.mVelocityTracker;
/* 1536 */                      velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
/* 1537 */                      int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
/* 1539 */                      this.mPopulatePending = true;
/* 1540 */                      int widthWithMargin = getWidth() + this.mPageMargin;
/* 1541 */                      int scrollX = getScrollX();
/* 1542 */                      int currentPage = scrollX / ((int) (widthWithMargin * this.mPreviewRatio));
/* 1543 */                      float pageOffset = (scrollX % widthWithMargin) / widthWithMargin;
/* 1544 */                      int activePointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1546 */                      float x2 = MotionEventCompat.getX(ev, activePointerIndex);
/* 1547 */                      int totalDelta = (int) (x2 - this.mInitialMotionX);
/* 1548 */                      int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity, totalDelta);
/* 1550 */                      setCurrentItemInternal(nextPage, true, true, initialVelocity);
/* 1552 */                      this.mActivePointerId = -1;
/* 1553 */                      endDrag();
/* 1554 */                      needsInvalidate = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                            }
                            break;
                        case 2:
/* 1479 */                  if (!this.mIsBeingDragged) {
/* 1480 */                      int pointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1481 */                      float x3 = MotionEventCompat.getX(ev, pointerIndex);
/* 1482 */                      float xDiff = Math.abs(x3 - this.mLastMotionX);
/* 1483 */                      float y = MotionEventCompat.getY(ev, pointerIndex);
/* 1484 */                      float yDiff = Math.abs(y - this.mLastMotionY);
                                if (xDiff > this.mTouchSlop && xDiff > yDiff) {
/* 1492 */                          this.mIsBeingDragged = true;
/* 1493 */                          this.mLastMotionX = x3;
/* 1494 */                          setScrollState(1);
/* 1495 */                          setScrollingCacheEnabled(true);
                                }
                            }
/* 1498 */                  if (this.mIsBeingDragged) {
/* 1500 */                      int activePointerIndex2 = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1502 */                      float x4 = MotionEventCompat.getX(ev, activePointerIndex2);
/* 1503 */                      float deltaX = this.mLastMotionX - x4;
/* 1504 */                      this.mLastMotionX = x4;
/* 1505 */                      float oldScrollX = getScrollX();
/* 1506 */                      float scrollX2 = oldScrollX + deltaX;
/* 1507 */                      int width = (int) (getWidth() * this.mPreviewRatio);
/* 1508 */                      int widthWithMargin2 = width + this.mPageMargin;
/* 1510 */                      int lastItemIndex = this.mAdapter.getCount() - 1;
/* 1511 */                      float leftBound = Math.max(0, (this.mCurItem - 1) * widthWithMargin2);
/* 1512 */                      float rightBound = Math.min(this.mCurItem + 1, lastItemIndex) * widthWithMargin2;
                                if (scrollX2 < leftBound) {
                                    if (leftBound == 0.0f) {
/* 1516 */                              float over = -scrollX2;
/* 1517 */                              needsInvalidate = this.mLeftEdge.onPull(over / width);
                                    }
/* 1519 */                          scrollX2 = leftBound;
                                } else if (scrollX2 > rightBound) {
                                    if (rightBound == lastItemIndex * widthWithMargin2) {
/* 1522 */                              float over2 = scrollX2 - rightBound;
/* 1523 */                              needsInvalidate = this.mRightEdge.onPull(over2 / width);
                                    }
/* 1525 */                          scrollX2 = rightBound;
                                }
/* 1528 */                      this.mLastMotionX += scrollX2 - ((int) scrollX2);
/* 1529 */                      scrollTo((int) scrollX2, getScrollY());
/* 1530 */                      pageScrolled((int) scrollX2);
                            }
                            break;
                        case 3:
/* 1558 */                  if (this.mIsBeingDragged) {
/* 1559 */                      setCurrentItemInternal(this.mCurItem, true, true);
/* 1560 */                      this.mActivePointerId = -1;
/* 1561 */                      endDrag();
/* 1562 */                      needsInvalidate = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                            }
                            break;
                        case 5:
/* 1566 */                  int index = MotionEventCompat.getActionIndex(ev);
/* 1567 */                  float x5 = MotionEventCompat.getX(ev, index);
/* 1568 */                  this.mLastMotionX = x5;
/* 1569 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                            break;
                        case 6:
/* 1573 */                  onSecondaryPointerUp(ev);
/* 1574 */                  this.mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                            break;
                    }
/* 1578 */          if (needsInvalidate) {
/* 1579 */              invalidate();
                    }
/* 1581 */          return true;
                }

/* 1585 */      private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaX) {
/* 1586 */          if (Math.abs(deltaX) > this.mFlingDistance && Math.abs(velocity) > this.mMinimumVelocity) {
/* 1587 */              if (velocity > 0) {
                            return currentPage;
                        }
/* 1587 */              int targetPage = currentPage + 1;
/* 1592 */              return targetPage;
                    }
/* 1589 */          int targetPage2 = (int) (currentPage + pageOffset + 0.5f);
/* 1592 */          return targetPage2;
                }

                @Override // android.view.View
/* 1596 */      public void draw(@NonNull Canvas canvas) {
/* 1597 */          super.draw(canvas);
/* 1598 */          boolean needsInvalidate = false;
/* 1600 */          int overScrollMode = ViewCompat.getOverScrollMode(this);
/* 1601 */          if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
/* 1604 */              if (!this.mLeftEdge.isFinished()) {
/* 1605 */                  int restoreCount = canvas.save();
/* 1606 */                  int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
/* 1608 */                  canvas.rotate(270.0f);
/* 1609 */                  canvas.translate((-height) + getPaddingTop(), 0.0f);
/* 1610 */                  this.mLeftEdge.setSize(height, getWidth());
/* 1598 */                  needsInvalidate = false | this.mLeftEdge.draw(canvas);
/* 1612 */                  canvas.restoreToCount(restoreCount);
                        }
/* 1614 */              if (!this.mRightEdge.isFinished()) {
/* 1615 */                  int restoreCount2 = canvas.save();
/* 1616 */                  int width = getWidth();
/* 1617 */                  int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
/* 1618 */                  int itemCount = this.mAdapter != null ? this.mAdapter.getCount() : 1;
/* 1620 */                  canvas.rotate(90.0f);
/* 1621 */                  canvas.translate(-getPaddingTop(), ((-itemCount) * (this.mPageMargin + width)) + this.mPageMargin);
/* 1623 */                  this.mRightEdge.setSize(height2, width);
/* 1624 */                  needsInvalidate |= this.mRightEdge.draw(canvas);
/* 1625 */                  canvas.restoreToCount(restoreCount2);
                        }
                    } else {
/* 1628 */              this.mLeftEdge.finish();
/* 1629 */              this.mRightEdge.finish();
                    }
/* 1632 */          if (needsInvalidate) {
/* 1634 */              invalidate();
                    }
                }

                @Override // android.view.View
/* 1639 */      protected void onDraw(Canvas canvas) {
/* 1640 */          super.onDraw(canvas);
/* 1643 */          if (this.mPageMargin > 0 && this.mMarginDrawable != null) {
/* 1644 */              int scrollX = getScrollX();
/* 1645 */              int width = getWidth();
/* 1646 */              int offset = scrollX % (this.mPageMargin + width);
/* 1647 */              if (offset != 0) {
/* 1649 */                  int left = (scrollX - offset) + width;
/* 1650 */                  this.mMarginDrawable.setBounds(left, this.mTopPageBounds, this.mPageMargin + left, this.mBottomPageBounds);
/* 1652 */                  this.mMarginDrawable.draw(canvas);
                        }
                    }
                }

/* 1673 */      public boolean beginFakeDrag() {
/* 1674 */          if (this.mIsBeingDragged) {
                        return false;
                    }
/* 1677 */          this.mFakeDragging = true;
/* 1678 */          setScrollState(1);
/* 1679 */          this.mLastMotionX = 0.0f;
                    this.mInitialMotionX = 0.0f;
/* 1680 */          if (this.mVelocityTracker == null) {
/* 1681 */              this.mVelocityTracker = VelocityTracker.obtain();
                    } else {
/* 1683 */              this.mVelocityTracker.clear();
                    }
/* 1685 */          long time = SystemClock.uptimeMillis();
/* 1686 */          MotionEvent ev = MotionEvent.obtain(time, time, 0, 0.0f, 0.0f, 0);
/* 1687 */          this.mVelocityTracker.addMovement(ev);
/* 1688 */          ev.recycle();
/* 1689 */          this.mFakeDragBeginTime = time;
                    return true;
                }

/* 1699 */      public void endFakeDrag() {
/* 1700 */          if (!this.mFakeDragging) {
/* 1701 */              throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
                    }
/* 1704 */          VelocityTracker velocityTracker = this.mVelocityTracker;
/* 1705 */          velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
/* 1706 */          int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
/* 1708 */          this.mPopulatePending = true;
/* 1709 */          int totalDelta = (int) (this.mLastMotionX - this.mInitialMotionX);
/* 1710 */          int scrollX = getScrollX();
/* 1711 */          int widthWithMargin = getWidth() + this.mPageMargin;
/* 1712 */          int currentPage = scrollX / widthWithMargin;
/* 1713 */          float pageOffset = (scrollX % widthWithMargin) / widthWithMargin;
/* 1714 */          int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity, totalDelta);
/* 1715 */          setCurrentItemInternal(nextPage, true, true, initialVelocity);
/* 1716 */          endDrag();
/* 1718 */          this.mFakeDragging = false;
                }

/* 1728 */      public void fakeDragBy(float xOffset) {
/* 1729 */          if (!this.mFakeDragging) {
/* 1730 */              throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
                    }
/* 1733 */          this.mLastMotionX += xOffset;
/* 1734 */          float scrollX = getScrollX() - xOffset;
/* 1735 */          int width = getWidth();
/* 1736 */          int widthWithMargin = width + this.mPageMargin;
/* 1738 */          float leftBound = Math.max(0, (this.mCurItem - 1) * widthWithMargin);
/* 1739 */          float rightBound = Math.min(this.mCurItem + 1, this.mAdapter.getCount() - 1) * widthWithMargin;
                    if (scrollX < leftBound) {
/* 1742 */              scrollX = leftBound;
                    } else if (scrollX > rightBound) {
/* 1744 */              scrollX = rightBound;
                    }
/* 1747 */          this.mLastMotionX += scrollX - ((int) scrollX);
/* 1748 */          scrollTo((int) scrollX, getScrollY());
/* 1749 */          pageScrolled((int) scrollX);
/* 1752 */          long time = SystemClock.uptimeMillis();
/* 1753 */          MotionEvent ev = MotionEvent.obtain(this.mFakeDragBeginTime, time, 2, this.mLastMotionX, 0.0f, 0);
/* 1755 */          this.mVelocityTracker.addMovement(ev);
/* 1756 */          ev.recycle();
                }

/* 1767 */      public boolean isFakeDragging() {
/* 1768 */          return this.mFakeDragging;
                }

/* 1771 */      private void onSecondaryPointerUp(MotionEvent ev) {
/* 1772 */          int pointerIndex = MotionEventCompat.getActionIndex(ev);
/* 1773 */          int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
/* 1774 */          if (pointerId == this.mActivePointerId) {
/* 1777 */              int newPointerIndex = pointerIndex == 0 ? 1 : 0;
/* 1778 */              this.mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
/* 1779 */              this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
/* 1780 */              if (this.mVelocityTracker != null) {
/* 1781 */                  this.mVelocityTracker.clear();
                        }
                    }
                }

/* 1786 */      private void endDrag() {
/* 1787 */          this.mIsBeingDragged = false;
/* 1788 */          this.mIsUnableToDrag = false;
/* 1790 */          if (this.mVelocityTracker != null) {
/* 1791 */              this.mVelocityTracker.recycle();
/* 1792 */              this.mVelocityTracker = null;
                    }
                }

/* 1796 */      private void setScrollingCacheEnabled(boolean enabled) {
/* 1797 */          if (this.mScrollingCacheEnabled != enabled) {
/* 1798 */              this.mScrollingCacheEnabled = enabled;
                    }
                }

/* 1822 */      protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
/* 1823 */          if (v instanceof ViewGroup) {
/* 1824 */              ViewGroup group = (ViewGroup) v;
/* 1825 */              int scrollX = v.getScrollX();
/* 1826 */              int scrollY = v.getScrollY();
/* 1827 */              int count = group.getChildCount();
                        for (int i = count - 1; i >= 0; i--) {
/* 1832 */                  View child = group.getChildAt(i);
/* 1833 */                  if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
/* 1837 */                      return true;
                            }
                        }
                    }
                    return checkV && ViewCompat.canScrollHorizontally(v, -dx);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1847 */      public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
                    return super.dispatchKeyEvent(event) || executeKeyEvent(event);
                }

/* 1859 */      public boolean executeKeyEvent(KeyEvent event) {
/* 1861 */          if (event.getAction() != 0) {
/* 1860 */              return false;
                    }
                    switch (event.getKeyCode()) {
                        case 21:
/* 1864 */                  boolean handled = arrowScroll(17);
/* 1882 */                  return handled;
                        case 22:
/* 1867 */                  boolean handled2 = arrowScroll(66);
/* 1882 */                  return handled2;
                        case 61:
/* 1870 */                  if (Build.VERSION.SDK_INT < 11) {
/* 1860 */                      return false;
                            }
/* 1873 */                  if (KeyEventCompat.hasNoModifiers(event)) {
/* 1874 */                      boolean handled3 = arrowScroll(2);
/* 1882 */                      return handled3;
                            }
/* 1875 */                  if (!KeyEventCompat.hasModifiers(event, 1)) {
/* 1860 */                      return false;
                            }
/* 1876 */                  boolean handled4 = arrowScroll(1);
/* 1882 */                  return handled4;
                        default:
/* 1860 */                  return false;
                    }
                }

/* 1885 */      public boolean arrowScroll(int direction) {
/* 1886 */          View currentFocused = findFocus();
/* 1887 */          if (currentFocused == this) {
                        currentFocused = null;
                    }
/* 1889 */          boolean handled = false;
/* 1891 */          View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
/* 1893 */          if (nextFocused != null && nextFocused != currentFocused) {
/* 1894 */              if (direction == 17) {
/* 1900 */                  handled = (currentFocused == null || nextFocused.getLeft() < currentFocused.getLeft()) ? nextFocused.requestFocus() : pageLeft();
                        } else if (direction == 66) {
/* 1908 */                  handled = (currentFocused == null || nextFocused.getLeft() > currentFocused.getLeft()) ? nextFocused.requestFocus() : pageRight();
                        }
                    } else if (direction == 17 || direction == 1) {
/* 1913 */              handled = pageLeft();
                    } else if (direction == 66 || direction == 2) {
/* 1916 */              handled = pageRight();
                    }
/* 1918 */          if (handled) {
/* 1919 */              playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    }
/* 1921 */          return handled;
                }

/* 1924 */      boolean pageLeft() {
/* 1925 */          if (this.mCurItem <= 0) {
                        return false;
                    }
/* 1926 */          setCurrentItem(this.mCurItem - 1, true);
                    return true;
                }

/* 1932 */      boolean pageRight() {
/* 1933 */          if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
                        return false;
                    }
/* 1934 */          setCurrentItem(this.mCurItem + 1, true);
                    return true;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1944 */      public void addFocusables(@NonNull ArrayList<View> views, int direction, int focusableMode) {
                    ItemInfo ii;
/* 1945 */          int focusableCount = views.size();
/* 1947 */          int descendantFocusability = getDescendantFocusability();
/* 1949 */          if (descendantFocusability != 393216) {
                        for (int i = 0; i < getChildCount(); i++) {
/* 1951 */                  View child = getChildAt(i);
/* 1952 */                  if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
/* 1955 */                      child.addFocusables(views, direction, focusableMode);
                            }
                        }
                    }
/* 1965 */          if ((descendantFocusability != 262144 || focusableCount == views.size()) && isFocusable()) {
/* 1974 */              if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
/* 1979 */                  views.add(this);
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1991 */      public void addTouchables(@NonNull ArrayList<View> views) {
                    ItemInfo ii;
                    for (int i = 0; i < getChildCount(); i++) {
/* 1993 */              View child = getChildAt(i);
/* 1994 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
/* 1997 */                  child.addTouchables(views);
                        }
                    }
                }

                @Override // android.view.ViewGroup
/* 2011 */      protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
                    int index;
                    int increment;
                    int end;
                    ItemInfo ii;
/* 2012 */          int count = getChildCount();
/* 2013 */          if ((direction & 2) != 0) {
/* 2014 */              index = 0;
/* 2015 */              increment = 1;
/* 2016 */              end = count;
                    } else {
/* 2018 */              index = count - 1;
/* 2019 */              increment = -1;
/* 2020 */              end = -1;
                    }
                    for (int i = index; i != end; i += increment) {
/* 2023 */              View child = getChildAt(i);
/* 2024 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.requestFocus(direction, previouslyFocusedRect)) {
/* 2028 */                  return true;
                        }
                    }
/* 2033 */          return false;
                }

                @Override // android.view.View
/* 2042 */      public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
                    ItemInfo ii;
/* 2043 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 2045 */              View child = getChildAt(i);
/* 2046 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(event)) {
/* 2050 */                  return true;
                        }
                    }
/* 2055 */          return false;
                }

                @Override // android.view.ViewGroup
/* 2059 */      protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 2060 */          return new LayoutParams();
                }

                @Override // android.view.ViewGroup
/* 2064 */      protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 2065 */          return generateDefaultLayoutParams();
                }

                @Override // android.view.ViewGroup
/* 2069 */      protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
                    return (p instanceof LayoutParams) && super.checkLayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 2074 */      public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 2075 */          return new LayoutParams(getContext(), attrs);
                }

/* 2076 */      private class PagerObserver extends DataSetObserver {
/* 2077 */          private PagerObserver() {
                    }
@Override // android.database.DataSetObserver
/* 2080 */          public void onChanged() {
/* 2081 */              DraftViewPager.this.dataSetChanged();
                    }

                    @Override // android.database.DataSetObserver
/* 2085 */          public void onInvalidated() {
/* 2086 */              DraftViewPager.this.dataSetChanged();
                    }
                }

/* 2106 */      public static class LayoutParams extends ViewGroup.LayoutParams {
                    public int gravity;
                    public boolean isDecor;

/* 2107 */          public LayoutParams() {
/* 2108 */              super(-1, -1);
                    }

/* 2111 */          public LayoutParams(Context context, AttributeSet attrs) {
/* 2112 */              super(context, attrs);
/* 2114 */              TypedArray a = context.obtainStyledAttributes(attrs, DraftViewPager.access$100());
/* 2115 */              this.gravity = a.getInteger(0, 0);
/* 2116 */              a.recycle();
                    }
                }

/* 2120 */      public int getLeftOfSelectedFragment() {
/* 2121 */          int width = getWidth();
/* 2122 */          return ((int) (width - (width * this.mPreviewRatio))) / 2;
                }
            }
