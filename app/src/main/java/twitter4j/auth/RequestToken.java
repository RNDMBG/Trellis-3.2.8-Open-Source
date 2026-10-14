            package twitter4j.auth;

            import java.io.Serializable;
            import twitter4j.TwitterException;
            import twitter4j.conf.Configuration;
            import twitter4j.conf.ConfigurationContext;
            import twitter4j.internal.http.HttpResponse;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public final class RequestToken extends OAuthToken implements Serializable {
                private static final long serialVersionUID = -8214365845469757952L;
                private final Configuration conf;
                private OAuthSupport oauth;







/* 33 */        RequestToken(HttpResponse res, OAuthSupport oauth) throws TwitterException {
/* 34 */            super(res);
/* 35 */            this.conf = ConfigurationContext.getInstance();
/* 36 */            this.oauth = oauth;
                }

/* 39 */        public RequestToken(String token, String tokenSecret) {
/* 40 */            super(token, tokenSecret);
/* 41 */            this.conf = ConfigurationContext.getInstance();
                }

/* 44 */        RequestToken(String token, String tokenSecret, OAuthSupport oauth) {
/* 45 */            super(token, tokenSecret);
/* 46 */            this.conf = ConfigurationContext.getInstance();
/* 47 */            this.oauth = oauth;
                }

/* 54 */        public String getAuthorizationURL() {
/* 55 */            return this.conf.getOAuthAuthorizationURL() + "?oauth_token=" + getToken();
                }

/* 62 */        public String getAuthenticationURL() {
/* 63 */            return this.conf.getOAuthAuthenticationURL() + "?oauth_token=" + getToken();
                }
            }
