            package com.facebook;

            import android.graphics.Bitmap;
            import android.location.Location;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.ParcelFileDescriptor;
            import android.text.TextUtils;
            import android.util.Pair;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.apache.MultipartEntity;
            import co.vine.android.provider.Vine;
            import co.vine.android.service.VineService;
            import com.facebook.internal.Logger;
            import com.facebook.internal.ServerProtocol;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import com.facebook.model.GraphMultiResult;
            import com.facebook.model.GraphObject;
            import com.facebook.model.GraphObjectList;
            import com.facebook.model.GraphPlace;
            import com.facebook.model.GraphUser;
            import com.google.android.gms.plus.PlusShare;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.net.HttpURLConnection;
            import java.net.MalformedURLException;
            import java.net.URL;
            import java.net.URLEncoder;
            import java.text.SimpleDateFormat;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Date;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Locale;
            import java.util.Map;
            import java.util.Set;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public class Request {
                private static final String ACCESS_TOKEN_PARAM = "access_token";
                private static final String ATTACHED_FILES_PARAM = "attached_files";
                private static final String ATTACHMENT_FILENAME_PREFIX = "file";
                private static final String BATCH_APP_ID_PARAM = "batch_app_id";
                private static final String BATCH_BODY_PARAM = "body";
                private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
                private static final String BATCH_ENTRY_NAME_PARAM = "name";
                private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
                private static final String BATCH_METHOD_PARAM = "method";
                private static final String BATCH_PARAM = "batch";
                private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
                private static final String CONTENT_TYPE_HEADER = "Content-Type";
                private static final String FORMAT_JSON = "json";
                private static final String FORMAT_PARAM = "format";
                private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
                public static final int MAXIMUM_BATCH_SIZE = 50;

                /* JADX INFO: renamed from: ME */
                private static final String f524ME = "me";
                private static final String MIGRATION_BUNDLE_PARAM = "migration_bundle";
                private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
                private static final String MY_FEED = "me/feed";
                private static final String MY_FRIENDS = "me/friends";
                private static final String MY_PHOTOS = "me/photos";
                private static final String MY_VIDEOS = "me/videos";
                private static final String PICTURE_PARAM = "picture";
                private static final String SDK_ANDROID = "android";
                private static final String SDK_PARAM = "sdk";
                private static final String SEARCH = "search";
                private static final String USER_AGENT_BASE = "FBAndroidSDK";
                private static final String USER_AGENT_HEADER = "User-Agent";
                private static String defaultBatchApplicationId;
                private static volatile String userAgent;
                private String batchEntryDependsOn;
                private String batchEntryName;
                private boolean batchEntryOmitResultOnSuccess;
                private Callback callback;
                private GraphObject graphObject;
                private String graphPath;
                private HttpMethod httpMethod;
                private String overriddenURL;
                private Bundle parameters;
                private String restMethod;
                private Session session;

                public interface Callback {
                    void onCompleted(Response response);
                }

                public interface GraphPlaceListCallback {
                    void onCompleted(List<GraphPlace> list, Response response);
                }

                public interface GraphUserCallback {
                    void onCompleted(GraphUser graphUser, Response response);
                }

                public interface GraphUserListCallback {
                    void onCompleted(List<GraphUser> list, Response response);
                }

                private interface KeyValueSerializer {
                    void writeString(String str, String str2) throws IOException;
                }

/* 64 */        static /* synthetic */ List access$000(Response x0, Class x1) {
/* 65 */            return typedListFromResponse(x0, x1);
                }

/* 64 */        static /* synthetic */ boolean access$100(Object x0) {
/* 65 */            return isSupportedParameterType(x0);
                }

/* 64 */        static /* synthetic */ String access$200(Object x0) {
/* 65 */            return parameterToString(x0);
                }

/* 122 */       public Request() {
/* 123 */           this(null, null, null, null, null);
                }

/* 137 */       public Request(Session session, String graphPath) {
/* 138 */           this(session, graphPath, null, null, null);
                }

/* 159 */       public Request(Session session, String graphPath, Bundle parameters, HttpMethod httpMethod) {
/* 160 */           this(session, graphPath, parameters, httpMethod, null);
                }

/* 182 */       public Request(Session session, String graphPath, Bundle parameters, HttpMethod httpMethod, Callback callback) {
/* 114 */           this.batchEntryOmitResultOnSuccess = true;
/* 184 */           this.session = session;
/* 185 */           this.graphPath = graphPath;
/* 186 */           this.callback = callback;
/* 188 */           setHttpMethod(httpMethod);
/* 190 */           if (parameters != null) {
/* 191 */               this.parameters = new Bundle(parameters);
                    } else {
/* 193 */               this.parameters = new Bundle();
                    }
/* 196 */           if (!this.parameters.containsKey(MIGRATION_BUNDLE_PARAM)) {
/* 197 */               this.parameters.putString(MIGRATION_BUNDLE_PARAM, FacebookSdkVersion.MIGRATION_BUNDLE);
                    }
                }

/* 200 */       Request(Session session, URL overriddenURL) {
/* 114 */           this.batchEntryOmitResultOnSuccess = true;
/* 202 */           this.session = session;
/* 203 */           this.overriddenURL = overriddenURL.toString();
/* 205 */           setHttpMethod(HttpMethod.GET);
/* 207 */           this.parameters = new Bundle();
                }

/* 224 */       public static Request newPostRequest(Session session, String graphPath, GraphObject graphObject, Callback callback) {
/* 225 */           Request request = new Request(session, graphPath, null, HttpMethod.POST, callback);
/* 226 */           request.setGraphObject(graphObject);
/* 227 */           return request;
                }

/* 244 */       public static Request newRestRequest(Session session, String restMethod, Bundle parameters, HttpMethod httpMethod) {
/* 245 */           Request request = new Request(session, null, parameters, httpMethod);
/* 246 */           request.setRestMethod(restMethod);
/* 247 */           return request;
                }

                /* JADX INFO: renamed from: com.facebook.Request$1 */
/* 258 */       static class C07611 implements Callback {
                    final /* synthetic */ GraphUserCallback val$callback;

/* 259 */           C07611(GraphUserCallback graphUserCallback) {
/* 260 */               this.val$callback = graphUserCallback;
                    }

                    @Override // com.facebook.Request.Callback
/* 262 */           public void onCompleted(Response response) {
/* 263 */               if (this.val$callback != null) {
/* 264 */                   this.val$callback.onCompleted((GraphUser) response.getGraphObjectAs(GraphUser.class), response);
                        }
                    }
                }

/* 259 */       public static Request newMeRequest(Session session, GraphUserCallback callback) {
/* 260 */           Callback wrapper = new C07611(callback);
/* 268 */           return new Request(session, "me", null, null, wrapper);
                }

                /* JADX INFO: renamed from: com.facebook.Request$2 */
/* 279 */       static class C07622 implements Callback {
                    final /* synthetic */ GraphUserListCallback val$callback;

/* 280 */           C07622(GraphUserListCallback graphUserListCallback) {
/* 281 */               this.val$callback = graphUserListCallback;
                    }

                    @Override // com.facebook.Request.Callback
/* 283 */           public void onCompleted(Response response) {
/* 284 */               if (this.val$callback != null) {
/* 285 */                   this.val$callback.onCompleted(Request.access$000(response, GraphUser.class), response);
                        }
                    }
                }

/* 280 */       public static Request newMyFriendsRequest(Session session, GraphUserListCallback callback) {
/* 281 */           Callback wrapper = new C07622(callback);
/* 289 */           return new Request(session, MY_FRIENDS, null, null, wrapper);
                }

/* 303 */       public static Request newUploadPhotoRequest(Session session, Bitmap image, Callback callback) {
/* 304 */           Bundle parameters = new Bundle(1);
/* 305 */           parameters.putParcelable(PICTURE_PARAM, image);
/* 307 */           return new Request(session, MY_PHOTOS, parameters, HttpMethod.POST, callback);
                }

/* 320 */       public static Request newUploadPhotoRequest(Session session, File file, Callback callback) throws FileNotFoundException {
/* 321 */           ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file, 268435456);
/* 322 */           Bundle parameters = new Bundle(1);
/* 323 */           parameters.putParcelable(PICTURE_PARAM, descriptor);
/* 325 */           return new Request(session, MY_PHOTOS, parameters, HttpMethod.POST, callback);
                }

