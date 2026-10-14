            package org.scribe.model;

            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.UnknownHostException;
            import java.util.HashMap;
            import java.util.Map;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.utils.StreamUtils;
            
            
    public class Response {
                private static final String EMPTY = "";
                private String body;
                private int code;
                private Map<String, String> headers;
                private String message;
                private InputStream stream;

        Response(HttpURLConnection connection) throws IOException {
                    try {
                connection.connect();
                this.code = connection.getResponseCode();
                this.message = connection.getResponseMessage();
                this.headers = parseHeaders(connection);
                this.stream = isSuccessful() ? connection.getInputStream() : connection.getErrorStream();
                    } catch (UnknownHostException e) {
                throw new OAuthException("The IP address of a host could not be determined.", e);
                    }
                }

        private String parseBodyContents() {
            this.body = StreamUtils.getStreamContents(getStream());
            return this.body;
                }

        private Map<String, String> parseHeaders(HttpURLConnection conn) {
            Map<String, String> headers = new HashMap<>();
                    for (String key : conn.getHeaderFields().keySet()) {
                headers.put(key, conn.getHeaderFields().get(key).get(0));
                    }
            return headers;
                }

        public boolean isSuccessful() {
                    return getCode() >= 200 && getCode() < 400;
                }

        public String getBody() {
                    return this.body != null ? this.body : parseBodyContents();
                }

        public InputStream getStream() {
            return this.stream;
                }

        public int getCode() {
            return this.code;
                }

       public String getMessage() {
           return this.message;
                }

       public Map<String, String> getHeaders() {
           return this.headers;
                }

       public String getHeader(String name) {
           return this.headers.get(name);
                }
            }
