            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class FlickrApi extends DefaultApi10a {
                @Override // org.scribe.builder.api.DefaultApi10a
/* 19 */        public String getAccessTokenEndpoint() {
/* 20 */            return "http://www.flickr.com/services/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 28 */        public String getAuthorizationUrl(Token requestToken) {
/* 29 */            return "http://www.flickr.com/services/oauth/authorize?oauth_token=" + requestToken.getToken();
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 37 */        public String getRequestTokenEndpoint() {
/* 38 */            return "http://www.flickr.com/services/oauth/request_token";
                }
            }
