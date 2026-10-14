            package org.scribe.builder.api;

            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class GoogleApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 11 */        public String getAccessTokenEndpoint() {
/* 12 */            return "https://www.google.com/accounts/OAuthGetAccessToken";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 17 */        public String getRequestTokenEndpoint() {
/* 18 */            return "https://www.google.com/accounts/OAuthGetRequestToken";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 23 */        public Verb getAccessTokenVerb() {
/* 24 */            return Verb.GET;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 29 */        public Verb getRequestTokenVerb() {
/* 30 */            return Verb.GET;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 35 */        public String getAuthorizationUrl(Token requestToken) {
/* 36 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }
            }
