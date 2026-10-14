            package com.flurry.sdk;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ex */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class C0839ex {

                /* JADX INFO: renamed from: a */
                private static long f837a = 1000;

                /* JADX INFO: renamed from: b */
                private static C0839ex f838b = null;

                /* JADX INFO: renamed from: c */
                private final C0840ey f839c = new C0840ey();

                /* JADX INFO: renamed from: a */
/* 12 */        public static synchronized C0839ex m920a() {
                    try {
                        if (f838b == null) {
/* 14 */                    f838b = new C0839ex();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 17 */            return f838b;
                }

/* 19 */        public C0839ex() {
/* 21 */            this.f839c.m931a(f837a);
/* 22 */            this.f839c.m933a(true);
                }

                /* JADX INFO: renamed from: a */
/* 25 */        public synchronized void m921a(C0840ey.a aVar) {
                    try {
                        this.f839c.m932a(aVar);
/* 27 */                if (!this.f839c.m936c() && this.f839c.m937d() > 0) {
/* 28 */                    this.f839c.m930a();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 32 */        public synchronized boolean m922b(C0840ey.a aVar) {
                    boolean zM935b;
                    try {
                        zM935b = this.f839c.m935b(aVar);
/* 34 */                if (this.f839c.m937d() == 0) {
/* 35 */                    this.f839c.m934b();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zM935b;
                }
            }
