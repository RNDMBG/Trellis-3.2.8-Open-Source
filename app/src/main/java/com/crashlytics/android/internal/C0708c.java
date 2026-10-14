            package com.crashlytics.android.internal;

            import java.util.concurrent.ConcurrentLinkedQueue;
            
            
            
   final class C0708c extends ThreadLocal<ConcurrentLinkedQueue<C0710e>> {
       C0708c(C0706b c0706b) {
                }

                @Override 
       protected final  ConcurrentLinkedQueue<C0710e> initialValue() {
           return new ConcurrentLinkedQueue<>();
                }
            }
