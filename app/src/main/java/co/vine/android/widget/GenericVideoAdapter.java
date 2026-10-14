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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 102 */   public abstract class GenericVideoAdapter<T extends GenericFeedViewHolder> extends BaseAdapter {
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

/* 103 */       public GenericVideoAdapter(Activity context, ListView listView, LikeCache likeCache, RevineCache revineCache, BaseTimelineFragment timelineFragment, String followEventSource, SLogger logger) {
/* 105 */           Resources res = context.getResources();
/* 107 */           this.mLogger = logger;
/* 108 */           this.mContext = context;
/* 109 */           this.mInflater = context.getLayoutInflater();
/* 110 */           this.mAppController = AppController.getInstance(context);
/* 111 */           this.mViewClickListener = timelineFragment;
/* 112 */           this.mSpanClickListener = timelineFragment;
/* 113 */           this.mViewHolders = new ArrayList<>();
/* 114 */           this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 3));
/* 118 */           this.mSensitiveContentWarningsAcknowledged = new HashSet<>();
/* 120 */           Point size = SystemUtil.getDisplaySize(context);
/* 122 */           this.mDimen = size.x;
/* 123 */           this.mLikeCache = likeCache;
/* 124 */           this.mRevineCache = revineCache;
/* 125 */           this.mFollowEventSource = followEventSource;
/* 127 */           this.mProfileImageSize = Util.getUserImageSize(res);
/* 129 */           this.mVineGreen = res.getColor(C0257R.color.vine_green);
/* 131 */           this.mVineGray = res.getColor(C0257R.color.black_thirty_five_percent);
/* 133 */           this.mProfileColor = this.mVineGreen;
/* 135 */           this.mRevineDrawable = this.mContext.getResources().getDrawable(C0257R.drawable.ic_revine);
/* 137 */           this.mListView = listView;
/* 138 */           this.mListView.setDivider(null);
/* 139 */           this.mListView.setOnScrollListener(timelineFragment);
/* 140 */           this.mTimelineFragment = timelineFragment;
                }

/* 144 */       public void setProfileColor(int color) {
/* 145 */           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 146 */               color = this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 148 */           if (this.mProfileColor != color) {
/* 149 */               this.mProfileColor = (-16777216) | color;
                    }
                }

/* 153 */       protected void resetStates(boolean hasDataSetChanged) {
                }

/* 156 */       public void showUnlikedByMe(long postId) {
/* 157 */           ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
/* 159 */               T holder = ref.get();
/* 160 */               if (holder == null) {
/* 161 */                   toRemove.add(ref);
                        } else if (holder.post != null && holder.post.postId == postId) {
/* 164 */                   if (holder.post.isLiked() && holder.post.likesCount > 0) {
                                holder.post.likesCount--;
                            }
/* 168 */                   if (holder.post.likes != null && holder.post.likes.items != null) {
/* 169 */                       ArrayList<VineLike> arrayList = holder.post.likes.items;
/* 170 */                       VineLike likeToRemove = null;
                                for (VineLike l : arrayList) {
                                    if (l.userId == this.mAppController.getActiveId()) {
/* 173 */                               likeToRemove = l;
                                    }
                                }
/* 176 */                       if (likeToRemove != null) {
/* 177 */                           arrayList.remove(likeToRemove);
                                }
                            }
/* 180 */                   holder.post.setFlagLiked(false);
/* 181 */                   holder.liked.setSelected(false);
/* 182 */                   setStyledCompactLikesComments(holder);
                        }
                    }
                    for (WeakReference<T> h : toRemove) {
/* 188 */               this.mViewHolders.remove(h);
                    }
                }

                /* JADX WARN: Type inference incomplete: some casts might be missing */
