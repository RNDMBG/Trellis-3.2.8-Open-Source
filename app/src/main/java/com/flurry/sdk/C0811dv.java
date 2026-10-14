            package com.flurry.sdk;

            import android.os.Looper;
            import android.telephony.TelephonyManager;
            import co.vine.android.service.VineService;
            import java.util.Arrays;
            
            
            
    public class C0811dv {

                
                private static final String f731a = C0811dv.class.getSimpleName();

                
                private static byte[] f732b;

                
        public static synchronized byte[] m777a() {
                    byte[] bArr;
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    throw new IllegalStateException("Must be called from a background thread!");
                        }
                if (f732b != null) {
                    bArr = f732b;
                        } else if (C0801dl.m732a().m736b().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                    bArr = null;
                        } else {
                    m778b();
                    bArr = f732b;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return bArr;
                }

                
        private static void m778b() {
                    String deviceId;
            TelephonyManager telephonyManager = (TelephonyManager) C0801dl.m732a().m736b().getSystemService(VineService.EXTRA_PHONE);
            if (telephonyManager != null && (deviceId = telephonyManager.getDeviceId()) != null && deviceId.trim().length() > 0) {
                        try {
                    byte[] bArrM968d = C0843fb.m968d(deviceId);
                    if (bArrM968d != null && bArrM968d.length == 20) {
                        f732b = bArrM968d;
                            } else {
                        C0827el.m885a(6, f731a, "sha1 is not 20 bytes long: " + Arrays.toString(bArrM968d));
                            }
                        } catch (Exception e) {
                    C0827el.m885a(6, f731a, "Exception in generateHashedImei()");
                        }
                    }
                }
            }
