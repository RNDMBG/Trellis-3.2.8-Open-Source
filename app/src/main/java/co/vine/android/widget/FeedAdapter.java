            package co.vine.android.widget;

            import android.app.Activity;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.support.annotation.Nullable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.text.style.ForegroundColorSpan;
            import android.util.AttributeSet;
            import android.util.SparseArray;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.AbsListView;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import co.vine.android.BaseTimelineFragment;
            import co.vine.android.C0257R;
            import co.vine.android.CommentsActivity;
            import co.vine.android.ProfileActivity;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.VineByline;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VinePost;
            import co.vine.android.cache.LikeCache;
            import co.vine.android.cache.RevineCache;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.Session;
            import co.vine.android.embed.player.PlayerState;
            import co.vine.android.embed.player.VinePlayer;
            import co.vine.android.player.HasVideoPlayerAdapter;
            import co.vine.android.player.OnListVideoClickListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.VideoSensitiveImageClickListener;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.LinkDispatcher;
            import co.vine.android.util.LongSparseArray;
            import co.vine.android.util.LoopManager;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.AnalyticsManager;
            import co.vine.android.util.analytics.BehaviorManager;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.views.MultiPhaseBitmapView;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 82 */    public class FeedAdapter extends GenericVideoAdapter<FeedViewHolder> implements HasVideoPlayerAdapter, PinnedHeaderAdapter {
                private static final int DOUBLE_TAP_INTERVAL = 450;
                private static final int POSITION_CALCULATION_CURRENT = -2;
                private static final int POSITION_CALCULATION_INVALID = -1;
                private int currentPlayerCount;
                private volatile int mCurrentPlaying;
                private int mDividerScrollAwayPadding;
                private final Bitmap mDoubleTapBitmap;
                private final boolean mDoubleTapToLikeOnPause;
                private final Bitmap mDoubleTapWink_1;
                private final Bitmap mDoubleTapWink_2;
                private final Bitmap mDoubleTapWink_3;
                private final Drawable mFeedLocationPinDrawable;
                private boolean mFirstPlayed;
                private final Handler mHandler;
                private boolean mHasFocus;
                private SdkVideoView mLastPlayer;
                private Handler mLoopCountHandler;
                private HandlerThread mLoopCountThread;
                private boolean mMuted;
                private boolean mNeedReleaseOtherPlayers;
                private final LongSparseArray<String> mPaths;
                private int mPinnedHeaderHeight;
                private int mPinnedHeaderListViewChildIndex;
                private int mPinnedHeaderPosition;
                private View mPinnedHeaderView;
                private final Runnable mPlayCurrentPositionRunnable;
                private final SparseArray<SdkVideoView> mPlayers;
                private SdkVideoView mPlayingPlayer;
                private final int mPostHeaderPaddingLarge;
                private final int mPostHeaderPaddingSmall;
                private final ArrayList<VinePost> mPosts;
                private long mShouldBePlayingPostId;
                private final HashMap<VideoKey, Long> mUrlReverse;
                private AttributeSet mVideoAttr;
                private final LongSparseArray<VideoKey> mVideoKeys;
                private boolean mWasResumed;
                private int maxPlayer;

/* 83 */        static /* synthetic */ int access$100(FeedAdapter x0) {
/* 84 */            return x0.mCurrentPlaying;
                }

/* 83 */        static /* synthetic */ SdkVideoView access$1000(FeedAdapter x0) {
/* 84 */            return x0.mLastPlayer;
                }

/* 83 */        static /* synthetic */ int access$1100(FeedAdapter x0) {
/* 84 */            return x0.calculateCurrentPosition();
                }

/* 83 */        static /* synthetic */ boolean access$1200(FeedAdapter x0, int x1) {
/* 84 */            return x0.getExplicit(x1);
                }

/* 83 */        static /* synthetic */ boolean access$1300(FeedAdapter x0) {
/* 84 */            return x0.hasPlayerPlaying();
                }

/* 83 */        static /* synthetic */ boolean access$1400(FeedAdapter x0) {
/* 84 */            return x0.mDoubleTapToLikeOnPause;
                }

/* 83 */        static /* synthetic */ void access$200(FeedAdapter x0, VideoViewInterface x1, long x2) {
/* 84 */            x0.countedMediaPlayerStart(x1, x2);
                }

/* 83 */        static /* synthetic */ int access$302(FeedAdapter x0, int x1) {
/* 84 */            x0.maxPlayer = x1;
                    return x1;
                }

/* 83 */        static /* synthetic */ int access$400(FeedAdapter x0) {
/* 84 */            return x0.currentPlayerCount;
                }

/* 83 */        static /* synthetic */ boolean access$502(FeedAdapter x0, boolean x1) {
/* 84 */            x0.mNeedReleaseOtherPlayers = x1;
                    return x1;
                }

/* 83 */        static /* synthetic */ LongSparseArray access$600(FeedAdapter x0) {
/* 84 */            return x0.mPaths;
                }

/* 83 */        static /* synthetic */ LongSparseArray access$700(FeedAdapter x0) {
/* 84 */            return x0.mVideoKeys;
                }

/* 83 */        static /* synthetic */ VinePost access$800(FeedAdapter x0, int x1) {
/* 84 */            return x0.getPost(x1);
                }

/* 83 */        static /* synthetic */ boolean access$900(FeedAdapter x0) {
/* 84 */            return x0.mHasFocus;
                }




/* 128 */       public FeedAdapter(Activity context, ListView listView, LikeCache likeCache, RevineCache revineCache, BaseTimelineFragment timelineFragment, String followEventSource, SLogger logger) {
/* 129 */           super(context, listView, likeCache, revineCache, timelineFragment, followEventSource, logger);
/* 96 */            this.mHandler = new Handler();
/* 106 */           this.mCurrentPlaying = Integer.MIN_VALUE;
/* 107 */           this.mShouldBePlayingPostId = -2147483648L;
/* 108 */           this.maxPlayer = 10;
/* 109 */           this.currentPlayerCount = 0;
/* 110 */           this.mNeedReleaseOtherPlayers = false;
/* 113 */           this.mWasResumed = false;
/* 115 */           this.mVideoAttr = null;
/* 117 */           this.mPinnedHeaderPosition = -1;
/* 118 */           this.mPinnedHeaderHeight = 0;
/* 121 */           this.mPinnedHeaderView = null;
/* 122 */           this.mDividerScrollAwayPadding = 0;
/* 123 */           this.mPinnedHeaderListViewChildIndex = 0;
/* 131 */           this.mPlayers = new SparseArray<>();
/* 132 */           this.mPosts = new ArrayList<>();
/* 133 */           this.mPaths = new LongSparseArray<>();
/* 134 */           this.mVideoKeys = new LongSparseArray<>();
/* 135 */           this.mUrlReverse = new HashMap<>();
/* 136 */           this.mLogger.m488d("Adapter constructed, accelerated? {}", Boolean.valueOf(listView.isHardwareAccelerated()));
/* 137 */           this.mPlayCurrentPositionRunnable = new PlayCurrentPositionRunnable(this, null);
/* 138 */           Resources res = context.getResources();
/* 139 */           this.mFeedLocationPinDrawable = res.getDrawable(C0257R.drawable.ic_feed_map_pin);
/* 140 */           this.mPinnedHeaderHeight = res.getDimensionPixelSize(C0257R.dimen.post_header_height);
/* 143 */           timelineFragment.addHeaderView(new LinearLayout(this.mContext), null, false);
/* 144 */           this.mLoopCountThread = new HandlerThread("loop-count-handler");
/* 145 */           this.mLoopCountThread.start();
/* 146 */           this.mLoopCountHandler = new Handler(this.mLoopCountThread.getLooper());
/* 148 */           this.mDividerScrollAwayPadding = res.getDimensionPixelSize(C0257R.dimen.divider_scroll_away_padding);
/* 149 */           this.mDoubleTapBitmap = BitmapFactory.decodeResource(res, C0257R.drawable.double_tap);
/* 150 */           this.mDoubleTapWink_1 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_1);
/* 151 */           this.mDoubleTapWink_2 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_2);
/* 152 */           this.mDoubleTapWink_3 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_3);
/* 153 */           this.mDoubleTapToLikeOnPause = ClientFlagsHelper.doubleTapToLikeOnPause(this.mContext);
/* 155 */           this.mPostHeaderPaddingSmall = res.getDimensionPixelOffset(C0257R.dimen.post_header_padding_small);
/* 156 */           this.mPostHeaderPaddingLarge = res.getDimensionPixelOffset(C0257R.dimen.post_header_padding_large);
                }

