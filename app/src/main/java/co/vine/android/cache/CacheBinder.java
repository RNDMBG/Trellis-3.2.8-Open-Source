            package co.vine.android.cache;

            import android.os.Binder;
            import android.support.v4.util.LruCache;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class CacheBinder<K, V> extends Binder {
                public final LruCache<K, V> cache;

/* 11 */        public CacheBinder(LruCache<K, V> cache) {
/* 13 */            this.cache = cache;
                }
            }
