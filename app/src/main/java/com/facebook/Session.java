            package com.facebook;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.content.pm.ResolveInfo;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            import android.support.v4.app.Fragment;
            import android.support.v4.content.LocalBroadcastManager;
            import android.util.Log;
            import co.vine.android.util.CommonUtil;
            import com.facebook.internal.SessionAuthorizationType;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import java.io.ByteArrayInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InvalidObjectException;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutputStream;
            import java.io.Serializable;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Date;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class Session implements Serializable {
                public static final String ACTION_ACTIVE_SESSION_CLOSED = "com.facebook.sdk.ACTIVE_SESSION_CLOSED";
                public static final String ACTION_ACTIVE_SESSION_OPENED = "com.facebook.sdk.ACTIVE_SESSION_OPENED";
                public static final String ACTION_ACTIVE_SESSION_SET = "com.facebook.sdk.ACTIVE_SESSION_SET";
                public static final String ACTION_ACTIVE_SESSION_UNSET = "com.facebook.sdk.ACTIVE_SESSION_UNSET";
                private static final String AUTH_BUNDLE_SAVE_KEY = "com.facebook.sdk.Session.authBundleKey";
                public static final int DEFAULT_AUTHORIZE_ACTIVITY_CODE = 64206;
                public static String DEFAULT_FACEBOOK_ID = null;
                private static final String MANAGE_PERMISSION_PREFIX = "manage";
                private static final String PUBLISH_PERMISSION_PREFIX = "publish";
                private static final String SESSION_BUNDLE_SAVE_KEY = "com.facebook.sdk.Session.saveSessionKey";
                private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
                private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
                public static final String WEB_VIEW_ERROR_CODE_KEY = "com.facebook.sdk.WebViewErrorCode";
                public static final String WEB_VIEW_FAILING_URL_KEY = "com.facebook.sdk.FailingUrl";
                private static Session activeSession = null;
                private static final long serialVersionUID = 1;
                private static volatile Context staticContext;
                private String applicationId;
                private volatile Bundle authorizationBundle;
                private AuthorizationClient authorizationClient;
                private AutoPublishAsyncTask autoPublishAsyncTask;
                private final List<StatusCallback> callbacks;
                private volatile TokenRefreshRequest currentTokenRefreshRequest;
                private Handler handler;
                private Date lastAttemptedTokenExtendDate;
                private final Object lock;
                private AuthorizationRequest pendingRequest;
                private SessionState state;
                private TokenCachingStrategy tokenCachingStrategy;
                private AccessToken tokenInfo;
                public static final String TAG = Session.class.getCanonicalName();
                private static final Object STATIC_LOCK = new Object();
                private static final Set<String> OTHER_PUBLISH_PERMISSIONS = new C07661();

                interface StartActivityDelegate {
                    Activity getActivityContext();

                    void startActivityForResult(Intent intent, int i);
                }

                public interface StatusCallback {
                    void call(Session session, SessionState sessionState, Exception exc);
                }

/* 49 */        /* synthetic */ Session(String x0, SessionState x1, AccessToken x2, Date x3, boolean x4, AuthorizationRequest x5, C07661 x6) {
/* 50 */            this(x0, x1, x2, x3, x4, x5);
                }

/* 49 */        static /* synthetic */ void access$200(Session x0, int x1, AuthorizationClient.Result x2) {
/* 50 */            x0.handleAuthorizationResult(x1, x2);
                }

/* 49 */        static /* synthetic */ List<StatusCallback> access$300(Session x0) {
/* 50 */            return x0.callbacks;
                }

/* 49 */        static /* synthetic */ Handler access$400(Session x0) {
/* 50 */            return x0.handler;
                }

/* 49 */        static /* synthetic */ void access$500(Handler x0, Runnable x1) {
/* 50 */            runWithHandlerOrExecutor(x0, x1);
                }

/* 49 */        static /* synthetic */ Context access$600() {
/* 50 */            return staticContext;
                }

/* 49 */        static /* synthetic */ TokenRefreshRequest access$700(Session x0) {
/* 50 */            return x0.currentTokenRefreshRequest;
                }

/* 49 */        static /* synthetic */ TokenRefreshRequest access$702(Session x0, TokenRefreshRequest x1) {
/* 50 */            x0.currentTokenRefreshRequest = x1;
                    return x1;
                }

/* 49 */        static /* synthetic */ AutoPublishAsyncTask access$902(Session x0, AutoPublishAsyncTask x1) {
/* 50 */            x0.autoPublishAsyncTask = x1;
                    return x1;
                }

                /* JADX INFO: renamed from: com.facebook.Session$1 */
/* 122 */       static class C07661 extends HashSet<String> {
/* 123 */           C07661() {
/* 125 */               add("ads_management");
/* 126 */               add("create_event");
/* 127 */               add("rsvp_event");
                    }
                }

/* 163 */       private static class SerializationProxyV1 implements Serializable {
                    private static final long serialVersionUID = 7663436173185080063L;
                    private final String applicationId;
                    private final Date lastAttemptedTokenExtendDate;
                    private final AuthorizationRequest pendingRequest;
                    private final boolean shouldAutoPublish;
                    private final SessionState state;
                    private final AccessToken tokenInfo;

/* 164 */           SerializationProxyV1(String applicationId, SessionState state, AccessToken tokenInfo, Date lastAttemptedTokenExtendDate, boolean shouldAutoPublish, AuthorizationRequest pendingRequest) {
/* 166 */               this.applicationId = applicationId;
/* 167 */               this.state = state;
/* 168 */               this.tokenInfo = tokenInfo;
/* 169 */               this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
/* 170 */               this.shouldAutoPublish = shouldAutoPublish;
/* 171 */               this.pendingRequest = pendingRequest;
                    }

/* 174 */           private Object readResolve() {
/* 175 */               return new Session(this.applicationId, this.state, this.tokenInfo, this.lastAttemptedTokenExtendDate, this.shouldAutoPublish, this.pendingRequest, null);
                    }
                }

/* 184 */       private Session(String applicationId, SessionState state, AccessToken tokenInfo, Date lastAttemptedTokenExtendDate, boolean shouldAutoPublish, AuthorizationRequest pendingRequest) {
/* 133 */           this.lastAttemptedTokenExtendDate = new Date(0L);
/* 144 */           this.lock = new Object();
/* 186 */           this.applicationId = applicationId;
/* 187 */           this.state = state;
/* 188 */           this.tokenInfo = tokenInfo;
/* 189 */           this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
/* 190 */           this.pendingRequest = pendingRequest;
/* 191 */           this.handler = new Handler(Looper.getMainLooper());
/* 192 */           this.currentTokenRefreshRequest = null;
/* 193 */           this.tokenCachingStrategy = null;
/* 194 */           this.callbacks = new ArrayList();
                }

/* 202 */       public Session(Context currentContext) {
/* 203 */           this(currentContext, null, null, true);
                }

/* 206 */       Session(Context context, String applicationId, TokenCachingStrategy tokenCachingStrategy) {
/* 207 */           this(context, applicationId, tokenCachingStrategy, true);
                }

/* 210 */       Session(Context context, String applicationId, TokenCachingStrategy tokenCachingStrategy, boolean loadTokenFromCache) {
/* 133 */           this.lastAttemptedTokenExtendDate = new Date(0L);
/* 144 */           this.lock = new Object();
/* 215 */           applicationId = applicationId == null ? DEFAULT_FACEBOOK_ID : applicationId;
/* 218 */           Validate.notNull(applicationId, "applicationId");
/* 220 */           initializeStaticContext(context);
/* 223 */           tokenCachingStrategy = tokenCachingStrategy == null ? new SharedPreferencesTokenCachingStrategy(staticContext) : tokenCachingStrategy;
/* 226 */           this.applicationId = applicationId;
/* 227 */           this.tokenCachingStrategy = tokenCachingStrategy;
/* 228 */           this.state = SessionState.CREATED;
/* 229 */           this.pendingRequest = null;
/* 230 */           this.callbacks = new ArrayList();
/* 231 */           this.handler = new Handler(Looper.getMainLooper());
/* 233 */           Bundle tokenState = loadTokenFromCache ? tokenCachingStrategy.load() : null;
/* 234 */           if (TokenCachingStrategy.hasTokenInformation(tokenState)) {
/* 235 */               Date cachedExpirationDate = TokenCachingStrategy.getDate(tokenState, TokenCachingStrategy.EXPIRATION_DATE_KEY);
/* 237 */               Date now = new Date();
/* 239 */               if (cachedExpirationDate == null || cachedExpirationDate.before(now)) {
/* 242 */                   tokenCachingStrategy.clear();
/* 243 */                   this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
/* 252 */                   return;
                        } else {
/* 246 */                   this.tokenInfo = AccessToken.createFromCache(tokenState);
/* 247 */                   this.state = SessionState.CREATED_TOKEN_LOADED;
/* 252 */                   return;
                        }
                    }
/* 250 */           this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
                }

/* 261 */       public final Bundle getAuthorizationBundle() {
                    Bundle bundle;
                    synchronized (this.lock) {
                        try {
/* 263 */                   bundle = this.authorizationBundle;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return bundle;
                }

/* 272 */       public final boolean isOpened() {
                    boolean zIsOpened;
                    synchronized (this.lock) {
                        try {
/* 274 */                   zIsOpened = this.state.isOpened();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zIsOpened;
                }

/* 278 */       public final boolean isClosed() {
                    boolean zIsClosed;
                    synchronized (this.lock) {
                        try {
/* 280 */                   zIsClosed = this.state.isClosed();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zIsClosed;
                }

/* 290 */       public final SessionState getState() {
                    SessionState sessionState;
                    synchronized (this.lock) {
                        try {
/* 292 */                   sessionState = this.state;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return sessionState;
                }

/* 301 */       public final String getApplicationId() {
/* 302 */           return this.applicationId;
                }

/* 310 */       public final String getAccessToken() {
                    String token;
                    synchronized (this.lock) {
                        try {
/* 312 */                   token = this.tokenInfo == null ? null : this.tokenInfo.getToken();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return token;
                }

/* 327 */       public final Date getExpirationDate() {
                    Date expires;
                    synchronized (this.lock) {
                        try {
/* 329 */                   expires = this.tokenInfo == null ? null : this.tokenInfo.getExpires();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return expires;
                }

/* 346 */       public final List<String> getPermissions() {
                    List<String> permissions;
                    synchronized (this.lock) {
                        try {
/* 348 */                   permissions = this.tokenInfo == null ? null : this.tokenInfo.getPermissions();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return permissions;
                }

/* 378 */       public final void openForRead(OpenRequest openRequest) {
/* 379 */           open(openRequest, SessionAuthorizationType.READ);
                }

/* 408 */       public final void openForPublish(OpenRequest openRequest) {
/* 409 */           open(openRequest, SessionAuthorizationType.PUBLISH);
                }

/* 428 */       public final void open(AccessToken accessToken, StatusCallback callback) {
                    synchronized (this.lock) {
                        try {
/* 430 */                   if (this.pendingRequest != null) {
/* 431 */                       throw new UnsupportedOperationException("Session: an attempt was made to open a session that has a pending request.");
                            }
/* 435 */                   if (this.state != SessionState.CREATED && this.state != SessionState.CREATED_TOKEN_LOADED) {
/* 436 */                       throw new UnsupportedOperationException("Session: an attempt was made to open an already opened session.");
                            }
/* 440 */                   if (callback != null) {
/* 441 */                       addCallback(callback);
                            }
/* 444 */                   this.tokenInfo = accessToken;
/* 446 */                   if (this.tokenCachingStrategy != null) {
/* 447 */                       this.tokenCachingStrategy.save(accessToken.toCacheBundle());
                            }
/* 450 */                   SessionState oldState = this.state;
/* 451 */                   this.state = SessionState.OPENED;
/* 452 */                   postStateChange(oldState, this.state, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 455 */           autoPublishAsync();
                }

/* 474 */       public final void requestNewReadPermissions(NewPermissionsRequest newPermissionsRequest) {
/* 475 */           requestNewPermissions(newPermissionsRequest, SessionAuthorizationType.READ);
                }

/* 494 */       public final void requestNewPublishPermissions(NewPermissionsRequest newPermissionsRequest) {
/* 495 */           requestNewPermissions(newPermissionsRequest, SessionAuthorizationType.PUBLISH);
                }

/* 518 */       public final boolean onActivityResult(Activity currentActivity, int requestCode, int resultCode, Intent data) {
                    boolean z = true;
/* 519 */           Validate.notNull(currentActivity, "currentActivity");
/* 521 */           initializeStaticContext(currentActivity);
                    synchronized (this.lock) {
                        try {
/* 524 */                   if (this.pendingRequest == null || requestCode != this.pendingRequest.getRequestCode()) {
/* 525 */                       z = false;
                            } else {
/* 530 */                       Exception exception = null;
/* 532 */                       if (data != null) {
/* 533 */                           AuthorizationClient.Result result = (AuthorizationClient.Result) data.getSerializableExtra("com.facebook.LoginActivity:Result");
/* 535 */                           if (result != null) {
/* 537 */                               handleAuthorizationResult(resultCode, result);
                                    } else if (this.authorizationClient != null) {
/* 541 */                               this.authorizationClient.onActivityResult(requestCode, resultCode, data);
                                    }
                                } else if (resultCode == 0) {
/* 545 */                           exception = new FacebookOperationCanceledException("User canceled operation.");
                                }
/* 548 */                       finishAuthOrReauth(null, exception);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 549 */           return z;
                }

/* 557 */       public final void close() {
                    synchronized (this.lock) {
                        try {
/* 559 */                   SessionState oldState = this.state;
                            switch (this.state) {
                                case CREATED:
                                case OPENING:
/* 564 */                           this.state = SessionState.CLOSED_LOGIN_FAILED;
/* 565 */                           postStateChange(oldState, this.state, new FacebookException("Log in attempt aborted."));
                                    break;
                                case CREATED_TOKEN_LOADED:
                                case OPENED:
                                case OPENED_TOKEN_UPDATED:
/* 572 */                           this.state = SessionState.CLOSED;
/* 573 */                           postStateChange(oldState, this.state, null);
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 583 */       public final void closeAndClearTokenInformation() {
/* 584 */           if (this.tokenCachingStrategy != null) {
/* 585 */               this.tokenCachingStrategy.clear();
                    }
/* 587 */           Utility.clearFacebookCookies(staticContext);
/* 588 */           close();
                }

/* 596 */       public final void addCallback(StatusCallback callback) {
                    synchronized (this.callbacks) {
/* 598 */               if (callback != null) {
                            try {
                                if (!this.callbacks.contains(callback)) {
/* 599 */                           this.callbacks.add(callback);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 609 */       public final void removeCallback(StatusCallback callback) {
                    synchronized (this.callbacks) {
                        try {
/* 611 */                   this.callbacks.remove(callback);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 616 */       public String toString() {
                    return "{Session state:" + this.state + ", token:" + (this.tokenInfo == null ? "null" : this.tokenInfo) + ", appId:" + (this.applicationId == null ? "null" : this.applicationId) + "}";
                }

/* 622 */       void extendTokenCompleted(Bundle bundle) {
                    synchronized (this.lock) {
                        try {
/* 624 */                   SessionState oldState = this.state;
                            switch (this.state) {
                                case OPENED:
/* 628 */                           this.state = SessionState.OPENED_TOKEN_UPDATED;
/* 629 */                           postStateChange(oldState, this.state, null);
                                    break;
                                case OPENED_TOKEN_UPDATED:
                                    break;
                                default:
/* 635 */                           Log.d(TAG, "refreshToken ignored in state " + this.state);
/* 643 */                           return;
                            }
/* 638 */                   this.tokenInfo = AccessToken.createFromRefresh(this.tokenInfo, bundle);
/* 639 */                   if (this.tokenCachingStrategy != null) {
/* 640 */                       this.tokenCachingStrategy.save(this.tokenInfo.toCacheBundle());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 645 */       private Object writeReplace() {
/* 646 */           return new SerializationProxyV1(this.applicationId, this.state, this.tokenInfo, this.lastAttemptedTokenExtendDate, false, this.pendingRequest);
                }

/* 651 */       private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 652 */           throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                }

/* 661 */       public static final void saveSession(Session session, Bundle bundle) {
/* 662 */           if (bundle != null && session != null && !bundle.containsKey(SESSION_BUNDLE_SAVE_KEY)) {
/* 663 */               ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        try {
/* 665 */                   new ObjectOutputStream(outputStream).writeObject(session);
/* 669 */                   bundle.putByteArray(SESSION_BUNDLE_SAVE_KEY, outputStream.toByteArray());
/* 670 */                   bundle.putBundle(AUTH_BUNDLE_SAVE_KEY, session.authorizationBundle);
                        } catch (IOException e) {
/* 667 */                   throw new FacebookException("Unable to save session.", e);
                        }
                    }
                }

/* 687 */       public static final Session restoreSession(Context context, TokenCachingStrategy cachingStrategy, StatusCallback callback, Bundle bundle) {
/* 688 */           if (bundle == null) {
                        return null;
                    }
/* 691 */           byte[] data = bundle.getByteArray(SESSION_BUNDLE_SAVE_KEY);
/* 692 */           if (data != null) {
/* 693 */               ByteArrayInputStream is = new ByteArrayInputStream(data);
                        try {
/* 695 */                   Session session = (Session) new ObjectInputStream(is).readObject();
/* 696 */                   initializeStaticContext(context);
/* 697 */                   if (cachingStrategy != null) {
/* 698 */                       session.tokenCachingStrategy = cachingStrategy;
                            } else {
/* 700 */                       session.tokenCachingStrategy = new SharedPreferencesTokenCachingStrategy(context);
                            }
/* 702 */                   if (callback != null) {
/* 703 */                       session.addCallback(callback);
                            }
/* 705 */                   session.authorizationBundle = bundle.getBundle(AUTH_BUNDLE_SAVE_KEY);
/* 713 */                   return session;
                        } catch (IOException e) {
/* 710 */                   Log.w(TAG, "Unable to restore session.", e);
                        } catch (ClassNotFoundException e2) {
/* 708 */                   Log.w(TAG, "Unable to restore session", e2);
                        }
                    }
                    return null;
                }

/* 722 */       public static final Session getActiveSession() {
                    Session session;
                    synchronized (STATIC_LOCK) {
                        try {
/* 724 */                   session = activeSession;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return session;
                }

/* 743 */       public static final void setActiveSession(Session session) {
                    synchronized (STATIC_LOCK) {
                        try {
/* 745 */                   if (session != activeSession) {
/* 746 */                       Session oldSession = activeSession;
/* 748 */                       if (oldSession != null) {
/* 749 */                           oldSession.close();
                                }
/* 752 */                       activeSession = session;
/* 754 */                       if (oldSession != null) {
/* 755 */                           postActiveSessionAction(ACTION_ACTIVE_SESSION_UNSET);
                                }
/* 758 */                       if (session != null) {
/* 759 */                           postActiveSessionAction(ACTION_ACTIVE_SESSION_SET);
/* 761 */                           if (session.isOpened()) {
/* 762 */                               postActiveSessionAction(ACTION_ACTIVE_SESSION_OPENED);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 776 */       public static Session openActiveSessionFromCache(Context context) {
/* 777 */           return openActiveSession(context, false, (OpenRequest) null);
                }

/* 798 */       public static Session openActiveSession(Activity activity, boolean allowLoginUI, StatusCallback callback) {
/* 799 */           return openActiveSession(activity, allowLoginUI, new OpenRequest(activity).setCallback(callback));
                }

/* 821 */       public static Session openActiveSession(Context context, Fragment fragment, boolean allowLoginUI, StatusCallback callback) {
/* 822 */           return openActiveSession(context, allowLoginUI, new OpenRequest(fragment).setCallback(callback));
                }

/* 847 */       public static Session openActiveSessionWithAccessToken(Context context, AccessToken accessToken, StatusCallback callback) {
/* 848 */           Session session = new Session(context, null, null, false);
/* 850 */           setActiveSession(session);
/* 851 */           session.open(accessToken, callback);
/* 853 */           return session;
                }

/* 856 */       private static Session openActiveSession(Context context, boolean allowLoginUI, OpenRequest openRequest) {
/* 857 */           Session session = new Builder(context).build();
/* 858 */           if (!SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) && !allowLoginUI) {
                        return null;
                    }
/* 859 */           setActiveSession(session);
/* 860 */           session.openForRead(openRequest);
/* 863 */           return session;
                }

/* 866 */       static Context getStaticContext() {
/* 867 */           return staticContext;
                }

/* 870 */       static void initializeStaticContext(Context currentContext) {
/* 871 */           if (currentContext != null && staticContext == null) {
/* 872 */               Context applicationContext = currentContext.getApplicationContext();
/* 873 */               if (applicationContext == null) {
                            applicationContext = currentContext;
                        }
                        staticContext = applicationContext;
                    }
                }

/* 877 */       void authorize(AuthorizationRequest request) {
/* 880 */           request.setApplicationId(this.applicationId);
/* 882 */           autoPublishAsync();
/* 884 */           boolean started = tryLoginActivity(request);
/* 886 */           if (!started && AuthorizationRequest.access$100(request)) {
/* 887 */               started = tryLegacyAuth(request);
                    }
/* 890 */           if (!started) {
                        synchronized (this.lock) {
                            try {
/* 892 */                       SessionState oldState = this.state;
                                switch (this.state) {
                                    case CLOSED:
                                    case CLOSED_LOGIN_FAILED:
                                        break;
                                    default:
/* 900 */                               this.state = SessionState.CLOSED_LOGIN_FAILED;
/* 901 */                               postStateChange(oldState, this.state, new FacebookException("Log in attempt failed."));
                                        break;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 907 */       private void open(OpenRequest openRequest, SessionAuthorizationType authType) {
                    SessionState newState;
/* 908 */           validatePermissions(openRequest, authType);
/* 909 */           validateLoginBehavior(openRequest);
                    synchronized (this.lock) {
                        try {
/* 913 */                   if (this.pendingRequest != null) {
/* 914 */                       postStateChange(this.state, this.state, new UnsupportedOperationException("Session: an attempt was made to open a session that has a pending request."));
/* 953 */                       return;
                            }
/* 918 */                   SessionState oldState = this.state;
                            switch (this.state) {
                                case CREATED:
/* 922 */                           newState = SessionState.OPENING;
                                    this.state = newState;
/* 923 */                           if (openRequest == null) {
/* 924 */                               throw new IllegalArgumentException("openRequest cannot be null when opening a new Session");
                                    }
/* 926 */                           this.pendingRequest = openRequest;
                                    break;
                                case OPENING:
                                default:
/* 941 */                           throw new UnsupportedOperationException("Session: an attempt was made to open an already opened session.");
                                case CREATED_TOKEN_LOADED:
/* 929 */                           if (openRequest != null && !Utility.isNullOrEmpty(openRequest.getPermissions()) && !Utility.isSubset(openRequest.getPermissions(), getPermissions())) {
/* 931 */                               this.pendingRequest = openRequest;
                                    }
/* 934 */                           if (this.pendingRequest == null) {
/* 935 */                               newState = SessionState.OPENED;
                                        this.state = newState;
                                    } else {
/* 937 */                               newState = SessionState.OPENING;
                                        this.state = newState;
                                    }
                                    break;
                            }
/* 944 */                   if (openRequest != null) {
/* 945 */                       addCallback(openRequest.getCallback());
                            }
/* 947 */                   postStateChange(oldState, newState, null);
/* 950 */                   if (newState == SessionState.OPENING) {
/* 951 */                       authorize(openRequest);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 955 */       private void requestNewPermissions(NewPermissionsRequest newPermissionsRequest, SessionAuthorizationType authType) {
/* 956 */           validatePermissions(newPermissionsRequest, authType);
/* 957 */           validateLoginBehavior(newPermissionsRequest);
/* 959 */           if (newPermissionsRequest != null) {
                        synchronized (this.lock) {
                            try {
/* 961 */                       if (this.pendingRequest != null) {
/* 962 */                           throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that has a pending request.");
                                }
                                switch (this.state) {
                                    case OPENED:
                                    case OPENED_TOKEN_UPDATED:
/* 968 */                               this.pendingRequest = newPermissionsRequest;
                                        break;
                                    default:
/* 971 */                               throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that is not currently open.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 976 */               newPermissionsRequest.setValidateSameFbidAsToken(getAccessToken());
/* 977 */               authorize(newPermissionsRequest);
                    }
                }

/* 981 */       private void validateLoginBehavior(AuthorizationRequest request) {
/* 982 */           if (request != null && !AuthorizationRequest.access$100(request)) {
/* 983 */               Intent intent = new Intent();
/* 984 */               intent.setClass(getStaticContext(), LoginActivity.class);
/* 985 */               if (!resolveIntent(intent)) {
/* 986 */                   throw new FacebookException(String.format("Cannot use SessionLoginBehavior %s when %s is not declared as an activity in AndroidManifest.xml", request.getLoginBehavior(), LoginActivity.class.getName()));
                        }
                    }
                }

/* 993 */       private void validatePermissions(AuthorizationRequest request, SessionAuthorizationType authType) {
/* 994 */           if (request == null || Utility.isNullOrEmpty(request.getPermissions())) {
/* 995 */               if (SessionAuthorizationType.PUBLISH.equals(authType)) {
/* 996 */                   throw new FacebookException("Cannot request publish or manage authorization with no permissions.");
                        }
/* 1017 */              return;
                    }
                    for (String permission : request.getPermissions()) {
/* 1001 */              if (isPublishPermission(permission)) {
/* 1002 */                  if (SessionAuthorizationType.READ.equals(authType)) {
/* 1003 */                      throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", permission));
                            }
                        } else if (SessionAuthorizationType.PUBLISH.equals(authType)) {
/* 1010 */                  Log.w(TAG, String.format("Should not pass a read permission (%s) to a request for publish or manage authorization", permission));
                        }
                    }
                }

/* 1019 */      static boolean isPublishPermission(String permission) {
                    return permission != null && (permission.startsWith(PUBLISH_PERMISSION_PREFIX) || permission.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(permission));
                }

/* 1027 */      private void handleAuthorizationResult(int resultCode, AuthorizationClient.Result result) {
/* 1028 */          AccessToken newToken = null;
/* 1029 */          Exception exception = null;
/* 1030 */          if (resultCode == -1) {
/* 1031 */              if (result.code == AuthorizationClient.Result.Code.SUCCESS) {
/* 1032 */                  newToken = result.token;
                        } else {
/* 1034 */                  exception = new FacebookAuthorizationException(result.errorMessage);
                        }
                    } else if (resultCode == 0) {
/* 1037 */              exception = new FacebookOperationCanceledException(result.errorMessage);
                    }
/* 1040 */          this.authorizationClient = null;
/* 1041 */          finishAuthOrReauth(newToken, exception);
                }

/* 1044 */      private boolean tryLoginActivity(AuthorizationRequest request) {
/* 1045 */          Intent intent = getLoginActivityIntent(request);
/* 1047 */          if (!resolveIntent(intent)) {
                        return false;
                    }
                    try {
/* 1052 */              request.getStartActivityDelegate().startActivityForResult(intent, request.getRequestCode());
/* 1057 */              return true;
                    } catch (ActivityNotFoundException e) {
                        return false;
                    }
                }

/* 1060 */      private boolean resolveIntent(Intent intent) {
/* 1061 */          ResolveInfo resolveInfo = getStaticContext().getPackageManager().resolveActivity(intent, 0);
                    return resolveInfo != null;
                }

/* 1068 */      private Intent getLoginActivityIntent(AuthorizationRequest request) {
/* 1069 */          Intent intent = new Intent();
/* 1070 */          intent.setClass(getStaticContext(), LoginActivity.class);
/* 1071 */          intent.setAction(request.getLoginBehavior().toString());
/* 1074 */          AuthorizationClient.AuthorizationRequest authClientRequest = request.getAuthorizationClientRequest();
/* 1075 */          Bundle extras = LoginActivity.populateIntentExtras(authClientRequest);
/* 1076 */          intent.putExtras(extras);
/* 1078 */          return intent;
                }

                /* JADX INFO: renamed from: com.facebook.Session$2 */
/* 1081 */      class C07672 implements AuthorizationClient.OnCompletedListener {
/* 1082 */          C07672() {
                    }

                    @Override // com.facebook.AuthorizationClient.OnCompletedListener
/* 1086 */          public void onCompleted(AuthorizationClient.Result result) {
                        int activityResult;
/* 1087 */              if (result.code == AuthorizationClient.Result.Code.CANCEL) {
/* 1088 */                  activityResult = 0;
                        } else {
/* 1090 */                  activityResult = -1;
                        }
/* 1092 */              Session.access$200(Session.this, activityResult, result);
                    }
                }

/* 1081 */      private boolean tryLegacyAuth(AuthorizationRequest request) {
/* 1082 */          this.authorizationClient = new AuthorizationClient();
/* 1083 */          this.authorizationClient.setOnCompletedListener(new C07672());
/* 1095 */          this.authorizationClient.setContext(getStaticContext());
/* 1096 */          this.authorizationClient.startOrContinueAuth(request.getAuthorizationClientRequest());
/* 1098 */          return true;
                }

/* 1103 */      void finishAuthOrReauth(AccessToken newToken, Exception exception) {
/* 1104 */          if (newToken != null && newToken.isInvalid()) {
/* 1105 */              newToken = null;
/* 1106 */              exception = new FacebookException("Invalid access token.");
                    }
                    synchronized (this.lock) {
                        try {
                            switch (this.state) {
                                case OPENING:
/* 1113 */                          finishAuthorization(newToken, exception);
                                    break;
                                case OPENED:
                                case OPENED_TOKEN_UPDATED:
/* 1119 */                          finishReauthorization(newToken, exception);
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1126 */      private void finishAuthorization(AccessToken newToken, Exception exception) {
/* 1127 */          SessionState oldState = this.state;
/* 1128 */          if (newToken != null) {
/* 1129 */              this.tokenInfo = newToken;
/* 1130 */              saveTokenToCache(newToken);
/* 1132 */              this.state = SessionState.OPENED;
                    } else if (exception != null) {
/* 1134 */              this.state = SessionState.CLOSED_LOGIN_FAILED;
                    }
/* 1136 */          this.pendingRequest = null;
/* 1137 */          postStateChange(oldState, this.state, exception);
                }

/* 1140 */      private void finishReauthorization(AccessToken newToken, Exception exception) {
/* 1141 */          SessionState oldState = this.state;
/* 1143 */          if (newToken != null) {
/* 1144 */              this.tokenInfo = newToken;
/* 1145 */              saveTokenToCache(newToken);
/* 1147 */              this.state = SessionState.OPENED_TOKEN_UPDATED;
                    }
/* 1150 */          this.pendingRequest = null;
/* 1151 */          postStateChange(oldState, this.state, exception);
                }

/* 1154 */      private void saveTokenToCache(AccessToken newToken) {
/* 1155 */          if (newToken != null && this.tokenCachingStrategy != null) {
/* 1156 */              this.tokenCachingStrategy.save(newToken.toCacheBundle());
                    }
                }

/* 1163 */      void postStateChange(SessionState oldState, SessionState newState, Exception exception) {
/* 1164 */          if (oldState != newState || oldState == SessionState.OPENED_TOKEN_UPDATED || exception != null) {
/* 1170 */              if (newState.isClosed()) {
/* 1171 */                  this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
                        }
                        synchronized (this.callbacks) {
                            try {
/* 1178 */                      Runnable runCallbacks = new RunnableC07683(newState, exception);
/* 1192 */                      runWithHandlerOrExecutor(this.handler, runCallbacks);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 1195 */              if (this == activeSession && oldState.isOpened() != newState.isOpened()) {
/* 1197 */                  if (newState.isOpened()) {
/* 1198 */                      postActiveSessionAction(ACTION_ACTIVE_SESSION_OPENED);
                            } else {
/* 1200 */                      postActiveSessionAction(ACTION_ACTIVE_SESSION_CLOSED);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.facebook.Session$3 */
/* 1176 */      class RunnableC07683 implements Runnable {
                    final /* synthetic */ Exception val$exception;
                    final /* synthetic */ SessionState val$newState;

/* 1177 */          RunnableC07683(SessionState sessionState, Exception exc) {
                        this.val$newState = sessionState;
                        this.val$exception = exc;
                    }

                    /* JADX INFO: renamed from: com.facebook.Session$3$1, reason: invalid class name */
/* 1179 */          class AnonymousClass1 implements Runnable {
                        final /* synthetic */ StatusCallback val$callback;

/* 1180 */              AnonymousClass1(StatusCallback statusCallback) {
                            this.val$callback = statusCallback;
                        }

                        @Override // java.lang.Runnable
/* 1183 */              public void run() {
/* 1184 */                  this.val$callback.call(Session.this, RunnableC07683.this.val$newState, RunnableC07683.this.val$exception);
                        }
                    }

                    @Override // java.lang.Runnable
/* 1179 */          public void run() {
                        for (StatusCallback callback : Session.access$300(Session.this)) {
/* 1181 */                  Runnable closure = new AnonymousClass1(callback);
/* 1188 */                  Session.access$500(Session.access$400(Session.this), closure);
                        }
                    }
                }

/* 1206 */      static void postActiveSessionAction(String action) {
/* 1207 */          Intent intent = new Intent(action);
/* 1209 */          LocalBroadcastManager.getInstance(getStaticContext()).sendBroadcast(intent);
                }

/* 1212 */      private static void runWithHandlerOrExecutor(Handler handler, Runnable runnable) {
/* 1213 */          if (handler != null) {
/* 1214 */              handler.post(runnable);
                    } else {
/* 1216 */              Settings.getExecutor().execute(runnable);
                    }
                }

/* 1220 */      void extendAccessTokenIfNeeded() {
/* 1221 */          if (shouldExtendAccessToken()) {
/* 1222 */              extendAccessToken();
                    }
                }

/* 1226 */      void extendAccessToken() {
                    TokenRefreshRequest newTokenRefreshRequest = null;
                    synchronized (this.lock) {
                        if (this.currentTokenRefreshRequest == null) {
                            newTokenRefreshRequest = new TokenRefreshRequest();
                            this.currentTokenRefreshRequest = newTokenRefreshRequest;
                        }
                        if (newTokenRefreshRequest != null) {
                            newTokenRefreshRequest.bind();
                        }
                    }
                }

/* 1240 */      boolean shouldExtendAccessToken() {
/* 1241 */          if (this.currentTokenRefreshRequest != null) {
/* 1242 */              return false;
                    }
/* 1247 */          Date now = new Date();
/* 1249 */          if (!this.state.isOpened() || !this.tokenInfo.getSource().canExtendToken() || now.getTime() - this.lastAttemptedTokenExtendDate.getTime() <= CommonUtil.HOUR_MILLIS || now.getTime() - this.tokenInfo.getLastRefresh().getTime() <= 86400000) {
/* 1245 */              return false;
                    }
/* 1252 */          return true;
                }

/* 1258 */      AccessToken getTokenInfo() {
/* 1259 */          return this.tokenInfo;
                }

/* 1262 */      void setTokenInfo(AccessToken tokenInfo) {
/* 1263 */          this.tokenInfo = tokenInfo;
                }

/* 1266 */      Date getLastAttemptedTokenExtendDate() {
/* 1267 */          return this.lastAttemptedTokenExtendDate;
                }

/* 1270 */      void setLastAttemptedTokenExtendDate(Date lastAttemptedTokenExtendDate) {
/* 1271 */          this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
                }

/* 1274 */      void setCurrentTokenRefreshRequest(TokenRefreshRequest request) {
/* 1275 */          this.currentTokenRefreshRequest = request;
                }

/* 1276 */      class TokenRefreshRequest implements ServiceConnection {
                    final Messenger messageReceiver;
                    Messenger messageSender = null;

/* 1277 */          TokenRefreshRequest() {
/* 1280 */              this.messageReceiver = new Messenger(new TokenRefreshRequestHandler(Session.this, this));
                    }

/* 1277 */          void access$800() {
/* 1278 */              cleanup();
                    }

/* 1285 */          public void bind() {
/* 1286 */              Intent intent = NativeProtocol.createTokenRefreshIntent(Session.getStaticContext());
/* 1287 */              if (intent != null && Session.access$600().bindService(intent, Session.this.new TokenRefreshRequest(), 1)) {
/* 1289 */                  Session.this.setLastAttemptedTokenExtendDate(new Date());
                        } else {
/* 1291 */                  cleanup();
                        }
                    }

                    @Override // android.content.ServiceConnection
/* 1296 */          public void onServiceConnected(ComponentName className, IBinder service) {
/* 1297 */              this.messageSender = new Messenger(service);
/* 1298 */              refreshToken();
                    }

                    @Override // android.content.ServiceConnection
/* 1302 */          public void onServiceDisconnected(ComponentName arg) {
/* 1303 */              cleanup();
/* 1307 */              Session.access$600().unbindService(this);
                    }

/* 1310 */          private void cleanup() {
/* 1311 */              if (Session.access$700(Session.this) == this) {
/* 1312 */                  Session.access$702(Session.this, null);
                        }
                    }

/* 1316 */          private void refreshToken() {
/* 1317 */              Bundle requestData = new Bundle();
/* 1318 */              requestData.putString("access_token", Session.this.getTokenInfo().getToken());
/* 1320 */              Message request = Message.obtain();
/* 1321 */              request.setData(requestData);
/* 1322 */              request.replyTo = this.messageReceiver;
                        try {
/* 1325 */                  this.messageSender.send(request);
                        } catch (RemoteException e) {
/* 1327 */                  cleanup();
                        }
                    }
                }

/* 1342 */      static class TokenRefreshRequestHandler extends Handler {
                    private WeakReference<TokenRefreshRequest> refreshRequestWeakReference;
                    private WeakReference<Session> sessionWeakReference;

/* 1343 */          TokenRefreshRequestHandler(Session session, TokenRefreshRequest refreshRequest) {
/* 1344 */              super(Looper.getMainLooper());
/* 1345 */              this.sessionWeakReference = new WeakReference<>(session);
/* 1346 */              this.refreshRequestWeakReference = new WeakReference<>(refreshRequest);
                    }

                    @Override // android.os.Handler
/* 1350 */          public void handleMessage(Message msg) {
/* 1351 */              String token = msg.getData().getString("access_token");
/* 1352 */              Session session = this.sessionWeakReference.get();
/* 1354 */              if (session != null && token != null) {
/* 1355 */                  session.extendTokenCompleted(msg.getData());
                        }
/* 1358 */              TokenRefreshRequest request = this.refreshRequestWeakReference.get();
/* 1359 */              if (request != null) {
/* 1362 */                  Session.access$600().unbindService(request);
/* 1363 */                  request.access$800();
                        }
                    }
                }

/* 1378 */      public int hashCode() {
/* 1379 */          return 0;
                }

/* 1383 */      public boolean equals(Object otherObj) {
/* 1384 */          if (!(otherObj instanceof Session)) {
                        return false;
                    }
/* 1387 */          Session other = (Session) otherObj;
                    return areEqual(other.applicationId, this.applicationId) && areEqual(other.authorizationBundle, this.authorizationBundle) && areEqual(other.state, this.state) && areEqual(other.getExpirationDate(), getExpirationDate());
                }

/* 1395 */      private static boolean areEqual(Object a, Object b) {
/* 1396 */          if (a == null) {
                        return b == null;
                    }
/* 1399 */          return a.equals(b);
                }

/* 1414 */      public static final class Builder {
                    private String applicationId;
                    private final Context context;
                    private TokenCachingStrategy tokenCachingStrategy;

/* 1415 */          public Builder(Context context) {
/* 1417 */              this.context = context;
                    }

/* 1426 */          public Builder setApplicationId(String applicationId) {
/* 1427 */              this.applicationId = applicationId;
/* 1428 */              return this;
                    }

/* 1437 */          public Builder setTokenCachingStrategy(TokenCachingStrategy tokenCachingStrategy) {
/* 1438 */              this.tokenCachingStrategy = tokenCachingStrategy;
/* 1439 */              return this;
                    }

/* 1447 */          public Session build() {
/* 1448 */              return new Session(this.context, this.applicationId, this.tokenCachingStrategy);
                    }
                }

/* 1458 */      private void autoPublishAsync() {
                    String applicationId;
/* 1459 */          AutoPublishAsyncTask asyncTask = null;
                    synchronized (this) {
                        try {
/* 1461 */                  if (this.autoPublishAsyncTask == null && Settings.getShouldAutoPublishInstall() && (applicationId = this.applicationId) != null) {
/* 1467 */                      AutoPublishAsyncTask asyncTask2 = new AutoPublishAsyncTask(applicationId, staticContext);
                                this.autoPublishAsyncTask = asyncTask2;
                                asyncTask = asyncTask2;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 1472 */          if (asyncTask != null) {
/* 1473 */              asyncTask.execute(new Void[0]);
                    }
                }

/* 1478 */      private class AutoPublishAsyncTask extends AsyncTask<Void, Void, Void> {
                    private final Context mApplicationContext;
                    private final String mApplicationId;



/* 1483 */          public AutoPublishAsyncTask(String applicationId, Context context) {
/* 1485 */              this.mApplicationId = applicationId;
/* 1486 */              this.mApplicationContext = context.getApplicationContext();
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 1491 */          protected Void doInBackground(Void... voids) {
                        try {
/* 1492 */                  Settings.publishInstallAndWait(this.mApplicationContext, this.mApplicationId);
/* 1496 */                  return null;
                        } catch (Exception e) {
/* 1494 */                  Utility.logd("Facebook-publish", e);
/* 1496 */                  return null;
                        }
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 1501 */          protected void onPostExecute(Void result) {
                        synchronized (Session.this) {
                            try {
/* 1503 */                      Session.access$902(Session.this, null);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 1509 */      public static class AuthorizationRequest implements Serializable {
                    private static final long serialVersionUID = 1;
                    private String applicationId;
                    private SessionDefaultAudience defaultAudience;
                    private boolean isLegacy;
                    private SessionLoginBehavior loginBehavior;
                    private List<String> permissions;
                    private int requestCode;
                    private final StartActivityDelegate startActivityDelegate;
                    private StatusCallback statusCallback;
                    private String validateSameFbidAsToken;

/* 1510 */          /* synthetic */ AuthorizationRequest(SessionLoginBehavior x0, int x1, List x2, String x3, boolean x4, String x5, String x6, C07661 x7) {
/* 1511 */              this(x0, x1, x2, x3, x4, x5, x6);
                    }

/* 1510 */          static /* synthetic */ boolean access$100(AuthorizationRequest x0) {
/* 1511 */              return x0.isLegacy;
                    }

/* 1510 */          static /* synthetic */ StartActivityDelegate access$1000(AuthorizationRequest x0) {
/* 1511 */              return x0.startActivityDelegate;
                    }

                    /* JADX INFO: renamed from: com.facebook.Session$AuthorizationRequest$1 */
/* 1524 */          class C07701 implements StartActivityDelegate {
                        final /* synthetic */ Activity val$activity;

/* 1525 */              C07701(Activity activity) {
                            this.val$activity = activity;
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1528 */              public void startActivityForResult(Intent intent, int requestCode) {
/* 1529 */                  this.val$activity.startActivityForResult(intent, requestCode);
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1533 */              public Activity getActivityContext() {
/* 1534 */                  return this.val$activity;
                        }
                    }

/* 1524 */          AuthorizationRequest(Activity activity) {
/* 1516 */              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
/* 1517 */              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
/* 1519 */              this.isLegacy = false;
/* 1520 */              this.permissions = Collections.emptyList();
/* 1521 */              this.defaultAudience = SessionDefaultAudience.FRIENDS;
/* 1526 */              this.startActivityDelegate = new C07701(activity);
                    }

                    /* JADX INFO: renamed from: com.facebook.Session$AuthorizationRequest$2 */
/* 1538 */          class C07712 implements StartActivityDelegate {
                        final /* synthetic */ Fragment val$fragment;

/* 1539 */              C07712(Fragment fragment) {
                            this.val$fragment = fragment;
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1542 */              public void startActivityForResult(Intent intent, int requestCode) {
/* 1543 */                  this.val$fragment.getActivity().startActivityForResult(intent, requestCode);
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1547 */              public Activity getActivityContext() {
/* 1548 */                  return this.val$fragment.getActivity();
                        }
                    }

/* 1538 */          AuthorizationRequest(Fragment fragment) {
/* 1516 */              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
/* 1517 */              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
/* 1519 */              this.isLegacy = false;
/* 1520 */              this.permissions = Collections.emptyList();
/* 1521 */              this.defaultAudience = SessionDefaultAudience.FRIENDS;
/* 1540 */              this.startActivityDelegate = new C07712(fragment);
                    }

                    /* JADX INFO: renamed from: com.facebook.Session$AuthorizationRequest$3 */
/* 1557 */          class C07723 implements StartActivityDelegate {
/* 1558 */              C07723() {
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1561 */              public void startActivityForResult(Intent intent, int requestCode) {
/* 1562 */                  throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                        }

                        @Override // com.facebook.Session.StartActivityDelegate
/* 1567 */              public Activity getActivityContext() {
/* 1568 */                  throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                        }
                    }

/* 1557 */          private AuthorizationRequest(SessionLoginBehavior loginBehavior, int requestCode, List<String> permissions, String defaultAudience, boolean isLegacy, String applicationId, String validateSameFbidAsToken) {
/* 1516 */              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
/* 1517 */              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
/* 1519 */              this.isLegacy = false;
/* 1520 */              this.permissions = Collections.emptyList();
/* 1521 */              this.defaultAudience = SessionDefaultAudience.FRIENDS;
/* 1559 */              this.startActivityDelegate = new C07723();
/* 1572 */              this.loginBehavior = loginBehavior;
/* 1573 */              this.requestCode = requestCode;
/* 1574 */              this.permissions = permissions;
/* 1575 */              this.defaultAudience = SessionDefaultAudience.valueOf(defaultAudience);
/* 1576 */              this.isLegacy = isLegacy;
/* 1577 */              this.applicationId = applicationId;
/* 1578 */              this.validateSameFbidAsToken = validateSameFbidAsToken;
                    }

/* 1586 */          public void setIsLegacy(boolean isLegacy) {
/* 1587 */              this.isLegacy = isLegacy;
                    }

/* 1590 */          boolean isLegacy() {
/* 1591 */              return this.isLegacy;
                    }

/* 1594 */          AuthorizationRequest setCallback(StatusCallback statusCallback) {
/* 1595 */              this.statusCallback = statusCallback;
/* 1596 */              return this;
                    }

/* 1599 */          StatusCallback getCallback() {
/* 1600 */              return this.statusCallback;
                    }

/* 1603 */          AuthorizationRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
/* 1604 */              if (loginBehavior != null) {
/* 1605 */                  this.loginBehavior = loginBehavior;
                        }
/* 1607 */              return this;
                    }

/* 1610 */          SessionLoginBehavior getLoginBehavior() {
/* 1611 */              return this.loginBehavior;
                    }

/* 1614 */          AuthorizationRequest setRequestCode(int requestCode) {
/* 1615 */              if (requestCode >= 0) {
/* 1616 */                  this.requestCode = requestCode;
                        }
/* 1618 */              return this;
                    }

/* 1621 */          int getRequestCode() {
/* 1622 */              return this.requestCode;
                    }

/* 1625 */          AuthorizationRequest setPermissions(List<String> permissions) {
/* 1626 */              if (permissions != null) {
/* 1627 */                  this.permissions = permissions;
                        }
/* 1629 */              return this;
                    }

/* 1632 */          List<String> getPermissions() {
/* 1633 */              return this.permissions;
                    }

/* 1636 */          AuthorizationRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
/* 1637 */              if (defaultAudience != null) {
/* 1638 */                  this.defaultAudience = defaultAudience;
                        }
/* 1640 */              return this;
                    }

/* 1643 */          SessionDefaultAudience getDefaultAudience() {
/* 1644 */              return this.defaultAudience;
                    }

/* 1647 */          StartActivityDelegate getStartActivityDelegate() {
/* 1648 */              return this.startActivityDelegate;
                    }

/* 1651 */          String getApplicationId() {
/* 1652 */              return this.applicationId;
                    }

/* 1655 */          void setApplicationId(String applicationId) {
/* 1656 */              this.applicationId = applicationId;
                    }

/* 1659 */          String getValidateSameFbidAsToken() {
/* 1660 */              return this.validateSameFbidAsToken;
                    }

/* 1663 */          void setValidateSameFbidAsToken(String validateSameFbidAsToken) {
/* 1664 */              this.validateSameFbidAsToken = validateSameFbidAsToken;
                    }

                    /* JADX INFO: renamed from: com.facebook.Session$AuthorizationRequest$4 */
/* 1666 */          class C07734 implements AuthorizationClient.StartActivityDelegate {
/* 1667 */              C07734() {
                        }

                        @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 1670 */              public void startActivityForResult(Intent intent, int requestCode) {
/* 1671 */                  AuthorizationRequest.access$1000(AuthorizationRequest.this).startActivityForResult(intent, requestCode);
                        }

                        @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 1675 */              public Activity getActivityContext() {
/* 1676 */                  return AuthorizationRequest.access$1000(AuthorizationRequest.this).getActivityContext();
                        }
                    }

/* 1667 */          AuthorizationClient.AuthorizationRequest getAuthorizationClientRequest() {
/* 1668 */              AuthorizationClient.StartActivityDelegate delegate = new C07734();
/* 1679 */              return new AuthorizationClient.AuthorizationRequest(this.loginBehavior, this.requestCode, this.isLegacy, this.permissions, this.defaultAudience, this.applicationId, this.validateSameFbidAsToken, delegate);
                    }

/* 1684 */          Object writeReplace() {
/* 1685 */              return new AuthRequestSerializationProxyV1(this.loginBehavior, this.requestCode, this.permissions, this.defaultAudience.name(), this.isLegacy, this.applicationId, this.validateSameFbidAsToken, null);
                    }

/* 1691 */          void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 1692 */              throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                    }

/* 1693 */          private static class AuthRequestSerializationProxyV1 implements Serializable {
                        private static final long serialVersionUID = -8748347685113614927L;
                        private final String applicationId;
                        private final String defaultAudience;
                        private boolean isLegacy;
                        private final SessionLoginBehavior loginBehavior;
                        private final List<String> permissions;
                        private final int requestCode;
                        private final String validateSameFbidAsToken;

/* 1694 */              /* synthetic */ AuthRequestSerializationProxyV1(SessionLoginBehavior x0, int x1, List x2, String x3, boolean x4, String x5, String x6, C07661 x7) {
/* 1695 */                  this(x0, x1, x2, x3, x4, x5, x6);
                        }

/* 1706 */              private AuthRequestSerializationProxyV1(SessionLoginBehavior loginBehavior, int requestCode, List<String> permissions, String defaultAudience, boolean isLegacy, String applicationId, String validateSameFbidAsToken) {
/* 1708 */                  this.loginBehavior = loginBehavior;
/* 1709 */                  this.requestCode = requestCode;
/* 1710 */                  this.permissions = permissions;
/* 1711 */                  this.defaultAudience = defaultAudience;
/* 1712 */                  this.isLegacy = isLegacy;
/* 1713 */                  this.applicationId = applicationId;
/* 1714 */                  this.validateSameFbidAsToken = validateSameFbidAsToken;
                        }

/* 1717 */              private Object readResolve() {
/* 1718 */                  return new AuthorizationRequest(this.loginBehavior, this.requestCode, this.permissions, this.defaultAudience, this.isLegacy, this.applicationId, this.validateSameFbidAsToken, null);
                        }
                    }
                }

/* 1725 */      public static final class OpenRequest extends AuthorizationRequest {
                    private static final long serialVersionUID = 1;






/* 1735 */          public OpenRequest(Activity activity) {
/* 1736 */              super(activity);
                    }

/* 1744 */          public OpenRequest(Fragment fragment) {
/* 1745 */              super(fragment);
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1755 */          public final OpenRequest setCallback(StatusCallback statusCallback) {
/* 1756 */              super.setCallback(statusCallback);
/* 1757 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1768 */          public final OpenRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
/* 1769 */              super.setLoginBehavior(loginBehavior);
/* 1770 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1782 */          public final OpenRequest setRequestCode(int requestCode) {
/* 1783 */              super.setRequestCode(requestCode);
/* 1784 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1795 */          public final OpenRequest setPermissions(List<String> permissions) {
/* 1796 */              super.setPermissions(permissions);
/* 1797 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1808 */          public final OpenRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
/* 1809 */              super.setDefaultAudience(defaultAudience);
/* 1810 */              return this;
                    }
                }

/* 1815 */      public static final class NewPermissionsRequest extends AuthorizationRequest {
                    private static final long serialVersionUID = 1;





/* 1826 */          public NewPermissionsRequest(Activity activity, List<String> permissions) {
/* 1827 */              super(activity);
/* 1828 */              setPermissions(permissions);
                    }

/* 1837 */          public NewPermissionsRequest(Fragment fragment, List<String> permissions) {
/* 1838 */              super(fragment);
/* 1839 */              setPermissions(permissions);
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1849 */          public final NewPermissionsRequest setCallback(StatusCallback statusCallback) {
/* 1850 */              super.setCallback(statusCallback);
/* 1851 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1862 */          public final NewPermissionsRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
/* 1863 */              super.setLoginBehavior(loginBehavior);
/* 1864 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1876 */          public final NewPermissionsRequest setRequestCode(int requestCode) {
/* 1877 */              super.setRequestCode(requestCode);
/* 1878 */              return this;
                    }

                    @Override // com.facebook.Session.AuthorizationRequest
/* 1887 */          public final NewPermissionsRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
/* 1888 */              super.setDefaultAudience(defaultAudience);
/* 1889 */              return this;
                    }
                }
            }
