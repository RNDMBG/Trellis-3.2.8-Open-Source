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
            import co.vine.android.C0257R;
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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 100 */   public class AppController implements PhotoImagesListener, VideoListener, com.facebook.Session.StatusCallback, VineServiceConnection.ServiceResponseHandler {
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
                // Last known revine state per post. Fragments unregister while paused
                // and miss revine/unrevine callbacks; they reconcile on resume.
                private final java.util.HashMap<Long, Boolean> mRevineStates = new java.util.HashMap<>();
                private long mRevineVersion = 0L;

                static {
/* 102 */           SINGLE_FACEBOOK_DIALOG = Build.MODEL.length() == "SM-C115".length() && Build.MODEL.contains("SM-C11");
/* 112 */           ACTION_UPDATED_FILTER = new IntentFilter(ACTION_EDITION_UPDATED);
/* 143 */           if (SLog.sLogsOn) {
/* 144 */               RecordConfigUtils.FOLDER_ROOT_DIRECT_UPLOAD.mkdirs();
/* 145 */               SaveVideoClicker.setLongPressSaveDir(RecordConfigUtils.FOLDER_ROOT_DIRECT_UPLOAD);
                    }
                }

/* 155 */       public static synchronized AppController getInstance(Context context) {
                    try {
                        if (sInstance == null) {
/* 157 */                   sInstance = new AppController(context.getApplicationContext());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 159 */           return sInstance;
                }

/* 161 */       private AppController(Context context) {
/* 163 */           this.mContext = context;
/* 168 */           Point p = SystemUtil.getDisplaySize(context);
/* 169 */           int maxImageSize = Math.max(p.x, p.y);
/* 170 */           this.mPhotoImagesCache = Util.newImageCache(context, maxImageSize, 31457280);
/* 171 */           this.mPhotoImagesCache.addListener(this);
/* 172 */           this.mVideoCache = Util.newVideoCache(context);
/* 173 */           this.mVideoCache.addListener(this);
/* 174 */           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
/* 175 */           this.mServerStatusRunnable = new ServerStatusRunnable();
/* 176 */           SLog.m465i("App Controller pid: {}", Integer.valueOf(Process.myPid()));
/* 177 */           this.mServiceConnection = new VineServiceConnection(this.mContext, this);
/* 178 */           context.bindService(new Intent(context, (Class<?>) VineService.class), this.mServiceConnection, 1);
/* 180 */           context.registerReceiver(new C03701(), new IntentFilter(BC_SEND_LOOPS), CrossConstants.BROADCAST_PERMISSION, null);
/* 186 */           refreshLoopSendingAlarm(System.currentTimeMillis());
/* 187 */           context.registerReceiver(new C03712(), new IntentFilter(UploadManager.BC_INVALIDATE_CACHE), CrossConstants.BROADCAST_PERMISSION, null);
                }

                /* JADX INFO: renamed from: co.vine.android.client.AppController$1 */
/* 178 */       class C03701 extends BroadcastReceiver {
/* 179 */           C03701() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 182 */           public void onReceive(Context context, Intent intent) {
/* 183 */               AppController.this.sendLoopCounts();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.client.AppController$2 */
/* 185 */       class C03712 extends BroadcastReceiver {
/* 186 */           C03712() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 189 */           public void onReceive(Context context, Intent intent) {
/* 190 */               SLog.m446d("Received invalidate cache broadcast");
/* 191 */               VideoCache.invalidateCache();
/* 192 */               PhotoImagesCache.invalidateCache();
                    }
                }

/* 197 */       public void refreshLoopSendingAlarm(long fisrtRunTime) {
/* 198 */           AlarmManager am = (AlarmManager) this.mContext.getSystemService("alarm");
/* 199 */           am.setRepeating(0, fisrtRunTime, this.mLoopCountSubmissionInterval, PendingIntent.getBroadcast(this.mContext, 0, new Intent(BC_SEND_LOOPS).setPackage(this.mContext.getPackageName()), 134217728));
                }

/* 205 */       public void setSessionKey(String key) {
/* 206 */           Session activeSession = VineAccountHelper.getCachedActiveSession();
/* 207 */           if (activeSession != null) {
/* 208 */               activeSession.setSessionKey(key);
                    }
                }

/* 212 */       public Twitter getTwitter() {
/* 213 */           return this.mTwitter;
                }

/* 216 */       public boolean isConnectedToFacebook(Activity activity) {
                    return getValidFacebookSession(activity, true) != null;
                }

/* 220 */       public com.facebook.Session getValidFacebookSession(Activity activity, boolean closeAfterwards) {
/* 221 */           com.facebook.Session session = com.facebook.Session.getActiveSession();
/* 222 */           boolean wasOpen = session != null;
/* 223 */           if (!wasOpen) {
/* 224 */               session = com.facebook.Session.openActiveSession(activity, false, (com.facebook.Session.StatusCallback) this);
                    }
/* 226 */           if (session == null || session.getState().isClosed()) {
/* 227 */               SLog.m446d("There were no Facebook session.");
                        return null;
                    }
/* 230 */           com.facebook.Session.setActiveSession(session);
/* 231 */           SessionState state = session.getState();
/* 232 */           SLog.m447d("Old Facebook Session state : {}", state);
/* 233 */           if (state == SessionState.CREATED_TOKEN_LOADED || state.isOpened()) {
/* 235 */               SLog.m446d("State is created or opened.");
/* 236 */               if (session.getPermissions().contains(PUBLISH_ACTIONS)) {
/* 237 */                   SLog.m446d("Permission is good.");
/* 238 */                   if (!wasOpen && closeAfterwards) {
/* 239 */                       session.close();
                            }
/* 241 */                   Date date = session.getExpirationDate();
/* 242 */                   if (date != null && date.getTime() < System.currentTimeMillis()) {
/* 243 */                       SLog.m446d("Session has expired.");
                                return null;
                            }
/* 256 */                   return session;
                        }
/* 249 */               session.closeAndClearTokenInformation();
/* 250 */               SLog.m446d("Permission missing.");
                        return null;
                    }
/* 254 */           session.close();
/* 255 */           SLog.m446d("Session state is invalid.");
                    return null;
                }

/* 261 */       public void connectToFacebookInitialize(Fragment fragment) {
/* 262 */           if (SINGLE_FACEBOOK_DIALOG) {
/* 263 */               connectToFacebookPublish(fragment);
/* 279 */               return;
                    }
/* 265 */           AuthorizationClient.CLEAR_COOKIE = true;
/* 266 */           Context context = this.mContext;
/* 267 */           com.facebook.Session session = new com.facebook.Session.Builder(context).setApplicationId(FacebookVineApp.APP_ID).setTokenCachingStrategy(new SharedPreferencesTokenCachingStrategy(context)).build();
/* 269 */           com.facebook.Session.setActiveSession(session);
/* 270 */           if (!session.isOpened()) {
/* 271 */               ArrayList<String> permissions = new ArrayList<>();
/* 272 */               permissions.add("public_profile");
/* 273 */               com.facebook.Session.OpenRequest openRequest = new com.facebook.Session.OpenRequest(fragment);
/* 274 */               openRequest.setPermissions((List<String>) permissions);
/* 275 */               openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
/* 276 */               session.openForRead(openRequest);
                    }
                }

/* 281 */       public void connectToFacebookPublish(Fragment fragment) {
/* 282 */           Context context = this.mContext;
/* 283 */           com.facebook.Session session = new com.facebook.Session.Builder(context).setApplicationId(FacebookVineApp.APP_ID).setTokenCachingStrategy(new SharedPreferencesTokenCachingStrategy(context)).build();
/* 285 */           com.facebook.Session.setActiveSession(session);
/* 286 */           if (!session.isOpened()) {
/* 287 */               ArrayList<String> permissions = new ArrayList<>();
/* 288 */               permissions.add(PUBLISH_ACTIONS);
/* 289 */               com.facebook.Session.OpenRequest openRequest = new com.facebook.Session.OpenRequest(fragment);
/* 290 */               openRequest.setDefaultAudience(SessionDefaultAudience.FRIENDS);
/* 291 */               openRequest.setPermissions((List<String>) permissions);
/* 292 */               openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
/* 293 */               session.openForPublish(openRequest);
                    }
                }

                @Override // com.facebook.Session.StatusCallback
/* 298 */       public void call(com.facebook.Session session, SessionState state, Exception exception) {
/* 299 */           SLog.m451d("Session state has changed: {}.", (Object) session.getState().name(), (Throwable) exception);
/* 300 */           ArrayList<AppSessionListener> listeners = this.mListeners;
                    for (int i = listeners.size() - 1; i >= 0; i--) {
/* 302 */               listeners.get(i).onFacebookSessionChanged(session, state, exception);
                    }
                }

/* 306 */       private void onLogout() {
/* 307 */           if (!getLoginStatus().equals(Session.LoginStatus.LOGGED_OUT)) {
/* 310 */               String cachedKey = getActiveSession().getSessionKey();
/* 313 */               Util.getDefaultSharedPrefs(this.mContext).edit().clear().commit();
/* 314 */               clearDbAll();
/* 315 */               clearFileCache();
/* 316 */               clearAccount();
/* 319 */               this.mContext.startService(GCMRegistrationService.getUnregisterIntent(this.mContext, getActiveId(), cachedKey));
/* 323 */               Util.getDefaultSharedPrefs(this.mContext).edit().putLong(PREF_LAST_USER_ID, getActiveId()).commit();
/* 326 */               Session activeSession = VineAccountHelper.getCachedActiveSession();
/* 327 */               VineAccountHelper.removeAccount(this.mContext, activeSession.getLoginEmail());
/* 328 */               if (activeSession != null) {
/* 329 */                   activeSession.logout();
                        }
/* 333 */               Intent logoutIntent = SessionChangedReceiver.createSessionLogoutIntent();
/* 334 */               this.mContext.sendBroadcast(logoutIntent, CrossConstants.BROADCAST_PERMISSION);
/* 335 */               Intent i = AppImpl.getInstance().getClearNotificationsIntent(this.mContext);
/* 336 */               if (i != null) {
/* 337 */                   this.mContext.startService(i);
                        }
/* 341 */               StartActivity.toStart(this.mContext);
                    }
                }

/* 344 */       private String clearAccount() {
/* 345 */           Bundle b = createServiceBundle();
/* 346 */           return executeServiceAction(115, b);
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:354:0x0ffe A[LOOP:64: B:352:0x0ff8->B:354:0x0ffe, LOOP_END] */
/* 350 */       void notifyListeners(String reqId, int actionCode, int statusCode, String reasonPhrase, Bundle b) {
                    ArrayList<VineChannel> channels;
/* 351 */           Context context = this.mContext;
                    switch (actionCode) {
                        case 2:
                            try {
/* 356 */                       VineLogin vl = loginComplete(VineAccountHelper.getCachedActiveSession(), statusCode, b);
/* 362 */                       int errorCode = b.getInt("error_code", -1);
/* 364 */                       Intent loginIntent = SessionChangedReceiver.createSessionLoginIntent();
/* 365 */                       this.mContext.sendBroadcast(loginIntent, CrossConstants.BROADCAST_PERMISSION);
                                for (AppSessionListener listener : this.mListeners) {
/* 368 */                           listener.onLoginComplete(VineAccountHelper.getCachedActiveSession(), reqId, statusCode, reasonPhrase, errorCode, vl);
                                }
                            } catch (VineLoggingException e) {
/* 358 */                       Util.showCenteredToast(this.mContext, C0257R.string.failed_to_add_account_on_device);
/* 359 */                       CrashUtil.logException(e);
/* 1240 */                      return;
                            }
                            break;
                        case 3:
/* 374 */                   onLogout();
                            break;
                        case 4:
/* 378 */                   VineLogin login = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
/* 379 */                   String password = b.getString(VineService.EXTRA_PASSWORD);
/* 380 */                   String accountName = b.getString(VineService.EXTRA_ACCOUNT_NAME);
/* 381 */                   String avatarUrl = b.getString("avatar_url");
/* 382 */                   Uri avatar = (Uri) b.getParcelable("uri");
/* 383 */                   String key = b.getString("s_key");
/* 384 */                   long userId = b.getLong(VineService.EXTRA_SESSION_OWNER_ID);
/* 385 */                   boolean uploaded = false;
                            for (AppSessionListener listener2 : this.mListeners) {
/* 388 */                       listener2.onSignUpComplete(reqId, statusCode, reasonPhrase, login, accountName, password, avatarUrl);
/* 394 */                       if (statusCode == 200 && !uploaded && avatar != null) {
/* 396 */                           uploaded = true;
/* 397 */                           Session session = getActiveSession();
/* 398 */                           session.setSessionKey(key);
/* 399 */                           session.setUserId(userId);
/* 400 */                           updateProfilePhoto(session, avatar);
                                }
/* 403 */                       Intent loginIntent2 = SessionChangedReceiver.createSessionLoginIntent();
/* 404 */                       this.mContext.sendBroadcast(loginIntent2, CrossConstants.BROADCAST_PERMISSION);
                            }
                            break;
                        case 5:
                            for (AppSessionListener listener3 : this.mListeners) {
/* 451 */                       listener3.onGetTwitterAuthUrlComplete(reqId, b.getString(VineService.EXTRA_LOAD_URL));
                            }
                            break;
                        case 6:
                            for (AppSessionListener listener4 : this.mListeners) {
/* 458 */                       listener4.onCheckTwitterComplete(reqId, statusCode, reasonPhrase, b.getInt("error_code", -1), b.getBoolean(VineService.EXTRA_ACCOUNT_EXISTS), (VineUser) b.getParcelable("user"), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN));
                            }
/* 465 */                   Intent loginIntent3 = SessionChangedReceiver.createSessionLoginIntent();
/* 466 */                   this.mContext.sendBroadcast(loginIntent3, CrossConstants.BROADCAST_PERMISSION);
                            break;
                        case 7:
                            for (AppSessionListener listener5 : this.mListeners) {
/* 471 */                       listener5.onGetTwitterUserComplete(reqId, statusCode, reasonPhrase, (TwitterUser) b.getParcelable(VineService.EXTRA_TWITTER_USER), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN));
                            }
                            break;
                        case 8:
/* 478 */                   VineLogin vl2 = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
                            for (AppSessionListener listener6 : this.mListeners) {
/* 480 */                       listener6.onTwitterxAuthComplete(reqId, statusCode, reasonPhrase, vl2);
                            }
                            break;
                        case 9:
                            for (AppSessionListener listener7 : this.mListeners) {
/* 486 */                       listener7.onResetPasswordComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 10:
/* 588 */                   VineUser meUser = (VineUser) b.getParcelable("user");
/* 589 */                   long ownerId = b.getLong(VineService.EXTRA_SESSION_OWNER_ID);
/* 590 */                   UrlCachePolicy policy = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
/* 591 */                   if (meUser != null) {
/* 593 */                       SharedPreferences.Editor e2 = Util.getDefaultSharedPrefs(this.mContext).edit();
/* 594 */                       e2.putString(Settings.PREF_NAME, meUser.username);
/* 595 */                       e2.putString(Settings.PREF_DESCRIPTION, meUser.description);
/* 596 */                       e2.putString(Settings.PREF_LOCATION, meUser.location);
/* 597 */                       e2.putString(Settings.PREF_PHONE, meUser.phoneNumber);
/* 598 */                       e2.putString(Settings.PREF_EMAIL, meUser.email);
/* 599 */                       e2.putString(Settings.PREF_AVATAR_URL, meUser.avatarUrl);
/* 600 */                       e2.putInt(Settings.PREF_FOLLOWING_COUNT, meUser.followingCount);
/* 601 */                       e2.putInt(Settings.PREF_FOLLOWER_COUNT, meUser.followerCount);
/* 602 */                       e2.putInt(Settings.PREF_AUTHORED_POST_COUNT, meUser.authoredPostCount);
/* 603 */                       e2.putInt(Settings.PREF_POST_COUNT, meUser.postCount);
/* 604 */                       e2.putInt(Settings.PREF_LIKE_COUNT, meUser.likeCount);
/* 605 */                       e2.putLong(Settings.PREF_LOOP_COUNT, meUser.loopCount);
/* 606 */                       e2.putBoolean(Settings.PREF_TWITTER_CONNECTED, meUser.twitterConnected == 1);
/* 607 */                       e2.putBoolean(Settings.PREF_FOLLOW_EDITORS_PICKS, meUser.includePromoted == 1);
/* 608 */                       e2.putBoolean(Settings.PREF_PRIVATE, meUser.isPrivate());
/* 609 */                       e2.putBoolean(Settings.PREF_EMAIL_VERIFIED, meUser.isEmailVerified());
/* 610 */                       e2.putBoolean(Settings.PREF_PHONE_VERIFIED, meUser.isPhoneVerified());
/* 611 */                       e2.putLong(Settings.PREF_USER_ROW_ID, meUser.f99id);
/* 612 */                       if (meUser.edition != null) {
/* 613 */                           e2.putString(Settings.PREF_EDITION, meUser.edition);
                                }
/* 615 */                       e2.putBoolean(Settings.PREF_ACCEPT_OON, meUser.acceptsOutOfNetworkConversations);
/* 616 */                       e2.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, !meUser.disableAddressBook);
/* 617 */                       e2.putBoolean(Settings.PREF_EMAIL_DISCOVERABLE, !meUser.hiddenEmail);
/* 618 */                       e2.putBoolean(Settings.PREF_PHONE_DISCOVERABLE, !meUser.hiddenPhoneNumber);
/* 619 */                       e2.putBoolean(Settings.PREF_TWITTER_DISCOVERABLE, !meUser.hiddenTwitter);
/* 621 */                       int profileBackground = meUser.profileBackground < 0 ? Settings.DEFAULT_PROFILE_COLOR : meUser.profileBackground;
/* 624 */                       e2.putInt("profile_background", profileBackground);
/* 625 */                       e2.apply();
                            }
                            for (AppSessionListener listener8 : this.mListeners) {
/* 628 */                       listener8.onGetUsersMeComplete(reqId, statusCode, reasonPhrase, ownerId, meUser, policy);
                            }
                            break;
                        case 11:
                        case 22:
                        case 23:
                        case 56:
                        case 82:
                        case 110:
/* 510 */                   int count = b.getInt("count", 0);
/* 511 */                   int nextPage = b.getInt("next_page", 0);
/* 512 */                   int previousPage = b.getInt("previous_page", 0);
/* 513 */                   long anchor = b.getLong(VineService.EXTRA_ANCHOR, 0L);
/* 514 */                   ArrayList<VineUser> users = b.getParcelableArrayList("users");
                            for (AppSessionListener listener9 : this.mListeners) {
/* 516 */                       listener9.onGetUsersComplete(VineAccountHelper.getCachedActiveSession(), reqId, statusCode, reasonPhrase, count, users, nextPage, previousPage, anchor);
                            }
                            break;
                        case 12:
/* 526 */                   int next = b.getInt("next_page");
/* 527 */                   long anchor2 = b.getLong(VineService.EXTRA_ANCHOR);
/* 528 */                   ArrayList<VineComment> comments = b.getParcelableArrayList("comments");
                            for (AppSessionListener listener10 : this.mListeners) {
/* 531 */                       listener10.onGetCommentsComplete(reqId, statusCode, reasonPhrase, next, anchor2, comments);
                            }
                            break;
                        case 13:
/* 522 */                   onGetPostComplete(reqId, b, statusCode, reasonPhrase);
                            break;
                        case 14:
/* 545 */                   long id = b.getLong("post_id");
/* 546 */                   VineComment comment = (VineComment) b.getParcelable(VineService.EXTRA_COMMENT_OBJ);
/* 547 */                   String commentText = b.getString("comment");
                            for (AppSessionListener listener11 : this.mListeners) {
/* 549 */                       listener11.onPostCommentComplete(reqId, statusCode, reasonPhrase, id, commentText, comment);
                            }
                            break;
                        case 15:
/* 633 */                   int count2 = b.getInt("count", 0);
/* 634 */                   ArrayList<VineUser> users2 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener12 : this.mListeners) {
/* 636 */                       listener12.onGetTwitterFriendsComplete(reqId, statusCode, reasonPhrase, count2, users2);
                            }
                            break;
                        case 16:
/* 641 */                   int count3 = b.getInt("count", 0);
/* 642 */                   ArrayList<VineUser> users3 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener13 : this.mListeners) {
/* 644 */                       listener13.onGetAddressFriendsComplete(reqId, statusCode, reasonPhrase, count3, users3);
                            }
                            break;
                        case 17:
                            for (AppSessionListener listener14 : this.mListeners) {
/* 735 */                       listener14.onRemoveUsersComplete(reqId);
                            }
                            break;
                        case 18:
/* 650 */                   long userId2 = b.getLong(VineService.EXTRA_FOLLOW_ID);
/* 652 */                   fetchNewPosts(getActiveSession(), 10, userId2, 1, 1, 0L, false, String.valueOf(getActiveId()), null, null);
/* 657 */                   SharedPreferences prefs = Util.getDefaultSharedPrefs(this.mContext);
/* 658 */                   SharedPreferences.Editor e3 = prefs.edit();
/* 659 */                   e3.putInt(Settings.PREF_FOLLOWING_COUNT, prefs.getInt(Settings.PREF_FOLLOWING_COUNT, 0) + 1);
/* 661 */                   e3.commit();
                            for (AppSessionListener listener15 : this.mListeners) {
/* 664 */                       listener15.onFollowComplete(reqId, statusCode, reasonPhrase, userId2);
                            }
                            break;
                        case 19:
/* 675 */                   long userId3 = b.getLong(VineService.EXTRA_FOLLOW_ID);
/* 676 */                   removeFollowPosts(getActiveSession(), userId3);
/* 679 */                   SharedPreferences prefs2 = Util.getDefaultSharedPrefs(this.mContext);
/* 680 */                   SharedPreferences.Editor e4 = prefs2.edit();
/* 681 */                   int oldCount = prefs2.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
/* 682 */                   e4.putInt(Settings.PREF_FOLLOWING_COUNT, oldCount > 0 ? oldCount - 1 : 0);
/* 684 */                   e4.commit();
                            for (AppSessionListener listener16 : this.mListeners) {
/* 687 */                       listener16.onUnFollowComplete(reqId, statusCode, reasonPhrase, userId3);
                            }
                            break;
                        case 20:
/* 709 */                   VinePagedData<VineEverydayNotification> notifications = (VinePagedData) b.getParcelable("notifications");
/* 710 */                   VinePagedData<VineSingleNotification> followRequests = (VinePagedData) b.getParcelable(VineService.EXTRA_FOLLOW_REQUESTS);
/* 711 */                   UrlCachePolicy policyUsed = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                            for (AppSessionListener listener17 : this.mListeners) {
/* 713 */                       listener17.onGetActivityComplete(reqId, statusCode, reasonPhrase, notifications, followRequests, policyUsed);
                            }
                            break;
                        case 21:
/* 740 */                   VineUser user = (VineUser) b.getParcelable("user");
/* 741 */                   UrlCachePolicy policy2 = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
                            for (AppSessionListener listener18 : this.mListeners) {
/* 743 */                       listener18.onGetUserComplete(reqId, statusCode, reasonPhrase, user, policy2);
                            }
                            break;
                        case 24:
/* 819 */                   long postId = b.getLong("post_id");
/* 821 */                   if (statusCode == 200) {
/* 823 */                       SharedPreferences prefs3 = Util.getDefaultSharedPrefs(this.mContext);
/* 824 */                       SharedPreferences.Editor e5 = prefs3.edit();
/* 825 */                       e5.putInt(Settings.PREF_LIKE_COUNT, prefs3.getInt(Settings.PREF_LIKE_COUNT, 0) + 1);
/* 826 */                       e5.commit();
                            }
                            for (AppSessionListener listener19 : this.mListeners) {
/* 830 */                       listener19.onLikePost(reqId, statusCode, reasonPhrase, postId);
                            }
                            break;
                        case 25:
/* 835 */                   long postId2 = b.getLong("post_id");
/* 837 */                   if (statusCode == 200) {
/* 839 */                       SharedPreferences prefs4 = Util.getDefaultSharedPrefs(this.mContext);
/* 840 */                       SharedPreferences.Editor e6 = prefs4.edit();
/* 841 */                       int oldCount2 = prefs4.getInt(Settings.PREF_LIKE_COUNT, 0);
/* 842 */                       e6.putInt(Settings.PREF_LIKE_COUNT, oldCount2 > 0 ? oldCount2 - 1 : 0);
/* 843 */                       e6.commit();
                            }
                            for (AppSessionListener listener20 : this.mListeners) {
/* 847 */                       listener20.onUnlikePost(reqId, statusCode, reasonPhrase, postId2);
                            }
                            break;
                        case 26:
/* 748 */                   String avatarUrl2 = b.getString("avatar_url");
                            for (AppSessionListener listener21 : this.mListeners) {
/* 750 */                       listener21.onUpdateProfileComplete(reqId, statusCode, reasonPhrase, avatarUrl2);
                            }
                            break;
                        case 27:
/* 554 */                   long postId3 = b.getLong("post_id");
                            for (AppSessionListener listener22 : this.mListeners) {
/* 556 */                       listener22.onReportPostComplete(reqId, statusCode, reasonPhrase, postId3);
                            }
                            break;
                        case 28:
                            for (AppSessionListener listener23 : this.mListeners) {
/* 883 */                       listener23.onGetSinglePostComplete(reqId, statusCode, reasonPhrase, (VinePost) b.getParcelable("post"));
                            }
                            break;
                        case 29:
/* 795 */                   SharedPreferences.Editor editor = Util.getSharedPrefs(this.mContext, Settings.PREF_LAST_CLEANUP).edit();
/* 796 */                   long lastCleanupTime = System.currentTimeMillis();
/* 797 */                   editor.putLong(Settings.PREF_LAST_CLEANUP, lastCleanupTime);
/* 798 */                   editor.commit();
                            break;
                        case 30:
/* 574 */                   long commentId = Long.parseLong(b.getString("comment_id"));
                            for (AppSessionListener listener24 : this.mListeners) {
/* 576 */                       listener24.onDeleteCommentComplete(reqId, statusCode, reasonPhrase, commentId);
                            }
                            break;
                        case 31:
/* 561 */                   SharedPreferences prefs5 = Util.getDefaultSharedPrefs(this.mContext);
/* 562 */                   SharedPreferences.Editor e7 = prefs5.edit();
/* 563 */                   int oldCount3 = prefs5.getInt(Settings.PREF_POST_COUNT, 0);
/* 564 */                   long postId4 = b.getLong("post_id");
/* 565 */                   e7.putInt(Settings.PREF_POST_COUNT, oldCount3 > 0 ? oldCount3 - 1 : 0);
/* 566 */                   e7.commit();
                            for (AppSessionListener listener25 : this.mListeners) {
/* 569 */                       listener25.onDeletePostComplete(reqId, postId4, statusCode, reasonPhrase);
                            }
                            break;
                        case 32:
/* 802 */                   String username = b.getString("username");
/* 803 */                   String token = b.getString(VineService.EXTRA_KEY);
/* 804 */                   String secret = b.getString("secret");
/* 805 */                   long userId4 = b.getLong("user_id");
                            for (AppSessionListener listener26 : this.mListeners) {
/* 807 */                       listener26.onConnectTwitterComplete(reqId, statusCode, reasonPhrase, username, token, secret, userId4);
                            }
                            break;
                        case 33:
                            for (AppSessionListener listener27 : this.mListeners) {
/* 814 */                       listener27.onDisconnectTwitterComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 36:
/* 868 */                   long postId5 = b.getLong("post_id");
                            for (AppSessionListener listener28 : this.mListeners) {
/* 870 */                       listener28.onGetPostIdComplete(reqId, statusCode, reasonPhrase, postId5);
                            }
                            break;
                        case 37:
                            for (AppSessionListener listener29 : this.mListeners) {
/* 890 */                       listener29.onConnectFacebookComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 38:
/* 917 */                   ServerStatus ss = (ServerStatus) b.getParcelable(VineService.EXTRA_SERVER_STATUS);
/* 918 */                   if (statusCode == 200 && ss != null && !TextUtils.isEmpty(ss.uploadType)) {
/* 920 */                       SharedPreferences.Editor e8 = Util.getDefaultSharedPrefs(this.mContext).edit();
/* 921 */                       e8.putString(Settings.PREF_SERVER_UPLOAD_TYPE, ss.uploadType);
/* 922 */                       e8.commit();
                                break;
                            }
                            break;
                        case 39:
/* 939 */                   String query = b.getString(VineService.EXTRA_QUERY);
/* 940 */                   ArrayList<VineUser> users4 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener30 : this.mListeners) {
/* 942 */                       listener30.onGetUserTypeAheadComplete(reqId, statusCode, reasonPhrase, query, users4);
                            }
                            break;
                        case 40:
/* 955 */                   String query2 = b.getString(VineService.EXTRA_QUERY);
/* 956 */                   ArrayList<VineTag> tags = b.getParcelableArrayList("tags");
                            for (AppSessionListener listener31 : this.mListeners) {
/* 958 */                       listener31.onGetTagTypeAheadComplete(reqId, statusCode, reasonPhrase, query2, tags);
                            }
                            break;
                        case 41:
/* 927 */                   int count4 = b.getInt("count", 0);
/* 928 */                   int nextPage2 = b.getInt("next_page", 0);
/* 929 */                   int previousPage2 = b.getInt("previous_page", 0);
/* 930 */                   ArrayList<VineUser> users5 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener32 : this.mListeners) {
/* 933 */                       listener32.onUserSearchComplete(reqId, statusCode, reasonPhrase, count4, nextPage2, previousPage2, users5);
                            }
                            break;
                        case 42:
/* 970 */                   int count5 = b.getInt("count", 0);
/* 971 */                   int nextPage3 = b.getInt("next_page", 0);
/* 972 */                   int previousPage3 = b.getInt("previous_page", 0);
/* 973 */                   ArrayList<VineTag> tags2 = b.getParcelableArrayList("tags");
                            for (AppSessionListener listener33 : this.mListeners) {
/* 976 */                       listener33.onTagSearchComplete(reqId, statusCode, reasonPhrase, count5, nextPage3, previousPage3, tags2);
                            }
                            break;
                        case 44:
/* 692 */                   boolean shouldFollow = b.getBoolean(VineService.EXTRA_SHOULD_FOLLOW_EDITORS_PICKS);
/* 693 */                   if (!shouldFollow) {
/* 695 */                       expireTimeline(true, 1, String.valueOf(VineAccountHelper.getCachedActiveSession().getUserId()));
                            } else {
/* 698 */                       fetchNewPosts(getActiveSession(), 10, 0L, 1, 1, 0L, true, String.valueOf(getActiveId()), null, null);
                            }
                            for (AppSessionListener listener34 : this.mListeners) {
/* 703 */                       listener34.onUpdateFollowEditorsPicksComplete(reqId, statusCode, reasonPhrase, shouldFollow);
                            }
                            break;
                        case 45:
/* 982 */                   int type = b.getInt("type");
/* 983 */                   String tag = b.getString("tag_name");
/* 984 */                   boolean alsoFetchPosts = b.getBoolean(VineService.EXTRA_ALSO_FETCH_POSTS, false);
/* 986 */                   if (alsoFetchPosts) {
/* 987 */                       fetchNewPosts(getActiveSession(), 10, getActiveId(), type, 1, 0L, true, tag, null, null);
                            }
                            break;
                        case 47:
/* 1000 */                  boolean userBlocked = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
/* 1001 */                  String username2 = b.getString("username");
                            for (AppSessionListener listener35 : this.mListeners) {
/* 1003 */                      listener35.onBlockUserComplete(reqId, statusCode, reasonPhrase, userBlocked, username2);
                            }
                            break;
                        case 48:
/* 1008 */                  boolean userUnblocked = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
/* 1009 */                  String username3 = b.getString("username");
                            for (AppSessionListener listener36 : this.mListeners) {
/* 1011 */                      listener36.onUnblockUserComplete(reqId, statusCode, reasonPhrase, userUnblocked, username3);
                            }
                            break;
                        case 49:
                            for (AppSessionListener listener37 : this.mListeners) {
/* 1017 */                      listener37.onReportUserComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 50:
/* 1036 */                  long userId5 = b.getLong("user_id");
/* 1037 */                  boolean accept = b.getBoolean(VineService.EXTRA_ACCEPT);
                            for (AppSessionListener listener38 : this.mListeners) {
/* 1039 */                      listener38.onRespondFollowRequestComplete(reqId, statusCode, reasonPhrase, accept, userId5);
                            }
                            break;
                        case 51:
/* 1045 */                  boolean explicit = b.getBoolean("explicit");
                            for (AppSessionListener listener39 : this.mListeners) {
/* 1047 */                      listener39.onUpdateExplicitComplete(reqId, statusCode, reasonPhrase, explicit);
                            }
                            break;
                        case 52:
/* 1052 */                  boolean priv = b.getBoolean(VineService.EXTRA_PRIVATE);
                            for (AppSessionListener listener40 : this.mListeners) {
/* 1054 */                      listener40.onUpdatePrivateComplete(reqId, statusCode, reasonPhrase, priv);
                            }
/* 1058 */                  channels = b.getParcelableArrayList("channels");
                            for (AppSessionListener listener41 : this.mListeners) {
/* 1060 */                      listener41.onGetChannelsComplete(reqId, statusCode, reasonPhrase, channels);
                            }
                            break;
                        case 53:
/* 1058 */                  channels = b.getParcelableArrayList("channels");
                            for (AppSessionListener listener41 : this.mListeners) {
/* 1060 */                      listener41.onGetChannelsComplete(reqId, statusCode, reasonPhrase, channels);
                            }
                            break;
                        case 54:
/* 852 */                   long postId6 = b.getLong("post_id");
/* 853 */                   VineRepost repost = (VineRepost) b.getSerializable("repost");
                            if (statusCode >= 200 && statusCode < 300) {
                                noteRevineState(postId6, true);
                            }
                            for (AppSessionListener listener42 : this.mListeners) {
/* 856 */                       listener42.onRevine(reqId, statusCode, reasonPhrase, postId6, repost);
                            }
                            break;
                        case 55:
/* 861 */                   long postId7 = b.getLong("post_id");
                            if (statusCode >= 200 && statusCode < 300) {
                                noteRevineState(postId7, false);
                            }
                            for (AppSessionListener listener43 : this.mListeners) {
/* 863 */                       listener43.onUnrevine(reqId, statusCode, reasonPhrase, postId7);
                            }
                            break;
                        case 58:
/* 1092 */                  clearFileCache();
                            for (AppSessionListener listener44 : this.mListeners) {
/* 1094 */                      listener44.onClearCacheComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 60:
/* 895 */                   long userId6 = b.getLong("user_id");
                            for (AppSessionListener listener45 : this.mListeners) {
/* 897 */                       listener45.onGcmRegistrationComplete(reqId, statusCode, reasonPhrase, userId6);
                            }
                            break;
                        case 61:
/* 903 */                   deleteSession(b.getString("s_key"));
                            break;
                        case 63:
                            for (AppSessionListener listener46 : this.mListeners) {
/* 1066 */                      listener46.onSharePostComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 65:
/* 1080 */                  int count6 = b.getInt("count");
/* 1081 */                  if (statusCode == 200) {
/* 1082 */                      Util.getDefaultSharedPrefs(this.mContext).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, count6).commit();
                            }
                            for (AppSessionListener listener47 : this.mListeners) {
/* 1086 */                      listener47.onGetPendingNotificationCountComplete(reqId, statusCode, reasonPhrase, count6);
                            }
                            break;
                        case 66:
/* 1135 */                  LoopManager loopManager = LoopManager.get(this.mContext);
/* 1136 */                  VineLoopSubmissionResponse response = (VineLoopSubmissionResponse) b.getParcelable(VineService.EXTRA_LOOP_SUBMISSION_RESULT);
/* 1137 */                  if (statusCode == 200 && response != null) {
/* 1138 */                      long intervalMs = ((long) response.submissionInterval) * 1000;
/* 1139 */                      SLog.m448d("Changing loop interval from {} to {}", Long.valueOf(this.mLoopCountSubmissionInterval), Long.valueOf(intervalMs));
/* 1140 */                      this.mLoopCountSubmissionInterval = Math.max(intervalMs, 60000L);
/* 1141 */                      refreshLoopSendingAlarm(System.currentTimeMillis() + this.mLoopCountSubmissionInterval);
/* 1142 */                      loopManager.onServerSucess();
                            } else {
/* 1144 */                      List<LoopManager.Record> failedLoops = b.getParcelableArrayList("loops");
/* 1145 */                      SLog.m447d("Loop submission failed; re-saving {} pending loops", Integer.valueOf(failedLoops.size()));
/* 1146 */                      loopManager.onServerFailure(failedLoops);
                            }
                            break;
                        case 67:
/* 993 */                   boolean success = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener48 : this.mListeners) {
/* 995 */                       listener48.onDeactivateAccountComplete(reqId, statusCode, reasonPhrase, success);
                            }
                            break;
                        case 68:
/* 1022 */                  boolean success2 = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener49 : this.mListeners) {
/* 1024 */                      listener49.onEnableUserRepostsComplete(reqId, statusCode, reasonPhrase, success2);
                            }
                            break;
                        case 69:
/* 1029 */                  boolean success3 = b.getBoolean(VineService.EXTRA_RESPONSE_SUCCESS, false);
                            for (AppSessionListener listener50 : this.mListeners) {
/* 1031 */                      listener50.onDisableUserRepostsComplete(reqId, statusCode, reasonPhrase, success3);
                            }
                            break;
                        case 70:
                            for (AppSessionListener listener51 : this.mListeners) {
/* 1100 */                      listener51.onGetEditionsComplete(statusCode, (ArrayList) b.getSerializable("editions"));
                            }
                            break;
                        case 71:
/* 755 */                   String edition = b.getString("edition");
/* 756 */                   SLog.m447d("Edition update success. New edition is {}", edition);
/* 757 */                   if (statusCode == 200) {
/* 758 */                       SharedPreferences.Editor editor2 = Util.getDefaultSharedPrefs(this.mContext).edit();
/* 759 */                       editor2.putString(Settings.PREF_EDITION, edition);
/* 760 */                       editor2.commit();
                            }
                            for (AppSessionListener listener52 : this.mListeners) {
/* 763 */                       listener52.onUpdateEditionComplete(reqId, statusCode, reasonPhrase, edition);
                            }
/* 765 */                   this.mContext.sendBroadcast(new Intent(ACTION_EDITION_UPDATED), CrossConstants.BROADCAST_PERMISSION);
                            break;
                        case 74:
/* 963 */                   int count7 = b.getInt("count", 0);
                            for (AppSessionListener listener53 : this.mListeners) {
/* 965 */                       listener53.onGetMessageInboxComplete(reqId, statusCode, reasonPhrase, count7);
                            }
                            break;
                        case 76:
                            for (AppSessionListener listener54 : this.mListeners) {
/* 537 */                       listener54.onGetConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"), b.getInt(VineService.EXTRA_RESPONSE_CODE), b.getBoolean(VineService.EXTRA_EMPTY));
                            }
                            break;
                        case 77:
/* 417 */                   long conversationId = b.getLong("conversation_id");
                            for (AppSessionListener listener55 : this.mListeners) {
/* 419 */                       listener55.onGetConversationRemoteIdComplete(conversationId);
                            }
                            break;
                        case 79:
                            for (AppSessionListener listener56 : this.mListeners) {
/* 492 */                       listener56.onIgnoreConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"));
                            }
                            break;
                        case 80:
                            for (AppSessionListener listener57 : this.mListeners) {
/* 499 */                       listener57.onDeleteConversationComplete(reqId, statusCode, reasonPhrase, b.getLong("conversation_row_id"));
                            }
                            break;
                        case 81:
/* 719 */                   int count8 = b.getInt("count");
/* 720 */                   int messagesCount = b.getInt(VineService.EXTRA_MESSAGES_COUNT);
/* 721 */                   int followRequestCount = b.getInt(VineService.EXTRA_FOLLOW_REQUEST_COUNT, 0);
/* 722 */                   SharedPreferences.Editor editor3 = Util.getDefaultSharedPrefs(this.mContext).edit();
/* 723 */                   editor3.putInt(Settings.PREF_FOLLOW_REQUEST_COUNT, followRequestCount);
/* 724 */                   editor3.putInt(Settings.PREF_UNREAD_MESSAGE_COUNT, messagesCount);
/* 725 */                   UrlCachePolicy policyUsed2 = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
/* 726 */                   editor3.apply();
                            for (AppSessionListener listener58 : this.mListeners) {
/* 728 */                       listener58.onGetActivityCountComplete(reqId, statusCode, reasonPhrase, count8, followRequestCount, messagesCount, policyUsed2);
                            }
                            break;
                        case 86:
/* 875 */                   long userId7 = b.getLong("user_id");
                            for (AppSessionListener listener59 : this.mListeners) {
/* 877 */                       listener59.onGetUserIdComplete(reqId, statusCode, reasonPhrase, userId7);
                            }
                            break;
                        case 88:
/* 409 */                   String avatarUrl3 = b.getString("avatar_url");
                            for (AppSessionListener listener60 : this.mListeners) {
/* 411 */                       listener60.onProfilePhotoUpdatedComplete(reqId, statusCode, reasonPhrase, avatarUrl3);
                            }
                            break;
                        case 89:
/* 1108 */                  String phone = b.getString(VineService.EXTRA_PHONE);
                            for (AppSessionListener listener61 : this.mListeners) {
/* 1110 */                      listener61.onRequestPhoneVerificationComplete(reqId, statusCode, reasonPhrase, phone);
                            }
                            break;
                        case 90:
                            for (AppSessionListener listener62 : this.mListeners) {
/* 1122 */                      listener62.onVerifyPhoneNumberComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 91:
/* 438 */                   long objectId = b.getLong("conversation_row_id");
/* 439 */                   VineRecipient recipient = (VineRecipient) b.getParcelable(VineService.EXTRA_RECIPIENT);
/* 440 */                   long recipientId = b.getLong(VineService.EXTRA_RECIPIENT_ID);
/* 441 */                   String username4 = b.getString("username");
/* 442 */                   boolean amFollowing = b.getBoolean("am_following");
                            for (AppSessionListener listener63 : this.mListeners) {
/* 444 */                       listener63.onGetConversationRowIdComplete(recipientId, !recipient.isFromUser(), objectId, username4, amFollowing);
                            }
                            break;
                        case 92:
/* 1127 */                  String email = b.getString("email");
                            for (AppSessionListener listener64 : this.mListeners) {
/* 1129 */                      listener64.onRequestEmailVerificationComplete(reqId, statusCode, reasonPhrase, email);
                            }
                            break;
                        case 96:
/* 1076 */                  SLog.m449d("Share as a vm : {} {} {}.", reqId, Integer.valueOf(statusCode), reasonPhrase);
                            break;
                        case 97:
/* 907 */                   ArrayList<VineSingleNotification> notifications2 = b.getParcelableArrayList("notifications");
/* 909 */                   VineSingleNotification lastNotification = (VineSingleNotification) b.getParcelable("notification");
                            for (AppSessionListener listener65 : this.mListeners) {
/* 912 */                       listener65.onMergeNotificationComplete(lastNotification, notifications2);
                            }
                            break;
                        case 98:
                            for (AppSessionListener listener66 : this.mListeners) {
/* 1072 */                      listener66.onPostVideoComplete(reqId, statusCode, reasonPhrase);
                            }
/* 1076 */                  SLog.m449d("Share as a vm : {} {} {}.", reqId, Integer.valueOf(statusCode), reasonPhrase);
                            break;
                        case 99:
/* 947 */                   String query3 = b.getString(VineService.EXTRA_QUERY);
/* 948 */                   ArrayList<VineUser> users6 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener67 : this.mListeners) {
/* 950 */                       listener67.onGetFriendsTypeAheadComplete(reqId, statusCode, reasonPhrase, query3, users6);
                            }
                            break;
                        case 105:
/* 769 */                   boolean acceptOon = b.getBoolean(VineService.EXTRA_ACCEPT_OON);
                            for (AppSessionListener listener68 : this.mListeners) {
/* 771 */                       listener68.onUpdateAcceptOonComplete(reqId, statusCode, reasonPhrase, acceptOon);
                            }
                            break;
                        case 106:
/* 776 */                   boolean enableAddressBook = b.getBoolean(VineService.EXTRA_ENABLE);
                            for (AppSessionListener listener69 : this.mListeners) {
/* 778 */                       listener69.onUpdateEnableAddressBookComplete(reqId, statusCode, reasonPhrase, enableAddressBook);
                            }
                            break;
                        case 107:
                            for (AppSessionListener listener70 : this.mListeners) {
/* 670 */                       listener70.onBulkFollowComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 108:
/* 784 */                   boolean enable = b.getBoolean(VineService.EXTRA_ENABLE);
/* 785 */                   int type2 = b.getInt("type");
                            for (AppSessionListener listener71 : this.mListeners) {
/* 787 */                       listener71.onUpdateDiscoverability(reqId, statusCode, reasonPhrase, type2, enable);
                            }
                            break;
                        case 109:
                            for (AppSessionListener listener72 : this.mListeners) {
/* 1116 */                      listener72.onVerifyEmailComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 111:
                            try {
/* 1152 */                      ClientFlagsHelper.setLastCheckMillis(context);
/* 1153 */                      VineClientFlags clientFlags = (VineClientFlags) b.getParcelable(VineService.EXTRA_CLIENT_FLAGS);
/* 1154 */                      if (statusCode == 200 && clientFlags != null) {
/* 1155 */                          ClientFlagsHelper.updateClientFlags(context, clientFlags);
/* 1156 */                          VineAPI.getInstance(context).refreshHostUrls(clientFlags, context.getResources());
/* 1158 */                          boolean abortRequests = b.getBoolean(VineService.EXTRA_ABORT_REQUESTS, true);
/* 1159 */                          boolean hostsDidChange = ClientFlagsHelper.hostsDidChange(context, clientFlags);
/* 1160 */                          if (hostsDidChange) {
/* 1161 */                              if (abortRequests) {
                                            abortAllRequests();
                                        }
/* 1163 */                              FlurryUtils.trackZeroRatedSessionStarted(!TextUtils.isEmpty(clientFlags.apiHost));
                                    }
/* 1166 */                          boolean showBanner = (TextUtils.isEmpty(clientFlags.messageTitle) && TextUtils.isEmpty(clientFlags.messageText)) ? false : true;
                                    for (AppSessionListener listener73 : this.mListeners) {
/* 1169 */                              listener73.onFetchClientFlagsComplete(reqId, statusCode, reasonPhrase, clientFlags, showBanner);
                                        break;
                                    }
                                    break;
                                }
                            } catch (Exception e9) {
/* 1174 */                      CrashUtil.logException(e9);
/* 1240 */                      return;
                            }
                            break;
                        case 112:
/* 1179 */                  context.sendBroadcast(new Intent(ACTION_ABORT_ALL_REQUESTS), CrossConstants.BROADCAST_PERMISSION);
                            for (AppSessionListener listener74 : this.mListeners) {
/* 1181 */                      listener74.onAbortAllRequestsComplete();
                            }
                            break;
                        case 113:
/* 424 */                   ArrayList<VineNotificationSetting> notifSettings = b.getParcelableArrayList(VineService.EXTRA_NOTIFICATION_SETTINGS);
                            for (AppSessionListener listener75 : this.mListeners) {
/* 427 */                       listener75.onGetNotificationSettingsComplete(reqId, notifSettings);
                            }
                            break;
                        case 114:
                            for (AppSessionListener listener76 : this.mListeners) {
/* 433 */                       listener76.onSaveNotificationSettingsComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 116:
                            for (AppSessionListener listener77 : this.mListeners) {
/* 1187 */                      listener77.onFollowChannelComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 117:
                            for (AppSessionListener listener78 : this.mListeners) {
/* 1193 */                      listener78.onBulkFollowChannelComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 119:
                            for (AppSessionListener listener79 : this.mListeners) {
/* 1199 */                      listener79.onFavoriteUserComplete(reqId, statusCode, reasonPhrase);
                            }
                            break;
                        case 120:
/* 1204 */                  int nextPage4 = b.getInt("next_page", 0);
/* 1205 */                  int prevPage = b.getInt("previous_page", 0);
/* 1206 */                  String anchor3 = b.getString(VineService.EXTRA_ANCHOR);
/* 1207 */                  ArrayList<VineUser> users7 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener80 : this.mListeners) {
/* 1209 */                      listener80.onFetchFavoritePeopleComplete(reqId, statusCode, reasonPhrase, users7, nextPage4, prevPage, anchor3);
                            }
                            break;
                        case 121:
/* 1215 */                  ArrayList<VineUser> users8 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener81 : this.mListeners) {
/* 1217 */                      listener81.onFetchSuggestedFavoritesComplete(reqId, statusCode, reasonPhrase, users8);
                            }
                            break;
                        case 122:
/* 1222 */                  ArrayList<VineUser> users9 = b.getParcelableArrayList("users");
                            for (AppSessionListener listener82 : this.mListeners) {
/* 1224 */                      listener82.onFetchOnboardingSuggestedFavoritesComplete(reqId, statusCode, reasonPhrase, users9);
                            }
                            break;
                        case 126:
/* 581 */                   long commentId2 = Long.parseLong(b.getString("comment_id"));
                            for (AppSessionListener listener83 : this.mListeners) {
/* 583 */                       listener83.onSpamCommentComplete(reqId, statusCode, reasonPhrase, commentId2);
                            }
                            break;
                        case 130:
/* 1229 */                  ArrayList<ComplaintMenuOption> options = (ArrayList) b.getSerializable(VineService.EXTRA_COMPLAINT_MENU);
                            for (AppSessionListener listener84 : this.mListeners) {
/* 1232 */                      listener84.onComplaintMenu(reqId, statusCode, options);
                            }
                            break;
                    }
                }

/* 1242 */      public void onGetPostComplete(String reqId, Bundle b, int statusCode, String reasonPhrase) {
                    ArrayList<VinePost> posts;
/* 1243 */          int type = b.getInt("type", -1);
/* 1244 */          int count = b.getInt("count", 0);
/* 1245 */          int size = b.getInt(VineService.EXTRA_SIZE, 0);
/* 1246 */          String tag = b.getString("tag_name");
/* 1247 */          int pageType = b.getInt(VineService.EXTRA_PAGE_TYPE);
/* 1248 */          int next = b.getInt("next_page");
/* 1249 */          int previous = b.getInt("previous_page");
/* 1250 */          long anchor = b.getLong(VineService.EXTRA_ANCHOR);
/* 1251 */          String title = b.getString("title");
/* 1252 */          boolean userInitiated = b.getBoolean(VineService.EXTRA_USER_INITIATED);
/* 1253 */          boolean memory = b.getBoolean(VineService.EXTRA_IN_MEMORY, false);
/* 1254 */          UrlCachePolicy cachePolicy = (UrlCachePolicy) b.getParcelable(VineService.EXTRA_CACHE_POLICY);
/* 1256 */          if (memory) {
/* 1257 */              posts = b.getParcelableArrayList("posts");
                    } else {
/* 1259 */              posts = null;
                    }
/* 1261 */          if (reqId != null) {
                        for (AppSessionListener listener : this.mListeners) {
/* 1263 */                  listener.onGetTimeLineComplete(reqId, statusCode, reasonPhrase, type, count, memory, posts, tag, pageType, next, previous, anchor, userInitiated, size, title, cachePolicy, b);
                        }
                    }
/* 1268 */          if (statusCode == 200) {
/* 1269 */              determineCleanup(getActiveSession());
                    }
                }

                @Override // co.vine.android.service.VineServiceConnection.ServiceResponseHandler
/* 1273 */      public void handleServiceResponse(int actionCode, int statusCode, String reasonPhrase, Bundle b) {
/* 1274 */
/* 1274 */          String reqId = b.getString(VineService.EXTRA_REQUEST_ID);
/* 1276 */          if (reqId != null) {
/* 1281 */              PendingAction action = this.mServiceConnection.remove(reqId);
/* 1283 */              if (b.getBoolean(VineService.EXTRA_LOGGED_OUT) && actionCode != 6) {
/* 1284 */                  onLogout();
/* 1302 */                  return;
                        }
/* 1288 */              if (b.getBoolean(VineService.EXTRA_REFRESH_SESSION, false)) {
/* 1289 */                  refreshSessionKey(b.getString("s_key"));
                        }
/* 1292 */              String captchaUrl = b.getString("captcha_url");
/* 1293 */              b.remove("captcha_url");
/* 1294 */              if (captchaUrl != null) {
                            for (AppSessionListener listener : this.mListeners) {
/* 1296 */                      listener.onCaptchaRequired(reqId, captchaUrl, action);
                            }
/* 1302 */                  return;
                        }
/* 1301 */              notifyListeners(reqId, actionCode, statusCode, reasonPhrase, b);
                    }
                }

/* 1304 */      public Bitmap getPhotoBitmap(ImageKey key) {
/* 1305 */          if (!Util.isUrlLocal(key.url)) {
/* 1306 */              return this.mPhotoImagesCache.getBitmap(getActiveId(), key);
                    }
/* 1308 */          String path = Uri.parse(key.url).getPath();
/* 1309 */          return BitmapFactory.decodeFile(path);
                }

/* 1313 */      public String getVideoFilePath(VideoKey key) {
                    return !Util.isUrlLocal(key.url) ? this.mVideoCache.getFile(getActiveId(), key, false) : Uri.parse(key.url).getPath();
                }

/* 1321 */      public String peekVideoFilePath(VideoKey key) {
/* 1322 */          return this.mVideoCache.getFile(getActiveId(), key, true);
                }

/* 1333 */      public boolean attemptCancelVideoFetchRequest(VideoKey key) {
                    return this.mVideoCache.cancel(key) > 0;
                }

/* 1338 */      public VineLogin loginComplete(Session session, int responseCode, String accountName, String password, VineLogin login, String avatarUrl) throws VineLoggingException {
                    String uniqueLogin;
/* 1339 */          if (responseCode == 200) {
/* 1340 */              session.setLoginStatus(Session.LoginStatus.LOGGED_IN);
                        switch (login.loginType) {
                            case 1:
/* 1350 */                      uniqueLogin = login.username;
                                break;
                            case 2:
/* 1346 */                      uniqueLogin = accountName;
                                break;
                            default:
/* 1354 */                      throw new UnsupportedOperationException("Unsupported login type.");
                        }
/* 1358 */              session.setUserId(login.userId);
/* 1359 */              session.setName(accountName);
/* 1360 */              session.setSessionKey(login.key);
/* 1361 */              session.setLoginEmail(uniqueLogin);
/* 1362 */              session.setAvatarUrl(avatarUrl);
/* 1364 */              VineAccountHelper.createAccount(this.mContext, uniqueLogin, login, password, avatarUrl, accountName);
/* 1366 */              SettingsManager.setCurrentAccount(this.mContext, uniqueLogin, accountName, login.userId);
                        if (Util.getDefaultSharedPrefs(this.mContext).getLong(PREF_LAST_USER_ID, 0L) != login.userId) {
/* 1371 */                  clearFacebookToken();
/* 1372 */                  Intent i = AppImpl.getInstance().getDiscardAllIntent(this.mContext);
/* 1373 */                  if (i != null) {
/* 1374 */                      this.mContext.startService(i);
                            }
                        }
/* 1378 */              if (login.edition != null) {
/* 1379 */                  Util.getDefaultSharedPrefs(this.mContext).edit().putString(Settings.PREF_EDITION, login.edition).commit();
                        }
/* 1383 */              FlurryAgent.setUserId(String.valueOf(login.userId));
/* 1386 */              return login;
                    }
/* 1386 */          return null;
                }

/* 1390 */      public VineLogin loginComplete(Session session, int responseCode, Bundle b) throws VineLoggingException {
/* 1391 */          VineLogin vl = (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN);
/* 1392 */          if (vl != null) {
/* 1393 */              return loginComplete(session, responseCode, b.getString(VineService.EXTRA_ACCOUNT_NAME), b.getString(VineService.EXTRA_PASSWORD), (VineLogin) b.getParcelable(VineService.EXTRA_LOGIN), b.getString("avatar_url"));
                    }
                    return null;
                }

/* 1403 */      public String fetchUsersMe(long userId, UrlCachePolicy cachePolicy) {
/* 1404 */          Bundle b = createServiceBundle();
/* 1405 */          b.putLong(VineService.EXTRA_SESSION_OWNER_ID, userId);
/* 1406 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
/* 1407 */          return executeServiceAction(10, b);
                }

/* 1411 */      public String updateProfile(Session session, String name, String description, String location, String email, String phone, Uri avatar, int profileBackgroundColor) {
/* 1412 */          Bundle b = createServiceBundle(session);
/* 1413 */          b.putString(VineService.EXTRA_ACCOUNT_NAME, name);
/* 1414 */          b.putString(VineService.EXTRA_DESCRIPTION, Util.cleanse(description));
/* 1415 */          b.putString("location", Util.cleanse(location));
/* 1416 */          b.putString("email", email);
/* 1417 */          b.putString(VineService.EXTRA_PHONE, phone);
/* 1418 */          b.putInt(VineService.EXTRA_COLOR_INT, profileBackgroundColor);
/* 1419 */          if (avatar != null) {
/* 1420 */              b.putParcelable("uri", avatar);
                    }
/* 1422 */          return executeServiceAction(26, b);
                }

/* 1425 */      public String updateAcceptOon(boolean acceptOon) {
/* 1426 */          Bundle b = createServiceBundle();
/* 1427 */          b.putBoolean(VineService.EXTRA_ACCEPT_OON, acceptOon);
/* 1428 */          return executeServiceAction(105, b);
                }

/* 1431 */      public String updateEnableAddressBook(boolean enable) {
/* 1432 */          Bundle b = createServiceBundle();
/* 1433 */          b.putBoolean(VineService.EXTRA_ENABLE, enable);
/* 1434 */          return executeServiceAction(106, b);
                }

/* 1437 */      public String updateDiscoverability(int type, boolean enable) {
/* 1438 */          Bundle b = createServiceBundle();
/* 1439 */          b.putBoolean(VineService.EXTRA_ENABLE, enable);
/* 1440 */          b.putInt("type", type);
/* 1441 */          return executeServiceAction(108, b);
                }

/* 1444 */      public String updateProfilePhoto(Session session, Uri avatar) {
/* 1445 */          Bundle b = createServiceBundle(session);
/* 1446 */          b.putParcelable("uri", avatar);
/* 1447 */          return executeServiceAction(88, b);
                }

/* 1450 */      public String fetchUser(long userId, UrlCachePolicy cachePolicy) {
/* 1451 */          Bundle b = createServiceBundle();
/* 1452 */          b.putLong("user_id", userId);
/* 1453 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
/* 1454 */          return executeServiceAction(21, b);
                }

/* 1457 */      public String fetchFollowers(long userId, int page, long anchor) {
/* 1458 */          Bundle b = createServiceBundle();
/* 1459 */          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
/* 1460 */          b.putInt("page", page);
/* 1461 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1462 */          return executeServiceAction(22, b);
                }

/* 1465 */      public String fetchFollowing(long userId, int page, long anchor) {
/* 1466 */          Bundle b = createServiceBundle();
/* 1467 */          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
/* 1468 */          b.putInt("page", page);
/* 1469 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1470 */          return executeServiceAction(23, b);
                }

/* 1473 */      public String fetchNotificationSettings() {
/* 1474 */          Bundle b = createServiceBundle();
/* 1475 */          return executeServiceAction(113, b);
                }

/* 1478 */      public String fetchFriends(int pageType, int type) {
/* 1479 */          Bundle b = createServiceBundle();
/* 1480 */          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
/* 1481 */          b.putInt("type", type);
/* 1482 */          return executeServiceAction(82, b);
                }

/* 1485 */      public String fetchFriendsTypeAhead(String key) {
/* 1486 */          Bundle b = createServiceBundle();
/* 1487 */          b.putString(VineService.EXTRA_QUERY, key);
/* 1488 */          return executeServiceAction(99, b);
                }

/* 1491 */      public String fetchPostLikers(Session session, long postId, int page, long anchor) {
/* 1492 */          Bundle b = createServiceBundle(session);
/* 1493 */          b.putLong("post_id", postId);
/* 1494 */          b.putInt("page", page);
/* 1495 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1496 */          return executeServiceAction(11, b);
                }

/* 1499 */      public String fetchNotificationUsers(Session session, long notificationId, int page, long anchor) {
/* 1500 */          Bundle b = createServiceBundle(session);
/* 1501 */          b.putLong("notification_id", notificationId);
/* 1502 */          b.putInt("page", page);
/* 1503 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1504 */          return executeServiceAction(110, b);
                }

/* 1507 */      public String fetchReviners(Session session, long postId, int page, long anchor) {
/* 1508 */          Bundle b = createServiceBundle(session);
/* 1509 */          b.putLong("post_id", postId);
/* 1510 */          b.putInt("page", page);
/* 1511 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1512 */          return executeServiceAction(56, b);
                }

/* 1516 */      public String fetchActivity(Session session, int page, long anchor, boolean followRequests, boolean clearPendingCount, UrlCachePolicy cachePolicy) {
/* 1517 */          Bundle b = createServiceBundle(session);
/* 1518 */          b.putInt("page", page);
/* 1519 */          b.putBoolean(VineService.EXTRA_GET_FOLLOW_REQUESTS, followRequests);
/* 1520 */          b.putBoolean(VineService.EXTRA_CLEAR, clearPendingCount);
/* 1521 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1522 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
/* 1523 */          return executeServiceAction(20, b);
                }

/* 1526 */      public String fetchActivityCounts() {
/* 1527 */          Bundle b = createServiceBundle();
/* 1528 */          b.putLong(VineService.EXTRA_SESSION_OWNER_ID, getActiveId());
/* 1529 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, UrlCachePolicy.FORCE_REFRESH);
/* 1530 */          return executeServiceAction(81, b);
                }

/* 1533 */      public String followUser(Session session, long userIdToFollow, boolean notify) {
/* 1534 */          Bundle b = createServiceBundle(session);
/* 1535 */          b.putLong(VineService.EXTRA_FOLLOW_ID, userIdToFollow);
/* 1536 */          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 1537 */          return executeServiceAction(18, b);
                }

/* 1540 */      public String bulkFollowUsers(Session session, ArrayList<String> userIdsToFollow) {
/* 1541 */          Bundle bundle = injectServiceBundle(new Bundle(), session);
/* 1542 */          bundle.putStringArrayList(VineService.EXTRA_FOLLOW_IDS, userIdsToFollow);
/* 1543 */          return executeServiceAction(107, bundle);
                }

/* 1546 */      public String unfollowUser(Session session, long userIdToUnfollow, boolean notify) {
/* 1547 */          return unfollowUser(session, userIdToUnfollow, notify, -1L);
                }

/* 1551 */      public String unfollowUser(Session session, long userIdToUnfollow, boolean notify, long currentProfileId) {
/* 1552 */          Bundle b = createServiceBundle(session);
/* 1553 */          b.putLong(VineService.EXTRA_FOLLOW_ID, userIdToUnfollow);
/* 1554 */          b.putLong(VineService.EXTRA_PROFILE_ID, currentProfileId);
/* 1555 */          b.putLong("user_id", getActiveId());
/* 1556 */          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 1557 */          return executeServiceAction(19, b);
                }

/* 1561 */      public String likePost(String source, Session session, long postToLike, long repostId, long meUserId, String meUsername, boolean notify) {
/* 1562 */          Bundle b = createServiceBundle(session);
/* 1563 */          b.putLong("post_id", postToLike);
/* 1564 */          b.putLong("repost_id", repostId);
/* 1565 */          b.putLong("user_id", meUserId);
/* 1566 */          b.putString("username", meUsername);
/* 1567 */          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 1568 */          FlurryUtils.trackLikePost(postToLike, source);
/* 1569 */          return executeServiceAction(24, b);
                }

/* 1572 */      public String unlikePost(String source, Session session, long postToUnlike, long repostId, boolean notify) {
/* 1573 */          Bundle b = createServiceBundle(session);
/* 1574 */          b.putLong("post_id", postToUnlike);
/* 1575 */          b.putLong("repost_id", repostId);
/* 1576 */          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 1577 */          FlurryUtils.trackUnLikePost(source);
/* 1578 */          return executeServiceAction(25, b);
                }

/* 1581 */      public String revine(String source, Session session, long postToRevine, long repostId, String meUsername) {
/* 1582 */          Bundle b = createServiceBundle(session);
/* 1583 */          b.putLong("post_id", postToRevine);
/* 1584 */          b.putLong("repost_id", repostId);
/* 1585 */          b.putString("username", meUsername);
/* 1586 */          FlurryUtils.trackRevine(postToRevine, source);
/* 1587 */          return executeServiceAction(54, b);
                }

/* 1591 */      public String unRevine(String source, Session session, long postToUnrevine, long myRepostId, long repostId, boolean following, long userId, boolean notify) {
/* 1592 */          Bundle b = createServiceBundle(session);
/* 1593 */          b.putLong("post_id", postToUnrevine);
/* 1594 */          b.putLong("my_repost_id", myRepostId);
/* 1595 */          b.putLong("repost_id", repostId);
/* 1596 */          b.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 1597 */          b.putBoolean("following", following);
/* 1598 */          FlurryUtils.trackUnRevine(source);
/* 1600 */          return executeServiceAction(55, b);
                }

/* 1603 */      public String removeFollowPosts(Session session, long userId) {
/* 1604 */          Bundle b = createServiceBundle(session);
/* 1605 */          b.putLong("user_id", userId);
/* 1606 */          return executeServiceAction(34, b);
                }

/* 1609 */      public String fetchServerStatus() {
/* 1610 */          Bundle b = createServiceBundle();
/* 1611 */          return executeServiceAction(38, b);
                }

/* 1614 */      public Session getActiveSession() {
/* 1615 */          return getActiveSession(false);
                }

/* 1618 */      public Session getActiveSessionReadOnly() {
/* 1619 */          return getActiveSession(true);
                }

/* 1622 */      public long getActiveId() {
/* 1623 */          return getActiveSessionReadOnly().getUserId();
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

/* 1632 */          public boolean isPendingRequest(String reqId) {
/* 1633 */          return this.mServiceConnection.isPending(reqId);
                }

/* 1637 */      public Session getSession(String accountName) {
/* 1638 */          return VineAccountHelper.getSession(this.mContext, accountName);
                }

/* 1641 */      private Session getActiveSession(boolean readOnly) {
/* 1642 */          return VineAccountHelper.getActiveSession(this.mContext, readOnly);
                }

/* 1651 */      public String login(Session session, VineLogin login, String password, boolean reactivate) {
/* 1652 */          session.setLoginStatus(Session.LoginStatus.LOGGING_IN);
/* 1653 */          Bundle b = createServiceBundle(session);
/* 1654 */          b.putParcelable(VineService.EXTRA_LOGIN, login);
/* 1655 */          b.putString(VineService.EXTRA_PASSWORD, password);
/* 1656 */          b.putBoolean("reactivate", reactivate);
/* 1657 */          return executeServiceAction(2, b);
                }

/* 1666 */      public String logout(Session session) {
/* 1667 */          if (session.getLoginStatus() != Session.LoginStatus.LOGGED_IN && session.getLoginStatus() != Session.LoginStatus.LOGGING_IN) {
                        return null;
                    }
/* 1669 */          session.setLoginStatus(Session.LoginStatus.LOGGING_OUT);
/* 1670 */          return executeServiceAction(3, createServiceBundle(session));
                }

/* 1681 */      public String logout() {
/* 1682 */          return logout(getActiveSession());
                }

/* 1687 */      public String signUp(String login, String password, String username, String phoneNumber, Uri profileUri, TwitterUser twitterUser, VineLogin vl) {
/* 1688 */          Bundle b = createServiceBundle();
/* 1689 */          b.putString("email", login);
/* 1690 */          b.putString(VineService.EXTRA_PASSWORD, password);
/* 1691 */          b.putString(VineService.EXTRA_ACCOUNT_NAME, username);
/* 1692 */          b.putString(VineService.EXTRA_PHONE, phoneNumber);
/* 1693 */          b.putParcelable("uri", profileUri);
/* 1694 */          b.putParcelable(VineService.EXTRA_TWITTER_USER, twitterUser);
/* 1695 */          if (vl != null) {
/* 1696 */              b.putString(VineService.EXTRA_KEY, vl.twitterToken);
/* 1697 */              b.putString("secret", vl.twitterSecret);
                    }
/* 1699 */          return executeServiceAction(4, b);
                }

/* 1702 */      public void followVineOnTwitter() {
/* 1703 */          executeServiceRequest(123, new Bundle());
                }

/* 1708 */      public String getTwitterAuth(String callback, String key, String secret) {
/* 1709 */          Bundle b = createServiceBundle();
/* 1710 */          b.putString("uri", callback);
/* 1711 */          b.putString(VineService.EXTRA_KEY, key);
/* 1712 */          b.putString("secret", secret);
/* 1713 */          return executeServiceAction(5, b);
                }

/* 1716 */      public String resetPassword(String email) {
/* 1717 */          Bundle b = createServiceBundle();
/* 1718 */          b.putString("email", email);
/* 1719 */          return executeServiceAction(9, b);
                }

/* 1722 */      public String fetchTwitterUser(VineLogin mLogin) {
/* 1723 */          Bundle b = createServiceBundle();
/* 1724 */          b.putParcelable(VineService.EXTRA_LOGIN, mLogin);
/* 1725 */          return executeServiceAction(7, b);
                }

/* 1728 */      public String loginWithxAuth(String username, String password) {
/* 1729 */          Bundle b = createServiceBundle();
/* 1730 */          b.putString("username", username);
/* 1731 */          b.putString(VineService.EXTRA_PASSWORD, password);
/* 1732 */          return executeServiceAction(8, b);
                }

/* 1736 */      public String fetchNewPosts(Session session, int size, long userId, int type, int page, long anchor, boolean userInitiated, String tag, String sort, Uri data) {
/* 1737 */          return fetchPosts(session, size, userId, type, page, anchor, userInitiated, tag, sort, data, UrlCachePolicy.FORCE_REFRESH);
                }

/* 1741 */      public String fetchPosts(Session session, int size, long userId, int type, int page, long anchor, boolean userInitiated, String tag, String sort, Uri data, UrlCachePolicy cachePolicy) {
/* 1742 */          Bundle b = createServiceBundle(session);
/* 1743 */          b.putInt(VineService.EXTRA_SIZE, size);
/* 1744 */          b.putInt("type", type);
/* 1745 */          b.putInt("page", page);
/* 1746 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1747 */          b.putLong(VineService.EXTRA_PROFILE_ID, userId);
/* 1748 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
/* 1749 */          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
/* 1750 */          if (tag != null) {
                        b.putString("tag_name", tag);
                    }
/* 1751 */          if (sort != null) {
                        b.putString("sort", sort);
                    }
/* 1752 */          b.putParcelable("data", data);
/* 1753 */          return executeServiceAction(13, b);
                }

/* 1756 */      public String fetchPost(Session session, long postId, UrlCachePolicy cachePolicy) {
/* 1757 */          Bundle b = createServiceBundle(session);
/* 1758 */          b.putLong("post_id", postId);
/* 1759 */          b.putParcelable(VineService.EXTRA_CACHE_POLICY, cachePolicy);
/* 1760 */          return executeServiceAction(28, b);
                }

/* 1763 */      public String fetchPostId(Session session, String shareId) {
/* 1764 */          Bundle b = createServiceBundle(session);
/* 1765 */          b.putString("post_share_id", shareId);
/* 1766 */          return executeServiceAction(36, b);
                }

/* 1769 */      public String fetchComments(long postId, int page, long anchor, int size) {
/* 1770 */          Bundle b = createServiceBundle();
/* 1771 */          b.putLong("post_id", postId);
/* 1772 */          b.putInt("page", page);
/* 1773 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1774 */          b.putInt(VineService.EXTRA_SIZE, size);
/* 1775 */          return executeServiceAction(12, b);
                }

/* 1778 */      public String updateClientProfile() {
/* 1779 */          SLog.m446d("Update Client Profile.");
/* 1780 */          return executeServiceAction(35, new Bundle());
                }

/* 1784 */      public String postComment(long postId, long repostId, Session session, String comment, ArrayList<VineEntity> entities) {
/* 1785 */          Bundle b = createServiceBundle();
/* 1786 */          b.putLong("post_id", postId);
/* 1787 */          b.putLong("repost_id", repostId);
/* 1788 */          b.putLong("user_id", session.getUserId());
/* 1789 */          b.putString("username", session.getName());
/* 1790 */          b.putString("comment", Util.cleanse(comment));
/* 1791 */          b.putString("avatar_url", session.getAvatarUrl());
/* 1792 */          b.putParcelableArrayList("entities", entities);
/* 1793 */          return executeServiceAction(14, b);
                }

/* 1796 */      public String deleteComment(long postId, long repostId, String commentId) {
/* 1797 */          Bundle b = createServiceBundle();
/* 1798 */          b.putLong("post_id", postId);
/* 1799 */          b.putLong("repost_id", repostId);
/* 1800 */          b.putString("comment_id", commentId);
/* 1801 */          return executeServiceAction(30, b);
                }

/* 1804 */      public String spamComment(String commentId, String postId, String event) {
/* 1805 */          Bundle b = createServiceBundle();
/* 1806 */          b.putString("comment_id", commentId);
/* 1807 */          b.putString("post_id", postId);
/* 1808 */          if (!TextUtils.isEmpty(event)) {
/* 1809 */              b.putString("event", event);
                    }
/* 1811 */          return executeServiceAction(126, b);
                }

/* 1814 */      public String spamComment(String commentId, String postId) {
/* 1815 */          return spamComment(commentId, postId, null);
                }

/* 1818 */      public String reportPost(Session session, long postId, String event) {
/* 1819 */          Bundle b = createServiceBundle(session);
/* 1820 */          b.putLong("post_id", postId);
/* 1821 */          if (!TextUtils.isEmpty(event)) {
/* 1822 */              b.putString("event", event);
                    }
/* 1824 */          return executeServiceAction(27, b);
                }

/* 1827 */      public String reportPost(Session session, long postId) {
/* 1828 */          return reportPost(session, postId, null);
                }

/* 1831 */      public String deletePost(Session session, long postId) {
/* 1832 */          Bundle b = createServiceBundle(session);
/* 1833 */          b.putLong("post_id", postId);
/* 1834 */          return executeServiceAction(31, b);
                }

/* 1837 */      public String sendFacebookToken() {
/* 1838 */          Bundle b = createServiceBundle();
/* 1839 */          b.putString(VineService.EXTRA_FACEBOOK_TOKEN, getPendingFacebookToken());
/* 1840 */          return executeServiceAction(37, b);
                }

/* 1843 */      public String sendGcmRegId(String regId, long userId) {
/* 1844 */          Bundle b = createServiceBundle();
/* 1845 */          b.putString(VineService.EXTRA_GCM_REG_ID, regId);
/* 1846 */          b.putLong("user_id", userId);
/* 1847 */          return executeServiceAction(60, b);
                }

/* 1850 */      public String clearGcmRegId(String regId, long userId, String key) {
/* 1851 */          Bundle b = createServiceBundle();
/* 1852 */          b.putString(VineService.EXTRA_GCM_REG_ID, regId);
/* 1853 */          b.putLong("user_id", userId);
/* 1854 */          b.putString("s_key", key);
/* 1855 */          return executeServiceAction(61, b);
                }

/* 1858 */      public void mergePushNotification(VineSingleNotification notification) {
/* 1859 */          Bundle b = new Bundle();
/* 1860 */          b.putParcelable("notification", notification);
/* 1861 */          executeServiceAction(97, b);
                }

/* 1864 */      public void clearPushNotifications(int notificationDisplayId) {
/* 1865 */          removeNotification(notificationDisplayId, -1L);
                }

/* 1868 */      public String getPendingFacebookToken() {
/* 1869 */          return Util.getDefaultSharedPrefs(this.mContext).getString(KEY_FACEBOOK_TOKEN, null);
                }

/* 1872 */      public static void setPendingFacebookToken(Context context, String token) {
/* 1873 */          Util.getDefaultSharedPrefs(context).edit().putString(KEY_FACEBOOK_TOKEN, token).commit();
                }

/* 1876 */      public void clearFacebookToken() {
/* 1877 */          com.facebook.Session fbSession = com.facebook.Session.getActiveSession();
/* 1878 */          if (fbSession == null) {
/* 1879 */              fbSession = com.facebook.Session.openActiveSessionFromCache(this.mContext);
                    }
/* 1881 */          if (fbSession != null) {
/* 1882 */              fbSession.closeAndClearTokenInformation();
                    }
                }

/* 1886 */      public String deleteSession(String key) {
/* 1887 */          Bundle b = createServiceBundle();
/* 1888 */          b.putString("s_key", key);
/* 1889 */          return executeServiceAction(64, b);
                }

/* 1894 */      public static void startTwitterAuthWithFinish(Twitter twitter, Activity activity) {
                    try {
/* 1895 */              if (!twitter.startAuthActivityForResult(activity, 1)) {
/* 1897 */                  activity.startActivityForResult(LoginTwitterActivity.getIntentWithFinish(activity), 2);
                        }
                    } catch (SecurityException e) {
/* 1901 */              activity.startActivityForResult(LoginTwitterActivity.getIntentWithFinish(activity), 2);
                    }
                }

/* 1911 */      public void followEditorsPicks(boolean shouldFollow) {
/* 1912 */          Session session = getActiveSession();
/* 1913 */          Bundle bundle = createServiceBundle(session);
/* 1914 */          int follow = shouldFollow ? 1 : 0;
/* 1915 */          bundle.putInt(VineService.EXTRA_SHOULD_FOLLOW_EDITORS_PICKS, follow);
/* 1916 */          executeServiceAction(44, bundle);
                }

/* 1928 */      public String loginCheckTwitter(String username, String token, String secret, long twitterUserId, boolean reactivate) {
/* 1929 */          Bundle b = createServiceBundle();
/* 1930 */          b.putString("username", username);
/* 1931 */          b.putString(VineService.EXTRA_KEY, token);
/* 1932 */          b.putString("secret", secret);
/* 1933 */          b.putLong(VineService.EXTRA_TWITTER_ID, twitterUserId);
/* 1934 */          b.putBoolean("reactivate", reactivate);
/* 1935 */          return executeServiceAction(6, b);
                }

/* 1938 */      public String fetchTwitterFriends(Session session, String key, String secret) {
/* 1939 */          Bundle b = createServiceBundle(session);
/* 1940 */          b.putString(VineService.EXTRA_KEY, key);
/* 1941 */          b.putString("secret", secret);
/* 1942 */          return executeServiceAction(15, b);
                }

/* 1945 */      public String fetchAddressFriends(Session session) {
/* 1946 */          Bundle b = createServiceBundle(session);
/* 1947 */          return executeServiceAction(16, b);
                }

/* 1950 */      public String sendAddressBook() {
/* 1951 */          Bundle b = createServiceBundle();
/* 1952 */          return executeServiceAction(101, b);
                }

/* 1955 */      public String removeUsers(Session session, int type) {
/* 1956 */          Bundle b = createServiceBundle(session);
/* 1957 */          b.putInt("type", type);
/* 1958 */          return executeServiceAction(17, b);
                }

/* 1962 */      public String connectTwitter(Session session, String username, String token, String secret, long userId) {
/* 1963 */          Bundle b = createServiceBundle(session);
/* 1964 */          b.putString("username", username);
/* 1965 */          b.putString(VineService.EXTRA_KEY, token);
/* 1966 */          b.putString("secret", secret);
/* 1967 */          b.putLong("user_id", userId);
/* 1968 */          return executeServiceAction(32, b);
                }

/* 1973 */      public String saveLoadedPosts(ArrayList<VinePost> posts, int groupType, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated) {
/* 1974 */          Bundle b = createServiceBundle();
/* 1975 */          b.putParcelableArrayList("posts", posts);
/* 1976 */          b.putInt("type", groupType);
/* 1977 */          b.putString("tag_name", tag);
/* 1978 */          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
/* 1979 */          b.putInt("next_page", next);
/* 1980 */          b.putInt("previous_page", previous);
/* 1981 */          b.putLong(VineService.EXTRA_ANCHOR, anchor);
/* 1982 */          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
/* 1983 */          return executeServiceAction(43, b);
                }

/* 1986 */      public String disconnectTwitter(Session session) {
/* 1987 */          Bundle b = createServiceBundle(session);
/* 1988 */          return executeServiceAction(33, b);
                }

/* 1991 */      public String acceptFollowRequest(Session session, long userId) {
/* 1992 */          Bundle b = createServiceBundle(session);
/* 1993 */          b.putLong("user_id", userId);
/* 1994 */          b.putBoolean(VineService.EXTRA_ACCEPT, true);
/* 1995 */          return executeServiceAction(50, b);
                }

/* 1999 */      public String acceptRejectRequest(Session session, long userId) {
/* 2000 */          Bundle b = createServiceBundle(session);
/* 2001 */          b.putLong("user_id", userId);
/* 2002 */          b.putBoolean(VineService.EXTRA_ACCEPT, false);
/* 2003 */          return executeServiceAction(50, b);
                }

/* 2006 */      public String updatePrivate(Session session, boolean priv) {
/* 2007 */          Bundle b = createServiceBundle(session);
/* 2008 */          b.putBoolean(VineService.EXTRA_PRIVATE, priv);
/* 2009 */          return executeServiceAction(52, b);
                }

/* 2012 */      public String updateExplicit(Session session, boolean explicit) {
/* 2013 */          Bundle b = createServiceBundle(session);
/* 2014 */          b.putBoolean("explicit", explicit);
/* 2015 */          return executeServiceAction(51, b);
                }

/* 2018 */      public String updateEdition(Session session, String editionCode) {
/* 2019 */          Bundle b = createServiceBundle(session);
/* 2020 */          b.putString("edition", editionCode);
/* 2021 */          SLog.m447d("Updating edition code to {}", editionCode);
/* 2022 */          return executeServiceAction(71, b);
                }

/* 2028 */      public void determineServerStatus() {
/* 2029 */          SharedPreferences prefs = Util.getDefaultSharedPrefs(this.mContext);
/* 2030 */          long lastRefresh = prefs.getLong(Settings.PREF_LAST_SERVER_STATUS_REFRESH, 0L);
/* 2031 */          long elapsedTime = System.currentTimeMillis() - lastRefresh;
/* 2032 */          long remainingTime = System.currentTimeMillis() - elapsedTime;
                    if (lastRefresh <= 0 || elapsedTime > CommonUtil.HOUR_MILLIS) {
/* 2034 */              fetchServerStatus();
/* 2035 */              prefs.edit().putLong(Settings.PREF_LAST_SERVER_STATUS_REFRESH, System.currentTimeMillis()).commit();
/* 2037 */              if (this.mHandler != null) {
/* 2038 */                  this.mHandler.removeCallbacks(this.mServerStatusRunnable);
/* 2039 */                  this.mHandler.postDelayed(this.mServerStatusRunnable, CommonUtil.HOUR_MILLIS);
/* 2048 */                  return;
                        }
/* 2048 */              return;
                    }
/* 2042 */          if (this.mHandler != null) {
/* 2043 */              this.mHandler.removeCallbacks(this.mServerStatusRunnable);
/* 2044 */              Handler handler = this.mHandler;
                        ServerStatusRunnable serverStatusRunnable = this.mServerStatusRunnable;
                        if (remainingTime <= 0) {
                            remainingTime = 3600000;
                        }
                        handler.postDelayed(serverStatusRunnable, remainingTime);
                    }
                }

/* 2050 */      public String sendLoopCounts() {
/* 2051 */          SLog.m446d("Sending loops....");
/* 2052 */          LoopManager loopManager = LoopManager.get(this.mContext);
/* 2054 */          ArrayList<LoopManager.Record> pendingLoops = new ArrayList<>(loopManager.popPendingLoops());
/* 2055 */          if (pendingLoops.isEmpty()) {
/* 2056 */              return "";
                    }
/* 2058 */          Bundle b = new Bundle();
/* 2059 */          b.putParcelableArrayList("loops", pendingLoops);
/* 2060 */          return executeServiceAction(66, b);
                }

/* 2063 */      public void mergeSinglePost(Bundle bundle) {
/* 2064 */          if (isLoggedInReadOnly()) {
/* 2065 */              executeServiceAction(57, injectServiceBundle(bundle, getActiveSession()));
                    }
                }

/* 2069 */      public void shareAsVM(ArrayList<VineRecipient> vineRecipients, long postId) {
/* 2070 */          Bundle b = createServiceBundle();
/* 2071 */          b.putParcelableArrayList("recipients", vineRecipients);
/* 2072 */          b.putLong("post_id", postId);
/* 2073 */          executeServiceAction(96, b);
                }

/* 2076 */      public void sharePost(String network, String message, long postId) {
/* 2077 */          Bundle b = createServiceBundle();
/* 2078 */          b.putString("network", network);
/* 2079 */          b.putString("message", message);
/* 2080 */          b.putLong("post_id", postId);
/* 2081 */          executeServiceAction(63, b);
                }

/* 2088 */      public void markMessageLoopViewed(VinePrivateMessage vpm) {
/* 2089 */          Bundle b = createServiceBundle();
/* 2090 */          b.putParcelable("message", vpm);
/* 2091 */          executeServiceAction(84, b);
                }

/* 2094 */      public void getEditions() {
/* 2095 */          Bundle b = createServiceBundle();
/* 2096 */          executeServiceAction(70, b);
                }

/* 2099 */      public void onLowMemory() {
                    for (AppSessionListener listener : this.mListeners) {
/* 2101 */              listener.onLowMemory();
                    }
                }

/* 2105 */      public void onTrimMemory(int level) {
                    for (AppSessionListener listener : this.mListeners) {
/* 2107 */              listener.onTrimMemory(level);
                    }
                }

/* 2111 */      public void removeFromVideoCache(VideoKey videoKey) {
/* 2112 */          this.mVideoCache.remove(videoKey);
                }

/* 2115 */      public void sendViewedVines() {
/* 2116 */          executeServiceAction(85, createServiceBundle());
                }

/* 2119 */      public void fetchConversationRowIdFromUserRemoteId(long userId, int networkType) {
/* 2120 */          Bundle b = createServiceBundle();
/* 2121 */          b.putInt("network", networkType);
/* 2122 */          b.putParcelable(VineService.EXTRA_RECIPIENT, VineRecipient.fromUser(userId));
/* 2123 */          b.putLong(VineService.EXTRA_RECIPIENT_ID, userId);
/* 2124 */          executeServiceAction(91, b);
                }

/* 2127 */      public void fetchConversationRowIdFromSingleRecipient(VineRecipient recipient, int networkType) {
/* 2128 */          Bundle b = createServiceBundle();
/* 2129 */          b.putInt("network", networkType);
/* 2130 */          b.putParcelable(VineService.EXTRA_RECIPIENT, recipient);
/* 2131 */          b.putLong(VineService.EXTRA_RECIPIENT_ID, recipient.contactId);
/* 2132 */          executeServiceAction(91, b);
                }

/* 2136 */      public void deleteMessage(long id) {
/* 2137 */          Bundle b = createServiceBundle();
/* 2138 */          b.putLong("message_id", id);
/* 2139 */          executeServiceAction(95, b);
                }

/* 2142 */      public void removeNotification(int notificationId, long conversationRowId) {
/* 2143 */          Bundle b = createServiceBundle();
/* 2144 */          b.putInt("notification_id", notificationId);
/* 2145 */          b.putLong("conversation_row_id", conversationRowId);
/* 2146 */          executeServiceAction(102, b);
                }

/* 2149 */      public void retryMessagesInConversationRowId(long conversationRowId) {
/* 2150 */          Bundle b = createServiceBundle();
/* 2151 */          b.putLong("conversation_row_id", conversationRowId);
/* 2152 */          executeServiceAction(103, b);
                }

/* 2155 */      public void clearInboxPageCursors() {
/* 2156 */          Bundle b = createServiceBundle();
/* 2157 */          executeServiceAction(104, b);
                }

/* 2160 */      public void clearImageCacheFromMemory() {
/* 2161 */          this.mPhotoImagesCache.clearMemory();
                }

/* 2164 */      public void executeServiceRequest(int actionCode, Bundle b) {
/* 2165 */          executeServiceAction(actionCode, b);
                }

/* 2168 */      public String saveNotificationSettings(HashMap<String, String> map) {
/* 2169 */          Bundle b = createServiceBundle();
/* 2170 */          b.putSerializable(VineService.EXTRA_NOTIFICATION_SETTINGS, map);
/* 2171 */          return executeServiceAction(114, b);
                }

/* 2174 */      public PhotoImagesCache getImageCache() {
/* 2175 */          return this.mPhotoImagesCache;
                }

/* 2178 */      public boolean hasPendingCacheRequests() {
                    return this.mPhotoImagesCache.hasPendingItems() || this.mVideoCache.hasPendingItems();
                }

/* 2181 */      class ServerStatusRunnable implements Runnable {
/* 2182 */          ServerStatusRunnable() {
                    }

                    @Override // java.lang.Runnable
/* 2185 */          public void run() {
/* 2186 */              AppController.this.determineServerStatus();
                    }
                }

/* 2195 */      public void determineCleanup(Session session) {
/* 2196 */          SharedPreferences prefs = Util.getSharedPrefs(this.mContext, Settings.PREF_LAST_CLEANUP);
/* 2197 */          long lastCleanupTime = prefs.getLong(Settings.PREF_LAST_CLEANUP, 0L);
                    if (lastCleanupTime > 0) {
                        if (System.currentTimeMillis() - lastCleanupTime > CommonUtil.HOUR_MILLIS) {
/* 2200 */                  performCleanup(session, lastCleanupTime);
                        }
                    } else {
/* 2205 */              SharedPreferences.Editor editor = prefs.edit();
/* 2206 */              long cleanupTime = System.currentTimeMillis();
/* 2207 */              editor.putLong(Settings.PREF_LAST_CLEANUP, cleanupTime);
/* 2208 */              editor.commit();
                    }
                }

/* 2220 */      private String performCleanup(Session session, long timeAnchor) {
/* 2221 */          Bundle b = createServiceBundle(session);
/* 2222 */          b.putLong(VineService.EXTRA_ANCHOR, timeAnchor);
/* 2223 */          return executeServiceAction(29, b);
                }

/* 2232 */      public Session.LoginStatus getLoginStatus() {
/* 2233 */          return getActiveSession().getLoginStatus();
                }

/* 2242 */      public boolean isLoggedIn() {
/* 2243 */          return VineAccountHelper.isLoggedIn(this.mContext, false);
                }

/* 2246 */      public boolean isLoggedInReadOnly() {
/* 2247 */          return VineAccountHelper.isLoggedIn(this.mContext, true);
                }

/* 2250 */      public void addListener(AppSessionListener listener) {
/* 2251 */          listener.setEnabled(true);
/* 2252 */          if (!this.mListeners.contains(listener)) {
/* 2255 */              this.mListeners.add(listener);
                    }
                }

/* 2258 */      public void removeListener(AppSessionListener listener) {
/* 2259 */          listener.setEnabled(false);
/* 2260 */          this.mListeners.remove(listener);
                }

/* 2263 */      public void refreshSessionKey(String newSessionKey) {
/* 2264 */          Session activeSession = VineAccountHelper.getCachedActiveSession();
/* 2265 */          if (activeSession != null) {
/* 2266 */              activeSession.setSessionKey(newSessionKey);
                    }
                }

/* 2273 */      public static void clearFileCache(Context context) {
/* 2274 */          Util.removeCache(context);
/* 2275 */          ImageUtils.removeFiles(context);
                }

/* 2281 */      public void clearFileCache() {
/* 2282 */          clearFileCache(this.mContext);
                }

/* 2289 */      public String clearDbCache(boolean notify) {
/* 2290 */          Bundle bundle = createServiceBundle();
/* 2291 */          bundle.putBoolean(VineService.EXTRA_NOTIFY, notify);
/* 2292 */          return executeServiceAction(58, bundle);
                }

/* 2303 */      public String clearDbAll() {
/* 2304 */          Bundle bundle = createServiceBundle();
/* 2305 */          return executeServiceAction(59, bundle);
                }

/* 2308 */      public String expireTimeline(boolean fetchPosts, int type, String tag) {
/* 2309 */          Bundle bundle = createServiceBundle();
/* 2310 */          bundle.putBoolean(VineService.EXTRA_ALSO_FETCH_POSTS, fetchPosts);
/* 2311 */          bundle.putInt("type", type);
/* 2312 */          bundle.putString("tag_name", tag);
/* 2313 */          return executeServiceAction(45, bundle);
                }

/* 2316 */      public String deactivateAccount() {
/* 2317 */          Bundle bundle = createServiceBundle();
/* 2318 */          return executeServiceAction(67, bundle);
                }

/* 2321 */      public String fetchChannels(int pageType) {
/* 2322 */          Bundle b = createServiceBundle();
/* 2323 */          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
/* 2324 */          return executeServiceAction(53, b);
                }

/* 2333 */      public void updateCredentials(String key) {
/* 2334 */          getActiveSession().setSessionKey(key);
                }

/* 2337 */      private String executeServiceAction(int actionCode, Bundle bundle) {
/* 2338 */          CrashUtil.set("Last Service Action Code", actionCode);
/* 2339 */          return this.mServiceConnection.queueAndExecute(actionCode, bundle);
                }

/* 2342 */      private Bundle createServiceBundle() {
/* 2343 */          return createServiceBundle(getActiveSession());
                }

/* 2346 */      private Bundle createServiceBundleReadOnly() {
/* 2347 */          return createServiceBundle(getActiveSessionReadOnly());
                }

/* 2350 */      public static Bundle injectServiceBundle(Bundle bundle, Session session) {
/* 2351 */          bundle.putString("s_key", session.getSessionKey());
/* 2352 */          bundle.putLong(VineService.EXTRA_SESSION_OWNER_ID, session.getUserId());
/* 2353 */          bundle.putString(VineService.EXTRA_ACCOUNT_NAME, session.getName());
/* 2354 */          bundle.putString("email", session.getLoginEmail());
/* 2355 */          return bundle;
                }

/* 2358 */      private Bundle createServiceBundle(Session session) {
/* 2359 */          Bundle bundle = new Bundle();
/* 2360 */          return injectServiceBundle(bundle, session);
                }

                @Override // co.vine.android.cache.image.PhotoImagesListener
/* 2364 */      public void onPhotoImageLoaded(PhotoImagesCache cache, HashMap<ImageKey, UrlImage> image) {
/* 2365 */          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
/* 2367 */              AppSessionListener listener = listeners.get(i);
/* 2368 */              if (listener.isEnabled()) {
/* 2369 */                  listener.onPhotoImageLoaded(image);
                        }
                    }
                }

                @Override // co.vine.android.cache.image.PhotoImagesListener
/* 2375 */      public void onPhotoImageError(PhotoImagesCache cache, ImageKey key, HttpResult result) {
/* 2376 */          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
/* 2378 */              AppSessionListener listener = listeners.get(i);
/* 2379 */              if (listener.isEnabled()) {
/* 2380 */                  listener.onPhotoImageError(key, result);
                        }
                    }
                }

                @Override // co.vine.android.cache.video.VideoListener
/* 2386 */      public void onVideoPathObtained(VideoCache cache, HashMap<VideoKey, UrlVideo> videos) {
/* 2387 */          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
/* 2389 */              AppSessionListener listener = listeners.get(i);
/* 2390 */              if (listener.isEnabled()) {
/* 2391 */                  listener.onVideoPathObtained(videos);
                        }
                    }
                }

                @Override // co.vine.android.cache.video.VideoListener
/* 2397 */      public void onVideoPathError(VideoCache cache, VideoKey key, HttpResult result) {
/* 2398 */          ArrayList<AppSessionListener> listeners = new ArrayList<>(this.mListeners);
                    for (int i = listeners.size() - 1; i >= 0; i--) {
/* 2400 */              AppSessionListener listener = listeners.get(i);
/* 2401 */              if (listener.isEnabled()) {
/* 2402 */                  listener.onVideoPathError(key, result);
                        }
                    }
                }

/* 2407 */      public long getTypeaheadThrottle() {
/* 2408 */          return 300L;
                }

/* 2411 */      public int getTypeaheadMaxCompose() {
/* 2412 */          return 50;
                }

/* 2415 */      public int getTypeaheadFetchThreshold(String type) {
                    return "tag".equals(type) ? 3 : 2;
                }

/* 2423 */      public String fetchUserTypeahead(String query) {
/* 2424 */          Bundle bundle = createServiceBundle();
/* 2425 */          bundle.putString(VineService.EXTRA_QUERY, query);
/* 2426 */          return executeServiceAction(39, bundle);
                }

/* 2429 */      public String fetchTagTypeahead(String query) {
/* 2430 */          Bundle bundle = createServiceBundle();
/* 2431 */          bundle.putString(VineService.EXTRA_QUERY, query);
/* 2432 */          return executeServiceAction(40, bundle);
                }

/* 2435 */      public String searchUsers(String query, int page) {
/* 2436 */          Bundle b = createServiceBundle();
/* 2437 */          b.putString(VineService.EXTRA_QUERY, query);
/* 2438 */          b.putInt("page", page);
/* 2439 */          return executeServiceAction(41, b);
                }

/* 2442 */      public String searchTags(String query, int page) {
/* 2443 */          Bundle b = createServiceBundle();
/* 2444 */          b.putString(VineService.EXTRA_QUERY, query);
/* 2445 */          b.putInt("page", page);
/* 2446 */          return executeServiceAction(42, b);
                }

/* 2449 */      public String blockUser(long userToBlock, String username) {
/* 2450 */          Bundle b = createServiceBundle();
/* 2451 */          b.putLong("user_id", getActiveId());
/* 2452 */          b.putLong(VineService.EXTRA_BLOCK_USER_ID, userToBlock);
/* 2453 */          if (!TextUtils.isEmpty(username)) {
/* 2454 */              b.putString("username", username);
                    }
/* 2456 */          return executeServiceAction(47, b);
                }

/* 2459 */      public String unblockUser(long userToUnblock, String username) {
/* 2460 */          Bundle b = createServiceBundle();
/* 2461 */          b.putLong("user_id", getActiveId());
/* 2462 */          b.putLong(VineService.EXTRA_BLOCK_USER_ID, userToUnblock);
/* 2463 */          if (!TextUtils.isEmpty(username)) {
/* 2464 */              b.putString("username", username);
                    }
/* 2466 */          return executeServiceAction(48, b);
                }

/* 2469 */      public String reportPerson(long userToReport, String event) {
/* 2470 */          Bundle b = createServiceBundle();
/* 2471 */          b.putLong("user_id", userToReport);
/* 2472 */          if (!TextUtils.isEmpty(event)) {
/* 2473 */              b.putString("event", event);
                    }
/* 2475 */          return executeServiceAction(49, b);
                }

/* 2478 */      public String reportPerson(long userToReport) {
/* 2479 */          return reportPerson(userToReport, null);
                }

/* 2482 */      public String enableReposts(long userId) {
/* 2483 */          Bundle b = createServiceBundle();
/* 2484 */          b.putLong("user_id", userId);
/* 2485 */          return executeServiceAction(68, b);
                }

/* 2488 */      public String disableReposts(long userId) {
/* 2489 */          Bundle b = createServiceBundle();
/* 2490 */          b.putLong("user_id", userId);
/* 2491 */          return executeServiceAction(69, b);
                }

/* 2494 */      public String fetchPendingNotificationCount(Session session) {
/* 2495 */          return executeServiceAction(65, createServiceBundle(session));
                }

/* 2499 */      public String fetchConversations(int pageType, boolean userInitiated, int networkType) {
/* 2500 */          Bundle b = createServiceBundleReadOnly();
/* 2501 */          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
/* 2502 */          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
/* 2503 */          b.putInt("network", networkType);
/* 2504 */          return executeServiceAction(74, b);
                }

/* 2508 */      public String fetchConversation(int pageType, boolean userInitiated, long conversationId, long conversationRowId, boolean prefetch) {
/* 2509 */          Bundle b = createServiceBundleReadOnly();
/* 2510 */          b.putInt(VineService.EXTRA_PAGE_TYPE, pageType);
/* 2511 */          b.putBoolean(VineService.EXTRA_USER_INITIATED, userInitiated);
/* 2512 */          b.putLong("conversation_id", conversationId);
/* 2513 */          b.putLong("conversation_row_id", conversationRowId);
/* 2514 */          b.putBoolean(VineService.EXTRA_PREFETCH, prefetch);
/* 2515 */          return executeServiceAction(76, b);
                }

/* 2518 */      public String getConversationRemoteId(long conversationRowId) {
/* 2519 */          Bundle b = new Bundle();
/* 2520 */          b.putLong("conversation_row_id", conversationRowId);
/* 2521 */          return executeServiceAction(77, b);
                }

/* 2524 */      public String ignoreConversation(long conversationRowId) {
/* 2525 */          Bundle b = createServiceBundle();
/* 2526 */          b.putLong("conversation_row_id", conversationRowId);
/* 2527 */          return executeServiceAction(79, b);
                }

/* 2530 */      public String deleteConversation(long conversationRowId) {
/* 2531 */          Bundle b = createServiceBundle();
/* 2532 */          b.putLong("conversation_row_id", conversationRowId);
/* 2533 */          return executeServiceAction(80, b);
                }

/* 2536 */      public String fetchUserId(ArrayList<String> vanityUrlSegments) {
/* 2537 */          Bundle b = createServiceBundle();
/* 2538 */          b.putStringArrayList("vanity_url", vanityUrlSegments);
/* 2539 */          return executeServiceAction(86, b);
                }

/* 2542 */      public String requestPhoneVerification(Session session, String phone, long id) {
/* 2543 */          Bundle b = createServiceBundle(session);
/* 2544 */          b.putString(VineService.EXTRA_PHONE, phone);
/* 2545 */          b.putLong("user_id", id);
/* 2546 */          return executeServiceAction(89, b);
                }

/* 2549 */      public String requestEmailVerification(Session session, String email, long id) {
/* 2550 */          Bundle b = createServiceBundle(session);
/* 2551 */          b.putString("email", email);
/* 2552 */          b.putLong("user_id", id);
/* 2553 */          return executeServiceAction(92, b);
                }

/* 2556 */      public String verifyPhoneNumber(Session session, String code) {
/* 2557 */          Bundle b = createServiceBundle(session);
/* 2558 */          b.putString(VineService.EXTRA_KEY, code);
/* 2559 */          return executeServiceAction(90, b);
                }

/* 2562 */      public String verifyEmail(Session session, String code) {
/* 2563 */          Bundle b = createServiceBundle(session);
/* 2564 */          b.putString(VineService.EXTRA_KEY, code);
/* 2565 */          return executeServiceAction(109, b);
                }

/* 2568 */      public String retryRequest(int actionCode, Bundle b) {
/* 2569 */          injectServiceBundle(b, getActiveSession());
/* 2570 */          b.remove("captcha_url");
/* 2571 */          return executeServiceAction(actionCode, b);
                }

/* 2574 */      public String generateReqIdForCanceledCaptcha() {
/* 2575 */          return this.mServiceConnection.generateRequestId();
                }

/* 2578 */      public void failRequest(String requestId, int actionCode, Bundle b) {
/* 2579 */          b.putString(VineService.EXTRA_REQUEST_ID, requestId);
/* 2580 */          notifyListeners(requestId, actionCode, STATUS_CODE_CAPTCHA_FAIL, null, b);
                }

/* 2583 */      public String setHideProfileReposts(long userId, boolean block) {
/* 2584 */          Bundle b = createServiceBundle();
/* 2585 */          b.putLong("user_id", userId);
/* 2586 */          b.putBoolean(VineService.EXTRA_HIDE_PROFILE_REPOSTS, block);
/* 2587 */          return executeServiceAction(87, b);
                }

/* 2590 */      public void clearUnreadMessageCount(long conversationRowId) {
/* 2591 */          Bundle b = new Bundle();
/* 2592 */          b.putLong("conversation_row_id", conversationRowId);
/* 2593 */          executeServiceAction(94, b);
/* 2594 */          fetchActivityCounts();
                }

/* 2597 */      public void fetchClientFlags(boolean abortRequestsOnHostChange) {
/* 2598 */          Bundle b = createServiceBundle();
/* 2599 */          b.putBoolean(VineService.EXTRA_ABORT_REQUESTS, abortRequestsOnHostChange);
/* 2600 */          executeServiceAction(111, createServiceBundle());
                }

/* 2603 */      public void abortAllRequests() {
/* 2604 */          this.mServiceConnection.cancelAll();
/* 2605 */          executeServiceAction(112, createServiceBundle());
                }

/* 2608 */      public void onMobileDataNetworkActivated() {
                    for (AppSessionListener listener : this.mListeners) {
/* 2610 */              listener.onMobileDataNetworkActivated();
                    }
                }

/* 2614 */      public void followChannel(long channelId, boolean following) {
/* 2615 */          Bundle b = createServiceBundle();
/* 2616 */          b.putLong("channelId", channelId);
/* 2617 */          b.putBoolean("following", following);
/* 2618 */          executeServiceAction(116, b);
                }

/* 2621 */      public void bulkFollowChannels(ArrayList<String> channelIds) {
/* 2622 */          Bundle b = createServiceBundle();
/* 2623 */          b.putStringArrayList("channels", channelIds);
/* 2624 */          executeServiceAction(117, b);
                }

/* 2627 */      public void markChannelLastUsed(long channelId) {
/* 2628 */          Bundle b = new Bundle();
/* 2629 */          b.putLong("channelId", channelId);
/* 2630 */          executeServiceAction(118, b);
                }

/* 2633 */      public void favoriteUser(long userId, boolean favorite, boolean isFollowing) {
/* 2634 */          Bundle b = createServiceBundle();
/* 2635 */          b.putLong("user_id", userId);
/* 2636 */          b.putBoolean(VineService.EXTRA_FAVORITE, favorite);
/* 2637 */          b.putBoolean("following", isFollowing);
/* 2638 */          executeServiceAction(119, b);
                }

/* 2641 */      public String fetchFavoriteUsers(int page, String anchor) {
/* 2642 */          Bundle b = createServiceBundle();
/* 2643 */          b.putInt("page", page);
/* 2644 */          b.putString(VineService.EXTRA_ANCHOR, anchor);
/* 2645 */          return executeServiceAction(120, b);
                }

/* 2648 */      public String fetchSuggestedFavoriteUsers() {
/* 2649 */          return executeServiceAction(121, createServiceBundle());
                }

/* 2653 */      public String fetchOnboardingSuggestedFavoriteUsers() {
/* 2654 */          return executeServiceAction(122, createServiceBundle());
                }

/* 2658 */      public String fetchComplaintsMenu() {
/* 2659 */          return executeServiceAction(130, createServiceBundle());
                }
            }
