            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            
            
            
  final class RunnableC0593G implements Runnable {

                
                private  Runnable f153a;

      RunnableC0593G(C0744v c0744v, Runnable runnable) {
          this.f153a = runnable;
                }

                @Override 
      public final void run() {
                    try {
              this.f153a.run();
                    } catch (Exception e) {
              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e);
                    }
                }
            }
