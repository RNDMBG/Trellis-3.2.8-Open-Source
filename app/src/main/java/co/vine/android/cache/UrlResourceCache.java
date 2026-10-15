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
            
            
    public abstract class UrlResourceCache<K extends CacheKey, V extends UrlResource<T>, T, J> {
                protected static final int IO_BUFFER_SIZE = 4096;
                static final long NEXT_REQUEST_INTERVAL = 60000;
                private static final long POST_DELAY_MILLIS = 40;
                private CacheBinder<K, V> mCacheBinder;
                protected final Context mContext;
                private final NetworkOperationFactory<J> mNetworkFactory;
                private final J mNetworkOperationContext;
                private final LruCache<K, V> mResourceCache;
                protected static final String TAG = "ResourceCache";
                protected static final boolean LOGGABLE = Log.isLoggable(TAG, 3);
                protected static final ExecutorService sExecutor = Executors.newFixedThreadPool(4);
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

        static  Object access$000(UrlResourceCache x0) {
            return x0.mNetworkOperationContext;
                }

        static  NetworkOperationFactory access$100(UrlResourceCache x0) {
            return x0.mNetworkFactory;
                }

        static  Handler access$200() {
            return sMainHandler;
                }

        public UrlResourceCache(Context context, int maxCacheSize, J networkOperationContext, NetworkOperationFactory<J> networkFactory) {
            this.mContext = context.getApplicationContext();
            if (maxCacheSize > 0) {
                this.mResourceCache = new MemoryLruCache(maxCacheSize);
                    } else {
                this.mResourceCache = null;
                    }
            this.mNetworkOperationContext = networkOperationContext;
            this.mNetworkFactory = networkFactory;
                }

        public Map<K, V> snapShot() {
            return this.mResourceCache.snapshot();
                }

        public void clear() {
            if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                        this.mResourceCache.evictAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    synchronized (this.mLock) {
                        try {
                    this.mPendingDownload.clear();
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

       public V peek(K key) {
                    V v;
           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                       v = this.mResourceCache.get(key);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return v;
                    }
           return null;
                }

       public V put(K key, V value) {
                    V vPut;
           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                       vPut = this.mResourceCache.put(key, value);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return vPut;
                    }
           return null;
                }

       public V remove(K key) {
                    V vRemove;
           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                       vRemove = this.mResourceCache.remove(key);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return vRemove;
                    }
           return null;
                }

       public int cancel(K key) {
                    synchronized (this.mLock) {
                        try {
                   PendingRes pendingRes = this.mQueuedDownload.remove(key);
                   if (pendingRes != null) {
                       this.mPendingDownload.remove(key);
                       return 1;
                            }
                   return 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public int cancel(List<K> keys) {
           int count = 0;
                    synchronized (this.mLock) {
                        try {
                            for (K key : keys) {
                       count += cancel(key);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return count;
                }

       protected final V get(long j, K k, String str, boolean z) {
           V v = (V) get(j, k, str, z, false);
           if (v != null) {
               k.setLoadedFromMemory();
                    }
           return v;
                }

       protected final V get(long j, K k, String str, boolean z, boolean z2) {
                    V v;
           if (str == null) {
               return null;
                    }
           if (LOGGABLE) {
               Log.d(TAG, "Url: " + str);
                    }
           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                       v = this.mResourceCache.get(k);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } else {
               v = null;
                    }
           if (v != null) {
               if (LOGGABLE) {
                   Log.d(TAG, "Have resource: " + v.url);
                        }
               if (!v.url.equals(str) || (v.nextRequestTime > 0 && v.nextRequestTime < System.currentTimeMillis())) {
                   return (V) queueResourceLoad(j, k, str, z, z2);
                        }
               k.wasLastLoadFromMemory();
               return v;
                    }
           if (LOGGABLE) {
               Log.d(TAG, "Looking in persistent storage: " + str);
                    }
           return (V) queueResourceLoad(j, k, str, z, z2);
                }

       private V queueResourceLoad(long j, K k, String str, boolean z, boolean z2) {
           if (!TextUtils.isEmpty(str)) {
               if (z2) {
                            try {
                       return (V) loadResource(j, k, str);
                            } catch (Throwable e) {
                                Log.w(TAG, "Synchronous resource load failed", e);
                                return null;
                            }
                        }
                        synchronized (this.mLock) {
                            try {
                       if (!this.mPendingDownload.containsKey(k)) {
                           PendingRes pendingRes = new PendingRes(str, z);
                           this.mPendingDownload.put(k, pendingRes);
                           this.mQueuedDownload.put(k, pendingRes);
                           sMainHandler.postDelayed(new QueueRunnable(j), POST_DELAY_MILLIS);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
           return null;
                }

       void urlResourceLoaded(HashMap<K, V> result) {
           if (!result.isEmpty()) {
                        for (Map.Entry<K, V> entry : result.entrySet()) {
                   K key = entry.getKey();
                   V resource = entry.getValue();
                   if (this.mResourceCache != null && resource != null) {
                                synchronized (this.mResourceCache) {
                                    try {
                               this.mResourceCache.put(key, resource);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                            synchronized (this.mLock) {
                                try {
                           this.mPendingDownload.remove(key);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
               onResourceLoaded(result);
                    }
                }

       public CacheBinder<K, V> getCacheBinder() {
           if (this.mResourceCache != null) {
                        synchronized (this.mResourceCache) {
                            try {
                       if (this.mCacheBinder == null) {
                           this.mCacheBinder = new CacheBinder<>(this.mResourceCache);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return this.mCacheBinder;
                    }
           return null;
                }

       public boolean hasPendingItems() {
                    boolean z;
                    synchronized (this.mLock) {
                        try {
                   z = this.mQueuedDownload.size() > 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

       private static class PendingRes {
                    final boolean persist;
                    final String url;

           public PendingRes(String url, boolean persist) {
               this.url = url;
               this.persist = persist;
                    }
                }

       private class QueueRunnable implements Runnable {
                    private final long mOwnerId;

           public QueueRunnable(long ownerId) {
               this.mOwnerId = ownerId;
                    }

                    @Override 
           public void run() {
                        synchronized (UrlResourceCache.this.mLock) {
                            try {
                       if (!UrlResourceCache.this.mQueuedDownload.isEmpty()) {
                           HashMap<K, PendingRes> workQueue = new HashMap<>(UrlResourceCache.this.mQueuedDownload.size());
                           workQueue.putAll(UrlResourceCache.this.mQueuedDownload);
                           UrlResourceCache.this.mQueuedDownload.clear();
                           UrlResourceCache.sExecutor.execute(new FetchRunnable(this.mOwnerId, workQueue));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       private class FetchRunnable implements Runnable {
                    private final long mOwnerId;
                    private final HashMap<K, PendingRes> mWorkQueue;

           public FetchRunnable(long ownerId, HashMap<K, PendingRes> queue) {
               this.mOwnerId = ownerId;
               this.mWorkQueue = queue;
                    }

                    
                    @Override 
           public void run() {
               HashMap<K, PendingRes> copy = this.mWorkQueue;
               if (UrlResourceCache.LOGGABLE) {
                   Log.d(UrlResourceCache.TAG, "Queueing " + copy.size());
                        }
               if (!copy.isEmpty()) {
                   HashMap map = new HashMap(copy.size());
                            for (Map.Entry<K, PendingRes> entry : copy.entrySet()) {
                       K key = entry.getKey();
                       PendingRes pendingRes = entry.getValue();
                       String url = pendingRes.url;
                       key.setLoadStartTime(System.currentTimeMillis());
                            UrlResource urlResourceLoadResource;
                            try {
                           urlResourceLoadResource = UrlResourceCache.this.loadResource(this.mOwnerId, key, url);
                            } catch (Throwable e) {
                                Log.w(UrlResourceCache.TAG, "Queued resource load failed", e);
                                urlResourceLoadResource = null;
                            }
                       if (urlResourceLoadResource != null) {
                           key.setCacheState(CacheKey.CacheState.DISK);
                           map.put(key, urlResourceLoadResource);
                                } else {
                           key.setCacheState(CacheKey.CacheState.NOT_CACHED);
                           if (UrlResourceCache.LOGGABLE) {
                               Log.d(UrlResourceCache.TAG, "Fetch " + url);
                                    }
                           URI uri = CommonUtil.parseURI(url);
                           if (uri != null) {
                               UrlResourceCache<K, V, T, J>.ResourceHttpOperationReader reader = new ResourceHttpOperationReader(this.mOwnerId, key, url, pendingRes.persist);
                               if (UrlResourceCache.this.fetchResourceDirect(url, reader)) {
                                   urlResourceLoadResource = reader.getResultResource();
                                        }
                               if (urlResourceLoadResource == null) {
                                   urlResourceLoadResource = UrlResourceCache.this.instantiateResource(key, url, null);
                                   urlResourceLoadResource.nextRequestTime = System.currentTimeMillis() + 60000;
                                        }
                               map.put(key, urlResourceLoadResource);
                                    }
                                }
                       key.setLoadTime((int) (System.currentTimeMillis() - key.getLoadStartTime()));
                            }
                   UrlResourceCache.access$200().post(new ResultRunnable(map));
                        }
                    }
                }

        
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

       private static class MemoryLruCache<K, V extends UrlResource> extends LruCache<K, V> {
                    

           public MemoryLruCache(int maxMemory) {
               super(maxMemory);
                    }

           protected int sizeOf(K key, V value) {
               return value.size();
                    }
                }

       private class ResourceHttpOperationReader implements NetworkOperationReader {
                    private final K mKey;
                    private final long mOwnerId;
                    private final boolean mPersist;
                    private V mResource = null;
                    private final String mUrl;

           public ResourceHttpOperationReader(long ownerId, K key, String url, boolean persist) {
               this.mOwnerId = ownerId;
               this.mKey = key;
               this.mUrl = url;
               this.mPersist = persist;
                    }

           public final V getResultResource() {
               return this.mResource;
                    }

                    @Override 
           public final void readInput(int i, long j, InputStream inputStream) throws IOException {
               if (this.mPersist) {
                   this.mResource = (V) UrlResourceCache.this.saveResource(this.mOwnerId, this.mKey, this.mUrl, inputStream, true);
                        } else {
                            try {
                       this.mResource = (V) UrlResourceCache.this.obtainResource(this.mKey, this.mUrl, inputStream);
                            } catch (Throwable e) {
                                throw new IOException("Resource decode failed", e);
                            }
                        }
                    }

                    @Override 
           public void onHandleError(HttpResult result) {
               UrlResourceCache.this.onResourceError(this.mKey, result);
                    }
                }

       private class ResultRunnable implements Runnable {
                    private final HashMap<K, V> mResult;

           public ResultRunnable(HashMap<K, V> result) {
               this.mResult = result;
                    }

                    @Override 
           public void run() {
               UrlResourceCache.this.urlResourceLoaded(this.mResult);
                    }
                }
            }
