            package com.google.android.gms.internal;

            import java.io.IOException;
            
            
            
            class C1115m implements InterfaceC1113k {

                
                private C1108gl f2134dD;

                
                private byte[] f2135dE;

                
                private final int f2136dF;

                public C1115m(int i) {
                    this.f2136dF = i;
                    reset();
                }

                @Override 
                
                public void mo2129b(int i, long j) throws IOException {
                    this.f2134dD.m2114b(i, j);
                }

                @Override 
                
                public void mo2130b(int i, String str) throws IOException {
                    this.f2134dD.m2115b(i, str);
                }

                @Override 
                
                public byte[] mo2131h() throws IOException {
                    int iM2118ec = this.f2134dD.m2118ec();
                    if (iM2118ec < 0) {
                        throw new IOException();
                    }
                    if (iM2118ec == 0) {
                        return this.f2135dE;
                    }
                    byte[] bArr = new byte[this.f2135dE.length - iM2118ec];
                    System.arraycopy(this.f2135dE, 0, bArr, 0, bArr.length);
                    return bArr;
                }

                @Override 
                public void reset() {
                    this.f2135dE = new byte[this.f2136dF];
                    this.f2134dD = C1108gl.m2109g(this.f2135dE);
                }
            }
