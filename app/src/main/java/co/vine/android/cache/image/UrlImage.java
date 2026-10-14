            package co.vine.android.cache.image;

            import android.graphics.Bitmap;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.util.ImageUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class UrlImage extends UrlResource<ImageUtils.BitmapInfo> {
                public Bitmap bitmap;

                /* JADX WARN: Multi-variable type inference failed */
/* 36 */        public UrlImage(String url, ImageUtils.BitmapInfo bitmapInfo) {
/* 37 */            super(url);
/* 38 */            this.value = bitmapInfo;
/* 39 */            if (bitmapInfo != null) {
/* 40 */                this.bitmap = bitmapInfo.bitmap;
                    }
                }

                @Override // co.vine.android.cache.UrlResource
/* 45 */        public int size() {
/* 46 */            if (this.bitmap != null) {
/* 47 */                return this.bitmap.getRowBytes() * this.bitmap.getHeight();
                    }
                    return 0;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // co.vine.android.cache.UrlResource
/* 54 */        public int originalSize() {
/* 55 */            if (this.value == null) {
                        return 0;
                    }
                    return ((ImageUtils.BitmapInfo) this.value).origHeight * ((ImageUtils.BitmapInfo) this.value).origWidth;
                }
            }
