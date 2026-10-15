            package co.vine.android;

            import android.Manifest;
            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.ContentUris;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.os.Build;
            import android.content.pm.PackageManager;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ConversationMenuHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.StringAnchorManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.ContactsMenuAdapter;
            import co.vine.android.widget.SectionAdapter;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class InboxFragment extends BaseCursorListFragment implements View.OnClickListener, SlidingMenu.OnOpenedListener, SlidingMenu.OnClosedListener {
                protected static final int FETCH_FLAG_BOTH = 3;
                protected static final int FETCH_FLAG_FRIENDS = 2;
                protected static final int FETCH_FLAG_INBOX = 1;
                protected static final int FETCH_FLAG_OTHERS = 4;
                protected static final int LOADER_ID_CONTACTS = 3;
                protected static final int LOADER_ID_FRIENDS = 2;
                protected static final int LOADER_ID_INBOX = 1;
                protected static final int LOADER_ID_OTHERS = 4;
                protected static final int VIEW_TYPE_CONTACTS = 3;
                protected static final int VIEW_TYPE_FRIENDS = 2;
                protected static final int VIEW_TYPE_MESSAGES = 1;
                private BaseAdapter mActiveAdapter;
                private ContactsMenuAdapter mAddressAdapter;
                private Cursor mAddressCursor;
                private StringAnchorManager mAnchorManager;
                private int mColor;
                private ArrayList<ContactEntry> mContactEntries;
                private HashMap<Long, ContactEntry> mContactEntryMap;
                private long mCurrentLastId;
                private MessageBoxAdapter mDummyAdapter;
                private ImageView mEmptyImage;
                private TextView mEmptyTitle;
                private View mEmptyView;
                private TextView mEmptyWords;
                protected int mFetchFlags;
                private MenuUsersAdapter mFriendsAdapter;
                private TextView mFriendsText;
                protected MessageBoxAdapter mInboxAdapter;
                private int mInboxCursorCount;
                private boolean mIsAddressBookShowing;
                private boolean mIsEmptyViewAdded;
                private boolean mIsSwitching;
                protected long mMessageCount;
                private MessageBoxAdapter mOtherAdapter;
                private int mOthersCursorCount;
                private TextView mOthersText;
                private SectionAdapter mSectionAdapter;
                private SortMenuContactsTask mSortMenuTask;
                private Typefaces mTypefaces;
                private int mUnReadCount;
                protected long mWaitingToStartId = -1;
                private boolean mLoadingMore = false;
                private final BroadcastReceiver mColorChangedReceiver = new C02321();
                private final Runnable mInvalidateEmptyViewRunnable = new RunnableC02332();
                private final PromptDialogSupportFragment.OnDialogDoneListener mStoreContactDialogDoneListener = new C02343();
                private final Runnable mInvalidateContactEntryRunnable = new RunnableC02354();
                private final OnContactEntryClickedListener mListener = new C02365();

        static  BaseAdapter access$100(InboxFragment x0) {
            return x0.mActiveAdapter;
                }

        static  HashMap access$1000(InboxFragment x0) {
            return x0.mContactEntryMap;
                }

        static  HashMap access$1002(InboxFragment x0, HashMap x1) {
            x0.mContactEntryMap = x1;
                    return x1;
                }

        static  BaseAdapter access$102(InboxFragment x0, BaseAdapter x1) {
            x0.mActiveAdapter = x1;
                    return x1;
                }

        static  ArrayList access$1102(InboxFragment x0, ArrayList x1) {
            x0.mContactEntries = x1;
                    return x1;
                }

        static  void access$1200(InboxFragment x0) {
            x0.invalidateContactEntries();
                }

        static  boolean access$1300(InboxFragment x0) {
            return x0.mIsAddressBookShowing;
                }

        static  boolean access$1402(InboxFragment x0, boolean x1) {
            x0.mLoadingMore = x1;
                    return x1;
                }

        static  SectionAdapter access$200(InboxFragment x0) {
            return x0.mSectionAdapter;
                }

        static  int access$300(InboxFragment x0) {
            return x0.mInboxCursorCount;
                }

        static  boolean access$400(InboxFragment x0) {
            return x0.mIsEmptyViewAdded;
                }

        static  boolean access$402(InboxFragment x0, boolean x1) {
            x0.mIsEmptyViewAdded = x1;
                    return x1;
                }

        static  View access$500(InboxFragment x0) {
            return x0.mEmptyView;
                }

        static  int access$600(InboxFragment x0) {
            return x0.mOthersCursorCount;
                }

        static  boolean access$700(InboxFragment x0) {
            return x0.mIsSwitching;
                }

        static  boolean access$702(InboxFragment x0, boolean x1) {
            x0.mIsSwitching = x1;
                    return x1;
                }

        static  void access$800(InboxFragment x0) {
            x0.invalidateAdapters();
                }

        static  SortMenuContactsTask access$902(InboxFragment x0, SortMenuContactsTask x1) {
            x0.mSortMenuTask = x1;
                    return x1;
                }


                
       class C02321 extends BroadcastReceiver {
           C02321() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               int color = Util.getDefaultSharedPrefs(InboxFragment.this.getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
               ((ConversationMenuHelper) ((BaseActionBarActivity) InboxFragment.this.getActivity()).getSlidingMenuHelper()).setPersonalizedColor(color);
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return createView(inflater, R.layout.friends_menu_list_fragment, container);
                }

                
                
                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    switch (loader.getId()) {
                        case 1:
                   if (cursor != null) {
                       onLoadMessageBoxComplete(loader.getId(), cursor);
                            }
                            break;
                        case 2:
                   Cursor oldCursor = this.mFriendsAdapter.getCursor();
                   if (oldCursor != null) {
                       oldCursor.unregisterContentObserver(this.mChangeObserver);
                            }
                   if (cursor != null) {
                       cursor.registerContentObserver(this.mChangeObserver);
                       if (cursor.moveToLast()) {
                           this.mCurrentLastId = cursor.getLong(1);
                           cursor.moveToFirst();
                                }
                            }
                   this.mFriendsAdapter.swapCursor(cursor);
                   if (this.mFriendsAdapter.isEmpty() && !isFetched(2)) {
                       fetchContent(3, 2, true);
                            } else {
                       makeSadFaceHeaderView();
                       hideProgress(3);
                            }
                            break;
                        case 3:
                   Cursor oldCursor2 = this.mAddressCursor;
                   if (oldCursor2 != null) {
                       oldCursor2.unregisterContentObserver(this.mChangeObserver);
                            }
                   if (cursor != null) {
                       cursor.registerContentObserver(this.mChangeObserver);
                            }
                   if (cursor != null) {
                       this.mAddressCursor = cursor;
                       if (this.mSortMenuTask == null) {
                           this.mSortMenuTask = new SortMenuContactsTask(this, null);
                           this.mSortMenuTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Cursor[]{this.mAddressCursor});
                                }
                            }
                            break;
                        case 4:
                   this.mUnReadCount = 0;
                   if (cursor != null && cursor.moveToFirst()) {
                       do {
                           this.mUnReadCount += cursor.getInt(4);
                       } while (cursor.moveToNext());
                       cursor.moveToFirst();
                            }
                   invalidateUnreadCount();
                   if (cursor != null) {
                       onLoadMessageBoxComplete(loader.getId(), cursor);
                            }
                            break;
                    }
           SLog.m447d("Load finished: {}.", Integer.valueOf(loader.getId()));
           invalidateEmptyView(false);
           if (loader.getId() != 3) {
               invalidateContactEntries();
                    }
                }

                
       class RunnableC02332 implements Runnable {
           RunnableC02332() {
                    }

                    @Override 
           public void run() {
               if (InboxFragment.access$100(InboxFragment.this) == InboxFragment.access$200(InboxFragment.this)) {
                   if (InboxFragment.access$300(InboxFragment.this) == 0) {
                       if (!InboxFragment.access$400(InboxFragment.this)) {
                           SLog.m446d("Header change: Add.");
                           InboxFragment.access$402(InboxFragment.this, true);
                           InboxFragment.this.mListView.addHeaderView(InboxFragment.access$500(InboxFragment.this));
                           return;
                                }
                       return;
                            }
                   if (InboxFragment.access$400(InboxFragment.this)) {
                       SLog.m446d("Header change: Remove.");
                       InboxFragment.access$402(InboxFragment.this, false);
                       InboxFragment.this.mListView.removeHeaderView(InboxFragment.access$500(InboxFragment.this));
                       return;
                            }
                   return;
                        }
               if (InboxFragment.access$600(InboxFragment.this) == 0) {
                   if (!InboxFragment.access$400(InboxFragment.this)) {
                       SLog.m446d("Header change: Add.");
                       InboxFragment.access$402(InboxFragment.this, true);
                       InboxFragment.this.mListView.addHeaderView(InboxFragment.access$500(InboxFragment.this));
                       return;
                            }
                   return;
                        }
               if (InboxFragment.access$400(InboxFragment.this)) {
                   SLog.m446d("Header change: Remove.");
                   InboxFragment.access$402(InboxFragment.this, false);
                   InboxFragment.this.mListView.removeHeaderView(InboxFragment.access$500(InboxFragment.this));
                        }
                    }
                }

       private synchronized void invalidateEmptyView(boolean immediate) {
                    try {
                        this.mHandler.removeCallbacks(this.mInvalidateEmptyViewRunnable);
               if (immediate) {
                   this.mInvalidateEmptyViewRunnable.run();
                        } else {
                   this.mHandler.postDelayed(this.mInvalidateEmptyViewRunnable, 100L);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void invalidateUnreadCount() {
           String text = null;
           if (this.mUnReadCount > 0) {
               if (this.mUnReadCount > 20) {
                   text = "20+";
                        } else {
                   text = String.valueOf(this.mUnReadCount);
                        }
                    }
           if (this.mOthersText != null) {
               String currentText = this.mOthersText.getText().toString();
               int index = currentText.indexOf("(");
               if (text != null) {
                   TextView textView = this.mOthersText;
                            StringBuilder sb = new StringBuilder();
                            if (index != -1) {
                       currentText = currentText.substring(0, index - 1);
                            }
                            textView.setText(sb.append(currentText).append(" (").append(text).append(") ").toString());
                   return;
                        }
               if (index != -1) {
                   this.mOthersText.setText(currentText.substring(0, index - 1));
                        }
                    }
                }

       public void onLoadMessageBoxComplete(int loaderId, Cursor cursor) {
           MessageBoxAdapter adapter = loaderId == 4 ? this.mOtherAdapter : this.mInboxAdapter;
           int flag = loaderId == 4 ? 4 : 1;
           Cursor oldCursor = adapter.getCursor();
           if (cursor != null) {
               if (loaderId == 4) {
                   this.mOthersCursorCount = cursor.getCount();
                        } else {
                   this.mInboxCursorCount = cursor.getCount();
                        }
               if (oldCursor != null) {
                   oldCursor.unregisterContentObserver(this.mChangeObserver);
                        }
               cursor.registerContentObserver(this.mChangeObserver);
                    }
           adapter.swapCursor(cursor);
           if (adapter.isEmpty()) {
               if (!isFetched(flag)) {
                   fetchContent(3, flag, true);
                   return;
                        } else {
                   showSadface(true);
                   return;
                        }
                    }
           hideProgress(3);
                }

       protected void fetchContent(int fetchType, int fetchMode, boolean silent) {
                    int pageType;
           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 1:
                       pageType = 3;
                                break;
                            case 2:
                            case 4:
                       pageType = 1;
                                break;
                            case 3:
                       pageType = 1;
                                break;
                            default:
                       pageType = 1;
                                break;
                        }
               if (pageType == 3 && fetchMode == 1) {
                   this.mLoadingMore = true;
                        }
               if ((fetchMode & 4) != 0) {
                   setFetched(4);
                   addRequest(this.mAppController.fetchConversations(pageType, false, 2), fetchType);
                        }
               if ((fetchMode & 1) != 0) {
                   setFetched(1);
                   addRequest(this.mAppController.fetchConversations(pageType, false, 1), fetchType);
                        }
               if ((fetchMode & 2) != 0) {
                   setFetched(2);
                   addRequest(this.mAppController.fetchFriends(pageType, 1), fetchType);
                        }
               if (!silent) {
                   showProgress(fetchType);
                        }
                    }
                }

       protected boolean isFetched(int mode) {
                    return (this.mFetchFlags & mode) != 0;
                }

       protected void setFetched(int mode) {
           this.mFetchFlags |= mode;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           this.mAppSessionListener = new ConversationMenuSessionListener();
           this.mMessageCount = 10L;
                }

                @Override 
       public View createView(LayoutInflater inflater, int id, ViewGroup container) {
           View root = super.createView(inflater, id, container);
           View header = getActivity().getLayoutInflater().inflate(R.layout.menu_header, (ViewGroup) this.mListView, false);
           this.mFriendsText = (TextView) header.findViewById(R.id.friends);
           this.mOthersText = (TextView) header.findViewById(R.id.others);
           this.mListView.addHeaderView(header, null, false);
           View emptyHeader = inflater.inflate(R.layout.vm_friends_empty, (ViewGroup) null);
           this.mEmptyView = emptyHeader;
           this.mEmptyImage = (ImageView) this.mEmptyView.findViewById(R.id.sadface);
           this.mEmptyWords = (TextView) this.mEmptyView.findViewById(R.id.sadwords);
           this.mEmptyTitle = (TextView) this.mEmptyView.findViewById(R.id.sadTitle);
           return root;
                }

                @Override 
       public void onClosed() {
           this.mMessageCount = 10L;
           this.mAppController.clearInboxPageCursors();
           this.mListView.setSelectionFromTop(0, 0);
           FragmentActivity activity = getActivity();
           if (activity != null) {
               restartLoader();
               ConversationMenuHelper helper = (ConversationMenuHelper) ((BaseActionBarActivity) activity).getSlidingMenuHelper();
               helper.toggleContactSearch(false);
                    }
                }

                
       class C02343 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02343() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
               FragmentActivity activity = InboxFragment.this.getActivity();
               if (activity != null) {
                   boolean enable = which == -1;
                   if (enable) {
                                InboxFragment.this.mAppController.sendAddressBook();
                            }
                   InboxFragment.this.mAppController.updateEnableAddressBook(enable);
                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(activity).edit();
                   editor.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, enable).apply();
                        }
                    }
                }

                @Override 
       public void onOpened() {
           Activity a = getActivity();
           if (a != null) {
               a.startService(GCMNotificationService.getClearNotificationIntent(a, 2));
                    }
           if (a instanceof HomeTabActivity) {
               if (!MuteUtil.isMuted(a)) {
                   a.sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                        }
               AccountManager am = AccountManager.get(a);
               Account account = VineAccountHelper.getActiveAccount(a);
               if (account != null && VineAccountHelper.shouldShowStoreContactsPrompt(am, account)) {
                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
                   p.setListener(this.mStoreContactDialogDoneListener);
                   p.setMessage(R.string.store_contacts_desc);
                   p.setTitle(R.string.store_contacts);
                   p.setNegativeButton(R.string.no);
                   p.setPositiveButton(R.string.ok);
                            try {
                       p.show(((HomeTabActivity) a).getSupportFragmentManager());
                       VineAccountHelper.setDidShowStoreContactsPrompt(am, account);
                            } catch (Exception e) {
                       CrashUtil.logException(e, "Failed to show contact agreement box. ", new Object[0]);
                            }
                        }
                    }
                }

       public void setPersonalizedColor(int profileBackground) {
           this.mColor = profileBackground;
           if (profileBackground == Settings.DEFAULT_PROFILE_COLOR || profileBackground <= 0) {
               profileBackground = 16777215 & getResources().getColor(R.color.vine_green);
                    }
           if (this.mListView != null) {
               ((RefreshableListView) this.mListView).colorizePTR((-16777216) | profileBackground);
                    }
                }

       public boolean isLoadingMore() {
           return this.mLoadingMore;
                }

       public class TabSwicher implements View.OnClickListener, Runnable {
                    public final BaseAdapter targetAdapter;

           public TabSwicher(BaseAdapter target) {
               this.targetAdapter = target;
                    }

                    @Override 
           public void onClick(View v) {
               if (InboxFragment.access$100(InboxFragment.this) != this.targetAdapter && !InboxFragment.access$700(InboxFragment.this)) {
                   InboxFragment.access$702(InboxFragment.this, true);
                   InboxFragment.this.mHandler.removeCallbacks(this);
                   InboxFragment.this.mHandler.postDelayed(this, 200L);
                        }
                    }

                    @Override 
           public void run() {
               InboxFragment.access$102(InboxFragment.this, this.targetAdapter);
               InboxFragment.this.mListView.setAdapter((ListAdapter) InboxFragment.access$100(InboxFragment.this));
               InboxFragment.access$702(InboxFragment.this, false);
               InboxFragment.access$800(InboxFragment.this);
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           this.mTypefaces = Typefaces.get(getActivity());
           int profileColor = Util.getDefaultSharedPrefs(getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
           if (profileColor == Settings.DEFAULT_PROFILE_COLOR || profileColor <= 0) {
               profileColor = 16777215 & getResources().getColor(R.color.vine_green);
                    }
           if (this.mInboxAdapter == null) {
               this.mInboxAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
           if (this.mDummyAdapter == null) {
               this.mDummyAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
           if (this.mFriendsAdapter == null) {
               this.mFriendsAdapter = new MenuUsersAdapter(getActivity(), this.mAppController, 0);
               this.mFriendsAdapter.setInboxAdapter(this.mInboxAdapter);
                    }
           if (this.mAddressAdapter == null) {
               this.mAddressAdapter = new ContactsMenuAdapter(getActivity());
                    }
           if (this.mOtherAdapter == null) {
               this.mOtherAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
           this.mSectionAdapter = new SectionAdapter(null, null, new int[]{0, 0, 0}, new BaseAdapter[]{this.mInboxAdapter, this.mFriendsAdapter, this.mAddressAdapter}, R.layout.menu_section_divider, 1);
           this.mFriendsText.setOnClickListener(new TabSwicher(this.mSectionAdapter));
           this.mOthersText.setOnClickListener(new TabSwicher(this.mOtherAdapter));
           this.mActiveAdapter = this.mSectionAdapter;
           this.mListView.setAdapter((ListAdapter) this.mActiveAdapter);
           this.mListView.setOnScrollListener(this);
           ((RefreshableListView) this.mListView).colorizePTR((-16777216) | profileColor);
           this.mAnchorManager = Vine.getUserSectionsAnchorManager(getActivity(), 1);
           invalidateAdapters();
                }

       private void invalidateAdapters() {
           if (this.mActiveAdapter == this.mOtherAdapter) {
               this.mFriendsText.setTextColor(CommonUtil.THIRTY_FIVE_ALPHA_MASK);
               this.mOthersText.setTextColor(-13421773);
               this.mFriendsText.setTypeface(this.mTypefaces.mediumContent);
               this.mOthersText.setTypeface(this.mTypefaces.boldContent);
               this.mCursorAdapter = null;
               this.mEmptyWords.setText(R.string.vm_other_empty);
               this.mEmptyImage.setImageResource(R.drawable.vm_watermark);
               this.mEmptyTitle.setVisibility(8);
               ViewUtil.setBackground(this.mListView, null);
                    } else {
               this.mOthersText.setTextColor(CommonUtil.THIRTY_FIVE_ALPHA_MASK);
               this.mFriendsText.setTextColor(-13421773);
               this.mOthersText.setTypeface(this.mTypefaces.mediumContent);
               this.mFriendsText.setTypeface(this.mTypefaces.boldContent);
               this.mCursorAdapter = this.mFriendsAdapter;
               this.mEmptyTitle.setVisibility(0);
               this.mEmptyWords.setText(R.string.empty_inbox);
               this.mEmptyImage.setImageResource(R.drawable.vm_roundel);
               this.mListView.setBackgroundColor(getResources().getColor(R.color.solid_super_light_gray));
                    }
           invalidateEmptyView(true);
                }

       public void addEntriesToAddressAdapter() {
           if (this.mContactEntries != null) {
               SLog.m447d("Showing address book: {}.", Integer.valueOf(this.mContactEntries.size()));
               this.mIsAddressBookShowing = true;
               if (this.mAddressAdapter.getCount() != this.mContactEntries.size()) {
                   this.mAddressAdapter.clear();
                   this.mAddressAdapter.addAll(this.mContactEntries);
                        }
               makeSadFaceHeaderView();
                    }
                }

       private class SortMenuContactsTask extends SortContactsTask {
           private SortMenuContactsTask() {
                    }

            SortMenuContactsTask(InboxFragment x0, C02321 x1) {
               this();
                    }


                    
           protected void onPostExecute(ArrayList<ContactEntry> entries) {
               InboxFragment.access$902(InboxFragment.this, null);
               InboxFragment.access$1002(InboxFragment.this, new HashMap());
                        for (ContactEntry entry : entries) {
                   InboxFragment.access$1000(InboxFragment.this).put(Long.valueOf(entry.contactId), entry);
                        }
               InboxFragment.access$1102(InboxFragment.this, entries);
               InboxFragment.access$1200(InboxFragment.this);
                    }
                }

                
       class RunnableC02354 implements Runnable {
           RunnableC02354() {
                    }

                    @Override 
           public void run() {
               if (InboxFragment.access$100(InboxFragment.this) == InboxFragment.access$200(InboxFragment.this)) {
                   int last = InboxFragment.this.mListView.getLastVisiblePosition();
                   boolean listViewFits = true;
                            try {
                       listViewFits = last == InboxFragment.this.mListView.getCount() + (-1) && InboxFragment.this.mListView.getChildAt(last).getBottom() <= InboxFragment.this.mListView.getHeight();
                            } catch (Exception e) {
                       SLog.m455e("Cannot get listview state.");
                            }
                   boolean isEmpty = false;
                            try {
                       isEmpty = InboxFragment.this.mInboxAdapter.isEmpty();
                            } catch (Exception e2) {
                       CrashUtil.logException(e2);
                            }
                   if (InboxFragment.access$1300(InboxFragment.this) || ((InboxFragment.this.isFetched(1) && isEmpty) || (InboxFragment.this.isFetched(3) && listViewFits))) {
                       InboxFragment.this.addEntriesToAddressAdapter();
                            }
                        }
                    }
                }

       private void invalidateContactEntries() {
           this.mHandler.removeCallbacks(this.mInvalidateContactEntryRunnable);
           this.mHandler.postDelayed(this.mInvalidateContactEntryRunnable, 100L);
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 2:
                   Uri contentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS, this.mAppController.getActiveId());
                   return new CursorLoader(getActivity(), contentUri, VineDatabaseSQL.UsersQuery.PROJECTION, "section_type=1", null, Vine.UserGroupsView.SORT_ORDER_FRIEND_REFRESH_FIRST_THEN_FRIEND_INDEX);
                        case 3:
                   if (!hasContactsPermission()) {
                       return new Loader<Cursor>(getActivity());
                            }
                   return new CursorLoader(getActivity(), ContactEntry.sUri, ContactEntry.sProjection, ContactEntry.sSelection, ContactEntry.sSelectionArgs, ContactEntry.sSortOrder);
                        default:
                            Uri contentUri2 = Vine.InboxView.CONTENT_URI.buildUpon().appendQueryParameter(Vine.InboxView.PARAM_HIDDEN, "0").appendQueryParameter("network_type", String.valueOf(id == 4 ? 2 : 1)).appendQueryParameter(Vine.QUERY_PARAMETER_LIMIT, String.valueOf(this.mMessageCount)).build();
                   return new CursorLoader(getActivity(), contentUri2, VineDatabaseSQL.InboxQuery.PROJECTION, null, null, null);
                    }
                }

                private boolean hasContactsPermission() {
                    if (Build.VERSION.SDK_INT < 23) {
                        return true;
                    }
                    Activity activity = getActivity();
                    return activity != null && activity.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
                }

       public void onInboxCursorClicked(Cursor inboxCursor, View item) {
           if (TextUtils.isEmpty(inboxCursor.getString(15)) || inboxCursor.getInt(14) <= 1) {
               long conversationObjectId = inboxCursor.getLong(1);
               String username = inboxCursor.getString(8);
               boolean amFollowing = inboxCursor.getInt(11) == 1;
               long userId = inboxCursor.getLong(10);
               boolean isUserExternal = inboxCursor.getInt(16) == 1;
               BaseActionBarActivity activity = (BaseActionBarActivity) getActivity();
               startActivity(ConversationActivity.getIntent(activity, conversationObjectId, username, userId, isUserExternal, amFollowing, false));
               return;
                    }
           this.mAppController.retryMessagesInConversationRowId(inboxCursor.getLong(1));
           TextView retryText = (TextView) item.findViewById(R.id.tap_to_retry);
           retryText.setText(R.string.message_sending);
           item.findViewById(R.id.failed_upload).setVisibility(8);
           item.findViewById(R.id.retry_progress).setVisibility(0);
                }

                @Override 
       public void onClick(View view) {
           if (view.getId() == R.id.load_more && !hasPendingRequest(1)) {
               view.findViewById(R.id.load_more_content).setVisibility(8);
               view.findViewById(R.id.progress).setVisibility(0);
               this.mMessageCount += 10;
               restartLoader(1);
               fetchContent(1, 1, true);
                    }
                }

       public class ConversationMenuSessionListener extends AppSessionListener {
           public ConversationMenuSessionListener() {
                    }

                    @Override 
           public void onGetMessageInboxComplete(String reqId, int statusCode, String reasonPhrase, int count) {
               PendingRequest req = InboxFragment.this.removeRequest(reqId);
               if (req != null) {
                   InboxFragment.access$1402(InboxFragment.this, false);
                   InboxFragment.this.hideProgress(req.fetchType);
                   InboxFragment.this.onGetMessageInboxComplete(count);
                            switch (statusCode) {
                                case 200:
                                    break;
                                default:
                           InboxFragment.this.hideProgress(3);
                           if (req.fetchType != 3) {
                               Util.showCenteredToast(InboxFragment.this.getActivity(), R.string.error_server);
                                    }
                           SLog.m455e("Error " + statusCode + " - " + reasonPhrase);
                                    break;
                            }
                        }
                    }

                    @Override 
           public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientExternal, long conversationObjectId, String username, boolean amFollowing) {
                        if (recipientId == InboxFragment.this.mWaitingToStartId) {
                   InboxFragment.this.mWaitingToStartId = -1L;
                   InboxFragment.this.mAppController.clearUnreadMessageCount(conversationObjectId);
                   InboxFragment.this.startActivity(ConversationActivity.getIntent(InboxFragment.this.getActivity(), conversationObjectId, username, recipientId, isRecipientExternal, amFollowing, false));
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               InboxFragment.this.onPhotoImageLoaded(images);
                    }

                    @Override 
           public void onGetUsersComplete(Session session, String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int previousPage, long anchor) {
               PendingRequest req = InboxFragment.this.removeRequest(reqId);
               if (req != null) {
                   InboxFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
                                    break;
                                default:
                           InboxFragment.this.hideProgress(3);
                           SLog.m455e("Error:  " + reasonPhrase);
                                    break;
                            }
                        }
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mInboxAdapter.getCursor() == null) {
               initLoader(1);
                    } else if (this.mInboxAdapter.isEmpty() && !isFetched(1)) {
               fetchContent(3, 1, false);
                    }
           if (this.mOtherAdapter.getCursor() == null) {
               initLoader(4);
                    } else if (this.mOtherAdapter.isEmpty() && !isFetched(4)) {
               fetchContent(3, 4, false);
                    }
           if (this.mFriendsAdapter.getCursor() == null) {
               initLoader(2);
                    } else if (this.mFriendsAdapter.isEmpty() && !isFetched(2)) {
               fetchContent(3, 3, false);
                    }
           if (this.mAddressCursor == null) {
               if (hasContactsPermission()) {
                   initLoader(3);
                            }
                    }
           getActivity().registerReceiver(this.mColorChangedReceiver, Util.COLOR_CHANGED_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override 
       protected void onListItemClick(ListView l, View v, int position, long id) {
           int position2 = position - this.mListView.getHeaderViewsCount();
           if (position2 >= 0) {
               if (this.mActiveAdapter == this.mSectionAdapter) {
                            switch (this.mSectionAdapter.getItemViewType(position2)) {
                                case 1:
                           onInboxCursorClicked((Cursor) this.mSectionAdapter.getItem(position2), v);
                                    break;
                                case 2:
                                    if (this.mWaitingToStartId == -1) {
                               this.mWaitingToStartId = id;
                               this.mAppController.fetchConversationRowIdFromUserRemoteId(id, 1);
                                    }
                                    break;
                                case 3:
                           this.mListener.onClick(this, this.mContactEntryMap.get(Long.valueOf(id)));
                                    break;
                            }
                        }
               onInboxCursorClicked((Cursor) this.mOtherAdapter.getItem(position2), v);
                    }
                }

       protected void onGetMessageInboxComplete(int count) {
           if (this.mActiveAdapter == this.mSectionAdapter) {
               if (count == 0) {
                   invalidateContactEntries();
                        } else {
                   this.mAddressAdapter.clear();
                   this.mIsAddressBookShowing = false;
                   this.mAddressAdapter.notifyDataSetChanged();
                        }
                    }
           invalidateEmptyView(false);
                }

                
       class C02365 extends OnContactEntryClickedListener {
           C02365() {
                    }

                    @Override 
           protected void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
                        if (InboxFragment.this.mWaitingToStartId == -1) {
                   InboxFragment.this.mWaitingToStartId = recipient.contactId;
                   InboxFragment.this.mAppController.fetchConversationRowIdFromSingleRecipient(recipient, 1);
                        }
                    }
                }

                @Override 
       protected void refresh() {
           if (this.mActiveAdapter == this.mSectionAdapter) {
               fetchContent(2, 3, false);
                    } else {
               fetchContent(2, 4, false);
                    }
                }

                @Override 
       protected void onScrollLastItem(Cursor cursor) {
           if (this.mActiveAdapter == this.mSectionAdapter) {
               if (this.mAnchorManager.haveMore()) {
                   fetchContent(1, 2, false);
                   return;
                        }
               if (!this.mIsAddressBookShowing) {
                   addEntriesToAddressAdapter();
                        }
               hideProgress(3);
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           getActivity().unregisterReceiver(this.mColorChangedReceiver);
                }

       public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
           if (this.mInboxAdapter != null) {
               this.mInboxAdapter.setUserImages(images);
                    }
           if (this.mFriendsAdapter != null) {
               this.mFriendsAdapter.setUserImages(images);
                    }
           if (this.mOtherAdapter != null) {
               this.mOtherAdapter.setUserImages(images);
                    }
                }
            }
