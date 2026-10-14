            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class SkyrockApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_RESOURCE = "/oauth/token";
                private static final String API_ENDPOINT = "https://api.skyrock.com/v2";
                private static final String AUTHORIZE_URL = "/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_RESOURCE = "/oauth/initiate";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 20 */        public String getAccessTokenEndpoint() {
/* 21 */            return "https://api.skyrock.com/v2/oauth/token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 26 */        public String getRequestTokenEndpoint() {
/* 27 */            return "https://api.skyrock.com/v2/oauth/initiate";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 32 */        public String getAuthorizationUrl(Token requestToken) {
/* 33 */            return String.format("https://api.skyrock.com/v2/oauth/authorize?oauth_token=%s", requestToken.getToken());
                }
            }
