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
            
            
    public class Session implements Serializable {
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

         Session(String x0, SessionState x1, AccessToken x2, Date x3, boolean x4, AuthorizationRequest x5, C07661 x6) {
            this(x0, x1, x2, x3, x4, x5);
                }

        static  void access$200(Session x0, int x1, AuthorizationClient.Result x2) {
            x0.handleAuthorizationResult(x1, x2);
                }

        static  List<StatusCallback> access$300(Session x0) {
            return x0.callbacks;
                }

        static  Handler access$400(Session x0) {
            return x0.handler;
                }

        static  void access$500(Handler x0, Runnable x1) {
            runWithHandlerOrExecutor(x0, x1);
                }

        static  Context access$600() {
            return staticContext;
                }

        static  TokenRefreshRequest access$700(Session x0) {
            return x0.currentTokenRefreshRequest;
                }

        static  TokenRefreshRequest access$702(Session x0, TokenRefreshRequest x1) {
            x0.currentTokenRefreshRequest = x1;
                    return x1;
                }

        static  AutoPublishAsyncTask access$902(Session x0, AutoPublishAsyncTask x1) {
            x0.autoPublishAsyncTask = x1;
                    return x1;
                }

                
       static class C07661 extends HashSet<String> {
           C07661() {
               add("ads_management");
               add("create_event");
               add("rsvp_event");
                    }
                }

       private static class SerializationProxyV1 implements Serializable {
                    private static final long serialVersionUID = 7663436173185080063L;
                    private final String applicationId;
                    private final Date lastAttemptedTokenExtendDate;
                    private final AuthorizationRequest pendingRequest;
                    private final boolean shouldAutoPublish;
                    private final SessionState state;
                    private final AccessToken tokenInfo;

           SerializationProxyV1(String applicationId, SessionState state, AccessToken tokenInfo, Date lastAttemptedTokenExtendDate, boolean shouldAutoPublish, AuthorizationRequest pendingRequest) {
               this.applicationId = applicationId;
               this.state = state;
               this.tokenInfo = tokenInfo;
               this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
               this.shouldAutoPublish = shouldAutoPublish;
               this.pendingRequest = pendingRequest;
                    }

           private Object readResolve() {
               return new Session(this.applicationId, this.state, this.tokenInfo, this.lastAttemptedTokenExtendDate, this.shouldAutoPublish, this.pendingRequest, null);
                    }
                }

       private Session(String applicationId, SessionState state, AccessToken tokenInfo, Date lastAttemptedTokenExtendDate, boolean shouldAutoPublish, AuthorizationRequest pendingRequest) {
           this.lastAttemptedTokenExtendDate = new Date(0L);
           this.lock = new Object();
           this.applicationId = applicationId;
           this.state = state;
           this.tokenInfo = tokenInfo;
           this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
           this.pendingRequest = pendingRequest;
           this.handler = new Handler(Looper.getMainLooper());
           this.currentTokenRefreshRequest = null;
           this.tokenCachingStrategy = null;
           this.callbacks = new ArrayList();
                }

       public Session(Context currentContext) {
           this(currentContext, null, null, true);
                }

       Session(Context context, String applicationId, TokenCachingStrategy tokenCachingStrategy) {
           this(context, applicationId, tokenCachingStrategy, true);
                }

       Session(Context context, String applicationId, TokenCachingStrategy tokenCachingStrategy, boolean loadTokenFromCache) {
           this.lastAttemptedTokenExtendDate = new Date(0L);
           this.lock = new Object();
           applicationId = applicationId == null ? DEFAULT_FACEBOOK_ID : applicationId;
           Validate.notNull(applicationId, "applicationId");
           initializeStaticContext(context);
           tokenCachingStrategy = tokenCachingStrategy == null ? new SharedPreferencesTokenCachingStrategy(staticContext) : tokenCachingStrategy;
           this.applicationId = applicationId;
           this.tokenCachingStrategy = tokenCachingStrategy;
           this.state = SessionState.CREATED;
           this.pendingRequest = null;
           this.callbacks = new ArrayList();
           this.handler = new Handler(Looper.getMainLooper());
           Bundle tokenState = loadTokenFromCache ? tokenCachingStrategy.load() : null;
           if (TokenCachingStrategy.hasTokenInformation(tokenState)) {
               Date cachedExpirationDate = TokenCachingStrategy.getDate(tokenState, TokenCachingStrategy.EXPIRATION_DATE_KEY);
               Date now = new Date();
               if (cachedExpirationDate == null || cachedExpirationDate.before(now)) {
                   tokenCachingStrategy.clear();
                   this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
                   return;
                        } else {
                   this.tokenInfo = AccessToken.createFromCache(tokenState);
                   this.state = SessionState.CREATED_TOKEN_LOADED;
                   return;
                        }
                    }
           this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
                }

       public final Bundle getAuthorizationBundle() {
                    Bundle bundle;
                    synchronized (this.lock) {
                        try {
                   bundle = this.authorizationBundle;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return bundle;
                }

       public final boolean isOpened() {
                    boolean zIsOpened;
                    synchronized (this.lock) {
                        try {
                   zIsOpened = this.state.isOpened();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zIsOpened;
                }

       public final boolean isClosed() {
                    boolean zIsClosed;
                    synchronized (this.lock) {
                        try {
                   zIsClosed = this.state.isClosed();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zIsClosed;
                }

       public final SessionState getState() {
                    SessionState sessionState;
                    synchronized (this.lock) {
                        try {
                   sessionState = this.state;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return sessionState;
                }

       public final String getApplicationId() {
           return this.applicationId;
                }

       public final String getAccessToken() {
                    String token;
                    synchronized (this.lock) {
                        try {
                   token = this.tokenInfo == null ? null : this.tokenInfo.getToken();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return token;
                }

       public final Date getExpirationDate() {
                    Date expires;
                    synchronized (this.lock) {
                        try {
                   expires = this.tokenInfo == null ? null : this.tokenInfo.getExpires();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return expires;
                }

       public final List<String> getPermissions() {
                    List<String> permissions;
                    synchronized (this.lock) {
                        try {
                   permissions = this.tokenInfo == null ? null : this.tokenInfo.getPermissions();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return permissions;
                }

       public final void openForRead(OpenRequest openRequest) {
           open(openRequest, SessionAuthorizationType.READ);
                }

       public final void openForPublish(OpenRequest openRequest) {
           open(openRequest, SessionAuthorizationType.PUBLISH);
                }

       public final void open(AccessToken accessToken, StatusCallback callback) {
                    synchronized (this.lock) {
                        try {
                   if (this.pendingRequest != null) {
                       throw new UnsupportedOperationException("Session: an attempt was made to open a session that has a pending request.");
                            }
                   if (this.state != SessionState.CREATED && this.state != SessionState.CREATED_TOKEN_LOADED) {
                       throw new UnsupportedOperationException("Session: an attempt was made to open an already opened session.");
                            }
                   if (callback != null) {
                       addCallback(callback);
                            }
                   this.tokenInfo = accessToken;
                   if (this.tokenCachingStrategy != null) {
                       this.tokenCachingStrategy.save(accessToken.toCacheBundle());
                            }
                   SessionState oldState = this.state;
                   this.state = SessionState.OPENED;
                   postStateChange(oldState, this.state, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           autoPublishAsync();
                }

       public final void requestNewReadPermissions(NewPermissionsRequest newPermissionsRequest) {
           requestNewPermissions(newPermissionsRequest, SessionAuthorizationType.READ);
                }

       public final void requestNewPublishPermissions(NewPermissionsRequest newPermissionsRequest) {
           requestNewPermissions(newPermissionsRequest, SessionAuthorizationType.PUBLISH);
                }

       public final boolean onActivityResult(Activity currentActivity, int requestCode, int resultCode, Intent data) {
                    boolean z = true;
           Validate.notNull(currentActivity, "currentActivity");
           initializeStaticContext(currentActivity);
                    synchronized (this.lock) {
                        try {
                   if (this.pendingRequest == null || requestCode != this.pendingRequest.getRequestCode()) {
                       z = false;
                            } else {
                       Exception exception = null;
                       if (data != null) {
                           AuthorizationClient.Result result = (AuthorizationClient.Result) data.getSerializableExtra("com.facebook.LoginActivity:Result");
                           if (result != null) {
                               handleAuthorizationResult(resultCode, result);
                                    } else if (this.authorizationClient != null) {
                               this.authorizationClient.onActivityResult(requestCode, resultCode, data);
                                    }
                                } else if (resultCode == 0) {
                           exception = new FacebookOperationCanceledException("User canceled operation.");
                                }
                       finishAuthOrReauth(null, exception);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return z;
                }

       public final void close() {
                    synchronized (this.lock) {
                        try {
                   SessionState oldState = this.state;
                            switch (this.state) {
                                case CREATED:
                                case OPENING:
                           this.state = SessionState.CLOSED_LOGIN_FAILED;
                           postStateChange(oldState, this.state, new FacebookException("Log in attempt aborted."));
                                    break;
                                case CREATED_TOKEN_LOADED:
                                case OPENED:
                                case OPENED_TOKEN_UPDATED:
                           this.state = SessionState.CLOSED;
                           postStateChange(oldState, this.state, null);
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public final void closeAndClearTokenInformation() {
           if (this.tokenCachingStrategy != null) {
               this.tokenCachingStrategy.clear();
                    }
           Utility.clearFacebookCookies(staticContext);
           close();
                }

       public final void addCallback(StatusCallback callback) {
                    synchronized (this.callbacks) {
               if (callback != null) {
                            try {
                                if (!this.callbacks.contains(callback)) {
                           this.callbacks.add(callback);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       public final void removeCallback(StatusCallback callback) {
                    synchronized (this.callbacks) {
                        try {
                   this.callbacks.remove(callback);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public String toString() {
                    return "{Session state:" + this.state + ", token:" + (this.tokenInfo == null ? "null" : this.tokenInfo) + ", appId:" + (this.applicationId == null ? "null" : this.applicationId) + "}";
                }

       void extendTokenCompleted(Bundle bundle) {
                    synchronized (this.lock) {
                        try {
                   SessionState oldState = this.state;
                            switch (this.state) {
                                case OPENED:
                           this.state = SessionState.OPENED_TOKEN_UPDATED;
                           postStateChange(oldState, this.state, null);
                                    break;
                                case OPENED_TOKEN_UPDATED:
                                    break;
                                default:
                           Log.d(TAG, "refreshToken ignored in state " + this.state);
                           return;
                            }
                   this.tokenInfo = AccessToken.createFromRefresh(this.tokenInfo, bundle);
                   if (this.tokenCachingStrategy != null) {
                       this.tokenCachingStrategy.save(this.tokenInfo.toCacheBundle());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private Object writeReplace() {
           return new SerializationProxyV1(this.applicationId, this.state, this.tokenInfo, this.lastAttemptedTokenExtendDate, false, this.pendingRequest);
                }

       private void readObject(ObjectInputStream stream) throws InvalidObjectException {
           throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                }

       public static final void saveSession(Session session, Bundle bundle) {
           if (bundle != null && session != null && !bundle.containsKey(SESSION_BUNDLE_SAVE_KEY)) {
               ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        try {
                   new ObjectOutputStream(outputStream).writeObject(session);
                   bundle.putByteArray(SESSION_BUNDLE_SAVE_KEY, outputStream.toByteArray());
                   bundle.putBundle(AUTH_BUNDLE_SAVE_KEY, session.authorizationBundle);
                        } catch (IOException e) {
                   throw new FacebookException("Unable to save session.", e);
                        }
                    }
                }

       public static final Session restoreSession(Context context, TokenCachingStrategy cachingStrategy, StatusCallback callback, Bundle bundle) {
           if (bundle == null) {
                        return null;
                    }
           byte[] data = bundle.getByteArray(SESSION_BUNDLE_SAVE_KEY);
           if (data != null) {
               ByteArrayInputStream is = new ByteArrayInputStream(data);
                        try {
                   Session session = (Session) new ObjectInputStream(is).readObject();
                   initializeStaticContext(context);
                   if (cachingStrategy != null) {
                       session.tokenCachingStrategy = cachingStrategy;
                            } else {
                       session.tokenCachingStrategy = new SharedPreferencesTokenCachingStrategy(context);
                            }
                   if (callback != null) {
                       session.addCallback(callback);
                            }
                   session.authorizationBundle = bundle.getBundle(AUTH_BUNDLE_SAVE_KEY);
                   return session;
                        } catch (IOException e) {
                   Log.w(TAG, "Unable to restore session.", e);
                        } catch (ClassNotFoundException e2) {
                   Log.w(TAG, "Unable to restore session", e2);
                        }
                    }
                    return null;
                }

       public static final Session getActiveSession() {
                    Session session;
                    synchronized (STATIC_LOCK) {
                        try {
                   session = activeSession;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return session;
                }

       public static final void setActiveSession(Session session) {
                    synchronized (STATIC_LOCK) {
                        try {
                   if (session != activeSession) {
                       Session oldSession = activeSession;
                       if (oldSession != null) {
                           oldSession.close();
                                }
                       activeSession = session;
                       if (oldSession != null) {
                           postActiveSessionAction(ACTION_ACTIVE_SESSION_UNSET);
                                }
                       if (session != null) {
                           postActiveSessionAction(ACTION_ACTIVE_SESSION_SET);
                           if (session.isOpened()) {
                               postActiveSessionAction(ACTION_ACTIVE_SESSION_OPENED);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static Session openActiveSessionFromCache(Context context) {
           return openActiveSession(context, false, (OpenRequest) null);
                }

       public static Session openActiveSession(Activity activity, boolean allowLoginUI, StatusCallback callback) {
           return openActiveSession(activity, allowLoginUI, new OpenRequest(activity).setCallback(callback));
                }

       public static Session openActiveSession(Context context, Fragment fragment, boolean allowLoginUI, StatusCallback callback) {
           return openActiveSession(context, allowLoginUI, new OpenRequest(fragment).setCallback(callback));
                }

       public static Session openActiveSessionWithAccessToken(Context context, AccessToken accessToken, StatusCallback callback) {
           Session session = new Session(context, null, null, false);
           setActiveSession(session);
           session.open(accessToken, callback);
           return session;
                }

       private static Session openActiveSession(Context context, boolean allowLoginUI, OpenRequest openRequest) {
           Session session = new Builder(context).build();
           if (!SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) && !allowLoginUI) {
                        return null;
                    }
           setActiveSession(session);
           session.openForRead(openRequest);
           return session;
                }

       static Context getStaticContext() {
           return staticContext;
                }

       static void initializeStaticContext(Context currentContext) {
           if (currentContext != null && staticContext == null) {
               Context applicationContext = currentContext.getApplicationContext();
               if (applicationContext == null) {
                            applicationContext = currentContext;
                        }
                        staticContext = applicationContext;
                    }
                }

       void authorize(AuthorizationRequest request) {
           request.setApplicationId(this.applicationId);
           autoPublishAsync();
           boolean started = tryLoginActivity(request);
           if (!started && AuthorizationRequest.access$100(request)) {
               started = tryLegacyAuth(request);
                    }
           if (!started) {
                        synchronized (this.lock) {
                            try {
                       SessionState oldState = this.state;
                                switch (this.state) {
                                    case CLOSED:
                                    case CLOSED_LOGIN_FAILED:
                                        break;
                                    default:
                               this.state = SessionState.CLOSED_LOGIN_FAILED;
                               postStateChange(oldState, this.state, new FacebookException("Log in attempt failed."));
                                        break;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       private void open(OpenRequest openRequest, SessionAuthorizationType authType) {
                    SessionState newState;
           validatePermissions(openRequest, authType);
           validateLoginBehavior(openRequest);
                    synchronized (this.lock) {
                        try {
                   if (this.pendingRequest != null) {
                       postStateChange(this.state, this.state, new UnsupportedOperationException("Session: an attempt was made to open a session that has a pending request."));
                       return;
                            }
                   SessionState oldState = this.state;
                            switch (this.state) {
                                case CREATED:
                           newState = SessionState.OPENING;
                                    this.state = newState;
                           if (openRequest == null) {
                               throw new IllegalArgumentException("openRequest cannot be null when opening a new Session");
                                    }
                           this.pendingRequest = openRequest;
                                    break;
                                case OPENING:
                                default:
                           throw new UnsupportedOperationException("Session: an attempt was made to open an already opened session.");
                                case CREATED_TOKEN_LOADED:
                           if (openRequest != null && !Utility.isNullOrEmpty(openRequest.getPermissions()) && !Utility.isSubset(openRequest.getPermissions(), getPermissions())) {
                               this.pendingRequest = openRequest;
                                    }
                           if (this.pendingRequest == null) {
                               newState = SessionState.OPENED;
                                        this.state = newState;
                                    } else {
                               newState = SessionState.OPENING;
                                        this.state = newState;
                                    }
                                    break;
                            }
                   if (openRequest != null) {
                       addCallback(openRequest.getCallback());
                            }
                   postStateChange(oldState, newState, null);
                   if (newState == SessionState.OPENING) {
                       authorize(openRequest);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void requestNewPermissions(NewPermissionsRequest newPermissionsRequest, SessionAuthorizationType authType) {
           validatePermissions(newPermissionsRequest, authType);
           validateLoginBehavior(newPermissionsRequest);
           if (newPermissionsRequest != null) {
                        synchronized (this.lock) {
                            try {
                       if (this.pendingRequest != null) {
                           throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that has a pending request.");
                                }
                                switch (this.state) {
                                    case OPENED:
                                    case OPENED_TOKEN_UPDATED:
                               this.pendingRequest = newPermissionsRequest;
                                        break;
                                    default:
                               throw new UnsupportedOperationException("Session: an attempt was made to request new permissions for a session that is not currently open.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               newPermissionsRequest.setValidateSameFbidAsToken(getAccessToken());
               authorize(newPermissionsRequest);
                    }
                }

       private void validateLoginBehavior(AuthorizationRequest request) {
           if (request != null && !AuthorizationRequest.access$100(request)) {
               Intent intent = new Intent();
               intent.setClass(getStaticContext(), LoginActivity.class);
               if (!resolveIntent(intent)) {
                   throw new FacebookException(String.format("Cannot use SessionLoginBehavior %s when %s is not declared as an activity in AndroidManifest.xml", request.getLoginBehavior(), LoginActivity.class.getName()));
                        }
                    }
                }

       private void validatePermissions(AuthorizationRequest request, SessionAuthorizationType authType) {
           if (request == null || Utility.isNullOrEmpty(request.getPermissions())) {
               if (SessionAuthorizationType.PUBLISH.equals(authType)) {
                   throw new FacebookException("Cannot request publish or manage authorization with no permissions.");
                        }
              return;
                    }
                    for (String permission : request.getPermissions()) {
              if (isPublishPermission(permission)) {
                  if (SessionAuthorizationType.READ.equals(authType)) {
                      throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", permission));
                            }
                        } else if (SessionAuthorizationType.PUBLISH.equals(authType)) {
                  Log.w(TAG, String.format("Should not pass a read permission (%s) to a request for publish or manage authorization", permission));
                        }
                    }
                }

      static boolean isPublishPermission(String permission) {
                    return permission != null && (permission.startsWith(PUBLISH_PERMISSION_PREFIX) || permission.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(permission));
                }

      private void handleAuthorizationResult(int resultCode, AuthorizationClient.Result result) {
          AccessToken newToken = null;
          Exception exception = null;
          if (resultCode == -1) {
              if (result.code == AuthorizationClient.Result.Code.SUCCESS) {
                  newToken = result.token;
                        } else {
                  exception = new FacebookAuthorizationException(result.errorMessage);
                        }
                    } else if (resultCode == 0) {
              exception = new FacebookOperationCanceledException(result.errorMessage);
                    }
          this.authorizationClient = null;
          finishAuthOrReauth(newToken, exception);
                }

      private boolean tryLoginActivity(AuthorizationRequest request) {
          Intent intent = getLoginActivityIntent(request);
          if (!resolveIntent(intent)) {
                        return false;
                    }
                    try {
              request.getStartActivityDelegate().startActivityForResult(intent, request.getRequestCode());
              return true;
                    } catch (ActivityNotFoundException e) {
                        return false;
                    }
                }

      private boolean resolveIntent(Intent intent) {
          ResolveInfo resolveInfo = getStaticContext().getPackageManager().resolveActivity(intent, 0);
                    return resolveInfo != null;
                }

      private Intent getLoginActivityIntent(AuthorizationRequest request) {
          Intent intent = new Intent();
          intent.setClass(getStaticContext(), LoginActivity.class);
          intent.setAction(request.getLoginBehavior().toString());
          AuthorizationClient.AuthorizationRequest authClientRequest = request.getAuthorizationClientRequest();
          Bundle extras = LoginActivity.populateIntentExtras(authClientRequest);
          intent.putExtras(extras);
          return intent;
                }

                
      class C07672 implements AuthorizationClient.OnCompletedListener {
          C07672() {
                    }

                    @Override 
          public void onCompleted(AuthorizationClient.Result result) {
                        int activityResult;
              if (result.code == AuthorizationClient.Result.Code.CANCEL) {
                  activityResult = 0;
                        } else {
                  activityResult = -1;
                        }
              Session.access$200(Session.this, activityResult, result);
                    }
                }

      private boolean tryLegacyAuth(AuthorizationRequest request) {
          this.authorizationClient = new AuthorizationClient();
          this.authorizationClient.setOnCompletedListener(new C07672());
          this.authorizationClient.setContext(getStaticContext());
          this.authorizationClient.startOrContinueAuth(request.getAuthorizationClientRequest());
          return true;
                }

      void finishAuthOrReauth(AccessToken newToken, Exception exception) {
          if (newToken != null && newToken.isInvalid()) {
              newToken = null;
              exception = new FacebookException("Invalid access token.");
                    }
                    synchronized (this.lock) {
                        try {
                            switch (this.state) {
                                case OPENING:
                          finishAuthorization(newToken, exception);
                                    break;
                                case OPENED:
                                case OPENED_TOKEN_UPDATED:
                          finishReauthorization(newToken, exception);
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

      private void finishAuthorization(AccessToken newToken, Exception exception) {
          SessionState oldState = this.state;
          if (newToken != null) {
              this.tokenInfo = newToken;
              saveTokenToCache(newToken);
              this.state = SessionState.OPENED;
                    } else if (exception != null) {
              this.state = SessionState.CLOSED_LOGIN_FAILED;
                    }
          this.pendingRequest = null;
          postStateChange(oldState, this.state, exception);
                }

      private void finishReauthorization(AccessToken newToken, Exception exception) {
          SessionState oldState = this.state;
          if (newToken != null) {
              this.tokenInfo = newToken;
              saveTokenToCache(newToken);
              this.state = SessionState.OPENED_TOKEN_UPDATED;
                    }
          this.pendingRequest = null;
          postStateChange(oldState, this.state, exception);
                }

      private void saveTokenToCache(AccessToken newToken) {
          if (newToken != null && this.tokenCachingStrategy != null) {
              this.tokenCachingStrategy.save(newToken.toCacheBundle());
                    }
                }

      void postStateChange(SessionState oldState, SessionState newState, Exception exception) {
          if (oldState != newState || oldState == SessionState.OPENED_TOKEN_UPDATED || exception != null) {
              if (newState.isClosed()) {
                  this.tokenInfo = AccessToken.createEmptyToken(Collections.emptyList());
                        }
                        synchronized (this.callbacks) {
                            try {
                      Runnable runCallbacks = new RunnableC07683(newState, exception);
                      runWithHandlerOrExecutor(this.handler, runCallbacks);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
              if (this == activeSession && oldState.isOpened() != newState.isOpened()) {
                  if (newState.isOpened()) {
                      postActiveSessionAction(ACTION_ACTIVE_SESSION_OPENED);
                            } else {
                      postActiveSessionAction(ACTION_ACTIVE_SESSION_CLOSED);
                            }
                        }
                    }
                }

                
      class RunnableC07683 implements Runnable {
                    final  Exception val$exception;
                    final  SessionState val$newState;

          RunnableC07683(SessionState sessionState, Exception exc) {
                        this.val$newState = sessionState;
                        this.val$exception = exc;
                    }

                    
          class AnonymousClass1 implements Runnable {
                        final  StatusCallback val$callback;

              AnonymousClass1(StatusCallback statusCallback) {
                            this.val$callback = statusCallback;
                        }

                        @Override 
              public void run() {
                  this.val$callback.call(Session.this, RunnableC07683.this.val$newState, RunnableC07683.this.val$exception);
                        }
                    }

                    @Override 
          public void run() {
                        for (StatusCallback callback : Session.access$300(Session.this)) {
                  Runnable closure = new AnonymousClass1(callback);
                  Session.access$500(Session.access$400(Session.this), closure);
                        }
                    }
                }

      static void postActiveSessionAction(String action) {
          Intent intent = new Intent(action);
          LocalBroadcastManager.getInstance(getStaticContext()).sendBroadcast(intent);
                }

      private static void runWithHandlerOrExecutor(Handler handler, Runnable runnable) {
          if (handler != null) {
              handler.post(runnable);
                    } else {
              Settings.getExecutor().execute(runnable);
                    }
                }

      void extendAccessTokenIfNeeded() {
          if (shouldExtendAccessToken()) {
              extendAccessToken();
                    }
                }

      void extendAccessToken() {
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

      boolean shouldExtendAccessToken() {
          if (this.currentTokenRefreshRequest != null) {
              return false;
                    }
          Date now = new Date();
          if (!this.state.isOpened() || !this.tokenInfo.getSource().canExtendToken() || now.getTime() - this.lastAttemptedTokenExtendDate.getTime() <= CommonUtil.HOUR_MILLIS || now.getTime() - this.tokenInfo.getLastRefresh().getTime() <= 86400000) {
              return false;
                    }
          return true;
                }

      AccessToken getTokenInfo() {
          return this.tokenInfo;
                }

      void setTokenInfo(AccessToken tokenInfo) {
          this.tokenInfo = tokenInfo;
                }

      Date getLastAttemptedTokenExtendDate() {
          return this.lastAttemptedTokenExtendDate;
                }

      void setLastAttemptedTokenExtendDate(Date lastAttemptedTokenExtendDate) {
          this.lastAttemptedTokenExtendDate = lastAttemptedTokenExtendDate;
                }

      void setCurrentTokenRefreshRequest(TokenRefreshRequest request) {
          this.currentTokenRefreshRequest = request;
                }

      class TokenRefreshRequest implements ServiceConnection {
                    final Messenger messageReceiver;
                    Messenger messageSender = null;

          TokenRefreshRequest() {
              this.messageReceiver = new Messenger(new TokenRefreshRequestHandler(Session.this, this));
                    }

          void access$800() {
              cleanup();
                    }

          public void bind() {
              Intent intent = NativeProtocol.createTokenRefreshIntent(Session.getStaticContext());
              if (intent != null && Session.access$600().bindService(intent, Session.this.new TokenRefreshRequest(), 1)) {
                  Session.this.setLastAttemptedTokenExtendDate(new Date());
                        } else {
                  cleanup();
                        }
                    }

                    @Override 
          public void onServiceConnected(ComponentName className, IBinder service) {
              this.messageSender = new Messenger(service);
              refreshToken();
                    }

                    @Override 
          public void onServiceDisconnected(ComponentName arg) {
              cleanup();
              Session.access$600().unbindService(this);
                    }

          private void cleanup() {
              if (Session.access$700(Session.this) == this) {
                  Session.access$702(Session.this, null);
                        }
                    }

          private void refreshToken() {
              Bundle requestData = new Bundle();
              requestData.putString("access_token", Session.this.getTokenInfo().getToken());
              Message request = Message.obtain();
              request.setData(requestData);
              request.replyTo = this.messageReceiver;
                        try {
                  this.messageSender.send(request);
                        } catch (RemoteException e) {
                  cleanup();
                        }
                    }
                }

      static class TokenRefreshRequestHandler extends Handler {
                    private WeakReference<TokenRefreshRequest> refreshRequestWeakReference;
                    private WeakReference<Session> sessionWeakReference;

          TokenRefreshRequestHandler(Session session, TokenRefreshRequest refreshRequest) {
              super(Looper.getMainLooper());
              this.sessionWeakReference = new WeakReference<>(session);
              this.refreshRequestWeakReference = new WeakReference<>(refreshRequest);
                    }

                    @Override 
          public void handleMessage(Message msg) {
              String token = msg.getData().getString("access_token");
              Session session = this.sessionWeakReference.get();
              if (session != null && token != null) {
                  session.extendTokenCompleted(msg.getData());
                        }
              TokenRefreshRequest request = this.refreshRequestWeakReference.get();
              if (request != null) {
                  Session.access$600().unbindService(request);
                  request.access$800();
                        }
                    }
                }

      public int hashCode() {
          return 0;
                }

      public boolean equals(Object otherObj) {
          if (!(otherObj instanceof Session)) {
                        return false;
                    }
          Session other = (Session) otherObj;
                    return areEqual(other.applicationId, this.applicationId) && areEqual(other.authorizationBundle, this.authorizationBundle) && areEqual(other.state, this.state) && areEqual(other.getExpirationDate(), getExpirationDate());
                }

      private static boolean areEqual(Object a, Object b) {
          if (a == null) {
                        return b == null;
                    }
          return a.equals(b);
                }

      public static final class Builder {
                    private String applicationId;
                    private final Context context;
                    private TokenCachingStrategy tokenCachingStrategy;

          public Builder(Context context) {
              this.context = context;
                    }

          public Builder setApplicationId(String applicationId) {
              this.applicationId = applicationId;
              return this;
                    }

          public Builder setTokenCachingStrategy(TokenCachingStrategy tokenCachingStrategy) {
              this.tokenCachingStrategy = tokenCachingStrategy;
              return this;
                    }

          public Session build() {
              return new Session(this.context, this.applicationId, this.tokenCachingStrategy);
                    }
                }

      private void autoPublishAsync() {
                    String applicationId;
          AutoPublishAsyncTask asyncTask = null;
                    synchronized (this) {
                        try {
                  if (this.autoPublishAsyncTask == null && Settings.getShouldAutoPublishInstall() && (applicationId = this.applicationId) != null) {
                      AutoPublishAsyncTask asyncTask2 = new AutoPublishAsyncTask(applicationId, staticContext);
                                this.autoPublishAsyncTask = asyncTask2;
                                asyncTask = asyncTask2;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
          if (asyncTask != null) {
              asyncTask.execute(new Void[0]);
                    }
                }

      private class AutoPublishAsyncTask extends AsyncTask<Void, Void, Void> {
                    private final Context mApplicationContext;
                    private final String mApplicationId;



          public AutoPublishAsyncTask(String applicationId, Context context) {
              this.mApplicationId = applicationId;
              this.mApplicationContext = context.getApplicationContext();
                    }

                    
          protected Void doInBackground(Void... voids) {
                        try {
                  Settings.publishInstallAndWait(this.mApplicationContext, this.mApplicationId);
                  return null;
                        } catch (Exception e) {
                  Utility.logd("Facebook-publish", e);
                  return null;
                        }
                    }

                    
          protected void onPostExecute(Void result) {
                        synchronized (Session.this) {
                            try {
                      Session.access$902(Session.this, null);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

      public static class AuthorizationRequest implements Serializable {
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

           AuthorizationRequest(SessionLoginBehavior x0, int x1, List x2, String x3, boolean x4, String x5, String x6, C07661 x7) {
              this(x0, x1, x2, x3, x4, x5, x6);
                    }

          static  boolean access$100(AuthorizationRequest x0) {
              return x0.isLegacy;
                    }

          static  StartActivityDelegate access$1000(AuthorizationRequest x0) {
              return x0.startActivityDelegate;
                    }

                    
          class C07701 implements StartActivityDelegate {
                        final  Activity val$activity;

              C07701(Activity activity) {
                            this.val$activity = activity;
                        }

                        @Override 
              public void startActivityForResult(Intent intent, int requestCode) {
                  this.val$activity.startActivityForResult(intent, requestCode);
                        }

                        @Override 
              public Activity getActivityContext() {
                  return this.val$activity;
                        }
                    }

          AuthorizationRequest(Activity activity) {
              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
              this.isLegacy = false;
              this.permissions = Collections.emptyList();
              this.defaultAudience = SessionDefaultAudience.FRIENDS;
              this.startActivityDelegate = new C07701(activity);
                    }

                    
          class C07712 implements StartActivityDelegate {
                        final  Fragment val$fragment;

              C07712(Fragment fragment) {
                            this.val$fragment = fragment;
                        }

                        @Override 
              public void startActivityForResult(Intent intent, int requestCode) {
                  this.val$fragment.getActivity().startActivityForResult(intent, requestCode);
                        }

                        @Override 
              public Activity getActivityContext() {
                  return this.val$fragment.getActivity();
                        }
                    }

          AuthorizationRequest(Fragment fragment) {
              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
              this.isLegacy = false;
              this.permissions = Collections.emptyList();
              this.defaultAudience = SessionDefaultAudience.FRIENDS;
              this.startActivityDelegate = new C07712(fragment);
                    }

                    
          class C07723 implements StartActivityDelegate {
              C07723() {
                        }

                        @Override 
              public void startActivityForResult(Intent intent, int requestCode) {
                  throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                        }

                        @Override 
              public Activity getActivityContext() {
                  throw new UnsupportedOperationException("Cannot create an AuthorizationRequest without a valid Activity or Fragment");
                        }
                    }

          private AuthorizationRequest(SessionLoginBehavior loginBehavior, int requestCode, List<String> permissions, String defaultAudience, boolean isLegacy, String applicationId, String validateSameFbidAsToken) {
              this.loginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
              this.requestCode = Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE;
              this.isLegacy = false;
              this.permissions = Collections.emptyList();
              this.defaultAudience = SessionDefaultAudience.FRIENDS;
              this.startActivityDelegate = new C07723();
              this.loginBehavior = loginBehavior;
              this.requestCode = requestCode;
              this.permissions = permissions;
              this.defaultAudience = SessionDefaultAudience.valueOf(defaultAudience);
              this.isLegacy = isLegacy;
              this.applicationId = applicationId;
              this.validateSameFbidAsToken = validateSameFbidAsToken;
                    }

          public void setIsLegacy(boolean isLegacy) {
              this.isLegacy = isLegacy;
                    }

          boolean isLegacy() {
              return this.isLegacy;
                    }

          AuthorizationRequest setCallback(StatusCallback statusCallback) {
              this.statusCallback = statusCallback;
              return this;
                    }

          StatusCallback getCallback() {
              return this.statusCallback;
                    }

          AuthorizationRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
              if (loginBehavior != null) {
                  this.loginBehavior = loginBehavior;
                        }
              return this;
                    }

          SessionLoginBehavior getLoginBehavior() {
              return this.loginBehavior;
                    }

          AuthorizationRequest setRequestCode(int requestCode) {
              if (requestCode >= 0) {
                  this.requestCode = requestCode;
                        }
              return this;
                    }

          int getRequestCode() {
              return this.requestCode;
                    }

          AuthorizationRequest setPermissions(List<String> permissions) {
              if (permissions != null) {
                  this.permissions = permissions;
                        }
              return this;
                    }

          List<String> getPermissions() {
              return this.permissions;
                    }

          AuthorizationRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
              if (defaultAudience != null) {
                  this.defaultAudience = defaultAudience;
                        }
              return this;
                    }

          SessionDefaultAudience getDefaultAudience() {
              return this.defaultAudience;
                    }

          StartActivityDelegate getStartActivityDelegate() {
              return this.startActivityDelegate;
                    }

          String getApplicationId() {
              return this.applicationId;
                    }

          void setApplicationId(String applicationId) {
              this.applicationId = applicationId;
                    }

          String getValidateSameFbidAsToken() {
              return this.validateSameFbidAsToken;
                    }

          void setValidateSameFbidAsToken(String validateSameFbidAsToken) {
              this.validateSameFbidAsToken = validateSameFbidAsToken;
                    }

                    
          class C07734 implements AuthorizationClient.StartActivityDelegate {
              C07734() {
                        }

                        @Override 
              public void startActivityForResult(Intent intent, int requestCode) {
                  AuthorizationRequest.access$1000(AuthorizationRequest.this).startActivityForResult(intent, requestCode);
                        }

                        @Override 
              public Activity getActivityContext() {
                  return AuthorizationRequest.access$1000(AuthorizationRequest.this).getActivityContext();
                        }
                    }

          AuthorizationClient.AuthorizationRequest getAuthorizationClientRequest() {
              AuthorizationClient.StartActivityDelegate delegate = new C07734();
              return new AuthorizationClient.AuthorizationRequest(this.loginBehavior, this.requestCode, this.isLegacy, this.permissions, this.defaultAudience, this.applicationId, this.validateSameFbidAsToken, delegate);
                    }

          Object writeReplace() {
              return new AuthRequestSerializationProxyV1(this.loginBehavior, this.requestCode, this.permissions, this.defaultAudience.name(), this.isLegacy, this.applicationId, this.validateSameFbidAsToken, null);
                    }

          void readObject(ObjectInputStream stream) throws InvalidObjectException {
              throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                    }

          private static class AuthRequestSerializationProxyV1 implements Serializable {
                        private static final long serialVersionUID = -8748347685113614927L;
                        private final String applicationId;
                        private final String defaultAudience;
                        private boolean isLegacy;
                        private final SessionLoginBehavior loginBehavior;
                        private final List<String> permissions;
                        private final int requestCode;
                        private final String validateSameFbidAsToken;

               AuthRequestSerializationProxyV1(SessionLoginBehavior x0, int x1, List x2, String x3, boolean x4, String x5, String x6, C07661 x7) {
                  this(x0, x1, x2, x3, x4, x5, x6);
                        }

              private AuthRequestSerializationProxyV1(SessionLoginBehavior loginBehavior, int requestCode, List<String> permissions, String defaultAudience, boolean isLegacy, String applicationId, String validateSameFbidAsToken) {
                  this.loginBehavior = loginBehavior;
                  this.requestCode = requestCode;
                  this.permissions = permissions;
                  this.defaultAudience = defaultAudience;
                  this.isLegacy = isLegacy;
                  this.applicationId = applicationId;
                  this.validateSameFbidAsToken = validateSameFbidAsToken;
                        }

              private Object readResolve() {
                  return new AuthorizationRequest(this.loginBehavior, this.requestCode, this.permissions, this.defaultAudience, this.isLegacy, this.applicationId, this.validateSameFbidAsToken, null);
                        }
                    }
                }

      public static final class OpenRequest extends AuthorizationRequest {
                    private static final long serialVersionUID = 1;






          public OpenRequest(Activity activity) {
              super(activity);
                    }

          public OpenRequest(Fragment fragment) {
              super(fragment);
                    }

                    @Override 
          public final OpenRequest setCallback(StatusCallback statusCallback) {
              super.setCallback(statusCallback);
              return this;
                    }

                    @Override 
          public final OpenRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
              super.setLoginBehavior(loginBehavior);
              return this;
                    }

                    @Override 
          public final OpenRequest setRequestCode(int requestCode) {
              super.setRequestCode(requestCode);
              return this;
                    }

                    @Override 
          public final OpenRequest setPermissions(List<String> permissions) {
              super.setPermissions(permissions);
              return this;
                    }

                    @Override 
          public final OpenRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
              super.setDefaultAudience(defaultAudience);
              return this;
                    }
                }

      public static final class NewPermissionsRequest extends AuthorizationRequest {
                    private static final long serialVersionUID = 1;





          public NewPermissionsRequest(Activity activity, List<String> permissions) {
              super(activity);
              setPermissions(permissions);
                    }

          public NewPermissionsRequest(Fragment fragment, List<String> permissions) {
              super(fragment);
              setPermissions(permissions);
                    }

                    @Override 
          public final NewPermissionsRequest setCallback(StatusCallback statusCallback) {
              super.setCallback(statusCallback);
              return this;
                    }

                    @Override 
          public final NewPermissionsRequest setLoginBehavior(SessionLoginBehavior loginBehavior) {
              super.setLoginBehavior(loginBehavior);
              return this;
                    }

                    @Override 
          public final NewPermissionsRequest setRequestCode(int requestCode) {
              super.setRequestCode(requestCode);
              return this;
                    }

                    @Override 
          public final NewPermissionsRequest setDefaultAudience(SessionDefaultAudience defaultAudience) {
              super.setDefaultAudience(defaultAudience);
              return this;
                    }
                }
            }
