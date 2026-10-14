            package com.crashlytics.android.internal;
            
            
            
     final class RunnableC0651Y implements Runnable {

                
                private final C0637K f282a;

                
                private final InterfaceC0647U f283b;

        public RunnableC0651Y(C0637K c0637k, InterfaceC0647U interfaceC0647U) {
            this.f282a = c0637k;
            this.f283b = interfaceC0647U;
                }

                @Override 
        public final void run() {
                    try {
                C0681ab.m221c("Performing time based analytics file roll over.");
                if (!this.f282a.m124a()) {
                    this.f283b.mo114c();
                        }
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to roll over session analytics file");
                    }
                }
            }
