            package android.support.v4.net;

            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    class ConnectivityManagerCompatHoneycombMR2 {
/* 34 */        ConnectivityManagerCompatHoneycombMR2() {
                }

/* 36 */        public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 37 */            NetworkInfo info = cm.getActiveNetworkInfo();
/* 38 */            if (info == null) {
                        return true;
                    }
/* 43 */            int type = info.getType();
                    switch (type) {
                        case 0:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        default:
                            return true;
                        case 1:
                        case 7:
                        case 9:
/* 55 */                    return false;
                    }
                }
            }
