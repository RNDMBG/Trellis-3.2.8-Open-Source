            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            
    public class SkyrockApi extends DefaultApi10a {
                private static final String ACCESS_TOKEN_RESOURCE = "/oauth/token";
                private static final String API_ENDPOINT = "https://api.skyrock.com/v2";
                private static final String AUTHORIZE_URL = "/oauth/authorize?oauth_token=%s";
                private static final String REQUEST_TOKEN_RESOURCE = "/oauth/initiate";

                @Override 
        public String getAccessTokenEndpoint() {
            return "https://api.skyrock.com/v2/oauth/token";
                }

                @Override 
        public String getRequestTokenEndpoint() {
            return "https://api.skyrock.com/v2/oauth/initiate";
                }

                @Override 
        public String getAuthorizationUrl(Token requestToken) {
            return String.format("https://api.skyrock.com/v2/oauth/authorize?oauth_token=%s", requestToken.getToken());
                }
            }
