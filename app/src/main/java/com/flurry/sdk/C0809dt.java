            package com.flurry.sdk;

            import android.os.Looper;
            import com.google.android.gms.ads.identifier.AdvertisingIdClient;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesRepairableException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import java.io.IOException;
            
            
            
    public class C0809dt {

                
                private static final String f729a = C0809dt.class.getSimpleName();

                
        public static synchronized AdvertisingIdClient.Info m770a() {
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    throw new IllegalStateException("Must be called from a background thread!");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return !m771b() ? null : m772c();
                }

                
        public static boolean m771b() {
                    try {
                int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(C0801dl.m732a().m736b());
                if (iIsGooglePlayServicesAvailable == 0) {
                    return true;
                        }
                C0827el.m900d(f729a, "Google Play Services not available - connection result: " + iIsGooglePlayServicesAvailable);
                return false;
                    } catch (Exception e) {
                C0827el.m900d(f729a, "Google Play Services not available - " + e);
                return false;
                    }
                }

                
        private static AdvertisingIdClient.Info m772c() {
                    try {
                return AdvertisingIdClient.getAdvertisingIdInfo(C0801dl.m732a().m736b());
                    } catch (GooglePlayServicesNotAvailableException e) {
                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e);
                return null;
                    } catch (GooglePlayServicesRepairableException e2) {
                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e2);
                return null;
                    } catch (IOException e3) {
                C0827el.m885a(6, f729a, "Exception in readAdvertisingInfo():" + e3);
                return null;
                    }
                }
            }
