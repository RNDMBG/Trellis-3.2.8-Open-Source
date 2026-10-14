            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class ConnectivityStatusReceiver extends BroadcastReceiver {
                private static int sLastTypeChanged = -1;

                @Override // android.content.BroadcastReceiver
/* 23 */        public void onReceive(Context context, Intent intent) {
                    try {
/* 24 */                ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 26 */                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
/* 27 */                if (activeNetwork != null && activeNetwork.isConnected() && sLastTypeChanged != activeNetwork.getType()) {
/* 29 */                    int networkType = activeNetwork.getType();
/* 31 */                    if (networkType == 0) {
/* 32 */                        AppController appController = AppController.getInstance(context);
/* 33 */                        appController.onMobileDataNetworkActivated();
                            }
                        }
/* 39 */                sLastTypeChanged = activeNetwork != null ? activeNetwork.getType() : -1;
/* 40 */                SystemUtil.onConnectivityChanged(context);
                    } catch (Throwable e) {
/* 43 */                CrashUtil.logException(e);
                    }
                }
            }
