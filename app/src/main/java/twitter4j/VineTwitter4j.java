            package twitter4j;

            import twitter4j.auth.AccessToken;
            import twitter4j.auth.Authorization;
            import twitter4j.auth.BasicAuthorization;
            import twitter4j.auth.OAuthAuthorization;
            import twitter4j.auth.OAuthSupport;
            import twitter4j.auth.RequestToken;
            import twitter4j.auth.VineAuthorizationFactory;
            import twitter4j.auth.VineOauthAuthorization;
            import twitter4j.conf.Configuration;
            import twitter4j.internal.http.HttpResponseEvent;
            import twitter4j.internal.http.XAuthAuthorization;
            
            
    public class VineTwitter4j extends TwitterImpl {












        VineTwitter4j(Configuration conf, Authorization auth) {
            super(conf, auth);
                }

                @Override 
        public synchronized AccessToken getOAuthAccessToken() throws TwitterException {
                    AccessToken oauthAccessToken;
                    try {
                        Authorization auth = getAuthorization();
                if (auth instanceof BasicAuthorization) {
                    BasicAuthorization basicAuth = (BasicAuthorization) auth;
                    Authorization auth2 = VineAuthorizationFactory.getInstance(this.conf);
                    if (auth2 instanceof VineOauthAuthorization) {
                        this.auth = auth2;
                        oauthAccessToken = ((VineOauthAuthorization) auth2).getOAuthAccessToken(basicAuth.getUserId(), basicAuth.getPassword());
                            } else {
                        throw new IllegalStateException("consumer key / secret combination not supplied.");
                            }
                        } else if (auth instanceof XAuthAuthorization) {
                    XAuthAuthorization xauth = (XAuthAuthorization) auth;
                    this.auth = xauth;
                    OAuthAuthorization oauthAuth = new OAuthAuthorization(this.conf);
                    oauthAuth.setOAuthConsumer(xauth.getConsumerKey(), xauth.getConsumerSecret());
                    oauthAccessToken = oauthAuth.getOAuthAccessToken(xauth.getUserId(), xauth.getPassword());
                        } else {
                    oauthAccessToken = getOAuth().getOAuthAccessToken();
                        }
                this.screenName = oauthAccessToken.getScreenName();
                this.f2541id = oauthAccessToken.getUserId();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return oauthAccessToken;
                }

                @Override 
        public synchronized AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(oauthVerifier);
                }

                @Override 
        public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(requestToken);
                }

                @Override 
        public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(requestToken, oauthVerifier);
                }

                @Override 
        public synchronized AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(screenName, password);
                }

        private OAuthSupport getOAuth() {
            if (!(this.auth instanceof OAuthSupport)) {
                throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
                    }
            return (OAuthSupport) this.auth;
                }

                @Override 
       public void httpResponseReceived(HttpResponseEvent event) {
                }
            }
