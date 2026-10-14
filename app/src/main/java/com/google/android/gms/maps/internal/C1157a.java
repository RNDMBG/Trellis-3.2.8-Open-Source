            package com.google.android.gms.maps.internal;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1157a {
                /* JADX INFO: renamed from: a */
                public static Boolean m2234a(byte b) {
                    switch (b) {
                        case 0:
                            return Boolean.FALSE;
                        case 1:
                            return Boolean.TRUE;
                        default:
                            return null;
                    }
                }

                /* JADX INFO: renamed from: b */
                public static byte m2235b(Boolean bool) {
                    if (bool != null) {
                        return bool.booleanValue() ? (byte) 1 : (byte) 0;
                    }
                    return (byte) -1;
                }
            }
