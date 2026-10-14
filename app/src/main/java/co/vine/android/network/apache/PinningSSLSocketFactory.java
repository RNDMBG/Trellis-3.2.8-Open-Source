            package co.vine.android.network.apache;

            import android.content.Context;
            import co.vine.android.network.ssl.VineSSLSocketFactory;
            import java.io.IOException;
            import java.net.InetAddress;
            import java.net.InetSocketAddress;
            import java.net.Socket;
            import java.security.KeyManagementException;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.UnrecoverableKeyException;
            import javax.net.ssl.SSLSocket;
            import org.apache.http.conn.ssl.SSLSocketFactory;
            import org.apache.http.conn.ssl.X509HostnameVerifier;
            import org.apache.http.params.HttpConnectionParams;
            import org.apache.http.params.HttpParams;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class PinningSSLSocketFactory extends SSLSocketFactory {
                private final VineSSLSocketFactory mSSLSocketFactory;

/* 38 */        public PinningSSLSocketFactory(Context context) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
/* 39 */            super(null);
/* 40 */            this.mSSLSocketFactory = new VineSSLSocketFactory(context);
                }

                @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
/* 44 */        public Socket createSocket() throws IOException {
/* 45 */            return this.mSSLSocketFactory.createSocket();
                }

                @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
/* 51 */        public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params) throws IOException {
/* 52 */            SSLSocket sslSock = (SSLSocket) (sock != null ? sock : createSocket());
/* 54 */            if (localAddress != null || localPort > 0) {
/* 55 */                if (localPort < 0) {
/* 56 */                    localPort = 0;
                        }
/* 59 */                sslSock.bind(new InetSocketAddress(localAddress, localPort));
                    }
/* 62 */            int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
/* 63 */            int soTimeout = HttpConnectionParams.getSoTimeout(params);
/* 65 */            InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
/* 66 */            sslSock.connect(remoteAddress, connTimeout);
/* 67 */            sslSock.setSoTimeout(soTimeout);
                    try {
/* 70 */                SSLSocketFactory.STRICT_HOSTNAME_VERIFIER.verify(host, sslSock);
/* 80 */                return sslSock;
                    } catch (IOException iox) {
                        try {
/* 74 */                    sslSock.close();
                        } catch (Exception e) {
                        }
/* 77 */                throw iox;
                    }
                }

                @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
/* 85 */        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
/* 86 */            return this.mSSLSocketFactory.createSocket(socket, host, port, autoClose);
                }

                @Override // org.apache.http.conn.ssl.SSLSocketFactory
/* 90 */        public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier) {
/* 91 */            this.mSSLSocketFactory.setHostnameVerifier();
                }

                @Override // org.apache.http.conn.ssl.SSLSocketFactory
/* 95 */        public X509HostnameVerifier getHostnameVerifier() {
/* 96 */            return SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
                }
            }
