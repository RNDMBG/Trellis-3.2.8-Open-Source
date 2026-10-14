            package co.vine.android.cache.video;

            import co.vine.android.cache.UrlResource;
            import java.io.File;
            
            
    public class UrlVideo extends UrlResource<File> {
                private static final long LONG_HIGH_BITS = -2147483648L;
                private File mFile;
                private Integer mSize;

                
        public UrlVideo(String url, File file) {
            super(url);
            this.value = file;
            if (file != null) {
                this.mFile = file;
                    }
                }

        public String getAbsolutePath() {
            return this.mFile.getAbsolutePath();
                }

                
        private int getSize() {
            if (this.mSize == null || this.mSize.intValue() == 0) {
                if (this.value != null) {
                    this.mSize = Integer.valueOf(safeLongToInt(((File) this.value).length()));
                        } else {
                    this.mSize = 0;
                        }
                    }
            return this.mSize.intValue();
                }

                @Override 
        public int size() {
            return getSize();
                }

                @Override 
        public int originalSize() {
            return getSize();
                }

        private static int safeLongToInt(long l) {
                    if ((l & LONG_HIGH_BITS) == 0 || (l & LONG_HIGH_BITS) == LONG_HIGH_BITS) {
                return (int) l;
                    }
            throw new IllegalArgumentException("File is too large to store in cache.");
                }
            }
