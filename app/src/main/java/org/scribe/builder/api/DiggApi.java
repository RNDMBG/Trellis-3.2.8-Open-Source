            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class DiggApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "http://digg.com/oauth/authorize?oauth_token=%s";
                private static final String BASE_URL = "http://services.digg.com/oauth/";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 13 */        public String getRequestTokenEndpoint() {
/* 14 */            return "http://services.digg.com/oauth/request_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getAccessTokenEndpoint() {
/* 20 */            return "http://services.digg.com/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAuthorizationUrl(Token requestToken) {
/* 26 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }
            }
