            package com.squareup.okhttp.internal.spdy;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public interface IncomingStreamHandler {
                public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new C12791();

                void receive(SpdyStream spdyStream) throws IOException;

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.IncomingStreamHandler$1 */
/* 21 */        static class C12791 implements IncomingStreamHandler {
/* 22 */            C12791() {
                    }

                    @Override // com.squareup.okhttp.internal.spdy.IncomingStreamHandler
/* 24 */            public void receive(SpdyStream stream) throws IOException {
/* 25 */                stream.close(ErrorCode.REFUSED_STREAM);
                    }
                }
            }
