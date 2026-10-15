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
            
            
    public class FriendsPickerAdapter extends CursorAdapter {
                private AppController mAppController;
                private final FriendsRecipientPickerFragment mFragment;
                private long mLatestRefreshTime;
                private final SparseArray<Drawable> mSelectedBackgroundMap;
                private ArrayList<WeakReference<FriendsPickerViewHolder>> mViewHolders;

        public FriendsPickerAdapter(Context context, AppController appController, int flags, FriendsRecipientPickerFragment fragment) {
            super(context, (Cursor) null, flags);
            this.mSelectedBackgroundMap = new SparseArray<>();
            this.mAppController = appController;
            this.mFragment = fragment;
            this.mViewHolders = new ArrayList<>();
                }

                @Override 
        public View getView(int position, View convertView, ViewGroup parent) {
            FriendsPickerExclusiveCursor cursor = (FriendsPickerExclusiveCursor) this.mCursor;
                    switch (cursor.getMode()) {
                        case LIST:
                    if (convertView == null) {
                        convertView = newView(this.mContext, cursor, parent);
                            }
                    bindView(position, convertView, this.mContext, cursor);
                            return convertView;
                        default:
                    return super.getView(position, convertView, parent);
                    }
                }

                @Override 
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(R.layout.recipient_picker_friends_row, parent, false);
            FriendsPickerViewHolder holder = new FriendsPickerViewHolder(context, this.mSelectedBackgroundMap, v);
            this.mViewHolders.add(new WeakReference<>(holder));
            v.setTag(holder);
            return v;
                }

        public void onRecipientItemRemoved(VineRecipient recipient) {
                    for (WeakReference<FriendsPickerViewHolder> h : this.mViewHolders) {
                FriendsPickerViewHolder holder = h.get();
                if (holder != null && recipient.equals(holder.recipient)) {
                    holder.setSelected(this.mContext.getResources(), false);
                        }
                    }
                }

                @Override 
        public void bindView(View view, Context context, Cursor cursor) {
            bindView(cursor.getPosition(), view, context, cursor);
                }

        public boolean haveSectionIndicator(VineRecipient lastItem, VineRecipient thisItem) {
                    return (thisItem.sectionIndex == lastItem.sectionIndex && (thisItem.sectionIndex == 0 || thisItem.getTextSortKey().equals(lastItem.getTextSortKey()))) ? false : true;
                }

        public void bindView(int position, View view, Context context, Cursor cursor) {
            FriendsPickerViewHolder holder = (FriendsPickerViewHolder) view.getTag();
            FriendsPickerExclusiveCursor theCursor = (FriendsPickerExclusiveCursor) cursor;
            holder.recipient = theCursor.getItem(position);
           if (position == 0) {
               this.mLatestRefreshTime = holder.recipient.lastFriendRefresh;
               holder.sectionIndicator.setVisibility(0);
               holder.sectionTitle.setVisibility(0);
               holder.sectionTitle.setText(holder.recipient.sectionTitle);
               if (holder.recipient.sectionIndex != 0) {
                   holder.sectionSort.setVisibility(0);
                   holder.sectionSort.setText(holder.recipient.getTextSortKey());
                        } else {
                   holder.sectionSort.setVisibility(8);
                        }
                    } else {
               VineRecipient lastItem = theCursor.getItem(position - 1);
               boolean haveSectionIndicator = haveSectionIndicator(lastItem, holder.recipient);
               holder.sectionIndicator.setVisibility(haveSectionIndicator ? 0 : 8);
               if (haveSectionIndicator) {
                   int lastSectionIndex = lastItem.sectionIndex;
                   if (holder.recipient.sectionIndex != lastSectionIndex) {
                       holder.sectionTitle.setText(holder.recipient.sectionTitle);
                       holder.sectionTitle.setVisibility(0);
                            } else {
                       holder.sectionTitle.setVisibility(8);
                            }
                   if (holder.recipient.sectionIndex != 0 && !holder.recipient.getTextSortKey().equals(lastItem.getTextSortKey())) {
                       holder.sectionSort.setVisibility(0);
                       holder.sectionSort.setText(holder.recipient.getTextSortKey());
                            }
                        }
                    }
           if (position + 1 < theCursor.getCount()) {
               if (haveSectionIndicator(holder.recipient, theCursor.getItem(position + 1))) {
                   holder.divider.setVisibility(8);
                        } else {
                   holder.divider.setVisibility(0);
                        }
                    }
           int color = holder.recipient.color;
           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
               color = 16777215 & this.mContext.getResources().getColor(R.color.vine_green);
                    }
           holder.username.setText(holder.recipient.display);
           holder.username.setTextColor((-16777216) | color);
           holder.setSelected(context.getResources(), this.mFragment.isRecipientSelected(holder.recipient));
           String url = holder.recipient.avatarUrl;
           if (!TextUtils.isEmpty(url)) {
               ImageKey key = new ImageKey(url, true);
               if (Util.isDefaultAvatarUrl(url)) {
                   Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | color);
                   return;
                        } else {
                   holder.avatarUrl = key;
                   setUserImage(holder, this.mAppController.getPhotoBitmap(key));
                   return;
                        }
                    }
           setUserImage(holder, null);
                }

                @Override 
       public long getItemId(int position) {
                    Cursor c;
           if (position >= getCount() || (c = (Cursor) super.getItem(position)) == null) {
                        return 0L;
                    }
           return ((FriendsPickerExclusiveCursor) c).getItem(position).userId;
                }

       private void setUserImage(FriendsPickerViewHolder holder, Bitmap bmp) {
           holder.image.setColorFilter((ColorFilter) null);
           if (bmp != null) {
               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else {
               holder.image.setImageBitmap(null);
                    }
                }

       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<FriendsPickerViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<FriendsPickerViewHolder> ref : this.mViewHolders) {
                   FriendsPickerViewHolder holder = ref.get();
                   if (holder == null) {
                       toRemove.add(ref);
                            } else {
                       UrlImage image = images.get(holder.avatarUrl);
                       if (image != null && image.isValid()) {
                           setUserImage(holder, image.bitmap);
                                }
                            }
                        }
                        for (WeakReference<FriendsPickerViewHolder> r : toRemove) {
                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
