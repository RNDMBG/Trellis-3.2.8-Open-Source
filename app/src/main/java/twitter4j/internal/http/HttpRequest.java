            package twitter4j.internal.http;

            import java.io.Serializable;
            import java.util.Arrays;
            import java.util.Map;
            import twitter4j.auth.Authorization;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public final class HttpRequest implements Serializable {
                private static final HttpParameter[] NULL_PARAMETERS = new HttpParameter[0];
                private static final long serialVersionUID = -3463594029098858381L;
                private final Authorization authorization;
                private final RequestMethod method;
                private final HttpParameter[] parameters;
                private Map<String, String> requestHeaders;
                private final String url;

/* 53 */        public HttpRequest(RequestMethod method, String url, HttpParameter[] parameters, Authorization authorization, Map<String, String> requestHeaders) {
/* 55 */            this.method = method;
/* 56 */            if (method != RequestMethod.POST && parameters != null && parameters.length != 0) {
/* 57 */                this.url = url + "?" + HttpParameter.encodeParameters(parameters);
/* 58 */                this.parameters = NULL_PARAMETERS;
                    } else {
/* 60 */                this.url = url;
/* 61 */                this.parameters = parameters;
                    }
/* 63 */            this.authorization = authorization;
/* 64 */            this.requestHeaders = requestHeaders;
                }

/* 67 */        public RequestMethod getMethod() {
/* 68 */            return this.method;
                }

/* 71 */        public HttpParameter[] getParameters() {
/* 72 */            return this.parameters;
                }

/* 75 */        public String getURL() {
/* 76 */            return this.url;
                }

/* 79 */        public Authorization getAuthorization() {
/* 80 */            return this.authorization;
                }

/* 83 */        public Map<String, String> getRequestHeaders() {
/* 84 */            return this.requestHeaders;
                }

/* 88 */        public boolean equals(Object o) {
/* 89 */            if (this == o) {
                        return true;
                    }
/* 90 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 92 */            HttpRequest that = (HttpRequest) o;
/* 94 */            if (this.authorization == null ? that.authorization != null : !this.authorization.equals(that.authorization)) {
                        return false;
                    }
/* 96 */            if (!Arrays.equals(this.parameters, that.parameters)) {
                        return false;
                    }
/* 97 */            if (this.requestHeaders == null ? that.requestHeaders != null : !this.requestHeaders.equals(that.requestHeaders)) {
                        return false;
                    }
/* 99 */            if (this.method == null ? that.method != null : !this.method.equals(that.method)) {
                        return false;
                    }
/* 101 */           if (this.url != null) {
                        if (this.url.equals(that.url)) {
                            return true;
                        }
                    } else if (that.url == null) {
                        return true;
                    }
                    return false;
                }

/* 108 */       public int hashCode() {
/* 109 */           int result = this.method != null ? this.method.hashCode() : 0;
/* 113 */           return (((((((result * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + (this.parameters != null ? Arrays.hashCode(this.parameters) : 0)) * 31) + (this.authorization != null ? this.authorization.hashCode() : 0)) * 31) + (this.requestHeaders != null ? this.requestHeaders.hashCode() : 0);
                }

/* 118 */       public String toString() {
                    return "HttpRequest{requestMethod=" + this.method + ", url='" + this.url + "', postParams=" + (this.parameters == null ? null : Arrays.asList(this.parameters)) + ", authentication=" + this.authorization + ", requestHeaders=" + this.requestHeaders + '}';
                }
            }
