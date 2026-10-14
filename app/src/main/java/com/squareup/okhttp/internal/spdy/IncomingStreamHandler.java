            package com.squareup.okhttp.internal.spdy;

            import java.io.IOException;
            
            
    public interface IncomingStreamHandler {
                public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new C12791();

                void receive(SpdyStream spdyStream) throws IOException;

                
        static class C12791 implements IncomingStreamHandler {
            C12791() {
                    }

                    @Override 
            public void receive(SpdyStream stream) throws IOException {
                stream.close(ErrorCode.REFUSED_STREAM);
                    }
                }
            }
