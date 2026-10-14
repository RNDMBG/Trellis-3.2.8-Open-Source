            package co.vine.android.network;

            import android.support.annotation.NonNull;
            import android.util.Log;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.cache.text.TextCache;
            import co.vine.android.cache.text.TextKey;
            import co.vine.android.cache.text.UrlText;
            import co.vine.android.util.FileLogger;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
            import java.io.ByteArrayInputStream;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.ArrayList;
            import java.util.concurrent.atomic.AtomicLong;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public abstract class NetworkOperation<T, K, L> {
                protected static final int DEFAULT_AUTH_RETRIES = 1;
                protected static final NetworkOperationReader DEFAULT_READER;
                public static final int DEFAULT_RETRIES = 0;
                protected static final HttpResult[] EMPTY_RESULTS;
                protected static final String HEADER_DATE = "Date";
                public static final int HIGH_PRIORITY_RETRIES = 1;
                public static final int HTTP_MULTIPLE_REDIRECT = 300;
                public static final int HTTP_OK = 200;
                protected static final boolean LOGGABLE;
                public static double RATE_LIMIT_BYTES_PER_MS = 0.0d;
                protected static final String TAG = "HttpOperation";
                public static final String USER_AGENT_STRING;
                private static FileLogger sFileLogger;
                public static long sNetworkDataUsed;
                public static long sSavedDataSize;
                public static final AtomicLong sTimeOffset;
                public Exception exception;
                private TextCache<?> mCache;
                private TextKey mCacheKey;
                protected final K mHttpClient;
                protected final T mHttpRequestBuilder;
                private NetworkOperationResult mOperationResult;
                protected final NetworkOperationReader mReader;
                protected HttpResult[] mResults;
                private UrlCachePolicy mUrlCachePolicy;
                protected final SLogger sLogger = SLogger.getLogger("nrk");
                public int statusCode;
                public String statusPhrase;
                public String uploadKey;

                public interface CancelableRequest {
                    void cancel();

                    boolean isCancelled();
                }

                public enum NetworkOperationResult {
                    CACHED,
                    NETWORK,
                    FAILURE
                }

                public abstract void addHeader(String str, String str2);

                protected abstract L buildRequest();

                @NonNull
                protected abstract ExecuteResult executeRequest(L l, int i, int i2, UrlCachePolicy urlCachePolicy) throws Exception;

                public abstract CancelableRequest getCancelableRequest();

                static {
/* 38 */            LOGGABLE = SLog.sLogsOn || Log.isLoggable(TAG, 3);
/* 43 */            sTimeOffset = new AtomicLong(0L);
/* 46 */            RATE_LIMIT_BYTES_PER_MS = -1.0d;
/* 52 */            USER_AGENT_STRING = System.getProperty("http.agent");
/* 71 */            EMPTY_RESULTS = new HttpResult[0];
/* 72 */            DEFAULT_READER = new DefaultHttpOperationReader();
                }

/* 90 */        public static void setLoggerFile(File networkLog) {
/* 91 */            sFileLogger = FileLogger.getLogger(networkLog);
                }

/* 99 */        public NetworkOperation<T, K, L> execute() {
/* 100 */           return execute(0);
                }

/* 103 */       public NetworkOperationReader getReader() {
/* 104 */           return this.mReader;
                }

/* 107 */       protected NetworkOperation(NetworkOperationReader reader, T httpRequestBuilder, K httpClient, HeaderInjecter injecter) {
/* 109 */           this.mHttpRequestBuilder = httpRequestBuilder;
/* 110 */           this.mHttpClient = httpClient;
/* 111 */           addHeader("Accept-Encoding", "gzip");
/* 112 */           addHeader("User-Agent", USER_AGENT_STRING);
/* 113 */           if (injecter != null) {
/* 114 */               injecter.addClientHeaders(this);
                    }
/* 116 */           if (reader == null) {
/* 117 */               this.mReader = DEFAULT_READER;
                    } else {
/* 119 */               this.mReader = reader;
                    }
                }

/* 132 */       protected void prepareRequest(long timeOffset) {
                }

/* 135 */       public NetworkOperationResult getLastExecuteResult() {
/* 136 */           return this.mOperationResult;
                }

/* 144 */       public boolean isOK() {
                    return this.statusCode == 200;
                }

/* 153 */       public boolean isSuccessful() {
                    return this.statusCode >= 200 && this.statusCode < 300;
                }

/* 162 */       public HttpResult[] getResults() {
                    return this.mResults == null ? EMPTY_RESULTS : this.mResults;
                }

/* 176 */       public NetworkOperation<T, K, L> execute(int retries) {
                    NetworkOperationResult networkOperationResult;
/* 177 */           UrlCachePolicy cachePolicy = getCachePolicy();
/* 179 */           boolean canUseCache = this.mCache != null && cachePolicy.mCachedResponseAllowed;
/* 181 */           boolean hasFetched = false;
/* 182 */           if (canUseCache && cachePolicy.mCacheTakesPriority) {
/* 183 */               hasFetched = fetchTextCacheResponse(cachePolicy, true);
                    }
/* 186 */           boolean wasCachedDataUsed = hasFetched;
/* 188 */           if (!hasFetched && cachePolicy.mNetworkDataAllowed) {
/* 189 */               hasFetched = fetchHttpResponse(retries, cachePolicy);
                    }
/* 192 */           if (!hasFetched && canUseCache) {
/* 193 */               hasFetched = fetchTextCacheResponse(cachePolicy, cachePolicy.mUseExpiredDataAllowedIfNetworkIsDown ? false : true);
/* 194 */               wasCachedDataUsed = hasFetched;
                    }
/* 197 */           if (hasFetched) {
/* 197 */               networkOperationResult = wasCachedDataUsed ? NetworkOperationResult.CACHED : NetworkOperationResult.NETWORK;
                    } else {
                        networkOperationResult = NetworkOperationResult.FAILURE;
                    }
                    this.mOperationResult = networkOperationResult;
/* 198 */           return this;
                }

/* 201 */       private boolean fetchHttpResponse(int retries, UrlCachePolicy cachePolicy) {
/* 202 */           ArrayList<HttpResult> results = new ArrayList<>(Math.max(retries, 0) + 1);
/* 204 */           int authRetry = 1;
/* 237 */           do {
/* 206 */               long currentTimeOffset = sTimeOffset.get();
/* 207 */               prepareRequest(currentTimeOffset);
                        try {
/* 210 */                   ExecuteResult result = executeRequest(buildRequest(), authRetry, retries, cachePolicy);
/* 219 */                   authRetry = result.mAuthRetries;
/* 220 */                   int retries2 = result.mRetries;
/* 221 */                   results.add(result.mResult);
/* 223 */                   if (result.mResult.statusCode != 200) {
/* 224 */                       this.mReader.onHandleError(result.mResult);
                            }
/* 227 */                   if (sFileLogger != null) {
/* 228 */                       sFileLogger.write("Remote {} {} ", this.mCacheKey.url, Integer.valueOf(this.statusCode));
                            }
/* 231 */                   this.statusCode = result.mResult.statusCode;
/* 232 */                   this.statusPhrase = result.mResult.reasonPhrase;
/* 233 */                   this.exception = result.mResult.exception;
/* 234 */                   this.uploadKey = result.mResult.uploadKey;
/* 236 */                   retries = retries2 - 1;
                        } catch (Exception e) {
                            Log.e("VineNetwork", "request failed: " + (this.mCacheKey == null ? "unknown" : this.mCacheKey.url), e);
/* 212 */                   if (SLog.sLogsOn) {
/* 213 */                       String error = MessageFormatter.toStringMessage("Request failed: {} ({})", this.mCacheKey.url, e.getMessage());
/* 214 */                       sFileLogger.write(error, new Object[0]);
/* 215 */                       this.sLogger.m500e(error, (Throwable) e);
                            }
                        }
/* 237 */           } while (retries >= 0);
/* 239 */           this.mResults = new HttpResult[results.size()];
/* 240 */           results.toArray(this.mResults);
                    return this.statusCode == 200;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 245 */       protected boolean fetchTextCacheResponse(UrlCachePolicy cachePolicy, boolean checkExpired) {
                    boolean expired;
/* 246 */           this.sLogger.m488d("Peek from cached {}", this.mCacheKey.url);
/* 247 */           this.sLogger.timingStart();
/* 248 */           UrlText data = this.mCache.get(this.mCacheKey);
/* 249 */           this.sLogger.timingStop();
/* 250 */           boolean valid = data != null && data.value != null;
/* 252 */           if (valid) {
                        expired = cachePolicy.mIfReachableFetchMaxStaleTimeMs > 0 && System.currentTimeMillis() - data.cacheTime > cachePolicy.mIfReachableFetchMaxStaleTimeMs;
/* 254 */               if (expired) {
/* 255 */                   this.sLogger.m488d("Cache has expired, it was loaded {}min ago.", Double.valueOf((System.currentTimeMillis() - data.cacheTime) / 60000.0d));
/* 256 */                   this.mCacheKey.setCacheState(CacheKey.CacheState.EXPIRED);
                        } else {
/* 258 */                   this.mCacheKey.setCacheState(CacheKey.CacheState.DISK);
/* 259 */                   this.sLogger.m487d("Valid cache found.");
                        }
                    } else {
/* 262 */               expired = false;
                    }
/* 264 */           if (sFileLogger != null) {
/* 265 */               sFileLogger.write("{}\n{}\n", this.mCacheKey.url, this.mCacheKey.getCacheState());
                    }
/* 267 */           if (valid && (!checkExpired || !expired)) {
/* 268 */               this.statusCode = 200;
/* 269 */               this.mResults = new HttpResult[]{new HttpResult(200, null)};
                        try {
/* 271 */                   sSavedDataSize += (long) ((byte[]) data.value).length;
/* 272 */                   this.mReader.readInput(this.statusCode, ((byte[]) data.value).length, new ByteArrayInputStream((byte[]) data.value));
                            return true;
                        } catch (Exception e) {
/* 275 */                   SLog.m459e("Failed to read from cache.", (Throwable) e);
                        }
                    } else {
/* 278 */               this.mCacheKey.setCacheState(CacheKey.CacheState.NOT_CACHED);
                    }
                    return false;
                }

/* 283 */       public void setCachePolicy(UrlCachePolicy urlCachePolicy) {
/* 284 */           this.mUrlCachePolicy = urlCachePolicy;
                }

/* 287 */       protected void setCacheStorage(TextCache cacheStorage) {
/* 288 */           this.mCache = cacheStorage;
                }

/* 291 */       protected void setCacheKey(TextKey textKey) {
/* 292 */           this.mCacheKey = textKey;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 295 */       protected void onRemoteInputStreamReady(InputStream in, UrlCachePolicy cachePolicy, int statusCode, long contentLength) throws IOException {
/* 296 */           sNetworkDataUsed += contentLength;
/* 297 */           if (statusCode == 200 && cachePolicy.mShouldCacheResponse) {
/* 298 */               this.sLogger.timingStart();
/* 299 */               UrlText data = this.mCache.save(this.mCacheKey, in);
/* 300 */               in = new ByteArrayInputStream((byte[]) data.value);
/* 301 */               this.sLogger.timingStop();
/* 302 */               this.sLogger.m488d("Cached {}", this.mCacheKey.url);
/* 303 */               sFileLogger.write("{} cached.", this.mCacheKey.url);
                    }
/* 305 */           this.mReader.readInput(statusCode, contentLength, in);
                }

                @NonNull
/* 309 */       public UrlCachePolicy getCachePolicy() {
                    return this.mUrlCachePolicy == null ? UrlCachePolicy.NOT_CACHABLE : this.mUrlCachePolicy;
                }

/* 318 */       protected static class ExecuteResult {
                    public final int mAuthRetries;
                    public final HttpResult mResult;
                    public final int mRetries;

/* 319 */           public ExecuteResult(HttpResult result, int retries, int authRetries) {
/* 321 */               this.mResult = result;
/* 322 */               this.mRetries = retries;
/* 323 */               this.mAuthRetries = authRetries;
                    }
                }
            }
