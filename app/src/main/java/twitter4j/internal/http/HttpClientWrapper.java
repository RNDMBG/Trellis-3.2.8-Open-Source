            package twitter4j.internal.http;

            import java.io.Serializable;
            import java.util.HashMap;
            import java.util.Map;
            import twitter4j.TwitterException;
            import twitter4j.auth.Authorization;
            import twitter4j.conf.ConfigurationContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public final class HttpClientWrapper implements Serializable {
                private static final long serialVersionUID = -6511977105603119379L;
                private HttpClient http;
                private HttpResponseListener httpResponseListener;
                private final Map<String, String> requestHeaders;
                private final HttpClientWrapperConfiguration wrapperConf;

/* 40 */        public HttpClientWrapper(HttpClientWrapperConfiguration wrapperConf) {
/* 42 */            this.wrapperConf = wrapperConf;
/* 43 */            this.requestHeaders = wrapperConf.getRequestHeaders();
/* 44 */            this.http = HttpClientFactory.getInstance(wrapperConf);
                }

/* 47 */        public HttpClientWrapper() {
/* 49 */            this.wrapperConf = ConfigurationContext.getInstance();
/* 50 */            this.requestHeaders = this.wrapperConf.getRequestHeaders();
/* 51 */            this.http = HttpClientFactory.getInstance(this.wrapperConf);
                }

/* 54 */        public void shutdown() {
/* 55 */            this.http.shutdown();
                }

/* 60 */        private HttpResponse request(HttpRequest req) throws TwitterException {
                    try {
/* 61 */                HttpResponse res = this.http.request(req);
/* 63 */                if (this.httpResponseListener != null) {
/* 64 */                    this.httpResponseListener.httpResponseReceived(new HttpResponseEvent(req, res, null));
                        }
/* 72 */                return res;
                    } catch (TwitterException te) {
/* 67 */                if (this.httpResponseListener != null) {
/* 68 */                    this.httpResponseListener.httpResponseReceived(new HttpResponseEvent(req, null, te));
                        }
/* 70 */                throw te;
                    }
                }

/* 75 */        public void setHttpResponseListener(HttpResponseListener listener) {
/* 76 */            this.httpResponseListener = listener;
                }

/* 80 */        public HttpResponse get(String url, HttpParameter[] parameters, Authorization authorization) throws TwitterException {
/* 81 */            return request(new HttpRequest(RequestMethod.GET, url, parameters, authorization, this.requestHeaders));
                }

/* 84 */        public HttpResponse get(String url, HttpParameter[] parameters) throws TwitterException {
/* 85 */            return request(new HttpRequest(RequestMethod.GET, url, parameters, null, this.requestHeaders));
                }

/* 88 */        public HttpResponse get(String url, Authorization authorization) throws TwitterException {
/* 89 */            return request(new HttpRequest(RequestMethod.GET, url, null, authorization, this.requestHeaders));
                }

/* 92 */        public HttpResponse get(String url) throws TwitterException {
/* 93 */            return request(new HttpRequest(RequestMethod.GET, url, null, null, this.requestHeaders));
                }

/* 97 */        public HttpResponse post(String url, HttpParameter[] parameters, Authorization authorization) throws TwitterException {
/* 98 */            return request(new HttpRequest(RequestMethod.POST, url, parameters, authorization, this.requestHeaders));
                }

/* 101 */       public HttpResponse post(String url, HttpParameter[] parameters) throws TwitterException {
/* 102 */           return request(new HttpRequest(RequestMethod.POST, url, parameters, null, this.requestHeaders));
                }

/* 105 */       public HttpResponse post(String url, HttpParameter[] parameters, Map<String, String> requestHeaders) throws TwitterException {
/* 106 */           Map<String, String> headers = new HashMap<>(this.requestHeaders);
/* 107 */           if (requestHeaders != null) {
/* 108 */               headers.putAll(requestHeaders);
                    }
/* 110 */           return request(new HttpRequest(RequestMethod.POST, url, parameters, null, headers));
                }

/* 113 */       public HttpResponse post(String url, Authorization authorization) throws TwitterException {
/* 114 */           return request(new HttpRequest(RequestMethod.POST, url, null, authorization, this.requestHeaders));
                }

/* 117 */       public HttpResponse post(String url) throws TwitterException {
/* 118 */           return request(new HttpRequest(RequestMethod.POST, url, null, null, this.requestHeaders));
                }

/* 122 */       public HttpResponse delete(String url, HttpParameter[] parameters, Authorization authorization) throws TwitterException {
/* 123 */           return request(new HttpRequest(RequestMethod.DELETE, url, parameters, authorization, this.requestHeaders));
                }

/* 126 */       public HttpResponse delete(String url, HttpParameter[] parameters) throws TwitterException {
/* 127 */           return request(new HttpRequest(RequestMethod.DELETE, url, parameters, null, this.requestHeaders));
                }

/* 131 */       public HttpResponse delete(String url, Authorization authorization) throws TwitterException {
/* 132 */           return request(new HttpRequest(RequestMethod.DELETE, url, null, authorization, this.requestHeaders));
                }

/* 135 */       public HttpResponse delete(String url) throws TwitterException {
/* 136 */           return request(new HttpRequest(RequestMethod.DELETE, url, null, null, this.requestHeaders));
                }

/* 140 */       public HttpResponse head(String url, HttpParameter[] parameters, Authorization authorization) throws TwitterException {
/* 141 */           return request(new HttpRequest(RequestMethod.HEAD, url, parameters, authorization, this.requestHeaders));
                }

/* 144 */       public HttpResponse head(String url, HttpParameter[] parameters) throws TwitterException {
/* 145 */           return request(new HttpRequest(RequestMethod.HEAD, url, parameters, null, this.requestHeaders));
                }

/* 149 */       public HttpResponse head(String url, Authorization authorization) throws TwitterException {
/* 150 */           return request(new HttpRequest(RequestMethod.HEAD, url, null, authorization, this.requestHeaders));
                }

/* 153 */       public HttpResponse head(String url) throws TwitterException {
/* 154 */           return request(new HttpRequest(RequestMethod.HEAD, url, null, null, this.requestHeaders));
                }

/* 158 */       public HttpResponse put(String url, HttpParameter[] parameters, Authorization authorization) throws TwitterException {
/* 159 */           return request(new HttpRequest(RequestMethod.PUT, url, parameters, authorization, this.requestHeaders));
                }

/* 162 */       public HttpResponse put(String url, HttpParameter[] parameters) throws TwitterException {
/* 163 */           return request(new HttpRequest(RequestMethod.PUT, url, parameters, null, this.requestHeaders));
                }

/* 166 */       public HttpResponse put(String url, Authorization authorization) throws TwitterException {
/* 167 */           return request(new HttpRequest(RequestMethod.PUT, url, null, authorization, this.requestHeaders));
                }

/* 170 */       public HttpResponse put(String url) throws TwitterException {
/* 171 */           return request(new HttpRequest(RequestMethod.PUT, url, null, null, this.requestHeaders));
                }

/* 175 */       public boolean equals(Object o) {
/* 176 */           if (this == o) {
                        return true;
                    }
/* 177 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 179 */           HttpClientWrapper that = (HttpClientWrapper) o;
                    return this.http.equals(that.http) && this.requestHeaders.equals(that.requestHeaders) && this.wrapperConf.equals(that.wrapperConf);
                }

/* 189 */       public int hashCode() {
/* 190 */           int result = this.wrapperConf.hashCode();
/* 192 */           return (((result * 31) + this.http.hashCode()) * 31) + this.requestHeaders.hashCode();
                }

/* 197 */       public String toString() {
/* 198 */           return "HttpClientWrapper{wrapperConf=" + this.wrapperConf + ", http=" + this.http + ", requestHeaders=" + this.requestHeaders + ", httpResponseListener=" + this.httpResponseListener + '}';
                }
            }