/* 192 */       public void showLikedByMe(long j) {
/* 193 */           ArrayList arrayList = new ArrayList();
                    for (WeakReference<T> weakReference : this.mViewHolders) {
/* 195 */               T t = weakReference.get();
/* 196 */               if (t == null) {
/* 197 */                   arrayList.add(weakReference);
                        } else if (t.post != null && t.post.postId == j) {
                            if (!t.post.isLiked()) {
                                t.post.likesCount++;
                            }
/* 201 */                   VinePagedData<VineLike> vinePagedData = t.post.likes;
/* 202 */                   VineLike vineLike = new VineLike();
/* 203 */                   Session activeSession = this.mAppController.getActiveSession();
/* 204 */                   vineLike.likeId = -1L;
/* 205 */                   vineLike.postId = j;
/* 206 */                   vineLike.username = activeSession.getName();
/* 207 */                   vineLike.userId = activeSession.getUserId();
/* 208 */                   if (vinePagedData == null) {
/* 209 */                       vinePagedData = new VinePagedData<>();
                            }
/* 211 */                   if (vinePagedData.items == null) {
/* 212 */                       vinePagedData.items = new ArrayList<>();
                            }
/* 214 */                   vinePagedData.items.add(vineLike);
/* 215 */                   t.post.setFlagLiked(true);
/* 216 */                   t.liked.setSelected(true);
/* 216 */                   setStyledCompactLikesComments(t);
                        }
                    }
/* 221 */           Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
/* 222 */               this.mViewHolders.remove((WeakReference) it.next());
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

/* 227 */       public void onDestroy() {
                }

                @Override // android.widget.BaseAdapter
/* 231 */       public void notifyDataSetChanged() {
/* 232 */           super.notifyDataSetChanged();
/* 233 */           resetStates(true);
                }

/* 236 */       public void startLoadingTimeProfiling(String tag) {
/* 237 */           this.mLoadingTimeTag = tag;
/* 238 */           this.mStartLoadingTime = System.currentTimeMillis();
                }

/* 241 */       public long timeSinceStartLoadingTime() {
/* 242 */           return System.currentTimeMillis() - this.mStartLoadingTime;
                }

/* 245 */       protected void endLoadingTimeProfiling() {
                    if (this.mStartLoadingTime > 0) {
/* 247 */               long loadingTime = Math.round(timeSinceStartLoadingTime() / 500) * 500;
/* 248 */               this.mStartLoadingTime = 0L;
/* 249 */               this.mLogger.m507i("Loading time for {} was {}ms.", this.mLoadingTimeTag, Long.valueOf(loadingTime));
/* 250 */               FlurryUtils.trackLoadingTime(this.mLoadingTimeTag, loadingTime);
                    }
                }

/* 254 */       public long getStartLoadingTime() {
/* 255 */           return this.mStartLoadingTime;
                }

/* 259 */       public void toggleMute(boolean mute) {
                }

/* 263 */       public void onPause(boolean isFocused) {
                }

/* 267 */       public void onResume(boolean isFocused) {
                }

/* 271 */       public void onDestroyView() {
                }

/* 275 */       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                }

/* 279 */       public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

/* 285 */       public boolean onBackPressed() {
/* 286 */           return false;
                }

/* 289 */       public void setOffsetResolver(ViewOffsetResolver resolver) {
/* 290 */           this.mViewOffsetResolver = resolver;
                }

/* 293 */       protected void bindAvatar(GenericFeedViewHolder holder) {
/* 294 */           VinePost post = holder.post;
/* 295 */           ImageView userImage = holder.userImage;
                    String avatarUrl = post.avatarUrl;
                    // Feed responses can carry the usable avatar only inside user.
                    // Prefer that value when the flattened field is empty/default.
                    if (post.user != null && !TextUtils.isEmpty(post.user.avatarUrl)
                            && (TextUtils.isEmpty(avatarUrl) || Util.isDefaultAvatarUrl(avatarUrl))) {
                        avatarUrl = post.user.avatarUrl;
                    }
                    if (avatarUrl != null && avatarUrl.startsWith("http://")) {
                        avatarUrl = "https://" + avatarUrl.substring(7);
                    }
/* 296 */           if (avatarUrl != null) {
/* 297 */               if (Util.isDefaultAvatarUrl(avatarUrl)) {
/* 298 */                   if (post.isRevined()) {
/* 299 */                       Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, this.mVineGreen);
/* 310 */                       return;
                            } else {
/* 301 */                       Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, this.mProfileColor);
/* 310 */                       return;
                            }
                        }
                    // Some reconstructed resource tables return 0 for user_image_size.
                    // Let the image cache decode at its normal size instead of asking
                    // BitmapFactory for a 0x0 bitmap.
