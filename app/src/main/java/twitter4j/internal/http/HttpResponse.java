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
            
            
    public abstract class HttpResponse {
                private static final String TAG = "T4JHttpResposne";
                protected final HttpClientConfiguration CONF;

                
                protected InputStream f2542is;
                protected String responseAsString;
                protected int statusCode;
                private boolean streamConsumed;

                public abstract void disconnect() throws IOException;

                public abstract String getResponseHeader(String str);

                public abstract Map<String, List<String>> getResponseHeaderFields();

        HttpResponse() {
            this.responseAsString = null;
            this.streamConsumed = false;
            this.CONF = ConfigurationContext.getInstance();
                }

        public HttpResponse(HttpClientConfiguration conf) {
            this.responseAsString = null;
            this.streamConsumed = false;
            this.CONF = conf;
                }

        public int getStatusCode() {
            return this.statusCode;
                }

        public InputStream asStream() {
            if (this.streamConsumed) {
                throw new IllegalStateException("Stream has already been consumed.");
                    }
            return this.f2542is;
                }

       public String asString() throws TwitterException {
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

       private void disconnectForcibly() {
                    try {
               disconnect();
                    } catch (Exception e) {
                    }
                }

       public String toString() {
           return "HttpResponse{statusCode=" + this.statusCode + ", responseAsString='" + this.responseAsString + "', is=" + this.f2542is + ", streamConsumed=" + this.streamConsumed + '}';
                }
            }
