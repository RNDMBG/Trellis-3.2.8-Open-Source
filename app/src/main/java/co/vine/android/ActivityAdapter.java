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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class ActivityAdapter extends BaseAdapter implements SectionIndexer, PinnedHeaderAdapter {
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

/* 49 */        static /* synthetic */ Context access$000(ActivityAdapter x0) {
/* 50 */            return x0.mContext;
                }

/* 49 */        static /* synthetic */ AppController access$100(ActivityAdapter x0) {
/* 50 */            return x0.mAppController;
                }

/* 84 */        public ActivityAdapter(Context context, ListView listView, AppController appController, View.OnClickListener viewClickListener) {
/* 86 */            this.mListView = listView;
/* 87 */            this.mAppController = appController;
/* 89 */            this.mViewClickListener = viewClickListener;
/* 90 */            this.mContext = context;
/* 92 */            Resources res = context.getResources();
/* 94 */            this.mFollowRequestsString = res.getString(C0257R.string.follow_header);
/* 95 */            this.mNewActivityString = res.getString(C0257R.string.new_activity);
/* 96 */            this.mOlderActivityString = res.getString(C0257R.string.older_activity);
/* 98 */            this.mVineGreen = res.getColor(C0257R.color.vine_green);
/* 99 */            this.mSpanColor = res.getColor(C0257R.color.soft_black);
/* 101 */           this.mPinnedHeaderHeight = res.getDimensionPixelSize(C0257R.dimen.activity_header_height);
/* 103 */           int profileImageSize = context.getResources().getDimensionPixelSize(C0257R.dimen.user_image_size);
                    if (profileImageSize <= 0) {
                        profileImageSize = Math.max(1, Math.round(48.0f * res.getDisplayMetrics().density));
                    }
                    this.mProfileImageSize = profileImageSize;
                    this.mFollowState = context.getSharedPreferences("vine_notification_follow_state", Context.MODE_PRIVATE);
                }

                /* JADX INFO: renamed from: co.vine.android.ActivityAdapter$1 */
/* 104 */       class ViewOnClickListenerC01461 implements View.OnClickListener {
/* 105 */           ViewOnClickListenerC01461() {
                    }

                    @Override // android.view.View.OnClickListener
/* 108 */           public void onClick(View v) {
/* 109 */               long postId = ((Long) v.getTag()).longValue();
/* 110 */               SingleActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), postId);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ActivityAdapter$2 */
/* 112 */       class ViewOnClickListenerC01472 implements View.OnClickListener {
/* 113 */           ViewOnClickListenerC01472() {
                    }

                    @Override // android.view.View.OnClickListener
/* 116 */           public void onClick(View v) {
/* 117 */               long userId = ((Long) v.getTag()).longValue();
/* 118 */               ProfileActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), userId, (String) null);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ActivityAdapter$3 */
/* 120 */       class ViewOnClickListenerC01483 implements View.OnClickListener {
/* 121 */           ViewOnClickListenerC01483() {
                    }

                    @Override // android.view.View.OnClickListener
/* 126 */           public void onClick(View v) {
/* 127 */               VineEverydayNotification notif = (VineEverydayNotification) v.getTag();
/* 128 */               if (notif.entities != null && !notif.entities.isEmpty()) {
/* 129 */                   VineEntity entity = notif.entities.get(0);
/* 130 */                   if (entity.isCommentListType()) {
/* 131 */                       CommentsActivity.start(ActivityAdapter.access$000(ActivityAdapter.this), entity.getPostId(), 0L, ActivityAdapter.access$100(ActivityAdapter.this).getActiveId(), true);
                            } else if (entity.isUserListType()) {
/* 133 */                       UsersActivity.startUserListForNotification(ActivityAdapter.access$000(ActivityAdapter.this), notif.notificationId, notif.anchor);
                            }
                        }
                    }
                }

/* 144 */       public View newView(int position, ViewGroup root) {
                    View view;
/* 145 */           int itemViewType = getItemViewType(position);
                    switch (itemViewType) {
                        case 1:
/* 149 */                   view = LayoutInflater.from(this.mContext).inflate(C0257R.layout.activity_row_view_milestone, root, false);
                            break;
                        case 2:
                        default:
/* 161 */                   view = LayoutInflater.from(this.mContext).inflate(C0257R.layout.activity_row_view, root, false);
                            break;
                        case 3:
/* 154 */                   view = LayoutInflater.from(this.mContext).inflate(C0257R.layout.activity_row_view_count, root, false);
                            break;
                    }
/* 167 */           ActivityViewHolder holder = new ActivityViewHolder(view);
/* 169 */           if (itemViewType != 1) {
/* 170 */               holder.contentLine.setMovementMethod(LinkMovementMethod.getInstance());
                    }
/* 173 */           view.setTag(holder);
/* 174 */           this.mViewHolders.add(new WeakReference<>(holder));
/* 176 */           return view;
                }

/* 180 */       public void bindView(View view, int position) {
/* 181 */           ActivityViewHolder h = (ActivityViewHolder) view.getTag();
/* 183 */           if (h == null) {
/* 184 */               throw new RuntimeException("View tag is null.");
                    }
/* 187 */           h.divider.setVisibility(nextViewHasHeader(position) ? 8 : 0);
/* 189 */           h.userId = 0L;
/* 190 */           h.postId = 0L;
/* 191 */           h.listItemClickType = -1;
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
/* 193 */           bindHeader(h, position);
                    switch (getItemViewType(position)) {
                        case 0:
/* 201 */                   bindGroupNotification(h, (VineEverydayNotification) getItem(position));
/* 224 */                   return;
                        case 1:
/* 197 */                   bindMilestone(h, (VineEverydayNotification) getItem(position));
/* 224 */                   return;
                        case 2:
/* 205 */                   bindFollowRequest(h, (VineSingleNotification) getItem(position));
/* 224 */                   return;
                        case 3:
/* 209 */                   VineEverydayNotification notif = (VineEverydayNotification) getItem(position);
/* 210 */                   h.notificationId = notif.notificationId;
/* 211 */                   VineEntity firstEntity = notif.entities.get(0);
/* 212 */                   if (firstEntity != null && firstEntity.isCommentListType()) {
/* 213 */                       h.listItemClickType = 3;
/* 214 */                       h.postId = firstEntity.getPostId();
                            } else {
/* 216 */                       h.listItemClickType = 2;
                            }
/* 218 */                   h.anchor = notif.anchor;
/* 219 */                   h.contentLine.setText(notif.comment);
/* 220 */                   h.contentLine.setOnClickListener(this.mCountContentLineClickListener);
/* 221 */                   h.contentLine.setTag(notif);
/* 224 */                   return;
                        default:
/* 224 */                   return;
                    }
                }

/* 226 */       private void bindHeader(ActivityViewHolder h, int position) {
/* 227 */           if (h.headerView != null) {
/* 229 */               Object[] sections = getSections();
/* 230 */               if (sections.length > 0) {
/* 231 */                   int section = getSectionForPosition(position);
/* 232 */                   int sectionPosition = getPositionForSection(section);
/* 233 */                   if (position == sectionPosition) {
/* 234 */                       h.headerView.setVisibility(0);
/* 235 */                       h.headerText.setText((CharSequence) sections[section]);
/* 243 */                       return;
                            } else {
/* 237 */                       h.headerView.setVisibility(8);
/* 243 */                       return;
                            }
                        }
/* 240 */               h.headerView.setVisibility(8);
                    }
                }

/* 245 */       private boolean nextViewHasHeader(int position) {
                    return (getSections().length == 0 || getSectionForPosition(position) == getSectionForPosition(position + 1)) ? false : true;
                }

/* 252 */       private void bindFollowRequest(ActivityViewHolder h, VineSingleNotification notif) {
/* 253 */           if (h == null) {
/* 254 */               throw new RuntimeException("View tag is null.");
                    }
/* 256 */           h.userId = notif.userId;
/* 257 */           h.listItemClickType = 0;
/* 258 */           setAvatar(h, h.avatar, notif.avatarUrl, notif.userId);
/* 259 */           h.avatar.setTag(Long.valueOf(notif.userId));
/* 260 */           h.avatar.setOnClickListener(this.mAvatarClicker);
/* 261 */           h.typeIcon.setVisibility(0);
/* 262 */           h.typeIcon.setImageResource(C0257R.drawable.ic_activity_follow);
/* 263 */           h.thumbnail.setVisibility(8);
/* 264 */           bindBodyText(h, notif);
                }

/* 267 */       private void bindMilestone(ActivityViewHolder h, VineEverydayNotification notif) {
/* 268 */           if (notif.milestone != null) {
/* 271 */               boolean isUserType = false;
/* 272 */               if (!TextUtils.isEmpty(notif.milestone.milestoneUrl)) {
/* 273 */                   Uri milestoneUri = Uri.parse(notif.milestone.milestoneUrl);
/* 274 */                   String host = milestoneUri.getHost();
/* 275 */                   Long id = Long.valueOf(Long.parseLong(milestoneUri.getLastPathSegment()));
/* 276 */                   if ("user-id".equals(host)) {
/* 277 */                       h.userId = id.longValue();
/* 278 */                       h.listItemClickType = 0;
/* 279 */                       isUserType = true;
                            } else if ("post".equals(host)) {
/* 281 */                       h.listItemClickType = 1;
/* 282 */                       h.postId = id.longValue();
/* 283 */                       isUserType = false;
                            }
                        }
/* 288 */               ImageKey key = new ImageKey(notif.milestone.backgroundImageUrl, true, notif.milestone.blurRadius * 2, true);
/* 289 */               h.milestoneBackgroundImageKey = key;
/* 290 */               setImage(h.milestoneBackground, this.mAppController.getPhotoBitmap(key), false);
/* 293 */               ImageKey key2 = new ImageKey(notif.milestone.backgroundImageUrl, this.mProfileImageSize, this.mProfileImageSize, isUserType);
/* 294 */               h.milestoneImageImageKey = key2;
/* 295 */               setImage(h.milestoneImage, this.mAppController.getPhotoBitmap(key2), false);
/* 297 */               h.milestoneImageFrame.setImageResource(isUserType ? C0257R.drawable.milestone_avatar_frame : C0257R.drawable.milestone_thumb_frame);
/* 301 */               ImageKey key3 = new ImageKey(notif.milestone.iconUrl);
/* 302 */               h.milestoneIconImageKey = key3;
/* 303 */               setImage(h.milestoneIcon, this.mAppController.getPhotoBitmap(key3), false);
/* 305 */               h.milestoneTitle.setText(notif.milestone.title);
/* 306 */               String date = Util.getRelativeTimeString(this.mContext, notif.createdAt, true);
/* 307 */               h.milestoneDescription.setText(date);
/* 309 */               if (notif.post != null) {
/* 310 */                   h.postId = notif.post.postId;
                        }
/* 313 */               GradientDrawable overlay = (GradientDrawable) h.milestoneOverlay.getBackground();
/* 314 */               overlay.setColor((-16777216) | notif.milestone.overlayColor);
/* 316 */               h.milestoneOverlay.setAlpha(notif.milestone.overlayAlpha + 0.1f);
                    }
                }

/* 321 */       private void bindGroupNotification(ActivityViewHolder h, VineEverydayNotification notif) {
/* 322 */           h.contentLine.setText(notif.comment);
/* 323 */           h.notificationId = notif.notificationId;
/* 324 */           VineUser user = notif.user;
/* 325 */           if (user != null) {
/* 326 */               h.userId = user.userId;
/* 327 */               h.listItemClickType = 0;
/* 328 */               setAvatar(h, h.avatar, user.avatarUrl, user.userId);
/* 329 */               h.avatar.setTag(Long.valueOf(user.userId));
/* 330 */               h.avatar.setOnClickListener(this.mAvatarClicker);
                    }
/* 333 */           if ((VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) && notif.entities != null) {
/* 336 */               h.userId = notif.entities.get(0).f96id;
/* 337 */               h.listItemClickType = 0;
                    }
/* 341 */           if (notif.post != null) {
/* 342 */               h.postId = notif.post.postId;
/* 343 */               h.listItemClickType = 1;
/* 344 */               h.thumbnail.setVisibility(0);
/* 345 */               h.thumbnail.setTag(Long.valueOf(h.postId));
/* 346 */               h.thumbnail.setOnClickListener(this.mPostClicker);
/* 348 */               if (notif.post.thumbnailUrl != null) {
/* 349 */                   ImageKey key = new ImageKey(notif.post.thumbnailUrl, false);
/* 350 */                   h.thumbnailImageKey = key;
/* 351 */                   setImage(h.thumbnail, this.mAppController.getPhotoBitmap(key));
                        } else {
/* 353 */                   setImage(h.thumbnail, BitmapFactory.decodeResource(this.mContext.getResources(), 2130837952));
                        }
                    } else if (VineEverydayNotification.TYPE_FOLLOW_APPROVED.equals(notif.type)) {
/* 358 */               h.thumbnail.setVisibility(8);
                    } else {
/* 360 */               h.thumbnail.setVisibility(4);
                    }
/* 364 */           if (VineEverydayNotification.TYPE_FOLLOWED.equals(notif.type) || VineEverydayNotification.TYPE_FOLLOW_APPROVED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) {
/* 367 */               h.typeIcon.setVisibility(0);
/* 368 */               h.typeIcon.setImageResource(C0257R.drawable.ic_activity_follow);
                    } else if (VineEverydayNotification.TYPE_COMMENT_MENTION.equals(notif.type) || VineEverydayNotification.TYPE_COMMENTED.equals(notif.type) || VineEverydayNotification.TYPE_GROUP_COMMENTED.equals(notif.type) || VineEverydayNotification.TYPE_POST_MENTION.equals(notif.type) || VineEverydayNotification.TYPE_MENTION.equals(notif.type)) {
/* 374 */               h.typeIcon.setVisibility(0);
/* 375 */               h.typeIcon.setImageResource(C0257R.drawable.ic_activity_comment);
                    } else if (VineEverydayNotification.TYPE_LIKED.equals(notif.type)) {
/* 377 */               h.typeIcon.setVisibility(0);
/* 378 */               h.typeIcon.setImageResource(C0257R.drawable.ic_activity_like);
                    } else if (VineEverydayNotification.TYPE_REVINED.equals(notif.type)) {
/* 380 */               h.typeIcon.setVisibility(0);
/* 381 */               h.typeIcon.setImageResource(C0257R.drawable.ic_activity_revine);
                    } else if (VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type)) {
/* 383 */               h.typeIcon.setVisibility(0);
/* 384 */               h.typeIcon.setImageResource(C0257R.drawable.ic_activity_twitter);
                    } else {
/* 386 */               h.typeIcon.setVisibility(8);
/* 387 */               h.clickable = false;
                    }
/* 390 */           if (VineEverydayNotification.TYPE_FOLLOWED.equals(notif.type) || VineEverydayNotification.TYPE_TWITTER_FRIEND_JOINED.equals(notif.type) || VineEverydayNotification.TYPE_ADDRESS_BOOK_FRIEND_JOINED.equals(notif.type)) {
/* 393 */               h.followButton.setVisibility(0);
/* 394 */               h.followButton.setOnClickListener(this.mViewClickListener);
/* 395 */               long followUserId = h.userId;
                    if (user != null) {
/* 396 */                   if (!this.mFriendships.contains(user.userId) && user.isFollowing()) {
/* 397 */                       this.mFriendships.addFollowing(user.userId);
                            }
                        followUserId = user.userId;
                    }
                    if (followUserId != 0) {
                        if (this.mFollowState.getBoolean("following_" + followUserId, false)) {
                            this.mFriendships.addFollowing(followUserId);
                        }
                        if (!isFollowing(followUserId) && (user == null || !user.isFollowing())) {
                            // The compatibility feed does not always flag actors the
                            // viewer already follows. Consult the local users table
                            // before rendering an unchecked follow button.
                            try {
                                VineDatabaseHelper db = VineDatabaseHelper.getDatabaseHelper(this.mContext);
                                if (db != null && db.amFollowingUser(db.getUserRowIdForUserRemoteId(followUserId))) {
                                    this.mFriendships.addFollowing(followUserId);
                                }
                            } catch (Throwable t) {
                                CrashUtil.logException(t);
                            }
                        }
/* 399 */                   h.followButton.setTag(Long.valueOf(followUserId));
/* 400 */                   if ((user != null && user.isFollowing()) || isFollowing(followUserId)) {
                               if (user != null && user.isFollowing()) {
                                   this.mFriendships.addFollowing(followUserId);
                               }
/* 401 */                       h.followButton.setBackgroundResource(C0257R.drawable.btn_following_default);
                            } else {
/* 403 */                       h.followButton.setBackgroundResource(C0257R.drawable.btn_follow_default);
                            }
                    }
                    } else {
/* 407 */               h.followButton.setVisibility(8);
                    }
/* 409 */           bindBodyText(h, notif);
                }

