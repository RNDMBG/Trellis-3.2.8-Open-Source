            package com.twitter.android.widget;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Handler;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.widget.AbsListView;
            import android.widget.Adapter;
            import android.widget.HeaderViewListAdapter;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import android.widget.Scroller;
            import co.vine.android.R;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.SdkListView;
            import java.util.ArrayList;
            
            
    public class RefreshableListView extends SdkListView implements TopScrollable {
                static final int MODE_ADDED = 1;
                protected static final int MODE_ATTACHED = 2;
                static final int MODE_POPPED = 16;
                static final int MODE_REFRESHING = 34;
                static final int MODE_REFRESH_STARTED = 32;
                static final int MODE_SCROLLING = 8;
                static final int MODE_SLOP = 64;
                static final int MODE_TRACKING = 4;
                private static final int SMOOTH_SCROLL_MAX_POSITION = 5;
                private RefreshViewListAdapter mAdapter;
                private boolean mAttachedToWindow;
                private AdapterDataSetObserver mDataSetObserver;
                private int mDownY;
                private final int mFooterLayoutId;
                private View mFooterView;
                private final ArrayList<ListView.FixedViewInfo> mFooterViewInfos;
                private final ArrayList<ListView.FixedViewInfo> mHeaderViewInfos;
                int mItemCount;
                private ImageView mLoading;
                private AnimateLoader mLoadingAnimation;
                private int mMode;
                private int mMoveY;
                private boolean mPTRDisabled;
                private final float mPixelScale;
                private int mPullBackgroundColor;
                private RefreshListener mRefreshListener;
                private ImageView mRefreshTriangle1;
                final RelativeLayout mRefreshView;
                private final ScrollRunnable mScrollRunnable;
                final Scroller mScroller;
                private int mTopPos;
                private final int mTouchSlop;
                private View mUnderNavRefreshHeader;
                private int mWidthMeasureSpec;

        static  ArrayList access$000(RefreshableListView x0) {
            return x0.mHeaderViewInfos;
                }

        static  RefreshViewListAdapter access$100(RefreshableListView x0) {
            return x0.mAdapter;
                }

        static  ArrayList access$200(RefreshableListView x0) {
            return x0.mFooterViewInfos;
                }


       public RefreshableListView(Context context) {
           this(context, null);
                }

       public RefreshableListView(Context context, AttributeSet attrs) {
           this(context, attrs, R.attr.refreshableListViewStyle);
                }

       public RefreshableListView(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
           this.mHeaderViewInfos = new ArrayList<>();
           this.mFooterViewInfos = new ArrayList<>();
           TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshableListView, defStyle, 0);
           this.mPixelScale = getResources().getDisplayMetrics().density;
           this.mPullBackgroundColor = a.getColor(R.styleable.RefreshableListView_pullBackgroundColor, -1);
           Drawable divider = a.getDrawable(R.styleable.RefreshableListView_pullDivider);
           this.mScroller = new Scroller(context);
           this.mScrollRunnable = new ScrollRunnable();
           LayoutInflater inflater = LayoutInflater.from(context);
           int layout = a.getResourceId(R.styleable.RefreshableListView_refreshHeader, R.layout.pull_to_refresh);
           if (layout == 0) {
               layout = R.layout.pull_to_refresh;
                    }
           RelativeLayout v = (RelativeLayout) inflater.inflate(layout, (ViewGroup) this, false);
           if (divider != null) {
               View dividerView = v.findViewById(R.id.refresh_divider);
               dividerView.setBackgroundDrawable(divider);
               dividerView.setVisibility(0);
                    }
           this.mLoading = (ImageView) v.findViewById(R.id.refresh_loading);
           this.mRefreshTriangle1 = (ImageView) v.findViewById(R.id.refresh_triangle_1);
           this.mRefreshView = v;
           ViewConfiguration config = ViewConfiguration.get(getContext());
           this.mTouchSlop = config.getScaledTouchSlop();
           ViewGroup.LayoutParams p = v.getLayoutParams();
           v.setLayoutParams(new AbsListView.LayoutParams(p.width, p.height, -1));
           int footerLayout = a.getResourceId(R.styleable.RefreshableListView_refreshFooter, R.layout.list_footer_view);
           this.mFooterLayoutId = footerLayout == 0 ? R.layout.list_footer_view : footerLayout;
           this.mLoadingAnimation = new AnimateLoader(this.mLoading);
           a.recycle();
                }

       public void setRefreshHeader(View header, int height) {
           if (this.mLoading != null) {
               this.mLoading.setVisibility(8);
                    }
           if (this.mRefreshTriangle1 != null) {
               this.mRefreshTriangle1.setVisibility(8);
                    }
           this.mLoading = (ImageView) header.findViewById(R.id.refresh_loading);
           this.mRefreshTriangle1 = (ImageView) header.findViewById(R.id.refresh_triangle_1);
           this.mLoadingAnimation = new AnimateLoader(this.mLoading);
           this.mUnderNavRefreshHeader = header;
           header.setBackgroundColor(this.mPullBackgroundColor);
           this.mRefreshView.setPadding(0, height, 0, this.mRefreshView.getPaddingBottom());
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           updateItemCount();
           this.mWidthMeasureSpec = widthMeasureSpec;
           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }

                @Override 
       protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
           if (isRefreshable() && !this.mAttachedToWindow && gainFocus && getSelectedItemPosition() < 0 && !isInTouchMode() && this.mAdapter != null) {
               updateItemCount();
                    }
           super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
                }

                @Override 
       protected void onAttachedToWindow() {
           if (isRefreshable() && this.mAdapter != null) {
               this.mAdapter.registerDataSetObserver();
               updateItemCount();
                    }
           this.mAttachedToWindow = true;
           super.onAttachedToWindow();
                }

                @Override 
       protected void onDetachedFromWindow() {
           if (isRefreshable()) {
               if (this.mAdapter != null) {
                   this.mAdapter.unregisterDataSetObserver();
                        }
               removeCallbacks(this.mScrollRunnable);
                    }
           this.mAttachedToWindow = false;
           super.onDetachedFromWindow();
                }

                @Override 
       protected void layoutChildren() {
           if (!isRefreshable()) {
               super.layoutChildren();
                    } else if (!isMode(12)) {
               if (this.mItemCount == 0) {
                   clearRecycledState(this.mHeaderViewInfos);
                   clearRecycledState(this.mFooterViewInfos);
                        }
               super.layoutChildren();
                    }
                }

       private void clearRecycledState(ArrayList<ListView.FixedViewInfo> infos) {
           if (infos != null) {
                        for (ListView.FixedViewInfo info : infos) {
                   View child = info.view;
                   AbsListView.LayoutParams p = (AbsListView.LayoutParams) child.getLayoutParams();
                   if (p != null) {
                       info.view.setLayoutParams(new AbsListView.LayoutParams(p.width, p.height, -2));
                            }
                        }
                    }
                }

                @Override 
       protected void onDraw(Canvas canvas) {
                    View child;
           if (isRefreshable() && (child = getChildAt(0)) != null && child.equals(this.mRefreshView)) {
               int left = getScrollX();
               canvas.save();
               canvas.clipRect(left, getScrollY(), getWidth() + left, child.getBottom());
               canvas.drawColor(this.mPullBackgroundColor);
               canvas.restore();
                    }
           super.onDraw(canvas);
                }

                @Override 
       public boolean onInterceptTouchEvent(MotionEvent ev) {
                    View topChild;
           if (!isRefreshable()) {
               return super.onInterceptTouchEvent(ev);
                    }
           int y = (int) ev.getY();
           int deltaY = y - this.mDownY;
                    switch (ev.getAction()) {
                        case 0:
                   this.mDownY = y;
                   this.mMoveY = Integer.MIN_VALUE;
                            break;
                        case 2:
                   if (isMode(32)) {
                       this.mMoveY = y;
                            } else {
                       boolean attached = isMode(2);
                       if (attached) {
                           topChild = getChildAt(1);
                                } else {
                           topChild = getChildAt(0);
                                }
                       int top = topChild != null ? topChild.getTop() : 0;
                       this.mMoveY = y;
                       if (getFirstVisiblePosition() == 0 && top >= getListPaddingTop() && deltaY > this.mTouchSlop) {
                                    return true;
                                }
                            }
                            break;
                    }
                    try {
               return super.onInterceptTouchEvent(ev);
                    } catch (IndexOutOfBoundsException | NullPointerException e) {
               silence(e, ev);
                        return false;
                    }
                }

       private void silence(Exception e, MotionEvent ev) {
           CrashUtil.logException(e, "Event was: {}, x: {}, y: {}.", Integer.valueOf(ev.getAction()), Float.valueOf(ev.getX()), Float.valueOf(ev.getY()));
                }

       public void setViewYOffset(int offset) {
           int pixels = (int) ((offset * this.mPixelScale) + 0.5f);
           this.mRefreshView.setPadding(0, this.mRefreshView.getPaddingTop(), 0, pixels);
                }

       public void setPullToRefreshBackgroundColor(int color) {
           this.mPullBackgroundColor = color;
           if (this.mUnderNavRefreshHeader != null) {
               this.mUnderNavRefreshHeader.setBackgroundColor(color);
                    }
                }

                @Override 
       public boolean onTouchEvent(MotionEvent ev) {
                    int diff;
                    View topChild;
           if (!isRefreshable()) {
               return super.onTouchEvent(ev);
                    }
           int action = ev.getAction() & 255;
           int y = (int) ev.getY();
           int deltaY = y - this.mDownY;
                    switch (action) {
                        case 0:
                   this.mDownY = y;
                   this.mMoveY = Integer.MIN_VALUE;
                   setMode(64);
                            break;
                        case 1:
                   unsetMode(20);
                   if (!isMode(32) && isMode(2)) {
                       View topChild2 = getChildAt(0);
                       int firstPos = getFirstVisiblePosition();
                       if (firstPos == 0 && topChild2 != null) {
                           int top = topChild2.getTop();
                           int bottom = topChild2.getBottom();
                           if (top > 0) {
                               this.mRefreshListener.onRefreshReleased(true);
                               startScroll(top);
                                    } else if (topChild2.getBottom() > 0) {
                               this.mRefreshListener.onRefreshReleased(false);
                               startScroll(getDividerHeight() + bottom);
                                    }
                                }
                            }
                            break;
                        case 2:
                   if (isMode(32)) {
                       this.mMoveY = y;
                            } else {
                       if (this.mMoveY != Integer.MIN_VALUE) {
                           diff = y - this.mMoveY;
                                } else {
                           diff = deltaY;
                                }
                       boolean attached = isMode(2);
                       if (attached) {
                           topChild = getChildAt(1);
                                } else {
                           topChild = getChildAt(0);
                                }
                       int top2 = topChild != null ? topChild.getTop() : 0;
                       boolean up = y >= this.mMoveY;
                       this.mMoveY = y;
                       if (getFirstVisiblePosition() == 0 && top2 >= getListPaddingTop() && deltaY > this.mTouchSlop) {
                           requestDisallowInterceptTouchEvent(true);
                           if (isMode(64)) {
                               super.onTouchEvent(ev);
                               unsetMode(64);
                                    }
                           setChildrenDrawingCacheEnabled(false);
                           setChildrenDrawnWithCacheEnabled(false);
                           if (attached) {
                               offsetChildrenTopAndBottom((int) (diff * 0.5f));
                               invalidate();
                               View child = getChildAt(0);
                               if (child != null && child.getTop() >= 0) {
                                   if (up && !isMode(16)) {
                                       setMode(16);
                                       this.mRefreshListener.onRefreshPulled();
                                            }
                                        } else if (!up && isMode(16)) {
                                   unsetMode(16);
                                   this.mRefreshListener.onRefreshCancelled();
                                        }
                                    } else {
                               attach(top2 - getDividerHeight());
                                    }
                           setMode(4);
                           return true;
                                }
                       detach();
                            }
                            break;
                    }
                    try {
               return super.onTouchEvent(ev);
                    } catch (IndexOutOfBoundsException | NullPointerException e) {
               silence(e, ev);
               return false;
                    }
                }

       public void colorizePTR(int color) {
           ViewUtil.fillColor(getResources(), color, R.id.refresh_loading, this.mLoading);
           ViewUtil.fillColor(getResources(), color, R.id.refresh_triangle_1, this.mRefreshTriangle1);
                }

       public void startRefresh() {
           if (isRefreshable()) {
                        if (!isMode(32) || !isMode(2)) {
                   setMode(32);
                   this.mRefreshTriangle1.setVisibility(4);
                   this.mLoading.setVisibility(0);
                   this.mLoadingAnimation.run();
                   if (!isMode(2)) {
                       this.mDataSetObserver.onChanged();
                            }
                        }
                    }
                }

       public void disablePTR() {
           this.mPTRDisabled = true;
                }

       class AnimateLoader implements Runnable {
                    int mRotate;
                    private ImageView mView;

           public AnimateLoader(ImageView v) {
               this.mView = v;
                    }

                    @Override 
           public void run() {
               this.mRotate += 500;
               if (this.mRotate > 10000) {
                   this.mRotate = 0;
                        }
               this.mView.setImageLevel(this.mRotate);
               RefreshableListView.this.postDelayed(this, 32L);
                    }

           public void cancel() {
               Handler h = RefreshableListView.this.getHandler();
               if (h != null) {
                   h.removeCallbacks(this);
                        }
                    }
                }

       public void stopRefresh() {
           RefreshListener l = this.mRefreshListener;
           if (l != null && isMode(32)) {
               this.mLoadingAnimation.cancel();
               int first = getFirstVisiblePosition();
               boolean isAttached = isMode(2);
               ItemPosition ip = l.getFirstItemPosition();
               int top = ip.offset;
               int itemPos = ip.position;
               l.onRefreshFinished();
               int pos = l.getPositionForItemId(ip.itemId);
               unsetMode(32);
               if (pos == itemPos) {
                   l.onRefreshFinishedNoChange();
                   if (first == 0 && isInTouchMode()) {
                       View firstChild = getChildAt(1);
                       int topChild = firstChild != null ? firstChild.getTop() : 0;
                       if (isAttached) {
                           startScroll(topChild);
                                }
                            } else {
                       if (isAttached) {
                           detach();
                                } else {
                           this.mDataSetObserver.onChanged();
                                }
                       setSelectionFromTop(itemPos - 1, top);
                            }
                        } else {
                   l.onRefreshFinishedNewData();
                   if (isAttached) {
                       detach();
                            } else {
                       this.mDataSetObserver.onChanged();
                            }
                   setSelectionFromTop(pos - 1, top);
                        }
               this.mRefreshTriangle1.setVisibility(0);
               this.mLoading.setVisibility(4);
                    }
                }

       private void startScroll(int distance) {
           if (distance > 0) {
               this.mScrollRunnable.scroll(distance);
                    }
                }

       void offsetChildrenTopAndBottom(int offset) {
           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
               View v = getChildAt(i);
               v.offsetTopAndBottom(offset);
                    }
                }

       public void setRefreshListener(RefreshListener listener) {
           if (!isRefreshable() && getHeaderViewsCount() > 0) {
               throw new IllegalStateException("setRefreshListener must be called before addHeaderView.");
                    }
           if (listener != this.mRefreshListener) {
               this.mRefreshListener = listener;
                    }
                }

                @Override 
       public void setAdapter(ListAdapter adapter) {
           if (isRefreshable()) {
               if (adapter != null) {
                   if (this.mAdapter == null || !this.mAdapter.getWrappedAdapter().equals(adapter)) {
                       this.mDataSetObserver = new AdapterDataSetObserver();
                       if (isRefreshable() && this.mFooterView == null) {
                           LayoutInflater inflater = LayoutInflater.from(getContext());
                           View footerView = inflater.inflate(this.mFooterLayoutId, (ViewGroup) null);
                           addFooterView(footerView, null, false);
                           this.mFooterView = footerView.findViewById(R.id.footer_content);
                                }
                       this.mAdapter = new RefreshViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter, this.mDataSetObserver);
                            }
                   this.mItemCount = this.mAdapter.getSuperCount();
                        } else {
                   this.mAdapter = null;
                   this.mItemCount = 0;
                        }
               super.setAdapter((ListAdapter) this.mAdapter);
               return;
                    }
           super.setAdapter(adapter);
                }

       private boolean isRefreshable() {
                    return this.mRefreshListener != null;
                }

                @Override 
       public void addHeaderView(View v, Object data, boolean isSelectable) {
           if (isRefreshable()) {
               ListView.FixedViewInfo info = new ListView.FixedViewInfo();
               info.view = v;
               info.data = data;
               info.isSelectable = isSelectable;
               this.mHeaderViewInfos.add(info);
               return;
                    }
           super.addHeaderView(v, data, isSelectable);
                }

       private void removeFixedViewInfo(View v, ArrayList<ListView.FixedViewInfo> where) {
           int len = where.size();
                    for (int i = 0; i < len; i++) {
               ListView.FixedViewInfo info = where.get(i);
               if (info.view == v) {
                   where.remove(i);
                   return;
                        }
                    }
                }

                @Override 
       public boolean removeHeaderView(View v) {
           if (isRefreshable()) {
               if (this.mHeaderViewInfos.size() > 0) {
                   boolean result = false;
                   if (this.mAdapter != null && this.mAdapter.removeHeader(v)) {
                       if (this.mDataSetObserver != null) {
                           this.mDataSetObserver.onChanged();
                                }
                       result = true;
                            }
                   removeFixedViewInfo(v, this.mHeaderViewInfos);
                   return result;
                        }
               return false;
                    }
           return super.removeHeaderView(v);
                }

                @Override 
       public int getHeaderViewsCount() {
           if (isRefreshable()) {
               if (isMode(34)) {
                   return this.mHeaderViewInfos.size() + 1;
                        }
               return this.mHeaderViewInfos.size();
                    }
           return super.getHeaderViewsCount();
                }

                @Override 
       public void addFooterView(View v, Object data, boolean isSelectable) {
           if (isRefreshable()) {
               ListView.FixedViewInfo info = new ListView.FixedViewInfo();
               info.view = v;
               info.data = data;
               info.isSelectable = isSelectable;
               this.mFooterViewInfos.add(info);
               return;
                    }
           super.addFooterView(v, data, isSelectable);
                }

                @Override 
       public int getFooterViewsCount() {
                    return isRefreshable() ? this.mFooterViewInfos.size() : super.getFooterViewsCount();
                }

                @Override 
       protected int computeVerticalScrollOffset() {
           if (!isSmoothScrollbarEnabled() || !isMode(2)) {
                        return super.computeVerticalScrollOffset();
                    }
           int firstPosition = getFirstVisiblePosition();
           int childCount = getChildCount();
           int count = this.mItemCount - 1;
           if (count <= 0 || firstPosition < 1 || childCount <= 0) {
                        return 0;
                    }
           View view = getChildAt(0);
           int top = view.getTop();
           int height = view.getHeight();
           if (height > 0) {
               return Math.max((((firstPosition - 1) * 100) - ((top * 100) / height)) + ((int) ((getScrollY() / getHeight()) * count * 100.0f)), 0);
                    }
                    return 0;
                }

                @Override 
       protected int computeVerticalScrollRange() {
           if (!isSmoothScrollbarEnabled() || !isMode(2)) {
                        return super.computeVerticalScrollRange();
                    }
           int count = this.mItemCount - 1;
           int scrollY = getScrollY();
           int result = Math.max(count * 100, 0);
           if (scrollY != 0) {
               return result + Math.abs((int) ((scrollY / getHeight()) * count * 100.0f));
                    }
           return result;
                }

       public void refreshMore(boolean start) {
           if (start) {
               this.mFooterView.setVisibility(0);
                    } else {
               this.mFooterView.setVisibility(8);
                    }
                }

       void setMode(int mode) {
           this.mMode |= mode;
                }

       void unsetMode(int mode) {
           this.mMode &= mode ^ (-1);
                }

       protected boolean isMode(int mode) {
                    return (this.mMode & mode) != 0;
                }

       void attach(int y) {
                    int childHeightSpec;
           if (!this.mPTRDisabled) {
               View refreshView = this.mRefreshView;
               ViewGroup.LayoutParams p = refreshView.getLayoutParams();
               boolean addedOnce = isMode(1);
               if (addedOnce) {
                   attachViewToParent(refreshView, 0, p);
                        } else {
                   addViewInLayout(refreshView, 0, p, true);
                   setMode(1);
                        }
               setMode(2);
               boolean needToMeasure = !addedOnce || refreshView.isLayoutRequested();
               if (needToMeasure) {
                   int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), p.width);
                   int lpHeight = p.height;
                   if (lpHeight > 0) {
                       childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
                            } else {
                       childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            }
                   refreshView.measure(childWidthSpec, childHeightSpec);
                        } else {
                   cleanupLayoutState(refreshView);
                        }
               int w = refreshView.getMeasuredWidth();
               int h = refreshView.getMeasuredHeight();
               int childTop = y - h;
               int childrenLeft = getListPaddingLeft();
               if (needToMeasure) {
                   int childRight = childrenLeft + w;
                   int childBottom = childTop + h;
                   refreshView.layout(childrenLeft, childTop, childRight, childBottom);
                        } else {
                   refreshView.offsetLeftAndRight(childrenLeft - refreshView.getLeft());
                   refreshView.offsetTopAndBottom(childTop - refreshView.getTop());
                        }
               this.mDataSetObserver.onChanged();
                    }
                }

       void detach() {
           if (isMode(2)) {
               if (this == this.mRefreshView.getParent()) {
                   detachViewFromParent(this.mRefreshView);
                        }
               unsetMode(30);
               this.mDataSetObserver.onChanged();
                    }
                }

                @Override 
       protected void detachViewsFromParent(int start, int count) {
           if (!isMode(4)) {
               super.detachViewsFromParent(start, count);
                    }
                }

       void requestLastChildLayout() {
                    View firstChild;
           View lastChild = getChildAt(getChildCount() - 1);
           if (lastChild != null && lastChild.getBottom() < (getBottom() - getBottomPaddingOffset()) - getDividerHeight() && (firstChild = getChildAt(0)) != null) {
               int before = firstChild.getTop();
               super.layoutChildren();
               offsetChildrenTopAndBottom(before - firstChild.getTop());
                    }
                }

       public void setTopPosition(int position) {
           this.mTopPos = position;
                }

                @Override 
                @TargetApi(8)
       public boolean scrollTop() {
           int pos = this.mTopPos;
           if (isMode(34)) {
               pos++;
                    }
           if (Build.VERSION.SDK_INT > 7) {
               int firstPos = getFirstVisiblePosition();
               View child = getChildAt(0);
               if (firstPos <= pos && (firstPos != pos || child == null || child.getTop() >= 0)) {
                            return false;
                        }
               if (firstPos > 5) {
                   setSelection(pos);
                        }
               smoothScrollToPosition(pos);
                        return true;
                    }
           setSelection(pos);
                    return true;
                }

       private class ScrollRunnable implements Runnable {
                    private static final int AUTO_SCROLL_DURATION = 450;
                    private int mLastY;

           ScrollRunnable() {
                    }

           void scroll(int distance) {
               this.mLastY = 0;
               RefreshableListView.this.setMode(8);
               RefreshableListView.this.mScroller.abortAnimation();
               RefreshableListView.this.mScroller.startScroll(0, 0, 0, distance, AUTO_SCROLL_DURATION);
               RefreshableListView.this.post(this);
                    }

                    @Override 
           public void run() {
               if (RefreshableListView.this.isMode(10)) {
                   Scroller scroller = RefreshableListView.this.mScroller;
                   boolean more = scroller.timePassed() < AUTO_SCROLL_DURATION && scroller.computeScrollOffset();
                   int y = more ? scroller.getCurrY() : scroller.getFinalY();
                   int delta = this.mLastY - y;
                   this.mLastY = y;
                   RefreshableListView.this.offsetChildrenTopAndBottom(delta);
                   RefreshableListView.this.invalidate();
                   RefreshableListView.this.requestLastChildLayout();
                   if (more) {
                       RefreshableListView.this.post(this);
                       return;
                            }
                   RefreshableListView.this.unsetMode(8);
                   if (RefreshableListView.this.isMode(32)) {
                       RefreshableListView.this.setSelectionFromTop(0, y);
                            } else {
                       RefreshableListView.this.detach();
                            }
                        }
                    }
                }

      private class RefreshViewListAdapter extends HeaderViewListAdapter {
                    private final AdapterDataSetObserver mDataSetObserver;
                    private boolean mRegistered;

          public RefreshViewListAdapter(ArrayList<ListView.FixedViewInfo> headerViewInfos, ArrayList<ListView.FixedViewInfo> footerViewInfos, ListAdapter adapter, AdapterDataSetObserver observer) {
              super(headerViewInfos, footerViewInfos, adapter);
              this.mDataSetObserver = observer;
              registerDataSetObserver();
                    }

                    @Override 
          public void registerDataSetObserver(DataSetObserver observer) {
              this.mDataSetObserver.registerDataSetObserver(observer);
                    }

                    @Override 
          public void unregisterDataSetObserver(DataSetObserver observer) {
              this.mDataSetObserver.unregisterDataSetObserver(observer);
                    }

          public boolean superEnabled(int position) {
                        try {
                  return super.isEnabled(position);
                        } catch (IndexOutOfBoundsException e) {
                  int numHeaders = RefreshableListView.access$000(RefreshableListView.this).size();
                  if (position < numHeaders) {
                      return ((ListView.FixedViewInfo) RefreshableListView.access$000(RefreshableListView.this).get(position)).isSelectable;
                            }
                  int adjPosition = position - numHeaders;
                  int adapterCount = 0;
                  if (RefreshableListView.access$100(RefreshableListView.this) != null && adjPosition < (adapterCount = RefreshableListView.access$100(RefreshableListView.this).getCount())) {
                      return RefreshableListView.access$100(RefreshableListView.this).isEnabled(adjPosition);
                            }
                            try {
                      return ((ListView.FixedViewInfo) RefreshableListView.access$200(RefreshableListView.this).get(adjPosition - adapterCount)).isSelectable;
                            } catch (Exception e2) {
                      CrashUtil.logException(e2);
                      return false;
                            }
                        }
                    }

                    @Override 
          public boolean isEnabled(int position) {
              if (RefreshableListView.this.isMode(34)) {
                            return position > 0 && superEnabled(position + (-1));
                        }
                        try {
                  return superEnabled(position);
                        } catch (IndexOutOfBoundsException e) {
                  int numHeaders = RefreshableListView.access$000(RefreshableListView.this).size();
                  if (position < numHeaders) {
                      return ((ListView.FixedViewInfo) RefreshableListView.access$000(RefreshableListView.this).get(position)).isSelectable;
                            }
                  int adjPosition = position - numHeaders;
                  int adapterCount = 0;
                  if (RefreshableListView.access$100(RefreshableListView.this) != null && adjPosition < (adapterCount = RefreshableListView.access$100(RefreshableListView.this).getCount())) {
                      return RefreshableListView.access$100(RefreshableListView.this).isEnabled(adjPosition);
                            }
                            try {
                      return ((ListView.FixedViewInfo) RefreshableListView.access$200(RefreshableListView.this).get(adjPosition - adapterCount)).isSelectable;
                            } catch (Exception e2) {
                      CrashUtil.logException(e2);
                                return false;
                            }
                        }
                    }

          int getSuperCount() {
              return super.getCount();
                    }

                    @Override 
          public int getCount() {
              return RefreshableListView.this.mItemCount;
                    }

                    @Override 
          public Object getItem(int position) {
              if (RefreshableListView.this.isMode(34)) {
                  if (position == 0) {
                      return null;
                            }
                  return super.getItem(position - 1);
                        }
              return super.getItem(position);
                    }

                    @Override 
          public long getItemId(int position) {
              if (RefreshableListView.this.isMode(34)) {
                  if (position == 0) {
                      return 0L;
                            }
                  return super.getItemId(position - 1);
                        }
              return super.getItemId(position);
                    }

                    @Override 
          public View getView(int position, View view, ViewGroup parent) {
              if (RefreshableListView.this.isMode(34)) {
                  if (position == 0) {
                      return RefreshableListView.this.mRefreshView;
                            }
                  return super.getView(position - 1, view, parent);
                        }
                        try {
                  return super.getView(position, view, parent);
                        } catch (IndexOutOfBoundsException e) {
                  CrashUtil.logException(e);
                  return RefreshableListView.this.mRefreshView;
                        }
                    }

                    @Override 
          public int getItemViewType(int position) {
              if (RefreshableListView.this.isMode(34)) {
                  if (position == 0) {
                      return -1;
                            }
                  return super.getItemViewType(position - 1);
                        }
              return super.getItemViewType(position);
                    }

          public final void registerDataSetObserver() {
              if (!this.mRegistered) {
                  super.registerDataSetObserver(this.mDataSetObserver);
                  this.mRegistered = true;
                        }
                    }

          public final void unregisterDataSetObserver() {
              if (this.mRegistered) {
                  super.unregisterDataSetObserver(this.mDataSetObserver);
                  this.mRegistered = false;
                        }
                    }
                }

                @Override 
      public Parcelable onSaveInstanceState() {
          Parcelable superState = super.onSaveInstanceState();
          SavedState ss = new SavedState(superState);
          ss.refreshable = isRefreshable();
          ss.mode = this.mMode;
          return ss;
                }

                @Override 
      public void onRestoreInstanceState(Parcelable state) {
          SavedState ss = (SavedState) state;
          super.onRestoreInstanceState(ss.getSuperState());
          if (ss.refreshable) {
              this.mMode = ss.mode;
                    }
                }

                @Override 
      protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
          super.onVisibilityChanged(changedView, visibility);
          if (visibility != 0) {
              Log.d("View", "RLV becoming invisible - " + visibility);
                    }
                }

      void updateItemCount() {
          if (isRefreshable()) {
              if (isMode(34)) {
                  this.mItemCount = this.mAdapter.getSuperCount() + 1;
                        } else {
                  this.mItemCount = this.mAdapter.getSuperCount();
                        }
                    }
                }

      private class AdapterDataSetObserver extends DataSetObserver {
                    private final ArrayList<DataSetObserver> mObservers = new ArrayList<>();

          public AdapterDataSetObserver() {
                    }

                    @Override 
          public void onChanged() {
              RefreshableListView.this.updateItemCount();
                        synchronized (this.mObservers) {
                            try {
                                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                          this.mObservers.get(i).onChanged();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
              RefreshableListView.this.requestLayout();
                    }

                    @Override 
          public void onInvalidated() {
              RefreshableListView.this.mItemCount = 0;
                        synchronized (this.mObservers) {
                            try {
                                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                          this.mObservers.get(i).onInvalidated();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
              RefreshableListView.this.requestLayout();
                    }

          public void registerDataSetObserver(DataSetObserver observer) {
              this.mObservers.add(observer);
                    }

          public void unregisterDataSetObserver(DataSetObserver observer) {
              this.mObservers.remove(observer);
                    }
                }

      static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C12931();
                    public int mode;
                    public boolean refreshable;

          public SavedState(Parcelable superState) {
              super(superState);
                    }

          public SavedState(Parcel source) {
              super(source);
              this.refreshable = source.readInt() == 1;
              this.mode = source.readInt();
                    }

                    @Override 
          public void writeToParcel(Parcel dest, int flags) {
              super.writeToParcel(dest, flags);
              dest.writeInt(this.refreshable ? 1 : 0);
              dest.writeInt(this.mode);
                    }

                    
          static class C12931 implements Parcelable.Creator<SavedState> {
              C12931() {
                        }



                        
                        @Override 
              public SavedState createFromParcel(Parcel in) {
                  return new SavedState(in);
                        }

                        
                        @Override 
              public SavedState[] newArray(int size) {
                  return new SavedState[size];
                        }
                    }
                }
            }
