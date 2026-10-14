            package co.vine.android;

            import android.content.Context;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.graphics.drawable.Drawable;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.provider.FriendsPickerExclusiveCursor;
            import co.vine.android.util.SparseArray;
            import co.vine.android.util.Util;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class FriendsPickerAdapter extends CursorAdapter {
                private AppController mAppController;
                private final FriendsRecipientPickerFragment mFragment;
                private long mLatestRefreshTime;
                private final SparseArray<Drawable> mSelectedBackgroundMap;
                private ArrayList<WeakReference<FriendsPickerViewHolder>> mViewHolders;

/* 38 */        public FriendsPickerAdapter(Context context, AppController appController, int flags, FriendsRecipientPickerFragment fragment) {
/* 39 */            super(context, (Cursor) null, flags);
/* 35 */            this.mSelectedBackgroundMap = new SparseArray<>();
/* 40 */            this.mAppController = appController;
/* 41 */            this.mFragment = fragment;
/* 42 */            this.mViewHolders = new ArrayList<>();
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 46 */        public View getView(int position, View convertView, ViewGroup parent) {
/* 47 */            FriendsPickerExclusiveCursor cursor = (FriendsPickerExclusiveCursor) this.mCursor;
                    switch (cursor.getMode()) {
                        case LIST:
/* 50 */                    if (convertView == null) {
/* 51 */                        convertView = newView(this.mContext, cursor, parent);
                            }
/* 53 */                    bindView(position, convertView, this.mContext, cursor);
                            return convertView;
                        default:
/* 58 */                    return super.getView(position, convertView, parent);
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 64 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 65 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 66 */            View v = layoutInflater.inflate(C0257R.layout.recipient_picker_friends_row, parent, false);
/* 67 */            FriendsPickerViewHolder holder = new FriendsPickerViewHolder(context, this.mSelectedBackgroundMap, v);
/* 68 */            this.mViewHolders.add(new WeakReference<>(holder));
/* 69 */            v.setTag(holder);
/* 70 */            return v;
                }

/* 73 */        public void onRecipientItemRemoved(VineRecipient recipient) {
                    for (WeakReference<FriendsPickerViewHolder> h : this.mViewHolders) {
/* 75 */                FriendsPickerViewHolder holder = h.get();
/* 76 */                if (holder != null && recipient.equals(holder.recipient)) {
/* 78 */                    holder.setSelected(this.mContext.getResources(), false);
                        }
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 85 */        public void bindView(View view, Context context, Cursor cursor) {
/* 86 */            bindView(cursor.getPosition(), view, context, cursor);
                }

/* 89 */        public boolean haveSectionIndicator(VineRecipient lastItem, VineRecipient thisItem) {
                    return (thisItem.sectionIndex == lastItem.sectionIndex && (thisItem.sectionIndex == 0 || thisItem.getTextSortKey().equals(lastItem.getTextSortKey()))) ? false : true;
                }

/* 93 */        public void bindView(int position, View view, Context context, Cursor cursor) {
/* 94 */            FriendsPickerViewHolder holder = (FriendsPickerViewHolder) view.getTag();
/* 96 */            FriendsPickerExclusiveCursor theCursor = (FriendsPickerExclusiveCursor) cursor;
/* 98 */            holder.recipient = theCursor.getItem(position);
/* 100 */           if (position == 0) {
/* 101 */               this.mLatestRefreshTime = holder.recipient.lastFriendRefresh;
/* 102 */               holder.sectionIndicator.setVisibility(0);
/* 103 */               holder.sectionTitle.setVisibility(0);
/* 104 */               holder.sectionTitle.setText(holder.recipient.sectionTitle);
/* 105 */               if (holder.recipient.sectionIndex != 0) {
/* 106 */                   holder.sectionSort.setVisibility(0);
/* 107 */                   holder.sectionSort.setText(holder.recipient.getTextSortKey());
                        } else {
/* 109 */                   holder.sectionSort.setVisibility(8);
                        }
                    } else {
/* 112 */               VineRecipient lastItem = theCursor.getItem(position - 1);
/* 113 */               boolean haveSectionIndicator = haveSectionIndicator(lastItem, holder.recipient);
/* 114 */               holder.sectionIndicator.setVisibility(haveSectionIndicator ? 0 : 8);
/* 115 */               if (haveSectionIndicator) {
/* 116 */                   int lastSectionIndex = lastItem.sectionIndex;
/* 117 */                   if (holder.recipient.sectionIndex != lastSectionIndex) {
/* 118 */                       holder.sectionTitle.setText(holder.recipient.sectionTitle);
/* 119 */                       holder.sectionTitle.setVisibility(0);
                            } else {
/* 121 */                       holder.sectionTitle.setVisibility(8);
                            }
/* 123 */                   if (holder.recipient.sectionIndex != 0 && !holder.recipient.getTextSortKey().equals(lastItem.getTextSortKey())) {
/* 125 */                       holder.sectionSort.setVisibility(0);
/* 126 */                       holder.sectionSort.setText(holder.recipient.getTextSortKey());
                            }
                        }
                    }
/* 131 */           if (position + 1 < theCursor.getCount()) {
/* 132 */               if (haveSectionIndicator(holder.recipient, theCursor.getItem(position + 1))) {
/* 133 */                   holder.divider.setVisibility(8);
                        } else {
/* 135 */                   holder.divider.setVisibility(0);
                        }
                    }
/* 139 */           int color = holder.recipient.color;
/* 140 */           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 141 */               color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 144 */           holder.username.setText(holder.recipient.display);
/* 145 */           holder.username.setTextColor((-16777216) | color);
/* 146 */           holder.setSelected(context.getResources(), this.mFragment.isRecipientSelected(holder.recipient));
/* 148 */           String url = holder.recipient.avatarUrl;
/* 149 */           if (!TextUtils.isEmpty(url)) {
/* 150 */               ImageKey key = new ImageKey(url, true);
/* 151 */               if (Util.isDefaultAvatarUrl(url)) {
/* 152 */                   Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | color);
/* 160 */                   return;
                        } else {
/* 154 */                   holder.avatarUrl = key;
/* 155 */                   setUserImage(holder, this.mAppController.getPhotoBitmap(key));
/* 160 */                   return;
                        }
                    }
/* 158 */           setUserImage(holder, null);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 163 */       public long getItemId(int position) {
                    Cursor c;
/* 164 */           if (position >= getCount() || (c = (Cursor) super.getItem(position)) == null) {
                        return 0L;
                    }
/* 167 */           return ((FriendsPickerExclusiveCursor) c).getItem(position).userId;
                }

/* 173 */       private void setUserImage(FriendsPickerViewHolder holder, Bitmap bmp) {
/* 174 */           holder.image.setColorFilter((ColorFilter) null);
/* 175 */           if (bmp != null) {
/* 176 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
/* 178 */               holder.image.setImageBitmap(null);
                    }
                }

/* 182 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<FriendsPickerViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<FriendsPickerViewHolder> ref : this.mViewHolders) {
/* 186 */                   FriendsPickerViewHolder holder = ref.get();
/* 187 */                   if (holder == null) {
/* 188 */                       toRemove.add(ref);
                            } else {
/* 190 */                       UrlImage image = images.get(holder.avatarUrl);
/* 191 */                       if (image != null && image.isValid()) {
/* 192 */                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<FriendsPickerViewHolder> r : toRemove) {
/* 197 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
