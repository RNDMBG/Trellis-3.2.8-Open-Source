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
            import co.vine.android.C0257R;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.SdkListView;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class RefreshableListView extends SdkListView implements TopScrollable {
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

/* 58 */        static /* synthetic */ ArrayList access$000(RefreshableListView x0) {
/* 59 */            return x0.mHeaderViewInfos;
                }

/* 58 */        static /* synthetic */ RefreshViewListAdapter access$100(RefreshableListView x0) {
/* 59 */            return x0.mAdapter;
                }

/* 58 */        static /* synthetic */ ArrayList access$200(RefreshableListView x0) {
/* 59 */            return x0.mFooterViewInfos;
                }


/* 117 */       public RefreshableListView(Context context) {
/* 118 */           this(context, null);
                }

/* 121 */       public RefreshableListView(Context context, AttributeSet attrs) {
/* 122 */           this(context, attrs, C0257R.attr.refreshableListViewStyle);
                }

/* 125 */       public RefreshableListView(Context context, AttributeSet attrs, int defStyle) {
/* 126 */           super(context, attrs, defStyle);
/* 112 */           this.mHeaderViewInfos = new ArrayList<>();
/* 113 */           this.mFooterViewInfos = new ArrayList<>();
/* 128 */           TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.RefreshableListView, defStyle, 0);
/* 131 */           this.mPixelScale = getResources().getDisplayMetrics().density;
/* 133 */           this.mPullBackgroundColor = a.getColor(C0257R.styleable.RefreshableListView_pullBackgroundColor, -1);
/* 136 */           Drawable divider = a.getDrawable(C0257R.styleable.RefreshableListView_pullDivider);
/* 138 */           this.mScroller = new Scroller(context);
/* 139 */           this.mScrollRunnable = new ScrollRunnable();
/* 141 */           LayoutInflater inflater = LayoutInflater.from(context);
/* 142 */           int layout = a.getResourceId(C0257R.styleable.RefreshableListView_refreshHeader, C0257R.layout.pull_to_refresh);
/* 143 */           if (layout == 0) {
/* 144 */               layout = C0257R.layout.pull_to_refresh;
                    }
/* 143 */           RelativeLayout v = (RelativeLayout) inflater.inflate(layout, (ViewGroup) this, false);
/* 145 */           if (divider != null) {
/* 146 */               View dividerView = v.findViewById(C0257R.id.refresh_divider);
/* 147 */               dividerView.setBackgroundDrawable(divider);
/* 148 */               dividerView.setVisibility(0);
                    }
/* 151 */           this.mLoading = (ImageView) v.findViewById(C0257R.id.refresh_loading);
/* 153 */           this.mRefreshTriangle1 = (ImageView) v.findViewById(C0257R.id.refresh_triangle_1);
/* 157 */           this.mRefreshView = v;
/* 159 */           ViewConfiguration config = ViewConfiguration.get(getContext());
/* 160 */           this.mTouchSlop = config.getScaledTouchSlop();
/* 163 */           ViewGroup.LayoutParams p = v.getLayoutParams();
/* 164 */           v.setLayoutParams(new AbsListView.LayoutParams(p.width, p.height, -1));
/* 166 */           int footerLayout = a.getResourceId(C0257R.styleable.RefreshableListView_refreshFooter, C0257R.layout.list_footer_view);
/* 167 */           this.mFooterLayoutId = footerLayout == 0 ? C0257R.layout.list_footer_view : footerLayout;
/* 168 */           this.mLoadingAnimation = new AnimateLoader(this.mLoading);
/* 170 */           a.recycle();
                }

/* 174 */       public void setRefreshHeader(View header, int height) {
/* 175 */           if (this.mLoading != null) {
/* 176 */               this.mLoading.setVisibility(8);
                    }
/* 178 */           if (this.mRefreshTriangle1 != null) {
/* 179 */               this.mRefreshTriangle1.setVisibility(8);
                    }
/* 181 */           this.mLoading = (ImageView) header.findViewById(C0257R.id.refresh_loading);
/* 182 */           this.mRefreshTriangle1 = (ImageView) header.findViewById(C0257R.id.refresh_triangle_1);
/* 183 */           this.mLoadingAnimation = new AnimateLoader(this.mLoading);
/* 184 */           this.mUnderNavRefreshHeader = header;
/* 185 */           header.setBackgroundColor(this.mPullBackgroundColor);
/* 187 */           this.mRefreshView.setPadding(0, height, 0, this.mRefreshView.getPaddingBottom());
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
/* 191 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 192 */           updateItemCount();
/* 193 */           this.mWidthMeasureSpec = widthMeasureSpec;
/* 194 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
/* 201 */       protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
/* 202 */           if (isRefreshable() && !this.mAttachedToWindow && gainFocus && getSelectedItemPosition() < 0 && !isInTouchMode() && this.mAdapter != null) {
/* 204 */               updateItemCount();
                    }
/* 206 */           super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
                }

                @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
