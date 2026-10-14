            package org.scribe.model;

            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.net.HttpURLConnection;
            import java.net.MalformedURLException;
            import java.net.URL;
            import java.nio.charset.Charset;
            import java.util.HashMap;
            import java.util.Map;
            import java.util.concurrent.TimeUnit;
            import org.scribe.exceptions.OAuthConnectionException;
            import org.scribe.exceptions.OAuthException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class Request {
                private static final String CONTENT_LENGTH = "Content-Length";
                private static final String CONTENT_TYPE = "Content-Type";
                public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
                private static RequestTuner NOOP = new C13081();
                private String charset;
                private HttpURLConnection connection;
                private String url;
                private Verb verb;
                private String payload = null;
                private byte[] bytePayload = null;
                private boolean connectionKeepAlive = false;
                private boolean followRedirects = true;
                private Long connectTimeout = null;
                private Long readTimeout = null;
                private ParameterList querystringParams = new ParameterList();
                private ParameterList bodyParams = new ParameterList();
                private Map<String, String> headers = new HashMap();

                /* JADX INFO: renamed from: org.scribe.model.Request$1 */
/* 18 */        static class C13081 extends RequestTuner {
/* 19 */            C13081() {
                    }

                    @Override // org.scribe.model.RequestTuner
/* 20 */            public void tune(Request request) {
                    }
                }

/* 45 */        public Request(Verb verb, String url) {
/* 47 */            this.verb = verb;
/* 48 */            this.url = url;
                }

/* 64 */        public Response send(RequestTuner tuner) {
                    try {
/* 65 */                createConnection();
/* 66 */                return doSend(tuner);
                    } catch (Exception e) {
/* 70 */                throw new OAuthConnectionException(e);
                    }
                }

/* 75 */        public Response send() {
/* 76 */            return send(NOOP);
                }

/* 80 */        private void createConnection() throws IOException {
/* 81 */            String completeUrl = getCompleteUrl();
/* 82 */            if (this.connection == null) {
/* 84 */                System.setProperty("http.keepAlive", this.connectionKeepAlive ? "true" : "false");
/* 85 */                this.connection = (HttpURLConnection) new URL(completeUrl).openConnection();
/* 86 */                this.connection.setInstanceFollowRedirects(this.followRedirects);
                    }
                }

/* 96 */        public String getCompleteUrl() {
/* 97 */            return this.querystringParams.appendTo(this.url);
                }

/* 101 */       Response doSend(RequestTuner tuner) throws IOException {
/* 102 */           this.connection.setRequestMethod(this.verb.name());
/* 103 */           if (this.connectTimeout != null) {
/* 105 */               this.connection.setConnectTimeout(this.connectTimeout.intValue());
                    }
/* 107 */           if (this.readTimeout != null) {
/* 109 */               this.connection.setReadTimeout(this.readTimeout.intValue());
                    }
/* 111 */           addHeaders(this.connection);
/* 112 */           if (this.verb.equals(Verb.PUT) || this.verb.equals(Verb.POST)) {
/* 114 */               addBody(this.connection, getByteBodyContents());
                    }
/* 116 */           tuner.tune(this);
/* 117 */           return new Response(this.connection);
                }

/* 121 */       void addHeaders(HttpURLConnection conn) {
                    for (String key : this.headers.keySet()) {
/* 123 */               conn.setRequestProperty(key, this.headers.get(key));
                    }
                }

/* 127 */       void addBody(HttpURLConnection conn, byte[] content) throws IOException {
/* 128 */           conn.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length));
/* 131 */           if (conn.getRequestProperty(CONTENT_TYPE) == null) {
/* 133 */               conn.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
                    }
/* 135 */           conn.setDoOutput(true);
/* 136 */           conn.getOutputStream().write(content);
                }

/* 146 */       public void addHeader(String key, String value) {
/* 147 */           this.headers.put(key, value);
                }

/* 157 */       public void addBodyParameter(String key, String value) {
/* 158 */           this.bodyParams.add(key, value);
                }

/* 168 */       public void addQuerystringParameter(String key, String value) {
/* 169 */           this.querystringParams.add(key, value);
                }

/* 183 */       public void addPayload(String payload) {
/* 184 */           this.payload = payload;
                }

/* 193 */       public void addPayload(byte[] payload) {
/* 194 */           this.bytePayload = (byte[]) payload.clone();
                }

/* 206 */       public ParameterList getQueryStringParams() {
                    try {
/* 207 */               ParameterList result = new ParameterList();
/* 208 */               String queryString = new URL(this.url).getQuery();
/* 209 */               result.addQuerystring(queryString);
/* 210 */               result.addAll(this.querystringParams);
/* 211 */               return result;
                    } catch (MalformedURLException mue) {
/* 215 */               throw new OAuthException("Malformed URL", mue);
                    }
                }

/* 225 */       public ParameterList getBodyParams() {
/* 226 */           return this.bodyParams;
                }

/* 235 */       public String getUrl() {
/* 236 */           return this.url;
                }

/* 245 */       public String getSanitizedUrl() {
/* 246 */           return this.url.replaceAll("\\?.*", "").replace("\\:\\d{4}", "");
                }

/* 258 */       public String getBodyContents() {
                    try {
/* 259 */               return new String(getByteBodyContents(), getCharset());
                    } catch (UnsupportedEncodingException uee) {
/* 263 */               throw new OAuthException("Unsupported Charset: " + this.charset, uee);
                    }
                }

/* 268 */       byte[] getByteBodyContents() {
/* 269 */           if (this.bytePayload != null) {
                        return this.bytePayload;
                    }
/* 270 */           String body = this.payload != null ? this.payload : this.bodyParams.asFormUrlEncodedString();
                    try {
/* 273 */               return body.getBytes(getCharset());
                    } catch (UnsupportedEncodingException uee) {
/* 277 */               throw new OAuthException("Unsupported Charset: " + getCharset(), uee);
                    }
                }

/* 287 */       public Verb getVerb() {
/* 288 */           return this.verb;
                }

/* 297 */       public Map<String, String> getHeaders() {
/* 298 */           return this.headers;
                }

/* 307 */       public String getCharset() {
                    return this.charset == null ? Charset.defaultCharset().name() : this.charset;
                }

/* 319 */       public void setConnectTimeout(int duration, TimeUnit unit) {
/* 320 */           this.connectTimeout = Long.valueOf(unit.toMillis(duration));
                }

/* 331 */       public void setReadTimeout(int duration, TimeUnit unit) {
/* 332 */           this.readTimeout = Long.valueOf(unit.toMillis(duration));
                }

/* 341 */       public void setCharset(String charsetName) {
/* 342 */           this.charset = charsetName;
                }

/* 352 */       public void setConnectionKeepAlive(boolean connectionKeepAlive) {
/* 353 */           this.connectionKeepAlive = connectionKeepAlive;
                }

/* 365 */       public void setFollowRedirects(boolean followRedirects) {
/* 366 */           this.followRedirects = followRedirects;
                }

/* 373 */       void setConnection(HttpURLConnection connection) {
/* 374 */           this.connection = connection;
                }

/* 379 */       public String toString() {
/* 380 */           return String.format("@Request(%s %s)", getVerb(), getUrl());
                }
            }
