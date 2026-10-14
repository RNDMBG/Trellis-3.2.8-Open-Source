            package com.squareup.okhttp.internal.spdy;

            import java.io.IOException;
            import java.util.List;
            import okio.BufferedSource;
            
            
    public interface PushObserver {
                public static final PushObserver CANCEL = new C12821();

                boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException;

                boolean onHeaders(int i, List<Header> list, boolean z);

                boolean onRequest(int i, List<Header> list);

                void onReset(int i, ErrorCode errorCode);

                
        static class C12821 implements PushObserver {
            C12821() {
                    }

                    @Override 
            public boolean onRequest(int streamId, List<Header> requestHeaders) {
                return true;
                    }

                    @Override 
            public boolean onHeaders(int streamId, List<Header> responseHeaders, boolean last) {
                return true;
                    }

                    @Override 
            public boolean onData(int streamId, BufferedSource source, int byteCount, boolean last) throws IOException {
                source.skip(byteCount);
                return true;
                    }

                    @Override 
            public void onReset(int streamId, ErrorCode errorCode) {
                    }
                }
            }
