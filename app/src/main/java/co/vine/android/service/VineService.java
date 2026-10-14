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
            import co.vine.android.C0257R;
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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 141 */   public class VineService extends Service {
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

/* 142 */       public VineService() {
                    this.mFileLogger = FileLogger.getLogger(SLog.sLogsOn ? LogReaderTask.SERVICE_LOG : null);
/* 514 */           this.mServiceBroadCastReceiver = new C05221();
                }

/* 142 */       static /* synthetic */ ExecutorService access$000(VineService x0) {
/* 143 */           return x0.mExecutor;
                }

/* 142 */       static /* synthetic */ boolean access$200() {
/* 143 */           return LOGGABLE;
                }

/* 142 */       static /* synthetic */ Handler access$300(VineService x0) {
/* 143 */           return x0.mMainHandler;
                }

                static {
/* 466 */           LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
/* 478 */           SAMSUNG_BADGER_URI = Uri.parse("content://com.sec.badge/apps");
/* 480 */           sActionMap = new HashMap<>(3);
/* 498 */           sActionMap.put(ACTION_RESTART_CONNECTION_MANAGER, 1);
/* 499 */           sActionMap.put(SessionChangedReceiver.ACTION_SESSION_LOGIN, 2);
/* 500 */           sActionMap.put(ACTION_DEVICE_BOOT, 128);
/* 501 */           sActionMap.put(ACTION_PREFETCH, 127);
/* 3620 */          START_CLASS_NAME = StartActivity.class.getCanonicalName();
/* 3622 */          BADGER_ARGS = new String[]{"co.vine.android"};
                }

/* 502 */       private static class ExecutionPair {

                    /* JADX INFO: renamed from: op */
                    final NetworkOperation f111op;

                    /* JADX INFO: renamed from: vp */
                    final VineParserReader f112vp;

/* 503 */           /* synthetic */ ExecutionPair(VineParserReader x0, NetworkOperation x1, C05221 x2) {
/* 504 */               this(x0, x1);
                    }

/* 507 */           private ExecutionPair(VineParserReader vp, NetworkOperation op) {
/* 509 */               this.f112vp = vp;
/* 510 */               this.f111op = op;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.VineService$1 */
/* 512 */       class C05221 extends BroadcastReceiver {
/* 513 */           C05221() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 517 */           public void onReceive(Context context, Intent intent) {
/* 518 */               if (intent != null && VineService.BC_ACTION_FETCH_AND_MERGE_POST.equals(intent.getAction())) {
/* 520 */                   SLog.m446d("Received broadcasted intent to merge a single post.");
/* 521 */                   AppController.getInstance(VineService.this).mergeSinglePost(intent.getExtras());
                        }
                    }
                }

/* 528 */       class VineServiceHandler extends Handler {
/* 529 */           VineServiceHandler() {
                    }

                    @Override // android.os.Handler
/* 532 */           public void handleMessage(Message msg) {
/* 533 */               msg.getData().setClassLoader(VineService.this.getClassLoader());
/* 534 */               VineService.access$000(VineService.this).execute(VineService.this.new ExecutionRunnable(0, msg.arg1, msg.getData(), null, msg.replyTo));
                    }
                }

                @Override // android.app.Service
/* 539 */       public IBinder onBind(Intent intent) {
/* 540 */           return this.mMessenger.getBinder();
                }

                @Override // android.app.Service
/* 544 */       public void onCreate() {
/* 545 */           super.onCreate();
/* 546 */           this.mExecutor = Executors.newCachedThreadPool();
/* 547 */           this.mStartIds = new LinkedHashMap<>(50, 50.0f);
/* 548 */           registerReceiver(this.mServiceBroadCastReceiver, SERVICE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override // android.app.Service
/* 552 */       public void onDestroy() {
/* 553 */           super.onDestroy();
/* 554 */           unregisterReceiver(this.mServiceBroadCastReceiver);
/* 555 */           this.mFileLogger.closeOutput();
                }

/* 558 */       private synchronized void init() {
                    try {
                        if (this.mApi == null) {
/* 560 */                   this.mApi = VineAPI.getInstance(this);
                        }
/* 562 */               if (this.mMediaUtility == null) {
/* 563 */                   this.mMediaUtility = new MediaUtility(this);
                        }
/* 565 */               if (SLog.sLogsOn) {
/* 566 */                   this.mFileLogger.prepareOutput();
                        }
                    } catch (Throwable th) {
                        CrashUtil.logException(th, "Post request failed", new Object[0]);
                    }
                }

                @Override // android.app.Service
/* 571 */       public int onStartCommand(Intent intent, int flags, int startId) {
                    VineServiceResponder responder;
/* 572 */           if (intent != null) {
/* 576 */               init();
/* 577 */               Bundle bundle = intent.getExtras();
/* 578 */               VineServiceCallback cb = (VineServiceCallback) intent.getParcelableExtra(EXTRA_CALLBACK);
/* 580 */               if (cb != null) {
/* 581 */                   responder = cb.getResponder();
                        } else {
/* 583 */                   responder = null;
                        }
/* 585 */               Integer action = sActionMap.get(intent.getAction());
/* 586 */               if (action == null) {
/* 587 */                   if (LOGGABLE) {
/* 588 */                       Log.d(TAG, "Unknown action: " + intent.getAction());
                            }
                        } else {
/* 590 */                   if (bundle == null) {
/* 591 */                       throw new IllegalArgumentException("Intent must contain extras: " + intent);
                            }
/* 593 */                   SLog.m465i("VineService started with action {}", action);
/* 594 */                   this.mExecutor.execute(new ExecutionRunnable(startId, action.intValue(), bundle, responder, null));
/* 595 */                   this.mStartIds.put(Integer.valueOf(startId), false);
                        }
                    }
                    return 2;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:1022:0x2a45 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:1023:0x2a3a A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:1029:0x2b13 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:1030:0x2b13 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:121:0x05fc  */
                /* JADX WARN: Code duplicated, block: B:124:0x062c A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:125:0x062e  */
                /* JADX WARN: Code duplicated, block: B:128:0x0645  */
                /* JADX WARN: Code duplicated, block: B:129:0x0648  */
                /* JADX WARN: Code duplicated, block: B:309:0x1265  */
                /* JADX WARN: Code duplicated, block: B:312:0x1275  */
                /* JADX WARN: Code duplicated, block: B:315:0x12b5  */
                /* JADX WARN: Code duplicated, block: B:317:0x12bb  */
                /* JADX WARN: Code duplicated, block: B:320:0x12dd  */
                /* JADX WARN: Code duplicated, block: B:322:0x12ec  */
                /* JADX WARN: Code duplicated, block: B:327:0x1317  */
                /* JADX WARN: Code duplicated, block: B:329:0x1323  */
                /* JADX WARN: Code duplicated, block: B:332:0x1340 A[LOOP:5: B:330:0x133a->B:332:0x1340, LOOP_END] */
                /* JADX WARN: Code duplicated, block: B:339:0x1384  */
                /* JADX WARN: Code duplicated, block: B:341:0x1389  */
                /* JADX WARN: Code duplicated, block: B:346:0x13bb  */
                /* JADX WARN: Code duplicated, block: B:350:0x140e  */
                /* JADX WARN: Code duplicated, block: B:353:0x141c  */
                /* JADX WARN: Code duplicated, block: B:475:0x1e6b  */
                /* JADX WARN: Code duplicated, block: B:482:0x1eb5  */
                /* JADX WARN: Code duplicated, block: B:485:0x1ecf  */
                /* JADX WARN: Code duplicated, block: B:488:0x1ee9  */
                /* JADX WARN: Code duplicated, block: B:491:0x1f05  */
                /* JADX WARN: Code duplicated, block: B:493:0x1f0f  */
                /* JADX WARN: Code duplicated, block: B:496:0x1f4c A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:497:0x1f4e  */
                /* JADX WARN: Code duplicated, block: B:500:0x1f65  */
                /* JADX WARN: Code duplicated, block: B:501:0x1f68  */
                /* JADX WARN: Code duplicated, block: B:502:0x1f6c  */
                /* JADX WARN: Code duplicated, block: B:647:0x2a01  */
                /* JADX WARN: Code duplicated, block: B:649:0x2a1c  */
                /* JADX WARN: Code duplicated, block: B:652:0x2a26  */
                /* JADX WARN: Code duplicated, block: B:659:0x2a65  */
                /* JADX WARN: Code duplicated, block: B:661:0x2a76  */
                /* JADX WARN: Code duplicated, block: B:664:0x2ab1  */
                /* JADX WARN: Code duplicated, block: B:666:0x2abb  */
                /* JADX WARN: Code duplicated, block: B:668:0x2ad8  */
                /* JADX WARN: Code duplicated, block: B:680:0x2b2b  */
                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
/* 607 */       Bundle executeAction(int actionCode, Bundle bundle) throws Throwable {
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
/* 608 */           String rid = null;
                    try {
/* 611 */               rid = bundle.getString(EXTRA_REQUEST_ID);
                    } catch (Exception e) {
/* 614 */               CrashUtil.logException(new IllegalArgumentException(e));
                    }
/* 617 */           if (LOGGABLE) {
/* 618 */               Log.d(TAG, "Running VineService action: " + actionCode);
/* 620 */               if (bundle.size() == 1 && rid != null) {
/* 621 */                   this.mFileLogger.write(" {} ({})\n", Integer.valueOf(actionCode), rid);
                        } else {
/* 623 */                   this.mFileLogger.write(" {} ({})\n{} \n", Integer.valueOf(actionCode), rid, bundle);
                        }
                    }
                    try {
/* 627 */               init();
/* 635 */               String key = bundle.getString("s_key");
/* 636 */               String accountName = bundle.getString(EXTRA_ACCOUNT_NAME);
/* 637 */               long sessionOwnerId = bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L);
/* 638 */               VineDatabaseHelper databaseHelper = VineDatabaseHelper.getDatabaseHelper(this);
/* 640 */               Context context = getApplicationContext();
/* 642 */               int statusCode = 0;
/* 643 */               String reasonPhrase = null;
/* 644 */               NetworkOperation op = null;
/* 645 */               VineError error = null;
/* 646 */               VineParserReader vp = null;
/* 647 */               ExecutionPair ep = null;
                        switch (actionCode) {
                            case 2:
/* 661 */                       VineLogin login = (VineLogin) bundle.getParcelable(EXTRA_LOGIN);
/* 662 */                       String password = bundle.getString(EXTRA_PASSWORD);
/* 663 */                       VineParserReader vp2 = VineParserReader.createLoginReader();
/* 664 */                       ArrayList<BasicNameValuePair> params3 = new ArrayList<>();
/* 665 */                       if (!TextUtils.isEmpty(login.username) && !TextUtils.isEmpty(password)) {
/* 666 */                           params3.add(new BasicNameValuePair("username", login.username));
/* 667 */                           params3.add(new BasicNameValuePair("password", password));
                                }
/* 669 */                       boolean isTwitter = (TextUtils.isEmpty(login.twitterToken) || TextUtils.isEmpty(login.twitterSecret) || login.twitterUserId <= 0) ? false : true;
/* 672 */                       if (isTwitter) {
/* 673 */                           params3.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, login.twitterToken));
/* 674 */                           params3.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, login.twitterSecret));
/* 675 */                           params3.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(login.twitterUserId)));
                                }
/* 679 */                       String lastPathSegment = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
/* 681 */                       StringBuilder url3 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", lastPathSegment);
/* 684 */                       NetworkOperation op2 = this.mNetOpFactory.createPostRequest(context, url3, params3, vp2, this.mApi).execute();
/* 687 */                       if (op2.isOK()) {
/* 688 */                           VineLogin vl = (VineLogin) vp2.getParsedObject();
/* 689 */                           vl.name = vl.username;
/* 690 */                           vl.username = login.username;
/* 691 */                           bundle.putString(EXTRA_ACCOUNT_NAME, vl.name);
/* 692 */                           bundle.putParcelable(EXTRA_LOGIN, vl);
/* 694 */                           FlurryUtils.trackLoginSuccess(isTwitter);
/* 697 */                           StringBuilder usersMeUrl = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
/* 699 */                           vp = VineParserReader.createParserReader(2);
/* 700 */                           op = this.mNetOpFactory.createBasicAuthGetRequest(context, usersMeUrl, this.mApi, vp, vl.key).execute();
/* 702 */                           if (op.isOK()) {
/* 703 */                               VineUser me = (VineUser) vp.getParsedObject();
/* 704 */                               bundle.putString("avatar_url", me.avatarUrl);
/* 705 */                               bundle.putString(EXTRA_DESCRIPTION, me.description);
                                    }
                                    break;
                                } else {
/* 708 */                           bundle2 = new Bundle();
/* 709 */                           bundle2.putInt(STATUS_CODE, op2.statusCode);
/* 710 */                           FlurryUtils.trackLoginFailure(isTwitter, op2.statusCode);
/* 711 */                           VineError error2 = (VineError) vp2.getParsedObject();
/* 712 */                           if (error2 != null) {
/* 713 */                               bundle2.putString(REASON_PHRASE, error2.message);
/* 714 */                               bundle.putInt("error_code", error2.errorCode);
                                    } else {
/* 716 */                               bundle2.putString(REASON_PHRASE, getString(C0257R.string.error_unknown));
                                    }
/* 718 */                           bundle2.putInt(EXECUTION_CODE, 1);
                                }
/* 3411 */                      return bundle2;
                            case 3:
/* 724 */                       databaseHelper.clearAllData();
/* 725 */                       this.mApi.getCacheStorage().clear();
                                break;
                            case 4:
/* 729 */                       String name = bundle.getString(EXTRA_ACCOUNT_NAME);
/* 730 */                       String email2 = bundle.getString("email");
/* 731 */                       String password2 = bundle.getString(EXTRA_PASSWORD);
/* 732 */                       String phone2 = bundle.getString(EXTRA_PHONE);
/* 733 */                       TwitterUser twitterUser = (TwitterUser) bundle.getParcelable(EXTRA_TWITTER_USER);
/* 735 */                       StringBuilder url4 = new StringBuilder(this.mApi.getBaseUrl()).append("/users");
/* 737 */                       VineParserReader vp3 = VineParserReader.createParserReader(5);
/* 739 */                       ArrayList<BasicNameValuePair> params4 = new ArrayList<>();
/* 741 */                       boolean twitterSignup = twitterUser != null;
/* 742 */                       String token = bundle.getString(EXTRA_KEY);
/* 743 */                       String secret = bundle.getString("secret");
/* 744 */                       String preinstallKey = CrossAnalytics.getInstance(this).get(CrossAnalytics.PRE_INSTALL_SIGN_UP);
/* 745 */                       if (twitterSignup) {
/* 746 */                           if (!TextUtils.isEmpty(twitterUser.name)) {
/* 747 */                               params4.add(new BasicNameValuePair("username", twitterUser.name));
                                    }
/* 749 */                           params4.add(new BasicNameValuePair("location", twitterUser.location));
/* 750 */                           params4.add(new BasicNameValuePair("description", twitterUser.description));
/* 751 */                           params4.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(twitterUser.userId)));
/* 753 */                           params4.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token));
/* 754 */                           params4.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret));
/* 755 */                           if (!TextUtils.isEmpty(phone2)) {
/* 756 */                               params4.add(new BasicNameValuePair("phoneNumber", phone2));
                                    }
                                } else {
/* 759 */                           params4.add(new BasicNameValuePair("username", name));
/* 760 */                           params4.add(new BasicNameValuePair("email", email2));
/* 761 */                           params4.add(new BasicNameValuePair("password", password2));
/* 762 */                           if (!TextUtils.isEmpty(phone2)) {
/* 763 */                               params4.add(new BasicNameValuePair("phoneNumber", phone2));
                                    }
                                }
/* 766 */                       params4.add(new BasicNameValuePair("authenticate", "true"));
/* 768 */                       if (preinstallKey != null) {
/* 769 */                           params4.add(new BasicNameValuePair(PARAM_PREINSTALL_TARGET, preinstallKey));
                                }
/* 772 */                       NetworkOperation op3 = this.mNetOpFactory.createPostRequest(context, url4, params4, vp3, this.mApi).execute();
/* 776 */                       if (op3.isOK()) {
/* 777 */                           FlurryUtils.onSignupSuccess(twitterSignup);
/* 778 */                           if (preinstallKey != null) {
/* 779 */                               FlurryUtils.onSignupWithPreinstallSuccess(preinstallKey);
/* 780 */                               CrossAnalytics.getInstance(this).clear(CrossAnalytics.PRE_INSTALL_SIGN_UP);
                                    }
/* 782 */                           VineLogin vl2 = (VineLogin) vp3.getParsedObject();
/* 783 */                           if (twitterSignup) {
/* 784 */                               vl2.twitterToken = token;
/* 785 */                               vl2.twitterSecret = secret;
/* 786 */                               vl2.twitterUserId = twitterUser.userId;
/* 787 */                               vl2.loginType = 2;
                                    } else {
/* 789 */                               vl2.username = email2;
                                    }
/* 791 */                           bundle.putParcelable(EXTRA_LOGIN, vl2);
/* 792 */                           bundle.putString(EXTRA_ACCOUNT_NAME, name);
/* 795 */                           StringBuilder usersMeUrl2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
/* 797 */                           vp = VineParserReader.createParserReader(2);
/* 799 */                           op = this.mNetOpFactory.createBasicAuthGetRequest(context, usersMeUrl2, this.mApi, vp, vl2.key).execute();
/* 802 */                           if (op.isOK()) {
/* 803 */                               VineUser me2 = (VineUser) vp.getParsedObject();
/* 804 */                               bundle.putString("avatar_url", me2.avatarUrl);
/* 805 */                               bundle.putString(EXTRA_DESCRIPTION, me2.description);
/* 806 */                               bundle.putString("s_key", vl2.key);
/* 807 */                               bundle.putLong(EXTRA_SESSION_OWNER_ID, vl2.userId);
                                    }
                                    break;
                                } else {
/* 810 */                           bundle2 = new Bundle();
/* 811 */                           bundle2.putInt(STATUS_CODE, op3.statusCode);
/* 812 */                           VineError error3 = (VineError) vp3.getParsedObject();
/* 813 */                           FlurryUtils.onSignupFailure(twitterSignup, error3, op3.statusCode);
/* 814 */                           if (error3 != null) {
/* 815 */                               bundle2.putString(REASON_PHRASE, error3.message);
                                    } else {
/* 817 */                               bundle2.putString(REASON_PHRASE, getString(C0257R.string.error_unknown));
                                    }
/* 819 */                           bundle2.putInt(EXECUTION_CODE, 1);
                                }
/* 3411 */                      return bundle2;
                            case 5:
/* 1039 */                      String tokenKey = bundle.getString(EXTRA_KEY);
/* 1040 */                      String tokenSecret = bundle.getString("secret");
/* 1041 */                      String callback = bundle.getString("uri");
/* 1042 */                      OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey(tokenKey).apiSecret(tokenSecret).callback(callback).build();
/* 1049 */                      Token token2 = service.getRequestToken();
/* 1050 */                      String authUrl = service.getAuthorizationUrl(token2);
/* 1051 */                      bundle.putString(EXTRA_LOAD_URL, authUrl);
                                break;
                            case 6:
/* 1055 */                      String username = bundle.getString("username");
/* 1056 */                      String tokenKey2 = bundle.getString(EXTRA_KEY);
/* 1057 */                      String tokenSecret2 = bundle.getString("secret");
/* 1058 */                      long userId = bundle.getLong(EXTRA_TWITTER_ID);
/* 1061 */                      if (this.mTwitter == null) {
/* 1062 */                          ConfigurationBuilder builder = new ConfigurationBuilder();
/* 1063 */                          builder.setOAuthConsumerKey(TwitterVineApp.API_KEY).setOAuthConsumerSecret(TwitterVineApp.API_SECRET).setOAuthAccessToken(tokenKey2).setOAuthAccessTokenSecret(tokenSecret2);
/* 1067 */                          this.mTwitter = new VineTwitterFactory(builder.build()).getInstance();
                                }
/* 1070 */                      if (!TextUtils.isEmpty(tokenKey2) && !TextUtils.isEmpty(tokenSecret2)) {
/* 1073 */                          String pathSegment = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
/* 1075 */                          StringBuilder url5 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", pathSegment, "twitter");
/* 1078 */                          ArrayList<BasicNameValuePair> params5 = new ArrayList<>(2);
/* 1080 */                          params5.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, tokenKey2));
/* 1081 */                          params5.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, tokenSecret2));
/* 1082 */                          vp = VineParserReader.createParserReader(5);
/* 1083 */                          op = this.mNetOpFactory.createPostRequest(context, url5, params5, vp, this.mApi).execute();
/* 1084 */                          if (op.isOK()) {
/* 1085 */                              bundle.putBoolean(EXTRA_ACCOUNT_EXISTS, true);
/* 1089 */                              VineLogin vl3 = (VineLogin) vp.getParsedObject();
/* 1090 */                              vl3.twitterUsername = username;
/* 1091 */                              vl3.twitterToken = tokenKey2;
/* 1092 */                              vl3.twitterSecret = tokenSecret2;
/* 1093 */                              vl3.twitterUserId = userId;
/* 1094 */                              vl3.loginType = 2;
/* 1095 */                              bundle.putParcelable(EXTRA_LOGIN, vl3);
/* 1098 */                              String usersMe = this.mApi.getBaseUrl() + "/users/me";
/* 1099 */                              vp = VineParserReader.createParserReader(2);
/* 1100 */                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, new StringBuilder(usersMe), this.mApi, vp, vl3.key).execute();
/* 1102 */                              if (op.isOK()) {
/* 1103 */                                  VineUser user = (VineUser) vp.getParsedObject();
/* 1104 */                                  bundle.putParcelable("user", user);
                                        }
                                    } else {
/* 1109 */                              error = (VineError) vp.getParsedObject();
/* 1110 */                              if (error == null) {
/* 1111 */                                  reasonPhrase = getString(C0257R.string.error_server);
                                        } else {
/* 1114 */                                  SLog.m448d("Got error: {} {}", Integer.valueOf(error.errorCode), error.message);
/* 1116 */                                  int code = error.errorCode;
/* 1117 */                                  if (VineKnownErrors.BAD_CREDENTIALS.code == code || VineKnownErrors.RECORD_DOES_NOT_EXIST.code == code || VineKnownErrors.ACCOUNT_DEACTIVATED.code == code) {
/* 1120 */                                      VineLogin login2 = new VineLogin(null, username, tokenKey2, tokenSecret2, userId, null);
/* 1122 */                                      bundle.putParcelable(EXTRA_LOGIN, login2);
/* 1123 */                                      bundle.putBoolean(EXTRA_ACCOUNT_EXISTS, false);
/* 1124 */                                      bundle.putInt("error_code", error.errorCode);
                                            } else {
/* 1126 */                                      reasonPhrase = error.message;
                                            }
                                        }
                                    }
                                }
                                break;
                            case 7:
