            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class NeteaseWeibooApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_URL = "http://api.t.163.com/oauth/access_token";
                private static final String AUTHENTICATE_URL = "http://api.t.163.com/oauth/authenticate?oauth_token=%s";
                private static final String AUTHORIZE_URL = "http://api.t.163.com/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_URL = "http://api.t.163.com/oauth/request_token";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 14 */        public String getRequestTokenEndpoint() {
/* 15 */            return REQUEST_TOKEN_URL;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 20 */        public String getAccessTokenEndpoint() {
/* 21 */            return ACCESS_TOKEN_URL;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 33 */        public String getAuthorizationUrl(Token requestToken) {
/* 34 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

/* 44 */        public String getAuthenticateUrl(Token requestToken) {
/* 45 */            return String.format(AUTHENTICATE_URL, requestToken.getToken());
                }
            }
