            package com.squareup.okhttp.internal.spdy;

            import com.squareup.okhttp.Protocol;
            import okio.BufferedSink;
            import okio.BufferedSource;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Variant {
                Protocol getProtocol();

                FrameReader newReader(BufferedSource bufferedSource, boolean z);

                FrameWriter newWriter(BufferedSink bufferedSink, boolean z);
            }
