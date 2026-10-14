            package co.vine.android.cache.text;

            import co.vine.android.cache.CacheKey;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class TextKey extends CacheKey {
                public final String url;

/* 10 */        public TextKey(String url) {
/* 12 */            this.url = url;
                }

/* 16 */        public boolean equals(Object o) {
/* 17 */            if (this == o) {
                        return true;
                    }
/* 20 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 23 */            if (this.url != null) {
/* 24 */                return this.url.equals(((TextKey) o).url);
                    }
                    return ((TextKey) o).url == null;
                }

                @Override // co.vine.android.cache.CacheKey
/* 31 */        public String toString() {
/* 32 */            return super.toString() + IOUtils.LINE_SEPARATOR_UNIX + this.url;
                }

/* 36 */        public int hashCode() {
/* 37 */            if (this.url != null) {
                        return this.url.hashCode() * 31;
                    }
                    return 0;
                }
            }
