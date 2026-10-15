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
            import org.apache.http.conn.params.ConnManagerParams;
            import org.apache.http.conn.params.ConnPerRouteBean;
            import org.apache.http.impl.client.DefaultHttpClient;
            import org.apache.http.params.BasicHttpParams;
            import org.apache.http.params.HttpConnectionParams;
            import org.apache.http.params.HttpParams;
            
            
    public abstract class HttpOperationClient {
                private static final int CONNECT_TIMEOUT = 20000;
                public static final String PREF_PROXY_ENABLED = "proxy_enabled";
                public static final String PREF_PROXY_HOST = "proxy_host";
                public static final String PREF_PROXY_PORT = "proxy_port";
                private static final int READ_TIMEOUT = 90000;

                public abstract HttpClient getHttpClient();

                public abstract void restart();

                public abstract void shutdown();

        protected SchemeRegistry initializeSchemeRegistry(Context context) {
                    try {
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", new PinningSSLSocketFactory(context), 443));
                schemeRegistry.register(new Scheme("https+blob", new PinningSSLSocketFactory(context), 443));
                return schemeRegistry;
                    } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
                throw new AssertionError(e);
                    }
                }

        protected HttpParams initializeHttpParams() {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
            ConnManagerParams.setMaxTotalConnections(httpParams, 30);
            ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(10));
            return httpParams;
                }

        private HttpProxyInfo getProxyInfo(Context context) {
            SharedPreferences preferences = Util.getDefaultSharedPrefs(context);
            return new HttpProxyInfo(preferences.getBoolean(PREF_PROXY_ENABLED, false), preferences.getString(PREF_PROXY_HOST, ""), preferences.getString(PREF_PROXY_PORT, ""));
                }

        protected HttpClient initializeHttpClient(Context context, ClientConnectionManager connectionManager, HttpParams httpParams) {
            DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParams);
           httpClient.removeRequestInterceptorByClass(RequestAddCookies.class);
           httpClient.removeResponseInterceptorByClass(ResponseProcessCookies.class);
           httpClient.setKeepAliveStrategy(new ShortKeepAliveStrategy());
           HttpProxyInfo proxySettings = getProxyInfo(context);
           if (proxySettings.enabled) {
               httpClient.getParams().setParameter("http.route.default-proxy", proxySettings.httpHost);
                    }
           return httpClient;
                }

       private static class HttpProxyInfo {
                    public final boolean enabled;
                    public final HttpHost httpHost;

           public HttpProxyInfo(boolean enabled, String host, String portString) {
                        int port;
               String host2 = host.trim();
               enabled = host2.length() == 0 ? false : enabled;
                        try {
                   port = Integer.parseInt(portString);
                        } catch (NumberFormatException e) {
                   port = -1;
                   enabled = false;
                        }
               this.httpHost = new HttpHost(host2, port, "http");
               this.enabled = enabled;
                    }
                }
            }
