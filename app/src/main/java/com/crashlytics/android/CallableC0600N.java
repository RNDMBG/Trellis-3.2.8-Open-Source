            package com.crashlytics.android;

            import java.util.Date;
            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0600N implements Callable<Void> {

                
                private  Date f157a;

                
                private  Thread f158b;

                
                private  Throwable f159c;

                
                private  C0744v f160d;

       CallableC0600N(C0744v c0744v, Date date, Thread thread, Throwable th) {
           this.f160d = c0744v;
                    this.f157a = date;
                    this.f158b = thread;
                    this.f159c = th;
                }

                @Override 
       public final  Void call() throws Exception {
           C0744v.m401a(this.f160d, this.f157a, this.f158b, this.f159c);
                    return null;
                }
            }
