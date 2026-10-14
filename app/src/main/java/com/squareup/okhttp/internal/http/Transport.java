            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.io.IOException;
            import okio.Sink;
            import okio.Source;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Transport {
                public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

                boolean canReuseConnection();

                Sink createRequestBody(Request request, long j) throws IOException;

                void disconnect(HttpEngine httpEngine) throws IOException;

                void emptyTransferStream() throws IOException;

                void flushRequest() throws IOException;

                Source getTransferStream(CacheRequest cacheRequest) throws IOException;

                Response.Builder readResponseHeaders() throws IOException;

                void releaseConnectionOnIdle() throws IOException;

                void writeRequestBody(RetryableSink retryableSink) throws IOException;

                void writeRequestHeaders(Request request) throws IOException;
            }
