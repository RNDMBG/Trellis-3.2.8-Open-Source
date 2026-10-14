            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class TrelloApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "https://trello.com/1/OAuthAuthorizeToken?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 11 */        public String getAccessTokenEndpoint() {
/* 12 */            return "https://trello.com/1/OAuthGetAccessToken";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 17 */        public String getRequestTokenEndpoint() {
/* 18 */            return "https://trello.com/1/OAuthGetRequestToken";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 23 */        public String getAuthorizationUrl(Token requestToken) {
/* 24 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }
            }
