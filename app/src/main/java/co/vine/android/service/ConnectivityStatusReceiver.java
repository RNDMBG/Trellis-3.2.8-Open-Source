            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            
            
    public class ConnectivityStatusReceiver extends BroadcastReceiver {
                private static int sLastTypeChanged = -1;

                @Override 
        public void onReceive(Context context, Intent intent) {
                    try {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected() && sLastTypeChanged != activeNetwork.getType()) {
                    int networkType = activeNetwork.getType();
                    if (networkType == 0) {
                        AppController appController = AppController.getInstance(context);
                        appController.onMobileDataNetworkActivated();
                            }
                        }
                sLastTypeChanged = activeNetwork != null ? activeNetwork.getType() : -1;
                SystemUtil.onConnectivityChanged(context);
                    } catch (Throwable e) {
                CrashUtil.logException(e);
                    }
                }
            }
