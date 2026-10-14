            package com.google.android.gms.internal;
            
            
            
            public final class C1064ev {
                
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
