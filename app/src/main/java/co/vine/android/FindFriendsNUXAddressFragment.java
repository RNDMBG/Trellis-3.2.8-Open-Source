            package co.vine.android;

            import android.app.Activity;
            import android.os.Bundle;
            import android.os.Handler;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.twitter.android.widget.ItemPosition;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class FindFriendsNUXAddressFragment extends FindFriendsBaseFragment {
                private FindFriendsNUXActivity mActivity;
                private ArrayList<VineUser> mAddressFriends;
                private RelativeLayout mContactsCountContainer;
                private TextView mContactsText;
                private boolean mFilterLoaderIsReady;
                private Runnable mFilterRunnable = new RunnableC02121();
                private Handler mHandler;
                private TextView mSelectAllToggle;
                private boolean mSelected;

/* 29 */        static /* synthetic */ boolean access$100(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mFilterLoaderIsReady;
                }

/* 29 */        static /* synthetic */ boolean access$102(FindFriendsNUXAddressFragment x0, boolean x1) {
/* 30 */            x0.mFilterLoaderIsReady = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ RelativeLayout access$200(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mContactsCountContainer;
                }

/* 29 */        static /* synthetic */ TextView access$300(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mContactsText;
                }

/* 29 */        static /* synthetic */ boolean access$400(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mSelected;
                }

/* 29 */        static /* synthetic */ TextView access$500(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mSelectAllToggle;
                }

/* 29 */        static /* synthetic */ FindFriendsNUXActivity access$600(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mActivity;
                }

/* 29 */        static /* synthetic */ ArrayList access$700(FindFriendsNUXAddressFragment x0) {
/* 30 */            return x0.mAddressFriends;
                }

                @Override // co.vine.android.FindFriendsBaseFragment, android.support.v4.app.Fragment
/* 42 */        public void onAttach(Activity activity) {
/* 43 */            super.onAttach(activity);
/* 44 */            this.mActivity = (FindFriendsNUXActivity) activity;
                }

                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 48 */        public void onCreate(Bundle savedInstanceState) {
/* 49 */            super.onCreate(savedInstanceState);
/* 51 */            Bundle args = getArguments();
/* 52 */            if (args != null) {
/* 53 */                setFocused(args.getBoolean("take_focus", true));
                    }
/* 56 */            this.mAppSessionListener = new FriendsNUXListener(this, null);
/* 57 */            this.mSelected = true;
/* 58 */            this.mAddressFriends = new ArrayList<>();
/* 59 */            this.mHandler = new Handler();
                }

                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 64 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 65 */            View v = super.onCreateView(inflater, container, savedInstanceState);
/* 66 */            this.mContactsCountContainer = (RelativeLayout) v.findViewById(C0257R.id.contacts_count_container);
/* 67 */            this.mContactsText = (TextView) v.findViewById(C0257R.id.contacts_text);
/* 68 */            this.mSelectAllToggle = (TextView) v.findViewById(C0257R.id.select_all_toggle);
/* 69 */            this.mSelectAllToggle.setOnClickListener(this);
/* 70 */            return v;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 74 */        public void onResume() {
/* 75 */            super.onResume();
/* 76 */            if (isFocused()) {
/* 77 */                onMoveTo(-1);
                    }
                }

                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 82 */        public void onMoveTo(int oldPosition) {
/* 83 */            super.onMoveTo(oldPosition);
/* 84 */            this.mActivity.clearSearch();
/* 85 */            if (!this.mFetched) {
/* 87 */                PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 89 */                p.setListener(this.mStoreContactDialogDoneListener);
/* 90 */                p.setMessage(C0257R.string.store_contacts_desc);
/* 91 */                p.setTitle(C0257R.string.store_contacts);
/* 92 */                p.setNegativeButton(C0257R.string.f87no);
/* 93 */                p.setPositiveButton(C0257R.string.f88ok);
/* 94 */                p.setCancelable(false);
/* 95 */                p.show(getFragmentManager());
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, com.twitter.android.widget.RefreshListener
/* 101 */       public ItemPosition getFirstItemPosition() {
/* 102 */           return null;
                }

                @Override // co.vine.android.FindFriendsBaseFragment, android.view.View.OnClickListener
/* 106 */       public void onClick(View view) {
/* 107 */           int id = view.getId();
/* 108 */           if (id == 2131427917) {
/* 109 */               FollowButtonViewHolder holder = (FollowButtonViewHolder) view.getTag();
/* 110 */               if (holder != null) {
/* 111 */                   if (!holder.following) {
/* 112 */                       this.mActivity.addUserToFollow(holder.userId);
/* 113 */                       this.mFriendships.addFollowing(holder.userId);
/* 114 */                       this.mCursorAdapter.notifyDataSetChanged();
/* 127 */                       return;
                            } else {
/* 116 */                       this.mActivity.removeUserToFollow(holder.userId);
/* 117 */                       this.mFriendships.removeFollowing(holder.userId);
/* 118 */                       this.mCursorAdapter.notifyDataSetChanged();
/* 127 */                       return;
                            }
                        }
/* 127 */               return;
                    }
/* 121 */           if (id == 2131427576) {
/* 122 */               this.mSelected = !this.mSelected;
/* 123 */               this.mActivity.toggleFollowAll(this.mSelected, this.mAddressFriends, this.mFriendships);
/* 124 */               this.mSelectAllToggle.setText(this.mSelected ? C0257R.string.deselect_all : C0257R.string.select_all);
/* 125 */               this.mCursorAdapter.notifyDataSetChanged();
                    }
                }

                @Override // co.vine.android.FindFriendsBaseFragment
/* 129 */       protected void startProfileActivity(long userId) {
/* 130 */           ProfileActivity.start(getActivity(), userId, "Find Friends: Address", false);
                }

                @Override // co.vine.android.FindFriendsBaseFragment, co.vine.android.FindFriendsNUXActivity.FilterableFriendsList
/* 134 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 135 */           this.mHandler.removeCallbacks(this.mFilterRunnable);
/* 136 */           this.mSearchQuery = s.toString();
/* 137 */           if (!TextUtils.isEmpty(this.mSearchQuery)) {
/* 138 */               this.mHandler.postDelayed(this.mFilterRunnable, 300L);
                    } else {
/* 140 */               restartLoader(0);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.FindFriendsNUXAddressFragment$1 */
/* 142 */       class RunnableC02121 implements Runnable {
/* 143 */           RunnableC02121() {
                    }

                    @Override // java.lang.Runnable
/* 146 */           public void run() {
/* 147 */               if (!FindFriendsNUXAddressFragment.access$100(FindFriendsNUXAddressFragment.this)) {
/* 148 */                   FindFriendsNUXAddressFragment.this.initLoader(1);
/* 149 */                   FindFriendsNUXAddressFragment.access$102(FindFriendsNUXAddressFragment.this, true);
                        } else {
/* 151 */                   FindFriendsNUXAddressFragment.this.restartLoader(1);
                        }
                    }
                }

/* 154 */       private class FriendsNUXListener extends AppSessionListener {
/* 155 */           private FriendsNUXListener() {
                    }

/* 155 */           /* synthetic */ FriendsNUXListener(FindFriendsNUXAddressFragment x0, RunnableC02121 x1) {
/* 156 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 160 */           public void onGetAddressFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
/* 161 */               FindFriendsNUXAddressFragment.this.hideProgress(3);
/* 162 */               FindFriendsNUXAddressFragment.this.dismissProgressDialog();
/* 163 */               if (reqId != null && statusCode == 200) {
/* 164 */                   if (count <= 0) {
/* 165 */                       FindFriendsNUXAddressFragment.this.showSadface(true);
                            } else {
/* 167 */                       FindFriendsNUXAddressFragment.this.showSadface(false);
/* 168 */                       FindFriendsNUXAddressFragment.access$200(FindFriendsNUXAddressFragment.this).setVisibility(0);
/* 169 */                       FindFriendsNUXAddressFragment.access$300(FindFriendsNUXAddressFragment.this).setText(FindFriendsNUXAddressFragment.this.getString(C0257R.string.find_friends_contacts_count, Integer.valueOf(count)));
/* 170 */                       FindFriendsNUXAddressFragment.access$500(FindFriendsNUXAddressFragment.this).setText(FindFriendsNUXAddressFragment.access$400(FindFriendsNUXAddressFragment.this) ? C0257R.string.deselect_all : C0257R.string.select_all);
/* 171 */                       FindFriendsNUXAddressFragment.access$600(FindFriendsNUXAddressFragment.this).addUsersToFollow(users, FindFriendsNUXAddressFragment.this.mFriendships);
/* 172 */                       FindFriendsNUXAddressFragment.access$700(FindFriendsNUXAddressFragment.this).addAll(users);
                            }
/* 175 */                   FlurryUtils.trackFindFriendsAddressCount(count);
/* 181 */                   return;
                        }
/* 177 */               FindFriendsNUXAddressFragment.this.showSadface(true);
/* 178 */               Util.showCenteredToast(FindFriendsNUXAddressFragment.this.getActivity(), C0257R.string.find_friends_address_error);
/* 179 */               FlurryUtils.trackFindFriendsAddressFailure();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 184 */           public void onRemoveUsersComplete(String reqId) {
/* 185 */               if (FindFriendsNUXAddressFragment.this.mCursorAdapter.getCursor() == null) {
/* 186 */                   FindFriendsNUXAddressFragment.this.initLoader();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 191 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 192 */               ((UsersAdapter) FindFriendsNUXAddressFragment.this.mCursorAdapter).setUserImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 197 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 198 */               if (reqId != null && statusCode != 200) {
/* 199 */                   FindFriendsNUXAddressFragment.this.mFriendships.removeFollowing(userId);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 205 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 206 */               if (reqId != null && statusCode != 200) {
/* 207 */                   FindFriendsNUXAddressFragment.this.mFriendships.addFollowing(userId);
                        }
                    }
                }
            }
