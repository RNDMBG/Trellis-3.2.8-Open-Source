            package co.vine.android.cache.text;

            import co.vine.android.cache.UrlResource;
            import java.io.IOException;
            
            
    public class UrlText extends UrlResource<byte[]> {
                private final int size;

        public UrlText(String url) {
            super(url);
            this.size = 0;
                }

                
        public UrlText(String url, byte[] bArr) throws IOException {
            super(url);
            this.value = bArr;
            if (this.value != null) {
                this.size = bArr.length;
                    } else {
                this.size = 0;
                    }
                }

                @Override 
        public int size() {
            return this.size;
                }

                @Override 
        public int originalSize() {
            return this.size;
                }
            }
