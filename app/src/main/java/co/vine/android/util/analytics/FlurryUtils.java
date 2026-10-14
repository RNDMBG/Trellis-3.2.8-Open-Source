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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class FlurryUtils {
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

/* 27 */        public static void start(Context context) {
                    try {
/* 28 */                FlurryAgent.onStartSession(context, "K5MVVCSN2MQ89JDRWKGY");
/* 29 */                FlurryAgent.setUseHttps(true);
/* 30 */                long userId = AppController.getInstance(context).getActiveId();
                        if (userId > 0) {
/* 32 */                    FlurryAgent.setUserId(String.valueOf(userId));
                        }
/* 34 */                FlurryAgent.setVersionName(BuildUtil.getVersionName(context));
                    } catch (Exception e) {
/* 36 */                CrashUtil.logException(e);
                    }
                }

/* 41 */        public static void stop(Context context) {
                    try {
/* 42 */                FlurryAgent.onEndSession(context);
                    } catch (Exception e) {
/* 44 */                CrashUtil.logException(e);
/* 45 */                throw new RuntimeException(e);
                    }
                }

/* 49 */        public static void trackLockOutSessionCount() {
/* 50 */            if (isSignupFailed) {
/* 51 */                isSignupFailed = false;
/* 52 */                FlurryEvent.log("SignUpLockOut");
                    }
                }

/* 56 */        public static void trackRecordingInfo(int phase, long total, long waitTime, int cuts) {
/* 57 */            new FlurryEvent("Recording").add("Duration", Long.valueOf(total)).add("Wait time", Long.valueOf(waitTime)).add("Cuts", Integer.valueOf(cuts)).add("Phase", Integer.valueOf(phase)).log();
                }

/* 65 */        public static void trackRespondTime(String host, String path, long firstByte, long durationMs, boolean isVideo) {
/* 66 */            if (BuildUtil.isProduction()) {
/* 67 */                FlurryEvent event = new FlurryEvent("Response_" + (isVideo ? MimeTypes.BASE_TYPE_VIDEO : "generic"));
/* 68 */                if (isVideo) {
/* 69 */                    event.add("host", host);
                        } else {
/* 71 */                    event.add("host", host).add("path", path);
                        }
/* 73 */                event.add("statusTime", getBucketedTime(firstByte)).add("totalTime", getBucketedTime(durationMs)).log();
                    }
                }

/* 81 */        public static void trackLoadingTime(String loadingTimeTag, long time) {
/* 82 */            if (BuildUtil.isProduction()) {
/* 83 */                mLoadingTime.put(LOADING_KEY, String.valueOf(time));
/* 84 */                FlurryAgent.logEvent("Loading_" + loadingTimeTag, mLoadingTime);
                    }
                }

/* 90 */        public static void trackDoubleTap(long postId) {
                    if (postId == sLastPostIdDoubleTapped) {
/* 92 */                sCountDoubleTapped++;
/* 93 */                FlurryEvent.log("doubleTap", "count", Integer.valueOf(sCountDoubleTapped));
                    } else {
/* 95 */                FlurryEvent.log("doubleTap", "count", 0);
                    }
/* 97 */            sLastPostIdDoubleTapped = postId;
                }

/* 100 */       private static String getBucketedTime(long time) {
                    return time < 1000 ? String.valueOf((time / 100) * 100) + "ms" : String.valueOf(Math.max(((time / 500) * 500) / 1000.0d, 1.0d) + VineAPI.RESOURCE_SHARED);
                }

/* 108 */       public static void trackValidPullToRefreshRelease(String source) {
/* 109 */           FlurryEvent.log("ValidPullToRefreshRelease", "source", source);
                }

/* 112 */       public static void trackLoginSuccess(boolean isTwitter) {
/* 113 */           FlurryEvent.log("LoginSuccess", "isTwitter", Boolean.valueOf(isTwitter));
                }

/* 116 */       public static void trackLoginFailure(boolean isTwitter, int statusCode) {
/* 117 */           FlurryEvent.log("LoginFailure", "isTwitter", Boolean.valueOf(isTwitter), "statusCode", Integer.valueOf(statusCode));
                }

/* 120 */       public static void trackTimeLinePageScroll(String name, int page) {
/* 121 */           FlurryEvent.log("TimeLinePageScroll_" + name, "page", Integer.valueOf(page));
                }

/* 124 */       public static void trackGraphTimelineRefreshNewCount(int count) {
/* 125 */           FlurryEvent.log("GraphRefreshNewVideoCount", "count", Integer.valueOf(count));
                }

/* 130 */       public static void onSignupSuccess(boolean twitterSignup) {
/* 131 */           isSignupFailed = false;
/* 132 */           FlurryEvent.log("SignupSuccess", "isTwitter", Boolean.valueOf(twitterSignup));
                }

/* 135 */       public static void onSignupWithPreinstallSuccess(String target) {
/* 136 */           FlurryEvent.log("SignUpWithPreinstall", "target", target);
                }

/* 139 */       public static void onSignupFailure(boolean twitterSignup, VineError error, int statusCode) {
/* 140 */           isSignupFailed = true;
/* 141 */           FlurryEvent event = new FlurryEvent("SignupFailure");
/* 142 */           if (error != null) {
/* 143 */               event.add("errorCode", Integer.valueOf(error.errorCode)).add("message", error.message);
                    }
/* 145 */           event.add("isTwitter", Boolean.valueOf(twitterSignup)).add("statusCode", Integer.valueOf(statusCode)).log();
                }

/* 148 */       public static void trackLikePost(long postId, String source) {
/* 149 */           FlurryEvent.log("Like", "postId", Long.valueOf(postId), "Source View", source);
                }

/* 152 */       public static void trackUnLikePost(String source) {
/* 153 */           FlurryEvent.log("UnLike", "Source View", source);
                }

/* 156 */       public static void trackRevine(long postId, String source) {
/* 157 */           FlurryEvent.log("Revine", "postId", Long.valueOf(postId), "Source View", source);
                }

/* 160 */       public static void trackUnRevine(String source) {
/* 161 */           FlurryEvent.log("UnRevine", "Source View", source);
                }

/* 164 */       public static void trackSeeReviners() {
/* 165 */           FlurryEvent.log("SeeReviners");
                }

/* 168 */       public static void trackGetUser(boolean self) {
/* 169 */           FlurryEvent.log("ProfileFetch", "self", Boolean.valueOf(self));
                }

/* 172 */       public static void trackConnectTwitter() {
/* 173 */           FlurryEvent.log("ConnectTwitter");
                }

/* 176 */       public static void trackResetPassword() {
/* 177 */           FlurryEvent.log("ResetPassword");
                }

/* 180 */       public static void trackGetComments() {
/* 181 */           FlurryEvent.log("GetComments");
                }

/* 184 */       public static void trackGetEditions() {
/* 185 */           FlurryEvent.log("GetEditions");
                }

/* 188 */       public static void trackPostComment() {
/* 189 */           FlurryEvent.log("PostComment");
                }

/* 192 */       public static void trackDeleteComment() {
/* 193 */           FlurryEvent.log("DeleteComment");
                }

/* 196 */       public static void trackDisConnectTwitter() {
/* 197 */           FlurryEvent.log("DisconnectTwitter");
                }

/* 200 */       public static void trackConnectFacebook() {
/* 201 */           FlurryEvent.log("ConnectFacebook");
                }

/* 204 */       public static void trackChangedEdition() {
/* 205 */           FlurryEvent.log("ChangedEdition");
                }

/* 208 */       public static void trackSearchUsers() {
/* 209 */           FlurryEvent.log("SearchUser");
                }

/* 212 */       public static void trackSearchTags() {
/* 213 */           FlurryEvent.log("SearchTags");
                }

/* 216 */       public static void trackBlockUser() {
/* 217 */           FlurryEvent.log("BlockUser");
                }

/* 220 */       public static void trackUnBlockUser() {
/* 221 */           FlurryEvent.log("UnBlockUser");
                }

/* 224 */       public static void trackReportUser() {
/* 225 */           FlurryEvent.log("ReportUser");
                }

/* 228 */       public static void trackSharePost(String network, long postId) {
/* 229 */           FlurryEvent.log("SharePost_" + network, "postId", Long.valueOf(postId));
                }

/* 232 */       public static void trackReportPost() {
/* 233 */           FlurryEvent.log("ReportPost");
                }

/* 236 */       public static void trackTabChange(String tag) {
/* 237 */           FlurryEvent.log("Page_" + tag);
                }

/* 240 */       public static void trackPost(boolean checked) {
/* 241 */           FlurryEvent.log("Post", "Vine", Boolean.valueOf(checked));
                }

/* 244 */       public static void trackChannelChange(String channelDetails) {
/* 245 */           FlurryEvent.log("PostChannelChange", "channelDetails", channelDetails);
                }

/* 248 */       public static void trackBackFromPostScreen() {
/* 249 */           FlurryEvent.log("BackToPreviewFromPostScreen");
                }

/* 252 */       public static void trackAbandonedStage(String stage) {
/* 253 */           FlurryEvent.log("Abandon", "stage", stage);
                }

/* 256 */       public static void trackAbandonedTier(String tier) {
/* 257 */           SLog.m449d("Post abandoned for {}, had to wait {}ms and {}ms.", tier, Long.valueOf(BasicVineRecorder.sTimeTaken), Long.valueOf(RecordController.sMaxKnownStopTime));
/* 258 */           FlurryEvent.log("Post_Abandon_" + tier, "waitOnCamera", Long.valueOf(BasicVineRecorder.sTimeTaken), "waitOnProcessing", Long.valueOf(RecordController.sMaxKnownStopTime));
                }

/* 261 */       public static void trackPostTier(String tier) {
/* 262 */           FlurryEvent.log("Post_Posted_" + tier, "waitOnCamera", Long.valueOf(BasicVineRecorder.sTimeTaken), "waitOnProcessing", Long.valueOf(RecordController.sMaxKnownStopTime));
                }

/* 265 */       public static void trackShareProfile() {
/* 266 */           FlurryEvent.log("ShareProfile");
                }

/* 269 */       public static void trackCreateProfileShortCut() {
/* 270 */           FlurryEvent.log("CreateProfileShortCut");
                }

/* 273 */       public static void trackVisitSettings(String source) {
/* 274 */           FlurryEvent.log("VisitSettings", "source", source);
                }

/* 277 */       public static void trackChangedName() {
/* 278 */           FlurryEvent.log("Settings_ChangedName");
                }

/* 281 */       public static void trackChangedDescription() {
/* 282 */           FlurryEvent.log("Settings_ChangedDescription");
                }

/* 285 */       public static void trackChangedLocation() {
/* 286 */           FlurryEvent.log("Settings_ChangedLocation");
                }

/* 289 */       public static void trackChangedEmail() {
/* 290 */           FlurryEvent.log("Settings_ChangedEmail");
                }

/* 293 */       public static void trackVisitFindFriends(String source) {
/* 294 */           FlurryEvent.log("VisitFindFriends", "source", source);
                }

/* 297 */       public static void trackFindFriendsAddressCount(int count) {
/* 298 */           FlurryEvent.log("FindFriendsAddressResultsCount", "Result Count", Integer.valueOf(count));
                }

/* 300 */       public static void trackFindFriendsTwitterCount(int count) {
/* 301 */           FlurryEvent.log("FindFriendsTwitterResultsCount", "Result Count", Integer.valueOf(count));
                }

/* 304 */       public static void trackFindFriendsAddressFailure() {
/* 305 */           FlurryEvent.log("FindFriendsAddressFailure");
                }

/* 307 */       public static void trackFindFriendsTwitterFailure() {
/* 308 */           FlurryEvent.log("FindFriendsTwitterFailure");
                }

/* 311 */       public static void trackAddressNewFollowingCount(String count) {
/* 312 */           FlurryEvent.log("FindFriendsAddressNewFollowing", "Count", count);
                }

/* 315 */       public static void trackTwitterNewFollowingCount(String count) {
/* 316 */           FlurryEvent.log("FindFriendsTwitterNewFollowing", "Count", count);
                }

/* 319 */       public static void trackVisitSearch(String source) {
/* 320 */           FlurryEvent.log("VisitSearch", "source", source);
                }

/* 323 */       public static void trackVisitInbox(String source) {
/* 324 */           FlurryEvent.log("VisitInbox", "source", source);
                }

/* 327 */       public static void trackLogout() {
/* 328 */           FlurryEvent.log("Logout");
                }

/* 337 */       public static void trackRecordingStart() {
/* 338 */           sRecordingCamera = false;
/* 339 */           sRecordingFocus = false;
/* 340 */           sRecordingGrid = false;
/* 341 */           sRecordingGhost = false;
/* 342 */           sRecordingSession = false;
/* 343 */           FlurryEvent.log("RecordingStart");
                }

/* 347 */       public static void trackRecordingDestroy() {
                }

/* 350 */       public static void trackCameraSwitchPressed(View v) {
/* 351 */           if (v != null && !sRecordingCamera) {
/* 352 */               sRecordingCamera = true;
/* 353 */               FlurryEvent.log("RecordingCamera");
                    }
                }

/* 357 */       public static void trackFocusSwitchPressed(View v) {
/* 358 */           if (v != null && !sRecordingFocus) {
/* 359 */               sRecordingFocus = true;
/* 360 */               FlurryEvent.log("RecordingFocus");
                    }
                }

/* 364 */       public static void trackGridSwitchPressed() {
/* 365 */           if (!sRecordingGrid) {
/* 366 */               sRecordingGrid = true;
/* 367 */               FlurryEvent.log("RecordingGrid");
                    }
                }

/* 371 */       public static void trackGhostSwitchPressed(View v) {
/* 372 */           if (v != null && !sRecordingGhost) {
/* 373 */               sRecordingGhost = true;
/* 374 */               FlurryEvent.log("RecordingGhost");
                    }
                }

/* 378 */       public static void trackSessionSwitchPressed() {
/* 379 */           if (!sRecordingSession) {
/* 380 */               sRecordingSession = true;
/* 381 */               FlurryEvent.log("RecordingSession");
                    }
                }

/* 385 */       public static void trackSessionSwitchPressedOnDraftUpgrade(int count) {
/* 386 */           if (!sRecordingSession) {
/* 387 */               sRecordingSession = true;
/* 388 */               FlurryEvent.log("RecordingSessionDraftUpgrading", "count", Integer.valueOf(count));
                    }
                }

/* 392 */       public static void trackAttribution() {
/* 393 */           FlurryEvent.log("Attribution");
                }

/* 396 */       public static void trackDeactivateAccount() {
/* 397 */           FlurryEvent.log("DeactivateAccount");
                }

/* 400 */       public static void trackTos() {
/* 401 */           FlurryEvent.log("TermsOfServiceClicked");
                }

/* 404 */       public static void trackInvite(String type, String source) {
/* 405 */           FlurryEvent.log("Invite_" + type, "source", source);
                }

/* 408 */       public static void trackPrivacyPolicy() {
/* 409 */           FlurryEvent.log("PrivacyPolicy");
                }

/* 412 */       public static void trackContentControls() {
/* 413 */           FlurryEvent.log("ContentControls");
                }

/* 416 */       public static void trackNotificationSettings() {
/* 417 */           FlurryEvent.log("NotificationSettings");
                }

/* 420 */       public static void trackSaveSession(String source) {
/* 421 */           FlurryEvent.log("SaveSession", "source", source);
                }

/* 424 */       public static void trackPreviewAction(String action) {
/* 425 */           FlurryEvent.log("PreviewAction", BaseFragment.ARG_ACTION, action);
                }

/* 428 */       public static void trackFindFriendsTabSelect(int position) {
/* 429 */           FlurryEvent.log("FindFriendsTabSelect", "Address/Twitter/Search", Integer.valueOf(position));
                }

/* 432 */       public static void trackInviteBannerViewed(long viewCount) {
/* 433 */           FlurryEvent.log("InviteBannerViews", "ViewCount", ">" + viewCount);
                }

/* 436 */       public static void trackFollow(String source) {
/* 437 */           FlurryEvent.log("Follow", "source", source);
                }

/* 440 */       public static void trackUnfollow(String source) {
/* 441 */           FlurryEvent.log("Unfollow", "source", source);
                }

/* 444 */       public static void trackProfileImageClick(boolean self) {
/* 445 */           FlurryEvent.log("ProfileImageClick", "self", Boolean.valueOf(self));
                }

/* 449 */       public static void trackFilterProfileReposts(boolean hideReposts, boolean isFollowing, boolean isMe) {
/* 450 */           String eventName = hideReposts ? "ProfileRepostsHidden" : "ProfileRepostsShown";
/* 451 */           FlurryEvent.log(eventName, "Is Following", Boolean.valueOf(isFollowing), "Is Me", Boolean.valueOf(isMe));
                }

/* 454 */       public static void trackCameraWidgetAdded() {
/* 455 */           FlurryEvent.log("CameraWidgetAdded");
                }

/* 458 */       public static void trackCameraWidgetRemoved() {
/* 459 */           FlurryEvent.log("CameraWidgetRemoved");
                }

/* 462 */       public static void trackNotificationReceived(String type) {
/* 463 */           FlurryEvent.log("notificationReceived", "type", type);
                }

/* 466 */       public static void trackNotificationShown(String type) {
/* 467 */           FlurryEvent.log("notificationShown", "type", type);
                }

/* 470 */       public static void trackNotificationClicked(String type) {
/* 471 */           FlurryEvent.log("notificationClicked", "type", type);
                }

/* 474 */       public static void trackNotificationDisabled() {
/* 475 */           FlurryEvent.log("notificationDisabled");
                }

/* 478 */       public static void trackZeroRatedSessionStarted(boolean wasStarted) {
/* 479 */           if (wasStarted) {
/* 480 */               FlurryEvent.log("ZeroRatedSessionStarted");
                    }
                }

/* 484 */       public static void trackVineLoopWatched() {
/* 485 */           FlurryEvent.log("VineLoopWatched");
                }

/* 488 */       public static void trackUsingVideoNormalUrl(boolean isSpeedGoodEnough, boolean normalVideoPlayable) {
/* 489 */           FlurryEvent.log("videoNormalUrl", "speedLimited", Boolean.valueOf(!isSpeedGoodEnough), "deviceLimited", Boolean.valueOf(normalVideoPlayable));
                }

/* 492 */       public static void trackUsingVideoLowUrl(boolean isSpeedGoodEnough, boolean normalVideoPlayable) {
/* 493 */           FlurryEvent.log("videoLowUrl", "speedLimited", Boolean.valueOf(!isSpeedGoodEnough), "deviceLimited", Boolean.valueOf(normalVideoPlayable));
                }

/* 496 */       public static void trackNuxStarted(String type) {
/* 497 */           FlurryEvent.log("nux_started", "type", type);
                }

/* 500 */       public static void trackNuxScreenDisplayed(String nuxScreen) {
/* 501 */           FlurryEvent.log("nux_screenDisplayed", "nuxScreen", nuxScreen);
                }

/* 504 */       public static void trackNuxAccountCreated(String accountType) {
/* 505 */           FlurryEvent.log("nux_accountCreated", "accountType", accountType);
                }

/* 509 */       public static void trackNuxChannelFollows(ArrayList<String> channelIds) {
/* 510 */           FlurryEvent.log("nux_channelFollowTotalFollowed", "numChannels", Integer.valueOf(channelIds.size()));
                    for (String channelId : channelIds) {
/* 512 */               FlurryEvent.log("nux_channelFollow", "channelId", channelId);
                    }
                }

/* 516 */       public static void trackNuxCompleted() {
/* 517 */           FlurryEvent.log("nux_completed");
                }

/* 520 */       public static void trackOnboardingShown(String onboardId) {
/* 521 */           FlurryEvent.log("onboarding_seen_" + onboardId);
                }

/* 524 */       public static void trackOnboardingDismissed(String option) {
/* 525 */           FlurryEvent.log("onboarding_dismissed_" + option);
                }

/* 528 */       public static void trackPrefetchStart(long lastStart) {
                    if (lastStart > 0) {
/* 530 */               FlurryEvent.log("prefetch_start", "since_last_start", Long.valueOf(System.currentTimeMillis() - lastStart));
                    } else {
/* 532 */               FlurryEvent.log("prefetch_start", "since_last_start", Integer.MAX_VALUE);
                    }
                }

/* 536 */       public static void trackPrefetchEnd(long duration) {
/* 537 */           FlurryEvent.log("prefetch_end", "duration", Long.valueOf(duration));
                }

/* 540 */       public static void trackVideoLoadHits(boolean wasSyncedRecently, CacheKey.CacheState cacheState) {
/* 541 */           FlurryEvent.log("VideoLoad_" + cacheState.name(), "syncedRecently", Boolean.valueOf(wasSyncedRecently));
                }
            }