/* 1133 */                      VineLogin v = (VineLogin) bundle.getParcelable(EXTRA_LOGIN);
/* 1134 */                      OAuthService service2 = new ServiceBuilder().provider(TwitterApi.class).apiKey(TwitterVineApp.API_KEY).apiSecret(TwitterVineApp.API_SECRET).build();
/* 1140 */                      String url6 = this.mApi.getTwitterUrl() + "/users/show.json?id=" + v.twitterUserId;
/* 1141 */                      OAuthRequest request = new OAuthRequest(Verb.GET, url6);
/* 1142 */                      Token token3 = new Token(v.twitterToken, v.twitterSecret);
/* 1143 */                      service2.signRequest(token3, request);
                                try {
/* 1146 */                          Response response = request.send();
/* 1148 */                          if (response.isSuccessful()) {
                                        try {
/* 1150 */                                  JsonParser p = new JsonFactory().createJsonParser(response.getBody());
/* 1151 */                                  p.nextToken();
/* 1152 */                                  TwitterUser tUser = VineParsers.parseTwitterUser(p);
/* 1154 */                                  bundle.putParcelable(EXTRA_TWITTER_USER, tUser);
                                        } catch (IOException e2) {
/* 1156 */                                  if (LOGGABLE) {
/* 1157 */                                      SLog.m452d("Failed to parse.", (Throwable) e2);
                                            }
                                        }
                                    }
                                } catch (OAuthException oAuthException) {
/* 1163 */                          SLog.m452d("Failed to connect.", (Throwable) oAuthException);
                                }
                                break;
                            case 8:
/* 1185 */                      String username2 = bundle.getString("username");
/* 1186 */                      String password3 = bundle.getString(EXTRA_PASSWORD);
/* 1188 */                      ConfigurationBuilder builder2 = new ConfigurationBuilder();
/* 1189 */                      builder2.setOAuthConsumerKey(TwitterVineApp.API_KEY);
/* 1190 */                      builder2.setOAuthConsumerSecret(TwitterVineApp.API_SECRET);
/* 1192 */                      Twitter twitter = new VineTwitterFactory(builder2.build()).getInstance(new BasicAuthorization(username2, password3));
/* 1194 */                      Resources res = context.getResources();
                                try {
                                    try {
/* 1197 */                              AccessToken aToken = twitter.getOAuthAccessToken();
/* 1198 */                              String screenName = aToken.getScreenName();
/* 1199 */                              String token4 = aToken.getToken();
/* 1200 */                              String tokenSecret3 = aToken.getTokenSecret();
/* 1202 */                              if (screenName != null && token4 != null && tokenSecret3 != null) {
/* 1203 */                                  VineLogin login3 = new VineLogin(null, aToken.getScreenName(), token4, aToken.getTokenSecret(), aToken.getUserId(), null);
/* 1205 */                                  bundle.putParcelable(EXTRA_LOGIN, login3);
/* 1206 */                                  statusCode = 200;
                                        } else {
/* 1208 */                                  statusCode = 500;
/* 1209 */                                  reasonPhrase = res.getString(C0257R.string.error_xauth);
/* 1210 */                                  CrashUtil.log("Twitter access_token response contained empty params. screenName={},token={}, tokenSecret={}", screenName, token4, tokenSecret3);
                                        }
/* 1230 */                              bundle.putString(EXTRA_PASSWORD, null);
                                    } catch (TwitterException e3) {
/* 1214 */                              if (e3.getStatusCode() == 401) {
/* 1215 */                                  statusCode = 401;
/* 1216 */                                  if (Util.isXauth2FactorError(e3)) {
/* 1217 */                                      reasonPhrase = res.getString(C0257R.string.error_auth_2_factor_error);
                                            } else {
/* 1219 */                                      reasonPhrase = res.getString(C0257R.string.error_auth_username_pass);
                                            }
                                        } else if (e3.isCausedByNetworkIssue()) {
/* 1222 */                                  statusCode = 500;
/* 1223 */                                  reasonPhrase = res.getString(C0257R.string.error_auth_check_internet_connection);
                                        } else {
/* 1225 */                                  statusCode = 500;
/* 1226 */                                  reasonPhrase = res.getString(C0257R.string.error_xauth);
                                        }
/* 1230 */                              bundle.putString(EXTRA_PASSWORD, null);
                                    }
                                } catch (Throwable th) {
                                    bundle.putString(EXTRA_PASSWORD, null);
                                    throw th;
                                }
                                break;
                            case 9:
/* 1236 */                      FlurryUtils.trackResetPassword();
/* 1237 */                      String email3 = bundle.getString("email");
/* 1238 */                      StringBuilder url7 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "forgotPassword");
/* 1240 */                      VineAPI.addParam(url7, "email", email3);
/* 1241 */                      vp = VineParserReader.createParserReader(1);
/* 1242 */                      op = this.mNetOpFactory.createGetRequest(context, url7, vp, this.mApi).execute();
                                break;
                            case 10:
/* 1578 */                      op = getUsersMe(bundle);
/* 1579 */                      vp = (VineParserReader) op.getReader();
/* 1580 */                      FlurryUtils.trackGetUser(true);
                                break;
                            case 11:
/* 1249 */                      long postId = bundle.getLong("post_id", 0L);
/* 1250 */                      int page = bundle.getInt("page", 1);
/* 1251 */                      long anchor = bundle.getLong(EXTRA_ANCHOR, 0L);
/* 1253 */                      StringBuilder url8 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "likes");
/* 1256 */                      VineAPI.addParam(url8, "page", page);
/* 1257 */                      VineAPI.addAnchor(url8, anchor);
/* 1258 */                      VineAPI.addParam(url8, "size", 20);
/* 1260 */                      vp = VineParserReader.createParserReader(11);
/* 1261 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url8, this.mApi, vp).execute();
/* 1265 */                      if (op.isOK()) {
/* 1266 */                          VinePagedData<VineLike> likes = (VinePagedData) vp.getParsedObject();
/* 1267 */                          databaseHelper.mergeLikes(likes.items, postId, likes.nextPage, likes.previousPage);
/* 1268 */                          bundle.putInt("count", likes.count);
/* 1269 */                          bundle.putInt("next_page", likes.nextPage);
/* 1270 */                          bundle.putInt("previous_page", likes.previousPage);
/* 1271 */                          bundle.putLong(EXTRA_ANCHOR, likes.anchor);
/* 1273 */                          ArrayList<VineUser> users4 = new ArrayList<>();
/* 1274 */                          Iterator i$ = likes.items.iterator();
                                    for (VineLike like : likes.items) {
/* 1275 */                              users4.add(like.user);
                                    }
/* 1277 */                          bundle.putParcelableArrayList("users", users4);
/* 1279 */                          if (likes.nextPage <= 0) {
/* 1280 */                              databaseHelper.markLastUser(5, String.valueOf(postId));
                                    }
                                }
                                break;
                            case 12:
/* 1513 */                      op = getComments(bundle.getLong("post_id", 0L), bundle.getInt("page", 1), bundle.getLong(EXTRA_ANCHOR, 0L), bundle.getInt("size", 20), key, bundle, databaseHelper);
/* 1518 */                      vp = (VineParserReader) op.getReader();
/* 1519 */                      FlurryUtils.trackGetComments();
                                break;
                            case 13:
/* 1478 */                      int type = bundle.getInt("type", -1);
/* 1479 */                      StringBuilder url9 = generatePostFetchUrl(type, bundle.getLong(EXTRA_PROFILE_ID), bundle.getInt("page", 1), bundle, false);
/* 1480 */                      op = getPosts(url9, type, bundle.getInt("size", 10), databaseHelper, bundle.getLong(EXTRA_ANCHOR, 0L), bundle, false);
/* 1481 */                      vp = (VineParserReader) op.getReader();
                                break;
                            case 14:
/* 1540 */                      ArrayList<VineEntity> entities = bundle.getParcelableArrayList("entities");
/* 1541 */                      op = postComment(bundle.getLong("post_id"), bundle.getLong("repost_id"), bundle.getLong("user_id"), bundle.getString("username"), bundle.getString("comment"), bundle.getString("avatar_url"), entities, databaseHelper, bundle);
/* 1545 */                      vp = (VineParserReader) op.getReader();
/* 1546 */                      FlurryUtils.trackPostComment();
                                break;
                            case 15:
/* 1584 */                      String twitterKey = bundle.getString(EXTRA_KEY);
/* 1585 */                      String twitterSecret = bundle.getString("secret");
/* 1587 */                      StringBuilder url10 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, "twitter");
/* 1594 */                      vp = VineParserReader.createParserReader(3);
/* 1596 */                      ArrayList<BasicNameValuePair> params6 = new ArrayList<>(2);
/* 1597 */                      params6.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, twitterKey));
/* 1598 */                      params6.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, twitterSecret));
/* 1600 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url10, this.mApi, params6, vp).execute();
/* 1603 */                      if (op.isOK() && (users2 = (ArrayList) vp.getParsedObject()) != null && !users2.isEmpty()) {
/* 1606 */                          databaseHelper.mergeUsers(users2, 6, String.valueOf(sessionOwnerId), 0, 0, null);
/* 1608 */                          bundle.putInt("count", users2.size());
/* 1609 */                          bundle.putParcelableArrayList("users", users2);
                                }
                                break;
                            case 16:
/* 1616 */                      String addressBook = null;
                                try {
/* 1618 */                          addressBook = Util.getAddressBookJson(getContentResolver());
                                } catch (IOException e4) {
/* 1620 */                          if (LOGGABLE) {
/* 1621 */                              e4.printStackTrace();
                                    }
                                }
/* 1625 */                      if (addressBook == null && 0 != 0) {
/* 1626 */                          op.statusCode = 1000;
/* 1627 */                          error = new VineError(1000, "Could not create json.");
                                } else {
/* 1631 */                          StringBuilder url11 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, VineAPI.RESOURCE_CONTACTS);
/* 1638 */                          vp = VineParserReader.createParserReader(3);
/* 1640 */                          ArrayList<BasicNameValuePair> params7 = new ArrayList<>();
/* 1641 */                          params7.add(new BasicNameValuePair(PARAM_ADDRESS_BOOK, addressBook));
/* 1642 */                          addCountryParam(params7);
/* 1644 */                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url11, this.mApi, params7, vp).execute();
/* 1648 */                          if (op.isOK() && (users = (ArrayList) vp.getParsedObject()) != null && !users.isEmpty()) {
/* 1651 */                              databaseHelper.mergeUsers(users, 7, String.valueOf(sessionOwnerId), 0, 0, null);
/* 1653 */                              bundle.putInt("count", users.size());
/* 1654 */                              bundle.putParcelableArrayList("users", users);
                                    }
                                }
                                break;
                            case 17:
/* 1696 */                      databaseHelper.removeUsersByGroup(bundle.getInt("type"));
                                break;
                            case 18:
/* 1700 */                      long userIdToFollow = bundle.getLong(EXTRA_FOLLOW_ID);
/* 1701 */                      boolean notify = bundle.getBoolean("notify");
/* 1702 */                      StringBuilder url12 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userIdToFollow), VineAPI.RESOURCE_FOLLOWERS);
/* 1705 */                      vp = VineParserReader.createParserReader(1);
/* 1707 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url12, this.mApi, null, vp).execute();
/* 1711 */                      if (op.isOK()) {
/* 1712 */                          databaseHelper.updateUserFollowingFlag(userIdToFollow, true);
/* 1713 */                          databaseHelper.addFollow(userIdToFollow, sessionOwnerId, 0L, notify);
/* 1714 */                          databaseHelper.markLastUser(1, String.valueOf(sessionOwnerId), "order_id ASC");
                                }
                                break;
                            case 19:
/* 1721 */                      long userToUnfollow = bundle.getLong(EXTRA_FOLLOW_ID);
/* 1722 */                      long currentProfileId = bundle.getLong(EXTRA_PROFILE_ID);
/* 1723 */                      long activeUserId = bundle.getLong("user_id");
/* 1724 */                      boolean notify2 = bundle.getBoolean("notify");
/* 1725 */                      StringBuilder url13 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userToUnfollow), VineAPI.RESOURCE_FOLLOWERS);
/* 1728 */                      vp = VineParserReader.createParserReader(1);
/* 1730 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url13, this.mApi, vp).execute();
/* 1734 */                      if (op.isOK()) {
/* 1735 */                          boolean deleteRow = currentProfileId == activeUserId;
/* 1736 */                          databaseHelper.updateUserFollowingFlag(userToUnfollow, false);
/* 1737 */                          databaseHelper.removeFollow(userToUnfollow, deleteRow, notify2);
/* 1738 */                          databaseHelper.markLastUser(1, String.valueOf(sessionOwnerId), "order_id ASC");
                                }
                                break;
                            case 20:
/* 1888 */                      ep = fetchAcitivity(sessionOwnerId, context, bundle);
                                break;
                            case 21:
/* 1896 */                      long userId2 = bundle.getLong("user_id");
/* 1897 */                      FlurryUtils.trackGetUser(userId2 != sessionOwnerId);
/* 1898 */                      StringBuilder url14 = getUserUrl(userId2);
/* 1900 */                      vp = VineParserReader.createParserReader(2);
/* 1901 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url14, this.mApi, vp);
/* 1904 */                      assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 1905 */                      op.execute();
/* 1906 */                      if (op.isOK()) {
/* 1907 */                          VineUser user2 = (VineUser) vp.getParsedObject();
/* 1908 */                          bundle.putParcelable("user", user2);
                                }
                                break;
                            case 22:
                            case 23:
/* 1288 */                      int userGroupsType = 2;
/* 1289 */                      String resourceFollowType = VineAPI.RESOURCE_FOLLOWERS;
/* 1290 */                      if (actionCode == 23) {
/* 1291 */                          userGroupsType = 1;
/* 1292 */                          resourceFollowType = "following";
                                }
/* 1294 */                      long userId3 = bundle.getLong(EXTRA_PROFILE_ID, 0L);
/* 1295 */                      int page2 = bundle.getInt("page", 1);
/* 1296 */                      long anchor2 = bundle.getLong(EXTRA_ANCHOR, 0L);
                                if (userId3 > 0) {
/* 1299 */                          StringBuilder url15 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId3), resourceFollowType);
/* 1301 */                          VineAPI.addParam(url15, "page", page2);
/* 1302 */                          VineAPI.addAnchor(url15, anchor2);
/* 1304 */                          VineAPI.addParam(url15, "size", 20);
/* 1305 */                          vp = VineParserReader.createParserReader(3);
/* 1307 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url15, this.mApi, vp).execute();
/* 1310 */                          if (op.isOK()) {
/* 1311 */                              VinePagedData<VineUser> users5 = (VinePagedData) vp.getParsedObject();
/* 1312 */                              if (users5.items != null) {
/* 1313 */                                  LongSparseArray<Long> orderMap2 = new LongSparseArray<>(users5.items.size());
                                            for (VineUser u : users5.items) {
/* 1316 */                                      orderMap2.put(u.userId, Long.valueOf(u.orderId));
                                            }
/* 1318 */                                  databaseHelper.mergeUsers(users5.items, userGroupsType, String.valueOf(userId3), users5.nextPage, users5.previousPage, orderMap2);
/* 1321 */                                  bundle.putInt("count", users5.count);
/* 1322 */                                  bundle.putInt("next_page", users5.nextPage);
/* 1323 */                                  bundle.putInt("previous_page", users5.previousPage);
/* 1324 */                                  bundle.putLong(EXTRA_ANCHOR, users5.anchor);
/* 1325 */                                  bundle.putParcelableArrayList("users", users5.items);
                                        }
/* 1327 */                              if (users5.nextPage <= 0) {
/* 1328 */                                  databaseHelper.markLastUser(userGroupsType, String.valueOf(userId3), "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 24:
/* 1761 */                      long postId2 = bundle.getLong("post_id");
/* 1762 */                      boolean notify3 = bundle.getBoolean("notify", true);
/* 1763 */                      StringBuilder url16 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId2), "likes");
/* 1765 */                      long repostId = bundle.getLong("repost_id");
                                if (repostId > 0) {
/* 1767 */                          VineAPI.addParam(url16, PARAM_REPOST_ID, repostId);
                                }
/* 1769 */                      vp = VineParserReader.createParserReader(10);
/* 1770 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url16, this.mApi, null, vp).execute();
/* 1773 */                      if (op.isOK()) {
/* 1774 */                          Long likeId = (Long) vp.getParsedObject();
/* 1776 */                          long meUserId = bundle.getLong("user_id");
/* 1777 */                          String username3 = bundle.getString("username");
/* 1779 */                          VineLike like = new VineLike();
/* 1780 */                          like.likeId = likeId.longValue();
/* 1781 */                          like.userId = meUserId;
/* 1782 */                          like.username = username3;
/* 1783 */                          like.postId = postId2;
/* 1785 */                          databaseHelper.addLike(like, notify3);
                                }
                                break;
                            case 25:
/* 1790 */                      long postId3 = bundle.getLong("post_id");
/* 1791 */                      long repostId2 = bundle.getLong("repost_id");
/* 1792 */                      boolean notify4 = bundle.getBoolean("notify", true);
/* 1793 */                      StringBuilder url17 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId3), "likes");
                                if (repostId2 > 0) {
/* 1796 */                          VineAPI.addParam(url17, PARAM_REPOST_ID, repostId2);
                                }
/* 1798 */                      vp = VineParserReader.createParserReader(10);
/* 1799 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url17, this.mApi, vp).execute();
/* 1802 */                      if (op.isOK()) {
/* 1803 */                          databaseHelper.removeLike(postId3, sessionOwnerId, notify4);
                                }
                                break;
                            case 26:
/* 1925 */                      Uri avatarUri = (Uri) bundle.getParcelable("uri");
/* 1926 */                      String f = null;
/* 1927 */                      if (avatarUri != null) {
                                    try {
/* 1929 */                              f = this.mMediaUtility.getPhotoUri(avatarUri, context);
/* 1937 */                              StringBuilder url18 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                                        if (avatarUri == null) {
                                            i2 = 5;
                                        } else {
/* 1940 */                                  i2 = 6;
                                        }
/* 1940 */                              params = new ArrayList<>(i2);
/* 1943 */                              params.add(new BasicNameValuePair("username", bundle.getString(EXTRA_ACCOUNT_NAME)));
/* 1944 */                              params.add(new BasicNameValuePair("description", bundle.getString(EXTRA_DESCRIPTION)));
/* 1946 */                              params.add(new BasicNameValuePair("location", bundle.getString("location")));
/* 1949 */                              if (avatarUri == null && TextUtils.isEmpty(avatarUri.toString())) {
/* 1950 */                                  finalAvatarUrl = "";
                                        } else {
/* 1952 */                                  finalAvatarUrl = f;
                                        }
/* 1955 */                              if (avatarUri != null) {
/* 1956 */                                  params.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl));
                                        }
/* 1959 */                              email = bundle.getString("email");
/* 1960 */                              if (!TextUtils.isEmpty(email)) {
/* 1961 */                                  params.add(new BasicNameValuePair("email", email));
                                        }
/* 1964 */                              phone = bundle.getString(EXTRA_PHONE);
/* 1965 */                              if (!TextUtils.isEmpty(phone)) {
/* 1966 */                                  params.add(new BasicNameValuePair("phoneNumber", bundle.getString(EXTRA_PHONE)));
                                        }
/* 1969 */                              if (bundle.containsKey(EXTRA_COLOR_INT)) {
/* 1970 */                                  color = bundle.getInt(EXTRA_COLOR_INT);
/* 1971 */                                  if (color > 0) {
/* 1972 */                                      params.add(new BasicNameValuePair("profileBackground", String.valueOf(bundle.getInt(EXTRA_COLOR_INT))));
                                            }
                                        }
/* 1976 */                              vp = VineParserReader.createParserReader(2);
/* 1977 */                              op = this.mNetOpFactory.createBasicAuthPutRequest(context, url18, this.mApi, params, vp).execute();
/* 1980 */                              if (op.isOK()) {
/* 1981 */                                  if (avatarUri == null) {
/* 1982 */                                      bundle.putString("avatar_url", "");
                                            } else {
/* 1984 */                                      bundle.putString("avatar_url", finalAvatarUrl);
                                            }
                                        }
                                    } catch (Exception e5) {
/* 1931 */                              statusCode = 900;
/* 1932 */                              reasonPhrase = e5.getMessage();
                                    }
                                } else {
/* 1937 */                          StringBuilder url19 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
                                    if (avatarUri == null) {
                                        i2 = 5;
                                    } else {
/* 1940 */                              i2 = 6;
                                    }
/* 1940 */                          params = new ArrayList<>(i2);
/* 1943 */                          params.add(new BasicNameValuePair("username", bundle.getString(EXTRA_ACCOUNT_NAME)));
/* 1944 */                          params.add(new BasicNameValuePair("description", bundle.getString(EXTRA_DESCRIPTION)));
/* 1946 */                          params.add(new BasicNameValuePair("location", bundle.getString("location")));
/* 1949 */                          if (avatarUri == null) {
/* 1952 */                              finalAvatarUrl = f;
                                    } else {
/* 1952 */                              finalAvatarUrl = f;
                                    }
/* 1955 */                          if (avatarUri != null) {
/* 1956 */                              params.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl));
                                    }
/* 1959 */                          email = bundle.getString("email");
/* 1960 */                          if (!TextUtils.isEmpty(email)) {
/* 1961 */                              params.add(new BasicNameValuePair("email", email));
                                    }
/* 1964 */                          phone = bundle.getString(EXTRA_PHONE);
/* 1965 */                          if (!TextUtils.isEmpty(phone)) {
/* 1966 */                              params.add(new BasicNameValuePair("phoneNumber", bundle.getString(EXTRA_PHONE)));
                                    }
/* 1969 */                          if (bundle.containsKey(EXTRA_COLOR_INT)) {
/* 1970 */                              color = bundle.getInt(EXTRA_COLOR_INT);
/* 1971 */                              if (color > 0) {
/* 1972 */                                  params.add(new BasicNameValuePair("profileBackground", String.valueOf(bundle.getInt(EXTRA_COLOR_INT))));
                                        }
                                    }
/* 1976 */                          vp = VineParserReader.createParserReader(2);
/* 1977 */                          op = this.mNetOpFactory.createBasicAuthPutRequest(context, url19, this.mApi, params, vp).execute();
/* 1980 */                          if (op.isOK()) {
/* 1981 */                              if (avatarUri == null) {
/* 1982 */                                  bundle.putString("avatar_url", "");
                                        } else {
/* 1984 */                                  bundle.putString("avatar_url", finalAvatarUrl);
                                        }
                                    }
                                }
                                break;
                            case 27:
/* 1990 */                      FlurryUtils.trackReportPost();
/* 1991 */                      long postId4 = bundle.getLong("post_id");
/* 1992 */                      String event = bundle.getString("event");
/* 1993 */                      ArrayList<BasicNameValuePair> params8 = null;
/* 1994 */                      if (!TextUtils.isEmpty(event)) {
/* 1995 */                          params8 = new ArrayList<>();
/* 1996 */                          params8.add(new BasicNameValuePair("code", event));
                                }
