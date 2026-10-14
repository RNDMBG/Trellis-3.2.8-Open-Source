            package com.facebook;

            import android.content.Context;
            import android.support.v4.os.EnvironmentCompat;
            import com.facebook.internal.CacheableRequestBatch;
            import com.facebook.internal.FileLruCache;
            import com.facebook.internal.Logger;
            import com.facebook.internal.Utility;
            import com.facebook.model.GraphObject;
            import com.facebook.model.GraphObjectList;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.MalformedURLException;
            import java.net.URL;
            import java.util.ArrayList;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            import org.json.JSONTokener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class Response {
                static final /* synthetic */ boolean $assertionsDisabled;
                private static final String BODY_KEY = "body";
                private static final String CODE_KEY = "code";
                private static final int INVALID_SESSION_FACEBOOK_ERROR_CODE = 190;
                public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
                private static final String RESPONSE_CACHE_TAG = "ResponseCache";
                private static final String RESPONSE_LOG_TAG = "Response";
                private static FileLruCache responseCache;
                private final HttpURLConnection connection;
                private final FacebookRequestError error;
                private final GraphObject graphObject;
                private final GraphObjectList<GraphObject> graphObjectList;
                private final boolean isFromCache;
                private final Request request;

                interface PagedResults extends GraphObject {
                    GraphObjectList<GraphObject> getData();

                    PagingInfo getPaging();
                }

                public enum PagingDirection {
                    NEXT,
                    PREVIOUS
                }

                interface PagingInfo extends GraphObject {
                    String getNext();

                    String getPrevious();
                }

                static {
/* 39 */            $assertionsDisabled = !Response.class.desiredAssertionStatus();
                }

/* 63 */        Response(Request request, HttpURLConnection connection, GraphObject graphObject, boolean isFromCache) {
/* 65 */            this.request = request;
/* 66 */            this.connection = connection;
/* 67 */            this.graphObject = graphObject;
/* 68 */            this.graphObjectList = null;
/* 69 */            this.isFromCache = isFromCache;
/* 70 */            this.error = null;
                }

/* 73 */        Response(Request request, HttpURLConnection connection, GraphObjectList<GraphObject> graphObjects, boolean isFromCache) {
/* 75 */            this.request = request;
/* 76 */            this.connection = connection;
/* 77 */            this.graphObject = null;
/* 78 */            this.graphObjectList = graphObjects;
/* 79 */            this.isFromCache = isFromCache;
/* 80 */            this.error = null;
                }

/* 82 */        Response(Request request, HttpURLConnection connection, FacebookRequestError error) {
/* 84 */            this.request = request;
/* 85 */            this.connection = connection;
/* 86 */            this.graphObject = null;
/* 87 */            this.graphObjectList = null;
/* 88 */            this.isFromCache = false;
/* 89 */            this.error = error;
                }

/* 97 */        public final FacebookRequestError getError() {
/* 98 */            return this.error;
                }

/* 106 */       public final GraphObject getGraphObject() {
/* 107 */           return this.graphObject;
                }

/* 117 */       public final <T extends GraphObject> T getGraphObjectAs(Class<T> cls) {
/* 118 */           if (this.graphObject == null) {
/* 119 */               return null;
                    }
/* 121 */           if (cls == null) {
/* 122 */               throw new NullPointerException("Must pass in a valid interface that extends GraphObject");
                    }
/* 124 */           return (T) this.graphObject.cast(cls);
                }

/* 132 */       public final GraphObjectList<GraphObject> getGraphObjectList() {
/* 133 */           return this.graphObjectList;
                }

/* 143 */       public final <T extends GraphObject> GraphObjectList<T> getGraphObjectListAs(Class<T> cls) {
/* 144 */           if (this.graphObjectList == null) {
/* 145 */               return null;
                    }
                    return (GraphObjectList<T>) this.graphObjectList.castToListOf(cls);
                }

/* 156 */       public final HttpURLConnection getConnection() {
/* 157 */           return this.connection;
                }

/* 165 */       public Request getRequest() {
/* 166 */           return this.request;
                }

/* 192 */       public Request getRequestForPagedResults(PagingDirection direction) {
/* 193 */           String link = null;
/* 194 */           if (this.graphObject != null) {
/* 195 */               PagedResults pagedResults = (PagedResults) this.graphObject.cast(PagedResults.class);
/* 196 */               PagingInfo pagingInfo = pagedResults.getPaging();
/* 197 */               if (pagingInfo != null) {
/* 201 */                   link = direction == PagingDirection.NEXT ? pagingInfo.getNext() : pagingInfo.getPrevious();
                        }
                    }
/* 205 */           if (Utility.isNullOrEmpty(link)) {
                        return null;
                    }
/* 209 */           if (link != null && link.equals(this.request.getUrlForSingleRequest())) {
                        return null;
                    }
                    try {
/* 217 */               return new Request(this.request.getSession(), new URL(link));
                    } catch (MalformedURLException e) {
                        return null;
                    }
                }

/* 231 */       public String toString() {
                    String responseCode;
                    try {
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : 200);
/* 232 */               responseCode = String.format("%d", objArr);
                    } catch (IOException e) {
/* 234 */               responseCode = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
/* 237 */           return "{Response:  responseCode: " + responseCode + ", graphObject: " + this.graphObject + ", error: " + this.error + ", isFromCache:" + this.isFromCache + "}";
                }

/* 245 */       public final boolean getIsFromCache() {
/* 246 */           return this.isFromCache;
                }

/* 249 */       static FileLruCache getResponseCache() {
                    Context applicationContext;
/* 250 */           if (responseCache == null && (applicationContext = Session.getStaticContext()) != null) {
/* 253 */               responseCache = new FileLruCache(applicationContext, RESPONSE_CACHE_TAG, new FileLruCache.Limits());
                    }
/* 257 */           return responseCache;
                }

                /* JADX WARN: Code duplicated, block: B:23:0x005b A[Catch: FacebookException -> 0x008e, JSONException -> 0x00a6, IOException -> 0x00c3, all -> 0x00e1, TryCatch #1 {FacebookException -> 0x008e, blocks: (B:21:0x0053, B:23:0x005b, B:24:0x005f, B:35:0x007c, B:39:0x0086), top: B:54:0x0053, outer: #0 }] */
                /* JADX WARN: Code duplicated, block: B:35:0x007c A[Catch: FacebookException -> 0x008e, JSONException -> 0x00a6, IOException -> 0x00c3, all -> 0x00e1, TRY_ENTER, TryCatch #1 {FacebookException -> 0x008e, blocks: (B:21:0x0053, B:23:0x005b, B:24:0x005f, B:35:0x007c, B:39:0x0086), top: B:54:0x0053, outer: #0 }] */
                /* JADX WARN: Code duplicated, block: B:37:0x0082 A[ADDED_TO_REGION] */
                /* JADX WARN: Code duplicated, block: B:54:0x0053 A[EXC_TOP_SPLITTER, PHI: r0 r1 r6
  0x0053: PHI (r0v1 'cache' com.facebook.internal.FileLruCache) = 
  (r0v0 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
  (r0v2 'cache' com.facebook.internal.FileLruCache)
 binds: [B:3:0x0007, B:10:0x002c, B:11:0x002e, B:13:0x0034, B:29:0x006e, B:31:0x0073, B:27:0x0069, B:20:0x0050] A[DONT_GENERATE, DONT_INLINE]
  0x0053: PHI (r1v1 'cacheKey' java.lang.String) = 
  (r1v0 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
  (r1v3 'cacheKey' java.lang.String)
 binds: [B:3:0x0007, B:10:0x002c, B:11:0x002e, B:13:0x0034, B:29:0x006e, B:31:0x0073, B:27:0x0069, B:20:0x0050] A[DONT_GENERATE, DONT_INLINE]
  0x0053: PHI (r6v1 'stream' java.io.InputStream) = 
  (r6v0 'stream' java.io.InputStream)
  (r6v0 'stream' java.io.InputStream)
  (r6v0 'stream' java.io.InputStream)
  (r6v0 'stream' java.io.InputStream)
  (r6v11 'stream' java.io.InputStream)
  (r6v12 'stream' java.io.InputStream)
  (r6v13 'stream' java.io.InputStream)
  (r6v14 'stream' java.io.InputStream)
 binds: [B:3:0x0007, B:10:0x002c, B:11:0x002e, B:13:0x0034, B:29:0x006e, B:31:0x0073, B:27:0x0069, B:20:0x0050] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
/* 261 */       static List<Response> fromHttpConnection(HttpURLConnection connection, RequestBatch requests) throws IOException, JSONException, FacebookException {
                    List<Response> listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException("Unable to read response"));
                    InputStream interceptStream = null;
/* 262 */           InputStream stream = null;
/* 264 */           FileLruCache cache = null;
/* 265 */           String cacheKey = null;
/* 266 */           if (requests instanceof CacheableRequestBatch) {
/* 267 */               CacheableRequestBatch cacheableRequestBatch = (CacheableRequestBatch) requests;
/* 268 */               cache = getResponseCache();
/* 269 */               cacheKey = cacheableRequestBatch.getCacheKeyOverride();
/* 270 */               if (Utility.isNullOrEmpty(cacheKey)) {
/* 271 */                   if (requests.size() == 1) {
/* 273 */                       cacheKey = requests.get(0).getUrlForSingleRequest();
                            } else {
/* 275 */                       Logger.log(LoggingBehavior.REQUESTS, RESPONSE_CACHE_TAG, "Not using cache for cacheable request because no key was specified");
                            }
                        }
/* 281 */               if (cacheableRequestBatch.getForceRoundTrip() || cache == null || Utility.isNullOrEmpty(cacheKey)) {
                            try {
                                try {
                                    try {
                                        try {
/* 298 */                                   if (connection.getResponseCode() >= 400) {
/* 299 */                                       stream = connection.getErrorStream();
                                            } else {
/* 301 */                                       stream = connection.getInputStream();
/* 302 */                                       if (cache != null && cacheKey != null && stream != null && (interceptStream = cache.interceptAndPut(cacheKey, stream)) != null) {
/* 305 */                                           stream = interceptStream;
                                                }
                                            }
/* 310 */                                   listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
/* 321 */                                   Utility.closeQuietly(stream);
                                        } catch (FacebookException facebookException) {
/* 312 */                                   Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", facebookException);
/* 313 */                                   listConstructErrorResponses = constructErrorResponses(requests, connection, facebookException);
/* 321 */                                   Utility.closeQuietly(stream);
                                        }
                                    } catch (JSONException exception) {
/* 315 */                               Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception);
/* 316 */                               listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException(exception));
/* 321 */                               Utility.closeQuietly(stream);
                                    }
                                } catch (IOException exception2) {
/* 318 */                           Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception2);
/* 319 */                           listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException(exception2));
/* 321 */                           Utility.closeQuietly(stream);
                                }
                            } catch (RuntimeException ex) {
                                Utility.closeQuietly(stream);
                                throw ex;
                            }
                        } else {
                            try {
/* 283 */                       stream = cache.get(cacheKey);
/* 284 */                       if (stream != null) {
/* 285 */                           listConstructErrorResponses = createResponsesFromStream(stream, null, requests, true);
/* 291 */                           Utility.closeQuietly(stream);
                                } else {
/* 291 */                           Utility.closeQuietly(stream);
/* 298 */                           if (connection.getResponseCode() >= 400) {
/* 299 */                               stream = connection.getErrorStream();
                                    } else {
/* 301 */                               stream = connection.getInputStream();
/* 302 */                               if (cache != null) {
/* 305 */                                   stream = interceptStream;
                                        }
                                    }
/* 310 */                           listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
/* 321 */                           Utility.closeQuietly(stream);
                                }
                            } catch (FacebookException e) {
/* 291 */                       Utility.closeQuietly(stream);
                            } catch (IOException e2) {
/* 291 */                       Utility.closeQuietly(stream);
                            } catch (JSONException e3) {
/* 291 */                       Utility.closeQuietly(stream);
                            } catch (RuntimeException ex) {
                                Utility.closeQuietly(stream);
                                throw ex;
                            }
                        }
                    } else {
/* 298 */               if (connection.getResponseCode() >= 400) {
/* 299 */                   stream = connection.getErrorStream();
                        } else {
/* 301 */                   stream = connection.getInputStream();
/* 302 */                   if (cache != null) {
/* 305 */                       stream = interceptStream;
                            }
                        }
/* 310 */               listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
/* 321 */               Utility.closeQuietly(stream);
                    }
