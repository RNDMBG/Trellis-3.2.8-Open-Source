            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0601O implements Callable<Boolean> {

                
                private  C0744v f161a;

       CallableC0601O(C0744v c0744v) {
           this.f161a = c0744v;
                }

                @Override 
       public final  Boolean call() throws Exception {
           if (C0744v.m390a(this.f161a).get()) {
                        return false;
                    }
                    C0744v.m413b(this.f161a);
                    C0744v.m415c(this.f161a);
                    return true;
                }
            }
