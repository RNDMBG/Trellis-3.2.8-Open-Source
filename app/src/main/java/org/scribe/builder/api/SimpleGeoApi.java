            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SimpleGeoApi extends DefaultApi10a {
                private static final String ENDPOINT = "these are not used since SimpleGeo uses 2 legged OAuth";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 14 */        public String getRequestTokenEndpoint() {
/* 15 */            return ENDPOINT;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 20 */        public String getAccessTokenEndpoint() {
/* 21 */            return ENDPOINT;
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 26 */        public String getAuthorizationUrl(Token requestToken) {
/* 27 */            return ENDPOINT;
                }
            }
