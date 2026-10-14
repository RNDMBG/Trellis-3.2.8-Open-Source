            package com.crashlytics.android.internal;
            
            
            
   final class RunnableC0644R implements Runnable {

                
                private  C0664aK f254a;

                
                private  String f255b;

                
                private  C0641O f256c;

       RunnableC0644R(C0641O c0641o, C0664aK c0664aK, String str) {
           this.f256c = c0641o;
                    this.f254a = c0664aK;
                    this.f255b = str;
                }

                @Override 
       public final void run() {
                    try {
               this.f256c.f239a.mo112a(this.f254a, this.f255b);
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to set analytics settings data.");
                    }
                }
            }
