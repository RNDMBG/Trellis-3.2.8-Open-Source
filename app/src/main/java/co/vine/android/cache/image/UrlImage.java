            package co.vine.android.cache.image;

            import android.graphics.Bitmap;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.util.ImageUtils;
            
            
    public class UrlImage extends UrlResource<ImageUtils.BitmapInfo> {
                public Bitmap bitmap;

                
        public UrlImage(String url, ImageUtils.BitmapInfo bitmapInfo) {
            super(url);
            this.value = bitmapInfo;
            if (bitmapInfo != null) {
                this.bitmap = bitmapInfo.bitmap;
                    }
                }

                @Override 
        public int size() {
            if (this.bitmap != null) {
                return this.bitmap.getRowBytes() * this.bitmap.getHeight();
                    }
                    return 0;
                }

                
                @Override 
        public int originalSize() {
            if (this.value == null) {
                        return 0;
                    }
                    return ((ImageUtils.BitmapInfo) this.value).origHeight * ((ImageUtils.BitmapInfo) this.value).origWidth;
                }
            }