/* 1998 */                      StringBuilder url20 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId4), VineAPI.RESOURCE_COMPLAINTS);
/* 2000 */                      vp = VineParserReader.createParserReader(1);
/* 2002 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url20, this.mApi, params8, vp).execute();
                                break;
                            case 28:
/* 1485 */                      long postId5 = bundle.getLong("post_id");
                                if (postId5 > 0) {
/* 1487 */                          StringBuilder url21 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "timelines", "posts", Long.valueOf(postId5));
/* 1489 */                          limitCommentAndLikesIfNeeded(url21);
/* 1490 */                          vp = VineParserReader.createParserReader(8);
/* 1491 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url21, this.mApi, vp);
/* 1492 */                          assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 1493 */                          op.execute();
/* 1494 */                          if (op.isOK()) {
/* 1495 */                              VinePagedData<VinePost> response2 = (VinePagedData) vp.getParsedObject();
                                        try {
/* 1497 */                                  databaseHelper.mergePosts(response2.items, 7, "", 0, 0, 0, 0L, true);
/* 1500 */                                  if (response2 != null && response2.items != null) {
/* 1501 */                                      bundle.putParcelable("post", (Parcelable) response2.items.get(0));
                                            }
                                        } catch (IOException e6) {
/* 1504 */                                  if (LOGGABLE) {
/* 1505 */                                      e6.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                break;
                            case 29:
/* 2008 */                      databaseHelper.cleanUp(bundle.getLong(EXTRA_ANCHOR));
                                break;
                            case 30:
/* 1550 */                      op = deleteComment(bundle.getLong("post_id"), bundle.getLong("repost_id"), bundle.getString("comment_id"), databaseHelper);
/* 1552 */                      vp = (VineParserReader) op.getReader();
/* 1553 */                      FlurryUtils.trackDeleteComment();
                                break;
                            case 31:
/* 2012 */                      long postId6 = bundle.getLong("post_id");
/* 2013 */                      StringBuilder url22 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId6));
/* 2015 */                      vp = VineParserReader.createParserReader(1);
/* 2016 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url22, this.mApi, vp).execute();
/* 2019 */                      if (op.isOK()) {
/* 2020 */                          databaseHelper.deletePost(postId6);
                                }
                                break;
                            case 32:
/* 2025 */                      FlurryUtils.trackConnectTwitter();
/* 2026 */                      String token5 = bundle.getString(EXTRA_KEY);
/* 2027 */                      String secret2 = bundle.getString("secret");
/* 2028 */                      long userId4 = bundle.getLong("user_id");
/* 2029 */                      StringBuilder url23 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 2031 */                      ArrayList<BasicNameValuePair> params9 = new ArrayList<>(3);
/* 2032 */                      params9.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token5));
/* 2033 */                      params9.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret2));
/* 2034 */                      params9.add(new BasicNameValuePair(PARAM_TWITTER_ID, String.valueOf(userId4)));
/* 2035 */                      vp = VineParserReader.createParserReader(1);
/* 2037 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url23, this.mApi, params9, vp).execute();
                                break;
                            case 33:
/* 2042 */                      FlurryUtils.trackDisConnectTwitter();
/* 2043 */                      StringBuilder url24 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 2045 */                      ArrayList<BasicNameValuePair> params10 = new ArrayList<>(3);
/* 2046 */                      params10.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, ""));
/* 2047 */                      params10.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, ""));
/* 2048 */                      params10.add(new BasicNameValuePair(PARAM_TWITTER_ID, ""));
/* 2049 */                      vp = VineParserReader.createParserReader(1);
/* 2050 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url24, this.mApi, params10, vp).execute();
                                break;
                            case 34:
/* 2056 */                      databaseHelper.removePostsFromGroupByUser(bundle.getLong("user_id"), 1, String.valueOf(sessionOwnerId));
                                break;
                            case 35:
/* 2061 */                      op = getClientProfile();
                                break;
                            case 36:
/* 2065 */                      String postShareId = bundle.getString("post_share_id");
/* 2066 */                      StringBuilder url25 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "timelines", "posts", VineAPI.RESOURCE_SHARED, postShareId);
/* 2069 */                      vp = VineParserReader.createParserReader(8);
/* 2070 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url25, this.mApi, vp).execute();
/* 2074 */                      if (op.isOK()) {
/* 2075 */                          VinePagedData<VinePost> response3 = (VinePagedData) vp.getParsedObject();
/* 2076 */                          ArrayList<VinePost> arrayList = response3.items;
/* 2077 */                          if (!arrayList.isEmpty()) {
/* 2078 */                              bundle.putLong("post_id", ((VinePost) arrayList.get(0)).postId);
                                    }
                                }
                                break;
                            case 37:
/* 2084 */                      FlurryUtils.trackConnectFacebook();
/* 2085 */                      StringBuilder url26 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 2088 */                      ArrayList<BasicNameValuePair> params11 = new ArrayList<>(3);
/* 2089 */                      params11.add(new BasicNameValuePair(PARAM_FACEBOOK_AUTH_TOKEN, bundle.getString(EXTRA_FACEBOOK_TOKEN)));
/* 2092 */                      vp = VineParserReader.createParserReader(1);
/* 2093 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url26, this.mApi, params11, vp).execute();
/* 2097 */                      SLog.m447d("Send facebook token result {}.", op != null ? Integer.valueOf(op.statusCode) : op);
/* 2099 */                      if (op != null && op.isOK()) {
/* 2100 */                          AppController.setPendingFacebookToken(this, null);
                                }
                                break;
                            case 38:
/* 2105 */                      StringBuilder url27 = new StringBuilder(BuildUtil.getStatusUrl(context));
/* 2106 */                      vp = VineParserReader.createParserReader(15);
/* 2107 */                      op = this.mNetOpFactory.createGetRequest(context, url27, vp, this.mApi).execute();
/* 2108 */                      if (op.isOK()) {
/* 2109 */                          ServerStatus ss = (ServerStatus) vp.getParsedObject();
/* 2110 */                          bundle.putParcelable(EXTRA_SERVER_STATUS, ss);
                                }
                                break;
                            case 39:
                            case 99:
/* 2588 */                      String query = bundle.getString(EXTRA_QUERY);
/* 2589 */                      String encodedQuery = VineAPI.encode(query.trim());
/* 2590 */                      StringBuilder url28 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "search", encodedQuery);
                                switch (actionCode) {
                                    case 39:
/* 2599 */                              VineAPI.addParam(url28, PARAM_SEARCH_TERM, VineEntity.TYPE_MENTION);
                                        break;
                                    case 99:
/* 2595 */                              VineAPI.addParam(url28, PARAM_SEARCH_TERM, "message");
                                        break;
                                }
/* 2604 */                      vp = VineParserReader.createParserReader(3);
/* 2605 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url28, this.mApi, vp, key).execute();
/* 2609 */                      if (op.isOK()) {
/* 2610 */                          VinePagedData<VineUser> users6 = (VinePagedData) vp.getParsedObject();
/* 2611 */                          if (users6.items != null) {
/* 2612 */                              databaseHelper.mergeUsers(users6.items, 0, null, 0, 0, null);
/* 2613 */                              bundle.putString(EXTRA_QUERY, query);
/* 2614 */                              bundle.putParcelableArrayList("users", users6.items);
                                    }
                                }
                                break;
                            case 40:
/* 2620 */                      String query2 = bundle.getString(EXTRA_QUERY);
/* 2621 */                      String encodedQuery2 = VineAPI.encode(query2);
/* 2622 */                      StringBuilder url29 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "tags", "search", encodedQuery2);
/* 2625 */                      vp = VineParserReader.createParserReader(14);
/* 2626 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url29, this.mApi, vp, key).execute();
/* 2630 */                      if (op.isOK()) {
/* 2631 */                          VinePagedData<VineTag> tags = (VinePagedData) vp.getParsedObject();
/* 2632 */                          if (tags.items != null) {
/* 2633 */                              bundle.putString(EXTRA_QUERY, query2);
/* 2634 */                              bundle.putParcelableArrayList("tags", tags.items);
                                    }
                                }
                                break;
                            case 41:
/* 2115 */                      String query3 = bundle.getString(EXTRA_QUERY);
/* 2116 */                      int page3 = bundle.getInt("page");
/* 2118 */                      if (!TextUtils.isEmpty(query3)) {
/* 2120 */                          FlurryUtils.trackSearchUsers();
/* 2122 */                          StringBuilder url30 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "search", query3);
/* 2124 */                          VineAPI.addParam(url30, "page", page3);
/* 2125 */                          VineAPI.addParam(url30, "size", 50);
/* 2127 */                          vp = VineParserReader.createParserReader(3);
/* 2128 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url30, this.mApi, vp, key).execute();
/* 2132 */                          if (op.isOK()) {
/* 2133 */                              VinePagedData<VineUser> users7 = (VinePagedData) vp.getParsedObject();
/* 2134 */                              if (users7.items != null) {
/* 2135 */                                  bundle.putInt("count", users7.count);
/* 2136 */                                  bundle.putInt("next_page", users7.nextPage);
/* 2137 */                                  bundle.putInt("previous_page", users7.previousPage);
/* 2138 */                                  bundle.putParcelableArrayList("users", users7.items);
                                        }
/* 2140 */                              if (users7.nextPage <= 0) {
/* 2141 */                                  databaseHelper.markLastUser(8, null, "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 42:
/* 2172 */                      String query4 = bundle.getString(EXTRA_QUERY);
/* 2173 */                      int page4 = bundle.getInt("page");
/* 2175 */                      if (!TextUtils.isEmpty(query4)) {
/* 2177 */                          FlurryUtils.trackSearchTags();
/* 2179 */                          StringBuilder url31 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "tags", "search", query4);
/* 2181 */                          VineAPI.addParam(url31, "page", page4);
/* 2182 */                          VineAPI.addParam(url31, "size", 50);
/* 2184 */                          vp = VineParserReader.createParserReader(14);
/* 2185 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url31, this.mApi, vp, key).execute();
/* 2189 */                          if (op.isOK()) {
/* 2190 */                              VinePagedData<VineTag> tags2 = (VinePagedData) vp.getParsedObject();
/* 2191 */                              if (tags2.items != null) {
/* 2192 */                                  databaseHelper.mergeSearchedTags(tags2.items, tags2.nextPage, tags2.previousPage);
/* 2193 */                                  bundle.putInt("count", tags2.count);
/* 2194 */                                  bundle.putInt("next_page", tags2.nextPage);
/* 2195 */                                  bundle.putInt("previous_page", tags2.previousPage);
/* 2196 */                                  bundle.putParcelableArrayList("tags", tags2.items);
                                        }
/* 2198 */                              if (tags2.nextPage <= 0) {
/* 2199 */                                  databaseHelper.markLastTag();
                                        }
                                    }
                                }
                                break;
                            case 43:
/* 2148 */                      Collection<VinePost> posts = bundle.getParcelableArrayList("posts");
/* 2149 */                      int groupType = bundle.getInt("type");
/* 2150 */                      String tag = bundle.getString("tag_name");
/* 2151 */                      int pageType = bundle.getInt(EXTRA_PAGE_TYPE);
/* 2152 */                      int next = bundle.getInt("next_page");
/* 2153 */                      int previous = bundle.getInt("previous_page");
/* 2154 */                      long anchor3 = bundle.getLong(EXTRA_ANCHOR);
/* 2155 */                      boolean userInitiated = bundle.getBoolean(EXTRA_USER_INITIATED);
/* 2156 */                      if (posts != null) {
                                    try {
/* 2158 */                              databaseHelper.mergePosts(posts, groupType, tag, pageType, next, previous, anchor3, userInitiated);
/* 2160 */                              if (next <= 0) {
/* 2161 */                                  databaseHelper.markLastPost(groupType, tag);
                                        }
                                    } catch (IOException e7) {
/* 2164 */                              if (LOGGABLE) {
/* 2165 */                                  e7.printStackTrace();
                                        }
                                    }
                                }
                                break;
                            case 44:
/* 941 */                       ArrayList<BasicNameValuePair> params12 = new ArrayList<>();
/* 943 */                       params12.add(new BasicNameValuePair(PARAM_INCLUDE_PROMOTED, String.valueOf(bundle.getInt(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS))));
/* 945 */                       StringBuilder url32 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
/* 949 */                       vp = VineParserReader.createParserReader(1);
/* 950 */                       op = this.mNetOpFactory.createBasicAuthPutRequest(context, url32, this.mApi, params12, vp).execute();
/* 953 */                       boolean shouldFollow = bundle.getInt(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS) == 1;
/* 954 */                       bundle.putBoolean(EXTRA_SHOULD_FOLLOW_EDITORS_PICKS, shouldFollow);
/* 955 */                       if (!shouldFollow) {
/* 956 */                           bundle.putBoolean(EXTRA_ALSO_FETCH_POSTS, true);
                                }
                                break;
                            case 45:
/* 962 */                       databaseHelper.expireTimeline(bundle.getInt("type"));
                                break;
                            case 47:
/* 2216 */                      FlurryUtils.trackBlockUser();
/* 2217 */                      long currentUserId = bundle.getLong("user_id");
/* 2218 */                      long userToBlock = bundle.getLong(EXTRA_BLOCK_USER_ID);
/* 2219 */                      StringBuilder url33 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(currentUserId), "blocked", Long.valueOf(userToBlock));
/* 2222 */                      vp = VineParserReader.createParserReader(1);
/* 2223 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url33, this.mApi, null, vp).execute();
/* 2227 */                      if (op.isOK()) {
/* 2228 */                          boolean userBlocked = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
/* 2229 */                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, userBlocked);
                                }
                                break;
                            case 48:
/* 2234 */                      FlurryUtils.trackUnBlockUser();
/* 2235 */                      long currentUserId2 = bundle.getLong("user_id");
/* 2236 */                      long userToUnblock = bundle.getLong(EXTRA_BLOCK_USER_ID);
/* 2237 */                      StringBuilder url34 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(currentUserId2), "blocked", Long.valueOf(userToUnblock));
/* 2240 */                      vp = VineParserReader.createParserReader(1);
/* 2241 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url34, this.mApi, vp).execute();
/* 2245 */                      if (op.isOK()) {
/* 2246 */                          boolean userUnblocked = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
/* 2247 */                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, userUnblocked);
                                }
                                break;
                            case 49:
/* 2252 */                      FlurryUtils.trackReportUser();
/* 2253 */                      long userToReport = bundle.getLong("user_id");
/* 2254 */                      String event2 = bundle.getString("event");
/* 2255 */                      ArrayList<BasicNameValuePair> params13 = null;
/* 2256 */                      if (!TextUtils.isEmpty(event2)) {
/* 2257 */                          params13 = new ArrayList<>();
/* 2258 */                          params13.add(new BasicNameValuePair("code", event2));
                                }
/* 2260 */                      StringBuilder url35 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userToReport), VineAPI.RESOURCE_COMPLAINTS);
/* 2263 */                      vp = VineParserReader.createParserReader(1);
/* 2264 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url35, this.mApi, params13, vp).execute();
                                break;
                            case 50:
/* 2270 */                      long followerId = bundle.getLong("user_id");
/* 2271 */                      boolean accept = bundle.getBoolean(EXTRA_ACCEPT);
/* 2272 */                      StringBuilder url36 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_FOLLOWERS, VineAPI.RESOURCE_REQUESTS, Long.valueOf(followerId));
/* 2275 */                      vp = VineParserReader.createParserReader(1);
/* 2281 */                      op = accept ? this.mNetOpFactory.createBasicAuthPutRequest(context, url36, this.mApi, null, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, url36, this.mApi, vp).execute();
                                break;
                            case 51:
/* 2288 */                      boolean explicit = bundle.getBoolean("explicit");
/* 2289 */                      StringBuilder url37 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "explicit");
/* 2291 */                      vp = VineParserReader.createParserReader(1);
/* 2293 */                      if (explicit) {
/* 2294 */                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url37, this.mApi, null, vp);
                                } else {
/* 2298 */                          op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url37, this.mApi, vp);
                                }
/* 2301 */                      op.execute();
                                break;
                            case 52:
/* 2305 */                      boolean priv = bundle.getBoolean(EXTRA_PRIVATE);
/* 2306 */                      StringBuilder url38 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 2308 */                      vp = VineParserReader.createParserReader(1);
/* 2309 */                      ArrayList<BasicNameValuePair> params14 = new ArrayList<>(1);
/* 2311 */                      params14.add(new BasicNameValuePair("private", priv ? "1" : "0"));
/* 2312 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url38, this.mApi, params14, vp).execute();
                                break;
                            case 53:
/* 2318 */                      int pageType2 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
/* 2319 */                      int page5 = 1;
                                switch (pageType2) {
                                    case 1:
                                    case 2:
/* 2323 */                              page5 = 1;
                                        break;
                                    case 3:
/* 2327 */                              int previous2 = databaseHelper.getPreviousPageCursor(2, 0, null, true);
/* 2329 */                              if (previous2 > 0) {
/* 2330 */                                  page5 = previous2;
                                        }
                                        break;
                                }
/* 2339 */                      StringBuilder url39 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", VineAPI.RESOURCE_FEATURED);
/* 2341 */                      VineAPI.addParam(url39, "page", page5);
/* 2343 */                      vp = VineParserReader.createParserReader(18);
/* 2344 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url39, this.mApi, vp).execute();
/* 2347 */                      if (op.isOK()) {
/* 2348 */                          VinePagedData<VineChannel> results = (VinePagedData) vp.getParsedObject();
/* 2349 */                          ArrayList<VineChannel> arrayList2 = results.items;
/* 2350 */                          databaseHelper.mergeChannels(arrayList2, results.nextPage, results.previousPage);
/* 2352 */                          if (results.nextPage <= 0) {
/* 2353 */                              databaseHelper.markLastChannel();
                                    }
/* 2355 */                          bundle.putParcelableArrayList("channels", arrayList2);
                                }
                                break;
                            case 54:
/* 1808 */                      long postId7 = bundle.getLong("post_id");
/* 1809 */                      long repostId3 = bundle.getLong("repost_id");
/* 1810 */                      StringBuilder url40 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId7), "repost");
                                if (repostId3 > 0) {
/* 1813 */                          VineAPI.addParam(url40, PARAM_REPOST_ID, repostId3);
                                }
/* 1815 */                      vp = VineParserReader.createParserReader(19);
/* 1816 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url40, this.mApi, null, vp).execute();
/* 1819 */                      if (op.isOK()) {
/* 1820 */                          VineRepost repost = (VineRepost) vp.getParsedObject();
/* 1821 */                          String username4 = bundle.getString("username");
/* 1823 */                          repost.userId = sessionOwnerId;
/* 1824 */                          repost.username = username4;
/* 1826 */                          bundle.putSerializable("repost", repost);
/* 1828 */                          databaseHelper.revine(repost, sessionOwnerId, false);
                                }
                                break;
                            case 55:
/* 1833 */                      boolean following = bundle.getBoolean("following");
/* 1834 */                      long postId8 = bundle.getLong("post_id");
/* 1835 */                      long myRepostId = bundle.getLong("my_repost_id");
/* 1836 */                      long repostId4 = bundle.getLong("repost_id");
/* 1837 */                      boolean notify5 = bundle.getBoolean("notify", true);
/* 1838 */                      StringBuilder url41 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId8), "repost", Long.valueOf(myRepostId));
                                if (repostId4 > 0) {
/* 1841 */                          VineAPI.addParam(url41, PARAM_REPOST_ID, repostId4);
                                }
/* 1843 */                      vp = VineParserReader.createParserReader(1);
/* 1845 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url41, this.mApi, vp).execute();
/* 1848 */                      if (op.isOK()) {
/* 1849 */                          databaseHelper.unRevine(postId8, sessionOwnerId, following, notify5);
                                }
                                break;
                            case 56:
/* 1854 */                      long postId9 = bundle.getLong("post_id", 0L);
/* 1855 */                      int page6 = bundle.getInt("page", 1);
/* 1856 */                      long anchor4 = bundle.getLong(EXTRA_ANCHOR, 0L);
/* 1858 */                      StringBuilder url42 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId9), "reposts");
/* 1861 */                      VineAPI.addParam(url42, "page", page6);
/* 1862 */                      VineAPI.addAnchor(url42, anchor4);
/* 1863 */                      VineAPI.addParam(url42, "size", 20);
/* 1865 */                      vp = VineParserReader.createParserReader(3);
/* 1866 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url42, this.mApi, vp).execute();
/* 1870 */                      if (op.isOK()) {
/* 1871 */                          VinePagedData<VineUser> users8 = (VinePagedData) vp.getParsedObject();
/* 1872 */                          databaseHelper.mergeUsers(users8.items, 9, String.valueOf(postId9), users8.nextPage, users8.previousPage, null);
/* 1874 */                          bundle.putInt("count", users8.count);
/* 1875 */                          bundle.putInt("next_page", users8.nextPage);
/* 1876 */                          bundle.putInt("previous_page", users8.previousPage);
/* 1877 */                          bundle.putLong(EXTRA_ANCHOR, users8.anchor);
/* 1878 */                          bundle.putParcelableArrayList("users", users8.items);
/* 1880 */                          if (users8.nextPage <= 0) {
/* 1881 */                              databaseHelper.markLastUser(9, String.valueOf(postId9));
                                    }
                                }
                                break;
                            case 57:
/* 2360 */                      VinePostResponse vpr = (VinePostResponse) bundle.getParcelable("post");
/* 2361 */                      VineAPI api = VineAPI.getInstance(this);
/* 2362 */                      StringBuilder getPostUrl = VineAPI.buildUponUrl(api.getBaseUrl(), "timelines", "posts", Long.valueOf(vpr.postId));
/* 2364 */                      vp = VineParserReader.createParserReader(8);
/* 2365 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, getPostUrl, api, vp, key).execute();
/* 2367 */                      if (op.isOK()) {
/* 2368 */                          VinePagedData<VinePost> posts2 = (VinePagedData) vp.getParsedObject();
/* 2369 */                          if (posts2 != null && !posts2.items.isEmpty()) {
/* 2370 */                              VineDatabaseHelper db = VineDatabaseHelper.getDatabaseHelper(this);
                                        try {
/* 2372 */                                  db.mergePosts(posts2.items, 1, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
/* 2374 */                                  db.mergePosts(posts2.items, 2, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
                                        } catch (IOException e8) {
/* 2377 */                                  SLog.m459e("Error writing uploaded post to db", (Throwable) e8);
                                        }
/* 2379 */                              Intent i3 = new Intent();
/* 2380 */                              i3.setAction(BC_ACTION_MERGE_POST_INTO_FEED);
/* 2381 */                              i3.putExtra("post", posts2.items.get(0));
/* 2382 */                              sendBroadcast(i3, CrossConstants.BROADCAST_PERMISSION);
                                    }
                                } else {
/* 2385 */                          error = (VineError) vp.getParsedObject();
/* 2386 */                          if (op != null && error != null) {
/* 2387 */                              SLog.m457e("Error retrieving uploaded post from server: {} {}", Integer.valueOf(op.statusCode), error.message);
                                    }
                                }
                                break;
                            case 58:
/* 2205 */                      boolean notify6 = bundle.getBoolean("notify", false);
/* 2206 */                      databaseHelper.clearCachedData(notify6);
/* 2207 */                      statusCode = 200;
                                break;
                            case 59:
/* 2211 */                      databaseHelper.clearAllData();
/* 2212 */                      statusCode = 200;
                                break;
                            case 60:
/* 2394 */                      String regId = bundle.getString(EXTRA_GCM_REG_ID, "");
/* 2397 */                      StringBuilder url43 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(bundle.getLong("user_id")));
/* 2399 */                      ArrayList<BasicNameValuePair> params15 = new ArrayList<>();
/* 2400 */                      params15.add(new BasicNameValuePair("deviceToken", regId));
/* 2402 */                      vp = VineParserReader.createParserReader(1);
/* 2403 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url43, this.mApi, params15, vp).execute();
                                break;
                            case 61:
/* 2410 */                      String regId2 = bundle.getString(EXTRA_GCM_REG_ID);
/* 2411 */                      if (regId2 == null) {
/* 2412 */                          regId2 = "";
                                }
/* 2414 */                      long userId5 = bundle.getLong("user_id");
/* 2415 */                      String cachedKey = bundle.getString("s_key");
/* 2416 */                      if (cachedKey == null) {
/* 2417 */                          cachedKey = "";
                                }
/* 2420 */                      StringBuilder url44 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId5), "deviceToken");
/* 2422 */                      VineAPI.addParam(url44, "deviceToken", regId2);
/* 2424 */                      vp = VineParserReader.createParserReader(1);
/* 2425 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url44, this.mApi, vp, cachedKey).execute();
                                break;
                            case 63:
/* 2521 */                      long postId10 = bundle.getLong("post_id");
/* 2522 */                      String network = bundle.getString("network");
/* 2523 */                      String message = bundle.getString("message");
/* 2525 */                      FlurryUtils.trackSharePost(network, postId10);
/* 2527 */                      StringBuilder url45 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId10), VineAPI.RESOURCE_SHARE, network);
/* 2529 */                      ArrayList<BasicNameValuePair> params16 = new ArrayList<>();
/* 2530 */                      params16.add(new BasicNameValuePair("message", message));
/* 2532 */                      vp = VineParserReader.createParserReader(1);
/* 2533 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url45, this.mApi, params16, vp).execute();
                                break;
                            case 64:
/* 2561 */                      String cachedKey2 = bundle.getString("s_key");
/* 2562 */                      if (cachedKey2 == null) {
/* 2563 */                          cachedKey2 = "";
                                }
