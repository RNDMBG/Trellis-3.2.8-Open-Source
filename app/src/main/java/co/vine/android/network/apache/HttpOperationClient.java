            package co.vine.android.network.apache;

            import android.content.Context;
            import android.content.SharedPreferences;
            import co.vine.android.util.Util;
            import java.security.KeyManagementException;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.UnrecoverableKeyException;
            import org.apache.http.HttpHost;
            import org.apache.http.client.HttpClient;
            import org.apache.http.client.protocol.RequestAddCookies;
            import org.apache.http.client.protocol.ResponseProcessCookies;
            import org.apache.http.conn.ClientConnectionManager;
            import org.apache.http.conn.scheme.PlainSocketFactory;
            import org.apache.http.conn.scheme.Scheme;
            import org.apache.http.conn.scheme.SchemeRegistry;
            import org.apache.http.impl.client.DefaultHttpClient;
            import org.apache.http.params.BasicHttpParams;
            import org.apache.http.params.HttpConnectionParams;
            import org.apache.http.params.HttpParams;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public abstract class HttpOperationClient {
                private static final int CONNECT_TIMEOUT = 20000;
                public static final String PREF_PROXY_ENABLED = "proxy_enabled";
                public static final String PREF_PROXY_HOST = "proxy_host";
                public static final String PREF_PROXY_PORT = "proxy_port";
                private static final int READ_TIMEOUT = 90000;

                public abstract HttpClient getHttpClient();

                public abstract void restart();

                public abstract void shutdown();

/* 65 */        protected SchemeRegistry initializeSchemeRegistry(Context context) {
                    try {
/* 66 */                SchemeRegistry schemeRegistry = new SchemeRegistry();
/* 67 */                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
/* 68 */                schemeRegistry.register(new Scheme("https", new PinningSSLSocketFactory(context), 443));
/* 69 */                schemeRegistry.register(new Scheme("https+blob", new PinningSSLSocketFactory(context), 443));
/* 70 */                return schemeRegistry;
                    } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
/* 72 */                throw new AssertionError(e);
                    }
                }

/* 76 */        protected HttpParams initializeHttpParams() {
/* 77 */            HttpParams httpParams = new BasicHttpParams();
/* 79 */            HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
/* 80 */            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
/* 81 */            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
/* 83 */            return httpParams;
                }

/* 86 */        private HttpProxyInfo getProxyInfo(Context context) {
/* 87 */            SharedPreferences preferences = Util.getDefaultSharedPrefs(context);
/* 89 */            return new HttpProxyInfo(preferences.getBoolean(PREF_PROXY_ENABLED, false), preferences.getString(PREF_PROXY_HOST, ""), preferences.getString(PREF_PROXY_PORT, ""));
                }

/* 97 */        protected HttpClient initializeHttpClient(Context context, ClientConnectionManager connectionManager, HttpParams httpParams) {
/* 98 */            DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParams);
/* 101 */           httpClient.removeRequestInterceptorByClass(RequestAddCookies.class);
/* 102 */           httpClient.removeResponseInterceptorByClass(ResponseProcessCookies.class);
/* 105 */           httpClient.setKeepAliveStrategy(new ShortKeepAliveStrategy());
/* 107 */           HttpProxyInfo proxySettings = getProxyInfo(context);
/* 109 */           if (proxySettings.enabled) {
/* 110 */               httpClient.getParams().setParameter("http.route.default-proxy", proxySettings.httpHost);
                    }
/* 114 */           return httpClient;
                }

/* 119 */       private static class HttpProxyInfo {
                    public final boolean enabled;
                    public final HttpHost httpHost;

/* 120 */           public HttpProxyInfo(boolean enabled, String host, String portString) {
                        int port;
/* 122 */               String host2 = host.trim();
/* 124 */               enabled = host2.length() == 0 ? false : enabled;
                        try {
/* 131 */                   port = Integer.parseInt(portString);
                        } catch (NumberFormatException e) {
/* 133 */                   port = -1;
/* 134 */                   enabled = false;
                        }
/* 137 */               this.httpHost = new HttpHost(host2, port, "http");
/* 138 */               this.enabled = enabled;
                    }
                }
            }