/* 412 */       public boolean isFollowing(long userId) {
/* 413 */           return this.mFriendships.isFollowing(userId);
                }

/* 417 */       private void bindBodyText(ActivityViewHolder h, VineNotification notif) {
/* 418 */           String contentLine = notif.comment;
/* 419 */           SpannableStringBuilder contentSb = new SpannableStringBuilder(contentLine);
/* 421 */           ArrayList<VineEntity> entities = null;
/* 422 */           if (notif.entities != null) {
/* 424 */               entities = new ArrayList<>(notif.entities.size());
                        for (int i = 0; i < notif.entities.size(); i++) {
/* 426 */                   entities.add(new VineEntity(notif.entities.get(i)));
                        }
                    }
/* 430 */           if (entities != null && !TextUtils.isEmpty(contentLine)) {
/* 431 */               Util.adjustEntities(entities, contentSb, 0, true);
/* 433 */               TextView contentTv = h.contentLine;
                        for (VineEntity entity : entities) {
/* 436 */                   EntityLinkClickableSpan span = new EntityLinkClickableSpan(entity.link);
/* 438 */                   if (span != null) {
                                try {
/* 439 */                           span.setColor(this.mSpanColor);
/* 440 */                           Util.safeSetSpan(contentSb, span, entity.start, entity.end, 33);
/* 441 */                           Util.safeSetSpan(contentSb, new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 4)), entity.start, entity.end, 33);
                                } catch (IndexOutOfBoundsException e) {
                                }
                            }
                        }