/* 304 */               ImageKey avatarKey = new ImageKey(avatarUrl, true);
/* 306 */               setUserImage(userImage, this.mAppController.getPhotoBitmap(avatarKey));
/* 307 */               holder.avatarKey = avatarKey;
                    }
                }

/* 318 */       public void updateRevinedCount(long postId, boolean revined) {
/* 319 */           ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
/* 322 */               GenericFeedViewHolder holder = ref.get();
/* 323 */               if (holder == null) {
/* 324 */                   toRemove.add(ref);
                        } else if (holder.post != null && holder.post.postId == postId) {
                            // The callback may be delivered more than once while the
                            // feed is refreshing.  Change the aggregate count only
                            // when the post actually changes state.
                            boolean wasRevined = holder.post.isRevined();
                            if (revined && !wasRevined) {
                                holder.post.revinersCount++;
                            } else if (!revined && wasRevined && holder.post.revinersCount > 0) {
                                holder.post.revinersCount--;
                            }
                            holder.post.setFlagRevined(revined);
                            holder.share.setColor(this.mProfileColor);
                            holder.share.setSelected(revined);
                            holder.share.invalidate();
/* 337 */                   setStyledCompactLikesComments(holder);
                        }
                    }
                    for (WeakReference<T> h : toRemove) {
/* 342 */               this.mViewHolders.remove(h);
                    }
                }

