            package co.vine.android.cache.text;

            import co.vine.android.cache.CacheKey;
            import org.apache.commons.io.IOUtils;
            
            
     public class TextKey extends CacheKey {
                public final String url;

        public TextKey(String url) {
            this.url = url;
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            if (this.url != null) {
                return this.url.equals(((TextKey) o).url);
                    }
                    return ((TextKey) o).url == null;
                }

                @Override 
        public String toString() {
            return super.toString() + IOUtils.LINE_SEPARATOR_UNIX + this.url;
                }

        public int hashCode() {
            if (this.url != null) {
                        return this.url.hashCode() * 31;
                    }
                    return 0;
                }
            }
