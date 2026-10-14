            package org.scribe.builder.api;

            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class SapoApi extends DefaultApi10a {
                private static final String ACCESS_URL = "https://id.sapo.pt/oauth/access_token";
                private static final String AUTHORIZE_URL = "https://id.sapo.pt/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_URL = "https://id.sapo.pt/oauth/request_token";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 13 */        public String getAccessTokenEndpoint() {
/* 14 */            return ACCESS_URL;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getRequestTokenEndpoint() {
/* 20 */            return REQUEST_URL;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAuthorizationUrl(Token requestToken) {
/* 26 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 31 */        public Verb getRequestTokenVerb() {
/* 32 */            return Verb.GET;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 37 */        public Verb getAccessTokenVerb() {
/* 38 */            return Verb.GET;
                }
            }
