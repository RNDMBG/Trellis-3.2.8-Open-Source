            package co.vine.android.cache.image;

            import co.vine.android.network.HttpResult;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PhotoImagesListener {
                void onPhotoImageError(PhotoImagesCache photoImagesCache, ImageKey imageKey, HttpResult httpResult);

                void onPhotoImageLoaded(PhotoImagesCache photoImagesCache, HashMap<ImageKey, UrlImage> map);
            }