/* 348 */       protected void setStyledComments(GenericFeedViewHolder holder, VinePagedData<VineComment> commentData) {
                    TextView thisComment;
/* 349 */           Resources res = this.mContext.getResources();
/* 351 */           holder.comments1.setVisibility(8);
/* 352 */           holder.comments2.setVisibility(8);
/* 353 */           holder.comments3.setVisibility(8);
/* 355 */           View postCommentsDivider = holder.postCommentsDivider;
/* 356 */           if (commentData != null && !commentData.isEmpty()) {
/* 357 */               postCommentsDivider.setVisibility(0);
                    } else {
/* 359 */               postCommentsDivider.setVisibility(8);
                    }
/* 362 */           if (commentData != null && commentData.items != null && !commentData.items.isEmpty() && holder != null) {
/* 366 */               if (commentData.count > 3) {
/* 367 */                   String cta = res.getString(C0257R.string.timeline_comments_many, Integer.valueOf(commentData.count));
/* 368 */                   SpannableStringBuilder ssb = new SpannableStringBuilder();
/* 369 */                   ssb.append((CharSequence) cta);
/* 370 */                   StyledClickableSpan clickableSpan = new StyledClickableSpan(3, new SpanCommentObjectTag(holder.post.postId, holder.post.getVineRepostRepostId(), holder.post.userId), this.mSpanClickListener);
/* 373 */                   clickableSpan.setColor(this.mVineGray);
/* 374 */                   Util.safeSetSpan(ssb, clickableSpan, 0, ssb.length(), 33);
/* 376 */                   TextView viewAllComments = holder.viewAllComments;
/* 377 */                   viewAllComments.setVisibility(0);
/* 378 */                   viewAllComments.setMovementMethod(LinkMovementMethod.getInstance());
/* 379 */                   viewAllComments.setText(ssb);
                        } else {
/* 381 */                   holder.viewAllComments.setVisibility(8);
                        }
/* 384 */               ArrayList<VineComment> arrayList = commentData.items;
/* 385 */               int size = arrayList.size();
/* 386 */               int count = 0;
/* 389 */               int index = Math.max(size - 3, 0);
                        while (index < size) {
/* 390 */                   VineComment comment = (VineComment) arrayList.get(index);
/* 391 */                   if (count < 3) {
                                switch (count) {
                                    case 0:
/* 398 */                               thisComment = holder.comments1;
                                        break;
                                    case 1:
/* 402 */                               thisComment = holder.comments2;
                                        break;
                                    default:
/* 407 */                               thisComment = holder.comments3;
                                        break;
                                }
/* 412 */                       thisComment.setVisibility(0);
/* 414 */                       if (comment.commentSb == null) {
/* 415 */                           comment.commentSb = new SpannableStringBuilder();
/* 416 */                           SpannableStringBuilder thisCommentSb = comment.commentSb;
                                    try {
/* 420 */                               String relativeTimeString = Util.getRelativeTimeString(this.mContext, comment.timestamp, false);
/* 422 */                               thisCommentSb.append((CharSequence) comment.username).append(SPACE).append((CharSequence) comment.comment).append(SPACE).append((CharSequence) relativeTimeString);
/* 432 */                               int usernameEnd = comment.username.length();
/* 433 */                               int timestampStart = comment.comment.length() + usernameEnd + 2;
/* 434 */                               int timestampEnd = thisCommentSb.length();
/* 436 */                               StyledClickableSpan span = new StyledClickableSpan(1, Long.valueOf(comment.userId), this.mSpanClickListener);
/* 438 */                               TypefacesSpan boldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 3));
/* 440 */                               ColorStateList color = new ColorStateList(new int[][]{new int[0]}, new int[]{res.getColor(C0257R.color.black_thirty_five_percent)});
/* 442 */                               TextAppearanceSpan timestampSpan = new TextAppearanceSpan(null, 0, res.getDimensionPixelSize(C0257R.dimen.font_size_middle), color, null);
/* 445 */                               span.setColor(this.mProfileColor);
/* 446 */                               Util.safeSetSpan(thisCommentSb, span, 0, usernameEnd, 33);
/* 447 */                               Util.safeSetSpan(thisCommentSb, boldSpan, 0, usernameEnd, 33);
/* 448 */                               Util.safeSetSpan(thisCommentSb, timestampSpan, timestampStart, timestampEnd, 33);
/* 450 */                               if (comment.transientEntities == null && comment.entities != null) {
/* 451 */                                   comment.transientEntities = new ArrayList<>();
                                            for (VineEntity entity : comment.entities) {
/* 453 */                                       comment.transientEntities.add(entity.duplicate());
                                            }
                                        }
/* 458 */                               ArrayList<VineEntity> entities = comment.transientEntities;
/* 460 */                               if (entities != null) {
                                            try {
/* 461 */                                       Util.adjustEntities(entities, thisCommentSb, usernameEnd + 1, false);
                                                for (VineEntity e : entities) {
/* 463 */                                           if (e.isUserType()) {
/* 464 */                                               StyledClickableSpan click = new StyledClickableSpan(1, Long.valueOf(e.f96id), this.mSpanClickListener);
/* 466 */                                               click.setColor(this.mProfileColor);
/* 467 */                                               Util.safeSetSpan(thisCommentSb, click, e.start, e.end, 33);
                                                    } else if (e.isTagType()) {
/* 469 */                                               StyledClickableSpan click2 = new StyledClickableSpan(4, e.title, this.mSpanClickListener);
/* 471 */                                               click2.setColor(this.mProfileColor);
/* 472 */                                               Util.safeSetSpan(thisCommentSb, click2, e.start, e.end, 33);
                                                    }
                                                }
                                            } catch (Exception e2) {
/* 477 */                                       if (BuildUtil.isLogsOn()) {
/* 478 */                                           throw new RuntimeException(e2);
                                                }
/* 480 */                                       CrashUtil.logException(e2, "Failed to adjust entities on " + comment.commentId, new Object[0]);
                                            }
                                        }
/* 484 */                               thisComment.setMovementMethod(LinkMovementMethod.getInstance());
/* 485 */                               thisComment.setText(comment.commentSb);
                                    } catch (NullPointerException e3) {
/* 426 */                               CrashUtil.logException(e3, "Comment attribute caused NPE: {} {} {}", Long.valueOf(comment.commentId), comment.username, comment.comment);
                                    }
                                } else {
/* 484 */                           thisComment.setMovementMethod(LinkMovementMethod.getInstance());
/* 485 */                           thisComment.setText(comment.commentSb);
                                }
/* 389 */                       index++;
                                count++;
                            } else {
/* 487 */                       return;
                            }
                        }
                    }
                }

