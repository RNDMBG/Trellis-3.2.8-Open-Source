            package com.crashlytics.android.internal;

            import java.util.Locale;
            import java.util.concurrent.Executors;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.atomic.AtomicLong;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.al */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    final class ThreadFactoryC0691al implements ThreadFactory {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ String f378a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ AtomicLong f379b;

/* 37 */        ThreadFactoryC0691al(String str, AtomicLong atomicLong) {
/* 38 */            this.f378a = str;
                    this.f379b = atomicLong;
                }

                @Override // java.util.concurrent.ThreadFactory
/* 40 */        public final Thread newThread(Runnable runnable) {
/* 41 */            Thread threadNewThread = Executors.defaultThreadFactory().newThread(new C0692am(this, runnable));
                    threadNewThread.setName(String.format(Locale.US, this.f378a, Long.valueOf(this.f379b.getAndIncrement())));
/* 48 */            return threadNewThread;
                }
            }
