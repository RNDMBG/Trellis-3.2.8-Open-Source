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
            
            
    public final class Settings {
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

                
        static class ThreadFactoryC07741 implements ThreadFactory {
                    private final AtomicInteger counter = new AtomicInteger(0);

            ThreadFactoryC07741() {
                    }

                    @Override 
                    @NonNull
            public Thread newThread(@NonNull Runnable runnable) {
                return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
                    }
                }

        public static final Set<LoggingBehavior> getLoggingBehaviors() {
                    Set<LoggingBehavior> setUnmodifiableSet;
                    synchronized (loggingBehaviors) {
                        try {
                    setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return setUnmodifiableSet;
                }

       public static final void addLoggingBehavior(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
                   loggingBehaviors.add(behavior);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static final void removeLoggingBehavior(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
                   loggingBehaviors.remove(behavior);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static final void clearLoggingBehaviors() {
                    synchronized (loggingBehaviors) {
                        try {
                   loggingBehaviors.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static final boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
                    synchronized (loggingBehaviors) {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return false;
                }

       public static Executor getExecutor() {
                    synchronized (LOCK) {
                        try {
                   if (executor == null) {
                       Executor executor2 = getAsyncTaskExecutor();
                       if (executor2 == null) {
                           executor2 = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                                }
                       executor = executor2;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return executor;
                }

       public static void setExecutor(Executor executor2) {
           Validate.notNull(executor2, "executor");
                    synchronized (LOCK) {
                        try {
                   executor = executor2;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private static Executor getAsyncTaskExecutor() {
                    try {
               Field executorField = AsyncTask.class.getField("THREAD_POOL_EXECUTOR");
               if (executorField == null) {
                            return null;
                        }
                        try {
                   Object executorObject = executorField.get(null);
                   if (executorObject != null && (executorObject instanceof Executor)) {
                       return (Executor) executorObject;
                            }
                            return null;
                        } catch (IllegalAccessException e) {
                            return null;
                        }
                    } catch (NoSuchFieldException e2) {
                        return null;
                    }
                }

       public static void publishInstallAsync(Context context, String applicationId) {
           publishInstallAsync(context, applicationId, null);
                }

                
       static class RunnableC07752 implements Runnable {
                    final  Context val$applicationContext;
                    final  String val$applicationId;
                    final  Request.Callback val$callback;

           RunnableC07752(Context context, String str, Request.Callback callback) {
               this.val$applicationContext = context;
                        this.val$applicationId = str;
                        this.val$callback = callback;
                    }

                    @Override 
           public void run() {
               Response response = Settings.publishInstallAndWaitForResponse(this.val$applicationContext, this.val$applicationId);
               if (this.val$callback != null) {
                   Handler handler = new Handler(Looper.getMainLooper());
                   handler.post(new AnonymousClass1(response));
                        }
                    }

                    
           class AnonymousClass1 implements Runnable {
                        final  Response val$response;

               AnonymousClass1(Response response) {
                            this.val$response = response;
                        }

                        @Override 
               public void run() {
                   RunnableC07752.this.val$callback.onCompleted(this.val$response);
                        }
                    }
                }

       public static void publishInstallAsync(Context context, String applicationId, Request.Callback callback) {
           Context applicationContext = context.getApplicationContext();
           getExecutor().execute(new RunnableC07752(applicationContext, applicationId, callback));
                }

       public static void setShouldAutoPublishInstall(boolean shouldAutoPublishInstall2) {
           shouldAutoPublishInstall = shouldAutoPublishInstall2;
                }

       public static boolean getShouldAutoPublishInstall() {
           return shouldAutoPublishInstall;
                }

       public static boolean publishInstallAndWait(Context context, String applicationId) {
           Response response = publishInstallAndWaitForResponse(context, applicationId);
                    return response != null && response.getError() == null;
                }

       public static Response publishInstallAndWaitForResponse(Context context, String applicationId) {
                    try {
               if (context == null || applicationId == null) {
                   throw new IllegalArgumentException("Both context and applicationId must be non-null");
                        }
               String attributionId = getAttributionId(context.getContentResolver());
               SharedPreferences preferences = context.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
               String pingKey = applicationId + "ping";
               String jsonKey = applicationId + "json";
               long lastPing = preferences.getLong(pingKey, 0L);
               String lastResponseJSON = preferences.getString(jsonKey, null);
               GraphObject publishParams = GraphObject.Factory.create();
               publishParams.setProperty("event", MOBILE_INSTALL_EVENT);
               publishParams.setProperty("attribution", attributionId);
               String publishUrl = String.format(PUBLISH_ACTIVITY_PATH, applicationId);
               Request publishRequest = Request.newPostRequest(null, publishUrl, publishParams, null);
                        if (lastPing != 0) {
                   GraphObject graphObject = null;
                   if (lastResponseJSON != null) {
                                try {
                           graphObject = GraphObject.Factory.create(new JSONObject(lastResponseJSON));
                                } catch (JSONException e) {
                                }
                            }
                   if (graphObject == null) {
                       return Response.createResponsesFromString("true", null, new RequestBatch(publishRequest), true).get(0);
                            }
                   return new Response((Request) null, (HttpURLConnection) null, graphObject, true);
                        }
               if (attributionId == null) {
                   throw new FacebookException("No attribution id returned from the Facebook application");
                        }
               if (!Utility.queryAppAttributionSupportAndWait(applicationId)) {
                   throw new FacebookException("Install attribution has been disabled on the server.");
                        }
               Response publishResponse = publishRequest.executeAndWait();
               SharedPreferences.Editor editor = preferences.edit();
               long lastPing2 = System.currentTimeMillis();
               editor.putLong(pingKey, lastPing2);
               if (publishResponse.getGraphObject() != null && publishResponse.getGraphObject().getInnerJSONObject() != null) {
                   editor.putString(jsonKey, publishResponse.getGraphObject().getInnerJSONObject().toString());
                        }
               editor.apply();
                        return publishResponse;
                    } catch (Exception e2) {
               Utility.logd("Facebook-publish", e2);
               return new Response(null, null, new FacebookRequestError(null, e2));
                    }
                }

       public static String getAttributionId(ContentResolver contentResolver) {
           String[] projection = {ATTRIBUTION_ID_COLUMN_NAME};
           Cursor c = contentResolver.query(ATTRIBUTION_ID_CONTENT_URI, projection, null, null, null);
           if (c == null || !c.moveToFirst()) {
                        return null;
                    }
           String string = c.getString(c.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
           c.close();
           return string;
                }

       public static String getSdkVersion() {
           return FacebookSdkVersion.BUILD;
                }

       public static String getMigrationBundle() {
           return FacebookSdkVersion.MIGRATION_BUNDLE;
                }
            }
