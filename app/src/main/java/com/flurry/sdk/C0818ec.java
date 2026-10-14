            package com.flurry.sdk;

            import java.lang.ref.WeakReference;
            import java.util.concurrent.Callable;
            import java.util.concurrent.FutureTask;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ec */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class C0818ec<V> extends FutureTask<V> {

                /* JADX INFO: renamed from: a */
                private final WeakReference<Callable<V>> f761a;

                /* JADX INFO: renamed from: b */
                private final WeakReference<Runnable> f762b;

/* 23 */        public C0818ec(Runnable runnable, V v) {
/* 24 */            super(runnable, v);
/* 26 */            this.f761a = null;
/* 27 */            this.f762b = new WeakReference<>(runnable);
                }

                /* JADX INFO: renamed from: a */
/* 34 */        public Runnable m826a() {
/* 35 */            return this.f762b.get();
                }
            }
