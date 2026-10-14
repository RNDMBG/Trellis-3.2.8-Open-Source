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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class PinnedHeaderListView extends RefreshableListView implements AbsListView.OnScrollListener {
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


/* 33 */        public PinnedHeaderListView(Context context) {
/* 34 */            super(context);
/* 20 */            this.mPinnedHeaderOffset = 0;
/* 21 */            this.mPinnedHeaderIsVisible = true;
/* 22 */            this.mPinnedHeaderView = null;
/* 23 */            this.mOnScrollListener = null;
/* 24 */            this.mPinnedHeaderAdapter = null;
/* 26 */            this.mPinnedHeaderHeight = 0;
/* 27 */            this.mTrackedChild = null;
/* 28 */            this.mTrackedChildPrevTop = 0;
/* 29 */            this.mTrackedChildPrevPosition = 0;
/* 30 */            this.mScrollDeltaListener = null;
/* 31 */            this.mNavBottom = 0;
/* 35 */            init();
                }

/* 38 */        public PinnedHeaderListView(Context context, AttributeSet attrs) {
/* 39 */            super(context, attrs);
/* 20 */            this.mPinnedHeaderOffset = 0;
/* 21 */            this.mPinnedHeaderIsVisible = true;
/* 22 */            this.mPinnedHeaderView = null;
/* 23 */            this.mOnScrollListener = null;
/* 24 */            this.mPinnedHeaderAdapter = null;
/* 26 */            this.mPinnedHeaderHeight = 0;
/* 27 */            this.mTrackedChild = null;
/* 28 */            this.mTrackedChildPrevTop = 0;
/* 29 */            this.mTrackedChildPrevPosition = 0;
/* 30 */            this.mScrollDeltaListener = null;
/* 31 */            this.mNavBottom = 0;
/* 40 */            init();
                }

/* 43 */        public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
/* 44 */            super(context, attrs, defStyle);
/* 20 */            this.mPinnedHeaderOffset = 0;
/* 21 */            this.mPinnedHeaderIsVisible = true;
/* 22 */            this.mPinnedHeaderView = null;
/* 23 */            this.mOnScrollListener = null;
/* 24 */            this.mPinnedHeaderAdapter = null;
/* 26 */            this.mPinnedHeaderHeight = 0;
/* 27 */            this.mTrackedChild = null;
/* 28 */            this.mTrackedChildPrevTop = 0;
/* 29 */            this.mTrackedChildPrevPosition = 0;
/* 30 */            this.mScrollDeltaListener = null;
/* 31 */            this.mNavBottom = 0;
/* 45 */            init();
                }

/* 48 */        private void init() {
/* 49 */            super.setOnScrollListener(this);
                }

                @Override // com.twitter.android.widget.RefreshableListView, android.widget.AbsListView, android.view.ViewGroup
/* 53 */        public boolean onInterceptTouchEvent(MotionEvent event) {
/* 54 */            if (isTouchInPinnedHeader(event)) {
/* 55 */                return true;
                    }
                    return super.onInterceptTouchEvent(event);
                }

/* 60 */        public boolean isTouchInPinnedHeader(MotionEvent event) {
                    return this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && event.getY() > ((float) this.mNavBottom) && event.getY() < ((float) ((this.mPinnedHeaderOffset + this.mPinnedHeaderHeight) + this.mNavBottom));
                }

/* 67 */        public boolean isAbsoluteTouchInPinnedHeader(MotionEvent event) {
/* 68 */            int[] absoluteLoc = new int[2];
/* 69 */            getLocationInWindow(absoluteLoc);
                    return this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && event.getRawY() > ((float) (this.mNavBottom + absoluteLoc[1])) && event.getRawY() < ((float) (((this.mNavBottom + this.mPinnedHeaderOffset) + this.mPinnedHeaderHeight) + absoluteLoc[1]));
                }

/* 76 */        public int getPinnedHeaderBottomAbs() {
/* 77 */            int[] absoluteLoc = new int[2];
/* 78 */            getLocationInWindow(absoluteLoc);
/* 79 */            return this.mPinnedHeaderOffset + this.mPinnedHeaderHeight + this.mNavBottom + absoluteLoc[1];
                }

                @Override // com.twitter.android.widget.RefreshableListView, android.widget.AbsListView, android.view.View
/* 83 */        public boolean onTouchEvent(MotionEvent event) {
/* 84 */            if (!isTouchInPinnedHeader(event)) {
                        return super.onTouchEvent(event);
                    }
/* 85 */            event.setLocation(event.getX(), (event.getY() - this.mPinnedHeaderOffset) - this.mNavBottom);
/* 86 */            return this.mPinnedHeaderView.dispatchTouchEvent(event);
                }

                @Override // co.vine.android.views.SdkListView, android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
/* 91 */        public void dispatchDraw(@NonNull Canvas c) {
/* 92 */            super.dispatchDraw(c);
/* 93 */            if (this.mPinnedHeaderIsVisible && this.mPinnedHeaderView != null && !isMode(2) && this.mPinnedHeaderOffset > (-this.mPinnedHeaderHeight)) {
/* 94 */                c.save();
/* 95 */                c.clipRect(0, this.mPinnedHeaderOffset + this.mNavBottom, getWidth(), this.mPinnedHeaderHeight + this.mPinnedHeaderOffset + this.mNavBottom);
/* 96 */                c.translate(0.0f, this.mPinnedHeaderOffset + this.mNavBottom);
/* 97 */                this.mPinnedHeaderView.draw(c);
/* 98 */                c.restore();
                    }
                }

                @Override // com.twitter.android.widget.RefreshableListView, android.widget.ListView, android.widget.AbsListView, android.view.View
