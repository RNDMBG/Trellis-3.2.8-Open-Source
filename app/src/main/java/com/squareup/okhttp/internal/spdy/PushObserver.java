            package com.squareup.okhttp.internal.spdy;

            import java.io.IOException;
            import java.util.List;
            import okio.BufferedSource;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    public interface PushObserver {
                public static final PushObserver CANCEL = new C12821();

                boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException;

                boolean onHeaders(int i, List<Header> list, boolean z);

                boolean onRequest(int i, List<Header> list);

                void onReset(int i, ErrorCode errorCode);

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.PushObserver$1 */
/* 74 */        static class C12821 implements PushObserver {
/* 75 */            C12821() {
                    }

                    @Override // com.squareup.okhttp.internal.spdy.PushObserver
/* 78 */            public boolean onRequest(int streamId, List<Header> requestHeaders) {
/* 79 */                return true;
                    }

                    @Override // com.squareup.okhttp.internal.spdy.PushObserver
/* 82 */            public boolean onHeaders(int streamId, List<Header> responseHeaders, boolean last) {
/* 83 */                return true;
                    }

                    @Override // com.squareup.okhttp.internal.spdy.PushObserver
/* 87 */            public boolean onData(int streamId, BufferedSource source, int byteCount, boolean last) throws IOException {
/* 88 */                source.skip(byteCount);
/* 89 */                return true;
                    }

                    @Override // com.squareup.okhttp.internal.spdy.PushObserver
/* 92 */            public void onReset(int streamId, ErrorCode errorCode) {
                    }
                }
            }
