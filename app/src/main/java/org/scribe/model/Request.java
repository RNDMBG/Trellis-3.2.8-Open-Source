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
            
            
    public class Request {
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

                
        static class C13081 extends RequestTuner {
            C13081() {
                    }

                    @Override 
            public void tune(Request request) {
                    }
                }

        public Request(Verb verb, String url) {
            this.verb = verb;
            this.url = url;
                }

        public Response send(RequestTuner tuner) {
                    try {
                createConnection();
                return doSend(tuner);
                    } catch (Exception e) {
                throw new OAuthConnectionException(e);
                    }
                }

        public Response send() {
            return send(NOOP);
                }

        private void createConnection() throws IOException {
            String completeUrl = getCompleteUrl();
            if (this.connection == null) {
                System.setProperty("http.keepAlive", this.connectionKeepAlive ? "true" : "false");
                this.connection = (HttpURLConnection) new URL(completeUrl).openConnection();
                this.connection.setInstanceFollowRedirects(this.followRedirects);
                    }
                }

        public String getCompleteUrl() {
            return this.querystringParams.appendTo(this.url);
                }

       Response doSend(RequestTuner tuner) throws IOException {
           this.connection.setRequestMethod(this.verb.name());
           if (this.connectTimeout != null) {
               this.connection.setConnectTimeout(this.connectTimeout.intValue());
                    }
           if (this.readTimeout != null) {
               this.connection.setReadTimeout(this.readTimeout.intValue());
                    }
           addHeaders(this.connection);
           if (this.verb.equals(Verb.PUT) || this.verb.equals(Verb.POST)) {
               addBody(this.connection, getByteBodyContents());
                    }
           tuner.tune(this);
           return new Response(this.connection);
                }

       void addHeaders(HttpURLConnection conn) {
                    for (String key : this.headers.keySet()) {
               conn.setRequestProperty(key, this.headers.get(key));
                    }
                }

       void addBody(HttpURLConnection conn, byte[] content) throws IOException {
           conn.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length));
           if (conn.getRequestProperty(CONTENT_TYPE) == null) {
               conn.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
                    }
           conn.setDoOutput(true);
           conn.getOutputStream().write(content);
                }

       public void addHeader(String key, String value) {
           this.headers.put(key, value);
                }

       public void addBodyParameter(String key, String value) {
           this.bodyParams.add(key, value);
                }

       public void addQuerystringParameter(String key, String value) {
           this.querystringParams.add(key, value);
                }

       public void addPayload(String payload) {
           this.payload = payload;
                }

       public void addPayload(byte[] payload) {
           this.bytePayload = (byte[]) payload.clone();
                }

       public ParameterList getQueryStringParams() {
                    try {
               ParameterList result = new ParameterList();
               String queryString = new URL(this.url).getQuery();
               result.addQuerystring(queryString);
               result.addAll(this.querystringParams);
               return result;
                    } catch (MalformedURLException mue) {
               throw new OAuthException("Malformed URL", mue);
                    }
                }

       public ParameterList getBodyParams() {
           return this.bodyParams;
                }

       public String getUrl() {
           return this.url;
                }

       public String getSanitizedUrl() {
           return this.url.replaceAll("\\?.*", "").replace("\\:\\d{4}", "");
                }

       public String getBodyContents() {
                    try {
               return new String(getByteBodyContents(), getCharset());
                    } catch (UnsupportedEncodingException uee) {
               throw new OAuthException("Unsupported Charset: " + this.charset, uee);
                    }
                }

       byte[] getByteBodyContents() {
           if (this.bytePayload != null) {
                        return this.bytePayload;
                    }
           String body = this.payload != null ? this.payload : this.bodyParams.asFormUrlEncodedString();
                    try {
               return body.getBytes(getCharset());
                    } catch (UnsupportedEncodingException uee) {
               throw new OAuthException("Unsupported Charset: " + getCharset(), uee);
                    }
                }

       public Verb getVerb() {
           return this.verb;
                }

       public Map<String, String> getHeaders() {
           return this.headers;
                }

       public String getCharset() {
                    return this.charset == null ? Charset.defaultCharset().name() : this.charset;
                }

       public void setConnectTimeout(int duration, TimeUnit unit) {
           this.connectTimeout = Long.valueOf(unit.toMillis(duration));
                }

       public void setReadTimeout(int duration, TimeUnit unit) {
           this.readTimeout = Long.valueOf(unit.toMillis(duration));
                }

       public void setCharset(String charsetName) {
           this.charset = charsetName;
                }

       public void setConnectionKeepAlive(boolean connectionKeepAlive) {
           this.connectionKeepAlive = connectionKeepAlive;
                }

       public void setFollowRedirects(boolean followRedirects) {
           this.followRedirects = followRedirects;
                }

       void setConnection(HttpURLConnection connection) {
           this.connection = connection;
                }

       public String toString() {
           return String.format("@Request(%s %s)", getVerb(), getUrl());
                }
            }
