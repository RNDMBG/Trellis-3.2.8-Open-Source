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
            
            
    public class Response {
                static final  boolean $assertionsDisabled;
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
            $assertionsDisabled = !Response.class.desiredAssertionStatus();
                }

        Response(Request request, HttpURLConnection connection, GraphObject graphObject, boolean isFromCache) {
            this.request = request;
            this.connection = connection;
            this.graphObject = graphObject;
            this.graphObjectList = null;
            this.isFromCache = isFromCache;
            this.error = null;
                }

        Response(Request request, HttpURLConnection connection, GraphObjectList<GraphObject> graphObjects, boolean isFromCache) {
            this.request = request;
            this.connection = connection;
            this.graphObject = null;
            this.graphObjectList = graphObjects;
            this.isFromCache = isFromCache;
            this.error = null;
                }

        Response(Request request, HttpURLConnection connection, FacebookRequestError error) {
            this.request = request;
            this.connection = connection;
            this.graphObject = null;
            this.graphObjectList = null;
            this.isFromCache = false;
            this.error = error;
                }

        public final FacebookRequestError getError() {
            return this.error;
                }

       public final GraphObject getGraphObject() {
           return this.graphObject;
                }

       public final <T extends GraphObject> T getGraphObjectAs(Class<T> cls) {
           if (this.graphObject == null) {
               return null;
                    }
           if (cls == null) {
               throw new NullPointerException("Must pass in a valid interface that extends GraphObject");
                    }
           return (T) this.graphObject.cast(cls);
                }

       public final GraphObjectList<GraphObject> getGraphObjectList() {
           return this.graphObjectList;
                }

       public final <T extends GraphObject> GraphObjectList<T> getGraphObjectListAs(Class<T> cls) {
           if (this.graphObjectList == null) {
               return null;
                    }
                    return (GraphObjectList<T>) this.graphObjectList.castToListOf(cls);
                }

       public final HttpURLConnection getConnection() {
           return this.connection;
                }

       public Request getRequest() {
           return this.request;
                }

       public Request getRequestForPagedResults(PagingDirection direction) {
           String link = null;
           if (this.graphObject != null) {
               PagedResults pagedResults = (PagedResults) this.graphObject.cast(PagedResults.class);
               PagingInfo pagingInfo = pagedResults.getPaging();
               if (pagingInfo != null) {
                   link = direction == PagingDirection.NEXT ? pagingInfo.getNext() : pagingInfo.getPrevious();
                        }
                    }
           if (Utility.isNullOrEmpty(link)) {
                        return null;
                    }
           if (link != null && link.equals(this.request.getUrlForSingleRequest())) {
                        return null;
                    }
                    try {
               return new Request(this.request.getSession(), new URL(link));
                    } catch (MalformedURLException e) {
                        return null;
                    }
                }

       public String toString() {
                    String responseCode;
                    try {
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : 200);
               responseCode = String.format("%d", objArr);
                    } catch (IOException e) {
               responseCode = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
           return "{Response:  responseCode: " + responseCode + ", graphObject: " + this.graphObject + ", error: " + this.error + ", isFromCache:" + this.isFromCache + "}";
                }

       public final boolean getIsFromCache() {
           return this.isFromCache;
                }

       static FileLruCache getResponseCache() {
                    Context applicationContext;
           if (responseCache == null && (applicationContext = Session.getStaticContext()) != null) {
               responseCache = new FileLruCache(applicationContext, RESPONSE_CACHE_TAG, new FileLruCache.Limits());
                    }
           return responseCache;
                }

                
                
                
                






























       static List<Response> fromHttpConnection(HttpURLConnection connection, RequestBatch requests) throws IOException, JSONException, FacebookException {
                    List<Response> listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException("Unable to read response"));
                    InputStream interceptStream = null;
           InputStream stream = null;
           FileLruCache cache = null;
           String cacheKey = null;
           if (requests instanceof CacheableRequestBatch) {
               CacheableRequestBatch cacheableRequestBatch = (CacheableRequestBatch) requests;
               cache = getResponseCache();
               cacheKey = cacheableRequestBatch.getCacheKeyOverride();
               if (Utility.isNullOrEmpty(cacheKey)) {
                   if (requests.size() == 1) {
                       cacheKey = requests.get(0).getUrlForSingleRequest();
                            } else {
                       Logger.log(LoggingBehavior.REQUESTS, RESPONSE_CACHE_TAG, "Not using cache for cacheable request because no key was specified");
                            }
                        }
               if (cacheableRequestBatch.getForceRoundTrip() || cache == null || Utility.isNullOrEmpty(cacheKey)) {
                            try {
                                try {
                                    try {
                                        try {
                                   if (connection.getResponseCode() >= 400) {
                                       stream = connection.getErrorStream();
                                            } else {
                                       stream = connection.getInputStream();
                                       if (cache != null && cacheKey != null && stream != null && (interceptStream = cache.interceptAndPut(cacheKey, stream)) != null) {
                                           stream = interceptStream;
                                                }
                                            }
                                   listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
                                   Utility.closeQuietly(stream);
                                        } catch (FacebookException facebookException) {
                                   Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", facebookException);
                                   listConstructErrorResponses = constructErrorResponses(requests, connection, facebookException);
                                   Utility.closeQuietly(stream);
                                        }
                                    } catch (JSONException exception) {
                               Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception);
                               listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException(exception));
                               Utility.closeQuietly(stream);
                                    }
                                } catch (IOException exception2) {
                           Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception2);
                           listConstructErrorResponses = constructErrorResponses(requests, connection, new FacebookException(exception2));
                           Utility.closeQuietly(stream);
                                }
                            } catch (RuntimeException ex) {
                                Utility.closeQuietly(stream);
                                throw ex;
                            }
                        } else {
                            try {
                       stream = cache.get(cacheKey);
                       if (stream != null) {
                           listConstructErrorResponses = createResponsesFromStream(stream, null, requests, true);
                           Utility.closeQuietly(stream);
                                } else {
                           Utility.closeQuietly(stream);
                           if (connection.getResponseCode() >= 400) {
                               stream = connection.getErrorStream();
                                    } else {
                               stream = connection.getInputStream();
                               if (cache != null) {
                                   stream = interceptStream;
                                        }
                                    }
                           listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
                           Utility.closeQuietly(stream);
                                }
                            } catch (FacebookException e) {
                       Utility.closeQuietly(stream);
                            } catch (IOException e2) {
                       Utility.closeQuietly(stream);
                            } catch (JSONException e3) {
                       Utility.closeQuietly(stream);
                            } catch (RuntimeException ex) {
                                Utility.closeQuietly(stream);
                                throw ex;
                            }
                        }
                    } else {
               if (connection.getResponseCode() >= 400) {
                   stream = connection.getErrorStream();
                        } else {
                   stream = connection.getInputStream();
                   if (cache != null) {
                       stream = interceptStream;
                            }
                        }
               listConstructErrorResponses = createResponsesFromStream(stream, connection, requests, false);
               Utility.closeQuietly(stream);
                    }
           return listConstructErrorResponses;
                }

       static List<Response> createResponsesFromStream(InputStream stream, HttpURLConnection connection, RequestBatch requests, boolean isFromCache) throws IOException, JSONException, FacebookException {
           String responseString = Utility.readStreamToString(stream);
           Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(responseString.length()), responseString);
           return createResponsesFromString(responseString, connection, requests, isFromCache);
                }

       static List<Response> createResponsesFromString(String responseString, HttpURLConnection connection, RequestBatch requests, boolean isFromCache) throws JSONException, FacebookException, IOException {
           JSONTokener tokener = new JSONTokener(responseString);
           Object resultObject = tokener.nextValue();
           List<Response> responses = createResponsesFromObject(connection, requests, resultObject, isFromCache);
           Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requests.getId(), Integer.valueOf(responseString.length()), responses);
           return responses;
                }

       private static List<Response> createResponsesFromObject(HttpURLConnection connection, List<Request> requests, Object object, boolean isFromCache) throws JSONException, FacebookException {
           if (!$assertionsDisabled && connection == null && !isFromCache) {
                        throw new AssertionError();
                    }
           int numRequests = requests.size();
           List<Response> responses = new ArrayList<>(numRequests);
           if (numRequests == 1) {
               Request request = requests.get(0);
                        try {
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("body", object);
                   int responseCode = connection != null ? connection.getResponseCode() : 200;
                   jsonObject.put("code", responseCode);
                   JSONArray jsonArray = new JSONArray();
                   jsonArray.put(jsonObject);
                   object = jsonArray;
                        } catch (IOException e) {
                   responses.add(new Response(request, connection, new FacebookRequestError(connection, e)));
                        } catch (JSONException e2) {
                   responses.add(new Response(request, connection, new FacebookRequestError(connection, e2)));
                        }
                    }
           if (!(object instanceof JSONArray) || ((JSONArray) object).length() != numRequests) {
               FacebookException exception = new FacebookException("Unexpected number of results");
               throw exception;
                    }
           JSONArray jsonArray2 = (JSONArray) object;
                    for (int i = 0; i < jsonArray2.length(); i++) {
               Request request2 = requests.get(i);
                        try {
                   Object obj = jsonArray2.get(i);
                   responses.add(createResponseFromObject(request2, connection, obj, isFromCache, object));
                        } catch (FacebookException e3) {
                   responses.add(new Response(request2, connection, new FacebookRequestError(connection, e3)));
                        } catch (JSONException e4) {
                   responses.add(new Response(request2, connection, new FacebookRequestError(connection, e4)));
                        }
                    }
           return responses;
                }

       private static Response createResponseFromObject(Request request, HttpURLConnection connection, Object object, boolean isFromCache, Object originalResult) throws JSONException {
                    Session session;
           if (object instanceof JSONObject) {
               JSONObject jsonObject = (JSONObject) object;
               FacebookRequestError error = FacebookRequestError.checkResponseAndCreateError(jsonObject, originalResult, connection);
               if (error != null) {
                   if (error.getErrorCode() == INVALID_SESSION_FACEBOOK_ERROR_CODE && (session = request.getSession()) != null) {
                       session.closeAndClearTokenInformation();
                            }
                   return new Response(request, connection, error);
                        }
               Object body = Utility.getStringPropertyAsJSON(jsonObject, "body", NON_JSON_RESPONSE_PROPERTY);
               if (body instanceof JSONObject) {
                   GraphObject graphObject = GraphObject.Factory.create((JSONObject) body);
                   return new Response(request, connection, graphObject, isFromCache);
                        }
               if (body instanceof JSONArray) {
                   GraphObjectList<GraphObject> graphObjectList = GraphObject.Factory.createList((JSONArray) body, GraphObject.class);
                   return new Response(request, connection, graphObjectList, isFromCache);
                        }
               object = JSONObject.NULL;
                    }
           if (object == JSONObject.NULL) {
               return new Response(request, connection, (GraphObject) null, isFromCache);
                    }
           throw new FacebookException("Got unexpected object type in response, class: " + object.getClass().getSimpleName());
                }

       static List<Response> constructErrorResponses(List<Request> requests, HttpURLConnection connection, FacebookException error) {
           int count = requests.size();
           List<Response> responses = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
               Response response = new Response(requests.get(i), connection, new FacebookRequestError(connection, error));
               responses.add(response);
                    }
           return responses;
                }
            }
