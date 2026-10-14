            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.io.InputStream;
            
            
            
   final class C0697ar implements InterfaceC0700au {

                
                private boolean f405a = true;

                
                private  StringBuilder f406b;

       C0697ar(C0696aq c0696aq, StringBuilder sb) {
           this.f406b = sb;
                }

                @Override 
                
       public final void mo290a(InputStream inputStream, int i) throws IOException {
           if (this.f405a) {
               this.f405a = false;
                    } else {
               this.f406b.append(", ");
                    }
           this.f406b.append(i);
                }
            }
