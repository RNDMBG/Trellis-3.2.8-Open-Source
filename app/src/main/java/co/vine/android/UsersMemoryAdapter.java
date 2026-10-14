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
            
            
    public class UsersMemoryAdapter extends BaseAdapter {
                protected final AppController mAppController;
                protected final Context mContext;
                private boolean mFollow;
                private Friendships mFriendships;
                private View.OnClickListener mListener;
                long mLoggedInUserId;
                private final int mProfileImageSize;
                protected ArrayList<VineUser> mUsers;
                protected final ArrayList<WeakReference<UserViewHolder>> mViewHolders = new ArrayList<>();

        public UsersMemoryAdapter(Context context, AppController appController, boolean follow, View.OnClickListener listener, Friendships friendships) {
            this.mContext = context;
            this.mAppController = appController;
            this.mLoggedInUserId = appController.getActiveId();
            this.mFollow = follow;
            this.mListener = listener;
            this.mFriendships = friendships;
            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override 
                @Nullable
        public View getView(int position, View convertView, ViewGroup parent) {
                    View view;
            if (convertView == null) {
                view = newView(position, parent);
                    } else {
                view = convertView;
                    }
            bindView(view, position);
            return view;
                }

        public View newView(int position, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
            View v = layoutInflater.inflate(C0257R.layout.user_row_view, parent, false);
            UserViewHolder holder = new UserViewHolder(v);
            if (this.mFollow) {
                holder.followButton.setOnClickListener(this.mListener);
                    } else {
                holder.followButton.setVisibility(8);
                    }
            this.mViewHolders.add(new WeakReference<>(holder));
            v.setTag(holder);
           return v;
                }

       public void bindView(View view, int position) {
                    boolean following;
           VineUser user = this.mUsers.get(position);
           UserViewHolder holder = (UserViewHolder) view.getTag();
           holder.username.setText(user.username);
           holder.userId = user.userId;
           if (user.verified == 1) {
               holder.verified.setVisibility(0);
                    } else {
               holder.verified.setVisibility(8);
                    }
           ImageButton followButton = holder.followButton;
           if (followButton != null) {
               if (this.mFollow && holder.userId != this.mAppController.getActiveId()) {
                   followButton.setVisibility(0);
                   Friendships friendships = this.mFriendships;
                   long userId = user.userId;
                   if (friendships != null && friendships.contains(userId)) {
                       following = friendships.isFollowing(userId);
                            } else {
                       following = user.isFollowing();
                            }
                   followButton.setTag(new FollowButtonViewHolder(userId, following));
                   if (following) {
                       followButton.setBackgroundResource(C0257R.drawable.btn_following_default);
                            } else {
                       followButton.setBackgroundResource(C0257R.drawable.btn_follow_default);
                            }
                        } else {
                   followButton.setVisibility(8);
                        }
                    }
           String url = user.avatarUrl;
           if (!TextUtils.isEmpty(url)) {
               ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
               if (Util.isDefaultAvatarUrl(url)) {
                   Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mContext.getResources().getColor(C0257R.color.vine_green));
                   return;
                        } else {
                   holder.avatarUrl = key;
                   setUserImage(holder, this.mAppController.getPhotoBitmap(key));
                   return;
                        }
                    }
           setUserImage(holder, null);
                }

       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserViewHolder> ref : this.mViewHolders) {
                   UserViewHolder holder = ref.get();
                   if (holder == null) {
                       toRemove.add(ref);
                            } else {
                       UrlImage image = images.get(holder.avatarUrl);
                       if (image != null && image.isValid()) {
                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserViewHolder> r : toRemove) {
                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public int getCount() {
           if (this.mUsers != null) {
               return this.mUsers.size();
                    }
                    return 0;
                }

                @Override 
       public Object getItem(int position) {
           if (this.mUsers != null) {
               return this.mUsers.get(position);
                    }
                    return null;
                }

                @Override 
       public long getItemId(int position) {
                    VineUser user;
           if (this.mUsers == null || position >= this.mUsers.size() || (user = this.mUsers.get(position)) == null) {
                        return 0L;
                    }
           return user.userId;
                }

       private void setUserImage(UserViewHolder holder, Bitmap bmp) {
           holder.image.setColorFilter((ColorFilter) null);
           if (bmp != null) {
               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }

       public void mergeData(ArrayList<VineUser> users, boolean mergeAfter) {
           if (!mergeAfter) {
               this.mUsers = new ArrayList<>();
                    }
           if (this.mUsers == null) {
               this.mUsers = new ArrayList<>();
                    }
           if (users != null) {
                        for (VineUser user : users) {
                   this.mUsers.add(user);
                        }
                    }
           if (users != null) {
               notifyDataSetChanged();
                    }
                }

       public ArrayList<VineUser> getUsers() {
           return this.mUsers;
                }
            }