/* 210 */       protected void onAttachedToWindow() {
/* 211 */           if (isRefreshable() && this.mAdapter != null) {
/* 212 */               this.mAdapter.registerDataSetObserver();
/* 213 */               updateItemCount();
                    }
/* 215 */           this.mAttachedToWindow = true;
/* 216 */           super.onAttachedToWindow();
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
/* 220 */       protected void onDetachedFromWindow() {
/* 221 */           if (isRefreshable()) {
/* 222 */               if (this.mAdapter != null) {
/* 223 */                   this.mAdapter.unregisterDataSetObserver();
                        }
/* 225 */               removeCallbacks(this.mScrollRunnable);
                    }
/* 227 */           this.mAttachedToWindow = false;
/* 228 */           super.onDetachedFromWindow();
                }

                @Override // android.widget.ListView, android.widget.AbsListView
/* 232 */       protected void layoutChildren() {
/* 233 */           if (!isRefreshable()) {
/* 234 */               super.layoutChildren();
                    } else if (!isMode(12)) {
/* 244 */               if (this.mItemCount == 0) {
/* 245 */                   clearRecycledState(this.mHeaderViewInfos);
/* 246 */                   clearRecycledState(this.mFooterViewInfos);
                        }
/* 248 */               super.layoutChildren();
                    }
                }

/* 251 */       private void clearRecycledState(ArrayList<ListView.FixedViewInfo> infos) {
/* 252 */           if (infos != null) {
                        for (ListView.FixedViewInfo info : infos) {
/* 256 */                   View child = info.view;
/* 257 */                   AbsListView.LayoutParams p = (AbsListView.LayoutParams) child.getLayoutParams();
/* 258 */                   if (p != null) {
/* 259 */                       info.view.setLayoutParams(new AbsListView.LayoutParams(p.width, p.height, -2));
                            }
                        }
                    }
                }

                @Override // android.view.View
/* 267 */       protected void onDraw(Canvas canvas) {
                    View child;
/* 268 */           if (isRefreshable() && (child = getChildAt(0)) != null && child.equals(this.mRefreshView)) {
/* 271 */               int left = getScrollX();
/* 272 */               canvas.save();
/* 273 */               canvas.clipRect(left, getScrollY(), getWidth() + left, child.getBottom());
/* 274 */               canvas.drawColor(this.mPullBackgroundColor);
/* 275 */               canvas.restore();
                    }
/* 278 */           super.onDraw(canvas);
                }

                @Override // android.widget.AbsListView, android.view.ViewGroup
/* 282 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
                    View topChild;
/* 283 */           if (!isRefreshable()) {
/* 284 */               return super.onInterceptTouchEvent(ev);
                    }
/* 287 */           int y = (int) ev.getY();
/* 288 */           int deltaY = y - this.mDownY;
                    switch (ev.getAction()) {
                        case 0:
/* 292 */                   this.mDownY = y;
/* 293 */                   this.mMoveY = Integer.MIN_VALUE;
                            break;
                        case 2:
/* 297 */                   if (isMode(32)) {
/* 298 */                       this.mMoveY = y;
                            } else {
/* 301 */                       boolean attached = isMode(2);
/* 304 */                       if (attached) {
/* 305 */                           topChild = getChildAt(1);
                                } else {
/* 307 */                           topChild = getChildAt(0);
                                }
/* 310 */                       int top = topChild != null ? topChild.getTop() : 0;
/* 311 */                       this.mMoveY = y;
/* 313 */                       if (getFirstVisiblePosition() == 0 && top >= getListPaddingTop() && deltaY > this.mTouchSlop) {
                                    return true;
                                }
                            }
                            break;
                    }
                    try {
/* 325 */               return super.onInterceptTouchEvent(ev);
                    } catch (IndexOutOfBoundsException | NullPointerException e) {
/* 327 */               silence(e, ev);
                        return false;
                    }
                }

/* 332 */       private void silence(Exception e, MotionEvent ev) {
/* 333 */           CrashUtil.logException(e, "Event was: {}, x: {}, y: {}.", Integer.valueOf(ev.getAction()), Float.valueOf(ev.getX()), Float.valueOf(ev.getY()));
                }

/* 336 */       public void setViewYOffset(int offset) {
/* 337 */           int pixels = (int) ((offset * this.mPixelScale) + 0.5f);
/* 338 */           this.mRefreshView.setPadding(0, this.mRefreshView.getPaddingTop(), 0, pixels);
                }

/* 341 */       public void setPullToRefreshBackgroundColor(int color) {
/* 342 */           this.mPullBackgroundColor = color;
/* 343 */           if (this.mUnderNavRefreshHeader != null) {
/* 344 */               this.mUnderNavRefreshHeader.setBackgroundColor(color);
                    }
                }

                @Override // android.widget.AbsListView, android.view.View
