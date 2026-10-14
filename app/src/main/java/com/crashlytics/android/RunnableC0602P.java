            package com.crashlytics.android;

            import java.util.Date;
            
            
            
   final class RunnableC0602P implements Runnable {

                
                private  Date f162a;

                
                private  Thread f163b;

                
                private  Throwable f164c;

                
                private  C0744v f165d;

       RunnableC0602P(C0744v c0744v, Date date, Thread thread, Throwable th) {
           this.f165d = c0744v;
                    this.f162a = date;
                    this.f163b = thread;
                    this.f164c = th;
                }

                @Override 
       public final void run() {
           if (!C0744v.m390a(this.f165d).get()) {
               C0744v.m414b(this.f165d, this.f162a, this.f163b, this.f164c);
                    }
                }
            }
