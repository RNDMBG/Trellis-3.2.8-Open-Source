            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            
            
            
  final class CallableC0595I<T> implements Callable<T> {

                
                private  Callable f154a;

      CallableC0595I(C0744v c0744v, Callable callable) {
          this.f154a = callable;
                }

                @Override 
      public final T call() throws Exception {
                    try {
              return (T) this.f154a.call();
                    } catch (Exception e) {
              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e);
              return null;
                    }
                }
            }
