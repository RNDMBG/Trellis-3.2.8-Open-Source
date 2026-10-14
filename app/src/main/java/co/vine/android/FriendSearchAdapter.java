            package co.vine.android;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.os.Build;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
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
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class FriendSearchAdapter extends ArrayAdapter<VineUser> {
                private final AppController mAppController;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<UserViewHolder>> mViewHolders;
                private final int mVineGreen;

/* 36 */        public FriendSearchAdapter(Context context, AppController appController, List<VineUser> users) {
/* 37 */            super(context, 0, users);
/* 38 */            this.mAppController = appController;
/* 39 */            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
/* 40 */            this.mViewHolders = new ArrayList<>();
/* 41 */            this.mVineGreen = context.getResources().getColor(C0257R.color.vine_green);
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 46 */        public View getView(int position, View convertView, ViewGroup parent) {
                    UserViewHolder holder;
/* 47 */            if (convertView != null) {
/* 48 */                holder = (UserViewHolder) convertView.getTag();
/* 49 */                this.mViewHolders.add(new WeakReference<>(holder));
                    } else {
/* 51 */                LayoutInflater inflater = LayoutInflater.from(getContext());
/* 52 */                convertView = inflater.inflate(C0257R.layout.user_row_simple_view, parent, false);
/* 53 */                holder = new UserViewHolder(convertView);
                    }
/* 56 */            VineUser user = getItem(position);
/* 57 */            holder.username.setText(user.username);
/* 58 */            String url = user.avatarUrl;
/* 59 */            if (!TextUtils.isEmpty(url)) {
/* 60 */                ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
/* 61 */                if (Util.isDefaultAvatarUrl(url)) {
/* 62 */                    Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mVineGreen);
                        } else {
/* 64 */                    holder.avatarUrl = key;
/* 65 */                    setUserImage(holder, this.mAppController.getPhotoBitmap(key));
                        }
                    } else {
/* 68 */                setUserImage(holder, null);
                    }
/* 71 */            convertView.setTag(holder);
/* 72 */            return convertView;
                }

/* 75 */        public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserViewHolder> ref : this.mViewHolders) {
/* 78 */                    UserViewHolder holder = ref.get();
/* 79 */                    if (holder == null) {
/* 80 */                        toRemove.add(ref);
                            } else {
/* 82 */                        UrlImage image = images.get(holder.avatarUrl);
/* 83 */                        if (image != null && image.isValid()) {
/* 84 */                            setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserViewHolder> r : toRemove) {
/* 89 */                    this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 94 */        public long getItemId(int position) {
/* 95 */            VineUser user = getItem(position);
/* 96 */            return user.userId;
                }

/* 100 */       public void setData(List<VineUser> users) {
/* 101 */           if (Build.VERSION.SDK_INT >= 11) {
/* 102 */               addAll(users);
/* 108 */               return;
                    }
                    for (VineUser user : users) {
/* 105 */               add(user);
                    }
                }

/* 110 */       private void setUserImage(UserViewHolder holder, Bitmap bmp) {
/* 111 */           holder.image.setColorFilter((ColorFilter) null);
/* 112 */           if (bmp != null) {
/* 113 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(getContext().getResources(), bmp));
                    } else {
/* 116 */               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }
            }
