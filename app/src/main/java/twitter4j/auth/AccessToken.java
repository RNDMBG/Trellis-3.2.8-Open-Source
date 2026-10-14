            package twitter4j.auth;

            import java.io.Serializable;
            import twitter4j.TwitterException;
            import twitter4j.internal.http.HttpResponse;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class AccessToken extends OAuthToken implements Serializable {
                private static final long serialVersionUID = -8344528374458826291L;
                private String screenName;
                private long userId;




/* 33 */        AccessToken(HttpResponse res) throws TwitterException {
/* 34 */            this(res.asString());
                }

/* 37 */        AccessToken(String str) {
/* 38 */            super(str);
/* 31 */            this.userId = -1L;
/* 39 */            this.screenName = getParameter("screen_name");
/* 40 */            String sUserId = getParameter("user_id");
/* 41 */            if (sUserId != null) {
/* 42 */                this.userId = Long.parseLong(sUserId);
                    }
                }

/* 46 */        public AccessToken(String token, String tokenSecret) {
/* 47 */            super(token, tokenSecret);
/* 31 */            this.userId = -1L;
/* 49 */            int dashIndex = token.indexOf("-");
/* 50 */            if (dashIndex != -1) {
/* 51 */                String sUserId = token.substring(0, dashIndex);
                        try {
/* 53 */                    this.userId = Long.parseLong(sUserId);
                        } catch (NumberFormatException e) {
                        }
                    }
                }

/* 59 */        public AccessToken(String token, String tokenSecret, long userId) {
/* 60 */            super(token, tokenSecret);
/* 31 */            this.userId = -1L;
/* 61 */            this.userId = userId;
                }

/* 69 */        public String getScreenName() {
/* 70 */            return this.screenName;
                }

/* 78 */        public long getUserId() {
/* 79 */            return this.userId;
                }

                @Override // twitter4j.auth.OAuthToken
/* 83 */        public boolean equals(Object o) {
/* 84 */            if (this == o) {
                        return true;
                    }
/* 85 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 86 */            if (!super.equals(o)) {
                        return false;
                    }
/* 88 */            AccessToken that = (AccessToken) o;
                    if (this.userId != that.userId) {
                        return false;
                    }
/* 91 */            if (this.screenName != null) {
                        if (this.screenName.equals(that.screenName)) {
                            return true;
                        }
                    } else if (that.screenName == null) {
                        return true;
                    }
                    return false;
                }

                @Override // twitter4j.auth.OAuthToken
/* 98 */        public int hashCode() {
/* 99 */            int result = super.hashCode();
/* 101 */           return (((result * 31) + (this.screenName != null ? this.screenName.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)));
                }

                @Override // twitter4j.auth.OAuthToken
/* 106 */       public String toString() {
/* 107 */           return "AccessToken{screenName='" + this.screenName + "', userId=" + this.userId + '}';
                }
            }