/* 338 */       public static Request newUploadVideoRequest(Session session, File file, Callback callback) throws FileNotFoundException {
/* 339 */           ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file, 268435456);
/* 340 */           Bundle parameters = new Bundle(1);
/* 341 */           parameters.putParcelable(file.getName(), descriptor);
/* 343 */           return new Request(session, MY_VIDEOS, parameters, HttpMethod.POST, callback);
                }

/* 357 */       public static Request newGraphPathRequest(Session session, String graphPath, Callback callback) {
/* 358 */           return new Request(session, graphPath, null, null, callback);
                }

/* 384 */       public static Request newPlacesSearchRequest(Session session, Location location, int radiusInMeters, int resultsLimit, String searchText, GraphPlaceListCallback callback) {
/* 385 */           if (location == null && Utility.isNullOrEmpty(searchText)) {
/* 386 */               throw new FacebookException("Either location or searchText must be specified.");
                    }
/* 389 */           Bundle parameters = new Bundle(5);
/* 390 */           parameters.putString("type", "place");
/* 391 */           parameters.putInt(Vine.QUERY_PARAMETER_LIMIT, resultsLimit);
/* 392 */           if (location != null) {
/* 393 */               parameters.putString("center", String.format(Locale.US, "%f,%f", Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())));
/* 395 */               parameters.putInt("distance", radiusInMeters);
                    }
/* 397 */           if (!Utility.isNullOrEmpty(searchText)) {
/* 398 */               parameters.putString(VineService.EXTRA_QUERY, searchText);
                    }
/* 401 */           Callback wrapper = new C07633(callback);
/* 410 */           return new Request(session, "search", parameters, HttpMethod.GET, wrapper);
                }

                /* JADX INFO: renamed from: com.facebook.Request$3 */
/* 399 */       static class C07633 implements Callback {
                    final /* synthetic */ GraphPlaceListCallback val$callback;

/* 400 */           C07633(GraphPlaceListCallback graphPlaceListCallback) {
/* 401 */               this.val$callback = graphPlaceListCallback;
                    }

                    @Override // com.facebook.Request.Callback
/* 403 */           public void onCompleted(Response response) {
/* 404 */               if (this.val$callback != null) {
/* 405 */                   this.val$callback.onCompleted(Request.access$000(response, GraphPlace.class), response);
                        }
                    }
                }

/* 424 */       public static Request newStatusUpdateRequest(Session session, String message, Callback callback) {
/* 425 */           Bundle parameters = new Bundle();
/* 426 */           parameters.putString("message", message);
/* 428 */           return new Request(session, MY_FEED, parameters, HttpMethod.POST, callback);
                }

/* 436 */       public final GraphObject getGraphObject() {
/* 437 */           return this.graphObject;
                }

/* 446 */       public final void setGraphObject(GraphObject graphObject) {
/* 447 */           this.graphObject = graphObject;
                }

/* 455 */       public final String getGraphPath() {
/* 456 */           return this.graphPath;
                }

/* 465 */       public final void setGraphPath(String graphPath) {
/* 466 */           this.graphPath = graphPath;
                }

