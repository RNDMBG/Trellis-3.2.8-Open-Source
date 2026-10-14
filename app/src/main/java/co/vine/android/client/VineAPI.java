            package co.vine.android.client;

import android.content.Context;
import android.util.Log;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.text.TextUtils;
            import co.vine.android.C0257R;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.cache.text.TextCache;
            import co.vine.android.network.HeaderInjecter;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.google.gdata.util.common.base.PercentEscaper;
            import java.util.Locale;
            
            
   public class VineAPI implements HeaderInjecter {
                public static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
                private static PercentEscaper ENCODER = null;
                public static final int ERROR_VERIFICATION_REQURED = 616;
                public static final String HOST_VINE = "vine.co";
                public static final char PATH_SEPARATOR = '/';
                public static final String RESOURCE_ACCEPT_OUT_OF_NETWORK_CONVERSATIONS = "acceptsOutOfNetworkConversations";
                public static final String RESOURCE_ACTIVITY_COUNTS = "activityCounts";
                public static final String RESOURCE_AUTHENTICATE = "authenticate";
                public static final String RESOURCE_BLOCKED = "blocked";
                public static final String RESOURCE_CHANNELS = "channels";
                public static final String RESOURCE_CLIENT_FLAGS = "clientflags";
                public static final String RESOURCE_CLIENT_PROFILE = "clientprofiles";
                public static final String RESOURCE_COMMENTS = "comments";
                public static final String RESOURCE_COMPLAINTS = "complaints";
                public static final String RESOURCE_CONTACTS = "contacts";
                public static final String RESOURCE_CONVERSATIONS = "conversations";
                public static final String RESOURCE_DEVICE_TOKEN = "deviceToken";
                public static final String RESOURCE_EDITION = "edition";
                public static final String RESOURCE_EDITIONS = "editions";
                public static final String RESOURCE_EXPLICIT = "explicit";
                public static final String RESOURCE_FAVORITES = "favorites";
                public static final String RESOURCE_FEATURED = "featured";
                public static final String RESOURCE_FOLLOWERS = "followers";
                public static final String RESOURCE_FOLLOWING = "following";
                public static final String RESOURCE_FOLLOW_REQUESTS = "followRequests";
                public static final String RESOURCE_FRIENDS = "friends";
                public static final String RESOURCE_GRAPH = "graph";
                public static final String RESOURCE_GROUPED = "grouped";
                public static final String RESOURCE_IGNORED = "ignored";
                public static final String RESOURCE_LIKES = "likes";
                public static final String RESOURCE_LOOPS = "loops";
                public static final String RESOURCE_ME = "me";
                public static final String RESOURCE_MENU = "menu";
                public static final String RESOURCE_MESSAGES = "messages";
                public static final String RESOURCE_NOTIFICATIONS = "notifications";
                public static final String RESOURCE_ONBOARDING_SUGGESTED_FAVORITES = "onboardingSuggestedFavorites";
                public static final String RESOURCE_OUT_OF_NETWORK = "outOfNetwork";
                public static final String RESOURCE_POPULAR_NOW = "popular";
                public static final String RESOURCE_POSTS = "posts";
                public static final String RESOURCE_PREFERENCES = "preferences";
                public static final String RESOURCE_PROFILES = "profiles";
                public static final String RESOURCE_PROFILE_BACKGROUND = "profileBackground";
                public static final String RESOURCE_REACTIVATE = "reactivate";
                public static final String RESOURCE_REPOST = "repost";
                public static final String RESOURCE_REPOSTS = "reposts";
                public static final String RESOURCE_REQUESTS = "requests";
                public static final String RESOURCE_SEARCH = "search";
                public static final String RESOURCE_SECTIONED_LIST = "sectionedList";
                public static final String RESOURCE_SETTINGS = "settings";
                public static final String RESOURCE_SHARE = "share";
                public static final String RESOURCE_SHARED = "s";
                public static final String RESOURCE_SUGGESTED = "suggested";
                public static final String RESOURCE_SUGGESTED_FAVORITES = "suggestedFavorites";
                public static final String RESOURCE_TAGS = "tags";
                public static final String RESOURCE_TIMELINES = "timelines";
                public static final String RESOURCE_TRENDING = "trending";
                public static final String RESOURCE_TWITTER = "twitter";
                public static final String RESOURCE_USERS = "users";
                public static final String RESOURCE_VANITY = "vanity";
                public static final String RESOURCE_VERIFY_EMAIL = "verifyEmail";
                public static final String RESOURCE_VERIFY_PHONE_NUMBER = "verifyPhoneNumber";
                public static final String RESOURCE_VIEWED = "viewed";
                public static final String SCHEME_HTTP = "http://";
                public static final String SCHEME_HTTPS = "https://";
                public static final String SECRET_AUTH_HEADER = "X-Vine-Auth";
                public static final String X_VINE_CLIENT = "X-Vine-Client";
                private static VineAPI mApi;
                private String mAccountName;
                private String mAmazonUrl;
                private final TextCache mApiResponseCache;
                private String mBaseUrl;
                private String mConfigUrl;
                private final Context mContext;
                private String mExploreUrl;
                private String mLocale;
                private String mMediaUrl;
                private String mRtcUrl;
                private Session mSession;
                private String mTwitterUrl;
                private String mXVineClientHeader;

       private VineAPI(Context context, Resources res) {
                    String clientSuffix;
           this.mContext = context;
           this.mApiResponseCache = Util.newTextCache(context, this);
           refreshHostUrls(null, res);
           refreshLocale();
           String versionName = BuildUtil.getVersionName(context);
           if (!TextUtils.isEmpty(versionName)) {
               if (BuildUtil.isExplore()) {
                   clientSuffix = "-gb";
                        } else if (!ClientFlagsHelper.getUseVinePlayer(context)) {
                   clientSuffix = "-ns";
                        } else {
                   clientSuffix = "";
                        }
               this.mXVineClientHeader = "android" + clientSuffix + "/" + versionName;
               return;
                    }
           this.mXVineClientHeader = null;
                }

       public void refreshHostUrls(VineClientFlags clientFlags, Resources resources) {
                    String savedBaseHost;
                    String savedRtcHost;
                    String savedMediaHost;
                    String savedExploreHost;
           Context context = this.mContext;
           if (resources == null) {
               resources = this.mContext.getResources();
                    }
           if (clientFlags == null) {
               SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
               savedBaseHost = prefs.getString(ClientFlagsHelper.PREF_API_HOST, null);
               savedRtcHost = prefs.getString(ClientFlagsHelper.PREF_RTC_HOST, null);
               savedMediaHost = prefs.getString(ClientFlagsHelper.PREF_MEDIA_HOST, null);
               savedExploreHost = prefs.getString(ClientFlagsHelper.PREF_EXPLORE_HOST, null);
                    } else {
               savedBaseHost = clientFlags.apiHost;
               savedRtcHost = clientFlags.rtcHost;
               savedMediaHost = clientFlags.mediaHost;
               savedExploreHost = clientFlags.exploreHost;
                    }
           
           
           
           this.mBaseUrl = BuildUtil.getBaseUrl(resources);
           this.mAmazonUrl = resources.getString(C0257R.string.amazon_s3);
           this.mTwitterUrl = resources.getString(C0257R.string.twitter_api);
           this.mRtcUrl = savedRtcHost != null ? SCHEME_HTTPS + savedRtcHost : BuildUtil.getRtcUrl(resources);
           this.mMediaUrl = savedMediaHost != null ? SCHEME_HTTPS + savedMediaHost : BuildUtil.getMediaUrl(resources);
           this.mExploreUrl = savedExploreHost != null ? SCHEME_HTTP + savedExploreHost : BuildUtil.getExploreUrl(resources);
           this.mExploreUrl += BuildUtil.getExplorePath(resources);
           this.mConfigUrl = BuildUtil.getConfigUrl(resources);
           SLog.m446d("hosts=" + this.mBaseUrl + ", " + this.mRtcUrl + ", " + this.mExploreUrl + ", " + this.mMediaUrl);
                }

       public static VineAPI getInstance(Context context) {
           if (mApi == null) {
               Resources res = context.getApplicationContext().getResources();
               if (res == null) {
                   res = context.getResources();
                        }
               if (res == null) {
                   throw new InvalidContextForInitializationException("Context does not have resources attached, Application Context is: " + context.getApplicationContext().getClass().getName());
                        }
               mApi = new VineAPI(context.getApplicationContext(), res);
                    }
           return mApi;
                }

       public TextCache getCacheStorage() {
           return this.mApiResponseCache;
                }

       public static class InvalidContextForInitializationException extends IllegalStateException {
           public InvalidContextForInitializationException(String s) {
               super(s);
                    }
                }

       public void refreshLocale() {
           String locale = Locale.getDefault().getCountry();
           if (!TextUtils.isEmpty(locale)) {
               this.mLocale = locale;
                    } else {
               this.mLocale = null;
                    }
                }

       public String getBaseUrl() {
           return this.mBaseUrl;
                }

       public String getAmazonUrl() {
           return this.mAmazonUrl;
                }

       public String getTwitterUrl() {
           return this.mTwitterUrl;
                }

       public String getRtcUrl() {
           return this.mRtcUrl;
                }

       public String getMediaUrl() {
           return this.mMediaUrl;
                }

       public String getExploreUrl() {
           return this.mExploreUrl;
                }

       public String getConfigUrl() {
           return this.mConfigUrl;
                }

                @Override 
       public void addClientHeaders(NetworkOperation request) {
           if (!TextUtils.isEmpty(this.mXVineClientHeader)) {
               request.addHeader(X_VINE_CLIENT, this.mXVineClientHeader);
               String language = Util.getLocale();
               if (!TextUtils.isEmpty(language)) {
                   request.addHeader(ACCEPT_LANGUAGE_HEADER, language);
                        }
               if (!BuildUtil.isProduction()) {
                   request.addHeader(SECRET_AUTH_HEADER, getAuthHeaderSecret());
                        }
                    }
                }

       public String getVineClientHeader() {
           return this.mXVineClientHeader;
                }

       public void addSessionKeyAuthHeader(NetworkOperation op) {
           String key = getSessionKey();
           op.addHeader("vine-session-id", key);
           Log.d("VineNetwork", "auth header attached; present=" + (key != null && key.length() > 0) + ", length=" + (key == null ? 0 : key.length()) + ", userId=" + AppController.getInstance(this.mContext).getActiveSessionReadOnly().getUserId());
                }

       public static void addSessionKeyAuthHeader(NetworkOperation op, String key) {
           op.addHeader("vine-session-id", key);
                }

       public static void addContentTypeHeader(NetworkOperation op, String contentType) {
           op.addHeader("content-type", contentType);
                }

       public static void addPollingHeader(NetworkOperation op, boolean isPolling) {
           op.addHeader("X-Vine-Polling", String.valueOf(isPolling));
                }

       private String getSessionKey() {
           return AppController.getInstance(this.mContext).getActiveSessionReadOnly().getSessionKey();
                }

       public String getAuthHeaderSecret() {
           return this.mContext.getResources().getString(C0257R.string.app_secret);
                }

       public StringBuilder buildUrl(Object... paths) {
           return buildUponUrl(this.mBaseUrl, paths);
                }

       public static StringBuilder buildUponUrl(String baseUrl, Object... paths) {
           StringBuilder sb = new StringBuilder(baseUrl);
           if (paths != null) {
                        for (Object path : paths) {
                   sb.append('/');
                   sb.append(path);
                        }
                    }
           return sb;
                }

       public static void addParam(StringBuilder sb, String name, boolean value) {
           prefixParam(sb, name).append(value);
                }

       public static void addParam(StringBuilder sb, String name, int value) {
           prefixParam(sb, name).append(value);
                }

       public static void addParam(StringBuilder sb, String name, long value) {
           prefixParam(sb, name).append(value);
                }

       public static void addParam(StringBuilder sb, String name, double value) {
           prefixParam(sb, name).append(value);
                }

       public static void addParam(StringBuilder sb, String name, String value) {
           prefixParam(sb, name).append(encode(value));
                }

       public static int addParam(StringBuilder sb, String name, long[] values, int start, int max) {
           if (values == null || start >= values.length || max <= 0) {
                        return 0;
                    }
           prefixParam(sb, name);
           int end = Math.min(start + max, values.length);
                    for (int i = start; i < end; i++) {
               sb.append(values[i]);
               if (i < end - 1) {
                   sb.append(',');
                        }
                    }
           return end - start;
                }

       private static StringBuilder prefixParam(StringBuilder sb, String name) {
           if (sb.indexOf("?") == -1) {
               sb.append('?');
                    } else {
               sb.append('&');
                    }
           return sb.append(name).append('=');
                }

       public static String encode(String text) {
           if (text == null) {
               return "";
                    }
           if (ENCODER == null) {
               ENCODER = new PercentEscaper("-._~", false);
                    }
           return ENCODER.escape(text);
                }

       public static StringBuilder addAnchor(StringBuilder url, long anchor) {
                    if (anchor > 0) {
               addParam(url, "anchor", anchor);
                    }
           return url;
                }
            }