/* 159 */       private int calculateCurrentPosition() {
                    int pos;
/* 160 */           int currentFirst = this.mListView.getFirstVisiblePosition();
/* 161 */           int offset = 0;
/* 162 */           if (this.mViewOffsetResolver != null) {
/* 163 */               offset = this.mViewOffsetResolver.getOffset(this);
                    }
/* 165 */           int currentFirst2 = currentFirst - offset;
/* 167 */           HashSet<View> headers = this.mTimelineFragment.getHeaderViewParents();
/* 168 */           if (headers.size() > 0) {
/* 169 */               int missingHeaderViewCount = 0;
                        for (View view : headers) {
/* 171 */                   if (!view.isShown()) {
/* 172 */                       missingHeaderViewCount++;
                            }
                        }
/* 175 */               currentFirst2 -= missingHeaderViewCount;
/* 176 */               if (CommonUtil.DEBUG_VERBOSE) {
/* 177 */                   this.mLogger.m489d("Try to get current First: {}, missing header count: {}", Integer.valueOf(currentFirst2), Integer.valueOf(missingHeaderViewCount));
                        }
                    }
/* 181 */           View currentView = getViewAtPosition(currentFirst2);
/* 182 */           if (currentView == null) {
/* 183 */               return -1;
                    }
/* 186 */           int currentlyPlayingOffset = 0;
/* 187 */           if (getLastPlayer() != null) {
/* 188 */               currentlyPlayingOffset = getLastPlayer().getHeight() / 2;
                    }
/* 190 */           float bottom = currentView.getBottom() + currentlyPlayingOffset;
/* 191 */           float height = currentView.getHeight() + currentlyPlayingOffset;
                    if (height == 0.0f) {
                        height = 1.0f;
                    }
                    float ratio = bottom / height;
                    if (ratio < 0.5d || (currentFirst2 == -1 && currentFirst2 < getCount() - 1 && currentFirst2 + 1 != this.mCurrentPlaying)) {
/* 198 */               pos = currentFirst2 + 1;
                    } else if (currentFirst2 != this.mCurrentPlaying) {
/* 200 */               pos = currentFirst2;
                    } else {
/* 202 */               pos = currentFirst2 == this.mCurrentPlaying ? -2 : -1;
                    }
/* 204 */           if (CommonUtil.DEBUG_VERBOSE) {
/* 205 */               this.mLogger.m490d("Current First: {}, ratio: {}, currently playing: {}", Integer.valueOf(pos), Float.valueOf(ratio), Integer.valueOf(this.mCurrentPlaying));
/* 207 */               return pos;
                    }
/* 207 */           return pos;
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 210 */       protected void resetStates(boolean hasDataSetChanged) {
/* 211 */           boolean currentPlayerChanged = true;
/* 212 */           if (this.mLastPlayer != null && this.mCurrentPlaying >= 0) {
/* 213 */               Object tag = this.mLastPlayer.getTag();
/* 214 */               long postId = -1;
/* 215 */               if (tag instanceof Long) {
/* 216 */                   postId = ((Long) tag).longValue();
                        }
/* 218 */               if (this.mPosts.size() > this.mCurrentPlaying && this.mPosts.get(this.mCurrentPlaying).postId == postId) {
/* 219 */                   currentPlayerChanged = false;
                        }
                    }
/* 222 */           if (currentPlayerChanged) {
/* 223 */               stopCurrentPlayer();
/* 224 */               this.mShouldBePlayingPostId = -1L;
/* 225 */               this.currentPlayerCount = 0;
/* 226 */               this.mFirstPlayed = false;
                    }
/* 228 */           if (hasDataSetChanged) {
/* 229 */               this.mLikeCache.clear();
/* 230 */               this.mRevineCache.clear();
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 234 */       public synchronized void playFile(int position, boolean isVideoLoaded) {
                    boolean newView = true;
                    synchronized (this) {
                        if (position >= 0) {
                            try {
/* 238 */                       this.mLogger.m506i("Playing item: {}", Integer.valueOf(position));
/* 240 */                       View currentView = getViewAtPosition(position);
/* 241 */                       if (currentView == null) {
/* 242 */                           this.mLogger.m506i("Item is not visible: {}", Integer.valueOf(position));
                                } else {
/* 246 */                           FeedViewHolder tag = (FeedViewHolder) currentView.getTag();
/* 247 */                           if (tag != null) {
/* 251 */                               SdkVideoView currentVideoView = tag.videoView;
/* 253 */                               if (currentVideoView != this.mLastPlayer) {
/* 254 */                                   this.mLogger.m505i("Pause previous player.");
/* 255 */                                   pauseCurrentPlayer();
                                        }
/* 258 */                               if (tag.post == null) {
/* 259 */                                   this.mLogger.m505i("Post was cleared.");
                                        } else {
/* 263 */                                   String newPath = this.mPaths.get(tag.post.postId);
/* 265 */                                   if (newPath == null) {
/* 266 */                                       this.mLogger.m505i("Path was cleared.");
                                            } else {
/* 270 */                                       if (!currentVideoView.isPathPlaying(newPath)) {
/* 271 */                                           if (SystemUtil.isSinglePlayerEnabled(this.mContext)) {
                                                        if (SdkVideoView.getCurrentUri() != null) {
                                                            newView = false;
                                                        }
                                                    } else if (currentVideoView.getPath() != null) {
                                                        newView = false;
                                                    }
/* 273 */                                           if (!newView) {
/* 274 */                                               suspendPlayer(tag.videoView);
/* 275 */                                               Drawable bg = tag.videoImage.getBackground();
/* 276 */                                               ViewUtil.setBackground(tag.videoImageTop, bg);
/* 277 */                                               tag.videoImageTop.clearAnimation();
/* 278 */                                               tag.videoImageTop.setVisibility(0);
/* 279 */                                               tag.videoView.setVisibility(8);
/* 280 */                                               tag.videoView.setSurfaceUpdatedListener(new C05501(tag));
                                                    }
/* 294 */                                           currentVideoView = tag.videoView;
/* 295 */                                           currentVideoView.setMute(this.mMuted);
/* 296 */                                           if (this.mNeedReleaseOtherPlayers && this.currentPlayerCount >= this.maxPlayer) {
/* 297 */                                               releaseOtherPlayers(tag.videoView);
                                                    }
/* 299 */                                           this.mLogger.m505i("Start playing by set path.");
/* 300 */                                           currentVideoView.setTag(Long.valueOf(tag.post.postId));
/* 301 */                                           CrashUtil.log("Setting video path in feed for post {}", Long.valueOf(tag.post.postId));
/* 302 */                                           currentVideoView.setVideoPath(newPath);
/* 303 */                                           currentVideoView.setCacheKey(this.mVideoKeys.get(tag.post.postId));
/* 304 */                                           if (!currentVideoView.isSurfaceReady() && currentVideoView.getVisibility() != 0) {
/* 305 */                                               currentVideoView.setVisibility(0);
                                                    }
/* 307 */                                           this.currentPlayerCount++;
                                                } else {
/* 309 */                                           if (!tag.videoView.isAvailable()) {
/* 310 */                                               tag.videoView.setVisibility(0);
                                                    }
/* 312 */                                           currentVideoView.setMute(this.mMuted);
/* 313 */                                           if (currentVideoView.isInPlaybackState()) {
/* 314 */                                               this.mLogger.m505i("Start playing by calling start.");
/* 315 */                                               resumePlayer(currentVideoView);
                                                    } else {
/* 317 */                                               this.mLogger.m505i("Start playing by set path.");
/* 318 */                                               currentVideoView.setTag(Long.valueOf(tag.post.postId));
/* 319 */                                               CrashUtil.log("Setting video path in feed for post {}", Long.valueOf(tag.post.postId));
/* 320 */                                               currentVideoView.setVideoPath(newPath);
                                                    }
                                                }
/* 323 */                                       currentVideoView.setPlayingPosition(position);
/* 324 */                                       tag.listener.setPosition(position);
/* 325 */                                       if (tag.imageListener != null) {
/* 326 */                                           tag.imageListener.setPosition(position);
/* 327 */                                           tag.imageListener.setPostId(tag.post.postId);
                                                }
/* 329 */                                       SLog.m465i("Make current player {}", Integer.valueOf(position));
/* 330 */                                       this.mCurrentPlaying = position;
/* 331 */                                       this.mLastPlayer = currentVideoView;
                                            }
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$1 */
/* 278 */       class C05501 implements SdkVideoView.SurfaceUpdatedListener {
                    private int mFrameCount = 0;
                    final /* synthetic */ FeedViewHolder val$tag;

/* 279 */           C05501(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override // co.vine.android.player.SdkVideoView.SurfaceUpdatedListener
/* 284 */           public void onSurfaceUpdated() {
/* 285 */               if (this.mFrameCount == 1) {
/* 286 */                   this.val$tag.videoImageTop.setVisibility(8);
/* 287 */                   this.val$tag.videoView.setSurfaceUpdatedListener(null);
/* 288 */                   this.val$tag.videoView.setVisibility(0);
                        }
/* 290 */               this.mFrameCount++;
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 335 */       public void pausePlayer(SdkVideoView player) {
/* 336 */           if (player != null) {
                        try {
/* 338 */                   player.pause();
                        } catch (IllegalStateException e) {
                        }
/* 343 */               onPlayerStoppedPlaying(player);
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 348 */       public void resumePlayer(SdkVideoView player) {
/* 349 */           player.resume();
/* 350 */           if (this.mPlayingPlayer != player) {
/* 351 */               if (this.mPlayingPlayer != null) {
/* 352 */                   SLog.m477w("There's already someone playing, pause.");
/* 353 */                   pausePlayer(this.mPlayingPlayer);
                        }
/* 355 */               this.mPlayingPlayer = player;
                    }
                }

/* 359 */       private void onPlayerStoppedPlaying(SdkVideoView videoView) {
/* 360 */           if (videoView == this.mPlayingPlayer) {
/* 361 */               this.mPlayingPlayer = null;
                    }
                }

/* 365 */       private void suspendPlayer(SdkVideoView player) {
/* 366 */           player.suspend();
/* 367 */           onPlayerStoppedPlaying(player);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 370 */       public synchronized void getVideoPathAndPlayForPosition(int requestedPosition, boolean forceLowKey) {
                    try {
                        this.mLogger.m488d("play task executing with position {} ", Integer.valueOf(requestedPosition));
/* 372 */               if (this.mHasFocus) {
/* 373 */                   VinePost post = getPost(requestedPosition);
/* 374 */                   String postVidePath = null;
/* 375 */                   if (post != null) {
/* 376 */                       String postVidePath2 = this.mPaths.get(post.postId);
                                postVidePath = postVidePath2;
                            }
/* 378 */                   if (postVidePath != null) {
/* 379 */                       if (preFetch(requestedPosition + 1)) {
/* 380 */                           preFetch(requestedPosition + 2);
                                }
/* 382 */                       this.mLogger.m488d("{} is in cache or it is already pre-fetched.", Integer.valueOf(requestedPosition));
/* 383 */                       this.mLogger.m488d("playing file at position {}", Integer.valueOf(requestedPosition));
/* 384 */                       playFile(requestedPosition, true);
                            } else {
/* 386 */                       this.mLogger.m488d("Real fetch for {}.", Integer.valueOf(requestedPosition));
/* 387 */                       if (post != null) {
/* 388 */                           VideoKey url = getRequestKey(post, forceLowKey);
/* 389 */                           if (url == null) {
/* 390 */                               onInvalidRequestKey(post);
                                    } else {
/* 393 */                               String path = this.mAppController.getVideoFilePath(url);
/* 394 */                               if (preFetch(requestedPosition + 1)) {
/* 395 */                                   preFetch(requestedPosition + 2);
                                        }
/* 397 */                               if (path != null) {
/* 398 */                                   this.mPaths.put(post.postId, path);
/* 399 */                                   this.mVideoKeys.put(post.postId, url);
/* 400 */                                   this.mLogger.m488d("playing file at position {}", Integer.valueOf(requestedPosition));
/* 401 */                                   playFile(requestedPosition, true);
                                        } else {
/* 403 */                                   this.mShouldBePlayingPostId = post.postId;
/* 404 */                                   this.mUrlReverse.put(url, Long.valueOf(post.postId));
/* 405 */                                   this.mLogger.m488d("{} is not in app cache yet.", Integer.valueOf(requestedPosition));
/* 406 */                                   pauseCurrentPlayer();
/* 407 */                                   View view = getViewAtPosition(requestedPosition);
/* 408 */                                   if (view != null) {
/* 409 */                                       Object tag = view.getTag();
/* 410 */                                       if (tag instanceof FeedViewHolder) {
/* 411 */                                           ((FeedViewHolder) tag).videoLoadImage.setVisibility(0);
                                                } else {
/* 413 */                                           CrashUtil.logException(new IllegalStateException("Somehow tag was " + tag + ", view class is '" + view.getClass().getName() + "'."));
                                                }
                                            }
                                        }
                                    }
                                } else {
/* 418 */                           this.mLogger.m488d("mPosts is {} or the requested position has no post object in it yet, return -1.", this.mPosts);
                                }
                            }
                        } else {
/* 422 */                   this.mLogger.m487d("not playing because not focused.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Nullable
/* 427 */       private VinePost getPost(int position) {
/* 428 */           if (position < 0 || position >= this.mPosts.size()) {
                        return null;
                    }
/* 429 */           return this.mPosts.get(position);
                }

/* 434 */       private boolean preFetch(int requestedPosition) {
/* 435 */           if (requestedPosition >= this.mPosts.size()) {
                        return false;
                    }
/* 436 */           VinePost nextPost = getPost(requestedPosition);
/* 437 */           if (nextPost != null) {
/* 438 */               if (this.mPaths.get(nextPost.postId) == null) {
/* 439 */                   VideoKey nextUrl = getRequestKey(getPost(requestedPosition), false);
/* 440 */                   this.mLogger.m489d("Pre-fetch {}: {}", Integer.valueOf(requestedPosition), nextUrl);
/* 441 */                   if (nextUrl == null) {
                                return true;
                            }
/* 442 */                   if (nextPost.thumbnailUrl != null) {
/* 443 */                       ImageKey imageKey = new ImageKey(nextPost.thumbnailUrl);
/* 444 */                       this.mAppController.getPhotoBitmap(imageKey);
                            }
/* 446 */                   if (nextPost.avatarUrl != null) {
/* 447 */                       ImageKey imageKey2 = new ImageKey(nextPost.avatarUrl, true);
/* 448 */                       this.mAppController.getPhotoBitmap(imageKey2);
                            }
/* 450 */                   String nextPath = this.mAppController.getVideoFilePath(nextUrl);
/* 451 */                   if (nextPath != null) {
/* 452 */                       this.mPaths.put(nextPost.postId, nextPath);
                                return true;
                            }
/* 454 */                   this.mUrlReverse.put(nextUrl, Long.valueOf(nextPost.postId));
                            return true;
                        }
/* 458 */               this.mLogger.m488d("{} is already fetched.", Integer.valueOf(requestedPosition));
                        return true;
                    }
/* 462 */           this.mLogger.m489d("End of list, no pre-fetching: {} {}", Integer.valueOf(requestedPosition), Integer.valueOf(getCount()));
                    return false;
                }

/* 469 */       private boolean getExplicit(int position) {
/* 470 */           VinePost post = getPost(position);
                    return (post == null || !post.isExplicit() || this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(post.postId))) ? false : true;
                }

/* 474 */       private boolean hasPlayerPlaying() {
                    return (this.mLastPlayer == null || !this.mLastPlayer.isInPlaybackState() || this.mLastPlayer.isPaused()) ? false : true;
                }

/* 480 */       public void stopCurrentPlayer() {
/* 481 */           if (this.mLastPlayer != null) {
/* 482 */               suspendPlayer(this.mLastPlayer);
/* 483 */               this.mLastPlayer = null;
/* 484 */               this.mCurrentPlaying = -1;
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 488 */       public void pauseCurrentPlayer() {
/* 489 */           pausePlayer(this.mLastPlayer);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 492 */       public SdkVideoView getPlayer(int position, boolean b) {
/* 493 */           return this.mPlayers.get(position);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 496 */       public synchronized void playCurrentPosition() {
                    try {
                        playCurrentPosition(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 500 */       public synchronized void playCurrentPosition(long delay) {
                    try {
                        this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
/* 502 */               this.mHandler.postDelayed(this.mPlayCurrentPositionRunnable, delay);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 505 */       public boolean isPlaying() {
/* 506 */           return hasPlayerPlaying();
                }

/* 512 */       private static class VideoImageTopHideAnimation extends AlphaAnimation implements Animation.AnimationListener {
                    private final View mView;

/* 513 */           public VideoImageTopHideAnimation(View view) {
/* 514 */               super(1.0f, 0.0f);
/* 515 */               this.mView = view;
/* 516 */               setDuration(200L);
/* 517 */               setAnimationListener(this);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 522 */           public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 526 */           public void onAnimationEnd(Animation animation) {
/* 527 */               this.mView.setVisibility(8);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 532 */           public void onAnimationRepeat(Animation animation) {
                    }
                }

/* 536 */       private void initVideoView(int position, FeedViewHolder tag) {
/* 537 */           tag.videoLoadImage.setVisibility(8);
/* 538 */           SdkVideoView view = tag.videoView;
/* 539 */           view.setVinePlayerListener(new C05552(tag));
/* 570 */           view.setOnPreparedListener(new C05563(tag));
/* 583 */           view.setOnErrorListener(new C05574(view, tag, position));
/* 606 */           view.setOnCompletionListener(new C05585(tag));
/* 619 */           view.setSurfaceReadyListener(new C05596());
/* 627 */           this.mPlayers.put(position, view);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$2 */
/* 537 */       class C05552 implements VinePlayer.EventListener {
                    final /* synthetic */ FeedViewHolder val$tag;

/* 538 */           C05552(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 542 */           public void onError(PlayerState state, int operation, Throwable e) {
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 547 */           public void onVideoSizeChanged(int newWidth, int newHeight) {
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 552 */           public void onLoopCompleted() {
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 557 */           public void onPrepared() {
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 561 */           public void onFirstFrameRendererd() {
/* 562 */               this.val$tag.videoImageTop.startAnimation(new VideoImageTopHideAnimation(this.val$tag.videoImageTop));
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 566 */           public void onPastError(Exception error) {
/* 567 */               CrashUtil.logException(error);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$3 */
/* 568 */       class C05563 implements VideoViewInterface.OnPreparedListener {
                    final /* synthetic */ FeedViewHolder val$tag;

/* 569 */           C05563(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 572 */           public void onPrepared(VideoViewInterface view) {
/* 573 */               if (view.getPlayingPosition() == FeedAdapter.access$100(FeedAdapter.this)) {
/* 574 */                   FeedAdapter.access$200(FeedAdapter.this, view, this.val$tag.post.postId);
                        } else {
/* 576 */                   FeedAdapter.this.mLogger.m498e("This video should not be played: {}, {}.", Integer.valueOf(view.getPlayingPosition()), Integer.valueOf(FeedAdapter.access$100(FeedAdapter.this)));
                        }
/* 578 */               FeedAdapter.this.endLoadingTimeProfiling();
/* 579 */               AnalyticsManager.onVideoLoaded(view);
/* 580 */               BehaviorManager.getInstance(FeedAdapter.this.mContext).onVideoLoaded();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$4 */
/* 581 */       class C05574 implements VideoViewInterface.OnErrorListener {
                    final /* synthetic */ int val$position;
                    final /* synthetic */ FeedViewHolder val$tag;
                    final /* synthetic */ SdkVideoView val$view;

/* 582 */           C05574(SdkVideoView sdkVideoView, FeedViewHolder feedViewHolder, int i) {
                        this.val$view = sdkVideoView;
                        this.val$tag = feedViewHolder;
                        this.val$position = i;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 585 */           public boolean onError(VideoViewInterface videoView, int what, int extra) {
/* 586 */               FeedAdapter.this.mLogger.m489d("Got error, try recycling it more aggressively: {}, {}", Integer.valueOf(what), Integer.valueOf(extra));
/* 587 */               FeedAdapter.access$302(FeedAdapter.this, FeedAdapter.access$400(FeedAdapter.this) - 1);
/* 588 */               FeedAdapter.access$502(FeedAdapter.this, true);
/* 589 */               FeedAdapter.this.releaseOtherPlayers(this.val$view);
/* 590 */               if (!this.val$view.retryOpenVideo(false)) {
/* 591 */                   FeedAdapter.this.mLogger.m496e("Replay video using videoLowUrl.");
/* 592 */                   if (this.val$tag.post != null) {
/* 593 */                       FeedAdapter.access$600(FeedAdapter.this).remove(this.val$tag.post.postId);
/* 594 */                       FeedAdapter.access$700(FeedAdapter.this).remove(this.val$tag.post.postId);
/* 595 */                       this.val$view.setReportOpenError(true);
/* 596 */                       if (FeedAdapter.access$100(FeedAdapter.this) == this.val$position) {
/* 597 */                           FeedAdapter.this.stopCurrentPlayer();
                                }
/* 599 */                       FeedAdapter.this.getVideoPathAndPlayForPosition(this.val$position, true);
                            }
                        }
                        return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$5 */
/* 604 */       class C05585 implements VideoViewInterface.OnCompletionListener {
                    final /* synthetic */ FeedViewHolder val$tag;

/* 605 */           C05585(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnCompletionListener
/* 608 */           public void onCompletion(VideoViewInterface videoView) {
/* 609 */               videoView.seekTo(0);
/* 610 */               VinePost post = FeedAdapter.access$800(FeedAdapter.this, FeedAdapter.access$100(FeedAdapter.this));
/* 611 */               if (post != null) {
/* 612 */                   this.val$tag.counterView.adjustExtraCount(LoopManager.getLocalLoopCount(post.postId));
/* 613 */                   this.val$tag.counterView.invalidateCounterUI();
                        }
/* 615 */               FlurryUtils.trackVineLoopWatched();
/* 616 */               FeedAdapter.access$200(FeedAdapter.this, videoView, this.val$tag.post.postId);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$6 */
/* 617 */       class C05596 implements SdkVideoView.SurfaceReadyListener {
/* 618 */           C05596() {
                    }

                    @Override // co.vine.android.player.SdkVideoView.SurfaceReadyListener
/* 621 */           public void onSurfaceTextureAvailable(SdkVideoView videoView) {
/* 622 */               if (FeedAdapter.access$900(FeedAdapter.this) && videoView == FeedAdapter.access$1000(FeedAdapter.this)) {
/* 623 */                   videoView.startOpenVideo();
                        }
                    }
                }

/* 630 */       private void countedMediaPlayerStart(VideoViewInterface view, long postId) {
/* 631 */           if (view.getPlayingPosition() == this.mCurrentPlaying) {
/* 632 */               view.start();
/* 633 */               this.mLoopCountHandler.postDelayed(new LoopCountRunnable(this.mCurrentPlaying, postId), 500L);
                    } else {
/* 635 */               this.mLogger.m498e("This video should not be played: {}, {}.", Integer.valueOf(view.getPlayingPosition()), Integer.valueOf(this.mCurrentPlaying));
                    }
                }

/* 639 */       public void removePost(long postId) {
                    for (int i = 0; i < this.mPosts.size(); i++) {
/* 641 */               VinePost post = getPost(i);
/* 642 */               if (post != null && post.postId == postId) {
/* 644 */                   this.mPosts.remove(i);
                            break;
                        }
                    }
/* 649 */           this.mPaths.clear();
/* 650 */           this.mUrlReverse.clear();
/* 651 */           this.mPlayers.clear();
/* 652 */           notifyDataSetChanged();
/* 653 */           playCurrentPosition();
                }

                /** Removes reposted items from the currently displayed profile feed immediately. */
                public void removeReposts() {
                    boolean changed = false;
                    for (int i = this.mPosts.size() - 1; i >= 0; i--) {
                        VinePost post = this.mPosts.get(i);
                        if (post != null && (post.repost != null || post.myRepostId > 0)) {
                            this.mPosts.remove(i);
                            changed = true;
                        }
                    }
                    if (changed) {
                        this.mPaths.clear();
                        this.mUrlReverse.clear();
                        this.mPlayers.clear();
                        notifyDataSetChanged();
                        playCurrentPosition();
                    }
                }

/* 658 */       private class LoopCountRunnable implements Runnable {
                    public final int lastCurrentPlaying;
                    public final long postId;

/* 659 */           public LoopCountRunnable(int currentPlaying, long postId) {
/* 661 */               this.lastCurrentPlaying = currentPlaying;
/* 662 */               this.postId = postId;
                    }

                    @Override // java.lang.Runnable
/* 666 */           public void run() {
/* 667 */               if (FeedAdapter.access$100(FeedAdapter.this) == this.lastCurrentPlaying) {
/* 668 */                   LoopManager.get(FeedAdapter.this.mContext).increment(this.postId);
                        }
                    }
                }

/* 673 */       public synchronized void releaseOtherPlayers(SdkVideoView view) {
                    try {
                        Iterator i$ = this.mViewHolders.iterator();
                        while (i$.hasNext()) {
                            WeakReference<FeedViewHolder> key = (WeakReference) i$.next();
/* 675 */                   FeedViewHolder holder = key.get();
/* 676 */                   if (holder != null && view != holder.videoView) {
/* 677 */                       this.mLogger.m488d("Releasing player: {}", Integer.valueOf(holder.position));
/* 678 */                       suspendPlayer(holder.videoView);
/* 679 */                       this.currentPlayerCount--;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX WARN: Type inference incomplete: some casts might be missing */
/* 684 */       public View newView(ViewGroup viewGroup) {
/* 685 */           View viewInflate = this.mInflater.inflate(C0257R.layout.post_row_view, viewGroup, false);
/* 687 */           RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(C0257R.id.videoContainer);
/* 688 */           ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
/* 689 */           layoutParams.height = this.mDimen;
/* 690 */           layoutParams.width = this.mDimen;
/* 691 */           relativeLayout.setLayoutParams(layoutParams);
/* 693 */           ImageView imageView = (ImageView) viewInflate.findViewById(C0257R.id.video_image);
/* 694 */           ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
/* 695 */           layoutParams2.width = this.mDimen;
/* 696 */           layoutParams2.height = this.mDimen;
/* 697 */           imageView.setLayoutParams(layoutParams2);
/* 699 */           FeedViewHolder feedViewHolder = new FeedViewHolder(viewInflate);
/* 700 */           feedViewHolder.doubleTapView = (MultiPhaseBitmapView) viewInflate.findViewById(C0257R.id.doubleTapView);
/* 701 */           feedViewHolder.doubleTapView.setBitmaps(this.mDoubleTapBitmap, this.mDoubleTapWink_1, this.mDoubleTapWink_2, this.mDoubleTapWink_3);
/* 703 */           feedViewHolder.videoImage = imageView;
/* 704 */           feedViewHolder.videoImageTop = (ImageView) relativeLayout.findViewById(C0257R.id.video_image_top);
/* 705 */           feedViewHolder.videoImageTop.setLayoutParams(layoutParams2);
/* 706 */           feedViewHolder.videoView = (SdkVideoView) relativeLayout.findViewById(C0257R.id.videoView);
/* 708 */           VideoContaineTouchListener videoContaineTouchListener = new VideoContaineTouchListener(feedViewHolder);
/* 709 */           relativeLayout.setOnClickListener(videoContaineTouchListener);
/* 710 */           relativeLayout.setOnTouchListener(videoContaineTouchListener);
/* 711 */           relativeLayout.setSoundEffectsEnabled(false);
/* 712 */           feedViewHolder.listener = videoContaineTouchListener;
/* 714 */           viewInflate.setTag(feedViewHolder);
/* 715 */           this.mViewHolders.add(new WeakReference<FeedViewHolder>(feedViewHolder));
/* 718 */           if (Build.VERSION.SDK_INT > 15) {
/* 719 */               feedViewHolder.loopsHintButton.setOnTouchListener(new ViewOnTouchListenerC05607(feedViewHolder));
                    }
/* 738 */           return viewInflate;
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$7 */
/* 717 */       class ViewOnTouchListenerC05607 implements View.OnTouchListener {
                    final /* synthetic */ FeedViewHolder val$tag;

/* 718 */           ViewOnTouchListenerC05607(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override // android.view.View.OnTouchListener
/* 721 */           public boolean onTouch(View view, MotionEvent event) {
/* 722 */               if (event.getAction() == 1 && (FeedAdapter.this.mListView instanceof PinnedHeaderListView)) {
/* 723 */                   TooltipOverlayView tooltip = new TooltipOverlayView(FeedAdapter.this.mContext, FeedAdapter.this.mContext.getString(C0257R.string.loops_hint));
/* 725 */                   PinnedHeaderListView listView = (PinnedHeaderListView) FeedAdapter.this.mListView;
/* 726 */                   boolean touchedPinned = listView.isAbsoluteTouchInPinnedHeader(event);
/* 727 */                   int[] absoluteLoc = new int[2];
/* 728 */                   view.getLocationInWindow(absoluteLoc);
/* 729 */                   int offset = touchedPinned ? listView.getPinnedHeaderBottomAbs() : absoluteLoc[1] + view.getHeight();
/* 731 */                   tooltip.showTooltip(offset, this.val$tag.loopsHintButton);
                            return true;
                        }
/* 734 */               return false;
                    }
                }

/* 741 */       private boolean shouldShowThumbnail(int position) {
                    return (this.mCurrentPlaying == position && hasPlayerPlaying()) ? false : true;
                }

/* 745 */       private void bindView(View view, FeedViewHolder h) {
                    boolean liked;
                    boolean revined;
/* 746 */           VinePost post = h.post;
/* 747 */           int position = h.position;
/* 750 */           boolean needReplay = false;
/* 751 */           if (position == 0) {
/* 752 */               if (hasPlayerPlaying()) {
/* 753 */                   stopCurrentPlayer();
/* 754 */                   needReplay = true;
                        }
/* 757 */               this.mTimelineFragment.onTopViewBinded();
                    }
/* 760 */           if (shouldShowThumbnail(position)) {
/* 761 */               h.videoLoadImage.setVisibility(0);
/* 762 */               if (h.videoView.getVisibility() != 8) {
/* 763 */                   h.videoView.setVisibility(8);
                        }
                    }
/* 767 */           h.videoImageTop.setVisibility(8);
/* 769 */           h.videoImageKey = null;
/* 771 */           bindVideoImage(h);
/* 773 */           if (!this.mIconsSwitched) {
/* 775 */               this.mIconsSwitched = true;
                    }
/* 778 */           if (!h.post.isExplicit() || this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(h.post.postId))) {
/* 779 */               h.videoImage.setVisibility(0);
                    } else {
/* 781 */               h.videoImage.setVisibility(8);
                    }
/* 785 */           if (post.byline != null) {
/* 786 */               bindByline(h, post);
                    } else if (post.repost != null) {
/* 788 */               h.revineAndBylineContainer.setVisibility(0);
/* 789 */               h.bylineIcon.setImageDrawable(this.mRevineDrawable);
/* 790 */               long userId = post.repost.userId;
                        if (userId == this.mAppController.getActiveId()) {
/* 792 */                   h.revineAndBylineText.setText(this.mContext.getString(C0257R.string.revine_line_me));
                        } else if (post.repost.username != null) {
/* 794 */                   h.revineAndBylineText.setOnClickListener(new ViewOnClickListenerC05618(userId));
/* 800 */                   String revinedBy = this.mContext.getString(C0257R.string.revine_line, post.repost.username);
/* 801 */                   SpannableStringBuilder revinedBySb = new SpannableStringBuilder(revinedBy);
/* 802 */                   h.revineAndBylineText.setMovementMethod(LinkMovementMethod.getInstance());
/* 803 */                   h.revineAndBylineText.setText(revinedBySb);
                        } else {
/* 805 */                   h.revineAndBylineContainer.setVisibility(8);
                        }
                    } else {
/* 808 */               h.revineAndBylineContainer.setVisibility(8);
                    }
/* 812 */           if (position != 0 && h.revineAndBylineContainer.getVisibility() == 8) {
/* 813 */               h.grayPostSpacer.setVisibility(0);
                    } else {
/* 815 */               h.grayPostSpacer.setVisibility(8);
                    }
/* 818 */           if (!TextUtils.isEmpty(post.description)) {
/* 819 */               h.description.setVisibility(0);
/* 821 */               if (post.transientEntities == null && post.entities != null) {
/* 822 */                   post.transientEntities = new ArrayList<>();
/* 823 */                   Iterator<VineEntity> it = post.entities.iterator();
                            while (it.hasNext()) {
/* 824 */                       post.transientEntities.add(it.next().duplicate());
                            }
                        }
/* 828 */               ArrayList<VineEntity> postEntities = post.transientEntities;
/* 829 */               String description = Util.addDirectionalMarkers(post.description);
/* 830 */               if (postEntities != null) {
/* 831 */                   h.description.setMovementMethod(LinkMovementMethod.getInstance());
/* 832 */                   if (post.descriptionSb == null) {
/* 833 */                       post.descriptionSb = new SpannableStringBuilder(description);
/* 834 */                       SpannableStringBuilder descriptionSb = post.descriptionSb;
                                try {
/* 836 */                           Util.adjustEntities(postEntities, descriptionSb, 0, false);
                                    for (VineEntity entity : postEntities) {
/* 839 */                               if (entity.isUserType()) {
/* 840 */                                   StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mSpanClickListener);
/* 843 */                                   usernameSpan.setColor(this.mProfileColor);
/* 844 */                                   Util.safeSetSpan(descriptionSb, usernameSpan, entity.start, entity.end, 33);
                                        } else if (entity.isTagType()) {
/* 847 */                                   StyledClickableSpan usernameSpan2 = new StyledClickableSpan(4, entity.title, this.mSpanClickListener);
/* 850 */                                   usernameSpan2.setColor(this.mProfileColor);
/* 851 */                                   Util.safeSetSpan(descriptionSb, usernameSpan2, entity.start, entity.end, 33);
                                        }
                                    }
                                } catch (Exception e) {
/* 856 */                           CrashUtil.logException(e);
                                }
                            }
/* 859 */                   h.description.setText(post.descriptionSb);
                        } else {
/* 861 */                   h.description.setText(description);
                        }
/* 864 */               if (h.post.isRTL == null) {
/* 865 */                   h.post.isRTL = Boolean.valueOf(Util.isRtlLanguage(description));
                        }
/* 868 */               if (h.post.isRTL.booleanValue()) {
/* 869 */                   h.description.setGravity(5);
                        } else {
/* 871 */                   h.description.setGravity(3);
                        }
                    } else {
/* 875 */               h.description.setVisibility(8);
                    }
/* 878 */           h.listener.setPosition(position);
/* 879 */           if (h.imageListener != null) {
/* 880 */               h.imageListener.setPosition(position);
/* 881 */               h.imageListener.setPostId(post.postId);
                    }
/* 884 */           bindHeader(h);
/* 886 */           h.comment.setColor(this.mProfileColor);
/* 887 */           h.comment.setOnClickListener(new ViewOnClickListenerC05629(post));
/* 894 */           LinearLayout commentsContainer = h.commentList;
/* 895 */           boolean showComments = h.post.comments != null && h.post.comments.items.size() > 0;
/* 898 */           // Profile rows can be backed by cached VinePost instances whose
/* 899 */           // formatted counter was created before the network refresh.
/* 900 */           // Rebuild it for every bind so likes/comments/revines reflect the
/* 901 */           // counts parsed for this profile response.
/* 902 */           setStyledCompactLikesComments(h, true);
/* 899 */           commentsContainer.setVisibility(0);
/* 902 */           if (showComments) {
/* 903 */               setStyledComments(h, post.comments);
/* 904 */               commentsContainer.setVisibility(0);
/* 905 */               if (post.commentsCount > 3) {
/* 906 */                   h.commentType = 2;
                        } else {
/* 908 */                   h.commentType = 1;
                        }
                    } else {
/* 911 */               commentsContainer.setVisibility(8);
                    }
/* 916 */           if (this.mLikeCache.contains(post.postId)) {
/* 917 */               liked = this.mLikeCache.isLike(post.postId);
/* 918 */               post.setFlagLiked(liked);
                    } else {
/* 920 */               liked = post.isLiked();
/* 921 */               if (liked) {
/* 922 */                   this.mLikeCache.like(post.postId);
                        } else {
/* 924 */                   this.mLikeCache.unlike(post.postId);
                        }
                    }
/* 928 */           h.liked.setColor(this.mProfileColor);
/* 930 */           h.liked.setSelected(liked);
/* 932 */           h.liked.setTag(post);
/* 933 */           h.liked.setOnClickListener(this.mViewClickListener);
/* 935 */           h.more.setTag(post);
/* 936 */           h.more.setOnClickListener(this.mViewClickListener);
/* 938 */           h.share.setVisibility(0);
/* 940 */           if (this.mRevineCache.contains(post.postId)) {
/* 941 */               revined = this.mRevineCache.isRevined(post.postId);
/* 942 */               post.setFlagRevined(revined);
                    } else {
/* 944 */               revined = post.myRepostId > 0 || post.isRevined();
/* 945 */               if (revined) {
/* 946 */                   this.mRevineCache.putMyRepostId(post.postId, post.myRepostId);
/* 947 */                   this.mRevineCache.revine(post.postId);
                        } else {
/* 949 */                   this.mRevineCache.unRevine(post.postId);
                        }
                    }
/* 953 */           h.share.setColor(this.mProfileColor);
/* 954 */           h.share.setSelected(revined);
/* 956 */           h.share.setTag(post);
/* 957 */           h.share.setOnClickListener(this.mViewClickListener);
/* 959 */           h.doubleTapView.setVisibility(8);
/* 960 */           h.doubleTapView.reset();
/* 962 */           initVideoView(position, h);
/* 963 */           if (!this.mFirstPlayed && position == 0 && !post.isExplicit()) {
/* 964 */               getVideoPathAndPlayForPosition(0, false);
/* 965 */               if (SLog.sLogsOn && this.mStartLoadingTime > 0) {
/* 966 */                   this.mLogger.m506i("Time since started loading to request 0th video to play: {}ms", Long.valueOf(timeSinceStartLoadingTime()));
                        }
/* 968 */               this.mFirstPlayed = true;
                    }
/* 971 */           if (needReplay) {
/* 972 */               playCurrentPosition(0L);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$8 */
/* 792 */       class ViewOnClickListenerC05618 implements View.OnClickListener {
                    final /* synthetic */ long val$userId;

/* 793 */           ViewOnClickListenerC05618(long j) {
                        this.val$userId = j;
                    }

                    @Override // android.view.View.OnClickListener
/* 796 */           public void onClick(View v) {
/* 797 */               FeedAdapter.this.mTimelineFragment.onRevineBySpanClicked(this.val$userId);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$9 */
/* 885 */       class ViewOnClickListenerC05629 implements View.OnClickListener {
                    final /* synthetic */ VinePost val$post;

/* 886 */           ViewOnClickListenerC05629(VinePost vinePost) {
                        this.val$post = vinePost;
                    }

                    @Override // android.view.View.OnClickListener
/* 889 */           public void onClick(View v) {
/* 890 */               CommentsActivity.start(FeedAdapter.this.mContext, this.val$post.postId, this.val$post.getVineRepostRepostId(), this.val$post.userId, false);
                    }
                }

/* 976 */       private void bindByline(FeedViewHolder h, VinePost post) {
/* 977 */           VineByline byline = post.byline;
/* 978 */           if (byline.body != null) {
/* 979 */               h.revineAndBylineContainer.setVisibility(0);
/* 980 */               h.bylineIconImageKey = new ImageKey(byline.iconUrl);
/* 981 */               Bitmap bmp = this.mAppController.getPhotoBitmap(h.bylineIconImageKey);
/* 982 */               setBylineIcon(bmp, h);
/* 984 */               if (!byline.entities.isEmpty()) {
/* 985 */                   h.revineAndBylineText.setOnClickListener(new ViewOnClickListenerC055110(byline));
                        }
/* 992 */               h.revineAndBylineText.setText(byline.body);
/* 996 */               return;
                    }
/* 994 */           h.revineAndBylineContainer.setVisibility(8);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$10 */
/* 983 */       class ViewOnClickListenerC055110 implements View.OnClickListener {
                    final /* synthetic */ VineByline val$byline;

/* 984 */           ViewOnClickListenerC055110(VineByline vineByline) {
                        this.val$byline = vineByline;
                    }

                    @Override // android.view.View.OnClickListener
/* 987 */           public void onClick(View v) {
/* 988 */               LinkDispatcher.dispatch(this.val$byline.entities.get(0).link, FeedAdapter.this.mContext);
                    }
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 998 */       public synchronized void onPause(boolean focused) {
                    try {
                        this.mLogger.m489d("OnPause {}. {}", Boolean.valueOf(focused), this);
/* 1000 */              this.mHasFocus = focused;
/* 1001 */              this.mShouldBePlayingPostId = -2147483648L;
/* 1002 */              this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
/* 1003 */              releaseOtherPlayers(null);
/* 1004 */              resetStates(false);
/* 1005 */              this.mWasResumed = false;
/* 1006 */              if (this.mPlayingPlayer != null) {
/* 1007 */                  pausePlayer(this.mPlayingPlayer);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1011 */      public synchronized void onResume(boolean focused) {
                    int currentPosition;
                    try {
                        this.mLogger.m489d("OnResume {}. {}", Boolean.valueOf(focused), this);
/* 1013 */              if (!this.mWasResumed) {
/* 1014 */                  this.mWasResumed = true;
/* 1015 */                  this.mShouldBePlayingPostId = -2147483648L;
/* 1016 */                  this.mMuted = MuteUtil.isMuted(this.mContext);
                        }
/* 1018 */              this.mHasFocus = focused;
/* 1019 */              if (this.mHasFocus && (currentPosition = calculateCurrentPosition()) > 0 && this.mViewHolders.size() > currentPosition) {
                            try {
/* 1023 */                      FeedViewHolder h = getHolderFromPosition(currentPosition);
/* 1024 */                      if (h != null) {
/* 1025 */                          bindVideoImage(h);
                                }
                            } catch (IndexOutOfBoundsException e) {
/* 1029 */                      this.mLogger.m500e("Race condition.", (Throwable) e);
                            }
                        }
/* 1033 */              playCurrentPosition();
                            // If returning from another activity left the visible video
                            // stuck buffering (open raced a released surface/player),
                            // re-kick it once. Healthy playback is already playing and
                            // is left alone.
                            this.mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!FeedAdapter.this.mWasResumed) {
                                            return;
                                        }
                                        int pos = FeedAdapter.this.calculateCurrentPosition();
                                        if (pos > 0) {
                                            SdkVideoView v = FeedAdapter.this.getPlayer(pos, false);
                                            if (v != null && v.getWindowToken() != null && !v.isPlaying()) {
                                                v.retryOpenVideo(true);
                                            }
                                        }
                                    } catch (Throwable t) {
                                        CrashUtil.logException(t);
                                    }
                                }
                            }, 2000L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 1036 */      protected FeedViewHolder getHolderFromPosition(int position) {
/* 1037 */          WeakReference<FeedViewHolder> p = (WeakReference) this.mViewHolders.get(position);
/* 1038 */          if (p != null) {
                        return p.get();
                    }
                    return null;
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1042 */      public void onDestroy() {
/* 1043 */          stopCurrentPlayer();
/* 1044 */          releaseOtherPlayers(null);
/* 1045 */          this.mLoopCountHandler.getLooper().quit();
/* 1046 */          this.mLoopCountThread.interrupt();
/* 1047 */          this.mLoopCountThread = null;
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1051 */      public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 1052 */          if (scrollState == 0) {
/* 1053 */              playCurrentPosition();
                    }
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1057 */      public void toggleMute(boolean mute) {
/* 1058 */          this.mMuted = mute;
/* 1059 */          this.mLogger.m488d("Mute state changed to muted? {}.", Boolean.valueOf(mute));
/* 1060 */          SdkVideoView lastPlayer = getLastPlayer();
/* 1061 */          if (lastPlayer != null) {
/* 1062 */              lastPlayer.setMute(this.mMuted);
                    }
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1066 */      public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
/* 1067 */          this.mLogger.m487d("CALLBACK onVideoPathObtained");
                    for (VideoKey key : videos.keySet()) {
/* 1069 */              UrlVideo video = videos.get(key);
/* 1070 */              if (video.isValid() && this.mUrlReverse.get(key) != null) {
/* 1071 */                  Long pathKey = this.mUrlReverse.get(key);
/* 1072 */                  this.mPaths.put(pathKey.longValue(), video.getAbsolutePath());
/* 1073 */                  this.mVideoKeys.put(pathKey.longValue(), key);
                            if (this.mUrlReverse.get(key).longValue() == this.mShouldBePlayingPostId) {
/* 1075 */                      playCurrentPosition();
                            }
                        }
                    }
                }

/* 1081 */      protected void onVideoImageObtained(FeedViewHolder holder, boolean b) {
/* 1082 */          holder.isVideoImageLoaded = b;
/* 1083 */          playCurrentPosition();
                }

/* 1087 */      protected void createSensitiveLisenter(FeedViewHolder h) {
/* 1088 */          h.imageListener = new VideoSensitiveImageClickListener(this, this.mSensitiveContentWarningsAcknowledged);
/* 1089 */          h.sensitiveTextContainer.setOnClickListener(h.imageListener);
/* 1090 */          h.sensitiveTextContainer.setClickable(true);
/* 1091 */          h.isVideoImageLoaded = true;
                }

/* 1095 */      protected void createInsensitiveListener(FeedViewHolder h) {
/* 1096 */          h.sensitiveTextContainer.setVisibility(8);
/* 1097 */          h.sensitiveTextContainer.setOnClickListener(null);
/* 1098 */          h.sensitiveTextContainer.setClickable(false);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 1102 */      public SdkVideoView getLastPlayer() {
/* 1103 */          return this.mLastPlayer;
                }

                @Override // co.vine.android.widget.GenericVideoAdapter
/* 1107 */      public void onDestroyView() {
/* 1108 */          releaseOtherPlayers(null);
                }

                @Override // android.widget.Adapter
/* 1112 */      public int getCount() {
/* 1113 */          return this.mPosts.size();
                }

                @Override // android.widget.Adapter
/* 1117 */      public Object getItem(int i) {
/* 1118 */          return getPost(i);
                }

                @Override // android.widget.Adapter
                @Nullable
/* 1123 */      public View getView(int i, View view, ViewGroup viewGroup) {
/* 1124 */          if (view == null) {
/* 1125 */              view = newView(viewGroup);
                    }
/* 1127 */          FeedViewHolder h = (FeedViewHolder) view.getTag();
/* 1128 */          h.post = getPost(i);
/* 1129 */          h.position = i;
/* 1131 */          if (h.post != null) {
/* 1132 */              bindView(view, h);
                    } else {
/* 1134 */              CrashUtil.log("Post null: {} {}", Integer.valueOf(i), Integer.valueOf(this.mPosts.size()));
                    }
/* 1136 */          return view;
                }

                @Override // co.vine.android.widget.GenericVideoAdapter, android.widget.Adapter
/* 1140 */      public long getItemId(int postition) {
/* 1141 */          return postition;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 1145 */      public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
                    FeedViewHolder h;
/* 1146 */          int pinnedHeaderPosition = this.mPinnedHeaderPosition;
/* 1147 */          this.mPinnedHeaderListViewChildIndex = ((PinnedHeaderListView) this.mListView).getPositionForPixelLocation(navBottom);
/* 1148 */          this.mPinnedHeaderPosition = (this.mListView.getFirstVisiblePosition() - this.mListView.getHeaderViewsCount()) + this.mPinnedHeaderListViewChildIndex;
/* 1149 */          View child = this.mListView.getChildAt(this.mPinnedHeaderListViewChildIndex);
/* 1150 */          int offset = 0;
/* 1151 */          boolean show = true;
/* 1152 */          if (child != null && (h = (FeedViewHolder) child.getTag()) != null) {
/* 1155 */              View byline = ((ViewGroup) child).getChildAt(0);
/* 1156 */              boolean bylineVisible = byline.getVisibility() == 0;
/* 1158 */              int viewTop = child.getTop();
/* 1161 */              int dividerTop = child.getTop() + h.divider.getTop();
/* 1168 */              if (dividerTop < this.mPinnedHeaderHeight + navBottom + this.mDividerScrollAwayPadding) {
/* 1169 */                  offset = (dividerTop - this.mPinnedHeaderHeight) - navBottom;
                        }
/* 1171 */              if (bylineVisible) {
/* 1172 */                  show = viewTop <= navBottom - byline.getHeight();
                        } else if (h.grayPostSpacer.getVisibility() == 0) {
/* 1174 */                  show = viewTop <= navBottom - h.grayPostSpacer.getHeight();
                        }
                    }
/* 1179 */          return new PinnedHeaderAdapter.PinnedHeaderStatus(offset, this.mPinnedHeaderView == null || pinnedHeaderPosition != this.mPinnedHeaderPosition, show);
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 1184 */      public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 1192 */      public View getPinnedHeaderView(View convertView) {
/* 1193 */          ViewGroup topLevelView = (ViewGroup) this.mListView.getChildAt(this.mPinnedHeaderListViewChildIndex);
/* 1194 */          if (topLevelView == null) {
                        return null;
                    }
/* 1195 */          View pinnedView = topLevelView.findViewById(C0257R.id.top_content_container);
/* 1196 */          if (pinnedView != null) {
/* 1197 */              pinnedView.setBackgroundColor(-1);
                    }
/* 1199 */          this.mPinnedHeaderView = pinnedView;
/* 1202 */          return pinnedView;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 1207 */      public int getPinnedHeaderHeight() {
/* 1208 */          return this.mPinnedHeaderHeight;
                }

/* 1211 */      private void bindHeader(FeedViewHolder h) {
                    int color;
/* 1212 */          VinePost post = h.post;
/* 1213 */          String usernameString = post.username;
/* 1215 */          h.avatarKey = null;
/* 1216 */          bindAvatar(h);
/* 1218 */          if (usernameString == null) {
/* 1219 */              usernameString = "";
/* 1220 */              CrashUtil.logException(new VineLoggingException("Invalid user name for post " + post.userId));
                    }
/* 1223 */          if (h.post.styledUserName == null) {
/* 1224 */              SpannableStringBuilder sb = new SpannableStringBuilder(usernameString);
/* 1225 */              ForegroundColorSpan colorSpan = new ForegroundColorSpan(this.mProfileColor);
/* 1226 */              Util.safeSetSpan(sb, this.mBoldSpan, 0, usernameString.length(), 33);
/* 1227 */              Util.safeSetSpan(sb, colorSpan, 0, usernameString.length(), 33);
/* 1228 */              h.post.styledUserName = sb;
                    }
/* 1231 */          h.username.setText(h.post.styledUserName);
/* 1233 */          h.topContentContainer.setOnClickListener(new ViewOnClickListenerC055211(h));
/* 1240 */          long myId = AppController.getInstance(this.mContext).getActiveId();
/* 1241 */          if (!h.post.originalFollowingState && h.post.userId != myId) {
/* 1242 */              h.avatarFollowContainer.setVisibility(0);
/* 1243 */              CompoundButton followButton = h.followButton;
/* 1244 */              followButton.setOnClickListener(new ViewOnClickListenerC055312(followButton));
/* 1259 */              followButton.setChecked(post.following);
/* 1260 */              followButton.setTag(Long.valueOf(post.userId));
/* 1262 */              Drawable bg = h.followButton.getBackground();
/* 1263 */              if (bg != null) {
/* 1265 */                  if (this.mProfileColor != 0) {
/* 1266 */                      color = this.mProfileColor;
                            } else {
/* 1268 */                      color = this.mVineGreen;
                            }
/* 1270 */                  bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                        }
                    } else {
/* 1273 */              h.avatarFollowContainer.setVisibility(8);
                    }
/* 1276 */          h.userImage.setOnTouchListener(new ViewOnTouchListenerC055413(h));
/* 1283 */          int left = h.avatarFollowContainer.getVisibility() != 0 ? this.mPostHeaderPaddingSmall : this.mPostHeaderPaddingLarge;
/* 1285 */          h.username.setPadding(left, 0, 0, 0);
/* 1286 */          h.timestamp.setPadding(left, 0, 0, 0);
/* 1288 */          boolean hasVenue = (post.venueData == null || TextUtils.isEmpty(post.venueData.venueName)) ? false : true;
/* 1290 */          if (h.post.dateString == null) {
/* 1291 */              h.post.dateString = Util.getRelativeTimeString(this.mContext, post.created, false);
/* 1293 */              if (hasVenue) {
/* 1294 */                  h.post.dateString = this.mContext.getString(C0257R.string.date_and_location_formatted, h.post.dateString, post.venueData.venueName.trim());
                        }
                    }
/* 1299 */          h.timestamp.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, hasVenue ? this.mFeedLocationPinDrawable : null, (Drawable) null);
/* 1301 */          h.timestamp.setText(h.post.dateString);
/* 1303 */          h.updateCount(post);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$11 */
/* 1231 */      class ViewOnClickListenerC055211 implements View.OnClickListener {
                    final /* synthetic */ FeedViewHolder val$h;

/* 1232 */          ViewOnClickListenerC055211(FeedViewHolder feedViewHolder) {
                        this.val$h = feedViewHolder;
                    }

                    @Override // android.view.View.OnClickListener
/* 1235 */          public void onClick(View view) {
/* 1236 */              ProfileActivity.start(FeedAdapter.this.mContext, this.val$h.post.userId, FeedAdapter.this.mFollowEventSource);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$12 */
/* 1242 */      class ViewOnClickListenerC055312 implements View.OnClickListener {
                    final /* synthetic */ CompoundButton val$followButton;

/* 1243 */          ViewOnClickListenerC055312(CompoundButton compoundButton) {
                        this.val$followButton = compoundButton;
                    }

                    @Override // android.view.View.OnClickListener
/* 1246 */          public void onClick(View v) {
/* 1247 */              Object userId = this.val$followButton.getTag();
/* 1248 */              if (userId instanceof Long) {
/* 1249 */                  Session session = FeedAdapter.this.mAppController.getActiveSessionReadOnly();
/* 1251 */                  if (this.val$followButton.isChecked()) {
/* 1252 */                      FeedAdapter.this.mAppController.followUser(session, ((Long) userId).longValue(), true);
                            } else {
/* 1254 */                      FeedAdapter.this.mAppController.unfollowUser(session, ((Long) userId).longValue(), true);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FeedAdapter$13 */
/* 1274 */      class ViewOnTouchListenerC055413 implements View.OnTouchListener {
                    final /* synthetic */ FeedViewHolder val$h;

/* 1275 */          ViewOnTouchListenerC055413(FeedViewHolder feedViewHolder) {
                        this.val$h = feedViewHolder;
                    }

                    @Override // android.view.View.OnTouchListener
/* 1278 */          public boolean onTouch(View view, MotionEvent motionEvent) {
                        return !this.val$h.post.originalFollowingState && this.val$h.followButton.dispatchTouchEvent(motionEvent);
                    }
                }

/* 1304 */      private class PlayCurrentPositionRunnable implements Runnable {
/* 1305 */          private PlayCurrentPositionRunnable() {
                    }

/* 1305 */          /* synthetic */ PlayCurrentPositionRunnable(FeedAdapter x0, C05501 x1) {
/* 1306 */              this();
                    }

                    @Override // java.lang.Runnable
/* 1309 */          public synchronized void run() {
                        try {
                            int calculatedPosition = FeedAdapter.access$1100(FeedAdapter.this);
/* 1311 */                  if (calculatedPosition != -2 && calculatedPosition != -1) {
/* 1313 */                      if (!FeedAdapter.access$1200(FeedAdapter.this, calculatedPosition)) {
/* 1314 */                          FeedAdapter.this.getVideoPathAndPlayForPosition(calculatedPosition, false);
                                } else {
/* 1316 */                          FeedAdapter.this.pauseCurrentPlayer();
                                }
                            } else if (calculatedPosition == -2 && !FeedAdapter.access$1300(FeedAdapter.this)) {
/* 1320 */                      FeedAdapter.this.getVideoPathAndPlayForPosition(FeedAdapter.access$100(FeedAdapter.this), false);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1325 */      public void addOlderPosts(ArrayList<VinePost> newPosts) {
/* 1326 */          this.mPosts.addAll(newPosts);
/* 1327 */          notifyDataSetChanged();
                }

/* 1330 */      private void printPostIds(String tag, Collection<VinePost> posts) {
/* 1331 */          if (CommonUtil.DEBUG_VERBOSE) {
/* 1332 */              long[] ids = new long[posts.size()];
/* 1333 */              int m = 0;
                        for (VinePost post : posts) {
                            ids[m] = post.postId;
/* 1335 */                  m++;
                        }
/* 1337 */              this.mLogger.m507i("{} : {}", tag, ids);
                    }
                }

/* 1342 */      public void replacePosts(ArrayList<VinePost> newPosts) {
/* 1343 */          this.mPaths.clear();
/* 1344 */          this.mVideoKeys.clear();
/* 1345 */          this.mPlayers.clear();
/* 1346 */          this.mUrlReverse.clear();
/* 1347 */          this.mPosts.clear();
/* 1348 */          this.mPosts.addAll(newPosts);
/* 1349 */          notifyDataSetChanged();
                }

/* 1355 */      public int appendPostsOnTop(ArrayList<VinePost> newPosts) {
/* 1356 */          printPostIds("newPosts", newPosts);
/* 1357 */          printPostIds("oldPosts", this.mPosts);
/* 1359 */          HashMap<Long, VinePost> postIds = new HashMap<>();
/* 1361 */          boolean wasEmpty = this.mPosts.size() == 0;
                    for (VinePost post : this.mPosts) {
/* 1363 */              postIds.put(Long.valueOf(post.postId), post);
                    }
/* 1366 */          ArrayList<VinePost> resolvedPosts = new ArrayList<>();
/* 1368 */          int added = 0;
                    for (VinePost post2 : newPosts) {
/* 1370 */              if (postIds.get(Long.valueOf(post2.postId)) == null) {
/* 1371 */                  resolvedPosts.add(post2);
/* 1372 */                  added++;
                        }
                    }
/* 1375 */          if (added == newPosts.size()) {
/* 1377 */              return -1;
                    }
                    for (VinePost post3 : this.mPosts) {
/* 1380 */              VinePost updated = postIds.get(Long.valueOf(post3.postId));
/* 1381 */              if (updated == null) {
/* 1382 */                  resolvedPosts.add(post3);
                        } else {
/* 1384 */                  resolvedPosts.add(updated);
                        }
                    }
/* 1387 */          this.mPosts.clear();
/* 1388 */          this.mPosts.addAll(resolvedPosts);
/* 1389 */          printPostIds("newPosts", this.mPosts);
/* 1391 */          if (!wasEmpty) {
/* 1392 */              int index = this.mListView.getFirstVisiblePosition();
/* 1394 */              int foundFirstChild = -1;
                        for (int i = 0; i < this.mListView.getChildCount(); i++) {
/* 1396 */                  View c = this.mListView.getChildAt(i);
/* 1397 */                  if (c != null && (c.getTag() instanceof FeedViewHolder)) {
/* 1398 */                      foundFirstChild = i;
                                break;
                            }
                        }
/* 1404 */              int extraNeeded = 1;
/* 1406 */              if (foundFirstChild == 2) {
/* 1407 */                  extraNeeded = 2;
                        }
/* 1410 */              SLog.m465i("First child is at index {}", Integer.valueOf(foundFirstChild));
/* 1412 */              int hvc = this.mListView.getHeaderViewsCount();
/* 1413 */              View v = this.mListView.getChildAt(hvc);
/* 1415 */              int top = v == null ? 0 : v.getTop();
/* 1417 */              notifyDataSetChanged();
/* 1419 */              this.mListView.setSelectionFromTop(index + added + extraNeeded, top);
/* 1420 */              SLog.m470i("Scroll: {}, {}, {}, {}", new Object[]{Integer.valueOf(index), Integer.valueOf(added), Integer.valueOf(hvc), Integer.valueOf(top)});
/* 1424 */              return added;
                    }
/* 1423 */          notifyDataSetChanged();
/* 1424 */          return 0;
                }

/* 1429 */      public void mergePosts(ArrayList<VinePost> newPosts) {
/* 1430 */          printPostIds("newPosts", newPosts);
/* 1431 */          printPostIds("oldPosts", this.mPosts);
/* 1433 */          HashMap<Long, Integer> usedViewPostIds = new HashMap<>();
/* 1435 */          ArrayList<VinePost> posts = new ArrayList<>(this.mPosts);
/* 1437 */          int size = posts.size();
                    for (int i = 0; i < size; i++) {
/* 1440 */              usedViewPostIds.put(Long.valueOf(posts.get(i).postId), Integer.valueOf(i));
                    }
                    for (VinePost newPost : newPosts) {
/* 1444 */              Integer position = usedViewPostIds.get(Long.valueOf(newPost.postId));
/* 1445 */              if (position == null) {
/* 1446 */                  posts.add(newPost);
                        } else {
/* 1448 */                  posts.set(position.intValue(), newPost);
                        }
                    }
/* 1452 */          this.mLogger.m507i("{} : {}", "activePosts", usedViewPostIds.keySet());
/* 1454 */          this.mPosts.clear();
/* 1455 */          this.mPosts.addAll(posts);
/* 1456 */          printPostIds("mergedLists", this.mPosts);
/* 1457 */          notifyDataSetChanged();
                }

/* 1460 */      public void mergePost(VinePost post) {
/* 1461 */          ArrayList<VinePost> posts = new ArrayList<>();
/* 1462 */          posts.add(post);
/* 1463 */          mergePosts(posts);
                }

/* 1466 */      public void mergeMyPost(VinePost post) {
/* 1467 */          if (post != null) {
                        for (VinePost p : this.mPosts) {
/* 1469 */                  if (p != null && p.postId == post.postId) {
/* 1481 */                      return;
                            }
                        }
/* 1473 */              this.mPosts.add(0, post);
/* 1474 */              this.mPaths.clear();
/* 1475 */              this.mVideoKeys.clear();
/* 1476 */              this.mUrlReverse.clear();
/* 1477 */              this.mPlayers.clear();
/* 1478 */              notifyDataSetChanged();
/* 1479 */              playCurrentPosition();
                    }
                }

/* 1489 */      private class VideoContaineTouchListener extends OnListVideoClickListener implements View.OnTouchListener {
                    private int mLastA;
                    private long mLastTapTime;
                    private float mLastX;
                    private float mLastY;
                    private final Random mRandom;
                    private final FeedViewHolder mTag;

/* 1490 */          public VideoContaineTouchListener(FeedViewHolder tag) {
/* 1492 */              super(FeedAdapter.this);
/* 1488 */              this.mRandom = new Random();
/* 1493 */              this.mTag = tag;
                    }

                    @Override // co.vine.android.player.OnListVideoClickListener, android.view.View.OnClickListener
/* 1497 */          public void onClick(View v) {
/* 1498 */              super.onClick(v);
                        if (System.currentTimeMillis() - this.mLastTapTime <= 450) {
/* 1502 */                  if (this.mTag.videoView.isInPlaybackState() && (FeedAdapter.access$1400(FeedAdapter.this) || !this.mTag.videoView.isPaused())) {
/* 1505 */                      if (this.mTag.doubleTapView.getVisibility() != 0) {
/* 1506 */                          this.mTag.doubleTapView.setVisibility(0);
                                }
/* 1508 */                      int angle = nextAngle();
/* 1509 */                      this.mTag.doubleTapView.add(this.mRandom.nextInt(50) % 10 == 0, this.mLastX, this.mLastY, angle);
/* 1514 */                      this.mLastA = angle;
/* 1515 */                      this.mTag.doubleTapView.postInvalidate();
/* 1516 */                      FlurryUtils.trackDoubleTap(this.mTag.post.postId);
/* 1517 */                      FeedAdapter.this.mTimelineFragment.like(this.mTag.post, false);
                            }
/* 1520 */                  this.mLastTapTime = 0L;
/* 1524 */                  return;
                        }
/* 1522 */              this.mLastTapTime = System.currentTimeMillis();
                    }

/* 1526 */          private int nextAngle() {
                        int currentAngle;
/* 1527 */              if (this.mLastA == 0) {
/* 1528 */                  this.mLastA = this.mRandom.nextInt(60) - 30;
                        }
/* 1533 */              do {
/* 1532 */                  currentAngle = this.mRandom.nextInt(60) - 30;
/* 1533 */              } while (Math.abs(this.mLastA - currentAngle) < 10);
/* 1534 */              return currentAngle;
                    }

                    @Override // android.view.View.OnTouchListener
/* 1538 */          public boolean onTouch(View v, MotionEvent event) {
/* 1539 */              if (event.getAction() == 0) {
/* 1540 */                  this.mLastX = event.getX();
/* 1541 */                  this.mLastY = event.getY();
/* 1543 */                  return false;
                        }
/* 1543 */              return false;
                    }
                }
            }
