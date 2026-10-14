            package com.google.android.gms.internal;

            import android.os.Process;
            import java.util.concurrent.RejectedExecutionException;
            import java.util.concurrent.SynchronousQueue;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.atomic.AtomicInteger;
            
            
            
            public final class C0996ch {

                
                private static final ThreadFactory f1584hF = new AnonymousClass2();

                
                private static final ThreadPoolExecutor f1585hG = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue(true), f1584hF);

                
                static class AnonymousClass1 implements Runnable {

                    
                    final  Runnable f1586hH;

                    AnonymousClass1(Runnable runnable) {
                        this.f1586hH = runnable;
                    }

                    @Override 
                    public void run() {
                        Process.setThreadPriority(10);
                        this.f1586hH.run();
                    }
                }

                
                static class AnonymousClass2 implements ThreadFactory {

                    
                    private final AtomicInteger f1587hI = new AtomicInteger(1);

                    AnonymousClass2() {
                    }

                    @Override 
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable, "AdWorker #" + this.f1587hI.getAndIncrement());
                    }
                }

                public static void execute(Runnable task) {
                    try {
                        f1585hG.execute(new AnonymousClass1(task));
                    } catch (RejectedExecutionException e) {
                        C1002cn.m1452b("Too many background threads already running. Aborting task.", e);
                    }
                }
            }
