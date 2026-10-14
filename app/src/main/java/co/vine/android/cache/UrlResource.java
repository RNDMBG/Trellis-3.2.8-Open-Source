            package co.vine.android.cache;
            
            
    public abstract class UrlResource<V> {
                protected static final int NO_REQUEST_NEEDED = 0;
                public long cacheTime;
                public long nextRequestTime = 0;
                public final String url;
                public V value;

                public abstract int originalSize();

                public abstract int size();

        public UrlResource(String url) {
            this.url = url;
                }

        public boolean isValid() {
                    return this.value != null && this.nextRequestTime == 0;
                }
            }
