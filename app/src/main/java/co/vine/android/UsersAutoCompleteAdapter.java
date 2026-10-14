            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineTypeAhead;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.Util;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class UsersAutoCompleteAdapter extends CursorAdapter {
                private AppController mAppController;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<UserDropDownViewHolder>> mViewHolders;

/* 39 */        public UsersAutoCompleteAdapter(Activity activity, AppController appController) {
/* 40 */            super((Context) activity, (Cursor) null, true);
/* 41 */            this.mAppController = appController;
/* 42 */            this.mViewHolders = new ArrayList<>();
/* 43 */            this.mProfileImageSize = activity.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 47 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 48 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 49 */            View rowView = layoutInflater.inflate(C0257R.layout.user_dropdown_row_view, parent, false);
/* 50 */            UserDropDownViewHolder vh = new UserDropDownViewHolder(rowView);
/* 51 */            vh.image.setScaleType(ImageView.ScaleType.FIT_CENTER);
/* 52 */            this.mViewHolders.add(new WeakReference<>(vh));
/* 53 */            rowView.setTag(vh);
/* 54 */            return rowView;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 58 */        public void bindView(View view, Context context, Cursor cursor) {
/* 59 */            UserDropDownViewHolder holder = (UserDropDownViewHolder) view.getTag();
/* 60 */            holder.userId = cursor.getLong(1);
/* 62 */            String url = cursor.getString(3);
/* 63 */            if (!TextUtils.isEmpty(url)) {
/* 64 */                ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
/* 65 */                if (Util.isDefaultAvatarUrl(url)) {
/* 66 */                    Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mContext.getResources().getColor(C0257R.color.vine_green));
                        } else {
/* 68 */                    holder.avatarUrl = key;
/* 69 */                    setUserImage(holder, this.mAppController.getPhotoBitmap(key));
                        }
                    } else {
/* 72 */                setUserImage(holder, null);
                    }
/* 74 */            holder.userNameView.setText("@" + cursor.getString(2));
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 78 */        public Object getItem(int position) {
                    long id;
                    String tagName;
/* 79 */            Cursor c = (Cursor) super.getItem(position);
/* 82 */            if (c != null) {
/* 83 */                id = c.getLong(1);
/* 84 */                tagName = c.getString(2);
                    } else {
/* 86 */                id = 0;
/* 87 */                tagName = "";
                    }
/* 89 */            return new VineTypeAhead(VineEntity.TYPE_MENTION, tagName, id);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 93 */        public long getItemId(int position) {
/* 94 */            Cursor c = (Cursor) super.getItem(position);
/* 95 */            if (c != null) {
/* 96 */                return c.getLong(1);
                    }
                    return 0L;
                }

/* 106 */       private class UserDropDownViewHolder {
                    public ImageKey avatarUrl;
                    public final ImageView image;
                    public long userId;
                    public final TextView userNameView;

/* 107 */           public UserDropDownViewHolder(View rowView) {
/* 109 */               this.image = (ImageView) rowView.findViewById(C0257R.id.user_image);
/* 110 */               this.userNameView = (TextView) rowView.findViewById(C0257R.id.username);
                    }
                }

/* 114 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserDropDownViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserDropDownViewHolder> ref : this.mViewHolders) {
/* 118 */                   UserDropDownViewHolder holder = ref.get();
/* 119 */                   if (holder == null) {
/* 120 */                       toRemove.add(ref);
                            } else {
/* 122 */                       UrlImage image = images.get(holder.avatarUrl);
/* 123 */                       if (image != null && image.isValid()) {
/* 124 */                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserDropDownViewHolder> r : toRemove) {
/* 129 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 133 */       private void setUserImage(UserDropDownViewHolder holder, Bitmap bmp) {
/* 134 */           holder.image.setColorFilter((ColorFilter) null);
/* 135 */           if (bmp != null) {
/* 136 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
/* 138 */               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }
            }
