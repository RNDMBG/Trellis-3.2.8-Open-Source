            package co.vine.android.widget;

            import android.app.Activity;
            import android.content.Context;
            import android.content.res.ColorStateList;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.graphics.Point;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.text.style.TextAppearanceSpan;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.widget.AbsListView;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.BaseTimelineFragment;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.ViewOffsetResolver;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineLike;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineVideoUrlTier;
            import co.vine.android.cache.LikeCache;
            import co.vine.android.cache.RevineCache;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoCache;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.Session;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.GenericFeedViewHolder;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            
            
   public abstract class GenericVideoAdapter<T extends GenericFeedViewHolder> extends BaseAdapter {
                protected static final int COMMENT_TYPE_FEW = 1;
                protected static final int COMMENT_TYPE_MANY = 2;
                protected static final char INTERPUNCT = 183;
                protected static final int MAX_COMMENT_ITEMS = 3;
                protected static final char SPACE = ' ';
                protected static final int SPAN_FLAGS = 33;
                protected final AppController mAppController;
                protected final TypefacesSpan mBoldSpan;
                protected final Context mContext;
                protected final int mDimen;
                protected final String mFollowEventSource;
                protected boolean mIconsSwitched;
                protected final LayoutInflater mInflater;
                protected LikeCache mLikeCache;
                protected final ListView mListView;
                protected String mLoadingTimeTag;
                protected final SLogger mLogger;
                private Boolean mNormalVideoPlayable;
                protected int mProfileColor;
                private int mProfileImageSize;
                protected RevineCache mRevineCache;
                protected final Drawable mRevineDrawable;
                protected final HashSet<Long> mSensitiveContentWarningsAcknowledged;
                protected final SpanClickListener mSpanClickListener;
                protected long mStartLoadingTime;
                protected final BaseTimelineFragment mTimelineFragment;
                protected final View.OnClickListener mViewClickListener;
                protected final ArrayList<WeakReference<T>> mViewHolders;
                protected ViewOffsetResolver mViewOffsetResolver;
                private final int mVineGray;
                protected final int mVineGreen;

                protected abstract void createInsensitiveListener(T t);

                protected abstract void createSensitiveLisenter(T t);

       public GenericVideoAdapter(Activity context, ListView listView, LikeCache likeCache, RevineCache revineCache, BaseTimelineFragment timelineFragment, String followEventSource, SLogger logger) {
           Resources res = context.getResources();
           this.mLogger = logger;
           this.mContext = context;
           this.mInflater = context.getLayoutInflater();
           this.mAppController = AppController.getInstance(context);
           this.mViewClickListener = timelineFragment;
           this.mSpanClickListener = timelineFragment;
           this.mViewHolders = new ArrayList<>();
           this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 3));
           this.mSensitiveContentWarningsAcknowledged = new HashSet<>();
           Point size = SystemUtil.getDisplaySize(context);
           this.mDimen = size.x;
           this.mLikeCache = likeCache;
           this.mRevineCache = revineCache;
           this.mFollowEventSource = followEventSource;
           this.mProfileImageSize = Util.getUserImageSize(res);
           this.mVineGreen = res.getColor(C0257R.color.vine_green);
           this.mVineGray = res.getColor(C0257R.color.black_thirty_five_percent);
           this.mProfileColor = this.mVineGreen;
           this.mRevineDrawable = this.mContext.getResources().getDrawable(C0257R.drawable.ic_revine);
           this.mListView = listView;
           this.mListView.setDivider(null);
           this.mListView.setOnScrollListener(timelineFragment);
           this.mTimelineFragment = timelineFragment;
                }

       public void setProfileColor(int color) {
           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
               color = this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
           if (this.mProfileColor != color) {
               this.mProfileColor = (-16777216) | color;
                    }
                }

       protected void resetStates(boolean hasDataSetChanged) {
                }

       public void showUnlikedByMe(long postId) {
           ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
               T holder = ref.get();
               if (holder == null) {
                   toRemove.add(ref);
                        } else if (holder.post != null && holder.post.postId == postId) {
                   if (holder.post.isLiked() && holder.post.likesCount > 0) {
                                holder.post.likesCount--;
                            }
                   if (holder.post.likes != null && holder.post.likes.items != null) {
                       ArrayList<VineLike> arrayList = holder.post.likes.items;
                       VineLike likeToRemove = null;
                                for (VineLike l : arrayList) {
                                    if (l.userId == this.mAppController.getActiveId()) {
                               likeToRemove = l;
                                    }
                                }
                       if (likeToRemove != null) {
                           arrayList.remove(likeToRemove);
                                }
                            }
                   holder.post.setFlagLiked(false);
                   holder.liked.setSelected(false);
                   setStyledCompactLikesComments(holder);
                        }
                    }
                    for (WeakReference<T> h : toRemove) {
               this.mViewHolders.remove(h);
                    }
                }

                
       public void showLikedByMe(long j) {
           ArrayList arrayList = new ArrayList();
                    for (WeakReference<T> weakReference : this.mViewHolders) {
               T t = weakReference.get();
               if (t == null) {
                   arrayList.add(weakReference);
                        } else if (t.post != null && t.post.postId == j) {
                            if (!t.post.isLiked()) {
                                t.post.likesCount++;
                            }
                   VinePagedData<VineLike> vinePagedData = t.post.likes;
                   VineLike vineLike = new VineLike();
                   Session activeSession = this.mAppController.getActiveSession();
                   vineLike.likeId = -1L;
                   vineLike.postId = j;
                   vineLike.username = activeSession.getName();
                   vineLike.userId = activeSession.getUserId();
                   if (vinePagedData == null) {
                       vinePagedData = new VinePagedData<>();
                            }
                   if (vinePagedData.items == null) {
                       vinePagedData.items = new ArrayList<>();
                            }
                   vinePagedData.items.add(vineLike);
                   t.post.setFlagLiked(true);
                   t.liked.setSelected(true);
                   setStyledCompactLikesComments(t);
                        }
                    }
           Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
               this.mViewHolders.remove((WeakReference) it.next());
                    }
                }

                public void incrementCommentCount(long postId) {
                    ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
                        T holder = ref.get();
                        if (holder == null) {
                            toRemove.add(ref);
                        } else if (holder.post != null && holder.post.postId == postId) {
                            holder.post.commentsCount++;
                            if (holder.post.comments != null) {
                                holder.post.comments.count = holder.post.commentsCount;
                            }
                            setStyledCompactLikesComments(holder);
                        }
                    }
                    for (WeakReference<T> ref : toRemove) {
                        this.mViewHolders.remove(ref);
                    }
                }

       public void onDestroy() {
                }

                @Override 
       public void notifyDataSetChanged() {
           super.notifyDataSetChanged();
           resetStates(true);
                }

       public void startLoadingTimeProfiling(String tag) {
           this.mLoadingTimeTag = tag;
           this.mStartLoadingTime = System.currentTimeMillis();
                }

       public long timeSinceStartLoadingTime() {
           return System.currentTimeMillis() - this.mStartLoadingTime;
                }

       protected void endLoadingTimeProfiling() {
                    if (this.mStartLoadingTime > 0) {
               long loadingTime = Math.round(timeSinceStartLoadingTime() / 500) * 500;
               this.mStartLoadingTime = 0L;
               this.mLogger.m507i("Loading time for {} was {}ms.", this.mLoadingTimeTag, Long.valueOf(loadingTime));
               FlurryUtils.trackLoadingTime(this.mLoadingTimeTag, loadingTime);
                    }
                }

       public long getStartLoadingTime() {
           return this.mStartLoadingTime;
                }

       public void toggleMute(boolean mute) {
                }

       public void onPause(boolean isFocused) {
                }

       public void onResume(boolean isFocused) {
                }

       public void onDestroyView() {
                }

       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                }

       public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

       public boolean onBackPressed() {
           return false;
                }

       public void setOffsetResolver(ViewOffsetResolver resolver) {
           this.mViewOffsetResolver = resolver;
                }

       protected void bindAvatar(GenericFeedViewHolder holder) {
           VinePost post = holder.post;
           ImageView userImage = holder.userImage;
                    String avatarUrl = post.avatarUrl;
                    
                    
                    if (post.user != null && !TextUtils.isEmpty(post.user.avatarUrl)
                            && (TextUtils.isEmpty(avatarUrl) || Util.isDefaultAvatarUrl(avatarUrl))) {
                        avatarUrl = post.user.avatarUrl;
                    }
                    if (avatarUrl != null && avatarUrl.startsWith("http://")) {
                        avatarUrl = "https://" + avatarUrl.substring(7);
                    }
           if (avatarUrl != null) {
               if (Util.isDefaultAvatarUrl(avatarUrl)) {
                   if (post.isRevined()) {
                       Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, this.mVineGreen);
                       return;
                            } else {
                       Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, this.mProfileColor);
                       return;
                            }
                        }
                    
                    
                    
               ImageKey avatarKey = new ImageKey(avatarUrl, true);
               setUserImage(userImage, this.mAppController.getPhotoBitmap(avatarKey));
               holder.avatarKey = avatarKey;
                    }
                }

        public void updateRevinedCount(long postId, boolean revined) {
            updateRevinedCount(postId, revined, -1L);
                 }

        public void updateRevinedCount(long postId, boolean revined, long myRepostId) {
            ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                     for (WeakReference<T> ref : this.mViewHolders) {
                GenericFeedViewHolder holder = ref.get();
                if (holder == null) {
                    toRemove.add(ref);
                         } else if (holder.post != null && holder.post.postId == postId) {
                             
                             
                             
                            boolean wasRevined = holder.post.isRevined();
                             if (revined && !wasRevined) {
                                 holder.post.revinersCount++;
                             } else if (!revined && wasRevined && holder.post.revinersCount > 0) {
                                 holder.post.revinersCount--;
                             }
                             if (myRepostId >= 0) {
                                 holder.post.myRepostId = myRepostId;
                             }
                            holder.post.setFlagRevined(revined);
                            holder.share.setColor(this.mProfileColor);
                            holder.share.setSelected(revined);
                            holder.share.invalidate();
                   setStyledCompactLikesComments(holder);
                        }
                    }
                    for (WeakReference<T> h : toRemove) {
               this.mViewHolders.remove(h);
                    }
                }

       protected void setStyledComments(GenericFeedViewHolder holder, VinePagedData<VineComment> commentData) {
                    TextView thisComment;
           Resources res = this.mContext.getResources();
           holder.comments1.setVisibility(8);
           holder.comments2.setVisibility(8);
           holder.comments3.setVisibility(8);
           View postCommentsDivider = holder.postCommentsDivider;
           if (commentData != null && !commentData.isEmpty()) {
               postCommentsDivider.setVisibility(0);
                    } else {
               postCommentsDivider.setVisibility(8);
                    }
           if (commentData != null && commentData.items != null && !commentData.items.isEmpty() && holder != null) {
               if (commentData.count > 3) {
                   String cta = res.getString(C0257R.string.timeline_comments_many, Integer.valueOf(commentData.count));
                   SpannableStringBuilder ssb = new SpannableStringBuilder();
                   ssb.append((CharSequence) cta);
                   StyledClickableSpan clickableSpan = new StyledClickableSpan(3, new SpanCommentObjectTag(holder.post.postId, holder.post.getVineRepostRepostId(), holder.post.userId), this.mSpanClickListener);
                   clickableSpan.setColor(this.mVineGray);
                   Util.safeSetSpan(ssb, clickableSpan, 0, ssb.length(), 33);
                   TextView viewAllComments = holder.viewAllComments;
                   viewAllComments.setVisibility(0);
                   viewAllComments.setMovementMethod(LinkMovementMethod.getInstance());
                   viewAllComments.setText(ssb);
                        } else {
                   holder.viewAllComments.setVisibility(8);
                        }
               ArrayList<VineComment> arrayList = commentData.items;
               int size = arrayList.size();
               int count = 0;
               int index = Math.max(size - 3, 0);
                        while (index < size) {
                   VineComment comment = (VineComment) arrayList.get(index);
                   if (count < 3) {
                                switch (count) {
                                    case 0:
                               thisComment = holder.comments1;
                                        break;
                                    case 1:
                               thisComment = holder.comments2;
                                        break;
                                    default:
                               thisComment = holder.comments3;
                                        break;
                                }
                       thisComment.setVisibility(0);
                       if (comment.commentSb == null) {
                           comment.commentSb = new SpannableStringBuilder();
                           SpannableStringBuilder thisCommentSb = comment.commentSb;
                                    try {
                               String relativeTimeString = Util.getRelativeTimeString(this.mContext, comment.timestamp, false);
                               thisCommentSb.append((CharSequence) comment.username).append(SPACE).append((CharSequence) comment.comment).append(SPACE).append((CharSequence) relativeTimeString);
                               int usernameEnd = comment.username.length();
                               int timestampStart = comment.comment.length() + usernameEnd + 2;
                               int timestampEnd = thisCommentSb.length();
                               StyledClickableSpan span = new StyledClickableSpan(1, Long.valueOf(comment.userId), this.mSpanClickListener);
                               TypefacesSpan boldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 3));
                               ColorStateList color = new ColorStateList(new int[][]{new int[0]}, new int[]{res.getColor(C0257R.color.black_thirty_five_percent)});
                               TextAppearanceSpan timestampSpan = new TextAppearanceSpan(null, 0, res.getDimensionPixelSize(C0257R.dimen.font_size_middle), color, null);
                               span.setColor(this.mProfileColor);
                               Util.safeSetSpan(thisCommentSb, span, 0, usernameEnd, 33);
                               Util.safeSetSpan(thisCommentSb, boldSpan, 0, usernameEnd, 33);
                               Util.safeSetSpan(thisCommentSb, timestampSpan, timestampStart, timestampEnd, 33);
                               if (comment.transientEntities == null && comment.entities != null) {
                                   comment.transientEntities = new ArrayList<>();
                                            for (VineEntity entity : comment.entities) {
                                       comment.transientEntities.add(entity.duplicate());
                                            }
                                        }
                               ArrayList<VineEntity> entities = comment.transientEntities;
                               if (entities != null) {
                                            try {
                                       Util.adjustEntities(entities, thisCommentSb, usernameEnd + 1, false);
                                                for (VineEntity e : entities) {
                                           if (e.isUserType()) {
                                               StyledClickableSpan click = new StyledClickableSpan(1, Long.valueOf(e.f96id), this.mSpanClickListener);
                                               click.setColor(this.mProfileColor);
                                               Util.safeSetSpan(thisCommentSb, click, e.start, e.end, 33);
                                                    } else if (e.isTagType()) {
                                               StyledClickableSpan click2 = new StyledClickableSpan(4, e.title, this.mSpanClickListener);
                                               click2.setColor(this.mProfileColor);
                                               Util.safeSetSpan(thisCommentSb, click2, e.start, e.end, 33);
                                                    }
                                                }
                                            } catch (Exception e2) {
                                       if (BuildUtil.isLogsOn()) {
                                           throw new RuntimeException(e2);
                                                }
                                       CrashUtil.logException(e2, "Failed to adjust entities on " + comment.commentId, new Object[0]);
                                            }
                                        }
                               thisComment.setMovementMethod(LinkMovementMethod.getInstance());
                               thisComment.setText(comment.commentSb);
                                    } catch (NullPointerException e3) {
                               CrashUtil.logException(e3, "Comment attribute caused NPE: {} {} {}", Long.valueOf(comment.commentId), comment.username, comment.comment);
                                    }
                                } else {
                           thisComment.setMovementMethod(LinkMovementMethod.getInstance());
                           thisComment.setText(comment.commentSb);
                                }
                       index++;
                                count++;
                            } else {
                       return;
                            }
                        }
                    }
                }

       protected void setStyledCompactLikesComments(GenericFeedViewHolder h) {
           setStyledCompactLikesComments(h, true);
                }

       protected void setStyledCompactLikesComments(GenericFeedViewHolder h, boolean countsUpdated) {
           if (h.post.styledCompactLikesComments == null || countsUpdated) {
               int likeCount = h.post.likesCount;
               int commentCount = h.post.commentsCount;
               int revinersCount = h.post.revinersCount;
               boolean showCommentsText = h.post.comments != null;
               Resources res = this.mContext.getResources();
               SpannableStringBuilder sb = new SpannableStringBuilder();
               if (likeCount > 0) {
                   String numberLikesString = Util.numberFormat(res, likeCount);
                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.timeline_compact_likes, likeCount, numberLikesString));
                   StyledClickableSpan clickableSpan = new StyledClickableSpan(2, Long.valueOf(h.post.postId), this.mSpanClickListener);
                   clickableSpan.setColor(this.mVineGray);
                   int end = sb.length();
                   Util.safeSetSpan(sb, clickableSpan, 0, end, 33);
                        }
               if (commentCount > 0 && showCommentsText) {
                   if (sb.length() > 0) {
                       sb.append(SPACE).append(INTERPUNCT).append(SPACE);
                            }
                   String numberCommentsString = Util.numberFormat(res, commentCount);
                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.timeline_compact_comments, commentCount, numberCommentsString));
                   StyledClickableSpan clickableSpan2 = new StyledClickableSpan(3, new SpanCommentObjectTag(h.post.postId, h.post.getVineRepostRepostId(), h.post.userId), this.mSpanClickListener);
                   clickableSpan2.setColor(this.mVineGray);
                   int interpunctIndex = sb.toString().indexOf(183);
                   int start = interpunctIndex < 0 ? 0 : interpunctIndex + 2;
                   int end2 = sb.length();
                   Util.safeSetSpan(sb, clickableSpan2, start, end2, 33);
                        }
               if (revinersCount > 0) {
                   if (sb.length() > 0) {
                       sb.append(SPACE).append(INTERPUNCT).append(SPACE);
                            }
                   String numRevinersString = Util.numberFormat(res, revinersCount);
                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.revine_count, revinersCount, numRevinersString));
                   StyledClickableSpan clickableSpan3 = new StyledClickableSpan(7, new SpanCommentObjectTag(h.post.postId, h.post.getVineRepostRepostId(), h.post.userId), this.mSpanClickListener);
                   clickableSpan3.setColor(this.mVineGray);
                   int interpunctIndex2 = sb.toString().lastIndexOf(183);
                   int start2 = interpunctIndex2 < 0 ? 0 : interpunctIndex2 + 2;
                   int end3 = sb.length();
                   Util.safeSetSpan(sb, clickableSpan3, start2, end3, 33);
                        }
               if (sb.length() > 0) {
                   TypefacesSpan boldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 2));
                   Util.safeSetSpan(sb, boldSpan, 0, sb.length(), 33);
                        }
               h.post.styledCompactLikesComments = sb;
                    }
           if (h.post.styledCompactLikesComments.length() > 0) {
               h.compactLikesCommentsRevinesCounts.setMovementMethod(LinkMovementMethod.getInstance());
               h.compactLikesCommentsRevinesCounts.setText(h.post.styledCompactLikesComments);
                    } else {
               h.compactLikesCommentsRevinesCounts.setText(this.mContext.getString(C0257R.string.first_to_like));
                    }
           h.compactLikesCommentsRevinesCounts.setVisibility(0);
                }

       protected void onVideoImageObtained(T holder, boolean b) {
                }

                @Override 
       public long getItemId(int position) {
           return position;
                }

                @Override 
       public boolean hasStableIds() {
           return false;
                }

       protected boolean setUserImage(ImageView imageView, Bitmap bitmap) {
           imageView.setColorFilter((ColorFilter) null);
           if (bitmap == null) {
               imageView.setImageResource(C0257R.drawable.circle_shape_light);
                        return false;
                    }
           imageView.setBackgroundColor(0);
           imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
           return true;
                }

       protected boolean setImage(View imageView, Bitmap bitmap, String url) {
           if (bitmap == null) {
               imageView.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
               return false;
                    }
           ViewUtil.setBackground(imageView, new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
           return true;
                }

       public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
                    UrlImage image3;
           ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
               T holder = ref.get();
               if (holder == null) {
                   toRemove.add(ref);
                        } else {
                   if (holder.avatarKey != null && (image3 = images.get(holder.avatarKey)) != null && image3.isValid()) {
                       setUserImage(holder.userImage, image3.bitmap);
                       this.mLogger.m487d("found for avatar.");
                            }
                   if (holder.videoImageKey != null && (image2 = images.get(holder.videoImageKey)) != null && image2.isValid()) {
                       this.mLogger.m488d("found for video: {}", image2.url);
                       onVideoImageObtained(holder, setImage(holder.getViewForVideoImage(), image2.bitmap, image2.url));
                            }
                   if (holder.bylineIconImageKey != null && (image = images.get(holder.bylineIconImageKey)) != null && image.isValid()) {
                       this.mLogger.m488d("got icon for {}", image.url);
                       setBylineIcon(image.bitmap, holder);
                            }
                        }
                    }
           this.mViewHolders.removeAll(toRemove);
                }

       protected void setBylineIcon(Bitmap bmp, GenericFeedViewHolder h) {
           if (bmp == null) {
               h.bylineIcon.setImageDrawable(null);
                    } else {
               RecyclableBitmapDrawable draw = new RecyclableBitmapDrawable(this.mContext.getResources(), bmp);
               h.bylineIcon.setImageDrawable(draw);
                    }
                }

       protected synchronized boolean bindVideoImage(T h) {
                    boolean z = false;
                    synchronized (this) {
                        try {
                            VinePost post = h.post;
                   int i = h.position;
                   if (post == null) {
                                z = true;
                            } else {
                       h.hasVideoImage = post.thumbnailUrl != null;
                       boolean isSensitiveImage = post.isExplicit() && !this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(post.postId));
                       if (isSensitiveImage) {
                           h.sensitiveTextContainer.setVisibility(0);
                           createSensitiveLisenter(h);
                                } else {
                           createInsensitiveListener(h);
                           if (h.hasVideoImage) {
                               ImageKey videoImageKey = new ImageKey(post.thumbnailUrl);
                               h.videoImageKey = videoImageKey;
                               h.isVideoImageLoaded = setImage(h.getViewForVideoImage(), this.mAppController.getPhotoBitmap(videoImageKey), videoImageKey.url);
                                    }
                                }
                                z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

       public boolean isPlaying() {
           return false;
                }

       public void onTrimMemory(int level) {
           this.mLogger.m496e("onTrimMemory called.");
                }

       public void onLowMemory() {
           this.mLogger.m496e("onLowMemory called.");
                }

       public class SpanCommentObjectTag {
                    public final long authorId;
                    public final long postId;
                    public final long repostId;

           public SpanCommentObjectTag(long postId, long repostId, long authorId) {
               this.postId = postId;
               this.repostId = repostId;
               this.authorId = authorId;
                    }
                }

       protected void onInvalidRequestKey(VinePost post) {
           if (post.getUrls() != null) {
                        for (VineVideoUrlTier tier : post.getUrls()) {
                   if (tier != null) {
                       CrashUtil.log("postId " + post.postId + " tier rate " + tier.rate + " format " + tier.format + " url " + tier.url);
                            }
                        }
                    }
           CrashUtil.logException(new IllegalArgumentException(), "Invalid request key for post " + post.postId, new Object[0]);
                }

       protected VideoKey getRequestKey(VinePost post, boolean forceLowKey) {
           if (this.mNormalVideoPlayable == null) {
                        this.mNormalVideoPlayable = Boolean.valueOf(SystemUtil.isNormalVideoPlayable(this.mContext) != SystemUtil.PrefBooleanState.FALSE);
                    }
           int avgSpeed = VideoCache.getCurrentAverageSpeed();
           this.mLogger.m489d("Getting HQ Video: speed {} && can play normal vid {}", Integer.valueOf(avgSpeed), this.mNormalVideoPlayable);
           return Util.getVideoRequestKey(post, forceLowKey, avgSpeed);
                }

       public View getViewAtPosition(int wantedPosition) {
           ListView listView = this.mListView;
           int offset = 0;
           if (this.mViewOffsetResolver != null) {
               offset = this.mViewOffsetResolver.getOffset(this);
                    }
           int headerViewCount = listView.getHeaderViewsCount();
           int firstPosition = (listView.getFirstVisiblePosition() - headerViewCount) - offset;
           int wantedChild = wantedPosition - firstPosition;
           if (wantedChild < 0 || wantedChild >= listView.getChildCount()) {
               if (SLog.sLogsOn) {
                   this.mLogger.m522w("Unable to get view for desired position, because it's not being displayed on screen: {} {} {} {}.", new Object[]{Integer.valueOf(wantedPosition), Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
                        }
               return null;
                    }
           if (SLog.sLogsOn) {
               this.mLogger.m494d("Get view for desired position: {} {} {} {}.", new Object[]{Integer.valueOf(wantedPosition), Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
                    }
           return listView.getChildAt(wantedChild);
                }
            }
