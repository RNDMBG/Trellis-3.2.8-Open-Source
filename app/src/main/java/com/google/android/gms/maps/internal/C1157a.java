            package com.google.android.gms.maps.internal;
            
            
            
            public final class C1157a {
                
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

                
                public static byte m2235b(Boolean bool) {
                    if (bool != null) {
                        return bool.booleanValue() ? (byte) 1 : (byte) 0;
                    }
                    return (byte) -1;
                }
            }