/* 349 */       public boolean onTouchEvent(MotionEvent ev) {
                    int diff;
                    View topChild;
/* 350 */           if (!isRefreshable()) {
/* 351 */               return super.onTouchEvent(ev);
                    }
/* 354 */           int action = ev.getAction() & 255;
/* 356 */           int y = (int) ev.getY();
/* 357 */           int deltaY = y - this.mDownY;
                    switch (action) {
                        case 0:
/* 361 */                   this.mDownY = y;
/* 362 */                   this.mMoveY = Integer.MIN_VALUE;
/* 363 */                   setMode(64);
                            break;
                        case 1:
/* 368 */                   unsetMode(20);
/* 370 */                   if (!isMode(32) && isMode(2)) {
/* 371 */                       View topChild2 = getChildAt(0);
/* 372 */                       int firstPos = getFirstVisiblePosition();
/* 374 */                       if (firstPos == 0 && topChild2 != null) {
/* 375 */                           int top = topChild2.getTop();
/* 376 */                           int bottom = topChild2.getBottom();
/* 377 */                           if (top > 0) {
/* 379 */                               this.mRefreshListener.onRefreshReleased(true);
/* 380 */                               startScroll(top);
                                    } else if (topChild2.getBottom() > 0) {
/* 382 */                               this.mRefreshListener.onRefreshReleased(false);
/* 383 */                               startScroll(getDividerHeight() + bottom);
                                    }
                                }
                            }
                            break;
                        case 2:
/* 390 */                   if (isMode(32)) {
/* 391 */                       this.mMoveY = y;
                            } else {
/* 394 */                       if (this.mMoveY != Integer.MIN_VALUE) {
/* 395 */                           diff = y - this.mMoveY;
                                } else {
/* 397 */                           diff = deltaY;
                                }
/* 401 */                       boolean attached = isMode(2);
/* 404 */                       if (attached) {
/* 405 */                           topChild = getChildAt(1);
                                } else {
/* 407 */                           topChild = getChildAt(0);
                                }
/* 410 */                       int top2 = topChild != null ? topChild.getTop() : 0;
/* 411 */                       boolean up = y >= this.mMoveY;
/* 413 */                       this.mMoveY = y;
/* 415 */                       if (getFirstVisiblePosition() == 0 && top2 >= getListPaddingTop() && deltaY > this.mTouchSlop) {
/* 417 */                           requestDisallowInterceptTouchEvent(true);
/* 420 */                           if (isMode(64)) {
/* 421 */                               super.onTouchEvent(ev);
/* 422 */                               unsetMode(64);
                                    }
/* 426 */                           setChildrenDrawingCacheEnabled(false);
/* 427 */                           setChildrenDrawnWithCacheEnabled(false);
/* 429 */                           if (attached) {
/* 431 */                               offsetChildrenTopAndBottom((int) (diff * 0.5f));
/* 432 */                               invalidate();
/* 433 */                               View child = getChildAt(0);
/* 434 */                               if (child != null && child.getTop() >= 0) {
/* 435 */                                   if (up && !isMode(16)) {
/* 436 */                                       setMode(16);
/* 437 */                                       this.mRefreshListener.onRefreshPulled();
                                            }
                                        } else if (!up && isMode(16)) {
/* 441 */                                   unsetMode(16);
/* 442 */                                   this.mRefreshListener.onRefreshCancelled();
                                        }
                                    } else {
/* 446 */                               attach(top2 - getDividerHeight());
                                    }
/* 449 */                           setMode(4);
/* 451 */                           return true;
                                }
/* 453 */                       detach();
                            }
                            break;
                    }
                    try {
/* 462 */               return super.onTouchEvent(ev);
                    } catch (IndexOutOfBoundsException | NullPointerException e) {
/* 464 */               silence(e, ev);
/* 465 */               return false;
                    }
                }

/* 469 */       public void colorizePTR(int color) {
/* 470 */           ViewUtil.fillColor(getResources(), color, C0257R.id.refresh_loading, this.mLoading);
/* 471 */           ViewUtil.fillColor(getResources(), color, C0257R.id.refresh_triangle_1, this.mRefreshTriangle1);
                }

/* 477 */       public void startRefresh() {
/* 478 */           if (isRefreshable()) {
                        if (!isMode(32) || !isMode(2)) {
/* 481 */                   setMode(32);
/* 482 */                   this.mRefreshTriangle1.setVisibility(4);
/* 486 */                   this.mLoading.setVisibility(0);
/* 487 */                   this.mLoadingAnimation.run();
/* 493 */                   if (!isMode(2)) {
/* 494 */                       this.mDataSetObserver.onChanged();
                            }
                        }
                    }
                }

/* 498 */       public void disablePTR() {
/* 499 */           this.mPTRDisabled = true;
                }

/* 504 */       class AnimateLoader implements Runnable {
                    int mRotate;
                    private ImageView mView;

/* 505 */           public AnimateLoader(ImageView v) {
/* 507 */               this.mView = v;
                    }

                    @Override // java.lang.Runnable
/* 511 */           public void run() {
/* 512 */               this.mRotate += 500;
/* 513 */               if (this.mRotate > 10000) {
/* 514 */                   this.mRotate = 0;
                        }
/* 516 */               this.mView.setImageLevel(this.mRotate);
/* 517 */               RefreshableListView.this.postDelayed(this, 32L);
                    }

/* 520 */           public void cancel() {
/* 521 */               Handler h = RefreshableListView.this.getHandler();
/* 522 */               if (h != null) {
/* 523 */                   h.removeCallbacks(this);
                        }
                    }
                }