/* 102 */       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 103 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 104 */           measureHeader(this.mPinnedHeaderView);
                }

                @Override // com.twitter.android.widget.RefreshableListView, android.widget.ListView, android.widget.AbsListView
/* 107 */       public void setAdapter(ListAdapter adapter) {
/* 108 */           super.setAdapter(adapter);
/* 109 */           if (adapter instanceof PinnedHeaderAdapter) {
/* 110 */               setPinnedHeaderAdapter((PinnedHeaderAdapter) adapter);
                    }
                }

/* 114 */       public void setPinnedHeaderAdapter(PinnedHeaderAdapter adapter) {
/* 115 */           this.mPinnedHeaderAdapter = adapter;
                }

                @Override // android.widget.AbsListView
/* 118 */       public void setOnScrollListener(AbsListView.OnScrollListener listener) {
/* 119 */           this.mOnScrollListener = listener;
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 123 */       public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 124 */           if (this.mOnScrollListener != null) {
/* 125 */               this.mOnScrollListener.onScrollStateChanged(view, scrollState);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 130 */       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    PinnedHeaderAdapter.PinnedHeaderStatus headerStatus;
/* 131 */           if (this.mPinnedHeaderAdapter != null && (headerStatus = this.mPinnedHeaderAdapter.getPinnedHeaderStatus(this.mNavBottom)) != null) {
/* 138 */               this.mPinnedHeaderOffset = headerStatus.pinnedHeaderOffset;
/* 139 */               this.mPinnedHeaderIsVisible = headerStatus.pinnedHeaderIsVisible;
/* 141 */               if (headerStatus.shouldRequestNewView) {
/* 142 */                   this.mPinnedHeaderHeight = this.mPinnedHeaderAdapter.getPinnedHeaderHeight();
/* 143 */                   this.mPinnedHeaderView = this.mPinnedHeaderAdapter.getPinnedHeaderView(this.mPinnedHeaderView);
/* 145 */                   if (this.mPinnedHeaderView != null) {
/* 146 */                       ensureHeaderHasLayoutParams(this.mPinnedHeaderView);
/* 147 */                       measureHeader(this.mPinnedHeaderView);
/* 148 */                       this.mPinnedHeaderAdapter.layoutPinnedHeader(this.mPinnedHeaderView, 0, this.mPinnedHeaderOffset + this.mNavBottom, getWidth(), this.mNavBottom + this.mPinnedHeaderOffset + this.mPinnedHeaderHeight);
                            }
                        }
                    }
/* 156 */           if (this.mOnScrollListener != null) {
/* 157 */               this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                    }
                }

/* 161 */       private void ensureHeaderHasLayoutParams(View header) {
/* 162 */           ViewGroup.LayoutParams lp = header.getLayoutParams();
/* 163 */           if (lp == null) {
/* 165 */               header.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
                    } else if (lp.height == -1) {
/* 167 */               lp.height = -2;
/* 168 */               header.setLayoutParams(lp);
                    }
                }

/* 172 */       private void measureHeader(View header) {
/* 173 */           if (header != null) {
/* 174 */               int width = getMeasuredWidth();
/* 175 */               int parentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
/* 177 */               int parentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPinnedHeaderHeight, 1073741824);
/* 179 */               measureChild(header, parentWidthMeasureSpec, parentHeightMeasureSpec);
                    }
                }

                @Override // android.view.View
/* 185 */       protected void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 186 */           super.onScrollChanged(l, t, oldl, oldt);
/* 187 */           if (this.mTrackedChild == null) {
/* 188 */               if (getChildCount() > 0) {
/* 189 */                   this.mTrackedChild = getChildInTheMiddle();
/* 190 */                   this.mTrackedChildPrevTop = this.mTrackedChild.getTop();
/* 191 */                   if (this.mTrackedChild.getParent() != null) {
/* 192 */                       this.mTrackedChildPrevPosition = getPositionForView(this.mTrackedChild);
/* 210 */                       return;
                            } else {
/* 194 */                       this.mTrackedChildPrevPosition = -1;
/* 210 */                       return;
                            }
                        }
/* 210 */               return;
                    }
/* 198 */           boolean childIsSafeToTrack = this.mTrackedChild.getParent() == this && getPositionForView(this.mTrackedChild) == this.mTrackedChildPrevPosition;
/* 199 */           if (childIsSafeToTrack) {
/* 200 */               int top = this.mTrackedChild.getTop();
/* 201 */               if (this.mScrollDeltaListener != null) {
/* 202 */                   int deltaY = top - this.mTrackedChildPrevTop;
/* 203 */                   this.mScrollDeltaListener.onScroll(deltaY);
                        }
/* 205 */               this.mTrackedChildPrevTop = top;
/* 210 */               return;
                    }
/* 207 */           this.mTrackedChild = null;
                }

/* 212 */       public void untrackScrollawayChild() {
/* 213 */           this.mTrackedChild = null;
                }

/* 216 */       private View getChildInTheMiddle() {
/* 217 */           return getChildAt(getChildCount() / 2);
                }

/* 224 */       public void setScrollDeltaListener(ScrollDeltaListener listener) {
/* 225 */           this.mScrollDeltaListener = listener;
                }

/* 228 */       public void setNavBottom(int bottom) {
/* 229 */           this.mNavBottom = bottom;
                }

/* 232 */       public int getPositionForPixelLocation(int y) {
                    for (int i = 0; i < getChildCount(); i++) {
/* 234 */               View child = getChildAt(i);
/* 235 */               if (child != null && child.getTop() <= y && child.getBottom() >= y) {
/* 239 */                   return i;
                        }
                    }
/* 239 */           return 0;
                }
            }
