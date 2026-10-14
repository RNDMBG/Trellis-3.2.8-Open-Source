            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            
            
            
    final class C0631E extends AbstractRunnableC0680aa {

                
                private  C0630D f221a;

        C0631E(C0630D c0630d) {
            this.f221a = c0630d;
                }

                @Override 
                
        public final void mo38a() {
                    try {
                C0630D.m104a(this.f221a);
                    } catch (Exception e) {
                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered during Crashlytics initialization.", e);
                    }
                }
            }
