            package org.scribe.builder.api;

            import org.scribe.model.Token;
            
            
    public class MeetupApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "http://www.meetup.com/authenticate?oauth_token=%s";

                @Override 
        public String getRequestTokenEndpoint() {
            return "http://api.meetup.com/oauth/request/";
                }

                @Override 
        public String getAccessTokenEndpoint() {
            return "http://api.meetup.com/oauth/access/";
                }

                @Override 
        public String getAuthorizationUrl(Token requestToken) {
            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }
            }