/* 2565 */                      StringBuilder url46 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "authenticate");
/* 2567 */                      vp = VineParserReader.createParserReader(1);
/* 2568 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url46, this.mApi, vp, cachedKey2).execute();
                                break;
                            case 65:
/* 2574 */                      StringBuilder url47 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "pendingNotificationsCount");
/* 2577 */                      vp = VineParserReader.createParserReader(21);
/* 2579 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url47, this.mApi, vp).execute();
/* 2581 */                      if (op.isOK()) {
/* 2582 */                          bundle.putInt("count", ((Integer) vp.getParsedObject()).intValue());
                                }
                                break;
                            case 66:
/* 2641 */                      ArrayList<LoopManager.Record> records = bundle.getParcelableArrayList("loops");
/* 2642 */                      StringBuilder url48 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "loops");
/* 2645 */                      JSONArray loopsList = new JSONArray();
/* 2646 */                      JSONObject postBody = new JSONObject();
                                try {
                                    for (LoopManager.Record r : records) {
/* 2649 */                              JSONObject loopBody = new JSONObject();
/* 2650 */                              loopBody.put("postId", r.postId);
/* 2651 */                              loopBody.put("count", r.loopCount);
/* 2652 */                              loopBody.put(com.twitter.android.sdk.Twitter.EXTRA_TOKEN_SECRET, r.timeStamp / 1000.0d);
/* 2653 */                              loopsList.put(loopBody);
                                    }
/* 2655 */                          postBody.put("loops", loopsList);
                                } catch (JSONException e9) {
/* 2657 */                          CrashUtil.logOrThrowInDebug(e9);
                                }
/* 2660 */                      vp = VineParserReader.createParserReader(27);
/* 2661 */                      op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url48, this.mApi, postBody, vp).execute();
/* 2664 */                      if (op.isOK()) {
/* 2665 */                          VineLoopSubmissionResponse response4 = (VineLoopSubmissionResponse) vp.getParsedObject();
/* 2666 */                          bundle.putParcelable(EXTRA_LOOP_SUBMISSION_RESULT, response4);
                                }
                                break;
                            case 67:
/* 2671 */                      StringBuilder url49 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
/* 2675 */                      vp = VineParserReader.createParserReader(1);
/* 2676 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url49, this.mApi, vp).execute();
/* 2680 */                      if (op.isOK()) {
/* 2681 */                          boolean success = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
/* 2682 */                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success);
                                }
                                break;
                            case 68:
/* 2688 */                      StringBuilder url50 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(bundle.getLong("user_id")), VineAPI.RESOURCE_FOLLOWERS, "reposts");
/* 2694 */                      vp = VineParserReader.createParserReader(1);
/* 2695 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url50, this.mApi, null, vp).execute();
/* 2699 */                      if (op.isOK()) {
/* 2700 */                          boolean success2 = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
/* 2701 */                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success2);
                                }
                                break;
                            case 69:
/* 2706 */                      long userId6 = bundle.getLong("user_id");
/* 2707 */                      StringBuilder url51 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId6), VineAPI.RESOURCE_FOLLOWERS, "reposts");
/* 2713 */                      vp = VineParserReader.createParserReader(1);
/* 2714 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url51, this.mApi, vp).execute();
/* 2718 */                      if (op.isOK()) {
/* 2719 */                          databaseHelper.removePostsByReposterId(userId6);
/* 2721 */                          boolean success3 = VineParsers.API_RESPONSE.SUCCESS.equals(vp.getParsedObject());
/* 2722 */                          bundle.putBoolean(EXTRA_RESPONSE_SUCCESS, success3);
                                }
                                break;
                            case 70:
/* 1523 */                      StringBuilder url52 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "editions");
/* 1526 */                      vp = VineParserReader.createParserReader(20);
/* 1527 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url52, this.mApi, vp).execute();
/* 1531 */                      if (op.isOK()) {
/* 1532 */                          ArrayList<Pair<String, String>> editions = (ArrayList) vp.getParsedObject();
/* 1533 */                          databaseHelper.mergeEditions(editions);
/* 1534 */                          bundle.putSerializable("editions", editions);
                                }
/* 1536 */                      FlurryUtils.trackGetEditions();
                                break;
                            case 71:
/* 1913 */                      ArrayList<BasicNameValuePair> params17 = new ArrayList<>(1);
/* 1915 */                      StringBuilder url53 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "edition");
/* 1917 */                      String edition = bundle.getString("edition");
/* 1918 */                      params17.add(new BasicNameValuePair("edition", edition));
/* 1919 */                      vp = VineParserReader.createParserReader(1);
/* 1920 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url53, this.mApi, params17, vp).execute();
                                break;
                            case 74:
/* 2727 */                      int pageType3 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
/* 2728 */                          if (!bundle.getBoolean(EXTRA_USER_INITIATED, false)) {
/* 2729 */                              break;
                                }
                                try {
/* 2731 */                          op = getInbox(sessionOwnerId, pageType3, actionCode, key, databaseHelper, bundle);
/* 2732 */                          vp = (VineParserReader) op.getReader();
                                } catch (IOException e10) {
/* 2734 */                          if (LOGGABLE) {
/* 2735 */                              e10.printStackTrace();
                                    }
                                }
                                break;
                            case 76:
/* 2845 */                      long conversationRowId2 = bundle.getLong("conversation_row_id");
                                if (conversationRowId2 > 0) {
/* 2847 */                          conversationId = databaseHelper.getConversationRemoteId(conversationRowId2);
                                } else {
/* 2849 */                          conversationId = bundle.getLong("conversation_id");
                                }
                                if (conversationId != -1) {
/* 2854 */                          boolean prefetch = bundle.getBoolean("prefetch");
/* 2855 */                          int pageType4 = bundle.getInt(EXTRA_PAGE_TYPE);
/* 2857 */                          StringBuilder url54 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId));
/* 2860 */                          int page7 = 1;
                                    switch (pageType4) {
                                        case 2:
/* 2865 */                                  int newer = databaseHelper.getPreviousPageCursor(7, 0, String.valueOf(conversationRowId2), false);
/* 2866 */                                  if (newer > 0) {
/* 2867 */                                      page7 = newer;
                                            }
                                            break;
                                        case 3:
/* 2871 */                                  int older = databaseHelper.getNextPageCursor(7, 0, String.valueOf(conversationRowId2), false);
/* 2872 */                                  if (older > 0) {
/* 2873 */                                      page7 = older;
                                            }
                                            break;
                                    }
/* 2879 */                          VineAPI.addParam(url54, "prefetch", prefetch ? 1 : 0);
/* 2880 */                          VineAPI.addParam(url54, "page", page7);
/* 2882 */                          vp = VineParserReader.createParserReader(22);
/* 2883 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url54, this.mApi, vp).execute();
/* 2888 */                          if (op.isOK()) {
/* 2889 */                              VinePagedData<VinePrivateMessage> data = (VinePagedData) vp.getParsedObject();
/* 2890 */                              if (data != null && data.items != null && !data.items.isEmpty()) {
/* 2891 */                                  VineConversation conversation2 = new VineConversation(conversationId, data.lastMessage, data.items, data.unreadMessageCount);
/* 2893 */                                  conversation2.networkType = data.networkType;
                                            for (VinePrivateMessage item : data.items) {
/* 2896 */                                      item.networkType = data.networkType;
                                            }
/* 2899 */                                  bundle.putBoolean(EXTRA_EMPTY, false);
                                            try {
/* 2901 */                                      long conversationLocalObjectId = databaseHelper.mergeConversation(sessionOwnerId, conversation2, conversation2.networkType, data.nextPage, data.previousPage, data.anchor);
/* 2904 */                                      if (data.nextPage <= 0) {
/* 2905 */                                          databaseHelper.markLastMessage(conversationLocalObjectId);
                                                }
/* 2907 */                                      databaseHelper.clearUnreadCount(conversationRowId2);
                                            } catch (IOException e11) {
/* 2909 */                                      if (LOGGABLE) {
/* 2910 */                                          e11.printStackTrace();
                                                }
                                            }
                                        } else {
/* 2914 */                                  bundle.putBoolean(EXTRA_EMPTY, true);
                                        }
                                    }
                                }
                                break;
                            case 77:
/* 2839 */                      bundle.putLong("conversation_id", databaseHelper.getConversationRemoteId(bundle.getLong("conversation_row_id")));
                                break;
                            case 79:
/* 2921 */                      long conversationObjectId = bundle.getLong("conversation_row_id");
/* 2922 */                      long conversationId2 = databaseHelper.getConversationRemoteId(conversationObjectId);
/* 2923 */                      StringBuilder url55 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId2), VineAPI.RESOURCE_IGNORED);
/* 2928 */                      vp = VineParserReader.createParserReader(1);
/* 2929 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url55, this.mApi, null, vp).execute();
/* 2931 */                      databaseHelper.deleteConversation(conversationId2, conversationObjectId);
                                break;
                            case 80:
/* 2935 */                      long conversationObjectId2 = bundle.getLong("conversation_row_id");
/* 2936 */                      long conversationId3 = databaseHelper.getConversationRemoteId(conversationObjectId2);
/* 2937 */                      long messageId = databaseHelper.getLastMessageIdInConversation(conversationObjectId2);
/* 2938 */                      StringBuilder url56 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId3));
                                if (messageId > 0) {
/* 2943 */                          VineAPI.addParam(url56, PARAM_LAST_MESSAGE_ID, messageId);
                                }
/* 2945 */                      vp = VineParserReader.createParserReader(1);
/* 2946 */                      op = this.mNetOpFactory.createBasicAuthDeleteRequest(context, url56, this.mApi, vp).execute();
/* 2948 */                      databaseHelper.deleteConversation(conversationId3, conversationObjectId2);
                                break;
                            case 81:
/* 1892 */                      ep = fetchActivityCounts(bundle.getLong(EXTRA_SESSION_OWNER_ID), context, bundle);
                                break;
                            case 82:
/* 1382 */                      long userId7 = bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L);
/* 1383 */                      int sectionType = bundle.getInt("type", 0);
/* 1384 */                      int pageType5 = bundle.getInt(EXTRA_PAGE_TYPE, 1);
/* 1385 */                      long refreshTime = -1;
/* 1386 */                      String anchor5 = null;
/* 1388 */                      StringAnchorManager sm = Vine.getUserSectionsAnchorManager(this, sectionType);
                                switch (pageType5) {
                                    case 1:
/* 1392 */                              anchor5 = null;
/* 1393 */                              refreshTime = System.currentTimeMillis();
/* 1411 */                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
/* 1414 */                              if (sectionType == 1) {
/* 1415 */                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
/* 1419 */                              if (!TextUtils.isEmpty(anchor5)) {
/* 1420 */                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1 && refreshTime == -1) {
/* 1424 */                                  bundle.putBoolean(EXTRA_EMPTY, true);
                                            break;
                                        }
/* 1429 */                              VineAPI.addParam(url2, "size", 10);
/* 1431 */                              vp = VineParserReader.createParserReader(3);
/* 1432 */                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
/* 1433 */                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
/* 1435 */                              if (op.isOK()) {
/* 1436 */                                  edit = sm.edit();
/* 1437 */                                  if (anchor5 == null) {
/* 1438 */                                      databaseHelper.removeUserSectionsWithType(sectionType);
/* 1439 */                                      edit.setRefreshTime(refreshTime);
                                            }
/* 1441 */                                  users3 = (VinePagedData) vp.getParsedObject();
/* 1442 */                                  if (users3.stringAnchor != null) {
/* 1443 */                                      edit.setAnchor(users3.stringAnchor);
/* 1444 */                                      if (users3.items != null && !users3.items.isEmpty()) {
/* 1445 */                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
/* 1448 */                                  if (users3.items != null) {
/* 1449 */                                      lastId = 0;
/* 1450 */                                      if (!users3.items.isEmpty()) {
/* 1451 */                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
/* 1454 */                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
/* 1456 */                                          Collection<VineUser> collection = users3.items;
                                                    String strValueOf = String.valueOf(userId7);
                                                    int i4 = users3.nextPage;
                                                    int i5 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
/* 1456 */                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection, 10, strValueOf, i4, i5, orderMap, j, sectionType);
/* 1459 */                                          bundle.putInt("count", users3.count);
/* 1460 */                                          bundle.putInt("next_page", users3.nextPage);
/* 1461 */                                          bundle.putInt("previous_page", users3.previousPage);
/* 1462 */                                          bundle.putParcelableArrayList("users", users3.items);
/* 1463 */                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
/* 1466 */                                      if (users3.stringAnchor == null) {
/* 1467 */                                          lastId = 0;
                                                }
/* 1470 */                                      edit.setLastId(lastId);
                                            }
/* 1473 */                                  edit.commit();
                                        }
                                        break;
                                    case 2:
/* 1397 */                              throw new IllegalArgumentException("Invalid page type.");
                                    case 3:
/* 1400 */                              anchor5 = sm.getStringAnchor();
/* 1401 */                              if (anchor5 != null) {
/* 1402 */                                  refreshTime = sm.getRefreshTime();
                                        }
/* 1411 */                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
/* 1414 */                              if (sectionType == 1) {
/* 1415 */                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
/* 1419 */                              if (!TextUtils.isEmpty(anchor5)) {
/* 1420 */                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1) {
                                        }
/* 1429 */                              VineAPI.addParam(url2, "size", 10);
/* 1431 */                              vp = VineParserReader.createParserReader(3);
/* 1432 */                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
/* 1433 */                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
/* 1435 */                              if (op.isOK()) {
/* 1436 */                                  edit = sm.edit();
/* 1437 */                                  if (anchor5 == null) {
/* 1438 */                                      databaseHelper.removeUserSectionsWithType(sectionType);
/* 1439 */                                      edit.setRefreshTime(refreshTime);
                                            }
/* 1441 */                                  users3 = (VinePagedData) vp.getParsedObject();
/* 1442 */                                  if (users3.stringAnchor != null) {
/* 1443 */                                      edit.setAnchor(users3.stringAnchor);
/* 1444 */                                      if (users3.items != null) {
/* 1445 */                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
/* 1448 */                                  if (users3.items != null) {
/* 1449 */                                      lastId = 0;
/* 1450 */                                      if (!users3.items.isEmpty()) {
/* 1451 */                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
/* 1454 */                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
/* 1456 */                                          Collection<VineUser> collection2 = users3.items;
                                                    String strValueOf2 = String.valueOf(userId7);
                                                    int i6 = users3.nextPage;
                                                    int i7 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
/* 1456 */                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection2, 10, strValueOf2, i6, i7, orderMap, j, sectionType);
/* 1459 */                                          bundle.putInt("count", users3.count);
/* 1460 */                                          bundle.putInt("next_page", users3.nextPage);
/* 1461 */                                          bundle.putInt("previous_page", users3.previousPage);
/* 1462 */                                          bundle.putParcelableArrayList("users", users3.items);
/* 1463 */                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
/* 1466 */                                      if (users3.stringAnchor == null) {
/* 1467 */                                          lastId = 0;
                                                }
/* 1470 */                                      edit.setLastId(lastId);
                                            }
/* 1473 */                                  edit.commit();
                                        }
                                        break;
                                    default:
/* 1411 */                              url2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId7), "friends");
/* 1414 */                              if (sectionType == 1) {
/* 1415 */                                  VineAPI.addParam(url2, PARAM_WITH_FAVORITES, String.valueOf(0));
                                        }
/* 1419 */                              if (!TextUtils.isEmpty(anchor5)) {
/* 1420 */                                  VineAPI.addParam(url2, "anchor", anchor5);
                                        } else if (pageType5 == 1) {
                                        }
/* 1429 */                              VineAPI.addParam(url2, "size", 10);
/* 1431 */                              vp = VineParserReader.createParserReader(3);
/* 1432 */                              vp.getExtraBundle().putLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, sm.getIndex());
/* 1433 */                              op = this.mNetOpFactory.createBasicAuthGetRequest(context, url2, this.mApi, vp, key).execute();
/* 1435 */                              if (op.isOK()) {
/* 1436 */                                  edit = sm.edit();
/* 1437 */                                  if (anchor5 == null) {
/* 1438 */                                      databaseHelper.removeUserSectionsWithType(sectionType);
/* 1439 */                                      edit.setRefreshTime(refreshTime);
                                            }
/* 1441 */                                  users3 = (VinePagedData) vp.getParsedObject();
/* 1442 */                                  if (users3.stringAnchor != null) {
/* 1443 */                                      edit.setAnchor(users3.stringAnchor);
/* 1444 */                                      if (users3.items != null) {
/* 1445 */                                          edit.setIndex(((VineUser) users3.items.get(users3.items.size() - 1)).friendIndex);
                                                }
                                            }
/* 1448 */                                  if (users3.items != null) {
/* 1449 */                                      lastId = 0;
/* 1450 */                                      if (!users3.items.isEmpty()) {
/* 1451 */                                          orderMap = new LongSparseArray<>(users3.items.size());
                                                    for (VineUser u2 : users3.items) {
/* 1454 */                                              orderMap.put(u2.userId, Long.valueOf(u2.orderId));
                                                    }
/* 1456 */                                          Collection<VineUser> collection3 = users3.items;
                                                    String strValueOf3 = String.valueOf(userId7);
                                                    int i8 = users3.nextPage;
                                                    int i9 = users3.previousPage;
                                                    if (refreshTime != -1) {
                                                        j = refreshTime;
                                                    } else {
/* 1456 */                                              j = -1;
                                                    }
                                                    databaseHelper.mergeUsers(collection3, 10, strValueOf3, i8, i9, orderMap, j, sectionType);
/* 1459 */                                          bundle.putInt("count", users3.count);
/* 1460 */                                          bundle.putInt("next_page", users3.nextPage);
/* 1461 */                                          bundle.putInt("previous_page", users3.previousPage);
/* 1462 */                                          bundle.putParcelableArrayList("users", users3.items);
/* 1463 */                                          lastId = ((VineUser) users3.items.get(users3.items.size() - 1)).userId;
                                                }
/* 1466 */                                      if (users3.stringAnchor == null) {
/* 1467 */                                          lastId = 0;
                                                }
/* 1470 */                                      edit.setLastId(lastId);
                                            }
/* 1473 */                                  edit.commit();
                                        }
                                        break;
                                }
                                break;
                            case 84:
/* 2958 */                      VinePrivateMessage vpm = (VinePrivateMessage) bundle.getParcelable("message");
/* 2959 */                      ArrayList<BasicNameValuePair> params18 = new ArrayList<>();
/* 2960 */                      params18.add(new BasicNameValuePair(PARAM_DATE, DateTimeUtil.getTimeInStringFromMs(vpm.localStartTime, CommonUtil.TIMESTAMP_FORMAT)));
/* 2961 */                      StringBuilder url57 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(vpm.conversationId), "messages", Long.valueOf(vpm.messageId), VineAPI.RESOURCE_VIEWED);
/* 2967 */                      vp = VineParserReader.createParserReader(1);
/* 2968 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url57, this.mApi, params18, vp).execute();
/* 2971 */                      if (!op.isOK()) {
/* 2972 */                          SLog.m456e("Failed to send view receipt for {}.", Long.valueOf(vpm.messageId));
/* 2973 */                          databaseHelper.updateMessageLoopStartTime(vpm.localStartTime, vpm.messageId);
                                }
                                break;
                            case 85:
/* 2978 */                      ArrayList<VineDatabaseHelper.VineViewedMessage> messages = databaseHelper.getMessagesThatWereViewed();
/* 2979 */                      ArrayList<BasicNameValuePair> params19 = new ArrayList<>();
                                for (VineDatabaseHelper.VineViewedMessage msg : messages) {
/* 2981 */                          params19.clear();
/* 2982 */                          params19.add(new BasicNameValuePair(PARAM_DATE, DateTimeUtil.getTimeInStringFromMs(msg.localStartTime, CommonUtil.TIMESTAMP_FORMAT)));
/* 2983 */                          StringBuilder url58 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(msg.conversationId), "messages", Long.valueOf(msg.messageId), VineAPI.RESOURCE_VIEWED);
/* 2989 */                          vp = VineParserReader.createParserReader(1);
/* 2990 */                          op = this.mNetOpFactory.createBasicAuthPutRequest(context, url58, this.mApi, params19, vp).execute();
/* 2993 */                          if (op.isOK()) {
/* 2994 */                              databaseHelper.updateMessageLoopStartTime(0L, msg.messageId);
                                    } else {
/* 2996 */                              SLog.m456e("Failed to send view receipt for {}.", Long.valueOf(msg.messageId));
                                    }
                                }
                                break;
                            case 86:
/* 3006 */                      ArrayList<String> vanityUrlSegments = bundle.getStringArrayList("vanity_url");
/* 3007 */                      StringBuilder url59 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_PROFILES, VineAPI.RESOURCE_VANITY);
                                for (String pathSegment2 : vanityUrlSegments) {
/* 3014 */                          url59 = VineAPI.buildUponUrl(url59.toString(), pathSegment2.replaceAll(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "%20"));
                                }
/* 3017 */                      vp = VineParserReader.createParserReader(2);
/* 3018 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url59, this.mApi, vp).execute();
/* 3022 */                      if (op.isOK()) {
/* 3023 */                          VineUser user3 = (VineUser) vp.getParsedObject();
/* 3024 */                          bundle.putLong("user_id", user3.userId);
                                }
                                break;
                            case 87:
/* 2952 */                      long userId8 = bundle.getLong("user_id");
/* 2953 */                      boolean hide = bundle.getBoolean(EXTRA_HIDE_PROFILE_REPOSTS);
/* 2954 */                      databaseHelper.setShouldHideProfileRevines(userId8, hide);
                                break;
                            case 88:
/* 825 */                       StringBuilder url60 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 827 */                       Uri avatarUri2 = (Uri) bundle.getParcelable("uri");
/* 829 */                       String f2 = null;
/* 831 */                       if (avatarUri2 != null) {
                                    try {
/* 833 */                               f2 = this.mMediaUtility.getPhotoUri(avatarUri2, context);
/* 841 */                               if (avatarUri2 == null && TextUtils.isEmpty(avatarUri2.toString())) {
/* 842 */                                   finalAvatarUrl2 = "";
                                        } else {
/* 844 */                                   finalAvatarUrl2 = f2;
                                        }
/* 846 */                               params2 = new ArrayList<>(1);
/* 848 */                               if (avatarUri2 != null) {
/* 849 */                                   params2.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl2));
                                        }
/* 852 */                               vp = VineParserReader.createParserReader(2);
/* 853 */                               op = this.mNetOpFactory.createBasicAuthPutRequest(context, url60, this.mApi, params2, vp).execute();
/* 857 */                               if (op.isOK()) {
/* 858 */                                   if (avatarUri2 == null) {
/* 859 */                                       bundle.putString("avatar_url", "");
                                            } else {
/* 861 */                                       bundle.putString("avatar_url", finalAvatarUrl2);
                                            }
                                        }
                                    } catch (Exception e12) {
/* 835 */                               statusCode = 900;
/* 836 */                               reasonPhrase = e12.getMessage();
                                    }
                                } else {
/* 841 */                           if (avatarUri2 == null) {
/* 844 */                               finalAvatarUrl2 = f2;
                                    } else {
/* 844 */                               finalAvatarUrl2 = f2;
                                    }
/* 846 */                           params2 = new ArrayList<>(1);
/* 848 */                           if (avatarUri2 != null) {
/* 849 */                               params2.add(new BasicNameValuePair(PARAM_AVATAR, finalAvatarUrl2));
                                    }
/* 852 */                           vp = VineParserReader.createParserReader(2);
/* 853 */                           op = this.mNetOpFactory.createBasicAuthPutRequest(context, url60, this.mApi, params2, vp).execute();
/* 857 */                           if (op.isOK()) {
/* 858 */                               if (avatarUri2 == null) {
/* 859 */                                   bundle.putString("avatar_url", "");
                                        } else {
/* 861 */                                   bundle.putString("avatar_url", finalAvatarUrl2);
                                        }
                                    }
                                }
                                break;
                            case 89:
/* 3029 */                      String phone3 = bundle.getString(EXTRA_PHONE);
/* 3030 */                      long id = bundle.getLong("user_id");
/* 3031 */                      StringBuilder url61 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(id));
/* 3034 */                      vp = VineParserReader.createParserReader(1);
/* 3036 */                      ArrayList<BasicNameValuePair> params20 = new ArrayList<>(2);
/* 3037 */                      params20.add(new BasicNameValuePair("phoneNumber", phone3));
/* 3038 */                      params20.add(new BasicNameValuePair("verifyPhoneNumber", String.valueOf(1)));
/* 3040 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url61, this.mApi, params20, vp).execute();
                                break;
                            case 90:
/* 3045 */                      String code2 = bundle.getString(EXTRA_KEY);
/* 3046 */                      StringBuilder url62 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "verifyPhoneNumber");
/* 3049 */                      vp = VineParserReader.createParserReader(1);
/* 3051 */                      ArrayList<BasicNameValuePair> params21 = new ArrayList<>(1);
/* 3052 */                      params21.add(new BasicNameValuePair("t", code2));
/* 3054 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url62, this.mApi, params21, vp).execute();
                                break;
                            case 91:
/* 895 */                       int networkType = bundle.getInt("network", 1);
/* 897 */                       VineRecipient recipient = (VineRecipient) bundle.getParcelable(EXTRA_RECIPIENT);
/* 898 */                       if (recipient == null) {
/* 899 */                           throw new IllegalArgumentException("You must provide a recipient if you want to get the conversation id for it");
                                }
/* 905 */                       if (recipient.isFromUser()) {
                                    if (recipient.recipientId <= 0) {
/* 908 */                               recipient.recipientId = databaseHelper.getUserRowIdForUserRemoteId(recipient.userId);
                                    } else if (recipient.userId < 0) {
/* 910 */                               recipient.userId = databaseHelper.getUserRemoteIdForUserRowId(recipient.recipientId);
                                    }
/* 912 */                           conversationRowId = databaseHelper.determineBestConversationRowIdForUserRemoteId(recipient.userId);
                                } else {
/* 915 */                           ArrayList<VineRecipient> recipients = new ArrayList<>();
/* 916 */                           recipients.add(recipient);
/* 917 */                           conversationRowId = databaseHelper.determineOrCreateBestConversationRowIdForRecipients(recipients, networkType);
                                }
                                if (conversationRowId <= 0) {
/* 923 */                           ArrayList<VineRecipient> recipients2 = new ArrayList<>();
/* 924 */                           recipients2.add(recipient);
/* 925 */                           conversationRowId = databaseHelper.createPreMergeConversationIdAndRecipientsIfNecessary(recipients2, networkType);
                                }
/* 929 */                       if (TextUtils.isEmpty(recipient.display)) {
/* 930 */                           recipient.display = databaseHelper.getDisplayForUser(recipient.recipientId);
                                }
/* 933 */                       boolean amFollowing = databaseHelper.amFollowingUser(recipient.recipientId);
/* 935 */                       bundle.putLong("conversation_row_id", conversationRowId);
/* 936 */                       bundle.putBoolean("am_following", amFollowing);
/* 937 */                       bundle.putString("username", recipient.display);
                                bundle2 = bundle;
/* 3411 */                      return bundle2;
                            case 92:
/* 3071 */                      String email4 = bundle.getString("email");
/* 3072 */                      long id2 = bundle.getLong("user_id");
/* 3073 */                      StringBuilder url63 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(id2));
/* 3076 */                      ArrayList<BasicNameValuePair> params22 = new ArrayList<>(2);
/* 3077 */                      params22.add(new BasicNameValuePair("email", email4));
/* 3078 */                      params22.add(new BasicNameValuePair("verifyEmail", String.valueOf(1)));
/* 3080 */                      vp = VineParserReader.createParserReader(1);
/* 3082 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url63, this.mApi, params22, vp).execute();
                                break;
                            case 93:
/* 2744 */                      int networkType2 = bundle.getInt("network", 1);
/* 2746 */                      VineUpload upload = (VineUpload) bundle.getParcelable("upload");
/* 2747 */                      PostInfo info = upload.getPostInfo();
/* 2749 */                      ArrayList<VineRecipient> recipients3 = info.recipients;
/* 2750 */                      long conversationRowId3 = upload.conversationRowId;
/* 2752 */                      if (recipients3 == null && conversationRowId3 > 0) {
/* 2753 */                          databaseHelper.getConversationRecipientsFromConversationRowId(conversationRowId3);
                                }
/* 2756 */                      if (recipients3 != null) {
                                    for (VineRecipient recipient2 : recipients3) {
/* 2759 */                              if (recipient2.isFromUser()) {
                                            if (recipient2.recipientId <= 0) {
/* 2761 */                                      recipient2.recipientId = databaseHelper.getUserRowIdForUserRemoteId(recipient2.userId);
                                            } else if (recipient2.userId < 0) {
/* 2763 */                                      recipient2.userId = databaseHelper.getUserRemoteIdForUserRowId(recipient2.recipientId);
                                            }
                                        }
                                    }
                                }
/* 2769 */                      long myUserId = databaseHelper.getUserRowIdForUserRemoteId(sessionOwnerId);
                                if (conversationRowId3 <= 0) {
/* 2776 */                          conversationRowId3 = databaseHelper.determineOrCreateBestConversationRowIdForRecipients(recipients3, networkType2);
                                }
/* 2778 */                      upload.conversationRowId = conversationRowId3;
/* 2780 */                      long created = System.currentTimeMillis();
/* 2785 */                      String videoPath = null;
/* 2786 */                      String thumbPath = null;
/* 2787 */                      String errorReason = null;
/* 2788 */                      int errorCode = 0;
/* 2789 */                      if (upload.path != null) {
/* 2790 */                          RecordSessionVersion version = UploadManager.getVersionFromPath(upload.path);
/* 2791 */                          if (new File(upload.path + version.videoOutputExtension).exists()) {
/* 2792 */                              videoPath = CommonUtil.FILE_SCHEME + upload.path + version.videoOutputExtension;
                                    } else {
/* 2794 */                              videoPath = CommonUtil.FILE_SCHEME + upload.path;
                                    }
/* 2796 */                          thumbPath = CommonUtil.FILE_SCHEME + RecordConfigUtils.getThumbnailPath(upload.path);
/* 2797 */                          if (upload.status == 2) {
/* 2798 */                              errorReason = getString(C0257R.string.message_failed_tap_to_retry);
/* 2799 */                              errorCode = -2;
                                    }
                                }
/* 2803 */                      long messageId2 = databaseHelper.getNewMessageId();
                                if (info.postId > 0) {
/* 2805 */                          videoPath = info.sharedPostVideoUrl;
/* 2806 */                          thumbPath = info.sharedPostThumbUrl;
                                }
/* 2814 */                      long messageRowId = databaseHelper.mergeMessage(conversationRowId3, new VinePrivateMessage(-1L, conversationRowId3, messageId2, myUserId, created, info.message, videoPath, thumbPath, 1, false, false, Integer.MAX_VALUE, 0L, info.maxLoops > 0, 0L, info.postId, errorCode, errorReason, upload.path));
/* 2815 */                      databaseHelper.updateConversationWithLastMessage(conversationRowId3, messageRowId, created);
/* 2819 */                      upload.postInfo = info.toString();
/* 2820 */                      bundle.putParcelable("upload", upload);
/* 2821 */                      bundle.putLong(EXTRA_PREMERGED_MESSAGE_ID, messageRowId);
                                break;
                            case 94:
/* 3086 */                      databaseHelper.clearUnreadCount(bundle.getLong("conversation_row_id"));
                                break;
                            case 95:
/* 2833 */                      databaseHelper.deleteMessage(bundle.getLong("message_id"));
                                break;
                            case 96:
/* 2540 */                      long postId11 = bundle.getLong("post_id");
/* 2541 */                      long created2 = System.currentTimeMillis();
/* 2542 */                      JSONObject postBody2 = new JSONObject();
/* 2543 */                      ArrayList<VineRecipient> recipients4 = bundle.getParcelableArrayList("recipients");
/* 2544 */                      StringBuilder url64 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations");
                                try {
/* 2547 */                          postBody2.put("postId", postId11);
/* 2548 */                          postBody2.put("to", PostInfo.recipientsToJsonArray(recipients4));
/* 2549 */                          postBody2.put("created", created2);
/* 2550 */                          addCountryParam(postBody2);
                                } catch (JSONException e13) {
/* 2552 */                          CrashUtil.logOrThrowInDebug(e13);
                                }
/* 2554 */                      vp = VineParserReader.createParserReader(12);
/* 2555 */                      op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url64, this.mApi, postBody2, vp).execute();
                                break;
                            case 97:
/* 2442 */                      VineSingleNotification notification = (VineSingleNotification) bundle.getParcelable("notification");
/* 2443 */                      if (notification.isMessaging()) {
/* 2444 */                          notification.notificationTypeId = 18;
/* 2445 */                          VineConversation conversation3 = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
/* 2447 */                          if (conversation3 != null) {
/* 2448 */                              notification.conversationRowId = conversation3.conversationObjectId;
/* 2449 */                              VinePrivateMessage message2 = conversation3.lastMessage();
/* 2450 */                              if (message2 != null && message2.thumbnailUrl != null && message2.videoUrl != null) {
/* 2452 */                                  notification.imageKeys.add(new ImageKey(message2.thumbnailUrl));
/* 2453 */                                  notification.videoKeys.add(new VideoKey(message2.videoUrl));
                                        }
                                    }
                                }
/* 2458 */                      ArrayList<VineSingleNotification> notifications = new ArrayList<>();
/* 2459 */                      if (TextUtils.isEmpty(notification.onboard)) {
/* 2460 */                          ArrayList<VineSingleNotification> notifs = databaseHelper.mergePushNotification(notification);
/* 2461 */                          if (notifs.size() == 0) {
/* 2464 */                              bundle.remove("notification");
                                    } else {
/* 2467 */                              notifications.addAll(notifs);
/* 2470 */                              if (notification.isMessaging()) {
/* 2471 */                                  unclearedNotifications = new ArrayList<>(notifications);
/* 2472 */                                  uniqueNotifications = new LongSparseArray<>();
/* 2474 */                                  if (unclearedNotifications.size() > 1) {
                                                for (VineSingleNotification n2 : unclearedNotifications) {
/* 2476 */                                          notif2 = uniqueNotifications.get(n2.conversationRowId);
/* 2477 */                                          if (notif2 != null) {
/* 2478 */                                              notif2.messageCount++;
                                                    } else {
/* 2480 */                                              uniqueNotifications.put(n2.conversationRowId, n2);
/* 2481 */                                              n2.messageCount = 1;
                                                    }
                                                }
/* 2484 */                                      unclearedNotifications.clear();
/* 2485 */                                      n = uniqueNotifications.size();
                                                for (i = 0; i < n; i++) {
/* 2487 */                                          notif = uniqueNotifications.valueAt(i);
/* 2488 */                                          if (notif.messageCount > 1) {
/* 2489 */                                              notif.comment = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notif.comment), Integer.valueOf(notif.messageCount)});
                                                    }
/* 2491 */                                          unclearedNotifications.add(notif);
                                                    if (notif.conversationRowId == notification.conversationRowId) {
/* 2493 */                                              notification.messageCount = notif.messageCount;
                                                    }
/* 2495 */                                          if (i > 0) {
/* 2496 */                                              SLog.m464i("Prefetching message headers...");
/* 2497 */                                              conversation = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
/* 2499 */                                              if (conversation != null) {
/* 2500 */                                                  VinePrivateMessage lastMessage = conversation.lastMessage();
/* 2501 */                                                  if (Util.toPrettyComment(notification) == null && i < 4 && lastMessage.thumbnailUrl != null && lastMessage.videoUrl != null) {
/* 2503 */                                                      notification.imageKeys.add(new ImageKey(lastMessage.thumbnailUrl));
/* 2504 */                                                      notification.videoKeys.add(new VideoKey(lastMessage.videoUrl));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
/* 2511 */                                  bundle.putParcelableArrayList("notifications", unclearedNotifications);
                                        } else {
/* 2513 */                                  bundle.putParcelableArrayList("notifications", notifications);
                                        }
/* 2516 */                              bundle.putParcelable("notification", notification);
                                    }
                                } else {
/* 2470 */                          if (notification.isMessaging()) {
/* 2471 */                              unclearedNotifications = new ArrayList<>(notifications);
/* 2472 */                              uniqueNotifications = new LongSparseArray<>();
/* 2474 */                              if (unclearedNotifications.size() > 1) {
                                            for (VineSingleNotification n2 : unclearedNotifications) {
/* 2476 */                                      notif2 = uniqueNotifications.get(n2.conversationRowId);
/* 2477 */                                      if (notif2 != null) {
/* 2478 */                                          notif2.messageCount++;
                                                } else {
/* 2480 */                                          uniqueNotifications.put(n2.conversationRowId, n2);
/* 2481 */                                          n2.messageCount = 1;
                                                }
                                            }
/* 2484 */                                  unclearedNotifications.clear();
/* 2485 */                                  n = uniqueNotifications.size();
                                            while (i < n) {
/* 2487 */                                      notif = uniqueNotifications.valueAt(i);
/* 2488 */                                      if (notif.messageCount > 1) {
/* 2489 */                                          notif.comment = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notif.comment), Integer.valueOf(notif.messageCount)});
                                                }
/* 2491 */                                      unclearedNotifications.add(notif);
                                                if (notif.conversationRowId == notification.conversationRowId) {
/* 2493 */                                          notification.messageCount = notif.messageCount;
                                                }
/* 2495 */                                      if (i > 0) {
/* 2496 */                                          SLog.m464i("Prefetching message headers...");
/* 2497 */                                          conversation = getConversationWithRemoteId(sessionOwnerId, notification.conversationId, 1, databaseHelper, true, 1);
/* 2499 */                                          if (conversation != null) {
/* 2500 */                                              VinePrivateMessage lastMessage2 = conversation.lastMessage();
/* 2501 */                                              if (Util.toPrettyComment(notification) == null) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
/* 2511 */                              bundle.putParcelableArrayList("notifications", unclearedNotifications);
                                    } else {
/* 2513 */                              bundle.putParcelableArrayList("notifications", notifications);
                                    }
/* 2516 */                          bundle.putParcelable("notification", notification);
                                }
                                break;
                            case 98:
/* 3090 */                      VineUpload upload2 = (VineUpload) bundle.getParcelable("upload");
/* 3091 */                      op = post(context, upload2, bundle);
/* 3092 */                      bundle.putParcelable("upload", upload2);
                                break;
                            case 100:
/* 2825 */                      String uploadPath = bundle.getString("upload_path");
/* 2826 */                      if (!TextUtils.isEmpty(uploadPath)) {
/* 2827 */                          databaseHelper.setMessageError(uploadPath, -2, getString(C0257R.string.message_failed_tap_to_retry));
                                }
                                break;
                            case 101:
/* 1661 */                      String addressBook2 = null;
                                try {
/* 1663 */                          addressBook2 = Util.getAddressBookJson(getContentResolver());
                                } catch (IOException e14) {
/* 1665 */                          if (LOGGABLE) {
/* 1666 */                              e14.printStackTrace();
                                    }
                                }
/* 1670 */                      if (addressBook2 == null && 0 != 0) {
/* 1671 */                          op.statusCode = 1000;
/* 1672 */                          error = new VineError(1000, "Could not create json.");
                                } else {
/* 1676 */                          StringBuilder url65 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SUGGESTED, VineAPI.RESOURCE_CONTACTS);
/* 1683 */                          vp = VineParserReader.createParserReader(3);
/* 1685 */                          ArrayList<BasicNameValuePair> params23 = new ArrayList<>();
/* 1686 */                          params23.add(new BasicNameValuePair(PARAM_ADDRESS_BOOK, addressBook2));
/* 1687 */                          String language = Util.getLocale();
/* 1688 */                          params23.add(new BasicNameValuePair(PARAM_LOCALE, language));
/* 1690 */                          op = this.mNetOpFactory.createBasicAuthPostRequest(context, url65, this.mApi, params23, vp).execute();
                                }
                                break;
                            case 102:
/* 2432 */                      int notificationId = bundle.getInt("notification_id");
/* 2433 */                      long conversationRowId4 = bundle.getLong("conversation_row_id", -1L);
                                if (conversationRowId4 >= 0) {
/* 2435 */                          databaseHelper.deletePushNotificationsForConversation(conversationRowId4);
                                } else {
/* 2437 */                          databaseHelper.deletePushNotifications(notificationId);
                                }
                                break;
                            case 103:
/* 1170 */                      Cursor c = databaseHelper.getMessagesForConversationRowId(bundle.getLong("conversation_row_id"));
/* 1171 */                      if (c != null) {
/* 1173 */                          while (c.moveToNext()) {
                                        try {
/* 1174 */                                  VinePrivateMessage vpm2 = new VinePrivateMessage(c);
/* 1175 */                                  startService(VineUploadService.getVMPostIntent(this, vpm2.uploadPath, true, vpm2.messageRowId, vpm2.conversationRowId, null, vpm2.message, vpm2.postId, vpm2.videoUrl, vpm2.thumbnailUrl, vpm2.maxLoops));
                                        } catch (Throwable th2) {
                                            c.close();
                                            throw th2;
                                        }
                                    }
                                    c.close();
                                }
                                break;
                            case 104:
/* 2739 */                      databaseHelper.savePageCursor(6, 1, null, 0, 0, 0L, true);
/* 2740 */                      databaseHelper.savePageCursor(6, 2, null, 0, 0, 0L, true);
                                break;
                            case 105:
/* 3096 */                      boolean acceptOon = bundle.getBoolean(EXTRA_ACCEPT_OON);
/* 3097 */                      StringBuilder url66 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId));
/* 3100 */                      ArrayList<BasicNameValuePair> params24 = new ArrayList<>();
/* 3101 */                      params24.add(new BasicNameValuePair("acceptsOutOfNetworkConversations", acceptOon ? "1" : "0"));
/* 3104 */                      vp = VineParserReader.createParserReader(1);
/* 3105 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url66, this.mApi, params24, vp).execute();
                                break;
                            case 106:
/* 3109 */                      boolean enableAddressBook = bundle.getBoolean(EXTRA_ENABLE);
/* 3110 */                      StringBuilder url67 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
/* 3113 */                      ArrayList<BasicNameValuePair> params25 = new ArrayList<>();
/* 3114 */                      params25.add(new BasicNameValuePair(PARAM_DISABLE_ADDRESS_BOOK, enableAddressBook ? "0" : "1"));
/* 3117 */                      vp = VineParserReader.createParserReader(1);
/* 3118 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url67, this.mApi, params25, vp).execute();
                                break;
                            case 107:
/* 1745 */                      ArrayList<String> userIdsToFollow = bundle.getStringArrayList(EXTRA_FOLLOW_IDS);
/* 1746 */                      StringBuilder url68 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_FOLLOWERS);
/* 1748 */                      ArrayList<BasicNameValuePair> params26 = new ArrayList<>();
/* 1749 */                      Iterator<String> it = userIdsToFollow.iterator();
                                while (it.hasNext()) {
/* 1750 */                          params26.add(new BasicNameValuePair(PARAM_USER_IDS, it.next()));
                                }
/* 1753 */                      vp = VineParserReader.createParserReader(1);
/* 1754 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url68, this.mApi, params26, vp).execute();
                                break;
                            case 108:
/* 3122 */                      boolean enable = bundle.getBoolean(EXTRA_ENABLE);
/* 3123 */                      int type2 = bundle.getInt("type");
/* 3124 */                      StringBuilder url69 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), VineAPI.RESOURCE_PREFERENCES);
/* 3128 */                      if (type2 == 0) {
/* 3129 */                          param = PARAM_HIDDEN_EMAIL;
                                } else if (type2 == 1) {
/* 3131 */                          param = PARAM_HIDDEN_PHONE_NUMBER;
                                } else {
/* 3133 */                          param = PARAM_HIDDEN_TWITTER;
                                }
/* 3136 */                      ArrayList<BasicNameValuePair> params27 = new ArrayList<>();
/* 3137 */                      params27.add(new BasicNameValuePair(param, enable ? "0" : "1"));
/* 3139 */                      vp = VineParserReader.createParserReader(1);
/* 3140 */                      op = this.mNetOpFactory.createBasicAuthPutRequest(context, url69, this.mApi, params27, vp).execute();
                                break;
                            case 109:
