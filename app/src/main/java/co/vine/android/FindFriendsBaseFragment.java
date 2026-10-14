            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.ContentUris;
            import android.content.DialogInterface;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.style.StyleSpan;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.Util;
            import co.vine.android.widget.UserViewHolder;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public abstract class FindFriendsBaseFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, FindFriendsNUXActivity.FilterableFriendsList {
                protected static final int DIALOG_STORE_CONTACTS = 1;
                protected static final String EVENT_SOURCE_TITLE = "Find Friends: Address";
                protected static final int LOADER_ID_FILTER = 1;
                protected static final String STATE_FETCHED = "fetch";
                protected static final String STATE_FRIENDSHIPS = "friendships";
                protected FindFriendsBaseActivity mActivity;
                protected StyleSpan[] mBold;
                protected boolean mFetched;
                protected Friendships mFriendships;
                protected int mNewFollowsCount;
                protected String[] mProjection;
                protected volatile String mSearchQuery;
                protected String mSortOrder;
                protected final PromptDialogSupportFragment.OnDialogDoneListener mStoreContactDialogDoneListener = new C02101();

                public abstract void onClick(View view);

                public abstract void onTextChanged(CharSequence charSequence, int i, int i2, int i3);

                protected abstract void startProfileActivity(long j);


                /* JADX INFO: renamed from: co.vine.android.FindFriendsBaseFragment$1 */
/* 54 */        class C02101 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 55 */            C02101() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 58 */            public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 59 */                AccountManager am = AccountManager.get(FindFriendsBaseFragment.this.getActivity());
/* 60 */                Account account = VineAccountHelper.getAccount(FindFriendsBaseFragment.this.getActivity(), FindFriendsBaseFragment.this.mAppController.getActiveSession().getLoginEmail());
/* 62 */                VineAccountHelper.setDidShowStoreContactsPrompt(am, account);
/* 63 */                boolean enable = which == -1;
/* 64 */                FindFriendsBaseFragment.this.mAppController.updateEnableAddressBook(enable);
/* 65 */                SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(FindFriendsBaseFragment.this.getActivity()).edit();
/* 66 */                editor.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, enable).apply();
/* 67 */                FindFriendsBaseFragment.this.fetchContent(3);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 72 */        public void onAttach(Activity activity) {
/* 73 */            super.onAttach(activity);
/* 74 */            this.mActivity = (FindFriendsBaseActivity) activity;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 78 */        public void onCreate(Bundle savedInstanceState) {
/* 79 */            super.onCreate(savedInstanceState);
/* 81 */            this.mBold = new StyleSpan[]{new StyleSpan(1)};
/* 83 */            if (savedInstanceState != null) {
/* 84 */                if (savedInstanceState.containsKey(STATE_FETCHED)) {
/* 85 */                    this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                        }
/* 87 */                if (savedInstanceState.containsKey(STATE_FRIENDSHIPS)) {
/* 88 */                    this.mFriendships = (Friendships) savedInstanceState.getParcelable(STATE_FRIENDSHIPS);
                        }
                    } else {
/* 91 */                this.mFriendships = new Friendships();
                    }
/* 95 */            this.mAppController.removeUsers(this.mAppController.getActiveSession(), 7);
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 101 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 102 */           View view = createView(inflater, C0257R.layout.find_friends_address_list_fragment, container);
/* 103 */           this.mListView.setDividerHeight(0);
/* 104 */           return view;
                }

                @Override // android.support.v4.app.Fragment
/* 108 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 109 */           super.onActivityCreated(savedInstanceState);
/* 110 */           if (this.mCursorAdapter == null) {
/* 111 */               this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
/* 112 */               this.mSortOrder = Vine.Users.SORT_ORDER_DEFAULT;
/* 113 */               this.mCursorAdapter = new UsersAdapter(getActivity(), this.mAppController, true, this, this.mFriendships, 0);
                    }
/* 116 */           this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 120 */       public void onSaveInstanceState(Bundle outState) {
/* 121 */           super.onSaveInstanceState(outState);
/* 122 */           outState.putBoolean(STATE_FETCHED, this.mFetched);
/* 123 */           outState.putParcelable(STATE_FRIENDSHIPS, this.mFriendships);
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 127 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
/* 130 */                   Uri.Builder uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
/* 132 */                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mSearchQuery));
/* 133 */                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(7));
/* 135 */                   return new CursorLoader(getActivity(), uri.build(), this.mProjection, null, null, this.mSortOrder);
                        default:
/* 141 */                   return new CursorLoader(getActivity(), ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_ADDRESS, this.mAppController.getActiveId()), this.mProjection, null, null, this.mSortOrder);
                    }
                }

/* 146 */       protected void fetchContent(int fetchType) {
/* 147 */           this.mFetched = true;
/* 148 */           showProgress(fetchType);
                    switch (fetchType) {
                        case 3:
/* 152 */                   ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 154 */                   d.setMessage(getString(C0257R.string.finding_friends_address));
/* 155 */                   d.setProgressStyle(0);
/* 156 */                   d.show();
/* 157 */                   this.mProgressDialog = d;
/* 158 */                   this.mAppController.fetchAddressFriends(this.mAppController.getActiveSession());
                            break;
                    }
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 171 */       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 172 */           if (view.getTag() instanceof UserViewHolder) {
/* 173 */               UserViewHolder h = (UserViewHolder) view.getTag();
/* 174 */               startProfileActivity(h.userId);
                    }
                }

                @Override // co.vine.android.FindFriendsNUXActivity.FilterableFriendsList
/* 183 */       public boolean shouldShowSearchIcon() {
                    return (this.mCursorAdapter == null || this.mCursorAdapter.isEmpty()) ? false : true;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 188 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 189 */           super.onLoadFinished(loader, cursor);
/* 190 */           if (cursor != null && cursor.getCount() > 0 && (this.mActivity instanceof FindFriendsNUXActivity)) {
/* 191 */               ((FindFriendsNUXActivity) this.mActivity).showSearchIcon(true);
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 196 */       public void onLoaderReset(Loader<Cursor> loader) {
/* 197 */           this.mCursorAdapter.swapCursor(null);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 201 */       public void onMoveTo(int oldPosition) {
/* 202 */           super.onMoveTo(oldPosition);
/* 203 */           getActivity().invalidateOptionsMenu();
                }
            }
