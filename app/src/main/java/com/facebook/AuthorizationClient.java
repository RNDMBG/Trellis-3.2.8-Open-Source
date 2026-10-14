            package com.facebook;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.webkit.CookieSyncManager;
            import co.vine.android.client.VineAPI;
            import com.facebook.internal.ServerProtocol;
            import com.facebook.internal.Utility;
            import com.facebook.model.GraphMultiResult;
            import com.facebook.model.GraphObject;
            import com.facebook.model.GraphObjectList;
            import com.facebook.model.GraphUser;
            import com.facebook.widget.WebDialog;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class AuthorizationClient implements Serializable {
                public static boolean CLEAR_COOKIE = false;
                private static final String TAG = "Facebook-AuthorizationClient";
                private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
                private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
                private static final long serialVersionUID = 1;
                transient BackgroundProcessingListener backgroundProcessingListener;
                transient boolean checkedInternetPermission;
                transient Context context;
                AuthHandler currentHandler;
                List<AuthHandler> handlersToTry;
                transient OnCompletedListener onCompletedListener;
                AuthorizationRequest pendingRequest;
                transient StartActivityDelegate startActivityDelegate;

                interface BackgroundProcessingListener {
                    void onBackgroundProcessingStarted();

                    void onBackgroundProcessingStopped();
                }

                interface OnCompletedListener {
                    void onCompleted(Result result);
                }

                interface StartActivityDelegate {
                    Activity getActivityContext();

                    void startActivityForResult(Intent intent, int i);
                }

/* 38 */        static /* synthetic */ void access$000(AuthorizationClient x0) {
/* 39 */            x0.notifyBackgroundProcessingStop();
                }

/* 38 */        static /* synthetic */ void access$100(AuthorizationClient x0) {
/* 39 */            x0.notifyBackgroundProcessingStart();
                }

/* 71 */        void setContext(Context context) {
/* 72 */            this.context = context;
/* 74 */            this.startActivityDelegate = null;
                }

/* 77 */        void setContext(Activity activity) {
/* 78 */            this.context = activity;
/* 82 */            this.startActivityDelegate = new C07501(activity);
                }

                /* JADX INFO: renamed from: com.facebook.AuthorizationClient$1 */
/* 80 */        class C07501 implements StartActivityDelegate {
                    final /* synthetic */ Activity val$activity;

/* 81 */            C07501(Activity activity) {
                        this.val$activity = activity;
                    }

                    @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 84 */            public void startActivityForResult(Intent intent, int requestCode) {
/* 85 */                this.val$activity.startActivityForResult(intent, requestCode);
                    }

                    @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 89 */            public Activity getActivityContext() {
/* 90 */                return this.val$activity;
                    }
                }

/* 95 */        void startOrContinueAuth(AuthorizationRequest request) {
/* 96 */            if (getInProgress()) {
/* 97 */                continueAuth();
                    } else {
/* 99 */                authorize(request);
                    }
                }

/* 103 */       void authorize(AuthorizationRequest request) {
/* 104 */           if (request != null) {
/* 108 */               if (this.pendingRequest != null) {
/* 109 */                   throw new FacebookException("Attempted to authorize while a request is pending.");
                        }
/* 112 */               if (!request.needsNewTokenValidation()) {
/* 116 */                   this.pendingRequest = request;
/* 117 */                   this.handlersToTry = getHandlerTypes(request);
/* 118 */                   tryNextHandler();
                        }
                    }
                }

/* 121 */       void continueAuth() {
/* 122 */           if (this.pendingRequest == null || this.currentHandler == null) {
/* 123 */               throw new FacebookException("Attempted to continue authorization without a pending request.");
                    }
/* 126 */           if (this.currentHandler.needsRestart()) {
/* 127 */               this.currentHandler.cancel();
/* 128 */               tryCurrentHandler();
                    }
                }

/* 132 */       boolean getInProgress() {
                    return (this.pendingRequest == null || this.currentHandler == null) ? false : true;
                }

/* 136 */       void cancelCurrentHandler() {
/* 137 */           if (this.currentHandler != null) {
/* 138 */               this.currentHandler.cancel();
                    }
                }

/* 142 */       boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                    return requestCode == this.pendingRequest.getRequestCode() && this.currentHandler.onActivityResult(requestCode, resultCode, data);
                }

