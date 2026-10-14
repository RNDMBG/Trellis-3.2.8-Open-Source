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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 37 */    public abstract class DragSortWidget extends RelativeLayout implements View.OnTouchListener, AdapterView.OnItemClickListener, HorizontalListView.OnScrollListener {
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

/* 38 */        static /* synthetic */ HorizontalListView access$000(DragSortWidget x0) {
/* 39 */            return x0.mListView;
                }

/* 38 */        static /* synthetic */ int access$100(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewListPosition;
                }

/* 38 */        static /* synthetic */ float access$1000(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewAlpha;
                }

/* 38 */        static /* synthetic */ float access$1002(DragSortWidget x0, float x1) {
/* 39 */            x0.mFloatViewAlpha = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$102(DragSortWidget x0, int x1) {
/* 39 */            x0.mFloatViewListPosition = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ void access$1100(DragSortWidget x0) {
/* 39 */            x0.destroyFloatView();
                }

/* 38 */        static /* synthetic */ void access$1200(DragSortWidget x0) {
/* 39 */            x0.adjustItems();
                }

/* 38 */        static /* synthetic */ Rect access$1300(DragSortWidget x0) {
/* 39 */            return x0.mContentViewBounds;
                }

/* 38 */        static /* synthetic */ int access$1400(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewWidth;
                }

/* 38 */        static /* synthetic */ int access$1402(DragSortWidget x0, int x1) {
/* 39 */            x0.mFloatViewWidth = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$1500(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewHeight;
                }

/* 38 */        static /* synthetic */ int access$1502(DragSortWidget x0, int x1) {
/* 39 */            x0.mFloatViewHeight = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$1600(DragSortWidget x0) {
/* 39 */            return x0.mXOffsetFromTouch;
                }

/* 38 */        static /* synthetic */ int access$1602(DragSortWidget x0, int x1) {
/* 39 */            x0.mXOffsetFromTouch = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$1700(DragSortWidget x0) {
/* 39 */            return x0.mYOffsetFromTouch;
                }

/* 38 */        static /* synthetic */ int access$1702(DragSortWidget x0, int x1) {
/* 39 */            x0.mYOffsetFromTouch = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$1800(DragSortWidget x0) {
/* 39 */            return x0.mTouchY;
                }

/* 38 */        static /* synthetic */ int access$1900(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewDefaultSize;
                }

/* 38 */        static /* synthetic */ int access$200(DragSortWidget x0) {
/* 39 */            return x0.mTouchX;
                }

/* 38 */        static /* synthetic */ boolean access$2002(DragSortWidget x0, boolean x1) {
/* 39 */            x0.mPickingUpContent = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$2100(DragSortWidget x0) {
/* 39 */            return x0.mRemovePosition;
                }

/* 38 */        static /* synthetic */ int access$2102(DragSortWidget x0, int x1) {
/* 39 */            x0.mRemovePosition = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$2200(DragSortWidget x0) {
/* 39 */            return x0.mSelectionPosition;
                }

/* 38 */        static /* synthetic */ void access$2300(DragSortWidget x0) {
/* 39 */            x0.finishRemoveSelection();
                }

/* 38 */        static /* synthetic */ Drawable access$2400(DragSortWidget x0) {
/* 39 */            return x0.mActivatedDrawable;
                }

/* 38 */        static /* synthetic */ int access$2500(DragSortWidget x0) {
/* 39 */            return x0.mFocusedPosition;
                }

/* 38 */        static /* synthetic */ RemoveAnimator access$2600(DragSortWidget x0) {
/* 39 */            return x0.mRemoveAnimator;
                }

/* 38 */        static /* synthetic */ ImageView access$2800(DragSortWidget x0) {
/* 39 */            return x0.mFloatView;
                }

/* 38 */        static /* synthetic */ int access$2900(DragSortWidget x0) {
/* 39 */            return x0.mTouchListPosition;
                }

/* 38 */        static /* synthetic */ float access$300(DragSortWidget x0) {
/* 39 */            return x0.mMaxScrollSpeed;
                }

/* 38 */        static /* synthetic */ int access$3000(DragSortWidget x0) {
/* 39 */            return x0.mDownPosition;
                }

/* 38 */        static /* synthetic */ boolean access$3100(DragSortWidget x0, int x1, boolean x2) {
/* 39 */            return x0.handleClick(x1, x2);
                }

/* 38 */        static /* synthetic */ boolean access$3200(DragSortWidget x0, int x1) {
/* 39 */            return x0.pickUpView(x1);
                }

/* 38 */        static /* synthetic */ int access$400(DragSortWidget x0) {
/* 39 */            return x0.getClosestItemPosition();
                }

/* 38 */        static /* synthetic */ void access$500(DragSortWidget x0, int x1, int x2) {
/* 39 */            x0.dragFloatView(x1, x2);
                }

/* 38 */        static /* synthetic */ FloatViewDestroyAnimator access$600(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewDestroyAnimator;
                }

/* 38 */        static /* synthetic */ int access$700(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewX;
                }

/* 38 */        static /* synthetic */ int access$702(DragSortWidget x0, int x1) {
/* 39 */            x0.mFloatViewX = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$800(DragSortWidget x0) {
/* 39 */            return x0.mFloatViewY;
                }

/* 38 */        static /* synthetic */ int access$802(DragSortWidget x0, int x1) {
/* 39 */            x0.mFloatViewY = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ void access$900(DragSortWidget x0) {
/* 39 */            x0.finishDrop();
                }

/* 113 */       public DragSortWidget(Context context) {
/* 114 */           this(context, null);
                }

/* 117 */       public DragSortWidget(Context context, AttributeSet attrs) {
/* 118 */           this(context, attrs, 0);
                }

/* 121 */       public DragSortWidget(Context context, AttributeSet attrs, int defStyle) {
/* 122 */           super(context, attrs, defStyle);
/* 60 */            this.mDragScroller = new DragScroller();
/* 61 */            this.mDropAnimator = new DropAnimator(SMOOTHNESS, 150);
/* 62 */            this.mRemoveAnimator = new RemoveAnimator(SMOOTHNESS, 100);
/* 63 */            this.mFloatViewDestroyAnimator = new FloatViewDestroyAnimator(SMOOTHNESS, 100);
/* 65 */            this.mContentViewPickUpAnimator = new ContentViewPickUpAnimator(SMOOTHNESS, 130);
/* 70 */            this.mFloatView = null;
/* 94 */            this.mFloatViewListPosition = -1;
/* 95 */            this.mSelectionPosition = -1;
/* 102 */           this.mFloatViewAlpha = 255.0f;
/* 104 */           this.mDropping = false;
/* 105 */           this.mRemoving = false;
/* 109 */           this.mPickingUpContent = false;
/* 110 */           this.isFromContentView = false;
/* 123 */           TypedArray a = getStylingArray(context, attrs, defStyle);
/* 124 */           this.mActivatedDrawable = getActiveDrawable(a);
/* 125 */           this.mFloatViewActivatedDrawable = getFloatViewActivatedDrawable(a);
/* 126 */           this.mFloatViewDefaultSize = getFloatViewDefaultSize(a);
/* 127 */           this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
/* 128 */           a.recycle();
                }

                @Override // android.view.View
/* 140 */       public void onFinishInflate() {
/* 141 */           this.mListView = (HorizontalListView) findViewById(R.id.list);
/* 142 */           this.mListView.setOnTouchListener(this);
/* 143 */           this.mListView.setOnItemClickListener(this);
/* 144 */           this.mListView.setFingerScrollEnabled(true);
/* 145 */           this.mListView.setOnScrollListener(this);
/* 146 */           this.mCheckForLongPress = new CheckForLongPress();
/* 147 */           this.mMaxScrollSpeed = getResources().getDisplayMetrics().xdpi * MAX_SCROLL_SPEED_DENSITY_MULTIPLIER;
                }

/* 150 */       public void setFirstItem(int position) {
/* 151 */           this.mListView.setSelection(position);
/* 152 */           if (this.mFloatView != null && !this.mDropping) {
/* 153 */               dragFloatView(this.mFloatViewListPosition, getClosestItemPosition());
                    }
                }

/* 157 */       public void setSelection(int selection) {
/* 158 */           this.mSelectionPosition = selection;
/* 159 */           if (selection != -1) {
/* 160 */               this.mFocusedPosition = selection;
                    }
/* 162 */           adjustItems();
/* 163 */           invalidate();
                }

/* 166 */       public void setFocused(int position) {
/* 167 */           this.mFocusedPosition = position;
/* 168 */           adjustItems();
/* 169 */           this.mListView.invalidate();
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
/* 172 */       private boolean handleListViewTouch(MotionEvent event) {
/* 173 */           int action = event.getAction() & 255;
/* 174 */           this.mTouchListPosition = getClosestItemPosition();
                    switch (action) {
                        case 0:
/* 178 */                   this.mTouchDownX = this.mTouchX;
/* 179 */                   this.mTouchDownY = this.mTouchY;
/* 180 */                   this.mActivePointerId = event.getPointerId(0);
/* 181 */                   this.mDownPosition = this.mTouchListPosition;
/* 182 */                   postDelayed(this.mCheckForLongPress, PICK_UP_DURATION);
                            return false;
                        case 1:
                            break;
                        case 2:
/* 185 */                   int oldHoverPosition = this.mFloatViewListPosition;
/* 186 */                   if (this.mTouchListPosition != -1) {
/* 187 */                       this.mFloatViewListPosition = this.mTouchListPosition;
                            }
/* 189 */                   int deltaY = Math.abs(this.mTouchY - this.mTouchDownY);
/* 190 */                   int deltaX = Math.abs(this.mTouchX - this.mTouchDownX);
/* 191 */                   boolean canPickupFloatView = this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.canPickUpFloatView();
/* 193 */                   if (this.mFloatView == null && deltaX < this.mTouchSlop && deltaY >= this.mTouchSlop && this.mTouchY - this.mTouchDownY < 0 && this.mSelectionPosition != this.mTouchListPosition && canPickupFloatView) {
/* 196 */                       handleClick(this.mTouchListPosition, false);
                            }
/* 198 */                   if (this.mFloatView == null && (deltaX >= this.mTouchSlop || deltaY >= this.mTouchSlop)) {
/* 200 */                       removeCallbacks(this.mCheckForLongPress);
/* 201 */                       if (canPickupFloatView && pickUpView(this.mFloatViewListPosition)) {
/* 202 */                           invalidate();
                                    return true;
                                }
                            } else if (this.mFloatView != null && !this.mDropping) {
/* 206 */                       this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
/* 207 */                       this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
/* 208 */                       if (this.mFloatViewInteractionListener != null) {
/* 209 */                           this.mFloatViewInteractionListener.floatViewMoved();
                                }
/* 211 */                       dragFloatView(oldHoverPosition, this.mFloatViewListPosition);
                                return true;
                            }
                            return false;
                        case 3:
                        case 4:
                        case 5:
                        default:
                            return false;
                        case 6:
/* 216 */                   int pointerIndex = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
/* 218 */                   int pointerId = event.getPointerId(pointerIndex);
/* 219 */                   if (pointerId == this.mActivePointerId) {
                            }
                            return false;
                    }
/* 223 */           this.mDownPosition = -1;
/* 224 */           removeCallbacks(this.mCheckForLongPress);
/* 225 */           if (this.mFloatView != null && !this.mDropping) {
/* 226 */               if (this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.floatViewDropped(this.mFloatViewListPosition)) {
/* 228 */                   this.mDropAnimator.start();
                        }
/* 230 */               this.mDragScroller.stopScrolling(true);
/* 231 */               invalidate();
                        return true;
                    }
                    return false;
                }

/* 239 */       public void deleteSelection() {
/* 240 */           if (canDelete()) {
/* 241 */               this.mFloatViewDestroyAnimator.start();
/* 242 */               removeSelection();
                    }
                }

/* 246 */       public boolean canDelete() {
                    return !this.mPickingUpContent;
                }

/* 250 */       private boolean handleContentViewTouch(MotionEvent event) {
/* 251 */           int action = event.getAction() & 255;
/* 252 */           this.mTouchListPosition = getClosestItemPosition();
                    switch (action) {
                        case 0:
/* 256 */                   this.mTouchDownX = this.mTouchX;
/* 257 */                   this.mTouchDownY = this.mTouchY;
/* 258 */                   this.mActivePointerId = event.getPointerId(0);
/* 259 */                   this.mDownPosition = this.mSelectionPosition;
                            break;
                        case 1:
/* 288 */                   this.mDownPosition = -1;
/* 289 */                   removeCallbacks(this.mCheckForLongPress);
/* 290 */                   if (this.mFloatView != null && !this.mDropping) {
/* 291 */                       if (this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.floatViewDropped(this.mFloatViewListPosition)) {
/* 293 */                           this.mDropAnimator.start();
                                }
/* 295 */                       this.mDragScroller.stopScrolling(true);
/* 296 */                       invalidate();
                                return true;
                            }
                            break;
                        case 2:
/* 262 */                   int deltaX = Math.abs(this.mTouchX - this.mTouchDownX);
/* 263 */                   int deltaY = Math.abs(this.mTouchY - this.mTouchDownY);
/* 264 */                   int oldHoverPosition = this.mFloatViewListPosition;
/* 265 */                   if (this.mTouchListPosition != -1) {
/* 266 */                       this.mFloatViewListPosition = this.mTouchListPosition;
                            }
/* 268 */                   boolean canPickupFloatView = this.mFloatViewInteractionListener != null && this.mFloatViewInteractionListener.canPickUpFloatView();
/* 270 */                   ListAdapter adapter = this.mListView != null ? this.mListView.getAdapter() : null;
/* 271 */                   if (this.mContentView != null && adapter != null && adapter.getCount() > 0 && ((deltaY >= this.mTouchSlop || deltaX >= this.mTouchSlop) && this.mFloatView == null && canPickupFloatView && this.mTouchDownY < this.mContentViewBounds.bottom - (this.mContentViewBounds.height() / 5))) {
/* 274 */                       pickUpContentView();
                                return true;
                            }
/* 276 */                   if (this.mFloatView != null && !this.mDropping) {
/* 277 */                       this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
/* 278 */                       this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
/* 279 */                       if (this.mFloatViewInteractionListener != null) {
/* 280 */                           this.mFloatViewInteractionListener.floatViewMoved();
                                }
/* 282 */                       dragFloatView(oldHoverPosition, this.mFloatViewListPosition);
                                return true;
                            }
/* 285 */                   invalidate();
                            break;
                    }
                    return false;
                }

/* 304 */       public boolean onTouch(View v, MotionEvent event, int xOffset, int yOffset) {
/* 305 */           this.mTouchX = ((int) event.getX()) + v.getLeft() + xOffset;
/* 306 */           this.mTouchY = ((int) event.getY()) + v.getTop() + yOffset;
/* 307 */           if (v == this.mListView) {
/* 308 */               this.isFromContentView = false;
/* 309 */               return handleListViewTouch(event);
                    }
/* 310 */           if (v == this.mContentView) {
                    }
                    return false;
                }

                @Override // android.view.View.OnTouchListener
/* 320 */       public boolean onTouch(View v, MotionEvent event) {
/* 321 */           return onTouch(v, event, 0, 0);
                }

/* 324 */       private boolean floatViewIntersectsList() {
                    return !this.mPickingUpContent && this.mFloatViewX <= this.mListView.getRight() && this.mFloatViewY <= this.mListView.getBottom() && this.mFloatViewX + this.mFloatViewWidth >= this.mListView.getLeft() && this.mFloatViewY + this.mFloatViewHeight >= this.mListView.getTop();
                }

/* 329 */       private void dragFloatView(int oldFloatViewPosition, int newFloatViewPosition) {
/* 330 */           if (oldFloatViewPosition > -1 && newFloatViewPosition > -1 && oldFloatViewPosition != newFloatViewPosition && floatViewIntersectsList()) {
/* 332 */               this.mFloatViewListPosition = newFloatViewPosition;
/* 333 */               finishSwap(oldFloatViewPosition, newFloatViewPosition);
                    } else if (floatViewIntersectsList() && oldFloatViewPosition == newFloatViewPosition) {
/* 335 */               View last = this.mListView.getChildAt(this.mListView.getLastVisiblePosition() - this.mListView.getFirstVisiblePosition());
/* 337 */               if (last != null && this.mFloatViewX > last.getRight()) {
/* 338 */                   this.mFloatViewListPosition = this.mListView.getLastVisiblePosition();
/* 339 */                   finishSwap(oldFloatViewPosition, this.mFloatViewListPosition);
                        }
                    } else {
/* 342 */               this.mFloatViewListPosition = oldFloatViewPosition;
                    }
/* 344 */           handleDragScroll();
/* 345 */           invalidate();
                }

/* 348 */       private void triggerSwapAnimation(int to, int from) {
/* 349 */           View oldSwapView = this.mListView.getChildAt(from - this.mListView.getFirstVisiblePosition());
/* 351 */           int swapViewStartLeft = oldSwapView == null ? 0 : oldSwapView.getLeft();
/* 353 */           ViewTreeObserver observer = getViewTreeObserver();
/* 354 */           observer.addOnPreDrawListener(new ViewTreeObserverOnPreDrawListenerC03721(observer, to, swapViewStartLeft));
                }

                /* JADX INFO: renamed from: co.vine.android.dragsort.DragSortWidget$1 */
/* 352 */       class ViewTreeObserverOnPreDrawListenerC03721 implements ViewTreeObserver.OnPreDrawListener {
                    final /* synthetic */ ViewTreeObserver val$observer;
                    final /* synthetic */ int val$swapViewStartLeft;
                    final /* synthetic */ int val$to;

/* 353 */           ViewTreeObserverOnPreDrawListenerC03721(ViewTreeObserver viewTreeObserver, int i, int i2) {
                        this.val$observer = viewTreeObserver;
                        this.val$to = i;
                        this.val$swapViewStartLeft = i2;
                    }

                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
/* 355 */           public boolean onPreDraw() {
/* 356 */               this.val$observer.removeOnPreDrawListener(this);
/* 358 */               View switchView = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.val$to - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 360 */               if (switchView != null) {
/* 361 */                   int swapViewNewLeft = switchView.getLeft();
/* 362 */                   int delta = this.val$swapViewStartLeft - swapViewNewLeft;
/* 364 */                   switchView.setTranslationX(delta);
/* 366 */                   ObjectAnimator animator = ObjectAnimator.ofFloat(switchView, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
/* 368 */                   animator.setDuration(100L);
/* 369 */                   animator.addUpdateListener(new AnonymousClass1());
/* 375 */                   animator.start();
                        }
                        return true;
                    }

                    /* JADX INFO: renamed from: co.vine.android.dragsort.DragSortWidget$1$1, reason: invalid class name */
/* 367 */           class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
/* 368 */               AnonymousClass1() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
/* 371 */               public void onAnimationUpdate(ValueAnimator animation) {
/* 372 */                   DragSortWidget.access$000(DragSortWidget.this).invalidate();
                        }
                    }
                }

/* 383 */       private void finishDrop() {
/* 384 */           this.mDropping = false;
/* 385 */           if (this.mFloatViewInteractionListener != null) {
/* 386 */               this.mFloatViewInteractionListener.floatViewLanded(this.mFloatViewListPosition);
                    }
/* 388 */           destroyFloatView();
/* 389 */           adjustItems();
/* 390 */           invalidate();
                }

/* 393 */       private void finishRemoveSelection() {
/* 394 */           if (this.mRemovePosition != -1) {
/* 395 */               if (this.mRemoveListener != null) {
/* 396 */                   this.mRemoveListener.remove(this.mRemovePosition);
                        }
/* 398 */               this.mRemovePosition = -1;
/* 399 */               this.mSelectionPosition = getNextPositionForSelection(this.mSelectionPosition);
/* 400 */               this.mFocusedPosition = this.mSelectionPosition;
/* 401 */               if (this.mSelectionChangedListener != null) {
/* 402 */                   this.mSelectionChangedListener.onSelectionChanged(this.mSelectionPosition, false);
                        }
                    }
                }

/* 407 */       private int getNextPositionForSelection(int mSelectionPosition) {
                    return mSelectionPosition < this.mAdapterWrapper.getCount() ? mSelectionPosition : this.mAdapterWrapper.getCount() - 1;
                }

/* 415 */       public void removeSelection() {
/* 416 */           this.mRemoveAnimator.start();
                }

/* 419 */       private void finishSwap(int oldFloatViewPosition, int newFloatViewPosition) {
/* 420 */           if (this.mDragListener != null) {
/* 421 */               if (oldFloatViewPosition < newFloatViewPosition) {
                            for (int i = oldFloatViewPosition; i < newFloatViewPosition; i++) {
/* 424 */                       if (i >= 0) {
/* 425 */                           this.mDragListener.drag(i, i + 1);
/* 426 */                           triggerSwapAnimation(i, i + 1);
                                }
                            }
                        } else {
                            for (int i2 = oldFloatViewPosition; i2 > newFloatViewPosition; i2--) {
/* 432 */                       if (i2 >= 1) {
/* 433 */                           this.mDragListener.drag(i2, i2 - 1);
/* 434 */                           triggerSwapAnimation(i2, i2 - 1);
                                }
                            }
                        }
                    }
/* 439 */           if (newFloatViewPosition != -1) {
/* 440 */               this.mSelectionPosition = newFloatViewPosition;
/* 441 */               this.mFocusedPosition = newFloatViewPosition;
                    }
/* 443 */           adjustItems();
                }

/* 446 */       private void adjustItems() {
                    for (int i = 0; i < this.mListView.getChildCount(); i++) {
/* 448 */               adjustItem(this.mListView.getFirstVisiblePosition() + i);
                    }
                }

/* 452 */       private void adjustItem(int position) {
/* 453 */           View childView = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
/* 454 */           if (childView != null) {
/* 455 */               if (this.mFloatView != null && position == this.mFloatViewListPosition) {
/* 456 */                   childView.setVisibility(4);
/* 457 */                   childView.setActivated(true);
                        } else if (position == this.mSelectionPosition) {
/* 459 */                   childView.setActivated(true);
/* 460 */                   childView.setVisibility(0);
                        } else {
/* 462 */                   childView.setActivated(false);
/* 463 */                   childView.setVisibility(0);
                        }
/* 465 */               childView.setAlpha(position == this.mFocusedPosition ? 1.0f : 0.3f);
/* 466 */               childView.invalidate();
                    }
                }

/* 474 */       private int getClosestItemPosition() {
                    for (int i = 0; i < this.mListView.getChildCount(); i++) {
/* 476 */               if (this.mTouchX - this.mListView.getLeft() < this.mListView.getChildAt(i).getRight()) {
/* 477 */                   return this.mListView.getFirstVisiblePosition() + i;
                        }
                    }
/* 480 */           return -1;
                }

/* 483 */       private void handleDragScroll() {
/* 484 */           boolean intersectsList = floatViewIntersectsList();
                    if (this.mTouchX - this.mListView.getLeft() > this.mListView.getWidth() * 2 * SCROLL_THRESHOLD && intersectsList) {
/* 487 */               this.mDragScroller.startScrolling(1);
                    } else if (this.mTouchX - this.mListView.getLeft() < this.mListView.getWidth() * SCROLL_THRESHOLD && intersectsList) {
/* 490 */               this.mDragScroller.startScrolling(0);
                    } else {
/* 492 */               this.mDragScroller.stopScrolling(true);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 497 */       protected void dispatchDraw(@NonNull Canvas canvas) {
                    View selection;
/* 498 */           super.dispatchDraw(canvas);
/* 500 */           if (this.mFloatView != null) {
/* 501 */               canvas.save();
/* 502 */               canvas.translate(this.mFloatViewX, this.mFloatViewY);
/* 503 */               canvas.clipRect(0, 0, this.mFloatViewWidth, this.mFloatViewHeight);
/* 504 */               canvas.saveLayerAlpha(0.0f, 0.0f, this.mFloatViewWidth, this.mFloatViewHeight, (int) this.mFloatViewAlpha, 31);
/* 506 */               this.mFloatView.layout(0, 0, this.mFloatViewWidth, this.mFloatViewHeight);
/* 507 */               this.mFloatView.draw(canvas);
/* 508 */               canvas.restore();
/* 509 */               canvas.restore();
/* 510 */               this.mFloatViewActivatedDrawable.setBounds(this.mFloatViewX, this.mFloatViewY, this.mFloatViewX + this.mFloatViewWidth, this.mFloatViewY + this.mFloatViewHeight);
/* 512 */               this.mFloatViewActivatedDrawable.setAlpha((int) this.mFloatViewAlpha);
/* 513 */               this.mFloatViewActivatedDrawable.draw(canvas);
/* 526 */               return;
                    }
/* 515 */           if (this.mSelectionPosition != -1 && (selection = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition())) != null && !this.mRemoving) {
/* 518 */               this.mActivatedDrawable.setBounds(selection.getLeft() + this.mListView.getLeft(), selection.getTop() + this.mListView.getTop(), selection.getRight() + this.mListView.getLeft(), selection.getBottom() + this.mListView.getTop());
/* 522 */               this.mActivatedDrawable.setAlpha(255);
/* 523 */               this.mActivatedDrawable.draw(canvas);
                    }
                }

/* 528 */       private void createFloatView(int position) {
/* 529 */           View v = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
/* 531 */           if (v != null && this.mTouchDownX - this.mListView.getLeft() >= v.getLeft() && this.mTouchDownY - this.mListView.getTop() >= v.getTop() && this.mTouchDownX - this.mListView.getLeft() <= v.getRight() && this.mTouchDownY - this.mListView.getTop() <= v.getBottom()) {
/* 543 */               v.setDrawingCacheQuality(1048576);
/* 544 */               v.setDrawingCacheEnabled(true);
/* 545 */               this.mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
/* 546 */               v.setDrawingCacheEnabled(false);
/* 548 */               if (this.mFloatView == null) {
/* 549 */                   this.mFloatView = new ImageView(this.mListView.getContext());
                        }
/* 551 */               this.mFloatView.setImageBitmap(this.mFloatBitmap);
/* 552 */               this.mFloatView.setLayoutParams(new ViewGroup.LayoutParams(v.getWidth(), v.getHeight()));
/* 553 */               this.mXOffsetFromTouch = this.mTouchDownX - (v.getLeft() + this.mListView.getLeft());
/* 554 */               this.mYOffsetFromTouch = this.mTouchDownY - (v.getTop() + this.mListView.getTop());
/* 556 */               this.mFloatViewWidth = v.getWidth();
/* 557 */               this.mFloatViewHeight = v.getHeight();
/* 559 */               this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
/* 560 */               this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
/* 562 */               this.mFloatViewAlpha = 255.0f;
/* 564 */               adjustItem(position);
                    }
                }

/* 567 */       private void createFloatViewFromContentView() {
/* 568 */           this.mFloatViewListPosition = this.mSelectionPosition;
/* 569 */           View v = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition());
/* 570 */           this.mContentViewPickUpAnimator.start();
/* 572 */           this.mXOffsetFromTouch = this.mTouchX - this.mContentViewBounds.left;
/* 573 */           this.mYOffsetFromTouch = this.mTouchY - this.mContentViewBounds.top;
/* 575 */           this.mFloatViewX = this.mTouchX - this.mXOffsetFromTouch;
/* 576 */           this.mFloatViewY = this.mTouchY - this.mYOffsetFromTouch;
/* 578 */           this.mFloatViewWidth = this.mContentViewBounds.width();
/* 579 */           this.mFloatViewHeight = this.mContentViewBounds.height();
/* 581 */           this.mFloatViewAlpha = 255.0f;
/* 582 */           if (v == null && this.mSelectionPosition >= 0 && this.mSelectionPosition < this.mListView.getAdapter().getCount()) {
/* 583 */               v = this.mListView.getAdapter().getView(this.mSelectionPosition, null, null);
                    }
/* 585 */           if (v instanceof DragSortListItem) {
/* 586 */               this.mFloatBitmap = ((DragSortListItem) v).getBitmap();
                    }
/* 589 */           if (this.mFloatView == null) {
/* 590 */               this.mFloatView = new ImageView(this.mListView.getContext());
                    }
/* 593 */           this.mFloatView.setImageBitmap(this.mFloatBitmap);
/* 594 */           this.mFloatView.setLayoutParams(new ViewGroup.LayoutParams(this.mFloatViewWidth, this.mFloatViewHeight));
/* 596 */           adjustItem(this.mSelectionPosition);
                }

/* 599 */       private void destroyFloatView() {
/* 600 */           if (this.mFloatView != null) {
/* 601 */               this.mFloatView.setImageDrawable(null);
/* 602 */               this.mFloatBitmap = null;
/* 603 */               this.mFloatView = null;
                    }
                }

/* 607 */       private boolean pickUpView(int position) {
/* 608 */           if (position != this.mSelectionPosition || position == -1) {
                        return false;
                    }
/* 609 */           createFloatView(position);
/* 610 */           if (this.mFloatViewInteractionListener != null) {
/* 611 */               this.mFloatViewInteractionListener.floatViewPickedUp(position);
                    }
/* 613 */           invalidate();
/* 614 */           return true;
                }

/* 619 */       private boolean pickUpContentView() {
/* 620 */           if (this.mSelectionPosition == -1) {
/* 621 */               int position = this.mCurrentlyPlayingProvider.getCurrentlyPlayingPosition();
/* 622 */               handleClick(position, false);
/* 623 */               this.mFloatViewListPosition = position;
/* 624 */               this.mSelectionPosition = position;
                    }
/* 626 */           createFloatViewFromContentView();
/* 627 */           if (this.mFloatViewInteractionListener != null) {
/* 628 */               this.mFloatViewInteractionListener.floatViewPickedUp(this.mSelectionPosition);
                    }
/* 630 */           invalidate();
/* 631 */           return true;
                }

/* 634 */       public void setCurrentlyPlayingProvider(CurrentlyPlayingProvider cpp) {
/* 635 */           this.mCurrentlyPlayingProvider = cpp;
                }

/* 638 */       public void releaseReferences() {
/* 639 */           setAdapter(null);
/* 640 */           this.mCurrentlyPlayingProvider = null;
/* 641 */           this.mContentView = null;
                }

/* 644 */       private boolean handleClick(int position, boolean listViewClick) {
                    View oldChild;
/* 645 */           View view = this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
/* 646 */           if (view == null) {
                        return false;
                    }
/* 647 */           if (this.mSelectionPosition == position) {
/* 649 */               view.setActivated(false);
/* 650 */               this.mSelectionPosition = -1;
/* 651 */               this.mFocusedPosition = -1;
                    } else {
/* 654 */               view.setActivated(true);
/* 657 */               if (this.mSelectionPosition != -1 && (oldChild = this.mListView.getChildAt(this.mSelectionPosition - this.mListView.getFirstVisiblePosition())) != null) {
/* 661 */                   oldChild.setActivated(false);
                        }
/* 664 */               this.mSelectionPosition = position;
/* 665 */               this.mFocusedPosition = position;
                    }
/* 667 */           if (this.mSelectionChangedListener != null) {
/* 668 */               this.mSelectionChangedListener.onSelectionChanged(this.mSelectionPosition, listViewClick);
                    }
/* 670 */           adjustItems();
/* 671 */           invalidate();
                    return true;
                }

/* 677 */       public boolean hasFloatView() {
                    return this.mFloatView != null;
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 684 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 685 */           if (this.mTouchX - this.mListView.getLeft() >= 0 && this.mTouchX - this.mListView.getLeft() < this.mListView.getWidth() && this.mTouchY - this.mListView.getTop() >= 0 && this.mTouchY - this.mListView.getTop() < this.mListView.getHeight()) {
/* 687 */               handleClick(position, true);
                    }
                }

                @Nullable
/* 692 */       public Rect getFloatViewBounds(Rect r) {
/* 693 */           if (this.mFloatView == null) {
                        return null;
                    }
/* 694 */           r.set(this.mFloatViewX, this.mFloatViewY, this.mFloatViewX + this.mFloatViewWidth, this.mFloatViewY + this.mFloatViewHeight);
/* 698 */           return r;
                }

/* 701 */       public int[] getFloatViewPosition(int[] intersectLoc) {
/* 702 */           if (this.mFloatView == null) {
                        return null;
                    }
/* 703 */           intersectLoc[0] = this.mFloatViewX;
/* 704 */           intersectLoc[1] = this.mFloatViewY;
/* 707 */           return intersectLoc;
                }

                @Override // co.vine.android.views.HorizontalListView.OnScrollListener
/* 711 */       public void onScroll() {
/* 712 */           invalidate();
/* 713 */           removeCallbacks(this.mCheckForLongPress);
                }

/* 716 */       public void forceListLayout() {
/* 717 */           if (this.mListView != null) {
/* 718 */               this.mListView.forceLayout();
/* 719 */               this.mListView.invalidate();
                    }
                }

/* 723 */       public int getSelection() {
/* 724 */           return this.mSelectionPosition;
                }

/* 725 */       public class DragScroller implements Runnable {
                    public static final int LEFT = 0;
                    public static final int RIGHT = 1;

                    /* JADX INFO: renamed from: dt */
                    private float f100dt;

                    /* JADX INFO: renamed from: dx */
                    private int f101dx;
                    private boolean mAbort;
                    private long mCurrTime;
                    private long mPrevTime;
                    private float mScrollSpeed = 0.0f;
                    private boolean mScrolling = false;
                    private int scrollDir;
                    private long tStart;

/* 726 */           public DragScroller() {
                    }

/* 746 */           public void startScrolling(int dir) {
/* 747 */               if (!this.mScrolling) {
/* 748 */                   this.mAbort = false;
/* 749 */                   this.mScrolling = true;
/* 750 */                   this.tStart = SystemClock.uptimeMillis();
/* 751 */                   this.mPrevTime = this.tStart;
/* 752 */                   this.scrollDir = dir;
/* 753 */                   DragSortWidget.this.post(this);
                        }
                    }

/* 757 */           public void stopScrolling(boolean now) {
/* 758 */               if (now) {
/* 759 */                   DragSortWidget.access$000(DragSortWidget.this).removeCallbacks(this);
/* 760 */                   this.mScrolling = false;
                        } else {
/* 762 */                   this.mAbort = true;
                        }
                    }

                    @Override // java.lang.Runnable
/* 767 */           public void run() {
/* 768 */               if (this.mAbort) {
/* 769 */                   this.mScrolling = false;
/* 829 */                   return;
                        }
/* 773 */               int oldHoverPosition = DragSortWidget.access$100(DragSortWidget.this);
/* 775 */               int first = DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition();
/* 776 */               int last = DragSortWidget.access$000(DragSortWidget.this).getLastVisiblePosition();
/* 777 */               int count = DragSortWidget.access$000(DragSortWidget.this).getCount();
/* 778 */               int listWidth = DragSortWidget.access$000(DragSortWidget.this).getWidth();
/* 780 */               if (this.scrollDir == 0) {
/* 781 */                   View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(0);
/* 782 */                   if (v == null) {
/* 783 */                       this.mScrolling = false;
/* 829 */                       return;
                            }
/* 786 */                   if (first == 0 && v.getLeft() == 0) {
/* 787 */                       this.mScrolling = false;
/* 829 */                       return;
                            }
/* 791 */                   int denominator = DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getLeft();
/* 792 */                   if (denominator < 0) {
/* 793 */                       denominator = 0;
                            }
/* 795 */                   this.mScrollSpeed = DragSortWidget.access$300(DragSortWidget.this) / (denominator + 1);
                        } else {
/* 797 */                   View v2 = DragSortWidget.access$000(DragSortWidget.this).getChildAt(last - first);
/* 798 */                   if (v2 == null) {
/* 799 */                       this.mScrolling = false;
/* 829 */                       return;
                            }
/* 802 */                   if (last == count - 1 && v2.getRight() <= listWidth) {
/* 803 */                       this.mScrolling = false;
/* 829 */                       return;
                            }
/* 808 */                   int denominator2 = (listWidth - DragSortWidget.access$200(DragSortWidget.this)) + DragSortWidget.access$000(DragSortWidget.this).getLeft();
/* 809 */                   if (denominator2 < 0) {
/* 810 */                       denominator2 = 0;
                            }
/* 812 */                   this.mScrollSpeed = (-DragSortWidget.access$300(DragSortWidget.this)) / (denominator2 + 1);
                        }
/* 814 */               this.mCurrTime = SystemClock.uptimeMillis();
/* 815 */               this.f100dt = this.mCurrTime - this.mPrevTime;
/* 817 */               this.f101dx = Math.round(this.mScrollSpeed * this.f100dt);
/* 819 */               DragSortWidget.access$000(DragSortWidget.this).scroll(this.f101dx, this.f101dx);
/* 821 */               this.mPrevTime = this.mCurrTime;
/* 822 */               DragSortWidget.access$102(DragSortWidget.this, DragSortWidget.access$400(DragSortWidget.this));
/* 823 */               if (DragSortWidget.access$100(DragSortWidget.this) == -1) {
/* 824 */                   DragSortWidget.access$102(DragSortWidget.this, oldHoverPosition);
                        }
/* 826 */               DragSortWidget.access$500(DragSortWidget.this, oldHoverPosition, DragSortWidget.access$100(DragSortWidget.this));
/* 828 */               DragSortWidget.this.post(this);
                    }
                }

/* 841 */       private class DropAnimator extends SmoothAnimator {
                    private float mDeltaX;
                    private float mDeltaY;
                    private int mTargetPosition;
                    private int mTargetX;
                    private int mTargetY;

/* 842 */           public DropAnimator(float smoothness, int duration) {
/* 844 */               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 848 */           public void onStart() {
/* 849 */               this.mTargetPosition = DragSortWidget.access$100(DragSortWidget.this);
/* 850 */               View hoverItem = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.mTargetPosition - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 851 */               DragSortWidget.this.mDropping = true;
/* 852 */               if (hoverItem != null) {
/* 853 */                   this.mTargetY = hoverItem.getTop() + DragSortWidget.access$000(DragSortWidget.this).getTop();
/* 854 */                   this.mTargetX = hoverItem.getLeft() + DragSortWidget.access$000(DragSortWidget.this).getLeft();
                        } else {
/* 856 */                   DragSortWidget.this.mDropping = false;
/* 857 */                   DragSortWidget.access$600(DragSortWidget.this).start();
/* 858 */                   cancel();
                        }
/* 860 */               this.mDeltaX = DragSortWidget.access$700(DragSortWidget.this) - this.mTargetX;
/* 861 */               this.mDeltaY = DragSortWidget.access$800(DragSortWidget.this) - this.mTargetY;
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 865 */           public void onUpdate(float frac, float smoothFrac) {
/* 866 */               int newTargetX = this.mTargetX;
/* 867 */               int newTargetY = this.mTargetY;
/* 868 */               View hoverItem = DragSortWidget.access$000(DragSortWidget.this).getChildAt(this.mTargetPosition - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 869 */               if (hoverItem != null) {
/* 870 */                   newTargetY = hoverItem.getTop() + DragSortWidget.access$000(DragSortWidget.this).getTop();
/* 871 */                   newTargetX = hoverItem.getLeft() + DragSortWidget.access$000(DragSortWidget.this).getLeft();
                        }
/* 873 */               this.mTargetX = newTargetX;
/* 874 */               this.mTargetY = newTargetY;
/* 876 */               float f = 1.0f - smoothFrac;
/* 877 */               DragSortWidget.access$702(DragSortWidget.this, this.mTargetX + ((int) (this.mDeltaX * f)));
/* 878 */               DragSortWidget.access$802(DragSortWidget.this, this.mTargetY + ((int) (this.mDeltaY * f)));
/* 879 */               DragSortWidget.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 883 */           public void onStop() {
/* 884 */               DragSortWidget.access$900(DragSortWidget.this);
                    }
                }

/* 888 */       private class FloatViewDestroyAnimator extends SmoothAnimator {
/* 889 */           public FloatViewDestroyAnimator(float smoothness, int duration) {
/* 891 */               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 895 */           public void onUpdate(float frac, float smoothFrac) {
/* 896 */               DragSortWidget.access$1002(DragSortWidget.this, ((1.0f - smoothFrac) * 255.0f) - 50.0f);
/* 897 */               DragSortWidget.access$1002(DragSortWidget.this, DragSortWidget.access$1000(DragSortWidget.this) >= 0.0f ? DragSortWidget.access$1000(DragSortWidget.this) : 0.0f);
/* 898 */               DragSortWidget.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 902 */           public void onStop() {
/* 903 */               DragSortWidget.access$1002(DragSortWidget.this, 0.0f);
/* 904 */               DragSortWidget.access$102(DragSortWidget.this, -1);
/* 905 */               DragSortWidget.access$1100(DragSortWidget.this);
/* 906 */               DragSortWidget.access$1200(DragSortWidget.this);
/* 907 */               DragSortWidget.this.invalidate();
                    }
                }

/* 917 */       private class ContentViewPickUpAnimator extends SmoothAnimator {
                    private int mFloatViewStartHeight;
                    private int mFloatViewStartWidth;
                    private int mHeightDelta;
                    private int mWidthDelta;
                    private float mXOffsetRatio;
                    private float mYOffsetRatio;

/* 918 */           public ContentViewPickUpAnimator(float smoothness, int duration) {
/* 920 */               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 924 */           public void onStart() {
/* 925 */               this.mFloatViewStartWidth = DragSortWidget.access$1300(DragSortWidget.this).width();
/* 926 */               this.mFloatViewStartHeight = DragSortWidget.access$1300(DragSortWidget.this).height();
/* 928 */               DragSortWidget.access$1402(DragSortWidget.this, this.mFloatViewStartWidth);
/* 929 */               DragSortWidget.access$1502(DragSortWidget.this, this.mFloatViewStartHeight);
/* 931 */               DragSortWidget.access$1602(DragSortWidget.this, DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$1300(DragSortWidget.this).left);
/* 932 */               DragSortWidget.access$1702(DragSortWidget.this, DragSortWidget.access$1800(DragSortWidget.this) - DragSortWidget.access$1300(DragSortWidget.this).top);
/* 934 */               if (DragSortWidget.access$1300(DragSortWidget.this).width() != 0) {
/* 935 */                   this.mXOffsetRatio = DragSortWidget.access$1600(DragSortWidget.this) / (DragSortWidget.access$1300(DragSortWidget.this).width() * 1.0f);
                        }
/* 938 */               if (DragSortWidget.access$1300(DragSortWidget.this).height() != 0) {
/* 939 */                   this.mYOffsetRatio = DragSortWidget.access$1700(DragSortWidget.this) / (DragSortWidget.access$1300(DragSortWidget.this).height() * 1.0f);
                        }
/* 942 */               this.mWidthDelta = DragSortWidget.access$1400(DragSortWidget.this) - DragSortWidget.access$1900(DragSortWidget.this);
/* 943 */               this.mHeightDelta = DragSortWidget.access$1500(DragSortWidget.this) - DragSortWidget.access$1900(DragSortWidget.this);
/* 945 */               DragSortWidget.access$2002(DragSortWidget.this, true);
/* 946 */               DragSortWidget.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 950 */           public void onUpdate(float frac, float smoothFrac) {
/* 951 */               DragSortWidget.access$1402(DragSortWidget.this, (int) (this.mFloatViewStartWidth - (this.mWidthDelta * smoothFrac)));
/* 952 */               DragSortWidget.access$1502(DragSortWidget.this, (int) (this.mFloatViewStartHeight - (this.mHeightDelta * smoothFrac)));
/* 953 */               int oldXOffset = DragSortWidget.access$1600(DragSortWidget.this);
/* 954 */               int oldYOffset = DragSortWidget.access$1700(DragSortWidget.this);
/* 955 */               DragSortWidget.access$1602(DragSortWidget.this, (int) (DragSortWidget.access$1400(DragSortWidget.this) * this.mXOffsetRatio));
/* 956 */               DragSortWidget.access$1702(DragSortWidget.this, (int) (DragSortWidget.access$1500(DragSortWidget.this) * this.mYOffsetRatio));
/* 957 */               DragSortWidget.access$702(DragSortWidget.this, (DragSortWidget.access$700(DragSortWidget.this) + oldXOffset) - DragSortWidget.access$1600(DragSortWidget.this));
/* 958 */               DragSortWidget.access$802(DragSortWidget.this, (DragSortWidget.access$800(DragSortWidget.this) + oldYOffset) - DragSortWidget.access$1700(DragSortWidget.this));
/* 959 */               DragSortWidget.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 963 */           public void onStop() {
/* 964 */               DragSortWidget.access$2002(DragSortWidget.this, false);
/* 965 */               DragSortWidget.this.invalidate();
                    }
                }

/* 967 */       private class RemoveAnimator extends SmoothAnimator {
                    private int mWidth;

/* 968 */           int access$2700() {
/* 969 */               return this.mWidth;
                    }

/* 971 */           public RemoveAnimator(float smoothness, int duration) {
/* 973 */               super(DragSortWidget.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 977 */           public void onStart() {
/* 978 */               DragSortWidget.access$2102(DragSortWidget.this, DragSortWidget.access$2200(DragSortWidget.this));
/* 979 */               View toMeasure = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 981 */               if (toMeasure != null) {
/* 982 */                   this.mWidth = toMeasure.getWidth();
/* 983 */                   toMeasure.animate().alpha(0.0f).setDuration((long) this.mDurationF).start();
/* 984 */                   ((DragSortListItem) toMeasure).hasTransientState = true;
/* 991 */                   DragSortWidget.this.mRemoving = true;
/* 992 */                   return;
                        }
/* 987 */               DragSortWidget.access$2300(DragSortWidget.this);
/* 988 */               cancel();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 995 */           public void onUpdate(float frac, float smoothFrac) {
/* 996 */               View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 997 */               if (v != null) {
/* 998 */                   float f = 1.0f - smoothFrac;
/* 999 */                   this.mWidth = Math.round(this.mWidth * f);
/* 1000 */                  ViewGroup.LayoutParams lp = v.getLayoutParams();
/* 1001 */                  lp.width = this.mWidth;
/* 1002 */                  v.setLayoutParams(lp);
/* 1003 */                  DragSortWidget.access$000(DragSortWidget.this).forceLayout();
                        } else {
/* 1005 */                  DragSortWidget.access$2300(DragSortWidget.this);
/* 1006 */                  DragSortWidget.this.mRemoving = false;
/* 1007 */                  cancel();
                        }
/* 1009 */              DragSortWidget.access$000(DragSortWidget.this).invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 1013 */          public void onStop() {
/* 1014 */              View v = DragSortWidget.access$000(DragSortWidget.this).getChildAt(DragSortWidget.access$2100(DragSortWidget.this) - DragSortWidget.access$000(DragSortWidget.this).getFirstVisiblePosition());
/* 1015 */              if (v != null) {
/* 1016 */                  v.setVisibility(8);
/* 1017 */                  v.animate().cancel();
/* 1018 */                  v.setAlpha(1.0f);
/* 1019 */                  ((DragSortListItem) v).hasTransientState = false;
                        }
/* 1021 */              DragSortWidget.access$2300(DragSortWidget.this);
/* 1022 */              DragSortWidget.this.mRemoving = false;
                    }
                }

/* 1027 */      private class DuplicateAnimator extends SmoothAnimator {
                    private View mView;

/* 1028 */          public DuplicateAnimator(View view) {
/* 1030 */              super(DragSortWidget.this, DragSortWidget.SMOOTHNESS, 200);
/* 1031 */              this.mView = view;
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 1035 */          public void onStart() {
/* 1036 */              if (this.mView != null) {
/* 1037 */                  this.mView.setAlpha(1.0f);
                        } else {
/* 1040 */                  cancel();
                        }
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 1046 */          public void onUpdate(float frac, float smoothFrac) {
/* 1047 */              if (this.mView != null) {
/* 1048 */                  this.mView.setAlpha((0.7f * (1.0f - smoothFrac)) + 0.3f);
                        } else {
/* 1050 */                  cancel();
                        }
/* 1052 */              DragSortWidget.access$000(DragSortWidget.this).invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 1056 */          public void onStop() {
/* 1057 */              if (this.mView != null) {
/* 1058 */                  this.mView.setAlpha(0.3f);
                        }
                    }
                }

/* 1063 */      public void setAdapter(ListAdapter adapter) {
/* 1064 */          if (adapter != null) {
/* 1065 */              if (adapter instanceof FloatViewInteractionListener) {
/* 1066 */                  setFloatViewInteractionListener((FloatViewInteractionListener) adapter);
                        }
/* 1068 */              if (adapter instanceof DragListener) {
/* 1069 */                  setDragListener((DragListener) adapter);
                        }
/* 1071 */              if (adapter instanceof RemoveListener) {
/* 1072 */                  setRemoveListener((RemoveListener) adapter);
                        }
/* 1074 */              if (adapter instanceof SelectionChangedListener) {
/* 1075 */                  setSelectionChangedListener((SelectionChangedListener) adapter);
                        }
/* 1077 */              if (this.mAdapterWrapper == null) {
/* 1078 */                  this.mAdapterWrapper = new DragSortAdapterWrapper(getContext());
                        }
/* 1080 */              this.mAdapterWrapper.setAdapter(adapter);
/* 1081 */              this.mListView.setAdapter((ListAdapter) this.mAdapterWrapper);
/* 1088 */              return;
                    }
/* 1083 */          this.mListView.setAdapter((ListAdapter) null);
/* 1084 */          if (this.mAdapterWrapper != null) {
/* 1085 */              this.mAdapterWrapper.setAdapter(null);
                    }
                }

/* 1126 */      public void setFloatViewInteractionListener(FloatViewInteractionListener l) {
/* 1127 */          this.mFloatViewInteractionListener = l;
                }

/* 1130 */      public void setRemoveListener(RemoveListener l) {
/* 1131 */          this.mRemoveListener = l;
                }

/* 1134 */      public void setDragListener(DragListener l) {
/* 1135 */          this.mDragListener = l;
                }

/* 1138 */      public void setSelectionChangedListener(SelectionChangedListener l) {
/* 1139 */          this.mSelectionChangedListener = l;
                }

/* 1143 */      public class DragSortListItem extends ViewGroup {
                    public boolean hasTransientState;

/* 1144 */          public DragSortListItem(Context context) {
/* 1146 */              super(context);
/* 1143 */              this.hasTransientState = false;
/* 1147 */              setLayoutParams(new HorizontalListView.LayoutParams(-2, -1));
                    }

/* 1152 */          public Bitmap getBitmap() {
                        BitmapDrawable bd;
/* 1153 */              View v = getChildAt(0);
/* 1154 */              if (v == null || (bd = (BitmapDrawable) v.getBackground()) == null) {
                            return null;
                        }
/* 1157 */              return bd.getBitmap();
                    }

                    @Override // android.view.ViewGroup, android.view.View
/* 1164 */          public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 1165 */              if (DragSortWidget.access$2400(DragSortWidget.this) != null) {
/* 1166 */                  DragSortWidget.access$2400(DragSortWidget.this).setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        }
/* 1168 */              View child = getChildAt(0);
/* 1169 */              if (child != null) {
/* 1170 */                  child.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        }
                    }

                    @Override // android.view.View
/* 1176 */          protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 1177 */              int height = View.MeasureSpec.getSize(heightMeasureSpec);
/* 1178 */              int width = View.MeasureSpec.getSize(widthMeasureSpec);
/* 1180 */              int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 1182 */              View child = getChildAt(0);
/* 1183 */              if (child == null) {
/* 1184 */                  setMeasuredDimension(0, 0);
/* 1204 */                  return;
                        }
/* 1188 */              if (child.isLayoutRequested()) {
/* 1190 */                  measureChild(child, View.MeasureSpec.makeMeasureSpec(0, 0), heightMeasureSpec);
                        }
/* 1193 */              if (widthMode == 0) {
/* 1194 */                  ViewGroup.LayoutParams lp = getLayoutParams();
/* 1196 */                  if (lp.width > 0 || this.hasTransientState) {
/* 1197 */                      width = lp.width;
                            } else {
/* 1199 */                      width = child.getMeasuredWidth();
                            }
                        }
/* 1203 */              setMeasuredDimension(width, height);
                    }
                }

/* 1208 */      public class DragSortAdapterWrapper extends BaseAdapter implements ListAdapter {
                    private ListAdapter mAdapter;
                    private Context mContext;
                    private DataSetObserver mObserver = new C03731();

                    /* JADX INFO: renamed from: co.vine.android.dragsort.DragSortWidget$DragSortAdapterWrapper$1 */
/* 1209 */          class C03731 extends DataSetObserver {
/* 1210 */              C03731() {
                        }

                        @Override // android.database.DataSetObserver
/* 1213 */              public void onChanged() {
/* 1214 */                  DragSortAdapterWrapper.this.notifyDataSetChanged();
/* 1215 */                  DragSortWidget.access$1200(DragSortWidget.this);
                        }
                    }

/* 1218 */          public DragSortAdapterWrapper(Context context) {
/* 1220 */              this.mContext = context;
                    }

/* 1223 */          public void setAdapter(ListAdapter adapter) {
/* 1224 */              this.mAdapter = adapter;
/* 1225 */              if (adapter != null) {
/* 1226 */                  adapter.registerDataSetObserver(this.mObserver);
                        }
                    }

                    @Override // android.widget.Adapter
/* 1231 */          public int getCount() {
/* 1232 */              return this.mAdapter.getCount();
                    }

                    @Override // android.widget.Adapter
/* 1236 */          public Object getItem(int position) {
/* 1237 */              return this.mAdapter.getItem(position);
                    }

                    @Override // android.widget.Adapter
/* 1241 */          public long getItemId(int position) {
/* 1242 */              return this.mAdapter.getItemId(position);
                    }

                    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 1246 */          public boolean isEnabled(int position) {
/* 1247 */              return this.mAdapter.isEnabled(position);
                    }

                    @Override // android.widget.Adapter
/* 1253 */          public View getView(int position, View convertView, ViewGroup parent) {
                        View child;
                        DragSortListItem item;
/* 1254 */              if (convertView != null) {
/* 1255 */                  item = (DragSortListItem) convertView;
/* 1256 */                  View oldChild = item.getChildAt(position);
/* 1257 */                  child = this.mAdapter.getView(position, oldChild, parent);
                        } else {
/* 1259 */                  child = this.mAdapter.getView(position, null, parent);
/* 1260 */                  item = DragSortWidget.this.new DragSortListItem(this.mContext);
                        }
/* 1262 */              item.removeAllViews();
/* 1263 */              item.addView(child);
/* 1264 */              item.setActivated(position == DragSortWidget.access$2200(DragSortWidget.this));
/* 1265 */              item.setAlpha(position == DragSortWidget.access$2500(DragSortWidget.this) ? 1.0f : 0.3f);
/* 1267 */              ViewGroup.LayoutParams lp = item.getLayoutParams();
/* 1268 */              if (DragSortWidget.this.mRemoving && position == DragSortWidget.access$2100(DragSortWidget.this)) {
/* 1269 */                  lp.width = DragSortWidget.access$2600(DragSortWidget.this).access$2700();
                        } else {
/* 1272 */                  lp.width = 0;
                        }
/* 1274 */              item.setLayoutParams(lp);
/* 1276 */              if (DragSortWidget.access$2800(DragSortWidget.this) != null && position == DragSortWidget.access$100(DragSortWidget.this)) {
/* 1277 */                  item.setVisibility(4);
                        } else {
/* 1279 */                  item.setVisibility(0);
                        }
/* 1282 */              if (((ShouldAnimateInProvider) this.mAdapter).shouldAnimateIn(position)) {
/* 1283 */                  DragSortWidget.this.new DuplicateAnimator(item).start();
                        }
/* 1286 */              return item;
                    }
                }

/* 1288 */      public class CheckForLongPress implements Runnable {
/* 1289 */          public CheckForLongPress() {
                    }

                    @Override // java.lang.Runnable
/* 1292 */          public void run() {
/* 1293 */              if (DragSortWidget.access$2900(DragSortWidget.this) == DragSortWidget.access$3000(DragSortWidget.this)) {
/* 1294 */                  if (DragSortWidget.access$2900(DragSortWidget.this) != DragSortWidget.access$2200(DragSortWidget.this)) {
/* 1295 */                      DragSortWidget.access$3100(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this), false);
                            }
/* 1297 */                  DragSortWidget.access$102(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this));
/* 1298 */                  if (DragSortWidget.access$3200(DragSortWidget.this, DragSortWidget.access$2900(DragSortWidget.this))) {
/* 1299 */                      DragSortWidget.access$702(DragSortWidget.this, DragSortWidget.access$200(DragSortWidget.this) - DragSortWidget.access$1600(DragSortWidget.this));
/* 1300 */                      DragSortWidget.access$802(DragSortWidget.this, DragSortWidget.access$1800(DragSortWidget.this) - DragSortWidget.access$1700(DragSortWidget.this));
                            }
                        }
                    }
                }

/* 1306 */      public void setContentView(View view, Rect bounds) {
/* 1307 */          this.mContentView = view;
/* 1308 */          this.mContentViewBounds = bounds;
/* 1309 */          view.setOnTouchListener(this);
                }

/* 1312 */      public void cleanUp() {
/* 1313 */          this.mSelectionPosition = -1;
/* 1314 */          this.mFocusedPosition = -1;
/* 1315 */          this.mRemovePosition = -1;
/* 1316 */          this.mDropping = false;
/* 1317 */          this.mRemoving = false;
/* 1318 */          destroyFloatView();
                }

/* 1321 */      public boolean floatViewIsFromContentView() {
/* 1322 */          return this.isFromContentView;
                }

/* 1329 */      public boolean isVisible(int position) {
                    return this.mListView != null && position >= this.mListView.getFirstVisiblePosition() && position <= this.mListView.getFirstVisiblePosition() + this.mListView.getChildCount();
                }
            }
