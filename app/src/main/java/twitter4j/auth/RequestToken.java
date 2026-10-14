            package twitter4j.auth;

            import java.io.Serializable;
            import twitter4j.TwitterException;
            import twitter4j.conf.Configuration;
            import twitter4j.conf.ConfigurationContext;
            import twitter4j.internal.http.HttpResponse;
            
            
    public final class RequestToken extends OAuthToken implements Serializable {
                private static final long serialVersionUID = -8214365845469757952L;
                private final Configuration conf;
                private OAuthSupport oauth;







        RequestToken(HttpResponse res, OAuthSupport oauth) throws TwitterException {
            super(res);
            this.conf = ConfigurationContext.getInstance();
            this.oauth = oauth;
                }

        public RequestToken(String token, String tokenSecret) {
            super(token, tokenSecret);
            this.conf = ConfigurationContext.getInstance();
                }

        RequestToken(String token, String tokenSecret, OAuthSupport oauth) {
            super(token, tokenSecret);
            this.conf = ConfigurationContext.getInstance();
            this.oauth = oauth;
                }

        public String getAuthorizationURL() {
            return this.conf.getOAuthAuthorizationURL() + "?oauth_token=" + getToken();
                }

        public String getAuthenticationURL() {
            return this.conf.getOAuthAuthenticationURL() + "?oauth_token=" + getToken();
                }
            }