/* 147 */       private List<AuthHandler> getHandlerTypes(AuthorizationRequest request) {
/* 148 */           ArrayList<AuthHandler> handlers = new ArrayList<>();
/* 150 */           SessionLoginBehavior behavior = request.getLoginBehavior();
/* 151 */           if (behavior.allowsKatanaAuth()) {
/* 152 */               if (!request.isLegacy()) {
/* 153 */                   handlers.add(new GetTokenAuthHandler());
/* 154 */                   handlers.add(new KatanaLoginDialogAuthHandler());
                        }
/* 156 */               handlers.add(new KatanaProxyAuthHandler());
                    }
/* 159 */           if (behavior.allowsWebViewAuth()) {
/* 160 */               handlers.add(new WebViewAuthHandler());
                    }
/* 163 */           return handlers;
                }

/* 166 */       void tryNextHandler() {
/* 167 */           while (this.handlersToTry != null && !this.handlersToTry.isEmpty()) {
/* 168 */               this.currentHandler = this.handlersToTry.remove(0);
/* 170 */               boolean started = tryCurrentHandler();
/* 172 */               if (started) {
/* 181 */                   return;
                        }
                    }
/* 177 */           if (this.pendingRequest != null) {
/* 179 */               completeWithFailure();
                    }
                }

/* 183 */       private void completeWithFailure() {
/* 184 */           complete(Result.createErrorResult("Login attempt failed.", null));
                }

/* 187 */       boolean tryCurrentHandler() {
/* 188 */           return this.currentHandler.tryAuthorize(this.pendingRequest);
                }

/* 192 */       void completeAndValidate(Result outcome) {
/* 193 */           if (outcome.token != null && this.pendingRequest.needsNewTokenValidation()) {
/* 194 */               validateSameFbidAndFinish(outcome);
                    } else {
/* 197 */               complete(outcome);
                    }
                }

/* 201 */       void complete(Result outcome) {
/* 202 */           this.handlersToTry = null;
/* 203 */           this.currentHandler = null;
/* 204 */           this.pendingRequest = null;
/* 206 */           notifyOnCompleteListener(outcome);
                }

/* 209 */       OnCompletedListener getOnCompletedListener() {
/* 210 */           return this.onCompletedListener;
                }

/* 213 */       void setOnCompletedListener(OnCompletedListener onCompletedListener) {
/* 214 */           this.onCompletedListener = onCompletedListener;
                }

/* 217 */       BackgroundProcessingListener getBackgroundProcessingListener() {
/* 218 */           return this.backgroundProcessingListener;
                }

/* 221 */       void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener) {
/* 222 */           this.backgroundProcessingListener = backgroundProcessingListener;
                }

/* 225 */       StartActivityDelegate getStartActivityDelegate() {
/* 226 */           if (this.startActivityDelegate != null) {
/* 227 */               return this.startActivityDelegate;
                    }
/* 228 */           if (this.pendingRequest != null) {
/* 230 */               return new C07512();
                    }
/* 242 */           return null;
                }

                /* JADX INFO: renamed from: com.facebook.AuthorizationClient$2 */
/* 228 */       class C07512 implements StartActivityDelegate {
/* 229 */           C07512() {
                    }

                    @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 232 */           public void startActivityForResult(Intent intent, int requestCode) {
/* 233 */               AuthorizationClient.this.pendingRequest.getStartActivityDelegate().startActivityForResult(intent, requestCode);
                    }

                    @Override // com.facebook.AuthorizationClient.StartActivityDelegate
/* 237 */           public Activity getActivityContext() {
/* 238 */               return AuthorizationClient.this.pendingRequest.getStartActivityDelegate().getActivityContext();
                    }
                }

/* 245 */       int checkPermission(String permission) {
/* 246 */           return this.context.checkCallingOrSelfPermission(permission);
                }

