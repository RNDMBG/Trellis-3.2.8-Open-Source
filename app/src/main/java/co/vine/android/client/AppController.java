            package co.vine.android.client;

            import android.app.Activity;
            import android.app.AlarmManager;
            import android.app.PendingIntent;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Point;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Process;
            import android.util.Log;
            import android.support.v4.app.Fragment;
            import android.text.TextUtils;
            import co.vine.android.AppImpl;
            import co.vine.android.R;
            import co.vine.android.LoginTwitterActivity;
            import co.vine.android.Settings;
            import co.vine.android.StartActivity;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.ComplaintMenuOption;
            import co.vine.android.api.ServerStatus;
            import co.vine.android.api.TwitterUser;
            import co.vine.android.api.VineChannel;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineEverydayNotification;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineLoopSubmissionResponse;
            import co.vine.android.api.VineNotificationSetting;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineRepost;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.PhotoImagesCache;
            import co.vine.android.cache.image.PhotoImagesListener;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoCache;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.cache.video.VideoListener;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.player.SaveVideoClicker;
            import co.vine.android.provider.SettingsManager;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.service.GCMRegistrationService;
            import co.vine.android.service.PendingAction;
            import co.vine.android.service.SessionChangedReceiver;
            import co.vine.android.service.VineService;
            import co.vine.android.service.VineServiceConnection;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.LoopManager;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.SLog;
            import com.facebook.AuthorizationClient;
            import com.facebook.SessionDefaultAudience;
            import com.facebook.SessionLoginBehavior;
            import com.facebook.SessionState;
            import com.facebook.SharedPreferencesTokenCachingStrategy;
            import com.flurry.android.FlurryAgent;
            import com.twitter.android.sdk.Twitter;
            import java.util.ArrayList;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.List;
            
            
   public class AppController implements PhotoImagesListener, VideoListener, com.facebook.Session.StatusCallback, VineServiceConnection.ServiceResponseHandler {
                public static final String ACTION_ABORT_ALL_REQUESTS = "abort_all_requests";
                private static final String ACTION_EDITION_UPDATED = "action_edition_updated";
                public static final IntentFilter ACTION_UPDATED_FILTER;
                private static final String BC_SEND_LOOPS = "co.vine.android.bc_send_loops";
                private static final long CLEANUP_INTERVAL = 3600000;
                private static final String KEY_FACEBOOK_TOKEN = "KEY_FACEBOOK_TOKEN";
                private static final long MIN_LOOP_COUNT_INTERVAL = 60000;
                private static final String PREF_LAST_USER_ID = "last_user_id";
                public static final String PUBLISH_ACTIONS = "publish_actions";
                public static final int REQUEST_CODE_TWITTER_SDK = 1;
                public static final int REQUEST_CODE_TWITTER_XAUTH = 2;
                private static final long SERVER_REFRESH_INTERVAL = 3600000;
                public static final boolean SINGLE_FACEBOOK_DIALOG;
                public static final int STATUS_CODE_CAPTCHA_FAIL = 455;
                private static final int TYPEAHEAD_DEFAULT_FETCH_THRESHOLD = 2;
                private static final int TYPEAHEAD_DEFAULT_MAX_COMPOSE = 50;
                public static final long TYPEAHEAD_DEFAULT_THROTTLE = 300;
                private static final int TYPEAHEAD_TAGS_FETCH_THRESHOLD = 3;
                private static AppController sInstance;
                final Context mContext;
                private final PhotoImagesCache mPhotoImagesCache;
                private ServerStatusRunnable mServerStatusRunnable;
                private final VineServiceConnection mServiceConnection;
                private final Twitter mTwitter;
                private final VideoCache<VineAPI> mVideoCache;
                private long mLoopCountSubmissionInterval = 60000;
                private final Handler mHandler = new Handler(Looper.getMainLooper());
                private ArrayList<AppSessionListener> mListeners = new ArrayList<>();
                
                
                private final java.util.HashMap<Long, Boolean> mRevineStates = new java.util.HashMap<>();
                private long mRevineVersion = 0L;

                static {
           SINGLE_FACEBOOK_DIALOG = Build.MODEL.length() == "SM-C115".length() && Build.MODEL.contains("SM-C11");
           ACTION_UPDATED_FILTER = new IntentFilter(ACTION_EDITION_UPDATED);
           if (SLog.sLogsOn) {
               RecordConfigUtils.FOLDER_ROOT_DIRECT_UPLOAD.mkdirs();
               SaveVideoClicker.setLongPressSaveDir(RecordConfigUtils.FOLDER_ROOT_DIRECT_UPLOAD);
                    }
                }

       public static synchronized AppController getInstance(Context context) {
                    try {
                        if (sInstance == null) {
                   sInstance = new AppController(context.getApplicationContext());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return sInstance;
                }

       private AppController(Context context) {
           this.mContext = context;
           Point p = SystemUtil.getDisplaySize(context);
           int maxImageSize = Math.max(p.x, p.y);
           this.mPhotoImagesCache = Util.newImageCache(context, maxImageSize, 31457280);
           this.mPhotoImagesCache.addListener(this);
           this.mVideoCache = Util.newVideoCache(context);
           this.mVideoCache.addListener(this);
           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
           this.mServerStatusRunnable = new ServerStatusRunnable();
           SLog.m465i("App Controller pid: {}", Integer.valueOf(Process.myPid()));
           this.mServiceConnection = new VineServiceConnection(this.mContext, this);
           context.bindService(new Intent(context, (Class<?>) VineService.class), this.mServiceConnection, 1);
           context.registerReceiver(new C03701(), new IntentFilter(BC_SEND_LOOPS), CrossConstants.BROADCAST_PERMISSION, null);
           refreshLoopSendingAlarm(System.currentTimeMillis());
           context.registerReceiver(new C03712(), new IntentFilter(UploadManager.BC_INVALIDATE_CACHE), CrossConstants.BROADCAST_PERMISSION, null);
                }

                
       class C03701 extends BroadcastReceiver {
           C03701() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               AppController.this.sendLoopCounts();
                    }
                }

                
       class C03712 extends BroadcastReceiver {
           C03712() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               SLog.m446d("Received invalidate cache broadcast");
               VideoCache.invalidateCache();
               PhotoImagesCache.invalidateCache();
                    }
                }

       public void refreshLoopSendingAlarm(long fisrtRunTime) {
           AlarmManager am = (AlarmManager) this.mContext.getSystemService("alarm");
           am.setRepeating(0, fisrtRunTime, this.mLoopCountSubmissionInterval, PendingIntent.getBroadcast(this.mContext, 0, new Intent(BC_SEND_LOOPS).setPackage(this.mContext.getPackageName()), 134217728));
                }

       public void setSessionKey(String key) {
           Session activeSession = VineAccountHelper.getCachedActiveSession();
           if (activeSession != null) {
               activeSession.setSessionKey(key);
                    }
                }

       public Twitter getTwitter() {
           return this.mTwitter;
                }

       public boolean isConnectedToFacebook(Activity activity) {
                    return getValidFacebookSession(activity, true) != null;
                }

       public com.facebook.Session getValidFacebookSession(Activity activity, boolean closeAfterwards) {
           com.facebook.Session session = com.facebook.Session.getActiveSession();
           boolean wasOpen = session != null;
           if (!wasOpen) {
               session = com.facebook.Session.openActiveSession(activity, false, (com.facebook.Session.StatusCallback) this);
                    }
           if (session == null || session.getState().isClosed()) {
               SLog.m446d("There were no Facebook session.");
                        return null;
                    }
           com.facebook.Session.setActiveSession(session);
           SessionState state = session.getState();
           SLog.m447d("Old Facebook Session state : {}", state);
           if (state == SessionState.CREATED_TOKEN_LOADED || state.isOpened()) {
               SLog.m446d("State is created or opened.");
               if (session.getPermissions().contains(PUBLISH_ACTIONS)) {
                   SLog.m446d("Permission is good.");
                   if (!wasOpen && closeAfterwards) {
                       session.close();
                            }
                   Date date = session.getExpirationDate();
                   if (date != null && date.getTime() < System.currentTimeMillis()) {
                       SLog.m446d("Session has expired.");
                                return null;
                            }
                   return session;
                        }
               session.closeAndClearTokenInformation();
               SLog.m446d("Permission missing.");
                        return null;
                    }
           session.close();
           SLog.m446d("Session state is invalid.");
                    return null;
                }

       public void connectToFacebookInitialize(Fragment fragment) {
           if (SINGLE_FACEBOOK_DIALOG) {
               connectToFacebookPublish(fragment);
               return;
                    }
           AuthorizationClient.CLEAR_COOKIE = true;
           Context context = this.mContext;
           com.facebook.Session session = new com.facebook.Session.Builder(context).setApplicationId(FacebookVineApp.APP_ID).setTokenCachingStrategy(new SharedPreferencesTokenCachingStrategy(context)).build();
           com.facebook.Session.setActiveSession(session);
           if (!session.isOpened()) {
               ArrayList<String> permissions = new ArrayList<>();
               permissions.add("public_profile");
               com.facebook.Session.OpenRequest openRequest = new com.facebook.Session.OpenRequest(fragment);
               openRequest.setPermissions((List<String>) permissions);
               openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
               session.openForRead(openRequest);
                    }
                }

       public void connectToFacebookPublish(Fragment fragment) {
           Context context = this.mContext;
           com.facebook.Session session = new com.facebook.Session.Builder(context).setApplicationId(FacebookVineApp.APP_ID).setTokenCachingStrategy(new SharedPreferencesTokenCachingStrategy(context)).build();
           com.facebook.Session.setActiveSession(session);
           if (!session.isOpened()) {
               ArrayList<String> permissions = new ArrayList<>();
               permissions.add(PUBLISH_ACTIONS);
               com.facebook.Session.OpenRequest openRequest = new com.facebook.Session.OpenRequest(fragment);
               openRequest.setDefaultAudience(SessionDefaultAudience.FRIENDS);
               openRequest.setPermissions((List<String>) permissions);
               openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
               session.openForPublish(openRequest);
                    }
                }

                @Override 
       public void call(com.facebook.Session session, SessionState state, Exception exception) {
           SLog.m451d("Session state has changed: {}.", (Object) session.getState().name(), (Throwable) exception);
           ArrayList<AppSessionListener> listeners = this.mListeners;
                    for (int i = listeners.size() - 1; i >= 0; i--) {
               listeners.get(i).onFacebookSessionChanged(session, state, exception);
                    }
                }

       private void onLogout() {
           if (!getLoginStatus().equals(Session.LoginStatus.LOGGED_OUT)) {
               String cachedKey = getActiveSession().getSessionKey();
               Util.getDefaultSharedPrefs(this.mContext).edit().clear().commit();
               clearDbAll();
               clearFileCache();
               clearAccount();
               this.mContext.startService(GCMRegistrationService.getUnregisterIntent(this.mContext, getActiveId(), cachedKey));
               Util.getDefaultSharedPrefs(this.mContext).edit().putLong(PREF_LAST_USER_ID, getActiveId()).commit();
               Session activeSession = VineAccountHelper.getCachedActiveSession();
               VineAccountHelper.removeAccount(this.mContext, activeSession.getLoginEmail());
               if (activeSession != null) {
                   activeSession.logout();
                        }
               Intent logoutIntent = SessionChangedReceiver.createSessionLogoutIntent();
               this.mContext.sendBroadcast(logoutIntent, CrossConstants.BROADCAST_PERMISSION);
               Intent i = AppImpl.getInstance().getClearNotificationsIntent(this.mContext);
               if (i != null) {
                   this.mContext.startService(i);
                        }
               StartActivity.toStart(this.mContext);
                    }
                }

       private String clearAccount() {
           Bundle b = createServiceBundle();
           return executeServiceAction(115, b);
                }

                
                
       void notifyListeners(String reqId, int actionCode, int statusCode, String reasonPhrase, Bundle b) {
                    ArrayList<VineChannel> channels;
           Context context = this.mContext;
                    switch (actionCode) {
                        case 2:
                            try {
                       VineLogin vl = loginComplete(VineAccountHelper.getCachedActiveSession(), statusCode, b);
                       int errorCode = b.getInt("error_code", -1);
                       Intent loginIntent = SessionChangedReceiver.createSessionLoginIntent();
                       this.mContext.sendBroadcast(loginIntent, CrossConstants.BROADCAST_PERMISSION);
                                for (AppSessionListener listener : this.mListeners) {
                           listener.onLoginComplete(VineAccountHelper.getCachedActiveSession(), reqId, statusCode, reasonPhrase, errorCode, vl);
                                }
                            } catch (VineLoggingException e) {
                       Util.showCenteredToast(this.mContext, R.string.failed_to_add_account_on_device);
                       CrashUtil.logException(e);
                      return;
                            }
                            break;
                        case 3:
                   onLogout();
                            break;
                        case 4:
                   VineLogin login = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
                   String password = b.getString(VineService.EXTRA_PASSWORD);
                   String accountName = b.getString(VineService.EXTRA_ACCOUNT_NAME);
                   String avatarUrl = b.getString("avatar_url");
                   Uri avatar = (Uri) b.getParcelable("uri");
                   String key = b.getString("s_key");
                   long userId = b.getLong(VineService.EXTRA_SESSION_OWNER_ID);
                   boolean uploaded = false;
                            for (AppSessionListener listener2 : this.mListeners) {
                       listener2.onSignUpComplete(reqId, statusCode, reasonPhrase, login, accountName, password, avatarUrl);
                       if (statusCode == 200 && !uploaded && avatar != null) {
                           uploaded = true;
                           Session session = getActiveSession();
                           session.setSessionKey(key);
                           session.setUserId(userId);
                           updateProfilePhoto(session, avatar);
                                }
                       Intent loginIntent2 = SessionChangedReceiver.createSessionLoginIntent();
                       this.mContext.sendBroadcast(loginIntent2, CrossConstants.BROADCAST_PERMISSION);
                            }
                            break;
                        case 5:
                            for (AppSessionListener listener3 : this.mListeners) {
                       listener3.onGetTwitterAuthUrlComplete(reqId, b.getString(VineService.EXTRA_LOAD_URL));
                            }
                            break;
                        case 6:
                            for (AppSessionListener listener4 : this.mListeners) {
                       listener4.onCheckTwitterComplete(reqId, statusCode, reasonPhrase, b.getInt("error_code", -1), b.getBoolean(VineService.EXTRA_ACCOUNT_EXISTS), (VineUser) b.getParcelable("user"), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN));
                            }
                   Intent loginIntent3 = SessionChangedReceiver.createSessionLoginIntent();
                   this.mContext.sendBroadcast(loginIntent3, CrossConstants.BROADCAST_PERMISSION);
                            break;
                        case 7:
                            for (AppSessionListener listener5 : this.mListeners) {
                       listener5.onGetTwitterUserComplete(reqId, statusCode, reasonPhrase, (TwitterUser) b.getParcelable(VineService.EXTRA_TWITTER_USER), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN));
                            }
                            break;
                        case 8:
                   VineLogin vl2 = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
                            for (AppSessionListener listener6 : this.mListeners) {
                       listener6.onTwitterxAuthComplete(reqId, statusCode, reasonPhrase, vl2);
                            }
                            break;
                        case 9:
                            for (AppSessionListener listener7 : this.mListeners) {
                       listener7.onResetPasswordComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 10:
                   VineUser meUser = (VineUser) b.getParcelable("user");
                   long ownerId = b.getLong(VineService.EXTRA_SESSION_OWNER_ID);
                   UrlCachePolicy policy = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                   if (meUser != null) {
                       SharedPreferences.Editor e2 = Util.getDefaultSharedPrefs(this.mContext).edit();
                       e2.putString(Settings.PREF_NAME, meUser.username);
                       e2.putString(Settings.PREF_DESCRIPTION, meUser.description);
                       e2.putString(Settings.PREF_LOCATION, meUser.location);
                       e2.putString(Settings.PREF_PHONE, meUser.phoneNumber);
                       e2.putString(Settings.PREF_EMAIL, meUser.email);
                       e2.putString(Settings.PREF_AVATAR_URL, meUser.avatarUrl);
                       e2.putInt(Settings.PREF_FOLLOWING_COUNT, meUser.followingCount);
                       e2.putInt(Settings.PREF_FOLLOWER_COUNT, meUser.followerCount);
                       e2.putInt(Settings.PREF_AUTHORED_POST_COUNT, meUser.authoredPostCount);
                       e2.putInt(Settings.PREF_POST_COUNT, meUser.postCount);
                       e2.putInt(Settings.PREF_LIKE_COUNT, meUser.likeCount);
                       e2.putLong(Settings.PREF_LOOP_COUNT, meUser.loopCount);
                       e2.putBoolean(Settings.PREF_TWITTER_CONNECTED, meUser.twitterConnected == 1);
                       e2.putBoolean(Settings.PREF_FOLLOW_EDITORS_PICKS, meUser.includePromoted == 1);
                       e2.putBoolean(Settings.PREF_PRIVATE, meUser.isPrivate());
                       e2.putBoolean(Settings.PREF_EMAIL_VERIFIED, meUser.isEmailVerified());
                       e2.putBoolean(Settings.PREF_PHONE_VERIFIED, meUser.isPhoneVerified());
                       e2.putLong(Settings.PREF_USER_ROW_ID, meUser.f99id);
                       if (meUser.edition != null) {
                           e2.putString(Settings.PREF_EDITION, meUser.edition);
                                }
                       e2.putBoolean(Settings.PREF_ACCEPT_OON, meUser.acceptsOutOfNetworkConversations);
                       e2.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, !meUser.disableAddressBook);
                       e2.putBoolean(Settings.PREF_EMAIL_DISCOVERABLE, !meUser.hiddenEmail);
                       e2.putBoolean(Settings.PREF_PHONE_DISCOVERABLE, !meUser.hiddenPhoneNumber);
                       e2.putBoolean(Settings.PREF_TWITTER_DISCOVERABLE, !meUser.hiddenTwitter);
                       int profileBackground = meUser.profileBackground < 0 ? Settings.DEFAULT_PROFILE_COLOR : meUser.profileBackground;
                       e2.putInt("profile_background", profileBackground);
                       e2.apply();
                            }
                            for (AppSessionListener listener8 : this.mListeners) {
                       listener8.onGetUsersMeComplete(reqId, statusCode, reasonPhrase, ownerId, meUser, policy);
                            }
                            break;
                        case 11:
                        case 22:
                        case 23:
                        case 56:
                        case 82:
                        case 110:
                   int count = b.getInt("count", 0);
                   int nextPage = b.getInt("next_page", 0);
                   int previousPage = b.getInt("previous_page", 0);
                   long anchor = b.getLong(VineService.EXTRA_ANCHOR, 0L);
                   ArrayList<VineUser> users = b.getParcelableArrayList("users");
                            for (AppSessionListener listener9 : this.mListeners) {
                       listener9.onGetUsersComplete(VineAccountHelper.getCachedActiveSession(), reqId, statusCode, reasonPhrase, count, users, nextPage, previousPage, anchor);
                            }
                            break;
                        case 12:
                   int next = b.getInt("next_page");
                   long anchor2 = b.getLong(VineService.EXTRA_ANCHOR);
                   ArrayList<VineComment> comments = b.getParcelableArrayList("comments");
                            for (AppSessionListener listener10 : this.mListeners) {
                       listener10.onGetCommentsComplete(reqId, statusCode, reasonPhrase, next, anchor2, comments);
                            }
                            break;
                        case 13:
                   onGetPostComplete(reqId, b, statusCode, reasonPhrase);
                            break;
                        case 14:
                   long id = b.getLong("post_id");
                   VineComment comment = (VineComment) b.getParcelable(VineService.EXTRA_COMMENT_OBJ);
                   String commentText = b.getString("comment");
                            for (AppSessionListener listener11 : this.mListeners) {
                       listener11.onPostCommentComplete(reqId, statusCode, reasonPhrase, id, commentText, comment);
                            }
                            break;
                        case 15:
                   int count2 = b.getInt("count", 0);
                   ArrayList<VineUser> users2 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener12 : this.mListeners) {
                       listener12.onGetTwitterFriendsComplete(reqId, statusCode, reasonPhrase, count2, users2);
                            }
                            break;
                        case 16:
                   int count3 = b.getInt("count", 0);
                   ArrayList<VineUser> users3 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener13 : this.mListeners) {
                       listener13.onGetAddressFriendsComplete(reqId, statusCode, reasonPhrase, count3, users3);
                            }
                            break;
                        case 17:
                            for (AppSessionListener listener14 : this.mListeners) {
                       listener14.onRemoveUsersComplete(reqId);
                            }
                            break;
                        case 18:
                   long userId2 = b.getLong(VineService.EXTRA_FOLLOW_ID);
                   fetchNewPosts(getActiveSession(), 10, userId2, 1, 1, 0L, false, String.valueOf(getActiveId()), null, null);
                   SharedPreferences prefs = Util.getDefaultSharedPrefs(this.mContext);
                   SharedPreferences.Editor e3 = prefs.edit();
                   e3.putInt(Settings.PREF_FOLLOWING_COUNT, prefs.getInt(Settings.PREF_FOLLOWING_COUNT, 0) + 1);
                   e3.commit();
                            for (AppSessionListener listener15 : this.mListeners) {
                       listener15.onFollowComplete(reqId, statusCode, reasonPhrase, userId2);
                            }
                            break;
                        case 19:
                   long userId3 = b.getLong(VineService.EXTRA_FOLLOW_ID);
                   removeFollowPosts(getActiveSession(), userId3);
                   SharedPreferences prefs2 = Util.getDefaultSharedPrefs(this.mContext);
                   SharedPreferences.Editor e4 = prefs2.edit();
                   int oldCount = prefs2.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
                   e4.putInt(Settings.PREF_FOLLOWING_COUNT, oldCount > 0 ? oldCount - 1 : 0);
                   e4.commit();
                            for (AppSessionListener listener16 : this.mListeners) {
                       listener16.onUnFollowComplete(reqId, statusCode, reasonPhrase, userId3);
                            }
                            break;
                        case 20:
                   VinePagedData<VineEverydayNotification> notifications = (VinePagedData) b.getParcelable("notifications");
                   VinePagedData<VineSingleNotification> followRequests = (VinePagedData) b.getParcelable(VineService.EXTRA_FOLLOW_REQUESTS);
                   UrlCachePolicy policyUsed = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                            for (AppSessionListener listener17 : this.mListeners) {
                       listener17.onGetActivityComplete(reqId, statusCode, reasonPhrase, notifications, followRequests, policyUsed);
                            }
                            break;
                        case 21:
                   VineUser user = (VineUser) b.getParcelable("user");
                   UrlCachePolicy policy2 = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                            for (AppSessionListener listener18 : this.mListeners) {
                       listener18.onGetUserComplete(reqId, statusCode, reasonPhrase, user, policy2);
                            }
                            break;
                        case 24:
                   long postId = b.getLong("post_id");
                   if (statusCode == 200) {
                       SharedPreferences prefs3 = Util.getDefaultSharedPrefs(this.mContext);
                       SharedPreferences.Editor e5 = prefs3.edit();
                       e5.putInt(Settings.PREF_LIKE_COUNT, prefs3.getInt(Settings.PREF_LIKE_COUNT, 0) + 1);
                       e5.commit();
                            }
                            for (AppSessionListener listener19 : this.mListeners) {
                       listener19.onLikePost(reqId, statusCode, reasonPhrase, postId);
                            }
                            break;
                        case 25:
                   long postId2 = b.getLong("post_id");
                   if (statusCode == 200) {
                       SharedPreferences prefs4 = Util.getDefaultSharedPrefs(this.mContext);
                       SharedPreferences.Editor e6 = prefs4.edit();
                       int oldCount2 = prefs4.getInt(Settings.PREF_LIKE_COUNT, 0);
                       e6.putInt(Settings.PREF_LIKE_COUNT, oldCount2 > 0 ? oldCount2 - 1 : 0);
                       e6.commit();
                            }
                            for (AppSessionListener listener20 : this.mListeners) {
                       listener20.onUnlikePost(reqId, statusCode, reasonPhrase, postId2);
                            }
                            break;
                        case 26:
                   String avatarUrl2 = b.getString("avatar_url");
                            for (AppSessionListener listener21 : this.mListeners) {
                       listener21.onUpdateProfileComplete(reqId, statusCode, reasonPhrase, avatarUrl2);
                            }
                            break;
                        case 27:
                   long postId3 = b.getLong("post_id");
                            for (AppSessionListener listener22 : this.mListeners) {
                       listener22.onReportPostComplete(reqId, statusCode, reasonPhrase, postId3);
                            }
                            break;
                        case 28:
                            for (AppSessionListener listener23 : this.mListeners) {
                       listener23.onGetSinglePostComplete(reqId, statusCode, reasonPhrase, (VinePost) b.getParcelable("post"));
                            }
                            break;
                        case 29:
                   SharedPreferences.Editor editor = Util.getSharedPrefs(this.mContext, Settings.PREF_LAST_CLEANUP).edit();
                   long lastCleanupTime = System.currentTimeMillis();
                   editor.putLong(Settings.PREF_LAST_CLEANUP, lastCleanupTime);
                   editor.commit();
                            break;
                        case 30:
                   long commentId = Long.parseLong(b.getString("comment_id"));
                            for (AppSessionListener listener24 : this.mListeners) {
                       listener24.onDeleteCommentComplete(reqId, statusCode, reasonPhrase, commentId);
                            }
                            break;
                        case 31:
                   SharedPreferences prefs5 = Util.getDefaultSharedPrefs(this.mContext);
                   SharedPreferences.Editor e7 = prefs5.edit();
                   int oldCount3 = prefs5.getInt(Settings.PREF_POST_COUNT, 0);
                   long postId4 = b.getLong("post_id");
                   e7.putInt(Settings.PREF_POST_COUNT, oldCount3 > 0 ? oldCount3 - 1 : 0);
                   e7.commit();
                            for (AppSessionListener listener25 : this.mListeners) {
                       listener25.onDeletePostComplete(reqId, postId4, statusCode, reasonPhrase);
                            }
                            break;
                        case 32:
                   String username = b.getString("username");
                   String token = b.getString(VineService.EXTRA_KEY);
                   String secret = b.getString("secret");
                   long userId4 = b.getLong("user_id");
                            for (AppSessionListener listener26 : this.mListeners) {
                       listener26.onConnectTwitterComplete(reqId, statusCode, reasonPhrase, username, token, secret, userId4);
                            }
                            break;
                        case 33:
                            for (AppSessionListener listener27 : this.mListeners) {
                       listener27.onDisconnectTwitterComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 36:
                   long postId5 = b.getLong("post_id");
                            for (AppSessionListener listener28 : this.mListeners) {
                       listener28.onGetPostIdComplete(reqId, statusCode, reasonPhrase, postId5);
                            }
                            break;
                        case 37:
                            for (AppSessionListener listener29 : this.mListeners) {
                       listener29.onConnectFacebookComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 38:
                   ServerStatus ss = (ServerStatus) b.getParcelable(VineService.EXTRA_SERVER_STATUS);
                   if (statusCode == 200 && ss != null && !TextUtils.isEmpty(ss.uploadType)) {
                       SharedPreferences.Editor e8 = Util.getDefaultSharedPrefs(this.mContext).edit();
                       e8.putString(Settings.PREF_SERVER_UPLOAD_TYPE, ss.uploadType);
                       e8.commit();
                                break;
                            }
                            break;
                        case 39:
                   String query = b.getString(VineService.EXTRA_QUERY);
                   ArrayList<VineUser> users4 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener30 : this.mListeners) {
                       listener30.onGetUserTypeAheadComplete(reqId, statusCode, reasonPhrase, query, users4);
                            }
                            break;
                        case 40:
                   String query2 = b.getString(VineService.EXTRA_QUERY);
                   ArrayList<VineTag> tags = b.getParcelableArrayList("tags");
                            for (AppSessionListener listener31 : this.mListeners) {
                       listener31.onGetTagTypeAheadComplete(reqId, statusCode, reasonPhrase, query2, tags);
                            }
                            break;
                        case 41:
                   int count4 = b.getInt("count", 0);
                   int nextPage2 = b.getInt("next_page", 0);
                   int previousPage2 = b.getInt("previous_page", 0);
                   ArrayList<VineUser> users5 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener32 : this.mListeners) {
                       listener32.onUserSearchComplete(reqId, statusCode, reasonPhrase, count4, nextPage2, previousPage2, users5);
                            }
                            break;
                        case 42:
                   int count5 = b.getInt("count", 0);
                   int nextPage3 = b.getInt("next_page", 0);
                   int previousPage3 = b.getInt("previous_page", 0);
                   ArrayList<VineTag> tags2 = b.getParcelableArrayList("tags");
                            for (AppSessionListener listener33 : this.mListeners) {
                       listener33.onTagSearchComplete(reqId, statusCode, reasonPhrase, count5, nextPage3, previousPage3, tags2);
                            }
                            break;
                        case 44:
                   boolean shouldFollow = b.getBoolean(VineService.EXTRA_SHOULD_FOLLOW_EDITORS_PICKS);
                   if (!shouldFollow) {
                       expireTimeline(true, 1, String.valueOf(VineAccountHelper.getCachedActiveSession().getUserId()));
                            } else {
                       fetchNewPosts(getActiveSession(), 10, 0L, 1, 1, 0L, true, String.valueOf(getActiveId()), null, null);
                            }
                            for (AppSessionListener listener34 : this.mListeners) {
                       listener34.onUpdateFollowEditorsPicksComplete(reqId, statusCode, reasonPhrase, shouldFollow);
                            }
                            break;
                        case 45:
                   int type = b.getInt("type");
                   String tag = b.getString("tag_name");
                   boolean alsoFetchPosts = b.getBoolean(VineService.EXTRA_ALSO_FETCH_POSTS, false);
                   if (alsoFetchPosts) {
                       fetchNewPosts(getActiveSession(), 10, getActiveId(), type, 1, 0L, true, tag, null, null);
                            }
                            break;
                        case 47:
                  boolean userBlocked = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                  String username2 = b.getString("username");
                            for (AppSessionListener listener35 : this.mListeners) {
                      listener35.onBlockUserComplete(reqId, statusCode, reasonPhrase, userBlocked, username2);
                            }
                            break;
                        case 48:
                  boolean userUnblocked = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                  String username3 = b.getString("username");
                            for (AppSessionListener listener36 : this.mListeners) {
                      listener36.onUnblockUserComplete(reqId, statusCode, reasonPhrase, userUnblocked, username3);
                            }
                            break;
                        case 49:
                            for (AppSessionListener listener37 : this.mListeners) {
                      listener37.onReportUserComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 50:
                  long userId5 = b.getLong("user_id");
                  boolean accept = b.getBoolean(VineService.EXTRA_ACCEPT);
                            for (AppSessionListener listener38 : this.mListeners) {
                      listener38.onRespondFollowRequestComplete(reqId, statusCode, reasonPhrase, accept, userId5);
                            }
                            break;
                        case 51:
                  boolean explicit = b.getBoolean("explicit");
                            for (AppSessionListener listener39 : this.mListeners) {
                      listener39.onUpdateExplicitComplete(reqId, statusCode, reasonPhrase, explicit);
                            }
                            break;
                        case 52:
                  boolean priv = b.getBoolean(VineService.EXTRA_PRIVATE);
                            for (AppSessionListener listener40 : this.mListeners) {
                      listener40.onUpdatePrivateComplete(reqId, statusCode, reasonPhrase, priv);
                            }
                  channels = b.getParcelableArrayList("channels");
                            for (AppSessionListener listener41 : this.mListeners) {
                      listener41.onGetChannelsComplete(reqId, statusCode, reasonPhrase, channels);
                            }
                            break;
                        case 53:
                  channels = b.getParcelableArrayList("channels");
                            for (AppSessionListener listener41 : this.mListeners) {
                      listener41.onGetChannelsComplete(reqId, statusCode, reasonPhrase, channels);
                            }
                            break;
                        case 54:
                   long postId6 = b.getLong("post_id");
                   VineRepost repost = (VineRepost) b.getSerializable("repost");
                            if (statusCode >= 200 && statusCode < 300) {
                                noteRevineState(postId6, true);
                            }
                            for (AppSessionListener listener42 : this.mListeners) {
                       listener42.onRevine(reqId, statusCode, reasonPhrase, postId6, repost);
                            }
                            break;
                        case 55:
                   long postId7 = b.getLong("post_id");
                            if (statusCode >= 200 && statusCode < 300) {
                                noteRevineState(postId7, false);
                            }
                            for (AppSessionListener listener43 : this.mListeners) {
                       listener43.onUnrevine(reqId, statusCode, reasonPhrase, postId7);
                            }
                            break;
                        case 58:
                  clearFileCache();
                            for (AppSessionListener listener44 : this.mListeners) {
                      listener44.onClearCacheComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 60:
                   long userId6 = b.getLong("user_id");
                            for (AppSessionListener listener45 : this.mListeners) {
                       listener45.onGcmRegistrationComplete(reqId, statusCode, reasonPhrase, userId6);
                            }
                            break;
                        case 61:
                   deleteSession(b.getString("s_key"));
                            break;
                        case 63:
                            for (AppSessionListener listener46 : this.mListeners) {
                      listener46.onSharePostComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 65:
                  int count6 = b.getInt("count");
                  if (statusCode == 200) {
                      Util.getDefaultSharedPrefs(this.mContext).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, count6).commit();
                            }
                            for (AppSessionListener listener47 : this.mListeners) {
                      listener47.onGetPendingNotificationCountComplete(reqId, statusCode, reasonPhrase, count6);
                            }
                            break;
                        case 66:
                  LoopManager loopManager = LoopManager.get(this.mContext);
                  VineLoopSubmissionResponse response = (VineLoopSubmissionResponse) b.getParcelable(VineService.EXTRA_LOOP_SUBMISSION_RESULT);
                  if (statusCode == 200 && response != null) {
                      long intervalMs = ((long) response.submissionInterval) * 1000;
                      SLog.m448d("Changing loop interval from {} to {}", Long.valueOf(this.mLoopCountSubmissionInterval), Long.valueOf(intervalMs));
                      this.mLoopCountSubmissionInterval = Math.max(intervalMs, 60000L);
                      refreshLoopSendingAlarm(System.currentTimeMillis() + this.mLoopCountSubmissionInterval);
                      loopManager.onServerSucess();
                            } else {
                      List<LoopManager.Record> failedLoops = b.getParcelableArrayList("loops");
                      SLog.m447d("Loop submission failed; re-saving {} pending loops", Integer.valueOf(failedLoops.size()));
                      loopManager.onServerFailure(failedLoops);
                            }
                            break;
                        case 67:
                   boolean success = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener48 : this.mListeners) {
                       listener48.onDeactivateAccountComplete(reqId, statusCode, reasonPhrase, success);
                            }
                            break;
                        case 68:
                  boolean success2 = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener49 : this.mListeners) {
                      listener49.onEnableUserRepostsComplete(reqId, statusCode, reasonPhrase, success2);
                            }
                            break;
                        case 69:
                  boolean success3 = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener50 : this.mListeners) {
                      listener50.onDisableUserRepostsComplete(reqId, statusCode, reasonPhrase, success3);
                            }
                            break;
                        case 70:
                            for (AppSessionListener listener51 : this.mListeners) {
                      listener51.onGetEditionsComplete(statusCode, (ArrayList) b.getSerializable("editions"));
                            }
                            break;
                        case 71:
                   String edition = b.getString("edition");
                   SLog.m447d("Edition update success. New edition is {}", edition);
                   if (statusCode == 200) {
                       SharedPreferences.Editor editor2 = Util.getDefaultSharedPrefs(this.mContext).edit();
                       editor2.putString(Settings.PREF_EDITION, edition);
                       editor2.commit();
                            }
                            for (AppSessionListener listener52 : this.mListeners) {
                       listener52.onUpdateEditionComplete(reqId, statusCode, reasonPhrase, edition);
                            }
                   this.mContext.sendBroadcast(new Intent(ACTION_EDITION_UPDATED), CrossConstants.BROADCAST_PERMISSION);
                            break;
                        case 74:
                   int count7 = b.getInt("count", 0);
                            for (AppSessionListener listener53 : this.mListeners) {
                       listener53.onGetMessageInboxComplete(reqId, statusCode, reasonPhrase, count7);
                            }
                            break;
                        case 76:
                            for (AppSessionListener listener54 : this.mListeners) {
                       listener54.onGetConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"), b.getInt(VineService.EXTRA_RESPONSE_CODE), b.getBoolean(VineService.EXTRA_EMPTY));
                            }
                            break;
                        case 77:
                   long conversationId = b.getLong("conversation_id");
                            for (AppSessionListener listener55 : this.mListeners) {
                       listener55.onGetConversationRemoteIdComplete(conversationId);
                            }
                            break;
                        case 79:
                            for (AppSessionListener listener56 : this.mListeners) {
                       listener56.onIgnoreConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"));
                            }
                            break;
                        case 80:
                            for (AppSessionListener listener57 : this.mListeners) {
                       listener57.onDeleteConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"));
                            }
                            break;
                        case 81:
                   int count8 = b.getInt("count");
                   int messagesCount = b.getInt(VineService.EXTRA_MESSAGES_COUNT);
                   int followRequestCount = b.getInt(VineService.EXTRA_FOLLOW_REQUEST_COUNT, 0);
                   SharedPreferences.Editor editor3 = Util.getDefaultSharedPrefs(this.mContext).edit();
                   editor3.putInt(Settings.PREF_FOLLOW_REQUEST_COUNT, followRequestCount);
                   editor3.putInt(Settings.PREF_UNREAD_MESSAGE_COUNT, messagesCount);
                   UrlCachePolicy policyUsed2 = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                   editor3.apply();
                            for (AppSessionListener listener58 : this.mListeners) {
                       listener58.onGetActivityCountComplete(reqId, statusCode, reasonPhrase, count8, followRequestCount, messagesCount, policyUsed2);
                            }
                            break;
                        case 86:
                   long userId7 = b.getLong("user_id");
                            for (AppSessionListener listener59 : this.mListeners) {
                       listener59.onGetUserIdComplete(reqId, statusCode, reasonPhrase, userId7);
                            }
                            break;
                        case 88:
                   String avatarUrl3 = b.getString("avatar_url");
                            for (AppSessionListener listener60 : this.mListeners) {
                       listener60.onProfilePhotoUpdatedComplete(reqId, statusCode, reasonPhrase, avatarUrl3);
                            }
                            break;
                        case 89:
                  String phone = b.getString(VineService.EXTRA_PHONE);
                            for (AppSessionListener listener61 : this.mListeners) {
                      listener61.onRequestPhoneVerificationComplete(reqId, statusCode, reasonPhrase, phone);
                            }
                            break;
                        case 90:
                            for (AppSessionListener listener62 : this.mListeners) {
                      listener62.onVerifyPhoneNumberComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 91:
                   long objectId = b.getLong("conversation_row_id");
                   VineRecipient recipient = (VineRecipient) b.getParcelable(VineService.EXTRA_RECIPIENT);
                   long recipientId = b.getLong(VineService.EXTRA_RECIPIENT_ID);
                   String username4 = b.getString("username");
                   boolean amFollowing = b.getBoolean("am_following");
                            for (AppSessionListener listener63 : this.mListeners) {
                       listener63.onGetConversationRowIdComplete(recipientId, !recipient.isFromUser(), objectId, username4, amFollowing);
                            }
                            break;
                        case 92:
                  String email = b.getString("email");
                            for (AppSessionListener listener64 : this.mListeners) {
                      listener64.onRequestEmailVerificationComplete(reqId, statusCode, reasonPhrase, email);
                            }
                            break;
                        case 96:
                  SLog.m449d("Share as a vm : {} {} {}.", reqId, Integer.valueOf(statusCode), reasonPhrase);
                            break;
                        case 97:
                   ArrayList<VineSingleNotification> notifications2 = b.getParcelableArrayList("notifications");
                   VineSingleNotification lastNotification = (VineSingleNotification) b.getParcelable("notification");
                            for (AppSessionListener listener65 : this.mListeners) {
                       listener65.onMergeNotificationComplete(lastNotification, notifications2);
                            }
                            break;
                        case 98:
                            for (AppSessionListener listener66 : this.mListeners) {
                      listener66.onPostVideoComplete(reqId, statusCode, reasonPhrase);
                            }
                  SLog.m449d("Share as a vm : {} {} {}.", reqId, Integer.valueOf(statusCode), reasonPhrase);
                            break;
                        case 99:
                   String query3 = b.getString(VineService.EXTRA_QUERY);
                   ArrayList<VineUser> users6 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener67 : this.mListeners) {
                       listener67.onGetFriendsTypeAheadComplete(reqId, statusCode, reasonPhrase, query3, users6);
                            }
                            break;
                        case 105:
                   boolean acceptOon = b.getBoolean(VineService.EXTRA_ACCEPT_OON);
                            for (AppSessionListener listener68 : this.mListeners) {
                       listener68.onUpdateAcceptOonComplete(reqId, statusCode, reasonPhrase, acceptOon);
                            }
                            break;
                        case 106:
                   boolean enableAddressBook = b.getBoolean(VineService.EXTRA_ENABLE);
                            for (AppSessionListener listener69 : this.mListeners) {
                       listener69.onUpdateEnableAddressBookComplete(reqId, statusCode, reasonPhrase, enableAddressBook);
                            }
                            break;
                        case 107:
                            for (AppSessionListener listener70 : this.mListeners) {
                       listener70.onBulkFollowComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 108:
                   boolean enable = b.getBoolean(VineService.EXTRA_ENABLE);
                   int type2 = b.getInt("type");
                            for (AppSessionListener listener71 : this.mListeners) {
                       listener71.onUpdateDiscoverability(reqId, statusCode, reasonPhrase, type2, enable);
                            }
                            break;
                        case 109:
                            for (AppSessionListener listener72 : this.mListeners) {
                      listener72.onVerifyEmailComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 111:
                            try {
                      ClientFlagsHelper.setLastCheckMillis(context);
                      VineClientFlags clientFlags = (VineClientFlags) b.getParcelable(VineService.EXTRA_CLIENT_FLAGS);
                      if (statusCode == 200 && clientFlags != null) {
                          ClientFlagsHelper.updateClientFlags(context, clientFlags);
                          VineAPI.getInstance(context).refreshHostUrls(clientFlags, context.getResources());
                          boolean abortRequests = b.getBoolean(VineService.EXTRA_ABORT_REQUESTS, true);
                          boolean hostsDidChange = ClientFlagsHelper.hostsDidChange(context, clientFlags);
                          if (hostsDidChange) {
                              if (abortRequests) {
                                            abortAllRequests();
                                        }
                              FlurryUtils.trackZeroRatedSessionStarted(!TextUtils.isEmpty(clientFlags.apiHost));
                                    }
                          boolean showBanner = (TextUtils.isEmpty(clientFlags.messageTitle) && TextUtils.isEmpty(clientFlags.messageText)) ? false : true;
                                    for (AppSessionListener listener73 : this.mListeners) {
                              listener73.onFetchClientFlagsComplete(reqId, statusCode, reasonPhrase, clientFlags, showBanner);
                                        break;
                                    }
                                    break;
                                }
                            } catch (Exception e9) {
                      CrashUtil.logException(e9);
                      return;
                            }
                            break;
                        case 112:
                  context.sendBroadcast(new Intent(ACTION_ABORT_ALL_REQUESTS), CrossConstants.BROADCAST_PERMISSION);
                            for (AppSessionListener listener74 : this.mListeners) {
                      listener74.onAbortAllRequestsComplete();
                            }
                            break;
                        case 113:
                   ArrayList<VineNotificationSetting> notifSettings = b.getParcelableArrayList(VineService.EXTRA_NOTIFICATION_SETTINGS);
                            for (AppSessionListener listener75 : this.mListeners) {
                       listener75.onGetNotificationSettingsComplete(reqId, notifSettings);
                            }
                            break;
                        case 114:
                            for (AppSessionListener listener76 : this.mListeners) {
                       listener76.onSaveNotificationSettingsComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 116:
                            for (AppSessionListener listener77 : this.mListeners) {
                      listener77.onFollowChannelComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 117:
                            for (AppSessionListener listener78 : this.mListeners) {
                      listener78.onBulkFollowChannelComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 119:
                            for (AppSessionListener listener79 : this.mListeners) {
                      listener79.onFavoriteUserComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 120:
                  int nextPage4 = b.getInt("next_page", 0);
                  int prevPage = b.getInt("previous_page", 0);
                  String anchor3 = b.getString(VineService.EXTRA_ANCHOR);
                  ArrayList<VineUser> users7 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener80 : this.mListeners) {
                      listener80.onFetchFavoritePeopleComplete(reqId, statusCode, reasonPhrase, users7, nextPage4, prevPage, anchor3);
                            }
                            break;
                        case 121:
                  ArrayList<VineUser> users8 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener81 : this.mListeners) {
                      listener81.onFetchSuggestedFavoritesComplete(reqId, statusCode, reasonPhrase, users8);
                            }
                            break;
                        case 122:
                  ArrayList<VineUser> users9 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener82 : this.mListeners) {
                      listener82.onFetchOnboardingSuggestedFavoritesComplete(reqId, statusCode, reasonPhrase, users9);
                            }
                            break;
                        case 126:
                   long commentId2 = Long.parseLong(b.getString("comment_id"));
                            for (AppSessionListener listener83 : this.mListeners) {
                       listener83.onSpamCommentComplete(reqId, statusCode, reasonPhrase, commentId2);
                            }
                            break;
                        case 130:
                  ArrayList<ComplaintMenuOption> options = (ArrayList) b.getSerializable(VineService.EXTRA_COMPLAINT_MENU);
                            for (AppSessionListener listener84 : this.mListeners) {
                      listener84.onComplaintMenu(reqId, statusCode, options);
                            }
                            break;
                    }
                }

      public void onGetPostComplete(String reqId, Bundle b, int statusCode, String reasonPhrase) {
                    ArrayList<VinePost> posts;
          int type = b.getInt("type", -1);
          int count = b.getInt("count", 0);
          int size = b.getInt(VineService.EXTRA_SIZE, 0);
          String tag = b.getString("tag_name");
          int pageType = b.getInt(VineService.EXTRA_PAGE_TYPE);
          int next = b.getInt("next_page");
          int previous = b.getInt("previous_page");
          long anchor = b.getLong(VineService.EXTRA_ANCHOR);
          String title = b.getString("title");
          boolean userInitiated = b.getBoolean(VineService.EXTRA_USER_INITIATED);
          boolean memory = b.getBoolean(VineService.EXTRA_IN_MEMORY, false);
          UrlCachePolicy cachePolicy = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
          if (memory) {
              posts = b.getParcelableArrayList("posts");
                    } else {
              posts = null;
                    }
          if (reqId != null) {
                        for (AppSessionListener listener : this.mListeners) {
                  listener.onGetTimeLineComplete(reqId, statusCode, reasonPhrase, type, count, memory, posts, tag, pageType, next, previous, anchor, userInitiated, size, title, cachePolicy, b);
                        }
                    }
          if (statusCode == 200) {
              determineCleanup(getActiveSession());
                    }
                }

                @Override 
      public void handleServiceResponse(int actionCode, int statusCode, String reasonPhrase, Bundle b) {

          String reqId = b.getString(VineService.EXTRA_REQUEST_ID);
          if (reqId != null) {
              PendingAction action = this.mServiceConnection.remove(reqId);
              if (b.getBoolean(VineService.EXTRA_LOGGED_OUT) && actionCode != 6) {
                  onLogout();
                  return;
                        }
              if (b.getBoolean(VineService.EXTRA_REFRESH_SESSION, false)) {
                  refreshSessionKey(b.getString("s_key"));
                        }
              String captchaUrl = b.getString("captcha_url");
              b.remove("captcha_url");
              if (captchaUrl != null) {
                            for (AppSessionListener listener : this.mListeners) {
                      listener.onCaptchaRequired(reqId, captchaUrl, action);
                            }
                  return;
                        }
              notifyListeners(reqId, actionCode, statusCode, reasonPhrase, b);
                    }
                }

      public Bitmap getPhotoBitmap(ImageKey key) {
          if (!Util.isUrlLocal(key.url)) {
              return this.mPhotoImagesCache.getBitmap(getActiveId(), key);
                    }
          String path = Uri.parse(key.url).getPath();
          return BitmapFactory.decodeFile(path);
                }

      public String getVideoFilePath(VideoKey key) {
                    return !Util.isUrlLocal(key.url) ? this.mVideoCache.getFile(getActiveId(), key, false) : Uri.parse(key.url).getPath();
                }

      public String peekVideoFilePath(VideoKey key) {
          return this.mVideoCache.getFile(getActiveId(), key, true);
                }

      public boolean attemptCancelVideoFetchRequest(VideoKey key) {
                    return this.mVideoCache.cancel(key) > 0;
                }

      public VineLogin loginComplete(Session session, int responseCode, String accountName, String password, VineLogin login, String avatarUrl) throws VineLoggingException {
                    String uniqueLogin;
          if (responseCode == 200) {
              session.setLoginStatus(Session.LoginStatus.LOGGED_IN);
                        switch (login.loginType) {
                            case 1:
                      uniqueLogin = login.username;
                                break;
                            case 2:
                      uniqueLogin = accountName;
                                break;
                            default:
                      throw new UnsupportedOperationException("Unsupported login type.");
                        }
              session.setUserId(login.userId);
              session.setName(accountName);
              session.setSessionKey(login.key);
              session.setLoginEmail(uniqueLogin);
              session.setAvatarUrl(avatarUrl);
              VineAccountHelper.createAccount(this.mContext, uniqueLogin, login, password, avatarUrl, accountName);
              SettingsManager.setCurrentAccount(this.mContext, uniqueLogin, accountName, login.userId);
                        if (Util.getDefaultSharedPrefs(this.mContext).getLong(PREF_LAST_USER_ID, 0L) != login.userId) {
                  clearFacebookToken();
                  Intent i = AppImpl.getInstance().getDiscardAllIntent(this.mContext);
                  if (i != null) {
                      this.mContext.startService(i);
                            }
                        }
              if (login.edition != null) {
                  Util.getDefaultSharedPrefs(this.mContext).edit().putString(Settings.PREF_EDITION, login.edition).commit();
                        }
              FlurryAgent.setUserId(String.valueOf(login.userId));
              return login;
                    }
          return null;
                }

      public VineLogin loginComplete(Session session, int responseCode, Bundle b) throws VineLoggingException {
          VineLogin vl = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
          if (vl != null) {
              return loginComplete(session, responseCode, b.getString(VineService.EXTRA_ACCOUNT_NAME), b.getString(VineService.EXTRA_PASSWORD), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN), b.getString("avatar_url"));
                    }
                    return null;
                }

      public String fetchUsersMe(long userId, UrlCachePolicy cachePolicy) {
          Bundle b = createServiceBundle();
          b.putLong(VineService.EXTRA_SESSION_OWNER_ID, userId);
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
          return executeServiceAction(10, b);
                }

      public String updateProfile(Session session, String name, String description, String location, String email, String phone, Uri avatar, int profileBackgroundColor) {
          Bundle b = createServiceBundle(session);
          b.putString(VineService.EXTRA_ACCOUNT_NAME, name);
          b.putString(VineService.EXTRA_DESCRIPTION, Util.cleanse(description));
          b.putString("location", Util.cleanse(location));
          b.putString("email", email);
          b.putString(VineService.EXTRA_PHONE, phone);
          b.putInt(VineService.EXTRA_COLOR_INT, profileBackgroundColor);
          if (avatar != null) {
              b.putParcelable("uri", avatar);
                    }
          return executeServiceAction(26, b);
                }

      public String updateAcceptOon(boolean acceptOon) {
          Bundle b = createServiceBundle();
          b.putBoolean(VineService.EXTRA_ACCEPT_OON, acceptOon);
          return executeServiceAction(105, b);
                }

      public String updateEnableAddressBook(boolean enable) {
          Bundle b = createServiceBundle();
          b.putBoolean(VineService.EXTRA_ENABLE, enable);
          return executeServiceAction(106, b);
                }

      public String updateDiscoverability(int type, boolean enable) {
          Bundle b = createServiceBundle();
          b.putBoolean(VineService.EXTRA_ENABLE, enable);
          b.putInt("type", type);
          return executeServiceAction(108, b);
                }

      public String updateProfilePhoto(Session session, Uri avatar) {
          Bundle b = createServiceBundle(session);
          b.putParcelable("uri", avatar);
          return executeServiceAction(88, b);
                }

      public String fetchUser(long userId, UrlCachePolicy cachePolicy) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userId);
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
          return executeServiceAction(21, b);
                }

      public String fetchFollowers(long userId, int page, long anchor) {
          Bundle b = createServiceBundle();
          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(22, b);
                }

      public String fetchFollowing(long userId, int page, long anchor) {
          Bundle b = createServiceBundle();
          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(23, b);
                }

      public String fetchNotificationSettings() {
          Bundle b = createServiceBundle();
          return executeServiceAction(113, b);
                }

      public String fetchFriends(int pageType, int type) {
          Bundle b = createServiceBundle();
          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
          b.putInt("type", type);
          return executeServiceAction(82, b);
                }

      public String fetchFriendsTypeAhead(String key) {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_QUERY, key);
          return executeServiceAction(99, b);
                }

      public String fetchPostLikers(Session session, long postId, int page, long anchor) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(11, b);
                }

      public String fetchNotificationUsers(Session session, long notificationId, int page, long anchor) {
          Bundle b = createServiceBundle(session);
          b.putLong("notification_id", notificationId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(110, b);
                }

      public String fetchReviners(Session session, long postId, int page, long anchor) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(56, b);
                }

      public String fetchActivity(Session session, int page, long anchor, boolean followRequests, boolean clearPendingCount, UrlCachePolicy cachePolicy) {
          Bundle b = createServiceBundle(session);
          b.putInt("page", page);
          b.putBoolean(VineService.EXTRA_GET_FOLLOW_REQUESTS, followRequests);
          b.putBoolean(VineService.EXTRA_CLEAR, clearPendingCount);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
          return executeServiceAction(20, b);
                }

      public String fetchActivityCounts() {
          Bundle b = createServiceBundle();
          b.putLong(VineService.EXTRA_SESSION_OWNER_ID, getActiveId());
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, UrlCachePolicy.FORCE_REFRESH);
          return executeServiceAction(81, b);
                }

      public String followUser(Session session, long userIdToFollow, boolean notify) {
          Bundle b = createServiceBundle(session);
          b.putLong(VineService.EXTRA_FOLLOW_ID, userIdToFollow);
          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
          return executeServiceAction(18, b);
                }

      public String bulkFollowUsers(Session session, ArrayList<String> userIdsToFollow) {
          Bundle bundle = injectServiceBundle(new Bundle(), session);
          bundle.putStringArrayList(VineService.EXTRA_FOLLOW_IDS, userIdsToFollow);
          return executeServiceAction(107, bundle);
                }

      public String unfollowUser(Session session, long userIdToUnfollow, boolean notify) {
          return unfollowUser(session, userIdToUnfollow, notify, -1L);
                }

      public String unfollowUser(Session session, long userIdToUnfollow, boolean notify, long currentProfileId) {
          Bundle b = createServiceBundle(session);
          b.putLong(VineService.EXTRA_FOLLOW_ID, userIdToUnfollow);
          b.putLong(VineService.EXTRA_PROFILE_ID, currentProfileId);
          b.putLong("user_id", getActiveId());
          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
          return executeServiceAction(19, b);
                }

      public String likePost(String source, Session session, long postToLike, long repostId, long meUserId, String meUsername, boolean notify) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postToLike);
          b.putLong("repost_id", repostId);
          b.putLong("user_id", meUserId);
          b.putString("username", meUsername);
          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
          FlurryUtils.trackLikePost(postToLike, source);
          return executeServiceAction(24, b);
                }

      public String unlikePost(String source, Session session, long postToUnlike, long repostId, boolean notify) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postToUnlike);
          b.putLong("repost_id", repostId);
          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
          FlurryUtils.trackUnLikePost(source);
          return executeServiceAction(25, b);
                }

      public String revine(String source, Session session, long postToRevine, long repostId, String meUsername) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postToRevine);
          b.putLong("repost_id", repostId);
          b.putString("username", meUsername);
          FlurryUtils.trackRevine(postToRevine, source);
          return executeServiceAction(54, b);
                }

      public String unRevine(String source, Session session, long postToUnrevine, long myRepostId, long repostId, boolean following, long userId, boolean notify) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postToUnrevine);
          b.putLong("my_repost_id", myRepostId);
          b.putLong("repost_id", repostId);
          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
          b.putBoolean("following", following);
          FlurryUtils.trackUnRevine(source);
          return executeServiceAction(55, b);
                }

      public String removeFollowPosts(Session session, long userId) {
          Bundle b = createServiceBundle(session);
          b.putLong("user_id", userId);
          return executeServiceAction(34, b);
                }

      public String fetchServerStatus() {
          Bundle b = createServiceBundle();
          return executeServiceAction(38, b);
                }

      public Session getActiveSession() {
          return getActiveSession(false);
                }

      public Session getActiveSessionReadOnly() {
          return getActiveSession(true);
                }

      public long getActiveId() {
          return getActiveSessionReadOnly().getUserId();
                }

                public long getRevineVersion() {
                    return this.mRevineVersion;
                }

                public java.util.HashMap<Long, Boolean> getRevineStates() {
                    return new java.util.HashMap<>(this.mRevineStates);
                }

                private void noteRevineState(long postId, boolean revined) {
                    this.mRevineStates.put(Long.valueOf(postId), Boolean.valueOf(revined));
                    if (this.mRevineStates.size() > 100) {
                        java.util.Iterator<Long> it = this.mRevineStates.keySet().iterator();
                        if (it.hasNext()) {
                            it.next();
                            it.remove();
                        }
                    }
                    this.mRevineVersion++;
                }

          public boolean isPendingRequest(String reqId) {
          return this.mServiceConnection.isPending(reqId);
                }

      public Session getSession(String accountName) {
          return VineAccountHelper.getSession(this.mContext, accountName);
                }

      private Session getActiveSession(boolean readOnly) {
          return VineAccountHelper.getActiveSession(this.mContext, readOnly);
                }

      public String login(Session session, VineLogin login, String password, boolean reactivate) {
          session.setLoginStatus(Session.LoginStatus.LOGGING_IN);
          Bundle b = createServiceBundle(session);
          b.putParcelable(VineService.EXTRA_LOGIN, login);
          b.putString(VineService.EXTRA_PASSWORD, password);
          b.putBoolean("reactivate", reactivate);
          return executeServiceAction(2, b);
                }

      public String logout(Session session) {
          if (session.getLoginStatus() != Session.LoginStatus.LOGGED_IN && session.getLoginStatus() != Session.LoginStatus.LOGGING_IN) {
                        return null;
                    }
          session.setLoginStatus(Session.LoginStatus.LOGGING_OUT);
          return executeServiceAction(3, createServiceBundle(session));
                }

      public String logout() {
          return logout(getActiveSession());
                }

      public String signUp(String login, String password, String username, String phoneNumber, Uri profileUri, TwitterUser twitterUser, VineLogin vl) {
          Bundle b = createServiceBundle();
          b.putString("email", login);
          b.putString(VineService.EXTRA_PASSWORD, password);
          b.putString(VineService.EXTRA_ACCOUNT_NAME, username);
          b.putString(VineService.EXTRA_PHONE, phoneNumber);
          b.putParcelable("uri", profileUri);
          b.putParcelable(VineService.EXTRA_TWITTER_USER, twitterUser);
          if (vl != null) {
              b.putString(VineService.EXTRA_KEY, vl.twitterToken);
              b.putString("secret", vl.twitterSecret);
                    }
          return executeServiceAction(4, b);
                }

      public void followVineOnTwitter() {
          executeServiceRequest(123, new Bundle());
                }

      public String getTwitterAuth(String callback, String key, String secret) {
          Bundle b = createServiceBundle();
          b.putString("uri", callback);
          b.putString(VineService.EXTRA_KEY, key);
          b.putString("secret", secret);
          return executeServiceAction(5, b);
                }

      public String resetPassword(String email) {
          Bundle b = createServiceBundle();
          b.putString("email", email);
          return executeServiceAction(9, b);
                }

      public String fetchTwitterUser(VineLogin mLogin) {
          Bundle b = createServiceBundle();
          b.putParcelable(VineService.EXTRA_LOGIN, mLogin);
          return executeServiceAction(7, b);
                }

      public String loginWithxAuth(String username, String password) {
          Bundle b = createServiceBundle();
          b.putString("username", username);
          b.putString(VineService.EXTRA_PASSWORD, password);
          return executeServiceAction(8, b);
                }

      public String fetchNewPosts(Session session, int size, long userId, int type, int page, long anchor, boolean userInitiated, String tag, String sort, Uri data) {
          return fetchPosts(session, size, userId, type, page, anchor, userInitiated, tag, sort, data, UrlCachePolicy.FORCE_REFRESH);
                }

      public String fetchPosts(Session session, int size, long userId, int type, int page, long anchor, boolean userInitiated, String tag, String sort, Uri data, UrlCachePolicy cachePolicy) {
          Bundle b = createServiceBundle(session);
          b.putInt(VineService.EXTRA_SIZE, size);
          b.putInt("type", type);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
          if (tag != null) {
                        b.putString("tag_name", tag);
                    }
          if (sort != null) {
                        b.putString("sort", sort);
                    }
          b.putParcelable("data", data);
          return executeServiceAction(13, b);
                }

      public String fetchPost(Session session, long postId, UrlCachePolicy cachePolicy) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postId);
          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
          return executeServiceAction(28, b);
                }

      public String fetchPostId(Session session, String shareId) {
          Bundle b = createServiceBundle(session);
          b.putString("post_share_id", shareId);
          return executeServiceAction(36, b);
                }

      public String fetchComments(long postId, int page, long anchor, int size) {
          Bundle b = createServiceBundle();
          b.putLong("post_id", postId);
          b.putInt("page", page);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          b.putInt(VineService.EXTRA_SIZE, size);
          return executeServiceAction(12, b);
                }

      public String updateClientProfile() {
          SLog.m446d("Update Client Profile.");
          return executeServiceAction(35, new Bundle());
                }

      public String postComment(long postId, long repostId, Session session, String comment, ArrayList<VineEntity> entities) {
          Bundle b = createServiceBundle();
          b.putLong("post_id", postId);
          b.putLong("repost_id", repostId);
          b.putLong("user_id", session.getUserId());
          b.putString("username", session.getName());
          b.putString("comment", Util.cleanse(comment));
          b.putString("avatar_url", session.getAvatarUrl());
          b.putParcelableArrayList("entities", entities);
          return executeServiceAction(14, b);
                }

      public String deleteComment(long postId, long repostId, String commentId) {
          Bundle b = createServiceBundle();
          b.putLong("post_id", postId);
          b.putLong("repost_id", repostId);
          b.putString("comment_id", commentId);
          return executeServiceAction(30, b);
                }

      public String spamComment(String commentId, String postId, String event) {
          Bundle b = createServiceBundle();
          b.putString("comment_id", commentId);
          b.putString("post_id", postId);
          if (!TextUtils.isEmpty(event)) {
              b.putString("event", event);
                    }
          return executeServiceAction(126, b);
                }

      public String spamComment(String commentId, String postId) {
          return spamComment(commentId, postId, null);
                }

      public String reportPost(Session session, long postId, String event) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postId);
          if (!TextUtils.isEmpty(event)) {
              b.putString("event", event);
                    }
          return executeServiceAction(27, b);
                }

      public String reportPost(Session session, long postId) {
          return reportPost(session, postId, null);
                }

      public String deletePost(Session session, long postId) {
          Bundle b = createServiceBundle(session);
          b.putLong("post_id", postId);
          return executeServiceAction(31, b);
                }

      public String sendFacebookToken() {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_FACEBOOK_TOKEN, getPendingFacebookToken());
          return executeServiceAction(37, b);
                }

      public String sendGcmRegId(String regId, long userId) {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_GCM_REG_ID, regId);
          b.putLong("user_id", userId);
          return executeServiceAction(60, b);
                }

      public String clearGcmRegId(String regId, long userId, String key) {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_GCM_REG_ID, regId);
          b.putLong("user_id", userId);
          b.putString("s_key", key);
          return executeServiceAction(61, b);
                }

      public void mergePushNotification(VineSingleNotification notification) {
          Bundle b = new Bundle();
          b.putParcelable("notification", notification);
          executeServiceAction(97, b);
                }

      public void clearPushNotifications(int notificationDisplayId) {
          removeNotification(notificationDisplayId, -1L);
                }

      public String getPendingFacebookToken() {
          return Util.getDefaultSharedPrefs(this.mContext).getString(KEY_FACEBOOK_TOKEN, null);
                }

      public static void setPendingFacebookToken(Context context, String token) {
          Util.getDefaultSharedPrefs(context).edit().putString(KEY_FACEBOOK_TOKEN, token).commit();
                }

      public void clearFacebookToken() {
          com.facebook.Session fbSession = com.facebook.Session.getActiveSession();
          if (fbSession == null) {
              fbSession = com.facebook.Session.openActiveSessionFromCache(this.mContext);
                    }
          if (fbSession != null) {
              fbSession.closeAndClearTokenInformation();
                    }
                }

      public String deleteSession(String key) {
          Bundle b = createServiceBundle();
          b.putString("s_key", key);
          return executeServiceAction(64, b);
                }

      public static void startTwitterAuthWithFinish(Twitter twitter, Activity activity) {
                    try {
              if (!twitter.startAuthActivityForResult(activity, 1)) {
                  activity.startActivityForResult(LoginTwitterActivity.getIntentWithFinish(activity), 2);
                        }
                    } catch (SecurityException e) {
              activity.startActivityForResult(LoginTwitterActivity.getIntentWithFinish(activity), 2);
                    }
                }

      public void followEditorsPicks(boolean shouldFollow) {
          Session session = getActiveSession();
          Bundle bundle = createServiceBundle(session);
          int follow = shouldFollow ? 1 : 0;
          bundle.putInt(VineService.EXTRA_SHOULD_FOLLOW_EDITORS_PICKS, follow);
          executeServiceAction(44, bundle);
                }

      public String loginCheckTwitter(String username, String token, String secret, long twitterUserId, boolean reactivate) {
          Bundle b = createServiceBundle();
          b.putString("username", username);
          b.putString(VineService.EXTRA_KEY, token);
          b.putString("secret", secret);
          b.putLong(VineService.EXTRA_TWITTER_ID, twitterUserId);
          b.putBoolean("reactivate", reactivate);
          return executeServiceAction(6, b);
                }

      public String fetchTwitterFriends(Session session, String key, String secret) {
          Bundle b = createServiceBundle(session);
          b.putString(VineService.EXTRA_KEY, key);
          b.putString("secret", secret);
          return executeServiceAction(15, b);
                }

      public String fetchAddressFriends(Session session) {
          Bundle b = createServiceBundle(session);
          return executeServiceAction(16, b);
                }

      public String sendAddressBook() {
          Bundle b = createServiceBundle();
          return executeServiceAction(101, b);
                }

      public String removeUsers(Session session, int type) {
          Bundle b = createServiceBundle(session);
          b.putInt("type", type);
          return executeServiceAction(17, b);
                }

      public String connectTwitter(Session session, String username, String token, String secret, long userId) {
          Bundle b = createServiceBundle(session);
          b.putString("username", username);
          b.putString(VineService.EXTRA_KEY, token);
          b.putString("secret", secret);
          b.putLong("user_id", userId);
          return executeServiceAction(32, b);
                }

      public String saveLoadedPosts(ArrayList<VinePost> posts, int groupType, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated) {
          Bundle b = createServiceBundle();
          b.putParcelableArrayList("posts", posts);
          b.putInt("type", groupType);
          b.putString("tag_name", tag);
          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
          b.putInt("next_page", next);
          b.putInt("previous_page", previous);
          b.putLong(VineService.EXTRA_ANCHOR, anchor);
          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
          return executeServiceAction(43, b);
                }

      public String disconnectTwitter(Session session) {
          Bundle b = createServiceBundle(session);
          return executeServiceAction(33, b);
                }

      public String acceptFollowRequest(Session session, long userId) {
          Bundle b = createServiceBundle(session);
          b.putLong("user_id", userId);
          b.putBoolean(VineService.EXTRA_ACCEPT, true);
          return executeServiceAction(50, b);
                }

      public String acceptRejectRequest(Session session, long userId) {
          Bundle b = createServiceBundle(session);
          b.putLong("user_id", userId);
          b.putBoolean(VineService.EXTRA_ACCEPT, false);
          return executeServiceAction(50, b);
                }

      public String updatePrivate(Session session, boolean priv) {
          Bundle b = createServiceBundle(session);
          b.putBoolean(VineService.EXTRA_PRIVATE, priv);
          return executeServiceAction(52, b);
                }

      public String updateExplicit(Session session, boolean explicit) {
          Bundle b = createServiceBundle(session);
          b.putBoolean("explicit", explicit);
          return executeServiceAction(51, b);
                }

      public String updateEdition(Session session, String editionCode) {
          Bundle b = createServiceBundle(session);
          b.putString("edition", editionCode);
          SLog.m447d("Updating edition code to {}", editionCode);
          return executeServiceAction(71, b);
                }

      public void determineServerStatus() {
          SharedPreferences prefs = Util.getDefaultSharedPrefs(this.mContext);
          long lastRefresh = prefs.getLong(Settings.PREF_LAST_SERVER_STATUS_REFRESH, 0L);
          long elapsedTime = System.currentTimeMillis() - lastRefresh;
          long remainingTime = System.currentTimeMillis() - elapsedTime;
                    if (lastRefresh <= 0 || elapsedTime > CommonUtil.HOUR_MILLIS) {
              fetchServerStatus();
              prefs.edit().putLong(Settings.PREF_LAST_SERVER_STATUS_REFRESH, System.currentTimeMillis()).commit();
              if (this.mHandler != null) {
                  this.mHandler.removeCallbacks(this.mServerStatusRunnable);
                  this.mHandler.postDelayed(this.mServerStatusRunnable, CommonUtil.HOUR_MILLIS);
                  return;
                        }
              return;
                    }
          if (this.mHandler != null) {
              this.mHandler.removeCallbacks(this.mServerStatusRunnable);
              Handler handler = this.mHandler;
                        ServerStatusRunnable serverStatusRunnable = this.mServerStatusRunnable;
                        if (remainingTime <= 0) {
                            remainingTime = 3600000;
                        }
                        handler.postDelayed(serverStatusRunnable, remainingTime);
                    }
                }

      public String sendLoopCounts() {
          SLog.m446d("Sending loops....");
          LoopManager loopManager = LoopManager.get(this.mContext);
          ArrayList<LoopManager.Record> pendingLoops = new ArrayList<>(loopManager.popPendingLoops());
          if (pendingLoops.isEmpty()) {
              return "";
                    }
          Bundle b = new Bundle();
          b.putParcelableArrayList("loops", pendingLoops);
          return executeServiceAction(66, b);
                }

      public void mergeSinglePost(Bundle bundle) {
          if (isLoggedInReadOnly()) {
              executeServiceAction(57, injectServiceBundle(bundle, getActiveSession()));
                    }
                }

      public void shareAsVM(ArrayList<VineRecipient> vineRecipients, long postId) {
          Bundle b = createServiceBundle();
          b.putParcelableArrayList("recipients", vineRecipients);
          b.putLong("post_id", postId);
          executeServiceAction(96, b);
                }

      public void sharePost(String network, String message, long postId) {
          Bundle b = createServiceBundle();
          b.putString("network", network);
          b.putString("message", message);
          b.putLong("post_id", postId);
          executeServiceAction(63, b);
                }

      public void markMessageLoopViewed(VinePrivateMessage vpm) {
          Bundle b = createServiceBundle();
          b.putParcelable("message", vpm);
          executeServiceAction(84, b);
                }

      public void getEditions() {
          Bundle b = createServiceBundle();
          executeServiceAction(70, b);
                }

      public void onLowMemory() {
                    for (AppSessionListener listener : this.mListeners) {
              listener.onLowMemory();
                    }
                }

      public void onTrimMemory(int level) {
                    for (AppSessionListener listener : this.mListeners) {
              listener.onTrimMemory(level);
                    }
                }

      public void removeFromVideoCache(VideoKey videoKey) {
          this.mVideoCache.remove(videoKey);
                }

      public void sendViewedVines() {
          executeServiceAction(85, createServiceBundle());
                }

      public void fetchConversationRowIdFromUserRemoteId(long userId, int networkType) {
          Bundle b = createServiceBundle();
          b.putInt("network", networkType);
          b.putParcelable(VineService.EXTRA_RECIPIENT, VineRecipient.fromUser(userId));
          b.putLong(VineService.EXTRA_RECIPIENT_ID, userId);
          executeServiceAction(91, b);
                }

      public void fetchConversationRowIdFromSingleRecipient(VineRecipient recipient, int networkType) {
          Bundle b = createServiceBundle();
          b.putInt("network", networkType);
          b.putParcelable(VineService.EXTRA_RECIPIENT, recipient);
          b.putLong(VineService.EXTRA_RECIPIENT_ID, recipient.contactId);
          executeServiceAction(91, b);
                }

      public void deleteMessage(long id) {
          Bundle b = createServiceBundle();
          b.putLong("message_id", id);
          executeServiceAction(95, b);
                }

      public void removeNotification(int notificationId, long conversationRowId) {
          Bundle b = createServiceBundle();
          b.putInt("notification_id", notificationId);
          b.putLong("conversation_row_id", conversationRowId);
          executeServiceAction(102, b);
                }

      public void retryMessagesInConversationRowId(long conversationRowId) {
          Bundle b = createServiceBundle();
          b.putLong("conversation_row_id", conversationRowId);
          executeServiceAction(103, b);
                }

      public void clearInboxPageCursors() {
          Bundle b = createServiceBundle();
          executeServiceAction(104, b);
                }

      public void clearImageCacheFromMemory() {
          this.mPhotoImagesCache.clearMemory();
                }

      public void executeServiceRequest(int actionCode, Bundle b) {
          executeServiceAction(actionCode, b);
                }

      public String saveNotificationSettings(HashMap<String, String> map) {
          Bundle b = createServiceBundle();
          b.putSerializable(VineService.EXTRA_NOTIFICATION_SETTINGS, map);
          return executeServiceAction(114, b);
                }

      public PhotoImagesCache getImageCache() {
          return this.mPhotoImagesCache;
                }

      public boolean hasPendingCacheRequests() {
                    return this.mPhotoImagesCache.hasPendingItems() || this.mVideoCache.hasPendingItems();
                }

      class ServerStatusRunnable implements Runnable {
          ServerStatusRunnable() {
                    }

                    @Override 
          public void run() {
              AppController.this.determineServerStatus();
                    }
                }

      public void determineCleanup(Session session) {
          SharedPreferences prefs = Util.getSharedPrefs(this.mContext, Settings.PREF_LAST_CLEANUP);
          long lastCleanupTime = prefs.getLong(Settings.PREF_LAST_CLEANUP, 0L);
                    if (lastCleanupTime > 0) {
                        if (System.currentTimeMillis() - lastCleanupTime > CommonUtil.HOUR_MILLIS) {
                  performCleanup(session, lastCleanupTime);
                        }
                    } else {
              SharedPreferences.Editor editor = prefs.edit();
              long cleanupTime = System.currentTimeMillis();
              editor.putLong(Settings.PREF_LAST_CLEANUP, cleanupTime);
              editor.commit();
                    }
                }

      private String performCleanup(Session session, long timeAnchor) {
          Bundle b = createServiceBundle(session);
          b.putLong(VineService.EXTRA_ANCHOR, timeAnchor);
          return executeServiceAction(29, b);
                }

      public Session.LoginStatus getLoginStatus() {
          return getActiveSession().getLoginStatus();
                }

      public boolean isLoggedIn() {
          return VineAccountHelper.isLoggedIn(this.mContext, false);
                }

      public boolean isLoggedInReadOnly() {
          return VineAccountHelper.isLoggedIn(this.mContext, true);
                }

      public void addListener(AppSessionListener listener) {
          listener.setEnabled(true);
          if (!this.mListeners.contains(listener)) {
              this.mListeners.add(listener);
                    }
                }

      public void removeListener(AppSessionListener listener) {
          listener.setEnabled(false);
          this.mListeners.remove(listener);
                }

      public void refreshSessionKey(String newSessionKey) {
          Session activeSession = VineAccountHelper.getCachedActiveSession();
          if (activeSession != null) {
              activeSession.setSessionKey(newSessionKey);
                    }
                }

      public static void clearFileCache(Context context) {
          Util.removeCache(context);
          ImageUtils.removeFiles(context);
                }

      public void clearFileCache() {
          clearFileCache(this.mContext);
                }

      public String clearDbCache(boolean notify) {
          Bundle bundle = createServiceBundle();
          bundle.putBoolean(VineService.EXTRA_NOTIFY, notify);
          return executeServiceAction(58, bundle);
                }

      public String clearDbAll() {
          Bundle bundle = createServiceBundle();
          return executeServiceAction(59, bundle);
                }

      public String expireTimeline(boolean fetchPosts, int type, String tag) {
          Bundle bundle = createServiceBundle();
          bundle.putBoolean(VineService.EXTRA_ALSO_FETCH_POSTS, fetchPosts);
          bundle.putInt("type", type);
          bundle.putString("tag_name", tag);
          return executeServiceAction(45, bundle);
                }

      public String deactivateAccount() {
          Bundle bundle = createServiceBundle();
          return executeServiceAction(67, bundle);
                }

      public String fetchChannels(int pageType) {
          Bundle b = createServiceBundle();
          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
          return executeServiceAction(53, b);
                }

      public void updateCredentials(String key) {
          getActiveSession().setSessionKey(key);
                }

      private String executeServiceAction(int actionCode, Bundle bundle) {
          CrashUtil.set("Last Service Action Code", actionCode);
          return this.mServiceConnection.queueAndExecute(actionCode, bundle);
                }

      private Bundle createServiceBundle() {
          return createServiceBundle(getActiveSession());
                }

      private Bundle createServiceBundleReadOnly() {
          return createServiceBundle(getActiveSessionReadOnly());
                }

      public static Bundle injectServiceBundle(Bundle bundle, Session session) {
          bundle.putString("s_key", session.getSessionKey());
          bundle.putLong(VineService.EXTRA_SESSION_OWNER_ID, session.getUserId());
          bundle.putString(VineService.EXTRA_ACCOUNT_NAME, session.getName());
          bundle.putString("email", session.getLoginEmail());
          return bundle;
                }

      private Bundle createServiceBundle(Session session) {
          Bundle bundle = new Bundle();
          return injectServiceBundle(bundle, session);
                }

                @Override 
      public void onPhotoImageLoaded(PhotoImagesCache cache, HashMap<ImageKey, UrlImage> image) {
          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
              AppSessionListener listener = listeners.get(i);
              if (listener.isEnabled()) {
                  listener.onPhotoImageLoaded(image);
                        }
                    }
                }

                @Override 
      public void onPhotoImageError(PhotoImagesCache cache, ImageKey key, HttpResult result) {
          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
              AppSessionListener listener = listeners.get(i);
              if (listener.isEnabled()) {
                  listener.onPhotoImageError(key, result);
                        }
                    }
                }

                @Override 
      public void onVideoPathObtained(VideoCache cache, HashMap<VideoKey, UrlVideo> videos) {
          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
              AppSessionListener listener = listeners.get(i);
              if (listener.isEnabled()) {
                  listener.onVideoPathObtained(videos);
                        }
                    }
                }

                @Override 
      public void onVideoPathError(VideoCache cache, VideoKey key, HttpResult result) {
          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
              AppSessionListener listener = listeners.get(i);
              if (listener.isEnabled()) {
                  listener.onVideoPathError(key, result);
                        }
                    }
                }

      public long getTypeaheadThrottle() {
          return 300L;
                }

      public int getTypeaheadMaxCompose() {
          return 50;
                }

      public int getTypeaheadFetchThreshold(String type) {
                    return "tag".equals(type) ? 3 : 2;
                }

      public String fetchUserTypeahead(String query) {
          Bundle bundle = createServiceBundle();
          bundle.putString(VineService.EXTRA_QUERY, query);
          return executeServiceAction(39, bundle);
                }

      public String fetchTagTypeahead(String query) {
          Bundle bundle = createServiceBundle();
          bundle.putString(VineService.EXTRA_QUERY, query);
          return executeServiceAction(40, bundle);
                }

      public String searchUsers(String query, int page) {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_QUERY, query);
          b.putInt("page", page);
          return executeServiceAction(41, b);
                }

      public String searchTags(String query, int page) {
          Bundle b = createServiceBundle();
          b.putString(VineService.EXTRA_QUERY, query);
          b.putInt("page", page);
          return executeServiceAction(42, b);
                }

      public String blockUser(long userToBlock, String username) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", getActiveId());
          b.putLong(VineService.EXTRA_BLOCK_USER_ID, userToBlock);
          if (!TextUtils.isEmpty(username)) {
              b.putString("username", username);
                    }
          return executeServiceAction(47, b);
                }

      public String unblockUser(long userToUnblock, String username) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", getActiveId());
          b.putLong(VineService.EXTRA_BLOCK_USER_ID, userToUnblock);
          if (!TextUtils.isEmpty(username)) {
              b.putString("username", username);
                    }
          return executeServiceAction(48, b);
                }

      public String reportPerson(long userToReport, String event) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userToReport);
          if (!TextUtils.isEmpty(event)) {
              b.putString("event", event);
                    }
          return executeServiceAction(49, b);
                }

      public String reportPerson(long userToReport) {
          return reportPerson(userToReport, null);
                }

      public String enableReposts(long userId) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userId);
          return executeServiceAction(68, b);
                }

      public String disableReposts(long userId) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userId);
          return executeServiceAction(69, b);
                }

      public String fetchPendingNotificationCount(Session session) {
          return executeServiceAction(65, createServiceBundle(session));
                }

      public String fetchConversations(int pageType, boolean userInitiated, int networkType) {
          Bundle b = createServiceBundleReadOnly();
          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
          b.putInt("network", networkType);
          return executeServiceAction(74, b);
                }

      public String fetchConversation(int pageType, boolean userInitiated, long conversationId, long conversationRowId, boolean prefetch) {
          Bundle b = createServiceBundleReadOnly();
          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
          b.putLong("conversation_id", conversationId);
          b.putLong("conversation_row_id", conversationRowId);
          b.putBoolean(VineService.EXTRA_PREFETCH, prefetch);
          return executeServiceAction(76, b);
                }

      public String getConversationRemoteId(long conversationRowId) {
          Bundle b = new Bundle();
          b.putLong("conversation_row_id", conversationRowId);
          return executeServiceAction(77, b);
                }

      public String ignoreConversation(long conversationRowId) {
          Bundle b = createServiceBundle();
          b.putLong("conversation_row_id", conversationRowId);
          return executeServiceAction(79, b);
                }

      public String deleteConversation(long conversationRowId) {
          Bundle b = createServiceBundle();
          b.putLong("conversation_row_id", conversationRowId);
          return executeServiceAction(80, b);
                }

      public String fetchUserId(ArrayList<String> vanityUrlSegments) {
          Bundle b = createServiceBundle();
          b.putStringArrayList("vanity_url", vanityUrlSegments);
          return executeServiceAction(86, b);
                }

      public String requestPhoneVerification(Session session, String phone, long id) {
          Bundle b = createServiceBundle(session);
          b.putString(VineService.EXTRA_PHONE, phone);
          b.putLong("user_id", id);
          return executeServiceAction(89, b);
                }

      public String requestEmailVerification(Session session, String email, long id) {
          Bundle b = createServiceBundle(session);
          b.putString("email", email);
          b.putLong("user_id", id);
          return executeServiceAction(92, b);
                }

      public String verifyPhoneNumber(Session session, String code) {
          Bundle b = createServiceBundle(session);
          b.putString(VineService.EXTRA_KEY, code);
          return executeServiceAction(90, b);
                }

      public String verifyEmail(Session session, String code) {
          Bundle b = createServiceBundle(session);
          b.putString(VineService.EXTRA_KEY, code);
          return executeServiceAction(109, b);
                }

      public String retryRequest(int actionCode, Bundle b) {
          injectServiceBundle(b, getActiveSession());
          b.remove("captcha_url");
          return executeServiceAction(actionCode, b);
                }

      public String generateReqIdForCanceledCaptcha() {
          return this.mServiceConnection.generateRequestId();
                }

      public void failRequest(String requestId, int actionCode, Bundle b) {
          b.putString(VineService.EXTRA_REQUEST_ID, requestId);
          notifyListeners(requestId, actionCode, STATUS_CODE_CAPTCHA_FAIL, null, b);
                }

      public String setHideProfileReposts(long userId, boolean block) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userId);
          b.putBoolean(VineService.EXTRA_HIDE_PROFILE_REPOSTS, block);
          return executeServiceAction(87, b);
                }

      public void clearUnreadMessageCount(long conversationRowId) {
          Bundle b = new Bundle();
          b.putLong("conversation_row_id", conversationRowId);
          executeServiceAction(94, b);
          fetchActivityCounts();
                }

      public void fetchClientFlags(boolean abortRequestsOnHostChange) {
          Bundle b = createServiceBundle();
          b.putBoolean(VineService.EXTRA_ABORT_REQUESTS, abortRequestsOnHostChange);
          executeServiceAction(111, createServiceBundle());
                }

      public void abortAllRequests() {
          this.mServiceConnection.cancelAll();
          executeServiceAction(112, createServiceBundle());
                }

      public void onMobileDataNetworkActivated() {
                    for (AppSessionListener listener : this.mListeners) {
              listener.onMobileDataNetworkActivated();
                    }
                }

      public void followChannel(long channelId, boolean following) {
          Bundle b = createServiceBundle();
          b.putLong("channelId", channelId);
          b.putBoolean("following", following);
          executeServiceAction(116, b);
                }

      public void bulkFollowChannels(ArrayList<String> channelIds) {
          Bundle b = createServiceBundle();
          b.putStringArrayList("channels", channelIds);
          executeServiceAction(117, b);
                }

      public void markChannelLastUsed(long channelId) {
          Bundle b = new Bundle();
          b.putLong("channelId", channelId);
          executeServiceAction(118, b);
                }

      public void favoriteUser(long userId, boolean favorite, boolean isFollowing) {
          Bundle b = createServiceBundle();
          b.putLong("user_id", userId);
          b.putBoolean(VineService.EXTRA_FAVORITE, favorite);
          b.putBoolean("following", isFollowing);
          executeServiceAction(119, b);
                }

      public String fetchFavoriteUsers(int page, String anchor) {
          Bundle b = createServiceBundle();
          b.putInt("page", page);
          b.putString(VineService.EXTRA_ANCHOR, anchor);
          return executeServiceAction(120, b);
                }

      public String fetchSuggestedFavoriteUsers() {
          return executeServiceAction(121, createServiceBundle());
                }

      public String fetchOnboardingSuggestedFavoriteUsers() {
          return executeServiceAction(122, createServiceBundle());
                }

      public String fetchComplaintsMenu() {
          return executeServiceAction(130, createServiceBundle());
                }
            }
