            package com.google.android.gms.internal;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ev */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1064ev {
                /* JADX INFO: renamed from: R */
                public static String m1895R(int i) {
                    switch (i) {
                        case 0:
                            return "DAILY";
                        case 1:
                            return "WEEKLY";
                        case 2:
                            return "ALL_TIME";
                        default:
                            throw new IllegalArgumentException("Unknown time span " + i);
                    }
                }
            }
