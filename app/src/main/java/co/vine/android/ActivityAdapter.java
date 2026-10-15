            package co.vine.android;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.ColorFilter;
            import android.graphics.drawable.GradientDrawable;
            import android.net.Uri;
            import android.support.annotation.Nullable;
            import android.support.v4.view.ViewCompat;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.ListView;
            import android.widget.SectionIndexer;
            import android.widget.TextView;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineEverydayNotification;
            import co.vine.android.api.VineNotification;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widget.ActivityViewHolder;
            import co.vine.android.widget.EntityLinkClickableSpan;
            import co.vine.android.widget.PinnedHeaderAdapter;
            import co.vine.android.widget.PinnedHeaderListView;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            
            
    public class ActivityAdapter extends BaseAdapter implements SectionIndexer, PinnedHeaderAdapter {
                private static final int BLUR_MULTIPLE = 2;
                public static final int NUM_VIEW_TYPES = 4;
                private static final int SPAN_FLAGS = 33;
                public static final int VIEW_TYPE_ACTIVITY = 0;
                public static final int VIEW_TYPE_FOLLOW_REQUEST = 2;
                public static final int VIEW_TYPE_MILESTONE = 1;
                public static final int VIEW_TYPE_USER_LIST_COUNT = 3;
                private AppController mAppController;
                private final Context mContext;
                private final String mFollowRequestsString;
                private final ListView mListView;
                private final String mNewActivityString;
                private final String mOlderActivityString;
                private final int mPinnedHeaderHeight;
                private final int mProfileImageSize;
                private final int mSpanColor;
                private View.OnClickListener mViewClickListener;
                private final int mVineGreen;
                private Friendships mFriendships = new Friendships();
                private final SharedPreferences mFollowState;
                private ArrayList<VineEverydayNotification> mNotifications = new ArrayList<>();
                private ArrayList<VineSingleNotification> mFollowRequests = new ArrayList<>();
                private int mLastNewIndex = -1;
                private ArrayList<String> mSections = new ArrayList<>();
                private int mPinnedHeaderSection = -1;
                private boolean mShouldReloadHeaderBecauseDataChanged = false;
                private View.OnClickListener mPostClicker = new ViewOnClickListenerC01461();
                private View.OnClickListener mAvatarClicker = new ViewOnClickListenerC01472();
                private View.OnClickListener mCountContentLineClickListener = new ViewOnClickListenerC01483();
                private ArrayList<WeakReference<ActivityViewHolder>> mViewHolders = new ArrayList<>();

        static  Context access$000(ActivityAdapter x0) {
            return x0.mContext;
                }

        static  AppController access$100(ActivityAdapter x0) {
            return x0.mAppController;
                }

        public ActivityAdapter(Context context, ListView listView, AppController appController, View.OnClickListener viewClickListener) {
            this.mListView = listView;
            this.mAppController = appController;
            this.mViewClickListener = viewClickListener;
            this.mContext = context;
            Resources res = context.getResources();
            this.mFollowRequestsString = res.getString(R.string.follow_header);
            this.mNewActivityString = res.getString(R.string.new_activity);
            this.mOlderActivityString = res.getString(R.string.older_activity);
            this.mVineGreen = res.getColor(R.color.vine_green);
            this.mSpanColor = res.getColor(R.color.soft_black);
           this.mPinnedHeaderHeight = res.getDimensionPixelSize(R.dimen.activity_header_height);
           int profileImageSize = context.getResources().getDimensionPixelSize(R.dimen.user_image_size);
                    if (profileImageSize <= 0) {
                        profileImageSize = Math.max(1, Math.round(48.0f * res.getDisplayMetrics().density));
                    }
                    this.mProfileImageSize = profileImageSize;
                    this.mFollowState = context.getSharedPreferences("vine_notification_follow_state", Context.MODE_PRIVATE);
                }

                
       class ViewOnClickListenerC01461 implements View.OnClickListener {
           ViewOnClickListenerC01461() {
                    }

                    @Override 
           public void onClick(View v) {
               long postId = ((Long) v.getTag()).longValue();
               SingleActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), postId);
                    }
                }

                
       class ViewOnClickListenerC01472 implements View.OnClickListener {
           ViewOnClickListenerC01472() {
                    }

                    @Override 
           public void onClick(View v) {
               long userId = ((Long) v.getTag()).longValue();
               ProfileActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), userId, (String) null);
                    }
                }

                
       class ViewOnClickListenerC01483 implements View.OnClickListener {
           ViewOnClickListenerC01483() {
                    }

                    @Override 
           public void onClick(View v) {
               VineEverydayNotification notif = (VineEverydayNotification) v.getTag();
               if (notif.entities != null && !notif.entities.isEmpty()) {
                   VineEntity entity = notif.entities.get(0);
                   if (entity.isCommentListType()) {
                       CommentsActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), entity.getPostId(), 0L, ActivityAdapter.access$100(ActivityAdapter.this).getActiveId(), true);
                            } else if (entity.isUserListType()) {
                       UsersActivity.startUserListForNotification(ActivityAdapter.access$000(ActivityAdapter.this), notif.notificationId, notif.anchor);
                            }
                        }
                    }
                }

       public View newView(int position, ViewGroup root) {
                    View view;
           int itemViewType = getItemViewType(position);
                    switch (itemViewType) {
                        case 1:
                   view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_row_view_milestone, root, false);
                            break;
                        case 2:
                        default:
                   view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_row_view, root, false);
                            break;
                        case 3:
                   view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_row_view_count, root, false);
                            break;
                    }
           ActivityViewHolder holder = new ActivityViewHolder(view);
           if (itemViewType != 1) {
               holder.contentLine.setMovementMethod(LinkMovementMethod.getInstance());
                    }
           view.setTag(holder);
           this.mViewHolders.add(new WeakReference<>(holder));
           return view;
                }

       public void bindView(View view, int position) {
           ActivityViewHolder h = (ActivityViewHolder) view.getTag();
           if (h == null) {
               throw new RuntimeException("View tag is null.");
                    }
           h.divider.setVisibility(nextViewHasHeader(position) ? 8 : 0);
           h.userId = 0L;
           h.postId = 0L;
           h.listItemClickType = -1;
                    h.avatarImageKey = null;
                    h.thumbnailImageKey = null;
                    if (h.avatar != null) {
                        h.avatar.setOnClickListener(null);
                        h.avatar.setTag(null);
                        setUserImage(h.avatar, null);
                    }
                    if (h.followButton != null) {
                        h.followButton.setVisibility(8);
                        h.followButton.setOnClickListener(null);
                        h.followButton.setTag(null);
                    }
           bindHeader(h, position);
                    switch (getItemViewType(position)) {
                        case 0:
                   bindGroupNotification(h, (VineEverydayNotification) getItem(position));
                   return;
                        case 1:
                   bindMilestone(h, (VineEverydayNotification) getItem(position));
                   return;
                        case 2:
                   bindFollowRequest(h, (VineSingleNotification) getItem(position));
                   return;
                        case 3:
                   VineEverydayNotification notif = (VineEverydayNotification) getItem(position);
                   h.notificationId = notif.notificationId;
                   VineEntity firstEntity = notif.entities.get(0);
                   if (firstEntity != null && firstEntity.isCommentListType()) {
                       h.listItemClickType = 3;
                       h.postId = firstEntity.getPostId();
                            } else {
                       h.listItemClickType = 2;
                            }
                   h.anchor = notif.anchor;
                   h.contentLine.setText(notif.comment);
                   h.contentLine.setOnClickListener(this.mCountContentLineClickListener);
                   h.contentLine.setTag(notif);
                   return;
                        default:
                   return;
                    }
                }

       private void bindHeader(ActivityViewHolder h, int position) {
           if (h.headerView != null) {
               Object[] sections = getSections();
               if (sections.length > 0) {
                   int section = getSectionForPosition(position);
                   int sectionPosition = getPositionForSection(section);
                   if (position == sectionPosition) {
                       h.headerView.setVisibility(0);
                       h.headerText.setText((CharSequence) sections[section]);
                       return;
                            } else {
                       h.headerView.setVisibility(8);
                       return;
                            }
                        }
               h.headerView.setVisibility(8);
                    }
                }

       private boolean nextViewHasHeader(int position) {
                    return (getSections().length == 0 || getSectionForPosition(position) == getSectionForPosition(position + 1)) ? false : true;
                }

       private void bindFollowRequest(ActivityViewHolder h, VineSingleNotification notif) {
           if (h == null) {
               throw new RuntimeException("View tag is null.");
                    }
           h.userId = notif.userId;
           h.listItemClickType = 0;
           setAvatar(h, h.avatar, notif.avatarUrl, notif.userId);
           h.avatar.setTag(Long.valueOf(notif.userId));
           h.avatar.setOnClickListener(this.mAvatarClicker);
           h.typeIcon.setVisibility(0);
           h.typeIcon.setImageResource(R.drawable.ic_activity_follow);
           h.thumbnail.setVisibility(8);
           bindBodyText(h, notif);
                }

       private void bindMilestone(ActivityViewHolder h, VineEverydayNotification notif) {
           if (notif.milestone != null) {
               boolean isUserType = false;
               if (!TextUtils.isEmpty(notif.milestone.milestoneUrl)) {
                   Uri milestoneUri = Uri.parse(notif.milestone.milestoneUrl);
                   String host = milestoneUri.getHost();
                   Long id = Long.valueOf(Long.parseLong(milestoneUri.getLastPathSegment()));
                   if ("user-id".equals(host)) {
                       h.userId = id.longValue();
                       h.listItemClickType = 0;
                       isUserType = true;
                            } else if ("post".equals(host)) {
                       h.listItemClickType = 1;
                       h.postId = id.longValue();
                       isUserType = false;
                            }
                        }
               ImageKey key = new ImageKey(notif.milestone.backgroundImageUrl, true, notif.milestone.blurRadius * 2, true);
               h.milestoneBackgroundImageKey = key;
               setImage(h.milestoneBackground, this.mAppController.getPhotoBitmap(key), false);
               ImageKey key2 = new ImageKey(notif.milestone.backgroundImageUrl, this.mProfileImageSize, this.mProfileImageSize, isUserType);
               h.milestoneImageImageKey = key2;
               setImage(h.milestoneImage, this.mAppController.getPhotoBitmap(key2), false);
               h.milestoneImageFrame.setImageResource(isUserType ? R.drawable.milestone_avatar_frame : R.drawable.milestone_thumb_frame);
               ImageKey key3 = new ImageKey(notif.milestone.iconUrl);
               h.milestoneIconImageKey = key3;
               setImage(h.milestoneIcon, this.mAppController.getPhotoBitmap(key3), false);
               h.milestoneTitle.setText(notif.milestone.title);
               String date = Util.getRelativeTimeString(this.mContext, notif.createdAt, true);
               h.milestoneDescription.setText(date);
               if (notif.post != null) {
                   h.postId = notif.post.postId;
                        }
               GradientDrawable overlay = (GradientDrawable) h.milestoneOverlay.getBackground();
               overlay.setColor((-16777216) | notif.milestone.overlayColor);
               h.milestoneOverlay.setAlpha(notif.milestone.overlayAlpha + 0.1f);
                    }
                }

       private void bindGroupNotification(ActivityViewHolder h, VineEverydayNotification notif) {
           h.contentLine.setText(notif.comment);
           h.notificationId = notif.notificationId;
           VineUser user = notif.user;
           if (user != null) {
               h.userId = user.userId;
               h.listItemClickType = 0;
               setAvatar(h, h.avatar, user.avatarUrl, user.userId);
               h.avatar.setTag(Long.valueOf(user.userId));
               h.avatar.setOnClickListener(this.mAvatarClicker);
                    }
           if ((VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) && notif.entities != null) {
               h.userId = notif.entities.get(0).f96id;
               h.listItemClickType = 0;
                    }
           if (notif.post != null) {
               h.postId = notif.post.postId;
               h.listItemClickType = 1;
               h.thumbnail.setVisibility(0);
               h.thumbnail.setTag(Long.valueOf(h.postId));
               h.thumbnail.setOnClickListener(this.mPostClicker);
               if (notif.post.thumbnailUrl != null) {
                   ImageKey key = new ImageKey(notif.post.thumbnailUrl, false);
                   h.thumbnailImageKey = key;
                   setImage(h.thumbnail, this.mAppController.getPhotoBitmap(key));
                        } else {
                   setImage(h.thumbnail, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.ic_launcher));
                        }
                    } else if (VineEverydayNotification.TYPE_FOLLOW_APPROVED.equals(notif.type)) {
               h.thumbnail.setVisibility(8);
                    } else {
               h.thumbnail.setVisibility(4);
                    }
           if (VineEverydayNotification.TYPE_FOLLOWED.equals(notif.type) || VineEverydayNotification.TYPE_FOLLOW_APPROVED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) {
               h.typeIcon.setVisibility(0);
               h.typeIcon.setImageResource(R.drawable.ic_activity_follow);
                    } else if (VineEverydayNotification.TYPE_COMMENT_MENTION.equals(notif.type) || VineEverydayNotification.TYPE_COMMENTED.equals(notif.type) || VineEverydayNotification.TYPE_GROUP_COMMENTED.equals(notif.type) || VineEverydayNotification.TYPE_POST_MENTION.equals(notif.type) || VineEverydayNotification.TYPE_MENTION.equals(notif.type)) {
               h.typeIcon.setVisibility(0);
               h.typeIcon.setImageResource(R.drawable.ic_activity_comment);
                    } else if (VineEverydayNotification.TYPE_LIKED.equals(notif.type)) {
               h.typeIcon.setVisibility(0);
               h.typeIcon.setImageResource(R.drawable.ic_activity_like);
                    } else if (VineEverydayNotification.TYPE_REVINED.equals(notif.type)) {
               h.typeIcon.setVisibility(0);
               h.typeIcon.setImageResource(R.drawable.ic_activity_revine);
                    } else if (VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type)) {
               h.typeIcon.setVisibility(0);
               h.typeIcon.setImageResource(R.drawable.ic_activity_twitter);
                    } else {
               h.typeIcon.setVisibility(8);
               h.clickable = false;
                    }
           if (VineEverydayNotification.TYPE_FOLLOWED.equals(notif.type) || VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) {
               h.followButton.setVisibility(0);
               h.followButton.setOnClickListener(this.mViewClickListener);
               long followUserId = h.userId;
                    if (user != null) {
                   if (!this.mFriendships.contains(user.userId) && user.isFollowing()) {
                       this.mFriendships.addFollowing(user.userId);
                            }
                        followUserId = user.userId;
                    }
                    if (followUserId != 0) {
                        if (this.mFollowState.getBoolean("following_" + followUserId, false)) {
                            this.mFriendships.addFollowing(followUserId);
                        }
                        if (!isFollowing(followUserId) && (user == null || !user.isFollowing())) {
                            
                            
                            
                            try {
                                VineDatabaseHelper db = VineDatabaseHelper.getDatabaseHelper(this.mContext);
                                if (db != null && db.amFollowingUser(db.getUserRowIdForUserRemoteId(followUserId))) {
                                    this.mFriendships.addFollowing(followUserId);
                                }
                            } catch (Throwable t) {
                                CrashUtil.logException(t);
                            }
                        }
                   h.followButton.setTag(Long.valueOf(followUserId));
                   if ((user != null && user.isFollowing()) || isFollowing(followUserId)) {
                               if (user != null && user.isFollowing()) {
                                   this.mFriendships.addFollowing(followUserId);
                               }
                       h.followButton.setBackgroundResource(R.drawable.btn_following_default);
                            } else {
                       h.followButton.setBackgroundResource(R.drawable.btn_follow_default);
                            }
                    }
                    } else {
               h.followButton.setVisibility(8);
                    }
           bindBodyText(h, notif);
                }

       public boolean isFollowing(long userId) {
           return this.mFriendships.isFollowing(userId);
                }

       private void bindBodyText(ActivityViewHolder h, VineNotification notif) {
           String contentLine = notif.comment;
           SpannableStringBuilder contentSb = new SpannableStringBuilder(contentLine);
           ArrayList<VineEntity> entities = null;
           if (notif.entities != null) {
               entities = new ArrayList<>(notif.entities.size());
                        for (int i = 0; i < notif.entities.size(); i++) {
                   entities.add(new VineEntity(notif.entities.get(i)));
                        }
                    }
           if (entities != null && !TextUtils.isEmpty(contentLine)) {
               Util.adjustEntities(entities, contentSb, 0, true);
               TextView contentTv = h.contentLine;
                        for (VineEntity entity : entities) {
                   EntityLinkClickableSpan span = new EntityLinkClickableSpan(entity.link);
                   if (span != null) {
                                try {
                           span.setColor(this.mSpanColor);
                           Util.safeSetSpan(contentSb, span, entity.start, entity.end, 33);
                           Util.safeSetSpan(contentSb, new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 4)), entity.start, entity.end, 33);
                                } catch (IndexOutOfBoundsException e) {
                                }
                            }
                        }
               if (h.dateLine != null) {
                   String date = Util.getRelativeTimeString(this.mContext, notif.createdAt, true);
                   h.dateLine.setText(date);
                        }
               contentTv.setText(contentSb);
                    }
                }

       private void setAvatar(ActivityViewHolder h, ImageView avatarImageView, String avatarUrl, long userId) {
                    if (avatarUrl != null && avatarUrl.startsWith("http://")) {
                        avatarUrl = "https://" + avatarUrl.substring(7);
                    }
           if (Util.isDefaultAvatarUrl(avatarUrl)) {
               Util.safeSetDefaultAvatar(avatarImageView, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mVineGreen);
                    } else {
               ImageKey key = new ImageKey(avatarUrl, this.mProfileImageSize, this.mProfileImageSize, true);
               h.avatarImageKey = key;
               setUserImage(avatarImageView, this.mAppController.getPhotoBitmap(key));
                    }
           avatarImageView.setTag(Long.valueOf(userId));
                }

                @Override 
       public int getCount() {
           return this.mFollowRequests.size() + this.mNotifications.size();
                }

                @Override 
       public Object getItem(int position) {
                    return position < this.mFollowRequests.size() ? this.mFollowRequests.get(position) : this.mNotifications.get(position - this.mFollowRequests.size());
                }

                @Override 
       public long getItemId(int position) {
           return ((VineNotification) getItem(position)).notificationId;
                }

                @Override 
                @Nullable
       public View getView(int position, View convertView, ViewGroup parent) {
                    View view;
           if (convertView == null) {
               view = newView(position, parent);
                    } else {
               view = convertView;
                    }
           bindView(view, position);
           return view;
                }

                @Override 
       public int getItemViewType(int position) {
           if (position < this.mFollowRequests.size()) {
               return 2;
                    }
           VineEverydayNotification notif = (VineEverydayNotification) getItem(position);
           if (notif.milestone != null) {
               return 1;
                    }
           if ("count".equals(notif.type)) {
               return 3;
                    }
           return 0;
                }

                @Override 
       public int getViewTypeCount() {
           return 4;
                }

       public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
                    UrlImage targetImage;
                    UrlImage targetImage2;
           ArrayList<WeakReference<ActivityViewHolder>> toRemove = new ArrayList<>();
                    for (WeakReference<ActivityViewHolder> ref : this.mViewHolders) {
               ActivityViewHolder holder = ref.get();
               if (holder == null) {
                   toRemove.add(ref);
                        } else {
                   if (holder.avatarImageKey != null) {
                       ImageKey key = holder.avatarImageKey;
                       UrlImage image3 = images.get(key);
                       if (image3 != null && image3.isValid()) {
                           setUserImage(holder.avatar, image3.bitmap);
                                }
                            }
                   if (holder.thumbnailImageKey != null && (targetImage2 = images.get(holder.thumbnailImageKey)) != null && targetImage2.isValid()) {
                       setImage(holder.thumbnail, targetImage2.bitmap);
                            }
                   if (holder.milestoneImageImageKey != null && (targetImage = images.get(holder.milestoneImageImageKey)) != null && targetImage.isValid()) {
                       setImage(holder.milestoneImage, targetImage.bitmap);
                            }
                   if (holder.milestoneBackgroundImageKey != null && (image2 = images.get(holder.milestoneBackgroundImageKey)) != null && image2.isValid()) {
                       setImage(holder.milestoneBackground, image2.bitmap);
                            }
                   if (holder.milestoneIconImageKey != null && (image = images.get(holder.milestoneIconImageKey)) != null && image.isValid()) {
                       setImage(holder.milestoneIcon, image.bitmap);
                            }
                        }
                    }
                    for (WeakReference<ActivityViewHolder> r : toRemove) {
               this.mViewHolders.remove(r);
                    }
                }

       private void setUserImage(ImageView v, Bitmap bmp) {
           if (v != null) {
               v.setColorFilter((ColorFilter) null);
               if (bmp == null) {
                   v.setImageResource(R.drawable.circle_shape_light);
                        } else {
                   v.setBackgroundColor(0);
                   v.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                        }
                    }
                }

       private void setImage(ImageView v, Bitmap bmp) {
           setImage(v, bmp, true);
                }

       private void setImage(ImageView v, Bitmap bmp, boolean showGray) {
           if (v != null) {
               if (bmp == null) {
                   if (showGray) {
                       v.setBackgroundColor(this.mContext.getResources().getColor(R.color.solid_light_gray));
                            } else {
                       v.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
                            }
                   v.setImageBitmap(null);
                   return;
                        }
               v.setBackgroundColor(0);
               v.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    }
                }

                @Override 
       public void notifyDataSetChanged() {
           super.notifyDataSetChanged();
           this.mShouldReloadHeaderBecauseDataChanged = true;
                }

       public void mergeData(ArrayList<VineEverydayNotification> notifications, ArrayList<VineSingleNotification> followRequests, boolean mergeAfter) {
           if (!mergeAfter) {
               this.mNotifications = new ArrayList<>();
               this.mFollowRequests = new ArrayList<>();
               this.mFriendships.clear();
               this.mLastNewIndex = -1;
                    }
           if (notifications != null) {
                        for (VineEverydayNotification notif : notifications) {
                   if (notif.isNew) {
                       this.mLastNewIndex++;
                            }
                   this.mNotifications.add(notif);
                        }
                    }
           if (followRequests != null) {
               Iterator<VineSingleNotification> it = followRequests.iterator();
                        while (it.hasNext()) {
                   this.mFollowRequests.add(it.next());
                        }
                    }
           ((PinnedHeaderListView) this.mListView).untrackScrollawayChild();
           if (notifications != null || followRequests != null) {
               notifyDataSetChanged();
                    }
                }

       public void follow(long userId) {
           this.mFriendships.addFollowing(userId);
                    this.mFollowState.edit().putBoolean("following_" + userId, true).apply();
           notifyDataSetChanged();
                }

       public void unfollow(long userId) {
           this.mFriendships.removeFollowing(userId);
                    this.mFollowState.edit().putBoolean("following_" + userId, false).apply();
           notifyDataSetChanged();
                }

                @Override 
       public Object[] getSections() {
           this.mSections.clear();
           if (this.mFollowRequests.size() > 0) {
               this.mSections.add(this.mFollowRequestsString);
                    }
           if (this.mLastNewIndex >= 0) {
               this.mSections.add(this.mNewActivityString);
                    }
           if (this.mLastNewIndex >= 0 || this.mFollowRequests.size() > 0) {
               this.mSections.add(this.mOlderActivityString);
                    }
           return this.mSections.toArray();
                }

                @Override 
       public int getPositionForSection(int sectionIndex) {
           if (getSections().length <= 0 || sectionIndex == 0) {
                        return 0;
                    }
           if (sectionIndex == 1) {
               if (this.mFollowRequests.size() > 0) {
                   return this.mFollowRequests.size();
                        }
               if (this.mLastNewIndex >= 0) {
                   return this.mLastNewIndex + 1;
                        }
                    }
           if (sectionIndex == 2) {
               return this.mFollowRequests.size() + this.mLastNewIndex + 1;
                    }
                    return 0;
                }

                @Override 
       public int getSectionForPosition(int position) {
           if (getSections().length <= 0 || position < this.mFollowRequests.size()) {
                        return 0;
                    }
           if (position <= this.mFollowRequests.size() + this.mLastNewIndex) {
               return this.mFollowRequests.size() <= 0 ? 0 : 1;
                    }
                    return (this.mFollowRequests.size() > 0 ? 1 : 0) + (this.mLastNewIndex < 0 ? 0 : 1);
                }

                @Override 
       public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
                    View nextView;
           int pinnedHeaderSection = this.mPinnedHeaderSection;
           int childIndex = ((PinnedHeaderListView) this.mListView).getPositionForPixelLocation(navBottom);
           int position = (this.mListView.getFirstVisiblePosition() - this.mListView.getHeaderViewsCount()) + childIndex;
           this.mPinnedHeaderSection = getSectionForPosition(position);
           int offset = 0;
           if (nextViewHasHeader(position) && (nextView = this.mListView.getChildAt(childIndex + 1)) != null && nextView.getTop() < this.mPinnedHeaderHeight + navBottom) {
               offset = (nextView.getTop() - this.mPinnedHeaderHeight) - navBottom;
                    }
           boolean shouldReloadHeader = pinnedHeaderSection != this.mPinnedHeaderSection || this.mShouldReloadHeaderBecauseDataChanged;
           this.mShouldReloadHeaderBecauseDataChanged = false;
           return new PinnedHeaderAdapter.PinnedHeaderStatus(offset, shouldReloadHeader, true);
                }

                @Override 
       public View getPinnedHeaderView(View convertView) {
           if (getSections().length == 0) {
                        return null;
                    }
           if (convertView == null) {
               convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_header, (ViewGroup) null);
               convertView.layout(0, 0, this.mListView.getWidth(), this.mPinnedHeaderHeight);
               ActivityViewHolder h = new ActivityViewHolder(convertView);
               convertView.setTag(h);
                    }
           ActivityViewHolder h2 = (ActivityViewHolder) convertView.getTag();
           bindHeader(h2, getPositionForSection(this.mPinnedHeaderSection));
                    return convertView;
                }

                @Override 
       public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
           header.layout(l, t, r, b);
                }

                @Override 
       public int getPinnedHeaderHeight() {
           return this.mPinnedHeaderHeight;
                }
            }
