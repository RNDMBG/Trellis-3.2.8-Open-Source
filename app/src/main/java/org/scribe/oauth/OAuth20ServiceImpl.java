            package org.scribe.oauth;

            import org.scribe.builder.api.DefaultApi20;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.OAuthConstants;
            import org.scribe.model.OAuthRequest;
            import org.scribe.model.Response;
            import org.scribe.model.Token;
            import org.scribe.model.Verifier;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class OAuth20ServiceImpl implements OAuthService {
                private static final String VERSION = "2.0";
                private final DefaultApi20 api;
                private final OAuthConfig config;

/* 19 */        public OAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config) {
/* 21 */            this.api = api;
/* 22 */            this.config = config;
                }

                @Override // org.scribe.oauth.OAuthService
/* 29 */        public Token getAccessToken(Token requestToken, Verifier verifier) {
/* 30 */            OAuthRequest request = new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint());
/* 31 */            request.addQuerystringParameter("client_id", this.config.getApiKey());
/* 32 */            request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, this.config.getApiSecret());
/* 33 */            request.addQuerystringParameter("code", verifier.getValue());
/* 34 */            request.addQuerystringParameter("redirect_uri", this.config.getCallback());
/* 35 */            if (this.config.hasScope()) {
                        request.addQuerystringParameter("scope", this.config.getScope());
                    }
/* 36 */            Response response = request.send();
/* 37 */            return this.api.getAccessTokenExtractor().extract(response.getBody());
                }

                @Override // org.scribe.oauth.OAuthService
/* 44 */        public Token getRequestToken() {
/* 45 */            throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
                }

                @Override // org.scribe.oauth.OAuthService
/* 52 */        public String getVersion() {
/* 53 */            return VERSION;
                }

                @Override // org.scribe.oauth.OAuthService
/* 60 */        public void signRequest(Token accessToken, OAuthRequest request) {
/* 61 */            request.addQuerystringParameter("access_token", accessToken.getToken());
                }

                @Override // org.scribe.oauth.OAuthService
/* 68 */        public String getAuthorizationUrl(Token requestToken) {
/* 69 */            return this.api.getAuthorizationUrl(this.config);
                }
            }
