            package com.flurry.sdk;

            import java.io.IOException;
            import java.net.InetAddress;
            import java.net.InetSocketAddress;
            import java.net.Socket;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.SSLSocket;
            import javax.net.ssl.TrustManager;
            import org.apache.http.conn.scheme.LayeredSocketFactory;
            import org.apache.http.conn.scheme.SocketFactory;
            import org.apache.http.params.HttpConnectionParams;
            import org.apache.http.params.HttpParams;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ef */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class C0821ef implements LayeredSocketFactory, SocketFactory {

                /* JADX INFO: renamed from: a */
                private SSLContext f778a = null;

                /* JADX INFO: renamed from: a */
/* 50 */        private static SSLContext m838a() throws IOException {
                    try {
/* 51 */                SSLContext sSLContext = SSLContext.getInstance("TLS");
/* 52 */                sSLContext.init(null, new TrustManager[]{new C0822eg(null)}, null);
/* 53 */                return sSLContext;
                    } catch (Exception e) {
/* 55 */                throw new IOException(e.getMessage());
                    }
                }

                /* JADX INFO: renamed from: b */
/* 59 */        private SSLContext m839b() throws IOException {
/* 60 */            if (this.f778a == null) {
/* 61 */                this.f778a = m838a();
                    }
/* 63 */            return this.f778a;
                }

                @Override // org.apache.http.conn.scheme.SocketFactory
/* 71 */        public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
/* 72 */            int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
/* 73 */            int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
/* 74 */            InetSocketAddress inetSocketAddress = new InetSocketAddress(str, i);
/* 75 */            SSLSocket sSLSocket = (SSLSocket) (socket != null ? socket : createSocket());
/* 77 */            if (inetAddress != null || i2 > 0) {
/* 79 */                if (i2 < 0) {
/* 80 */                    i2 = 0;
                        }
/* 83 */                sSLSocket.bind(new InetSocketAddress(inetAddress, i2));
                    }
/* 86 */            sSLSocket.connect(inetSocketAddress, connectionTimeout);
/* 87 */            sSLSocket.setSoTimeout(soTimeout);
/* 88 */            return sSLSocket;
                }

                @Override // org.apache.http.conn.scheme.SocketFactory
/* 95 */        public Socket createSocket() throws IOException {
/* 96 */            return m839b().getSocketFactory().createSocket();
                }

                @Override // org.apache.http.conn.scheme.SocketFactory
/* 102 */       public boolean isSecure(Socket socket) throws IllegalArgumentException {
/* 103 */           return true;
                }

                @Override // org.apache.http.conn.scheme.LayeredSocketFactory
/* 111 */       public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
/* 112 */           return m839b().getSocketFactory().createSocket(socket, str, i, z);
                }

/* 121 */       public boolean equals(Object obj) {
                    return obj != null && obj.getClass().equals(C0821ef.class);
                }

/* 125 */       public int hashCode() {
/* 126 */           return C0821ef.class.hashCode();
                }
            }
