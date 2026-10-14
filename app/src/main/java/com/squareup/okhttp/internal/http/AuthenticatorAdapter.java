            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Authenticator;
            import com.squareup.okhttp.Challenge;
            import com.squareup.okhttp.Credentials;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.io.IOException;
            import java.net.InetAddress;
            import java.net.InetSocketAddress;
            import java.net.PasswordAuthentication;
            import java.net.Proxy;
            import java.net.URL;
            import java.util.List;
            import org.scribe.model.OAuthConstants;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public final class AuthenticatorAdapter implements Authenticator {
                public static final Authenticator INSTANCE = new AuthenticatorAdapter();

                @Override // com.squareup.okhttp.Authenticator
/* 37 */        public Request authenticate(Proxy proxy, Response response) throws IOException {
                    PasswordAuthentication auth;
/* 38 */            List<Challenge> challenges = response.challenges();
/* 39 */            Request request = response.request();
/* 40 */            URL url = request.url();
                    int size = challenges.size();
                    for (int i = 0; i < size; i++) {
/* 42 */                Challenge challenge = challenges.get(i);
/* 43 */                if ("Basic".equalsIgnoreCase(challenge.getScheme()) && (auth = java.net.Authenticator.requestPasswordAuthentication(url.getHost(), getConnectToInetAddress(proxy, url), url.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, java.net.Authenticator.RequestorType.SERVER)) != null) {
/* 50 */                    String credential = Credentials.basic(auth.getUserName(), new String(auth.getPassword()));
/* 51 */                    return request.newBuilder().header(OAuthConstants.HEADER, credential).build();
                        }
                    }
                    return null;
                }

                @Override // com.squareup.okhttp.Authenticator
/* 59 */        public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
/* 60 */            List<Challenge> challenges = response.challenges();
/* 61 */            Request request = response.request();
/* 62 */            URL url = request.url();
                    int size = challenges.size();
                    for (int i = 0; i < size; i++) {
/* 64 */                Challenge challenge = challenges.get(i);
/* 65 */                if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
/* 67 */                    InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
/* 68 */                    PasswordAuthentication auth = java.net.Authenticator.requestPasswordAuthentication(proxyAddress.getHostName(), getConnectToInetAddress(proxy, url), proxyAddress.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, java.net.Authenticator.RequestorType.PROXY);
/* 72 */                    if (auth != null) {
/* 74 */                        String credential = Credentials.basic(auth.getUserName(), new String(auth.getPassword()));
/* 75 */                        return request.newBuilder().header("Proxy-Authorization", credential).build();
                            }
                        }
                    }
                    return null;
                }

/* 82 */        private InetAddress getConnectToInetAddress(Proxy proxy, URL url) throws IOException {
                    return (proxy == null || proxy.type() == Proxy.Type.DIRECT) ? InetAddress.getByName(url.getHost()) : ((InetSocketAddress) proxy.address()).getAddress();
                }
            }
