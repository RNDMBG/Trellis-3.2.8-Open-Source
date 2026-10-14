            package co.vine.android.cache.text;

            import co.vine.android.cache.UrlResource;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class UrlText extends UrlResource<byte[]> {
                private final int size;

/* 13 */        public UrlText(String url) {
/* 14 */            super(url);
/* 15 */            this.size = 0;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 24 */        public UrlText(String url, byte[] bArr) throws IOException {
/* 25 */            super(url);
/* 26 */            this.value = bArr;
/* 27 */            if (this.value != null) {
/* 28 */                this.size = bArr.length;
                    } else {
/* 30 */                this.size = 0;
                    }
                }

                @Override // co.vine.android.cache.UrlResource
/* 35 */        public int size() {
/* 36 */            return this.size;
                }

                @Override // co.vine.android.cache.UrlResource
/* 40 */        public int originalSize() {
/* 41 */            return this.size;
                }
            }
