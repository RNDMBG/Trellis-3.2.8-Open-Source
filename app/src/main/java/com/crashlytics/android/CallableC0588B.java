            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0588B implements Callable<Boolean> {

                
                private  C0744v f126a;

       CallableC0588B(C0744v c0744v) {
           this.f126a = c0744v;
                }

                @Override 
       public final  Boolean call() throws Exception {
           return m5a();
                }

                
       private Boolean m5a() throws Exception {
                    try {
               boolean zDelete = C0744v.m419f(this.f126a).delete();
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Initialization marker file removed: " + zDelete);
               return Boolean.valueOf(zDelete);
                    } catch (Exception e) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered deleting Crashlytics initialization marker.", e);
               return false;
                    }
                }
            }
