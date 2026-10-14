            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class MeetupApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "http://www.meetup.com/authenticate?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 15 */        public String getRequestTokenEndpoint() {
/* 16 */            return "http://api.meetup.com/oauth/request/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 21 */        public String getAccessTokenEndpoint() {
/* 22 */            return "http://api.meetup.com/oauth/access/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 27 */        public String getAuthorizationUrl(Token requestToken) {
/* 28 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }
            }
