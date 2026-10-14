            package co.vine.android.dragsort;

            import android.R;
            import android.animation.ObjectAnimator;
            import android.animation.ValueAnimator;
            import android.annotation.TargetApi;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.Drawable;
            import android.os.SystemClock;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import android.support.v4.view.MotionEventCompat;
            import android.util.AttributeSet;
            import android.util.Property;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.ViewTreeObserver;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.RelativeLayout;
            import co.vine.android.animation.SmoothAnimator;
            import co.vine.android.views.HorizontalListView;
            
            
            @TargetApi(14)
    public abstract class DragSortWidget extends RelativeLayout implements View.OnTouchListener, AdapterView.OnItemClickListener, HorizontalListView.OnScrollListener {
                private static final int DROP_DURATION = 150;
                private static final int DUPLICATE_DURATION = 200;
                private static final float FOCUSED_ALPHA = 1.0f;
                public static final int INVALID_POSITION = -1;
                private static final float MAX_SCROLL_SPEED_DENSITY_MULTIPLIER = 0.1f;
                private static final int PICK_UP_CONTENT_DURATION = 130;
                private static final int PICK_UP_DURATION = ViewConfiguration.getTapTimeout() + 1;
                private static final int REMOVE_DURATION = 100;
                private static final float SCROLL_THRESHOLD = 0.33f;
                private static final float SMOOTHNESS = 0.05f;
                private static final int SWAP_DURATION = 100;
                private static final float UNFOCUSED_ALPHA = 0.3f;
                private boolean isFromContentView;
                private Drawable mActivatedDrawable;
                private int mActivePointerId;
                private DragSortAdapterWrapper mAdapterWrapper;
                private CheckForLongPress mCheckForLongPress;
                private View mContentView;
                private Rect mContentViewBounds;
                private ContentViewPickUpAnimator mContentViewPickUpAnimator;
                private CurrentlyPlayingProvider mCurrentlyPlayingProvider;
                private int mDownPosition;
                private DragListener mDragListener;
                private DragScroller mDragScroller;
                private DropAnimator mDropAnimator;
                boolean mDropping;
                private Bitmap mFloatBitmap;
                private ImageView mFloatView;
                private Drawable mFloatViewActivatedDrawable;
                private float mFloatViewAlpha;
                private int mFloatViewDefaultSize;
                private FloatViewDestroyAnimator mFloatViewDestroyAnimator;
                private int mFloatViewHeight;
                private FloatViewInteractionListener mFloatViewInteractionListener;
                private int mFloatViewListPosition;
                private int mFloatViewWidth;
                private int mFloatViewX;
                private int mFloatViewY;
                private int mFocusedPosition;
                private HorizontalListView mListView;
                private float mMaxScrollSpeed;
                private boolean mPickingUpContent;
                private RemoveAnimator mRemoveAnimator;
                private RemoveListener mRemoveListener;
                private int mRemovePosition;
                boolean mRemoving;
                private SelectionChangedListener mSelectionChangedListener;
                private int mSelectionPosition;
                private int mTouchDownX;
                private int mTouchDownY;
                private int mTouchListPosition;
                private final int mTouchSlop;
                private int mTouchX;
                private int mTouchY;
                private int mXOffsetFromTouch;
                private int mYOffsetFromTouch;

                public interface CurrentlyPlayingProvider {
                    int getCurrentlyPlayingPosition();
                }

                public interface DragListener {
                    void drag(int i, int i2);
                }

                public interface FloatViewInteractionListener {
                    boolean canPickUpFloatView();

                    boolean floatViewDropped(int i);

                    void floatViewLanded(int i);

                    void floatViewMoved();

                    void floatViewPickedUp(int i);
                }

                public interface RemoveListener {
                    void remove(int i);
                }

                public interface SelectionChangedListener {
                    void onSelectionChanged(int i, boolean z);
                }

                public interface ShouldAnimateInProvider {
                    boolean shouldAnimateIn(int i);
                }

                protected abstract Drawable getActiveDrawable(TypedArray typedArray);

                protected abstract Drawable getFloatViewActivatedDrawable(TypedArray typedArray);

                protected abstract int getFloatViewDefaultSize(TypedArray typedArray);

                protected abstract TypedArray getStylingArray(Context context, AttributeSet attributeSet, int i);

        static  HorizontalListView access$000(DragSortWidget x0) {
            return x0.mListView;
                }

        static  int access$100(DragSortWidget x0) {
            return x0.mFloatViewListPosition;
                }

        static  float access$1000(DragSortWidget x0) {
            return x0.mFloatViewAlpha;
                }

        static  float access$1002(DragSortWidget x0, float x1) {
            x0.mFloatViewAlpha = x1;
                    return x1;
                }

        static  int access$102(DragSortWidget x0, int x1) {
            x0.mFloatViewListPosition = x1;
                    return x1;
                }

        static  void access$1100(DragSortWidget x0) {
            x0.destroyFloatView();
                }

        static  void access$1200(DragSortWidget x0) {
            x0.adjustItems();
                }

        static  Rect access$1300(DragSortWidget x0) {
            return x0.mContentViewBounds;
                }

        static  int access$1400(DragSortWidget x0) {
            return x0.mFloatViewWidth;
                }

        static  int access$1402(DragSortWidget x0, int x1) {
            x0.mFloatViewWidth = x1;
                    return x1;
                }

        static  int access$1500(DragSortWidget x0) {
            return x0.mFloatViewHeight;
                }

        static  int access$1502(DragSortWidget x0, int x1) {
            x0.mFloatViewHeight = x1;
                    return x1;
                }

        static  int access$1600(DragSortWidget x0) {
            return x0.mXOffsetFromTouch;
                }

        static  int access$1602(DragSortWidget x0, int x1) {
            x0.mXOffsetFromTouch = x1;
                    return x1;
                }

        static  int access$1700(DragSortWidget x0) {
            return x0.mYOffsetFromTouch;
                }

        static  int access$1702(DragSortWidget x0, int x1) {
            x0.mYOffsetFromTouch = x1;
                    return x1;
                }

        static  int access$1800(DragSortWidget x0) {
            return x0.mTouchY;
                }

        static  int access$1900(DragSortWidget x0) {
            return x0.mFloatViewDefaultSize;
                }

        static  int access$200(DragSortWidget x0) {
            return x0.mTouchX;
                }

        static  boolean access$2002(DragSortWidget x0, boolean x1) {
            x0.mPickingUpContent = x1;
                    return x1;
                }

        static  int access$2100(DragSortWidget x0) {
            return x0.mRemovePosition;
                }

        static  int access$2102(DragSortWidget x0, int x1) {
            x0.mRemovePosition = x1;
                    return x1;
                }

        static  int access$2200(DragSortWidget x0) {
            return x0.mSelectionPosition;
                }

        static  void access$2300(DragSortWidget x0) {
            x0.finishRemoveSelection();
                }

        static  Drawable access$2400(DragSortWidget x0) {
            return x0.mActivatedDrawable;
                }

        static  int access$2500(DragSortWidget x0) {
            return x0.mFocusedPosition;
                }

        static  RemoveAnimator access$2600(DragSortWidget x0) {
            return x0.mRemoveAnimator;
                }

        static  ImageView access$2800(DragSortWidget x0) {
            return x0.mFloatView;
                }

        static  int access$2900(DragSortWidget x0) {
            return x0.mTouchListPosition;
                }

        static  float access$300(DragSortWidget x0) {
            return x0.mMaxScrollSpeed;
                }

        static  int access$3000(DragSortWidget x0) {
            return x0.mDownPosition;
                }

        static  boolean access$3100(DragSortWidget x0, int x1, boolean x2) {
            return x0.handleClick(x1, x2);
                }

        static  boolean access$3200(DragSortWidget x0, int x1) {
            return x0.pickUpView(x1);
                }

        static  int access$400(DragSortWidget x0) {
            return x0.getClosestItemPosition();
                }

        static  void access$500(DragSortWidget x0, int x1, int x2) {
            x0.dragFloatView(x1, x2);
                }

        static  FloatViewDestroyAnimator access$600(DragSortWidget x0) {
            return x0.mFloatViewDestroyAnimator;
                }

        static  int access$700(DragSortWidget x0) {
            return x0.mFloatViewX;
                }

        static  int access$702(DragSortWidget x0, int x1) {
            x0.mFloatViewX = x1;
                    return x1;
                }

        static  int access$800(DragSortWidget x0) {
            return x0.mFloatViewY;
                }

        static  int access$802(DragSortWidget x0, int x1) {
            x0.mFloatViewY = x1;
                    return x1;
                }

        static  void access$900(DragSortWidget x0) {
            x0.finishDrop();
                }

       public DragSortWidget(Context context) {
           this(context, null);
                }

       public DragSortWidget(Context context, AttributeSet attrs) {
           this(context, attrs, 0);
                }

       public DragSortWidget(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
            this.mDragScroller = new DragScroller();
            this.mDropAnimator = new DropAnimator(SMOOTHNESS, 150);
            this.mRemoveAnimator = new RemoveAnimator(SMOOTHNESS, 100);
            this.mFloatViewDestroyAnimator = new FloatViewDestroyAnimator(SMOOTHNESS, 100);
            this.mContentViewPickUpAnimator = new ContentViewPickUpAnimator(SMOOTHNESS, 130);
            this.mFloatView = null;
            this.mFloatViewListPosition = -1;
            this.mSelectionPosition = -1;
           this.mFloatViewAlpha = 255.0f;
           this.mDropping = false;
           this.mRemoving = false;
           this.mPickingUpContent = false;
           this.isFromContentView = false;
           TypedArray a = getStylingArray(context, attrs, defStyle);
           this.mActivatedDrawable = getActiveDrawable(a);
           this.mFloatViewActivatedDrawable = getFloatViewActivatedDrawable(a);
           this.mFloatViewDefaultSize = getFloatViewDefaultSize(a);
           this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
           a.recycle();
                }

                @Override 
       public void onFinishInflate() {
           this.mListView = (HorizontalListView) findViewById(R.id.list);
           this.mListView.setOnTouchListener(this);
           this.mListView.setOnItemClickListener(this);
           this.mListView.setFingerScrollEnabled(true);
           this.mListView.setOnScrollListener(this);
           this.mCheckForLongPress = new CheckForLongPress();
           this.mMaxScrollSpeed = getResources().getDisplayMetrics().xdpi * MAX_SCROLL_SPEED_DENSITY_MULTIPLIER;
                }

       public void setFirstItem(int position) {
           this.mListView.setSelection(position);
           if (this.mFloatView != null && !this.mDropping) {
               dragFloatView(this.mFloatViewListPosition, getClosestItemPosition());
                    }
                }

       public void setSelection(int selection) {
           this.mSelectionPosition = selection;
           if (selection != -1) {
               this.mFocusedPosition = selection;
                    }
           adjustItems();
           invalidate();
                }

       public void setFocused(int position) {
           this.mFocusedPosition = position;
           adjustItems();
           this.mListView.invalidate();
                }

                
       private boolean handleListViewTouch(MotionEvent event) {
           int action = event.getAction() & 255;
           this.mTouchListPosition = getClosestItemPosition();
                    switch (action) {
                        case 0:
                   this.mTouchDownX = this.mTouchX;
                   this.mTouchDownY = this.mTouchY;
                   this.mActivePointerId = event.getPointerId(0);
                   this.mDownPosition = this.mTouchListPosition;
                   postDelayed(this.mCheckForLongPress, PICK_UP_DURATION);
                            return false;
                        case 1:
                            break;
                        case 2:
                   int oldHoverPosition = this.mFloatViewListPosition;
                   if (this.mTouchListPosition != -1) {
                       this.mFloatViewListPosition = this.mTouchListPosition;
                            }
                   int deltaY = Math.abs(this.mTouchY - this.mTouchDownY);
                   int deltaX = Math.abs(this.mTouchX - this.mTouchDownX);
                   boolean canPickupFloatView = this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.canPickUpFloatView();
                   if (this.mFloatView == null && deltaX < this.mTouchSlop && deltaY >= this.mTouchSlop && this.mTouchY - this.mTouchDownY < 0 && this.mSelectionPosition != this.mTouchListPosition && canPickupFloatView) {
                       handleClick(this.mTouchListPosition, false);
                            }
                   if (this.mFloatView == null && (deltaX >= this.mTouchSlop || deltaY >= this.mTouchSlop)) {
                       removeCallbacks(this.mCheckForLongPress);
                       if (canPickupFloatView && pickUpView(this.mFloatViewListPosition)) {
                           invalidate();
                                    return true;
                                }
                            } else if (this.mFloatView != null && !this.mDropping) {
                       this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
                       this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
                       if (this.mFloatViewInteractionListener != null) {
                           this.mFloatViewInteractionListener.floatViewMoved();
                                }
                       dragFloatView(oldHoverPosition, this.mFloatViewListPosition);
                                return true;
                            }
                            return false;
                        case 3:
                        case 4:
                        case 5:
                        default:
                            return false;
                        case 6:
                   int pointerIndex = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                   int pointerId = event.getPointerId(pointerIndex);
                   if (pointerId == this.mActivePointerId) {
                            }
                            return false;
                    }
           this.mDownPosition = -1;
           removeCallbacks(this.mCheckForLongPress);
           if (this.mFloatView != null && !this.mDropping) {
               if (this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.floatViewDropped(this.mFloatViewListPosition)) {
                   this.mDropAnimator.start();
                        }
               this.mDragScroller.stopScrolling(true);
               invalidate();
                        return true;
                    }
                    return false;
                }

       public void deleteSelection() {
           if (canDelete()) {
               this.mFloatViewDestroyAnimator.start();
               removeSelection();
                    }
                }

       public boolean canDelete() {
                    return !this.mPickingUpContent;
                }

       private boolean handleContentViewTouch(MotionEvent event) {
           int action = event.getAction() & 255;
           this.mTouchListPosition = getClosestItemPosition();
                    switch (action) {
                        case 0:
                   this.mTouchDownX = this.mTouchX;
                   this.mTouchDownY = this.mTouchY;
                   this.mActivePointerId = event.getPointerId(0);
                   this.mDownPosition = this.mSelectionPosition;
                            break;
                        case 1:
                   this.mDownPosition = -1;
                   removeCallbacks(this.mCheckForLongPress);
                   if (this.mFloatView != null && !this.mDropping) {
                       if (this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.floatViewDropped(this.mFloatViewListPosition)) {
                           this.mDropAnimator.start();
                                }
                       this.mDragScroller.stopScrolling(true);
                       invalidate();
                                return true;
                            }
                            break;
                        case 2:
                   int deltaX = Math.abs(this.mTouchX - this.mTouchDownX);
                   int deltaY = Math.abs(this.mTouchY - this.mTouchDownY);
                   int oldHoverPosition = this.mFloatViewListPosition;
                   if (this.mTouchListPosition != -1) {
                       this.mFloatViewListPosition = this.mTouchListPosition;
                            }
                   boolean canPickupFloatView = this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.canPickUpFloatView();
                   ListAdapter adapter = this.mListView != null ? this.mListView.getAdapter() : null;
                   if (this.mContentView != null && adapter != null && adapter.getCount() > 0 && ((deltaY >= this.mTouchSlop || deltaX >= this.mTouchSlop) && this.mFloatView == null && canPickupFloatView && this.mTouchDownY < this.mContentViewBounds.bottom - (this.mContentViewBounds.height() / 5))) {
                       pickUpContentView();
                                return true;
                            }
                   if (this.mFloatView != null && !this.mDropping) {
                       this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
                       this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
                       if (this.mFloatViewInteractionListener != null) {
                           this.mFloatViewInteractionListener.floatViewMoved();
                                }
                       dragFloatView(oldHoverPosition, this.mFloatViewListPosition);
                                return true;
                            }
                   invalidate();
                            break;
                    }
                    return false;
                }

       public boolean onTouch(View v, MotionEvent event, int xOffset, int yOffset) {
           this.mTouchX = ((int) event.getX()) + v.getLeft() + xOffset;
           this.mTouchY = ((int) event.getY()) + v.getTop() + yOffset;
           if (v == this.mListView) {
               this.isFromContentView = false;
               return handleListViewTouch(event);
                    }
           if (v == this.mContentView) {
                    }
                    return false;
                }

                @Override 
       public boolean onTouch(View v, MotionEvent event) {
           return onTouch(v, event, 0, 0);
                }

       private boolean floatViewIntersectsList() {
                    return !this.mPickingUpContent && this.mFloatViewX <= this.mListView.getRight() && this.mFloatViewY <= this.mListView.getBottom() && this.mFloatViewX + this.mFloatViewWidth >= this.mListView.getLeft() && this.mFloatViewY + this.mFloatViewHeight >= this.mListView.getTop();
                }

       private void dragFloatView(int oldFloatViewPosition, int newFloatViewPosition) {
           if (oldFloatViewPosition > -1 && newFloatViewPosition > -1 && oldFloatViewPosition != newFloatViewPosition && floatViewIntersectsList()) {
               this.mFloatViewListPosition = newFloatViewPosition;
               finishSwap(oldFloatViewPosition, newFloatViewPosition);
                    } else if (floatViewIntersectsList() && oldFloatViewPosition == newFloatViewPosition) {
               View last = this.mListView.getChildAt(this.mListView.getLastVisiblePosition() - this.mListView.getFirstVisiblePosition());
               if (last != null && this.mFloatViewX > last.getRight()) {
                   this.mFloatViewListPosition = this.mListView.getLastVisiblePosition();
                   finishSwap(oldFloatViewPosition, this.mFloatViewListPosition);
                        }
                    } else {
               this.mFloatViewListPosition = oldFloatViewPosition;
                    }
           handleDragScroll();
           invalidate();
                }

       private void triggerSwapAnimation(int to, int from) {
           View oldSwapView = this.mListView.getChildAt(from - this.mListView.getFirstVisiblePosition());
           int swapViewStartLeft = oldSwapView == null ? 0 : oldSwapView.getLeft();
           ViewTreeObserver observer = getViewTreeObserver();
           observer.addOnPreDrawListener(new ViewTreeObserverOnPreDrawListenerC03721(observer, to, swapViewStartLeft));
                }

                
       class ViewTreeObserverOnPreDrawListenerC03721 implements ViewTreeObserver.OnPreDrawListener {
                    final  ViewTreeObserver val$observer;
                    final  int val$swapViewStartLeft;
                    final  int val$to;

           ViewTreeObserverOnPreDrawListenerC03721(ViewTreeObserver viewTreeObserver, int i, int i2) {
                        this.val$observer = viewTreeObserver;
                        this.val$to = i;
                        this.val$swapViewStartLeft = i2;
                    }

                    @Override 
           public boolean onPreDraw() {
               this.val$observer.removeOnPreDrawListener(this);
               View switchView = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.val$to - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
               if (switchView != null) {
                   int swapViewNewLeft = switchView.getLeft();
                   int delta = this.val$swapViewStartLeft - swapViewNewLeft;
                   switchView.setTranslationX(delta);
                   ObjectAnimator animator = ObjectAnimator.ofFloat(switchView, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
                   animator.setDuration(100L);
                   animator.addUpdateListener(new AnonymousClass1());
                   animator.start();
                        }
                        return true;
                    }

                    
           class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
               AnonymousClass1() {
                        }

                        @Override 
               public void onAnimationUpdate(ValueAnimator animation) {
                   DragSortWidget.access$000(DragSortWidget.this).invalidate();
                        }
                    }
                }

       private void finishDrop() {
           this.mDropping = false;
           if (this.mFloatViewInteractionListener != null) {
               this.mFloatViewInteractionListener.floatViewLanded(this.mFloatViewListPosition);
                    }
           destroyFloatView();
           adjustItems();
           invalidate();
                }

       private void finishRemoveSelection() {
           if (this.mRemovePosition != -1) {
               if (this.mRemoveListener != null) {
                   this.mRemoveListener.remove(this.mRemovePosition);
                        }
               this.mRemovePosition = -1;
               this.mSelectionPosition = getNextPositionForSelection(this.mSelectionPosition);
               this.mFocusedPosition = this.mSelectionPosition;
               if (this.mSelectionChangedListener != null) {
                   this.mSelectionChangedListener.onSelectionChanged(this.mSelectionPosition, false);
                        }
                    }
                }

       private int getNextPositionForSelection(int mSelectionPosition) {
                    return mSelectionPosition < this.mAdapterWrapper.getCount() ? mSelectionPosition : this.mAdapterWrapper.getCount() - 1;
                }

       public void removeSelection() {
           this.mRemoveAnimator.start();
                }

       private void finishSwap(int oldFloatViewPosition, int newFloatViewPosition) {
           if (this.mDragListener != null) {
               if (oldFloatViewPosition < newFloatViewPosition) {
                            for (int i = oldFloatViewPosition; i < newFloatViewPosition; i++) {
                       if (i >= 0) {
                           this.mDragListener.drag(i, i + 1);
                           triggerSwapAnimation(i, i + 1);
                                }
                            }
                        } else {
                            for (int i2 = oldFloatViewPosition; i2 > newFloatViewPosition; i2--) {
                       if (i2 >= 1) {
                           this.mDragListener.drag(i2, i2 - 1);
                           triggerSwapAnimation(i2, i2 - 1);
                                }
                            }
                        }
                    }
           if (newFloatViewPosition != -1) {
               this.mSelectionPosition = newFloatViewPosition;
               this.mFocusedPosition = newFloatViewPosition;
                    }
           adjustItems();
                }

       private void adjustItems() {
                    for (int i = 0; i < this.mListView.getChildCount(); i++) {
               adjustItem(this.mListView.getFirstVisiblePosition() + i);
                    }
                }

       private void adjustItem(int position) {
           View childView = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
           if (childView != null) {
               if (this.mFloatView != null && position == this.mFloatViewListPosition) {
                   childView.setVisibility(4);
                   childView.setActivated(true);
                        } else if (position == this.mSelectionPosition) {
                   childView.setActivated(true);
                   childView.setVisibility(0);
                        } else {
                   childView.setActivated(false);
                   childView.setVisibility(0);
                        }
               childView.setAlpha(position == this.mFocusedPosition ? 1.0f : 0.3f);
               childView.invalidate();
                    }
                }

       private int getClosestItemPosition() {
                    for (int i = 0; i < this.mListView.getChildCount(); i++) {
               if (this.mTouchX - this.mListView.getLeft() < this.mListView.getChildAt(i).getRight()) {
                   return this.mListView.getFirstVisiblePosition() + i;
                        }
                    }
           return -1;
                }

       private void handleDragScroll() {
           boolean intersectsList = floatViewIntersectsList();
                    if (this.mTouchX - this.mListView.getLeft() > this.mListView.getWidth() * 2 * SCROLL_THRESHOLD && intersectsList) {
               this.mDragScroller.startScrolling(1);
                    } else if (this.mTouchX - this.mListView.getLeft() < this.mListView.getWidth() * SCROLL_THRESHOLD && intersectsList) {
               this.mDragScroller.startScrolling(0);
                    } else {
               this.mDragScroller.stopScrolling(true);
                    }
                }

                @Override 
       protected void dispatchDraw(@NonNull Canvas canvas) {
                    View selection;
           super.dispatchDraw(canvas);
           if (this.mFloatView != null) {
               canvas.save();
               canvas.translate(this.mFloatViewX, this.mFloatViewY);
               canvas.clipRect(0, 0, this.mFloatViewWidth, this.mFloatViewHeight);
               canvas.saveLayerAlpha(0.0f, 0.0f, this.mFloatViewWidth, this.mFloatViewHeight, (int) this.mFloatViewAlpha, 31);
               this.mFloatView.layout(0, 0, this.mFloatViewWidth, this.mFloatViewHeight);
               this.mFloatView.draw(canvas);
               canvas.restore();
               canvas.restore();
               this.mFloatViewActivatedDrawable.setBounds(this.mFloatViewX, this.mFloatViewY, this.mFloatViewX + this.mFloatViewWidth, this.mFloatViewY + this.mFloatViewHeight);
               this.mFloatViewActivatedDrawable.setAlpha((int) this.mFloatViewAlpha);
               this.mFloatViewActivatedDrawable.draw(canvas);
               return;
                    }
           if (this.mSelectionPosition != -1 && (selection = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition())) != null && !this.mRemoving) {
               this.mActivatedDrawable.setBounds(selection.getLeft() + this.mListView.getLeft(), selection.getTop() + this.mListView.getTop(), selection.getRight() + this.mListView.getLeft(), selection.getBottom() + this.mListView.getTop());
               this.mActivatedDrawable.setAlpha(255);
               this.mActivatedDrawable.draw(canvas);
                    }
                }

       private void createFloatView(int position) {
           View v = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
           if (v != null && this.mTouchDownX - this.mListView.getLeft() >= v.getLeft() && this.mTouchDownY - this.mListView.getTop() >= v.getTop() && this.mTouchDownX - this.mListView.getLeft() <= v.getRight() && this.mTouchDownY - this.mListView.getTop() <= v.getBottom()) {
               v.setDrawingCacheQuality(1048576);
               v.setDrawingCacheEnabled(true);
               this.mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
               v.setDrawingCacheEnabled(false);
               if (this.mFloatView == null) {
                   this.mFloatView = new ImageView(this.mListView.getContext());
                        }
               this.mFloatView.setImageBitmap(this.mFloatBitmap);
               this.mFloatView.setLayoutParams(new ViewGroup.LayoutParams(v.getWidth(), v.getHeight()));
               this.mXOffsetFromTouch = this.mTouchDownX - (v.getLeft() + this.mListView.getLeft());
               this.mYOffsetFromTouch = this.mTouchDownY - (v.getTop() + this.mListView.getTop());
               this.mFloatViewWidth = v.getWidth();
               this.mFloatViewHeight = v.getHeight();
               this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
               this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
               this.mFloatViewAlpha = 255.0f;
               adjustItem(position);
                    }
                }

       private void createFloatViewFromContentView() {
           this.mFloatViewListPosition = this.mSelectionPosition;
           View v = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition());
           this.mContentViewPickUpAnimator.start();
           this.mXOffsetFromTouch = this.mTouchX - this.mContentViewBounds.left;
           this.mYOffsetFromTouch = this.mTouchY - this.mContentViewBounds.top;
           this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
           this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
           this.mFloatViewWidth = this.mContentViewBounds.width();
           this.mFloatViewHeight = this.mContentViewBounds.height();
           this.mFloatViewAlpha = 255.0f;
           if (v == null && this.mSelectionPosition >= 0 && this.mSelectionPosition < this.mListView.getAdapter().getCount()) {
               v = this.mListView.getAdapter().getView(this.mSelectionPosition, null, null);
                    }
           if (v instanceof DragSortListItem) {
               this.mFloatBitmap = ((DragSortListItem) v).getBitmap();
                    }
           if (this.mFloatView == null) {
               this.mFloatView = new ImageView(this.mListView.getContext());
                    }
           this.mFloatView.setImageBitmap(this.mFloatBitmap);
           this.mFloatView.setLayoutParams(new ViewGroup.LayoutParams(this.mFloatViewWidth, this.mFloatViewHeight));
           adjustItem(this.mSelectionPosition);
                }

       private void destroyFloatView() {
           if (this.mFloatView != null) {
               this.mFloatView.setImageDrawable(null);
               this.mFloatBitmap = null;
               this.mFloatView = null;
                    }
                }

       private boolean pickUpView(int position) {
           if (position != this.mSelectionPosition || position == -1) {
                        return false;
                    }
           createFloatView(position);
           if (this.mFloatViewInteractionListener != null) {
               this.mFloatViewInteractionListener.floatViewPickedUp(position);
                    }
           invalidate();
           return true;
                }

       private boolean pickUpContentView() {
           if (this.mSelectionPosition == -1) {
               int position = this.mCurrentlyPlayingProvider.getCurrentlyPlayingPosition();
               handleClick(position, false);
               this.mFloatViewListPosition = position;
               this.mSelectionPosition = position;
                    }
           createFloatViewFromContentView();
           if (this.mFloatViewInteractionListener != null) {
               this.mFloatViewInteractionListener.floatViewPickedUp(this.mSelectionPosition);
                    }
           invalidate();
           return true;
                }

       public void setCurrentlyPlayingProvider(CurrentlyPlayingProvider cpp) {
           this.mCurrentlyPlayingProvider = cpp;
                }

       public void releaseReferences() {
           setAdapter(null);
           this.mCurrentlyPlayingProvider = null;
           this.mContentView = null;
                }

       private boolean handleClick(int position, boolean listViewClick) {
                    View oldChild;
           View view = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
           if (view == null) {
                        return false;
                    }
           if (this.mSelectionPosition == position) {
               view.setActivated(false);
               this.mSelectionPosition = -1;
               this.mFocusedPosition = -1;
                    } else {
               view.setActivated(true);
               if (this.mSelectionPosition != -1 && (oldChild = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition())) != null) {
                   oldChild.setActivated(false);
                        }
               this.mSelectionPosition = position;
               this.mFocusedPosition = position;
                    }
           if (this.mSelectionChangedListener != null) {
               this.mSelectionChangedListener.onSelectionChanged(this.mSelectionPosition, listViewClick);
                    }
           adjustItems();
           invalidate();
                    return true;
                }

       public boolean hasFloatView() {
                    return this.mFloatView != null;
                }

                @Override 
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           if (this.mTouchX - this.mListView.getLeft() >= 0 && this.mTouchX - this.mListView.getLeft() < this.mListView.getWidth() && this.mTouchY - this.mListView.getTop() >= 0 && this.mTouchY - this.mListView.getTop() < this.mListView.getHeight()) {
               handleClick(position, true);
                    }
                }

                @Nullable
       public Rect getFloatViewBounds(Rect r) {
           if (this.mFloatView == null) {
                        return null;
                    }
           r.set(this.mFloatViewX, this.mFloatViewY, this.mFloatViewX + this.mFloatViewWidth, this.mFloatViewY + this.mFloatViewHeight);
           return r;
                }

       public int[] getFloatViewPosition(int[] intersectLoc) {
           if (this.mFloatView == null) {
                        return null;
                    }
           intersectLoc[0] = this.mFloatViewX;
           intersectLoc[1] = this.mFloatViewY;
           return intersectLoc;
                }

                @Override 
       public void onScroll() {
           invalidate();
           removeCallbacks(this.mCheckForLongPress);
                }

       public void forceListLayout() {
           if (this.mListView != null) {
               this.mListView.forceLayout();
               this.mListView.invalidate();
                    }
                }

       public int getSelection() {
           return this.mSelectionPosition;
                }

       public class DragScroller implements Runnable {
                    public static final int LEFT = 0;
                    public static final int RIGHT = 1;

                    
                    private float f100dt;

                    
                    private int f101dx;
                    private boolean mAbort;
                    private long mCurrTime;
                    private long mPrevTime;
                    private float mScrollSpeed = 0.0f;
                    private boolean mScrolling = false;
                    private int scrollDir;
                    private long tStart;

           public DragScroller() {
                    }

           public void startScrolling(int dir) {
               if (!this.mScrolling) {
                   this.mAbort = false;
                   this.mScrolling = true;
                   this.tStart = SystemClock.uptimeMillis();
                   this.mPrevTime = this.tStart;
                   this.scrollDir = dir;
                   DragSortWidget.this.post(this);
                        }
                    }

           public void stopScrolling(boolean now) {
               if (now) {
                   DragSortWidget.access$000(DragSortWidget.this).removeCallbacks(this);
                   this.mScrolling = false;
                        } else {
                   this.mAbort = true;
                        }
                    }

                    @Override 
           public void run() {
               if (this.mAbort) {
                   this.mScrolling = false;
                   return;
                        }
               int oldHoverPosition = DragSortWidget.access$100(DragSortWidget.this);
               int first = DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition();
               int last = DragSortWidget.access$000(DragSortWidget.this).getLastVisiblePosition();
               int count = DragSortWidget.access$000(DragSortWidget.this).getCount();
               int listWidth = DragSortWidget.access$000(DragSortWidget.this).getWidth();
               if (this.scrollDir == 0) {
                   View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(0);
                   if (v == null) {
                       this.mScrolling = false;
                       return;
                            }
                   if (first == 0 && v.getLeft() == 0) {
                       this.mScrolling = false;
                       return;
                            }
                   int denominator = DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getLeft();
                   if (denominator < 0) {
                       denominator = 0;
                            }
                   this.mScrollSpeed = DragSortWidget.access$300(DragSortWidget.this) / (denominator + 1);
                        } else {
                   View v2 = DragSortWidget.access$000(DragSortWidget.this).getChildAt(last - first);
                   if (v2 == null) {
                       this.mScrolling = false;
                       return;
                            }
                   if (last == count - 1 && v2.getRight() <= listWidth) {
                       this.mScrolling = false;
                       return;
                            }
                   int denominator2 = (listWidth - DragSortWidget.access$200(DragSortWidget.this)) + DragSortWidget.access$000(DragSortWidget.this).getLeft();
                   if (denominator2 < 0) {
                       denominator2 = 0;
                            }
                   this.mScrollSpeed = (-DragSortWidget.access$300(DragSortWidget.this)) / (denominator2 + 1);
                        }
               this.mCurrTime = SystemClock.uptimeMillis();
               this.f100dt = this.mCurrTime - this.mPrevTime;
               this.f101dx = Math.round(this.mScrollSpeed * this.f100dt);
               DragSortWidget.access$000(DragSortWidget.this).scroll(this.f101dx, this.f101dx);
               this.mPrevTime = this.mCurrTime;
               DragSortWidget.access$102(DragSortWidget.this, DragSortWidget.access$400(DragSortWidget.this));
               if (DragSortWidget.access$100(DragSortWidget.this) == -1) {
                   DragSortWidget.access$102(DragSortWidget.this, oldHoverPosition);
                        }
               DragSortWidget.access$500(DragSortWidget.this, oldHoverPosition, DragSortWidget.access$100(DragSortWidget.this));
               DragSortWidget.this.post(this);
                    }
                }

       private class DropAnimator extends SmoothAnimator {
                    private float mDeltaX;
                    private float mDeltaY;
                    private int mTargetPosition;
                    private int mTargetX;
                    private int mTargetY;

           public DropAnimator(float smoothness, int duration) {
               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override 
           public void onStart() {
               this.mTargetPosition = DragSortWidget.access$100(DragSortWidget.this);
               View hoverItem = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.mTargetPosition - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
               DragSortWidget.this.mDropping = true;
               if (hoverItem != null) {
                   this.mTargetY = hoverItem.getTop() + DragSortWidget.access$000(DragSortWidget.this).getTop();
                   this.mTargetX = hoverItem.getLeft() + DragSortWidget.access$000(DragSortWidget.this).getLeft();
                        } else {
                   DragSortWidget.this.mDropping = false;
                   DragSortWidget.access$600(DragSortWidget.this).start();
                   cancel();
                        }
               this.mDeltaX = DragSortWidget.access$700(DragSortWidget.this) - this.mTargetX;
               this.mDeltaY = DragSortWidget.access$800(DragSortWidget.this) - this.mTargetY;
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               int newTargetX = this.mTargetX;
               int newTargetY = this.mTargetY;
               View hoverItem = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.mTargetPosition - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
               if (hoverItem != null) {
                   newTargetY = hoverItem.getTop() + DragSortWidget.access$000(DragSortWidget.this).getTop();
                   newTargetX = hoverItem.getLeft() + DragSortWidget.access$000(DragSortWidget.this).getLeft();
                        }
               this.mTargetX = newTargetX;
               this.mTargetY = newTargetY;
               float f = 1.0f - smoothFrac;
               DragSortWidget.access$702(DragSortWidget.this, this.mTargetX + ((int) (this.mDeltaX * f)));
               DragSortWidget.access$802(DragSortWidget.this, this.mTargetY + ((int) (this.mDeltaY * f)));
               DragSortWidget.this.invalidate();
                    }

                    @Override 
           public void onStop() {
               DragSortWidget.access$900(DragSortWidget.this);
                    }
                }

       private class FloatViewDestroyAnimator extends SmoothAnimator {
           public FloatViewDestroyAnimator(float smoothness, int duration) {
               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               DragSortWidget.access$1002(DragSortWidget.this, ((1.0f - smoothFrac) * 255.0f) - 50.0f);
               DragSortWidget.access$1002(DragSortWidget.this, DragSortWidget.access$1000(DragSortWidget.this) >= 0.0f ? DragSortWidget.access$1000(DragSortWidget.this) : 0.0f);
               DragSortWidget.this.invalidate();
                    }

                    @Override 
           public void onStop() {
               DragSortWidget.access$1002(DragSortWidget.this, 0.0f);
               DragSortWidget.access$102(DragSortWidget.this, -1);
               DragSortWidget.access$1100(DragSortWidget.this);
               DragSortWidget.access$1200(DragSortWidget.this);
               DragSortWidget.this.invalidate();
                    }
                }

       private class ContentViewPickUpAnimator extends SmoothAnimator {
                    private int mFloatViewStartHeight;
                    private int mFloatViewStartWidth;
                    private int mHeightDelta;
                    private int mWidthDelta;
                    private float mXOffsetRatio;
                    private float mYOffsetRatio;

           public ContentViewPickUpAnimator(float smoothness, int duration) {
               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override 
           public void onStart() {
               this.mFloatViewStartWidth = DragSortWidget.access$1300(DragSortWidget.this).width();
               this.mFloatViewStartHeight = DragSortWidget.access$1300(DragSortWidget.this).height();
               DragSortWidget.access$1402(DragSortWidget.this, this.mFloatViewStartWidth);
               DragSortWidget.access$1502(DragSortWidget.this, this.mFloatViewStartHeight);
               DragSortWidget.access$1602(DragSortWidget.this, DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$1300(DragSortWidget.this).left);
               DragSortWidget.access$1702(DragSortWidget.this, DragSortWidget.access$1800(DragSortWidget.this) - DragSortWidget.access$1300(DragSortWidget.this).top);
               if (DragSortWidget.access$1300(DragSortWidget.this).width() != 0) {
                   this.mXOffsetRatio = DragSortWidget.access$1600(DragSortWidget.this) / (DragSortWidget.access$1300(DragSortWidget.this).width() * 1.0f);
                        }
               if (DragSortWidget.access$1300(DragSortWidget.this).height() != 0) {
                   this.mYOffsetRatio = DragSortWidget.access$1700(DragSortWidget.this) / (DragSortWidget.access$1300(DragSortWidget.this).height() * 1.0f);
                        }
               this.mWidthDelta = DragSortWidget.access$1400(DragSortWidget.this) - DragSortWidget.access$1900(DragSortWidget.this);
               this.mHeightDelta = DragSortWidget.access$1500(DragSortWidget.this) - DragSortWidget.access$1900(DragSortWidget.this);
               DragSortWidget.access$2002(DragSortWidget.this, true);
               DragSortWidget.this.invalidate();
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               DragSortWidget.access$1402(DragSortWidget.this, (int) (this.mFloatViewStartWidth - (this.mWidthDelta * smoothFrac)));
               DragSortWidget.access$1502(DragSortWidget.this, (int) (this.mFloatViewStartHeight - (this.mHeightDelta * smoothFrac)));
               int oldXOffset = DragSortWidget.access$1600(DragSortWidget.this);
               int oldYOffset = DragSortWidget.access$1700(DragSortWidget.this);
               DragSortWidget.access$1602(DragSortWidget.this, (int) (DragSortWidget.access$1400(DragSortWidget.this) * this.mXOffsetRatio));
               DragSortWidget.access$1702(DragSortWidget.this, (int) (DragSortWidget.access$1500(DragSortWidget.this) * this.mYOffsetRatio));
               DragSortWidget.access$702(DragSortWidget.this, (DragSortWidget.access$700(DragSortWidget.this) + oldXOffset) - DragSortWidget.access$1600(DragSortWidget.this));
               DragSortWidget.access$802(DragSortWidget.this, (DragSortWidget.access$800(DragSortWidget.this) + oldYOffset) - DragSortWidget.access$1700(DragSortWidget.this));
               DragSortWidget.this.invalidate();
                    }

                    @Override 
           public void onStop() {
               DragSortWidget.access$2002(DragSortWidget.this, false);
               DragSortWidget.this.invalidate();
                    }
                }

       private class RemoveAnimator extends SmoothAnimator {
                    private int mWidth;

           int access$2700() {
               return this.mWidth;
                    }

           public RemoveAnimator(float smoothness, int duration) {
               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override 
           public void onStart() {
               DragSortWidget.access$2102(DragSortWidget.this, DragSortWidget.access$2200(DragSortWidget.this));
               View toMeasure = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
               if (toMeasure != null) {
                   this.mWidth = toMeasure.getWidth();
                   toMeasure.animate().alpha(0.0f).setDuration((long) this.mDurationF).start();
                   ((DragSortListItem) toMeasure).hasTransientState = true;
                   DragSortWidget.this.mRemoving = true;
                   return;
                        }
               DragSortWidget.access$2300(DragSortWidget.this);
               cancel();
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
               if (v != null) {
                   float f = 1.0f - smoothFrac;
                   this.mWidth = Math.round(this.mWidth * f);
                  ViewGroup.LayoutParams lp = v.getLayoutParams();
                  lp.width = this.mWidth;
                  v.setLayoutParams(lp);
                  DragSortWidget.access$000(DragSortWidget.this).forceLayout();
                        } else {
                  DragSortWidget.access$2300(DragSortWidget.this);
                  DragSortWidget.this.mRemoving = false;
                  cancel();
                        }
              DragSortWidget.access$000(DragSortWidget.this).invalidate();
                    }

                    @Override 
          public void onStop() {
              View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
              if (v != null) {
                  v.setVisibility(8);
                  v.animate().cancel();
                  v.setAlpha(1.0f);
                  ((DragSortListItem) v).hasTransientState = false;
                        }
              DragSortWidget.access$2300(DragSortWidget.this);
              DragSortWidget.this.mRemoving = false;
                    }
                }

      private class DuplicateAnimator extends SmoothAnimator {
                    private View mView;

          public DuplicateAnimator(View view) {
              super(DragSortWidget.this, DragSortWidget.SMOOTHNESS, 200);
              this.mView = view;
                    }

                    @Override 
          public void onStart() {
              if (this.mView != null) {
                  this.mView.setAlpha(1.0f);
                        } else {
                  cancel();
                        }
                    }

                    @Override 
          public void onUpdate(float frac, float smoothFrac) {
              if (this.mView != null) {
                  this.mView.setAlpha((0.7f * (1.0f - smoothFrac)) + 0.3f);
                        } else {
                  cancel();
                        }
              DragSortWidget.access$000(DragSortWidget.this).invalidate();
                    }

                    @Override 
          public void onStop() {
              if (this.mView != null) {
                  this.mView.setAlpha(0.3f);
                        }
                    }
                }

      public void setAdapter(ListAdapter adapter) {
          if (adapter != null) {
              if (adapter instanceof FloatViewInteractionListener) {
                  setFloatViewInteractionListener((FloatViewInteractionListener) adapter);
                        }
              if (adapter instanceof DragListener) {
                  setDragListener((DragListener) adapter);
                        }
              if (adapter instanceof RemoveListener) {
                  setRemoveListener((RemoveListener) adapter);
                        }
              if (adapter instanceof SelectionChangedListener) {
                  setSelectionChangedListener((SelectionChangedListener) adapter);
                        }
              if (this.mAdapterWrapper == null) {
                  this.mAdapterWrapper = new DragSortAdapterWrapper(getContext());
                        }
              this.mAdapterWrapper.setAdapter(adapter);
              this.mListView.setAdapter((ListAdapter) this.mAdapterWrapper);
              return;
                    }
          this.mListView.setAdapter((ListAdapter) null);
          if (this.mAdapterWrapper != null) {
              this.mAdapterWrapper.setAdapter(null);
                    }
                }

      public void setFloatViewInteractionListener(FloatViewInteractionListener l) {
          this.mFloatViewInteractionListener = l;
                }

      public void setRemoveListener(RemoveListener l) {
          this.mRemoveListener = l;
                }

      public void setDragListener(DragListener l) {
          this.mDragListener = l;
                }

      public void setSelectionChangedListener(SelectionChangedListener l) {
          this.mSelectionChangedListener = l;
                }

      public class DragSortListItem extends ViewGroup {
                    public boolean hasTransientState;

          public DragSortListItem(Context context) {
              super(context);
              this.hasTransientState = false;
              setLayoutParams(new HorizontalListView.LayoutParams(-2, -1));
                    }

          public Bitmap getBitmap() {
                        BitmapDrawable bd;
              View v = getChildAt(0);
              if (v == null || (bd = (BitmapDrawable) v.getBackground()) == null) {
                            return null;
                        }
              return bd.getBitmap();
                    }

                    @Override 
          public void onLayout(boolean changed, int l, int t, int r, int b) {
              if (DragSortWidget.access$2400(DragSortWidget.this) != null) {
                  DragSortWidget.access$2400(DragSortWidget.this).setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        }
              View child = getChildAt(0);
              if (child != null) {
                  child.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        }
                    }

                    @Override 
          protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
              int height = View.MeasureSpec.getSize(heightMeasureSpec);
              int width = View.MeasureSpec.getSize(widthMeasureSpec);
              int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
              View child = getChildAt(0);
              if (child == null) {
                  setMeasuredDimension(0, 0);
                  return;
                        }
              if (child.isLayoutRequested()) {
                  measureChild(child, View.MeasureSpec.makeMeasureSpec(0, 0), heightMeasureSpec);
                        }
              if (widthMode == 0) {
                  ViewGroup.LayoutParams lp = getLayoutParams();
                  if (lp.width > 0 || this.hasTransientState) {
                      width = lp.width;
                            } else {
                      width = child.getMeasuredWidth();
                            }
                        }
              setMeasuredDimension(width, height);
                    }
                }

      public class DragSortAdapterWrapper extends BaseAdapter implements ListAdapter {
                    private ListAdapter mAdapter;
                    private Context mContext;
                    private DataSetObserver mObserver = new C03731();

                    
          class C03731 extends DataSetObserver {
              C03731() {
                        }

                        @Override 
              public void onChanged() {
                  DragSortAdapterWrapper.this.notifyDataSetChanged();
                  DragSortWidget.access$1200(DragSortWidget.this);
                        }
                    }

          public DragSortAdapterWrapper(Context context) {
              this.mContext = context;
                    }

          public void setAdapter(ListAdapter adapter) {
              this.mAdapter = adapter;
              if (adapter != null) {
                  adapter.registerDataSetObserver(this.mObserver);
                        }
                    }

                    @Override 
          public int getCount() {
              return this.mAdapter.getCount();
                    }

                    @Override 
          public Object getItem(int position) {
              return this.mAdapter.getItem(position);
                    }

                    @Override 
          public long getItemId(int position) {
              return this.mAdapter.getItemId(position);
                    }

                    @Override 
          public boolean isEnabled(int position) {
              return this.mAdapter.isEnabled(position);
                    }

                    @Override 
          public View getView(int position, View convertView, ViewGroup parent) {
                        View child;
                        DragSortListItem item;
              if (convertView != null) {
                  item = (DragSortListItem) convertView;
                  View oldChild = item.getChildAt(position);
                  child = this.mAdapter.getView(position, oldChild, parent);
                        } else {
                  child = this.mAdapter.getView(position, null, parent);
                  item = DragSortWidget.this.new DragSortListItem(this.mContext);
                        }
              item.removeAllViews();
              item.addView(child);
              item.setActivated(position == DragSortWidget.access$2200(DragSortWidget.this));
              item.setAlpha(position == DragSortWidget.access$2500(DragSortWidget.this) ? 1.0f : 0.3f);
              ViewGroup.LayoutParams lp = item.getLayoutParams();
              if (DragSortWidget.this.mRemoving && position == DragSortWidget.access$2100(DragSortWidget.this)) {
                  lp.width = DragSortWidget.access$2600(DragSortWidget.this).access$2700();
                        } else {
                  lp.width = 0;
                        }
              item.setLayoutParams(lp);
              if (DragSortWidget.access$2800(DragSortWidget.this) != null && position == DragSortWidget.access$100(DragSortWidget.this)) {
                  item.setVisibility(4);
                        } else {
                  item.setVisibility(0);
                        }
              if (((ShouldAnimateInProvider) this.mAdapter).shouldAnimateIn(position)) {
                  DragSortWidget.this.new DuplicateAnimator(item).start();
                        }
              return item;
                    }
                }

      public class CheckForLongPress implements Runnable {
          public CheckForLongPress() {
                    }

                    @Override 
          public void run() {
              if (DragSortWidget.access$2900(DragSortWidget.this) == DragSortWidget.access$3000(DragSortWidget.this)) {
                  if (DragSortWidget.access$2900(DragSortWidget.this) != DragSortWidget.access$2200(DragSortWidget.this)) {
                      DragSortWidget.access$3100(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this), false);
                            }
                  DragSortWidget.access$102(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this));
                  if (DragSortWidget.access$3200(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this))) {
                      DragSortWidget.access$702(DragSortWidget.this, DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$1600(DragSortWidget.this));
                      DragSortWidget.access$802(DragSortWidget.this, DragSortWidget.access$1800(DragSortWidget.this) - DragSortWidget.access$1700(DragSortWidget.this));
                            }
                        }
                    }
                }

      public void setContentView(View view, Rect bounds) {
          this.mContentView = view;
          this.mContentViewBounds = bounds;
          view.setOnTouchListener(this);
                }

      public void cleanUp() {
          this.mSelectionPosition = -1;
          this.mFocusedPosition = -1;
          this.mRemovePosition = -1;
          this.mDropping = false;
          this.mRemoving = false;
          destroyFloatView();
                }

      public boolean floatViewIsFromContentView() {
          return this.isFromContentView;
                }

      public boolean isVisible(int position) {
                    return this.mListView != null && position >= this.mListView.getFirstVisiblePosition() && position <= this.mListView.getFirstVisiblePosition() + this.mListView.getChildCount();
                }
            }