/* 449 */               if (h.dateLine != null) {
/* 451 */                   String date = Util.getRelativeTimeString(this.mContext, notif.createdAt, true);
/* 452 */                   h.dateLine.setText(date);
                        }
/* 455 */               contentTv.setText(contentSb);
                    }
                }

/* 459 */       private void setAvatar(ActivityViewHolder h, ImageView avatarImageView, String avatarUrl, long userId) {
                    if (avatarUrl != null && avatarUrl.startsWith("http://")) {
                        avatarUrl = "https://" + avatarUrl.substring(7);
                    }
/* 460 */           if (Util.isDefaultAvatarUrl(avatarUrl)) {
/* 461 */               Util.safeSetDefaultAvatar(avatarImageView, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mVineGreen);
                    } else {
/* 463 */               ImageKey key = new ImageKey(avatarUrl, this.mProfileImageSize, this.mProfileImageSize, true);
/* 464 */               h.avatarImageKey = key;
/* 465 */               setUserImage(avatarImageView, this.mAppController.getPhotoBitmap(key));
                    }
/* 467 */           avatarImageView.setTag(Long.valueOf(userId));
                }

                @Override // android.widget.Adapter
/* 471 */       public int getCount() {
/* 472 */           return this.mFollowRequests.size() + this.mNotifications.size();
                }

                @Override // android.widget.Adapter
