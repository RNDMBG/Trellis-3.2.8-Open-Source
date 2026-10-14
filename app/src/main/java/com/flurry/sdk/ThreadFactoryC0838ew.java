            package com.flurry.sdk;

            import java.util.concurrent.ThreadFactory;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ew */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class ThreadFactoryC0838ew implements ThreadFactory {

                /* JADX INFO: renamed from: a */
                private final ThreadGroup f835a;

                /* JADX INFO: renamed from: b */
                private final int f836b;

/* 13 */        public ThreadFactoryC0838ew(String str, int i) {
/* 15 */            this.f835a = new ThreadGroup(str);
/* 16 */            this.f836b = i;
                }

                @Override // java.util.concurrent.ThreadFactory
/* 19 */        public Thread newThread(Runnable runnable) {
/* 20 */            Thread thread = new Thread(this.f835a, runnable);
/* 21 */            thread.setName(this.f835a.getName() + ":" + thread.getId());
/* 22 */            thread.setPriority(this.f836b);
/* 24 */            return thread;
                }
            }
