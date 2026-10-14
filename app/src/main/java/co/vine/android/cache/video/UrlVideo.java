            package co.vine.android.cache.video;

            import co.vine.android.cache.UrlResource;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class UrlVideo extends UrlResource<File> {
                private static final long LONG_HIGH_BITS = -2147483648L;
                private File mFile;
                private Integer mSize;

                /* JADX WARN: Multi-variable type inference failed */
/* 18 */        public UrlVideo(String url, File file) {
/* 19 */            super(url);
/* 20 */            this.value = file;
/* 21 */            if (file != null) {
/* 22 */                this.mFile = file;
                    }
                }

/* 26 */        public String getAbsolutePath() {
/* 27 */            return this.mFile.getAbsolutePath();
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 30 */        private int getSize() {
/* 31 */            if (this.mSize == null || this.mSize.intValue() == 0) {
/* 32 */                if (this.value != null) {
/* 33 */                    this.mSize = Integer.valueOf(safeLongToInt(((File) this.value).length()));
                        } else {
/* 35 */                    this.mSize = 0;
                        }
                    }
/* 38 */            return this.mSize.intValue();
                }

                @Override // co.vine.android.cache.UrlResource
/* 42 */        public int size() {
/* 43 */            return getSize();
                }

                @Override // co.vine.android.cache.UrlResource
/* 47 */        public int originalSize() {
/* 48 */            return getSize();
                }

/* 51 */        private static int safeLongToInt(long l) {
                    if ((l & LONG_HIGH_BITS) == 0 || (l & LONG_HIGH_BITS) == LONG_HIGH_BITS) {
/* 53 */                return (int) l;
                    }
/* 55 */            throw new IllegalArgumentException("File is too large to store in cache.");
                }
            }
