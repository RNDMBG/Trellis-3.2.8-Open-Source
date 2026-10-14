            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class MisoApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_RESOURCE = "http://gomiso.com/oauth/access_token";
                private static final String AUTHORIZE_URL = "http://gomiso.com/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_RESOURCE = "http://gomiso.com/oauth/request_token";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 13 */        public String getAccessTokenEndpoint() {
/* 14 */            return ACCESS_TOKEN_RESOURCE;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getRequestTokenEndpoint() {
/* 20 */            return REQUEST_TOKEN_RESOURCE;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAuthorizationUrl(Token requestToken) {
/* 26 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }
            }
