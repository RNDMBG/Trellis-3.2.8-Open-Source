            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class ConstantContactApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "https://oauth.constantcontact.com/ws/oauth/confirm_access?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 11 */        public String getAccessTokenEndpoint() {
/* 12 */            return "https://oauth.constantcontact.com/ws/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 17 */        public String getAuthorizationUrl(Token requestToken) {
/* 18 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 23 */        public String getRequestTokenEndpoint() {
/* 24 */            return "https://oauth.constantcontact.com/ws/oauth/request_token";
                }
            }