/* 474 */       public final HttpMethod getHttpMethod() {
/* 475 */           return this.httpMethod;
                }

/* 484 */       public final void setHttpMethod(HttpMethod httpMethod) {
/* 485 */           if (this.overriddenURL != null && httpMethod != HttpMethod.GET) {
/* 486 */               throw new FacebookException("Can't change HTTP method on request with overridden URL.");
                    }
/* 488 */           if (httpMethod == null) {
/* 488 */               httpMethod = HttpMethod.GET;
                    }
                    this.httpMethod = httpMethod;
                }

/* 496 */       public final Bundle getParameters() {
/* 497 */           return this.parameters;
                }

/* 506 */       public final void setParameters(Bundle parameters) {
/* 507 */           this.parameters = parameters;
                }

/* 515 */       public final String getRestMethod() {
/* 516 */           return this.restMethod;
                }

/* 525 */       public final void setRestMethod(String restMethod) {
/* 526 */           this.restMethod = restMethod;
                }

/* 534 */       public final Session getSession() {
/* 535 */           return this.session;
                }

/* 545 */       public final void setSession(Session session) {
/* 546 */           this.session = session;
                }

/* 554 */       public final String getBatchEntryName() {
/* 555 */           return this.batchEntryName;
                }

/* 568 */       public final void setBatchEntryName(String batchEntryName) {
/* 569 */           this.batchEntryName = batchEntryName;
                }

/* 577 */       public final String getBatchEntryDependsOn() {
/* 578 */           return this.batchEntryDependsOn;
                }

/* 590 */       public final void setBatchEntryDependsOn(String batchEntryDependsOn) {
/* 591 */           this.batchEntryDependsOn = batchEntryDependsOn;
                }

/* 601 */       public final boolean getBatchEntryOmitResultOnSuccess() {
/* 602 */           return this.batchEntryOmitResultOnSuccess;
                }

/* 614 */       public final void setBatchEntryOmitResultOnSuccess(boolean batchEntryOmitResultOnSuccess) {
/* 615 */           this.batchEntryOmitResultOnSuccess = batchEntryOmitResultOnSuccess;
                }

/* 625 */       public static final String getDefaultBatchApplicationId() {
/* 626 */           return defaultBatchApplicationId;
                }

/* 637 */       public static final void setDefaultBatchApplicationId(String applicationId) {
/* 638 */           defaultBatchApplicationId = applicationId;
                }

/* 646 */       public final Callback getCallback() {
/* 647 */           return this.callback;
                }

/* 656 */       public final void setCallback(Callback callback) {
/* 657 */           this.callback = callback;
                }

/* 677 */       public static RequestAsyncTask executePostRequestAsync(Session session, String graphPath, GraphObject graphObject, Callback callback) {
/* 678 */           return newPostRequest(session, graphPath, graphObject, callback).executeAsync();
                }

/* 698 */       public static RequestAsyncTask executeRestRequestAsync(Session session, String restMethod, Bundle parameters, HttpMethod httpMethod) {
/* 699 */           return newRestRequest(session, restMethod, parameters, httpMethod).executeAsync();
                }

/* 713 */       public static RequestAsyncTask executeMeRequestAsync(Session session, GraphUserCallback callback) {
/* 714 */           return newMeRequest(session, callback).executeAsync();
                }

/* 728 */       public static RequestAsyncTask executeMyFriendsRequestAsync(Session session, GraphUserListCallback callback) {
/* 729 */           return newMyFriendsRequest(session, callback).executeAsync();
                }

/* 745 */       public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, Bitmap image, Callback callback) {
/* 746 */           return newUploadPhotoRequest(session, image, callback).executeAsync();
                }

/* 761 */       public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, File file, Callback callback) throws FileNotFoundException {
/* 762 */           return newUploadPhotoRequest(session, file, callback).executeAsync();
                }

/* 778 */       public static RequestAsyncTask executeGraphPathRequestAsync(Session session, String graphPath, Callback callback) {
/* 779 */           return newGraphPathRequest(session, graphPath, callback).executeAsync();
                }

/* 806 */       public static RequestAsyncTask executePlacesSearchRequestAsync(Session session, Location location, int radiusInMeters, int resultsLimit, String searchText, GraphPlaceListCallback callback) {
/* 807 */           return newPlacesSearchRequest(session, location, radiusInMeters, resultsLimit, searchText, callback).executeAsync();
                }

/* 823 */       public static RequestAsyncTask executeStatusUpdateRequestAsync(Session session, String message, Callback callback) {
/* 824 */           return newStatusUpdateRequest(session, message, callback).executeAsync();
                }

/* 838 */       public final Response executeAndWait() {
/* 839 */           return executeAndWait(this);
                }

/* 851 */       public final RequestAsyncTask executeAsync() {
                    return executeBatchAsync(this);
                }

/* 869 */       public static HttpURLConnection toHttpConnection(Request... requests) {
/* 870 */           return toHttpConnection(Arrays.asList(requests));
                }

/* 887 */       public static HttpURLConnection toHttpConnection(Collection<Request> requests) {
/* 888 */           Validate.notEmptyAndContainsNoNulls(requests, VineAPI.RESOURCE_REQUESTS);
/* 890 */           return toHttpConnection(new RequestBatch(requests));
                }

