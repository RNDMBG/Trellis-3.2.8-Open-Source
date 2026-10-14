            package twitter4j.auth;

            import android.support.annotation.Nullable;
            import java.io.Serializable;
            import javax.crypto.spec.SecretKeySpec;
            import org.scribe.model.OAuthConstants;
            import twitter4j.TwitterException;
            import twitter4j.internal.http.HttpResponse;
            import twitter4j.internal.util.z_T4JInternalStringUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    abstract class OAuthToken implements Serializable {
                private static final long serialVersionUID = 3891133932519746686L;
                String[] responseStr;
                private transient SecretKeySpec secretKeySpec;
                private String token;
                private String tokenSecret;

/* 35 */        public OAuthToken(String token, String tokenSecret) {
/* 34 */            this.responseStr = null;
/* 37 */            this.token = token;
/* 38 */            this.tokenSecret = tokenSecret;
                }

/* 41 */        OAuthToken(HttpResponse response) throws TwitterException {
/* 42 */            this(response.asString());
                }

/* 44 */        OAuthToken(String string) {
/* 34 */            this.responseStr = null;
/* 46 */            this.responseStr = z_T4JInternalStringUtil.split(string, "&");
/* 47 */            this.tokenSecret = getParameter(OAuthConstants.TOKEN_SECRET);
/* 48 */            this.token = getParameter(OAuthConstants.TOKEN);
                }

                @Nullable
/* 52 */        public String getToken() {
/* 53 */            return this.token;
                }

                @Nullable
/* 57 */        public String getTokenSecret() {
/* 58 */            return this.tokenSecret;
                }

/* 61 */        void setSecretKeySpec(SecretKeySpec secretKeySpec) {
/* 62 */            this.secretKeySpec = secretKeySpec;
                }

/* 65 */        SecretKeySpec getSecretKeySpec() {
/* 66 */            return this.secretKeySpec;
                }

/* 69 */        public String getParameter(String parameter) {
/* 71 */            String[] arr$ = this.responseStr;
                    for (String str : arr$) {
/* 72 */                if (str.startsWith(parameter + '=')) {
/* 73 */                    String[] splitResult = z_T4JInternalStringUtil.split(str, "=");
/* 74 */                    if (splitResult.length <= 1 || splitResult[1] == null) {
/* 70 */                        return null;
                            }
/* 75 */                    String value = splitResult[1].trim();
/* 80 */                    return value;
                        }
                    }
/* 70 */            return null;
                }

/* 84 */        public boolean equals(Object o) {
/* 85 */            if (this == o) {
                        return true;
                    }
/* 86 */            if (!(o instanceof OAuthToken)) {
                        return false;
                    }
/* 88 */            OAuthToken that = (OAuthToken) o;
/* 90 */            if (this.token == null ? that.token != null : !this.token.equals(that.token)) {
                        return false;
                    }
/* 92 */            if (this.tokenSecret != null) {
                        if (this.tokenSecret.equals(that.tokenSecret)) {
                            return true;
                        }
                    } else if (that.tokenSecret == null) {
                        return true;
                    }
                    return false;
                }

/* 99 */        public int hashCode() {
/* 100 */           int result = this.token.hashCode();
/* 101 */           return (result * 31) + this.tokenSecret.hashCode();
                }

/* 106 */       public String toString() {
/* 107 */           return "OAuthToken{token='" + this.token + "', tokenSecret='" + this.tokenSecret + "', secretKeySpec=" + this.secretKeySpec + '}';
                }
            }
