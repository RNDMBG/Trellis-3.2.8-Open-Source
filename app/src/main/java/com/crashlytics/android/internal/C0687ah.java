            package com.crashlytics.android.internal;

            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import java.util.concurrent.ScheduledExecutorService;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.atomic.AtomicLong;
            
            
            
    public class C0687ah {
                
        public long m240a() {
            return System.currentTimeMillis();
                }

                
        public static ExecutorService m236a(String str) {
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor(m239c(str));
            m237a(str, executorServiceNewSingleThreadExecutor);
            return executorServiceNewSingleThreadExecutor;
                }

                
        public static ScheduledExecutorService m238b(String str) {
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(m239c(str));
            m237a(str, scheduledExecutorServiceNewSingleThreadScheduledExecutor);
            return scheduledExecutorServiceNewSingleThreadScheduledExecutor;
                }

                
        public static ThreadFactory m239c(String str) {
            return new ThreadFactoryC0691al(str, new AtomicLong(1L));
                }

                
        private static void m237a(String str, ExecutorService executorService) {
                    Runtime.getRuntime().addShutdownHook(new Thread(new C0693an(str, executorService, 2L, TimeUnit.SECONDS), "Crashlytics Shutdown Hook for " + str));
                }
            }
