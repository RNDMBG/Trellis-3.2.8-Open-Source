            package co.vine.android.cache.video;

            import co.vine.android.network.HttpResult;
            import java.util.HashMap;
            
            
            public interface VideoListener {
                void onVideoPathError(VideoCache videoCache, VideoKey videoKey, HttpResult httpResult);

                void onVideoPathObtained(VideoCache videoCache, HashMap<VideoKey, UrlVideo> map);
            }
