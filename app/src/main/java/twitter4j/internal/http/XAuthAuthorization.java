            package twitter4j.internal.http;

            import java.io.Serializable;
            import twitter4j.auth.Authorization;
            import twitter4j.auth.BasicAuthorization;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class XAuthAuthorization implements Authorization, Serializable {
                private static final long serialVersionUID = -6082451214083464902L;
                private BasicAuthorization basic;
                private String consumerKey;
                private String consumerSecret;

/* 32 */        public XAuthAuthorization(BasicAuthorization basic) {
/* 34 */            this.basic = basic;
                }

                @Override // twitter4j.auth.Authorization
/* 38 */        public String getAuthorizationHeader(HttpRequest req) {
/* 39 */            return this.basic.getAuthorizationHeader(req);
                }

/* 42 */        public String getUserId() {
/* 43 */            return this.basic.getUserId();
                }

/* 46 */        public String getPassword() {
/* 47 */            return this.basic.getPassword();
                }

/* 50 */        public String getConsumerKey() {
/* 51 */            return this.consumerKey;
                }

/* 54 */        public String getConsumerSecret() {
/* 55 */            return this.consumerSecret;
                }

/* 58 */        public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
                    try {
                        this.consumerKey = consumerKey;
/* 60 */                this.consumerSecret = consumerSecret;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // twitter4j.auth.Authorization
/* 64 */        public boolean isEnabled() {
/* 65 */            return this.basic.isEnabled();
                }
            }