/* 531 */       public void stopRefresh() {
/* 532 */           RefreshListener l = this.mRefreshListener;
/* 534 */           if (l != null && isMode(32)) {
/* 535 */               this.mLoadingAnimation.cancel();
/* 537 */               int first = getFirstVisiblePosition();
/* 538 */               boolean isAttached = isMode(2);
/* 540 */               ItemPosition ip = l.getFirstItemPosition();
/* 541 */               int top = ip.offset;
/* 542 */               int itemPos = ip.position;
/* 545 */               l.onRefreshFinished();
/* 548 */               int pos = l.getPositionForItemId(ip.itemId);
/* 551 */               unsetMode(32);
/* 554 */               if (pos == itemPos) {
/* 555 */                   l.onRefreshFinishedNoChange();
/* 558 */                   if (first == 0 && isInTouchMode()) {
/* 559 */                       View firstChild = getChildAt(1);
/* 560 */                       int topChild = firstChild != null ? firstChild.getTop() : 0;
/* 561 */                       if (isAttached) {
/* 562 */                           startScroll(topChild);
                                }
                            } else {
/* 566 */                       if (isAttached) {
/* 567 */                           detach();
                                } else {
/* 569 */                           this.mDataSetObserver.onChanged();
                                }
/* 572 */                       setSelectionFromTop(itemPos - 1, top);
                            }
                        } else {
/* 575 */                   l.onRefreshFinishedNewData();
/* 579 */                   if (isAttached) {
/* 580 */                       detach();
                            } else {
/* 582 */                       this.mDataSetObserver.onChanged();
                            }
/* 584 */                   setSelectionFromTop(pos - 1, top);
                        }
/* 586 */               this.mRefreshTriangle1.setVisibility(0);
/* 589 */               this.mLoading.setVisibility(4);
                    }
                }

/* 593 */       private void startScroll(int distance) {
/* 594 */           if (distance > 0) {
/* 595 */               this.mScrollRunnable.scroll(distance);
                    }
                }

/* 604 */       void offsetChildrenTopAndBottom(int offset) {
/* 605 */           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
/* 609 */               View v = getChildAt(i);
/* 610 */               v.offsetTopAndBottom(offset);
                    }
                }

/* 619 */       public void setRefreshListener(RefreshListener listener) {
/* 620 */           if (!isRefreshable() && getHeaderViewsCount() > 0) {
/* 621 */               throw new IllegalStateException("setRefreshListener must be called before addHeaderView.");
                    }
/* 624 */           if (listener != this.mRefreshListener) {
/* 625 */               this.mRefreshListener = listener;
                    }
                }

                @Override // android.widget.ListView, android.widget.AbsListView
/* 630 */       public void setAdapter(ListAdapter adapter) {
/* 631 */           if (isRefreshable()) {
/* 632 */               if (adapter != null) {
/* 633 */                   if (this.mAdapter == null || !this.mAdapter.getWrappedAdapter().equals(adapter)) {
/* 634 */                       this.mDataSetObserver = new AdapterDataSetObserver();
/* 636 */                       if (isRefreshable() && this.mFooterView == null) {
/* 638 */                           LayoutInflater inflater = LayoutInflater.from(getContext());
/* 639 */                           View footerView = inflater.inflate(this.mFooterLayoutId, (ViewGroup) null);
/* 641 */                           addFooterView(footerView, null, false);
/* 642 */                           this.mFooterView = footerView.findViewById(C0257R.id.footer_content);
                                }
/* 645 */                       this.mAdapter = new RefreshViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter, this.mDataSetObserver);
                            }
/* 648 */                   this.mItemCount = this.mAdapter.getSuperCount();
                        } else {
/* 650 */                   this.mAdapter = null;
/* 651 */                   this.mItemCount = 0;
                        }
/* 653 */               super.setAdapter((ListAdapter) this.mAdapter);
/* 657 */               return;
                    }
/* 655 */           super.setAdapter(adapter);
                }

/* 659 */       private boolean isRefreshable() {
                    return this.mRefreshListener != null;
                }

                @Override // android.widget.ListView
/* 664 */       public void addHeaderView(View v, Object data, boolean isSelectable) {
/* 665 */           if (isRefreshable()) {
/* 666 */               ListView.FixedViewInfo info = new ListView.FixedViewInfo();
/* 667 */               info.view = v;
/* 668 */               info.data = data;
/* 669 */               info.isSelectable = isSelectable;
/* 670 */               this.mHeaderViewInfos.add(info);
/* 674 */               return;
                    }
/* 672 */           super.addHeaderView(v, data, isSelectable);
                }

