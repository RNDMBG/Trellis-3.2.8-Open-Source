            package com.flurry.sdk;
            
            
            
    public class C0839ex {

                
                private static long f837a = 1000;

                
                private static C0839ex f838b = null;

                
                private final C0840ey f839c = new C0840ey();

                
        public static synchronized C0839ex m920a() {
                    try {
                        if (f838b == null) {
                    f838b = new C0839ex();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f838b;
                }

        public C0839ex() {
            this.f839c.m931a(f837a);
            this.f839c.m933a(true);
                }

                
        public synchronized void m921a(C0840ey.a aVar) {
                    try {
                        this.f839c.m932a(aVar);
                if (!this.f839c.m936c() && this.f839c.m937d() > 0) {
                    this.f839c.m930a();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized boolean m922b(C0840ey.a aVar) {
                    boolean zM935b;
                    try {
                        zM935b = this.f839c.m935b(aVar);
                if (this.f839c.m937d() == 0) {
                    this.f839c.m934b();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zM935b;
                }
            }
