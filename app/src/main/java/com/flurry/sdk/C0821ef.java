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
            
            
            
    public class C0821ef implements LayeredSocketFactory, SocketFactory {

                
                private SSLContext f778a = null;

                
        private static SSLContext m838a() throws IOException {
                    try {
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, new TrustManager[]{new C0822eg(null)}, null);
                return sSLContext;
                    } catch (Exception e) {
                throw new IOException(e.getMessage());
                    }
                }

                
        private SSLContext m839b() throws IOException {
            if (this.f778a == null) {
                this.f778a = m838a();
                    }
            return this.f778a;
                }

                @Override 
        public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
            int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
            int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(str, i);
            SSLSocket sSLSocket = (SSLSocket) (socket != null ? socket : createSocket());
            if (inetAddress != null || i2 > 0) {
                if (i2 < 0) {
                    i2 = 0;
                        }
                sSLSocket.bind(new InetSocketAddress(inetAddress, i2));
                    }
            sSLSocket.connect(inetSocketAddress, connectionTimeout);
            sSLSocket.setSoTimeout(soTimeout);
            return sSLSocket;
                }

                @Override 
        public Socket createSocket() throws IOException {
            return m839b().getSocketFactory().createSocket();
                }

                @Override 
       public boolean isSecure(Socket socket) throws IllegalArgumentException {
           return true;
                }

                @Override 
       public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
           return m839b().getSocketFactory().createSocket(socket, str, i, z);
                }

       public boolean equals(Object obj) {
                    return obj != null && obj.getClass().equals(C0821ef.class);
                }

       public int hashCode() {
           return C0821ef.class.hashCode();
                }
            }
