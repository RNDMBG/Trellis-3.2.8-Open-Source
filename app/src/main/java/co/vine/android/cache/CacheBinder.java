            package co.vine.android.cache;

            import android.os.Binder;
            import android.support.v4.util.LruCache;
            
            
    public class CacheBinder<K, V> extends Binder {
                public final LruCache<K, V> cache;

        public CacheBinder(LruCache<K, V> cache) {
            this.cache = cache;
                }
            }
