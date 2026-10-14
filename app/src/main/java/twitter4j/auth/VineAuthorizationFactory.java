            package twitter4j.auth;

            import twitter4j.conf.Configuration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class VineAuthorizationFactory {
/* 34 */        public static Authorization getInstance(Configuration conf) {
/* 36 */            String consumerKey = conf.getOAuthConsumerKey();
/* 37 */            String consumerSecret = conf.getOAuthConsumerSecret();
/* 39 */            if (consumerKey != null && consumerSecret != null) {
/* 40 */                VineOauthAuthorization oauth = new VineOauthAuthorization(conf);
/* 41 */                String accessToken = conf.getOAuthAccessToken();
/* 42 */                String accessTokenSecret = conf.getOAuthAccessTokenSecret();
/* 43 */                if (accessToken != null && accessTokenSecret != null) {
/* 44 */                    oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
                        }
/* 46 */                return oauth;
                    }
/* 48 */            String screenName = conf.getUser();
/* 49 */            String password = conf.getPassword();
/* 50 */            if (screenName == null || password == null) {
/* 35 */                return null;
                    }
/* 51 */            Authorization auth = new BasicAuthorization(screenName, password);
/* 54 */            return auth;
                }
            }
