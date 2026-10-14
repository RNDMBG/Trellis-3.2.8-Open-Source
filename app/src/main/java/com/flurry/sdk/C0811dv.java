            package com.flurry.sdk;

            import android.os.Looper;
            import android.telephony.TelephonyManager;
            import co.vine.android.service.VineService;
            import java.util.Arrays;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dv */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class C0811dv {

                /* JADX INFO: renamed from: a */
                private static final String f731a = C0811dv.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static byte[] f732b;

                /* JADX INFO: renamed from: a */
/* 23 */        public static synchronized byte[] m777a() {
                    byte[] bArr;
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
/* 25 */                    throw new IllegalStateException("Must be called from a background thread!");
                        }
/* 28 */                if (f732b != null) {
/* 29 */                    bArr = f732b;
                        } else if (C0801dl.m732a().m736b().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
/* 33 */                    bArr = null;
                        } else {
/* 36 */                    m778b();
/* 37 */                    bArr = f732b;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return bArr;
                }

                /* JADX INFO: renamed from: b */
/* 41 */        private static void m778b() {
                    String deviceId;
/* 42 */            TelephonyManager telephonyManager = (TelephonyManager) C0801dl.m732a().m736b().getSystemService(VineService.EXTRA_PHONE);
/* 45 */            if (telephonyManager != null && (deviceId = telephonyManager.getDeviceId()) != null && deviceId.trim().length() > 0) {
                        try {
/* 53 */                    byte[] bArrM968d = C0843fb.m968d(deviceId);
/* 56 */                    if (bArrM968d != null && bArrM968d.length == 20) {
/* 57 */                        f732b = bArrM968d;
                            } else {
/* 59 */                        C0827el.m885a(6, f731a, "sha1 is not 20 bytes long: " + Arrays.toString(bArrM968d));
                            }
                        } catch (Exception e) {
/* 63 */                    C0827el.m885a(6, f731a, "Exception in generateHashedImei()");
                        }
                    }
                }
            }
