            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            
            
    public class RenrenApi extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://graph.renren.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
                private static final String SCOPED_AUTHORIZE_URL = "https://graph.renren.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";

                @Override 
        public AccessTokenExtractor getAccessTokenExtractor() {
            return new JsonTokenExtractor();
                }

                @Override 
        public String getAccessTokenEndpoint() {
            return "https://graph.renren.com/oauth/token?grant_type=authorization_code";
                }

                @Override 
        public String getAuthorizationUrl(OAuthConfig config) {
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }
            }
