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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 117 */   public class VineAPI implements HeaderInjecter {
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

/* 118 */       private VineAPI(Context context, Resources res) {
                    String clientSuffix;
/* 120 */           this.mContext = context;
/* 121 */           this.mApiResponseCache = Util.newTextCache(context, this);
/* 122 */           refreshHostUrls(null, res);
/* 123 */           refreshLocale();
/* 125 */           String versionName = BuildUtil.getVersionName(context);
/* 126 */           if (!TextUtils.isEmpty(versionName)) {
/* 128 */               if (BuildUtil.isExplore()) {
/* 129 */                   clientSuffix = "-gb";
                        } else if (!ClientFlagsHelper.getUseVinePlayer(context)) {
/* 131 */                   clientSuffix = "-ns";
                        } else {
/* 133 */                   clientSuffix = "";
                        }
/* 135 */               this.mXVineClientHeader = "android" + clientSuffix + "/" + versionName;
/* 139 */               return;
                    }
/* 137 */           this.mXVineClientHeader = null;
                }

/* 141 */       public void refreshHostUrls(VineClientFlags clientFlags, Resources resources) {
                    String savedBaseHost;
                    String savedRtcHost;
                    String savedMediaHost;
                    String savedExploreHost;
/* 142 */           Context context = this.mContext;
/* 143 */           if (resources == null) {
/* 144 */               resources = this.mContext.getResources();
                    }
/* 151 */           if (clientFlags == null) {
/* 152 */               SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 153 */               savedBaseHost = prefs.getString(ClientFlagsHelper.PREF_API_HOST, null);
/* 154 */               savedRtcHost = prefs.getString(ClientFlagsHelper.PREF_RTC_HOST, null);
/* 155 */               savedMediaHost = prefs.getString(ClientFlagsHelper.PREF_MEDIA_HOST, null);
/* 156 */               savedExploreHost = prefs.getString(ClientFlagsHelper.PREF_EXPLORE_HOST, null);
                    } else {
/* 158 */               savedBaseHost = clientFlags.apiHost;
/* 159 */               savedRtcHost = clientFlags.rtcHost;
/* 160 */               savedMediaHost = clientFlags.mediaHost;
/* 161 */               savedExploreHost = clientFlags.exploreHost;
                    }
/* 164 */           // Keep the packaged API host authoritative.  Older client-flags
/* 165 */           // responses can leave a stale host (for example trls.uk) in
/* 166 */           // preferences and prevent timeline requests from completing.
/* 167 */           this.mBaseUrl = BuildUtil.getBaseUrl(resources);
/* 165 */           this.mAmazonUrl = resources.getString(C0257R.string.amazon_s3);
/* 166 */           this.mTwitterUrl = resources.getString(C0257R.string.twitter_api);
/* 167 */           this.mRtcUrl = savedRtcHost != null ? SCHEME_HTTPS + savedRtcHost : BuildUtil.getRtcUrl(resources);
/* 168 */           this.mMediaUrl = savedMediaHost != null ? SCHEME_HTTPS + savedMediaHost : BuildUtil.getMediaUrl(resources);
/* 169 */           this.mExploreUrl = savedExploreHost != null ? SCHEME_HTTP + savedExploreHost : BuildUtil.getExploreUrl(resources);
/* 170 */           this.mExploreUrl += BuildUtil.getExplorePath(resources);
/* 171 */           this.mConfigUrl = BuildUtil.getConfigUrl(resources);
/* 173 */           SLog.m446d("hosts=" + this.mBaseUrl + ", " + this.mRtcUrl + ", " + this.mExploreUrl + ", " + this.mMediaUrl);
                }

/* 176 */       public static VineAPI getInstance(Context context) {
/* 177 */           if (mApi == null) {
/* 178 */               Resources res = context.getApplicationContext().getResources();
/* 179 */               if (res == null) {
/* 180 */                   res = context.getResources();
                        }
/* 182 */               if (res == null) {
/* 183 */                   throw new InvalidContextForInitializationException("Context does not have resources attached, Application Context is: " + context.getApplicationContext().getClass().getName());
                        }
/* 186 */               mApi = new VineAPI(context.getApplicationContext(), res);
                    }
/* 188 */           return mApi;
                }

/* 191 */       public TextCache getCacheStorage() {
/* 192 */           return this.mApiResponseCache;
                }

/* 196 */       public static class InvalidContextForInitializationException extends IllegalStateException {
/* 197 */           public InvalidContextForInitializationException(String s) {
/* 198 */               super(s);
                    }
                }

/* 202 */       public void refreshLocale() {
/* 203 */           String locale = Locale.getDefault().getCountry();
/* 205 */           if (!TextUtils.isEmpty(locale)) {
/* 206 */               this.mLocale = locale;
                    } else {
/* 208 */               this.mLocale = null;
                    }
                }

/* 212 */       public String getBaseUrl() {
/* 213 */           return this.mBaseUrl;
                }

/* 216 */       public String getAmazonUrl() {
/* 217 */           return this.mAmazonUrl;
                }

/* 220 */       public String getTwitterUrl() {
/* 221 */           return this.mTwitterUrl;
                }

/* 224 */       public String getRtcUrl() {
/* 225 */           return this.mRtcUrl;
                }

/* 228 */       public String getMediaUrl() {
/* 229 */           return this.mMediaUrl;
                }

/* 232 */       public String getExploreUrl() {
/* 233 */           return this.mExploreUrl;
                }

/* 236 */       public String getConfigUrl() {
/* 237 */           return this.mConfigUrl;
                }

                @Override // co.vine.android.network.HeaderInjecter
/* 241 */       public void addClientHeaders(NetworkOperation request) {
/* 242 */           if (!TextUtils.isEmpty(this.mXVineClientHeader)) {
/* 245 */               request.addHeader(X_VINE_CLIENT, this.mXVineClientHeader);
/* 246 */               String language = Util.getLocale();
/* 247 */               if (!TextUtils.isEmpty(language)) {
/* 248 */                   request.addHeader(ACCEPT_LANGUAGE_HEADER, language);
                        }
/* 250 */               if (!BuildUtil.isProduction()) {
/* 251 */                   request.addHeader(SECRET_AUTH_HEADER, getAuthHeaderSecret());
                        }
                    }
                }

/* 255 */       public String getVineClientHeader() {
/* 256 */           return this.mXVineClientHeader;
                }

/* 259 */       public void addSessionKeyAuthHeader(NetworkOperation op) {
/* 260 */           String key = getSessionKey();
/* 261 */           op.addHeader("vine-session-id", key);
/* 262 */           Log.d("VineNetwork", "auth header attached; present=" + (key != null && key.length() > 0) + ", length=" + (key == null ? 0 : key.length()) + ", userId=" + AppController.getInstance(this.mContext).getActiveSessionReadOnly().getUserId());
                }

/* 263 */       public static void addSessionKeyAuthHeader(NetworkOperation op, String key) {
/* 264 */           op.addHeader("vine-session-id", key);
                }

/* 274 */       public static void addContentTypeHeader(NetworkOperation op, String contentType) {
/* 275 */           op.addHeader("content-type", contentType);
                }

/* 278 */       public static void addPollingHeader(NetworkOperation op, boolean isPolling) {
/* 279 */           op.addHeader("X-Vine-Polling", String.valueOf(isPolling));
                }

/* 282 */       private String getSessionKey() {
/* 283 */           return AppController.getInstance(this.mContext).getActiveSessionReadOnly().getSessionKey();
                }

/* 286 */       public String getAuthHeaderSecret() {
/* 287 */           return this.mContext.getResources().getString(C0257R.string.app_secret);
                }

/* 297 */       public StringBuilder buildUrl(Object... paths) {
/* 298 */           return buildUponUrl(this.mBaseUrl, paths);
                }

/* 311 */       public static StringBuilder buildUponUrl(String baseUrl, Object... paths) {
/* 312 */           StringBuilder sb = new StringBuilder(baseUrl);
/* 313 */           if (paths != null) {
                        for (Object path : paths) {
/* 315 */                   sb.append('/');
/* 316 */                   sb.append(path);
                        }
                    }
/* 319 */           return sb;
                }

/* 329 */       public static void addParam(StringBuilder sb, String name, boolean value) {
/* 330 */           prefixParam(sb, name).append(value);
                }

/* 340 */       public static void addParam(StringBuilder sb, String name, int value) {
/* 341 */           prefixParam(sb, name).append(value);
                }

/* 351 */       public static void addParam(StringBuilder sb, String name, long value) {
/* 352 */           prefixParam(sb, name).append(value);
                }

/* 362 */       public static void addParam(StringBuilder sb, String name, double value) {
/* 363 */           prefixParam(sb, name).append(value);
                }

/* 373 */       public static void addParam(StringBuilder sb, String name, String value) {
/* 374 */           prefixParam(sb, name).append(encode(value));
                }

/* 378 */       public static int addParam(StringBuilder sb, String name, long[] values, int start, int max) {
/* 379 */           if (values == null || start >= values.length || max <= 0) {
                        return 0;
                    }
/* 380 */           prefixParam(sb, name);
/* 381 */           int end = Math.min(start + max, values.length);
                    for (int i = start; i < end; i++) {
/* 383 */               sb.append(values[i]);
/* 384 */               if (i < end - 1) {
/* 385 */                   sb.append(',');
                        }
                    }
/* 388 */           return end - start;
                }

/* 394 */       private static StringBuilder prefixParam(StringBuilder sb, String name) {
/* 395 */           if (sb.indexOf("?") == -1) {
/* 396 */               sb.append('?');
                    } else {
/* 398 */               sb.append('&');
                    }
/* 400 */           return sb.append(name).append('=');
                }

/* 404 */       public static String encode(String text) {
/* 405 */           if (text == null) {
/* 406 */               return "";
                    }
/* 408 */           if (ENCODER == null) {
/* 409 */               ENCODER = new PercentEscaper("-._~", false);
                    }
/* 411 */           return ENCODER.escape(text);
                }

/* 414 */       public static StringBuilder addAnchor(StringBuilder url, long anchor) {
                    if (anchor > 0) {
/* 416 */               addParam(url, "anchor", anchor);
                    }
/* 418 */           return url;
                }
            }
