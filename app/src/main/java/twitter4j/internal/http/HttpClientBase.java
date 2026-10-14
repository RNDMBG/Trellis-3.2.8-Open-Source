            package twitter4j.internal.http;

            import android.util.Log;
            import com.edisonwang.android.slog.SLog;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public abstract class HttpClientBase implements HttpClient, Serializable {
                private static final String TAG = "t4jHttpClientBase";
                private static final long serialVersionUID = 6944924907755685265L;
                protected final HttpClientConfiguration CONF;

/* 15 */        public HttpClientBase(HttpClientConfiguration conf) {
/* 17 */            this.CONF = conf;
                }

                @Override // twitter4j.internal.http.HttpClient
/* 21 */        public void shutdown() {
                }

/* 24 */        protected boolean isProxyConfigured() {
                    return (this.CONF.getHttpProxyHost() == null || this.CONF.getHttpProxyHost().equals("")) ? false : true;
                }

/* 28 */        public void write(DataOutputStream out, String outStr) throws IOException {
/* 29 */            out.writeBytes(outStr);
/* 30 */            if (SLog.sLogsOn) {
/* 31 */                Log.v(TAG, outStr);
                    }
                }

/* 36 */        public boolean equals(Object o) {
/* 37 */            if (this == o) {
                        return true;
                    }
/* 38 */            if (!(o instanceof HttpClientBase)) {
                        return false;
                    }
/* 40 */            HttpClientBase that = (HttpClientBase) o;
                    return this.CONF.equals(that.CONF);
                }

/* 48 */        public int hashCode() {
/* 49 */            return this.CONF.hashCode();
                }

/* 53 */        public String toString() {
/* 54 */            return "HttpClientBase{CONF=" + this.CONF + '}';
                }
            }
