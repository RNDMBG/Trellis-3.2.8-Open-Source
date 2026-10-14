            package org.scribe.model;

            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.UnknownHostException;
            import java.util.HashMap;
            import java.util.Map;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.utils.StreamUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class Response {
                private static final String EMPTY = "";
                private String body;
                private int code;
                private Map<String, String> headers;
                private String message;
                private InputStream stream;

/* 25 */        Response(HttpURLConnection connection) throws IOException {
                    try {
/* 29 */                connection.connect();
/* 30 */                this.code = connection.getResponseCode();
/* 31 */                this.message = connection.getResponseMessage();
/* 32 */                this.headers = parseHeaders(connection);
/* 33 */                this.stream = isSuccessful() ? connection.getInputStream() : connection.getErrorStream();
                    } catch (UnknownHostException e) {
/* 37 */                throw new OAuthException("The IP address of a host could not be determined.", e);
                    }
                }

/* 42 */        private String parseBodyContents() {
/* 43 */            this.body = StreamUtils.getStreamContents(getStream());
/* 44 */            return this.body;
                }

/* 48 */        private Map<String, String> parseHeaders(HttpURLConnection conn) {
/* 49 */            Map<String, String> headers = new HashMap<>();
                    for (String key : conn.getHeaderFields().keySet()) {
/* 52 */                headers.put(key, conn.getHeaderFields().get(key).get(0));
                    }
/* 54 */            return headers;
                }

/* 58 */        public boolean isSuccessful() {
                    return getCode() >= 200 && getCode() < 400;
                }

/* 68 */        public String getBody() {
                    return this.body != null ? this.body : parseBodyContents();
                }

/* 79 */        public InputStream getStream() {
/* 80 */            return this.stream;
                }

/* 89 */        public int getCode() {
/* 90 */            return this.code;
                }

/* 100 */       public String getMessage() {
/* 101 */           return this.message;
                }

/* 110 */       public Map<String, String> getHeaders() {
/* 111 */           return this.headers;
                }

/* 122 */       public String getHeader(String name) {
/* 123 */           return this.headers.get(name);
                }
            }