/* 3058 */                      String code3 = bundle.getString(EXTRA_KEY);
/* 3059 */                      StringBuilder url70 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", "verifyEmail");
/* 3062 */                      vp = VineParserReader.createParserReader(1);
/* 3064 */                      ArrayList<BasicNameValuePair> params28 = new ArrayList<>(1);
/* 3065 */                      params28.add(new BasicNameValuePair("t", code3));
/* 3067 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url70, this.mApi, params28, vp).execute();
                                break;
                            case 110:
/* 1339 */                      int page8 = bundle.getInt("page", 1);
/* 1340 */                      long anchor6 = bundle.getLong(EXTRA_ANCHOR, 0L);
/* 1341 */                      long notificationId2 = bundle.getLong("notification_id", 0L);
                                if (sessionOwnerId > 0) {
/* 1344 */                          StringBuilder url71 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", VineAPI.RESOURCE_GROUPED, Long.valueOf(notificationId2), "users");
/* 1347 */                          VineAPI.addParam(url71, "page", page8);
/* 1348 */                          VineAPI.addAnchor(url71, anchor6);
/* 1349 */                          VineAPI.addParam(url71, "size", 20);
/* 1350 */                          vp = VineParserReader.createParserReader(3);
/* 1352 */                          op = this.mNetOpFactory.createBasicAuthGetRequest(context, url71, this.mApi, vp).execute();
/* 1355 */                          if (op.isOK()) {
/* 1356 */                              VinePagedData<VineUser> users9 = (VinePagedData) vp.getParsedObject();
/* 1357 */                              if (users9.items != null) {
/* 1358 */                                  LongSparseArray<Long> orderMap3 = new LongSparseArray<>(users9.items.size());
                                            for (VineUser u3 : users9.items) {
/* 1361 */                                      orderMap3.put(u3.userId, Long.valueOf(u3.orderId));
                                            }
/* 1337 */                                  databaseHelper.mergeUsers(users9.items, 12, String.valueOf(notificationId2), users9.nextPage, users9.previousPage, orderMap3);
/* 1366 */                                  bundle.putInt("count", users9.count);
/* 1367 */                                  bundle.putInt("next_page", users9.nextPage);
/* 1368 */                                  bundle.putInt("previous_page", users9.previousPage);
/* 1369 */                                  bundle.putLong(EXTRA_ANCHOR, users9.anchor);
/* 1370 */                                  bundle.putParcelableArrayList("users", users9.items);
                                        }
/* 1372 */                              if (users9.nextPage <= 0) {
/* 1337 */                                  databaseHelper.markLastUser(12, String.valueOf(sessionOwnerId), "_id DESC");
                                        }
                                    }
                                }
                                break;
                            case 111:
/* 3144 */                      StringBuilder url72 = VineAPI.buildUponUrl(this.mApi.getConfigUrl(), VineAPI.RESOURCE_CLIENT_FLAGS);
/* 3147 */                      vp = VineParserReader.createParserReader(29);
/* 3148 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url72, this.mApi, vp).execute();
/* 3150 */                      if (op.isOK()) {
/* 3151 */                          VineClientFlags clientFlags = (VineClientFlags) vp.getParsedObject();
/* 3152 */                          bundle.putParcelable(EXTRA_CLIENT_FLAGS, clientFlags);
                                }
                                break;
                            case 112:
/* 3158 */                      Iterator<NetworkOperation.CancelableRequest> iterator = this.mHttpRequests.values().iterator();
/* 3159 */                      while (iterator.hasNext()) {
/* 3160 */                          NetworkOperation.CancelableRequest request2 = iterator.next();
/* 3161 */                          if (request2 != null && !request2.isCancelled()) {
/* 3162 */                              request2.cancel();
                                    }
/* 3164 */                          iterator.remove();
                                }
                                break;
                            case 113:
/* 882 */                       StringBuilder url73 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", "settings");
/* 884 */                       vp = VineParserReader.createParserReader(30);
/* 885 */                       op = this.mNetOpFactory.createBasicAuthGetRequest(context, url73, this.mApi, vp).execute();
/* 888 */                       if (op.isOK()) {
/* 889 */                           VinePagedData<VineNotificationSetting> notifSettings = (VinePagedData) vp.getParsedObject();
/* 890 */                           bundle.putParcelableArrayList(EXTRA_NOTIFICATION_SETTINGS, notifSettings.items);
                                }
                                break;
                            case 114:
/* 867 */                       StringBuilder url74 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", "settings");
/* 870 */                       HashMap<String, String> params29 = (HashMap) bundle.getSerializable(EXTRA_NOTIFICATION_SETTINGS);
/* 871 */                       ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
                                for (Map.Entry<String, String> entry : params29.entrySet()) {
/* 873 */                           BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
/* 874 */                           pairs.add(pair);
                                }
/* 876 */                       vp = VineParserReader.createParserReader(1);
/* 877 */                       op = this.mNetOpFactory.createBasicAuthPutRequest(context, url74, this.mApi, pairs, vp).execute();
                                break;
                            case 115:
/* 3002 */                      databaseHelper.removeAccount();
                                break;
                            case 116:
/* 3169 */                      long channelId = bundle.getLong("channelId");
/* 3170 */                      boolean follow = bundle.getBoolean("following");
/* 3171 */                      StringBuilder uri = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", Long.valueOf(channelId), VineAPI.RESOURCE_FOLLOWERS);
/* 3174 */                      vp = VineParserReader.createParserReader(1);
/* 3178 */                      op = follow ? this.mNetOpFactory.createBasicAuthPostRequest(context, uri, this.mApi, null, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, uri, this.mApi, vp).execute();
                                break;
                            case 117:
/* 3184 */                      ArrayList<String> channelIds = new ArrayList<>(bundle.getStringArrayList("channels"));
/* 3185 */                      StringBuilder uri2 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "channels", VineAPI.RESOURCE_FOLLOWERS);
/* 3188 */                      ArrayList<BasicNameValuePair> params30 = new ArrayList<>();
/* 3189 */                      Iterator<String> it2 = channelIds.iterator();
                                while (it2.hasNext()) {
/* 3190 */                          params30.add(new BasicNameValuePair(PARAM_CHANNEL_IDS, it2.next()));
                                }
/* 3193 */                      vp = VineParserReader.createParserReader(1);
/* 3194 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, uri2, this.mApi, params30, vp).execute();
                                break;
                            case 118:
/* 3199 */                      long channelId2 = bundle.getLong("channelId");
                                if (channelId2 > 0) {
/* 3201 */                          databaseHelper.markChannelLastUsedTimestamp(channelId2);
                                }
                                break;
                            case 119:
/* 3206 */                      long userId9 = bundle.getLong("user_id");
/* 3207 */                      boolean favorite = bundle.getBoolean(EXTRA_FAVORITE);
/* 3208 */                      boolean isFollowing = bundle.getBoolean("following");
/* 3210 */                      ArrayList<BasicNameValuePair> params31 = new ArrayList<>();
/* 3212 */                      if (isFollowing) {
/* 3213 */                          url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId9), VineAPI.RESOURCE_FOLLOWERS, "notifications");
                                } else {
/* 3216 */                          params31.add(new BasicNameValuePair("notify", "1"));
/* 3217 */                          url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(userId9), VineAPI.RESOURCE_FOLLOWERS);
/* 3219 */                          VineAPI.addParam(url, "notify", "1");
                                }
/* 3222 */                      vp = VineParserReader.createParserReader(1);
/* 3226 */                      op = favorite ? this.mNetOpFactory.createBasicAuthPostRequest(context, url, this.mApi, params31, vp).execute() : this.mNetOpFactory.createBasicAuthDeleteRequest(context, url, this.mApi, vp).execute();
                                break;
                            case 120:
/* 3232 */                      int page9 = bundle.getInt("page", 1);
/* 3233 */                      String anchor7 = bundle.getString(EXTRA_ANCHOR);
/* 3235 */                      StringBuilder url75 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", VineAPI.RESOURCE_SECTIONED_LIST);
/* 3237 */                      VineAPI.addParam(url75, "page", page9);
/* 3238 */                      if (!TextUtils.isEmpty(anchor7)) {
/* 3239 */                          VineAPI.addParam(url75, "anchor", anchor7);
                                }
/* 3242 */                      VineAPI.addParam(url75, "size", 20);
/* 3244 */                      vp = VineParserReader.createParserReader(3);
/* 3245 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url75, this.mApi, vp).execute();
/* 3247 */                      if (op.isOK()) {
/* 3248 */                          VinePagedData<VineUser> pagedData = (VinePagedData) vp.getParsedObject();
/* 3249 */                          bundle.putInt("next_page", pagedData.nextPage);
/* 3250 */                          bundle.putInt("previous_page", pagedData.previousPage);
/* 3251 */                          bundle.putString(EXTRA_ANCHOR, pagedData.stringAnchor);
/* 3252 */                          bundle.putParcelableArrayList("users", pagedData.items);
                                }
                                break;
                            case 121:
                            case 122:
/* 3260 */                      if (actionCode == 121) {
/* 3261 */                          endpoint = VineAPI.RESOURCE_SUGGESTED_FAVORITES;
                                } else {
/* 3263 */                          endpoint = VineAPI.RESOURCE_ONBOARDING_SUGGESTED_FAVORITES;
                                }
/* 3265 */                      StringBuilder url76 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "following", endpoint);
/* 3268 */                      vp = VineParserReader.createParserReader(3);
/* 3269 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url76, this.mApi, vp).execute();
/* 3271 */                      if (op.isOK()) {
/* 3273 */                          bundle.putParcelableArrayList("users", ((VinePagedData) vp.getParsedObject()).items);
                                }
                                break;
                            case 123:
/* 3278 */                      if (this.mTwitter != null) {
                                    try {
/* 3280 */                              this.mTwitter.createFriendship(586671909L);
                                    } catch (TwitterException ex) {
/* 3282 */                              CrashUtil.logException(ex, "Unable to follow Vine on Twitter", new Object[0]);
                                    }
                                }
                                break;
                            case 126:
/* 1558 */                      String commentId = bundle.getString("comment_id");
/* 1559 */                      String postId12 = bundle.getString("post_id");
/* 1560 */                      String event3 = bundle.getString("event");
/* 1561 */                      ArrayList<BasicNameValuePair> params32 = null;
/* 1562 */                      if (!TextUtils.isEmpty(event3)) {
/* 1563 */                          params32 = new ArrayList<>();
/* 1564 */                          params32.add(new BasicNameValuePair("code", event3));
                                }
/* 1566 */                      StringBuilder url77 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", postId12, "comments", commentId, VineAPI.RESOURCE_COMPLAINTS);
/* 1571 */                      vp = VineParserReader.createParserReader(1);
/* 1572 */                      op = this.mNetOpFactory.createBasicAuthPostRequest(context, url77, this.mApi, params32, vp).execute();
                                break;
                            case 127:
/* 3287 */                      PrefetchManager manager = PrefetchManager.getInstance(this);
/* 3288 */                      if (manager.isClientPrefetchEnabled()) {
/* 3289 */                          boolean manual = bundle.getBoolean(EXTRA_MANUAL, false);
/* 3290 */                          manager.notifySyncStart(this);
/* 3291 */                          FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Sync was requested.", new Object[0]);
/* 3292 */                          if (!manual && !manager.isSuitableToSync()) {
/* 3295 */                              manager.notifySyncEnd(this, "Device not ready.", false, manual);
                                    } else {
/* 3297 */                              manager.setCurrentSyncStart(System.currentTimeMillis());
/* 3298 */                              AppController ctr = AppController.getInstance(context);
/* 3299 */                              long startNetworkUsed = NetworkOperation.sNetworkDataUsed;
/* 3300 */                              long userId10 = ctr.getActiveId();
                                        if (userId10 > 0) {
/* 3302 */                                  PrefetchManager.injectPrefetchArguments(bundle, PrefetchManager.getMinimumDelay(manager.getInterval()));
/* 3303 */                                  performTimeLinePrefetch(new Bundle(bundle), databaseHelper, BehaviorManager.getInstance(this).getTopUsagesTimelineEndpoints());
/* 3304 */                                  performActivityPrefetch(context, new Bundle(bundle), databaseHelper, userId10);
/* 3305 */                                  performGetUsersMePrefetch(context, new Bundle(bundle));
/* 3306 */                                  fetchActivityCounts(userId10, context, bundle);
/* 3307 */                                  long waited = 0;
/* 3308 */                                  while (ctr.hasPendingCacheRequests() && waited < 600000) {
                                                try {
/* 3310 */                                          Thread.sleep(1000L);
/* 3311 */                                          waited += 1000;
                                                } catch (InterruptedException e15) {
                                                }
                                            }
                                        }
/* 3317 */                              if (SLog.sLogsOn) {
/* 3318 */                                  FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Prefetch used: {} kb", Long.valueOf((NetworkOperation.sNetworkDataUsed - startNetworkUsed) / FileUtils.ONE_KB));
                                        }
/* 3320 */                              manager.notifySyncEnd(this, null, true, manual);
                                    }
                                }
                                break;
                            case 128:
/* 651 */                       SLog.m464i("Running device boot actions.");
                                try {
/* 653 */                           PrefetchManager.getInstance(this).onDeviceBoot();
/* 654 */                           AppController.getInstance(this).refreshLoopSendingAlarm(System.currentTimeMillis());
                                } catch (RuntimeException e16) {
/* 656 */                           CrashUtil.logOrThrowInDebug(e16);
                                }
                                break;
                            case 130:
/* 3326 */                      StringBuilder url78 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), VineAPI.RESOURCE_COMPLAINTS, VineAPI.RESOURCE_MENU);
/* 3330 */                      vp = VineParserReader.createParserReader(35);
/* 3331 */                      op = this.mNetOpFactory.createBasicAuthGetRequest(context, url78, this.mApi, vp).execute();
/* 3333 */                      if (op.isOK()) {
/* 3334 */                          ArrayList<ComplaintMenuOption> complaintMenu = (ArrayList) vp.getParsedObject();
/* 3336 */                          bundle.putSerializable(EXTRA_COMPLAINT_MENU, complaintMenu);
                                }
                                break;
                            case 1000:
/* 966 */                       Account account = VineAccountHelper.getAccount(context, accountName);
/* 967 */                       if ((account != null || (account = VineAccountHelper.getAccount(context, bundle.getString("email"))) != null) && (loginType = VineAccountHelper.getLoginType((am = AccountManager.get(context)), account)) != null) {
                                    switch (loginType.intValue()) {
                                        case 1:
/* 982 */                                   String email5 = account.name;
/* 983 */                                   String password4 = VineAccountHelper.getPassword(am, account, context);
/* 984 */                                   vp = VineParserReader.createLoginReader();
/* 986 */                                   ArrayList<BasicNameValuePair> params33 = new ArrayList<>();
/* 989 */                                   params33.add(new BasicNameValuePair("username", email5));
/* 990 */                                   params33.add(new BasicNameValuePair("password", password4));
/* 992 */                                   StringBuilder url79 = new StringBuilder(this.mApi.getBaseUrl()).append("/users/authenticate");
/* 995 */                                   op = this.mNetOpFactory.createPostRequest(context, url79, params33, vp, this.mApi).execute();
/* 999 */                                   if (op.isOK()) {
/* 1000 */                                      VineLogin vl4 = (VineLogin) vp.getParsedObject();
/* 1001 */                                      VineAccountHelper.setSessionKey(am, account, vl4.key);
/* 1002 */                                      bundle.putString("s_key", vl4.key);
/* 1003 */                                      AppController.getInstance(context).setSessionKey(vl4.key);
                                            }
                                            break;
                                        case 2:
/* 1008 */                                  String token6 = VineAccountHelper.getTwitterToken(am, account);
/* 1009 */                                  String secret3 = VineAccountHelper.getTwitterSecret(am, account);
/* 1010 */                                  vp = VineParserReader.createLoginReader();
/* 1012 */                                  ArrayList<BasicNameValuePair> params34 = new ArrayList<>();
/* 1015 */                                  params34.add(new BasicNameValuePair(PARAM_TWITTER_TOKEN, token6));
/* 1016 */                                  params34.add(new BasicNameValuePair(PARAM_TWITTER_SECRET, secret3));
/* 1018 */                                  String pathSegment3 = bundle.getBoolean("reactivate", false) ? "reactivate" : "authenticate";
/* 1020 */                                  StringBuilder url80 = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", pathSegment3, "twitter");
/* 1023 */                                  op = this.mNetOpFactory.createPostRequest(context, url80, params34, vp, this.mApi).execute();
/* 1027 */                                  if (op.isOK()) {
/* 1028 */                                      VineLogin vl5 = (VineLogin) vp.getParsedObject();
/* 1029 */                                      VineAccountHelper.setSessionKey(am, account, vl5.key);
/* 1030 */                                      bundle.putString("s_key", vl5.key);
/* 1031 */                                      AppController.getInstance(context).setSessionKey(vl5.key);
                                            }
                                            break;
                                    }
                                }
                                break;
                        }
/* 3347 */              if (ep != null) {
/* 3348 */                  vp = ep.f112vp;
/* 3349 */                  op = ep.f111op;
                        }
/* 3352 */              bundle2 = new Bundle();
/* 3353 */              if (op != null) {
/* 3355 */                  NetworkOperation.CancelableRequest request3 = op.getCancelableRequest();
/* 3357 */                  if (request3 != null && !request3.isCancelled()) {
/* 3358 */                      this.mHttpRequests.put(Integer.valueOf(request3.hashCode()), request3);
                            }
/* 3361 */                  statusCode = op.statusCode;
/* 3362 */                  bundle2.putInt(STATUS_CODE, statusCode);
/* 3363 */                  if (!op.isOK() && vp != null && error == null) {
/* 3364 */                      error = (VineError) vp.getParsedObject();
                            }
/* 3366 */                  if (error != null) {
/* 3367 */                      reasonPhrase = error.message;
/* 3368 */                      bundle2.putString(REASON_PHRASE, reasonPhrase);
/* 3371 */                      int errorCode2 = error.errorCode;
/* 3372 */                      if (errorCode2 == VineKnownErrors.BAD_CREDENTIALS.code || errorCode2 == VineKnownErrors.REQUIRES_LOGIN.code) {
/* 3374 */                          bundle2.putInt(EXECUTION_CODE, 3);
                                } else if (errorCode2 == VineKnownErrors.INVALID_SESSION.code || errorCode2 == VineKnownErrors.EXPIRED_SESSION.code) {
/* 3379 */                          bundle2.putInt(EXECUTION_CODE, 2);
                                } else if (errorCode2 == VineKnownErrors.CAPTCHA.code) {
/* 3383 */                          bundle2.putInt(EXECUTION_CODE, 4);
/* 3384 */                          bundle2.putString("captcha_url", error.data);
                                }
                            }
/* 3411 */                  return bundle2;
                        }
/* 3389 */              bundle2.putInt(STATUS_CODE, statusCode);
/* 3390 */              if (!TextUtils.isEmpty(reasonPhrase)) {
/* 3391 */                  bundle2.putString(REASON_PHRASE, reasonPhrase);
                        }
/* 3395 */              if (LOGGABLE) {
/* 3397 */                  if (reasonPhrase != null) {
/* 3398 */                      completeString = MessageFormatter.toStringMessage(" {} ({}) complete {}, {}", Integer.valueOf(actionCode), rid, Integer.valueOf(statusCode), reasonPhrase);
                            } else {
/* 3400 */                      completeString = MessageFormatter.toStringMessage(" {} ({}) - {}", Integer.valueOf(actionCode), rid, Integer.valueOf(statusCode), reasonPhrase);
                            }
/* 3402 */                  SLog.m464i(completeString);
/* 3403 */                  if (bundle2.size() == 1 && bundle2.containsKey(STATUS_CODE)) {
/* 3404 */                      this.mFileLogger.write(MessageFormatter.toStringMessage("{}\n", completeString), new Object[0]);
                            } else {
/* 3406 */                      this.mFileLogger.write(MessageFormatter.toStringMessage("{}\n {}\n", completeString, bundle2), new Object[0]);
                            }
                        }
/* 3410 */              bundle2.putInt(EXECUTION_CODE, 1);
                    } catch (VineAPI.InvalidContextForInitializationException e17) {
/* 629 */               CrashUtil.logException(e17, "Service was not ready for {}", Integer.valueOf(actionCode));
/* 630 */               Bundle bundle3 = new Bundle();
/* 631 */               bundle3.putInt(STATUS_CODE, -999);
                        bundle2 = bundle3;
                    }
/* 3411 */          return bundle2;
                }

/* 3414 */      private void performGetUsersMePrefetch(Context context, Bundle bundle) {
/* 3415 */          getUsersMe(bundle);
/* 3416 */          VineUser user = (VineUser) bundle.getParcelable("user");
/* 3417 */          if (user != null && user.avatarUrl != null) {
/* 3419 */              AppController.getInstance(context).getPhotoBitmap(ImageKey.newDownloadOnlyKey(user.avatarUrl));
                    }
                }

/* 3425 */      private ExecutionPair fetchAcitivity(long sessionOwnerId, Context context, Bundle b) {
/* 3426 */          int page = b.getInt("page", 1);
/* 3427 */          long anchor = b.getLong(EXTRA_ANCHOR, 0L);
/* 3428 */          boolean getFollowRequests = b.getBoolean(EXTRA_GET_FOLLOW_REQUESTS, false);
/* 3430 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications");
VineAPI.addParam(url, VineAPI.RESOURCE_GROUPED, 1);
/* 3433 */          VineAPI.addParam(url, EXTRA_CLEAR, b.getBoolean(EXTRA_CLEAR) ? 1 : 0);
/* 3434 */          VineAPI.addParam(url, "page", page);
/* 3435 */          VineAPI.addAnchor(url, anchor);
/* 3436 */          VineAPI.addParam(url, "size", 20);
/* 3438 */          VineParserReader vp = VineParserReader.createParserReader(28);
/* 3439 */          Log.d("VineNetwork", "notifications request userId=" + sessionOwnerId + " url=" + url.toString());
/* 3440 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(context, url, this.mApi, vp);
/* 3442 */          assignPollingHeader(op, b);
/* 3443 */          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 3444 */          op.execute();
/* 3446 */          if (op.isOK()) {
/* 3447 */              VinePagedData<VineEverydayNotification> results = (VinePagedData) vp.getParsedObject();
/* 3448 */              // Keep the notification payload binder-safe. The old client only needs
/* 3449 */              // the actor, text, type and timestamp to render this screen; nested posts
/* 3450 */              // can be fetched separately when the row is opened.
/* 3451 */              if (results != null && results.items != null) {
/* 3452 */                  for (VineEverydayNotification notification : results.items) {
/* 3453 */                      notification.post = null;
/* 3454 */                      notification.entities = null;
/* 3455 */                  }
/* 3456 */              }
/* 3457 */              b.putParcelable("notifications", results);
                    }
/* 3450 */          Log.d("VineNetwork", "notifications response status=" + op.statusCode + " reason=" + op.statusPhrase + " parsed=" + (vp.getParsedObject() != null));
/* 3451 */          if (getFollowRequests) {
/* 3452 */              StringBuilder followUrl = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionOwnerId), "notifications", VineAPI.RESOURCE_FOLLOW_REQUESTS);
/* 3456 */              VineAPI.addParam(followUrl, "page", 1);
/* 3457 */              VineAPI.addParam(followUrl, "size", 20);
/* 3459 */              VineParserReader followVp = VineParserReader.createParserReader(9);
/* 3462 */              NetworkOperation followOp = this.mNetOpFactory.createBasicAuthGetRequest(context, followUrl, this.mApi, followVp).execute();
/* 3466 */              if (followOp.isOK()) {
/* 3467 */                  VinePagedData<VineSingleNotification> followNotifs = (VinePagedData) followVp.getParsedObject();
/* 3469 */                  b.putParcelable(EXTRA_FOLLOW_REQUESTS, followNotifs);
                        }
                    }
/* 3472 */          return new ExecutionPair(vp, op, null);
                }

