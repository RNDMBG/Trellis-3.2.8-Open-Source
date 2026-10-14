            package twitter4j.auth;

            import twitter4j.conf.Configuration;
            
            
    public class VineAuthorizationFactory {
        public static Authorization getInstance(Configuration conf) {
            String consumerKey = conf.getOAuthConsumerKey();
            String consumerSecret = conf.getOAuthConsumerSecret();
            if (consumerKey != null && consumerSecret != null) {
                VineOauthAuthorization oauth = new VineOauthAuthorization(conf);
                String accessToken = conf.getOAuthAccessToken();
                String accessTokenSecret = conf.getOAuthAccessTokenSecret();
                if (accessToken != null && accessTokenSecret != null) {
                    oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
                        }
                return oauth;
                    }
            String screenName = conf.getUser();
            String password = conf.getPassword();
            if (screenName == null || password == null) {
                return null;
                    }
            Authorization auth = new BasicAuthorization(screenName, password);
            return auth;
                }
            }
