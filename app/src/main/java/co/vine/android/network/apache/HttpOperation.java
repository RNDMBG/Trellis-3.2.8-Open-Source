            package co.vine.android.network.apache;

            import android.support.annotation.NonNull;
            import android.util.Log;
            import co.vine.android.cache.text.TextKey;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.NetworkOperationReader;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.MeasureInputStream;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.URI;
            import java.util.Date;
            import java.util.zip.GZIPInputStream;
            import org.apache.http.Header;
            import org.apache.http.HttpEntity;
            import org.apache.http.HttpResponse;
            import org.apache.http.StatusLine;
            import org.apache.http.client.HttpClient;
            import org.apache.http.client.methods.HttpRequestBase;
            import twitter4j.internal.http.HttpResponseCode;
            
            
    public class HttpOperation extends NetworkOperation<HttpRequestBase, HttpClient, HttpRequestBase> {
                private final HttpRequestBase mHttpRequest;



        public HttpOperation(HttpOperationClient client, HttpRequestBase httpReq, NetworkOperationReader reader, VineAPI api) {
            super(reader, httpReq, client.getHttpClient(), api);
            setCacheStorage(api.getCacheStorage());
            setCacheKey(new TextKey(httpReq.getURI().toString()));
            this.mHttpRequest = httpReq;
                }

                
                @Override 
        public void addHeader(String name, String value) {
            ((HttpRequestBase) this.mHttpRequestBuilder).addHeader(name, value);
                }

                @Override 
        protected HttpRequestBase buildRequest() {
            return this.mHttpRequest;
                }

        private static class HttpCancelableRequest implements NetworkOperation.CancelableRequest {
                    private final HttpRequestBase request;

            public HttpCancelableRequest(HttpRequestBase request) {
                this.request = request;
                    }

                    @Override 
            public void cancel() {
                this.request.abort();
                    }

                    @Override 
            public boolean isCancelled() {
                return this.request.isAborted();
                    }
                }

                @Override 
        public NetworkOperation.CancelableRequest getCancelableRequest() {
           return new HttpCancelableRequest(this.mHttpRequest);
                }

                @NonNull
       protected NetworkOperation.ExecuteResult executeRequest(HttpRequestBase httpReq, int authRetry, int retries, UrlCachePolicy cachePolicy) throws Exception {
                    String contentEncoding;
                    String contentType;
           String uploadKey = null;
           long readTime = 0;
           long closeTime = 0;
           if (LOGGABLE) {
               Log.d("HttpOperation", httpReq.getMethod() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + httpReq.getURI());
                    }
           long before = System.currentTimeMillis();
                    try {
                        try {
                   Log.d("VineNetwork", "http execute start " + httpReq.getURI());
                   HttpResponse resp = ((HttpClient) this.mHttpClient).execute(httpReq);
                   StatusLine statusLine = resp.getStatusLine();
                   Log.d("VineNetwork", "http execute complete " + statusLine.getStatusCode() + " " + httpReq.getURI());
                   int statusCode = statusLine.getStatusCode();
                   long firstByte = System.currentTimeMillis() - before;
                   String reasonPhrase = statusLine.getReasonPhrase();
                   if (LOGGABLE) {
                       Log.d("HttpOperation", statusCode + "/" + reasonPhrase + " [" + httpReq.getURI() + "]");
                            }
                            switch (statusCode) {
                                case 200:
                           Header[] headers = resp.getHeaders("X-Upload-Key");
                           if (headers != null && headers.length > 0) {
                               uploadKey = headers[0].getValue();
                                    }
                                    break;
                                case 401:
                           Header dateHeader = resp.getLastHeader("Date");
                           if (dateHeader != null) {
                               Date d = CommonUtil.DATE_TIME_RFC1123.parse(dateHeader.getValue());
                               long ts = d.getTime();
                               long now = System.currentTimeMillis();
                               sTimeOffset.set(ts - now);
                                    }
                           if (authRetry > 0) {
                               authRetry--;
                               retries++;
                                    }
                                    break;
                                case 500:
                                case HttpResponseCode.BAD_GATEWAY :
                                case HttpResponseCode.SERVICE_UNAVAILABLE :
                                    break;
                                default:
                           retries = 0;
                                    break;
                            }
                   HttpEntity entity = resp.getEntity();
                   long openTime = System.currentTimeMillis() - before;
                   if (entity != null && entity.isStreaming()) {
                       if (entity.getContentEncoding() != null) {
                           contentEncoding = entity.getContentEncoding().getValue();
                                } else {
                           contentEncoding = null;
                                }
                       Header entityContentType = entity.getContentType();
                       if (entityContentType != null) {
                           contentType = entityContentType.getValue();
                                } else {
                           contentType = null;
                                }
                       int contentLength = (int) entity.getContentLength();
                       if (LOGGABLE) {
                           Log.d("HttpOperation", "read: Encoding: " + contentEncoding + ", type: " + contentType + ", length: " + contentLength);
                                }
                                try {
                           MeasureInputStream measuredIn = new MeasureInputStream(entity.getContent());
                           InputStream in = measuredIn;
                           if (contentType != null && !contentType.startsWith("application/octet-stream") && !contentType.startsWith("video/avc") && !contentType.startsWith(MimeTypes.VIDEO_WEBM) && !contentType.startsWith(MimeTypes.VIDEO_MP4) && !contentType.startsWith("binary/octet-stream")) {
                               if (contentType.startsWith(CommonUtil.ContentType.APPLICATION_JSON)) {
                                   if (contentEncoding != null && contentEncoding.equals("gzip")) {
                                       contentLength = -1;
                                       in = new GZIPInputStream(in);
                                            }
                                        } else if (contentType.startsWith("text/html") || contentType.startsWith("text/plain") || contentType.startsWith("text/xml") || contentType.startsWith("application/xml") || contentType.startsWith("image/")) {
                                   if (contentEncoding != null && contentEncoding.equals("gzip")) {
                                       contentLength = -1;
                                       in = new GZIPInputStream(in);
                                            }
                                        } else {
                                   throw new IOException("Unsupported content type: " + contentType);
                                        }
                                    }
                           onRemoteInputStreamReady(in, cachePolicy, statusCode, contentLength);
                           readTime = measuredIn.getReadTime();
                           if (SLog.sLogsOn && RATE_LIMIT_BYTES_PER_MS != -1.0d) {
                               long rateLimitLeft = (long) ((((double) contentLength) / RATE_LIMIT_BYTES_PER_MS) - readTime);
                                        if (rateLimitLeft > 0) {
                                   SLog.m447d("Rate limiting: {}ms.", Long.valueOf(rateLimitLeft));
                                   Thread.sleep(rateLimitLeft);
                                        }
                                    }
                           long now2 = System.currentTimeMillis();
                           CommonUtil.closeSilently(in);
                           closeTime = System.currentTimeMillis() - now2;
                                } catch (Throwable th) {
                                    long now3 = System.currentTimeMillis();
                           CommonUtil.closeSilently(null);
                           long closeTime2 = System.currentTimeMillis() - now3;
                           throw th;
                                }
                            }
                   long durationMs = openTime + readTime + closeTime;
                            if (firstByte != -1 && (SLog.sLogsOn || Math.random() > 0.1d)) {
                       URI uri = httpReq.getURI();
                       SLog.m453d("{}, {} took {} ms to open and {} total.", new Object[]{uri.getHost(), uri.getPath(), Long.valueOf(firstByte), Long.valueOf(durationMs)});
                       FlurryUtils.trackRespondTime(uri.getHost(), uri.getPath(), firstByte, durationMs, uri.toString().contains("/r/videos/"));
                            }
                   HttpResult result = new HttpResult(statusCode, reasonPhrase);
                   result.exception = null;
                   result.durationMs = durationMs;
                   result.uploadKey = uploadKey;
                   return new NetworkOperation.ExecuteResult(result, retries, authRetry);
                        } catch (Exception ex) {
                   if (LOGGABLE) {
                       Log.d("HttpOperation", "[" + httpReq.getURI() + "]", ex);
                            }
                   httpReq.abort();
                   throw ex;
                        }
                    } catch (Throwable th2) {
               long durationMs2 = 0 + 0 + 0;
                        if (-1 != -1 && (SLog.sLogsOn || Math.random() > 0.1d)) {
                   URI uri2 = httpReq.getURI();
                   SLog.m453d("{}, {} took {} ms to open and {} total.", new Object[]{uri2.getHost(), uri2.getPath(), -1L, Long.valueOf(durationMs2)});
                   FlurryUtils.trackRespondTime(uri2.getHost(), uri2.getPath(), -1L, durationMs2, uri2.toString().contains("/r/videos/"));
                        }
               throw th2;
                    }
                }
            }
