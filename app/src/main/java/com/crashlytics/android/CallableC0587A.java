            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0587A implements Callable<Void> {

                
                private  C0744v f125a;

       CallableC0587A(C0744v c0744v) {
           this.f125a = c0744v;
                }

                @Override 
       public final  Void call() throws Exception {
           C0744v.m419f(this.f125a).createNewFile();
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Initialization marker file created.");
                    return null;
                }
            }
