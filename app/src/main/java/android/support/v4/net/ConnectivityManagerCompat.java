            package android.support.v4.net;

            import android.content.Intent;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ConnectivityManagerCompat {
                private static final ConnectivityManagerCompatImpl IMPL;

                interface ConnectivityManagerCompatImpl {
                    boolean isActiveNetworkMetered(ConnectivityManager connectivityManager);
                }

/* 35 */        static class BaseConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
/* 36 */            BaseConnectivityManagerCompatImpl() {
                    }

                    @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
/* 39 */            public boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 40 */                NetworkInfo info = cm.getActiveNetworkInfo();
/* 41 */                if (info == null) {
                            return true;
                        }
/* 46 */                int type = info.getType();
                        switch (type) {
                            case 0:
                            default:
                                return true;
                            case 1:
/* 51 */                        return false;
                        }
                    }
                }

/* 57 */        static class GingerbreadConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
/* 58 */            GingerbreadConnectivityManagerCompatImpl() {
                    }

                    @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
/* 61 */            public boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 62 */                return ConnectivityManagerCompatGingerbread.isActiveNetworkMetered(cm);
                    }
                }

/* 64 */        static class HoneycombMR2ConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
/* 65 */            HoneycombMR2ConnectivityManagerCompatImpl() {
                    }

                    @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
/* 69 */            public boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 70 */                return ConnectivityManagerCompatHoneycombMR2.isActiveNetworkMetered(cm);
                    }
                }

/* 72 */        static class JellyBeanConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
/* 73 */            JellyBeanConnectivityManagerCompatImpl() {
                    }

                    @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
/* 76 */            public boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 77 */                return ConnectivityManagerCompatJellyBean.isActiveNetworkMetered(cm);
                    }
                }

                static {
/* 84 */            if (Build.VERSION.SDK_INT >= 16) {
/* 85 */                IMPL = new JellyBeanConnectivityManagerCompatImpl();
/* 93 */                return;
                    }
/* 86 */            if (Build.VERSION.SDK_INT >= 13) {
/* 87 */                IMPL = new HoneycombMR2ConnectivityManagerCompatImpl();
                    } else if (Build.VERSION.SDK_INT >= 8) {
/* 89 */                IMPL = new GingerbreadConnectivityManagerCompatImpl();
                    } else {
/* 91 */                IMPL = new BaseConnectivityManagerCompatImpl();
                    }
                }

/* 102 */       public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
/* 103 */           return IMPL.isActiveNetworkMetered(cm);
                }

/* 113 */       public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager cm, Intent intent) {
/* 114 */           NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
/* 115 */           return cm.getNetworkInfo(info.getType());
                }
            }
