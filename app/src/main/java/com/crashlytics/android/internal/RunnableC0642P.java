            package com.crashlytics.android.internal;

            import java.util.Collections;
            
            
            
    final class RunnableC0642P implements Runnable {

                
                private  String f249a;

                
                private  C0641O f250b;

        RunnableC0642P(C0641O c0641o, String str) {
            this.f250b = c0641o;
                    this.f249a = str;
                }

                @Override 
        public final void run() {
                    try {
                this.f250b.f239a.mo111a(C0648V.m151a(C0641O.m128a(this.f250b), C0641O.m132b(this.f250b), C0641O.m133c(this.f250b), C0641O.m134d(this.f250b), C0641O.m135e(this.f250b), C0641O.m136f(this.f250b), C0641O.m137g(this.f250b), C0641O.m138h(this.f250b), EnumC0649W.CRASH, Collections.singletonMap("sessionId", this.f249a)));
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to record crash event");
                    }
                }
            }
