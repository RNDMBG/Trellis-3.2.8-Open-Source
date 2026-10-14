            package co.vine.android.network;

            import co.vine.android.client.VineAPI;
            import co.vine.android.network.apache.HttpOperationFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class VineNetworkUtils {
/* 11 */        public static NetworkOperationFactory<VineAPI> getDefaultNetworkOperationFactory() {
/* 12 */            return getApacheFactory();
                }

/* 15 */        public static HttpOperationFactory getApacheFactory() {
/* 16 */            return HttpOperationFactory.getInstance();
                }
            }
