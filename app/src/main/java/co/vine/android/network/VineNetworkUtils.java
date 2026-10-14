            package co.vine.android.network;

            import co.vine.android.client.VineAPI;
            import co.vine.android.network.apache.HttpOperationFactory;
            
            
    public class VineNetworkUtils {
        public static NetworkOperationFactory<VineAPI> getDefaultNetworkOperationFactory() {
            return getApacheFactory();
                }

        public static HttpOperationFactory getApacheFactory() {
            return HttpOperationFactory.getInstance();
                }
            }
