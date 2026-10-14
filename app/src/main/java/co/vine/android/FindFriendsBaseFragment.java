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
            
            
    public abstract class FindFriendsBaseFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, FindFriendsNUXActivity.FilterableFriendsList {
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


                
        class C02101 implements PromptDialogSupportFragment.OnDialogDoneListener {
            C02101() {
                    }

                    @Override 
            public void onDialogDone(DialogInterface dialog, int id, int which) {
                AccountManager am = AccountManager.get(FindFriendsBaseFragment.this.getActivity());
                Account account = VineAccountHelper.getAccount(FindFriendsBaseFragment.this.getActivity(), FindFriendsBaseFragment.this.mAppController.getActiveSession().getLoginEmail());
                VineAccountHelper.setDidShowStoreContactsPrompt(am, account);
                boolean enable = which == -1;
                FindFriendsBaseFragment.this.mAppController.updateEnableAddressBook(enable);
                SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(FindFriendsBaseFragment.this.getActivity()).edit();
                editor.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, enable).apply();
                FindFriendsBaseFragment.this.fetchContent(3);
                    }
                }

                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.mActivity = (FindFriendsBaseActivity) activity;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mBold = new StyleSpan[]{new StyleSpan(1)};
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(STATE_FETCHED)) {
                    this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                        }
                if (savedInstanceState.containsKey(STATE_FRIENDSHIPS)) {
                    this.mFriendships = (Friendships) savedInstanceState.getParcelable(STATE_FRIENDSHIPS);
                        }
                    } else {
                this.mFriendships = new Friendships();
                    }
            this.mAppController.removeUsers(this.mAppController.getActiveSession(), 7);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View view = createView(inflater, C0257R.layout.find_friends_address_list_fragment, container);
           this.mListView.setDividerHeight(0);
           return view;
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           if (this.mCursorAdapter == null) {
               this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
               this.mSortOrder = Vine.Users.SORT_ORDER_DEFAULT;
               this.mCursorAdapter = new UsersAdapter(getActivity(), this.mAppController, true, this, this.mFriendships, 0);
                    }
           this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_FETCHED, this.mFetched);
           outState.putParcelable(STATE_FRIENDSHIPS, this.mFriendships);
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
                   Uri.Builder uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mSearchQuery));
                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(7));
                   return new CursorLoader(getActivity(), uri.build(), this.mProjection, null, null, this.mSortOrder);
                        default:
                   return new CursorLoader(getActivity(), ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_ADDRESS, this.mAppController.getActiveId()), this.mProjection, null, null, this.mSortOrder);
                    }
                }

       protected void fetchContent(int fetchType) {
           this.mFetched = true;
           showProgress(fetchType);
                    switch (fetchType) {
                        case 3:
                   ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                   d.setMessage(getString(C0257R.string.finding_friends_address));
                   d.setProgressStyle(0);
                   d.show();
                   this.mProgressDialog = d;
                   this.mAppController.fetchAddressFriends(this.mAppController.getActiveSession());
                            break;
                    }
                }

                @Override 
       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           if (view.getTag() instanceof UserViewHolder) {
               UserViewHolder h = (UserViewHolder) view.getTag();
               startProfileActivity(h.userId);
                    }
                }

                @Override 
       public boolean shouldShowSearchIcon() {
                    return (this.mCursorAdapter == null || this.mCursorAdapter.isEmpty()) ? false : true;
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           super.onLoadFinished(loader, cursor);
           if (cursor != null && cursor.getCount() > 0 && (this.mActivity instanceof FindFriendsNUXActivity)) {
               ((FindFriendsNUXActivity) this.mActivity).showSearchIcon(true);
                    }
                }

                @Override 
       public void onLoaderReset(Loader<Cursor> loader) {
           this.mCursorAdapter.swapCursor(null);
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           super.onMoveTo(oldPosition);
           getActivity().invalidateOptionsMenu();
                }
            }
