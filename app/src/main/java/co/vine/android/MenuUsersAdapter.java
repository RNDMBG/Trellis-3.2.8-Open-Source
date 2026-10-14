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
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.Util;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class MenuUsersAdapter extends CursorAdapter {
                protected final AppController mAppController;
                private final HashMap<ImageKey, WeakReference<Bitmap>> mAvatars;
                private MessageBoxAdapter mInboxAdapter;
                private long mLatestRefreshTime;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<FriendsViewHolder>> mViewHolders;
                private WeakReference<FriendsViewHolder> mZeroHolder;

        public MenuUsersAdapter(Context context, AppController appController, int flags) {
            super(context, (Cursor) null, flags);
            this.mAvatars = new HashMap<>();
            this.mAppController = appController;
            this.mViewHolders = new ArrayList<>();
            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override 
        public View getView(int position, View convertView, ViewGroup parent) {
                    return (convertView == null || (convertView.getTag() instanceof FriendsViewHolder)) ? super.getView(position, convertView, parent) : super.getView(position, null, parent);
                }

                @Override 
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(C0257R.layout.menu_friends_row, parent, false);
            FriendsViewHolder holder = new FriendsViewHolder(context, v);
            this.mViewHolders.add(new WeakReference<>(holder));
            v.setTag(holder);
            return v;
                }

                @Override 
        public void bindView(View view, Context context, Cursor cursor) {
            FriendsViewHolder holder = (FriendsViewHolder) view.getTag();
            holder.recipient = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
            holder.friendName.setText(holder.recipient.display);
            if (!TextUtils.isEmpty(holder.recipient.avatarUrl)) {
                ImageKey key = new ImageKey(holder.recipient.avatarUrl, this.mProfileImageSize, this.mProfileImageSize, true);
                if (Util.isDefaultAvatarUrl(holder.recipient.avatarUrl)) {
                    Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.recipient.color);
                        } else {
                    holder.avatarKey = key;
                    WeakReference<Bitmap> bmKey = this.mAvatars.get(key);
                    Bitmap bm = null;
                    if (bmKey != null) {
                        Bitmap bm2 = bmKey.get();
                                bm = bm2;
                            }
                    if (bm == null && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
                        this.mAvatars.put(key, new WeakReference<>(bm));
                            }
                    setUserImage(holder, bm);
                        }
                    } else {
                setUserImage(holder, null);
                    }
            int position = cursor.getPosition();
            holder.position = position;
            if (position == 0) {
               this.mZeroHolder = new WeakReference<>(holder);
               this.mLatestRefreshTime = holder.recipient.lastFriendRefresh;
               holder.sectionIndicator.setVisibility(0);
               holder.sectionTitle.setVisibility(0);
               holder.sectionTitle.setText(holder.recipient.sectionTitle);
               holder.sectionSort.setVisibility(0);
               holder.sectionSort.setText(holder.recipient.getTextSortKey());
                    } else {
               cursor.moveToPrevious();
               VineRecipient lastItem = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
               cursor.moveToNext();
               int lastSectionIndex = lastItem.sectionIndex;
               boolean sectionIndicatorVisible = false;
               if (holder.recipient.sectionIndex != lastSectionIndex) {
                   sectionIndicatorVisible = true;
                   holder.sectionTitle.setText(holder.recipient.sectionTitle);
                   holder.sectionTitle.setVisibility(0);
                        } else {
                   holder.sectionTitle.setVisibility(8);
                        }
               if (holder.recipient.sectionIndex != 0 && !holder.recipient.getTextSortKey().equals(lastItem.getTextSortKey())) {
                   sectionIndicatorVisible = true;
                   holder.sectionSort.setVisibility(0);
                   holder.sectionSort.setText(holder.recipient.getTextSortKey());
                        }
               holder.sectionIndicator.setVisibility(sectionIndicatorVisible ? 0 : 8);
                    }
           invalidateDividerVisibility(holder, cursor);
                }

       private void invalidateDividerVisibility(FriendsViewHolder holder, Cursor cursor) {
           if (cursor.moveToNext()) {
               VineRecipient lastItem = holder.recipient;
               VineRecipient nextItem = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
               int lastSectionIndex = lastItem.sectionIndex;
               boolean shouldHide = (nextItem.sectionIndex == lastSectionIndex && (nextItem.sectionIndex == 0 || nextItem.getTextSortKey().equals(lastItem.getTextSortKey()))) ? false : true;
               holder.divider.setVisibility(shouldHide ? 8 : 0);
               cursor.moveToPrevious();
               return;
                    }
           holder.divider.setVisibility(8);
                }

       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<FriendsViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<FriendsViewHolder> ref : this.mViewHolders) {
                   FriendsViewHolder holder = ref.get();
                   if (holder == null) {
                       toRemove.add(ref);
                            } else {
                       UrlImage image = images.get(holder.avatarKey);
                       if (image != null && image.isValid()) {
                           this.mAvatars.put(holder.avatarKey, new WeakReference<>(image.bitmap));
                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<FriendsViewHolder> r : toRemove) {
                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public long getItemId(int position) {
           Cursor c = (Cursor) super.getItem(position);
           if (c != null) {
               return c.getLong(1);
                    }
                    return 0L;
                }

       private void setUserImage(FriendsViewHolder holder, Bitmap bmp) {
           holder.image.setColorFilter((ColorFilter) null);
           if (bmp != null) {
               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }

       public void setInboxAdapter(MessageBoxAdapter inboxAdapter) {
           this.mInboxAdapter = inboxAdapter;
                }

       public static class FriendsViewHolder {
                    public ImageKey avatarKey;
                    public View divider;
                    public TextView friendName;
                    public ImageView image;
                    public int position;
                    public VineRecipient recipient;
                    public View sectionIndicator;
                    public TextView sectionSort;
                    public TextView sectionTitle;

           public FriendsViewHolder(Context context, View view) {
               this.friendName = (TextView) view.findViewById(C0257R.id.friend_name);
               this.image = (ImageView) view.findViewById(C0257R.id.user_image);
               this.sectionIndicator = view.findViewById(C0257R.id.section_indicator);
               this.sectionTitle = (TextView) view.findViewById(C0257R.id.section_title);
               this.sectionSort = (TextView) view.findViewById(C0257R.id.section_sort);
               this.divider = view.findViewById(C0257R.id.divider);
               Util.styleSectionHeader(context, this.sectionTitle, this.sectionSort);
                    }
                }
            }