/* 321 */           return listConstructErrorResponses;
                }

/* 327 */       static List<Response> createResponsesFromStream(InputStream stream, HttpURLConnection connection, RequestBatch requests, boolean isFromCache) throws IOException, JSONException, FacebookException {
/* 328 */           String responseString = Utility.readStreamToString(stream);
/* 329 */           Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(responseString.length()), responseString);
/* 333 */           return createResponsesFromString(responseString, connection, requests, isFromCache);
                }

/* 337 */       static List<Response> createResponsesFromString(String responseString, HttpURLConnection connection, RequestBatch requests, boolean isFromCache) throws JSONException, FacebookException, IOException {
/* 338 */           JSONTokener tokener = new JSONTokener(responseString);
/* 339 */           Object resultObject = tokener.nextValue();
/* 341 */           List<Response> responses = createResponsesFromObject(connection, requests, resultObject, isFromCache);
/* 342 */           Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requests.getId(), Integer.valueOf(responseString.length()), responses);
/* 345 */           return responses;
                }

/* 349 */       private static List<Response> createResponsesFromObject(HttpURLConnection connection, List<Request> requests, Object object, boolean isFromCache) throws JSONException, FacebookException {
/* 350 */           if (!$assertionsDisabled && connection == null && !isFromCache) {
                        throw new AssertionError();
                    }
/* 352 */           int numRequests = requests.size();
/* 353 */           List<Response> responses = new ArrayList<>(numRequests);
/* 356 */           if (numRequests == 1) {
/* 357 */               Request request = requests.get(0);
                        try {
/* 362 */                   JSONObject jsonObject = new JSONObject();
/* 363 */                   jsonObject.put("body", object);
/* 364 */                   int responseCode = connection != null ? connection.getResponseCode() : 200;
/* 365 */                   jsonObject.put("code", responseCode);
/* 367 */                   JSONArray jsonArray = new JSONArray();
/* 368 */                   jsonArray.put(jsonObject);
/* 371 */                   object = jsonArray;
                        } catch (IOException e) {
/* 375 */                   responses.add(new Response(request, connection, new FacebookRequestError(connection, e)));
                        } catch (JSONException e2) {
/* 373 */                   responses.add(new Response(request, connection, new FacebookRequestError(connection, e2)));
                        }
                    }
/* 379 */           if (!(object instanceof JSONArray) || ((JSONArray) object).length() != numRequests) {
/* 380 */               FacebookException exception = new FacebookException("Unexpected number of results");
/* 381 */               throw exception;
                    }
/* 384 */           JSONArray jsonArray2 = (JSONArray) object;
                    for (int i = 0; i < jsonArray2.length(); i++) {
/* 387 */               Request request2 = requests.get(i);
                        try {
/* 389 */                   Object obj = jsonArray2.get(i);
/* 354 */                   responses.add(createResponseFromObject(request2, connection, obj, isFromCache, object));
                        } catch (FacebookException e3) {
/* 394 */                   responses.add(new Response(request2, connection, new FacebookRequestError(connection, e3)));
                        } catch (JSONException e4) {
/* 392 */                   responses.add(new Response(request2, connection, new FacebookRequestError(connection, e4)));
                        }
                    }
/* 398 */           return responses;
                }

