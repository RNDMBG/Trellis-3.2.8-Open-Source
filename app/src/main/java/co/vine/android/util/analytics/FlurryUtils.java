            package co.vine.android.util.analytics;

            import android.content.Context;
            import android.view.View;
            import co.vine.android.BaseFragment;
            import co.vine.android.analytics.FlurryEvent;
            import co.vine.android.api.VineError;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAPI;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordController;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.flurry.android.FlurryAgent;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class FlurryUtils {
                private static final String LOADING_KEY = "Loading";
                private static boolean isSignupFailed;
                private static boolean sRecordingCamera;
                private static boolean sRecordingFocus;
                private static boolean sRecordingGhost;
                private static boolean sRecordingGrid;
                private static boolean sRecordingSession;
                private static final HashMap<String, String> mLoadingTime = new HashMap<>();
                private static long sLastPostIdDoubleTapped = -1;
                private static int sCountDoubleTapped = 0;

        public static void start(Context context) {
                    try {
                FlurryAgent.onStartSession(context, "K5MVVCSN2MQ89JDRWKGY");
                FlurryAgent.setUseHttps(true);
                long userId = AppController.getInstance(context).getActiveId();
                        if (userId > 0) {
                    FlurryAgent.setUserId(String.valueOf(userId));
                        }
                FlurryAgent.setVersionName(BuildUtil.getVersionName(context));
                    } catch (Exception e) {
                CrashUtil.logException(e);
                    }
                }

        public static void stop(Context context) {
                    try {
                FlurryAgent.onEndSession(context);
                    } catch (Exception e) {
                CrashUtil.logException(e);
                throw new RuntimeException(e);
                    }
                }

        public static void trackLockOutSessionCount() {
            if (isSignupFailed) {
                isSignupFailed = false;
                FlurryEvent.log("SignUpLockOut");
                    }
                }

        public static void trackRecordingInfo(int phase, long total, long waitTime, int cuts) {
            new FlurryEvent("Recording").add("Duration", Long.valueOf(total)).add("Wait time", Long.valueOf(waitTime)).add("Cuts", Integer.valueOf(cuts)).add("Phase", Integer.valueOf(phase)).log();
                }

        public static void trackRespondTime(String host, String path, long firstByte, long durationMs, boolean isVideo) {
            if (BuildUtil.isProduction()) {
                FlurryEvent event = new FlurryEvent("Response_" + (isVideo ? MimeTypes.BASE_TYPE_VIDEO : "generic"));
                if (isVideo) {
                    event.add("host", host);
                        } else {
                    event.add("host", host).add("path", path);
                        }
                event.add("statusTime", getBucketedTime(firstByte)).add("totalTime", getBucketedTime(durationMs)).log();
                    }
                }

        public static void trackLoadingTime(String loadingTimeTag, long time) {
            if (BuildUtil.isProduction()) {
                mLoadingTime.put(LOADING_KEY, String.valueOf(time));
                FlurryAgent.logEvent("Loading_" + loadingTimeTag, mLoadingTime);
                    }
                }

        public static void trackDoubleTap(long postId) {
                    if (postId == sLastPostIdDoubleTapped) {
                sCountDoubleTapped++;
                FlurryEvent.log("doubleTap", "count", Integer.valueOf(sCountDoubleTapped));
                    } else {
                FlurryEvent.log("doubleTap", "count", 0);
                    }
            sLastPostIdDoubleTapped = postId;
                }

       private static String getBucketedTime(long time) {
                    return time < 1000 ? String.valueOf((time / 100) * 100) + "ms" : String.valueOf(Math.max(((time / 500) * 500) / 1000.0d, 1.0d) + VineAPI.RESOURCE_SHARED);
                }

       public static void trackValidPullToRefreshRelease(String source) {
           FlurryEvent.log("ValidPullToRefreshRelease", "source", source);
                }

       public static void trackLoginSuccess(boolean isTwitter) {
           FlurryEvent.log("LoginSuccess", "isTwitter", Boolean.valueOf(isTwitter));
                }

       public static void trackLoginFailure(boolean isTwitter, int statusCode) {
           FlurryEvent.log("LoginFailure", "isTwitter", Boolean.valueOf(isTwitter), "statusCode", Integer.valueOf(statusCode));
                }

       public static void trackTimeLinePageScroll(String name, int page) {
           FlurryEvent.log("TimeLinePageScroll_" + name, "page", Integer.valueOf(page));
                }

       public static void trackGraphTimelineRefreshNewCount(int count) {
           FlurryEvent.log("GraphRefreshNewVideoCount", "count", Integer.valueOf(count));
                }

       public static void onSignupSuccess(boolean twitterSignup) {
           isSignupFailed = false;
           FlurryEvent.log("SignupSuccess", "isTwitter", Boolean.valueOf(twitterSignup));
                }

       public static void onSignupWithPreinstallSuccess(String target) {
           FlurryEvent.log("SignUpWithPreinstall", "target", target);
                }

       public static void onSignupFailure(boolean twitterSignup, VineError error, int statusCode) {
           isSignupFailed = true;
           FlurryEvent event = new FlurryEvent("SignupFailure");
           if (error != null) {
               event.add("errorCode", Integer.valueOf(error.errorCode)).add("message", error.message);
                    }
           event.add("isTwitter", Boolean.valueOf(twitterSignup)).add("statusCode", Integer.valueOf(statusCode)).log();
                }

       public static void trackLikePost(long postId, String source) {
           FlurryEvent.log("Like", "postId", Long.valueOf(postId), "Source View", source);
                }

       public static void trackUnLikePost(String source) {
           FlurryEvent.log("UnLike", "Source View", source);
                }

       public static void trackRevine(long postId, String source) {
           FlurryEvent.log("Revine", "postId", Long.valueOf(postId), "Source View", source);
                }

       public static void trackUnRevine(String source) {
           FlurryEvent.log("UnRevine", "Source View", source);
                }

       public static void trackSeeReviners() {
           FlurryEvent.log("SeeReviners");
                }

       public static void trackGetUser(boolean self) {
           FlurryEvent.log("ProfileFetch", "self", Boolean.valueOf(self));
                }

       public static void trackConnectTwitter() {
           FlurryEvent.log("ConnectTwitter");
                }

       public static void trackResetPassword() {
           FlurryEvent.log("ResetPassword");
                }

       public static void trackGetComments() {
           FlurryEvent.log("GetComments");
                }

       public static void trackGetEditions() {
           FlurryEvent.log("GetEditions");
                }

       public static void trackPostComment() {
           FlurryEvent.log("PostComment");
                }

       public static void trackDeleteComment() {
           FlurryEvent.log("DeleteComment");
                }

       public static void trackDisConnectTwitter() {
           FlurryEvent.log("DisconnectTwitter");
                }

       public static void trackConnectFacebook() {
           FlurryEvent.log("ConnectFacebook");
                }

       public static void trackChangedEdition() {
           FlurryEvent.log("ChangedEdition");
                }

       public static void trackSearchUsers() {
           FlurryEvent.log("SearchUser");
                }

       public static void trackSearchTags() {
           FlurryEvent.log("SearchTags");
                }

       public static void trackBlockUser() {
           FlurryEvent.log("BlockUser");
                }

       public static void trackUnBlockUser() {
           FlurryEvent.log("UnBlockUser");
                }

       public static void trackReportUser() {
           FlurryEvent.log("ReportUser");
                }

       public static void trackSharePost(String network, long postId) {
           FlurryEvent.log("SharePost_" + network, "postId", Long.valueOf(postId));
                }

       public static void trackReportPost() {
           FlurryEvent.log("ReportPost");
                }

       public static void trackTabChange(String tag) {
           FlurryEvent.log("Page_" + tag);
                }

       public static void trackPost(boolean checked) {
           FlurryEvent.log("Post", "Vine", Boolean.valueOf(checked));
                }

       public static void trackChannelChange(String channelDetails) {
           FlurryEvent.log("PostChannelChange", "channelDetails", channelDetails);
                }

       public static void trackBackFromPostScreen() {
           FlurryEvent.log("BackToPreviewFromPostScreen");
                }

       public static void trackAbandonedStage(String stage) {
           FlurryEvent.log("Abandon", "stage", stage);
                }

       public static void trackAbandonedTier(String tier) {
           SLog.m449d("Post abandoned for {}, had to wait {}ms and {}ms.", tier, Long.valueOf(BasicVineRecorder.sTimeTaken), Long.valueOf(RecordController.sMaxKnownStopTime));
           FlurryEvent.log("Post_Abandon_" + tier, "waitOnCamera", Long.valueOf(BasicVineRecorder.sTimeTaken), "waitOnProcessing", Long.valueOf(RecordController.sMaxKnownStopTime));
                }

       public static void trackPostTier(String tier) {
           FlurryEvent.log("Post_Posted_" + tier, "waitOnCamera", Long.valueOf(BasicVineRecorder.sTimeTaken), "waitOnProcessing", Long.valueOf(RecordController.sMaxKnownStopTime));
                }

       public static void trackShareProfile() {
           FlurryEvent.log("ShareProfile");
                }

       public static void trackCreateProfileShortCut() {
           FlurryEvent.log("CreateProfileShortCut");
                }

       public static void trackVisitSettings(String source) {
           FlurryEvent.log("VisitSettings", "source", source);
                }

       public static void trackChangedName() {
           FlurryEvent.log("Settings_ChangedName");
                }

       public static void trackChangedDescription() {
           FlurryEvent.log("Settings_ChangedDescription");
                }

       public static void trackChangedLocation() {
           FlurryEvent.log("Settings_ChangedLocation");
                }

       public static void trackChangedEmail() {
           FlurryEvent.log("Settings_ChangedEmail");
                }

       public static void trackVisitFindFriends(String source) {
           FlurryEvent.log("VisitFindFriends", "source", source);
                }

       public static void trackFindFriendsAddressCount(int count) {
           FlurryEvent.log("FindFriendsAddressResultsCount", "Result Count", Integer.valueOf(count));
                }

       public static void trackFindFriendsTwitterCount(int count) {
           FlurryEvent.log("FindFriendsTwitterResultsCount", "Result Count", Integer.valueOf(count));
                }

       public static void trackFindFriendsAddressFailure() {
           FlurryEvent.log("FindFriendsAddressFailure");
                }

       public static void trackFindFriendsTwitterFailure() {
           FlurryEvent.log("FindFriendsTwitterFailure");
                }

       public static void trackAddressNewFollowingCount(String count) {
           FlurryEvent.log("FindFriendsAddressNewFollowing", "Count", count);
                }

       public static void trackTwitterNewFollowingCount(String count) {
           FlurryEvent.log("FindFriendsTwitterNewFollowing", "Count", count);
                }

       public static void trackVisitSearch(String source) {
           FlurryEvent.log("VisitSearch", "source", source);
                }

       public static void trackVisitInbox(String source) {
           FlurryEvent.log("VisitInbox", "source", source);
                }

       public static void trackLogout() {
           FlurryEvent.log("Logout");
                }

       public static void trackRecordingStart() {
           sRecordingCamera = false;
           sRecordingFocus = false;
           sRecordingGrid = false;
           sRecordingGhost = false;
           sRecordingSession = false;
           FlurryEvent.log("RecordingStart");
                }

       public static void trackRecordingDestroy() {
                }

       public static void trackCameraSwitchPressed(View v) {
           if (v != null && !sRecordingCamera) {
               sRecordingCamera = true;
               FlurryEvent.log("RecordingCamera");
                    }
                }

       public static void trackFocusSwitchPressed(View v) {
           if (v != null && !sRecordingFocus) {
               sRecordingFocus = true;
               FlurryEvent.log("RecordingFocus");
                    }
                }

       public static void trackGridSwitchPressed() {
           if (!sRecordingGrid) {
               sRecordingGrid = true;
               FlurryEvent.log("RecordingGrid");
                    }
                }

       public static void trackGhostSwitchPressed(View v) {
           if (v != null && !sRecordingGhost) {
               sRecordingGhost = true;
               FlurryEvent.log("RecordingGhost");
                    }
                }

       public static void trackSessionSwitchPressed() {
           if (!sRecordingSession) {
               sRecordingSession = true;
               FlurryEvent.log("RecordingSession");
                    }
                }

       public static void trackSessionSwitchPressedOnDraftUpgrade(int count) {
           if (!sRecordingSession) {
               sRecordingSession = true;
               FlurryEvent.log("RecordingSessionDraftUpgrading", "count", Integer.valueOf(count));
                    }
                }

       public static void trackAttribution() {
           FlurryEvent.log("Attribution");
                }

       public static void trackDeactivateAccount() {
           FlurryEvent.log("DeactivateAccount");
                }

       public static void trackTos() {
           FlurryEvent.log("TermsOfServiceClicked");
                }

       public static void trackInvite(String type, String source) {
           FlurryEvent.log("Invite_" + type, "source", source);
                }

       public static void trackPrivacyPolicy() {
           FlurryEvent.log("PrivacyPolicy");
                }

       public static void trackContentControls() {
           FlurryEvent.log("ContentControls");
                }

       public static void trackNotificationSettings() {
           FlurryEvent.log("NotificationSettings");
                }

       public static void trackSaveSession(String source) {
           FlurryEvent.log("SaveSession", "source", source);
                }

       public static void trackPreviewAction(String action) {
           FlurryEvent.log("PreviewAction", BaseFragment.ARG_ACTION, action);
                }

       public static void trackFindFriendsTabSelect(int position) {
           FlurryEvent.log("FindFriendsTabSelect", "Address/Twitter/Search", Integer.valueOf(position));
                }

       public static void trackInviteBannerViewed(long viewCount) {
           FlurryEvent.log("InviteBannerViews", "ViewCount", ">" + viewCount);
                }

       public static void trackFollow(String source) {
           FlurryEvent.log("Follow", "source", source);
                }

       public static void trackUnfollow(String source) {
           FlurryEvent.log("Unfollow", "source", source);
                }

       public static void trackProfileImageClick(boolean self) {
           FlurryEvent.log("ProfileImageClick", "self", Boolean.valueOf(self));
                }

       public static void trackFilterProfileReposts(boolean hideReposts, boolean isFollowing, boolean isMe) {
           String eventName = hideReposts ? "ProfileRepostsHidden" : "ProfileRepostsShown";
           FlurryEvent.log(eventName, "Is Following", Boolean.valueOf(isFollowing), "Is Me", Boolean.valueOf(isMe));
                }

       public static void trackCameraWidgetAdded() {
           FlurryEvent.log("CameraWidgetAdded");
                }

       public static void trackCameraWidgetRemoved() {
           FlurryEvent.log("CameraWidgetRemoved");
                }

       public static void trackNotificationReceived(String type) {
           FlurryEvent.log("notificationReceived", "type", type);
                }

       public static void trackNotificationShown(String type) {
           FlurryEvent.log("notificationShown", "type", type);
                }

       public static void trackNotificationClicked(String type) {
           FlurryEvent.log("notificationClicked", "type", type);
                }

       public static void trackNotificationDisabled() {
           FlurryEvent.log("notificationDisabled");
                }

       public static void trackZeroRatedSessionStarted(boolean wasStarted) {
           if (wasStarted) {
               FlurryEvent.log("ZeroRatedSessionStarted");
                    }
                }

       public static void trackVineLoopWatched() {
           FlurryEvent.log("VineLoopWatched");
                }

       public static void trackUsingVideoNormalUrl(boolean isSpeedGoodEnough, boolean normalVideoPlayable) {
           FlurryEvent.log("videoNormalUrl", "speedLimited", Boolean.valueOf(!isSpeedGoodEnough), "deviceLimited", Boolean.valueOf(normalVideoPlayable));
                }

       public static void trackUsingVideoLowUrl(boolean isSpeedGoodEnough, boolean normalVideoPlayable) {
           FlurryEvent.log("videoLowUrl", "speedLimited", Boolean.valueOf(!isSpeedGoodEnough), "deviceLimited", Boolean.valueOf(normalVideoPlayable));
                }

       public static void trackNuxStarted(String type) {
           FlurryEvent.log("nux_started", "type", type);
                }

       public static void trackNuxScreenDisplayed(String nuxScreen) {
           FlurryEvent.log("nux_screenDisplayed", "nuxScreen", nuxScreen);
                }

       public static void trackNuxAccountCreated(String accountType) {
           FlurryEvent.log("nux_accountCreated", "accountType", accountType);
                }

       public static void trackNuxChannelFollows(ArrayList<String> channelIds) {
           FlurryEvent.log("nux_channelFollowTotalFollowed", "numChannels", Integer.valueOf(channelIds.size()));
                    for (String channelId : channelIds) {
               FlurryEvent.log("nux_channelFollow", "channelId", channelId);
                    }
                }

       public static void trackNuxCompleted() {
           FlurryEvent.log("nux_completed");
                }

       public static void trackOnboardingShown(String onboardId) {
           FlurryEvent.log("onboarding_seen_" + onboardId);
                }

       public static void trackOnboardingDismissed(String option) {
           FlurryEvent.log("onboarding_dismissed_" + option);
                }

       public static void trackPrefetchStart(long lastStart) {
                    if (lastStart > 0) {
               FlurryEvent.log("prefetch_start", "since_last_start", Long.valueOf(System.currentTimeMillis() - lastStart));
                    } else {
               FlurryEvent.log("prefetch_start", "since_last_start", Integer.MAX_VALUE);
                    }
                }

       public static void trackPrefetchEnd(long duration) {
           FlurryEvent.log("prefetch_end", "duration", Long.valueOf(duration));
                }

       public static void trackVideoLoadHits(boolean wasSyncedRecently, CacheKey.CacheState cacheState) {
           FlurryEvent.log("VideoLoad_" + cacheState.name(), "syncedRecently", Boolean.valueOf(wasSyncedRecently));
                }
            }
