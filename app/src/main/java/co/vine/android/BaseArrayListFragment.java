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
            
            
    public class BaseArrayListFragment extends BaseAdapterFragment implements AbsListView.OnScrollListener, OnTabChangedListener, RefreshListener, PinnedHeaderListView.ScrollDeltaListener {
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

                
        class C01511 implements AdapterView.OnItemClickListener {
            C01511() {
                    }

                    @Override 
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BaseArrayListFragment.this.onListItemClick((ListView) parent, view, i, l);
                    }
                }

                
        class C01522 implements AdapterView.OnItemLongClickListener {
            C01522() {
                    }

                    @Override 
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return BaseArrayListFragment.this.onListItemLongClick((ListView) parent, view, position, id);
                    }
                }

                
        class RunnableC01533 implements Runnable {
            RunnableC01533() {
                    }

                    @Override 
            public void run() {
                BaseArrayListFragment.this.mListView.focusableViewAvailable(BaseArrayListFragment.this.mListView);
                    }
                }

       public void onMoveAway(int newPosition) {
           this.mFocused = false;
                }

       public void onMoveTo(int oldPosition) {
           this.mFocused = true;
                }

       protected boolean isFocused() {
           return this.mFocused;
                }

       public void setFocused(boolean focused) {
           this.mFocused = focused;
                }

       protected void onListItemClick(ListView l, View v, int position, long id) {
                }

       protected boolean onListItemLongClick(ListView parent, View view, int position, long id) {
           return false;
                }

       public boolean isSadFacePartOfHeader() {
           return this.mIsSadFacePartOfHeader;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           this.mListState = new ListState();
           if (savedInstanceState != null) {
               this.mScrollPos = savedInstanceState.getInt(STATE_SCROLL_POS_KEY);
               this.mScrollOffset = savedInstanceState.getInt(STATE_SCROLL_OFF_KEY);
               this.mPendingRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                    } else {
               this.mScrollPos = 0;
               this.mScrollOffset = 0;
                    }
           Bundle args = getArguments();
           if (args != null) {
               this.mRefreshable = args.getBoolean("refresh", true);
               this.mOwnerId = args.getLong("owner_id", this.mAppController.getActiveId());
               if (args.containsKey("chmode")) {
                   this.mChoiceMode = args.getInt("chmode", 0);
                        }
               this.mBgColor = args.getInt("bg_color", 0);
               this.mEmptyTextStringRes = args.getInt("empty_desc", 0);
               return;
                    }
           this.mRefreshable = true;
           this.mOwnerId = this.mAppController.getActiveId();
           this.mChoiceMode = 0;
           this.mBgColor = 0;
                }

       public View getRootView() {
           return this.mRootView;
                }

       public View createView(LayoutInflater inflater, int layout, ViewGroup container) {
           View v = inflater.inflate(layout, container, false);
           this.mRootView = v;
           ListView listView = (ListView) v.findViewById(android.R.id.list);
           listView.setOnItemClickListener(this.mOnItemClickListener);
           listView.setOnItemLongClickListener(this.mOnItemLongClickListenerClickListener);
           listView.setScrollbarFadingEnabled(true);
           listView.setOnScrollListener(this);
           listView.setChoiceMode(this.mChoiceMode);
           int bgColor = this.mBgColor;
           if (bgColor != 0) {
               listView.setBackgroundColor(bgColor);
               listView.setCacheColorHint(bgColor);
                    }
           View emptyView = v.findViewById(android.R.id.empty);
           if (emptyView != null) {
               listView.setEmptyView(emptyView);
               this.mEmptyProgress = (ProgressBar) emptyView.findViewById(R.id.list_empty_progress);
               if (getActivity() instanceof HomeTabActivity) {
                   ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mEmptyProgress.getLayoutParams();
                   params.topMargin = getActivity().getResources().getDimensionPixelSize(R.dimen.tabbar_height);
                        }
                    }
           this.mEmptyView = emptyView;
           this.mEmptyText = (TextView) v.findViewById(R.id.empty_text);
           if (this.mEmptyTextStringRes > 0 && this.mEmptyText != null) {
               this.mEmptyText.setText(this.mEmptyTextStringRes);
                    }
           this.mSadface = v.findViewById(R.id.real_sadface);
           if (this.mRefreshable) {
               ((RefreshableListView) listView).setRefreshListener(this);
                    }
           this.mListView = listView;
           if (this.mListView instanceof PinnedHeaderListView) {
               ((PinnedHeaderListView) this.mListView).setScrollDeltaListener(this);
                    }
           return v;
                }

       protected View createDefaultView(LayoutInflater inflater, ViewGroup container) {
           return createView(inflater, R.layout.msg_list_fragment, container);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return createDefaultView(inflater, container);
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           this.mHandler.post(this.mRequestFocus);
                }

                @Override 
       public void onDestroyView() {
           this.mHandler.removeCallbacks(this.mRequestFocus);
           super.onDestroyView();
                }

                @Override 
       public void onResume() {
           super.onResume();
           restorePosition();
           this.mPendingRequestHelper.handlePendingCaptchaRequest();
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mListView.getCount() > 0) {
               savePosition();
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           savePosition();
           outState.putInt(STATE_SCROLL_POS_KEY, this.mScrollPos);
           outState.putInt(STATE_SCROLL_OFF_KEY, this.mScrollOffset);
                }

                @Override 
       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
           this.mListState.firstVisibleItem = firstVisibleItem;
           this.mListState.visibleItemCount = visibleItemCount;
           this.mListState.totalItemCount = totalItemCount;
           if (visibleItemCount != 0 && firstVisibleItem > 0 && this.mAdapter != null) {
               int position = firstVisibleItem + visibleItemCount;
               if (position >= totalItemCount - 1 && this.mAdapter.getCount() > 0 && this.mListState.hasNewScrollState) {
                   this.mListState.hasNewScrollState = false;
                   onScrollLastItem();
                        }
                    }
                }

       protected void onScrollLastItem() {
                }

       public void setRefreshableHeaderOffset(int offset) {
           if (this.mRefreshable) {
               ((RefreshableListView) this.mListView).setViewYOffset(offset);
                    }
                }

                @Override 
       public void onScrollStateChanged(AbsListView view, int scrollState) {
           this.mListState.hasNewScrollState = true;
           this.mListState.setScrollState(scrollState);
           if (scrollState == 0) {
               onScroll(view, this.mListState.firstVisibleItem, this.mListState.visibleItemCount, this.mListState.totalItemCount);
                    }
                }

       protected void refresh() {
                }

       public synchronized void makeSadFaceHeaderView() {
                    try {
                        if (!this.mIsSadFacePartOfHeader) {
                   this.mIsSadFacePartOfHeader = true;
                   this.mIsSadFaceHeaderAdded = true;
                   if (this.mSadface != null) {
                       boolean alreadyAttached = false;
                       ViewParent parent = this.mSadface.getParent();
                       if (parent != null) {
                           if (parent instanceof ViewGroup) {
                               if (parent != this.mListView) {
                                   ((ViewGroup) parent).removeView(this.mSadface);
                                        } else {
                                   alreadyAttached = true;
                                        }
                                    } else {
                               throw new IllegalStateException("The sad face does not belong to a valid parent: " + parent);
                                    }
                                }
                       if (!alreadyAttached) {
                           ViewGroup.LayoutParams params = this.mSadface.getLayoutParams();
                           if (params != null) {
                               this.mSadface.setLayoutParams(new AbsListView.LayoutParams(params.width, params.height));
                                    }
                           this.mListView.addHeaderView(this.mSadface, null, false);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       protected void onScrollLastItem(Cursor cursor) {
                }

                @Override 
       protected void showProgress(int progressType) {
                    switch (progressType) {
                        case 1:
                   if (this.mRefreshable) {
                       ((RefreshableListView) this.mListView).refreshMore(true);
                            }
                            break;
                        case 2:
                        case 4:
                   if (this.mRefreshable) {
                       ((RefreshableListView) this.mListView).startRefresh();
                       this.mRefreshing = true;
                            }
                            break;
                        case 3:
                   showLoading(true);
                            break;
                    }
                }

                @Override 
       protected void hideProgress(int progressType) {
                    switch (progressType) {
                        case 1:
                   if (this.mRefreshable) {
                       ((RefreshableListView) this.mListView).refreshMore(false);
                            }
                            break;
                        case 2:
                        case 4:
                   showLoading(false);
                   if (this.mRefreshable) {
                       ((RefreshableListView) this.mListView).stopRefresh();
                       this.mRefreshing = false;
                            }
                            break;
                        case 3:
                   showLoading(false);
                            break;
                    }
                }

       protected void processPostOptionsResult(PostOptionsDialogActivity.Result result) {
           if (result.request != null) {
               addRequest(result.request);
                    }
           if (result.intent != null) {
               startActivity(result.intent);
                    }
                }

       protected boolean isEmpty() {
           ListView l = this.mListView;
                    return (l.getCount() - l.getHeaderViewsCount()) - l.getFooterViewsCount() == 0;
                }

       private void showLoading(boolean show) {
           if (show) {
               ListView l = this.mListView;
               l.setVisibility(8);
               if (this.mSadface != null) {
                   this.mSadface.setVisibility(8);
                        }
               if (this.mEmptyProgress != null) {
                   this.mEmptyProgress.setVisibility(0);
                   return;
                        }
               return;
                    }
           ListView l2 = this.mListView;
           if (!isEmpty()) {
               l2.setVisibility(0);
                    } else if (this.mEmptyProgress != null) {
               this.mEmptyProgress.setVisibility(8);
                    }
                }

       protected void showSadface(boolean show) {
           showSadface(show, show);
                }

       protected void showSadface(boolean show, boolean showFrown) {
           if (show) {
               if (this.mListView != null && this.mSadface != null) {
                   this.mEmptyProgress.setVisibility(8);
                   if (!this.mIsSadFacePartOfHeader) {
                       this.mListView.setVisibility(8);
                            } else {
                       this.mListView.setVisibility(0);
                       if (!this.mIsSadFaceHeaderAdded) {
                           this.mListView.addHeaderView(this.mSadface);
                           this.mIsSadFaceHeaderAdded = true;
                                }
                            }
                   this.mSadface.setVisibility(0);
                   View sadFace = this.mSadface.findViewById(R.id.sadface);
                   if (sadFace != null) {
                       sadFace.setVisibility(showFrown ? 0 : 8);
                       return;
                            }
                   return;
                        }
               return;
                    }
           if (this.mListView != null && this.mSadface != null) {
               this.mListView.setVisibility(0);
               if (this.mIsSadFacePartOfHeader) {
                   if (this.mIsSadFaceHeaderAdded) {
                       this.mListView.removeHeaderView(this.mSadface);
                       this.mSadface.setVisibility(8);
                       this.mIsSadFacePartOfHeader = false;
                       return;
                            }
                   return;
                        }
               this.mSadface.setVisibility(8);
                    }
                }

       protected void setEmptyStringMessage(int res) {
           this.mEmptyText.setText(getString(res));
                }

                @Override 
       public void onRefreshPulled() {
                }

                @Override 
       public void onRefreshCancelled() {
                }

                @Override 
       public void onRefreshReleased(boolean shouldStartRefresh) {
           if (shouldStartRefresh) {
               FlurryUtils.trackValidPullToRefreshRelease(getClass().getName());
               refresh();
                    }
                }

                @Override 
       public void onRefreshFinishedNewData() {
                }

                @Override 
       public void onRefreshFinishedNoChange() {
                }

                @Override 
       public void onRefreshFinished() {
                }

                @Override 
       public int getPositionForItemId(long id) {
           if (this.mAdapter != null) {
                        for (int i = 0; i < this.mAdapter.getCount(); i++) {
                            if (this.mAdapter.getItemId(i) == id) {
                       return i;
                            }
                        }
                    }
           return 0;
                }

       protected void restorePosition() {
           this.mListView.setSelectionFromTop(this.mScrollPos, this.mScrollOffset);
                }

       protected void savePosition() {
                    int offset;
           ListView l = this.mListView;
           if (l != null) {
               View v = l.getChildAt(0);
               if (v != null) {
                   offset = v.getTop();
                        } else {
                   offset = 0;
                        }
               this.mScrollPos = l.getFirstVisiblePosition();
               this.mScrollOffset = offset;
                    }
                }

                @Override 
       public ItemPosition getFirstItemPosition() {
                    int position;
                    View child;
           ListView l = this.mListView;
           if (l.getCount() < 1) {
                        return null;
                    }
           int firstPos = l.getFirstVisiblePosition();
           int headerCount = l.getHeaderViewsCount();
           if (this.mRefreshing) {
               headerCount++;
                    }
           if (firstPos < headerCount) {
               position = headerCount;
               child = l.getChildAt(position - firstPos);
                    } else {
               position = firstPos;
               child = l.getChildAt(0);
                    }
           return new ItemPosition(position, l.getItemIdAtPosition(position), child != null ? child.getTop() : 0);
                }

       void invokeScrollFirstItem() {
           if (this.mScrollListener != null) {
               this.mScrollListener.onScrollFirstItem();
                    }
                }

       public static Bundle prepareArguments(Intent intent, boolean refreshable) {
           Bundle args = intent.getExtras();
           if (args == null) {
               args = new Bundle();
                    }
           args.putParcelable("data", intent.getData());
           args.putBoolean("refresh", refreshable);
           return args;
                }

       public void setScrollListener(TabScrollListener listener) {
           this.mScrollListener = listener;
                }

       protected Dialog onCreateDialog(int dialog) {
           return null;
                }

       public void showDialog(int dialog) {
           Dialog d = onCreateDialog(dialog);
           if (d != null) {
               d.show();
                    }
                }

       public void setOwnerId(long ownerId) {
           this.mOwnerId = ownerId;
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           this.mPendingRequestHelper.onActivityResult(requestCode, resultCode, data);
                }

                @Override 
       public void onScroll(int delta) {
           ComponentCallbacks2 activity = getActivity();
           if (activity instanceof ScrollListener) {
               ((ScrollListener) activity).onScroll(delta);
                    }
                }

       public void setNavBottom(int offset) {
           if (this.mListView instanceof PinnedHeaderListView) {
               ((PinnedHeaderListView) this.mListView).setNavBottom(offset);
                    }
                }
            }
