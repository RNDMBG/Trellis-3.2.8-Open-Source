            package com.flurry.sdk;

            import java.lang.ref.WeakReference;
            import java.util.concurrent.Callable;
            import java.util.concurrent.FutureTask;
            
            
            
    public class C0818ec<V> extends FutureTask<V> {

                
                private final WeakReference<Callable<V>> f761a;

                
                private final WeakReference<Runnable> f762b;

        public C0818ec(Runnable runnable, V v) {
            super(runnable, v);
            this.f761a = null;
            this.f762b = new WeakReference<>(runnable);
                }

                
        public Runnable m826a() {
            return this.f762b.get();
                }
            }
