            package twitter4j.internal.http;

            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class HttpResponseImpl extends HttpResponse {
                private HttpURLConnection con;

/* 31 */        HttpResponseImpl(HttpURLConnection con, HttpClientConfiguration conf) throws IOException {
/* 32 */            super(conf);
/* 33 */            this.con = con;
/* 34 */            this.statusCode = con.getResponseCode();
/* 35 */            InputStream errorStream = con.getErrorStream();
                    this.f2542is = errorStream;
                    if (errorStream == null) {
/* 36 */                this.f2542is = con.getInputStream();
                    }
/* 38 */            if (this.f2542is != null && "gzip".equals(con.getContentEncoding())) {
/* 40 */                this.f2542is = new StreamingGZIPInputStream(this.f2542is);
                    }
                }

/* 45 */        HttpResponseImpl(String content) {
/* 47 */            this.responseAsString = content;
                }

                @Override // twitter4j.internal.http.HttpResponse
/* 51 */        public String getResponseHeader(String name) {
/* 52 */            return this.con.getHeaderField(name);
                }

                @Override // twitter4j.internal.http.HttpResponse
/* 56 */        public Map<String, List<String>> getResponseHeaderFields() {
/* 57 */            return this.con.getHeaderFields();
                }

                @Override // twitter4j.internal.http.HttpResponse
/* 64 */        public void disconnect() {
/* 65 */            this.con.disconnect();
                }
            }
