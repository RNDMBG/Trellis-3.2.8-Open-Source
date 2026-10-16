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
            
            
    public abstract class BaseTimelineFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener {
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

        public BaseTimelineFragment() {
            String logTag = getClass().getSimpleName();
            this.mLogger = SLogger.getLogger(logTag.replace("Fragment", ""));
            this.mVineSpanClicker = new VineSpanClicker(this);
            this.mHeaderParents = new HashSet<>();
            this.mMergePostReceiverRegistered = false;
            this.mMergePostReceiver = new C01581();
            this.mCommentCountReceiver = new C01583();
           this.mMuteChangeReceiver = new C01592();
                }

        static  ActionBarActivity access$000(BaseTimelineFragment x0) {
            return x0.mCallback;
                }

        static  void access$100(BaseTimelineFragment x0) {
            x0.hideClientFlagsBanner();
                }

                
        class C01581 extends BroadcastReceiver {
            C01581() {
                    }

                    @Override 
            public void onReceive(Context context, Intent intent) {
                if (intent != null && VineService.BC_ACTION_MERGE_POST_INTO_FEED.equals(intent.getAction())) {
                    VinePost post = (VinePost) intent.getExtras().getParcelable("post");
                    ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).mergeMyPost(post);
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

       public void registerMergePostReceiver() {
           this.mMergePostReceiverRegistered = true;
           getActivity().registerReceiver(this.mMergePostReceiver, VineService.SHOW_POST_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                
       class C01592 extends BroadcastReceiver {
           C01592() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               if (intent == null || intent.getAction() == null) {
                   CrashUtil.log("Something wrong has happened");
                        } else {
                   boolean mute = intent.getAction().equals(MuteUtil.ACTION_CHANGED_TO_MUTE);
                   BaseTimelineFragment.this.mFeedAdapter.toggleMute(mute);
                        }
                    }
                }

                @Override 
       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
           super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

       public void onRevineBySpanClicked(long userId) {
           this.mVineSpanClicker.onRevineBySpanClicked(userId);
                }

                @Override 
       public void onResume() {
           super.onResume();
           getActivity().registerReceiver(this.mMuteChangeReceiver, MuteUtil.MUTE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    reconcileRevineStates();
                }

                





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

                
                protected void onRevineStateReconciled(long postId, boolean revined) {
                }

                @Override 
       public void onPause() {
           super.onPause();
           getActivity().unregisterReceiver(this.mMuteChangeReceiver);
                }

       public void addHeaderView(View view, Object data, boolean isSeletable) {
           this.mListView.addHeaderView(view, data, isSeletable);
           this.mHeaderParents.add(view);
                }

       public HashSet<View> getHeaderViewParents() {
           return this.mHeaderParents;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           this.mAppSessionListener = new TimeLineSessionListener();
           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIKE_CACHE)) {
               this.mLikeCache = (LikeCache) savedInstanceState.getParcelable(STATE_LIKE_CACHE);
               this.mRevineCache = (RevineCache) savedInstanceState.getParcelable(STATE_REVINE_CACHE);
                    } else {
               this.mLikeCache = new LikeCache();
               this.mRevineCache = new RevineCache();
                    }
                }

       protected void setFlurryEventSource(String source) {
           this.mFlurryEventSource = source;
           this.mVineSpanClicker.setFlurryEventSource(source);
                }

       public boolean onBackPressed() {
           return this.mFeedAdapter.onBackPressed();
                }

                @Override 
       public void onAttach(Activity activity) {
           super.onAttach(activity);
           if (activity instanceof ActionBarActivity) {
               this.mCallback = (ActionBarActivity) activity;
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           Activity activity = getActivity();
           if (getActivity() instanceof HomeTabActivity) {
               setUpUnderNavHeader();
                    }
           this.mFeedAdapter = new FeedAdapter(activity, this.mListView, this.mLikeCache, this.mRevineCache, this, this.mFlurryEventSource, this.mLogger);
           this.mAdapter = this.mFeedAdapter;
                getActivity().registerReceiver(this.mCommentCountReceiver, new android.content.IntentFilter(ACTION_COMMENT_COUNT_CHANGED), CrossConstants.BROADCAST_PERMISSION, null);
                }

       public void setUpUnderNavHeader() {
           View header = LayoutInflater.from(getActivity()).inflate(R.layout.under_nav_header, (ViewGroup) null);
           ((PinnedHeaderListView) this.mListView).setRefreshHeader(header, getResources().getDimensionPixelSize(R.dimen.tabbar_height));
           addHeaderView(header, null, false);
                }

                @Override 
       public void onDestroyView() {
           this.mFeedAdapter.onDestroyView();
                getActivity().unregisterReceiver(this.mCommentCountReceiver);
           if (this.mMergePostReceiverRegistered) {
               getActivity().unregisterReceiver(this.mMergePostReceiver);
                    }
           super.onDestroyView();
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putParcelable(STATE_LIKE_CACHE, this.mLikeCache);
           outState.putParcelable(STATE_REVINE_CACHE, this.mRevineCache);
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           super.onMoveTo(oldPosition);
           fetchContent(3, false, UrlCachePolicy.FORCE_REFRESH);
           this.mFeedAdapter.onResume(isFocused());
                }

                @Override 
       public void onMoveAway(int newPosition) {
           super.onMoveAway(newPosition);
           this.mFeedAdapter.onPause(isFocused());
                }

       protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
           this.mLastFetchType = fetchType;
           if (!hasPendingRequest(fetchType)) {
               this.mFetched = true;
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
                       this.mNextPage = 1;
                       this.mAnchor = 0L;
                                break;
                        }
               if (userInitiated) {
                   showProgress(fetchType);
                        }
               addRequest(fetchPosts(this.mNextPage, this.mAnchor, userInitiated, cachePolicy), fetchType);
                    }
                }

                @Override 
       protected void refresh() {
                        clearPendingRequests(2);
           hideProgress(2);
           fetchContent(2, true, UrlCachePolicy.FORCE_REFRESH);
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
           this.mVineSpanClicker.onSpanClicked(view, type, tag);
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == R.id.like) {
               like((VinePost) view.getTag(), true);
               return;
                    }
           if (id == R.id.more_options) {
               VinePost post = (VinePost) view.getTag();
               FragmentActivity activity = getActivity();
               if (activity != null) {
                            Intent intent = PostOptionsDialogActivity.getMoreIntent(post, activity, post.userId == this.mAppController.getActiveId(), false);
                   activity.startActivityForResult(intent, 10);
                   return;
                        }
               return;
                    }
           if (id == R.id.share || id == R.id.share_post) {
               VinePost post2 = (VinePost) view.getTag();
               FragmentActivity activity2 = getActivity();
               if (activity2 != null) {
                            long myRepostId = this.mRevineCache.getMyRepostId(post2.postId);
                            if (myRepostId <= 0) {
                                myRepostId = post2.myRepostId;
                            }
                            boolean wasRevined = this.mRevineCache.isRevined(post2.postId) || myRepostId > 0 || post2.myRepostId > 0 || post2.isRevined();
                            activity2.startActivityForResult(PostOptionsDialogActivity.getShareIntent(post2, activity2, post2.userId == this.mAppController.getActiveId(), myRepostId, post2.getVineRepostRepostId(), wasRevined, this.mFlurryEventSource), 10);
                   return;
                        }
               return;
                    }
           if (id == R.id.clear_client_flags_banner) {
               hideClientFlagsBanner();
               this.mClientFlagsBannerWasDismissed = true;
                    }
                }

       public void like(VinePost post, boolean unlikeIfLiked) {
           long postId = post.postId;
           long repostId = post.getVineRepostRepostId();
           if (this.mLikeCache.isLike(postId)) {
               if (unlikeIfLiked) {
                   addRequest(this.mAppController.unlikePost(this.mFlurryEventSource, this.mAppController.getActiveSession(), postId, repostId, false));
                   this.mLikeCache.unlike(postId);
                   this.mFeedAdapter.showUnlikedByMe(postId);
                   return;
                        }
               return;
                    }
           addRequest(this.mAppController.likePost(this.mFlurryEventSource, this.mAppController.getActiveSession(), postId, repostId, this.mAppController.getActiveId(), this.mAppController.getActiveSession().getName(), false));
           this.mLikeCache.like(postId);
           this.mFeedAdapter.showLikedByMe(postId);
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 10:
                   processPostOptionsResult(PostOptionsDialogActivity.processActivityResult(this.mAppController, getActivity(), resultCode, data));
                            break;
                        default:
                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

                @Override 
       public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    try {
                        super.onScrollStateChanged(view, scrollState);
               this.mFeedAdapter.onScrollStateChanged(view, scrollState);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public void onDestroy() {
                    try {
               super.onDestroy();
               this.mFeedAdapter.onDestroy();
                    } catch (Exception e) {
               CrashUtil.logException(e, "Failed to remove timeline fragment", new Object[0]);
                    }
                }

       protected boolean trackNewCount() {
           return false;
                }

                @Override 
       protected void onScrollLastItem() {
           if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
               this.mFeedAdapter.onPause(isFocused());
               fetchContent(1, true, UrlCachePolicy.NETWORK_THEN_CACHE);
               showProgress(1);
               FlurryUtils.trackTimeLinePageScroll(getClass().getName(), this.mNextPage);
                    }
                }

       protected void showClientFlagsBanner() {
           if (this.mClientFlagsBannerParent == null) {
               ListView listView = this.mListView;
               RelativeLayout bannerParent = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.zero_rating_banner, (ViewGroup) listView, false);
               ImageButton clearButton = (ImageButton) bannerParent.findViewById(R.id.clear_client_flags_banner);
               clearButton.setColorFilter(getResources().getColor(R.color.clear_client_flags_banner));
               clearButton.setOnClickListener(this);
               listView.addHeaderView(bannerParent);
               this.mClientFlagsBannerParent = bannerParent;
                    }
           ViewGroup container = (ViewGroup) this.mClientFlagsBannerParent.findViewById(R.id.collapsible);
           int visibility = container.getVisibility();
           container.setVisibility(0);
           if (visibility != 0) {
                        this.mFeedAdapter.notifyDataSetChanged();
                    }
                }

       protected void populateClientFlagsBanner(String title, String text) {
           if (!TextUtils.isEmpty(title)) {
               setBannerText(R.id.text1, title);
                    }
           if (!TextUtils.isEmpty(text)) {
               setBannerText(R.id.text2, text);
                    }
                }

       protected void setBannerText(int id, @NonNull String s) {
           if (this.mClientFlagsBannerParent != null) {
               ((TextView) this.mClientFlagsBannerParent.findViewById(id)).setText(s);
                    }
                }

       private void hideClientFlagsBanner() {
           if (this.mClientFlagsBannerParent != null) {
               this.mClientFlagsBannerParent.findViewById(R.id.collapsible).setVisibility(8);
                    }
                }

       protected void fetchInitialRequest(UrlCachePolicy cachePolicy) {
           fetchContent(3, cachePolicy.mCacheTakesPriority, cachePolicy);
                }

       public void onTopViewBinded() {
                }

       class TimeLineSessionListener extends AppSessionListener {
           TimeLineSessionListener() {
                    }

                    @Override 
           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
               BaseTimelineFragment.this.mFeedAdapter.onVideoPathObtained(videos);
                    }

                    @Override 
           public void onVideoPathError(VideoKey key, HttpResult result) {
               SLog.m455e("Download of video failed: " + result.reasonPhrase + " key: " + key.url);
                    }

                    @Override 
           public void onPhotoImageError(ImageKey key, HttpResult result) {
               SLog.m455e("Download of image failed: " + result.reasonPhrase + " key: " + key.url);
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               BaseTimelineFragment.this.mFeedAdapter.setImages(images);
                    }

                    @Override 
           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
               if (req != null) {
                   if (memory && statusCode == 200) {
                       if (SLog.sLogsOn && BaseTimelineFragment.this.mFeedAdapter.getStartLoadingTime() > 0) {
                           SLog.m465i("Time since started loading to end fetch: {}ms", Long.valueOf(BaseTimelineFragment.this.mFeedAdapter.timeSinceStartLoadingTime()));
                                }
                       if (posts != null) {
                           BaseTimelineFragment.this.mNextPage = next;
                           BaseTimelineFragment.this.mAnchor = anchor;
                           if (req.fetchType == 1) {
                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).addOlderPosts(posts);
                                    } else if (cachePolicy.mNetworkDataAllowed && !cachePolicy.mUseExpiredDataAllowedIfNetworkIsDown) {
                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).replacePosts(posts);
                                    } else if (!cachePolicy.mNetworkDataAllowed) {
                               ((FeedAdapter) BaseTimelineFragment.this.mFeedAdapter).mergePosts(posts);
                                    } else {
                               BaseTimelineFragment.this.onPostUpdatesCameIn(posts);
                                    }
                           BaseTimelineFragment.this.mAppController.saveLoadedPosts(posts, type, tag, pageType, next, previous, anchor, false);
                                }
                            }
                   if (!cachePolicy.mNetworkDataAllowed) {
                                
                                
                                
                                
                                
                                BaseTimelineFragment.this.hideProgress(req.fetchType);
                                BaseTimelineFragment.this.fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                            switch (req.fetchType) {
                                case 3:
                           if (BaseTimelineFragment.this.mAdapter != null && BaseTimelineFragment.this.mAdapter.isEmpty() && count == 0) {
                                        BaseTimelineFragment.this.hideProgress(req.fetchType);
                                        BaseTimelineFragment.this.showSadface(true);
                                    } else {
                               BaseTimelineFragment.this.hideProgress(req.fetchType);
                                        BaseTimelineFragment.this.showSadface(false);
                                    }
                                    break;
                                default:
                           BaseTimelineFragment.this.hideProgress(req.fetchType);
                                    break;
                            }
                        }
               if (!TextUtils.isEmpty(title) && BaseTimelineFragment.access$000(BaseTimelineFragment.this).getSupportActionBar() != null) {
                   BaseTimelineFragment.access$000(BaseTimelineFragment.this).getSupportActionBar().setTitle(title);
                        }
                    }

                    @Override 
           public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
               super.onGetCommentsComplete(reqId, statusCode, reasonPhrase, nextPage, anchor, comments);
                    }

                    @Override 
           public void onTrimMemory(int level) {
               super.onTrimMemory(level);
               GenericVideoAdapter adapter = BaseTimelineFragment.this.mFeedAdapter;
               if (adapter != null) {
                   adapter.onTrimMemory(level);
                        }
                    }

                    @Override 
           public void onLowMemory() {
               super.onLowMemory();
               GenericVideoAdapter adapter = BaseTimelineFragment.this.mFeedAdapter;
               if (adapter != null) {
                   adapter.onLowMemory();
                        }
                    }

                    @Override 
           public void onReportPostComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
               if (req != null) {
                   if (statusCode == 200) {
                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), R.string.post_reported);
                            } else {
                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), R.string.post_reported_error);
                            }
                        }
                    }

                    @Override 
           public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
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
                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), R.string.post_deleted);
                        } else {
                       Util.showCenteredToast(BaseTimelineFragment.this.getActivity(), R.string.post_deleted_error);
                        }
                    }
                }

                    @Override 
           public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
               BaseTimelineFragment.this.removeRequest(reqId);
               if (statusCode >= 200 && statusCode < 300) {
                      BaseTimelineFragment.this.mLikeCache.like(postId);
                      BaseTimelineFragment.this.mFeedAdapter.showLikedByMe(postId);
                  } else {
                   BaseTimelineFragment.this.mLikeCache.removeEntry(postId);
                   BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                        }
                    }

                    @Override 
           public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
               BaseTimelineFragment.this.removeRequest(reqId);
               if (statusCode >= 200 && statusCode < 300) {
                      BaseTimelineFragment.this.mLikeCache.unlike(postId);
                      BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                  } else {
                   if (statusCode == 404) {
                       BaseTimelineFragment.this.mLikeCache.removeEntry(postId);
                       BaseTimelineFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                            } else {
                       BaseTimelineFragment.this.mLikeCache.like(postId);
                       BaseTimelineFragment.this.mFeedAdapter.showLikedByMe(postId);
                            }
                        }
                    }

                    @Override 
           public void onRevine(String reqId, int statusCode, String reasonPhrase, long postId, VineRepost repost) {
               BaseTimelineFragment.this.removeRequest(reqId);
                if (statusCode >= 200 && statusCode < 300) {
                    BaseTimelineFragment.this.mRevineCache.revine(postId);
                    if (repost != null && repost.repostId > 0) {
                        BaseTimelineFragment.this.mRevineCache.putMyRepostId(postId, repost.repostId);
                             }
                    BaseTimelineFragment.this.mAdapter.notifyDataSetChanged();
                    BaseTimelineFragment.this.mFeedAdapter.updateRevinedCount(postId, true, repost != null ? repost.repostId : -1L);
                         }
                    }

                    @Override 
           public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
               BaseTimelineFragment.this.removeRequest(reqId);
                if (statusCode >= 200 && statusCode < 300) {
                    BaseTimelineFragment.this.mRevineCache.unRevine(postId);
                    BaseTimelineFragment.this.mRevineCache.removeMyRepostId(postId);
                    BaseTimelineFragment.this.mAdapter.notifyDataSetChanged();
                    BaseTimelineFragment.this.mFeedAdapter.updateRevinedCount(postId, false, 0L);
                         }
                    }

                    @Override 
           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
               PendingRequest req = BaseTimelineFragment.this.removeRequest(reqId);
               if (req != null) {
                   BaseTimelineFragment.this.mPendingRequestHelper.onCaptchaRequired(BaseTimelineFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }

                    @Override 
           public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, VineClientFlags clientFlags, boolean showBanner) {
               if (statusCode == 200 && BaseTimelineFragment.this.isFocused() && !BaseTimelineFragment.this.mClientFlagsBannerWasDismissed) {
                   if (showBanner) {
                       BaseTimelineFragment.this.showClientFlagsBanner();
                       BaseTimelineFragment.this.populateClientFlagsBanner(clientFlags.messageTitle, clientFlags.messageText);
                            } else {
                       BaseTimelineFragment.access$100(BaseTimelineFragment.this);
                            }
                        }
                    }

                    @Override 
           public void onMobileDataNetworkActivated() {
               if (ClientFlagsHelper.shouldShowClientFlagsBanner(BaseTimelineFragment.this.getActivity()) && BaseTimelineFragment.this.isFocused()) {
                   BaseTimelineFragment.this.showClientFlagsBanner();
                   SharedPreferences prefs = Util.getDefaultSharedPrefs(BaseTimelineFragment.this.getActivity());
                   String title = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TITLE, null);
                   String text = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TEXT, null);
                   BaseTimelineFragment.this.populateClientFlagsBanner(title, text);
                        }
                    }

                    @Override 
           public void onAbortAllRequestsComplete() {
               BaseTimelineFragment.this.hideProgress(2);
               BaseTimelineFragment.this.fetchContent(2, false, UrlCachePolicy.CACHE_THEN_NETWORK);
                    }
                }

       protected void onPostUpdatesCameIn(ArrayList<VinePost> posts) {
           ((FeedAdapter) this.mFeedAdapter).mergePosts(posts);
                }
            }
