            package co.vine.android;

            import android.content.Context;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.ColorDrawable;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.Util;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class MessageBoxAdapter extends CursorAdapter {
                private AppController mAppController;
                private final HashMap<ImageKey, WeakReference<Bitmap>> mAvatars;
                private Context mContext;
                private final ColorDrawable mDefaultBg;
                private InboxFragment mFragment;
                private ArrayList<Long> mIdsToNotifyFail;
                private ArrayList<WeakReference<ConversationViewHolder>> mViewHolders;

        public MessageBoxAdapter(Context context, AppController appController, InboxFragment fragment, int flags) {
            super(context, (Cursor) null, flags);
            this.mAvatars = new HashMap<>();
            this.mAppController = appController;
            this.mContext = context;
            this.mViewHolders = new ArrayList<>();
            this.mFragment = fragment;
            this.mDefaultBg = new ColorDrawable(this.mContext.getResources().getColor(C0257R.color.conversation_menu_bg));
                }

                @Override 
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(C0257R.layout.inbox_row_view, parent, false);
            ConversationViewHolder holder = new ConversationViewHolder(v);
            v.setTag(holder);
            this.mViewHolders.add(new WeakReference<>(holder));
            return v;
                }

                @Override 
        public void bindView(View view, Context context, Cursor cursor) {
            ConversationViewHolder holder = (ConversationViewHolder) view.getTag();
            int count = cursor.getInt(14);
            String username = cursor.getString(8);
            if (count == 1) {
                holder.username.setText(username);
                holder.image.setVisibility(0);
                    } else {
                if (count == 2) {
                    holder.username.setText(username + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.mContext.getString(C0257R.string.and_x_others_single));
                        } else if (count > 2) {
                    holder.username.setText(username + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.format(this.mContext.getString(C0257R.string.and_x_others_plural), Integer.valueOf(count - 1)));
                        }
                holder.image.setVisibility(8);
                    }
            holder.loadMoreContent.setVisibility(0);
            holder.loadMoreProgress.findViewById(C0257R.id.progress).setVisibility(8);
            holder.loadMore.setVisibility(8);
            holder.retryProgress.setVisibility(8);
            long timestamp = cursor.getLong(2);
            String relativeTime = Util.getRelativeTimeString(context, timestamp, true);
            holder.timestamp.setText(relativeTime);
            int color = cursor.getInt(13);
            if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
                color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
            holder.color = color;
            int unreadCount = cursor.getInt(4);
           if (unreadCount > 0) {
               holder.unreadCount.setVisibility(0);
               holder.unreadCount.setText(String.valueOf(unreadCount));
               holder.unreadCount.getBackground().setColorFilter(new PorterDuffColorFilter((-16777216) | color, PorterDuff.Mode.SRC_IN));
                    } else {
               holder.unreadCount.setVisibility(8);
                    }
           String errorMessage = cursor.getString(15);
           if (count == 1 || TextUtils.isEmpty(errorMessage)) {
               holder.username.setTextColor((-16777216) | color);
               holder.failedUpload.setVisibility(8);
               holder.timestamp.setVisibility(0);
               holder.tapToRetry.setVisibility(8);
                    } else {
               holder.username.setTextColor(this.mContext.getResources().getColor(C0257R.color.conversation_menu_section_text));
               holder.unreadCount.setVisibility(8);
               holder.failedUpload.setVisibility(0);
               holder.timestamp.setVisibility(8);
               holder.tapToRetry.setText(C0257R.string.message_failed_tap_to_retry);
               holder.tapToRetry.setVisibility(0);
                    }
           String url = cursor.getString(12);
           if (!TextUtils.isEmpty(url)) {
               ImageKey key = new ImageKey(url, true);
               if (Util.isDefaultAvatarUrl(url)) {
                   setUserImage(holder, null, false);
                        } else {
                   holder.avatarUrl = key;
                   WeakReference<Bitmap> bmKey = this.mAvatars.get(key);
                   Bitmap bm = null;
                   if (bmKey != null) {
                       Bitmap bm2 = bmKey.get();
                                bm = bm2;
                            }
                   if (bm == null && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
                       this.mAvatars.put(key, new WeakReference<>(bm));
                            }
                   setUserImage(holder, bm, true);
                        }
                    } else {
               setUserImage(holder, null, false);
                    }
           if (cursor.isLast()) {
               holder.divider.setVisibility(8);
               if (cursor.getInt(7) != 1) {
                   holder.divider.setVisibility(0);
                   holder.loadMore.setVisibility(0);
                   if (this.mFragment.isLoadingMore()) {
                       holder.loadMoreContent.setVisibility(8);
                       holder.loadMoreProgress.setVisibility(0);
                            }
                   holder.loadMore.setOnClickListener(this.mFragment);
                   return;
                        }
               return;
                    }
           holder.divider.setVisibility(0);
           holder.loadMore.setVisibility(8);
                }

                @Override 
       public long getItemId(int position) {
           Cursor cursor = getCursor();
                    return (cursor == null || cursor.isClosed() || !cursor.moveToPosition(position)) ? super.getItemId(position) : cursor.getLong(3);
                }

       public void setIdsToNotifyFailed(ArrayList<Long> idsToNotifyFailed) {
           this.mIdsToNotifyFail = idsToNotifyFailed;
                }

       private class ConversationViewHolder {
                    public ImageKey avatarUrl;
                    public int color;
                    public View divider;
                    public ImageView failedUpload;
                    public ImageView image;
                    public FrameLayout loadMore;
                    public View loadMoreContent;
                    public View loadMoreProgress;
                    public View retryProgress;
                    public TextView tapToRetry;
                    public TextView timestamp;
                    public TextView unreadCount;
                    public TextView username;

           public ConversationViewHolder(View v) {
               this.failedUpload = (ImageView) v.findViewById(C0257R.id.failed_upload);
               this.image = (ImageView) v.findViewById(C0257R.id.avatar);
               this.timestamp = (TextView) v.findViewById(C0257R.id.timestamp);
               this.username = (TextView) v.findViewById(C0257R.id.inbox_username);
               this.unreadCount = (TextView) v.findViewById(C0257R.id.unread_count);
               this.loadMore = (FrameLayout) v.findViewById(C0257R.id.load_more);
               this.divider = v.findViewById(C0257R.id.divider);
               this.tapToRetry = (TextView) v.findViewById(C0257R.id.tap_to_retry);
               this.retryProgress = v.findViewById(C0257R.id.retry_progress);
               this.loadMoreContent = v.findViewById(C0257R.id.load_more_content);
               this.loadMoreProgress = v.findViewById(C0257R.id.progress);
                    }
                }

       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<ConversationViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<ConversationViewHolder> ref : this.mViewHolders) {
                   ConversationViewHolder holder = ref.get();
                   if (holder == null) {
                       toRemove.add(ref);
                            } else {
                       UrlImage image = images.get(holder.avatarUrl);
                       if (image != null && image.isValid()) {
                           this.mAvatars.put(holder.avatarUrl, new WeakReference<>(image.bitmap));
                           setUserImage(holder, image.bitmap, true);
                                }
                            }
                        }
                        for (WeakReference<ConversationViewHolder> r : toRemove) {
                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void setUserImage(ConversationViewHolder holder, Bitmap bmp, boolean loading) {
           holder.image.setColorFilter((ColorFilter) null);
           if (bmp != null) {
               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else if (loading) {
               holder.image.setImageBitmap(null);
               holder.image.setBackgroundColor(this.mContext.getResources().getColor(android.R.color.transparent));
                    } else {
               holder.image.setImageDrawable(null);
               Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.color);
                    }
                }
            }
