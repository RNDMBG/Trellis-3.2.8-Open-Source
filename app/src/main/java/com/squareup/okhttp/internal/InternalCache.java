            package com.squareup.okhttp.internal;

            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import com.squareup.okhttp.internal.http.CacheRequest;
            import com.squareup.okhttp.internal.http.CacheStrategy;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InternalCache {
                Response get(Request request) throws IOException;

                CacheRequest put(Response response) throws IOException;

                void remove(Request request) throws IOException;

                void trackConditionalCacheHit();

                void trackResponse(CacheStrategy cacheStrategy);

                void update(Response response, Response response2) throws IOException;
            }
