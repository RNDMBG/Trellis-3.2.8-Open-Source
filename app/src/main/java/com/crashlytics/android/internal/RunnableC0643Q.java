            package com.crashlytics.android.internal;
            
            
            
   final class RunnableC0643Q implements Runnable {

                
                private  C0648V f251a;

                
                private  boolean f252b;

                
                private  C0641O f253c;

       RunnableC0643Q(C0641O c0641o, C0648V c0648v, boolean z) {
           this.f253c = c0641o;
                    this.f251a = c0648v;
                    this.f252b = z;
                }

                @Override 
       public final void run() {
                    try {
               this.f253c.f239a.mo111a(this.f251a);
               if (this.f252b) {
                   this.f253c.f239a.mo115d();
                        }
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to record session event.");
                    }
                }
            }