/* 249 */       void validateSameFbidAndFinish(Result pendingResult) {
/* 250 */           if (pendingResult.token == null) {
/* 251 */               throw new FacebookException("Can't validate without a token");
                    }
/* 254 */           RequestBatch batch = createReauthValidationBatch(pendingResult);
/* 256 */           notifyBackgroundProcessingStart();
/* 258 */           batch.executeAsync();
                }

/* 265 */       RequestBatch createReauthValidationBatch(Result pendingResult) {
/* 266 */           ArrayList<String> fbids = new ArrayList<>();
/* 267 */           ArrayList<String> tokenPermissions = new ArrayList<>();
/* 268 */           String newToken = pendingResult.token.getToken();
/* 270 */           Request.Callback meCallback = new C07523(fbids);
/* 283 */           String validateSameFbidAsToken = this.pendingRequest.getPreviousAccessToken();
/* 284 */           Request requestCurrentTokenMe = createGetProfileIdRequest(validateSameFbidAsToken);
/* 285 */           requestCurrentTokenMe.setCallback(meCallback);
/* 287 */           Request requestNewTokenMe = createGetProfileIdRequest(newToken);
/* 288 */           requestNewTokenMe.setCallback(meCallback);
/* 290 */           Request requestCurrentTokenPermissions = createGetPermissionsRequest(validateSameFbidAsToken);
/* 291 */           requestCurrentTokenPermissions.setCallback(new C07534(tokenPermissions));
/* 310 */           RequestBatch batch = new RequestBatch(requestCurrentTokenMe, requestNewTokenMe, requestCurrentTokenPermissions);
/* 312 */           batch.setBatchApplicationId(this.pendingRequest.getApplicationId());
/* 313 */           batch.addCallback(new C07545(fbids, pendingResult, tokenPermissions));
/* 338 */           return batch;
                }

                /* JADX INFO: renamed from: com.facebook.AuthorizationClient$3 */
