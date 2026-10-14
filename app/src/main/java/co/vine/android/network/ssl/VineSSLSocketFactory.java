            package co.vine.android.network.ssl;

            import android.content.Context;
            import co.vine.android.client.VineAPI;
            import java.io.IOException;
            import java.net.InetAddress;
            import java.net.Socket;
            import java.security.KeyManagementException;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.util.HashSet;
            import java.util.Set;
            import javax.net.SocketFactory;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.SSLSocket;
            import javax.net.ssl.SSLSocketFactory;
            import javax.net.ssl.TrustManager;
            import javax.net.ssl.TrustManagerFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class VineSSLSocketFactory extends SSLSocketFactory {
                private static final Set<String> PINNING_HOSTS = new HashSet();
                private final SSLSocketFactory mPinningSocketFactory;
                private final SSLSocketFactory mSystemSocketFactory;

                static {
/* 34 */            PINNING_HOSTS.add("api.twitter.com");
/* 35 */            PINNING_HOSTS.add("api.vineapp.com");
/* 36 */            PINNING_HOSTS.add("media.vineapp.com");
/* 37 */            PINNING_HOSTS.add(VineAPI.HOST_VINE);
/* 38 */            PINNING_HOSTS.add("rtc.vineapp.com");
                }

/* 40 */        public VineSSLSocketFactory(Context context) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
/* 42 */            SystemKeyStore keyStore = SystemKeyStore.getInstance(context);
/* 43 */            SSLContext pinningSslContext = SSLContext.getInstance("TLS");
/* 44 */            SSLContext systemSslContext = SSLContext.getInstance("TLS");
/* 46 */            TrustManager[] pinningTrustManagers = initializePinningTrustManagers(keyStore);
/* 47 */            TrustManager[] systemTrustManagers = initializeSystemTrustManagers(keyStore);
/* 48 */            pinningSslContext.init(null, pinningTrustManagers, null);
/* 49 */            systemSslContext.init(null, systemTrustManagers, null);
/* 51 */            this.mPinningSocketFactory = pinningSslContext.getSocketFactory();
/* 52 */            this.mSystemSocketFactory = systemSslContext.getSocketFactory();
                }

/* 55 */        public SocketFactory getPinningSocketFactory() {
/* 56 */            return this.mPinningSocketFactory;
                }

/* 59 */        public SocketFactory getSystemSocketFactory() {
/* 60 */            return this.mSystemSocketFactory;
                }

/* 64 */        public TrustManager[] initializeSystemTrustManagers(SystemKeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
/* 65 */            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
/* 66 */            tmf.init(keyStore.getTrustStore());
/* 68 */            return tmf.getTrustManagers();
                }

/* 71 */        public TrustManager[] initializePinningTrustManagers(SystemKeyStore keyStore) {
/* 72 */            TrustManager[] trustManagers = {new PinningTrustManager(keyStore, VinePins.PINS)};
/* 75 */            return trustManagers;
                }

                @Override // javax.net.SocketFactory
/* 78 */        public Socket createSocket() throws IOException {
/* 79 */            return this.mSystemSocketFactory.createSocket();
                }

                @Override // javax.net.ssl.SSLSocketFactory
/* 83 */        public String[] getDefaultCipherSuites() {
/* 84 */            return new String[0];
                }

                @Override // javax.net.ssl.SSLSocketFactory
/* 88 */        public String[] getSupportedCipherSuites() {
/* 89 */            return new String[0];
                }

/* 92 */        private void verify(String host, SSLSocket sslSocket) throws IOException {
/* 93 */            org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER.verify(host, sslSocket);
                }

/* 96 */        private int verifyPost(int port) {
/* 97 */            if (port == -1) {
/* 98 */                return 443;
                    }
/* 100 */           return port;
                }

/* 103 */       private SSLSocketFactory getFactoryForHost(String host) {
                    return PINNING_HOSTS.contains(host) ? this.mPinningSocketFactory : this.mSystemSocketFactory;
                }

                @Override // javax.net.ssl.SSLSocketFactory
/* 111 */       public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
/* 112 */           SSLSocketFactory socketFactory = getFactoryForHost(host);
/* 115 */           SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(socket, host, verifyPost(port), autoClose);
/* 118 */           verify(host, sslSocket);
/* 120 */           return sslSocket;
                }

                @Override // javax.net.SocketFactory
/* 123 */       public Socket createSocket(String host, int port) throws IOException {
/* 124 */           SSLSocketFactory socketFactory = getFactoryForHost(host);
/* 127 */           SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(host, verifyPost(port));
/* 129 */           verify(host, sslSocket);
/* 131 */           return sslSocket;
                }

                @Override // javax.net.SocketFactory
/* 135 */       public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
/* 136 */           SSLSocketFactory socketFactory = getFactoryForHost(host);
/* 139 */           SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(host, verifyPost(port), localHost, localPort);
/* 141 */           verify(host, sslSocket);
/* 143 */           return sslSocket;
                }

                @Override // javax.net.SocketFactory
/* 147 */       public Socket createSocket(InetAddress host, int port) throws IOException {
/* 148 */           return this.mSystemSocketFactory.createSocket(host, port);
                }

                @Override // javax.net.SocketFactory
/* 152 */       public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
/* 153 */           return this.mSystemSocketFactory.createSocket(address, port, localAddress, localPort);
                }

/* 156 */       public void setHostnameVerifier() {
/* 157 */           throw new IllegalArgumentException("Only strict hostname verification (default)  is supported!");
                }
            }
