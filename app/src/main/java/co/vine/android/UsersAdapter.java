            package co.vine.android;

            import android.content.Context;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
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
/* 69 */    public class UsersAdapter extends CursorAdapter {
                protected final AppController mAppController;
                private boolean mFollow;
                private Friendships mFriendships;
                private View.OnClickListener mListener;
                long mLoggedInUserId;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<UserViewHolder>> mViewHolders;

/* 70 */        public UsersAdapter(Context context, AppController appController, boolean follow, View.OnClickListener listener, Friendships friendships, int flags) {
/* 71 */            super(context, (Cursor) null, flags);
/* 72 */            this.mAppController = appController;
/* 73 */            this.mLoggedInUserId = appController.getActiveId();
/* 74 */            this.mFollow = follow;
/* 75 */            this.mListener = listener;
/* 76 */            this.mFriendships = friendships;
/* 77 */            this.mViewHolders = new ArrayList<>();
/* 78 */            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 82 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 83 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 84 */            View v = layoutInflater.inflate(C0257R.layout.user_row_view, parent, false);
/* 86 */            UserViewHolder holder = new UserViewHolder(v);
/* 88 */            if (this.mFollow) {
/* 89 */                holder.followButton.setOnClickListener(this.mListener);
                    } else {
/* 91 */                holder.followButton.setVisibility(8);
                    }
/* 94 */            this.mViewHolders.add(new WeakReference<>(holder));
/* 95 */            v.setTag(holder);
/* 96 */            return v;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 100 */       public void bindView(View view, Context context, Cursor cursor) {
                    boolean following;
/* 101 */           UserViewHolder holder = (UserViewHolder) view.getTag();
/* 102 */           holder.username.setText(cursor.getString(13));
/* 103 */           holder.userId = cursor.getLong(1);
/* 105 */           if (cursor.getInt(14) == 1) {
/* 106 */               holder.verified.setVisibility(0);
                    } else {
/* 108 */               holder.verified.setVisibility(8);
                    }
/* 112 */           ImageButton followButton = holder.followButton;
/* 113 */           if (this.mFollow && holder.userId != this.mAppController.getActiveId()) {
/* 114 */               followButton.setVisibility(0);
/* 116 */               Friendships friendships = this.mFriendships;
/* 117 */               long userId = cursor.getLong(1);
/* 119 */               if (friendships != null) {
/* 120 */                   if (friendships.contains(userId)) {
/* 121 */                       following = friendships.isFollowing(userId);
                            } else {
/* 123 */                       following = (cursor.getInt(10) & 1) > 0;
                            }
                        } else {
/* 127 */                   following = (cursor.getInt(10) & 1) > 0;
                        }
/* 131 */               followButton.setTag(new FollowButtonViewHolder(userId, following));
/* 133 */               if (following) {
/* 134 */                   followButton.setBackgroundResource(C0257R.drawable.btn_following_default);
                        } else {
/* 136 */                   followButton.setBackgroundResource(C0257R.drawable.btn_follow_default);
                        }
                    } else {
/* 139 */               followButton.setVisibility(8);
                    }
/* 141 */           String url = cursor.getString(2);
/* 142 */           if (!TextUtils.isEmpty(url)) {
/* 143 */               ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
/* 144 */               if (Util.isDefaultAvatarUrl(url)) {
/* 145 */                   Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mContext.getResources().getColor(C0257R.color.vine_green));
/* 153 */                   return;
                        } else {
/* 147 */                   holder.avatarUrl = key;
/* 148 */                   setUserImage(holder, this.mAppController.getPhotoBitmap(key));
/* 153 */                   return;
                        }
                    }
/* 151 */           setUserImage(holder, null);
                }

/* 155 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserViewHolder> ref : this.mViewHolders) {
/* 158 */                   UserViewHolder holder = ref.get();
/* 159 */                   if (holder == null) {
/* 160 */                       toRemove.add(ref);
                            } else {
/* 162 */                       UrlImage image = images.get(holder.avatarUrl);
/* 163 */                       if (image != null && image.isValid()) {
/* 164 */                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserViewHolder> r : toRemove) {
/* 169 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 174 */       public long getItemId(int position) {
/* 175 */           Cursor c = (Cursor) super.getItem(position);
/* 176 */           if (c == null || !c.moveToPosition(position)) {
                        return 0L;
                    }
/* 178 */           return c.getLong(1);
                }

/* 187 */       private void setUserImage(UserViewHolder holder, Bitmap bmp) {
/* 188 */           holder.image.setColorFilter((ColorFilter) null);
/* 189 */           if (bmp != null) {
/* 190 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
/* 192 */               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }
            }
