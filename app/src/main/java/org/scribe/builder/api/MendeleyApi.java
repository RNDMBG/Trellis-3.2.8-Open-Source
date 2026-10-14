            package org.scribe.builder.api;

            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class MendeleyApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "http://api.mendeley.com/oauth/authorize?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 16 */        public String getRequestTokenEndpoint() {
/* 17 */            return "http://api.mendeley.com/oauth/request_token/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 22 */        public String getAccessTokenEndpoint() {
/* 23 */            return "http://api.mendeley.com/oauth/access_token/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 28 */        public String getAuthorizationUrl(Token requestToken) {
/* 29 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 34 */        public Verb getAccessTokenVerb() {
/* 35 */            return Verb.GET;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 40 */        public Verb getRequestTokenVerb() {
/* 41 */            return Verb.GET;
                }
            }
