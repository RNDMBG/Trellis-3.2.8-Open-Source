            package co.vine.android;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ImageButton;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.Util;
            import co.vine.android.widget.UserViewHolder;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 60 */    public class UsersMemoryAdapter extends BaseAdapter {
                protected final AppController mAppController;
                protected final Context mContext;
                private boolean mFollow;
                private Friendships mFriendships;
                private View.OnClickListener mListener;
                long mLoggedInUserId;
                private final int mProfileImageSize;
                protected ArrayList<VineUser> mUsers;
                protected final ArrayList<WeakReference<UserViewHolder>> mViewHolders = new ArrayList<>();

/* 61 */        public UsersMemoryAdapter(Context context, AppController appController, boolean follow, View.OnClickListener listener, Friendships friendships) {
/* 63 */            this.mContext = context;
/* 64 */            this.mAppController = appController;
/* 65 */            this.mLoggedInUserId = appController.getActiveId();
/* 66 */            this.mFollow = follow;
/* 67 */            this.mListener = listener;
/* 68 */            this.mFriendships = friendships;
/* 70 */            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override // android.widget.Adapter
                @Nullable
/* 76 */        public View getView(int position, View convertView, ViewGroup parent) {
                    View view;
/* 77 */            if (convertView == null) {
/* 78 */                view = newView(position, parent);
                    } else {
/* 80 */                view = convertView;
                    }
/* 82 */            bindView(view, position);
/* 83 */            return view;
                }

/* 86 */        public View newView(int position, ViewGroup parent) {
/* 87 */            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
/* 88 */            View v = layoutInflater.inflate(C0257R.layout.user_row_view, parent, false);
/* 90 */            UserViewHolder holder = new UserViewHolder(v);
/* 92 */            if (this.mFollow) {
/* 93 */                holder.followButton.setOnClickListener(this.mListener);
                    } else {
/* 95 */                holder.followButton.setVisibility(8);
                    }
/* 98 */            this.mViewHolders.add(new WeakReference<>(holder));
/* 99 */            v.setTag(holder);
/* 100 */           return v;
                }

/* 103 */       public void bindView(View view, int position) {
                    boolean following;
/* 104 */           VineUser user = this.mUsers.get(position);
/* 106 */           UserViewHolder holder = (UserViewHolder) view.getTag();
/* 107 */           holder.username.setText(user.username);
/* 108 */           holder.userId = user.userId;
/* 110 */           if (user.verified == 1) {
/* 111 */               holder.verified.setVisibility(0);
                    } else {
/* 113 */               holder.verified.setVisibility(8);
                    }
/* 117 */           ImageButton followButton = holder.followButton;
/* 118 */           if (followButton != null) {
/* 119 */               if (this.mFollow && holder.userId != this.mAppController.getActiveId()) {
/* 120 */                   followButton.setVisibility(0);
/* 122 */                   Friendships friendships = this.mFriendships;
/* 123 */                   long userId = user.userId;
/* 125 */                   if (friendships != null && friendships.contains(userId)) {
/* 127 */                       following = friendships.isFollowing(userId);
                            } else {
/* 129 */                       following = user.isFollowing();
                            }
/* 135 */                   followButton.setTag(new FollowButtonViewHolder(userId, following));
/* 137 */                   if (following) {
/* 138 */                       followButton.setBackgroundResource(C0257R.drawable.btn_following_default);
                            } else {
/* 140 */                       followButton.setBackgroundResource(C0257R.drawable.btn_follow_default);
                            }
                        } else {
/* 143 */                   followButton.setVisibility(8);
                        }
                    }
/* 147 */           String url = user.avatarUrl;
/* 148 */           if (!TextUtils.isEmpty(url)) {
/* 149 */               ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
/* 150 */               if (Util.isDefaultAvatarUrl(url)) {
/* 151 */                   Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mContext.getResources().getColor(C0257R.color.vine_green));
/* 159 */                   return;
                        } else {
/* 153 */                   holder.avatarUrl = key;
/* 154 */                   setUserImage(holder, this.mAppController.getPhotoBitmap(key));
/* 159 */                   return;
                        }
                    }
/* 157 */           setUserImage(holder, null);
                }

/* 161 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserViewHolder> ref : this.mViewHolders) {
/* 164 */                   UserViewHolder holder = ref.get();
/* 165 */                   if (holder == null) {
/* 166 */                       toRemove.add(ref);
                            } else {
/* 168 */                       UrlImage image = images.get(holder.avatarUrl);
/* 169 */                       if (image != null && image.isValid()) {
/* 170 */                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserViewHolder> r : toRemove) {
/* 175 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.widget.Adapter
/* 180 */       public int getCount() {
/* 181 */           if (this.mUsers != null) {
/* 182 */               return this.mUsers.size();
                    }
                    return 0;
                }

                @Override // android.widget.Adapter
/* 188 */       public Object getItem(int position) {
/* 189 */           if (this.mUsers != null) {
/* 190 */               return this.mUsers.get(position);
                    }
                    return null;
                }

                @Override // android.widget.Adapter
/* 196 */       public long getItemId(int position) {
                    VineUser user;
/* 197 */           if (this.mUsers == null || position >= this.mUsers.size() || (user = this.mUsers.get(position)) == null) {
                        return 0L;
                    }
/* 200 */           return user.userId;
                }

/* 206 */       private void setUserImage(UserViewHolder holder, Bitmap bmp) {
/* 207 */           holder.image.setColorFilter((ColorFilter) null);
/* 208 */           if (bmp != null) {
/* 209 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
/* 211 */               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }

/* 216 */       public void mergeData(ArrayList<VineUser> users, boolean mergeAfter) {
/* 217 */           if (!mergeAfter) {
/* 218 */               this.mUsers = new ArrayList<>();
                    }
/* 221 */           if (this.mUsers == null) {
/* 222 */               this.mUsers = new ArrayList<>();
                    }
/* 225 */           if (users != null) {
                        for (VineUser user : users) {
/* 227 */                   this.mUsers.add(user);
                        }
                    }
/* 230 */           if (users != null) {
/* 231 */               notifyDataSetChanged();
                    }
                }

/* 235 */       public ArrayList<VineUser> getUsers() {
/* 236 */           return this.mUsers;
                }
            }
