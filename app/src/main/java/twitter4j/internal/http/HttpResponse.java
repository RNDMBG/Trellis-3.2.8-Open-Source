            package twitter4j.internal.http;

            import android.util.Log;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedReader;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.util.List;
            import java.util.Map;
            import org.apache.commons.io.IOUtils;
            import twitter4j.TwitterException;
            import twitter4j.conf.ConfigurationContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public abstract class HttpResponse {
                private static final String TAG = "T4JHttpResposne";
                protected final HttpClientConfiguration CONF;

                /* JADX INFO: renamed from: is */
                protected InputStream f2542is;
                protected String responseAsString;
                protected int statusCode;
                private boolean streamConsumed;

                public abstract void disconnect() throws IOException;

                public abstract String getResponseHeader(String str);

                public abstract Map<String, List<String>> getResponseHeaderFields();

/* 43 */        HttpResponse() {
/* 53 */            this.responseAsString = null;
/* 55 */            this.streamConsumed = false;
/* 45 */            this.CONF = ConfigurationContext.getInstance();
                }

/* 47 */        public HttpResponse(HttpClientConfiguration conf) {
/* 53 */            this.responseAsString = null;
/* 55 */            this.streamConsumed = false;
/* 49 */            this.CONF = conf;
                }

/* 57 */        public int getStatusCode() {
/* 58 */            return this.statusCode;
                }

/* 76 */        public InputStream asStream() {
/* 77 */            if (this.streamConsumed) {
/* 78 */                throw new IllegalStateException("Stream has already been consumed.");
                    }
/* 80 */            return this.f2542is;
                }

/* 90 */       public String asString() throws TwitterException {
                    if (this.responseAsString != null) {
                        return this.responseAsString;
                    }
                    InputStream stream = asStream();
                    if (stream == null) {
                        disconnectForcibly();
                        return null;
                    }
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(stream, IoUtils.UTF_8));
                        StringBuilder buf = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            buf.append(line).append(IOUtils.LINE_SEPARATOR_UNIX);
                        }
                        this.responseAsString = buf.toString();
                        this.streamConsumed = true;
                        if (SLog.sLogsOn) {
                            Log.d(TAG, this.responseAsString);
                        }
                        return this.responseAsString;
                    } catch (IOException e) {
                        throw new TwitterException(e.getMessage(), e);
                    } finally {
                        IoUtils.closeQuietly(br);
                        IoUtils.closeQuietly(stream);
                        disconnectForcibly();
                    }
                }

/* 133 */       private void disconnectForcibly() {
                    try {
/* 134 */               disconnect();
                    } catch (Exception e) {
                    }
                }

/* 142 */       public String toString() {
/* 143 */           return "HttpResponse{statusCode=" + this.statusCode + ", responseAsString='" + this.responseAsString + "', is=" + this.f2542is + ", streamConsumed=" + this.streamConsumed + '}';
                }
            }
