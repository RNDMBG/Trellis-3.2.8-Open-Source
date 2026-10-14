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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class MessageBoxAdapter extends CursorAdapter {
                private AppController mAppController;
                private final HashMap<ImageKey, WeakReference<Bitmap>> mAvatars;
                private Context mContext;
                private final ColorDrawable mDefaultBg;
                private InboxFragment mFragment;
                private ArrayList<Long> mIdsToNotifyFail;
                private ArrayList<WeakReference<ConversationViewHolder>> mViewHolders;

/* 46 */        public MessageBoxAdapter(Context context, AppController appController, InboxFragment fragment, int flags) {
/* 47 */            super(context, (Cursor) null, flags);
/* 42 */            this.mAvatars = new HashMap<>();
/* 48 */            this.mAppController = appController;
/* 49 */            this.mContext = context;
/* 50 */            this.mViewHolders = new ArrayList<>();
/* 51 */            this.mFragment = fragment;
/* 53 */            this.mDefaultBg = new ColorDrawable(this.mContext.getResources().getColor(C0257R.color.conversation_menu_bg));
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 57 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 58 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 59 */            View v = layoutInflater.inflate(C0257R.layout.inbox_row_view, parent, false);
/* 60 */            ConversationViewHolder holder = new ConversationViewHolder(v);
/* 61 */            v.setTag(holder);
/* 62 */            this.mViewHolders.add(new WeakReference<>(holder));
/* 63 */            return v;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 67 */        public void bindView(View view, Context context, Cursor cursor) {
/* 68 */            ConversationViewHolder holder = (ConversationViewHolder) view.getTag();
/* 70 */            int count = cursor.getInt(14);
/* 71 */            String username = cursor.getString(8);
/* 72 */            if (count == 1) {
/* 73 */                holder.username.setText(username);
/* 74 */                holder.image.setVisibility(0);
                    } else {
/* 76 */                if (count == 2) {
/* 78 */                    holder.username.setText(username + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.mContext.getString(C0257R.string.and_x_others_single));
                        } else if (count > 2) {
/* 81 */                    holder.username.setText(username + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.format(this.mContext.getString(C0257R.string.and_x_others_plural), Integer.valueOf(count - 1)));
                        }
/* 83 */                holder.image.setVisibility(8);
                    }
/* 85 */            holder.loadMoreContent.setVisibility(0);
/* 86 */            holder.loadMoreProgress.findViewById(C0257R.id.progress).setVisibility(8);
/* 87 */            holder.loadMore.setVisibility(8);
/* 88 */            holder.retryProgress.setVisibility(8);
/* 90 */            long timestamp = cursor.getLong(2);
/* 91 */            String relativeTime = Util.getRelativeTimeString(context, timestamp, true);
/* 92 */            holder.timestamp.setText(relativeTime);
/* 94 */            int color = cursor.getInt(13);
/* 95 */            if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 96 */                color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 98 */            holder.color = color;
/* 99 */            int unreadCount = cursor.getInt(4);
/* 100 */           if (unreadCount > 0) {
/* 101 */               holder.unreadCount.setVisibility(0);
/* 102 */               holder.unreadCount.setText(String.valueOf(unreadCount));
/* 103 */               holder.unreadCount.getBackground().setColorFilter(new PorterDuffColorFilter((-16777216) | color, PorterDuff.Mode.SRC_IN));
                    } else {
/* 105 */               holder.unreadCount.setVisibility(8);
                    }
/* 108 */           String errorMessage = cursor.getString(15);
/* 109 */           if (count == 1 || TextUtils.isEmpty(errorMessage)) {
/* 110 */               holder.username.setTextColor((-16777216) | color);
/* 111 */               holder.failedUpload.setVisibility(8);
/* 112 */               holder.timestamp.setVisibility(0);
/* 113 */               holder.tapToRetry.setVisibility(8);
                    } else {
/* 116 */               holder.username.setTextColor(this.mContext.getResources().getColor(C0257R.color.conversation_menu_section_text));
/* 117 */               holder.unreadCount.setVisibility(8);
/* 118 */               holder.failedUpload.setVisibility(0);
/* 119 */               holder.timestamp.setVisibility(8);
/* 120 */               holder.tapToRetry.setText(C0257R.string.message_failed_tap_to_retry);
/* 121 */               holder.tapToRetry.setVisibility(0);
                    }
/* 124 */           String url = cursor.getString(12);
/* 125 */           if (!TextUtils.isEmpty(url)) {
/* 126 */               ImageKey key = new ImageKey(url, true);
/* 127 */               if (Util.isDefaultAvatarUrl(url)) {
/* 128 */                   setUserImage(holder, null, false);
                        } else {
/* 130 */                   holder.avatarUrl = key;
/* 131 */                   WeakReference<Bitmap> bmKey = this.mAvatars.get(key);
/* 132 */                   Bitmap bm = null;
/* 133 */                   if (bmKey != null) {
/* 134 */                       Bitmap bm2 = bmKey.get();
                                bm = bm2;
                            }
/* 136 */                   if (bm == null && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
/* 139 */                       this.mAvatars.put(key, new WeakReference<>(bm));
                            }
/* 142 */                   setUserImage(holder, bm, true);
                        }
                    } else {
/* 145 */               setUserImage(holder, null, false);
                    }
/* 148 */           if (cursor.isLast()) {
/* 149 */               holder.divider.setVisibility(8);
/* 150 */               if (cursor.getInt(7) != 1) {
/* 151 */                   holder.divider.setVisibility(0);
/* 152 */                   holder.loadMore.setVisibility(0);
/* 153 */                   if (this.mFragment.isLoadingMore()) {
/* 154 */                       holder.loadMoreContent.setVisibility(8);
/* 155 */                       holder.loadMoreProgress.setVisibility(0);
                            }
/* 157 */                   holder.loadMore.setOnClickListener(this.mFragment);
/* 163 */                   return;
                        }
/* 163 */               return;
                    }
/* 160 */           holder.divider.setVisibility(0);
/* 161 */           holder.loadMore.setVisibility(8);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 166 */       public long getItemId(int position) {
/* 167 */           Cursor cursor = getCursor();
                    return (cursor == null || cursor.isClosed() || !cursor.moveToPosition(position)) ? super.getItemId(position) : cursor.getLong(3);
                }

/* 175 */       public void setIdsToNotifyFailed(ArrayList<Long> idsToNotifyFailed) {
/* 176 */           this.mIdsToNotifyFail = idsToNotifyFailed;
                }

/* 192 */       private class ConversationViewHolder {
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

/* 193 */           public ConversationViewHolder(View v) {
/* 195 */               this.failedUpload = (ImageView) v.findViewById(C0257R.id.failed_upload);
/* 196 */               this.image = (ImageView) v.findViewById(C0257R.id.avatar);
/* 197 */               this.timestamp = (TextView) v.findViewById(C0257R.id.timestamp);
/* 198 */               this.username = (TextView) v.findViewById(C0257R.id.inbox_username);
/* 199 */               this.unreadCount = (TextView) v.findViewById(C0257R.id.unread_count);
/* 200 */               this.loadMore = (FrameLayout) v.findViewById(C0257R.id.load_more);
/* 201 */               this.divider = v.findViewById(C0257R.id.divider);
/* 202 */               this.tapToRetry = (TextView) v.findViewById(C0257R.id.tap_to_retry);
/* 203 */               this.retryProgress = v.findViewById(C0257R.id.retry_progress);
/* 204 */               this.loadMoreContent = v.findViewById(C0257R.id.load_more_content);
/* 205 */               this.loadMoreProgress = v.findViewById(C0257R.id.progress);
                    }
                }

/* 209 */       public synchronized void setUserImages(HashMap<ImageKey, UrlImage> images) {
                    try {
                        ArrayList<WeakReference<ConversationViewHolder>> toRemove = new ArrayList<>();
                        for (WeakReference<ConversationViewHolder> ref : this.mViewHolders) {
/* 212 */                   ConversationViewHolder holder = ref.get();
/* 213 */                   if (holder == null) {
/* 214 */                       toRemove.add(ref);
                            } else {
/* 216 */                       UrlImage image = images.get(holder.avatarUrl);
/* 217 */                       if (image != null && image.isValid()) {
/* 218 */                           this.mAvatars.put(holder.avatarUrl, new WeakReference<>(image.bitmap));
/* 219 */                           setUserImage(holder, image.bitmap, true);
                                }
                            }
                        }
                        for (WeakReference<ConversationViewHolder> r : toRemove) {
/* 224 */                   this.mViewHolders.remove(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 228 */       private void setUserImage(ConversationViewHolder holder, Bitmap bmp, boolean loading) {
/* 229 */           holder.image.setColorFilter((ColorFilter) null);
/* 230 */           if (bmp != null) {
/* 231 */               holder.image.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    } else if (loading) {
/* 233 */               holder.image.setImageBitmap(null);
/* 234 */               holder.image.setBackgroundColor(this.mContext.getResources().getColor(android.R.color.transparent));
                    } else {
/* 237 */               holder.image.setImageDrawable(null);
/* 238 */               Util.safeSetDefaultAvatar(holder.image, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.color);
                    }
                }
            }
