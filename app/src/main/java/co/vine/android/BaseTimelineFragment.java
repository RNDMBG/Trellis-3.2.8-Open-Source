            package co.vine.android;

            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.support.annotation.NonNull;
            import android.support.v4.app.FragmentActivity;
            import android.support.v7.app.ActionBarActivity;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AbsListView;
            import android.widget.ImageButton;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineRepost;
            import co.vine.android.cache.LikeCache;
            import co.vine.android.cache.RevineCache;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.service.PendingAction;
            import co.vine.android.service.VineService;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.FeedAdapter;
            import co.vine.android.widget.GenericVideoAdapter;
            import co.vine.android.widget.PinnedHeaderListView;
            import co.vine.android.widget.SpanClickListener;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public abstract class BaseTimelineFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener {
                protected static final int REQUEST_CODE_OPTIONS = 10;
                private static final String STATE_LIKE_CACHE = "state_like_cache";
                private static final String STATE_REVINE_CACHE = "state_revine_cache";
                private ActionBarActivity mCallback;
                protected ViewGroup mClientFlagsBannerParent;
                protected boolean mClientFlagsBannerWasDismissed;
                protected GenericVideoAdapter mFeedAdapter;
                protected boolean mFetched;
                private String mFlurryEventSource;
                protected final HashSet<View> mHeaderParents;
                protected int mLastFetchType;
                protected LikeCache mLikeCache;
                protected final SLogger mLogger;
                protected final BroadcastReceiver mMergePostReceiver;
                private boolean mMergePostReceiverRegistered;
                private final BroadcastReceiver mMuteChangeReceiver;
                protected RevineCache mRevineCache;
                private long mLastRevineVersion = 0L;
                protected final VineSpanClicker mVineSpanClicker;
                public static final String ACTION_COMMENT_COUNT_CHANGED = "co.vine.android.COMMENT_COUNT_CHANGED";
                private final BroadcastReceiver mCommentCountReceiver;

                protected abstract String fetchPosts(int i, long j, boolean z, UrlCachePolicy urlCachePolicy);

/* 57 */        public BaseTimelineFragment() {
/* 67 */            String logTag = getClass().getSimpleName();
/* 68 */            this.mLogger = SLogger.getLogger(logTag.replace("Fragment", ""));
/* 80 */            this.mVineSpanClicker = new VineSpanClicker(this);
/* 83 */            this.mHeaderParents = new HashSet<>();
/* 86 */            this.mMergePostReceiverRegistered = false;
/* 89 */            this.mMergePostReceiver = new C01581();
/* 90 */            this.mCommentCountReceiver = new C01583();
/* 111 */           this.mMuteChangeReceiver = new C01592();
                }

/* 57 */        static /* synthetic */ ActionBarActivity access$000(BaseTimelineFragment x0) {
/* 58 */            return x0.mCallback;
                }

/* 57 */        static /* synthetic */ void access$100(BaseTimelineFragment x0) {
/* 58 */            x0.hideClientFlagsBanner();
                }

                /* JADX INFO: renamed from: co.vine.android.BaseTimelineFragment$1 */
/* 87 */        class C01581 extends BroadcastReceiver {
/* 88 */            C01581() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 91 */            public void onReceive(Context context, Intent intent) {
/* 92 */                if (intent != null && VineService.BC_ACTION_MERGE_POST_INTO_FEED.equals(intent.getAction())) {
/* 94 */                    VinePost post = (VinePost) intent.getExtras().getParcelable("post");
/* 95 */                    ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).mergeMyPost(post);
                        }
                    }
                }

                class C01583 extends BroadcastReceiver {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null && ACTION_COMMENT_COUNT_CHANGED.equals(intent.getAction()) && BaseTimelineFragment.this.mFeedAdapter != null) {
                            BaseTimelineFragment.this.mFeedAdapter.incrementCommentCount(intent.getLongExtra("post_id", -1L));
                        }
                    }
                }

