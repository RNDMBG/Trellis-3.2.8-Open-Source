            package com.flurry.sdk;

            import java.util.concurrent.ThreadFactory;
            
            
            
    public class ThreadFactoryC0838ew implements ThreadFactory {

                
                private final ThreadGroup f835a;

                
                private final int f836b;

        public ThreadFactoryC0838ew(String str, int i) {
            this.f835a = new ThreadGroup(str);
            this.f836b = i;
                }

                @Override 
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.f835a, runnable);
            thread.setName(this.f835a.getName() + ":" + thread.getId());
            thread.setPriority(this.f836b);
            return thread;
                }
            }