/* 3475 */      private ExecutionPair fetchActivityCounts(long sessionId, Context context, Bundle b) {
                    VineActivityCounts activityCounts;
/* 3476 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", Long.valueOf(sessionId), VineAPI.RESOURCE_ACTIVITY_COUNTS);
/* 3478 */          VineAPI.addParam(url, VineAPI.RESOURCE_GROUPED, 1);
/* 3480 */          VineParserReader vp = VineParserReader.createParserReader(26);
/* 3481 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(context, url, this.mApi, vp);
/* 3484 */          assignPollingHeader(op, b);
/* 3485 */          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 3486 */          op.execute();
/* 3488 */          if (op.isOK() && (activityCounts = (VineActivityCounts) vp.getParsedObject()) != null) {
/* 3491 */              b.putInt(EXTRA_MESSAGES_COUNT, activityCounts.messages);
/* 3492 */              b.putInt(EXTRA_NOTIFICATIONS_COUNT, activityCounts.notifications);
/* 3493 */              Intent countIntent = new Intent();
/* 3494 */              countIntent.putExtra(EXTRA_MESSAGES_COUNT, activityCounts.messages);
/* 3495 */              countIntent.putExtra(EXTRA_NOTIFICATIONS_COUNT, activityCounts.notifications);
/* 3496 */              countIntent.setAction(BC_ACTIVITY_COUNTS);
/* 3497 */              sendBroadcast(countIntent, CrossConstants.BROADCAST_PERMISSION);
/* 3498 */              updateBadgeCount(activityCounts.messages + activityCounts.notifications);
                    }
/* 3501 */          return new ExecutionPair(vp, op, null);
                }

/* 3504 */      private StringBuilder getUserUrl(long userId) {
                    return VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_PROFILES, Long.valueOf(userId));
                }

/* 3510 */      private void performActivityPrefetch(Context context, Bundle b, VineDatabaseHelper dbHelper, long sessionOwnerId) {
                    ArrayList<VineSingleNotification> arrayList;
                    ArrayList<VineEverydayNotification> arrayList2;
/* 3511 */          b.putBoolean(EXTRA_GET_FOLLOW_REQUESTS, true);
/* 3512 */          fetchAcitivity(sessionOwnerId, context, b);
/* 3513 */          VinePagedData<VineEverydayNotification> notifications = (VinePagedData) b.getParcelable("notifications");
/* 3514 */          VinePagedData<VineSingleNotification> followRequests = (VinePagedData) b.getParcelable(EXTRA_FOLLOW_REQUESTS);
/* 3515 */          AppController ctr = AppController.getInstance(context);
/* 3516 */          ArrayList<ImageKey> images = new ArrayList<>();
/* 3517 */          if (notifications != null && (arrayList2 = notifications.items) != null) {
                        for (VineEverydayNotification notification : arrayList2) {
/* 3521 */                  if (notification.user != null && notification.user.avatarUrl != null) {
/* 3522 */                      images.add(ImageKey.newDownloadOnlyKey(notification.user.avatarUrl));
                            }
/* 3524 */                  if (notification.post != null && notification.post.thumbnailUrl != null) {
/* 3525 */                      images.add(ImageKey.newDownloadOnlyKey(notification.post.thumbnailUrl));
                            }
                        }
                    }
/* 3530 */          if (followRequests != null && (arrayList = followRequests.items) != null) {
                        for (VineSingleNotification notification2 : arrayList) {
/* 3534 */                  if (notification2.thumbnailUrl != null) {
/* 3535 */                      images.add(ImageKey.newDownloadOnlyKey(notification2.thumbnailUrl));
                            }
                        }
                    }
                    for (ImageKey key : images) {
/* 3541 */              ctr.getPhotoBitmap(key);
                    }
                }

/* 3546 */      private void performTimeLinePrefetch(Bundle b, VineDatabaseHelper dbHelper, String[] topUsagesTimelineEndpoints) {
                    for (String endpoint : topUsagesTimelineEndpoints) {
/* 3548 */              NetworkOperation op = getPosts(new StringBuilder(VineAPI.getInstance(this).buildUrl(endpoint)), -1, 10, dbHelper, 0L, b, true);
/* 3549 */              if (op.isOK()) {
/* 3550 */                  performTimeLinePrefetch(b);
                        }
                    }
                }

/* 3555 */      private void performTimeLinePrefetch(Bundle b) {
/* 3556 */          ArrayList<VinePost> posts = b.getParcelableArrayList("posts");
/* 3557 */          AppController appController = AppController.getInstance(this);
/* 3558 */          if (posts != null) {
/* 3559 */              ArrayList<ImageKey> images = new ArrayList<>();
/* 3560 */              ArrayList<VideoKey> videos = new ArrayList<>();
/* 3561 */              ArrayList<Long> users = new ArrayList<>();
                        for (VinePost post : posts) {
/* 3563 */                  if (post.avatarUrl != null) {
/* 3564 */                      images.add(ImageKey.newDownloadOnlyKey(post.avatarUrl));
                            }
/* 3566 */                  if (post.thumbnailUrl != null) {
/* 3567 */                      images.add(ImageKey.newDownloadOnlyKey(post.thumbnailUrl));
                            }
/* 3569 */                  VideoKey videoKey = Util.getVideoRequestKey(post, false, Integer.MAX_VALUE);
/* 3570 */                  if (videoKey != null) {
/* 3571 */                      videoKey.setDownloadOnly(true);
/* 3572 */                      videos.add(videoKey);
                            }
/* 3574 */                  users.add(Long.valueOf(post.userId));
                        }
                        for (ImageKey key : images) {
/* 3578 */                  appController.getPhotoBitmap(key);
                        }
                        for (VideoKey key2 : videos) {
/* 3582 */                  appController.getVideoFilePath(key2);
                        }
                        for (Long userId : users) {
/* 3586 */                  StringBuilder url = getUserUrl(userId.longValue());
/* 3588 */                  VineParserReader vp = VineParserReader.createParserReader(2);
/* 3589 */                  NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
/* 3592 */                  assignPollingHeader(op, b);
/* 3593 */                  assignCachePolicy(op, b, UrlCachePolicy.NETWORK_ONLY);
/* 3594 */                  op.execute();
/* 3595 */                  if (op.isOK()) {
/* 3596 */                      VineUser user = (VineUser) vp.getParsedObject();
/* 3597 */                      b.putParcelable("user", user);
                            }
                        }
                    }
                }

/* 3603 */      private static UrlCachePolicy assignCachePolicy(NetworkOperation op, Bundle b, UrlCachePolicy defaultPolicy) {
/* 3604 */          UrlCachePolicy cp = (UrlCachePolicy) b.getParcelable(EXTRA_CACHE_POLICY);
/* 3605 */          if (cp == null) {
/* 3606 */              cp = defaultPolicy;
/* 3607 */              b.putParcelable(EXTRA_CACHE_POLICY, defaultPolicy);
                    }
/* 3609 */          op.setCachePolicy(cp);
/* 3610 */          return cp;
                }

/* 3613 */      private static void assignPollingHeader(NetworkOperation op, Bundle b) {
/* 3614 */          VineAPI.addPollingHeader(op, b.getBoolean(EXTRA_IS_POLLING, false));
                }

/* 3624 */      private void updateBadgeCount(int count) {
/* 3625 */          ContentResolver resolver = getContentResolver();
                    try {
/* 3627 */              Cursor c = resolver.query(SAMSUNG_BADGER_URI, new String[]{PACKAGE, BADGE_COUNT}, null, null, null);
/* 3629 */              if (c != null) {
/* 3631 */                  ContentValues cv = new ContentValues();
/* 3632 */                  cv.put(PACKAGE, "co.vine.android");
/* 3633 */                  cv.put(CLASS, START_CLASS_NAME);
/* 3634 */                  cv.put(BADGE_COUNT, Integer.valueOf(count));
/* 3636 */                  if (c.getCount() > 0) {
/* 3637 */                      resolver.update(SAMSUNG_BADGER_URI, cv, BADGER_SELECTION, BADGER_ARGS);
                            } else {
/* 3639 */                      resolver.insert(SAMSUNG_BADGER_URI, cv);
                            }
/* 3641 */                  c.close();
                        }
                    } catch (Exception e) {
/* 3644 */              SLog.m459e("Ignore samsung error.", (Throwable) e);
                    }
                }

/* 3649 */      public synchronized NetworkOperation post(Context context, VineUpload upload, Bundle bundle) {
                    StringBuilder url;
                    VineParserReader vp;
                    NetworkOperation op = null;
                    try {
                        VineAPI api = VineAPI.getInstance(context);
/* 3651 */              JSONObject postBody = new JSONObject();
/* 3652 */              PostInfo info = upload.getPostInfo();
/* 3653 */              String path = upload.path;
/* 3654 */              boolean isPrivate = upload.isPrivate;
/* 3656 */              String[] uris = upload.getUris();
                        try {
/* 3661 */                  if (upload.path != null) {
/* 3662 */                      RecordSessionVersion version = UploadManager.getVersionFromPath(upload.path);
/* 3663 */                      String videoUrl = "" + uris[0];
/* 3664 */                      String thumbnailUrl = "" + uris[1];
/* 3665 */                      if (version == RecordSessionVersion.SW_WEBM) {
/* 3666 */                          postBody.put("videoWebmUrl", videoUrl);
                                } else {
/* 3668 */                          postBody.put("videoUrl", videoUrl);
                                }
/* 3670 */                      postBody.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, thumbnailUrl);
/* 3671 */                      postBody.put("model", Build.MODEL);
/* 3672 */                      postBody.put("device", Build.DEVICE);
/* 3674 */                      File metaDataFile = new File(RecordConfigUtils.getMetadataPath(upload.path));
/* 3675 */                      JSONArray cuts = null;
/* 3676 */                      if (metaDataFile.exists()) {
                                    try {
/* 3678 */                              cuts = new JSONArray(RecordConfigUtils.readForUpload(metaDataFile));
                                    } catch (Throwable e) {
/* 3680 */                              CrashUtil.logException(e);
                                    }
                                }
/* 3684 */                      if (cuts != null) {
/* 3685 */                          postBody.put("cuts", cuts);
                                }
                                try {
/* 3689 */                          postBody.put("version", String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode));
                                } catch (Exception e2) {
                                }
                            }
                        } catch (JSONException e3) {
/* 3695 */                  CrashUtil.logOrThrowInDebug(e3);
                        }
/* 3700 */              if (!isPrivate) {
/* 3701 */                  url = VineAPI.buildUponUrl(api.getBaseUrl(), "posts");
/* 3702 */                  vp = VineParserReader.createParserReader(12);
/* 3704 */                  if (info != null) {
                                try {
/* 3705 */                          if (!info.caption.isEmpty()) {
/* 3706 */                              SLog.m447d("Added description: {}.", info.caption);
/* 3707 */                              postBody.put("description", Util.cleanse(info.caption));
/* 3708 */                              if (info.entities != null && !info.entities.isEmpty()) {
/* 3709 */                                  postBody.put("entities", PostInfo.entitiesToJsonArray(info.entities));
                                        }
                                    } else {
/* 3712 */                              SLog.m446d("empty description.");
                                    }
                                    if (info.channelId > 0) {
/* 3715 */                              postBody.put("channelId", String.valueOf(info.channelId));
                                    }
/* 3717 */                          postBody.put("postToTwitter", info.postToTwitter ? "1" : "0");
/* 3718 */                          postBody.put("postToFacebook", info.postToFacebook ? "1" : "0");
                                } catch (JSONException e4) {
/* 3721 */                          CrashUtil.logOrThrowInDebug(e4);
                                }
                            }
                        } else {
/* 3724 */                  url = VineAPI.buildUponUrl(api.getBaseUrl(), "conversations");
/* 3725 */                  VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(context);
/* 3726 */                  long conversationId = dbHelper.getConversationRemoteId(upload.conversationRowId);
                            if (conversationId > 0) {
/* 3729 */                      url = VineAPI.buildUponUrl(url.toString(), Long.valueOf(conversationId));
                            } else if (info.recipients == null && upload.conversationRowId > 0) {
/* 3732 */                      info.recipients = dbHelper.getConversationRecipientsFromConversationRowId(upload.conversationRowId);
                            }
/* 3736 */                  vp = VineParserReader.createParserReader(24);
/* 3738 */                  if (info != null) {
                                try {
/* 3739 */                          if (!TextUtils.isEmpty(info.message)) {
/* 3740 */                              postBody.put("message", info.message);
                                    }
                                    if (info.postId > 0) {
/* 3743 */                              postBody.put("postId", info.postId);
                                    }
/* 3745 */                          postBody.put("created", info.created);
/* 3746 */                          if (info.maxLoops > 0 && info.maxLoops < Integer.MAX_VALUE) {
/* 3747 */                              postBody.put("maxLoops", info.maxLoops);
                                    }
/* 3749 */                          if (info.recipients != null && !info.recipients.isEmpty()) {
/* 3750 */                              postBody.put("to", PostInfo.recipientsToJsonArray(info.recipients));
                                    } else if (conversationId <= 0) {
/* 3752 */                              SLog.dWithTag(TAG, "VM post failed: The recipients array is empty and no conversation id");
                                    }
                                } catch (JSONException e5) {
/* 3758 */                          CrashUtil.logOrThrowInDebug(e5);
                                }
                            }
/* 3756 */                  addCountryParam(postBody);
                        }
/* 3762 */              SLog.m447d("Prefix {}", "");
/* 3763 */              SLog.m448d("Adding URI Params: {}, {}.", uris[0], uris[1]);
/* 3764 */              op = this.mNetOpFactory.createBasicAuthJsonPostRequest(context, url, api, postBody, vp).execute();
/* 3766 */              if (op.isOK()) {
/* 3767 */                  if (!isPrivate) {
/* 3768 */                      VinePostResponse vpr = (VinePostResponse) vp.getParsedObject();
/* 3770 */                      if (vpr == null) {
/* 3771 */                          op = null;
                                } else {
/* 3774 */                          Intent intent = new Intent(BC_ACTION_FETCH_AND_MERGE_POST);
/* 3775 */                          intent.putExtra("post", vpr);
/* 3776 */                          SLog.m446d("Regular video post successful, sending merge broadcast now");
/* 3777 */                          context.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                                }
                            } else {
/* 3779 */                      handleVinePrivateMessageResponses(context, upload, info, vp, bundle);
                            }
                        } else {
/* 3782 */                  VineError error = (VineError) vp.getParsedObject();
/* 3783 */                  if (error != null) {
/* 3784 */                      SLog.m457e("ERROR POSTING: {} {}", Integer.valueOf(op.statusCode), error.message);
/* 3785 */                      if (error.errorCode == VineKnownErrors.CAPTCHA.code) {
/* 3786 */                          upload.captchaUrl = error.data;
/* 3787 */                          if (upload.isPrivate) {
/* 3788 */                              VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, error.errorCode, getString(C0257R.string.message_failed_tap_to_retry));
                                    }
                                }
/* 3792 */                      if (upload.isPrivate) {
/* 3793 */                          VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, error.errorCode, getString(C0257R.string.message_failed_tap_to_retry));
                                }
                            } else {
/* 3797 */                      if (upload.isPrivate) {
/* 3798 */                          VineDatabaseHelper.getDatabaseHelper(this).setMessageError(upload.mergedMessageId, -1, getString(C0257R.string.message_failed_tap_to_retry));
                                }
/* 3801 */                      SLog.dWithTag(TAG, "Post failed: Unknown error while posting path=" + path);
                            }
                        }
                    } catch (Throwable th) {
                        CrashUtil.logException(th, "Post request failed", new Object[0]);
                    }
                    return op;
                }

/* 3807 */      private void handleVinePrivateMessageResponses(Context context, VineUpload upload, PostInfo info, VineParserReader vp, Bundle bundle) {
                    String text;
/* 3808 */          VinePrivateMessagePostResponseWithUsers serverResponse = (VinePrivateMessagePostResponseWithUsers) vp.getParsedObject();
/* 3809 */          ArrayList<VinePrivateMessageResponse> messageResponses = serverResponse.responses;
/* 3810 */          ArrayList<VineUser> users = serverResponse.users;
/* 3811 */          ArrayList<VineRecipient> recipients = serverResponse.recipients;
/* 3813 */          VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(context);
/* 3814 */          VinePrivateMessage premergedMessage = dbHelper.getMessageFromMessageRow(upload.mergedMessageId);
                    if (upload.mergedMessageId <= 0 || premergedMessage == null) {
/* 3817 */              CrashUtil.log("No local message to merge with.");
/* 3896 */              return;
                    }
/* 3821 */          if (users != null) {
/* 3822 */              dbHelper.mergeRecipientsWithUsersAndRemoveUnusedRecipients(premergedMessage.conversationRowId, recipients, users);
                    }
/* 3829 */          long messageIdToMerge = -1;
/* 3831 */          if (messageResponses == null || messageResponses.size() < 1) {
                        Object[] objArr = new Object[1];
                        objArr[0] = Boolean.valueOf(messageResponses == null);
/* 3833 */              CrashUtil.log("No vine private message responses, deleting the conversation and bailing. Response is null: {}", objArr);
/* 3834 */              dbHelper.deleteConversation(-1L, premergedMessage.conversationRowId);
/* 3896 */              return;
                    }
/* 3836 */          if (messageResponses.size() == 1) {
                        if (premergedMessage.conversationId <= 0) {
/* 3839 */                  premergedMessage.conversationRowId = dbHelper.mergeConversationWithLocalId(premergedMessage.conversationRowId, messageResponses.get(0).conversationId, premergedMessage.networkType);
                        }
/* 3843 */              messageIdToMerge = upload.mergedMessageId;
                    } else {
/* 3846 */              dbHelper.deleteConversation(-1L, premergedMessage.conversationRowId);
                    }
/* 3849 */          String smsFormatter = null;
/* 3850 */          String smsFooter = null;
/* 3851 */          String videoUrl = null;
/* 3852 */          String thumbnailUrl = null;
                    for (VinePrivateMessageResponse vpmr : messageResponses) {
/* 3854 */              if (vpmr != null) {
/* 3857 */                  if (vpmr.recipient != null && vpmr.recipient.key.equals("phoneNumber") && vpmr.shareUrl != null) {
/* 3860 */                      if (smsFormatter == null) {
/* 3861 */                          smsFormatter = getString(C0257R.string.sms_text);
                                }
/* 3864 */                      if (smsFooter == null) {
/* 3865 */                          smsFooter = getString(C0257R.string.sms_footer);
                                }
/* 3869 */                      if (TextUtils.isEmpty(info.message) || info.maxLoops > 0) {
/* 3870 */                          text = String.format(smsFormatter, vpmr.shareUrl);
                                } else {
/* 3872 */                          text = info.message + ": " + vpmr.shareUrl;
                                }
/* 3875 */                      String text2 = text + smsFooter;
/* 3877 */                      SLog.m448d("Send SMS to {} with text {}", vpmr.recipient.value, text2);
/* 3878 */                      SMSUtil.sendSMS(vpmr.recipient.value, text2);
                            }
/* 3881 */                  if (vpmr.videoUrl != null) {
/* 3882 */                      videoUrl = vpmr.videoUrl;
                            }
/* 3884 */                  if (vpmr.thumbnailUrl != null) {
/* 3885 */                      thumbnailUrl = vpmr.thumbnailUrl;
                            }
/* 3888 */                  handleSingleVinePrivateMessageResponse(context, dbHelper, vpmr, info, messageIdToMerge, premergedMessage.conversationRowId);
/* 3890 */                  if (vpmr.error != null) {
/* 3891 */                      bundle.putBoolean(EXTRA_KEEP_UPLOAD, true);
                            }
                        }
                    }
/* 3894 */          bundle.putString(EXTRA_VIDEO_URL, videoUrl);
/* 3895 */          bundle.putString("thumbnail_url", thumbnailUrl);
                }

/* 3900 */      private void handleSingleVinePrivateMessageResponse(Context context, VineDatabaseHelper dbHelper, VinePrivateMessageResponse vpmr, PostInfo info, long messageIdToMerge, long conversationRowIdToMerge) {
                    int errorCode;
                    String errorReason;
                    long conversationRowId;
/* 3901 */          long userRowId = dbHelper.getUserRowIdForUserRemoteId(AppController.getInstance(context).getActiveSessionReadOnly().getUserId());
/* 3905 */          if (vpmr.error != null) {
/* 3906 */              errorCode = vpmr.error.errorCode;
/* 3907 */              errorReason = vpmr.error.message;
                    } else {
/* 3909 */              errorCode = 0;
/* 3910 */              errorReason = null;
                    }
/* 3913 */          VinePrivateMessage vpm = new VinePrivateMessage(-1L, vpmr.conversationId, vpmr.messageId, userRowId, info.created, info.message, vpmr.videoUrl, vpmr.thumbnailUrl, 1, false, false, info.maxLoops, 0L, info.maxLoops > 0 && info.maxLoops < Integer.MAX_VALUE, 0L, info.postId, errorCode, errorReason);
                    if (messageIdToMerge > 0) {
/* 3921 */              long finalMessageRowId = dbHelper.mergeMessageWithMessageRow(messageIdToMerge, conversationRowIdToMerge, vpm);
/* 3923 */              dbHelper.updateConversationWithLastMessage(conversationRowIdToMerge, finalMessageRowId, vpm.created);
                    } else {
/* 3926 */              HashSet<Long> userRowIds = new HashSet<>();
/* 3928 */              userRowIds.add(Long.valueOf(vpmr.recipient.recipientId));
                        if (vpmr.conversationId > 0) {
/* 3933 */                  conversationRowId = dbHelper.getConversationRowId(vpmr.conversationId);
                        } else {
/* 3935 */                  conversationRowId = dbHelper.determineBestConversationRowIdForUserRowId(vpmr.recipient.recipientId);
                        }
                        if (conversationRowId <= 0) {
/* 3941 */                  conversationRowId = dbHelper.createConversationRowId(userRowIds, 1);
/* 3942 */                  dbHelper.mergeConversationWithLocalId(conversationRowId, vpmr.conversationId, 1);
                        }
/* 3945 */              long messageId = dbHelper.mergeMessage(conversationRowId, vpm);
/* 3946 */              dbHelper.updateConversationWithLastMessage(conversationRowId, messageId, vpm.created);
                    }
/* 3949 */          SLog.m446d("VM post successful, sending merge broadcast now");
/* 3950 */          Intent intent = new Intent(BC_ACTION_ALERT_RTC_NEW_MESSAGE);
/* 3951 */          intent.putExtra("conversation_id", vpm.conversationId);
/* 3952 */          intent.putExtra("message_id", vpm.messageId);
/* 3953 */          context.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                }

/* 3956 */      public void limitCommentAndLikesIfNeeded(StringBuilder url) {
/* 3957 */          if (BuildUtil.isOldDeviceOrLowEndDevice(this)) {
/* 3958 */              VineAPI.addParam(url, PARAM_COMMENT_MAX, 0);
/* 3959 */              VineAPI.addParam(url, PARAM_LIKE_MAX, 0);
                    }
                }

/* 3965 */      private StringBuilder generatePostFetchUrl(int type, long userId, int page, Bundle b, boolean prefetch) {
                    StringBuilder url;
/* 3966 */          String base = this.mApi.getBaseUrl();
                    switch (type) {
                        case 1:
/* 3969 */                  url = VineAPI.buildUponUrl(base, "timelines", VineAPI.RESOURCE_GRAPH);
                            break;
                        case 2:
/* 3974 */                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId));
/* 3976 */                  VineAPI.addParam(url, "reposts", 1);
/* 3977 */                  VineAPI.addParam(url, PARAM_COMMENT_OVERFLOW, "trunc");
                            break;
                        case 3:
