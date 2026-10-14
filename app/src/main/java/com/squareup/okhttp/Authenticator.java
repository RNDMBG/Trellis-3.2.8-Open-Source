            package com.squareup.okhttp;

            import java.io.IOException;
            import java.net.Proxy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Authenticator {
                Request authenticate(Proxy proxy, Response response) throws IOException;

                Request authenticateProxy(Proxy proxy, Response response) throws IOException;
            }
