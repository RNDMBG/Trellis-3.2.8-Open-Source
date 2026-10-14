            package co.vine.android.client;

            import android.os.Bundle;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import android.util.Pair;
            import co.vine.android.api.ComplaintMenuOption;
            import co.vine.android.api.TwitterUser;
            import co.vine.android.api.VineChannel;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineEverydayNotification;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineNotificationSetting;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineRTCConversation;
            import co.vine.android.api.VineRepost;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.service.PendingAction;
            import com.facebook.SessionState;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 51 */    public abstract class AppSessionListener {
                private boolean mEnabled;

/* 52 */        public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
                }

/* 60 */        public void onLogoutComplete(String reqId) {
                }

/* 73 */        public void onSignUpComplete(String reqId, int statusCode, String reasonPhrase, VineLogin vineLogin, String accountName, String password, String avatarUrl) {
                }

/* 79 */        public void onGetUsersComplete(Session session, String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int previousPage, long anchor) {
                }

/* 83 */        public void onGetTwitterAuthUrlComplete(String reqId, String authUrl) {
                }

/* 86 */        public void onGetEditionsComplete(int statusCode, ArrayList<Pair<String, String>> editions) {
                }

/* 90 */        public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
                }

/* 94 */        public void onGetTwitterUserComplete(String reqId, int statusCode, String reasonPhrase, TwitterUser user, VineLogin login) {
                }

/* 102 */       public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                }

/* 106 */       public void onPhotoImageError(ImageKey key, HttpResult result) {
                }

/* 111 */       public void onTwitterxAuthComplete(String reqId, int statusCode, String reasonPhrase, VineLogin login) {
                }

/* 116 */       public void onGetTwitterFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
                }

/* 120 */       public void onResetPasswordComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 124 */       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                }

/* 128 */       public void onVideoPathError(VideoKey key, HttpResult result) {
                }

/* 132 */       public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
                }

/* 136 */       public void onPostCommentComplete(String reqId, int statusCode, String reasonPhrase, long postId, String comment, VineComment vc) {
                }

/* 140 */       public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, @Nullable VineUser meUser, UrlCachePolicy policy) {
                }

/* 146 */       public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
                }

/* 149 */       public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

/* 152 */       public void onBulkFollowComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 155 */       public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

/* 160 */       public void onGetActivityComplete(String reqId, int statusCode, String reasonPhrase, VinePagedData<VineEverydayNotification> notifications, VinePagedData<VineSingleNotification> followRequests, UrlCachePolicy policyUsed) {
                }

/* 165 */       public void onGetActivityCountComplete(String reqId, int statusCode, String reasonPhrase, int activityCount, int followRequestCount, int messagesCount, UrlCachePolicy policyUsed) {
                }

/* 169 */       public void onGetAddressFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
                }

/* 172 */       public void onRemoveUsersComplete(String reqId) {
                }

/* 175 */       public void onGetUserComplete(String reqId, int statusCode, String reasonPhrase, @Nullable VineUser user, UrlCachePolicy policy) {
                }

/* 178 */       public void onUpdateProfileComplete(String reqId, int statusCode, String reasonPhrase, String avatarUri) {
                }

/* 181 */       public void onUpdateEditionComplete(String reqId, int statusCode, String reasonPhrase, String edition) {
                }

/* 184 */       public void onReportPostComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

/* 187 */       public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
                }

/* 193 */       public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
                }

/* 196 */       public void onDisconnectTwitterComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 200 */       public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

/* 204 */       public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

/* 208 */       public void onRevine(String reqId, int statusCode, String reasonPhrase, long postId, VineRepost repost) {
                }

/* 212 */       public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

/* 216 */       public void onGetSinglePostComplete(String reqId, int statusCode, String reasonPhrase, VinePost post) {
                }

/* 219 */       public void onGetPostIdComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

/* 223 */       public void onFacebookSessionChanged(com.facebook.Session session, SessionState state, Exception exception) {
                }

/* 227 */       public void onUserSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineUser> users) {
                }

/* 231 */       public void onTagSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineTag> tags) {
                }

/* 234 */       public void onClearCacheComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 237 */       public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 241 */       public void onUpdateFollowEditorsPicksComplete(String reqId, int statusCode, String reasonPhrase, boolean isFollowing) {
                }

/* 244 */       public void onBlockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userBlocked, String blockedUsername) {
                }

/* 247 */       public void onUnblockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userUnblocked, String blockedUsername) {
                }

/* 250 */       public void onReportUserComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 254 */       public void onRespondFollowRequestComplete(String reqId, int statusCode, String reasonPhrase, boolean accept, long userId) {
                }