/* 908 */       public static HttpURLConnection toHttpConnection(RequestBatch requests) {
                    URL url;
                    for (Request request : requests) {
/* 910 */               request.validate();
                    }
                    try {
/* 915 */               if (requests.size() == 1) {
/* 917 */                   Request request2 = requests.get(0);
/* 919 */                   url = new URL(request2.getUrlForSingleRequest());
                        } else {
/* 923 */                   url = new URL(ServerProtocol.GRAPH_URL);
                        }
                        try {
/* 931 */                   HttpURLConnection connection = createConnection(url);
/* 933 */                   serializeToUrlConnection(requests, connection);
/* 940 */                   return connection;
                        } catch (IOException e) {
/* 935 */                   throw new FacebookException("could not construct request body", e);
                        } catch (JSONException e2) {
/* 937 */                   throw new FacebookException("could not construct request body", e2);
                        }
                    } catch (MalformedURLException e3) {
/* 926 */               throw new FacebookException("could not construct URL for request", e3);
                    }
                }

/* 956 */       public static Response executeAndWait(Request request) {
/* 957 */           List<Response> responses = executeBatchAndWait(request);
/* 959 */           if (responses == null || responses.size() != 1) {
/* 960 */               throw new FacebookException("invalid state: expected a single response");
                    }
/* 963 */           return responses.get(0);
                }

/* 982 */       public static List<Response> executeBatchAndWait(Request... requests) {
/* 983 */           Validate.notNull(requests, VineAPI.RESOURCE_REQUESTS);
/* 985 */           return executeBatchAndWait(Arrays.asList(requests));
                }

/* 1002 */      public static List<Response> executeBatchAndWait(Collection<Request> requests) {
/* 1003 */          return executeBatchAndWait(new RequestBatch(requests));
                }

/* 1022 */      public static List<Response> executeBatchAndWait(RequestBatch requests) {
/* 1023 */          Validate.notEmptyAndContainsNoNulls(requests, VineAPI.RESOURCE_REQUESTS);
                    try {
/* 1027 */              HttpURLConnection connection = toHttpConnection(requests);
/* 1034 */              return executeConnectionAndWait(connection, requests);
                    } catch (Exception ex) {
/* 1029 */              List<Response> responses = Response.constructErrorResponses(requests.getRequests(), null, new FacebookException(ex));
/* 1030 */              runCallbacks(requests, responses);
/* 1035 */              return responses;
                    }
                }

/* 1052 */      public static RequestAsyncTask executeBatchAsync(Request... requests) {
/* 1053 */          Validate.notNull(requests, VineAPI.RESOURCE_REQUESTS);
/* 1055 */          return executeBatchAsync(Arrays.asList(requests));
                }

/* 1072 */      public static RequestAsyncTask executeBatchAsync(Collection<Request> requests) {
/* 1073 */          return executeBatchAsync(new RequestBatch(requests));
                }

/* 1090 */      public static RequestAsyncTask executeBatchAsync(RequestBatch requests) {
/* 1091 */          Validate.notEmptyAndContainsNoNulls(requests, VineAPI.RESOURCE_REQUESTS);
/* 1093 */          RequestAsyncTask asyncTask = new RequestAsyncTask(requests);
/* 1094 */          asyncTask.executeOnSettingsExecutor();
/* 1095 */          return asyncTask;
                }

/* 1114 */      public static List<Response> executeConnectionAndWait(HttpURLConnection connection, Collection<Request> requests) throws IOException, JSONException, FacebookException {
/* 1115 */          return executeConnectionAndWait(connection, new RequestBatch(requests));
                }

/* 1134 */      public static List<Response> executeConnectionAndWait(HttpURLConnection connection, RequestBatch requests) throws IOException, JSONException, FacebookException {
/* 1135 */          List<Response> responses = Response.fromHttpConnection(connection, requests);
/* 1137 */          Utility.disconnectQuietly(connection);
/* 1139 */          int numRequests = requests.size();
/* 1140 */          if (numRequests != responses.size()) {
/* 1141 */              throw new FacebookException(String.format("Received %d responses while expecting %d", Integer.valueOf(responses.size()), Integer.valueOf(numRequests)));
                    }
/* 1145 */          runCallbacks(requests, responses);
/* 1149 */          HashSet<Session> sessions = new HashSet<>();
                    for (Request request : requests) {
/* 1151 */              if (request.session != null) {
/* 1152 */                  sessions.add(request.session);
                        }
                    }
                    for (Session session : sessions) {
/* 1156 */              session.extendAccessTokenIfNeeded();
                    }
/* 1159 */          return responses;
                }

/* 1178 */      public static RequestAsyncTask executeConnectionAsync(HttpURLConnection connection, RequestBatch requests) {
/* 1179 */          return executeConnectionAsync(null, connection, requests);
                }

/* 1202 */      public static RequestAsyncTask executeConnectionAsync(Handler callbackHandler, HttpURLConnection connection, RequestBatch requests) {
/* 1203 */          Validate.notNull(connection, "connection");
/* 1205 */          RequestAsyncTask asyncTask = new RequestAsyncTask(connection, requests);
/* 1206 */          requests.setCallbackHandler(callbackHandler);
/* 1207 */          asyncTask.executeOnSettingsExecutor();
/* 1208 */          return asyncTask;
                }

/* 1217 */      public String toString() {
/* 1218 */          return "{Request:  session: " + this.session + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", restMethod: " + this.restMethod + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
                }

