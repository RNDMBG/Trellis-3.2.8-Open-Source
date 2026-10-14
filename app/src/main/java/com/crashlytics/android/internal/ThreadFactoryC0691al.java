            package com.crashlytics.android.internal;

            import java.util.Locale;
            import java.util.concurrent.Executors;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.atomic.AtomicLong;
            
            
            
    final class ThreadFactoryC0691al implements ThreadFactory {

                
                private  String f378a;

                
                private  AtomicLong f379b;

        ThreadFactoryC0691al(String str, AtomicLong atomicLong) {
            this.f378a = str;
                    this.f379b = atomicLong;
                }

                @Override 
        public final Thread newThread(Runnable runnable) {
            Thread threadNewThread = Executors.defaultThreadFactory().newThread(new C0692am(this, runnable));
                    threadNewThread.setName(String.format(Locale.US, this.f378a, Long.valueOf(this.f379b.getAndIncrement())));
            return threadNewThread;
                }
            }