/* 258 */       public void onUpdateExplicitComplete(String reqId, int statusCode, String reasonPhrase, boolean explicit) {
                }

/* 262 */       public void onUpdatePrivateComplete(String reqId, int statusCode, String reasonPhrase, boolean priv) {
                }

/* 266 */       public void onGetChannelsComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineChannel> channels) {
                }

/* 269 */       public void onSharePostComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 272 */       public void onGcmRegistrationComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

/* 276 */       public void onGetPendingNotificationCountComplete(String reqId, int statusCode, String reasonPhrase, int count) {
                }

/* 280 */       public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
                }

/* 284 */       public void onGetFriendsTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
                }

/* 288 */       public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
                }

/* 292 */       public void onEnableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

/* 296 */       public void onDisableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

/* 300 */       public void onDeactivateAccountComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

/* 303 */       public void onLowMemory() {
                }

/* 306 */       public void onTrimMemory(int level) {
                }

/* 310 */       public void onGetMessageInboxComplete(String reqId, int statusCode, String reasonPhrase, int count) {
                }

/* 313 */       public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction actionToRetry) {
                }

/* 316 */       public void onGetUserIdComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

/* 321 */       public void onProfilePhotoUpdatedComplete(String reqId, int statusCode, String reasonPhrase, String avatarUrl) {
                }

/* 326 */       public void onGetConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationObjectId, int responseCode, boolean empty) {
                }

/* 330 */       public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientAVineUser, long conversationObjectId, String username, boolean amFollowing) {
                }

/* 333 */       public void onGetConversationRemoteIdComplete(long conversationId) {
                }

/* 337 */       public void onRequestPhoneVerificationComplete(String reqId, int statusCode, String reasonPhrase, String phone) {
                }

/* 341 */       public void onVerifyPhoneNumberComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 345 */       public void onVerifyEmailComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 349 */       public void onRequestEmailVerificationComplete(String reqId, int statusCode, String reasonPhrase, String email) {
                }

/* 353 */       public void onIgnoreConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationObjectId) {
                }

/* 356 */       public void onDeleteConversationComplete(String reqId, int statusCode, String reasonPhrase, long aLong) {
                }

/* 359 */       public void onMergeNotificationComplete(VineSingleNotification lastNotification, @NonNull ArrayList<VineSingleNotification> notifications) {
                }

/* 362 */       public void onWebSocketConnectComplete() {
                }

/* 365 */       public void onWebSocketDisconnected() {
                }

/* 368 */       public void onWebSocketError() {
                }

/* 371 */       public void onDeleteCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
                }

/* 374 */       public void onSpamCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
                }

/* 377 */       public void onUpdateTypingStateComplete(boolean isTyping) {
                }

/* 380 */       public void onSubscribeConversationComplete() {
                }

/* 384 */       public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                }

/* 388 */       public void onUpdateAcceptOonComplete(String reqId, int statusCode, String reasonPhrase, boolean acceptOon) {
                }

/* 392 */       public void onUpdateEnableAddressBookComplete(String reqId, int statusCode, String reasonPhrase, boolean enableAddressBook) {
                }

/* 395 */       public void onPostVideoComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 399 */       public void onUpdateDiscoverability(String reqId, int statusCode, String reasonPhrase, int type, boolean enable) {
                }

/* 403 */       public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, @Nullable VineClientFlags clientFlags, boolean showBanner) {
                }

/* 406 */       public void onAbortAllRequestsComplete() {
                }

/* 409 */       public void onMobileDataNetworkActivated() {
                }

/* 412 */       public void onGetNotificationSettingsComplete(String reqId, ArrayList<VineNotificationSetting> notifSettings) {
                }

/* 415 */       public void onSaveNotificationSettingsComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 418 */       public void onFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 421 */       public void onBulkFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 424 */       public void onFavoriteUserComplete(String reqId, int statusCode, String reasonPhrase) {
                }

/* 429 */       public void onFetchFavoritePeopleComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users, int nextPage, int prevPage, String anchor) {
                }

/* 433 */       public void onFetchSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
                }

/* 437 */       public void onFetchOnboardingSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
                }

/* 440 */       public void setEnabled(boolean enabled) {
/* 441 */           this.mEnabled = enabled;
                }

/* 444 */       public boolean isEnabled() {
/* 445 */           return this.mEnabled;
                }

/* 449 */       public void onComplaintMenu(String reqId, int statusCode, ArrayList<ComplaintMenuOption> complaintMenu) {
                }
            }
