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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class FindFriendsAddressFragment extends FindFriendsBaseFragment {
                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 30 */        public void onCreate(Bundle savedInstanceState) {
/* 31 */            super.onCreate(savedInstanceState);
/* 32 */            this.mAppSessionListener = new FriendsListener();
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 36 */        public void onViewCreated(View view, Bundle savedInstanceState) {
/* 37 */            super.onViewCreated(view, savedInstanceState);
/* 38 */            LayoutInflater inflater = LayoutInflater.from(getActivity());
/* 39 */            View title = inflater.inflate(C0257R.layout.list_header, (ViewGroup) null);
/* 40 */            this.mListView.addHeaderView(title);
/* 42 */            ((TextView) title.findViewById(C0257R.id.subtitle)).setText(C0257R.string.find_friends_address_subtitle);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 47 */        public void onResume() {
/* 48 */            super.onResume();
/* 49 */            if (!this.mFetched) {
/* 50 */                AccountManager am = AccountManager.get(getActivity());
/* 51 */                Account account = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
/* 53 */                if (VineAccountHelper.shouldShowStoreContactsPrompt(am, account)) {
/* 54 */                    PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 56 */                    p.setListener(this.mStoreContactDialogDoneListener);
/* 57 */                    p.setMessage(C0257R.string.store_contacts_desc);
/* 58 */                    p.setTitle(C0257R.string.store_contacts);
/* 59 */                    p.setNegativeButton(C0257R.string.f87no);
/* 60 */                    p.setPositiveButton(C0257R.string.f88ok);
/* 61 */                    p.setCancelable(false);
/* 62 */                    p.show(getFragmentManager());
/* 67 */                    return;
                        }
/* 64 */                fetchContent(3);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, com.twitter.android.widget.RefreshListener
/* 71 */        public ItemPosition getFirstItemPosition() {
/* 72 */            return null;
                }

                @Override // co.vine.android.FindFriendsBaseFragment, android.view.View.OnClickListener
/* 76 */        public void onClick(View view) {
                    FollowButtonViewHolder holder;
/* 77 */            if (view.getId() == 2131427917 && (holder = (FollowButtonViewHolder) view.getTag()) != null) {
/* 80 */                if (!holder.following) {
/* 81 */                    this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, true);
/* 83 */                    this.mFriendships.addFollowing(holder.userId);
/* 84 */                    this.mCursorAdapter.notifyDataSetChanged();
/* 85 */                    this.mNewFollowsCount++;
/* 94 */                    return;
                        }
/* 87 */                this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, true);
/* 89 */                this.mFriendships.removeFollowing(holder.userId);
/* 90 */                this.mCursorAdapter.notifyDataSetChanged();
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 97 */        public void onMoveAway(int newPosition) {
/* 98 */            super.onMoveAway(newPosition);
/* 99 */            if (this.mFetched) {
/* 100 */               String count = this.mNewFollowsCount > 15 ? ">15" : String.valueOf(this.mNewFollowsCount);
/* 101 */               FlurryUtils.trackAddressNewFollowingCount(count);
                    }
/* 103 */           this.mNewFollowsCount = 0;
                }

                @Override // co.vine.android.FindFriendsBaseFragment
/* 106 */       protected void startProfileActivity(long userId) {
/* 107 */           ProfileActivity.start(getActivity(), userId, "Find Friends: Address", true);
                }

                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.FindFriendsNUXActivity.FilterableFriendsList
/* 111 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

/* 112 */       class FriendsListener extends AppSessionListener {
/* 113 */           FriendsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 118 */           public void onGetAddressFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
/* 119 */               FindFriendsAddressFragment.this.hideProgress(3);
/* 120 */               FindFriendsAddressFragment.this.dismissProgressDialog();
/* 121 */               if (reqId != null && statusCode == 200) {
/* 122 */                   if (FindFriendsAddressFragment.this.mCursorAdapter.isEmpty()) {
/* 123 */                       FindFriendsAddressFragment.this.showSadface(true);
                            }
/* 125 */                   FlurryUtils.trackFindFriendsAddressCount(count);
                        } else {
/* 127 */                   FindFriendsAddressFragment.this.showSadface(true);
/* 128 */                   Util.showCenteredToast(FindFriendsAddressFragment.this.getActivity(), C0257R.string.find_friends_address_error);
/* 129 */                   FlurryUtils.trackFindFriendsAddressFailure();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 134 */           public void onRemoveUsersComplete(String reqId) {
/* 135 */               if (FindFriendsAddressFragment.this.mCursorAdapter.getCursor() == null) {
/* 136 */                   FindFriendsAddressFragment.this.initLoader();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 141 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 142 */               ((UsersAdapter) FindFriendsAddressFragment.this.mCursorAdapter).setUserImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 147 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 148 */               if (reqId != null && statusCode != 200) {
/* 149 */                   FindFriendsAddressFragment.this.mFriendships.removeFollowing(userId);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 155 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 156 */               if (reqId != null && statusCode != 200) {
/* 157 */                   FindFriendsAddressFragment.this.mFriendships.addFollowing(userId);
                        }
                    }
                }
            }