/* 676 */       private void removeFixedViewInfo(View v, ArrayList<ListView.FixedViewInfo> where) {
/* 677 */           int len = where.size();
                    for (int i = 0; i < len; i++) {
/* 679 */               ListView.FixedViewInfo info = where.get(i);
/* 680 */               if (info.view == v) {
/* 681 */                   where.remove(i);
/* 685 */                   return;
                        }
                    }
                }

                @Override // android.widget.ListView
/* 688 */       public boolean removeHeaderView(View v) {
/* 689 */           if (isRefreshable()) {
/* 690 */               if (this.mHeaderViewInfos.size() > 0) {
/* 691 */                   boolean result = false;
/* 692 */                   if (this.mAdapter != null && this.mAdapter.removeHeader(v)) {
/* 693 */                       if (this.mDataSetObserver != null) {
/* 694 */                           this.mDataSetObserver.onChanged();
                                }
/* 696 */                       result = true;
                            }
/* 698 */                   removeFixedViewInfo(v, this.mHeaderViewInfos);
/* 703 */                   return result;
                        }
/* 701 */               return false;
                    }
/* 703 */           return super.removeHeaderView(v);
                }

                @Override // android.widget.ListView
/* 709 */       public int getHeaderViewsCount() {
/* 710 */           if (isRefreshable()) {
/* 711 */               if (isMode(34)) {
/* 712 */                   return this.mHeaderViewInfos.size() + 1;
                        }
/* 714 */               return this.mHeaderViewInfos.size();
                    }
/* 717 */           return super.getHeaderViewsCount();
                }

                @Override // android.widget.ListView
/* 722 */       public void addFooterView(View v, Object data, boolean isSelectable) {
/* 723 */           if (isRefreshable()) {
/* 724 */               ListView.FixedViewInfo info = new ListView.FixedViewInfo();
/* 725 */               info.view = v;
/* 726 */               info.data = data;
/* 727 */               info.isSelectable = isSelectable;
/* 728 */               this.mFooterViewInfos.add(info);
/* 732 */               return;
                    }
/* 730 */           super.addFooterView(v, data, isSelectable);
                }

                @Override // android.widget.ListView
/* 735 */       public int getFooterViewsCount() {
                    return isRefreshable() ? this.mFooterViewInfos.size() : super.getFooterViewsCount();
                }

                @Override // android.widget.AbsListView, android.view.View
/* 744 */       protected int computeVerticalScrollOffset() {
/* 745 */           if (!isSmoothScrollbarEnabled() || !isMode(2)) {
                        return super.computeVerticalScrollOffset();
                    }
/* 746 */           int firstPosition = getFirstVisiblePosition();
/* 747 */           int childCount = getChildCount();
/* 748 */           int count = this.mItemCount - 1;
/* 750 */           if (count <= 0 || firstPosition < 1 || childCount <= 0) {
                        return 0;
                    }
/* 751 */           View view = getChildAt(0);
/* 752 */           int top = view.getTop();
/* 753 */           int height = view.getHeight();
/* 755 */           if (height > 0) {
/* 756 */               return Math.max((((firstPosition - 1) * 100) - ((top * 100) / height)) + ((int) ((getScrollY() / getHeight()) * count * 100.0f)), 0);
                    }
                    return 0;
                }

                @Override // android.widget.AbsListView, android.view.View
/* 768 */       protected int computeVerticalScrollRange() {
/* 769 */           if (!isSmoothScrollbarEnabled() || !isMode(2)) {
                        return super.computeVerticalScrollRange();
                    }
/* 770 */           int count = this.mItemCount - 1;
/* 771 */           int scrollY = getScrollY();
/* 772 */           int result = Math.max(count * 100, 0);
/* 773 */           if (scrollY != 0) {
/* 775 */               return result + Math.abs((int) ((scrollY / getHeight()) * count * 100.0f));
                    }
/* 779 */           return result;
                }

/* 783 */       public void refreshMore(boolean start) {
/* 784 */           if (start) {
/* 785 */               this.mFooterView.setVisibility(0);
                    } else {
/* 787 */               this.mFooterView.setVisibility(8);
                    }
                }

/* 791 */       void setMode(int mode) {
/* 792 */           this.mMode |= mode;
                }

/* 795 */       void unsetMode(int mode) {
/* 796 */           this.mMode &= mode ^ (-1);
                }

/* 799 */       protected boolean isMode(int mode) {
                    return (this.mMode & mode) != 0;
                }

/* 808 */       void attach(int y) {
                    int childHeightSpec;
/* 809 */           if (!this.mPTRDisabled) {
/* 810 */               View refreshView = this.mRefreshView;
/* 811 */               ViewGroup.LayoutParams p = refreshView.getLayoutParams();
/* 812 */               boolean addedOnce = isMode(1);
/* 814 */               if (addedOnce) {
/* 815 */                   attachViewToParent(refreshView, 0, p);
                        } else {
/* 817 */                   addViewInLayout(refreshView, 0, p, true);
/* 818 */                   setMode(1);
                        }
/* 821 */               setMode(2);
/* 823 */               boolean needToMeasure = !addedOnce || refreshView.isLayoutRequested();
/* 825 */               if (needToMeasure) {
/* 826 */                   int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), p.width);
/* 828 */                   int lpHeight = p.height;
/* 830 */                   if (lpHeight > 0) {
/* 831 */                       childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
                            } else {
/* 833 */                       childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            }
/* 835 */                   refreshView.measure(childWidthSpec, childHeightSpec);
                        } else {
/* 837 */                   cleanupLayoutState(refreshView);
                        }
