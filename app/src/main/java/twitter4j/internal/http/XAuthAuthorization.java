            package twitter4j.internal.http;

            import java.io.Serializable;
            import twitter4j.auth.Authorization;
            import twitter4j.auth.BasicAuthorization;
            
            
    public class XAuthAuthorization implements Authorization, Serializable {
                private static final long serialVersionUID = -6082451214083464902L;
                private BasicAuthorization basic;
                private String consumerKey;
                private String consumerSecret;

        public XAuthAuthorization(BasicAuthorization basic) {
            this.basic = basic;
                }

                @Override 
        public String getAuthorizationHeader(HttpRequest req) {
            return this.basic.getAuthorizationHeader(req);
                }

        public String getUserId() {
            return this.basic.getUserId();
                }

        public String getPassword() {
            return this.basic.getPassword();
                }

        public String getConsumerKey() {
            return this.consumerKey;
                }

        public String getConsumerSecret() {
            return this.consumerSecret;
                }

        public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
                    try {
                        this.consumerKey = consumerKey;
                this.consumerSecret = consumerSecret;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public boolean isEnabled() {
            return this.basic.isEnabled();
                }
            }
