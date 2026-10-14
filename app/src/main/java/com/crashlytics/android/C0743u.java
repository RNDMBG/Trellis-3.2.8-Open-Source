            package com.crashlytics.android;

            import java.util.concurrent.CountDownLatch;
            
            
            
   final class C0743u {

                
                private boolean f491a;

                
                private final CountDownLatch f492b;

       private C0743u(Crashlytics crashlytics) {
           this.f491a = false;
           this.f492b = new CountDownLatch(1);
                }

        C0743u(Crashlytics crashlytics, byte b) {
           this(crashlytics);
                }

                
       final void m375a(boolean z) {
           this.f491a = z;
           this.f492b.countDown();
                }

                
       final boolean m376a() {
           return this.f491a;
                }

                
       final void m377b() {
                    try {
              this.f492b.await();
                    } catch (InterruptedException e) {
                    }
                }
            }
