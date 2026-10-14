            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            
            
   final class CallableC0589C implements Callable<Boolean> {

                
                private  C0744v f127a;

       CallableC0589C(C0744v c0744v) {
           this.f127a = c0744v;
                }

                @Override 
       public final  Boolean call() throws Exception {
           return Boolean.valueOf(C0744v.m419f(this.f127a).exists());
                }
            }
