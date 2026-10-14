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
            
            
    public class FeedAdapter extends GenericVideoAdapter<FeedViewHolder> implements HasVideoPlayerAdapter, PinnedHeaderAdapter {
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

        static  int access$100(FeedAdapter x0) {
            return x0.mCurrentPlaying;
                }

        static  SdkVideoView access$1000(FeedAdapter x0) {
            return x0.mLastPlayer;
                }

        static  int access$1100(FeedAdapter x0) {
            return x0.calculateCurrentPosition();
                }

        static  boolean access$1200(FeedAdapter x0, int x1) {
            return x0.getExplicit(x1);
                }

        static  boolean access$1300(FeedAdapter x0) {
            return x0.hasPlayerPlaying();
                }

        static  boolean access$1400(FeedAdapter x0) {
            return x0.mDoubleTapToLikeOnPause;
                }

        static  void access$200(FeedAdapter x0, VideoViewInterface x1, long x2) {
            x0.countedMediaPlayerStart(x1, x2);
                }

        static  int access$302(FeedAdapter x0, int x1) {
            x0.maxPlayer = x1;
                    return x1;
                }

        static  int access$400(FeedAdapter x0) {
            return x0.currentPlayerCount;
                }

        static  boolean access$502(FeedAdapter x0, boolean x1) {
            x0.mNeedReleaseOtherPlayers = x1;
                    return x1;
                }

        static  LongSparseArray access$600(FeedAdapter x0) {
            return x0.mPaths;
                }

        static  LongSparseArray access$700(FeedAdapter x0) {
            return x0.mVideoKeys;
                }

        static  VinePost access$800(FeedAdapter x0, int x1) {
            return x0.getPost(x1);
                }

        static  boolean access$900(FeedAdapter x0) {
            return x0.mHasFocus;
                }




       public FeedAdapter(Activity context, ListView listView, LikeCache likeCache, RevineCache revineCache, BaseTimelineFragment timelineFragment, String followEventSource, SLogger logger) {
           super(context, listView, likeCache, revineCache, timelineFragment, followEventSource, logger);
            this.mHandler = new Handler();
           this.mCurrentPlaying = Integer.MIN_VALUE;
           this.mShouldBePlayingPostId = -2147483648L;
           this.maxPlayer = 10;
           this.currentPlayerCount = 0;
           this.mNeedReleaseOtherPlayers = false;
           this.mWasResumed = false;
           this.mVideoAttr = null;
           this.mPinnedHeaderPosition = -1;
           this.mPinnedHeaderHeight = 0;
           this.mPinnedHeaderView = null;
           this.mDividerScrollAwayPadding = 0;
           this.mPinnedHeaderListViewChildIndex = 0;
           this.mPlayers = new SparseArray<>();
           this.mPosts = new ArrayList<>();
           this.mPaths = new LongSparseArray<>();
           this.mVideoKeys = new LongSparseArray<>();
           this.mUrlReverse = new HashMap<>();
           this.mLogger.m488d("Adapter constructed, accelerated? {}", Boolean.valueOf(listView.isHardwareAccelerated()));
           this.mPlayCurrentPositionRunnable = new PlayCurrentPositionRunnable(this, null);
           Resources res = context.getResources();
           this.mFeedLocationPinDrawable = res.getDrawable(C0257R.drawable.ic_feed_map_pin);
           this.mPinnedHeaderHeight = res.getDimensionPixelSize(C0257R.dimen.post_header_height);
           timelineFragment.addHeaderView(new LinearLayout(this.mContext), null, false);
           this.mLoopCountThread = new HandlerThread("loop-count-handler");
           this.mLoopCountThread.start();
           this.mLoopCountHandler = new Handler(this.mLoopCountThread.getLooper());
           this.mDividerScrollAwayPadding = res.getDimensionPixelSize(C0257R.dimen.divider_scroll_away_padding);
           this.mDoubleTapBitmap = BitmapFactory.decodeResource(res, C0257R.drawable.double_tap);
           this.mDoubleTapWink_1 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_1);
           this.mDoubleTapWink_2 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_2);
           this.mDoubleTapWink_3 = BitmapFactory.decodeResource(res, C0257R.drawable.btn_wink_3);
           this.mDoubleTapToLikeOnPause = ClientFlagsHelper.doubleTapToLikeOnPause(this.mContext);
           this.mPostHeaderPaddingSmall = res.getDimensionPixelOffset(C0257R.dimen.post_header_padding_small);
           this.mPostHeaderPaddingLarge = res.getDimensionPixelOffset(C0257R.dimen.post_header_padding_large);
                }

       private int calculateCurrentPosition() {
                    int pos;
           int currentFirst = this.mListView.getFirstVisiblePosition();
           int offset = 0;
           if (this.mViewOffsetResolver != null) {
               offset = this.mViewOffsetResolver.getOffset(this);
                    }
           int currentFirst2 = currentFirst - offset;
           HashSet<View> headers = this.mTimelineFragment.getHeaderViewParents();
           if (headers.size() > 0) {
               int missingHeaderViewCount = 0;
                        for (View view : headers) {
                   if (!view.isShown()) {
                       missingHeaderViewCount++;
                            }
                        }
               currentFirst2 -= missingHeaderViewCount;
               if (CommonUtil.DEBUG_VERBOSE) {
                   this.mLogger.m489d("Try to get current First: {}, missing header count: {}", Integer.valueOf(currentFirst2), Integer.valueOf(missingHeaderViewCount));
                        }
                    }
           View currentView = getViewAtPosition(currentFirst2);
           if (currentView == null) {
               return -1;
                    }
           int currentlyPlayingOffset = 0;
           if (getLastPlayer() != null) {
               currentlyPlayingOffset = getLastPlayer().getHeight() / 2;
                    }
           float bottom = currentView.getBottom() + currentlyPlayingOffset;
           float height = currentView.getHeight() + currentlyPlayingOffset;
                    if (height == 0.0f) {
                        height = 1.0f;
                    }
                    float ratio = bottom / height;
                    if (ratio < 0.5d || (currentFirst2 == -1 && currentFirst2 < getCount() - 1 && currentFirst2 + 1 != this.mCurrentPlaying)) {
               pos = currentFirst2 + 1;
                    } else if (currentFirst2 != this.mCurrentPlaying) {
               pos = currentFirst2;
                    } else {
               pos = currentFirst2 == this.mCurrentPlaying ? -2 : -1;
                    }
           if (CommonUtil.DEBUG_VERBOSE) {
               this.mLogger.m490d("Current First: {}, ratio: {}, currently playing: {}", Integer.valueOf(pos), Float.valueOf(ratio), Integer.valueOf(this.mCurrentPlaying));
               return pos;
                    }
           return pos;
                }

                @Override 
       protected void resetStates(boolean hasDataSetChanged) {
           boolean currentPlayerChanged = true;
           if (this.mLastPlayer != null && this.mCurrentPlaying >= 0) {
               Object tag = this.mLastPlayer.getTag();
               long postId = -1;
               if (tag instanceof Long) {
                   postId = ((Long) tag).longValue();
                        }
               if (this.mPosts.size() > this.mCurrentPlaying && this.mPosts.get(this.mCurrentPlaying).postId == postId) {
                   currentPlayerChanged = false;
                        }
                    }
           if (currentPlayerChanged) {
               stopCurrentPlayer();
               this.mShouldBePlayingPostId = -1L;
               this.currentPlayerCount = 0;
               this.mFirstPlayed = false;
                    }
           if (hasDataSetChanged) {
               this.mLikeCache.clear();
               this.mRevineCache.clear();
                    }
                }

                @Override 
       public synchronized void playFile(int position, boolean isVideoLoaded) {
                    boolean newView = true;
                    synchronized (this) {
                        if (position >= 0) {
                            try {
                       this.mLogger.m506i("Playing item: {}", Integer.valueOf(position));
                       View currentView = getViewAtPosition(position);
                       if (currentView == null) {
                           this.mLogger.m506i("Item is not visible: {}", Integer.valueOf(position));
                                } else {
                           FeedViewHolder tag = (FeedViewHolder) currentView.getTag();
                           if (tag != null) {
                               SdkVideoView currentVideoView = tag.videoView;
                               if (currentVideoView != this.mLastPlayer) {
                                   this.mLogger.m505i("Pause previous player.");
                                   pauseCurrentPlayer();
                                        }
                               if (tag.post == null) {
                                   this.mLogger.m505i("Post was cleared.");
                                        } else {
                                   String newPath = this.mPaths.get(tag.post.postId);
                                   if (newPath == null) {
                                       this.mLogger.m505i("Path was cleared.");
                                            } else {
                                       if (!currentVideoView.isPathPlaying(newPath)) {
                                           if (SystemUtil.isSinglePlayerEnabled(this.mContext)) {
                                                        if (SdkVideoView.getCurrentUri() != null) {
                                                            newView = false;
                                                        }
                                                    } else if (currentVideoView.getPath() != null) {
                                                        newView = false;
                                                    }
                                           if (!newView) {
                                               suspendPlayer(tag.videoView);
                                               Drawable bg = tag.videoImage.getBackground();
                                               ViewUtil.setBackground(tag.videoImageTop, bg);
                                               tag.videoImageTop.clearAnimation();
                                               tag.videoImageTop.setVisibility(0);
                                               tag.videoView.setVisibility(8);
                                               tag.videoView.setSurfaceUpdatedListener(new C05501(tag));
                                                    }
                                           currentVideoView = tag.videoView;
                                           currentVideoView.setMute(this.mMuted);
                                           if (this.mNeedReleaseOtherPlayers && this.currentPlayerCount >= this.maxPlayer) {
                                               releaseOtherPlayers(tag.videoView);
                                                    }
                                           this.mLogger.m505i("Start playing by set path.");
                                           currentVideoView.setTag(Long.valueOf(tag.post.postId));
                                           CrashUtil.log("Setting video path in feed for post {}", Long.valueOf(tag.post.postId));
                                           currentVideoView.setVideoPath(newPath);
                                           currentVideoView.setCacheKey(this.mVideoKeys.get(tag.post.postId));
                                           if (!currentVideoView.isSurfaceReady() && currentVideoView.getVisibility() != 0) {
                                               currentVideoView.setVisibility(0);
                                                    }
                                           this.currentPlayerCount++;
                                                } else {
                                           if (!tag.videoView.isAvailable()) {
                                               tag.videoView.setVisibility(0);
                                                    }
                                           currentVideoView.setMute(this.mMuted);
                                           if (currentVideoView.isInPlaybackState()) {
                                               this.mLogger.m505i("Start playing by calling start.");
                                               resumePlayer(currentVideoView);
                                                    } else {
                                               this.mLogger.m505i("Start playing by set path.");
                                               currentVideoView.setTag(Long.valueOf(tag.post.postId));
                                               CrashUtil.log("Setting video path in feed for post {}", Long.valueOf(tag.post.postId));
                                               currentVideoView.setVideoPath(newPath);
                                                    }
                                                }
                                       currentVideoView.setPlayingPosition(position);
                                       tag.listener.setPosition(position);
                                       if (tag.imageListener != null) {
                                           tag.imageListener.setPosition(position);
                                           tag.imageListener.setPostId(tag.post.postId);
                                                }
                                       SLog.m465i("Make current player {}", Integer.valueOf(position));
                                       this.mCurrentPlaying = position;
                                       this.mLastPlayer = currentVideoView;
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

                
       class C05501 implements SdkVideoView.SurfaceUpdatedListener {
                    private int mFrameCount = 0;
                    final  FeedViewHolder val$tag;

           C05501(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override 
           public void onSurfaceUpdated() {
               if (this.mFrameCount == 1) {
                   this.val$tag.videoImageTop.setVisibility(8);
                   this.val$tag.videoView.setSurfaceUpdatedListener(null);
                   this.val$tag.videoView.setVisibility(0);
                        }
               this.mFrameCount++;
                    }
                }

                @Override 
       public void pausePlayer(SdkVideoView player) {
           if (player != null) {
                        try {
                   player.pause();
                        } catch (IllegalStateException e) {
                        }
               onPlayerStoppedPlaying(player);
                    }
                }

                @Override 
       public void resumePlayer(SdkVideoView player) {
           player.resume();
           if (this.mPlayingPlayer != player) {
               if (this.mPlayingPlayer != null) {
                   SLog.m477w("There's already someone playing, pause.");
                   pausePlayer(this.mPlayingPlayer);
                        }
               this.mPlayingPlayer = player;
                    }
                }

       private void onPlayerStoppedPlaying(SdkVideoView videoView) {
           if (videoView == this.mPlayingPlayer) {
               this.mPlayingPlayer = null;
                    }
                }

       private void suspendPlayer(SdkVideoView player) {
           player.suspend();
           onPlayerStoppedPlaying(player);
                }

                @Override 
       public synchronized void getVideoPathAndPlayForPosition(int requestedPosition, boolean forceLowKey) {
                    try {
                        this.mLogger.m488d("play task executing with position {} ", Integer.valueOf(requestedPosition));
               if (this.mHasFocus) {
                   VinePost post = getPost(requestedPosition);
                   String postVidePath = null;
                   if (post != null) {
                       String postVidePath2 = this.mPaths.get(post.postId);
                                postVidePath = postVidePath2;
                            }
                   if (postVidePath != null) {
                       if (preFetch(requestedPosition + 1)) {
                           preFetch(requestedPosition + 2);
                                }
                       this.mLogger.m488d("{} is in cache or it is already pre-fetched.", Integer.valueOf(requestedPosition));
                       this.mLogger.m488d("playing file at position {}", Integer.valueOf(requestedPosition));
                       playFile(requestedPosition, true);
                            } else {
                       this.mLogger.m488d("Real fetch for {}.", Integer.valueOf(requestedPosition));
                       if (post != null) {
                           VideoKey url = getRequestKey(post, forceLowKey);
                           if (url == null) {
                               onInvalidRequestKey(post);
                                    } else {
                               String path = this.mAppController.getVideoFilePath(url);
                               if (preFetch(requestedPosition + 1)) {
                                   preFetch(requestedPosition + 2);
                                        }
                               if (path != null) {
                                   this.mPaths.put(post.postId, path);
                                   this.mVideoKeys.put(post.postId, url);
                                   this.mLogger.m488d("playing file at position {}", Integer.valueOf(requestedPosition));
                                   playFile(requestedPosition, true);
                                        } else {
                                   this.mShouldBePlayingPostId = post.postId;
                                   this.mUrlReverse.put(url, Long.valueOf(post.postId));
                                   this.mLogger.m488d("{} is not in app cache yet.", Integer.valueOf(requestedPosition));
                                   pauseCurrentPlayer();
                                   View view = getViewAtPosition(requestedPosition);
                                   if (view != null) {
                                       Object tag = view.getTag();
                                       if (tag instanceof FeedViewHolder) {
                                           ((FeedViewHolder) tag).videoLoadImage.setVisibility(0);
                                                } else {
                                           CrashUtil.logException(new IllegalStateException("Somehow tag was " + tag + ", view class is '" + view.getClass().getName() + "'."));
                                                }
                                            }
                                        }
                                    }
                                } else {
                           this.mLogger.m488d("mPosts is {} or the requested position has no post object in it yet, return -1.", this.mPosts);
                                }
                            }
                        } else {
                   this.mLogger.m487d("not playing because not focused.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Nullable
       private VinePost getPost(int position) {
           if (position < 0 || position >= this.mPosts.size()) {
                        return null;
                    }
           return this.mPosts.get(position);
                }

       private boolean preFetch(int requestedPosition) {
           if (requestedPosition >= this.mPosts.size()) {
                        return false;
                    }
           VinePost nextPost = getPost(requestedPosition);
           if (nextPost != null) {
               if (this.mPaths.get(nextPost.postId) == null) {
                   VideoKey nextUrl = getRequestKey(getPost(requestedPosition), false);
                   this.mLogger.m489d("Pre-fetch {}: {}", Integer.valueOf(requestedPosition), nextUrl);
                   if (nextUrl == null) {
                                return true;
                            }
                   if (nextPost.thumbnailUrl != null) {
                       ImageKey imageKey = new ImageKey(nextPost.thumbnailUrl);
                       this.mAppController.getPhotoBitmap(imageKey);
                            }
                   if (nextPost.avatarUrl != null) {
                       ImageKey imageKey2 = new ImageKey(nextPost.avatarUrl, true);
                       this.mAppController.getPhotoBitmap(imageKey2);
                            }
                   String nextPath = this.mAppController.getVideoFilePath(nextUrl);
                   if (nextPath != null) {
                       this.mPaths.put(nextPost.postId, nextPath);
                                return true;
                            }
                   this.mUrlReverse.put(nextUrl, Long.valueOf(nextPost.postId));
                            return true;
                        }
               this.mLogger.m488d("{} is already fetched.", Integer.valueOf(requestedPosition));
                        return true;
                    }
           this.mLogger.m489d("End of list, no pre-fetching: {} {}", Integer.valueOf(requestedPosition), Integer.valueOf(getCount()));
                    return false;
                }

       private boolean getExplicit(int position) {
           VinePost post = getPost(position);
                    return (post == null || !post.isExplicit() || this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(post.postId))) ? false : true;
                }

       private boolean hasPlayerPlaying() {
                    return (this.mLastPlayer == null || !this.mLastPlayer.isInPlaybackState() || this.mLastPlayer.isPaused()) ? false : true;
                }

       public void stopCurrentPlayer() {
           if (this.mLastPlayer != null) {
               suspendPlayer(this.mLastPlayer);
               this.mLastPlayer = null;
               this.mCurrentPlaying = -1;
                    }
                }

                @Override 
       public void pauseCurrentPlayer() {
           pausePlayer(this.mLastPlayer);
                }

                @Override 
       public SdkVideoView getPlayer(int position, boolean b) {
           return this.mPlayers.get(position);
                }

                @Override 
       public synchronized void playCurrentPosition() {
                    try {
                        playCurrentPosition(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void playCurrentPosition(long delay) {
                    try {
                        this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
               this.mHandler.postDelayed(this.mPlayCurrentPositionRunnable, delay);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public boolean isPlaying() {
           return hasPlayerPlaying();
                }

       private static class VideoImageTopHideAnimation extends AlphaAnimation implements Animation.AnimationListener {
                    private final View mView;

           public VideoImageTopHideAnimation(View view) {
               super(1.0f, 0.0f);
               this.mView = view;
               setDuration(200L);
               setAnimationListener(this);
                    }

                    @Override 
           public void onAnimationStart(Animation animation) {
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               this.mView.setVisibility(8);
                    }

                    @Override 
           public void onAnimationRepeat(Animation animation) {
                    }
                }

       private void initVideoView(int position, FeedViewHolder tag) {
           tag.videoLoadImage.setVisibility(8);
           SdkVideoView view = tag.videoView;
           view.setVinePlayerListener(new C05552(tag));
           view.setOnPreparedListener(new C05563(tag));
           view.setOnErrorListener(new C05574(view, tag, position));
           view.setOnCompletionListener(new C05585(tag));
           view.setSurfaceReadyListener(new C05596());
           this.mPlayers.put(position, view);
                }

                
       class C05552 implements VinePlayer.EventListener {
                    final  FeedViewHolder val$tag;

           C05552(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override 
           public void onError(PlayerState state, int operation, Throwable e) {
                    }

                    @Override 
           public void onVideoSizeChanged(int newWidth, int newHeight) {
                    }

                    @Override 
           public void onLoopCompleted() {
                    }

                    @Override 
           public void onPrepared() {
                    }

                    @Override 
           public void onFirstFrameRendererd() {
               this.val$tag.videoImageTop.startAnimation(new VideoImageTopHideAnimation(this.val$tag.videoImageTop));
                    }

                    @Override 
           public void onPastError(Exception error) {
               CrashUtil.logException(error);
                    }
                }

                
       class C05563 implements VideoViewInterface.OnPreparedListener {
                    final  FeedViewHolder val$tag;

           C05563(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override 
           public void onPrepared(VideoViewInterface view) {
               if (view.getPlayingPosition() == FeedAdapter.access$100(FeedAdapter.this)) {
                   FeedAdapter.access$200(FeedAdapter.this, view, this.val$tag.post.postId);
                        } else {
                   FeedAdapter.this.mLogger.m498e("This video should not be played: {}, {}.", Integer.valueOf(view.getPlayingPosition()), Integer.valueOf(FeedAdapter.access$100(FeedAdapter.this)));
                        }
               FeedAdapter.this.endLoadingTimeProfiling();
               AnalyticsManager.onVideoLoaded(view);
               BehaviorManager.getInstance(FeedAdapter.this.mContext).onVideoLoaded();
                    }
                }

                
       class C05574 implements VideoViewInterface.OnErrorListener {
                    final  int val$position;
                    final  FeedViewHolder val$tag;
                    final  SdkVideoView val$view;

           C05574(SdkVideoView sdkVideoView, FeedViewHolder feedViewHolder, int i) {
                        this.val$view = sdkVideoView;
                        this.val$tag = feedViewHolder;
                        this.val$position = i;
                    }

                    @Override 
           public boolean onError(VideoViewInterface videoView, int what, int extra) {
               FeedAdapter.this.mLogger.m489d("Got error, try recycling it more aggressively: {}, {}", Integer.valueOf(what), Integer.valueOf(extra));
               FeedAdapter.access$302(FeedAdapter.this, FeedAdapter.access$400(FeedAdapter.this) - 1);
               FeedAdapter.access$502(FeedAdapter.this, true);
               FeedAdapter.this.releaseOtherPlayers(this.val$view);
               if (!this.val$view.retryOpenVideo(false)) {
                   FeedAdapter.this.mLogger.m496e("Replay video using videoLowUrl.");
                   if (this.val$tag.post != null) {
                       FeedAdapter.access$600(FeedAdapter.this).remove(this.val$tag.post.postId);
                       FeedAdapter.access$700(FeedAdapter.this).remove(this.val$tag.post.postId);
                       this.val$view.setReportOpenError(true);
                       if (FeedAdapter.access$100(FeedAdapter.this) == this.val$position) {
                           FeedAdapter.this.stopCurrentPlayer();
                                }
                       FeedAdapter.this.getVideoPathAndPlayForPosition(this.val$position, true);
                            }
                        }
                        return true;
                    }
                }

                
       class C05585 implements VideoViewInterface.OnCompletionListener {
                    final  FeedViewHolder val$tag;

           C05585(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override 
           public void onCompletion(VideoViewInterface videoView) {
               videoView.seekTo(0);
               VinePost post = FeedAdapter.access$800(FeedAdapter.this, FeedAdapter.access$100(FeedAdapter.this));
               if (post != null) {
                   this.val$tag.counterView.adjustExtraCount(LoopManager.getLocalLoopCount(post.postId));
                   this.val$tag.counterView.invalidateCounterUI();
                        }
               FlurryUtils.trackVineLoopWatched();
               FeedAdapter.access$200(FeedAdapter.this, videoView, this.val$tag.post.postId);
                    }
                }

                
       class C05596 implements SdkVideoView.SurfaceReadyListener {
           C05596() {
                    }

                    @Override 
           public void onSurfaceTextureAvailable(SdkVideoView videoView) {
               if (FeedAdapter.access$900(FeedAdapter.this) && videoView == FeedAdapter.access$1000(FeedAdapter.this)) {
                   videoView.startOpenVideo();
                        }
                    }
                }

       private void countedMediaPlayerStart(VideoViewInterface view, long postId) {
           if (view.getPlayingPosition() == this.mCurrentPlaying) {
               view.start();
               this.mLoopCountHandler.postDelayed(new LoopCountRunnable(this.mCurrentPlaying, postId), 500L);
                    } else {
               this.mLogger.m498e("This video should not be played: {}, {}.", Integer.valueOf(view.getPlayingPosition()), Integer.valueOf(this.mCurrentPlaying));
                    }
                }

       public void removePost(long postId) {
                    for (int i = 0; i < this.mPosts.size(); i++) {
               VinePost post = getPost(i);
               if (post != null && post.postId == postId) {
                   this.mPosts.remove(i);
                            break;
                        }
                    }
           this.mPaths.clear();
           this.mUrlReverse.clear();
           this.mPlayers.clear();
           notifyDataSetChanged();
           playCurrentPosition();
                }

                
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

       private class LoopCountRunnable implements Runnable {
                    public final int lastCurrentPlaying;
                    public final long postId;

           public LoopCountRunnable(int currentPlaying, long postId) {
               this.lastCurrentPlaying = currentPlaying;
               this.postId = postId;
                    }

                    @Override 
           public void run() {
               if (FeedAdapter.access$100(FeedAdapter.this) == this.lastCurrentPlaying) {
                   LoopManager.get(FeedAdapter.this.mContext).increment(this.postId);
                        }
                    }
                }

       public synchronized void releaseOtherPlayers(SdkVideoView view) {
                    try {
                        Iterator i$ = this.mViewHolders.iterator();
                        while (i$.hasNext()) {
                            WeakReference<FeedViewHolder> key = (WeakReference) i$.next();
                   FeedViewHolder holder = key.get();
                   if (holder != null && view != holder.videoView) {
                       this.mLogger.m488d("Releasing player: {}", Integer.valueOf(holder.position));
                       suspendPlayer(holder.videoView);
                       this.currentPlayerCount--;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public View newView(ViewGroup viewGroup) {
           View viewInflate = this.mInflater.inflate(C0257R.layout.post_row_view, viewGroup, false);
           RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(C0257R.id.videoContainer);
           ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
           layoutParams.height = this.mDimen;
           layoutParams.width = this.mDimen;
           relativeLayout.setLayoutParams(layoutParams);
           ImageView imageView = (ImageView) viewInflate.findViewById(C0257R.id.video_image);
           ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
           layoutParams2.width = this.mDimen;
           layoutParams2.height = this.mDimen;
           imageView.setLayoutParams(layoutParams2);
           FeedViewHolder feedViewHolder = new FeedViewHolder(viewInflate);
           feedViewHolder.doubleTapView = (MultiPhaseBitmapView) viewInflate.findViewById(C0257R.id.doubleTapView);
           feedViewHolder.doubleTapView.setBitmaps(this.mDoubleTapBitmap, this.mDoubleTapWink_1, this.mDoubleTapWink_2, this.mDoubleTapWink_3);
           feedViewHolder.videoImage = imageView;
           feedViewHolder.videoImageTop = (ImageView) relativeLayout.findViewById(C0257R.id.video_image_top);
           feedViewHolder.videoImageTop.setLayoutParams(layoutParams2);
           feedViewHolder.videoView = (SdkVideoView) relativeLayout.findViewById(C0257R.id.videoView);
           VideoContaineTouchListener videoContaineTouchListener = new VideoContaineTouchListener(feedViewHolder);
           relativeLayout.setOnClickListener(videoContaineTouchListener);
           relativeLayout.setOnTouchListener(videoContaineTouchListener);
           relativeLayout.setSoundEffectsEnabled(false);
           feedViewHolder.listener = videoContaineTouchListener;
           viewInflate.setTag(feedViewHolder);
           this.mViewHolders.add(new WeakReference<FeedViewHolder>(feedViewHolder));
           if (Build.VERSION.SDK_INT > 15) {
               feedViewHolder.loopsHintButton.setOnTouchListener(new ViewOnTouchListenerC05607(feedViewHolder));
                    }
           return viewInflate;
                }

                
       class ViewOnTouchListenerC05607 implements View.OnTouchListener {
                    final  FeedViewHolder val$tag;

           ViewOnTouchListenerC05607(FeedViewHolder feedViewHolder) {
                        this.val$tag = feedViewHolder;
                    }

                    @Override 
           public boolean onTouch(View view, MotionEvent event) {
               if (event.getAction() == 1 && (FeedAdapter.this.mListView instanceof PinnedHeaderListView)) {
                   TooltipOverlayView tooltip = new TooltipOverlayView(FeedAdapter.this.mContext, FeedAdapter.this.mContext.getString(C0257R.string.loops_hint));
                   PinnedHeaderListView listView = (PinnedHeaderListView) FeedAdapter.this.mListView;
                   boolean touchedPinned = listView.isAbsoluteTouchInPinnedHeader(event);
                   int[] absoluteLoc = new int[2];
                   view.getLocationInWindow(absoluteLoc);
                   int offset = touchedPinned ? listView.getPinnedHeaderBottomAbs() : absoluteLoc[1] + view.getHeight();
                   tooltip.showTooltip(offset, this.val$tag.loopsHintButton);
                            return true;
                        }
               return false;
                    }
                }

       private boolean shouldShowThumbnail(int position) {
                    return (this.mCurrentPlaying == position && hasPlayerPlaying()) ? false : true;
                }

       private void bindView(View view, FeedViewHolder h) {
                    boolean liked;
                    boolean revined;
           VinePost post = h.post;
           int position = h.position;
           boolean needReplay = false;
           if (position == 0) {
               if (hasPlayerPlaying()) {
                   stopCurrentPlayer();
                   needReplay = true;
                        }
               this.mTimelineFragment.onTopViewBinded();
                    }
           if (shouldShowThumbnail(position)) {
               h.videoLoadImage.setVisibility(0);
               if (h.videoView.getVisibility() != 8) {
                   h.videoView.setVisibility(8);
                        }
                    }
           h.videoImageTop.setVisibility(8);
           h.videoImageKey = null;
           bindVideoImage(h);
           if (!this.mIconsSwitched) {
               this.mIconsSwitched = true;
                    }
           if (!h.post.isExplicit() || this.mSensitiveContentWarningsAcknowledged.contains(Long.valueOf(h.post.postId))) {
               h.videoImage.setVisibility(0);
                    } else {
               h.videoImage.setVisibility(8);
                    }
           if (post.byline != null) {
               bindByline(h, post);
                    } else if (post.repost != null) {
               h.revineAndBylineContainer.setVisibility(0);
               h.bylineIcon.setImageDrawable(this.mRevineDrawable);
               long userId = post.repost.userId;
                        if (userId == this.mAppController.getActiveId()) {
                   h.revineAndBylineText.setText(this.mContext.getString(C0257R.string.revine_line_me));
                        } else if (post.repost.username != null) {
                   h.revineAndBylineText.setOnClickListener(new ViewOnClickListenerC05618(userId));
                   String revinedBy = this.mContext.getString(C0257R.string.revine_line, post.repost.username);
                   SpannableStringBuilder revinedBySb = new SpannableStringBuilder(revinedBy);
                   h.revineAndBylineText.setMovementMethod(LinkMovementMethod.getInstance());
                   h.revineAndBylineText.setText(revinedBySb);
                        } else {
                   h.revineAndBylineContainer.setVisibility(8);
                        }
                    } else {
               h.revineAndBylineContainer.setVisibility(8);
                    }
           if (position != 0 && h.revineAndBylineContainer.getVisibility() == 8) {
               h.grayPostSpacer.setVisibility(0);
                    } else {
               h.grayPostSpacer.setVisibility(8);
                    }
           if (!TextUtils.isEmpty(post.description)) {
               h.description.setVisibility(0);
               if (post.transientEntities == null && post.entities != null) {
                   post.transientEntities = new ArrayList<>();
                   Iterator<VineEntity> it = post.entities.iterator();
                            while (it.hasNext()) {
                       post.transientEntities.add(it.next().duplicate());
                            }
                        }
               ArrayList<VineEntity> postEntities = post.transientEntities;
               String description = Util.addDirectionalMarkers(post.description);
               if (postEntities != null) {
                   h.description.setMovementMethod(LinkMovementMethod.getInstance());
                   if (post.descriptionSb == null) {
                       post.descriptionSb = new SpannableStringBuilder(description);
                       SpannableStringBuilder descriptionSb = post.descriptionSb;
                                try {
                           Util.adjustEntities(postEntities, descriptionSb, 0, false);
                                    for (VineEntity entity : postEntities) {
                               if (entity.isUserType()) {
                                   StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mSpanClickListener);
                                   usernameSpan.setColor(this.mProfileColor);
                                   Util.safeSetSpan(descriptionSb, usernameSpan, entity.start, entity.end, 33);
                                        } else if (entity.isTagType()) {
                                   StyledClickableSpan usernameSpan2 = new StyledClickableSpan(4, entity.title, this.mSpanClickListener);
                                   usernameSpan2.setColor(this.mProfileColor);
                                   Util.safeSetSpan(descriptionSb, usernameSpan2, entity.start, entity.end, 33);
                                        }
                                    }
                                } catch (Exception e) {
                           CrashUtil.logException(e);
                                }
                            }
                   h.description.setText(post.descriptionSb);
                        } else {
                   h.description.setText(description);
                        }
               if (h.post.isRTL == null) {
                   h.post.isRTL = Boolean.valueOf(Util.isRtlLanguage(description));
                        }
               if (h.post.isRTL.booleanValue()) {
                   h.description.setGravity(5);
                        } else {
                   h.description.setGravity(3);
                        }
                    } else {
               h.description.setVisibility(8);
                    }
           h.listener.setPosition(position);
           if (h.imageListener != null) {
               h.imageListener.setPosition(position);
               h.imageListener.setPostId(post.postId);
                    }
           bindHeader(h);
           h.comment.setColor(this.mProfileColor);
           h.comment.setOnClickListener(new ViewOnClickListenerC05629(post));
           LinearLayout commentsContainer = h.commentList;
           boolean showComments = h.post.comments != null && h.post.comments.items.size() > 0;
           
           
           
           
           setStyledCompactLikesComments(h, true);
           commentsContainer.setVisibility(0);
           if (showComments) {
               setStyledComments(h, post.comments);
               commentsContainer.setVisibility(0);
               if (post.commentsCount > 3) {
                   h.commentType = 2;
                        } else {
                   h.commentType = 1;
                        }
                    } else {
               commentsContainer.setVisibility(8);
                    }
           if (this.mLikeCache.contains(post.postId)) {
               liked = this.mLikeCache.isLike(post.postId);
               post.setFlagLiked(liked);
                    } else {
               liked = post.isLiked();
               if (liked) {
                   this.mLikeCache.like(post.postId);
                        } else {
                   this.mLikeCache.unlike(post.postId);
                        }
                    }
           h.liked.setColor(this.mProfileColor);
           h.liked.setSelected(liked);
           h.liked.setTag(post);
           h.liked.setOnClickListener(this.mViewClickListener);
           h.more.setTag(post);
           h.more.setOnClickListener(this.mViewClickListener);
           h.share.setVisibility(0);
           if (this.mRevineCache.contains(post.postId)) {
               revined = this.mRevineCache.isRevined(post.postId);
               post.setFlagRevined(revined);
                    } else {
               revined = post.myRepostId > 0 || post.isRevined();
               if (revined) {
                   this.mRevineCache.putMyRepostId(post.postId, post.myRepostId);
                   this.mRevineCache.revine(post.postId);
                        } else {
                   this.mRevineCache.unRevine(post.postId);
                        }
                    }
           h.share.setColor(this.mProfileColor);
           h.share.setSelected(revined);
           h.share.setTag(post);
           h.share.setOnClickListener(this.mViewClickListener);
           h.doubleTapView.setVisibility(8);
           h.doubleTapView.reset();
           initVideoView(position, h);
           if (!this.mFirstPlayed && position == 0 && !post.isExplicit()) {
               getVideoPathAndPlayForPosition(0, false);
               if (SLog.sLogsOn && this.mStartLoadingTime > 0) {
                   this.mLogger.m506i("Time since started loading to request 0th video to play: {}ms", Long.valueOf(timeSinceStartLoadingTime()));
                        }
               this.mFirstPlayed = true;
                    }
           if (needReplay) {
               playCurrentPosition(0L);
                    }
                }

                
       class ViewOnClickListenerC05618 implements View.OnClickListener {
                    final  long val$userId;

           ViewOnClickListenerC05618(long j) {
                        this.val$userId = j;
                    }

                    @Override 
           public void onClick(View v) {
               FeedAdapter.this.mTimelineFragment.onRevineBySpanClicked(this.val$userId);
                    }
                }

                
       class ViewOnClickListenerC05629 implements View.OnClickListener {
                    final  VinePost val$post;

           ViewOnClickListenerC05629(VinePost vinePost) {
                        this.val$post = vinePost;
                    }

                    @Override 
           public void onClick(View v) {
               CommentsActivity.start(FeedAdapter.this.mContext, this.val$post.postId, this.val$post.getVineRepostRepostId(), this.val$post.userId, false);
                    }
                }

       private void bindByline(FeedViewHolder h, VinePost post) {
           VineByline byline = post.byline;
           if (byline.body != null) {
               h.revineAndBylineContainer.setVisibility(0);
               h.bylineIconImageKey = new ImageKey(byline.iconUrl);
               Bitmap bmp = this.mAppController.getPhotoBitmap(h.bylineIconImageKey);
               setBylineIcon(bmp, h);
               if (!byline.entities.isEmpty()) {
                   h.revineAndBylineText.setOnClickListener(new ViewOnClickListenerC055110(byline));
                        }
               h.revineAndBylineText.setText(byline.body);
               return;
                    }
           h.revineAndBylineContainer.setVisibility(8);
                }

                
       class ViewOnClickListenerC055110 implements View.OnClickListener {
                    final  VineByline val$byline;

           ViewOnClickListenerC055110(VineByline vineByline) {
                        this.val$byline = vineByline;
                    }

                    @Override 
           public void onClick(View v) {
               LinkDispatcher.dispatch(this.val$byline.entities.get(0).link, FeedAdapter.this.mContext);
                    }
                }

                @Override 
       public synchronized void onPause(boolean focused) {
                    try {
                        this.mLogger.m489d("OnPause {}. {}", Boolean.valueOf(focused), this);
              this.mHasFocus = focused;
              this.mShouldBePlayingPostId = -2147483648L;
              this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
              releaseOtherPlayers(null);
              resetStates(false);
              this.mWasResumed = false;
              if (this.mPlayingPlayer != null) {
                  pausePlayer(this.mPlayingPlayer);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
      public synchronized void onResume(boolean focused) {
                    int currentPosition;
                    try {
                        this.mLogger.m489d("OnResume {}. {}", Boolean.valueOf(focused), this);
              if (!this.mWasResumed) {
                  this.mWasResumed = true;
                  this.mShouldBePlayingPostId = -2147483648L;
                  this.mMuted = MuteUtil.isMuted(this.mContext);
                        }
              this.mHasFocus = focused;
              if (this.mHasFocus && (currentPosition = calculateCurrentPosition()) > 0 && this.mViewHolders.size() > currentPosition) {
                            try {
                      FeedViewHolder h = getHolderFromPosition(currentPosition);
                      if (h != null) {
                          bindVideoImage(h);
                                }
                            } catch (IndexOutOfBoundsException e) {
                      this.mLogger.m500e("Race condition.", (Throwable) e);
                            }
                        }
              playCurrentPosition();
                            
                            
                            
                            
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

      protected FeedViewHolder getHolderFromPosition(int position) {
          WeakReference<FeedViewHolder> p = (WeakReference) this.mViewHolders.get(position);
          if (p != null) {
                        return p.get();
                    }
                    return null;
                }

                @Override 
      public void onDestroy() {
          stopCurrentPlayer();
          releaseOtherPlayers(null);
          this.mLoopCountHandler.getLooper().quit();
          this.mLoopCountThread.interrupt();
          this.mLoopCountThread = null;
                }

                @Override 
      public void onScrollStateChanged(AbsListView view, int scrollState) {
          if (scrollState == 0) {
              playCurrentPosition();
                    }
                }

                @Override 
      public void toggleMute(boolean mute) {
          this.mMuted = mute;
          this.mLogger.m488d("Mute state changed to muted? {}.", Boolean.valueOf(mute));
          SdkVideoView lastPlayer = getLastPlayer();
          if (lastPlayer != null) {
              lastPlayer.setMute(this.mMuted);
                    }
                }

                @Override 
      public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
          this.mLogger.m487d("CALLBACK onVideoPathObtained");
                    for (VideoKey key : videos.keySet()) {
              UrlVideo video = videos.get(key);
              if (video.isValid() && this.mUrlReverse.get(key) != null) {
                  Long pathKey = this.mUrlReverse.get(key);
                  this.mPaths.put(pathKey.longValue(), video.getAbsolutePath());
                  this.mVideoKeys.put(pathKey.longValue(), key);
                            if (this.mUrlReverse.get(key).longValue() == this.mShouldBePlayingPostId) {
                      playCurrentPosition();
                            }
                        }
                    }
                }

      protected void onVideoImageObtained(FeedViewHolder holder, boolean b) {
          holder.isVideoImageLoaded = b;
          playCurrentPosition();
                }

      protected void createSensitiveLisenter(FeedViewHolder h) {
          h.imageListener = new VideoSensitiveImageClickListener(this, this.mSensitiveContentWarningsAcknowledged);
          h.sensitiveTextContainer.setOnClickListener(h.imageListener);
          h.sensitiveTextContainer.setClickable(true);
          h.isVideoImageLoaded = true;
                }

      protected void createInsensitiveListener(FeedViewHolder h) {
          h.sensitiveTextContainer.setVisibility(8);
          h.sensitiveTextContainer.setOnClickListener(null);
          h.sensitiveTextContainer.setClickable(false);
                }

                @Override 
      public SdkVideoView getLastPlayer() {
          return this.mLastPlayer;
                }

                @Override 
      public void onDestroyView() {
          releaseOtherPlayers(null);
                }

                @Override 
      public int getCount() {
          return this.mPosts.size();
                }

                @Override 
      public Object getItem(int i) {
          return getPost(i);
                }

                @Override 
                @Nullable
      public View getView(int i, View view, ViewGroup viewGroup) {
          if (view == null) {
              view = newView(viewGroup);
                    }
          FeedViewHolder h = (FeedViewHolder) view.getTag();
          h.post = getPost(i);
          h.position = i;
          if (h.post != null) {
              bindView(view, h);
                    } else {
              CrashUtil.log("Post null: {} {}", Integer.valueOf(i), Integer.valueOf(this.mPosts.size()));
                    }
          return view;
                }

                @Override 
      public long getItemId(int postition) {
          return postition;
                }

                @Override 
      public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
                    FeedViewHolder h;
          int pinnedHeaderPosition = this.mPinnedHeaderPosition;
          this.mPinnedHeaderListViewChildIndex = ((PinnedHeaderListView) this.mListView).getPositionForPixelLocation(navBottom);
          this.mPinnedHeaderPosition = (this.mListView.getFirstVisiblePosition() - this.mListView.getHeaderViewsCount()) + this.mPinnedHeaderListViewChildIndex;
          View child = this.mListView.getChildAt(this.mPinnedHeaderListViewChildIndex);
          int offset = 0;
          boolean show = true;
          if (child != null && (h = (FeedViewHolder) child.getTag()) != null) {
              View byline = ((ViewGroup) child).getChildAt(0);
              boolean bylineVisible = byline.getVisibility() == 0;
              int viewTop = child.getTop();
              int dividerTop = child.getTop() + h.divider.getTop();
              if (dividerTop < this.mPinnedHeaderHeight + navBottom + this.mDividerScrollAwayPadding) {
                  offset = (dividerTop - this.mPinnedHeaderHeight) - navBottom;
                        }
              if (bylineVisible) {
                  show = viewTop <= navBottom - byline.getHeight();
                        } else if (h.grayPostSpacer.getVisibility() == 0) {
                  show = viewTop <= navBottom - h.grayPostSpacer.getHeight();
                        }
                    }
          return new PinnedHeaderAdapter.PinnedHeaderStatus(offset, this.mPinnedHeaderView == null || pinnedHeaderPosition != this.mPinnedHeaderPosition, show);
                }

                @Override 
      public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
                }

                @Override 
      public View getPinnedHeaderView(View convertView) {
          ViewGroup topLevelView = (ViewGroup) this.mListView.getChildAt(this.mPinnedHeaderListViewChildIndex);
          if (topLevelView == null) {
                        return null;
                    }
          View pinnedView = topLevelView.findViewById(C0257R.id.top_content_container);
          if (pinnedView != null) {
              pinnedView.setBackgroundColor(-1);
                    }
          this.mPinnedHeaderView = pinnedView;
          return pinnedView;
                }

                @Override 
      public int getPinnedHeaderHeight() {
          return this.mPinnedHeaderHeight;
                }

      private void bindHeader(FeedViewHolder h) {
                    int color;
          VinePost post = h.post;
          String usernameString = post.username;
          h.avatarKey = null;
          bindAvatar(h);
          if (usernameString == null) {
              usernameString = "";
              CrashUtil.logException(new VineLoggingException("Invalid user name for post " + post.userId));
                    }
          if (h.post.styledUserName == null) {
              SpannableStringBuilder sb = new SpannableStringBuilder(usernameString);
              ForegroundColorSpan colorSpan = new ForegroundColorSpan(this.mProfileColor);
              Util.safeSetSpan(sb, this.mBoldSpan, 0, usernameString.length(), 33);
              Util.safeSetSpan(sb, colorSpan, 0, usernameString.length(), 33);
              h.post.styledUserName = sb;
                    }
          h.username.setText(h.post.styledUserName);
          h.topContentContainer.setOnClickListener(new ViewOnClickListenerC055211(h));
          long myId = AppController.getInstance(this.mContext).getActiveId();
          if (!h.post.originalFollowingState && h.post.userId != myId) {
              h.avatarFollowContainer.setVisibility(0);
              CompoundButton followButton = h.followButton;
              followButton.setOnClickListener(new ViewOnClickListenerC055312(followButton));
              followButton.setChecked(post.following);
              followButton.setTag(Long.valueOf(post.userId));
              Drawable bg = h.followButton.getBackground();
              if (bg != null) {
                  if (this.mProfileColor != 0) {
                      color = this.mProfileColor;
                            } else {
                      color = this.mVineGreen;
                            }
                  bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                        }
                    } else {
              h.avatarFollowContainer.setVisibility(8);
                    }
          h.userImage.setOnTouchListener(new ViewOnTouchListenerC055413(h));
          int left = h.avatarFollowContainer.getVisibility() != 0 ? this.mPostHeaderPaddingSmall : this.mPostHeaderPaddingLarge;
          h.username.setPadding(left, 0, 0, 0);
          h.timestamp.setPadding(left, 0, 0, 0);
          boolean hasVenue = (post.venueData == null || TextUtils.isEmpty(post.venueData.venueName)) ? false : true;
          if (h.post.dateString == null) {
              h.post.dateString = Util.getRelativeTimeString(this.mContext, post.created, false);
              if (hasVenue) {
                  h.post.dateString = this.mContext.getString(C0257R.string.date_and_location_formatted, h.post.dateString, post.venueData.venueName.trim());
                        }
                    }
          h.timestamp.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, hasVenue ? this.mFeedLocationPinDrawable : null, (Drawable) null);
          h.timestamp.setText(h.post.dateString);
          h.updateCount(post);
                }

                
      class ViewOnClickListenerC055211 implements View.OnClickListener {
                    final  FeedViewHolder val$h;

          ViewOnClickListenerC055211(FeedViewHolder feedViewHolder) {
                        this.val$h = feedViewHolder;
                    }

                    @Override 
          public void onClick(View view) {
              ProfileActivity.start(FeedAdapter.this.mContext, this.val$h.post.userId, FeedAdapter.this.mFollowEventSource);
                    }
                }

                
      class ViewOnClickListenerC055312 implements View.OnClickListener {
                    final  CompoundButton val$followButton;

          ViewOnClickListenerC055312(CompoundButton compoundButton) {
                        this.val$followButton = compoundButton;
                    }

                    @Override 
          public void onClick(View v) {
              Object userId = this.val$followButton.getTag();
              if (userId instanceof Long) {
                  Session session = FeedAdapter.this.mAppController.getActiveSessionReadOnly();
                  if (this.val$followButton.isChecked()) {
                      FeedAdapter.this.mAppController.followUser(session, ((Long) userId).longValue(), true);
                            } else {
                      FeedAdapter.this.mAppController.unfollowUser(session, ((Long) userId).longValue(), true);
                            }
                        }
                    }
                }

                
      class ViewOnTouchListenerC055413 implements View.OnTouchListener {
                    final  FeedViewHolder val$h;

          ViewOnTouchListenerC055413(FeedViewHolder feedViewHolder) {
                        this.val$h = feedViewHolder;
                    }

                    @Override 
          public boolean onTouch(View view, MotionEvent motionEvent) {
                        return !this.val$h.post.originalFollowingState && this.val$h.followButton.dispatchTouchEvent(motionEvent);
                    }
                }

      private class PlayCurrentPositionRunnable implements Runnable {
          private PlayCurrentPositionRunnable() {
                    }

           PlayCurrentPositionRunnable(FeedAdapter x0, C05501 x1) {
              this();
                    }

                    @Override 
          public synchronized void run() {
                        try {
                            int calculatedPosition = FeedAdapter.access$1100(FeedAdapter.this);
                  if (calculatedPosition != -2 && calculatedPosition != -1) {
                      if (!FeedAdapter.access$1200(FeedAdapter.this, calculatedPosition)) {
                          FeedAdapter.this.getVideoPathAndPlayForPosition(calculatedPosition, false);
                                } else {
                          FeedAdapter.this.pauseCurrentPlayer();
                                }
                            } else if (calculatedPosition == -2 && !FeedAdapter.access$1300(FeedAdapter.this)) {
                      FeedAdapter.this.getVideoPathAndPlayForPosition(FeedAdapter.access$100(FeedAdapter.this), false);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

      public void addOlderPosts(ArrayList<VinePost> newPosts) {
          this.mPosts.addAll(newPosts);
          notifyDataSetChanged();
                }

      private void printPostIds(String tag, Collection<VinePost> posts) {
          if (CommonUtil.DEBUG_VERBOSE) {
              long[] ids = new long[posts.size()];
              int m = 0;
                        for (VinePost post : posts) {
                            ids[m] = post.postId;
                  m++;
                        }
              this.mLogger.m507i("{} : {}", tag, ids);
                    }
                }

      public void replacePosts(ArrayList<VinePost> newPosts) {
          this.mPaths.clear();
          this.mVideoKeys.clear();
          this.mPlayers.clear();
          this.mUrlReverse.clear();
          this.mPosts.clear();
          this.mPosts.addAll(newPosts);
          notifyDataSetChanged();
                }

      public int appendPostsOnTop(ArrayList<VinePost> newPosts) {
          printPostIds("newPosts", newPosts);
          printPostIds("oldPosts", this.mPosts);
          HashMap<Long, VinePost> postIds = new HashMap<>();
          boolean wasEmpty = this.mPosts.size() == 0;
                    for (VinePost post : this.mPosts) {
              postIds.put(Long.valueOf(post.postId), post);
                    }
          ArrayList<VinePost> resolvedPosts = new ArrayList<>();
          int added = 0;
                    for (VinePost post2 : newPosts) {
              if (postIds.get(Long.valueOf(post2.postId)) == null) {
                  resolvedPosts.add(post2);
                  added++;
                        }
                    }
          if (added == newPosts.size()) {
              return -1;
                    }
                    for (VinePost post3 : this.mPosts) {
              VinePost updated = postIds.get(Long.valueOf(post3.postId));
              if (updated == null) {
                  resolvedPosts.add(post3);
                        } else {
                  resolvedPosts.add(updated);
                        }
                    }
          this.mPosts.clear();
          this.mPosts.addAll(resolvedPosts);
          printPostIds("newPosts", this.mPosts);
          if (!wasEmpty) {
              int index = this.mListView.getFirstVisiblePosition();
              int foundFirstChild = -1;
                        for (int i = 0; i < this.mListView.getChildCount(); i++) {
                  View c = this.mListView.getChildAt(i);
                  if (c != null && (c.getTag() instanceof FeedViewHolder)) {
                      foundFirstChild = i;
                                break;
                            }
                        }
              int extraNeeded = 1;
              if (foundFirstChild == 2) {
                  extraNeeded = 2;
                        }
              SLog.m465i("First child is at index {}", Integer.valueOf(foundFirstChild));
              int hvc = this.mListView.getHeaderViewsCount();
              View v = this.mListView.getChildAt(hvc);
              int top = v == null ? 0 : v.getTop();
              notifyDataSetChanged();
              this.mListView.setSelectionFromTop(index + added + extraNeeded, top);
              SLog.m470i("Scroll: {}, {}, {}, {}", new Object[]{Integer.valueOf(index), Integer.valueOf(added), Integer.valueOf(hvc), Integer.valueOf(top)});
              return added;
                    }
          notifyDataSetChanged();
          return 0;
                }

      public void mergePosts(ArrayList<VinePost> newPosts) {
          printPostIds("newPosts", newPosts);
          printPostIds("oldPosts", this.mPosts);
          HashMap<Long, Integer> usedViewPostIds = new HashMap<>();
          ArrayList<VinePost> posts = new ArrayList<>(this.mPosts);
          int size = posts.size();
                    for (int i = 0; i < size; i++) {
              usedViewPostIds.put(Long.valueOf(posts.get(i).postId), Integer.valueOf(i));
                    }
                    for (VinePost newPost : newPosts) {
              Integer position = usedViewPostIds.get(Long.valueOf(newPost.postId));
              if (position == null) {
                  posts.add(newPost);
                        } else {
                  posts.set(position.intValue(), newPost);
                        }
                    }
          this.mLogger.m507i("{} : {}", "activePosts", usedViewPostIds.keySet());
          this.mPosts.clear();
          this.mPosts.addAll(posts);
          printPostIds("mergedLists", this.mPosts);
          notifyDataSetChanged();
                }

      public void mergePost(VinePost post) {
          ArrayList<VinePost> posts = new ArrayList<>();
          posts.add(post);
          mergePosts(posts);
                }

      public void mergeMyPost(VinePost post) {
          if (post != null) {
                        for (VinePost p : this.mPosts) {
                  if (p != null && p.postId == post.postId) {
                      return;
                            }
                        }
              this.mPosts.add(0, post);
              this.mPaths.clear();
              this.mVideoKeys.clear();
              this.mUrlReverse.clear();
              this.mPlayers.clear();
              notifyDataSetChanged();
              playCurrentPosition();
                    }
                }

      private class VideoContaineTouchListener extends OnListVideoClickListener implements View.OnTouchListener {
                    private int mLastA;
                    private long mLastTapTime;
                    private float mLastX;
                    private float mLastY;
                    private final Random mRandom;
                    private final FeedViewHolder mTag;

          public VideoContaineTouchListener(FeedViewHolder tag) {
              super(FeedAdapter.this);
              this.mRandom = new Random();
              this.mTag = tag;
                    }

                    @Override 
          public void onClick(View v) {
              super.onClick(v);
                        if (System.currentTimeMillis() - this.mLastTapTime <= 450) {
                  if (this.mTag.videoView.isInPlaybackState() && (FeedAdapter.access$1400(FeedAdapter.this) || !this.mTag.videoView.isPaused())) {
                      if (this.mTag.doubleTapView.getVisibility() != 0) {
                          this.mTag.doubleTapView.setVisibility(0);
                                }
                      int angle = nextAngle();
                      this.mTag.doubleTapView.add(this.mRandom.nextInt(50) % 10 == 0, this.mLastX, this.mLastY, angle);
                      this.mLastA = angle;
                      this.mTag.doubleTapView.postInvalidate();
                      FlurryUtils.trackDoubleTap(this.mTag.post.postId);
                      FeedAdapter.this.mTimelineFragment.like(this.mTag.post, false);
                            }
                  this.mLastTapTime = 0L;
                  return;
                        }
              this.mLastTapTime = System.currentTimeMillis();
                    }

          private int nextAngle() {
                        int currentAngle;
              if (this.mLastA == 0) {
                  this.mLastA = this.mRandom.nextInt(60) - 30;
                        }
              do {
                  currentAngle = this.mRandom.nextInt(60) - 30;
              } while (Math.abs(this.mLastA - currentAngle) < 10);
              return currentAngle;
                    }

                    @Override 
          public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction() == 0) {
                  this.mLastX = event.getX();
                  this.mLastY = event.getY();
                  return false;
                        }
              return false;
                    }
                }
            }
