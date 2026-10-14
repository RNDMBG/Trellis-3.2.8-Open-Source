            package co.vine.android;

            import android.app.Dialog;
            import android.content.ComponentCallbacks2;
            import android.content.Intent;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.AbsListView;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.ListView;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.views.ListState;
            import co.vine.android.widget.OnTabChangedListener;
            import co.vine.android.widget.PinnedHeaderListView;
            import co.vine.android.widget.tabs.TabScrollListener;
            import com.twitter.android.widget.ItemPosition;
            import com.twitter.android.widget.RefreshListener;
            import com.twitter.android.widget.RefreshableListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 80 */    public class BaseArrayListFragment extends BaseAdapterFragment implements AbsListView.OnScrollListener, OnTabChangedListener, RefreshListener, PinnedHeaderListView.ScrollDeltaListener {
                public static final String ARG_BG_COLOR = "bg_color";
                public static final String ARG_CHOICE_MODE = "chmode";
                public static final String ARG_DATA = "data";
                public static final String ARG_EMPTY_DESC = "empty_desc";
                public static final String ARG_OWNER_ID = "owner_id";
                public static final String ARG_REFRESHABLE = "refresh";
                public static final String ARG_TAKE_FOCUS = "take_focus";
                private static final String STATE_SCROLL_OFF_KEY = "scroll_off";
                private static final String STATE_SCROLL_POS_KEY = "scroll_pos";
                protected BaseAdapter mAdapter;
                protected int mBgColor;
                private View mChildObservedForScrollDistance;
                protected int mChoiceMode;
                protected ProgressBar mEmptyProgress;
                protected TextView mEmptyText;
                protected int mEmptyTextStringRes;
                private View mEmptyView;
                protected boolean mFocused;
                private boolean mIsSadFaceHeaderAdded;
                private boolean mIsSadFacePartOfHeader;
                protected ListState mListState;
                protected ListView mListView;
                protected long mOwnerId;
                protected boolean mRefreshable;
                private View mRootView;
                protected View mSadface;
                private TabScrollListener mScrollListener;
                private int mScrollOffset;
                private int mScrollPos;
                protected int mNextPage = 1;
                protected long mAnchor = 0;
                private final AdapterView.OnItemClickListener mOnItemClickListener = new C01511();
                private final AdapterView.OnItemLongClickListener mOnItemLongClickListenerClickListener = new C01522();
                private final Runnable mRequestFocus = new RunnableC01533();
                private int mChildFormerTop = -1;

                /* JADX INFO: renamed from: co.vine.android.BaseArrayListFragment$1 */
/* 81 */        class C01511 implements AdapterView.OnItemClickListener {
/* 82 */            C01511() {
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 84 */            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
/* 85 */                BaseArrayListFragment.this.onListItemClick((ListView) parent, view, i, l);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.BaseArrayListFragment$2 */
/* 87 */        class C01522 implements AdapterView.OnItemLongClickListener {
/* 88 */            C01522() {
                    }

                    @Override // android.widget.AdapterView.OnItemLongClickListener
/* 91 */            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/* 92 */                return BaseArrayListFragment.this.onListItemLongClick((ListView) parent, view, position, id);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.BaseArrayListFragment$3 */
/* 94 */        class RunnableC01533 implements Runnable {
/* 95 */            RunnableC01533() {
                    }

                    @Override // java.lang.Runnable
/* 97 */            public void run() {
/* 98 */                BaseArrayListFragment.this.mListView.focusableViewAvailable(BaseArrayListFragment.this.mListView);
                    }
                }

/* 109 */       public void onMoveAway(int newPosition) {
/* 110 */           this.mFocused = false;
                }

/* 114 */       public void onMoveTo(int oldPosition) {
/* 115 */           this.mFocused = true;
                }

/* 118 */       protected boolean isFocused() {
/* 119 */           return this.mFocused;
                }

/* 122 */       public void setFocused(boolean focused) {
/* 123 */           this.mFocused = focused;
                }

/* 126 */       protected void onListItemClick(ListView l, View v, int position, long id) {
                }

/* 129 */       protected boolean onListItemLongClick(ListView parent, View view, int position, long id) {
/* 130 */           return false;
                }

/* 133 */       public boolean isSadFacePartOfHeader() {
/* 134 */           return this.mIsSadFacePartOfHeader;
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 139 */       public void onCreate(Bundle savedInstanceState) {
/* 140 */           super.onCreate(savedInstanceState);
/* 142 */           this.mListState = new ListState();
/* 144 */           if (savedInstanceState != null) {
/* 145 */               this.mScrollPos = savedInstanceState.getInt(STATE_SCROLL_POS_KEY);
/* 146 */               this.mScrollOffset = savedInstanceState.getInt(STATE_SCROLL_OFF_KEY);
/* 147 */               this.mPendingRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                    } else {
/* 149 */               this.mScrollPos = 0;
/* 150 */               this.mScrollOffset = 0;
                    }
/* 153 */           Bundle args = getArguments();
/* 154 */           if (args != null) {
/* 155 */               this.mRefreshable = args.getBoolean("refresh", true);
/* 156 */               this.mOwnerId = args.getLong("owner_id", this.mAppController.getActiveId());
/* 158 */               if (args.containsKey("chmode")) {
/* 159 */                   this.mChoiceMode = args.getInt("chmode", 0);
                        }
/* 161 */               this.mBgColor = args.getInt("bg_color", 0);
/* 162 */               this.mEmptyTextStringRes = args.getInt("empty_desc", 0);
/* 169 */               return;
                    }
/* 164 */           this.mRefreshable = true;
/* 165 */           this.mOwnerId = this.mAppController.getActiveId();
/* 166 */           this.mChoiceMode = 0;
/* 167 */           this.mBgColor = 0;
                }

/* 171 */       public View getRootView() {
/* 172 */           return this.mRootView;
                }

/* 175 */       public View createView(LayoutInflater inflater, int layout, ViewGroup container) {
/* 176 */           View v = inflater.inflate(layout, container, false);
/* 178 */           this.mRootView = v;
/* 180 */           ListView listView = (ListView) v.findViewById(android.R.id.list);
/* 181 */           listView.setOnItemClickListener(this.mOnItemClickListener);
/* 182 */           listView.setOnItemLongClickListener(this.mOnItemLongClickListenerClickListener);
/* 183 */           listView.setScrollbarFadingEnabled(true);
/* 184 */           listView.setOnScrollListener(this);
/* 185 */           listView.setChoiceMode(this.mChoiceMode);
/* 186 */           int bgColor = this.mBgColor;
/* 187 */           if (bgColor != 0) {
/* 188 */               listView.setBackgroundColor(bgColor);
/* 189 */               listView.setCacheColorHint(bgColor);
                    }
/* 192 */           View emptyView = v.findViewById(android.R.id.empty);
/* 193 */           if (emptyView != null) {
/* 194 */               listView.setEmptyView(emptyView);
/* 195 */               this.mEmptyProgress = (ProgressBar) emptyView.findViewById(C0257R.id.list_empty_progress);
/* 196 */               if (getActivity() instanceof HomeTabActivity) {
/* 197 */                   ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mEmptyProgress.getLayoutParams();
/* 198 */                   params.topMargin = getActivity().getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height);
                        }
                    }
/* 202 */           this.mEmptyView = emptyView;
/* 204 */           this.mEmptyText = (TextView) v.findViewById(C0257R.id.empty_text);
/* 205 */           if (this.mEmptyTextStringRes > 0 && this.mEmptyText != null) {
/* 207 */               this.mEmptyText.setText(this.mEmptyTextStringRes);
                    }
/* 211 */           this.mSadface = v.findViewById(C0257R.id.real_sadface);
/* 213 */           if (this.mRefreshable) {
/* 214 */               ((RefreshableListView) listView).setRefreshListener(this);
                    }
/* 217 */           this.mListView = listView;
/* 219 */           if (this.mListView instanceof PinnedHeaderListView) {
/* 220 */               ((PinnedHeaderListView) this.mListView).setScrollDeltaListener(this);
                    }
/* 223 */           return v;
                }

/* 226 */       protected View createDefaultView(LayoutInflater inflater, ViewGroup container) {
/* 227 */           return createView(inflater, C0257R.layout.msg_list_fragment, container);
                }

                @Override // android.support.v4.app.Fragment
/* 233 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 234 */           return createDefaultView(inflater, container);
                }

                @Override // android.support.v4.app.Fragment
/* 238 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 239 */           super.onViewCreated(view, savedInstanceState);
/* 240 */           this.mHandler.post(this.mRequestFocus);
                }

                @Override // android.support.v4.app.Fragment
/* 244 */       public void onDestroyView() {
/* 245 */           this.mHandler.removeCallbacks(this.mRequestFocus);
/* 246 */           super.onDestroyView();
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 250 */       public void onResume() {
/* 251 */           super.onResume();
/* 252 */           restorePosition();
/* 253 */           this.mPendingRequestHelper.handlePendingCaptchaRequest();
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 257 */       public void onPause() {
/* 258 */           super.onPause();
/* 262 */           if (this.mListView.getCount() > 0) {
/* 263 */               savePosition();
                    }
                }

                @Override // co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 268 */       public void onSaveInstanceState(Bundle outState) {
/* 269 */           super.onSaveInstanceState(outState);
/* 270 */           savePosition();
/* 271 */           outState.putInt(STATE_SCROLL_POS_KEY, this.mScrollPos);
/* 272 */           outState.putInt(STATE_SCROLL_OFF_KEY, this.mScrollOffset);
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 278 */       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/* 279 */           this.mListState.firstVisibleItem = firstVisibleItem;
/* 280 */           this.mListState.visibleItemCount = visibleItemCount;
/* 281 */           this.mListState.totalItemCount = totalItemCount;
/* 283 */           if (visibleItemCount != 0 && firstVisibleItem > 0 && this.mAdapter != null) {
/* 288 */               int position = firstVisibleItem + visibleItemCount;
/* 290 */               if (position >= totalItemCount - 1 && this.mAdapter.getCount() > 0 && this.mListState.hasNewScrollState) {
/* 292 */                   this.mListState.hasNewScrollState = false;
/* 293 */                   onScrollLastItem();
                        }
                    }
                }

/* 298 */       protected void onScrollLastItem() {
                }

/* 301 */       public void setRefreshableHeaderOffset(int offset) {
/* 302 */           if (this.mRefreshable) {
/* 303 */               ((RefreshableListView) this.mListView).setViewYOffset(offset);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 308 */       public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 309 */           this.mListState.hasNewScrollState = true;
/* 310 */           this.mListState.setScrollState(scrollState);
/* 313 */           if (scrollState == 0) {
/* 314 */               onScroll(view, this.mListState.firstVisibleItem, this.mListState.visibleItemCount, this.mListState.totalItemCount);
                    }
                }

/* 324 */       protected void refresh() {
                }

/* 327 */       public synchronized void makeSadFaceHeaderView() {
                    try {
                        if (!this.mIsSadFacePartOfHeader) {
/* 329 */                   this.mIsSadFacePartOfHeader = true;
/* 330 */                   this.mIsSadFaceHeaderAdded = true;
/* 331 */                   if (this.mSadface != null) {
/* 332 */                       boolean alreadyAttached = false;
/* 333 */                       ViewParent parent = this.mSadface.getParent();
/* 334 */                       if (parent != null) {
/* 335 */                           if (parent instanceof ViewGroup) {
/* 336 */                               if (parent != this.mListView) {
/* 337 */                                   ((ViewGroup) parent).removeView(this.mSadface);
                                        } else {
/* 339 */                                   alreadyAttached = true;
                                        }
                                    } else {
/* 342 */                               throw new IllegalStateException("The sad face does not belong to a valid parent: " + parent);
                                    }
                                }
/* 345 */                       if (!alreadyAttached) {
/* 346 */                           ViewGroup.LayoutParams params = this.mSadface.getLayoutParams();
/* 347 */                           if (params != null) {
/* 349 */                               this.mSadface.setLayoutParams(new AbsListView.LayoutParams(params.width, params.height));
                                    }
/* 351 */                           this.mListView.addHeaderView(this.mSadface, null, false);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 360 */       protected void onScrollLastItem(Cursor cursor) {
                }

                @Override // co.vine.android.BaseAdapterFragment
/* 368 */       protected void showProgress(int progressType) {
                    switch (progressType) {
                        case 1:
/* 371 */                   if (this.mRefreshable) {
/* 372 */                       ((RefreshableListView) this.mListView).refreshMore(true);
                            }
                            break;
                        case 2:
                        case 4:
/* 379 */                   if (this.mRefreshable) {
/* 380 */                       ((RefreshableListView) this.mListView).startRefresh();
/* 381 */                       this.mRefreshing = true;
                            }
                            break;
                        case 3:
/* 387 */                   showLoading(true);
                            break;
                    }
                }

                @Override // co.vine.android.BaseAdapterFragment
/* 397 */       protected void hideProgress(int progressType) {
                    switch (progressType) {
                        case 1:
/* 415 */                   if (this.mRefreshable) {
/* 416 */                       ((RefreshableListView) this.mListView).refreshMore(false);
                            }
                            break;
                        case 2:
                        case 4:
/* 406 */                   showLoading(false);
/* 407 */                   if (this.mRefreshable) {
/* 408 */                       ((RefreshableListView) this.mListView).stopRefresh();
/* 409 */                       this.mRefreshing = false;
                            }
                            break;
                        case 3:
/* 400 */                   showLoading(false);
                            break;
                    }
                }

/* 427 */       protected void processPostOptionsResult(PostOptionsDialogActivity.Result result) {
/* 428 */           if (result.request != null) {
/* 429 */               addRequest(result.request);
                    }
/* 431 */           if (result.intent != null) {
/* 432 */               startActivity(result.intent);
                    }
                }

/* 436 */       protected boolean isEmpty() {
/* 437 */           ListView l = this.mListView;
                    return (l.getCount() - l.getHeaderViewsCount()) - l.getFooterViewsCount() == 0;
                }

/* 441 */       private void showLoading(boolean show) {
/* 442 */           if (show) {
/* 443 */               ListView l = this.mListView;
/* 444 */               l.setVisibility(8);
/* 445 */               if (this.mSadface != null) {
/* 446 */                   this.mSadface.setVisibility(8);
                        }
/* 448 */               if (this.mEmptyProgress != null) {
/* 449 */                   this.mEmptyProgress.setVisibility(0);
/* 461 */                   return;
                        }
/* 461 */               return;
                    }
/* 452 */           ListView l2 = this.mListView;
/* 453 */           if (!isEmpty()) {
/* 454 */               l2.setVisibility(0);
                    } else if (this.mEmptyProgress != null) {
/* 457 */               this.mEmptyProgress.setVisibility(8);
                    }
                }

/* 463 */       protected void showSadface(boolean show) {
/* 464 */           showSadface(show, show);
                }

/* 467 */       protected void showSadface(boolean show, boolean showFrown) {
/* 468 */           if (show) {
/* 469 */               if (this.mListView != null && this.mSadface != null) {
/* 470 */                   this.mEmptyProgress.setVisibility(8);
/* 471 */                   if (!this.mIsSadFacePartOfHeader) {
/* 472 */                       this.mListView.setVisibility(8);
                            } else {
/* 474 */                       this.mListView.setVisibility(0);
/* 475 */                       if (!this.mIsSadFaceHeaderAdded) {
/* 476 */                           this.mListView.addHeaderView(this.mSadface);
/* 477 */                           this.mIsSadFaceHeaderAdded = true;
                                }
                            }
/* 480 */                   this.mSadface.setVisibility(0);
/* 483 */                   View sadFace = this.mSadface.findViewById(C0257R.id.sadface);
/* 484 */                   if (sadFace != null) {
/* 485 */                       sadFace.setVisibility(showFrown ? 0 : 8);
/* 502 */                       return;
                            }
/* 502 */                   return;
                        }
/* 502 */               return;
                    }
/* 489 */           if (this.mListView != null && this.mSadface != null) {
/* 490 */               this.mListView.setVisibility(0);
/* 491 */               if (this.mIsSadFacePartOfHeader) {
/* 492 */                   if (this.mIsSadFaceHeaderAdded) {
/* 493 */                       this.mListView.removeHeaderView(this.mSadface);
/* 494 */                       this.mSadface.setVisibility(8);
/* 495 */                       this.mIsSadFacePartOfHeader = false;
/* 502 */                       return;
                            }
/* 502 */                   return;
                        }
/* 498 */               this.mSadface.setVisibility(8);
                    }
                }

/* 504 */       protected void setEmptyStringMessage(int res) {
/* 505 */           this.mEmptyText.setText(getString(res));
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 509 */       public void onRefreshPulled() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 513 */       public void onRefreshCancelled() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 516 */       public void onRefreshReleased(boolean shouldStartRefresh) {
/* 517 */           if (shouldStartRefresh) {
/* 518 */               FlurryUtils.trackValidPullToRefreshRelease(getClass().getName());
/* 519 */               refresh();
                    }
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 523 */       public void onRefreshFinishedNewData() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 526 */       public void onRefreshFinishedNoChange() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 529 */       public void onRefreshFinished() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 532 */       public int getPositionForItemId(long id) {
/* 533 */           if (this.mAdapter != null) {
                        for (int i = 0; i < this.mAdapter.getCount(); i++) {
                            if (this.mAdapter.getItemId(i) == id) {
/* 540 */                       return i;
                            }
                        }
                    }
/* 540 */           return 0;
                }

/* 543 */       protected void restorePosition() {
/* 544 */           this.mListView.setSelectionFromTop(this.mScrollPos, this.mScrollOffset);
                }

/* 549 */       protected void savePosition() {
                    int offset;
/* 550 */           ListView l = this.mListView;
/* 551 */           if (l != null) {
/* 553 */               View v = l.getChildAt(0);
/* 555 */               if (v != null) {
/* 556 */                   offset = v.getTop();
                        } else {
/* 558 */                   offset = 0;
                        }
/* 561 */               this.mScrollPos = l.getFirstVisiblePosition();
/* 562 */               this.mScrollOffset = offset;
                    }
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 566 */       public ItemPosition getFirstItemPosition() {
                    int position;
                    View child;
/* 567 */           ListView l = this.mListView;
/* 568 */           if (l.getCount() < 1) {
                        return null;
                    }
/* 569 */           int firstPos = l.getFirstVisiblePosition();
/* 570 */           int headerCount = l.getHeaderViewsCount();
/* 572 */           if (this.mRefreshing) {
/* 573 */               headerCount++;
                    }
/* 579 */           if (firstPos < headerCount) {
/* 580 */               position = headerCount;
/* 581 */               child = l.getChildAt(position - firstPos);
                    } else {
/* 583 */               position = firstPos;
/* 584 */               child = l.getChildAt(0);
                    }
/* 587 */           return new ItemPosition(position, l.getItemIdAtPosition(position), child != null ? child.getTop() : 0);
                }

/* 591 */       void invokeScrollFirstItem() {
/* 592 */           if (this.mScrollListener != null) {
/* 593 */               this.mScrollListener.onScrollFirstItem();
                    }
                }

/* 604 */       public static Bundle prepareArguments(Intent intent, boolean refreshable) {
/* 605 */           Bundle args = intent.getExtras();
/* 606 */           if (args == null) {
/* 607 */               args = new Bundle();
                    }
/* 609 */           args.putParcelable("data", intent.getData());
/* 610 */           args.putBoolean("refresh", refreshable);
/* 611 */           return args;
                }

/* 614 */       public void setScrollListener(TabScrollListener listener) {
/* 615 */           this.mScrollListener = listener;
                }

/* 624 */       protected Dialog onCreateDialog(int dialog) {
/* 625 */           return null;
                }

/* 633 */       public void showDialog(int dialog) {
/* 634 */           Dialog d = onCreateDialog(dialog);
/* 635 */           if (d != null) {
/* 636 */               d.show();
                    }
                }

/* 640 */       public void setOwnerId(long ownerId) {
/* 641 */           this.mOwnerId = ownerId;
                }

                @Override // android.support.v4.app.Fragment
/* 645 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 646 */           this.mPendingRequestHelper.onActivityResult(requestCode, resultCode, data);
                }

                @Override // co.vine.android.widget.PinnedHeaderListView.ScrollDeltaListener
/* 650 */       public void onScroll(int delta) {
/* 651 */           ComponentCallbacks2 activity = getActivity();
/* 652 */           if (activity instanceof ScrollListener) {
/* 653 */               ((ScrollListener) activity).onScroll(delta);
                    }
                }

/* 657 */       public void setNavBottom(int offset) {
/* 658 */           if (this.mListView instanceof PinnedHeaderListView) {
/* 659 */               ((PinnedHeaderListView) this.mListView).setNavBottom(offset);
                    }
                }
            }