/* 476 */       public Object getItem(int position) {
                    return position < this.mFollowRequests.size() ? this.mFollowRequests.get(position) : this.mNotifications.get(position - this.mFollowRequests.size());
                }

                @Override // android.widget.Adapter
/* 485 */       public long getItemId(int position) {
/* 486 */           return ((VineNotification) getItem(position)).notificationId;
                }

                @Override // android.widget.Adapter
                @Nullable
/* 492 */       public View getView(int position, View convertView, ViewGroup parent) {
                    View view;
/* 493 */           if (convertView == null) {
/* 494 */               view = newView(position, parent);
                    } else {
/* 496 */               view = convertView;
                    }
/* 498 */           bindView(view, position);
/* 499 */           return view;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 503 */       public int getItemViewType(int position) {
/* 504 */           if (position < this.mFollowRequests.size()) {
/* 505 */               return 2;
                    }
/* 507 */           VineEverydayNotification notif = (VineEverydayNotification) getItem(position);
/* 508 */           if (notif.milestone != null) {
/* 509 */               return 1;
                    }
/* 510 */           if ("count".equals(notif.type)) {
/* 511 */               return 3;
                    }
/* 513 */           return 0;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 517 */       public int getViewTypeCount() {
/* 518 */           return 4;
                }

/* 521 */       public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
                    UrlImage targetImage;
                    UrlImage targetImage2;
/* 522 */           ArrayList<WeakReference<ActivityViewHolder>> toRemove = new ArrayList<>();
                    for (WeakReference<ActivityViewHolder> ref : this.mViewHolders) {
/* 524 */               ActivityViewHolder holder = ref.get();
/* 525 */               if (holder == null) {
/* 526 */                   toRemove.add(ref);
                        } else {
/* 528 */                   if (holder.avatarImageKey != null) {
/* 529 */                       ImageKey key = holder.avatarImageKey;
/* 530 */                       UrlImage image3 = images.get(key);
/* 531 */                       if (image3 != null && image3.isValid()) {
/* 532 */                           setUserImage(holder.avatar, image3.bitmap);
                                }
                            }
/* 536 */                   if (holder.thumbnailImageKey != null && (targetImage2 = images.get(holder.thumbnailImageKey)) != null && targetImage2.isValid()) {
/* 539 */                       setImage(holder.thumbnail, targetImage2.bitmap);
                            }
/* 543 */                   if (holder.milestoneImageImageKey != null && (targetImage = images.get(holder.milestoneImageImageKey)) != null && targetImage.isValid()) {
/* 546 */                       setImage(holder.milestoneImage, targetImage.bitmap);
                            }
/* 550 */                   if (holder.milestoneBackgroundImageKey != null && (image2 = images.get(holder.milestoneBackgroundImageKey)) != null && image2.isValid()) {
/* 553 */                       setImage(holder.milestoneBackground, image2.bitmap);
                            }
/* 557 */                   if (holder.milestoneIconImageKey != null && (image = images.get(holder.milestoneIconImageKey)) != null && image.isValid()) {
/* 560 */                       setImage(holder.milestoneIcon, image.bitmap);
                            }
                        }
                    }
                    for (WeakReference<ActivityViewHolder> r : toRemove) {
/* 566 */               this.mViewHolders.remove(r);
                    }
                }

/* 570 */       private void setUserImage(ImageView v, Bitmap bmp) {
/* 571 */           if (v != null) {
/* 574 */               v.setColorFilter((ColorFilter) null);
/* 575 */               if (bmp == null) {
/* 576 */                   v.setImageResource(C0257R.drawable.circle_shape_light);
                        } else {
/* 578 */                   v.setBackgroundColor(0);
/* 579 */                   v.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                        }
                    }
                }

/* 583 */       private void setImage(ImageView v, Bitmap bmp) {
/* 584 */           setImage(v, bmp, true);
                }

/* 587 */       private void setImage(ImageView v, Bitmap bmp, boolean showGray) {
/* 588 */           if (v != null) {
/* 591 */               if (bmp == null) {
/* 592 */                   if (showGray) {
/* 593 */                       v.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
                            } else {
/* 596 */                       v.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
                            }
/* 598 */                   v.setImageBitmap(null);
/* 603 */                   return;
                        }
/* 600 */               v.setBackgroundColor(0);
/* 601 */               v.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    }
                }

                @Override // android.widget.BaseAdapter
/* 606 */       public void notifyDataSetChanged() {
/* 607 */           super.notifyDataSetChanged();
/* 608 */           this.mShouldReloadHeaderBecauseDataChanged = true;
                }

/* 613 */       public void mergeData(ArrayList<VineEverydayNotification> notifications, ArrayList<VineSingleNotification> followRequests, boolean mergeAfter) {
/* 614 */           if (!mergeAfter) {
/* 615 */               this.mNotifications = new ArrayList<>();
/* 616 */               this.mFollowRequests = new ArrayList<>();
/* 617 */               this.mFriendships.clear();
/* 618 */               this.mLastNewIndex = -1;
                    }
/* 621 */           if (notifications != null) {
                        for (VineEverydayNotification notif : notifications) {
/* 624 */                   if (notif.isNew) {
/* 625 */                       this.mLastNewIndex++;
                            }
/* 627 */                   this.mNotifications.add(notif);
                        }
                    }
/* 631 */           if (followRequests != null) {
/* 632 */               Iterator<VineSingleNotification> it = followRequests.iterator();
                        while (it.hasNext()) {
/* 633 */                   this.mFollowRequests.add(it.next());
                        }
                    }
/* 638 */           ((PinnedHeaderListView) this.mListView).untrackScrollawayChild();
/* 640 */           if (notifications != null || followRequests != null) {
/* 641 */               notifyDataSetChanged();
                    }
                }

/* 645 */       public void follow(long userId) {
/* 646 */           this.mFriendships.addFollowing(userId);
                    this.mFollowState.edit().putBoolean("following_" + userId, true).apply();
/* 647 */           notifyDataSetChanged();
                }

/* 650 */       public void unfollow(long userId) {
/* 651 */           this.mFriendships.removeFollowing(userId);
                    this.mFollowState.edit().putBoolean("following_" + userId, false).apply();
/* 652 */           notifyDataSetChanged();
                }

                @Override // android.widget.SectionIndexer
/* 656 */       public Object[] getSections() {
/* 657 */           this.mSections.clear();
/* 658 */           if (this.mFollowRequests.size() > 0) {
/* 659 */               this.mSections.add(this.mFollowRequestsString);
                    }
/* 661 */           if (this.mLastNewIndex >= 0) {
/* 662 */               this.mSections.add(this.mNewActivityString);
                    }
/* 664 */           if (this.mLastNewIndex >= 0 || this.mFollowRequests.size() > 0) {
/* 665 */               this.mSections.add(this.mOlderActivityString);
                    }
/* 667 */           return this.mSections.toArray();
                }

                @Override // android.widget.SectionIndexer
/* 671 */       public int getPositionForSection(int sectionIndex) {
/* 672 */           if (getSections().length <= 0 || sectionIndex == 0) {
                        return 0;
                    }
/* 679 */           if (sectionIndex == 1) {
/* 681 */               if (this.mFollowRequests.size() > 0) {
/* 682 */                   return this.mFollowRequests.size();
                        }
/* 683 */               if (this.mLastNewIndex >= 0) {
/* 685 */                   return this.mLastNewIndex + 1;
                        }
                    }
/* 690 */           if (sectionIndex == 2) {
/* 691 */               return this.mFollowRequests.size() + this.mLastNewIndex + 1;
                    }
                    return 0;
                }

                @Override // android.widget.SectionIndexer
/* 698 */       public int getSectionForPosition(int position) {
/* 699 */           if (getSections().length <= 0 || position < this.mFollowRequests.size()) {
                        return 0;
                    }
/* 702 */           if (position <= this.mFollowRequests.size() + this.mLastNewIndex) {
/* 703 */               return this.mFollowRequests.size() <= 0 ? 0 : 1;
                    }
                    return (this.mFollowRequests.size() > 0 ? 1 : 0) + (this.mLastNewIndex < 0 ? 0 : 1);
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 712 */       public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
                    View nextView;
/* 713 */           int pinnedHeaderSection = this.mPinnedHeaderSection;
/* 714 */           int childIndex = ((PinnedHeaderListView) this.mListView).getPositionForPixelLocation(navBottom);
/* 715 */           int position = (this.mListView.getFirstVisiblePosition() - this.mListView.getHeaderViewsCount()) + childIndex;
/* 716 */           this.mPinnedHeaderSection = getSectionForPosition(position);
/* 717 */           int offset = 0;
/* 718 */           if (nextViewHasHeader(position) && (nextView = this.mListView.getChildAt(childIndex + 1)) != null && nextView.getTop() < this.mPinnedHeaderHeight + navBottom) {
/* 722 */               offset = (nextView.getTop() - this.mPinnedHeaderHeight) - navBottom;
                    }
/* 726 */           boolean shouldReloadHeader = pinnedHeaderSection != this.mPinnedHeaderSection || this.mShouldReloadHeaderBecauseDataChanged;
/* 727 */           this.mShouldReloadHeaderBecauseDataChanged = false;
/* 728 */           return new PinnedHeaderAdapter.PinnedHeaderStatus(offset, shouldReloadHeader, true);
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 732 */       public View getPinnedHeaderView(View convertView) {
/* 733 */           if (getSections().length == 0) {
                        return null;
                    }
/* 736 */           if (convertView == null) {
/* 737 */               convertView = LayoutInflater.from(this.mContext).inflate(C0257R.layout.activity_header, (ViewGroup) null);
/* 738 */               convertView.layout(0, 0, this.mListView.getWidth(), this.mPinnedHeaderHeight);
/* 739 */               ActivityViewHolder h = new ActivityViewHolder(convertView);
/* 740 */               convertView.setTag(h);
                    }
/* 742 */           ActivityViewHolder h2 = (ActivityViewHolder) convertView.getTag();
/* 743 */           bindHeader(h2, getPositionForSection(this.mPinnedHeaderSection));
                    return convertView;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 749 */       public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
/* 750 */           header.layout(l, t, r, b);
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 754 */       public int getPinnedHeaderHeight() {
/* 755 */           return this.mPinnedHeaderHeight;
                }
            }
