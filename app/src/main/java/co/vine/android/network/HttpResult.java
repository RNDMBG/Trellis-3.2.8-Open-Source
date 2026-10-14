            package co.vine.android.network;
            
            
    public class HttpResult {
                public long durationMs;
                public int errorCode;
                public Exception exception;
                public final String reasonPhrase;
                public final int statusCode;
                public String uploadKey;

        public HttpResult(int statusCode, String reasonPhrase) {
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
                }
            }
