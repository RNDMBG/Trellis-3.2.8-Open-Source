            package org.scribe.model;

            import java.io.Serializable;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class Token implements Serializable {
                private static final long serialVersionUID = 715000866082812683L;
                private final String rawResponse;
                private final String secret;
                private final String token;

/* 26 */        public Token(String token, String secret) {
/* 27 */            this(token, secret, null);
                }

/* 30 */        public Token(String token, String secret, String rawResponse) {
/* 32 */            Preconditions.checkNotNull(token, "Token can't be null");
/* 33 */            Preconditions.checkNotNull(secret, "Secret can't be null");
/* 35 */            this.token = token;
/* 36 */            this.secret = secret;
/* 37 */            this.rawResponse = rawResponse;
                }

/* 41 */        public String getToken() {
/* 42 */            return this.token;
                }

/* 46 */        public String getSecret() {
/* 47 */            return this.secret;
                }

/* 51 */        public String getRawResponse() {
/* 52 */            if (this.rawResponse == null) {
/* 54 */                throw new IllegalStateException("This token object was not constructed by scribe and does not have a rawResponse");
                    }
/* 56 */            return this.rawResponse;
                }

/* 61 */        public String toString() {
/* 62 */            return String.format("Token[%s , %s]", this.token, this.secret);
                }

/* 69 */        public boolean isEmpty() {
                    return "".equals(this.token) && "".equals(this.secret);
                }

/* 79 */        public static Token empty() {
/* 80 */            return new Token("", "");
                }

/* 85 */        public boolean equals(Object o) {
/* 86 */            if (this == o) {
                        return true;
                    }
/* 87 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 89 */            Token that = (Token) o;
                    return this.token.equals(that.token) && this.secret.equals(that.secret);
                }

/* 95 */        public int hashCode() {
/* 96 */            return (this.token.hashCode() * 31) + this.secret.hashCode();
                }
            }
