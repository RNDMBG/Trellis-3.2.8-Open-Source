            package android.support.v4.net;

            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    class ConnectivityManagerCompatGingerbread {
/* 32 */        ConnectivityManagerCompatGingerbread() {
                }

/* 34 */        public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 35 */            NetworkInfo info = cm.getActiveNetworkInfo();
/* 36 */            if (info == null) {
                        return true;
                    }
/* 41 */            int type = info.getType();
                    switch (type) {
                        case 0:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        default:
                            return true;
                        case 1:
/* 51 */                    return false;
                    }
                }
            }
