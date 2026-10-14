            package co.vine.android.network;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface NetworkOperationReader {
                void onHandleError(HttpResult httpResult);

                void readInput(int i, long j, InputStream inputStream) throws IOException;
            }
