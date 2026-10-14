            package org.scribe.builder.api;

            import org.scribe.model.Token;
            import org.scribe.services.PlaintextSignatureService;
            import org.scribe.services.SignatureService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class UbuntuOneApi extends DefaultApi10a {
                private static final String AUTHORIZATION_URL = "https://one.ubuntu.com/oauth/authorize/?oauth_token=%s";

                @Override // org.scribe.builder.api.DefaultApi10a
/* 18 */        public String getAccessTokenEndpoint() {
/* 19 */            return "https://one.ubuntu.com/oauth/access/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 24 */        public String getAuthorizationUrl(Token requestToken) {
/* 25 */            return String.format(AUTHORIZATION_URL, requestToken.getToken());
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 30 */        public String getRequestTokenEndpoint() {
/* 31 */            return "https://one.ubuntu.com/oauth/request/";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 36 */        public SignatureService getSignatureService() {
/* 37 */            return new PlaintextSignatureService();
                }
            }
