            package com.crashlytics.android;

            import com.crashlytics.android.internal.InterfaceC0660aG;
            import java.io.InputStream;
            
            
            
   final class C0733k implements InterfaceC0660aG {

                
                private  PinningInfoProvider f476a;

       C0733k(PinningInfoProvider pinningInfoProvider) {
           this.f476a = pinningInfoProvider;
                }

                @Override 
                
       public final InputStream mo164a() {
           return this.f476a.getKeyStoreStream();
                }

                @Override 
                
       public final String mo165b() {
           return this.f476a.getKeyStorePassword();
                }

                @Override 
                
       public final String[] mo166c() {
           return this.f476a.getPins();
                }
            }
