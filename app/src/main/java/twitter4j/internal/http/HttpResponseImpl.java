            package twitter4j.internal.http;

            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.util.List;
            import java.util.Map;
            
            
    public class HttpResponseImpl extends HttpResponse {
                private HttpURLConnection con;

        HttpResponseImpl(HttpURLConnection con, HttpClientConfiguration conf) throws IOException {
            super(conf);
            this.con = con;
            this.statusCode = con.getResponseCode();
            InputStream errorStream = con.getErrorStream();
                    this.f2542is = errorStream;
                    if (errorStream == null) {
                this.f2542is = con.getInputStream();
                    }
            if (this.f2542is != null && "gzip".equals(con.getContentEncoding())) {
                this.f2542is = new StreamingGZIPInputStream(this.f2542is);
                    }
                }

        HttpResponseImpl(String content) {
            this.responseAsString = content;
                }

                @Override 
        public String getResponseHeader(String name) {
            return this.con.getHeaderField(name);
                }

                @Override 
        public Map<String, List<String>> getResponseHeaderFields() {
            return this.con.getHeaderFields();
                }

                @Override 
        public void disconnect() {
            this.con.disconnect();
                }
            }
