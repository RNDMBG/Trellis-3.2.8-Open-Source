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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class VineTwitter4j extends TwitterImpl {












/* 26 */        VineTwitter4j(Configuration conf, Authorization auth) {
/* 27 */            super(conf, auth);
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.auth.OAuthSupport
/* 31 */        public synchronized AccessToken getOAuthAccessToken() throws TwitterException {
                    AccessToken oauthAccessToken;
                    try {
                        Authorization auth = getAuthorization();
/* 34 */                if (auth instanceof BasicAuthorization) {
/* 35 */                    BasicAuthorization basicAuth = (BasicAuthorization) auth;
/* 36 */                    Authorization auth2 = VineAuthorizationFactory.getInstance(this.conf);
/* 37 */                    if (auth2 instanceof VineOauthAuthorization) {
/* 38 */                        this.auth = auth2;
/* 40 */                        oauthAccessToken = ((VineOauthAuthorization) auth2).getOAuthAccessToken(basicAuth.getUserId(), basicAuth.getPassword());
                            } else {
/* 42 */                        throw new IllegalStateException("consumer key / secret combination not supplied.");
                            }
                        } else if (auth instanceof XAuthAuthorization) {
/* 46 */                    XAuthAuthorization xauth = (XAuthAuthorization) auth;
/* 47 */                    this.auth = xauth;
/* 48 */                    OAuthAuthorization oauthAuth = new OAuthAuthorization(this.conf);
/* 49 */                    oauthAuth.setOAuthConsumer(xauth.getConsumerKey(), xauth.getConsumerSecret());
/* 50 */                    oauthAccessToken = oauthAuth.getOAuthAccessToken(xauth.getUserId(), xauth.getPassword());
                        } else {
/* 52 */                    oauthAccessToken = getOAuth().getOAuthAccessToken();
                        }
/* 55 */                this.screenName = oauthAccessToken.getScreenName();
/* 56 */                this.f2541id = oauthAccessToken.getUserId();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return oauthAccessToken;
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.auth.OAuthSupport
/* 61 */        public synchronized AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(oauthVerifier);
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.auth.OAuthSupport
/* 66 */        public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(requestToken);
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.auth.OAuthSupport
/* 71 */        public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(requestToken, oauthVerifier);
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.auth.OAuthSupport
/* 76 */        public synchronized AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return super.getOAuthAccessToken(screenName, password);
                }

/* 81 */        private OAuthSupport getOAuth() {
/* 82 */            if (!(this.auth instanceof OAuthSupport)) {
/* 83 */                throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
                    }
/* 86 */            return (OAuthSupport) this.auth;
                }

                @Override // twitter4j.TwitterBaseImpl, twitter4j.internal.http.HttpResponseListener
/* 120 */       public void httpResponseReceived(HttpResponseEvent event) {
                }
            }
