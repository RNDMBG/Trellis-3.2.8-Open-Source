            package twitter4j.auth;

            import java.io.Serializable;
            import twitter4j.internal.http.BASE64Encoder;
            import twitter4j.internal.http.HttpRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class BasicAuthorization implements Authorization, Serializable {
                private static final long serialVersionUID = -5861104407848415060L;
                private String basic = encodeBasicAuthenticationString();
                private String password;
                private String userId;

/* 34 */        public BasicAuthorization(String userId, String password) {
/* 36 */            this.userId = userId;
/* 37 */            this.password = password;
                }

/* 41 */        public String getUserId() {
/* 42 */            return this.userId;
                }

/* 45 */        public String getPassword() {
/* 46 */            return this.password;
                }

/* 49 */        private String encodeBasicAuthenticationString() {
/* 50 */            if (this.userId == null || this.password == null) {
                        return null;
                    }
/* 51 */            return "Basic " + BASE64Encoder.encode((this.userId + ":" + this.password).getBytes());
                }

                @Override // twitter4j.auth.Authorization
/* 56 */        public String getAuthorizationHeader(HttpRequest req) {
/* 57 */            return this.basic;
                }

                @Override // twitter4j.auth.Authorization
/* 63 */        public boolean isEnabled() {
/* 64 */            return true;
                }

/* 68 */        public boolean equals(Object o) {
/* 69 */            if (this == o) {
                        return true;
                    }
/* 70 */            if (!(o instanceof BasicAuthorization)) {
                        return false;
                    }
/* 72 */            BasicAuthorization that = (BasicAuthorization) o;
/* 74 */            return this.basic.equals(that.basic);
                }

/* 79 */        public int hashCode() {
/* 80 */            return this.basic.hashCode();
                }

/* 84 */        public String toString() {
/* 85 */            return "BasicAuthorization{userId='" + this.userId + "', password='**********''}";
                }
            }