/* 268 */       class C07523 implements Request.Callback {
                    final /* synthetic */ ArrayList val$fbids;

/* 269 */           C07523(ArrayList arrayList) {
                        this.val$fbids = arrayList;
                    }

                    @Override // com.facebook.Request.Callback
/* 273 */           public void onCompleted(Response response) {
                        try {
/* 274 */                   GraphUser user = (GraphUser) response.getGraphObjectAs(GraphUser.class);
/* 275 */                   if (user != null) {
/* 276 */                       this.val$fbids.add(user.getId());
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                /* JADX INFO: renamed from: com.facebook.AuthorizationClient$4 */
/* 289 */       class C07534 implements Request.Callback {
                    final /* synthetic */ ArrayList val$tokenPermissions;

/* 290 */           C07534(ArrayList arrayList) {
                        this.val$tokenPermissions = arrayList;
                    }

                    @Override // com.facebook.Request.Callback
/* 294 */           public void onCompleted(Response response) {
                        GraphObjectList<GraphObject> data;
                        try {
/* 295 */                   GraphMultiResult result = (GraphMultiResult) response.getGraphObjectAs(GraphMultiResult.class);
/* 296 */                   if (result != null && (data = result.getData()) != null && data.size() == 1) {
/* 299 */                       GraphObject permissions = data.get(0);
/* 302 */                       this.val$tokenPermissions.addAll(permissions.asMap().keySet());
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                /* JADX INFO: renamed from: com.facebook.AuthorizationClient$5 */
/* 311 */       class C07545 implements RequestBatch.Callback {
                    final /* synthetic */ ArrayList val$fbids;
                    final /* synthetic */ Result val$pendingResult;
                    final /* synthetic */ ArrayList val$tokenPermissions;

/* 312 */           C07545(ArrayList arrayList, Result result, ArrayList arrayList2) {
                        this.val$fbids = arrayList;
                        this.val$pendingResult = result;
                        this.val$tokenPermissions = arrayList2;
                    }

                    @Override // com.facebook.RequestBatch.Callback
/* 316 */           public void onBatchCompleted(RequestBatch batch) {
                        Result result;
                        try {
                            try {
/* 318 */                       if (this.val$fbids.size() == 2 && this.val$fbids.get(0) != null && this.val$fbids.get(1) != null && ((String) this.val$fbids.get(0)).equals(this.val$fbids.get(1))) {
/* 321 */                           AccessToken tokenWithPermissions = AccessToken.createFromTokenWithRefreshedPermissions(this.val$pendingResult.token, this.val$tokenPermissions);
/* 324 */                           result = Result.createTokenResult(tokenWithPermissions);
                                } else {
/* 326 */                           result = Result.createErrorResult("User logged in as different Facebook user.", null);
                                }
/* 329 */                       AuthorizationClient.this.complete(result);
/* 333 */                       AuthorizationClient.access$000(AuthorizationClient.this);
                            } catch (Exception ex) {
/* 331 */                       AuthorizationClient.this.complete(Result.createErrorResult("Caught exception", ex.getMessage()));
/* 333 */                       AuthorizationClient.access$000(AuthorizationClient.this);
                            }
                        } catch (Throwable th) {
                            AuthorizationClient.access$000(AuthorizationClient.this);
                            throw th;
                        }
                    }
                }

/* 341 */       Request createGetPermissionsRequest(String accessToken) {
/* 342 */           Bundle parameters = new Bundle();
/* 343 */           parameters.putString("fields", "id");
/* 344 */           parameters.putString("access_token", accessToken);
/* 345 */           return new Request(null, "me/permissions", parameters, HttpMethod.GET, null);
                }

/* 348 */       Request createGetProfileIdRequest(String accessToken) {
/* 349 */           Bundle parameters = new Bundle();
/* 350 */           parameters.putString("fields", "id");
/* 351 */           parameters.putString("access_token", accessToken);
/* 352 */           return new Request(null, VineAPI.RESOURCE_ME, parameters, HttpMethod.GET, null);
                }

/* 355 */       private void notifyOnCompleteListener(Result outcome) {
/* 356 */           if (this.onCompletedListener != null) {
/* 357 */               this.onCompletedListener.onCompleted(outcome);
                    }
                }

/* 361 */       private void notifyBackgroundProcessingStart() {
/* 362 */           if (this.backgroundProcessingListener != null) {
/* 363 */               this.backgroundProcessingListener.onBackgroundProcessingStarted();
                    }
                }

/* 367 */       private void notifyBackgroundProcessingStop() {
/* 368 */           if (this.backgroundProcessingListener != null) {
/* 369 */               this.backgroundProcessingListener.onBackgroundProcessingStopped();
                    }
                }

/* 371 */       abstract class AuthHandler implements Serializable {
                    private static final long serialVersionUID = 1;

                    abstract boolean tryAuthorize(AuthorizationRequest authorizationRequest);

/* 372 */           AuthHandler() {
                    }

/* 378 */           boolean onActivityResult(int requestCode, int resultCode, Intent data) {
/* 379 */               return false;
                    }

/* 382 */           boolean needsRestart() {
/* 383 */               return false;
                    }

/* 386 */           void cancel() {
                    }
                }

/* 390 */       class WebViewAuthHandler extends AuthHandler {
                    private static final long serialVersionUID = 1;
                    private transient WebDialog loginDialog;

/* 391 */           WebViewAuthHandler() {
                        super();
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 399 */           boolean needsRestart() {
/* 400 */               return true;
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 404 */           void cancel() {
/* 405 */               if (this.loginDialog != null) {
/* 406 */                   this.loginDialog.dismiss();
/* 407 */                   this.loginDialog = null;
                        }
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 412 */           boolean tryAuthorize(AuthorizationRequest request) {
/* 413 */               String applicationId = request.getApplicationId();
/* 414 */               Bundle parameters = new Bundle();
/* 415 */               if (!Utility.isNullOrEmpty(request.getPermissions())) {
/* 416 */                   parameters.putString("scope", TextUtils.join(",", request.getPermissions()));
                        }
/* 419 */               String previousToken = request.getPreviousAccessToken();
/* 420 */               if (!Utility.isNullOrEmpty(previousToken) && previousToken.equals(loadCookieToken())) {
/* 421 */                   parameters.putString("access_token", previousToken);
                        } else if (AuthorizationClient.CLEAR_COOKIE) {
/* 425 */                   Utility.clearFacebookCookies(AuthorizationClient.this.context);
/* 426 */                   AuthorizationClient.CLEAR_COOKIE = false;
                        } else {
/* 428 */                   CookieSyncManager syncManager = CookieSyncManager.createInstance(AuthorizationClient.this.context);
/* 429 */                   syncManager.sync();
                        }
/* 433 */               WebDialog.OnCompleteListener listener = new C07561(request);
/* 440 */               WebDialog.Builder builder = new AuthDialogBuilder(AuthorizationClient.this.getStartActivityDelegate().getActivityContext(), applicationId, parameters).setOnCompleteListener(listener);
/* 443 */               this.loginDialog = builder.build();
/* 444 */               this.loginDialog.show();
/* 446 */               return true;
                    }

                    /* JADX INFO: renamed from: com.facebook.AuthorizationClient$WebViewAuthHandler$1 */
/* 431 */           class C07561 implements WebDialog.OnCompleteListener {
                        final /* synthetic */ AuthorizationRequest val$request;

/* 432 */               C07561(AuthorizationRequest authorizationRequest) {
                            this.val$request = authorizationRequest;
                        }

                        @Override // com.facebook.widget.WebDialog.OnCompleteListener
/* 435 */               public void onComplete(Bundle values, FacebookException error) {
/* 436 */                   WebViewAuthHandler.this.onWebDialogComplete(this.val$request, values, error);
                        }
                    }

/* 451 */           void onWebDialogComplete(AuthorizationRequest request, Bundle values, FacebookException error) {
                        Result outcome;
/* 452 */               if (values != null) {
/* 453 */                   AccessToken token = AccessToken.createFromWebBundle(request.getPermissions(), values, AccessTokenSource.WEB_VIEW);
/* 455 */                   outcome = Result.createTokenResult(token);
/* 460 */                   CookieSyncManager syncManager = CookieSyncManager.createInstance(AuthorizationClient.this.context);
/* 461 */                   syncManager.sync();
/* 462 */                   saveCookieToken(token.getToken());
                        } else if (error instanceof FacebookOperationCanceledException) {
/* 465 */                   outcome = Result.createCancelResult("User canceled log in.");
                        } else {
/* 467 */                   outcome = Result.createErrorResult(error.getMessage(), null);
                        }
/* 470 */               AuthorizationClient.this.completeAndValidate(outcome);
                    }

/* 473 */           private void saveCookieToken(String token) {
/* 474 */               Context context = AuthorizationClient.this.getStartActivityDelegate().getActivityContext();
/* 475 */               SharedPreferences sharedPreferences = context.getSharedPreferences(AuthorizationClient.WEB_VIEW_AUTH_HANDLER_STORE, 0);
/* 478 */               SharedPreferences.Editor editor = sharedPreferences.edit();
/* 479 */               editor.putString(AuthorizationClient.WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, token);
/* 480 */               if (!editor.commit()) {
/* 481 */                   Utility.logd(AuthorizationClient.TAG, "Could not update saved web view auth handler token.");
                        }
                    }

/* 485 */           private String loadCookieToken() {
/* 486 */               Context context = AuthorizationClient.this.getStartActivityDelegate().getActivityContext();
/* 487 */               SharedPreferences sharedPreferences = context.getSharedPreferences(AuthorizationClient.WEB_VIEW_AUTH_HANDLER_STORE, 0);
/* 490 */               return sharedPreferences.getString(AuthorizationClient.WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, "");
                    }
                }

/* 492 */       class GetTokenAuthHandler extends AuthHandler {
                    private static final long serialVersionUID = 1;
                    private transient GetTokenClient getTokenClient;

/* 493 */           GetTokenAuthHandler() {
                        super();
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 499 */           void cancel() {
/* 500 */               if (this.getTokenClient != null) {
/* 501 */                   this.getTokenClient.cancel();
/* 502 */                   this.getTokenClient = null;
                        }
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 506 */           boolean tryAuthorize(AuthorizationRequest request) {
/* 507 */               this.getTokenClient = new GetTokenClient(AuthorizationClient.this.context, request.getApplicationId());
/* 508 */               if (!this.getTokenClient.start()) {
/* 509 */                   return false;
                        }
/* 512 */               AuthorizationClient.access$100(AuthorizationClient.this);
/* 514 */               GetTokenClient.CompletedListener callback = new C07551(request);
/* 521 */               this.getTokenClient.setCompletedListener(callback);
/* 522 */               return true;
                    }

                    /* JADX INFO: renamed from: com.facebook.AuthorizationClient$GetTokenAuthHandler$1 */
/* 512 */           class C07551 implements GetTokenClient.CompletedListener {
                        final /* synthetic */ AuthorizationRequest val$request;

/* 513 */               C07551(AuthorizationRequest authorizationRequest) {
                            this.val$request = authorizationRequest;
                        }

                        @Override // com.facebook.GetTokenClient.CompletedListener
/* 516 */               public void completed(Bundle result) {
/* 517 */                   GetTokenAuthHandler.this.getTokenCompleted(this.val$request, result);
                        }
                    }

/* 525 */           void getTokenCompleted(AuthorizationRequest request, Bundle result) {
/* 526 */               this.getTokenClient = null;
/* 528 */               AuthorizationClient.access$000(AuthorizationClient.this);
/* 530 */               if (result != null) {
/* 531 */                   ArrayList<String> currentPermissions = result.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
/* 532 */                   List<String> permissions = request.getPermissions();
/* 533 */                   if (currentPermissions != null && (permissions == null || currentPermissions.containsAll(permissions))) {
/* 536 */                       AccessToken token = AccessToken.createFromNativeLogin(result, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE);
/* 538 */                       Result outcome = Result.createTokenResult(token);
/* 539 */                       AuthorizationClient.this.completeAndValidate(outcome);
/* 555 */                       return;
                            } else {
/* 545 */                       ArrayList<String> newPermissions = new ArrayList<>();
                                for (String permission : permissions) {
/* 547 */                           if (!currentPermissions.contains(permission)) {
/* 548 */                               newPermissions.add(permission);
                                    }
                                }
/* 551 */                       request.setPermissions(newPermissions);
                            }
                        }
/* 554 */               AuthorizationClient.this.tryNextHandler();
                    }
                }

/* 556 */       abstract class KatanaAuthHandler extends AuthHandler {
                    private static final long serialVersionUID = 1;

/* 557 */           KatanaAuthHandler() {
                        super();
                    }

/* 561 */           protected boolean tryIntent(Intent intent, int requestCode) {
/* 562 */               if (intent == null) {
                            return false;
                        }
                        try {
/* 567 */                   AuthorizationClient.this.getStartActivityDelegate().startActivityForResult(intent, requestCode);
/* 572 */                   return true;
                        } catch (ActivityNotFoundException e) {
                            return false;
                        }
                    }
                }

/* 574 */       class KatanaLoginDialogAuthHandler extends KatanaAuthHandler {
                    private static final long serialVersionUID = 1;

/* 575 */           KatanaLoginDialogAuthHandler() {
                        super();
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 580 */           boolean tryAuthorize(AuthorizationRequest request) {
/* 581 */               Intent intent = NativeProtocol.createLoginDialog20121101Intent(AuthorizationClient.this.context, request.getApplicationId(), new ArrayList(request.getPermissions()), request.getDefaultAudience().getNativeProtocolAudience());
/* 585 */               if (NativeProtocol.validateKatanaServiceIntent(AuthorizationClient.this.context, intent) == null) {
/* 586 */                   return false;
                        }
                        return tryIntent(intent, request.getRequestCode());
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 595 */           boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                        Result outcome;
/* 596 */               if (data == null) {
/* 598 */                   outcome = Result.createCancelResult("Operation canceled");
                        } else if (NativeProtocol.isServiceDisabledResult20121101(data)) {
/* 600 */                   outcome = null;
                        } else if (resultCode == 0) {
/* 602 */                   outcome = Result.createCancelResult(data.getStringExtra("com.facebook.platform.status.ERROR_DESCRIPTION"));
                        } else if (resultCode != -1) {
/* 605 */                   outcome = Result.createErrorResult("Unexpected resultCode from authorization.", null);
                        } else {
/* 607 */                   outcome = handleResultOk(data);
                        }
/* 610 */               if (outcome != null) {
/* 611 */                   AuthorizationClient.this.completeAndValidate(outcome);
/* 616 */                   return true;
                        }
/* 613 */               AuthorizationClient.this.tryNextHandler();
/* 616 */               return true;
                    }

/* 619 */           private Result handleResultOk(Intent data) {
/* 620 */               Bundle extras = data.getExtras();
/* 621 */               String errorType = extras.getString("com.facebook.platform.status.ERROR_TYPE");
/* 622 */               if (errorType == null) {
/* 623 */                   return Result.createTokenResult(AccessToken.createFromNativeLogin(extras, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE));
                        }
/* 625 */               if ("ServiceDisabled".equals(errorType)) {
                            return null;
                        }
/* 627 */               if ("UserCanceled".equals(errorType)) {
/* 628 */                   return Result.createCancelResult(null);
                        }
/* 630 */               return Result.createErrorResult(errorType, extras.getString("error_description"));
                    }
                }

/* 633 */       class KatanaProxyAuthHandler extends KatanaAuthHandler {
                    private static final long serialVersionUID = 1;

/* 634 */           KatanaProxyAuthHandler() {
                        super();
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 639 */           boolean tryAuthorize(AuthorizationRequest request) {
/* 640 */               Intent intent = NativeProtocol.createProxyAuthIntent(AuthorizationClient.this.context, request.getApplicationId(), request.getPermissions());
/* 643 */               return tryIntent(intent, request.getRequestCode());
                    }

                    @Override // com.facebook.AuthorizationClient.AuthHandler
/* 650 */           boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                        Result outcome;
/* 651 */               if (data == null) {
/* 653 */                   outcome = Result.createCancelResult("Operation canceled");
                        } else if (resultCode == 0) {
/* 655 */                   outcome = Result.createCancelResult(data.getStringExtra("error"));
                        } else if (resultCode != -1) {
/* 657 */                   outcome = Result.createErrorResult("Unexpected resultCode from authorization.", null);
                        } else {
/* 659 */                   outcome = handleResultOk(data);
                        }
/* 662 */               if (outcome != null) {
/* 663 */                   AuthorizationClient.this.completeAndValidate(outcome);
/* 667 */                   return true;
                        }
/* 665 */               AuthorizationClient.this.tryNextHandler();
/* 667 */               return true;
                    }

/* 670 */           private Result handleResultOk(Intent data) {
/* 671 */               Bundle extras = data.getExtras();
/* 672 */               String error = extras.getString("error");
/* 673 */               if (error == null) {
/* 674 */                   error = extras.getString("error_type");
                        }
/* 677 */               if (error == null) {
/* 678 */                   AccessToken token = AccessToken.createFromWebBundle(AuthorizationClient.this.pendingRequest.getPermissions(), extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB);
/* 680 */                   return Result.createTokenResult(token);
                        }
/* 681 */               if (ServerProtocol.errorsProxyAuthDisabled.contains(error)) {
                            return null;
                        }
/* 683 */               if (ServerProtocol.errorsUserCanceled.contains(error)) {
/* 684 */                   return Result.createCancelResult(null);
                        }
/* 686 */               return Result.createErrorResult(error, extras.getString("error_description"));
                    }
                }

/* 694 */       static class AuthDialogBuilder extends WebDialog.Builder {
                    private static final String OAUTH_DIALOG = "oauth";
                    static final String REDIRECT_URI = "fbconnect://success";

/* 695 */           public AuthDialogBuilder(Context context, String applicationId, Bundle parameters) {
/* 696 */               super(context, applicationId, OAUTH_DIALOG, parameters);
                    }

                    @Override // com.facebook.widget.WebDialog.Builder, com.facebook.widget.WebDialog.BuilderBase
/* 700 */           public WebDialog build() {
/* 701 */               Bundle parameters = getParameters();
/* 702 */               parameters.putString("redirect_uri", REDIRECT_URI);
/* 703 */               parameters.putString("client_id", getApplicationId());
/* 705 */               return new WebDialog(getContext(), OAUTH_DIALOG, parameters, getTheme(), getListener());
                    }
                }

/* 721 */       static class AuthorizationRequest implements Serializable {
                    private static final long serialVersionUID = 1;
                    private String applicationId;
                    private SessionDefaultAudience defaultAudience;
                    private boolean isLegacy;
                    private SessionLoginBehavior loginBehavior;
                    private List<String> permissions;
                    private String previousAccessToken;
                    private int requestCode;
                    private final transient StartActivityDelegate startActivityDelegate;

/* 722 */           AuthorizationRequest(SessionLoginBehavior loginBehavior, int requestCode, boolean isLegacy, List<String> permissions, SessionDefaultAudience defaultAudience, String applicationId, String validateSameFbidAsToken, StartActivityDelegate startActivityDelegate) {
/* 715 */               this.isLegacy = false;
/* 724 */               this.loginBehavior = loginBehavior;
/* 725 */               this.requestCode = requestCode;
/* 726 */               this.isLegacy = isLegacy;
/* 727 */               this.permissions = permissions;
/* 728 */               this.defaultAudience = defaultAudience;
/* 729 */               this.applicationId = applicationId;
/* 730 */               this.previousAccessToken = validateSameFbidAsToken;
/* 731 */               this.startActivityDelegate = startActivityDelegate;
                    }

/* 735 */           StartActivityDelegate getStartActivityDelegate() {
/* 736 */               return this.startActivityDelegate;
                    }

/* 739 */           List<String> getPermissions() {
/* 740 */               return this.permissions;
                    }

/* 743 */           void setPermissions(List<String> permissions) {
/* 744 */               this.permissions = permissions;
                    }

/* 747 */           SessionLoginBehavior getLoginBehavior() {
/* 748 */               return this.loginBehavior;
                    }

/* 751 */           int getRequestCode() {
/* 752 */               return this.requestCode;
                    }

/* 755 */           SessionDefaultAudience getDefaultAudience() {
/* 756 */               return this.defaultAudience;
                    }

/* 759 */           String getApplicationId() {
/* 760 */               return this.applicationId;
                    }

/* 763 */           boolean isLegacy() {
/* 764 */               return this.isLegacy;
                    }

/* 767 */           void setIsLegacy(boolean isLegacy) {
/* 768 */               this.isLegacy = isLegacy;
                    }

/* 771 */           String getPreviousAccessToken() {
/* 772 */               return this.previousAccessToken;
                    }

/* 775 */           boolean needsNewTokenValidation() {
                        return (this.previousAccessToken == null || this.isLegacy) ? false : true;
                    }
                }

/* 792 */       static class Result implements Serializable {
                    private static final long serialVersionUID = 1;
                    final Code code;
                    final String errorMessage;
                    final AccessToken token;

                    enum Code {
                        SUCCESS,
                        CANCEL,
                        ERROR
                    }

/* 793 */           private Result(Code code, AccessToken token, String errorMessage) {
/* 795 */               this.token = token;
/* 796 */               this.errorMessage = errorMessage;
/* 797 */               this.code = code;
                    }

/* 800 */           static Result createTokenResult(AccessToken token) {
/* 801 */               return new Result(Code.SUCCESS, token, null);
                    }

/* 804 */           static Result createCancelResult(String message) {
/* 805 */               return new Result(Code.CANCEL, null, message);
                    }

/* 808 */           static Result createErrorResult(String errorType, String errorDescription) {
/* 809 */               String message = errorType;
/* 810 */               if (errorDescription != null) {
/* 811 */                   message = message + ": " + errorDescription;
                        }
/* 813 */               return new Result(Code.ERROR, null, message);
                    }
                }
            }
