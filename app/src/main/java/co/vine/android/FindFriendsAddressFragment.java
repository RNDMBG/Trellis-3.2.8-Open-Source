            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.twitter.android.widget.ItemPosition;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class FindFriendsAddressFragment extends FindFriendsBaseFragment {
                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new FriendsListener();
                }

                @Override 
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View title = inflater.inflate(R.layout.list_header, (ViewGroup) null);
            this.mListView.addHeaderView(title);
            ((TextView) title.findViewById(R.id.subtitle)).setText(R.string.find_friends_address_subtitle);
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (!this.mFetched) {
                AccountManager am = AccountManager.get(getActivity());
                Account account = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
                if (VineAccountHelper.shouldShowStoreContactsPrompt(am, account)) {
                    PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
                    p.setListener(this.mStoreContactDialogDoneListener);
                    p.setMessage(R.string.store_contacts_desc);
                    p.setTitle(R.string.store_contacts);
                    p.setNegativeButton(R.string.no);
                    p.setPositiveButton(R.string.ok);
                    p.setCancelable(false);
                    p.show(getFragmentManager());
                    return;
                        }
                fetchContent(3);
                    }
                }

                @Override 
        public ItemPosition getFirstItemPosition() {
            return null;
                }

                @Override 
        public void onClick(View view) {
                    FollowButtonViewHolder holder;
            if (view.getId() == R.id.follow_button && (holder = (FollowButtonViewHolder) view.getTag()) != null) {
                if (!holder.following) {
                    this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, true);
                    this.mFriendships.addFollowing(holder.userId);
                    this.mCursorAdapter.notifyDataSetChanged();
                    this.mNewFollowsCount++;
                    return;
                        }
                this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, true);
                this.mFriendships.removeFollowing(holder.userId);
                this.mCursorAdapter.notifyDataSetChanged();
                    }
                }

                @Override 
        public void onMoveAway(int newPosition) {
            super.onMoveAway(newPosition);
            if (this.mFetched) {
               String count = this.mNewFollowsCount > 15 ? ">15" : String.valueOf(this.mNewFollowsCount);
               FlurryUtils.trackAddressNewFollowingCount(count);
                    }
           this.mNewFollowsCount = 0;
                }

                @Override 
       protected void startProfileActivity(long userId) {
           ProfileActivity.start(getActivity(), userId, "Find Friends: Address", true);
                }

                @Override 
       public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

       class FriendsListener extends AppSessionListener {
           FriendsListener() {
                    }

                    @Override 
           public void onGetAddressFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
               FindFriendsAddressFragment.this.hideProgress(3);
               FindFriendsAddressFragment.this.dismissProgressDialog();
               if (reqId != null && statusCode == 200) {
                   if (FindFriendsAddressFragment.this.mCursorAdapter.isEmpty()) {
                       FindFriendsAddressFragment.this.showSadface(true);
                            }
                   FlurryUtils.trackFindFriendsAddressCount(count);
                        } else {
                   FindFriendsAddressFragment.this.showSadface(true);
                   Util.showCenteredToast(FindFriendsAddressFragment.this.getActivity(), R.string.find_friends_address_error);
                   FlurryUtils.trackFindFriendsAddressFailure();
                        }
                    }

                    @Override 
           public void onRemoveUsersComplete(String reqId) {
               if (FindFriendsAddressFragment.this.mCursorAdapter.getCursor() == null) {
                   FindFriendsAddressFragment.this.initLoader();
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ((UsersAdapter) FindFriendsAddressFragment.this.mCursorAdapter).setUserImages(images);
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (reqId != null && statusCode != 200) {
                   FindFriendsAddressFragment.this.mFriendships.removeFollowing(userId);
                        }
                    }

                    @Override 
           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (reqId != null && statusCode != 200) {
                   FindFriendsAddressFragment.this.mFriendships.addFollowing(userId);
                        }
                    }
                }
            }
