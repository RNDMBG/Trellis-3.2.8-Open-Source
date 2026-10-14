            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class PlurkApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_URL = "http://www.plurk.com/OAuth/access_token";
                private static final String AUTHORIZATION_URL = "http://www.plurk.com/OAuth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_URL = "http://www.plurk.com/OAuth/request_token";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 13 */        public String getRequestTokenEndpoint() {
/* 14 */            return REQUEST_TOKEN_URL;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getAuthorizationUrl(Token requestToken) {
/* 20 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAccessTokenEndpoint() {
/* 26 */            return ACCESS_TOKEN_URL;
                }

/* 34 */        public static class Mobile extends PlurkApi {
                    private static final String AUTHORIZATION_URL = "http://www.plurk.com/m/authorize?oauth_token=%s";

                    @Override // org.scribe.builder.api.PlurkApi, org.scribe.builder.api.DefaultApi10a
/* 35 */            public String getAuthorizationUrl(Token requestToken) {
/* 36 */                return String.format(AUTHORIZATION_URL, requestToken.getToken());
                    }
                }
            }
