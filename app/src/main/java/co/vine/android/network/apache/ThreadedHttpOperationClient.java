            package co.vine.android.network.apache;

            import android.content.Context;
            import org.apache.http.client.HttpClient;
            import org.apache.http.conn.ClientConnectionManager;
            import org.apache.http.conn.scheme.SchemeRegistry;
            import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
            import org.apache.http.params.HttpParams;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ThreadedHttpOperationClient extends HttpOperationClient {
                private static ThreadedHttpOperationClient sInstance = null;
                private ClientConnectionManager mConnectionManager;
                private final Context mContext;
                private HttpClient mHttpClient;

/* 28 */        private ThreadedHttpOperationClient(Context context) {
/* 30 */            this.mContext = context.getApplicationContext();
/* 31 */            initialize();
                }

/* 34 */        public static synchronized ThreadedHttpOperationClient getInstance(Context context) {
                    try {
                        if (sInstance == null) {
/* 36 */                    sInstance = new ThreadedHttpOperationClient(context.getApplicationContext());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 38 */            return sInstance;
                }

                @Override // co.vine.android.network.apache.HttpOperationClient
/* 42 */        public HttpClient getHttpClient() {
/* 43 */            return this.mHttpClient;
                }

                @Override // co.vine.android.network.apache.HttpOperationClient
/* 47 */        public void shutdown() {
/* 48 */            this.mConnectionManager.shutdown();
                }

/* 51 */        private void initialize() {
/* 52 */            HttpParams params = initializeHttpParams();
/* 53 */            SchemeRegistry schemeRegistry = initializeSchemeRegistry(this.mContext);
/* 56 */            this.mConnectionManager = initializeConnectionManager(params, schemeRegistry);
/* 57 */            this.mHttpClient = initializeHttpClient(this.mContext, this.mConnectionManager, params);
                }

/* 61 */        private ClientConnectionManager initializeConnectionManager(HttpParams params, SchemeRegistry schemeRegistry) {
/* 62 */            return new ThreadSafeClientConnManager(params, schemeRegistry);
                }

                @Override // co.vine.android.network.apache.HttpOperationClient
/* 66 */        public void restart() {
/* 67 */            this.mConnectionManager.shutdown();
/* 68 */            initialize();
                }
            }
