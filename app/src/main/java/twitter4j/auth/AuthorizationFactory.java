            package twitter4j.auth;

            import twitter4j.conf.Configuration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class AuthorizationFactory {
/* 33 */        public static Authorization getInstance(Configuration conf) {
/* 35 */            String consumerKey = conf.getOAuthConsumerKey();
/* 36 */            String consumerSecret = conf.getOAuthConsumerSecret();
/* 38 */            if (consumerKey != null && consumerSecret != null) {
/* 39 */                if (conf.isApplicationOnlyAuthEnabled()) {
/* 34 */                    return null;
                        }
/* 50 */                OAuthAuthorization oauth = new OAuthAuthorization(conf);
/* 51 */                String accessToken = conf.getOAuthAccessToken();
/* 52 */                String accessTokenSecret = conf.getOAuthAccessTokenSecret();
/* 53 */                if (accessToken != null && accessTokenSecret != null) {
/* 54 */                    oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
                        }
/* 56 */                return oauth;
                    }
/* 59 */            String screenName = conf.getUser();
/* 60 */            String password = conf.getPassword();
/* 61 */            if (screenName == null || password == null) {
/* 34 */                return null;
                    }
/* 62 */            Authorization auth = new BasicAuthorization(screenName, password);
/* 68 */            return auth;
                }
            }