/* 840 */               int w = refreshView.getMeasuredWidth();
/* 841 */               int h = refreshView.getMeasuredHeight();
/* 842 */               int childTop = y - h;
/* 843 */               int childrenLeft = getListPaddingLeft();
/* 845 */               if (needToMeasure) {
/* 846 */                   int childRight = childrenLeft + w;
/* 847 */                   int childBottom = childTop + h;
/* 848 */                   refreshView.layout(childrenLeft, childTop, childRight, childBottom);
                        } else {
/* 850 */                   refreshView.offsetLeftAndRight(childrenLeft - refreshView.getLeft());
/* 851 */                   refreshView.offsetTopAndBottom(childTop - refreshView.getTop());
                        }
/* 854 */               this.mDataSetObserver.onChanged();
                    }
                }

/* 861 */       void detach() {
/* 862 */           if (isMode(2)) {
/* 863 */               if (this == this.mRefreshView.getParent()) {
/* 864 */                   detachViewFromParent(this.mRefreshView);
                        }
/* 866 */               unsetMode(30);
/* 867 */               this.mDataSetObserver.onChanged();
                    }
                }

                @Override // android.view.ViewGroup
/* 873 */       protected void detachViewsFromParent(int start, int count) {
/* 874 */           if (!isMode(4)) {
/* 877 */               super.detachViewsFromParent(start, count);
                    }
                }

/* 880 */       void requestLastChildLayout() {
                    View firstChild;
/* 881 */           View lastChild = getChildAt(getChildCount() - 1);
/* 882 */           if (lastChild != null && lastChild.getBottom() < (getBottom() - getBottomPaddingOffset()) - getDividerHeight() && (firstChild = getChildAt(0)) != null) {
/* 888 */               int before = firstChild.getTop();
/* 889 */               super.layoutChildren();
/* 890 */               offsetChildrenTopAndBottom(before - firstChild.getTop());
                    }
                }

/* 900 */       public void setTopPosition(int position) {
/* 901 */           this.mTopPos = position;
                }

                @Override // com.twitter.android.widget.TopScrollable
                @TargetApi(8)
/* 910 */       public boolean scrollTop() {
/* 911 */           int pos = this.mTopPos;
/* 912 */           if (isMode(34)) {
/* 913 */               pos++;
                    }
/* 915 */           if (Build.VERSION.SDK_INT > 7) {
/* 916 */               int firstPos = getFirstVisiblePosition();
/* 917 */               View child = getChildAt(0);
/* 919 */               if (firstPos <= pos && (firstPos != pos || child == null || child.getTop() >= 0)) {
                            return false;
                        }
/* 922 */               if (firstPos > 5) {
/* 923 */                   setSelection(pos);
                        }
/* 926 */               smoothScrollToPosition(pos);
                        return true;
                    }
/* 933 */           setSelection(pos);
                    return true;
                }

/* 944 */       private class ScrollRunnable implements Runnable {
                    private static final int AUTO_SCROLL_DURATION = 450;
                    private int mLastY;

/* 945 */           ScrollRunnable() {
                    }

/* 949 */           void scroll(int distance) {
/* 950 */               this.mLastY = 0;
/* 951 */               RefreshableListView.this.setMode(8);
/* 952 */               RefreshableListView.this.mScroller.abortAnimation();
/* 953 */               RefreshableListView.this.mScroller.startScroll(0, 0, 0, distance, AUTO_SCROLL_DURATION);
/* 954 */               RefreshableListView.this.post(this);
                    }

                    @Override // java.lang.Runnable
/* 957 */           public void run() {
/* 958 */               if (RefreshableListView.this.isMode(10)) {
/* 962 */                   Scroller scroller = RefreshableListView.this.mScroller;
/* 963 */                   boolean more = scroller.timePassed() < AUTO_SCROLL_DURATION && scroller.computeScrollOffset();
/* 965 */                   int y = more ? scroller.getCurrY() : scroller.getFinalY();
/* 970 */                   int delta = this.mLastY - y;
/* 971 */                   this.mLastY = y;
/* 974 */                   RefreshableListView.this.offsetChildrenTopAndBottom(delta);
/* 975 */                   RefreshableListView.this.invalidate();
/* 976 */                   RefreshableListView.this.requestLastChildLayout();
/* 979 */                   if (more) {
/* 980 */                       RefreshableListView.this.post(this);
/* 990 */                       return;
                            }
/* 982 */                   RefreshableListView.this.unsetMode(8);
/* 984 */                   if (RefreshableListView.this.isMode(32)) {
/* 985 */                       RefreshableListView.this.setSelectionFromTop(0, y);
                            } else {
/* 987 */                       RefreshableListView.this.detach();
                            }
                        }
                    }
                }

