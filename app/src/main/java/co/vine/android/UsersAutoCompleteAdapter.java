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
            
            
    public class UsersAutoCompleteAdapter extends CursorAdapter {
                private AppController mAppController;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<UserDropDownViewHolder>> mViewHolders;

        public UsersAutoCompleteAdapter(Activity activity, AppController appController) {
            super((Context) activity, (Cursor) null, true);
            this.mAppController = appController;
            this.mViewHolders = new ArrayList<>();
            this.mProfileImageSize = activity.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override 
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View rowView = layoutInflater.inflate(C0257R.layout.user_dropdown_row_view, parent, false);
            UserDropDownViewHolder vh = new UserDropDownViewHolder(rowView);
            vh.image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            this.mViewHolders.add(new WeakReference<>(vh));
            rowView.setTag(vh);
            return rowView;
                }

                @Override 
        public void bindView(View view, Context context, Cursor cursor) {
            UserDropDownViewHolder holder = (UserDropDownViewHolder) view.getTag();
            holder.userId = cursor.getLong(1);
            String url = cursor.getString(3);
            if (!TextUtils.isEmpty(url)) {
                ImageKey key = new ImageKey(url, this.mProfileImageSize, this.mProfileImageSize, true);
                if (Util.isDefaultAvatarUrl(url)) {
                    Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mContext.getResources().getColor(C0257R.color.vine_green));
                        } else {
                    holder.avatarUrl = key;
                    setUserImage(holder, this.mAppController.getPhotoBitmap(key));
                        }
                    } else {
                setUserImage(holder, null);
                    }
            holder.userNameView.setText("@" + cursor.getString(2));
                }

                @Override 
        public Object getItem(int position) {
                    long id;
                    String tagName;
            Cursor c = (Cursor) super.getItem(position);
            if (c != null) {
                id = c.getLong(1);
                tagName = c.getString(2);
                    } else {
                id = 0;
                tagName = "";
                    }
            return new VineTypeAhead(VineEntity.TYPE_MENTION, tagName, id);
                }

                @Override 
        public long getItemId(int position) {
            Cursor c = (Cursor) super.getItem(position);
            if (c != null) {
                return c.getLong(1);
                    }
                    return 0L;
                }

       private class UserDropDownViewHolder {
                    public ImageKey avatarUrl;
                    public final ImageView image;
                    public long userId;
                    public final TextView userNameView;

           public UserDropDownViewHolder(View rowView) {
               this.image = (ImageView) rowView.findViewById(C0257R.id.user_image);
               this.userNameView = (TextView) rowView.findViewById(C0257R.id.username);
                    }
                }

       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<UserDropDownViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<UserDropDownViewHolder> ref : this.mViewHolders) {
                   UserDropDownViewHolder holder = ref.get();
                   if (holder == null) {
                       toRemove.add(ref);
                            } else {
                       UrlImage image = images.get(holder.avatarUrl);
                       if (image != null && image.isValid()) {
                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<UserDropDownViewHolder> r : toRemove) {
                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void setUserImage(UserDropDownViewHolder holder, Bitmap bmp) {
           holder.image.setColorFilter((ColorFilter) null);
           if (bmp != null) {
               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }
            }
