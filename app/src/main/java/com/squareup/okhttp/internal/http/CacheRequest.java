            package com.squareup.okhttp.internal.http;

            import java.io.IOException;
            import okio.Sink;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface CacheRequest {
                void abort();

                Sink body() throws IOException;
            }
