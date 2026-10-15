            package co.vine.android.service;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.ActivityManager;
            import android.app.Service;
            import android.content.BroadcastReceiver;
            import android.content.ContentResolver;
            import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.Parcelable;
            import android.os.Process;
            import android.text.TextUtils;
            import android.util.Log;
            import android.util.Pair;
            import co.vine.android.AppImpl;
            import co.vine.android.R;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.StartActivity;
            import co.vine.android.api.ComplaintMenuOption;
            import co.vine.android.api.PostInfo;
            import co.vine.android.api.ServerStatus;
            import co.vine.android.api.TwitterUser;
            import co.vine.android.api.VineActivityCounts;
            import co.vine.android.api.VineChannel;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineConversation;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineError;
            import co.vine.android.api.VineEverydayNotification;
            import co.vine.android.api.VineKnownErrors;
            import co.vine.android.api.VineLike;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineLoopSubmissionResponse;
            import co.vine.android.api.VineNotificationSetting;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VineParserReader;
            import co.vine.android.api.VineParsers;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VinePostResponse;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.api.VinePrivateMessagePostResponseWithUsers;
            import co.vine.android.api.VinePrivateMessageResponse;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineRepost;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineTypeAhead;
            import co.vine.android.api.VineUpload;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.TwitterVineApp;
            import co.vine.android.client.VineAPI;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.network.VineNetworkUtils;
            import co.vine.android.plugin.LogReaderTask;
            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossAnalytics;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.DateTimeUtil;
            import co.vine.android.util.FileLogger;
            import co.vine.android.util.LongSparseArray;
            import co.vine.android.util.LoopManager;
            import co.vine.android.util.MediaUtility;
            import co.vine.android.util.SMSUtil;
            import co.vine.android.util.StringAnchorManager;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.BehaviorManager;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonFactory;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.google.android.gms.plus.PlusShare;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.Map;
            import java.util.concurrent.ConcurrentHashMap;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import org.apache.commons.io.FileUtils;
            import org.apache.http.message.BasicNameValuePair;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            import org.scribe.builder.ServiceBuilder;
            import org.scribe.builder.api.TwitterApi;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.model.OAuthRequest;
            import org.scribe.model.Response;
            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            import org.scribe.oauth.OAuthService;
            import twitter4j.Twitter;
            import twitter4j.TwitterException;
            import twitter4j.VineTwitterFactory;
            import twitter4j.auth.AccessToken;
            import twitter4j.auth.BasicAuthorization;
            import twitter4j.conf.ConfigurationBuilder;
            
            
   public class VineService extends Service {
                public static final int ACTION_CODE_ABORT_ALL_REQUESTS = 112;
                public static final int ACTION_CODE_BLOCK_USER = 47;
                public static final int ACTION_CODE_BULK_FOLLOW = 107;
                public static final int ACTION_CODE_BULK_FOLLOW_CHANNEL = 117;
                public static final int ACTION_CODE_CHECK_LOGIN_TWITTER = 6;
                public static final int ACTION_CODE_CLEANUP = 29;
                public static final int ACTION_CODE_CLEAR_DB_ALL = 59;
                public static final int ACTION_CODE_CLEAR_DB_CACHE = 58;
                public static final int ACTION_CODE_CLEAR_GCM_REG_ID = 61;
                public static final int ACTION_CODE_CLEAR_INBOX_PAGE_CURSORS = 104;
                public static final int ACTION_CODE_CLEAR_NOTIFICATION = 102;
                public static final int ACTION_CODE_CLEAR_UNREAD_COUNT = 94;
                public static final int ACTION_CODE_CLIENT_PROFILE = 35;
                public static final int ACTION_CODE_CONNECT_TWITTER = 32;
                public static final int ACTION_CODE_DEACTIVATE_ACCOUNT = 67;
                public static final int ACTION_CODE_DELETE_COMMENT = 30;
                public static final int ACTION_CODE_DELETE_CONVERSATION = 80;
                public static final int ACTION_CODE_DELETE_MESSAGE = 95;
                public static final int ACTION_CODE_DELETE_POST = 31;
                public static final int ACTION_CODE_DELETE_SESSION = 64;
                public static final int ACTION_CODE_DEVICE_BOOT = 128;
                public static final int ACTION_CODE_DISABLE_USER_REPOSTS = 69;
                public static final int ACTION_CODE_DISCONNECT_TWITTER = 33;
                public static final int ACTION_CODE_ENABLE_USER_REPOSTS = 68;
                public static final int ACTION_CODE_EXPIRE_TIMELINE = 45;
                public static final int ACTION_CODE_FAVORITE_USER = 119;
                public static final int ACTION_CODE_FETCH_CLIENT_FLAGS = 111;
                public static final int ACTION_CODE_FETCH_COMPLAINTS_MENU = 130;
                public static final int ACTION_CODE_FETCH_FAVORITE_USERS = 120;
                public static final int ACTION_CODE_FETCH_FEATURED_CHANNELS = 53;
                public static final int ACTION_CODE_FETCH_FRIENDS_TYPEAHEAD = 99;
                public static final int ACTION_CODE_FETCH_ONBOARDING_SUGGESTED_FAVORITES = 122;
                public static final int ACTION_CODE_FETCH_SERVER_STATUS = 38;
                public static final int ACTION_CODE_FETCH_SUGGESTED_FAVORITES = 121;
                public static final int ACTION_CODE_FETCH_USERS_TYPEAHEAD = 39;
                public static final int ACTION_CODE_FOLLOW = 18;
                public static final int ACTION_CODE_FOLLOW_CHANNEL = 116;
                public static final int ACTION_CODE_FOLLOW_EDITORS_PICKS = 44;
                public static final int ACTION_CODE_FOLLOW_VINE_ON_TWITTER = 123;
                public static final int ACTION_CODE_GET_ACTIVITY = 20;
                public static final int ACTION_CODE_GET_ACTIVITY_COUNTS = 81;
                public static final int ACTION_CODE_GET_ADDRESS_FRIENDS = 16;
                public static final int ACTION_CODE_GET_COMMENTS = 12;
                public static final int ACTION_CODE_GET_CONVERSATION = 76;
                public static final int ACTION_CODE_GET_CONVERSATION_REMOTE_ID = 77;
                public static final int ACTION_CODE_GET_CONVERSATION_ROW_ID_FROM_SINGLE_RECIPIENT = 91;
                public static final int ACTION_CODE_GET_EDITIONS = 70;
                public static final int ACTION_CODE_GET_FOLLOWERS = 22;
                public static final int ACTION_CODE_GET_FOLLOWING = 23;
                public static final int ACTION_CODE_GET_FRIENDS = 82;
                public static final int ACTION_CODE_GET_INBOX = 74;
                public static final int ACTION_CODE_GET_NOTIFICATION_SETTINGS = 113;
                public static final int ACTION_CODE_GET_NOTIFICATION_USERS = 110;
                public static final int ACTION_CODE_GET_NOTIF_COUNT = 65;
                public static final int ACTION_CODE_GET_POSTS = 13;
                public static final int ACTION_CODE_GET_POST_ID = 36;
                public static final int ACTION_CODE_GET_POST_LIKERS = 11;
                public static final int ACTION_CODE_GET_POST_SINGLE = 28;
                public static final int ACTION_CODE_GET_REVINERS = 56;
                public static final int ACTION_CODE_GET_TAGS_TYPEAHEAD = 40;
                public static final int ACTION_CODE_GET_TWITTER_AUTH = 5;
                public static final int ACTION_CODE_GET_TWITTER_FRIENDS = 15;
                public static final int ACTION_CODE_GET_TWITTER_USER = 7;
                public static final int ACTION_CODE_GET_USER = 21;
                public static final int ACTION_CODE_GET_USERS_ME = 10;
                public static final int ACTION_CODE_GET_USER_ID = 86;
                public static final int ACTION_CODE_IGNORE_CONVERSATION = 79;
                public static final int ACTION_CODE_LIKE_POST = 24;
                public static final int ACTION_CODE_LOGIN = 2;
                public static final int ACTION_CODE_LOGOUT = 3;
                public static final int ACTION_CODE_MARK_CHANNEL_LAST_USED = 118;
                public static final int ACTION_CODE_MARK_MESSAGE_UPLOAD_FAILED = 100;
                public static final int ACTION_CODE_MERGE_PUSH_NOTIFICATION = 97;
                public static final int ACTION_CODE_MERGE_SINGLE_POST = 57;
                public static final int ACTION_CODE_MESSAGE_LOOP_VIEWED = 84;
                public static final int ACTION_CODE_MESSAGE_LOOP_VIEWED_BATCH = 85;
                public static final int ACTION_CODE_POST = 98;
                public static final int ACTION_CODE_POST_COMMENT = 14;
                public static final int ACTION_CODE_PREFETCH = 127;
                public static final int ACTION_CODE_PRE_MERGE_MESSAGE = 93;
                public static final int ACTION_CODE_REFRESH_SESSION_KEY = 1000;
                public static final int ACTION_CODE_REMOVE_ACCOUNT = 115;
                public static final int ACTION_CODE_REMOVE_FOLLOW_POSTS = 34;
                public static final int ACTION_CODE_REMOVE_USERS_BY_GROUP = 17;
                public static final int ACTION_CODE_REPORT_POST = 27;
                public static final int ACTION_CODE_REPORT_USER = 49;
                public static final int ACTION_CODE_REQUEST_EMAIL_VERIFICATION = 92;
                public static final int ACTION_CODE_REQUEST_PHONE_VERIFICATION = 89;
                public static final int ACTION_CODE_RESET_PASSWORD = 9;
                public static final int ACTION_CODE_RESPOND_FOLLOW_REQUEST = 50;
                public static final int ACTION_CODE_RESTART_CONNECTION_MANAGER = 1;
                public static final int ACTION_CODE_RETRY_ALL_IN_CONVERSATION = 103;
                public static final int ACTION_CODE_REVINE = 54;
                public static final int ACTION_CODE_SAVE_NOTIFICATION_SETTINGS = 114;
                public static final int ACTION_CODE_SAVE_POSTS = 43;
                public static final int ACTION_CODE_SEARCH_TAGS = 42;
                public static final int ACTION_CODE_SEARCH_USERS = 41;
                public static final int ACTION_CODE_SEND_ADDRESS_BOOK = 101;
                public static final int ACTION_CODE_SEND_FACEBOOK_TOKEN = 37;
                public static final int ACTION_CODE_SEND_GCM_REG_ID = 60;
                public static final int ACTION_CODE_SEND_LOOP_COUNTS = 66;
                public static final int ACTION_CODE_SET_BLOCK_PROFILE_REPOSTS = 87;
                public static final int ACTION_CODE_SHARE_AS_VM = 96;
                public static final int ACTION_CODE_SHARE_POST = 63;
                public static final int ACTION_CODE_SIGNUP = 4;
                public static final int ACTION_CODE_SPAM_COMMENT = 126;
                public static final int ACTION_CODE_TWITTER_XAUTH = 8;
                public static final int ACTION_CODE_UNBLOCK_USER = 48;
                public static final int ACTION_CODE_UNFOLLOW = 19;
                public static final int ACTION_CODE_UNLIKE_POST = 25;
                public static final int ACTION_CODE_UNREVINE = 55;
                public static final int ACTION_CODE_UPDATE_ACCEPT_OON = 105;
                public static final int ACTION_CODE_UPDATE_DISCOVERABILITY = 108;
                public static final int ACTION_CODE_UPDATE_EDITION = 71;
                public static final int ACTION_CODE_UPDATE_ENABLE_ADDRESS_BOOK = 106;
                public static final int ACTION_CODE_UPDATE_EXPLICIT = 51;
                public static final int ACTION_CODE_UPDATE_PRIVATE = 52;
                public static final int ACTION_CODE_UPDATE_PROFILE = 26;
                public static final int ACTION_CODE_UPDATE_PROFILE_PHOTO = 88;
                public static final int ACTION_CODE_VERIFY_EMAIL = 109;
                public static final int ACTION_CODE_VERIFY_PHONE_NUMBER = 90;
                public static final String ACTION_DEVICE_BOOT = "co.vine.android.device.boot";
                public static final String ACTION_LOGIN = "LOGIN";
                public static final String ACTION_PREFETCH = "co.vine.android.service.prefetch";
                public static final String ACTION_PROFILE_COLOR_CHANGED = "co.vine.android.profileColor";
                public static final String ACTION_RESTART_CONNECTION_MANAGER = "RESTART_CM";
                public static final int AMAZON_ERROR_CODE = 900;
                private static final String[] BADGER_ARGS;
                private static final String BADGER_SELECTION = "package=?";
                private static final String BADGE_COUNT = "badgecount";
                public static final String BC_ACTION_ALERT_RTC_NEW_MESSAGE = "co.vine.android.service.mergeSelfNewMessage";
                public static final String BC_ACTIVITY_COUNTS = "co.vine.android.service.activityCounts";
                private static final String CLASS = "class";
                public static final int ERROR_CODE_UNKNOWN = -1;
                public static final int ERROR_CODE_UPLOAD_FAILED = -2;
                private static final int EXECUTION_CAPTCHA_REQUIRED = 4;
                private static final String EXECUTION_CODE = "executionCode";
                private static final int EXECUTION_COMPLETE = 1;
                private static final int EXECUTION_LOGGED_OUT = 3;
                private static final int EXECUTION_SERVICE_NOT_READY = -999;
                private static final int EXECUTION_SESSION_KEY_INVALID = 2;
                public static final String EXTRA_ABORT_REQUESTS = "abort_requests";
                public static final String EXTRA_ACCEPT = "accept";
                public static final String EXTRA_ACCEPT_OON = "accept_oon";
                public static final String EXTRA_ACCOUNT_EXISTS = "a_exists";
                public static final String EXTRA_ACCOUNT_NAME = "a_name";
                public static final String EXTRA_ACTION_CODE = "action_code";
                public static final String EXTRA_ALSO_FETCH_POSTS = "also_fetch_posts";
                public static final String EXTRA_AM_FOLLOWING = "am_following";
                public static final String EXTRA_ANCHOR = "time";
                public static final String EXTRA_AVATAR_URL = "avatar_url";
                public static final String EXTRA_BLOCK_USER_ID = "block_user_id";
                public static final String EXTRA_CACHE_POLICY = "cache_policy";
                public static final String EXTRA_CALLBACK = "ibinder";
                public static final String EXTRA_CAPTCHA_URL = "captcha_url";
                public static final String EXTRA_CHANNELS = "channels";
                public static final String EXTRA_CHANNEL_ID = "channelId";
                public static final String EXTRA_CLEAR = "clear";
                public static final String EXTRA_CLIENT_FLAGS = "client_flags";
                public static final String EXTRA_COLOR_INT = "color_int";
                public static final String EXTRA_COMMENT = "comment";
                public static final String EXTRA_COMMENTS = "comments";
                public static final String EXTRA_COMMENT_ID = "comment_id";
                public static final String EXTRA_COMMENT_OBJ = "comment_obj";
                public static final String EXTRA_COMPLAINT_MENU = "complaint_menu";
                public static final String EXTRA_CONVERSATION_ID = "conversation_id";
                public static final String EXTRA_CONVERSATION_ROW_ID = "conversation_row_id";
                public static final String EXTRA_COUNT = "count";
                public static final String EXTRA_DATA = "data";
                public static final String EXTRA_DESCRIPTION = "desc";
                public static final String EXTRA_DIRECT_USER_ID = "direct_user_id";
                public static final String EXTRA_EDITIONS_LIST = "editions";
                public static final String EXTRA_EDITION_CODE = "edition";
                public static final String EXTRA_EMAIL = "email";
                public static final String EXTRA_EMPTY = "empty";
                public static final String EXTRA_ENABLE = "enable";
                public static final String EXTRA_ENTITIES = "entities";
                public static final String EXTRA_ERROR_CODE = "error_code";
                public static final String EXTRA_EXPLICIT = "explicit";
                public static final String EXTRA_FACEBOOK_TOKEN = "facebook_token";
                public static final String EXTRA_FAVORITE = "favorite";
                public static final String EXTRA_FOLLOWING = "following";
                public static final String EXTRA_FOLLOW_ID = "follow_id";
                public static final String EXTRA_FOLLOW_IDS = "follow_ids";
                public static final String EXTRA_FOLLOW_REQUESTS = "follow_requests";
                public static final String EXTRA_FOLLOW_REQUEST_COUNT = "follow_reqs_c";
                public static final String EXTRA_GCM_REG_ID = "gcmRegId";
                public static final String EXTRA_GET_FOLLOW_REQUESTS = "follow_reqs";
                public static final String EXTRA_HIDE_PROFILE_REPOSTS = "block_profile_reposts";
                public static final String EXTRA_HIDE_REPOSTS = "hide_reposts";
                public static final String EXTRA_IN_MEMORY = "in_memory";
                public static final String EXTRA_IS_POLLING = "is_polling";
                public static final String EXTRA_KEEP_UPLOAD = "should_delete_upload";
                public static final String EXTRA_KEY = "key";
                public static final String EXTRA_LOAD_URL = "load_url";
                public static final String EXTRA_LOCATION = "location";
                public static final String EXTRA_LOGGED_OUT = "logged_out";
                public static final String EXTRA_LOGIN = "login";
                public static final String EXTRA_LOOPS = "loops";
                public static final String EXTRA_LOOP_SUBMISSION_RESULT = "loop_submission";
                public static final String EXTRA_MANUAL = "manual";
                public static final String EXTRA_MESSAGE = "message";
                public static final String EXTRA_MESSAGES_COUNT = "messages_count";
                public static final String EXTRA_MESSAGE_ID = "message_id";
                public static final String EXTRA_MY_REPOST_ID = "my_repost_id";
                public static final String EXTRA_NETWORK = "network";
                public static final String EXTRA_NEXT_PAGE = "next_page";
                public static final String EXTRA_NOTIFICATION = "notification";
                public static final String EXTRA_NOTIFICATIONS = "notifications";
                public static final String EXTRA_NOTIFICATIONS_COUNT = "notifications_count";
                public static final String EXTRA_NOTIFICATION_DISPLAY_ID = "notification_display_id";
                public static final String EXTRA_NOTIFICATION_ID = "notification_id";
                public static final String EXTRA_NOTIFICATION_SETTINGS = "notificationSettings";
                public static final String EXTRA_NOTIFY = "notify";
                public static final String EXTRA_PAGE = "page";
                public static final String EXTRA_PAGE_TYPE = "page_type";
                public static final String EXTRA_PASSWORD = "pass";
                public static final String EXTRA_PHONE = "phone";
                public static final String EXTRA_POST = "post";
                public static final String EXTRA_POSTS = "posts";
                public static final String EXTRA_POST_ID = "post_id";
                public static final String EXTRA_POST_SHARE_ID = "post_share_id";
                public static final String EXTRA_PREFETCH = "prefetch";
                public static final String EXTRA_PREMERGED_MESSAGE_ID = "premerged_message_id";
                public static final String EXTRA_PREVIOUS_PAGE = "previous_page";
                public static final String EXTRA_PRIVATE = "priv";
                public static final String EXTRA_PROFILE_ID = "profile_id";
                public static final String EXTRA_QUERY = "q";
                public static final String EXTRA_REACTIVATE = "reactivate";
                public static final String EXTRA_REASON_PHRASE = "reason_phrase";
                public static final String EXTRA_RECIPIENT = "recipient";
                public static final String EXTRA_RECIPIENTS = "recipients";
                public static final String EXTRA_RECIPIENT_ID = "recipient_id";
                public static final String EXTRA_REFRESH_SESSION = "refresh_session";
                public static final String EXTRA_REPORTING_EVENT = "event";
                public static final String EXTRA_REPOST = "repost";
                public static final String EXTRA_REPOST_ID = "repost_id";
                public static final String EXTRA_REQUEST_ID = "rid";
                public static final String EXTRA_RESPONSE_CODE = "resp_code";
                public static final String EXTRA_RESPONSE_SUCCESS = "response_success";
                public static final String EXTRA_SECRET = "secret";
                public static final String EXTRA_SERVER_STATUS = "server_status";
                public static final String EXTRA_SESSION_KEY = "s_key";
                public static final String EXTRA_SESSION_OWNER_ID = "s_owner_id";
                public static final String EXTRA_SHOULD_FOLLOW_EDITORS_PICKS = "should_follow_editors_picks";
                public static final String EXTRA_SIZE = "size";
                public static final String EXTRA_SORT = "sort";
                public static final String EXTRA_TAGS = "tags";
                public static final String EXTRA_TAG_NAME = "tag_name";
                public static final String EXTRA_THUMBNAIL_URL = "thumbnail_url";
                public static final String EXTRA_TITLE = "title";
                public static final String EXTRA_TOKEN = "token";
                public static final String EXTRA_TWITTER_ID = "t_id";
                public static final String EXTRA_TWITTER_USER = "t_user";
                public static final String EXTRA_TYPE = "type";
                public static final String EXTRA_UPLOAD = "upload";
                public static final String EXTRA_UPLOAD_PATH = "upload_path";
                public static final String EXTRA_URI = "uri";
                public static final String EXTRA_USER = "user";
                public static final String EXTRA_USERNAME = "username";
                public static final String EXTRA_USERS = "users";
                public static final String EXTRA_USER_ID = "user_id";
                public static final String EXTRA_USER_INITIATED = "user_init";
                public static final String EXTRA_VANITY_URL = "vanity_url";
                public static final String EXTRA_VIDEO_URL = "post_url";
                private static final boolean LOGGABLE;
                private static final String PACKAGE = "package";
                private static final int PAGE_SIZE_SEARCH = 50;
                private static final String PARAM_ACCEPTS_OUT_OF_NETWORK_CONVERSATIONS = "acceptsOutOfNetworkConversations";
                private static final String PARAM_ADDRESS_BOOK = "addressBook";
                public static final String PARAM_ANCHOR = "anchor";
                private static final String PARAM_AUTHENTICATE = "authenticate";
                private static final String PARAM_AVATAR = "avatarUrl";
                private static final String PARAM_CHANNEL_IDS = "channelIds[]";
                private static final String PARAM_COLOR = "profileBackground";
                private static final String PARAM_COMMENT_MAX = "c_max";
                private static final String PARAM_COMMENT_OVERFLOW = "c_overflow";
                private static final String PARAM_CREATED = "created";
                private static final String PARAM_DATE = "date";
                private static final String PARAM_DESCRIPTION = "description";
                private static final String PARAM_DEVICE_TOKEN = "deviceToken";
                private static final String PARAM_DISABLE_ADDRESS_BOOK = "disableAddressBook";
                private static final String PARAM_EDITION = "edition";
                private static final String PARAM_EMAIL = "email";
                private static final String PARAM_FACEBOOK_AUTH_TOKEN = "facebookOauthToken";
                private static final String PARAM_HIDDEN_EMAIL = "hiddenEmail";
                private static final String PARAM_HIDDEN_PHONE_NUMBER = "hiddenPhoneNumber";
                private static final String PARAM_HIDDEN_TWITTER = "hiddenTwitter";
                private static final String PARAM_INBOX = "inbox";
                private static final String PARAM_INCLUDE_PROMOTED = "includePromoted";
                private static final String PARAM_LAST_MESSAGE_ID = "lastMessageId";
                private static final String PARAM_LIKE_MAX = "l_max";
                private static final String PARAM_LOCALE = "locale";
                private static final String PARAM_LOCATION = "location";
                private static final String PARAM_MESSAGE = "message";
                private static final String PARAM_NAME = "username";
                private static final String PARAM_NOTIFY = "notify";
                private static final String PARAM_PAGE = "page";
                private static final String PARAM_PASSWORD = "password";
                private static final String PARAM_PHONE = "phoneNumber";
                private static final String PARAM_PREFETCH = "prefetch";
                private static final String PARAM_PREINSTALL_TARGET = "preinstallTarget";
                private static final String PARAM_REPORTING_CODE = "code";
                private static final String PARAM_REPOSTS = "reposts";
                private static final String PARAM_REPOST_ID = "repostId";
                private static final String PARAM_SEARCH_TERM = "st";
                private static final String PARAM_SIZE = "size";
                private static final String PARAM_TWITTER_ID = "twitterId";
                private static final String PARAM_TWITTER_SECRET = "twitterOauthSecret";
                private static final String PARAM_TWITTER_TOKEN = "twitterOauthToken";
                private static final String PARAM_USERNAME = "username";
                private static final String PARAM_USER_IDS = "userIds[]";
                private static final String PARAM_VERIFY_EMAIL = "verifyEmail";
                private static final String PARAM_VERIFY_PHONE_NUMBER = "verifyPhoneNumber";
                private static final String PARAM_WITH_FAVORITES = "withFavorites";
                private static final String REASON_PHRASE = "reasonPhrase";
                private static final Uri SAMSUNG_BADGER_URI;
                private static final String START_CLASS_NAME;
                private static final String STATUS_CODE = "statusCode";
                private static final int STATUS_CODE_SERVICE_NOT_READY = -999;
                private static final String TAG = "VineService";
                private static final HashMap<String, Integer> sActionMap;
                private VineAPI mApi;
                private ExecutorService mExecutor;
                private final FileLogger mFileLogger;
                private MediaUtility mMediaUtility;
                private final BroadcastReceiver mServiceBroadCastReceiver;
                private LinkedHashMap<Integer, Boolean> mStartIds;
                public static final String BC_ACTION_FETCH_AND_MERGE_POST = "co.vine.android.service.mergePost";
                public static final IntentFilter SERVICE_INTENT_FILTER = new IntentFilter(BC_ACTION_FETCH_AND_MERGE_POST);
                public static final String BC_ACTION_MERGE_POST_INTO_FEED = "co.vine.android.service.mergePostFeed";
                public static final IntentFilter SHOW_POST_FILTER = new IntentFilter(BC_ACTION_MERGE_POST_INTO_FEED);
                private final Handler mMainHandler = new Handler(Looper.getMainLooper());
                private final NetworkOperationFactory<VineAPI> mNetOpFactory = VineNetworkUtils.getDefaultNetworkOperationFactory();
                private Twitter mTwitter = null;
                private final Messenger mMessenger = new Messenger(new VineServiceHandler());
                private final ConcurrentHashMap<Integer, NetworkOperation.CancelableRequest> mHttpRequests = new ConcurrentHashMap<>();

       public VineService() {
                    this.mFileLogger = FileLogger.getLogger(SLog.sLogsOn ? LogReaderTask.SERVICE_LOG : null);
           this.mServiceBroadCastReceiver = new C05221();
                }

       static  ExecutorService access$000(VineService x0) {
           return x0.mExecutor;
                }

       static  boolean access$200() {
           return LOGGABLE;
                }

       static  Handler access$300(VineService x0) {
           return x0.mMainHandler;
                }

                static {
           LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
           SAMSUNG_BADGER_URI = Uri.parse("content://com.sec.badge/apps");
           sActionMap = new HashMap<>(3);
           sActionMap.put(ACTION_RESTART_CONNECTION_MANAGER, 1);
           sActionMap.put(SessionChangedReceiver.ACTION_SESSION_LOGIN, 2);
           sActionMap.put(ACTION_DEVICE_BOOT, 128);
           sActionMap.put(ACTION_PREFETCH, 127);
          START_CLASS_NAME = StartActivity.class.getCanonicalName();
          BADGER_ARGS = new String[]{"co.vine.android"};
                }

       private static class ExecutionPair {

                    
                    final NetworkOperation f111op;

                    
                    final VineParserReader f112vp;

            ExecutionPair(VineParserReader x0, NetworkOperation x1, C05221 x2) {
               this(x0, x1);
                    }

           private ExecutionPair(VineParserReader vp, NetworkOperation op) {
               this.f112vp = vp;
               this.f111op = op;
                    }
                }

                
       class C05221 extends BroadcastReceiver {
           C05221() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               if (intent != null && VineService.BC_ACTION_FETCH_AND_MERGE_POST.equals(intent.getAction())) {
                   SLog.m446d("Received broadcasted intent to merge a single post.");
                   AppController.getInstance(VineService.this).mergeSinglePost(intent.getExtras());
                        }
                    }
                }

       class VineServiceHandler extends Handler {
           VineServiceHandler() {
                    }

                    @Override 
           public void handleMessage(Message msg) {
               msg.getData().setClassLoader(VineService.this.getClassLoader());
               VineService.access$000(VineService.this).execute(VineService.this.new ExecutionRunnable(0, msg.arg1, msg.getData(), null, msg.replyTo));
                    }
                }

                @Override 
       public IBinder onBind(Intent intent) {
           return this.mMessenger.getBinder();
                }

                @Override 
       public void onCreate() {
           super.onCreate();
           this.mExecutor = Executors.newCachedThreadPool();
           this.mStartIds = new LinkedHashMap<>(50, 50.0f);
           registerReceiver(this.mServiceBroadCastReceiver, SERVICE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           unregisterReceiver(this.mServiceBroadCastReceiver);
           this.mFileLogger.closeOutput();
                }

       private synchronized void init() {
                    try {
                        if (this.mApi == null) {
                   this.mApi = VineAPI.getInstance(this);
                        }
               if (this.mMediaUtility == null) {
                   this.mMediaUtility = new MediaUtility(this);
                        }
               if (SLog.sLogsOn) {
                   this.mFileLogger.prepareOutput();
                        }
                    } catch (Throwable th) {
                        CrashUtil.logException(th, "Post request failed", new Object[0]);
                    }
                }

                @Override 
       public int onStartCommand(Intent intent, int flags, int startId) {
                    VineServiceResponder responder;
           if (intent != null) {
               init();
               Bundle bundle = intent.getExtras();
               VineServiceCallback cb = (VineServiceCallback) intent.getParcelableExtra(EXTRA_CALLBACK);
               if (cb != null) {
                   responder = cb.getResponder();
                        } else {
                   responder = null;
                        }
               Integer action = sActionMap.get(intent.getAction());
               if (action == null) {
                   if (LOGGABLE) {
                       Log.d(TAG, "Unknown action: " + intent.getAction());
                            }
                        } else {
                   if (bundle == null) {
                       throw new IllegalArgumentException("Intent must contain extras: " + intent);
                            }
                   SLog.m465i("VineService started with action {}", action);
                   this.mExecutor.execute(new ExecutionRunnable(startId, action.intValue(), bundle, responder, null));
                   this.mStartIds.put(Integer.valueOf(startId), false);
                        }
                    }
                    return 2;
                }

                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                









       Bundle executeAction(int actionCode, Bundle bundle) throws Throwable {
                    Bundle bundle2;
                    String endpoint;
                    StringBuilder url;
                    String param;
                    long conversationId;
                    ArrayList<VineSingleNotification> unclearedNotifications;
                    LongSparseArray<VineSingleNotification> uniqueNotifications;
                    int n;
                    int i = 0;
                    VineSingleNotification notif;
                    VineConversation conversation;
                    VineSingleNotification notif2;
                    ArrayList<BasicNameValuePair> params;
                    int i2;
                    String finalAvatarUrl;
                    String email;
                    String phone;
                    int color;
                    ArrayList<VineUser> users;
                    ArrayList<VineUser> users2;
                    StringBuilder url2;
                    StringAnchorManager.Editor edit;
                    VinePagedData<VineUser> users3;
                    long lastId;
                    LongSparseArray<Long> orderMap;
                    long j;
                    AccountManager am;
                    Integer loginType;
                    long conversationRowId;
                    String finalAvatarUrl2;
                    ArrayList<BasicNameValuePair> params2;
                    String completeString;
           String rid = null;
                    try {
               rid = bundle.getString(EXTRA_REQUEST_ID);
                    } catch (Exception e) {
               CrashUtil.logException(new IllegalArgumentException(e));
                    }
           if (LOGGABLE) {
               Log.d(TAG, "Running VineService action: " + actionCode);
               if (bundle.size() == 1 && rid != null) {
                   this.mFileLogger.write(" {} ({})\n", Integer.valueOf(actionCode), rid);
                        } else {
                   this.mFileLogger.write(" {} ({})\n{} \n", Integer.valueOf(actionCode), rid, bundle);
                        }
                    }
                    try {
               init();
               String key = bundle.getString("s_key");
               String accountName = bundle.getString(EXTRA_ACCOUNT_NAME);
               long sessionOwnerId = bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L);
               VineDatabaseHelper databaseHelper = VineDatabaseHelper.getDatabaseHelper(this);
               Context context = getApplicationContext();
               int statusCode = 0;
               String reasonPhrase = null;
               NetworkOperation op = null;
               VineError error = null;
               VineParserReader vp = null;
               ExecutionPair ep = null;
                        switch (actionCode) {
                            case 2:
                       VineLogin login = (VineLogin) bundle.getParcelable(EXTRA_LOGIN);
                       String password = bundle.getString(EXTRA_PASSWORD);
                       VineParserReader vp2 = VineParserReader.createLoginReader();
                       ArrayList<BasicNameValuePair> params3 = new ArrayList<>();
                       if (!TextUtils.isEmpty(login.username) && !TextUtils.isEmpty(password)) {
                           params3.add(new BasicNameValuePair("username", login.username));
                           params3.add(new BasicNameValuePair("password", password));
                                }
                       boolean isTwitter = (TextUtils.isEmpty(login.twitterToken) || TextUtils.isEmpty(login.twitterSecret) || login.twitterUserId <= 0) ? false : true;
                       if (isTwitter) {
                           params3.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, login.twitterToken));
                           params3.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, login.twitterSecret));
                           params3.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(login.twitterUserId)));
                                }
                       String lastPathSegment = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
                       StringBuilder url3 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", lastPathSegment);
                       NetworkOperation op2 = this.mNetOpFactory.createPostRequest(context, url3, params3, vp2, this.mApi).execute();
                       if (op2.isOK()) {
                           VineLogin vl = (VineLogin) vp2.getParsedObject();
                           vl.name = vl.username;
                           vl.username = login.username;
                           bundle.putString(EXTRA_ACCOUNT_NAME, vl.name);
                           bundle.putParcelable(EXTRA_LOGIN, vl);
                           FlurryUtils.trackLoginSuccess(isTwitter);
                           StringBuilder usersMeUrl = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
                           vp = VineParserReader.createParserReader(2);
                           op = this.mNetOpFactory.createBasicAuthGetRequest(context, usersMeUrl, this.mApi, vp, vl.key).execute();
                           if (op.isOK()) {
                               VineUser me = (VineUser) vp.getParsedObject();
                               bundle.putString("avatar_url", me.avatarUrl);
                               bundle.putString(EXTRA_DESCRIPTION, me.description);
                                    }
                                    break;
                                } else {
                           bundle2 = new Bundle();
                           bundle2.putInt(STATUS_CODE, op2.statusCode);
                           FlurryUtils.trackLoginFailure(isTwitter, op2.statusCode);
                           VineError error2 = (VineError) vp2.getParsedObject();
                           if (error2 != null) {
                               bundle2.putString(REASON_PHRASE, error2.message);
                               bundle.putInt("error_code", error2.errorCode);
                                    } else {
                               bundle2.putString(REASON_PHRASE, getString(R.string.error_unknown));
                                    }
                           bundle2.putInt(EXECUTION_CODE, 1);
                                }
                      return bundle2;
                            case 3:
                       databaseHelper.clearAllData();
                       this.mApi.getCacheStorage().clear();
                                break;
                            case 4:
                       String name = bundle.getString(EXTRA_ACCOUNT_NAME);
                       String email2 = bundle.getString("email");
                       String password2 = bundle.getString(EXTRA_PASSWORD);
                       String phone2 = bundle.getString(EXTRA_PHONE);
                       TwitterUser twitterUser = (TwitterUser) bundle.getParcelable(EXTRA_TWITTER_USER);
                       StringBuilder url4 = new StringBuilder(this.mApi.getBaseUrl()).append("/users");
                       VineParserReader vp3 = VineParserReader.createParserReader(5);
                       ArrayList<BasicNameValuePair> params4 = new ArrayList<>();
                       boolean twitterSignup = twitterUser != null;
                       String token = bundle.getString(EXTRA_KEY);
                       String secret = bundle.getString("secret");
                       String preinstallKey = CrossAnalytics.getInstance(this).get(CrossAnalytics.PRE_INSTALL_SIGN_UP);
                       if (twitterSignup) {
                           if (!TextUtils.isEmpty(twitterUser.name)) {
                               params4.add(new BasicNameValuePair("username", twitterUser.name));
                                    }
                           params4.add(new BasicNameValuePair("location", twitterUser.location));
                           params4.add(new BasicNameValuePair("description", twitterUser.description));
                           params4.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(twitterUser.userId)));
                           params4.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token));
                           params4.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret));
                           if (!TextUtils.isEmpty(phone2)) {
                               params4.add(new BasicNameValuePair("phoneNumber", phone2));
                                    }
                                } else {
                           params4.add(new BasicNameValuePair("username", name));
                           params4.add(new BasicNameValuePair("email", email2));
                           params4.add(new BasicNameValuePair("password", password2));
                           if (!TextUtils.isEmpty(phone2)) {
                               params4.add(new BasicNameValuePair("phoneNumber", phone2));
                                    }
                                }
                       params4.add(new BasicNameValuePair("authenticate", "true"));
                       if (preinstallKey != null) {
                           params4.add(new BasicNameValuePair(PARAM_PREINSTALL_TARGET, preinstallKey));
                                }
                       NetworkOperation op3 = this.mNetOpFactory.createPostRequest(context, url4, params4, vp3, this.mApi).execute();
                       if (op3.isOK()) {
                           FlurryUtils.onSignupSuccess(twitterSignup);
                           if (preinstallKey != null) {
                               FlurryUtils.onSignupWithPreinstallSuccess(preinstallKey);
                               CrossAnalytics.getInstance(this).clear(CrossAnalytics.PRE_INSTALL_SIGN_UP);
                                    }
                           VineLogin vl2 = (VineLogin) vp3.getParsedObject();
                           if (twitterSignup) {
                               vl2.twitterToken = token;
                               vl2.twitterSecret = secret;
                               vl2.twitterUserId = twitterUser.userId;
                               vl2.loginType = 2;
                                    } else {
                               vl2.username = email2;
                                    }
                           bundle.putParcelable(EXTRA_LOGIN, vl2);
                           bundle.putString(EXTRA_ACCOUNT_NAME, name);
                           StringBuilder usersMeUrl2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
                           vp = VineParserReader.createParserReader(2);
                           op = this.mNetOpFactory.createBasicAuthGetRequest(context, usersMeUrl2, this.mApi, vp, vl2.key).execute();
                           if (op.isOK()) {
                               VineUser me2 = (VineUser) vp.getParsedObject();
                               bundle.putString("avatar_url", me2.avatarUrl);
                               bundle.putString(EXTRA_DESCRIPTION, me2.description);
                               bundle.putString("s_key", vl2.key);
                               bundle.putLong(EXTRA_SESSION_OWNER_ID, vl2.userId);
                                    }
                                    break;
                                } else {
                           bundle2 = new Bundle();
                           bundle2.putInt(STATUS_CODE, op3.statusCode);
                           VineError error3 = (VineError) vp3.getParsedObject();
                           FlurryUtils.onSignupFailure(twitterSignup, error3, op3.statusCode);
                           if (error3 != null) {
                               bundle2.putString(REASON_PHRASE, error3.message);
                                    } else {
                               bundle2.putString(REASON_PHRASE, getString(R.string.error_unknown));
                                    }
                           bundle2.putInt(EXECUTION_CODE, 1);
                                }
                      return bundle2;
                            case 5:
                      String tokenKey = bundle.getString(EXTRA_KEY);
                      String tokenSecret = bundle.getString("secret");
                      String callback = bundle.getString("uri");
                      OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey(tokenKey).apiSecret(tokenSecret).callback(callback).build();
                      Token token2 = service.getRequestToken();
                      String authUrl = service.getAuthorizationUrl(token2);
                      bundle.putString(EXTRA_LOAD_URL, authUrl);
                                break;
                            case 6:
                      String username = bundle.getString("username");
                      String tokenKey2 = bundle.getString(EXTRA_KEY);
                      String tokenSecret2 = bundle.getString("secret");
                      long userId = bundle.getLong(EXTRA_TWITTER_ID);
                      if (this.mTwitter == null) {
                          ConfigurationBuilder builder = new ConfigurationBuilder();
                          builder.setOAuthConsumerKey(TwitterVineApp.API_KEY).setOAuthConsumerSecret(TwitterVineApp.API_SECRET).setOAuthAccessToken(tokenKey2).setOAuthAccessTokenSecret(tokenSecret2);
                          this.mTwitter = new VineTwitterFactory(builder.build()).getInstance();
                                }
                      if (!TextUtils.isEmpty(tokenKey2) && !TextUtils.isEmpty(tokenSecret2)) {
                          String pathSegment = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
                          StringBuilder url5 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", pathSegment, "twitter");
                          ArrayList<BasicNameValuePair> params5 = new ArrayList<>(2);
                          params5.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, tokenKey2));
                          params5.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, tokenSecret2));
                          vp = VineParserReader.createParserReader(5);
                          op = this.mNetOpFactory.createPostRequest(context, url5, params5, vp, this.mApi).execute();
                          if (op.isOK()) {
                              bundle.putBoolean(EXTRA_ACCOUNT_EXISTS, true);
                              VineLogin vl3 = (VineLogin) vp.getParsedObject();
                              vl3.twitterUsername = username;
                              vl3.twitterToken = tokenKey2;
                              vl3.twitterSecret = tokenSecret2;
                              vl3.twitterUserId = userId;
                              vl3.loginType = 2;
                              bundle.putParcelable(EXTRA_LOGIN, vl3);
                              String usersMe = this.mApi.getBaseUrl() + "/users/me";
                              vp = VineParserReader.createParserReader(2);
                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, new StringBuilder(usersMe), this.mApi, vp, vl3.key).execute();
                              if (op.isOK()) {
                                  VineUser user = (VineUser) vp.getParsedObject();
                                  bundle.putParcelable("user", user);
                                        }
                                    } else {
                              error = (VineError) vp.getParsedObject();
                              if (error == null) {
                                  reasonPhrase = getString(R.string.error_server);
                                        } else {
                                  SLog.m448d("Got error: {} {}", Integer.valueOf(error.errorCode), error.message);
                                  int code = error.errorCode;
                                  if (VineKnownErrors.BAD_CREDENTIALS.code == code || VineKnownErrors.RECORD_DOES_NOT_EXIST.code == code || VineKnownErrors.ACCOUNT_DEACTIVATED.code == code) {
                                      VineLogin login2 = new VineLogin(null, username, tokenKey2, tokenSecret2, userId, null);
                                      bundle.putParcelable(EXTRA_LOGIN, login2);
                                      bundle.putBoolean(EXTRA_ACCOUNT_EXISTS, false);
                                      bundle.putInt("error_code", error.errorCode);
                                            } else {
                                      reasonPhrase = error.message;
                                            }
                                        }
                                    }
                                }
                                break;
                            case 7:
                      VineLogin v = (VineLogin) bundle.getParcelable(EXTRA_LOGIN);
                      OAuthService service2 = new ServiceBuilder().provider(TwitterApi.class).apiKey(TwitterVineApp.API_KEY).apiSecret(TwitterVineApp.API_SECRET).build();
                      String url6 = this.mApi.getTwitterUrl() + "/users/show.json?id=" + v.twitterUserId;
                      OAuthRequest request = new OAuthRequest(Verb.GET, url6);
                      Token token3 = new Token(v.twitterToken, v.twitterSecret);
                      service2.signRequest(token3, request);
                                try {
                          Response response = request.send();
                          if (response.isSuccessful()) {
                                        try {
                                  JsonParser p = new JsonFactory().createJsonParser(response.getBody());
                                  p.nextToken();
                                  TwitterUser tUser = VineParsers.parseTwitterUser(p);
                                  bundle.putParcelable(EXTRA_TWITTER_USER, tUser);
                                        } catch (IOException e2) {
                                  if (LOGGABLE) {
                                      SLog.m452d("Failed to parse.", (Throwable) e2);
                                            }
                                        }
                                    }
                                } catch (OAuthException oAuthException) {
                          SLog.m452d("Failed to connect.", (Throwable) oAuthException);
                                }
                                break;
                            case 8:
                      String username2 = bundle.getString("username");
                      String password3 = bundle.getString(EXTRA_PASSWORD);
                      ConfigurationBuilder builder2 = new ConfigurationBuilder();
                      builder2.setOAuthConsumerKey(TwitterVineApp.API_KEY);
                      builder2.setOAuthConsumerSecret(TwitterVineApp.API_SECRET);
                      Twitter twitter = new VineTwitterFactory(builder2.build()).getInstance(new BasicAuthorization(username2, password3));
                      Resources res = context.getResources();
                                try {
                                    try {
                              AccessToken aToken = twitter.getOAuthAccessToken();
                              String screenName = aToken.getScreenName();
                              String token4 = aToken.getToken();
                              String tokenSecret3 = aToken.getTokenSecret();
                              if (screenName != null && token4 != null && tokenSecret3 != null) {
                                  VineLogin login3 = new VineLogin(null, aToken.getScreenName(), token4, aToken.getTokenSecret(), aToken.getUserId(), null);
                                  bundle.putParcelable(EXTRA_LOGIN, login3);
                                  statusCode = 200;
                                        } else {
                                  statusCode = 500;
                                  reasonPhrase = res.getString(R.string.error_xauth);
                                  CrashUtil.log("Twitter access_token response contained empty params. screenName={},token={}, tokenSecret={}", screenName, token4, tokenSecret3);
                                        }
                              bundle.putString(EXTRA_PASSWORD, null);
                                    } catch (TwitterException e3) {
                              if (e3.getStatusCode() == 401) {
                                  statusCode = 401;
                                  if (Util.isXauth2FactorError(e3)) {
                                      reasonPhrase = res.getString(R.string.error_auth_2_factor_error);
                                            } else {
                                      reasonPhrase = res.getString(R.string.error_auth_username_pass);
                                            }
                                        } else if (e3.isCausedByNetworkIssue()) {
                                  statusCode = 500;
                                  reasonPhrase = res.getString(R.string.error_auth_check_internet_connection);
                                        } else {
                                  statusCode = 500;
                                  reasonPhrase = res.getString(R.string.error_xauth);
                                        }
                              bundle.putString(EXTRA_PASSWORD, null);
                                    }
                                } catch (Throwable th) {
                                    bundle.putString(EXTRA_PASSWORD, null);
                                    throw th;
                                }
                                break;
                            case 9:
                      FlurryUtils.trackResetPassword();
                      String email3 = bundle.getString("email");
                      StringBuilder url7 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "forgotPassword");
                      VineAPI.addParam(url7, "email", email3);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createGetRequest(context, url7, vp, this.mApi).execute();
                                break;
                            case 10:
                      op = getUsersMe(bundle);
                      vp = (VineParserReader) op.getReader();
                      FlurryUtils.trackGetUser(true);
                                break;
                            case 11:
                      long postId = bundle.getLong("post_id", 0L);
                      int page = bundle.getInt("page", 1);
                      long anchor = bundle.getLong(EXTRA_ANCHOR, 0L);
                      StringBuilder url8 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "likes");
                      VineAPI.addParam(url8, "page", page);
                      VineAPI.addAnchor(url8, anchor);
                      VineAPI.addParam(url8, "size", 20);
                      vp = VineParserReader.createParserReader(11);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url8, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VinePagedData<VineLike> likes = (VinePagedData) vp.getParsedObject();
                          databaseHelper.mergeLikes(likes.items, postId, likes.nextPage, likes.previousPage);
                          bundle.putInt("count", likes.count);
                          bundle.putInt("next_page", likes.nextPage);
                          bundle.putInt("previous_page", likes.previousPage);
                          bundle.putLong(EXTRA_ANCHOR, likes.anchor);
                          ArrayList<VineUser> users4 = new ArrayList<>();
                          Iterator i$ = likes.items.iterator();
                                    for (VineLike like : likes.items) {
                              users4.add(like.user);
                                    }
                          bundle.putParcelableArrayList("users", users4);
                          if (likes.nextPage <= 0) {
                              databaseHelper.markLastUser(5, String.valueOf(postId));
                                    }
                                }
                                break;
                            case 12:
                      op = getComments(bundle.getLong("post_id", 0L), bundle.getInt("page", 1), bundle.getLong(EXTRA_ANCHOR, 0L), bundle.getInt("size", 20), key, bundle, databaseHelper);
                      vp = (VineParserReader) op.getReader();
                      FlurryUtils.trackGetComments();
                                break;
                            case 13:
                      int type = bundle.getInt("type", -1);
                      StringBuilder url9 = generatePostFetchUrl(type, bundle.getLong(EXTRA_PROFILE_ID), bundle.getInt("page", 1), bundle, false);
                      op = getPosts(url9, type, bundle.getInt("size", 10), databaseHelper, bundle.getLong(EXTRA_ANCHOR, 0L), bundle, false);
                      vp = (VineParserReader) op.getReader();
                                break;
                            case 14:
                      ArrayList<VineEntity> entities = bundle.getParcelableArrayList("entities");
                      op = postComment(bundle.getLong("post_id"), bundle.getLong("repost_id"), bundle.getLong("user_id"), bundle.getString("username"), bundle.getString("comment"), bundle.getString("avatar_url"), entities, databaseHelper, bundle);
                      vp = (VineParserReader) op.getReader();
                      FlurryUtils.trackPostComment();
                                break;
                            case 15:
                      String twitterKey = bundle.getString(EXTRA_KEY);
                      String twitterSecret = bundle.getString("secret");
                      StringBuilder url10 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, "twitter");
                      vp = VineParserReader.createParserReader(3);
                      ArrayList<BasicNameValuePair> params6 = new ArrayList<>(2);
                      params6.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, twitterKey));
                      params6.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, twitterSecret));
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url10, this.mApi, params6, vp).execute();
                      if (op.isOK() && (users2 = (ArrayList) vp.getParsedObject()) != null && !users2.isEmpty()) {
                          databaseHelper.mergeUsers(users2, 6, String.valueOf(sessionOwnerId), 0, 0, null);
                          bundle.putInt("count", users2.size());
                          bundle.putParcelableArrayList("users", users2);
                                }
                                break;
                            case 16:
                      String addressBook = null;
                                try {
                          addressBook = Util.getAddressBookJson(getContentResolver());
                                } catch (IOException e4) {
                          if (LOGGABLE) {
                              e4.printStackTrace();
                                    }
                                }
                      if (addressBook == null && 0 != 0) {
                          op.statusCode = 1000;
                          error = new VineError(1000, "Could not create json.");
                                } else {
                          StringBuilder url11 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, VineAPI.RESOURCE_CONTACTS);
                          vp = VineParserReader.createParserReader(3);
                          ArrayList<BasicNameValuePair> params7 = new ArrayList<>();
                          params7.add(new BasicNameValuePair(PARAM_ADDRESS_BOOK, addressBook));
                          addCountryParam(params7);
                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url11, this.mApi, params7, vp).execute();
                          if (op.isOK() && (users = (ArrayList) vp.getParsedObject()) != null && !users.isEmpty()) {
                              databaseHelper.mergeUsers(users, 7, String.valueOf(sessionOwnerId), 0, 0, null);
                              bundle.putInt("count", users.size());
                              bundle.putParcelableArrayList("users", users);
                                    }
                                }
                                break;
                            case 17:
                      databaseHelper.removeUsersByGroup(bundle.getInt("type"));
                                break;
                            case 18:
                      long userIdToFollow = bundle.getLong(EXTRA_FOLLOW_ID);
                      boolean notify = bundle.getBoolean("notify");
                      StringBuilder url12 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userIdToFollow), VineAPI.RESOURCE_FOLLOWERS);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url12, this.mApi, null, vp).execute();
                      if (op.isOK()) {
                          databaseHelper.updateUserFollowingFlag(userIdToFollow, true);
                          databaseHelper.addFollow(userIdToFollow, sessionOwnerId, 0L, notify);
                          databaseHelper.markLastUser(1, String.valueOf(sessionOwnerId), "order_id ASC");
                                }
                                break;
                            case 19:
                      long userToUnfollow = bundle.getLong(EXTRA_FOLLOW_ID);
                      long currentProfileId = bundle.getLong(EXTRA_PROFILE_ID);
                      long activeUserId = bundle.getLong("user_id");
                      boolean notify2 = bundle.getBoolean("notify");
                      StringBuilder url13 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userToUnfollow), VineAPI.RESOURCE_FOLLOWERS);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url13, this.mApi, vp).execute();
                      if (op.isOK()) {
                          boolean deleteRow = currentProfileId == activeUserId;
                          databaseHelper.updateUserFollowingFlag(userToUnfollow, false);
                          databaseHelper.removeFollow(userToUnfollow, deleteRow, notify2);
                          databaseHelper.markLastUser(1, String.valueOf(sessionOwnerId), "order_id ASC");
                                }
                                break;
                            case 20:
                      ep = fetchAcitivity(sessionOwnerId, context, bundle);
                                break;
                            case 21:
                      long userId2 = bundle.getLong("user_id");
                      FlurryUtils.trackGetUser(userId2 != sessionOwnerId);
                      StringBuilder url14 = getUserUrl(userId2);
                      vp = VineParserReader.createParserReader(2);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url14, this.mApi, vp);
                      assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
                      op.execute();
                      if (op.isOK()) {
                          VineUser user2 = (VineUser) vp.getParsedObject();
                          bundle.putParcelable("user", user2);
                                }
                                break;
                            case 22:
                            case 23:
                      int userGroupsType = 2;
                      String resourceFollowType = VineAPI.RESOURCE_FOLLOWERS;
                      if (actionCode == 23) {
                          userGroupsType = 1;
                          resourceFollowType = "following";
                                }
                      long userId3 = bundle.getLong(EXTRA_PROFILE_ID, 0L);
                      int page2 = bundle.getInt("page", 1);
                      long anchor2 = bundle.getLong(EXTRA_ANCHOR, 0L);
                                if (userId3 > 0) {
                          StringBuilder url15 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId3), resourceFollowType);
                          VineAPI.addParam(url15, "page", page2);
                          VineAPI.addAnchor(url15, anchor2);
                          VineAPI.addParam(url15, "size", 20);
                          vp = VineParserReader.createParserReader(3);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url15, this.mApi, vp).execute();
                          if (op.isOK()) {
                              VinePagedData<VineUser> users5 = (VinePagedData) vp.getParsedObject();
                              if (users5.items != null) {
                                  LongSparseArray<Long> orderMap2 = new LongSparseArray<>(users5.items.size());
                                            for (VineUser u : users5.items) {
                                      orderMap2.put(u.userId, Long.valueOf(u.orderId));
                                            }
                                  databaseHelper.mergeUsers(users5.items, userGroupsType, String.valueOf(userId3), users5.nextPage, users5.previousPage, orderMap2);
                                  bundle.putInt("count", users5.count);
                                  bundle.putInt("next_page", users5.nextPage);
                                  bundle.putInt("previous_page", users5.previousPage);
                                  bundle.putLong(EXTRA_ANCHOR, users5.anchor);
                                  bundle.putParcelableArrayList("users", users5.items);
                                        }
                              if (users5.nextPage <= 0) {
                                  databaseHelper.markLastUser(userGroupsType, String.valueOf(userId3), "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 24:
                      long postId2 = bundle.getLong("post_id");
                      boolean notify3 = bundle.getBoolean("notify", true);
                      StringBuilder url16 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId2), "likes");
                      long repostId = bundle.getLong("repost_id");
                                if (repostId > 0) {
                          VineAPI.addParam(url16, PARAM_REPOST_ID, repostId);
                                }
                      vp = VineParserReader.createParserReader(10);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url16, this.mApi, null, vp).execute();
                      if (op.isOK()) {
                          Long likeId = (Long) vp.getParsedObject();
                          long meUserId = bundle.getLong("user_id");
                          String username3 = bundle.getString("username");
                          VineLike like = new VineLike();
                          like.likeId = likeId.longValue();
                          like.userId = meUserId;
                          like.username = username3;
                          like.postId = postId2;
                          databaseHelper.addLike(like, notify3);
                                }
                                break;
                            case 25:
                      long postId3 = bundle.getLong("post_id");
                      long repostId2 = bundle.getLong("repost_id");
                      boolean notify4 = bundle.getBoolean("notify", true);
                      StringBuilder url17 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId3), "likes");
                                if (repostId2 > 0) {
                          VineAPI.addParam(url17, PARAM_REPOST_ID, repostId2);
                                }
                      vp = VineParserReader.createParserReader(10);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url17, this.mApi, vp).execute();
                      if (op.isOK()) {
                          databaseHelper.removeLike(postId3, sessionOwnerId, notify4);
                                }
                                break;
                            case 26:
                      Uri avatarUri = (Uri) bundle.getParcelable("uri");
                      String f = null;
                      if (avatarUri != null) {
                                    try {
                              f = this.mMediaUtility.getPhotoUri(avatarUri, context);
                              StringBuilder url18 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                                        if (avatarUri == null) {
                                            i2 = 5;
                                        } else {
                                  i2 = 6;
                                        }
                              params = new ArrayList<>(i2);
                              params.add(new BasicNameValuePair("username", bundle.getString(EXTRA_ACCOUNT_NAME)));
                              params.add(new BasicNameValuePair("description", bundle.getString(EXTRA_DESCRIPTION)));
                              params.add(new BasicNameValuePair("location", bundle.getString("location")));
                              if (avatarUri == null && TextUtils.isEmpty(avatarUri.toString())) {
                                  finalAvatarUrl = "";
                                        } else {
                                  finalAvatarUrl = f;
                                        }
                              if (avatarUri != null) {
                                  params.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl));
                                        }
                              email = bundle.getString("email");
                              if (!TextUtils.isEmpty(email)) {
                                  params.add(new BasicNameValuePair("email", email));
                                        }
                              phone = bundle.getString(EXTRA_PHONE);
                              if (!TextUtils.isEmpty(phone)) {
                                  params.add(new BasicNameValuePair("phoneNumber", bundle.getString(EXTRA_PHONE)));
                                        }
                              if (bundle.containsKey(EXTRA_COLOR_INT)) {
                                  color = bundle.getInt(EXTRA_COLOR_INT);
                                  if (color > 0) {
                                      params.add(new BasicNameValuePair("profileBackground", String.valueOf(bundle.getInt(EXTRA_COLOR_INT))));
                                            }
                                        }
                              vp = VineParserReader.createParserReader(2);
                              op = this.mNetOpFactory.createBasicAuthPutRequest(context, url18, this.mApi, params, vp).execute();
                              if (op.isOK()) {
                                  if (avatarUri == null) {
                                      bundle.putString("avatar_url", "");
                                            } else {
                                      bundle.putString("avatar_url", finalAvatarUrl);
                                            }
                                        }
                                    } catch (Exception e5) {
                              statusCode = 900;
                              reasonPhrase = e5.getMessage();
                                    }
                                } else {
                          StringBuilder url19 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                                    if (avatarUri == null) {
                                        i2 = 5;
                                    } else {
                              i2 = 6;
                                    }
                          params = new ArrayList<>(i2);
                          params.add(new BasicNameValuePair("username", bundle.getString(EXTRA_ACCOUNT_NAME)));
                          params.add(new BasicNameValuePair("description", bundle.getString(EXTRA_DESCRIPTION)));
                          params.add(new BasicNameValuePair("location", bundle.getString("location")));
                          if (avatarUri == null) {
                              finalAvatarUrl = f;
                                    } else {
                              finalAvatarUrl = f;
                                    }
                          if (avatarUri != null) {
                              params.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl));
                                    }
                          email = bundle.getString("email");
                          if (!TextUtils.isEmpty(email)) {
                              params.add(new BasicNameValuePair("email", email));
                                    }
                          phone = bundle.getString(EXTRA_PHONE);
                          if (!TextUtils.isEmpty(phone)) {
                              params.add(new BasicNameValuePair("phoneNumber", bundle.getString(EXTRA_PHONE)));
                                    }
                          if (bundle.containsKey(EXTRA_COLOR_INT)) {
                              color = bundle.getInt(EXTRA_COLOR_INT);
                              if (color > 0) {
                                  params.add(new BasicNameValuePair("profileBackground", String.valueOf(bundle.getInt(EXTRA_COLOR_INT))));
                                        }
                                    }
                          vp = VineParserReader.createParserReader(2);
                          op = this.mNetOpFactory.createBasicAuthPutRequest(context, url19, this.mApi, params, vp).execute();
                          if (op.isOK()) {
                              if (avatarUri == null) {
                                  bundle.putString("avatar_url", "");
                                        } else {
                                  bundle.putString("avatar_url", finalAvatarUrl);
                                        }
                                    }
                                }
                                break;
                            case 27:
                      FlurryUtils.trackReportPost();
                      long postId4 = bundle.getLong("post_id");
                      String event = bundle.getString("event");
                      ArrayList<BasicNameValuePair> params8 = null;
                      if (!TextUtils.isEmpty(event)) {
                          params8 = new ArrayList<>();
                          params8.add(new BasicNameValuePair("code", event));
                                }
                      StringBuilder url20 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId4), VineAPI.RESOURCE_COMPLAINTS);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url20, this.mApi, params8, vp).execute();
                                break;
                            case 28:
                      long postId5 = bundle.getLong("post_id");
                                if (postId5 > 0) {
                          StringBuilder url21 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "timelines", "posts", Long.valueOf(postId5));
                          limitCommentAndLikesIfNeeded(url21);
                          vp = VineParserReader.createParserReader(8);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url21, this.mApi, vp);
                          assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
                          op.execute();
                          if (op.isOK()) {
                              VinePagedData<VinePost> response2 = (VinePagedData) vp.getParsedObject();
                                        try {
                                  databaseHelper.mergePosts(response2.items, 7, "", 0, 0, 0, 0L, true);
                                  if (response2 != null && response2.items != null) {
                                      bundle.putParcelable("post", (Parcelable) response2.items.get(0));
                                            }
                                        } catch (IOException e6) {
                                  if (LOGGABLE) {
                                      e6.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                break;
                            case 29:
                      databaseHelper.cleanUp(bundle.getLong(EXTRA_ANCHOR));
                                break;
                            case 30:
                      op = deleteComment(bundle.getLong("post_id"), bundle.getLong("repost_id"), bundle.getString("comment_id"), databaseHelper);
                      vp = (VineParserReader) op.getReader();
                      FlurryUtils.trackDeleteComment();
                                break;
                            case 31:
                      long postId6 = bundle.getLong("post_id");
                      StringBuilder url22 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId6));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url22, this.mApi, vp).execute();
                      if (op.isOK()) {
                          databaseHelper.deletePost(postId6);
                                }
                                break;
                            case 32:
                      FlurryUtils.trackConnectTwitter();
                      String token5 = bundle.getString(EXTRA_KEY);
                      String secret2 = bundle.getString("secret");
                      long userId4 = bundle.getLong("user_id");
                      StringBuilder url23 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                      ArrayList<BasicNameValuePair> params9 = new ArrayList<>(3);
                      params9.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token5));
                      params9.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret2));
                      params9.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(userId4)));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url23, this.mApi, params9, vp).execute();
                                break;
                            case 33:
                      FlurryUtils.trackDisConnectTwitter();
                      StringBuilder url24 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                      ArrayList<BasicNameValuePair> params10 = new ArrayList<>(3);
                      params10.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, ""));
                      params10.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, ""));
                      params10.add(new BasicNameValuePair(PARAM_TWITTER_ID, ""));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url24, this.mApi, params10, vp).execute();
                                break;
                            case 34:
                      databaseHelper.removePostsFromGroupByUser(bundle.getLong("user_id"), 1, String.valueOf(sessionOwnerId));
                                break;
                            case 35:
                      op = getClientProfile();
                                break;
                            case 36:
                      String postShareId = bundle.getString("post_share_id");
                      StringBuilder url25 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "timelines", "posts", VineAPI.RESOURCE_SHARED, postShareId);
                      vp = VineParserReader.createParserReader(8);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url25, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VinePagedData<VinePost> response3 = (VinePagedData) vp.getParsedObject();
                          ArrayList<VinePost> arrayList = response3.items;
                          if (!arrayList.isEmpty()) {
                              bundle.putLong("post_id", ((VinePost) arrayList.get(0)).postId);
                                    }
                                }
                                break;
                            case 37:
                      FlurryUtils.trackConnectFacebook();
                      StringBuilder url26 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                      ArrayList<BasicNameValuePair> params11 = new ArrayList<>(3);
                      params11.add(new BasicNameValuePair(PARAM_FACEBOOK_AUTH_TOKEN, bundle.getString(EXTRA_FACEBOOK_TOKEN)));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url26, this.mApi, params11, vp).execute();
                      SLog.m447d("Send facebook token result {}.", op != null ? Integer.valueOf(op.statusCode) : op);
                      if (op != null && op.isOK()) {
                          AppController.setPendingFacebookToken(this, null);
                                }
                                break;
                            case 38:
                      StringBuilder url27 = new StringBuilder(BuildUtil.getStatusUrl(context));
                      vp = VineParserReader.createParserReader(15);
                      op = this.mNetOpFactory.createGetRequest(context, url27, vp, this.mApi).execute();
                      if (op.isOK()) {
                          ServerStatus ss = (ServerStatus) vp.getParsedObject();
                          bundle.putParcelable(EXTRA_SERVER_STATUS, ss);
                                }
                                break;
                            case 39:
                            case 99:
                      String query = bundle.getString(EXTRA_QUERY);
                      String encodedQuery = VineAPI.encode(query.trim());
                      StringBuilder url28 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "search", encodedQuery);
                                switch (actionCode) {
                                    case 39:
                              VineAPI.addParam(url28, PARAM_SEARCH_TERM, VineEntity.TYPE_MENTION);
                                        break;
                                    case 99:
                              VineAPI.addParam(url28, PARAM_SEARCH_TERM, "message");
                                        break;
                                }
                      vp = VineParserReader.createParserReader(3);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url28, this.mApi, vp, key).execute();
                      if (op.isOK()) {
                          VinePagedData<VineUser> users6 = (VinePagedData) vp.getParsedObject();
                          if (users6.items != null) {
                              databaseHelper.mergeUsers(users6.items, 0, null, 0, 0, null);
                              bundle.putString(EXTRA_QUERY, query);
                              bundle.putParcelableArrayList("users", users6.items);
                                    }
                                }
                                break;
                            case 40:
                      String query2 = bundle.getString(EXTRA_QUERY);
                      String encodedQuery2 = VineAPI.encode(query2);
                      StringBuilder url29 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "tags", "search", encodedQuery2);
                      vp = VineParserReader.createParserReader(14);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url29, this.mApi, vp, key).execute();
                      if (op.isOK()) {
                          VinePagedData<VineTag> tags = (VinePagedData) vp.getParsedObject();
                          if (tags.items != null) {
                              bundle.putString(EXTRA_QUERY, query2);
                              bundle.putParcelableArrayList("tags", tags.items);
                                    }
                                }
                                break;
                            case 41:
                      String query3 = bundle.getString(EXTRA_QUERY);
                      int page3 = bundle.getInt("page");
                      if (!TextUtils.isEmpty(query3)) {
                          FlurryUtils.trackSearchUsers();
                          StringBuilder url30 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "search", query3);
                          VineAPI.addParam(url30, "page", page3);
                          VineAPI.addParam(url30, "size", 50);
                          vp = VineParserReader.createParserReader(3);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url30, this.mApi, vp, key).execute();
                          if (op.isOK()) {
                              VinePagedData<VineUser> users7 = (VinePagedData) vp.getParsedObject();
                              if (users7.items != null) {
                                  bundle.putInt("count", users7.count);
                                  bundle.putInt("next_page", users7.nextPage);
                                  bundle.putInt("previous_page", users7.previousPage);
                                  bundle.putParcelableArrayList("users", users7.items);
                                        }
                              if (users7.nextPage <= 0) {
                                  databaseHelper.markLastUser(8, null, "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 42:
                      String query4 = bundle.getString(EXTRA_QUERY);
                      int page4 = bundle.getInt("page");
                      if (!TextUtils.isEmpty(query4)) {
                          FlurryUtils.trackSearchTags();
                          StringBuilder url31 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "tags", "search", query4);
                          VineAPI.addParam(url31, "page", page4);
                          VineAPI.addParam(url31, "size", 50);
                          vp = VineParserReader.createParserReader(14);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url31, this.mApi, vp, key).execute();
                          if (op.isOK()) {
                              VinePagedData<VineTag> tags2 = (VinePagedData) vp.getParsedObject();
                              if (tags2.items != null) {
                                  databaseHelper.mergeSearchedTags(tags2.items, tags2.nextPage, tags2.previousPage);
                                  bundle.putInt("count", tags2.count);
                                  bundle.putInt("next_page", tags2.nextPage);
                                  bundle.putInt("previous_page", tags2.previousPage);
                                  bundle.putParcelableArrayList("tags", tags2.items);
                                        }
                              if (tags2.nextPage <= 0) {
                                  databaseHelper.markLastTag();
                                        }
                                    }
                                }
                                break;
                            case 43:
                      Collection<VinePost> posts = bundle.getParcelableArrayList("posts");
                      int groupType = bundle.getInt("type");
                      String tag = bundle.getString("tag_name");
                      int pageType = bundle.getInt(EXTRA_PAGE_TYPE);
                      int next = bundle.getInt("next_page");
                      int previous = bundle.getInt("previous_page");
                      long anchor3 = bundle.getLong(EXTRA_ANCHOR);
                      boolean userInitiated = bundle.getBoolean(EXTRA_USER_INITIATED);
                      if (posts != null) {
                                    try {
                              databaseHelper.mergePosts(posts, groupType, tag, pageType, next, previous, anchor3, userInitiated);
                              if (next <= 0) {
                                  databaseHelper.markLastPost(groupType, tag);
                                        }
                                    } catch (IOException e7) {
                              if (LOGGABLE) {
                                  e7.printStackTrace();
                                        }
                                    }
                                }
                                break;
                            case 44:
                       ArrayList<BasicNameValuePair> params12 = new ArrayList<>();
                       params12.add(new BasicNameValuePair(PARAM_INCLUDE_PROMOTED, String.valueOf(bundle.getInt(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS))));
                       StringBuilder url32 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
                       vp = VineParserReader.createParserReader(1);
                       op = this.mNetOpFactory.createBasicAuthPutRequest(context, url32, this.mApi, params12, vp).execute();
                       boolean shouldFollow = bundle.getInt(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS) == 1;
                       bundle.putBoolean(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS, shouldFollow);
                       if (!shouldFollow) {
                           bundle.putBoolean(EXTRA_ALSO_FETCH_POSTS, true);
                                }
                                break;
                            case 45:
                       databaseHelper.expireTimeline(bundle.getInt("type"));
                                break;
                            case 47:
                      FlurryUtils.trackBlockUser();
                      long currentUserId = bundle.getLong("user_id");
                      long userToBlock = bundle.getLong(EXTRA_BLOCK_USER_ID);
                      StringBuilder url33 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(currentUserId), "blocked", Long.valueOf(userToBlock));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url33, this.mApi, null, vp).execute();
                      if (op.isOK()) {
                          boolean userBlocked = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, userBlocked);
                                }
                                break;
                            case 48:
                      FlurryUtils.trackUnBlockUser();
                      long currentUserId2 = bundle.getLong("user_id");
                      long userToUnblock = bundle.getLong(EXTRA_BLOCK_USER_ID);
                      StringBuilder url34 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(currentUserId2), "blocked", Long.valueOf(userToUnblock));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url34, this.mApi, vp).execute();
                      if (op.isOK()) {
                          boolean userUnblocked = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, userUnblocked);
                                }
                                break;
                            case 49:
                      FlurryUtils.trackReportUser();
                      long userToReport = bundle.getLong("user_id");
                      String event2 = bundle.getString("event");
                      ArrayList<BasicNameValuePair> params13 = null;
                      if (!TextUtils.isEmpty(event2)) {
                          params13 = new ArrayList<>();
                          params13.add(new BasicNameValuePair("code", event2));
                                }
                      StringBuilder url35 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userToReport), VineAPI.RESOURCE_COMPLAINTS);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url35, this.mApi, params13, vp).execute();
                                break;
                            case 50:
                      long followerId = bundle.getLong("user_id");
                      boolean accept = bundle.getBoolean(EXTRA_ACCEPT);
                      StringBuilder url36 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_FOLLOWERS, VineAPI.RESOURCE_REQUESTS, Long.valueOf(followerId));
                      vp = VineParserReader.createParserReader(1);
                      op = accept ? this.mNetOpFactory.createBasicAuthPutRequest(context, url36, this.mApi, null, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, url36, this.mApi, vp).execute();
                                break;
                            case 51:
                      boolean explicit = bundle.getBoolean("explicit");
                      StringBuilder url37 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "explicit");
                      vp = VineParserReader.createParserReader(1);
                      if (explicit) {
                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url37, this.mApi, null, vp);
                                } else {
                          op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url37, this.mApi, vp);
                                }
                      op.execute();
                                break;
                            case 52:
                      boolean priv = bundle.getBoolean(EXTRA_PRIVATE);
                      StringBuilder url38 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                      vp = VineParserReader.createParserReader(1);
                      ArrayList<BasicNameValuePair> params14 = new ArrayList<>(1);
                      params14.add(new BasicNameValuePair("private", priv ? "1" : "0"));
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url38, this.mApi, params14, vp).execute();
                                break;
                            case 53:
                      int pageType2 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
                      int page5 = 1;
                                switch (pageType2) {
                                    case 1:
                                    case 2:
                              page5 = 1;
                                        break;
                                    case 3:
                              int previous2 = databaseHelper.getPreviousPageCursor(2, 0, null, true);
                              if (previous2 > 0) {
                                  page5 = previous2;
                                        }
                                        break;
                                }
                      StringBuilder url39 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", VineAPI.RESOURCE_FEATURED);
                      VineAPI.addParam(url39, "page", page5);
                      vp = VineParserReader.createParserReader(18);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url39, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VinePagedData<VineChannel> results = (VinePagedData) vp.getParsedObject();
                          ArrayList<VineChannel> arrayList2 = results.items;
                          databaseHelper.mergeChannels(arrayList2, results.nextPage, results.previousPage);
                          if (results.nextPage <= 0) {
                              databaseHelper.markLastChannel();
                                    }
                          bundle.putParcelableArrayList("channels", arrayList2);
                                }
                                break;
                            case 54:
                      long postId7 = bundle.getLong("post_id");
                      long repostId3 = bundle.getLong("repost_id");
                      StringBuilder url40 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId7), "repost");
                                if (repostId3 > 0) {
                          VineAPI.addParam(url40, PARAM_REPOST_ID, repostId3);
                                }
                      vp = VineParserReader.createParserReader(19);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url40, this.mApi, null, vp).execute();
                      if (op.isOK()) {
                          VineRepost repost = (VineRepost) vp.getParsedObject();
                          String username4 = bundle.getString("username");
                          repost.userId = sessionOwnerId;
                          repost.username = username4;
                          bundle.putSerializable("repost", repost);
                          databaseHelper.revine(repost, sessionOwnerId, false);
                                }
                                break;
                            case 55:
                      boolean following = bundle.getBoolean("following");
                      long postId8 = bundle.getLong("post_id");
                      long myRepostId = bundle.getLong("my_repost_id");
                      long repostId4 = bundle.getLong("repost_id");
                      boolean notify5 = bundle.getBoolean("notify", true);
                      StringBuilder url41 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId8), "repost", Long.valueOf(myRepostId));
                                if (repostId4 > 0) {
                          VineAPI.addParam(url41, PARAM_REPOST_ID, repostId4);
                                }
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url41, this.mApi, vp).execute();
                      if (op.isOK()) {
                          databaseHelper.unRevine(postId8, sessionOwnerId, following, notify5);
                                }
                                break;
                            case 56:
                      long postId9 = bundle.getLong("post_id", 0L);
                      int page6 = bundle.getInt("page", 1);
                      long anchor4 = bundle.getLong(EXTRA_ANCHOR, 0L);
                      StringBuilder url42 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId9), "reposts");
                      VineAPI.addParam(url42, "page", page6);
                      VineAPI.addAnchor(url42, anchor4);
                      VineAPI.addParam(url42, "size", 20);
                      vp = VineParserReader.createParserReader(3);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url42, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VinePagedData<VineUser> users8 = (VinePagedData) vp.getParsedObject();
                          databaseHelper.mergeUsers(users8.items, 9, String.valueOf(postId9), users8.nextPage, users8.previousPage, null);
                          bundle.putInt("count", users8.count);
                          bundle.putInt("next_page", users8.nextPage);
                          bundle.putInt("previous_page", users8.previousPage);
                          bundle.putLong(EXTRA_ANCHOR, users8.anchor);
                          bundle.putParcelableArrayList("users", users8.items);
                          if (users8.nextPage <= 0) {
                              databaseHelper.markLastUser(9, String.valueOf(postId9));
                                    }
                                }
                                break;
                            case 57:
                      VinePostResponse vpr = (VinePostResponse) bundle.getParcelable("post");
                      VineAPI api = VineAPI.getInstance(this);
                      StringBuilder getPostUrl = VineAPI.buildUponUrl(api.getBaseUrl(), "timelines", "posts", Long.valueOf(vpr.postId));
                      vp = VineParserReader.createParserReader(8);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, getPostUrl, api, vp, key).execute();
                      if (op.isOK()) {
                          VinePagedData<VinePost> posts2 = (VinePagedData) vp.getParsedObject();
                          if (posts2 != null && !posts2.items.isEmpty()) {
                              VineDatabaseHelper db = VineDatabaseHelper.getDatabaseHelper(this);
                                        try {
                                  db.mergePosts(posts2.items, 1, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
                                  db.mergePosts(posts2.items, 2, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
                                        } catch (IOException e8) {
                                  SLog.m459e("Error writing uploaded post to db", (Throwable) e8);
                                        }
                              Intent i3 = new Intent();
                              i3.setAction(BC_ACTION_MERGE_POST_INTO_FEED);
                              i3.putExtra("post", posts2.items.get(0));
                              sendBroadcast(i3, CrossConstants.BROADCAST_PERMISSION);
                                    }
                                } else {
                          error = (VineError) vp.getParsedObject();
                          if (op != null && error != null) {
                              SLog.m457e("Error retrieving uploaded post from server: {} {}", Integer.valueOf(op.statusCode), error.message);
                                    }
                                }
                                break;
                            case 58:
                      boolean notify6 = bundle.getBoolean("notify", false);
                      databaseHelper.clearCachedData(notify6);
                      statusCode = 200;
                                break;
                            case 59:
                      databaseHelper.clearAllData();
                      statusCode = 200;
                                break;
                            case 60:
                      String regId = bundle.getString(EXTRA_GCM_REG_ID, "");
                      StringBuilder url43 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(bundle.getLong("user_id")));
                      ArrayList<BasicNameValuePair> params15 = new ArrayList<>();
                      params15.add(new BasicNameValuePair("deviceToken", regId));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url43, this.mApi, params15, vp).execute();
                                break;
                            case 61:
                      String regId2 = bundle.getString(EXTRA_GCM_REG_ID);
                      if (regId2 == null) {
                          regId2 = "";
                                }
                      long userId5 = bundle.getLong("user_id");
                      String cachedKey = bundle.getString("s_key");
                      if (cachedKey == null) {
                          cachedKey = "";
                                }
                      StringBuilder url44 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId5), "deviceToken");
                      VineAPI.addParam(url44, "deviceToken", regId2);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url44, this.mApi, vp, cachedKey).execute();
                                break;
                            case 63:
                      long postId10 = bundle.getLong("post_id");
                      String network = bundle.getString("network");
                      String message = bundle.getString("message");
                      FlurryUtils.trackSharePost(network, postId10);
                      StringBuilder url45 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId10), VineAPI.RESOURCE_SHARE, network);
                      ArrayList<BasicNameValuePair> params16 = new ArrayList<>();
                      params16.add(new BasicNameValuePair("message", message));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url45, this.mApi, params16, vp).execute();
                                break;
                            case 64:
                      String cachedKey2 = bundle.getString("s_key");
                      if (cachedKey2 == null) {
                          cachedKey2 = "";
                                }
                      StringBuilder url46 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "authenticate");
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url46, this.mApi, vp, cachedKey2).execute();
                                break;
                            case 65:
                      StringBuilder url47 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "pendingNotificationsCount");
                      vp = VineParserReader.createParserReader(21);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url47, this.mApi, vp).execute();
                      if (op.isOK()) {
                          bundle.putInt("count", ((Integer) vp.getParsedObject()).intValue());
                                }
                                break;
                            case 66:
                      ArrayList<LoopManager.Record> records = bundle.getParcelableArrayList("loops");
                      StringBuilder url48 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "loops");
                      JSONArray loopsList = new JSONArray();
                      JSONObject postBody = new JSONObject();
                                try {
                                    for (LoopManager.Record r : records) {
                              JSONObject loopBody = new JSONObject();
                              loopBody.put("postId", r.postId);
                              loopBody.put("count", r.loopCount);
                              loopBody.put(com.twitter.android.sdk.Twitter.EXTRA_TOKEN_SECRET, r.timeStamp / 1000.0d);
                              loopsList.put(loopBody);
                                    }
                          postBody.put("loops", loopsList);
                                } catch (JSONException e9) {
                          CrashUtil.logOrThrowInDebug(e9);
                                }
                      vp = VineParserReader.createParserReader(27);
                      op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url48, this.mApi, postBody, vp).execute();
                      if (op.isOK()) {
                          VineLoopSubmissionResponse response4 = (VineLoopSubmissionResponse) vp.getParsedObject();
                          bundle.putParcelable(EXTRA_LOOP_SUBMISSION_RESULT, response4);
                                }
                                break;
                            case 67:
                      StringBuilder url49 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url49, this.mApi, vp).execute();
                      if (op.isOK()) {
                          boolean success = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success);
                                }
                                break;
                            case 68:
                      StringBuilder url50 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(bundle.getLong("user_id")), VineAPI.RESOURCE_FOLLOWERS, "reposts");
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url50, this.mApi, null, vp).execute();
                      if (op.isOK()) {
                          boolean success2 = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success2);
                                }
                                break;
                            case 69:
                      long userId6 = bundle.getLong("user_id");
                      StringBuilder url51 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId6), VineAPI.RESOURCE_FOLLOWERS, "reposts");
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url51, this.mApi, vp).execute();
                      if (op.isOK()) {
                          databaseHelper.removePostsByReposterId(userId6);
                          boolean success3 = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success3);
                                }
                                break;
                            case 70:
                      StringBuilder url52 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "editions");
                      vp = VineParserReader.createParserReader(20);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url52, this.mApi, vp).execute();
                      if (op.isOK()) {
                          ArrayList<Pair<String, String>> editions = (ArrayList) vp.getParsedObject();
                          databaseHelper.mergeEditions(editions);
                          bundle.putSerializable("editions", editions);
                                }
                      FlurryUtils.trackGetEditions();
                                break;
                            case 71:
                      ArrayList<BasicNameValuePair> params17 = new ArrayList<>(1);
                      StringBuilder url53 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "edition");
                      String edition = bundle.getString("edition");
                      params17.add(new BasicNameValuePair("edition", edition));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url53, this.mApi, params17, vp).execute();
                                break;
                            case 74:
                      int pageType3 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
                          if (!bundle.getBoolean(EXTRA_USER_INITIATED, false)) {
                              break;
                                }
                                try {
                          op = getInbox(sessionOwnerId, pageType3, actionCode, key, databaseHelper, bundle);
                          vp = (VineParserReader) op.getReader();
                                } catch (IOException e10) {
                          if (LOGGABLE) {
                              e10.printStackTrace();
                                    }
                                }
                                break;
                            case 76:
                      long conversationRowId2 = bundle.getLong("conversation_row_id");
                                if (conversationRowId2 > 0) {
                          conversationId = databaseHelper.getConversationRemoteId(conversationRowId2);
                                } else {
                          conversationId = bundle.getLong("conversation_id");
                                }
                                if (conversationId != -1) {
                          boolean prefetch = bundle.getBoolean("prefetch");
                          int pageType4 = bundle.getInt(EXTRA_PAGE_TYPE);
                          StringBuilder url54 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId));
                          int page7 = 1;
                                    switch (pageType4) {
                                        case 2:
                                  int newer = databaseHelper.getPreviousPageCursor(7, 0, String.valueOf(conversationRowId2), false);
                                  if (newer > 0) {
                                      page7 = newer;
                                            }
                                            break;
                                        case 3:
                                  int older = databaseHelper.getNextPageCursor(7, 0, String.valueOf(conversationRowId2), false);
                                  if (older > 0) {
                                      page7 = older;
                                            }
                                            break;
                                    }
                          VineAPI.addParam(url54, "prefetch", prefetch ? 1 : 0);
                          VineAPI.addParam(url54, "page", page7);
                          vp = VineParserReader.createParserReader(22);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url54, this.mApi, vp).execute();
                          if (op.isOK()) {
                              VinePagedData<VinePrivateMessage> data = (VinePagedData) vp.getParsedObject();
                              if (data != null && data.items != null && !data.items.isEmpty()) {
                                  VineConversation conversation2 = new VineConversation(conversationId, data.lastMessage, data.items, data.unreadMessageCount);
                                  conversation2.networkType = data.networkType;
                                            for (VinePrivateMessage item : data.items) {
                                      item.networkType = data.networkType;
                                            }
                                  bundle.putBoolean(EXTRA_EMPTY, false);
                                            try {
                                      long conversationLocalObjectId = databaseHelper.mergeConversation(sessionOwnerId, conversation2, conversation2.networkType, data.nextPage, data.previousPage, data.anchor);
                                      if (data.nextPage <= 0) {
                                          databaseHelper.markLastMessage(conversationLocalObjectId);
                                                }
                                      databaseHelper.clearUnreadCount(conversationRowId2);
                                            } catch (IOException e11) {
                                      if (LOGGABLE) {
                                          e11.printStackTrace();
                                                }
                                            }
                                        } else {
                                  bundle.putBoolean(EXTRA_EMPTY, true);
                                        }
                                    }
                                }
                                break;
                            case 77:
                      bundle.putLong("conversation_id", databaseHelper.getConversationRemoteId(bundle.getLong("conversation_row_id")));
                                break;
                            case 79:
                      long conversationObjectId = bundle.getLong("conversation_row_id");
                      long conversationId2 = databaseHelper.getConversationRemoteId(conversationObjectId);
                      StringBuilder url55 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId2), VineAPI.RESOURCE_IGNORED);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url55, this.mApi, null, vp).execute();
                      databaseHelper.deleteConversation(conversationId2, conversationObjectId);
                                break;
                            case 80:
                      long conversationObjectId2 = bundle.getLong("conversation_row_id");
                      long conversationId3 = databaseHelper.getConversationRemoteId(conversationObjectId2);
                      long messageId = databaseHelper.getLastMessageIdInConversation(conversationObjectId2);
                      StringBuilder url56 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId3));
                                if (messageId > 0) {
                          VineAPI.addParam(url56, PARAM_LAST_MESSAGE_ID, messageId);
                                }
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url56, this.mApi, vp).execute();
                      databaseHelper.deleteConversation(conversationId3, conversationObjectId2);
                                break;
                            case 81:
                      ep = fetchActivityCounts(bundle.getLong(EXTRA_SESSION_OWNER_ID), context, bundle);
                                break;
                            case 82:
                      long userId7 = bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L);
                      int sectionType = bundle.getInt("type", 0);
                      int pageType5 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
                      long refreshTime = -1;
                      String anchor5 = null;
                      StringAnchorManager sm = Vine.getUserSectionsAnchorManager(this, sectionType);
                                switch (pageType5) {
                                    case 1:
                              anchor5 = null;
                              refreshTime = System.currentTimeMillis();
                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
                              if (sectionType == 1) {
                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
                              if (!TextUtils.isEmpty(anchor5)) {
                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1 && refreshTime == -1) {
                                  bundle.putBoolean(EXTRA_EMPTY, true);
                                            break;
                                        }
                              VineAPI.addParam(url2, "size", 10);
                              vp = VineParserReader.createParserReader(3);
                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
                              if (op.isOK()) {
                                  edit = sm.edit();
                                  if (anchor5 == null) {
                                      databaseHelper.removeUserSectionsWithType(sectionType);
                                      edit.setRefreshTime(refreshTime);
                                            }
                                  users3 = (VinePagedData) vp.getParsedObject();
                                  if (users3.stringAnchor != null) {
                                      edit.setAnchor(users3.stringAnchor);
                                      if (users3.items != null && !users3.items.isEmpty()) {
                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
                                  if (users3.items != null) {
                                      lastId = 0;
                                      if (!users3.items.isEmpty()) {
                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
                                          Collection<VineUser> collection = users3.items;
                                                    String strValueOf = String.valueOf(userId7);
                                                    int i4 = users3.nextPage;
                                                    int i5 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection, 10, strValueOf, i4, i5, orderMap, j, sectionType);
                                          bundle.putInt("count", users3.count);
                                          bundle.putInt("next_page", users3.nextPage);
                                          bundle.putInt("previous_page", users3.previousPage);
                                          bundle.putParcelableArrayList("users", users3.items);
                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
                                      if (users3.stringAnchor == null) {
                                          lastId = 0;
                                                }
                                      edit.setLastId(lastId);
                                            }
                                  edit.commit();
                                        }
                                        break;
                                    case 2:
                              throw new IllegalArgumentException("Invalid page type.");
                                    case 3:
                              anchor5 = sm.getStringAnchor();
                              if (anchor5 != null) {
                                  refreshTime = sm.getRefreshTime();
                                        }
                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
                              if (sectionType == 1) {
                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
                              if (!TextUtils.isEmpty(anchor5)) {
                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1) {
                                        }
                              VineAPI.addParam(url2, "size", 10);
                              vp = VineParserReader.createParserReader(3);
                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
                              if (op.isOK()) {
                                  edit = sm.edit();
                                  if (anchor5 == null) {
                                      databaseHelper.removeUserSectionsWithType(sectionType);
                                      edit.setRefreshTime(refreshTime);
                                            }
                                  users3 = (VinePagedData) vp.getParsedObject();
                                  if (users3.stringAnchor != null) {
                                      edit.setAnchor(users3.stringAnchor);
                                      if (users3.items != null) {
                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
                                  if (users3.items != null) {
                                      lastId = 0;
                                      if (!users3.items.isEmpty()) {
                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
                                          Collection<VineUser> collection2 = users3.items;
                                                    String strValueOf2 = String.valueOf(userId7);
                                                    int i6 = users3.nextPage;
                                                    int i7 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection2, 10, strValueOf2, i6, i7, orderMap, j, sectionType);
                                          bundle.putInt("count", users3.count);
                                          bundle.putInt("next_page", users3.nextPage);
                                          bundle.putInt("previous_page", users3.previousPage);
                                          bundle.putParcelableArrayList("users", users3.items);
                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
                                      if (users3.stringAnchor == null) {
                                          lastId = 0;
                                                }
                                      edit.setLastId(lastId);
                                            }
                                  edit.commit();
                                        }
                                        break;
                                    default:
                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
                              if (sectionType == 1) {
                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
                              if (!TextUtils.isEmpty(anchor5)) {
                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1) {
                                        }
                              VineAPI.addParam(url2, "size", 10);
                              vp = VineParserReader.createParserReader(3);
                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
                              if (op.isOK()) {
                                  edit = sm.edit();
                                  if (anchor5 == null) {
                                      databaseHelper.removeUserSectionsWithType(sectionType);
                                      edit.setRefreshTime(refreshTime);
                                            }
                                  users3 = (VinePagedData) vp.getParsedObject();
                                  if (users3.stringAnchor != null) {
                                      edit.setAnchor(users3.stringAnchor);
                                      if (users3.items != null) {
                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
                                  if (users3.items != null) {
                                      lastId = 0;
                                      if (!users3.items.isEmpty()) {
                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
                                          Collection<VineUser> collection3 = users3.items;
                                                    String strValueOf3 = String.valueOf(userId7);
                                                    int i8 = users3.nextPage;
                                                    int i9 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection3, 10, strValueOf3, i8, i9, orderMap, j, sectionType);
                                          bundle.putInt("count", users3.count);
                                          bundle.putInt("next_page", users3.nextPage);
                                          bundle.putInt("previous_page", users3.previousPage);
                                          bundle.putParcelableArrayList("users", users3.items);
                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
                                      if (users3.stringAnchor == null) {
                                          lastId = 0;
                                                }
                                      edit.setLastId(lastId);
                                            }
                                  edit.commit();
                                        }
                                        break;
                                }
                                break;
                            case 84:
                      VinePrivateMessage vpm = (VinePrivateMessage) bundle.getParcelable("message");
                      ArrayList<BasicNameValuePair> params18 = new ArrayList<>();
                      params18.add(new BasicNameValuePair(PARAM_DATE, DateTimeUtil.getTimeInStringFromMs(vpm.localStartTime, CommonUtil.TIMESTAMP_FORMAT)));
                      StringBuilder url57 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(vpm.conversationId), "messages", Long.valueOf(vpm.messageId), VineAPI.RESOURCE_VIEWED);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url57, this.mApi, params18, vp).execute();
                      if (!op.isOK()) {
                          SLog.m456e("Failed to send view receipt for {}.", Long.valueOf(vpm.messageId));
                          databaseHelper.updateMessageLoopStartTime(vpm.localStartTime, vpm.messageId);
                                }
                                break;
                            case 85:
                      ArrayList<VineDatabaseHelper.VineViewedMessage> messages = databaseHelper.getMessagesThatWereViewed();
                      ArrayList<BasicNameValuePair> params19 = new ArrayList<>();
                                for (VineDatabaseHelper.VineViewedMessage msg : messages) {
                          params19.clear();
                          params19.add(new BasicNameValuePair(PARAM_DATE, DateTimeUtil.getTimeInStringFromMs(msg.localStartTime, CommonUtil.TIMESTAMP_FORMAT)));
                          StringBuilder url58 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(msg.conversationId), "messages", Long.valueOf(msg.messageId), VineAPI.RESOURCE_VIEWED);
                          vp = VineParserReader.createParserReader(1);
                          op = this.mNetOpFactory.createBasicAuthPutRequest(context, url58, this.mApi, params19, vp).execute();
                          if (op.isOK()) {
                              databaseHelper.updateMessageLoopStartTime(0L, msg.messageId);
                                    } else {
                              SLog.m456e("Failed to send view receipt for {}.", Long.valueOf(msg.messageId));
                                    }
                                }
                                break;
                            case 86:
                      ArrayList<String> vanityUrlSegments = bundle.getStringArrayList("vanity_url");
                      StringBuilder url59 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_PROFILES, VineAPI.RESOURCE_VANITY);
                                for (String pathSegment2 : vanityUrlSegments) {
                          url59 = VineAPI.buildUponUrl(url59.toString(), pathSegment2.replaceAll(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "%20"));
                                }
                      vp = VineParserReader.createParserReader(2);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url59, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VineUser user3 = (VineUser) vp.getParsedObject();
                          bundle.putLong("user_id", user3.userId);
                                }
                                break;
                            case 87:
                      long userId8 = bundle.getLong("user_id");
                      boolean hide = bundle.getBoolean(EXTRA_HIDE_PROFILE_REPOSTS);
                      databaseHelper.setShouldHideProfileRevines(userId8, hide);
                                break;
                            case 88:
                       StringBuilder url60 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                       Uri avatarUri2 = (Uri) bundle.getParcelable("uri");
                       String f2 = null;
                       if (avatarUri2 != null) {
                                    try {
                               f2 = this.mMediaUtility.getPhotoUri(avatarUri2, context);
                               if (avatarUri2 == null && TextUtils.isEmpty(avatarUri2.toString())) {
                                   finalAvatarUrl2 = "";
                                        } else {
                                   finalAvatarUrl2 = f2;
                                        }
                               params2 = new ArrayList<>(1);
                               if (avatarUri2 != null) {
                                   params2.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl2));
                                        }
                               vp = VineParserReader.createParserReader(2);
                               op = this.mNetOpFactory.createBasicAuthPutRequest(context, url60, this.mApi, params2, vp).execute();
                               if (op.isOK()) {
                                   if (avatarUri2 == null) {
                                       bundle.putString("avatar_url", "");
                                            } else {
                                       bundle.putString("avatar_url", finalAvatarUrl2);
                                            }
                                        }
                                    } catch (Exception e12) {
                               statusCode = 900;
                               reasonPhrase = e12.getMessage();
                                    }
                                } else {
                           if (avatarUri2 == null) {
                               finalAvatarUrl2 = f2;
                                    } else {
                               finalAvatarUrl2 = f2;
                                    }
                           params2 = new ArrayList<>(1);
                           if (avatarUri2 != null) {
                               params2.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl2));
                                    }
                           vp = VineParserReader.createParserReader(2);
                           op = this.mNetOpFactory.createBasicAuthPutRequest(context, url60, this.mApi, params2, vp).execute();
                           if (op.isOK()) {
                               if (avatarUri2 == null) {
                                   bundle.putString("avatar_url", "");
                                        } else {
                                   bundle.putString("avatar_url", finalAvatarUrl2);
                                        }
                                    }
                                }
                                break;
                            case 89:
                      String phone3 = bundle.getString(EXTRA_PHONE);
                      long id = bundle.getLong("user_id");
                      StringBuilder url61 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(id));
                      vp = VineParserReader.createParserReader(1);
                      ArrayList<BasicNameValuePair> params20 = new ArrayList<>(2);
                      params20.add(new BasicNameValuePair("phoneNumber", phone3));
                      params20.add(new BasicNameValuePair("verifyPhoneNumber", String.valueOf(1)));
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url61, this.mApi, params20, vp).execute();
                                break;
                            case 90:
                      String code2 = bundle.getString(EXTRA_KEY);
                      StringBuilder url62 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "verifyPhoneNumber");
                      vp = VineParserReader.createParserReader(1);
                      ArrayList<BasicNameValuePair> params21 = new ArrayList<>(1);
                      params21.add(new BasicNameValuePair("t", code2));
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url62, this.mApi, params21, vp).execute();
                                break;
                            case 91:
                       int networkType = bundle.getInt("network", 1);
                       VineRecipient recipient = (VineRecipient) bundle.getParcelable(EXTRA_RECIPIENT);
                       if (recipient == null) {
                           throw new IllegalArgumentException("You must provide a recipient if you want to get the conversation id for it");
                                }
                       if (recipient.isFromUser()) {
                                    if (recipient.recipientId <= 0) {
                               recipient.recipientId = databaseHelper.getUserRowIdForUserRemoteId(recipient.userId);
                                    } else if (recipient.userId < 0) {
                               recipient.userId = databaseHelper.getUserRemoteIdForUserRowId(recipient.recipientId);
                                    }
                           conversationRowId = databaseHelper.determineBestConversationRowIdForUserRemoteId(recipient.userId);
                                } else {
                           ArrayList<VineRecipient> recipients = new ArrayList<>();
                           recipients.add(recipient);
                           conversationRowId = databaseHelper.determineOrCreateBestConversationRowIdForRecipients(recipients, networkType);
                                }
                                if (conversationRowId <= 0) {
                           ArrayList<VineRecipient> recipients2 = new ArrayList<>();
                           recipients2.add(recipient);
                           conversationRowId = databaseHelper.createPreMergeConversationIdAndRecipientsIfNecessary(recipients2, networkType);
                                }
                       if (TextUtils.isEmpty(recipient.display)) {
                           recipient.display = databaseHelper.getDisplayForUser(recipient.recipientId);
                                }
                       boolean amFollowing = databaseHelper.amFollowingUser(recipient.recipientId);
                       bundle.putLong("conversation_row_id", conversationRowId);
                       bundle.putBoolean("am_following", amFollowing);
                       bundle.putString("username", recipient.display);
                                bundle2 = bundle;
                      return bundle2;
                            case 92:
                      String email4 = bundle.getString("email");
                      long id2 = bundle.getLong("user_id");
                      StringBuilder url63 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(id2));
                      ArrayList<BasicNameValuePair> params22 = new ArrayList<>(2);
                      params22.add(new BasicNameValuePair("email", email4));
                      params22.add(new BasicNameValuePair("verifyEmail", String.valueOf(1)));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url63, this.mApi, params22, vp).execute();
                                break;
                            case 93:
                      int networkType2 = bundle.getInt("network", 1);
                      VineUpload upload = (VineUpload) bundle.getParcelable("upload");
                      PostInfo info = upload.getPostInfo();
                      ArrayList<VineRecipient> recipients3 = info.recipients;
                      long conversationRowId3 = upload.conversationRowId;
                      if (recipients3 == null && conversationRowId3 > 0) {
                          databaseHelper.getConversationRecipientsFromConversationRowId(conversationRowId3);
                                }
                      if (recipients3 != null) {
                                    for (VineRecipient recipient2 : recipients3) {
                              if (recipient2.isFromUser()) {
                                            if (recipient2.recipientId <= 0) {
                                      recipient2.recipientId = databaseHelper.getUserRowIdForUserRemoteId(recipient2.userId);
                                            } else if (recipient2.userId < 0) {
                                      recipient2.userId = databaseHelper.getUserRemoteIdForUserRowId(recipient2.recipientId);
                                            }
                                        }
                                    }
                                }
                      long myUserId = databaseHelper.getUserRowIdForUserRemoteId(sessionOwnerId);
                                if (conversationRowId3 <= 0) {
                          conversationRowId3 = databaseHelper.determineOrCreateBestConversationRowIdForRecipients(recipients3, networkType2);
                                }
                      upload.conversationRowId = conversationRowId3;
                      long created = System.currentTimeMillis();
                      String videoPath = null;
                      String thumbPath = null;
                      String errorReason = null;
                      int errorCode = 0;
                      if (upload.path != null) {
                          RecordSessionVersion version = UploadManager.getVersionFromPath(upload.path);
                          if (new File(upload.path + version.videoOutputExtension).exists()) {
                              videoPath = CommonUtil.FILE_SCHEME + upload.path + version.videoOutputExtension;
                                    } else {
                              videoPath = CommonUtil.FILE_SCHEME + upload.path;
                                    }
                          thumbPath = CommonUtil.FILE_SCHEME + RecordConfigUtils.getThumbnailPath(upload.path);
                          if (upload.status == 2) {
                              errorReason = getString(R.string.message_failed_tap_to_retry);
                              errorCode = -2;
                                    }
                                }
                      long messageId2 = databaseHelper.getNewMessageId();
                                if (info.postId > 0) {
                          videoPath = info.sharedPostVideoUrl;
                          thumbPath = info.sharedPostThumbUrl;
                                }
                      long messageRowId = databaseHelper.mergeMessage(conversationRowId3, new VinePrivateMessage(-1L, conversationRowId3, messageId2, myUserId, created, info.message, videoPath, thumbPath, 1, false, false, Integer.MAX_VALUE, 0L, info.maxLoops > 0, 0L, info.postId, errorCode, errorReason, upload.path));
                      databaseHelper.updateConversationWithLastMessage(conversationRowId3, messageRowId, created);
                      upload.postInfo = info.toString();
                      bundle.putParcelable("upload", upload);
                      bundle.putLong(EXTRA_PREMERGED_MESSAGE_ID, messageRowId);
                                break;
                            case 94:
                      databaseHelper.clearUnreadCount(bundle.getLong("conversation_row_id"));
                                break;
                            case 95:
                      databaseHelper.deleteMessage(bundle.getLong("message_id"));
                                break;
                            case 96:
                      long postId11 = bundle.getLong("post_id");
                      long created2 = System.currentTimeMillis();
                      JSONObject postBody2 = new JSONObject();
                      ArrayList<VineRecipient> recipients4 = bundle.getParcelableArrayList("recipients");
                      StringBuilder url64 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations");
                                try {
                          postBody2.put("postId", postId11);
                          postBody2.put("to", PostInfo.recipientsToJsonArray(recipients4));
                          postBody2.put("created", created2);
                          addCountryParam(postBody2);
                                } catch (JSONException e13) {
                          CrashUtil.logOrThrowInDebug(e13);
                                }
                      vp = VineParserReader.createParserReader(12);
                      op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url64, this.mApi, postBody2, vp).execute();
                                break;
                            case 97:
                      VineSingleNotification notification = (VineSingleNotification) bundle.getParcelable("notification");
                      if (notification.isMessaging()) {
                          notification.notificationTypeId = 18;
                          VineConversation conversation3 = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
                          if (conversation3 != null) {
                              notification.conversationRowId = conversation3.conversationObjectId;
                              VinePrivateMessage message2 = conversation3.lastMessage();
                              if (message2 != null && message2.thumbnailUrl != null && message2.videoUrl != null) {
                                  notification.imageKeys.add(new ImageKey(message2.thumbnailUrl));
                                  notification.videoKeys.add(new VideoKey(message2.videoUrl));
                                        }
                                    }
                                }
                      ArrayList<VineSingleNotification> notifications = new ArrayList<>();
                      if (TextUtils.isEmpty(notification.onboard)) {
                          ArrayList<VineSingleNotification> notifs = databaseHelper.mergePushNotification(notification);
                          if (notifs.size() == 0) {
                              bundle.remove("notification");
                                    } else {
                              notifications.addAll(notifs);
                              if (notification.isMessaging()) {
                                  unclearedNotifications = new ArrayList<>(notifications);
                                  uniqueNotifications = new LongSparseArray<>();
                                  if (unclearedNotifications.size() > 1) {
                                                for (VineSingleNotification n2 : unclearedNotifications) {
                                          notif2 = uniqueNotifications.get(n2.conversationRowId);
                                          if (notif2 != null) {
                                              notif2.messageCount++;
                                                    } else {
                                              uniqueNotifications.put(n2.conversationRowId, n2);
                                              n2.messageCount = 1;
                                                    }
                                                }
                                      unclearedNotifications.clear();
                                      n = uniqueNotifications.size();
                                                for (i = 0; i < n; i++) {
                                          notif = uniqueNotifications.valueAt(i);
                                          if (notif.messageCount > 1) {
                                              notif.comment = getString(R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notif.comment), Integer.valueOf(notif.messageCount)});
                                                    }
                                          unclearedNotifications.add(notif);
                                                    if (notif.conversationRowId == notification.conversationRowId) {
                                              notification.messageCount = notif.messageCount;
                                                    }
                                          if (i > 0) {
                                              SLog.m464i("Prefetching message headers...");
                                              conversation = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
                                              if (conversation != null) {
                                                  VinePrivateMessage lastMessage = conversation.lastMessage();
                                                  if (Util.toPrettyComment(notification) == null && i < 4 && lastMessage.thumbnailUrl != null && lastMessage.videoUrl != null) {
                                                      notification.imageKeys.add(new ImageKey(lastMessage.thumbnailUrl));
                                                      notification.videoKeys.add(new VideoKey(lastMessage.videoUrl));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                  bundle.putParcelableArrayList("notifications", unclearedNotifications);
                                        } else {
                                  bundle.putParcelableArrayList("notifications", notifications);
                                        }
                              bundle.putParcelable("notification", notification);
                                    }
                                } else {
                          if (notification.isMessaging()) {
                              unclearedNotifications = new ArrayList<>(notifications);
                              uniqueNotifications = new LongSparseArray<>();
                              if (unclearedNotifications.size() > 1) {
                                            for (VineSingleNotification n2 : unclearedNotifications) {
                                      notif2 = uniqueNotifications.get(n2.conversationRowId);
                                      if (notif2 != null) {
                                          notif2.messageCount++;
                                                } else {
                                          uniqueNotifications.put(n2.conversationRowId, n2);
                                          n2.messageCount = 1;
                                                }
                                            }
                                  unclearedNotifications.clear();
                                  n = uniqueNotifications.size();
                                            while (i < n) {
                                      notif = uniqueNotifications.valueAt(i);
                                      if (notif.messageCount > 1) {
                                          notif.comment = getString(R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notif.comment), Integer.valueOf(notif.messageCount)});
                                                }
                                      unclearedNotifications.add(notif);
                                                if (notif.conversationRowId == notification.conversationRowId) {
                                          notification.messageCount = notif.messageCount;
                                                }
                                      if (i > 0) {
                                          SLog.m464i("Prefetching message headers...");
                                          conversation = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
                                          if (conversation != null) {
                                              VinePrivateMessage lastMessage2 = conversation.lastMessage();
                                              if (Util.toPrettyComment(notification) == null) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                              bundle.putParcelableArrayList("notifications", unclearedNotifications);
                                    } else {
                              bundle.putParcelableArrayList("notifications", notifications);
                                    }
                          bundle.putParcelable("notification", notification);
                                }
                                break;
                            case 98:
                      VineUpload upload2 = (VineUpload) bundle.getParcelable("upload");
                      op = post(context, upload2, bundle);
                      bundle.putParcelable("upload", upload2);
                                break;
                            case 100:
                      String uploadPath = bundle.getString("upload_path");
                      if (!TextUtils.isEmpty(uploadPath)) {
                          databaseHelper.setMessageError(uploadPath, -2, getString(R.string.message_failed_tap_to_retry));
                                }
                                break;
                            case 101:
                      String addressBook2 = null;
                                try {
                          addressBook2 = Util.getAddressBookJson(getContentResolver());
                                } catch (IOException e14) {
                          if (LOGGABLE) {
                              e14.printStackTrace();
                                    }
                                }
                      if (addressBook2 == null && 0 != 0) {
                          op.statusCode = 1000;
                          error = new VineError(1000, "Could not create json.");
                                } else {
                          StringBuilder url65 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, VineAPI.RESOURCE_CONTACTS);
                          vp = VineParserReader.createParserReader(3);
                          ArrayList<BasicNameValuePair> params23 = new ArrayList<>();
                          params23.add(new BasicNameValuePair(PARAM_ADDRESS_BOOK, addressBook2));
                          String language = Util.getLocale();
                          params23.add(new BasicNameValuePair(PARAM_LOCALE, language));
                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url65, this.mApi, params23, vp).execute();
                                }
                                break;
                            case 102:
                      int notificationId = bundle.getInt("notification_id");
                      long conversationRowId4 = bundle.getLong("conversation_row_id", -1L);
                                if (conversationRowId4 >= 0) {
                          databaseHelper.deletePushNotificationsForConversation(conversationRowId4);
                                } else {
                          databaseHelper.deletePushNotifications(notificationId);
                                }
                                break;
                            case 103:
                      Cursor c = databaseHelper.getMessagesForConversationRowId(bundle.getLong("conversation_row_id"));
                      if (c != null) {
                          while (c.moveToNext()) {
                                        try {
                                  VinePrivateMessage vpm2 = new VinePrivateMessage(c);
                                  startService(VineUploadService.getVMPostIntent(this, vpm2.uploadPath, true, vpm2.messageRowId, vpm2.conversationRowId, null, vpm2.message, vpm2.postId, vpm2.videoUrl, vpm2.thumbnailUrl, vpm2.maxLoops));
                                        } catch (Throwable th2) {
                                            c.close();
                                            throw th2;
                                        }
                                    }
                                    c.close();
                                }
                                break;
                            case 104:
                      databaseHelper.savePageCursor(6, 1, null, 0, 0, 0L, true);
                      databaseHelper.savePageCursor(6, 2, null, 0, 0, 0L, true);
                                break;
                            case 105:
                      boolean acceptOon = bundle.getBoolean(EXTRA_ACCEPT_OON);
                      StringBuilder url66 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                      ArrayList<BasicNameValuePair> params24 = new ArrayList<>();
                      params24.add(new BasicNameValuePair("acceptsOutOfNetworkConversations", acceptOon ? "1" : "0"));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url66, this.mApi, params24, vp).execute();
                                break;
                            case 106:
                      boolean enableAddressBook = bundle.getBoolean(EXTRA_ENABLE);
                      StringBuilder url67 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
                      ArrayList<BasicNameValuePair> params25 = new ArrayList<>();
                      params25.add(new BasicNameValuePair(PARAM_DISABLE_ADDRESS_BOOK, enableAddressBook ? "0" : "1"));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url67, this.mApi, params25, vp).execute();
                                break;
                            case 107:
                      ArrayList<String> userIdsToFollow = bundle.getStringArrayList(EXTRA_FOLLOW_IDS);
                      StringBuilder url68 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_FOLLOWERS);
                      ArrayList<BasicNameValuePair> params26 = new ArrayList<>();
                      Iterator<String> it = userIdsToFollow.iterator();
                                while (it.hasNext()) {
                          params26.add(new BasicNameValuePair(PARAM_USER_IDS, it.next()));
                                }
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url68, this.mApi, params26, vp).execute();
                                break;
                            case 108:
                      boolean enable = bundle.getBoolean(EXTRA_ENABLE);
                      int type2 = bundle.getInt("type");
                      StringBuilder url69 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
                      if (type2 == 0) {
                          param = PARAM_HIDDEN_EMAIL;
                                } else if (type2 == 1) {
                          param = PARAM_HIDDEN_PHONE_NUMBER;
                                } else {
                          param = PARAM_HIDDEN_TWITTER;
                                }
                      ArrayList<BasicNameValuePair> params27 = new ArrayList<>();
                      params27.add(new BasicNameValuePair(param, enable ? "0" : "1"));
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url69, this.mApi, params27, vp).execute();
                                break;
                            case 109:
                      String code3 = bundle.getString(EXTRA_KEY);
                      StringBuilder url70 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "verifyEmail");
                      vp = VineParserReader.createParserReader(1);
                      ArrayList<BasicNameValuePair> params28 = new ArrayList<>(1);
                      params28.add(new BasicNameValuePair("t", code3));
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url70, this.mApi, params28, vp).execute();
                                break;
                            case 110:
                      int page8 = bundle.getInt("page", 1);
                      long anchor6 = bundle.getLong(EXTRA_ANCHOR, 0L);
                      long notificationId2 = bundle.getLong("notification_id", 0L);
                                if (sessionOwnerId > 0) {
                          StringBuilder url71 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", VineAPI.RESOURCE_GROUPED, Long.valueOf(notificationId2), "users");
                          VineAPI.addParam(url71, "page", page8);
                          VineAPI.addAnchor(url71, anchor6);
                          VineAPI.addParam(url71, "size", 20);
                          vp = VineParserReader.createParserReader(3);
                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url71, this.mApi, vp).execute();
                          if (op.isOK()) {
                              VinePagedData<VineUser> users9 = (VinePagedData) vp.getParsedObject();
                              if (users9.items != null) {
                                  LongSparseArray<Long> orderMap3 = new LongSparseArray<>(users9.items.size());
                                            for (VineUser u3 : users9.items) {
                                      orderMap3.put(u3.userId, Long.valueOf(u3.orderId));
                                            }
                                  databaseHelper.mergeUsers(users9.items, 12, String.valueOf(notificationId2), users9.nextPage, users9.previousPage, orderMap3);
                                  bundle.putInt("count", users9.count);
                                  bundle.putInt("next_page", users9.nextPage);
                                  bundle.putInt("previous_page", users9.previousPage);
                                  bundle.putLong(EXTRA_ANCHOR, users9.anchor);
                                  bundle.putParcelableArrayList("users", users9.items);
                                        }
                              if (users9.nextPage <= 0) {
                                  databaseHelper.markLastUser(12, String.valueOf(sessionOwnerId), "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 111:
                      StringBuilder url72 = VineAPI.buildUponUrl(this.mApi.getConfigUrl(), VineAPI.RESOURCE_CLIENT_FLAGS);
                      vp = VineParserReader.createParserReader(29);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url72, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VineClientFlags clientFlags = (VineClientFlags) vp.getParsedObject();
                          bundle.putParcelable(EXTRA_CLIENT_FLAGS, clientFlags);
                                }
                                break;
                            case 112:
                      Iterator<NetworkOperation.CancelableRequest> iterator = this.mHttpRequests.values().iterator();
                      while (iterator.hasNext()) {
                          NetworkOperation.CancelableRequest request2 = iterator.next();
                          if (request2 != null && !request2.isCancelled()) {
                              request2.cancel();
                                    }
                          iterator.remove();
                                }
                                break;
                            case 113:
                       StringBuilder url73 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", "settings");
                       vp = VineParserReader.createParserReader(30);
                       op = this.mNetOpFactory.createBasicAuthGetRequest(context, url73, this.mApi, vp).execute();
                       if (op.isOK()) {
                           VinePagedData<VineNotificationSetting> notifSettings = (VinePagedData) vp.getParsedObject();
                           bundle.putParcelableArrayList(EXTRA_NOTIFICATION_SETTINGS, notifSettings.items);
                                }
                                break;
                            case 114:
                       StringBuilder url74 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", "settings");
                       HashMap<String, String> params29 = (HashMap) bundle.getSerializable(EXTRA_NOTIFICATION_SETTINGS);
                       ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
                                for (Map.Entry<String, String> entry : params29.entrySet()) {
                           BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                           pairs.add(pair);
                                }
                       vp = VineParserReader.createParserReader(1);
                       op = this.mNetOpFactory.createBasicAuthPutRequest(context, url74, this.mApi, pairs, vp).execute();
                                break;
                            case 115:
                      databaseHelper.removeAccount();
                                break;
                            case 116:
                      long channelId = bundle.getLong("channelId");
                      boolean follow = bundle.getBoolean("following");
                      StringBuilder uri = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", Long.valueOf(channelId), VineAPI.RESOURCE_FOLLOWERS);
                      vp = VineParserReader.createParserReader(1);
                      op = follow ? this.mNetOpFactory.createBasicAuthPostRequest(context, uri, this.mApi, null, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, uri, this.mApi, vp).execute();
                                break;
                            case 117:
                      ArrayList<String> channelIds = new ArrayList<>(bundle.getStringArrayList("channels"));
                      StringBuilder uri2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", VineAPI.RESOURCE_FOLLOWERS);
                      ArrayList<BasicNameValuePair> params30 = new ArrayList<>();
                      Iterator<String> it2 = channelIds.iterator();
                                while (it2.hasNext()) {
                          params30.add(new BasicNameValuePair(PARAM_CHANNEL_IDS, it2.next()));
                                }
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, uri2, this.mApi, params30, vp).execute();
                                break;
                            case 118:
                      long channelId2 = bundle.getLong("channelId");
                                if (channelId2 > 0) {
                          databaseHelper.markChannelLastUsedTimestamp(channelId2);
                                }
                                break;
                            case 119:
                      long userId9 = bundle.getLong("user_id");
                      boolean favorite = bundle.getBoolean(EXTRA_FAVORITE);
                      boolean isFollowing = bundle.getBoolean("following");
                      ArrayList<BasicNameValuePair> params31 = new ArrayList<>();
                      if (isFollowing) {
                          url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId9), VineAPI.RESOURCE_FOLLOWERS, "notifications");
                                } else {
                          params31.add(new BasicNameValuePair("notify", "1"));
                          url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId9), VineAPI.RESOURCE_FOLLOWERS);
                          VineAPI.addParam(url, "notify", "1");
                                }
                      vp = VineParserReader.createParserReader(1);
                      op = favorite ? this.mNetOpFactory.createBasicAuthPostRequest(context, url, this.mApi, params31, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, url, this.mApi, vp).execute();
                                break;
                            case 120:
                      int page9 = bundle.getInt("page", 1);
                      String anchor7 = bundle.getString(EXTRA_ANCHOR);
                      StringBuilder url75 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SECTIONED_LIST);
                      VineAPI.addParam(url75, "page", page9);
                      if (!TextUtils.isEmpty(anchor7)) {
                          VineAPI.addParam(url75, "anchor", anchor7);
                                }
                      VineAPI.addParam(url75, "size", 20);
                      vp = VineParserReader.createParserReader(3);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url75, this.mApi, vp).execute();
                      if (op.isOK()) {
                          VinePagedData<VineUser> pagedData = (VinePagedData) vp.getParsedObject();
                          bundle.putInt("next_page", pagedData.nextPage);
                          bundle.putInt("previous_page", pagedData.previousPage);
                          bundle.putString(EXTRA_ANCHOR, pagedData.stringAnchor);
                          bundle.putParcelableArrayList("users", pagedData.items);
                                }
                                break;
                            case 121:
                            case 122:
                      if (actionCode == 121) {
                          endpoint = VineAPI.RESOURCE_SUGGESTED_FAVORITES;
                                } else {
                          endpoint = VineAPI.RESOURCE_ONBOARDING_SUGGESTED_FAVORITES;
                                }
                      StringBuilder url76 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", endpoint);
                      vp = VineParserReader.createParserReader(3);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url76, this.mApi, vp).execute();
                      if (op.isOK()) {
                          bundle.putParcelableArrayList("users", ((VinePagedData) vp.getParsedObject()).items);
                                }
                                break;
                            case 123:
                      if (this.mTwitter != null) {
                                    try {
                              this.mTwitter.createFriendship(586671909L);
                                    } catch (TwitterException ex) {
                              CrashUtil.logException(ex, "Unable to follow Vine on Twitter", new Object[0]);
                                    }
                                }
                                break;
                            case 126:
                      String commentId = bundle.getString("comment_id");
                      String postId12 = bundle.getString("post_id");
                      String event3 = bundle.getString("event");
                      ArrayList<BasicNameValuePair> params32 = null;
                      if (!TextUtils.isEmpty(event3)) {
                          params32 = new ArrayList<>();
                          params32.add(new BasicNameValuePair("code", event3));
                                }
                      StringBuilder url77 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", postId12, "comments", commentId, VineAPI.RESOURCE_COMPLAINTS);
                      vp = VineParserReader.createParserReader(1);
                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url77, this.mApi, params32, vp).execute();
                                break;
                            case 127:
                      PrefetchManager manager = PrefetchManager.getInstance(this);
                      if (manager.isClientPrefetchEnabled()) {
                          boolean manual = bundle.getBoolean(EXTRA_MANUAL, false);
                          manager.notifySyncStart(this);
                          FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Sync was requested.", new Object[0]);
                          if (!manual && !manager.isSuitableToSync()) {
                              manager.notifySyncEnd(this, "Device not ready.", false, manual);
                                    } else {
                              manager.setCurrentSyncStart(System.currentTimeMillis());
                              AppController ctr = AppController.getInstance(context);
                              long startNetworkUsed = NetworkOperation.sNetworkDataUsed;
                              long userId10 = ctr.getActiveId();
                                        if (userId10 > 0) {
                                  PrefetchManager.injectPrefetchArguments(bundle, PrefetchManager.getMinimumDelay(manager.getInterval()));
                                  performTimeLinePrefetch(new Bundle(bundle), databaseHelper, BehaviorManager.getInstance(this).getTopUsagesTimelineEndpoints());
                                  performActivityPrefetch(context, new Bundle(bundle), databaseHelper, userId10);
                                  performGetUsersMePrefetch(context, new Bundle(bundle));
                                  fetchActivityCounts(userId10, context, bundle);
                                  long waited = 0;
                                  while (ctr.hasPendingCacheRequests() && waited < 600000) {
                                                try {
                                          Thread.sleep(1000L);
                                          waited += 1000;
                                                } catch (InterruptedException e15) {
                                                }
                                            }
                                        }
                              if (SLog.sLogsOn) {
                                  FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Prefetch used: {} kb", Long.valueOf((NetworkOperation.sNetworkDataUsed - startNetworkUsed) / FileUtils.ONE_KB));
                                        }
                              manager.notifySyncEnd(this, null, true, manual);
                                    }
                                }
                                break;
                            case 128:
                       SLog.m464i("Running device boot actions.");
                                try {
                           PrefetchManager.getInstance(this).onDeviceBoot();
                           AppController.getInstance(this).refreshLoopSendingAlarm(System.currentTimeMillis());
                                } catch (RuntimeException e16) {
                           CrashUtil.logOrThrowInDebug(e16);
                                }
                                break;
                            case 130:
                      StringBuilder url78 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), VineAPI.RESOURCE_COMPLAINTS, VineAPI.RESOURCE_MENU);
                      vp = VineParserReader.createParserReader(35);
                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url78, this.mApi, vp).execute();
                      if (op.isOK()) {
                          ArrayList<ComplaintMenuOption> complaintMenu = (ArrayList) vp.getParsedObject();
                          bundle.putSerializable(EXTRA_COMPLAINT_MENU, complaintMenu);
                                }
                                break;
                            case 1000:
                       Account account = VineAccountHelper.getAccount(context, accountName);
                       if ((account != null || (account = VineAccountHelper.getAccount(context, bundle.getString("email"))) != null) && (loginType = VineAccountHelper.getLoginType((am = AccountManager.get(context)), account)) != null) {
                                    switch (loginType.intValue()) {
                                        case 1:
                                   String email5 = account.name;
                                   String password4 = VineAccountHelper.getPassword(am, account, context);
                                   vp = VineParserReader.createLoginReader();
                                   ArrayList<BasicNameValuePair> params33 = new ArrayList<>();
                                   params33.add(new BasicNameValuePair("username", email5));
                                   params33.add(new BasicNameValuePair("password", password4));
                                   StringBuilder url79 = new StringBuilder(this.mApi.getBaseUrl()).append("/users/authenticate");
                                   op = this.mNetOpFactory.createPostRequest(context, url79, params33, vp, this.mApi).execute();
                                   if (op.isOK()) {
                                      VineLogin vl4 = (VineLogin) vp.getParsedObject();
                                      VineAccountHelper.setSessionKey(am, account, vl4.key);
                                      bundle.putString("s_key", vl4.key);
                                      AppController.getInstance(context).setSessionKey(vl4.key);
                                            }
                                            break;
                                        case 2:
                                  String token6 = VineAccountHelper.getTwitterToken(am, account);
                                  String secret3 = VineAccountHelper.getTwitterSecret(am, account);
                                  vp = VineParserReader.createLoginReader();
                                  ArrayList<BasicNameValuePair> params34 = new ArrayList<>();
                                  params34.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token6));
                                  params34.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret3));
                                  String pathSegment3 = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
                                  StringBuilder url80 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", pathSegment3, "twitter");
                                  op = this.mNetOpFactory.createPostRequest(context, url80, params34, vp, this.mApi).execute();
                                  if (op.isOK()) {
                                      VineLogin vl5 = (VineLogin) vp.getParsedObject();
                                      VineAccountHelper.setSessionKey(am, account, vl5.key);
                                      bundle.putString("s_key", vl5.key);
                                      AppController.getInstance(context).setSessionKey(vl5.key);
                                            }
                                            break;
                                    }
                                }
                                break;
                        }
              if (ep != null) {
                  vp = ep.f112vp;
                  op = ep.f111op;
                        }
              bundle2 = new Bundle();
              if (op != null) {
                  NetworkOperation.CancelableRequest request3 = op.getCancelableRequest();
                  if (request3 != null && !request3.isCancelled()) {
                      this.mHttpRequests.put(Integer.valueOf(request3.hashCode()), request3);
                            }
                  statusCode = op.statusCode;
                  bundle2.putInt(STATUS_CODE, statusCode);
                  if (!op.isOK() && vp != null && error == null) {
                      error = (VineError) vp.getParsedObject();
                            }
                  if (error != null) {
                      reasonPhrase = error.message;
                      bundle2.putString(REASON_PHRASE, reasonPhrase);
                      int errorCode2 = error.errorCode;
                      if (errorCode2 == VineKnownErrors.BAD_CREDENTIALS.code || errorCode2 == VineKnownErrors.REQUIRES_LOGIN.code) {
                          bundle2.putInt(EXECUTION_CODE, 3);
                                } else if (errorCode2 == VineKnownErrors.INVALID_SESSION.code || errorCode2 == VineKnownErrors.EXPIRED_SESSION.code) {
                          bundle2.putInt(EXECUTION_CODE, 2);
                                } else if (errorCode2 == VineKnownErrors.CAPTCHA.code) {
                          bundle2.putInt(EXECUTION_CODE, 4);
                          bundle2.putString("captcha_url", error.data);
                                }
                            }
                  return bundle2;
                        }
              bundle2.putInt(STATUS_CODE, statusCode);
              if (!TextUtils.isEmpty(reasonPhrase)) {
                  bundle2.putString(REASON_PHRASE, reasonPhrase);
                        }
              if (LOGGABLE) {
                  if (reasonPhrase != null) {
                      completeString = MessageFormatter.toStringMessage(" {} ({}) complete {}, {}", Integer.valueOf(actionCode), rid, Integer.valueOf(statusCode), reasonPhrase);
                            } else {
                      completeString = MessageFormatter.toStringMessage(" {} ({}) - {}", Integer.valueOf(actionCode), rid, Integer.valueOf(statusCode), reasonPhrase);
                            }
                  SLog.m464i(completeString);
                  if (bundle2.size() == 1 && bundle2.containsKey(STATUS_CODE)) {
                      this.mFileLogger.write(MessageFormatter.toStringMessage("{}\n", completeString), new Object[0]);
                            } else {
                      this.mFileLogger.write(MessageFormatter.toStringMessage("{}\n {}\n", completeString, bundle2), new Object[0]);
                            }
                        }
              bundle2.putInt(EXECUTION_CODE, 1);
                    } catch (VineAPI.InvalidContextForInitializationException e17) {
               CrashUtil.logException(e17, "Service was not ready for {}", Integer.valueOf(actionCode));
               Bundle bundle3 = new Bundle();
               bundle3.putInt(STATUS_CODE, -999);
                        bundle2 = bundle3;
                    }
          return bundle2;
                }

      private void performGetUsersMePrefetch(Context context, Bundle bundle) {
          getUsersMe(bundle);
          VineUser user = (VineUser) bundle.getParcelable("user");
          if (user != null && user.avatarUrl != null) {
              AppController.getInstance(context).getPhotoBitmap(ImageKey.newDownloadOnlyKey(user.avatarUrl));
                    }
                }

      private ExecutionPair fetchAcitivity(long sessionOwnerId, Context context, Bundle b) {
          int page = b.getInt("page", 1);
          long anchor = b.getLong(EXTRA_ANCHOR, 0L);
          boolean getFollowRequests = b.getBoolean(EXTRA_GET_FOLLOW_REQUESTS, false);
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications");
VineAPI.addParam(url, VineAPI.RESOURCE_GROUPED, 1);
          VineAPI.addParam(url, EXTRA_CLEAR, b.getBoolean(EXTRA_CLEAR) ? 1 : 0);
          VineAPI.addParam(url, "page", page);
          VineAPI.addAnchor(url, anchor);
          VineAPI.addParam(url, "size", 20);
          VineParserReader vp = VineParserReader.createParserReader(28);
          Log.d("VineNetwork", "notifications request userId=" + sessionOwnerId + " url=" + url.toString());
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(context, url, this.mApi, vp);
          assignPollingHeader(op, b);
          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
          op.execute();
          if (op.isOK()) {
              VinePagedData<VineEverydayNotification> results = (VinePagedData) vp.getParsedObject();
              
              
              
              if (results != null && results.items != null) {
                  for (VineEverydayNotification notification : results.items) {
                      notification.post = null;
                      notification.entities = null;
                  }
              }
              b.putParcelable("notifications", results);
                    }
          Log.d("VineNetwork", "notifications response status=" + op.statusCode + " reason=" + op.statusPhrase + " parsed=" + (vp.getParsedObject() != null));
          if (getFollowRequests) {
              StringBuilder followUrl = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", VineAPI.RESOURCE_FOLLOW_REQUESTS);
              VineAPI.addParam(followUrl, "page", 1);
              VineAPI.addParam(followUrl, "size", 20);
              VineParserReader followVp = VineParserReader.createParserReader(9);
              NetworkOperation followOp = this.mNetOpFactory.createBasicAuthGetRequest(context, followUrl, this.mApi, followVp).execute();
              if (followOp.isOK()) {
                  VinePagedData<VineSingleNotification> followNotifs = (VinePagedData) followVp.getParsedObject();
                  b.putParcelable(EXTRA_FOLLOW_REQUESTS, followNotifs);
                        }
                    }
          return new ExecutionPair(vp, op, null);
                }

      private ExecutionPair fetchActivityCounts(long sessionId, Context context, Bundle b) {
                    VineActivityCounts activityCounts;
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionId), VineAPI.RESOURCE_ACTIVITY_COUNTS);
          VineAPI.addParam(url, VineAPI.RESOURCE_GROUPED, 1);
          VineParserReader vp = VineParserReader.createParserReader(26);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(context, url, this.mApi, vp);
          assignPollingHeader(op, b);
          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
          op.execute();
          if (op.isOK() && (activityCounts = (VineActivityCounts) vp.getParsedObject()) != null) {
              b.putInt(EXTRA_MESSAGES_COUNT, activityCounts.messages);
              b.putInt(EXTRA_NOTIFICATIONS_COUNT, activityCounts.notifications);
              Intent countIntent = new Intent();
              countIntent.putExtra(EXTRA_MESSAGES_COUNT, activityCounts.messages);
              countIntent.putExtra(EXTRA_NOTIFICATIONS_COUNT, activityCounts.notifications);
              countIntent.setAction(BC_ACTIVITY_COUNTS);
              sendBroadcast(countIntent, CrossConstants.BROADCAST_PERMISSION);
              updateBadgeCount(activityCounts.messages + activityCounts.notifications);
                    }
          return new ExecutionPair(vp, op, null);
                }

      private StringBuilder getUserUrl(long userId) {
                    return VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_PROFILES, Long.valueOf(userId));
                }

      private void performActivityPrefetch(Context context, Bundle b, VineDatabaseHelper dbHelper, long sessionOwnerId) {
                    ArrayList<VineSingleNotification> arrayList;
                    ArrayList<VineEverydayNotification> arrayList2;
          b.putBoolean(EXTRA_GET_FOLLOW_REQUESTS, true);
          fetchAcitivity(sessionOwnerId, context, b);
          VinePagedData<VineEverydayNotification> notifications = (VinePagedData) b.getParcelable("notifications");
          VinePagedData<VineSingleNotification> followRequests = (VinePagedData) b.getParcelable(EXTRA_FOLLOW_REQUESTS);
          AppController ctr = AppController.getInstance(context);
          ArrayList<ImageKey> images = new ArrayList<>();
          if (notifications != null && (arrayList2 = notifications.items) != null) {
                        for (VineEverydayNotification notification : arrayList2) {
                  if (notification.user != null && notification.user.avatarUrl != null) {
                      images.add(ImageKey.newDownloadOnlyKey(notification.user.avatarUrl));
                            }
                  if (notification.post != null && notification.post.thumbnailUrl != null) {
                      images.add(ImageKey.newDownloadOnlyKey(notification.post.thumbnailUrl));
                            }
                        }
                    }
          if (followRequests != null && (arrayList = followRequests.items) != null) {
                        for (VineSingleNotification notification2 : arrayList) {
                  if (notification2.thumbnailUrl != null) {
                      images.add(ImageKey.newDownloadOnlyKey(notification2.thumbnailUrl));
                            }
                        }
                    }
                    for (ImageKey key : images) {
              ctr.getPhotoBitmap(key);
                    }
                }

      private void performTimeLinePrefetch(Bundle b, VineDatabaseHelper dbHelper, String[] topUsagesTimelineEndpoints) {
                    for (String endpoint : topUsagesTimelineEndpoints) {
              NetworkOperation op = getPosts(new StringBuilder(VineAPI.getInstance(this).buildUrl(endpoint)), -1, 10, dbHelper, 0L, b, true);
              if (op.isOK()) {
                  performTimeLinePrefetch(b);
                        }
                    }
                }

      private void performTimeLinePrefetch(Bundle b) {
          ArrayList<VinePost> posts = b.getParcelableArrayList("posts");
          AppController appController = AppController.getInstance(this);
          if (posts != null) {
              ArrayList<ImageKey> images = new ArrayList<>();
              ArrayList<VideoKey> videos = new ArrayList<>();
              ArrayList<Long> users = new ArrayList<>();
                        for (VinePost post : posts) {
                  if (post.avatarUrl != null) {
                      images.add(ImageKey.newDownloadOnlyKey(post.avatarUrl));
                            }
                  if (post.thumbnailUrl != null) {
                      images.add(ImageKey.newDownloadOnlyKey(post.thumbnailUrl));
                            }
                  VideoKey videoKey = Util.getVideoRequestKey(post, false, Integer.MAX_VALUE);
                  if (videoKey != null) {
                      videoKey.setDownloadOnly(true);
                      videos.add(videoKey);
                            }
                  users.add(Long.valueOf(post.userId));
                        }
                        for (ImageKey key : images) {
                  appController.getPhotoBitmap(key);
                        }
                        for (VideoKey key2 : videos) {
                  appController.getVideoFilePath(key2);
                        }
                        for (Long userId : users) {
                  StringBuilder url = getUserUrl(userId.longValue());
                  VineParserReader vp = VineParserReader.createParserReader(2);
                  NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
                  assignPollingHeader(op, b);
                  assignCachePolicy(op, b, UrlCachePolicy.NETWORK_ONLY);
                  op.execute();
                  if (op.isOK()) {
                      VineUser user = (VineUser) vp.getParsedObject();
                      b.putParcelable("user", user);
                            }
                        }
                    }
                }

      private static UrlCachePolicy assignCachePolicy(NetworkOperation op, Bundle b, UrlCachePolicy defaultPolicy) {
          UrlCachePolicy cp = (UrlCachePolicy) b.getParcelable(EXTRA_CACHE_POLICY);
          if (cp == null) {
              cp = defaultPolicy;
              b.putParcelable(EXTRA_CACHE_POLICY, defaultPolicy);
                    }
          op.setCachePolicy(cp);
          return cp;
                }

      private static void assignPollingHeader(NetworkOperation op, Bundle b) {
          VineAPI.addPollingHeader(op, b.getBoolean(EXTRA_IS_POLLING, false));
                }

      private void updateBadgeCount(int count) {
          ContentResolver resolver = getContentResolver();
                    try {
              Cursor c = resolver.query(SAMSUNG_BADGER_URI, new String[]{PACKAGE, BADGE_COUNT}, null, null, null);
              if (c != null) {
                  ContentValues cv = new ContentValues();
                  cv.put(PACKAGE, "co.vine.android");
                  cv.put(CLASS, START_CLASS_NAME);
                  cv.put(BADGE_COUNT, Integer.valueOf(count));
                  if (c.getCount() > 0) {
                      resolver.update(SAMSUNG_BADGER_URI, cv, BADGER_SELECTION, BADGER_ARGS);
                            } else {
                      resolver.insert(SAMSUNG_BADGER_URI, cv);
                            }
                  c.close();
                        }
                    } catch (Exception e) {
              SLog.m459e("Ignore samsung error.", (Throwable) e);
                    }
                }

      public synchronized NetworkOperation post(Context context, VineUpload upload, Bundle bundle) {
                    StringBuilder url;
                    VineParserReader vp;
                    NetworkOperation op = null;
                    try {
                        VineAPI api = VineAPI.getInstance(context);
              JSONObject postBody = new JSONObject();
              PostInfo info = upload.getPostInfo();
              String path = upload.path;
              boolean isPrivate = upload.isPrivate;
              String[] uris = upload.getUris();
                        try {
                  if (upload.path != null) {
                      RecordSessionVersion version = UploadManager.getVersionFromPath(upload.path);
                      String videoUrl = "" + uris[0];
                      String thumbnailUrl = "" + uris[1];
                      if (version == RecordSessionVersion.SW_WEBM) {
                          postBody.put("videoWebmUrl", videoUrl);
                                } else {
                          postBody.put("videoUrl", videoUrl);
                                }
                      postBody.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, thumbnailUrl);
                      postBody.put("model", Build.MODEL);
                      postBody.put("device", Build.DEVICE);
                      File metaDataFile = new File(RecordConfigUtils.getMetadataPath(upload.path));
                      JSONArray cuts = null;
                      if (metaDataFile.exists()) {
                                    try {
                              cuts = new JSONArray(RecordConfigUtils.readForUpload(metaDataFile));
                                    } catch (Throwable e) {
                              CrashUtil.logException(e);
                                    }
                                }
                      if (cuts != null) {
                          postBody.put("cuts", cuts);
                                }
                                try {
                          postBody.put("version", String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode));
                                } catch (Exception e2) {
                                }
                            }
                        } catch (JSONException e3) {
                  CrashUtil.logOrThrowInDebug(e3);
                        }
              if (!isPrivate) {
                  url = VineAPI.buildUponUrl(api.getBaseUrl(), "posts");
                  vp = VineParserReader.createParserReader(12);
                  if (info != null) {
                                try {
                          if (!info.caption.isEmpty()) {
                              SLog.m447d("Added description: {}.", info.caption);
                              postBody.put("description", Util.cleanse(info.caption));
                              if (info.entities != null && !info.entities.isEmpty()) {
                                  postBody.put("entities", PostInfo.entitiesToJsonArray(info.entities));
                                        }
                                    } else {
                              SLog.m446d("empty description.");
                                    }
                                    if (info.channelId > 0) {
                              postBody.put("channelId", String.valueOf(info.channelId));
                                    }
                          postBody.put("postToTwitter", info.postToTwitter ? "1" : "0");
                          postBody.put("postToFacebook", info.postToFacebook ? "1" : "0");
                                } catch (JSONException e4) {
                          CrashUtil.logOrThrowInDebug(e4);
                                }
                            }
                        } else {
                  url = VineAPI.buildUponUrl(api.getBaseUrl(), "conversations");
                  VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(context);
                  long conversationId = dbHelper.getConversationRemoteId(upload.conversationRowId);
                            if (conversationId > 0) {
                      url = VineAPI.buildUponUrl(url.toString(), Long.valueOf(conversationId));
                            } else if (info.recipients == null && upload.conversationRowId > 0) {
                      info.recipients = dbHelper.getConversationRecipientsFromConversationRowId(upload.conversationRowId);
                            }
                  vp = VineParserReader.createParserReader(24);
                  if (info != null) {
                                try {
                          if (!TextUtils.isEmpty(info.message)) {
                              postBody.put("message", info.message);
                                    }
                                    if (info.postId > 0) {
                              postBody.put("postId", info.postId);
                                    }
                          postBody.put("created", info.created);
                          if (info.maxLoops > 0 && info.maxLoops < Integer.MAX_VALUE) {
                              postBody.put("maxLoops", info.maxLoops);
                                    }
                          if (info.recipients != null && !info.recipients.isEmpty()) {
                              postBody.put("to", PostInfo.recipientsToJsonArray(info.recipients));
                                    } else if (conversationId <= 0) {
                              SLog.dWithTag(TAG, "VM post failed: The recipients array is empty and no conversation id");
                                    }
                                } catch (JSONException e5) {
                          CrashUtil.logOrThrowInDebug(e5);
                                }
                            }
                  addCountryParam(postBody);
                        }
              SLog.m447d("Prefix {}", "");
              SLog.m448d("Adding URI Params: {}, {}.", uris[0], uris[1]);
              op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url, api, postBody, vp).execute();
              if (op.isOK()) {
                  if (!isPrivate) {
                      VinePostResponse vpr = (VinePostResponse) vp.getParsedObject();
                      if (vpr == null) {
                          op = null;
                                } else {
                          Intent intent = new Intent(BC_ACTION_FETCH_AND_MERGE_POST);
                          intent.putExtra("post", vpr);
                          SLog.m446d("Regular video post successful, sending merge broadcast now");
                          context.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                                }
                            } else {
                      handleVinePrivateMessageResponses(context, upload, info, vp, bundle);
                            }
                        } else {
                  VineError error = (VineError) vp.getParsedObject();
                  if (error != null) {
                      SLog.m457e("ERROR POSTING: {} {}", Integer.valueOf(op.statusCode), error.message);
                      if (error.errorCode == VineKnownErrors.CAPTCHA.code) {
                          upload.captchaUrl = error.data;
                          if (upload.isPrivate) {
                              VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, error.errorCode, getString(R.string.message_failed_tap_to_retry));
                                    }
                                }
                      if (upload.isPrivate) {
                          VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, error.errorCode, getString(R.string.message_failed_tap_to_retry));
                                }
                            } else {
                      if (upload.isPrivate) {
                          VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, -1, getString(R.string.message_failed_tap_to_retry));
                                }
                      SLog.dWithTag(TAG, "Post failed: Unknown error while posting path=" + path);
                            }
                        }
                    } catch (Throwable th) {
                        CrashUtil.logException(th, "Post request failed", new Object[0]);
                    }
                    return op;
                }

      private void handleVinePrivateMessageResponses(Context context, VineUpload upload, PostInfo info, VineParserReader vp, Bundle bundle) {
                    String text;
          VinePrivateMessagePostResponseWithUsers serverResponse = (VinePrivateMessagePostResponseWithUsers) vp.getParsedObject();
          ArrayList<VinePrivateMessageResponse> messageResponses = serverResponse.responses;
          ArrayList<VineUser> users = serverResponse.users;
          ArrayList<VineRecipient> recipients = serverResponse.recipients;
          VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(context);
          VinePrivateMessage premergedMessage = dbHelper.getMessageFromMessageRow(upload.mergedMessageId);
                    if (upload.mergedMessageId <= 0 || premergedMessage == null) {
              CrashUtil.log("No local message to merge with.");
              return;
                    }
          if (users != null) {
              dbHelper.mergeRecipientsWithUsersAndRemoveUnusedRecipients(premergedMessage.conversationRowId, recipients, users);
                    }
          long messageIdToMerge = -1;
          if (messageResponses == null || messageResponses.size() < 1) {
                        Object[] objArr = new Object[1];
                        objArr[0] = Boolean.valueOf(messageResponses == null);
              CrashUtil.log("No vine private message responses, deleting the conversation and bailing. Response is null: {}", objArr);
              dbHelper.deleteConversation(-1L, premergedMessage.conversationRowId);
              return;
                    }
          if (messageResponses.size() == 1) {
                        if (premergedMessage.conversationId <= 0) {
                  premergedMessage.conversationRowId = dbHelper.mergeConversationWithLocalId(premergedMessage.conversationRowId, messageResponses.get(0).conversationId, premergedMessage.networkType);
                        }
              messageIdToMerge = upload.mergedMessageId;
                    } else {
              dbHelper.deleteConversation(-1L, premergedMessage.conversationRowId);
                    }
          String smsFormatter = null;
          String smsFooter = null;
          String videoUrl = null;
          String thumbnailUrl = null;
                    for (VinePrivateMessageResponse vpmr : messageResponses) {
              if (vpmr != null) {
                  if (vpmr.recipient != null && vpmr.recipient.key.equals("phoneNumber") && vpmr.shareUrl != null) {
                      if (smsFormatter == null) {
                          smsFormatter = getString(R.string.sms_text);
                                }
                      if (smsFooter == null) {
                          smsFooter = getString(R.string.sms_footer);
                                }
                      if (TextUtils.isEmpty(info.message) || info.maxLoops > 0) {
                          text = String.format(smsFormatter, vpmr.shareUrl);
                                } else {
                          text = info.message + ": " + vpmr.shareUrl;
                                }
                      String text2 = text + smsFooter;
                      SLog.m448d("Send SMS to {} with text {}", vpmr.recipient.value, text2);
                      SMSUtil.sendSMS(vpmr.recipient.value, text2);
                            }
                  if (vpmr.videoUrl != null) {
                      videoUrl = vpmr.videoUrl;
                            }
                  if (vpmr.thumbnailUrl != null) {
                      thumbnailUrl = vpmr.thumbnailUrl;
                            }
                  handleSingleVinePrivateMessageResponse(context, dbHelper, vpmr, info, messageIdToMerge, premergedMessage.conversationRowId);
                  if (vpmr.error != null) {
                      bundle.putBoolean(EXTRA_KEEP_UPLOAD, true);
                            }
                        }
                    }
          bundle.putString(EXTRA_VIDEO_URL, videoUrl);
          bundle.putString("thumbnail_url", thumbnailUrl);
                }

      private void handleSingleVinePrivateMessageResponse(Context context, VineDatabaseHelper dbHelper, VinePrivateMessageResponse vpmr, PostInfo info, long messageIdToMerge, long conversationRowIdToMerge) {
                    int errorCode;
                    String errorReason;
                    long conversationRowId;
          long userRowId = dbHelper.getUserRowIdForUserRemoteId(AppController.getInstance(context).getActiveSessionReadOnly().getUserId());
          if (vpmr.error != null) {
              errorCode = vpmr.error.errorCode;
              errorReason = vpmr.error.message;
                    } else {
              errorCode = 0;
              errorReason = null;
                    }
          VinePrivateMessage vpm = new VinePrivateMessage(-1L, vpmr.conversationId, vpmr.messageId, userRowId, info.created, info.message, vpmr.videoUrl, vpmr.thumbnailUrl, 1, false, false, info.maxLoops, 0L, info.maxLoops > 0 && info.maxLoops < Integer.MAX_VALUE, 0L, info.postId, errorCode, errorReason);
                    if (messageIdToMerge > 0) {
              long finalMessageRowId = dbHelper.mergeMessageWithMessageRow(messageIdToMerge, conversationRowIdToMerge, vpm);
              dbHelper.updateConversationWithLastMessage(conversationRowIdToMerge, finalMessageRowId, vpm.created);
                    } else {
              HashSet<Long> userRowIds = new HashSet<>();
              userRowIds.add(Long.valueOf(vpmr.recipient.recipientId));
                        if (vpmr.conversationId > 0) {
                  conversationRowId = dbHelper.getConversationRowId(vpmr.conversationId);
                        } else {
                  conversationRowId = dbHelper.determineBestConversationRowIdForUserRowId(vpmr.recipient.recipientId);
                        }
                        if (conversationRowId <= 0) {
                  conversationRowId = dbHelper.createConversationRowId(userRowIds, 1);
                  dbHelper.mergeConversationWithLocalId(conversationRowId, vpmr.conversationId, 1);
                        }
              long messageId = dbHelper.mergeMessage(conversationRowId, vpm);
              dbHelper.updateConversationWithLastMessage(conversationRowId, messageId, vpm.created);
                    }
          SLog.m446d("VM post successful, sending merge broadcast now");
          Intent intent = new Intent(BC_ACTION_ALERT_RTC_NEW_MESSAGE);
          intent.putExtra("conversation_id", vpm.conversationId);
          intent.putExtra("message_id", vpm.messageId);
          context.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                }

      public void limitCommentAndLikesIfNeeded(StringBuilder url) {
          if (BuildUtil.isOldDeviceOrLowEndDevice(this)) {
              VineAPI.addParam(url, PARAM_COMMENT_MAX, 0);
              VineAPI.addParam(url, PARAM_LIKE_MAX, 0);
                    }
                }

      private StringBuilder generatePostFetchUrl(int type, long userId, int page, Bundle b, boolean prefetch) {
                    StringBuilder url;
          String base = this.mApi.getBaseUrl();
                    switch (type) {
                        case 1:
                  url = VineAPI.buildUponUrl(base, "timelines", VineAPI.RESOURCE_GRAPH);
                            break;
                        case 2:
                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId));
                  VineAPI.addParam(url, "reposts", 1);
                  VineAPI.addParam(url, PARAM_COMMENT_OVERFLOW, "trunc");
                            break;
                        case 3:
                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId), "likes");
                            break;
                        case 4:
                  url = VineAPI.buildUponUrl(base, "timelines", "users", VineAPI.RESOURCE_TRENDING);
                            break;
                        case 5:
                  url = VineAPI.buildUponUrl(base, "timelines", "popular");
                            break;
                        case 6:
                  String tag = b.getString("tag_name");
                  url = VineAPI.buildUponUrl(base, "timelines", "tags", tag);
                            break;
                        case 7:
                        default:
                  throw new IllegalArgumentException("Tried to fetch timeline with unsupported type " + type);
                        case 8:
                        case 9:
                  String channelId = b.getString("tag_name");
                  String sort = b.getString("sort");
                  url = VineAPI.buildUponUrl(base, "timelines", "channels", channelId, sort);
                            break;
                        case 10:
                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId));
                  VineAPI.addParam(url, "reposts", 0);
                            break;
                        case 11:
                  url = VineAPI.buildUponUrl(base, "timelines");
                  Uri data = (Uri) b.getParcelable("data");
                            for (String pathSegment : data.getPathSegments()) {
                      url = VineAPI.buildUponUrl(url.toString(), pathSegment);
                            }
                            break;
                    }
          if (!prefetch) {
              BehaviorManager.getInstance(this).onFetchPosts(url.toString(), page);
                    }
          VineAPI.addParam(url, "page", page);
          return url;
                }

      private NetworkOperation getPosts(StringBuilder url, int type, int size, VineDatabaseHelper dbHelper, long anchor, Bundle bundle, boolean prefetch) {
                    VinePagedData<VinePost> pagedData;
          VineAPI.addAnchor(url, anchor);
          VineAPI.addParam(url, "size", size);
          limitCommentAndLikesIfNeeded(url);
          SLog.m447d("Fetching timeline: {}", url);
          VineParserReader vp = VineParserReader.createParserReader(8);
          Log.d("VineNetwork", "feed request type=" + type + " url=" + url.toString() + " userId=" + bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L));
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
          assignPollingHeader(op, bundle);
          UrlCachePolicy resolvedCachePolicy = assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
          op.execute();
          PrefetchManager.getInstance(this).onPostFetchOperationComplete(prefetch, op.getLastExecuteResult(), resolvedCachePolicy);
          if (op.isOK() && (pagedData = (VinePagedData) vp.getParsedObject()) != null) {
              if (type >= 0) {
                  if (Util.isPopularTimeline(type)) {
                      int count = 1;
                      Cursor c = dbHelper.getOldestSortId(type);
                      if (c != null) {
                          if (c.moveToFirst()) {
                              count = c.getInt(0) + 1;
                                    }
                          c.close();
                                }
                                for (VinePost post : pagedData.items) {
                                    post.orderId = String.valueOf(count);
                          count++;
                                }
                            }
                  bundle.putInt("count", pagedData.items == null ? 0 : pagedData.items.size());
                  bundle.putInt("size", pagedData.count);
                  bundle.putParcelableArrayList("posts", pagedData.items);
                  bundle.putBoolean(EXTRA_IN_MEMORY, true);
                  bundle.putInt("next_page", pagedData.nextPage);
                  bundle.putInt("previous_page", pagedData.previousPage);
                  bundle.putLong(EXTRA_ANCHOR, pagedData.anchor);
                  bundle.putString("title", pagedData.title);
                  if (type == 8 || type == 9) {
                      bundle.putParcelable("channels", pagedData.channel);
                            }
                        } else {
                  bundle.putParcelableArrayList("posts", pagedData.items);
                        }
                    }
          Log.d("VineNetwork", "feed response status=" + op.statusCode + " reason=" + op.statusPhrase + " parsed=" + (vp.getParsedObject() != null));
          return op;
                }

      private VineConversation getConversationWithRemoteId(long sessionOwnerId, long conversationId, int pageType, VineDatabaseHelper dbHelper, boolean prefetch, int networkTYpe) {
                    VinePagedData<VinePrivateMessage> data;
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId));
                    switch (pageType) {
                        case 1:
                        case 2:
                  VineAPI.addParam(url, "page", 1);
                            break;
                        case 3:
                  long page = dbHelper.getNextPageCursor(7, 0, String.valueOf(conversationId), false);
                            if (page > 0) {
                      VineAPI.addParam(url, "page", page);
                            }
                            break;
                    }
          VineAPI.addParam(url, "prefetch", prefetch ? 1 : 0);
          VineParserReader vp = VineParserReader.createParserReader(22);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp).execute();
          if (op.isOK() && (data = (VinePagedData) vp.getParsedObject()) != null && data.items != null && data.items.size() > 0) {
              VineConversation conversation = new VineConversation(conversationId, data.lastMessage, data.items, data.unreadMessageCount);
                        try {
                  long conversationLocalObjectId = dbHelper.mergeConversation(sessionOwnerId, conversation, networkTYpe, data.nextPage, data.previousPage, data.anchor);
                  if (data.nextPage <= 0) {
                      dbHelper.markLastMessage(conversationLocalObjectId);
                            }
                  conversation.conversationObjectId = conversationLocalObjectId;
                  return conversation;
                        } catch (IOException e) {
                  if (LOGGABLE) {
                      e.printStackTrace();
                            }
                        }
                    }
          return null;
                }

      private NetworkOperation getInbox(long sessionOwnerId, int pageType, int actionCode, String key, VineDatabaseHelper dbHelper, Bundle b) throws IOException {
                    int userGroup;
          int networkType = b.getInt("network", -1);
          int page = 1;
                    switch (pageType) {
                        case 1:
                        case 2:
                  page = 1;
                            break;
                        case 3:
                  int previous = dbHelper.getPreviousPageCursor(6, networkType, null, true);
                  if (previous > 0) {
                      page = previous;
                            }
                            break;
                    }
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations");
          if (networkType == -1) {
              throw new IllegalArgumentException("Invalid network type.");
                    }
          VineParserReader vp = VineParserReader.createParserReader(25);
          if (1 == networkType) {
              userGroup = 10;
                    } else {
              VineAPI.addParam(url, PARAM_INBOX, CommonUtil.FIELD_TYPE_OTHER);
              userGroup = 11;
                    }
          VineAPI.addParam(url, "page", page);
          VineAPI.addParam(url, "size", 10);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp, key).execute();
          if (op.isOK()) {
              VinePagedData<VineConversation> data = (VinePagedData) vp.getParsedObject();
              if (data.items != null && !data.items.isEmpty()) {
                  dbHelper.mergeInbox(sessionOwnerId, data, data.nextPage, data.previousPage, data.anchor, networkType, userGroup);
                  b.putInt("count", data.items.size());
                  if (data.nextPage <= 0) {
                      dbHelper.markLastConversation(networkType);
                            }
                        }
                    }
          return op;
                }

      public void addCountryParam(ArrayList<BasicNameValuePair> params) {
          params.add(new BasicNameValuePair(PARAM_LOCALE, Util.getCountryCode()));
                }

      public void addCountryParam(HashMap<String, Object> params) {
          params.put(PARAM_LOCALE, Util.getCountryCode());
                }

      public void addCountryParam(JSONObject params) throws JSONException {
          params.put(PARAM_LOCALE, Util.getCountryCode());
                }

      private NetworkOperation postComment(long postId, long repostId, long userId, String username, String comment, String avatarUrl, ArrayList<VineEntity> entities, VineDatabaseHelper dbHelper, Bundle b) {
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments");
          JSONObject postBody = new JSONObject();
                    try {
              postBody.put("comment", comment);
              JSONArray jsonEntities = new JSONArray();
              Iterator<VineEntity> it = entities.iterator();
                        while (it.hasNext()) {
                  jsonEntities.put(it.next().toJsonObject());
                        }
              if (jsonEntities.length() > 0) {
                  postBody.put("entities", jsonEntities);
                        }
                        if (repostId > 0) {
                  postBody.put(PARAM_REPOST_ID, repostId);
                        }
                    } catch (JSONException e) {
              CrashUtil.logOrThrowInDebug(e);
                    }
          VineParserReader vp = VineParserReader.createParserReader(7);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthJsonPostRequest(this, url, this.mApi, postBody, vp).execute();
          if (op.isOK()) {
              VineComment vc = (VineComment) vp.getParsedObject();
             if (vc == null) {
                 vc = new VineComment(0L, comment, avatarUrl, null, System.currentTimeMillis(), null, userId, username, false, 0L, new ArrayList<VineEntity>(), false);
                            }
              vc.postId = postId;
              vc.userId = userId;
              vc.username = username;
              vc.avatarUrl = avatarUrl;
              int offsetCount = 0;
                        if (entities == null) {
                 entities = new ArrayList<>();
                            }
                        for (VineEntity entity : entities) {
                  entity.start -= offsetCount;
                  if ("tag".equals(entity.type)) {
                      offsetCount++;
                      String plainTitle = VineTypeAhead.getPlainTag(entity.title);
                      comment = comment.replaceFirst(entity.title, plainTitle);
                      entity.title = plainTitle;
                      dbHelper.mergeSuggestedTag(new VineTag(plainTitle, entity.f96id));
                            }
                  entity.end -= offsetCount;
                        }
              vc.comment = comment;
              vc.entities = entities;
              vc.timestamp = System.currentTimeMillis();
              b.putParcelable(EXTRA_COMMENT_OBJ, vc);
                    }
          return op;
                }

      private NetworkOperation deleteComment(long postId, long repostId, String commentId, VineDatabaseHelper dbHelper) {
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments", commentId);
                    if (repostId > 0) {
              VineAPI.addParam(url, PARAM_REPOST_ID, repostId);
                    }
          VineParserReader vp = VineParserReader.createParserReader(7);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthDeleteRequest(this, url, this.mApi, vp).execute();
          return op;
                }

      private NetworkOperation getClientProfile() {
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), VineAPI.RESOURCE_CLIENT_PROFILE);
          SLog.m481w("Updating device profile for {} {} {}.", new Object[]{Build.MODEL, Build.DEVICE, Integer.valueOf(Build.VERSION.SDK_INT)});
          VineAPI.addParam(url, "model", Build.MODEL);
          VineAPI.addParam(url, "device", Build.DEVICE);
          VineAPI.addParam(url, "sdkVersion", Build.VERSION.SDK_INT);
          VineAPI.addParam(url, "memory", ((ActivityManager) getSystemService(HomeTabActivity.TAG_ACTIVITY)).getLargeMemoryClass());
          VineParserReader vp = VineParserReader.createParserReader(13);
          NetworkOperation op = this.mNetOpFactory.createGetRequest(this, url, vp, this.mApi);
          if (op.isOK()) {
              JSONObject object = (JSONObject) vp.getParsedObject();
              if (object != null) {
                  SLog.m447d("Got client profile {}.", object);
                  AppImpl.getInstance().updateRecordConfigs(getApplicationContext(), object);
                        } else {
                  SLog.m446d("Error getting client profile.");
                        }
                    } else {
              SLog.m447d("Failed to update client profile: {}.", Integer.valueOf(op.statusCode));
                    }
          return op;
                }

      public static Intent getPrefetchIntent(Context context, boolean manual) {
          Intent intent = new Intent(context, (Class<?>) VineService.class);
          intent.setAction(ACTION_PREFETCH);
          intent.putExtra(EXTRA_MANUAL, manual);
          return intent;
                }

      private NetworkOperation getComments(long postId, int page, long anchor, int size, String key, Bundle bundle, VineDatabaseHelper dbHelper) {
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments");
          VineAPI.addParam(url, "page", page);
          VineAPI.addAnchor(url, anchor);
          VineAPI.addParam(url, "size", size);
          VineParserReader vp = VineParserReader.createParserReader(6);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp).execute();
          if (op.isOK()) {
              VinePagedData<VineComment> comments = (VinePagedData) vp.getParsedObject();
              bundle.putInt("next_page", comments.nextPage);
              bundle.putLong(EXTRA_ANCHOR, comments.anchor);
              bundle.putInt("previous_page", comments.previousPage);
              bundle.putParcelableArrayList("comments", comments.items);
                    }
          return op;
                }

      private NetworkOperation getUsersMe(Bundle b) {
                    VineUser user;
          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
          VineParserReader vp = VineParserReader.createParserReader(2);
          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
          assignPollingHeader(op, b);
          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
          op.execute();
          if (op.isOK() && (user = (VineUser) vp.getParsedObject()) != null) {
              VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(this);
              user.f99id = dbHelper.mergeUserAndGetResultingRowId(user);
              b.putParcelable("user", user);
                    }
          return op;
                }

      void attemptStop(int startId) {
          this.mStartIds.put(Integer.valueOf(startId), true);
                    for (Map.Entry<Integer, Boolean> entry : this.mStartIds.entrySet()) {
              if (!entry.getValue().booleanValue()) {
                  return;
                        }
                    }
                    for (Integer key : this.mStartIds.keySet()) {
              stopSelf(key.intValue());
                    }
          this.mStartIds.clear();
                }

      private class ResponderRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final String mReasonPhrase;
                    private final VineServiceResponder mResponder;
                    private final int mStartId;
                    private final int mStatusCode;

          public ResponderRunnable(VineServiceResponder responder, int actionCode, int statusCode, String reasonPhrase, Bundle bundle, int startId) {
              this.mResponder = responder;
              this.mActionCode = actionCode;
              this.mStatusCode = statusCode;
              this.mReasonPhrase = reasonPhrase;
              this.mBundle = bundle;
              this.mStartId = startId;
                    }

                    @Override 
          public void run() {
              if (this.mResponder != null) {
                  this.mResponder.onServiceResponse(this.mActionCode, this.mStatusCode, this.mReasonPhrase, this.mBundle);
                        }
              if (this.mStartId > 0) {
                  VineService.this.attemptStop(this.mStartId);
                        }
                    }
                }

      private class MessengerResponderRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final String mReasonPhrase;
                    private final Messenger mResponder;
                    private final int mStartId;
                    private final int mStatusCode;

          public MessengerResponderRunnable(Messenger responder, int actionCode, int statusCode, String reasonPhrase, Bundle bundle, int startId) {
              this.mResponder = responder;
              this.mActionCode = actionCode;
              this.mStatusCode = statusCode;
              this.mReasonPhrase = reasonPhrase;
              this.mBundle = bundle;
              this.mStartId = startId;
                    }

                    @Override 
          public void run() {
              if (this.mResponder != null) {
                  Message msg = new Message();
                  msg.what = this.mActionCode;
                  msg.arg1 = this.mStatusCode;
                  Bundle b = msg.getData();
                  b.putString(VineService.EXTRA_REASON_PHRASE, this.mReasonPhrase);
                  b.putAll(this.mBundle);
                            try {
                      this.mResponder.send(msg);
                            } catch (Exception e) {
                      CrashUtil.logException(e, "Error sending service response", new Object[0]);
                            }
                        }
              if (this.mStartId > 0) {
                  VineService.this.attemptStop(this.mStartId);
                        }
                    }
                }

      private class ExecutionRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final Messenger mMessenger;
                    private final VineServiceResponder mResponder;
                    private final int mStartId;

          public ExecutionRunnable(int startId, int actionCode, Bundle bundle, VineServiceResponder responder, Messenger messenger) {
              this.mStartId = startId;
              this.mActionCode = actionCode;
              this.mBundle = bundle;
              this.mResponder = responder;
              this.mMessenger = messenger;
                    }

                    @Override 
          public void run() {
                        Runnable responderRunnable;
                        try {
              Process.setThreadPriority(10);
              Bundle result = VineService.this.executeAction(this.mActionCode, this.mBundle);
                        switch (result.getInt(VineService.EXECUTION_CODE)) {
                            case -999:
                      CrashUtil.log("Service is not ready, {} needs to retry later.", Integer.valueOf(this.mActionCode));
                                break;
                            case 2:
                      SLog.m446d("Session key was invalid. Refreshing session key and then will try again");
                      result = VineService.this.executeAction(1000, this.mBundle);
                                switch (result.getInt(VineService.EXECUTION_CODE)) {
                                    case 2:
                              SLog.m446d("Session key could not be refreshed. Aborting.");
                              this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                        break;
                                    case 3:
                              SLog.m446d("Session was logged out.");
                              this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                        break;
                                    default:
                              SLog.m446d("Session key successfully refreshed. Trying original action of " + this.mActionCode + " again");
                              result = VineService.this.executeAction(this.mActionCode, this.mBundle);
                                        switch (result.getInt(VineService.EXECUTION_CODE)) {
                                            case 2:
                                      SLog.m446d("Session key is still invalid. Aborting.");
                                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                                break;
                                            case 3:
                                      SLog.m446d("Session was logged out.");
                                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                                break;
                                            default:
                                      if (VineService.access$200()) {
                                          this.mBundle.putBoolean(VineService.EXTRA_REFRESH_SESSION, true);
                                          SLog.m446d("Session key refresh and retry complete.");
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                      SLog.m446d("Session was logged out.");
                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                break;
                            case 4:
                      SLog.m446d("Captcha triggered");
                      this.mBundle.putString("captcha_url", result.getString("captcha_url"));
                                break;
                        }
              int statusCode = result.getInt(VineService.STATUS_CODE);
              String reasonPhrase = result.getString(VineService.REASON_PHRASE);
              if (this.mResponder != null) {
                  responderRunnable = VineService.this.new ResponderRunnable(this.mResponder, this.mActionCode, statusCode, reasonPhrase, this.mBundle, this.mStartId);
                        } else if (this.mMessenger != null) {
                  responderRunnable = VineService.this.new MessengerResponderRunnable(this.mMessenger, this.mActionCode, statusCode, reasonPhrase, this.mBundle, this.mStartId);
                        } else {
                  responderRunnable = null;
                        }
              if (responderRunnable != null) {
                  VineService.access$300(VineService.this).post(responderRunnable);
              }
                        } catch (Throwable e) {

                            CrashUtil.logException(e, "Service execution failed", new Object[0]);
                        }
                    }
                }
            }


