            package co.vine.android.cache.video;

            import co.vine.android.network.HttpResult;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface VideoListener {
                void onVideoPathError(VideoCache videoCache, VideoKey videoKey, HttpResult httpResult);

                void onVideoPathObtained(VideoCache videoCache, HashMap<VideoKey, UrlVideo> map);
            }
