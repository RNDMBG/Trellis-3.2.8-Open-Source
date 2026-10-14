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
            
            
    public abstract class AppSessionListener {
                private boolean mEnabled;

        public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
                }

        public void onLogoutComplete(String reqId) {
                }

        public void onSignUpComplete(String reqId, int statusCode, String reasonPhrase, VineLogin vineLogin, String accountName, String password, String avatarUrl) {
                }

        public void onGetUsersComplete(Session session, String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int previousPage, long anchor) {
                }

        public void onGetTwitterAuthUrlComplete(String reqId, String authUrl) {
                }

        public void onGetEditionsComplete(int statusCode, ArrayList<Pair<String, String>> editions) {
                }

        public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
                }

        public void onGetTwitterUserComplete(String reqId, int statusCode, String reasonPhrase, TwitterUser user, VineLogin login) {
                }

       public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                }

       public void onPhotoImageError(ImageKey key, HttpResult result) {
                }

       public void onTwitterxAuthComplete(String reqId, int statusCode, String reasonPhrase, VineLogin login) {
                }

       public void onGetTwitterFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
                }

       public void onResetPasswordComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                }

       public void onVideoPathError(VideoKey key, HttpResult result) {
                }

       public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
                }

       public void onPostCommentComplete(String reqId, int statusCode, String reasonPhrase, long postId, String comment, VineComment vc) {
                }

       public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, @Nullable VineUser meUser, UrlCachePolicy policy) {
                }

       public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
                }

       public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

       public void onBulkFollowComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

       public void onGetActivityComplete(String reqId, int statusCode, String reasonPhrase, VinePagedData<VineEverydayNotification> notifications, VinePagedData<VineSingleNotification> followRequests, UrlCachePolicy policyUsed) {
                }

       public void onGetActivityCountComplete(String reqId, int statusCode, String reasonPhrase, int activityCount, int followRequestCount, int messagesCount, UrlCachePolicy policyUsed) {
                }

       public void onGetAddressFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
                }

       public void onRemoveUsersComplete(String reqId) {
                }

       public void onGetUserComplete(String reqId, int statusCode, String reasonPhrase, @Nullable VineUser user, UrlCachePolicy policy) {
                }

       public void onUpdateProfileComplete(String reqId, int statusCode, String reasonPhrase, String avatarUri) {
                }

       public void onUpdateEditionComplete(String reqId, int statusCode, String reasonPhrase, String edition) {
                }

       public void onReportPostComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

       public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
                }

       public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
                }

       public void onDisconnectTwitterComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

       public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

       public void onRevine(String reqId, int statusCode, String reasonPhrase, long postId, VineRepost repost) {
                }

       public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

       public void onGetSinglePostComplete(String reqId, int statusCode, String reasonPhrase, VinePost post) {
                }

       public void onGetPostIdComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
                }

       public void onFacebookSessionChanged(com.facebook.Session session, SessionState state, Exception exception) {
                }

       public void onUserSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineUser> users) {
                }

       public void onTagSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineTag> tags) {
                }

       public void onClearCacheComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onUpdateFollowEditorsPicksComplete(String reqId, int statusCode, String reasonPhrase, boolean isFollowing) {
                }

       public void onBlockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userBlocked, String blockedUsername) {
                }

       public void onUnblockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userUnblocked, String blockedUsername) {
                }

       public void onReportUserComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onRespondFollowRequestComplete(String reqId, int statusCode, String reasonPhrase, boolean accept, long userId) {
                }

       public void onUpdateExplicitComplete(String reqId, int statusCode, String reasonPhrase, boolean explicit) {
                }

       public void onUpdatePrivateComplete(String reqId, int statusCode, String reasonPhrase, boolean priv) {
                }

       public void onGetChannelsComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineChannel> channels) {
                }

       public void onSharePostComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onGcmRegistrationComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

       public void onGetPendingNotificationCountComplete(String reqId, int statusCode, String reasonPhrase, int count) {
                }

       public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
                }

       public void onGetFriendsTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
                }

       public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
                }

       public void onEnableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

       public void onDisableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

       public void onDeactivateAccountComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                }

       public void onLowMemory() {
                }

       public void onTrimMemory(int level) {
                }

       public void onGetMessageInboxComplete(String reqId, int statusCode, String reasonPhrase, int count) {
                }

       public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction actionToRetry) {
                }

       public void onGetUserIdComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                }

       public void onProfilePhotoUpdatedComplete(String reqId, int statusCode, String reasonPhrase, String avatarUrl) {
                }

       public void onGetConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationObjectId, int responseCode, boolean empty) {
                }

       public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientAVineUser, long conversationObjectId, String username, boolean amFollowing) {
                }

       public void onGetConversationRemoteIdComplete(long conversationId) {
                }

       public void onRequestPhoneVerificationComplete(String reqId, int statusCode, String reasonPhrase, String phone) {
                }

       public void onVerifyPhoneNumberComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onVerifyEmailComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onRequestEmailVerificationComplete(String reqId, int statusCode, String reasonPhrase, String email) {
                }

       public void onIgnoreConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationObjectId) {
                }

       public void onDeleteConversationComplete(String reqId, int statusCode, String reasonPhrase, long aLong) {
                }

       public void onMergeNotificationComplete(VineSingleNotification lastNotification, @NonNull ArrayList<VineSingleNotification> notifications) {
                }

       public void onWebSocketConnectComplete() {
                }

       public void onWebSocketDisconnected() {
                }

       public void onWebSocketError() {
                }

       public void onDeleteCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
                }

       public void onSpamCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
                }

       public void onUpdateTypingStateComplete(boolean isTyping) {
                }

       public void onSubscribeConversationComplete() {
                }

       public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                }

       public void onUpdateAcceptOonComplete(String reqId, int statusCode, String reasonPhrase, boolean acceptOon) {
                }

       public void onUpdateEnableAddressBookComplete(String reqId, int statusCode, String reasonPhrase, boolean enableAddressBook) {
                }

       public void onPostVideoComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onUpdateDiscoverability(String reqId, int statusCode, String reasonPhrase, int type, boolean enable) {
                }

       public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, @Nullable VineClientFlags clientFlags, boolean showBanner) {
                }

       public void onAbortAllRequestsComplete() {
                }

       public void onMobileDataNetworkActivated() {
                }

       public void onGetNotificationSettingsComplete(String reqId, ArrayList<VineNotificationSetting> notifSettings) {
                }

       public void onSaveNotificationSettingsComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onBulkFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onFavoriteUserComplete(String reqId, int statusCode, String reasonPhrase) {
                }

       public void onFetchFavoritePeopleComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users, int nextPage, int prevPage, String anchor) {
                }

       public void onFetchSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
                }

       public void onFetchOnboardingSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
                }

       public void setEnabled(boolean enabled) {
           this.mEnabled = enabled;
                }

       public boolean isEnabled() {
           return this.mEnabled;
                }

       public void onComplaintMenu(String reqId, int statusCode, ArrayList<ComplaintMenuOption> complaintMenu) {
                }
            }
