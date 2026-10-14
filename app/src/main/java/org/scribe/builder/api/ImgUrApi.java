            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class ImgUrApi extends DefaultApi10a {
                @Override // org.scribe.builder.api.DefaultApi10a
/* 16 */        public String getRequestTokenEndpoint() {
/* 17 */            return "https://api.imgur.com/oauth/request_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 22 */        public String getAccessTokenEndpoint() {
/* 23 */            return "https://api.imgur.com/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 28 */        public String getAuthorizationUrl(Token requestToken) {
/* 29 */            return String.format("https://api.imgur.com/oauth/authorize?oauth_token=%s", requestToken.getToken());
                }
            }
