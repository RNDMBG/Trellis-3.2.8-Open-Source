            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AbsListView;
            import android.widget.Adapter;
            import android.widget.ListAdapter;
            import com.twitter.android.widget.RefreshableListView;
            
            
    public class PinnedHeaderListView extends RefreshableListView implements AbsListView.OnScrollListener {
                private int mNavBottom;
                private AbsListView.OnScrollListener mOnScrollListener;
                private PinnedHeaderAdapter mPinnedHeaderAdapter;
                private int mPinnedHeaderHeight;
                private boolean mPinnedHeaderIsVisible;
                private int mPinnedHeaderOffset;
                private View mPinnedHeaderView;
                private ScrollDeltaListener mScrollDeltaListener;
                private View mTrackedChild;
                private int mTrackedChildPrevPosition;
                private int mTrackedChildPrevTop;

                public interface ScrollDeltaListener {
                    void onScroll(int i);
                }


        public PinnedHeaderListView(Context context) {
            super(context);
            this.mPinnedHeaderOffset = 0;
            this.mPinnedHeaderIsVisible = true;
            this.mPinnedHeaderView = null;
            this.mOnScrollListener = null;
            this.mPinnedHeaderAdapter = null;
            this.mPinnedHeaderHeight = 0;
            this.mTrackedChild = null;
            this.mTrackedChildPrevTop = 0;
            this.mTrackedChildPrevPosition = 0;
            this.mScrollDeltaListener = null;
            this.mNavBottom = 0;
            init();
                }

        public PinnedHeaderListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mPinnedHeaderOffset = 0;
            this.mPinnedHeaderIsVisible = true;
            this.mPinnedHeaderView = null;
            this.mOnScrollListener = null;
            this.mPinnedHeaderAdapter = null;
            this.mPinnedHeaderHeight = 0;
            this.mTrackedChild = null;
            this.mTrackedChildPrevTop = 0;
            this.mTrackedChildPrevPosition = 0;
            this.mScrollDeltaListener = null;
            this.mNavBottom = 0;
            init();
                }

        public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mPinnedHeaderOffset = 0;
            this.mPinnedHeaderIsVisible = true;
            this.mPinnedHeaderView = null;
            this.mOnScrollListener = null;
            this.mPinnedHeaderAdapter = null;
            this.mPinnedHeaderHeight = 0;
            this.mTrackedChild = null;
            this.mTrackedChildPrevTop = 0;
            this.mTrackedChildPrevPosition = 0;
            this.mScrollDeltaListener = null;
            this.mNavBottom = 0;
            init();
                }

        private void init() {
            super.setOnScrollListener(this);
                }

                @Override 
        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (isTouchInPinnedHeader(event)) {
                return true;
                    }
                    return super.onInterceptTouchEvent(event);
                }

        public boolean isTouchInPinnedHeader(MotionEvent event) {
                    return this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && event.getY() > ((float) this.mNavBottom) && event.getY() < ((float) ((this.mPinnedHeaderOffset + this.mPinnedHeaderHeight) + this.mNavBottom));
                }

        public boolean isAbsoluteTouchInPinnedHeader(MotionEvent event) {
            int[] absoluteLoc = new int[2];
            getLocationInWindow(absoluteLoc);
                    return this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && event.getRawY() > ((float) (this.mNavBottom + absoluteLoc[1])) && event.getRawY() < ((float) (((this.mNavBottom + this.mPinnedHeaderOffset) + this.mPinnedHeaderHeight) + absoluteLoc[1]));
                }

        public int getPinnedHeaderBottomAbs() {
            int[] absoluteLoc = new int[2];
            getLocationInWindow(absoluteLoc);
            return this.mPinnedHeaderOffset + this.mPinnedHeaderHeight + this.mNavBottom + absoluteLoc[1];
                }

                @Override 
        public boolean onTouchEvent(MotionEvent event) {
            if (!isTouchInPinnedHeader(event)) {
                        return super.onTouchEvent(event);
                    }
            event.setLocation(event.getX(), (event.getY() - this.mPinnedHeaderOffset) - this.mNavBottom);
            return this.mPinnedHeaderView.dispatchTouchEvent(event);
                }

                @Override 
        public void dispatchDraw(@NonNull Canvas c) {
            super.dispatchDraw(c);
            if (this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && !isMode(2) && this.mPinnedHeaderOffset > (-this.mPinnedHeaderHeight)) {
                c.save();
                c.clipRect(0, this.mPinnedHeaderOffset + this.mNavBottom, getWidth(), this.mPinnedHeaderHeight + this.mPinnedHeaderOffset + this.mNavBottom);
                c.translate(0.0f, this.mPinnedHeaderOffset + this.mNavBottom);
                this.mPinnedHeaderView.draw(c);
                c.restore();
                    }
                }

                @Override 
       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
           measureHeader(this.mPinnedHeaderView);
                }

                @Override 
       public void setAdapter(ListAdapter adapter) {
           super.setAdapter(adapter);
           if (adapter instanceof PinnedHeaderAdapter) {
               setPinnedHeaderAdapter((PinnedHeaderAdapter) adapter);
                    }
                }

       public void setPinnedHeaderAdapter(PinnedHeaderAdapter adapter) {
           this.mPinnedHeaderAdapter = adapter;
                }

                @Override 
       public void setOnScrollListener(AbsListView.OnScrollListener listener) {
           this.mOnScrollListener = listener;
                }

                @Override 
       public void onScrollStateChanged(AbsListView view, int scrollState) {
           if (this.mOnScrollListener != null) {
               this.mOnScrollListener.onScrollStateChanged(view, scrollState);
                    }
                }

                @Override 
       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    PinnedHeaderAdapter.PinnedHeaderStatus headerStatus;
           if (this.mPinnedHeaderAdapter != null && (headerStatus = this.mPinnedHeaderAdapter.getPinnedHeaderStatus(this.mNavBottom)) != null) {
               this.mPinnedHeaderOffset = headerStatus.pinnedHeaderOffset;
               this.mPinnedHeaderIsVisible = headerStatus.pinnedHeaderIsVisible;
               if (headerStatus.shouldRequestNewView) {
                   this.mPinnedHeaderHeight = this.mPinnedHeaderAdapter.getPinnedHeaderHeight();
                   this.mPinnedHeaderView = this.mPinnedHeaderAdapter.getPinnedHeaderView(this.mPinnedHeaderView);
                   if (this.mPinnedHeaderView != null) {
                       ensureHeaderHasLayoutParams(this.mPinnedHeaderView);
                       measureHeader(this.mPinnedHeaderView);
                       this.mPinnedHeaderAdapter.layoutPinnedHeader(this.mPinnedHeaderView, 0, this.mPinnedHeaderOffset + this.mNavBottom, getWidth(), this.mNavBottom + this.mPinnedHeaderOffset + this.mPinnedHeaderHeight);
                            }
                        }
                    }
           if (this.mOnScrollListener != null) {
               this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                    }
                }

       private void ensureHeaderHasLayoutParams(View header) {
           ViewGroup.LayoutParams lp = header.getLayoutParams();
           if (lp == null) {
               header.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
                    } else if (lp.height == -1) {
               lp.height = -2;
               header.setLayoutParams(lp);
                    }
                }

       private void measureHeader(View header) {
           if (header != null) {
               int width = getMeasuredWidth();
               int parentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
               int parentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPinnedHeaderHeight, 1073741824);
               measureChild(header, parentWidthMeasureSpec, parentHeightMeasureSpec);
                    }
                }

                @Override 
       protected void onScrollChanged(int l, int t, int oldl, int oldt) {
           super.onScrollChanged(l, t, oldl, oldt);
           if (this.mTrackedChild == null) {
               if (getChildCount() > 0) {
                   this.mTrackedChild = getChildInTheMiddle();
                   this.mTrackedChildPrevTop = this.mTrackedChild.getTop();
                   if (this.mTrackedChild.getParent() != null) {
                       this.mTrackedChildPrevPosition = getPositionForView(this.mTrackedChild);
                       return;
                            } else {
                       this.mTrackedChildPrevPosition = -1;
                       return;
                            }
                        }
               return;
                    }
           boolean childIsSafeToTrack = this.mTrackedChild.getParent() == this && getPositionForView(this.mTrackedChild) == this.mTrackedChildPrevPosition;
           if (childIsSafeToTrack) {
               int top = this.mTrackedChild.getTop();
               if (this.mScrollDeltaListener != null) {
                   int deltaY = top - this.mTrackedChildPrevTop;
                   this.mScrollDeltaListener.onScroll(deltaY);
                        }
               this.mTrackedChildPrevTop = top;
               return;
                    }
           this.mTrackedChild = null;
                }

       public void untrackScrollawayChild() {
           this.mTrackedChild = null;
                }

       private View getChildInTheMiddle() {
           return getChildAt(getChildCount() / 2);
                }

       public void setScrollDeltaListener(ScrollDeltaListener listener) {
           this.mScrollDeltaListener = listener;
                }

       public void setNavBottom(int bottom) {
           this.mNavBottom = bottom;
                }

       public int getPositionForPixelLocation(int y) {
                    for (int i = 0; i < getChildCount(); i++) {
               View child = getChildAt(i);
               if (child != null && child.getTop() <= y && child.getBottom() >= y) {
                   return i;
                        }
                    }
           return 0;
                }
            }
