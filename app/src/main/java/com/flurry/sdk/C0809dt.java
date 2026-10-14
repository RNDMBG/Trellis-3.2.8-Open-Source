            package com.flurry.sdk;

            import android.os.Looper;
            import com.google.android.gms.ads.identifier.AdvertisingIdClient;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesRepairableException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dt */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class C0809dt {

                /* JADX INFO: renamed from: a */
                private static final String f729a = C0809dt.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 22 */        public static synchronized AdvertisingIdClient.Info m770a() {
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
/* 24 */                    throw new IllegalStateException("Must be called from a background thread!");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 28 */            return !m771b() ? null : m772c();
                }

                /* JADX INFO: renamed from: b */
/* 35 */        public static boolean m771b() {
                    try {
/* 38 */                int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(C0801dl.m732a().m736b());
/* 39 */                if (iIsGooglePlayServicesAvailable == 0) {
/* 40 */                    return true;
                        }
/* 42 */                C0827el.m900d(f729a, "Google Play Services not available - connection result: " + iIsGooglePlayServicesAvailable);
/* 36 */                return false;
                    } catch (Exception e) {
/* 46 */                C0827el.m900d(f729a, "Google Play Services not available - " + e);
/* 36 */                return false;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 53 */        private static AdvertisingIdClient.Info m772c() {
                    try {
/* 56 */                return AdvertisingIdClient.getAdvertisingIdInfo(C0801dl.m732a().m736b());
                    } catch (GooglePlayServicesNotAvailableException e) {
/* 63 */                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e);
/* 54 */                return null;
                    } catch (GooglePlayServicesRepairableException e2) {
/* 66 */                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e2);
/* 54 */                return null;
                    } catch (IOException e3) {
/* 60 */                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e3);
/* 54 */                return null;
                    }
                }
            }
