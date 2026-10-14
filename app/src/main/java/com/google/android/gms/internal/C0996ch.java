            package com.google.android.gms.internal;

            import android.os.Process;
            import java.util.concurrent.RejectedExecutionException;
            import java.util.concurrent.SynchronousQueue;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.atomic.AtomicInteger;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ch */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0996ch {

                /* JADX INFO: renamed from: hF */
                private static final ThreadFactory f1584hF = new AnonymousClass2();

                /* JADX INFO: renamed from: hG */
                private static final ThreadPoolExecutor f1585hG = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue(true), f1584hF);

                /* JADX INFO: renamed from: com.google.android.gms.internal.ch$1, reason: invalid class name */
                static class AnonymousClass1 implements Runnable {

                    /* JADX INFO: renamed from: hH */
                    final /* synthetic */ Runnable f1586hH;

                    AnonymousClass1(Runnable runnable) {
                        this.f1586hH = runnable;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        Process.setThreadPriority(10);
                        this.f1586hH.run();
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ch$2, reason: invalid class name */
                static class AnonymousClass2 implements ThreadFactory {

                    /* JADX INFO: renamed from: hI */
                    private final AtomicInteger f1587hI = new AtomicInteger(1);

                    AnonymousClass2() {
                    }

                    @Override // java.util.concurrent.ThreadFactory
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
