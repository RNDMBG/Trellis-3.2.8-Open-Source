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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class HttpOperation extends NetworkOperation<HttpRequestBase, HttpClient, HttpRequestBase> {
                private final HttpRequestBase mHttpRequest;



/* 62 */        public HttpOperation(HttpOperationClient client, HttpRequestBase httpReq, NetworkOperationReader reader, VineAPI api) {
/* 63 */            super(reader, httpReq, client.getHttpClient(), api);
/* 64 */            setCacheStorage(api.getCacheStorage());
/* 65 */            setCacheKey(new TextKey(httpReq.getURI().toString()));
/* 66 */            this.mHttpRequest = httpReq;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // co.vine.android.network.NetworkOperation
/* 70 */        public void addHeader(String name, String value) {
/* 71 */            ((HttpRequestBase) this.mHttpRequestBuilder).addHeader(name, value);
                }

                @Override // co.vine.android.network.NetworkOperation
/* 75 */        protected HttpRequestBase buildRequest() {
/* 76 */            return this.mHttpRequest;
                }

/* 81 */        private static class HttpCancelableRequest implements NetworkOperation.CancelableRequest {
                    private final HttpRequestBase request;

/* 82 */            public HttpCancelableRequest(HttpRequestBase request) {
/* 84 */                this.request = request;
                    }

                    @Override // co.vine.android.network.NetworkOperation.CancelableRequest
/* 88 */            public void cancel() {
/* 89 */                this.request.abort();
                    }

                    @Override // co.vine.android.network.NetworkOperation.CancelableRequest
/* 93 */            public boolean isCancelled() {
/* 94 */                return this.request.isAborted();
                    }
                }

                @Override // co.vine.android.network.NetworkOperation
/* 99 */        public NetworkOperation.CancelableRequest getCancelableRequest() {
/* 100 */           return new HttpCancelableRequest(this.mHttpRequest);
                }

                @NonNull
/* 107 */       protected NetworkOperation.ExecuteResult executeRequest(HttpRequestBase httpReq, int authRetry, int retries, UrlCachePolicy cachePolicy) throws Exception {
                    String contentEncoding;
                    String contentType;
/* 109 */           String uploadKey = null;
/* 113 */           long readTime = 0;
/* 115 */           long closeTime = 0;
/* 119 */           if (LOGGABLE) {
/* 120 */               Log.d("HttpOperation", httpReq.getMethod() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + httpReq.getURI());
                    }
/* 124 */           long before = System.currentTimeMillis();
                    try {
                        try {
/* 128 */                   Log.d("VineNetwork", "http execute start " + httpReq.getURI());
/* 129 */                   HttpResponse resp = ((HttpClient) this.mHttpClient).execute(httpReq);
/* 129 */                   StatusLine statusLine = resp.getStatusLine();
/* 130 */                   Log.d("VineNetwork", "http execute complete " + statusLine.getStatusCode() + " " + httpReq.getURI());
/* 130 */                   int statusCode = statusLine.getStatusCode();
/* 132 */                   long firstByte = System.currentTimeMillis() - before;
/* 134 */                   String reasonPhrase = statusLine.getReasonPhrase();
/* 136 */                   if (LOGGABLE) {
/* 137 */                       Log.d("HttpOperation", statusCode + "/" + reasonPhrase + " [" + httpReq.getURI() + "]");
                            }
                            switch (statusCode) {
                                case 200:
/* 144 */                           Header[] headers = resp.getHeaders("X-Upload-Key");
/* 145 */                           if (headers != null && headers.length > 0) {
/* 146 */                               uploadKey = headers[0].getValue();
                                    }
                                    break;
                                case 401:
/* 151 */                           Header dateHeader = resp.getLastHeader("Date");
/* 152 */                           if (dateHeader != null) {
/* 153 */                               Date d = CommonUtil.DATE_TIME_RFC1123.parse(dateHeader.getValue());
/* 154 */                               long ts = d.getTime();
/* 155 */                               long now = System.currentTimeMillis();
/* 156 */                               sTimeOffset.set(ts - now);
                                    }
/* 158 */                           if (authRetry > 0) {
/* 159 */                               authRetry--;
/* 160 */                               retries++;
                                    }
                                    break;
                                case 500:
                                case HttpResponseCode.BAD_GATEWAY /* 502 */:
                                case HttpResponseCode.SERVICE_UNAVAILABLE /* 503 */:
                                    break;
                                default:
/* 175 */                           retries = 0;
                                    break;
                            }
/* 179 */                   HttpEntity entity = resp.getEntity();
/* 180 */                   long openTime = System.currentTimeMillis() - before;
/* 182 */                   if (entity != null && entity.isStreaming()) {
/* 184 */                       if (entity.getContentEncoding() != null) {
/* 185 */                           contentEncoding = entity.getContentEncoding().getValue();
                                } else {
/* 187 */                           contentEncoding = null;
                                }
/* 189 */                       Header entityContentType = entity.getContentType();
/* 191 */                       if (entityContentType != null) {
/* 192 */                           contentType = entityContentType.getValue();
                                } else {
/* 194 */                           contentType = null;
                                }
/* 196 */                       int contentLength = (int) entity.getContentLength();
/* 197 */                       if (LOGGABLE) {
/* 198 */                           Log.d("HttpOperation", "read: Encoding: " + contentEncoding + ", type: " + contentType + ", length: " + contentLength);
                                }
                                try {
/* 203 */                           MeasureInputStream measuredIn = new MeasureInputStream(entity.getContent());
/* 205 */                           InputStream in = measuredIn;
/* 206 */                           if (contentType != null && !contentType.startsWith("application/octet-stream") && !contentType.startsWith("video/avc") && !contentType.startsWith(MimeTypes.VIDEO_WEBM) && !contentType.startsWith(MimeTypes.VIDEO_MP4) && !contentType.startsWith("binary/octet-stream")) {
/* 215 */                               if (contentType.startsWith(CommonUtil.ContentType.APPLICATION_JSON)) {
/* 216 */                                   if (contentEncoding != null && contentEncoding.equals("gzip")) {
/* 218 */                                       contentLength = -1;
/* 217 */                                       in = new GZIPInputStream(in);
                                            }
                                        } else if (contentType.startsWith("text/html") || contentType.startsWith("text/plain") || contentType.startsWith("text/xml") || contentType.startsWith("application/xml") || contentType.startsWith("image/")) {
/* 226 */                                   if (contentEncoding != null && contentEncoding.equals("gzip")) {
/* 228 */                                       contentLength = -1;
/* 227 */                                       in = new GZIPInputStream(in);
                                            }
                                        } else {
/* 231 */                                   throw new IOException("Unsupported content type: " + contentType);
                                        }
                                    }
/* 233 */                           onRemoteInputStreamReady(in, cachePolicy, statusCode, contentLength);
/* 234 */                           readTime = measuredIn.getReadTime();
/* 235 */                           if (SLog.sLogsOn && RATE_LIMIT_BYTES_PER_MS != -1.0d) {
/* 236 */                               long rateLimitLeft = (long) ((((double) contentLength) / RATE_LIMIT_BYTES_PER_MS) - readTime);
                                        if (rateLimitLeft > 0) {
/* 238 */                                   SLog.m447d("Rate limiting: {}ms.", Long.valueOf(rateLimitLeft));
/* 239 */                                   Thread.sleep(rateLimitLeft);
                                        }
                                    }
/* 243 */                           long now2 = System.currentTimeMillis();
/* 244 */                           CommonUtil.closeSilently(in);
/* 245 */                           closeTime = System.currentTimeMillis() - now2;
                                } catch (Throwable th) {
                                    long now3 = System.currentTimeMillis();
/* 244 */                           CommonUtil.closeSilently(null);
/* 245 */                           long closeTime2 = System.currentTimeMillis() - now3;
/* 246 */                           throw th;
                                }
                            }
/* 259 */                   long durationMs = openTime + readTime + closeTime;
                            if (firstByte != -1 && (SLog.sLogsOn || Math.random() > 0.1d)) {
/* 261 */                       URI uri = httpReq.getURI();
/* 262 */                       SLog.m453d("{}, {} took {} ms to open and {} total.", new Object[]{uri.getHost(), uri.getPath(), Long.valueOf(firstByte), Long.valueOf(durationMs)});
/* 263 */                       FlurryUtils.trackRespondTime(uri.getHost(), uri.getPath(), firstByte, durationMs, uri.toString().contains("/r/videos/"));
                            }
/* 269 */                   HttpResult result = new HttpResult(statusCode, reasonPhrase);
/* 270 */                   result.exception = null;
/* 271 */                   result.durationMs = durationMs;
/* 272 */                   result.uploadKey = uploadKey;
/* 274 */                   return new NetworkOperation.ExecuteResult(result, retries, authRetry);
                        } catch (Exception ex) {
/* 249 */                   if (LOGGABLE) {
/* 250 */                       Log.d("HttpOperation", "[" + httpReq.getURI() + "]", ex);
                            }
/* 252 */                   httpReq.abort();
/* 257 */                   throw ex;
                        }
                    } catch (Throwable th2) {
/* 115 */               long durationMs2 = 0 + 0 + 0;
                        if (-1 != -1 && (SLog.sLogsOn || Math.random() > 0.1d)) {
/* 261 */                   URI uri2 = httpReq.getURI();
/* 262 */                   SLog.m453d("{}, {} took {} ms to open and {} total.", new Object[]{uri2.getHost(), uri2.getPath(), -1L, Long.valueOf(durationMs2)});
/* 111 */                   FlurryUtils.trackRespondTime(uri2.getHost(), uri2.getPath(), -1L, durationMs2, uri2.toString().contains("/r/videos/"));
                        }
/* 264 */               throw th2;
                    }
                }
            }
