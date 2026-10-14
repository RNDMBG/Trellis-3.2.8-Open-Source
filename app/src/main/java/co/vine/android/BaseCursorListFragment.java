            package co.vine.android;

            import android.app.Dialog;
            import android.content.Intent;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.support.v4.widget.CursorAdapter;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.AbsListView;
            import android.widget.AdapterView;
            import android.widget.ListView;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.views.ListState;
            import co.vine.android.widget.OnTabChangedListener;
            import co.vine.android.widget.tabs.TabScrollListener;
            import com.twitter.android.widget.ItemPosition;
            import com.twitter.android.widget.RefreshListener;
            import com.twitter.android.widget.RefreshableListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    public class BaseCursorListFragment extends BaseCursorAdapterFragment implements AbsListView.OnScrollListener, OnTabChangedListener, RefreshListener {
                public static final String ARG_BG_COLOR = "bg_color";
                public static final String ARG_CHOICE_MODE = "chmode";
                public static final String ARG_DATA = "data";
                public static final String ARG_EMPTY_DESC = "empty_desc";
                public static final String ARG_OWNER_ID = "owner_id";
                public static final String ARG_REFRESHABLE = "refresh";
                public static final String ARG_TAKE_FOCUS = "take_focus";
                private static final String STATE_SCROLL_OFF_KEY = "scroll_off";
                private static final String STATE_SCROLL_POS_KEY = "scroll_pos";
                protected int mBgColor;
                protected int mChoiceMode;
                private ProgressBar mEmptyProgress;
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
                private final AdapterView.OnItemClickListener mOnItemClickListener = new C01541();
                private final AdapterView.OnItemLongClickListener mOnItemLongClickListenerClickListener = new C01552();
                private final Runnable mRequestFocus = new RunnableC01563();

                /* JADX INFO: renamed from: co.vine.android.BaseCursorListFragment$1 */
/* 74 */        class C01541 implements AdapterView.OnItemClickListener {
/* 75 */            C01541() {
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 77 */            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
/* 78 */                BaseCursorListFragment.this.onListItemClick((ListView) parent, view, i, l);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.BaseCursorListFragment$2 */
/* 80 */        class C01552 implements AdapterView.OnItemLongClickListener {
/* 81 */            C01552() {
                    }

                    @Override // android.widget.AdapterView.OnItemLongClickListener
/* 84 */            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/* 85 */                return BaseCursorListFragment.this.onListItemLongClick((ListView) parent, view, position, id);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.BaseCursorListFragment$3 */
/* 87 */        class RunnableC01563 implements Runnable {
/* 88 */            RunnableC01563() {
                    }

                    @Override // java.lang.Runnable
/* 90 */            public void run() {
/* 91 */                BaseCursorListFragment.this.mListView.focusableViewAvailable(BaseCursorListFragment.this.mListView);
                    }
                }

                @Override // co.vine.android.widget.OnTabChangedListener
/* 100 */       public void onMoveAway(int newPosition) {
/* 101 */           this.mFocused = false;
                }

                @Override // co.vine.android.widget.OnTabChangedListener
/* 105 */       public void onMoveTo(int oldPosition) {
/* 106 */           this.mFocused = true;
                }

/* 109 */       protected boolean isFocused() {
/* 110 */           return this.mFocused;
                }

/* 113 */       public void setFocused(boolean focused) {
/* 114 */           this.mFocused = focused;
                }

/* 117 */       protected void onListItemClick(ListView l, View v, int position, long id) {
                }

/* 120 */       protected boolean onListItemLongClick(ListView parent, View view, int position, long id) {
/* 121 */           return false;
                }

/* 124 */       public boolean isSadFacePartOfHeader() {
/* 125 */           return this.mIsSadFacePartOfHeader;
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 130 */       public void onCreate(Bundle savedInstanceState) {
/* 131 */           super.onCreate(savedInstanceState);
/* 133 */           this.mListState = new ListState();
/* 135 */           if (savedInstanceState != null) {
/* 136 */               this.mScrollPos = savedInstanceState.getInt(STATE_SCROLL_POS_KEY);
/* 137 */               this.mScrollOffset = savedInstanceState.getInt(STATE_SCROLL_OFF_KEY);
/* 138 */               this.mPendingRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                    } else {
/* 140 */               this.mScrollPos = 0;
/* 141 */               this.mScrollOffset = 0;
                    }
/* 144 */           Bundle args = getArguments();
/* 145 */           if (args != null) {
/* 146 */               this.mRefreshable = args.getBoolean("refresh", true);
/* 147 */               this.mOwnerId = args.getLong("owner_id", this.mAppController.getActiveId());
/* 149 */               if (args.containsKey("chmode")) {
/* 150 */                   this.mChoiceMode = args.getInt("chmode", 0);
                        }
/* 152 */               this.mBgColor = args.getInt("bg_color", 0);
/* 153 */               this.mEmptyTextStringRes = args.getInt("empty_desc", 0);
/* 160 */               return;
                    }
/* 155 */           this.mRefreshable = true;
/* 156 */           this.mOwnerId = this.mAppController.getActiveId();
/* 157 */           this.mChoiceMode = 0;
/* 158 */           this.mBgColor = 0;
                }

/* 162 */       public View getRootView() {
/* 163 */           return this.mRootView;
                }

/* 166 */       public View createView(LayoutInflater inflater, int layout, ViewGroup container) {
/* 167 */           View v = inflater.inflate(layout, container, false);
/* 169 */           this.mRootView = v;
/* 171 */           ListView listView = (ListView) v.findViewById(android.R.id.list);
/* 172 */           listView.setOnItemClickListener(this.mOnItemClickListener);
/* 173 */           listView.setOnItemLongClickListener(this.mOnItemLongClickListenerClickListener);
/* 174 */           listView.setScrollbarFadingEnabled(true);
/* 175 */           listView.setOnScrollListener(this);
/* 176 */           listView.setChoiceMode(this.mChoiceMode);
/* 177 */           int bgColor = this.mBgColor;
/* 178 */           if (bgColor != 0) {
/* 179 */               listView.setBackgroundColor(bgColor);
/* 180 */               listView.setCacheColorHint(bgColor);
                    }
/* 183 */           View emptyView = v.findViewById(android.R.id.empty);
/* 184 */           if (emptyView != null) {
/* 185 */               listView.setEmptyView(emptyView);
/* 186 */               this.mEmptyProgress = (ProgressBar) emptyView.findViewById(C0257R.id.list_empty_progress);
                    }
/* 189 */           this.mEmptyView = emptyView;
/* 191 */           this.mEmptyText = (TextView) v.findViewById(C0257R.id.empty_text);
/* 192 */           if (this.mEmptyTextStringRes > 0 && this.mEmptyText != null) {
/* 194 */               this.mEmptyText.setText(this.mEmptyTextStringRes);
                    }
/* 198 */           this.mSadface = v.findViewById(C0257R.id.real_sadface);
/* 200 */           if (this.mRefreshable) {
/* 201 */               ((RefreshableListView) listView).setRefreshListener(this);
                    }
/* 204 */           this.mListView = listView;
/* 206 */           return v;
                }

/* 209 */       protected View createDefaultView(LayoutInflater inflater, ViewGroup container) {
/* 210 */           return createView(inflater, C0257R.layout.msg_list_fragment, container);
                }

                @Override // android.support.v4.app.Fragment
/* 216 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 217 */           return createDefaultView(inflater, container);
                }

                @Override // android.support.v4.app.Fragment
/* 221 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 222 */           super.onViewCreated(view, savedInstanceState);
/* 223 */           this.mHandler.post(this.mRequestFocus);
                }

                @Override // android.support.v4.app.Fragment
/* 227 */       public void onDestroyView() {
/* 228 */           this.mHandler.removeCallbacks(this.mRequestFocus);
/* 229 */           super.onDestroyView();
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 233 */       public void onResume() {
/* 234 */           super.onResume();
/* 235 */           restorePosition();
/* 236 */           this.mPendingRequestHelper.handlePendingCaptchaRequest();
                }

                @Override // co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 240 */       public void onPause() {
/* 241 */           super.onPause();
/* 242 */           savePosition();
                }

                @Override // co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 246 */       public void onSaveInstanceState(Bundle outState) {
/* 247 */           super.onSaveInstanceState(outState);
/* 248 */           savePosition();
/* 249 */           outState.putInt(STATE_SCROLL_POS_KEY, this.mScrollPos);
/* 250 */           outState.putInt(STATE_SCROLL_OFF_KEY, this.mScrollOffset);
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 256 */       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    Cursor cursor;
/* 257 */           this.mListState.firstVisibleItem = firstVisibleItem;
/* 258 */           this.mListState.visibleItemCount = visibleItemCount;
/* 259 */           this.mListState.totalItemCount = totalItemCount;
/* 261 */           if (visibleItemCount != 0) {
/* 265 */               if (firstVisibleItem == 0 && this.mScrollListener != null) {
/* 267 */                   this.mScrollListener.onScrollFirstItem();
                        }
/* 271 */               if (firstVisibleItem > 0 && this.mCursorAdapter != null) {
/* 272 */                   int position = firstVisibleItem + visibleItemCount;
/* 274 */                   if (position >= totalItemCount - 1 && this.mCursorAdapter.getCount() > 0 && (cursor = this.mCursorAdapter.getCursor()) != null && cursor.moveToLast() && this.mListState.hasNewScrollState) {
/* 278 */                       this.mListState.hasNewScrollState = false;
/* 279 */                       onScrollLastItem(cursor);
                            }
                        }
                    }
                }

/* 286 */       public void setRefreshableHeaderOffset(int offset) {
/* 287 */           if (this.mRefreshable) {
/* 288 */               ((RefreshableListView) this.mListView).setViewYOffset(offset);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 293 */       public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 294 */           this.mListState.hasNewScrollState = true;
/* 295 */           this.mListState.setScrollState(scrollState);
/* 298 */           if (scrollState == 0) {
/* 299 */               onScroll(view, this.mListState.firstVisibleItem, this.mListState.visibleItemCount, this.mListState.totalItemCount);
                    }
                }

/* 309 */       protected void refresh() {
                }

/* 312 */       public synchronized void makeSadFaceHeaderView() {
                    try {
                        if (!this.mIsSadFacePartOfHeader) {
/* 314 */                   this.mIsSadFacePartOfHeader = true;
/* 315 */                   this.mIsSadFaceHeaderAdded = true;
/* 316 */                   if (this.mSadface != null) {
/* 317 */                       boolean alreadyAttached = false;
/* 318 */                       ViewParent parent = this.mSadface.getParent();
/* 319 */                       if (parent != null) {
/* 320 */                           if (parent instanceof ViewGroup) {
/* 321 */                               if (parent != this.mListView) {
/* 322 */                                   ((ViewGroup) parent).removeView(this.mSadface);
                                        } else {
/* 324 */                                   alreadyAttached = true;
                                        }
                                    } else {
/* 327 */                               throw new IllegalStateException("The sad face does not belong to a valid parent: " + parent);
                                    }
                                }
/* 330 */                       if (!alreadyAttached) {
/* 331 */                           ViewGroup.LayoutParams params = this.mSadface.getLayoutParams();
/* 332 */                           if (params != null) {
/* 334 */                               this.mSadface.setLayoutParams(new AbsListView.LayoutParams(params.width, params.height));
                                    }
/* 336 */                           this.mListView.addHeaderView(this.mSadface, null, false);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 345 */       protected void onScrollLastItem(Cursor cursor) {
                }

                @Override // co.vine.android.BaseAdapterFragment
/* 353 */       protected void showProgress(int progressType) {
                    switch (progressType) {
                        case 1:
                        case 7:
/* 357 */                   if (this.mRefreshable) {
/* 358 */                       ((RefreshableListView) this.mListView).refreshMore(true);
                            }
                            break;
                        case 2:
                        case 4:
/* 365 */                   if (this.mRefreshable) {
/* 366 */                       ((RefreshableListView) this.mListView).startRefresh();
/* 367 */                       this.mRefreshing = true;
                            }
                            break;
                        case 3:
                        case 5:
/* 374 */                   showLoading(true);
                            break;
                    }
                }

                @Override // co.vine.android.BaseAdapterFragment
/* 384 */       protected void hideProgress(int progressType) {
                    switch (progressType) {
                        case 1:
/* 403 */                   if (this.mRefreshable) {
/* 404 */                       ((RefreshableListView) this.mListView).refreshMore(false);
                            }
                            break;
                        case 2:
                        case 4:
/* 394 */                   showLoading(false);
/* 395 */                   if (this.mRefreshable) {
/* 396 */                       ((RefreshableListView) this.mListView).stopRefresh();
/* 397 */                       this.mRefreshing = false;
                            }
                            break;
                        case 3:
                        case 5:
/* 388 */                   showLoading(false);
                            break;
                    }
                }

/* 415 */       protected void processPostOptionsResult(PostOptionsDialogActivity.Result result) {
/* 416 */           if (result.request != null) {
/* 417 */               addRequest(result.request);
                    }
/* 419 */           if (result.intent != null) {
/* 420 */               startActivity(result.intent);
                    }
                }

/* 424 */       protected boolean isEmpty() {
/* 425 */           ListView l = this.mListView;
                    return (l.getCount() - l.getHeaderViewsCount()) - l.getFooterViewsCount() == 0;
                }

/* 429 */       private void showLoading(boolean show) {
/* 430 */           if (show) {
/* 431 */               ListView l = this.mListView;
/* 432 */               l.setVisibility(8);
/* 433 */               if (this.mSadface != null) {
/* 434 */                   this.mSadface.setVisibility(8);
                        }
/* 436 */               if (this.mEmptyProgress != null) {
/* 437 */                   this.mEmptyProgress.setVisibility(0);
/* 449 */                   return;
                        }
/* 449 */               return;
                    }
/* 440 */           ListView l2 = this.mListView;
/* 441 */           if (!isEmpty()) {
/* 442 */               l2.setVisibility(0);
                    } else if (this.mEmptyProgress != null) {
/* 445 */               this.mEmptyProgress.setVisibility(8);
                    }
                }

/* 451 */       protected void showSadface(boolean show) {
/* 452 */           showSadface(show, show);
                }

/* 455 */       protected void showSadface(boolean show, boolean showFrown) {
/* 456 */           if (show) {
/* 457 */               if (this.mListView != null && this.mSadface != null) {
/* 458 */                   if (!this.mIsSadFacePartOfHeader) {
/* 459 */                       this.mListView.setVisibility(8);
                            } else {
/* 461 */                       this.mListView.setVisibility(0);
/* 462 */                       if (!this.mIsSadFaceHeaderAdded) {
/* 463 */                           this.mListView.addHeaderView(this.mSadface);
/* 464 */                           this.mIsSadFaceHeaderAdded = true;
                                }
                            }
/* 467 */                   this.mSadface.setVisibility(0);
/* 470 */                   View sadFace = this.mSadface.findViewById(C0257R.id.sadface);
/* 471 */                   if (sadFace != null) {
/* 472 */                       sadFace.setVisibility(showFrown ? 0 : 8);
/* 489 */                       return;
                            }
/* 489 */                   return;
                        }
/* 489 */               return;
                    }
/* 476 */           if (this.mListView != null && this.mSadface != null) {
/* 477 */               this.mListView.setVisibility(0);
/* 478 */               if (this.mIsSadFacePartOfHeader) {
/* 479 */                   if (this.mIsSadFaceHeaderAdded) {
/* 480 */                       this.mListView.removeHeaderView(this.mSadface);
/* 481 */                       this.mSadface.setVisibility(8);
/* 482 */                       this.mIsSadFacePartOfHeader = false;
/* 489 */                       return;
                            }
/* 489 */                   return;
                        }
/* 485 */               this.mSadface.setVisibility(8);
                    }
                }

/* 491 */       protected void setEmptyStringMessage(int res) {
/* 492 */           this.mEmptyText.setText(getString(res));
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 496 */       public void onRefreshPulled() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 500 */       public void onRefreshCancelled() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 503 */       public void onRefreshReleased(boolean shouldStartRefresh) {
/* 504 */           if (shouldStartRefresh) {
/* 505 */               FlurryUtils.trackValidPullToRefreshRelease(getClass().getName());
/* 506 */               refresh();
                    }
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 510 */       public void onRefreshFinishedNewData() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 513 */       public void onRefreshFinishedNoChange() {
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 516 */       public void onRefreshFinished() {
/* 517 */           CursorAdapter adapter = this.mCursorAdapter;
/* 519 */           if (adapter != null) {
/* 520 */               Cursor cursor = adapter.getCursor();
/* 521 */               if (cursor != null) {
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment
/* 533 */       protected void handleContentChanged() {
/* 534 */           onContentChanged();
                }

/* 541 */       protected void onContentChanged() {
/* 542 */           onRefreshFinished();
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 545 */       public int getPositionForItemId(long id) {
                    Cursor cursor;
/* 546 */           int idColumnIndex = getIdColumnIndex();
/* 548 */           if (idColumnIndex >= 0 && (cursor = this.mCursorAdapter.getCursor()) != null && cursor.moveToFirst()) {
                        while (cursor.getLong(idColumnIndex) != id) {
/* 564 */                   if (!cursor.moveToNext()) {
                            }
                        }
/* 554 */               int pos = cursor.getPosition() + this.mListView.getHeaderViewsCount();
/* 559 */               if (this.mRefreshing) {
/* 560 */                   return pos + 1;
                        }
/* 569 */               return pos;
                    }
/* 569 */           return 0;
                }

/* 580 */       protected int getIdColumnIndex() {
/* 581 */           return -1;
                }

/* 591 */       protected void saveItemPosition(ItemPosition itemPosition) {
                }

/* 605 */       protected void restorePosition() {
/* 606 */           this.mListView.setSelectionFromTop(this.mScrollPos, this.mScrollOffset);
                }

/* 611 */       protected void savePosition() {
                    int offset;
/* 612 */           ListView l = this.mListView;
/* 613 */           if (l != null) {
/* 615 */               View v = l.getChildAt(0);
/* 617 */               if (v != null) {
/* 618 */                   offset = v.getTop();
                        } else {
/* 620 */                   offset = 0;
                        }
/* 623 */               this.mScrollPos = l.getFirstVisiblePosition();
/* 624 */               this.mScrollOffset = offset;
                    }
                }

                @Override // com.twitter.android.widget.RefreshListener
/* 628 */       public ItemPosition getFirstItemPosition() {
                    int position;
                    View child;
/* 629 */           ListView l = this.mListView;
/* 630 */           if (l.getCount() < 1) {
                        return null;
                    }
/* 631 */           int firstPos = l.getFirstVisiblePosition();
/* 632 */           int headerCount = l.getHeaderViewsCount();
/* 634 */           if (this.mRefreshing) {
/* 635 */               headerCount++;
                    }
/* 641 */           if (firstPos < headerCount) {
/* 642 */               position = headerCount;
/* 643 */               child = l.getChildAt(position - firstPos);
                    } else {
/* 645 */               position = firstPos;
/* 646 */               child = l.getChildAt(0);
                    }
/* 649 */           return new ItemPosition(position, l.getItemIdAtPosition(position), child != null ? child.getTop() : 0);
                }

/* 653 */       void invokeScrollFirstItem() {
/* 654 */           if (this.mScrollListener != null) {
/* 655 */               this.mScrollListener.onScrollFirstItem();
                    }
                }

/* 666 */       public static Bundle prepareArguments(Intent intent, boolean refreshable) {
/* 667 */           Bundle args = intent.getExtras();
/* 668 */           if (args == null) {
/* 669 */               args = new Bundle();
                    }
/* 671 */           args.putParcelable("data", intent.getData());
/* 672 */           args.putBoolean("refresh", refreshable);
/* 673 */           return args;
                }

/* 676 */       public void setScrollListener(TabScrollListener listener) {
/* 677 */           this.mScrollListener = listener;
                }

/* 686 */       protected Dialog onCreateDialog(int dialog) {
/* 687 */           return null;
                }

/* 695 */       public void showDialog(int dialog) {
/* 696 */           Dialog d = onCreateDialog(dialog);
/* 697 */           if (d != null) {
/* 698 */               d.show();
                    }
                }

/* 702 */       public void setOwnerId(long ownerId) {
/* 703 */           this.mOwnerId = ownerId;
                }

                @Override // android.support.v4.app.Fragment
/* 707 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 708 */           this.mPendingRequestHelper.onActivityResult(requestCode, resultCode, data);
                }
            }