/* 402 */       private static Response createResponseFromObject(Request request, HttpURLConnection connection, Object object, boolean isFromCache, Object originalResult) throws JSONException {
                    Session session;
/* 403 */           if (object instanceof JSONObject) {
/* 404 */               JSONObject jsonObject = (JSONObject) object;
/* 406 */               FacebookRequestError error = FacebookRequestError.checkResponseAndCreateError(jsonObject, originalResult, connection);
/* 408 */               if (error != null) {
/* 409 */                   if (error.getErrorCode() == INVALID_SESSION_FACEBOOK_ERROR_CODE && (session = request.getSession()) != null) {
/* 412 */                       session.closeAndClearTokenInformation();
                            }
/* 415 */                   return new Response(request, connection, error);
                        }
/* 418 */               Object body = Utility.getStringPropertyAsJSON(jsonObject, "body", NON_JSON_RESPONSE_PROPERTY);
/* 420 */               if (body instanceof JSONObject) {
/* 421 */                   GraphObject graphObject = GraphObject.Factory.create((JSONObject) body);
/* 422 */                   return new Response(request, connection, graphObject, isFromCache);
                        }
/* 423 */               if (body instanceof JSONArray) {
/* 424 */                   GraphObjectList<GraphObject> graphObjectList = GraphObject.Factory.createList((JSONArray) body, GraphObject.class);
/* 426 */                   return new Response(request, connection, graphObjectList, isFromCache);
                        }
/* 429 */               object = JSONObject.NULL;
                    }
/* 432 */           if (object == JSONObject.NULL) {
/* 433 */               return new Response(request, connection, (GraphObject) null, isFromCache);
                    }
/* 435 */           throw new FacebookException("Got unexpected object type in response, class: " + object.getClass().getSimpleName());
                }

/* 441 */       static List<Response> constructErrorResponses(List<Request> requests, HttpURLConnection connection, FacebookException error) {
/* 442 */           int count = requests.size();
/* 443 */           List<Response> responses = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
/* 445 */               Response response = new Response(requests.get(i), connection, new FacebookRequestError(connection, error));
/* 446 */               responses.add(response);
                    }
/* 448 */           return responses;
                }
            }