/* 1224 */      static void runCallbacks(RequestBatch requests, List<Response> responses) {
/* 1225 */          int numRequests = requests.size();
/* 1228 */          ArrayList<Pair<Callback, Response>> callbacks = new ArrayList<>();
                    for (int i = 0; i < numRequests; i++) {
/* 1230 */              Request request = requests.get(i);
/* 1231 */              if (request.callback != null) {
/* 1232 */                  callbacks.add(new Pair<>(request.callback, responses.get(i)));
                        }
                    }
/* 1236 */          if (callbacks.size() > 0) {
/* 1237 */              Runnable runnable = new RunnableC07644(callbacks, requests);
/* 1250 */              Handler callbackHandler = requests.getCallbackHandler();
/* 1251 */              if (callbackHandler == null) {
/* 1253 */                  runnable.run();
                        } else {
/* 1256 */                  callbackHandler.post(runnable);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.facebook.Request$4 */
/* 1235 */      static class RunnableC07644 implements Runnable {
                    final /* synthetic */ ArrayList<Pair<Callback, Response>> val$callbacks;
                    final /* synthetic */ RequestBatch val$requests;

/* 1236 */          RunnableC07644(ArrayList<Pair<Callback, Response>> arrayList, RequestBatch requestBatch) {
/* 1237 */              this.val$callbacks = arrayList;
                        this.val$requests = requestBatch;
                    }

                    @Override // java.lang.Runnable
/* 1238 */          public void run() {
                        for (Pair<Callback, Response> pair : this.val$callbacks) {
/* 1240 */                  ((Callback) pair.first).onCompleted((Response) pair.second);
                        }
/* 1243 */              List<RequestBatch.Callback> batchCallbacks = this.val$requests.getCallbacks();
                        for (RequestBatch.Callback batchCallback : batchCallbacks) {
/* 1245 */                  batchCallback.onBatchCompleted(this.val$requests);
                        }
                    }
                }

/* 1262 */      static HttpURLConnection createConnection(URL url) throws IOException {
/* 1263 */          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
/* 1265 */          connection.setRequestProperty(USER_AGENT_HEADER, getUserAgent());
/* 1266 */          connection.setRequestProperty(CONTENT_TYPE_HEADER, getMimeContentType());
/* 1268 */          connection.setChunkedStreamingMode(0);
/* 1269 */          return connection;
                }

/* 1273 */      private void addCommonParameters() {
/* 1274 */          if (this.session != null) {
/* 1275 */              if (!this.session.isOpened()) {
/* 1276 */                  throw new FacebookException("Session provided to a Request in un-opened state.");
                        }
/* 1277 */              if (!this.parameters.containsKey("access_token")) {
/* 1278 */                  String accessToken = this.session.getAccessToken();
/* 1279 */                  Logger.registerAccessToken(accessToken);
/* 1280 */                  this.parameters.putString("access_token", accessToken);
                        }
                    }
/* 1283 */          this.parameters.putString(SDK_PARAM, SDK_ANDROID);
/* 1284 */          this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
                }

/* 1287 */      private String appendParametersToBaseUrl(String baseUrl) {
/* 1288 */          Uri.Builder uriBuilder = new Uri.Builder().encodedPath(baseUrl);
/* 1290 */          Set<String> keys = this.parameters.keySet();
                    for (String key : keys) {
/* 1292 */              Object value = this.parameters.get(key);
/* 1294 */              if (value == null) {
/* 1295 */                  value = "";
                        }
/* 1298 */              if (!isSupportedParameterType(value)) {
/* 1301 */                  if (this.httpMethod == HttpMethod.GET) {
/* 1302 */                      throw new IllegalArgumentException(String.format("Unsupported parameter type for GET request: %s", value.getClass().getSimpleName()));
                            }
                        } else {
/* 1308 */                  uriBuilder.appendQueryParameter(key, parameterToString(value).toString());
                        }
                    }
/* 1311 */          return uriBuilder.toString();
                }

/* 1314 */      final String getUrlForBatchedRequest() {
                    String baseUrl;
/* 1315 */          if (this.overriddenURL != null) {
/* 1316 */              throw new FacebookException("Can't override URL for a batch request");
                    }
/* 1320 */          if (this.restMethod != null) {
/* 1321 */              baseUrl = ServerProtocol.BATCHED_REST_METHOD_URL_BASE + this.restMethod;
                    } else {
/* 1323 */              baseUrl = this.graphPath;
                    }
/* 1326 */          addCommonParameters();
/* 1327 */          return appendParametersToBaseUrl(baseUrl);
                }

/* 1330 */      final String getUrlForSingleRequest() {
                    String baseUrl;
/* 1331 */          if (this.overriddenURL != null) {
/* 1332 */              return this.overriddenURL;
                    }
/* 1336 */          if (this.restMethod != null) {
/* 1337 */              baseUrl = ServerProtocol.REST_URL_BASE + this.restMethod;
                    } else {
/* 1339 */              baseUrl = ServerProtocol.GRAPH_URL_BASE + this.graphPath;
                    }
/* 1342 */          addCommonParameters();
/* 1343 */          return appendParametersToBaseUrl(baseUrl);
                }

/* 1347 */      private void serializeToBatch(JSONArray batch, Bundle attachments) throws JSONException, IOException {
/* 1348 */          JSONObject batchEntry = new JSONObject();
/* 1350 */          if (this.batchEntryName != null) {
/* 1351 */              batchEntry.put("name", this.batchEntryName);
/* 1352 */              batchEntry.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
                    }
/* 1354 */          if (this.batchEntryDependsOn != null) {
/* 1355 */              batchEntry.put(BATCH_ENTRY_DEPENDS_ON_PARAM, this.batchEntryDependsOn);
                    }
/* 1358 */          String relativeURL = getUrlForBatchedRequest();
/* 1359 */          batchEntry.put(BATCH_RELATIVE_URL_PARAM, relativeURL);
/* 1360 */          batchEntry.put(BATCH_METHOD_PARAM, this.httpMethod);
/* 1361 */          if (this.session != null) {
/* 1362 */              String accessToken = this.session.getAccessToken();
/* 1363 */              Logger.registerAccessToken(accessToken);
                    }
/* 1367 */          ArrayList<String> attachmentNames = new ArrayList<>();
/* 1368 */          Set<String> keys = this.parameters.keySet();
                    for (String key : keys) {
/* 1370 */              Object value = this.parameters.get(key);
/* 1371 */              if (isSupportedAttachmentType(value)) {
/* 1373 */                  String name = String.format("%s%d", "file", Integer.valueOf(attachments.size()));
/* 1374 */                  attachmentNames.add(name);
/* 1375 */                  Utility.putObjectInBundle(attachments, name, value);
                        }
                    }
/* 1379 */          if (!attachmentNames.isEmpty()) {
/* 1380 */              String attachmentNamesString = TextUtils.join(",", attachmentNames);
/* 1381 */              batchEntry.put(ATTACHED_FILES_PARAM, attachmentNamesString);
                    }
/* 1384 */          if (this.graphObject != null) {
/* 1386 */              ArrayList<String> keysAndValues = new ArrayList<>();
/* 1387 */              processGraphObject(this.graphObject, relativeURL, new C07655(keysAndValues));
/* 1393 */              String bodyValue = TextUtils.join("&", keysAndValues);
/* 1394 */              batchEntry.put("body", bodyValue);
                    }
/* 1397 */          batch.put(batchEntry);
                }

                /* JADX INFO: renamed from: com.facebook.Request$5 */
/* 1385 */      class C07655 implements KeyValueSerializer {
                    final /* synthetic */ ArrayList val$keysAndValues;

/* 1386 */          C07655(ArrayList arrayList) {
                        this.val$keysAndValues = arrayList;
                    }

                    @Override // com.facebook.Request.KeyValueSerializer
/* 1389 */          public void writeString(String key, String value) throws IOException {
/* 1390 */              this.val$keysAndValues.add(String.format("%s=%s", key, URLEncoder.encode(value, IoUtils.UTF_8)));
                    }
                }

/* 1400 */      private void validate() {
/* 1401 */          if (this.graphPath != null && this.restMethod != null) {
/* 1402 */              throw new IllegalArgumentException("Only one of a graph path or REST method may be specified per request.");
                    }
                }

/* 1407 */      static final void serializeToUrlConnection(RequestBatch requests, HttpURLConnection connection) throws JSONException, IOException {
/* 1408 */          Logger logger = new Logger(LoggingBehavior.REQUESTS, "Request");
/* 1410 */          int numRequests = requests.size();
/* 1412 */          HttpMethod connectionHttpMethod = numRequests == 1 ? requests.get(0).httpMethod : HttpMethod.POST;
/* 1413 */          connection.setRequestMethod(connectionHttpMethod.name());
/* 1415 */          URL url = connection.getURL();
/* 1416 */          logger.append("Request:\n");
/* 1417 */          logger.appendKeyValue("Id", requests.getId());
/* 1418 */          logger.appendKeyValue("URL", url);
/* 1419 */          logger.appendKeyValue("Method", connection.getRequestMethod());
/* 1420 */          logger.appendKeyValue(USER_AGENT_HEADER, connection.getRequestProperty(USER_AGENT_HEADER));
/* 1421 */          logger.appendKeyValue(CONTENT_TYPE_HEADER, connection.getRequestProperty(CONTENT_TYPE_HEADER));
/* 1423 */          connection.setConnectTimeout(requests.getTimeout());
/* 1424 */          connection.setReadTimeout(requests.getTimeout());
/* 1428 */          boolean isPost = connectionHttpMethod == HttpMethod.POST;
/* 1429 */          if (!isPost) {
/* 1430 */              logger.log();
/* 1474 */              return;
                    }
/* 1434 */          connection.setDoOutput(true);
/* 1436 */          BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                    try {
/* 1438 */              Serializer serializer = new Serializer(outputStream, logger);
/* 1440 */              if (numRequests == 1) {
/* 1441 */                  Request request = requests.get(0);
/* 1443 */                  logger.append("  Parameters:\n");
/* 1444 */                  serializeParameters(request.parameters, serializer);
/* 1446 */                  logger.append("  Attachments:\n");
/* 1447 */                  serializeAttachments(request.parameters, serializer);
/* 1449 */                  if (request.graphObject != null) {
/* 1450 */                      processGraphObject(request.graphObject, url.getPath(), serializer);
                            }
                        } else {
/* 1453 */                  String batchAppID = getBatchAppId(requests);
/* 1454 */                  if (Utility.isNullOrEmpty(batchAppID)) {
/* 1455 */                      throw new FacebookException("At least one request in a batch must have an open Session, or a default app ID must be specified.");
                            }
/* 1459 */                  serializer.writeString(BATCH_APP_ID_PARAM, batchAppID);
/* 1463 */                  Bundle attachments = new Bundle();
/* 1464 */                  serializeRequestsAsJSON(serializer, requests, attachments);
/* 1466 */                  logger.append("  Attachments:\n");
/* 1467 */                  serializeAttachments(attachments, serializer);
                        }
/* 1470 */              outputStream.close();
/* 1473 */              logger.log();
                    } catch (IOException | JSONException | RuntimeException | Error ex) {
                        outputStream.close();
                        throw ex;
                    }
                }

/* 1483 */      private static void processGraphObject(GraphObject graphObject, String path, KeyValueSerializer serializer) throws IOException {
/* 1484 */          boolean isOGAction = false;
/* 1485 */          if (path.startsWith("me/") || path.startsWith("/me/")) {
/* 1486 */              int colonLocation = path.indexOf(":");
/* 1487 */              int questionMarkLocation = path.indexOf("?");
/* 1488 */              isOGAction = colonLocation > 3 && (questionMarkLocation == -1 || colonLocation < questionMarkLocation);
                    }
/* 1491 */          Set<Map.Entry<String, Object>> entries = graphObject.asMap().entrySet();
                    for (Map.Entry<String, Object> entry : entries) {
/* 1493 */              boolean passByValue = isOGAction && entry.getKey().equalsIgnoreCase("image");
/* 1494 */              processGraphObjectProperty(entry.getKey(), entry.getValue(), serializer, passByValue);
                    }
                }

/* 1499 */      private static void processGraphObjectProperty(String key, Object value, KeyValueSerializer serializer, boolean passByValue) throws IOException {
/* 1500 */          Class<?> valueClass = value.getClass();
/* 1501 */          if (GraphObject.class.isAssignableFrom(valueClass)) {
/* 1502 */              value = ((GraphObject) value).getInnerJSONObject();
/* 1503 */              valueClass = value.getClass();
                    } else if (GraphObjectList.class.isAssignableFrom(valueClass)) {
/* 1505 */              value = ((GraphObjectList) value).getInnerJSONArray();
/* 1506 */              valueClass = value.getClass();
                    }
/* 1509 */          if (JSONObject.class.isAssignableFrom(valueClass)) {
/* 1510 */              JSONObject jsonObject = (JSONObject) value;
/* 1511 */              if (passByValue) {
/* 1514 */                  Iterator<String> keys = jsonObject.keys();
/* 1515 */                  while (keys.hasNext()) {
/* 1516 */                      String propertyName = keys.next();
/* 1517 */                      String subKey = String.format("%s[%s]", key, propertyName);
/* 1518 */                      processGraphObjectProperty(subKey, jsonObject.opt(propertyName), serializer, passByValue);
                            }
/* 1550 */                  return;
                        }
/* 1523 */              if (jsonObject.has("id")) {
/* 1524 */                  processGraphObjectProperty(key, jsonObject.optString("id"), serializer, passByValue);
/* 1550 */                  return;
                        } else {
/* 1525 */                  if (jsonObject.has(PlusShare.KEY_CALL_TO_ACTION_URL)) {
/* 1526 */                      processGraphObjectProperty(key, jsonObject.optString(PlusShare.KEY_CALL_TO_ACTION_URL), serializer, passByValue);
/* 1550 */                      return;
                            }
/* 1550 */                  return;
                        }
                    }
/* 1529 */          if (JSONArray.class.isAssignableFrom(valueClass)) {
/* 1530 */              JSONArray jsonArray = (JSONArray) value;
/* 1531 */              int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
/* 1533 */                  String subKey2 = String.format("%s[%d]", key, Integer.valueOf(i));
/* 1534 */                  processGraphObjectProperty(subKey2, jsonArray.opt(i), serializer, passByValue);
                        }
/* 1550 */              return;
                    }
/* 1536 */          if (String.class.isAssignableFrom(valueClass) || Number.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
/* 1539 */              serializer.writeString(key, value.toString());
                    } else if (Date.class.isAssignableFrom(valueClass)) {
/* 1541 */              Date date = (Date) value;
/* 1547 */              SimpleDateFormat iso8601DateFormat = new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US);
/* 1548 */              serializer.writeString(key, iso8601DateFormat.format(date));
                    }
                }

/* 1552 */      private static void serializeParameters(Bundle bundle, Serializer serializer) throws IOException {
/* 1553 */          Set<String> keys = bundle.keySet();
                    for (String key : keys) {
/* 1556 */              Object value = bundle.get(key);
/* 1557 */              if (isSupportedParameterType(value)) {
/* 1558 */                  serializer.writeObject(key, value);
                        }
                    }
                }

/* 1563 */      private static void serializeAttachments(Bundle bundle, Serializer serializer) throws IOException {
/* 1564 */          Set<String> keys = bundle.keySet();
                    for (String key : keys) {
/* 1567 */              Object value = bundle.get(key);
/* 1568 */              if (isSupportedAttachmentType(value)) {
/* 1569 */                  serializer.writeObject(key, value);
                        }
                    }
                }

/* 1575 */      private static void serializeRequestsAsJSON(Serializer serializer, Collection<Request> requests, Bundle attachments) throws JSONException, IOException {
/* 1576 */          JSONArray batch = new JSONArray();
                    for (Request request : requests) {
/* 1578 */              request.serializeToBatch(batch, attachments);
                    }
/* 1581 */          String batchAsString = batch.toString();
/* 1582 */          serializer.writeString(BATCH_PARAM, batchAsString);
                }

/* 1585 */      private static String getMimeContentType() {
/* 1586 */          return String.format("multipart/form-data; boundary=%s", MIME_BOUNDARY);
                }

/* 1591 */      private static String getUserAgent() {
/* 1592 */          if (userAgent == null) {
/* 1593 */              userAgent = String.format("%s.%s", USER_AGENT_BASE, FacebookSdkVersion.BUILD);
                    }
/* 1596 */          return userAgent;
                }

/* 1599 */      private static String getBatchAppId(RequestBatch batch) {
/* 1600 */          if (!Utility.isNullOrEmpty(batch.getBatchApplicationId())) {
/* 1601 */              return batch.getBatchApplicationId();
                    }
                    for (Request request : batch) {
/* 1605 */              Session session = request.session;
/* 1606 */              if (session != null) {
/* 1607 */                  return session.getApplicationId();
                        }
                    }
/* 1610 */          return defaultBatchApplicationId;
                }

/* 1613 */      private static <T extends GraphObject> List<T> typedListFromResponse(Response response, Class<T> cls) {
                    GraphObjectList<GraphObject> data;
/* 1614 */          GraphMultiResult graphMultiResult = (GraphMultiResult) response.getGraphObjectAs(GraphMultiResult.class);
/* 1615 */          if (graphMultiResult == null || (data = graphMultiResult.getData()) == null) {
                        return null;
                    }
/* 1624 */          return data.castToListOf(cls);
                }

/* 1627 */      private static boolean isSupportedAttachmentType(Object value) {
                    return (value instanceof Bitmap) || (value instanceof byte[]) || (value instanceof ParcelFileDescriptor);
                }

/* 1631 */      private static boolean isSupportedParameterType(Object value) {
                    return (value instanceof String) || (value instanceof Boolean) || (value instanceof Number) || (value instanceof Date);
                }

/* 1636 */      private static String parameterToString(Object value) {
/* 1637 */          if (value instanceof String) {
/* 1638 */              return (String) value;
                    }
/* 1639 */          if ((value instanceof Boolean) || (value instanceof Number)) {
/* 1640 */              return value.toString();
                    }
/* 1641 */          if (value instanceof Date) {
/* 1642 */              SimpleDateFormat iso8601DateFormat = new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US);
/* 1643 */              return iso8601DateFormat.format(value);
                    }
/* 1645 */          throw new IllegalArgumentException("Unsupported parameter type.");
                }

