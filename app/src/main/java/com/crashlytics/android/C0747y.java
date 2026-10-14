            package com.crashlytics.android;

            import com.crashlytics.android.internal.InterfaceC0700au;
            import java.io.IOException;
            import java.io.InputStream;
            
            
            
   final class C0747y implements InterfaceC0700au {

                
                private  byte[] f521a;

                
                private  int[] f522b;

       C0747y(C0744v c0744v, byte[] bArr, int[] iArr) {
           this.f521a = bArr;
                    this.f522b = iArr;
                }

                @Override 
                
       public final void mo290a(InputStream inputStream, int i) throws IOException {
                    try {
               inputStream.read(this.f521a, this.f522b[0], i);
               int[] iArr = this.f522b;
                        iArr[0] = iArr[0] + i;
               inputStream.close();
                    } catch (Throwable th) {
                        inputStream.close();
                        throw th;
                    }
                }
            }
