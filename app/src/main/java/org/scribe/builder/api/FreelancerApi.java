            package org.scribe.builder.api;

            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class FreelancerApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "http://www.freelancer.com/users/api-token/auth.php?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 11 */        public String getAccessTokenEndpoint() {
/* 12 */            return "http://api.freelancer.com/RequestAccessToken/requestAccessToken.xml?";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 17 */        public String getRequestTokenEndpoint() {
/* 18 */            return "http://api.freelancer.com/RequestRequestToken/requestRequestToken.xml";
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

/* 44 */        public static class Sandbox extends FreelancerApi {
                    private static final String SANDBOX_AUTHORIZATION_URL = "http://www.sandbox.freelancer.com/users/api-token/auth.php";

                    @Override // org.scribe.builder.api.FreelancerApi, org.scribe.builder.api.DefaultApi10a
/* 45 */            public String getRequestTokenEndpoint() {
/* 46 */                return "http://api.sandbox.freelancer.com/RequestRequestToken/requestRequestToken.xml";
                    }

                    @Override // org.scribe.builder.api.FreelancerApi, org.scribe.builder.api.DefaultApi10a
/* 51 */            public String getAccessTokenEndpoint() {
/* 52 */                return "http://api.sandbox.freelancer.com/RequestAccessToken/requestAccessToken.xml?";
                    }

                    @Override // org.scribe.builder.api.FreelancerApi, org.scribe.builder.api.DefaultApi10a
/* 57 */            public String getAuthorizationUrl(Token requestToken) {
/* 58 */                return String.format("http://www.sandbox.freelancer.com/users/api-token/auth.php?oauth_token=%s", requestToken.getToken());
                    }
                }
            }