/* 1655 */      private static class Serializer implements KeyValueSerializer {
                    private boolean firstWrite = true;
                    private final Logger logger;
                    private final BufferedOutputStream outputStream;

/* 1656 */          public Serializer(BufferedOutputStream outputStream, Logger logger) {
/* 1658 */              this.outputStream = outputStream;
/* 1659 */              this.logger = logger;
                    }

/* 1662 */          public void writeObject(String key, Object value) throws IOException {
/* 1663 */              if (Request.access$100(value)) {
/* 1664 */                  writeString(key, Request.access$200(value));
/* 1674 */                  return;
                        }
/* 1665 */              if (value instanceof Bitmap) {
/* 1666 */                  writeBitmap(key, (Bitmap) value);
                        } else if (value instanceof byte[]) {
/* 1668 */                  writeBytes(key, (byte[]) value);
                        } else {
/* 1669 */                  if (value instanceof ParcelFileDescriptor) {
/* 1670 */                      writeFile(key, (ParcelFileDescriptor) value);
/* 1674 */                      return;
                            }
/* 1672 */                  throw new IllegalArgumentException("value is not a supported type: String, Bitmap, byte[]");
                        }
                    }

                    @Override // com.facebook.Request.KeyValueSerializer
/* 1676 */          public void writeString(String key, String value) throws IOException {
/* 1677 */              writeContentDisposition(key, null, null);
/* 1678 */              writeLine("%s", value);
/* 1679 */              writeRecordBoundary();
/* 1680 */              if (this.logger != null) {
/* 1681 */                  this.logger.appendKeyValue("    " + key, value);
                        }
                    }

/* 1685 */          public void writeBitmap(String key, Bitmap bitmap) throws IOException {
/* 1686 */              writeContentDisposition(key, key, "image/png");
/* 1688 */              bitmap.compress(Bitmap.CompressFormat.PNG, 100, this.outputStream);
/* 1689 */              writeLine("", new Object[0]);
/* 1690 */              writeRecordBoundary();
/* 1691 */              this.logger.appendKeyValue("    " + key, "<Image>");
                    }

/* 1694 */          public void writeBytes(String key, byte[] bytes) throws IOException {
/* 1695 */              writeContentDisposition(key, key, "content/unknown");
/* 1696 */              this.outputStream.write(bytes);
/* 1697 */              writeLine("", new Object[0]);
/* 1698 */              writeRecordBoundary();
/* 1699 */              this.logger.appendKeyValue("    " + key, String.format("<Data: %d>", Integer.valueOf(bytes.length)));
                    }

/* 1702 */          public void writeFile(String key, ParcelFileDescriptor descriptor) throws IOException {
/* 1703 */              writeContentDisposition(key, key, "content/unknown");
                    ParcelFileDescriptor.AutoCloseInputStream inputStream = new ParcelFileDescriptor.AutoCloseInputStream(descriptor);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    try {
                        int totalBytes = 0;
                        byte[] buffer = new byte[8192];
                        while (true) {
                            int bytesRead = bufferedInputStream.read(buffer);
                            if (bytesRead == -1) {
                                break;
                            }
                            this.outputStream.write(buffer, 0, bytesRead);
                            totalBytes += bytesRead;
                        }
                        writeLine("", new Object[0]);
                        writeRecordBoundary();
                        this.logger.appendKeyValue("    " + key, String.format("<Data: %d>", Integer.valueOf(totalBytes)));
                    } finally {
                        bufferedInputStream.close();
                        inputStream.close();
                    }
                }

/* 1731 */          public void writeRecordBoundary() throws IOException {
/* 1732 */              writeLine("--%s", Request.MIME_BOUNDARY);
                    }

/* 1735 */          public void writeContentDisposition(String name, String filename, String contentType) throws IOException {
/* 1736 */              write("Content-Disposition: form-data; name=\"%s\"", name);
/* 1737 */              if (filename != null) {
/* 1738 */                  write("; filename=\"%s\"", filename);
                        }
/* 1740 */              writeLine("", new Object[0]);
/* 1741 */              if (contentType != null) {
/* 1742 */                  writeLine("%s: %s", Request.CONTENT_TYPE_HEADER, contentType);
                        }
/* 1744 */              writeLine("", new Object[0]);
                    }

/* 1747 */          public void write(String format, Object... args) throws IOException {
/* 1748 */              if (this.firstWrite) {
/* 1750 */                  this.outputStream.write(MultipartEntity.DASH_DASH.getBytes());
/* 1751 */                  this.outputStream.write(Request.MIME_BOUNDARY.getBytes());
/* 1752 */                  this.outputStream.write("\r\n".getBytes());
/* 1753 */                  this.firstWrite = false;
                        }
/* 1755 */              this.outputStream.write(String.format(format, args).getBytes());
                    }

/* 1758 */          public void writeLine(String format, Object... args) throws IOException {
/* 1759 */              write(format, args);
/* 1760 */              write("\r\n", new Object[0]);
                    }
                }
            }
