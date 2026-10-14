            package com.crashlytics.android.internal;
            
            
            
   final class RunnableC0645S implements Runnable {

                
                private  C0641O f257a;

       RunnableC0645S(C0641O c0641o) {
           this.f257a = c0641o;
                }

                @Override 
       public final void run() {
                    try {
               this.f257a.f239a.mo110a();
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to send analytics files.");
                    }
                }
            }