/* 1003 */      private class RefreshViewListAdapter extends HeaderViewListAdapter {
                    private final AdapterDataSetObserver mDataSetObserver;
                    private boolean mRegistered;

/* 1004 */          public RefreshViewListAdapter(ArrayList<ListView.FixedViewInfo> headerViewInfos, ArrayList<ListView.FixedViewInfo> footerViewInfos, ListAdapter adapter, AdapterDataSetObserver observer) {
/* 1006 */              super(headerViewInfos, footerViewInfos, adapter);
/* 1007 */              this.mDataSetObserver = observer;
/* 1008 */              registerDataSetObserver();
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1012 */          public void registerDataSetObserver(DataSetObserver observer) {
/* 1013 */              this.mDataSetObserver.registerDataSetObserver(observer);
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1017 */          public void unregisterDataSetObserver(DataSetObserver observer) {
/* 1018 */              this.mDataSetObserver.unregisterDataSetObserver(observer);
                    }

/* 1022 */          public boolean superEnabled(int position) {
                        try {
/* 1023 */                  return super.isEnabled(position);
                        } catch (IndexOutOfBoundsException e) {
/* 1026 */                  int numHeaders = RefreshableListView.access$000(RefreshableListView.this).size();
/* 1027 */                  if (position < numHeaders) {
/* 1028 */                      return ((ListView.FixedViewInfo) RefreshableListView.access$000(RefreshableListView.this).get(position)).isSelectable;
                            }
/* 1032 */                  int adjPosition = position - numHeaders;
/* 1033 */                  int adapterCount = 0;
/* 1034 */                  if (RefreshableListView.access$100(RefreshableListView.this) != null && adjPosition < (adapterCount = RefreshableListView.access$100(RefreshableListView.this).getCount())) {
/* 1037 */                      return RefreshableListView.access$100(RefreshableListView.this).isEnabled(adjPosition);
                            }
                            try {
/* 1043 */                      return ((ListView.FixedViewInfo) RefreshableListView.access$200(RefreshableListView.this).get(adjPosition - adapterCount)).isSelectable;
                            } catch (Exception e2) {
/* 1045 */                      CrashUtil.logException(e2);
/* 1046 */                      return false;
                            }
                        }
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.ListAdapter
/* 1051 */          public boolean isEnabled(int position) {
/* 1052 */              if (RefreshableListView.this.isMode(34)) {
                            return position > 0 && superEnabled(position + (-1));
                        }
                        try {
/* 1056 */                  return superEnabled(position);
                        } catch (IndexOutOfBoundsException e) {
/* 1059 */                  int numHeaders = RefreshableListView.access$000(RefreshableListView.this).size();
/* 1060 */                  if (position < numHeaders) {
/* 1061 */                      return ((ListView.FixedViewInfo) RefreshableListView.access$000(RefreshableListView.this).get(position)).isSelectable;
                            }
/* 1065 */                  int adjPosition = position - numHeaders;
/* 1066 */                  int adapterCount = 0;
/* 1067 */                  if (RefreshableListView.access$100(RefreshableListView.this) != null && adjPosition < (adapterCount = RefreshableListView.access$100(RefreshableListView.this).getCount())) {
/* 1070 */                      return RefreshableListView.access$100(RefreshableListView.this).isEnabled(adjPosition);
                            }
                            try {
/* 1076 */                      return ((ListView.FixedViewInfo) RefreshableListView.access$200(RefreshableListView.this).get(adjPosition - adapterCount)).isSelectable;
                            } catch (Exception e2) {
/* 1078 */                      CrashUtil.logException(e2);
                                return false;
                            }
                        }
                    }

/* 1088 */          int getSuperCount() {
/* 1089 */              return super.getCount();
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1092 */          public int getCount() {
/* 1093 */              return RefreshableListView.this.mItemCount;
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1096 */          public Object getItem(int position) {
/* 1097 */              if (RefreshableListView.this.isMode(34)) {
/* 1098 */                  if (position == 0) {
/* 1099 */                      return null;
                            }
/* 1101 */                  return super.getItem(position - 1);
                        }
/* 1104 */              return super.getItem(position);
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1108 */          public long getItemId(int position) {
/* 1109 */              if (RefreshableListView.this.isMode(34)) {
/* 1110 */                  if (position == 0) {
/* 1111 */                      return 0L;
                            }
/* 1113 */                  return super.getItemId(position - 1);
                        }
/* 1116 */              return super.getItemId(position);
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1120 */          public View getView(int position, View view, ViewGroup parent) {
/* 1121 */              if (RefreshableListView.this.isMode(34)) {
/* 1122 */                  if (position == 0) {
/* 1123 */                      return RefreshableListView.this.mRefreshView;
                            }
/* 1125 */                  return super.getView(position - 1, view, parent);
                        }
                        try {
/* 1129 */                  return super.getView(position, view, parent);
                        } catch (IndexOutOfBoundsException e) {
/* 1132 */                  CrashUtil.logException(e);
/* 1133 */                  return RefreshableListView.this.mRefreshView;
                        }
                    }

                    @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
/* 1138 */          public int getItemViewType(int position) {
/* 1139 */              if (RefreshableListView.this.isMode(34)) {
/* 1140 */                  if (position == 0) {
/* 1141 */                      return -1;
                            }
/* 1143 */                  return super.getItemViewType(position - 1);
                        }
/* 1146 */              return super.getItemViewType(position);
                    }

/* 1150 */          public final void registerDataSetObserver() {
/* 1151 */              if (!this.mRegistered) {
/* 1152 */                  super.registerDataSetObserver(this.mDataSetObserver);
/* 1153 */                  this.mRegistered = true;
                        }
                    }

/* 1157 */          public final void unregisterDataSetObserver() {
/* 1158 */              if (this.mRegistered) {
/* 1159 */                  super.unregisterDataSetObserver(this.mDataSetObserver);
/* 1160 */                  this.mRegistered = false;
                        }
                    }
                }

                @Override // android.widget.AbsListView, android.view.View
/* 1166 */      public Parcelable onSaveInstanceState() {
/* 1167 */          Parcelable superState = super.onSaveInstanceState();
/* 1168 */          SavedState ss = new SavedState(superState);
/* 1169 */          ss.refreshable = isRefreshable();
/* 1170 */          ss.mode = this.mMode;
/* 1171 */          return ss;
                }

                @Override // android.widget.AbsListView, android.view.View
/* 1175 */      public void onRestoreInstanceState(Parcelable state) {
/* 1176 */          SavedState ss = (SavedState) state;
/* 1177 */          super.onRestoreInstanceState(ss.getSuperState());
/* 1178 */          if (ss.refreshable) {
/* 1179 */              this.mMode = ss.mode;
                    }
                }

                @Override // android.view.View
/* 1184 */      protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
/* 1185 */          super.onVisibilityChanged(changedView, visibility);
/* 1186 */          if (visibility != 0) {
/* 1187 */              Log.d("View", "RLV becoming invisible - " + visibility);
                    }
                }

/* 1191 */      void updateItemCount() {
/* 1192 */          if (isRefreshable()) {
/* 1196 */              if (isMode(34)) {
/* 1197 */                  this.mItemCount = this.mAdapter.getSuperCount() + 1;
                        } else {
/* 1199 */                  this.mItemCount = this.mAdapter.getSuperCount();
                        }
                    }
                }

/* 1208 */      private class AdapterDataSetObserver extends DataSetObserver {
                    private final ArrayList<DataSetObserver> mObservers = new ArrayList<>();

/* 1209 */          public AdapterDataSetObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 1215 */          public void onChanged() {
/* 1216 */              RefreshableListView.this.updateItemCount();
                        synchronized (this.mObservers) {
                            try {
                                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
/* 1221 */                          this.mObservers.get(i).onChanged();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 1224 */              RefreshableListView.this.requestLayout();
                    }

                    @Override // android.database.DataSetObserver
/* 1228 */          public void onInvalidated() {
/* 1229 */              RefreshableListView.this.mItemCount = 0;
                        synchronized (this.mObservers) {
                            try {
                                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
/* 1232 */                          this.mObservers.get(i).onInvalidated();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 1235 */              RefreshableListView.this.requestLayout();
                    }

/* 1238 */          public void registerDataSetObserver(DataSetObserver observer) {
/* 1239 */              this.mObservers.add(observer);
                    }

/* 1242 */          public void unregisterDataSetObserver(DataSetObserver observer) {
/* 1243 */              this.mObservers.remove(observer);
                    }
                }

/* 1250 */      static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C12931();
                    public int mode;
                    public boolean refreshable;

/* 1251 */          public SavedState(Parcelable superState) {
/* 1252 */              super(superState);
                    }

/* 1255 */          public SavedState(Parcel source) {
/* 1256 */              super(source);
/* 1257 */              this.refreshable = source.readInt() == 1;
/* 1258 */              this.mode = source.readInt();
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 1262 */          public void writeToParcel(Parcel dest, int flags) {
/* 1263 */              super.writeToParcel(dest, flags);
/* 1264 */              dest.writeInt(this.refreshable ? 1 : 0);
/* 1265 */              dest.writeInt(this.mode);
                    }

                    /* JADX INFO: renamed from: com.twitter.android.widget.RefreshableListView$SavedState$1 */
/* 1267 */          static class C12931 implements Parcelable.Creator<SavedState> {
/* 1268 */              C12931() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1270 */              public SavedState createFromParcel(Parcel in) {
/* 1271 */                  return new SavedState(in);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1274 */              public SavedState[] newArray(int size) {
/* 1275 */                  return new SavedState[size];
                        }
                    }
                }
            }
