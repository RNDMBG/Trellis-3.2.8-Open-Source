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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class MenuUsersAdapter extends CursorAdapter {
                protected final AppController mAppController;
                private final HashMap<ImageKey, WeakReference<Bitmap>> mAvatars;
                private MessageBoxAdapter mInboxAdapter;
                private long mLatestRefreshTime;
                private final int mProfileImageSize;
                protected final ArrayList<WeakReference<FriendsViewHolder>> mViewHolders;
                private WeakReference<FriendsViewHolder> mZeroHolder;

/* 40 */        public MenuUsersAdapter(Context context, AppController appController, int flags) {
/* 41 */            super(context, (Cursor) null, flags);
/* 33 */            this.mAvatars = new HashMap<>();
/* 42 */            this.mAppController = appController;
/* 43 */            this.mViewHolders = new ArrayList<>();
/* 44 */            this.mProfileImageSize = context.getResources().getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 48 */        public View getView(int position, View convertView, ViewGroup parent) {
                    return (convertView == null || (convertView.getTag() instanceof FriendsViewHolder)) ? super.getView(position, convertView, parent) : super.getView(position, null, parent);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 57 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 58 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 59 */            View v = layoutInflater.inflate(C0257R.layout.menu_friends_row, parent, false);
/* 60 */            FriendsViewHolder holder = new FriendsViewHolder(context, v);
/* 61 */            this.mViewHolders.add(new WeakReference<>(holder));
/* 62 */            v.setTag(holder);
/* 63 */            return v;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 67 */        public void bindView(View view, Context context, Cursor cursor) {
/* 68 */            FriendsViewHolder holder = (FriendsViewHolder) view.getTag();
/* 69 */            holder.recipient = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
/* 70 */            holder.friendName.setText(holder.recipient.display);
/* 71 */            if (!TextUtils.isEmpty(holder.recipient.avatarUrl)) {
/* 72 */                ImageKey key = new ImageKey(holder.recipient.avatarUrl, this.mProfileImageSize, this.mProfileImageSize, true);
/* 74 */                if (Util.isDefaultAvatarUrl(holder.recipient.avatarUrl)) {
/* 75 */                    Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.recipient.color);
                        } else {
/* 77 */                    holder.avatarKey = key;
/* 78 */                    WeakReference<Bitmap> bmKey = this.mAvatars.get(key);
/* 79 */                    Bitmap bm = null;
/* 80 */                    if (bmKey != null) {
/* 81 */                        Bitmap bm2 = bmKey.get();
                                bm = bm2;
                            }
/* 83 */                    if (bm == null && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
/* 86 */                        this.mAvatars.put(key, new WeakReference<>(bm));
                            }
/* 89 */                    setUserImage(holder, bm);
                        }
                    } else {
/* 92 */                setUserImage(holder, null);
                    }
/* 95 */            int position = cursor.getPosition();
/* 97 */            holder.position = position;
/* 99 */            if (position == 0) {
/* 100 */               this.mZeroHolder = new WeakReference<>(holder);
/* 101 */               this.mLatestRefreshTime = holder.recipient.lastFriendRefresh;
/* 102 */               holder.sectionIndicator.setVisibility(0);
/* 103 */               holder.sectionTitle.setVisibility(0);
/* 104 */               holder.sectionTitle.setText(holder.recipient.sectionTitle);
/* 105 */               holder.sectionSort.setVisibility(0);
/* 106 */               holder.sectionSort.setText(holder.recipient.getTextSortKey());
                    } else {
/* 108 */               cursor.moveToPrevious();
/* 109 */               VineRecipient lastItem = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
/* 110 */               cursor.moveToNext();
/* 111 */               int lastSectionIndex = lastItem.sectionIndex;
/* 112 */               boolean sectionIndicatorVisible = false;
/* 113 */               if (holder.recipient.sectionIndex != lastSectionIndex) {
/* 114 */                   sectionIndicatorVisible = true;
/* 115 */                   holder.sectionTitle.setText(holder.recipient.sectionTitle);
/* 116 */                   holder.sectionTitle.setVisibility(0);
                        } else {
/* 118 */                   holder.sectionTitle.setVisibility(8);
                        }
/* 120 */               if (holder.recipient.sectionIndex != 0 && !holder.recipient.getTextSortKey().equals(lastItem.getTextSortKey())) {
/* 122 */                   sectionIndicatorVisible = true;
/* 123 */                   holder.sectionSort.setVisibility(0);
/* 124 */                   holder.sectionSort.setText(holder.recipient.getTextSortKey());
                        }
/* 126 */               holder.sectionIndicator.setVisibility(sectionIndicatorVisible ? 0 : 8);
                    }
/* 129 */           invalidateDividerVisibility(holder, cursor);
                }

/* 132 */       private void invalidateDividerVisibility(FriendsViewHolder holder, Cursor cursor) {
/* 133 */           if (cursor.moveToNext()) {
/* 134 */               VineRecipient lastItem = holder.recipient;
/* 135 */               VineRecipient nextItem = VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
/* 136 */               int lastSectionIndex = lastItem.sectionIndex;
/* 137 */               boolean shouldHide = (nextItem.sectionIndex == lastSectionIndex && (nextItem.sectionIndex == 0 || nextItem.getTextSortKey().equals(lastItem.getTextSortKey()))) ? false : true;
/* 139 */               holder.divider.setVisibility(shouldHide ? 8 : 0);
/* 140 */               cursor.moveToPrevious();
/* 144 */               return;
                    }
/* 142 */           holder.divider.setVisibility(8);
                }

/* 146 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<FriendsViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<FriendsViewHolder> ref : this.mViewHolders) {
/* 149 */                   FriendsViewHolder holder = ref.get();
/* 150 */                   if (holder == null) {
/* 151 */                       toRemove.add(ref);
                            } else {
/* 153 */                       UrlImage image = images.get(holder.avatarKey);
/* 154 */                       if (image != null && image.isValid()) {
/* 155 */                           this.mAvatars.put(holder.avatarKey, new WeakReference<>(image.bitmap));
/* 156 */                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<FriendsViewHolder> r : toRemove) {
/* 161 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 166 */       public long getItemId(int position) {
/* 167 */           Cursor c = (Cursor) super.getItem(position);
/* 168 */           if (c != null) {
/* 169 */               return c.getLong(1);
                    }
                    return 0L;
                }

/* 175 */       private void setUserImage(FriendsViewHolder holder, Bitmap bmp) {
/* 176 */           holder.image.setColorFilter((ColorFilter) null);
/* 177 */           if (bmp != null) {
/* 178 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
/* 180 */               holder.image.setImageResource(C0257R.drawable.circle_shape_light);
                    }
                }

/* 184 */       public void setInboxAdapter(MessageBoxAdapter inboxAdapter) {
/* 185 */           this.mInboxAdapter = inboxAdapter;
                }

/* 197 */       public static class FriendsViewHolder {
                    public ImageKey avatarKey;
                    public View divider;
                    public TextView friendName;
                    public ImageView image;
                    public int position;
                    public VineRecipient recipient;
                    public View sectionIndicator;
                    public TextView sectionSort;
                    public TextView sectionTitle;

/* 198 */           public FriendsViewHolder(Context context, View view) {
/* 200 */               this.friendName = (TextView) view.findViewById(C0257R.id.friend_name);
/* 201 */               this.image = (ImageView) view.findViewById(C0257R.id.user_image);
/* 202 */               this.sectionIndicator = view.findViewById(C0257R.id.section_indicator);
/* 203 */               this.sectionTitle = (TextView) view.findViewById(C0257R.id.section_title);
/* 204 */               this.sectionSort = (TextView) view.findViewById(C0257R.id.section_sort);
/* 205 */               this.divider = view.findViewById(C0257R.id.divider);
/* 206 */               Util.styleSectionHeader(context, this.sectionTitle, this.sectionSort);
                    }
                }
            }
