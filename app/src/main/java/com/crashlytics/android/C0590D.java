            package com.crashlytics.android;

            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import java.io.File;
            
            
            
  final class C0590D extends AbstractRunnableC0680aa {

                
                private  File f150a;

      C0590D(C0744v c0744v, File file) {
          this.f150a = file;
                }

                @Override 
                
      public final void mo38a() {
          InterfaceC0608V interfaceC0608VM35q = Crashlytics.getInstance().m35q();
          if (interfaceC0608VM35q != null) {
              new C0614aa(interfaceC0608VM35q).m58a(new C0612Z(this.f150a, C0744v.m421j()));
                    }
                }
            }
