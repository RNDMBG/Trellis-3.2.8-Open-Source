            package com.google.android.gms.internal;

            import android.util.Base64;
            
            
            
            public final class C1048ef {
                
                public static String m1729b(byte[] bArr) {
                    if (bArr == null) {
                        return null;
                    }
                    return Base64.encodeToString(bArr, 0);
                }

                
                public static String m1730c(byte[] bArr) {
                    if (bArr == null) {
                        return null;
                    }
                    return Base64.encodeToString(bArr, 10);
                }
            }