/* 489 */       protected void setStyledCompactLikesComments(GenericFeedViewHolder h) {
/* 490 */           setStyledCompactLikesComments(h, true);
                }

/* 493 */       protected void setStyledCompactLikesComments(GenericFeedViewHolder h, boolean countsUpdated) {
/* 494 */           if (h.post.styledCompactLikesComments == null || countsUpdated) {
/* 495 */               int likeCount = h.post.likesCount;
/* 496 */               int commentCount = h.post.commentsCount;
/* 497 */               int revinersCount = h.post.revinersCount;
/* 498 */               boolean showCommentsText = h.post.comments != null;
/* 500 */               Resources res = this.mContext.getResources();
/* 502 */               SpannableStringBuilder sb = new SpannableStringBuilder();
/* 505 */               if (likeCount > 0) {
/* 506 */                   String numberLikesString = Util.numberFormat(res, likeCount);
/* 507 */                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.timeline_compact_likes, likeCount, numberLikesString));
/* 509 */                   StyledClickableSpan clickableSpan = new StyledClickableSpan(2, Long.valueOf(h.post.postId), this.mSpanClickListener);
/* 511 */                   clickableSpan.setColor(this.mVineGray);
/* 513 */                   int end = sb.length();
/* 514 */                   Util.safeSetSpan(sb, clickableSpan, 0, end, 33);
                        }
/* 518 */               if (commentCount > 0 && showCommentsText) {
/* 519 */                   if (sb.length() > 0) {
/* 520 */                       sb.append(SPACE).append(INTERPUNCT).append(SPACE);
                            }
/* 523 */                   String numberCommentsString = Util.numberFormat(res, commentCount);
/* 524 */                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.timeline_compact_comments, commentCount, numberCommentsString));
/* 526 */                   StyledClickableSpan clickableSpan2 = new StyledClickableSpan(3, new SpanCommentObjectTag(h.post.postId, h.post.getVineRepostRepostId(), h.post.userId), this.mSpanClickListener);
/* 529 */                   clickableSpan2.setColor(this.mVineGray);
/* 530 */                   int interpunctIndex = sb.toString().indexOf(183);
/* 531 */                   int start = interpunctIndex < 0 ? 0 : interpunctIndex + 2;
/* 532 */                   int end2 = sb.length();
/* 533 */                   Util.safeSetSpan(sb, clickableSpan2, start, end2, 33);
                        }
/* 537 */               if (revinersCount > 0) {
/* 538 */                   if (sb.length() > 0) {
/* 539 */                       sb.append(SPACE).append(INTERPUNCT).append(SPACE);
                            }
/* 541 */                   String numRevinersString = Util.numberFormat(res, revinersCount);
/* 542 */                   sb.append((CharSequence) res.getQuantityString(C0257R.plurals.revine_count, revinersCount, numRevinersString));
/* 544 */                   StyledClickableSpan clickableSpan3 = new StyledClickableSpan(7, new SpanCommentObjectTag(h.post.postId, h.post.getVineRepostRepostId(), h.post.userId), this.mSpanClickListener);
/* 547 */                   clickableSpan3.setColor(this.mVineGray);
/* 548 */                   int interpunctIndex2 = sb.toString().lastIndexOf(183);
/* 549 */                   int start2 = interpunctIndex2 < 0 ? 0 : interpunctIndex2 + 2;
/* 550 */                   int end3 = sb.length();
/* 551 */                   Util.safeSetSpan(sb, clickableSpan3, start2, end3, 33);
                        }