/* 3987 */                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId), "likes");
                            break;
                        case 4:
/* 3992 */                  url = VineAPI.buildUponUrl(base, "timelines", "users", VineAPI.RESOURCE_TRENDING);
                            break;
                        case 5:
/* 3997 */                  url = VineAPI.buildUponUrl(base, "timelines", "popular");
                            break;
                        case 6:
/* 4002 */                  String tag = b.getString("tag_name");
/* 4003 */                  url = VineAPI.buildUponUrl(base, "timelines", "tags", tag);
                            break;
                        case 7:
                        default:
/* 4024 */                  throw new IllegalArgumentException("Tried to fetch timeline with unsupported type " + type);
                        case 8:
                        case 9:
/* 4009 */                  String channelId = b.getString("tag_name");
/* 4010 */                  String sort = b.getString("sort");
/* 4011 */                  url = VineAPI.buildUponUrl(base, "timelines", "channels", channelId, sort);
                            break;
                        case 10:
/* 3981 */                  url = VineAPI.buildUponUrl(base, "timelines", "users", Long.valueOf(userId));
/* 3983 */                  VineAPI.addParam(url, "reposts", 0);
                            break;
                        case 11:
/* 4016 */                  url = VineAPI.buildUponUrl(base, "timelines");
/* 4017 */                  Uri data = (Uri) b.getParcelable("data");
                            for (String pathSegment : data.getPathSegments()) {
/* 4019 */                      url = VineAPI.buildUponUrl(url.toString(), pathSegment);
                            }
                            break;
                    }
/* 4029 */          if (!prefetch) {
/* 4030 */              BehaviorManager.getInstance(this).onFetchPosts(url.toString(), page);
                    }
/* 4033 */          VineAPI.addParam(url, "page", page);
/* 4035 */          return url;
                }

/* 4040 */      private NetworkOperation getPosts(StringBuilder url, int type, int size, VineDatabaseHelper dbHelper, long anchor, Bundle bundle, boolean prefetch) {
                    VinePagedData<VinePost> pagedData;
/* 4041 */          VineAPI.addAnchor(url, anchor);
/* 4043 */          VineAPI.addParam(url, "size", size);
/* 4045 */          limitCommentAndLikesIfNeeded(url);
/* 4047 */          SLog.m447d("Fetching timeline: {}", url);
/* 4049 */          VineParserReader vp = VineParserReader.createParserReader(8);
/* 4050 */          Log.d("VineNetwork", "feed request type=" + type + " url=" + url.toString() + " userId=" + bundle.getLong(EXTRA_SESSION_OWNER_ID, 0L));
/* 4051 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
/* 4055 */          assignPollingHeader(op, bundle);
/* 4057 */          UrlCachePolicy resolvedCachePolicy = assignCachePolicy(op, bundle, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 4058 */          op.execute();
/* 4060 */          PrefetchManager.getInstance(this).onPostFetchOperationComplete(prefetch, op.getLastExecuteResult(), resolvedCachePolicy);
/* 4062 */          if (op.isOK() && (pagedData = (VinePagedData) vp.getParsedObject()) != null) {
/* 4065 */              if (type >= 0) {
/* 4066 */                  if (Util.isPopularTimeline(type)) {
/* 4067 */                      int count = 1;
/* 4068 */                      Cursor c = dbHelper.getOldestSortId(type);
/* 4069 */                      if (c != null) {
/* 4070 */                          if (c.moveToFirst()) {
/* 4071 */                              count = c.getInt(0) + 1;
                                    }
/* 4073 */                          c.close();
                                }
                                for (VinePost post : pagedData.items) {
                                    post.orderId = String.valueOf(count);
/* 4076 */                          count++;
                                }
                            }
/* 4080 */                  bundle.putInt("count", pagedData.items == null ? 0 : pagedData.items.size());
/* 4081 */                  bundle.putInt("size", pagedData.count);
/* 4082 */                  bundle.putParcelableArrayList("posts", pagedData.items);
/* 4083 */                  bundle.putBoolean(EXTRA_IN_MEMORY, true);
/* 4084 */                  bundle.putInt("next_page", pagedData.nextPage);
/* 4085 */                  bundle.putInt("previous_page", pagedData.previousPage);
/* 4086 */                  bundle.putLong(EXTRA_ANCHOR, pagedData.anchor);
/* 4087 */                  bundle.putString("title", pagedData.title);
/* 4088 */                  if (type == 8 || type == 9) {
/* 4089 */                      bundle.putParcelable("channels", pagedData.channel);
                            }
                        } else {
/* 4092 */                  bundle.putParcelableArrayList("posts", pagedData.items);
                        }
                    }
/* 4095 */          Log.d("VineNetwork", "feed response status=" + op.statusCode + " reason=" + op.statusPhrase + " parsed=" + (vp.getParsedObject() != null));
/* 4096 */          return op;
                }

/* 4099 */      private VineConversation getConversationWithRemoteId(long sessionOwnerId, long conversationId, int pageType, VineDatabaseHelper dbHelper, boolean prefetch, int networkTYpe) {
                    VinePagedData<VinePrivateMessage> data;
/* 4100 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations", Long.valueOf(conversationId));
                    switch (pageType) {
                        case 1:
                        case 2:
/* 4106 */                  VineAPI.addParam(url, "page", 1);
                            break;
                        case 3:
/* 4109 */                  long page = dbHelper.getNextPageCursor(7, 0, String.valueOf(conversationId), false);
                            if (page > 0) {
/* 4112 */                      VineAPI.addParam(url, "page", page);
                            }
                            break;
                    }
/* 4118 */          VineAPI.addParam(url, "prefetch", prefetch ? 1 : 0);
/* 4120 */          VineParserReader vp = VineParserReader.createParserReader(22);
/* 4121 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp).execute();
/* 4126 */          if (op.isOK() && (data = (VinePagedData) vp.getParsedObject()) != null && data.items != null && data.items.size() > 0) {
/* 4129 */              VineConversation conversation = new VineConversation(conversationId, data.lastMessage, data.items, data.unreadMessageCount);
                        try {
/* 4132 */                  long conversationLocalObjectId = dbHelper.mergeConversation(sessionOwnerId, conversation, networkTYpe, data.nextPage, data.previousPage, data.anchor);
/* 4135 */                  if (data.nextPage <= 0) {
/* 4136 */                      dbHelper.markLastMessage(conversationLocalObjectId);
                            }
/* 4138 */                  conversation.conversationObjectId = conversationLocalObjectId;
/* 4147 */                  return conversation;
                        } catch (IOException e) {
/* 4141 */                  if (LOGGABLE) {
/* 4142 */                      e.printStackTrace();
                            }
                        }
                    }
/* 4147 */          return null;
                }

/* 4152 */      private NetworkOperation getInbox(long sessionOwnerId, int pageType, int actionCode, String key, VineDatabaseHelper dbHelper, Bundle b) throws IOException {
                    int userGroup;
/* 4153 */          int networkType = b.getInt("network", -1);
/* 4155 */          int page = 1;
                    switch (pageType) {
                        case 1:
                        case 2:
/* 4159 */                  page = 1;
                            break;
                        case 3:
/* 4163 */                  int previous = dbHelper.getPreviousPageCursor(6, networkType, null, true);
/* 4165 */                  if (previous > 0) {
/* 4166 */                      page = previous;
                            }
                            break;
                    }
/* 4178 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "conversations");
/* 4181 */          if (networkType == -1) {
/* 4182 */              throw new IllegalArgumentException("Invalid network type.");
                    }
/* 4185 */          VineParserReader vp = VineParserReader.createParserReader(25);
/* 4187 */          if (1 == networkType) {
/* 4188 */              userGroup = 10;
                    } else {
/* 4190 */              VineAPI.addParam(url, PARAM_INBOX, CommonUtil.FIELD_TYPE_OTHER);
/* 4191 */              userGroup = 11;
                    }
/* 4194 */          VineAPI.addParam(url, "page", page);
/* 4195 */          VineAPI.addParam(url, "size", 10);
/* 4197 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp, key).execute();
/* 4201 */          if (op.isOK()) {
/* 4202 */              VinePagedData<VineConversation> data = (VinePagedData) vp.getParsedObject();
/* 4204 */              if (data.items != null && !data.items.isEmpty()) {
/* 4205 */                  dbHelper.mergeInbox(sessionOwnerId, data, data.nextPage, data.previousPage, data.anchor, networkType, userGroup);
/* 4208 */                  b.putInt("count", data.items.size());
/* 4209 */                  if (data.nextPage <= 0) {
/* 4210 */                      dbHelper.markLastConversation(networkType);
                            }
                        }
                    }
/* 4215 */          return op;
                }

/* 4218 */      public void addCountryParam(ArrayList<BasicNameValuePair> params) {
/* 4219 */          params.add(new BasicNameValuePair(PARAM_LOCALE, Util.getCountryCode()));
                }

/* 4222 */      public void addCountryParam(HashMap<String, Object> params) {
/* 4223 */          params.put(PARAM_LOCALE, Util.getCountryCode());
                }

/* 4226 */      public void addCountryParam(JSONObject params) throws JSONException {
/* 4227 */          params.put(PARAM_LOCALE, Util.getCountryCode());
                }

/* 4236 */      private NetworkOperation postComment(long postId, long repostId, long userId, String username, String comment, String avatarUrl, ArrayList<VineEntity> entities, VineDatabaseHelper dbHelper, Bundle b) {
/* 4237 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments");
/* 4239 */          JSONObject postBody = new JSONObject();
                    try {
/* 4241 */              postBody.put("comment", comment);
/* 4242 */              JSONArray jsonEntities = new JSONArray();
/* 4243 */              Iterator<VineEntity> it = entities.iterator();
                        while (it.hasNext()) {
/* 4244 */                  jsonEntities.put(it.next().toJsonObject());
                        }
/* 4246 */              if (jsonEntities.length() > 0) {
/* 4247 */                  postBody.put("entities", jsonEntities);
                        }
                        if (repostId > 0) {
/* 4250 */                  postBody.put(PARAM_REPOST_ID, repostId);
                        }
                    } catch (JSONException e) {
/* 4253 */              CrashUtil.logOrThrowInDebug(e);
                    }
/* 4256 */          VineParserReader vp = VineParserReader.createParserReader(7);
/* 4257 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthJsonPostRequest(this, url, this.mApi, postBody, vp).execute();
/* 4262 */          if (op.isOK()) {
/* 4263 */              VineComment vc = (VineComment) vp.getParsedObject();
/* 4263a */             if (vc == null) {
/* 4264a */                 vc = new VineComment(0L, comment, avatarUrl, null, System.currentTimeMillis(), null, userId, username, false, 0L, new ArrayList<VineEntity>(), false);
                            }
/* 4264 */              vc.postId = postId;
/* 4265 */              vc.userId = userId;
/* 4266 */              vc.username = username;
/* 4267 */              vc.avatarUrl = avatarUrl;
/* 4269 */              int offsetCount = 0;
                        if (entities == null) {
/* 4269a */                 entities = new ArrayList<>();
                            }
                        for (VineEntity entity : entities) {
/* 4271 */                  entity.start -= offsetCount;
/* 4274 */                  if ("tag".equals(entity.type)) {
/* 4275 */                      offsetCount++;
/* 4276 */                      String plainTitle = VineTypeAhead.getPlainTag(entity.title);
/* 4277 */                      comment = comment.replaceFirst(entity.title, plainTitle);
/* 4278 */                      entity.title = plainTitle;
/* 4279 */                      dbHelper.mergeSuggestedTag(new VineTag(plainTitle, entity.f96id));
                            }
/* 4281 */                  entity.end -= offsetCount;
                        }
/* 4283 */              vc.comment = comment;
/* 4284 */              vc.entities = entities;
/* 4285 */              vc.timestamp = System.currentTimeMillis();
/* 4286 */              b.putParcelable(EXTRA_COMMENT_OBJ, vc);
                    }
/* 4288 */          return op;
                }

/* 4292 */      private NetworkOperation deleteComment(long postId, long repostId, String commentId, VineDatabaseHelper dbHelper) {
/* 4293 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments", commentId);
                    if (repostId > 0) {
/* 4296 */              VineAPI.addParam(url, PARAM_REPOST_ID, repostId);
                    }
/* 4298 */          VineParserReader vp = VineParserReader.createParserReader(7);
/* 4300 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthDeleteRequest(this, url, this.mApi, vp).execute();
/* 4304 */          return op;
                }

/* 4307 */      private NetworkOperation getClientProfile() {
/* 4308 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), VineAPI.RESOURCE_CLIENT_PROFILE);
/* 4310 */          SLog.m481w("Updating device profile for {} {} {}.", new Object[]{Build.MODEL, Build.DEVICE, Integer.valueOf(Build.VERSION.SDK_INT)});
/* 4311 */          VineAPI.addParam(url, "model", Build.MODEL);
/* 4312 */          VineAPI.addParam(url, "device", Build.DEVICE);
/* 4313 */          VineAPI.addParam(url, "sdkVersion", Build.VERSION.SDK_INT);
/* 4314 */          VineAPI.addParam(url, "memory", ((ActivityManager) getSystemService(HomeTabActivity.TAG_ACTIVITY)).getLargeMemoryClass());
/* 4316 */          VineParserReader vp = VineParserReader.createParserReader(13);
/* 4318 */          NetworkOperation op = this.mNetOpFactory.createGetRequest(this, url, vp, this.mApi);
/* 4320 */          if (op.isOK()) {
/* 4321 */              JSONObject object = (JSONObject) vp.getParsedObject();
/* 4322 */              if (object != null) {
/* 4323 */                  SLog.m447d("Got client profile {}.", object);
/* 4324 */                  AppImpl.getInstance().updateRecordConfigs(getApplicationContext(), object);
                        } else {
/* 4326 */                  SLog.m446d("Error getting client profile.");
                        }
                    } else {
/* 4329 */              SLog.m447d("Failed to update client profile: {}.", Integer.valueOf(op.statusCode));
                    }
/* 4331 */          return op;
                }

/* 4334 */      public static Intent getPrefetchIntent(Context context, boolean manual) {
/* 4335 */          Intent intent = new Intent(context, (Class<?>) VineService.class);
/* 4336 */          intent.setAction(ACTION_PREFETCH);
/* 4337 */          intent.putExtra(EXTRA_MANUAL, manual);
/* 4338 */          return intent;
                }

/* 4342 */      private NetworkOperation getComments(long postId, int page, long anchor, int size, String key, Bundle bundle, VineDatabaseHelper dbHelper) {
/* 4343 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "posts", Long.valueOf(postId), "comments");
/* 4346 */          VineAPI.addParam(url, "page", page);
/* 4347 */          VineAPI.addAnchor(url, anchor);
/* 4348 */          VineAPI.addParam(url, "size", size);
/* 4350 */          VineParserReader vp = VineParserReader.createParserReader(6);
/* 4352 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp).execute();
/* 4355 */          if (op.isOK()) {
/* 4356 */              VinePagedData<VineComment> comments = (VinePagedData) vp.getParsedObject();
/* 4357 */              bundle.putInt("next_page", comments.nextPage);
/* 4358 */              bundle.putLong(EXTRA_ANCHOR, comments.anchor);
/* 4359 */              bundle.putInt("previous_page", comments.previousPage);
/* 4360 */              bundle.putParcelableArrayList("comments", comments.items);
                    }
/* 4362 */          return op;
                }

/* 4365 */      private NetworkOperation getUsersMe(Bundle b) {
                    VineUser user;
/* 4366 */          StringBuilder url = VineAPI.buildUponUrl(this.mApi.getBaseUrl(), "users", VineAPI.RESOURCE_ME);
/* 4369 */          VineParserReader vp = VineParserReader.createParserReader(2);
/* 4371 */          NetworkOperation op = this.mNetOpFactory.createBasicAuthGetRequest(this, url, this.mApi, vp);
/* 4375 */          assignPollingHeader(op, b);
/* 4376 */          assignCachePolicy(op, b, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 4377 */          op.execute();
/* 4378 */          if (op.isOK() && (user = (VineUser) vp.getParsedObject()) != null) {
/* 4381 */              VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(this);
/* 4382 */              user.f99id = dbHelper.mergeUserAndGetResultingRowId(user);
/* 4383 */              b.putParcelable("user", user);
                    }
/* 4386 */          return op;
                }

/* 4396 */      void attemptStop(int startId) {
/* 4397 */          this.mStartIds.put(Integer.valueOf(startId), true);
                    for (Map.Entry<Integer, Boolean> entry : this.mStartIds.entrySet()) {
/* 4401 */              if (!entry.getValue().booleanValue()) {
/* 4411 */                  return;
                        }
                    }
                    for (Integer key : this.mStartIds.keySet()) {
/* 4408 */              stopSelf(key.intValue());
                    }
/* 4410 */          this.mStartIds.clear();
                }

/* 4420 */      private class ResponderRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final String mReasonPhrase;
                    private final VineServiceResponder mResponder;
                    private final int mStartId;
                    private final int mStatusCode;

/* 4421 */          public ResponderRunnable(VineServiceResponder responder, int actionCode, int statusCode, String reasonPhrase, Bundle bundle, int startId) {
/* 4423 */              this.mResponder = responder;
/* 4424 */              this.mActionCode = actionCode;
/* 4425 */              this.mStatusCode = statusCode;
/* 4426 */              this.mReasonPhrase = reasonPhrase;
/* 4427 */              this.mBundle = bundle;
/* 4428 */              this.mStartId = startId;
                    }

                    @Override // java.lang.Runnable
/* 4431 */          public void run() {
/* 4432 */              if (this.mResponder != null) {
/* 4433 */                  this.mResponder.onServiceResponse(this.mActionCode, this.mStatusCode, this.mReasonPhrase, this.mBundle);
                        }
/* 4435 */              if (this.mStartId > 0) {
/* 4436 */                  VineService.this.attemptStop(this.mStartId);
                        }
                    }
                }

/* 4448 */      private class MessengerResponderRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final String mReasonPhrase;
                    private final Messenger mResponder;
                    private final int mStartId;
                    private final int mStatusCode;

/* 4449 */          public MessengerResponderRunnable(Messenger responder, int actionCode, int statusCode, String reasonPhrase, Bundle bundle, int startId) {
/* 4451 */              this.mResponder = responder;
/* 4452 */              this.mActionCode = actionCode;
/* 4453 */              this.mStatusCode = statusCode;
/* 4454 */              this.mReasonPhrase = reasonPhrase;
/* 4455 */              this.mBundle = bundle;
/* 4456 */              this.mStartId = startId;
                    }

                    @Override // java.lang.Runnable
/* 4459 */          public void run() {
/* 4460 */              if (this.mResponder != null) {
/* 4461 */                  Message msg = new Message();
/* 4462 */                  msg.what = this.mActionCode;
/* 4463 */                  msg.arg1 = this.mStatusCode;
/* 4464 */                  Bundle b = msg.getData();
/* 4465 */                  b.putString(VineService.EXTRA_REASON_PHRASE, this.mReasonPhrase);
/* 4466 */                  b.putAll(this.mBundle);
                            try {
/* 4468 */                      this.mResponder.send(msg);
                            } catch (Exception e) {
/* 4470 */                      CrashUtil.logException(e, "Error sending service response", new Object[0]);
                            }
                        }
/* 4473 */              if (this.mStartId > 0) {
/* 4474 */                  VineService.this.attemptStop(this.mStartId);
                        }
                    }
                }

/* 4486 */      private class ExecutionRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final Messenger mMessenger;
                    private final VineServiceResponder mResponder;
                    private final int mStartId;

/* 4487 */          public ExecutionRunnable(int startId, int actionCode, Bundle bundle, VineServiceResponder responder, Messenger messenger) {
/* 4489 */              this.mStartId = startId;
/* 4490 */              this.mActionCode = actionCode;
/* 4491 */              this.mBundle = bundle;
/* 4492 */              this.mResponder = responder;
/* 4493 */              this.mMessenger = messenger;
                    }

                    @Override // java.lang.Runnable
/* 4496 */          public void run() {
                        Runnable responderRunnable;
                        try {
/* 4497 */              Process.setThreadPriority(10);
/* 4498 */              Bundle result = VineService.this.executeAction(this.mActionCode, this.mBundle);
                        switch (result.getInt(VineService.EXECUTION_CODE)) {
                            case -999:
/* 4501 */                      CrashUtil.log("Service is not ready, {} needs to retry later.", Integer.valueOf(this.mActionCode));
                                break;
                            case 2:
/* 4505 */                      SLog.m446d("Session key was invalid. Refreshing session key and then will try again");
/* 4507 */                      result = VineService.this.executeAction(1000, this.mBundle);
                                switch (result.getInt(VineService.EXECUTION_CODE)) {
                                    case 2:
/* 4510 */                              SLog.m446d("Session key could not be refreshed. Aborting.");
/* 4511 */                              this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                        break;
                                    case 3:
/* 4515 */                              SLog.m446d("Session was logged out.");
/* 4516 */                              this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                        break;
                                    default:
/* 4521 */                              SLog.m446d("Session key successfully refreshed. Trying original action of " + this.mActionCode + " again");
/* 4523 */                              result = VineService.this.executeAction(this.mActionCode, this.mBundle);
                                        switch (result.getInt(VineService.EXECUTION_CODE)) {
                                            case 2:
/* 4526 */                                      SLog.m446d("Session key is still invalid. Aborting.");
/* 4527 */                                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                                break;
                                            case 3:
/* 4531 */                                      SLog.m446d("Session was logged out.");
/* 4532 */                                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                                break;
                                            default:
/* 4537 */                                      if (VineService.access$200()) {
/* 4538 */                                          this.mBundle.putBoolean(VineService.EXTRA_REFRESH_SESSION, true);
/* 4539 */                                          SLog.m446d("Session key refresh and retry complete.");
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
/* 4555 */                      SLog.m446d("Session was logged out.");
/* 4556 */                      this.mBundle.putBoolean(VineService.EXTRA_LOGGED_OUT, true);
                                break;
                            case 4:
/* 4550 */                      SLog.m446d("Captcha triggered");
/* 4551 */                      this.mBundle.putString("captcha_url", result.getString("captcha_url"));
                                break;
                        }
/* 4565 */              int statusCode = result.getInt(VineService.STATUS_CODE);
/* 4566 */              String reasonPhrase = result.getString(VineService.REASON_PHRASE);
/* 4569 */              if (this.mResponder != null) {
/* 4570 */                  responderRunnable = VineService.this.new ResponderRunnable(this.mResponder, this.mActionCode, statusCode, reasonPhrase, this.mBundle, this.mStartId);
                        } else if (this.mMessenger != null) {
/* 4573 */                  responderRunnable = VineService.this.new MessengerResponderRunnable(this.mMessenger, this.mActionCode, statusCode, reasonPhrase, this.mBundle, this.mStartId);
                        } else {
/* 4576 */                  responderRunnable = null;
                        }
/* 4579 */              if (responderRunnable != null) {
/* 4580 */                  VineService.access$300(VineService.this).post(responderRunnable);
              }
                        } catch (Throwable e) {

                            CrashUtil.logException(e, "Service execution failed", new Object[0]);
                        }
                    }
                }
            }


