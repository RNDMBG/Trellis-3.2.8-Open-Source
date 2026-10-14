            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class DropBoxApi extends DefaultApi10a {
                @Override // org.scribe.builder.api.DefaultApi10a
/* 9 */         public String getAccessTokenEndpoint() {
/* 10 */            return "https://api.dropbox.com/1/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 15 */        public String getAuthorizationUrl(Token requestToken) {
/* 16 */            return "https://www.dropbox.com/1/oauth/authorize?oauth_token=" + requestToken.getToken();
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 21 */        public String getRequestTokenEndpoint() {
/* 22 */            return "https://api.dropbox.com/1/oauth/request_token";
                }
            }