/* 554 */               if (sb.length() > 0) {
/* 555 */                   TypefacesSpan boldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 2));
/* 557 */                   Util.safeSetSpan(sb, boldSpan, 0, sb.length(), 33);
                        }
/* 559 */               h.post.styledCompactLikesComments = sb;
                    }
/* 562 */           if (h.post.styledCompactLikesComments.length() > 0) {
/* 563 */               h.compactLikesCommentsRevinesCounts.setMovementMethod(LinkMovementMethod.getInstance());
/* 564 */               h.compactLikesCommentsRevinesCounts.setText(h.post.styledCompactLikesComments);
                    } else {
/* 566 */               h.compactLikesCommentsRevinesCounts.setText(this.mContext.getString(C0257R.string.first_to_like));
                    }
/* 569 */           h.compactLikesCommentsRevinesCounts.setVisibility(0);
                }

/* 573 */       protected void onVideoImageObtained(T holder, boolean b) {
                }

                @Override // android.widget.Adapter
/* 577 */       public long getItemId(int position) {
/* 578 */           return position;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 582 */       public boolean hasStableIds() {
/* 583 */           return false;
                }

/* 586 */       protected boolean setUserImage(ImageView imageView, Bitmap bitmap) {
/* 587 */           imageView.setColorFilter((ColorFilter) null);
/* 588 */           if (bitmap == null) {
/* 589 */               imageView.setImageResource(C0257R.drawable.circle_shape_light);
                        return false;
                    }
/* 592 */           imageView.setBackgroundColor(0);
/* 593 */           imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
/* 594 */           return true;
                }

/* 598 */       protected boolean setImage(View imageView, Bitmap bitmap, String url) {
/* 599 */           if (bitmap == null) {
/* 600 */               imageView.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
/* 601 */               return false;
                    }
/* 603 */           ViewUtil.setBackground(imageView, new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
/* 604 */           return true;
                }

/* 608 */       public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
                    UrlImage image3;
/* 609 */           ArrayList<WeakReference<T>> toRemove = new ArrayList<>();
                    for (WeakReference<T> ref : this.mViewHolders) {
/* 611 */               T holder = ref.get();
/* 612 */               if (holder == null) {
/* 613 */                   toRemove.add(ref);
                        } else {
/* 615 */                   if (holder.avatarKey != null && (image3 = images.get(holder.avatarKey)) != null && image3.isValid()) {
/* 618 */                       setUserImage(holder.userImage, image3.bitmap);
/* 619 */                       this.mLogger.m487d("found for avatar.");
                            }
/* 622 */                   if (holder.videoImageKey != null && (image2 = images.get(holder.videoImageKey)) != null && image2.isValid()) {
/* 625 */                       this.mLogger.m488d("found for video: {}", image2.url);
/* 626 */                       onVideoImageObtained(holder, setImage(holder.getViewForVideoImage(), image2.bitmap, image2.url));
                            }
/* 632 */                   if (holder.bylineIconImageKey != null && (image = images.get(holder.bylineIconImageKey)) != null && image.isValid()) {
/* 635 */                       this.mLogger.m488d("got icon for {}", image.url);
/* 636 */                       setBylineIcon(image.bitmap, holder);
                            }
                        }
                    }
/* 641 */           this.mViewHolders.removeAll(toRemove);
                }

/* 644 */       protected void setBylineIcon(Bitmap bmp, GenericFeedViewHolder h) {
/* 645 */           if (bmp == null) {
/* 646 */               h.bylineIcon.setImageDrawable(null);
                    } else {
/* 648 */               RecyclableBitmapDrawable draw = new RecyclableBitmapDrawable(this.mContext.getResources(), bmp);
/* 649 */               h.bylineIcon.setImageDrawable(draw);
                    }
                }

/* 655 */       protected synchronized boolean bindVideoImage(T h) {
                    boolean z = false;
                    synchronized (this) {
                        try {
                            VinePost post = h.post;
/* 657 */                   int i = h.position;
/* 659 */                   if (post == null) {
                                z = true;
                            } else {
/* 660 */                       h.hasVideoImage = post.thumbnailUrl != null;
/* 662 */                       boolean isSensitiveImage = post.isExplicit() && !this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(post.postId));
/* 664 */                       if (isSensitiveImage) {
/* 665 */                           h.sensitiveTextContainer.setVisibility(0);
/* 666 */                           createSensitiveLisenter(h);
                                } else {
/* 669 */                           createInsensitiveListener(h);
/* 670 */                           if (h.hasVideoImage) {
/* 671 */                               ImageKey videoImageKey = new ImageKey(post.thumbnailUrl);
/* 672 */                               h.videoImageKey = videoImageKey;
/* 673 */                               h.isVideoImageLoaded = setImage(h.getViewForVideoImage(), this.mAppController.getPhotoBitmap(videoImageKey), videoImageKey.url);
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

/* 684 */       public boolean isPlaying() {
/* 685 */           return false;
                }

/* 688 */       public void onTrimMemory(int level) {
/* 689 */           this.mLogger.m496e("onTrimMemory called.");
                }

/* 692 */       public void onLowMemory() {
/* 693 */           this.mLogger.m496e("onLowMemory called.");
                }

/* 699 */       public class SpanCommentObjectTag {
                    public final long authorId;
                    public final long postId;
                    public final long repostId;

/* 700 */           public SpanCommentObjectTag(long postId, long repostId, long authorId) {
/* 702 */               this.postId = postId;
/* 703 */               this.repostId = repostId;
/* 704 */               this.authorId = authorId;
                    }
                }

/* 708 */       protected void onInvalidRequestKey(VinePost post) {
/* 709 */           if (post.getUrls() != null) {
                        for (VineVideoUrlTier tier : post.getUrls()) {
/* 711 */                   if (tier != null) {
/* 712 */                       CrashUtil.log("postId " + post.postId + " tier rate " + tier.rate + " format " + tier.format + " url " + tier.url);
                            }
                        }
                    }
/* 716 */           CrashUtil.logException(new IllegalArgumentException(), "Invalid request key for post " + post.postId, new Object[0]);
                }

/* 719 */       protected VideoKey getRequestKey(VinePost post, boolean forceLowKey) {
/* 720 */           if (this.mNormalVideoPlayable == null) {
                        this.mNormalVideoPlayable = Boolean.valueOf(SystemUtil.isNormalVideoPlayable(this.mContext) != SystemUtil.PrefBooleanState.FALSE);
                    }
/* 724 */           int avgSpeed = VideoCache.getCurrentAverageSpeed();
/* 725 */           this.mLogger.m489d("Getting HQ Video: speed {} && can play normal vid {}", Integer.valueOf(avgSpeed), this.mNormalVideoPlayable);
/* 727 */           return Util.getVideoRequestKey(post, forceLowKey, avgSpeed);
                }

/* 736 */       public View getViewAtPosition(int wantedPosition) {
/* 737 */           ListView listView = this.mListView;
/* 738 */           int offset = 0;
/* 739 */           if (this.mViewOffsetResolver != null) {
/* 740 */               offset = this.mViewOffsetResolver.getOffset(this);
                    }
/* 742 */           int headerViewCount = listView.getHeaderViewsCount();
/* 743 */           int firstPosition = (listView.getFirstVisiblePosition() - headerViewCount) - offset;
/* 744 */           int wantedChild = wantedPosition - firstPosition;
/* 745 */           if (wantedChild < 0 || wantedChild >= listView.getChildCount()) {
/* 746 */               if (SLog.sLogsOn) {
/* 747 */                   this.mLogger.m522w("Unable to get view for desired position, because it's not being displayed on screen: {} {} {} {}.", new Object[]{Integer.valueOf(wantedPosition), Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
                        }
/* 749 */               return null;
                    }
/* 751 */           if (SLog.sLogsOn) {
/* 752 */               this.mLogger.m494d("Get view for desired position: {} {} {} {}.", new Object[]{Integer.valueOf(wantedPosition), Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
                    }
/* 756 */           return listView.getChildAt(wantedChild);
                }
            }
