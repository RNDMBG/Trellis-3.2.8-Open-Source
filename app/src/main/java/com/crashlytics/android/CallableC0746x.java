            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0746x implements Callable<Void> {

                
                private  long f518a;

                
                private  String f519b;

                
                private  C0744v f520c;

       CallableC0746x(C0744v c0744v, long j, String str) {
           this.f520c = c0744v;
                    this.f518a = j;
                    this.f519b = str;
                }

                @Override 
       public final  Void call() throws Exception {
           if (C0744v.m390a(this.f520c).get()) {
                        return null;
                    }
                    if (C0744v.m417d(this.f520c) == null) {
                        C0744v.m418e(this.f520c);
                    }
                    C0744v c0744v = this.f520c;
                    C0744v.m400a(C0744v.m417d(this.f520c), 65536, this.f518a, this.f519b);
                    return null;
                }
            }
