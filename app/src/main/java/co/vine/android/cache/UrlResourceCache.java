            package co.vine.android.cache;

            import android.content.Context;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.v4.util.LruCache;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.network.NetworkOperationReader;
            import co.vine.android.util.CommonUtil;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.URI;
            import java.net.URL;
            import java.util.HashMap;
            import java.util.LinkedHashMap;
            import java.util.List;
            import java.util.Map;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public abstract class UrlResourceCache<K extends CacheKey, V extends UrlResource<T>, T, J> {
                protected static final int IO_BUFFER_SIZE = 4096;
                static final long NEXT_REQUEST_INTERVAL = 60000;
                private static final long POST_DELAY_MILLIS = 100;
                private CacheBinder<K, V> mCacheBinder;
                protected final Context mContext;
                private final NetworkOperationFactory<J> mNetworkFactory;
                private final J mNetworkOperationContext;
                private final LruCache<K, V> mResourceCache;
                protected static final String TAG = "ResourceCache";
                protected static final boolean LOGGABLE = Log.isLoggable(TAG, 3);
                protected static final ExecutorService sExecutor = Executors.newSingleThreadExecutor();
                private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
                final int[] mLock = new int[0];
                protected final LinkedHashMap<K, PendingRes> mPendingDownload = new LinkedHashMap<>(20, 10.0f, true);
                protected final HashMap<K, PendingRes> mQueuedDownload = new HashMap<>(20, 10.0f);

                protected abstract V instantiateResource(K k, String str, T t);

                protected abstract V loadResource(long j, K k, String str) throws Throwable;

                protected abstract V obtainResource(K k, String str, InputStream inputStream) throws Throwable;

                protected abstract void onResourceError(K k, HttpResult httpResult);

                protected abstract void onResourceLoaded(HashMap<K, V> map);

                protected abstract V saveResource(long j, K k, String str, InputStream inputStream, boolean z);

/* 25 */        static /* synthetic */ Object access$000(UrlResourceCache x0) {
/* 26 */            return x0.mNetworkOperationContext;
                }

/* 25 */        static /* synthetic */ NetworkOperationFactory access$100(UrlResourceCache x0) {
/* 26 */            return x0.mNetworkFactory;
                }

/* 25 */        static /* synthetic */ Handler access$200() {
/* 26 */            return sMainHandler;
                }

/* 59 */        public UrlResourceCache(Context context, int maxCacheSize, J networkOperationContext, NetworkOperationFactory<J> networkFactory) {
/* 61 */            this.mContext = context.getApplicationContext();
/* 64 */            if (maxCacheSize > 0) {
/* 65 */                this.mResourceCache = new MemoryLruCache(maxCacheSize);
                    } else {
/* 67 */                this.mResourceCache = null;
                    }
/* 69 */            this.mNetworkOperationContext = networkOperationContext;
/* 70 */            this.mNetworkFactory = networkFactory;
                }

/* 73 */        public Map<K, V> snapShot() {
/* 74 */            return this.mResourceCache.snapshot();
                }

/* 80 */        public void clear() {
/* 81 */            if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 83 */                        this.mResourceCache.evictAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    synchronized (this.mLock) {
                        try {
/* 87 */                    this.mPendingDownload.clear();
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

/* 117 */       public V peek(K key) {
                    V v;
/* 118 */           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 120 */                       v = this.mResourceCache.get(key);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 123 */               return v;
                    }
/* 123 */           return null;
                }

/* 127 */       public V put(K key, V value) {
                    V vPut;
/* 128 */           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 130 */                       vPut = this.mResourceCache.put(key, value);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 133 */               return vPut;
                    }
/* 133 */           return null;
                }

/* 137 */       public V remove(K key) {
                    V vRemove;
/* 138 */           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 140 */                       vRemove = this.mResourceCache.remove(key);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 143 */               return vRemove;
                    }
/* 143 */           return null;
                }

/* 153 */       public int cancel(K key) {
                    synchronized (this.mLock) {
                        try {
/* 155 */                   PendingRes pendingRes = this.mQueuedDownload.remove(key);
/* 156 */                   if (pendingRes != null) {
/* 157 */                       this.mPendingDownload.remove(key);
/* 158 */                       return 1;
                            }
/* 161 */                   return 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 170 */       public int cancel(List<K> keys) {
/* 171 */           int count = 0;
                    synchronized (this.mLock) {
                        try {
                            for (K key : keys) {
/* 174 */                       count += cancel(key);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 177 */           return count;
                }

/* 192 */       protected final V get(long j, K k, String str, boolean z) {
/* 193 */           V v = (V) get(j, k, str, z, false);
/* 194 */           if (v != null) {
/* 195 */               k.setLoadedFromMemory();
                    }
/* 197 */           return v;
                }

/* 213 */       protected final V get(long j, K k, String str, boolean z, boolean z2) {
                    V v;
/* 214 */           if (str == null) {
/* 215 */               return null;
                    }
/* 217 */           if (LOGGABLE) {
/* 218 */               Log.d(TAG, "Url: " + str);
                    }
/* 221 */           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 223 */                       v = this.mResourceCache.get(k);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } else {
/* 226 */               v = null;
                    }
/* 229 */           if (v != null) {
/* 230 */               if (LOGGABLE) {
/* 231 */                   Log.d(TAG, "Have resource: " + v.url);
                        }
/* 233 */               if (!v.url.equals(str) || (v.nextRequestTime > 0 && v.nextRequestTime < System.currentTimeMillis())) {
/* 236 */                   return (V) queueResourceLoad(j, k, str, z, z2);
                        }
/* 238 */               k.wasLastLoadFromMemory();
/* 245 */               return v;
                    }
/* 240 */           if (LOGGABLE) {
/* 241 */               Log.d(TAG, "Looking in persistent storage: " + str);
                    }
/* 243 */           return (V) queueResourceLoad(j, k, str, z, z2);
                }

/* 248 */       private V queueResourceLoad(long j, K k, String str, boolean z, boolean z2) {
/* 249 */           if (!TextUtils.isEmpty(str)) {
/* 250 */               if (z2) {
                            try {
/* 251 */                       return (V) loadResource(j, k, str);
                            } catch (Throwable e) {
                                Log.w(TAG, "Synchronous resource load failed", e);
                                return null;
                            }
                        }
                        synchronized (this.mLock) {
                            try {
/* 254 */                       if (!this.mPendingDownload.containsKey(k)) {
/* 255 */                           PendingRes pendingRes = new PendingRes(str, z);
/* 256 */                           this.mPendingDownload.put(k, pendingRes);
/* 257 */                           this.mQueuedDownload.put(k, pendingRes);
/* 261 */                           sMainHandler.postDelayed(new QueueRunnable(j), POST_DELAY_MILLIS);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
/* 266 */           return null;
                }

/* 274 */       void urlResourceLoaded(HashMap<K, V> result) {
/* 275 */           if (!result.isEmpty()) {
                        for (Map.Entry<K, V> entry : result.entrySet()) {
/* 279 */                   K key = entry.getKey();
/* 280 */                   V resource = entry.getValue();
/* 281 */                   if (this.mResourceCache != null && resource != null) {
                                synchronized (this.mResourceCache) {
                                    try {
/* 283 */                               this.mResourceCache.put(key, resource);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                            synchronized (this.mLock) {
                                try {
/* 287 */                           this.mPendingDownload.remove(key);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
/* 290 */               onResourceLoaded(result);
                    }
                }

/* 293 */       public CacheBinder<K, V> getCacheBinder() {
/* 294 */           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
/* 296 */                       if (this.mCacheBinder == null) {
/* 297 */                           this.mCacheBinder = new CacheBinder<>(this.mResourceCache);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 300 */               return this.mCacheBinder;
                    }
/* 302 */           return null;
                }

/* 306 */       public boolean hasPendingItems() {
                    boolean z;
                    synchronized (this.mLock) {
                        try {
/* 308 */                   z = this.mQueuedDownload.size() > 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

/* 314 */       private static class PendingRes {
                    final boolean persist;
                    final String url;

/* 315 */           public PendingRes(String url, boolean persist) {
/* 317 */               this.url = url;
/* 318 */               this.persist = persist;
                    }
                }

/* 323 */       private class QueueRunnable implements Runnable {
                    private final long mOwnerId;

/* 324 */           public QueueRunnable(long ownerId) {
/* 326 */               this.mOwnerId = ownerId;
                    }

                    @Override // java.lang.Runnable
/* 330 */           public void run() {
                        synchronized (UrlResourceCache.this.mLock) {
                            try {
/* 332 */                       if (!UrlResourceCache.this.mQueuedDownload.isEmpty()) {
/* 335 */                           HashMap<K, PendingRes> workQueue = new HashMap<>(UrlResourceCache.this.mQueuedDownload.size());
/* 336 */                           workQueue.putAll(UrlResourceCache.this.mQueuedDownload);
/* 337 */                           UrlResourceCache.this.mQueuedDownload.clear();
/* 339 */                           UrlResourceCache.sExecutor.execute(new FetchRunnable(this.mOwnerId, workQueue));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 345 */       private class FetchRunnable implements Runnable {
                    private final long mOwnerId;
                    private final HashMap<K, PendingRes> mWorkQueue;

/* 346 */           public FetchRunnable(long ownerId, HashMap<K, PendingRes> queue) {
/* 348 */               this.mOwnerId = ownerId;
/* 349 */               this.mWorkQueue = queue;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
/* 352 */           public void run() {
/* 353 */               HashMap<K, PendingRes> copy = this.mWorkQueue;
/* 354 */               if (UrlResourceCache.LOGGABLE) {
/* 355 */                   Log.d(UrlResourceCache.TAG, "Queueing " + copy.size());
                        }
/* 357 */               if (!copy.isEmpty()) {
/* 358 */                   HashMap map = new HashMap(copy.size());
                            for (Map.Entry<K, PendingRes> entry : copy.entrySet()) {
/* 365 */                       K key = entry.getKey();
/* 366 */                       PendingRes pendingRes = entry.getValue();
/* 367 */                       String url = pendingRes.url;
/* 368 */                       key.setLoadStartTime(System.currentTimeMillis());
                            UrlResource urlResourceLoadResource;
                            try {
/* 369 */                           urlResourceLoadResource = UrlResourceCache.this.loadResource(this.mOwnerId, key, url);
                            } catch (Throwable e) {
                                Log.w(UrlResourceCache.TAG, "Queued resource load failed", e);
                                urlResourceLoadResource = null;
                            }
/* 370 */                       if (urlResourceLoadResource != null) {
/* 371 */                           key.setCacheState(CacheKey.CacheState.DISK);
/* 372 */                           map.put(key, urlResourceLoadResource);
                                } else {
/* 374 */                           key.setCacheState(CacheKey.CacheState.NOT_CACHED);
/* 375 */                           if (UrlResourceCache.LOGGABLE) {
/* 376 */                               Log.d(UrlResourceCache.TAG, "Fetch " + url);
                                    }
/* 379 */                           URI uri = CommonUtil.parseURI(url);
/* 380 */                           if (uri != null) {
/* 381 */                               UrlResourceCache<K, V, T, J>.ResourceHttpOperationReader reader = new ResourceHttpOperationReader(this.mOwnerId, key, url, pendingRes.persist);
/* 385 */                               if (UrlResourceCache.this.fetchResourceDirect(url, reader)) {
/* 386 */                                   urlResourceLoadResource = reader.getResultResource();
                                        }
/* 392 */                               if (urlResourceLoadResource == null) {
/* 393 */                                   urlResourceLoadResource = UrlResourceCache.this.instantiateResource(key, url, null);
/* 394 */                                   urlResourceLoadResource.nextRequestTime = System.currentTimeMillis() + 60000;
                                        }
/* 397 */                               map.put(key, urlResourceLoadResource);
                                    }
                                }
/* 400 */                       key.setLoadTime((int) (System.currentTimeMillis() - key.getLoadStartTime()));
                            }
/* 402 */                   UrlResourceCache.access$200().post(new ResultRunnable(map));
                        }
                    }
                }

        /** Fetch media independently of the legacy API request client. */
        private boolean fetchResourceDirect(String url, NetworkOperationReader reader) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setInstanceFollowRedirects(true);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(30000);
                connection.setUseCaches(false);
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("User-Agent", NetworkOperation.USER_AGENT_STRING);
                int status = connection.getResponseCode();
                if (status >= 200 && status < 300) {
                    InputStream input = connection.getInputStream();
                    try {
                        reader.readInput(status, connection.getContentLength(), input);
                    } finally {
                        CommonUtil.closeSilently(input);
                    }
                    return true;
                }
                reader.onHandleError(new HttpResult(status, connection.getResponseMessage()));
            } catch (Exception e) {
                Log.w(TAG, "Direct resource fetch failed: " + url, e);
                reader.onHandleError(new HttpResult(-1, e.getMessage()));
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return false;
        }

/* 405 */       private static class MemoryLruCache<K, V extends UrlResource> extends LruCache<K, V> {
                    /* JADX WARN: Multi-variable type inference failed */

/* 409 */           public MemoryLruCache(int maxMemory) {
/* 410 */               super(maxMemory);
                    }

/* 414 */           protected int sizeOf(K key, V value) {
/* 415 */               return value.size();
                    }
                }

/* 425 */       private class ResourceHttpOperationReader implements NetworkOperationReader {
                    private final K mKey;
                    private final long mOwnerId;
                    private final boolean mPersist;
                    private V mResource = null;
                    private final String mUrl;

/* 426 */           public ResourceHttpOperationReader(long ownerId, K key, String url, boolean persist) {
/* 428 */               this.mOwnerId = ownerId;
/* 429 */               this.mKey = key;
/* 430 */               this.mUrl = url;
/* 431 */               this.mPersist = persist;
                    }

/* 434 */           public final V getResultResource() {
/* 435 */               return this.mResource;
                    }

                    @Override // co.vine.android.network.NetworkOperationReader
/* 439 */           public final void readInput(int i, long j, InputStream inputStream) throws IOException {
/* 440 */               if (this.mPersist) {
/* 441 */                   this.mResource = (V) UrlResourceCache.this.saveResource(this.mOwnerId, this.mKey, this.mUrl, inputStream, true);
                        } else {
                            try {
/* 443 */                       this.mResource = (V) UrlResourceCache.this.obtainResource(this.mKey, this.mUrl, inputStream);
                            } catch (Throwable e) {
                                throw new IOException("Resource decode failed", e);
                            }
                        }
                    }

                    @Override // co.vine.android.network.NetworkOperationReader
/* 447 */           public void onHandleError(HttpResult result) {
/* 448 */               UrlResourceCache.this.onResourceError(this.mKey, result);
                    }
                }

/* 453 */       private class ResultRunnable implements Runnable {
                    private final HashMap<K, V> mResult;

/* 454 */           public ResultRunnable(HashMap<K, V> result) {
/* 456 */               this.mResult = result;
                    }

                    @Override // java.lang.Runnable
/* 459 */           public void run() {
/* 460 */               UrlResourceCache.this.urlResourceLoaded(this.mResult);
                    }
                }
            }
