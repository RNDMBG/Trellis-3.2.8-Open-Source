            package com.crashlytics.android.internal;

            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import java.util.concurrent.ScheduledExecutorService;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.atomic.AtomicLong;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ah */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class C0687ah {
                /* JADX INFO: renamed from: a */
/* 11 */        public long m240a() {
/* 12 */            return System.currentTimeMillis();
                }

                /* JADX INFO: renamed from: a */
/* 21 */        public static ExecutorService m236a(String str) {
/* 23 */            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor(m239c(str));
/* 24 */            m237a(str, executorServiceNewSingleThreadExecutor);
/* 25 */            return executorServiceNewSingleThreadExecutor;
                }

                /* JADX INFO: renamed from: b */
/* 28 */        public static ScheduledExecutorService m238b(String str) {
/* 30 */            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(m239c(str));
/* 31 */            m237a(str, scheduledExecutorServiceNewSingleThreadScheduledExecutor);
/* 32 */            return scheduledExecutorServiceNewSingleThreadScheduledExecutor;
                }

                /* JADX INFO: renamed from: c */
/* 35 */        public static ThreadFactory m239c(String str) {
/* 38 */            return new ThreadFactoryC0691al(str, new AtomicLong(1L));
                }

                /* JADX INFO: renamed from: a */
/* 53 */        private static void m237a(String str, ExecutorService executorService) {
                    Runtime.getRuntime().addShutdownHook(new Thread(new C0693an(str, executorService, 2L, TimeUnit.SECONDS), "Crashlytics Shutdown Hook for " + str));
                }
            }