/* 101 */       public void registerMergePostReceiver() {
/* 102 */           this.mMergePostReceiverRegistered = true;
/* 103 */           getActivity().registerReceiver(this.mMergePostReceiver, VineService.SHOW_POST_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                /* JADX INFO: renamed from: co.vine.android.BaseTimelineFragment$2 */
/* 109 */       class C01592 extends BroadcastReceiver {
/* 110 */           C01592() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 114 */           public void onReceive(Context context, Intent intent) {
/* 115 */               if (intent == null || intent.getAction() == null) {
/* 116 */                   CrashUtil.log("Something wrong has happened");
                        } else {
/* 118 */                   boolean mute = intent.getAction().equals(MuteUtil.ACTION_CHANGED_TO_MUTE);
/* 119 */                   BaseTimelineFragment.this.mFeedAdapter.toggleMute(mute);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, android.widget.AbsListView.OnScrollListener
/* 126 */       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/* 127 */           super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

/* 130 */       public void onRevineBySpanClicked(long userId) {
/* 131 */           this.mVineSpanClicker.onRevineBySpanClicked(userId);
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 135 */       public void onResume() {
/* 136 */           super.onResume();
/* 137 */           getActivity().registerReceiver(this.mMuteChangeReceiver, MuteUtil.MUTE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    reconcileRevineStates();
                }

                /**
                 * Applies revine/unrevine changes that arrived while this fragment was
                 * paused (its listener was unregistered, so it missed the callbacks).
                 * Without this, share buttons stay green and revined posts linger until
                 * the next manual reload.
                 */
                protected void reconcileRevineStates() {
                    AppController app = this.mAppController;
                    if (app == null) {
                        return;
                    }
                    long version = app.getRevineVersion();
                    if (version == this.mLastRevineVersion) {
                        return;
                    }
                    this.mLastRevineVersion = version;
                    HashMap<Long, Boolean> states = app.getRevineStates();
                    if (states == null || states.isEmpty()) {
                        return;
                    }
                    for (Map.Entry<Long, Boolean> e : states.entrySet()) {
                        long postId = e.getKey().longValue();
                        boolean revined = e.getValue().booleanValue();
                        if (revined) {
                            this.mRevineCache.revine(postId);
                        } else {
                            this.mRevineCache.unRevine(postId);
                            this.mRevineCache.removeMyRepostId(postId);
                        }
                        if (this.mFeedAdapter != null) {
                            this.mFeedAdapter.updateRevinedCount(postId, revined);
                        }
                        onRevineStateReconciled(postId, revined);
                    }
                    if (this.mAdapter != null) {
                        this.mAdapter.notifyDataSetChanged();
                    }
                }

                /** Hook for subclasses (e.g. profile drops unrevined posts). */
                protected void onRevineStateReconciled(long postId, boolean revined) {
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 141 */       public void onPause() {
/* 142 */           super.onPause();
/* 143 */           getActivity().unregisterReceiver(this.mMuteChangeReceiver);
                }

/* 146 */       public void addHeaderView(View view, Object data, boolean isSeletable) {
/* 147 */           this.mListView.addHeaderView(view, data, isSeletable);
/* 148 */           this.mHeaderParents.add(view);
                }

/* 151 */       public HashSet<View> getHeaderViewParents() {
/* 152 */           return this.mHeaderParents;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 156 */       public void onCreate(Bundle savedInstanceState) {
/* 157 */           super.onCreate(savedInstanceState);
/* 158 */           this.mAppSessionListener = new TimeLineSessionListener();
/* 160 */           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIKE_CACHE)) {
/* 161 */               this.mLikeCache = (LikeCache) savedInstanceState.getParcelable(STATE_LIKE_CACHE);
/* 162 */               this.mRevineCache = (RevineCache) savedInstanceState.getParcelable(STATE_REVINE_CACHE);
                    } else {
/* 164 */               this.mLikeCache = new LikeCache();
/* 165 */               this.mRevineCache = new RevineCache();
                    }
                }

/* 169 */       protected void setFlurryEventSource(String source) {
/* 170 */           this.mFlurryEventSource = source;
/* 171 */           this.mVineSpanClicker.setFlurryEventSource(source);
                }

/* 179 */       public boolean onBackPressed() {
/* 180 */           return this.mFeedAdapter.onBackPressed();
                }

                @Override // android.support.v4.app.Fragment
/* 184 */       public void onAttach(Activity activity) {
/* 185 */           super.onAttach(activity);
/* 186 */           if (activity instanceof ActionBarActivity) {
/* 187 */               this.mCallback = (ActionBarActivity) activity;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 192 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 193 */           super.onActivityCreated(savedInstanceState);
/* 194 */           Activity activity = getActivity();
/* 195 */           if (getActivity() instanceof HomeTabActivity) {
/* 196 */               setUpUnderNavHeader();
                    }
/* 198 */           this.mFeedAdapter = new FeedAdapter(activity, this.mListView, this.mLikeCache, this.mRevineCache, this, this.mFlurryEventSource, this.mLogger);
/* 201 */           this.mAdapter = this.mFeedAdapter;
                getActivity().registerReceiver(this.mCommentCountReceiver, new android.content.IntentFilter(ACTION_COMMENT_COUNT_CHANGED), CrossConstants.BROADCAST_PERMISSION, null);
                }

/* 204 */       public void setUpUnderNavHeader() {
/* 205 */           View header = LayoutInflater.from(getActivity()).inflate(C0257R.layout.under_nav_header, (ViewGroup) null);
/* 206 */           ((PinnedHeaderListView) this.mListView).setRefreshHeader(header, getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height));
/* 207 */           addHeaderView(header, null, false);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 211 */       public void onDestroyView() {
/* 212 */           this.mFeedAdapter.onDestroyView();
                getActivity().unregisterReceiver(this.mCommentCountReceiver);
/* 213 */           if (this.mMergePostReceiverRegistered) {
/* 214 */               getActivity().unregisterReceiver(this.mMergePostReceiver);
                    }
/* 216 */           super.onDestroyView();
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 220 */       public void onSaveInstanceState(Bundle outState) {
/* 221 */           super.onSaveInstanceState(outState);
/* 222 */           outState.putParcelable(STATE_LIKE_CACHE, this.mLikeCache);
/* 223 */           outState.putParcelable(STATE_REVINE_CACHE, this.mRevineCache);
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 227 */       public void onMoveTo(int oldPosition) {
/* 228 */           super.onMoveTo(oldPosition);
/* 229 */           fetchContent(3, false, UrlCachePolicy.FORCE_REFRESH);
/* 232 */           this.mFeedAdapter.onResume(isFocused());
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 236 */       public void onMoveAway(int newPosition) {
/* 237 */           super.onMoveAway(newPosition);
/* 238 */           this.mFeedAdapter.onPause(isFocused());
                }

/* 248 */       protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
/* 249 */           this.mLastFetchType = fetchType;
/* 250 */           if (!hasPendingRequest(fetchType)) {
/* 253 */               this.mFetched = true;
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
/* 259 */                       this.mNextPage = 1;
/* 260 */                       this.mAnchor = 0L;
                                break;
                        }
/* 265 */               if (userInitiated) {
/* 266 */                   showProgress(fetchType);
                        }
/* 268 */               addRequest(fetchPosts(this.mNextPage, this.mAnchor, userInitiated, cachePolicy), fetchType);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 274 */       protected void refresh() {
                        clearPendingRequests(2);
/* 275 */           hideProgress(2);
/* 276 */           fetchContent(2, true, UrlCachePolicy.FORCE_REFRESH);
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 279 */       public void onSpanClicked(View view, int type, Object tag) {
/* 280 */           this.mVineSpanClicker.onSpanClicked(view, type, tag);
                }

                @Override // android.view.View.OnClickListener
/* 284 */       public void onClick(View view) {
/* 285 */           int id = view.getId();
/* 286 */           if (id == C0257R.id.like) {
/* 287 */               like((VinePost) view.getTag(), true);
/* 310 */               return;
                    }
/* 288 */           if (id == C0257R.id.more_options) {
/* 289 */               VinePost post = (VinePost) view.getTag();
/* 290 */               FragmentActivity activity = getActivity();
/* 291 */               if (activity != null) {
                            Intent intent = PostOptionsDialogActivity.getMoreIntent(post, activity, post.userId == this.mAppController.getActiveId(), false);
/* 294 */                   activity.startActivityForResult(intent, 10);
/* 310 */                   return;
                        }
/* 310 */               return;
                    }
/* 296 */           if (id == C0257R.id.share || id == C0257R.id.share_post) {
/* 297 */               VinePost post2 = (VinePost) view.getTag();
/* 298 */               FragmentActivity activity2 = getActivity();
/* 299 */               if (activity2 != null) {
                            long myRepostId = this.mRevineCache.getMyRepostId(post2.postId);
                            if (myRepostId <= 0) {
                                myRepostId = post2.myRepostId;
                            }
                            boolean wasRevined = this.mRevineCache.isRevined(post2.postId) || myRepostId > 0 || post2.myRepostId > 0 || post2.isRevined();
                            activity2.startActivityForResult(PostOptionsDialogActivity.getShareIntent(post2, activity2, post2.userId == this.mAppController.getActiveId(), myRepostId, post2.getVineRepostRepostId(), wasRevined, this.mFlurryEventSource), 10);
/* 310 */                   return;
                        }
/* 310 */               return;
                    }
/* 306 */           if (id == C0257R.id.clear_client_flags_banner) {
/* 307 */               hideClientFlagsBanner();
/* 308 */               this.mClientFlagsBannerWasDismissed = true;
                    }
                }

/* 312 */       public void like(VinePost post, boolean unlikeIfLiked) {
/* 313 */           long postId = post.postId;
/* 314 */           long repostId = post.getVineRepostRepostId();
/* 315 */           if (this.mLikeCache.isLike(postId)) {
/* 316 */               if (unlikeIfLiked) {
/* 318 */                   addRequest(this.mAppController.unlikePost(this.mFlurryEventSource, this.mAppController.getActiveSession(), postId, repostId, false));
/* 322 */                   this.mLikeCache.unlike(postId);
/* 323 */                   this.mFeedAdapter.showUnlikedByMe(postId);
/* 335 */                   return;
                        }
/* 335 */               return;
                    }
/* 327 */           addRequest(this.mAppController.likePost(this.mFlurryEventSource, this.mAppController.getActiveSession(), postId, repostId, this.mAppController.getActiveId(), this.mAppController.getActiveSession().getName(), false));
/* 332 */           this.mLikeCache.like(postId);
/* 333 */           this.mFeedAdapter.showLikedByMe(postId);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 338 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 10:
/* 341 */                   processPostOptionsResult(PostOptionsDialogActivity.processActivityResult(this.mAppController, getActivity(), resultCode, data));
                            break;
                        default:
/* 346 */                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, android.widget.AbsListView.OnScrollListener
/* 353 */       public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    try {
                        super.onScrollStateChanged(view, scrollState);
/* 355 */               this.mFeedAdapter.onScrollStateChanged(view, scrollState);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 360 */       public void onDestroy() {
                    try {
/* 361 */               super.onDestroy();
/* 362 */               this.mFeedAdapter.onDestroy();
                    } catch (Exception e) {
/* 364 */               CrashUtil.logException(e, "Failed to remove timeline fragment", new Object[0]);
                    }
                }

/* 368 */       protected boolean trackNewCount() {
/* 369 */           return false;
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 373 */       protected void onScrollLastItem() {
/* 374 */           if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
/* 376 */               this.mFeedAdapter.onPause(isFocused());
/* 377 */               fetchContent(1, true, UrlCachePolicy.NETWORK_THEN_CACHE);
/* 378 */               showProgress(1);
/* 379 */               FlurryUtils.trackTimeLinePageScroll(getClass().getName(), this.mNextPage);
                    }
                }

/* 384 */       protected void showClientFlagsBanner() {
/* 385 */           if (this.mClientFlagsBannerParent == null) {
/* 386 */               ListView listView = this.mListView;
/* 387 */               RelativeLayout bannerParent = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(C0257R.layout.zero_rating_banner, (ViewGroup) listView, false);
/* 390 */               ImageButton clearButton = (ImageButton) bannerParent.findViewById(C0257R.id.clear_client_flags_banner);
/* 391 */               clearButton.setColorFilter(getResources().getColor(C0257R.color.clear_client_flags_banner));
/* 392 */               clearButton.setOnClickListener(this);
/* 394 */               listView.addHeaderView(bannerParent);
/* 395 */               this.mClientFlagsBannerParent = bannerParent;
                    }
/* 398 */           ViewGroup container = (ViewGroup) this.mClientFlagsBannerParent.findViewById(C0257R.id.collapsible);
/* 399 */           int visibility = container.getVisibility();
/* 400 */           container.setVisibility(0);
/* 401 */           if (visibility != 0) {
                        this.mFeedAdapter.notifyDataSetChanged();
                    }
                }

/* 404 */       protected void populateClientFlagsBanner(String title, String text) {
/* 405 */           if (!TextUtils.isEmpty(title)) {
/* 406 */               setBannerText(2131427459, title);
                    }
/* 408 */           if (!TextUtils.isEmpty(text)) {
/* 409 */               setBannerText(2131427465, text);
                    }
                }

/* 413 */       protected void setBannerText(int id, @NonNull String s) {
/* 414 */           if (this.mClientFlagsBannerParent != null) {
/* 415 */               ((TextView) this.mClientFlagsBannerParent.findViewById(id)).setText(s);
                    }
                }

/* 419 */       private void hideClientFlagsBanner() {
/* 420 */           if (this.mClientFlagsBannerParent != null) {
/* 421 */               this.mClientFlagsBannerParent.findViewById(C0257R.id.collapsible).setVisibility(8);
                    }
                }

/* 425 */       protected void fetchInitialRequest(UrlCachePolicy cachePolicy) {
/* 426 */           fetchContent(3, cachePolicy.mCacheTakesPriority, cachePolicy);
                }

/* 430 */       public void onTopViewBinded() {
                }

/* 431 */       class TimeLineSessionListener extends AppSessionListener {
/* 432 */           TimeLineSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 436 */           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
/* 437 */               BaseTimelineFragment.this.mFeedAdapter.onVideoPathObtained(videos);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 441 */           public void onVideoPathError(VideoKey key, HttpResult result) {
/* 442 */               SLog.m455e("Download of video failed: " + result.reasonPhrase + " key: " + key.url);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 446 */           public void onPhotoImageError(ImageKey key, HttpResult result) {
/* 447 */               SLog.m455e("Download of image failed: " + result.reasonPhrase + " key: " + key.url);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 451 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 452 */               BaseTimelineFragment.this.mFeedAdapter.setImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 460 */           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
/* 461 */               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
/* 462 */               if (req != null) {
/* 463 */                   if (memory && statusCode == 200) {
/* 464 */                       if (SLog.sLogsOn && BaseTimelineFragment.this.mFeedAdapter.getStartLoadingTime() > 0) {
/* 465 */                           SLog.m465i("Time since started loading to end fetch: {}ms", Long.valueOf(BaseTimelineFragment.this.mFeedAdapter.timeSinceStartLoadingTime()));
                                }
/* 469 */                       if (posts != null) {
/* 470 */                           BaseTimelineFragment.this.mNextPage = next;
/* 471 */                           BaseTimelineFragment.this.mAnchor = anchor;
/* 472 */                           if (req.fetchType == 1) {
/* 473 */                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).addOlderPosts(posts);
                                    } else if (cachePolicy.mNetworkDataAllowed && !cachePolicy.mUseExpiredDataAllowedIfNetworkIsDown) {
/* 476 */                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).replacePosts(posts);
                                    } else if (!cachePolicy.mNetworkDataAllowed) {
/* 479 */                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).mergePosts(posts);
                                    } else {
/* 481 */                               BaseTimelineFragment.this.onPostUpdatesCameIn(posts);
                                    }
/* 485 */                           BaseTimelineFragment.this.mAppController.saveLoadedPosts(posts, type, tag, pageType, next, previous, anchor, false);
                                }
                            }
/* 489 */                   if (!cachePolicy.mNetworkDataAllowed) {
                                // The cache phase is over; clear its progress before chaining
                                // the network phase. Returning early here without hiding
                                // leaves the pull/fullscreen indicator spinning when the
                                // chained request is skipped or loses its callback
                                // (seen as a reload loop after deleting a post).
                                BaseTimelineFragment.this.hideProgress(req.fetchType);
                                BaseTimelineFragment.this.fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                            switch (req.fetchType) {
                                case 3:
/* 497 */                           if (BaseTimelineFragment.this.mAdapter != null && BaseTimelineFragment.this.mAdapter.isEmpty() && count == 0) {
                                        BaseTimelineFragment.this.hideProgress(req.fetchType);
                                        BaseTimelineFragment.this.showSadface(true);
                                    } else {
/* 501 */                               BaseTimelineFragment.this.hideProgress(req.fetchType);
                                        BaseTimelineFragment.this.showSadface(false);
                                    }
                                    break;
                                default:
/* 506 */                           BaseTimelineFragment.this.hideProgress(req.fetchType);
                                    break;
                            }
                        }
/* 511 */               if (!TextUtils.isEmpty(title) && BaseTimelineFragment.access$000(BaseTimelineFragment.this).getSupportActionBar() != null) {
/* 512 */                   BaseTimelineFragment.access$000(BaseTimelineFragment.this).getSupportActionBar().setTitle(title);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 518 */           public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
/* 519 */               super.onGetCommentsComplete(reqId, statusCode, reasonPhrase, nextPage, anchor, comments);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 523 */           public void onTrimMemory(int level) {
/* 524 */               super.onTrimMemory(level);
/* 525 */               GenericVideoAdapter adapter = BaseTimelineFragment.this.mFeedAdapter;
/* 526 */               if (adapter != null) {
/* 527 */                   adapter.onTrimMemory(level);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 532 */           public void onLowMemory() {
/* 533 */               super.onLowMemory();
/* 534 */               GenericVideoAdapter adapter = BaseTimelineFragment.this.mFeedAdapter;
/* 535 */               if (adapter != null) {
/* 536 */                   adapter.onLowMemory();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 542 */           public void onReportPostComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 543 */               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
/* 544 */               if (req != null) {
/* 545 */                   if (statusCode == 200) {
/* 546 */                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), C0257R.string.post_reported);
                            } else {
/* 548 */                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), C0257R.string.post_reported_error);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 554 */           public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
/* 555 */               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
                    if (statusCode >= 200 && statusCode < 300) {
                            if (BaseTimelineFragment.this.mFeedAdapter instanceof FeedAdapter) {
                                ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).removePost(postId);
                            } else if (BaseTimelineFragment.this.mFeedAdapter != null) {
                                BaseTimelineFragment.this.mFeedAdapter.notifyDataSetChanged();
                            }
                            if (BaseTimelineFragment.this.mAdapter != null) {
                                BaseTimelineFragment.this.mAdapter.notifyDataSetChanged();
                            }
                    }
                    if (req != null) {
                        if (statusCode >= 200 && statusCode < 300) {
/* 558 */                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), C0257R.string.post_deleted);
                        } else {
/* 560 */                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), C0257R.string.post_deleted_error);
                        }
                    }
                }

                    @Override // co.vine.android.client.AppSessionListener
/* 566 */           public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 567 */               BaseTimelineFragment.this.removeRequest(reqId);
/* 568 */               if (statusCode >= 200 && statusCode < 300) {
                      BaseTimelineFragment.this.mLikeCache.like(postId);
                      BaseTimelineFragment.this.mFeedAdapter.showLikedByMe(postId);
                  } else {
/* 570 */                   BaseTimelineFragment.this.mLikeCache.removeEntry(postId);
/* 571 */                   BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 577 */           public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 578 */               BaseTimelineFragment.this.removeRequest(reqId);
/* 579 */               if (statusCode >= 200 && statusCode < 300) {
                      BaseTimelineFragment.this.mLikeCache.unlike(postId);
                      BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                  } else {
/* 580 */                   if (statusCode == 404) {
/* 583 */                       BaseTimelineFragment.this.mLikeCache.removeEntry(postId);
/* 584 */                       BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                            } else {
/* 586 */                       BaseTimelineFragment.this.mLikeCache.like(postId);
/* 587 */                       BaseTimelineFragment.this.mFeedAdapter.showLikedByMe(postId);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 594 */           public void onRevine(String reqId, int statusCode, String reasonPhrase, long postId, VineRepost repost) {
/* 595 */               BaseTimelineFragment.this.removeRequest(reqId);
/* 596 */               if (statusCode >= 200 && statusCode < 300) {
/* 598 */                   BaseTimelineFragment.this.mRevineCache.revine(postId);
/* 599 */                   if (repost != null && repost.repostId > 0) {
/* 600 */                       BaseTimelineFragment.this.mRevineCache.putMyRepostId(postId, repost.repostId);
                            }
/* 600 */                   BaseTimelineFragment.this.mAdapter.notifyDataSetChanged();
/* 601 */                   BaseTimelineFragment.this.mFeedAdapter.updateRevinedCount(postId, true);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 607 */           public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 608 */               BaseTimelineFragment.this.removeRequest(reqId);
/* 609 */               if (statusCode >= 200 && statusCode < 300) {
/* 611 */                   BaseTimelineFragment.this.mRevineCache.unRevine(postId);
/* 612 */                   BaseTimelineFragment.this.mRevineCache.removeMyRepostId(postId);
/* 613 */                   BaseTimelineFragment.this.mAdapter.notifyDataSetChanged();
/* 614 */                   BaseTimelineFragment.this.mFeedAdapter.updateRevinedCount(postId, false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 620 */           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
/* 621 */               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
/* 622 */               if (req != null) {
/* 623 */                   BaseTimelineFragment.this.mPendingRequestHelper.onCaptchaRequired(BaseTimelineFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 630 */           public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, VineClientFlags clientFlags, boolean showBanner) {
/* 631 */               if (statusCode == 200 && BaseTimelineFragment.this.isFocused() && !BaseTimelineFragment.this.mClientFlagsBannerWasDismissed) {
/* 632 */                   if (showBanner) {
/* 633 */                       BaseTimelineFragment.this.showClientFlagsBanner();
/* 634 */                       BaseTimelineFragment.this.populateClientFlagsBanner(clientFlags.messageTitle, clientFlags.messageText);
                            } else {
/* 636 */                       BaseTimelineFragment.access$100(BaseTimelineFragment.this);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 642 */           public void onMobileDataNetworkActivated() {
/* 643 */               if (ClientFlagsHelper.shouldShowClientFlagsBanner(BaseTimelineFragment.this.getActivity()) && BaseTimelineFragment.this.isFocused()) {
/* 644 */                   BaseTimelineFragment.this.showClientFlagsBanner();
/* 645 */                   SharedPreferences prefs = Util.getDefaultSharedPrefs(BaseTimelineFragment.this.getActivity());
/* 646 */                   String title = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TITLE, null);
/* 647 */                   String text = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TEXT, null);
/* 648 */                   BaseTimelineFragment.this.populateClientFlagsBanner(title, text);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 653 */           public void onAbortAllRequestsComplete() {
/* 654 */               BaseTimelineFragment.this.hideProgress(2);
/* 655 */               BaseTimelineFragment.this.fetchContent(2, false, UrlCachePolicy.CACHE_THEN_NETWORK);
                    }
                }

/* 659 */       protected void onPostUpdatesCameIn(ArrayList<VinePost> posts) {
/* 660 */           ((FeedAdapter) this.mFeedAdapter).mergePosts(posts);
                }
            }
