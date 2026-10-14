            package com.facebook;

            import android.content.ContentResolver;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.annotation.NonNull;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import com.facebook.model.GraphObject;
            import java.lang.reflect.Field;
            import java.net.HttpURLConnection;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.Set;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.Executor;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.atomic.AtomicInteger;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 68 */    public final class Settings {
                private static final String ANALYTICS_EVENT = "event";
                private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
                private static final String ATTRIBUTION_KEY = "attribution";
                private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
                private static final int DEFAULT_CORE_POOL_SIZE = 5;
                private static final int DEFAULT_KEEP_ALIVE = 1;
                private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
                private static final String MOBILE_INSTALL_EVENT = "MOBILE_APP_INSTALL";
                private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
                private static volatile Executor executor;
                private static volatile boolean shouldAutoPublishInstall;
                private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet<>(Arrays.asList(LoggingBehavior.DEVELOPER_ERRORS));
                private static final Object LOCK = new Object();
                private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
                private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
                private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactoryC07741();

                /* JADX INFO: renamed from: com.facebook.Settings$1 */
/* 69 */        static class ThreadFactoryC07741 implements ThreadFactory {
                    private final AtomicInteger counter = new AtomicInteger(0);

/* 70 */            ThreadFactoryC07741() {
                    }

                    @Override // java.util.concurrent.ThreadFactory
                    @NonNull
/* 75 */            public Thread newThread(@NonNull Runnable runnable) {
/* 76 */                return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
                    }
                }

/* 89 */        public static final Set<LoggingBehavior> getLoggingBehaviors() {
                    Set<LoggingBehavior> setUnmodifiableSet;
                    synchronized (loggingBehaviors) {
                        try {
/* 91 */                    setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return setUnmodifiableSet;
                }

/* 104 */       public static final void addLoggingBehavior(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
/* 106 */                   loggingBehaviors.add(behavior);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 119 */       public static final void removeLoggingBehavior(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
/* 121 */                   loggingBehaviors.remove(behavior);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 131 */       public static final void clearLoggingBehaviors() {
                    synchronized (loggingBehaviors) {
                        try {
/* 133 */                   loggingBehaviors.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 147 */       public static final boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 149 */           return false;
                }

/* 161 */       public static Executor getExecutor() {
                    synchronized (LOCK) {
                        try {
/* 163 */                   if (executor == null) {
/* 164 */                       Executor executor2 = getAsyncTaskExecutor();
/* 165 */                       if (executor2 == null) {
/* 166 */                           executor2 = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                                }
/* 169 */                       executor = executor2;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 172 */           return executor;
                }

/* 181 */       public static void setExecutor(Executor executor2) {
/* 182 */           Validate.notNull(executor2, "executor");
                    synchronized (LOCK) {
                        try {
/* 184 */                   executor = executor2;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 188 */       private static Executor getAsyncTaskExecutor() {
                    try {
/* 191 */               Field executorField = AsyncTask.class.getField("THREAD_POOL_EXECUTOR");
/* 196 */               if (executorField == null) {
                            return null;
                        }
                        try {
/* 202 */                   Object executorObject = executorField.get(null);
/* 207 */                   if (executorObject != null && (executorObject instanceof Executor)) {
/* 215 */                       return (Executor) executorObject;
                            }
                            return null;
                        } catch (IllegalAccessException e) {
                            return null;
                        }
                    } catch (NoSuchFieldException e2) {
                        return null;
                    }
                }

/* 224 */       public static void publishInstallAsync(Context context, String applicationId) {
/* 225 */           publishInstallAsync(context, applicationId, null);
                }

                /* JADX INFO: renamed from: com.facebook.Settings$2 */
/* 237 */       static class RunnableC07752 implements Runnable {
                    final /* synthetic */ Context val$applicationContext;
                    final /* synthetic */ String val$applicationId;
                    final /* synthetic */ Request.Callback val$callback;

/* 238 */           RunnableC07752(Context context, String str, Request.Callback callback) {
/* 239 */               this.val$applicationContext = context;
                        this.val$applicationId = str;
                        this.val$callback = callback;
                    }

                    @Override // java.lang.Runnable
/* 241 */           public void run() {
/* 242 */               Response response = Settings.publishInstallAndWaitForResponse(this.val$applicationContext, this.val$applicationId);
/* 243 */               if (this.val$callback != null) {
/* 245 */                   Handler handler = new Handler(Looper.getMainLooper());
/* 246 */                   handler.post(new AnonymousClass1(response));
                        }
                    }

                    /* JADX INFO: renamed from: com.facebook.Settings$2$1, reason: invalid class name */
/* 244 */           class AnonymousClass1 implements Runnable {
                        final /* synthetic */ Response val$response;

/* 245 */               AnonymousClass1(Response response) {
                            this.val$response = response;
                        }

                        @Override // java.lang.Runnable
/* 248 */               public void run() {
/* 249 */                   RunnableC07752.this.val$callback.onCompleted(this.val$response);
                        }
                    }
                }

/* 237 */       public static void publishInstallAsync(Context context, String applicationId, Request.Callback callback) {
/* 238 */           Context applicationContext = context.getApplicationContext();
/* 239 */           getExecutor().execute(new RunnableC07752(applicationContext, applicationId, callback));
                }

/* 262 */       public static void setShouldAutoPublishInstall(boolean shouldAutoPublishInstall2) {
/* 263 */           shouldAutoPublishInstall = shouldAutoPublishInstall2;
                }

/* 271 */       public static boolean getShouldAutoPublishInstall() {
/* 272 */           return shouldAutoPublishInstall;
                }

/* 283 */       public static boolean publishInstallAndWait(Context context, String applicationId) {
/* 284 */           Response response = publishInstallAndWaitForResponse(context, applicationId);
                    return response != null && response.getError() == null;
                }

/* 296 */       public static Response publishInstallAndWaitForResponse(Context context, String applicationId) {
                    try {
/* 297 */               if (context == null || applicationId == null) {
/* 298 */                   throw new IllegalArgumentException("Both context and applicationId must be non-null");
                        }
/* 300 */               String attributionId = getAttributionId(context.getContentResolver());
/* 301 */               SharedPreferences preferences = context.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
/* 302 */               String pingKey = applicationId + "ping";
/* 303 */               String jsonKey = applicationId + "json";
/* 304 */               long lastPing = preferences.getLong(pingKey, 0L);
/* 305 */               String lastResponseJSON = preferences.getString(jsonKey, null);
/* 307 */               GraphObject publishParams = GraphObject.Factory.create();
/* 308 */               publishParams.setProperty("event", MOBILE_INSTALL_EVENT);
/* 309 */               publishParams.setProperty("attribution", attributionId);
/* 311 */               String publishUrl = String.format(PUBLISH_ACTIVITY_PATH, applicationId);
/* 312 */               Request publishRequest = Request.newPostRequest(null, publishUrl, publishParams, null);
                        if (lastPing != 0) {
/* 315 */                   GraphObject graphObject = null;
/* 317 */                   if (lastResponseJSON != null) {
                                try {
/* 318 */                           graphObject = GraphObject.Factory.create(new JSONObject(lastResponseJSON));
                                } catch (JSONException e) {
                                }
                            }
/* 324 */                   if (graphObject == null) {
/* 325 */                       return Response.createResponsesFromString("true", null, new RequestBatch(publishRequest), true).get(0);
                            }
/* 327 */                   return new Response((Request) null, (HttpURLConnection) null, graphObject, true);
                        }
/* 329 */               if (attributionId == null) {
/* 330 */                   throw new FacebookException("No attribution id returned from the Facebook application");
                        }
/* 333 */               if (!Utility.queryAppAttributionSupportAndWait(applicationId)) {
/* 334 */                   throw new FacebookException("Install attribution has been disabled on the server.");
                        }
/* 337 */               Response publishResponse = publishRequest.executeAndWait();
/* 340 */               SharedPreferences.Editor editor = preferences.edit();
/* 341 */               long lastPing2 = System.currentTimeMillis();
/* 342 */               editor.putLong(pingKey, lastPing2);
/* 345 */               if (publishResponse.getGraphObject() != null && publishResponse.getGraphObject().getInnerJSONObject() != null) {
/* 347 */                   editor.putString(jsonKey, publishResponse.getGraphObject().getInnerJSONObject().toString());
                        }
/* 349 */               editor.apply();
                        return publishResponse;
                    } catch (Exception e2) {
/* 355 */               Utility.logd("Facebook-publish", e2);
/* 356 */               return new Response(null, null, new FacebookRequestError(null, e2));
                    }
                }

/* 364 */       public static String getAttributionId(ContentResolver contentResolver) {
/* 365 */           String[] projection = {ATTRIBUTION_ID_COLUMN_NAME};
/* 366 */           Cursor c = contentResolver.query(ATTRIBUTION_ID_CONTENT_URI, projection, null, null, null);
/* 367 */           if (c == null || !c.moveToFirst()) {
                        return null;
                    }
/* 370 */           String string = c.getString(c.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
/* 371 */           c.close();
/* 372 */           return string;
                }

/* 380 */       public static String getSdkVersion() {
/* 381 */           return FacebookSdkVersion.BUILD;
                }

/* 391 */       public static String getMigrationBundle() {
/* 392 */           return FacebookSdkVersion.MIGRATION_BUNDLE;
                }
            }
