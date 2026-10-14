            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class EvernoteApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "https://www.evernote.com/OAuth.action?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 11 */        public String getRequestTokenEndpoint() {
/* 12 */            return "https://www.evernote.com/oauth";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 17 */        public String getAccessTokenEndpoint() {
/* 18 */            return "https://www.evernote.com/oauth";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 23 */        public String getAuthorizationUrl(Token requestToken) {
/* 24 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }

/* 32 */        public static class Sandbox extends EvernoteApi {
                    private static final String SANDBOX_URL = "https://sandbox.evernote.com";

                    @Override // org.scribe.builder.api.EvernoteApi, org.scribe.builder.api.DefaultApi10a
/* 33 */            public String getRequestTokenEndpoint() {
/* 34 */                return "https://sandbox.evernote.com/oauth";
                    }

                    @Override // org.scribe.builder.api.EvernoteApi, org.scribe.builder.api.DefaultApi10a
/* 39 */            public String getAccessTokenEndpoint() {
/* 40 */                return "https://sandbox.evernote.com/oauth";
                    }

                    @Override // org.scribe.builder.api.EvernoteApi, org.scribe.builder.api.DefaultApi10a
/* 45 */            public String getAuthorizationUrl(Token requestToken) {
/* 46 */                return String.format("https://sandbox.evernote.com/OAuth.action?oauth_token=%s", requestToken.getToken());
                    }
                }
            }
