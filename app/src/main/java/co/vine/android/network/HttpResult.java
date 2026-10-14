            package co.vine.android.network;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class HttpResult {
                public long durationMs;
                public int errorCode;
                public Exception exception;
                public final String reasonPhrase;
                public final int statusCode;
                public String uploadKey;

/* 15 */        public HttpResult(int statusCode, String reasonPhrase) {
/* 17 */            this.statusCode = statusCode;
/* 18 */            this.reasonPhrase = reasonPhrase;
                }
            }
