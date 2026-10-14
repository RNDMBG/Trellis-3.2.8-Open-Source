            package com.google.android.gms.internal;

            import android.util.Base64;
            
            
            
            class C0934a implements InterfaceC1112j {
                C0934a() {
                }

                @Override 
                
                public String mo1236a(byte[] bArr, boolean z) {
                    return Base64.encodeToString(bArr, z ? 11 : 2);
                }

                @Override 
                
                public byte[] mo1237a(String str, boolean z) throws IllegalArgumentException {
                    return Base64.decode(str, z ? 11 : 2);
                }
            }
