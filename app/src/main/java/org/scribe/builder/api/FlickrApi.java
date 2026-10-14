            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            
    public class FlickrApi extends DefaultApi10a {
                @Override 
        public String getAccessTokenEndpoint() {
            return "http://www.flickr.com/services/oauth/access_token";
                }

                @Override 
        public String getAuthorizationUrl(Token requestToken) {
            return "http://www.flickr.com/services/oauth/authorize?oauth_token=" + requestToken.getToken();
                }

                @Override 
        public String getRequestTokenEndpoint() {
            return "http://www.flickr.com/services/oauth/request_token";
                }
            }
