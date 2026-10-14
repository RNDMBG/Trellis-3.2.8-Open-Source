            package co.vine.android.cache.text;

            import co.vine.android.network.HttpResult;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface TextListener {
                void onPathError(TextCache textCache, TextKey textKey, HttpResult httpResult);

                void onPathObtained(TextCache textCache, HashMap<TextKey, UrlText> map);
            }
