            package com.google.android.gms.internal;

            import android.util.Base64;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ef */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1048ef {
                /* JADX INFO: renamed from: b */
                public static String m1729b(byte[] bArr) {
                    if (bArr == null) {
                        return null;
                    }
                    return Base64.encodeToString(bArr, 0);
                }

                /* JADX INFO: renamed from: c */
                public static String m1730c(byte[] bArr) {
                    if (bArr == null) {
                        return null;
                    }
                    return Base64.encodeToString(bArr, 10);
                }
            }
