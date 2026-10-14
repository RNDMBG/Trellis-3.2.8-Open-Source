            package org.scribe.oauth;

            import org.scribe.builder.api.DefaultApi20;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.OAuthConstants;
            import org.scribe.model.OAuthRequest;
            import org.scribe.model.Response;
            import org.scribe.model.Token;
            import org.scribe.model.Verifier;
            
            
    public class OAuth20ServiceImpl implements OAuthService {
                private static final String VERSION = "2.0";
                private final DefaultApi20 api;
                private final OAuthConfig config;

        public OAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config) {
            this.api = api;
            this.config = config;
                }

                @Override 
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            OAuthRequest request = new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint());
            request.addQuerystringParameter("client_id", this.config.getApiKey());
            request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, this.config.getApiSecret());
            request.addQuerystringParameter("code", verifier.getValue());
            request.addQuerystringParameter("redirect_uri", this.config.getCallback());
            if (this.config.hasScope()) {
                        request.addQuerystringParameter("scope", this.config.getScope());
                    }
            Response response = request.send();
            return this.api.getAccessTokenExtractor().extract(response.getBody());
                }

                @Override 
        public Token getRequestToken() {
            throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
                }

                @Override 
        public String getVersion() {
            return VERSION;
                }

                @Override 
        public void signRequest(Token accessToken, OAuthRequest request) {
            request.addQuerystringParameter("access_token", accessToken.getToken());
                }

                @Override 
        public String getAuthorizationUrl(Token requestToken) {
            return this.api.getAuthorizationUrl(this.config);
                }
            }
