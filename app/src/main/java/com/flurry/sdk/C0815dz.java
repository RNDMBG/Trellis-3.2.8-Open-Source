            package com.flurry.sdk;

            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            import java.util.WeakHashMap;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dz */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public final class C0815dz {

                /* JADX INFO: renamed from: a */
                private static C0815dz f755a;

                /* JADX INFO: renamed from: c */
                private final Map<Thread.UncaughtExceptionHandler, Void> f757c = new WeakHashMap();

                /* JADX INFO: renamed from: b */
                private final Thread.UncaughtExceptionHandler f756b = Thread.getDefaultUncaughtExceptionHandler();

                /* JADX INFO: renamed from: a */
/* 18 */        static /* synthetic */ void m816a(C0815dz c0815dz, Thread thread, Throwable th) {
/* 19 */            c0815dz.m817a(thread, th);
                }

                /* JADX INFO: renamed from: b */
/* 18 */        static /* synthetic */ void m819b(C0815dz c0815dz, Thread thread, Throwable th) {
/* 19 */            c0815dz.m820b(thread, th);
                }

                /* JADX INFO: renamed from: a */
/* 30 */        public static synchronized C0815dz m815a() {
                    try {
                        if (f755a == null) {
/* 32 */                    f755a = new C0815dz();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 35 */            return f755a;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        public void m821a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
                    synchronized (this.f757c) {
                        try {
/* 55 */                    this.f757c.put(uncaughtExceptionHandler, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 70 */        private Set<Thread.UncaughtExceptionHandler> m818b() {
                    Set<Thread.UncaughtExceptionHandler> setKeySet;
                    synchronized (this.f757c) {
                        try {
/* 72 */                    setKeySet = this.f757c.keySet();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return setKeySet;
                }

/* 75 */        private C0815dz() {
/* 79 */            Thread.setDefaultUncaughtExceptionHandler(new a(this, null));
                }

                /* JADX INFO: renamed from: a */
/* 86 */        private void m817a(Thread thread, Throwable th) {
/* 88 */            Iterator<Thread.UncaughtExceptionHandler> it = m818b().iterator();
                    while (it.hasNext()) {
                        try {
/* 90 */                    it.next().uncaughtException(thread, th);
                        } catch (Throwable th2) {
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 97 */        private void m820b(Thread thread, Throwable th) {
/* 98 */            if (this.f756b != null) {
/* 99 */                this.f756b.uncaughtException(thread, th);
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dz$a */
/* 101 */       final class a implements Thread.UncaughtExceptionHandler {
/* 102 */           private a() {
                    }

/* 102 */           /* synthetic */ a(C0815dz c0815dz, Object anonymousClass1) {
/* 103 */               this();
                    }

                    @Override // java.lang.Thread.UncaughtExceptionHandler
/* 105 */           public void uncaughtException(Thread thread, Throwable th) {
/* 106 */               C0815dz.m816a(C0815dz.this, thread, th);
/* 107 */               C0815dz.m819b(C0815dz.this, thread, th);
                    }
                }
            }
