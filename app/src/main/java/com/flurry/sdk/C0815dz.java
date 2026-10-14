            package com.flurry.sdk;

            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            import java.util.WeakHashMap;
            
            
            
    public final class C0815dz {

                
                private static C0815dz f755a;

                
                private final Map<Thread.UncaughtExceptionHandler, Void> f757c = new WeakHashMap();

                
                private final Thread.UncaughtExceptionHandler f756b = Thread.getDefaultUncaughtExceptionHandler();

                
        static  void m816a(C0815dz c0815dz, Thread thread, Throwable th) {
            c0815dz.m817a(thread, th);
                }

                
        static  void m819b(C0815dz c0815dz, Thread thread, Throwable th) {
            c0815dz.m820b(thread, th);
                }

                
        public static synchronized C0815dz m815a() {
                    try {
                        if (f755a == null) {
                    f755a = new C0815dz();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f755a;
                }

                
        public void m821a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
                    synchronized (this.f757c) {
                        try {
                    this.f757c.put(uncaughtExceptionHandler, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
        private Set<Thread.UncaughtExceptionHandler> m818b() {
                    Set<Thread.UncaughtExceptionHandler> setKeySet;
                    synchronized (this.f757c) {
                        try {
                    setKeySet = this.f757c.keySet();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return setKeySet;
                }

        private C0815dz() {
            Thread.setDefaultUncaughtExceptionHandler(new a(this, null));
                }

                
        private void m817a(Thread thread, Throwable th) {
            Iterator<Thread.UncaughtExceptionHandler> it = m818b().iterator();
                    while (it.hasNext()) {
                        try {
                    it.next().uncaughtException(thread, th);
                        } catch (Throwable th2) {
                        }
                    }
                }

                
        private void m820b(Thread thread, Throwable th) {
            if (this.f756b != null) {
                this.f756b.uncaughtException(thread, th);
                    }
                }

                
       final class a implements Thread.UncaughtExceptionHandler {
           private a() {
                    }

            a(C0815dz c0815dz, Object anonymousClass1) {
               this();
                    }

                    @Override 
           public void uncaughtException(Thread thread, Throwable th) {
               C0815dz.m816a(C0815dz.this, thread, th);
               C0815dz.m819b(C0815dz.this, thread, th);
                    }
                }
            }
