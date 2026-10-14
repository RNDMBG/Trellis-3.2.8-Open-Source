            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class TwitterApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_RESOURCE = "api.twitter.com/oauth/access_token";
                private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_RESOURCE = "api.twitter.com/oauth/request_token";

                public static class Authorize extends SSL {
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 13 */        public String getAccessTokenEndpoint() {
/* 14 */            return "http://api.twitter.com/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getRequestTokenEndpoint() {
/* 20 */            return "http://api.twitter.com/oauth/request_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAuthorizationUrl(Token requestToken) {
/* 26 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

/* 32 */        public static class SSL extends TwitterApi {
                    @Override // org.scribe.builder.api.TwitterApi, org.scribe.builder.api.DefaultApi10a
/* 33 */            public String getAccessTokenEndpoint() {
/* 34 */                return "https://api.twitter.com/oauth/access_token";
                    }

                    @Override // org.scribe.builder.api.TwitterApi, org.scribe.builder.api.DefaultApi10a
/* 39 */            public String getRequestTokenEndpoint() {
/* 40 */                return "https://api.twitter.com/oauth/request_token";
                    }
                }

/* 54 */        public static class Authenticate extends SSL {
                    private static final String AUTHENTICATE_URL = "https://api.twitter.com/oauth/authenticate?oauth_token=%s";

                    @Override // org.scribe.builder.api.TwitterApi, org.scribe.builder.api.DefaultApi10a
/* 55 */            public String getAuthorizationUrl(Token requestToken) {
/* 56 */                return String.format(AUTHENTICATE_URL, requestToken.getToken());
                    }
                }
            }
