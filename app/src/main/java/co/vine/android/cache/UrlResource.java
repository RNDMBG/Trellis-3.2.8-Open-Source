            package co.vine.android.cache;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public abstract class UrlResource<V> {
                protected static final int NO_REQUEST_NEEDED = 0;
                public long cacheTime;
                public long nextRequestTime = 0;
                public final String url;
                public V value;

                public abstract int originalSize();

                public abstract int size();

/* 15 */        public UrlResource(String url) {
/* 17 */            this.url = url;
                }

/* 29 */        public boolean isValid() {
                    return this.value != null && this.nextRequestTime == 0;
                }
            }
