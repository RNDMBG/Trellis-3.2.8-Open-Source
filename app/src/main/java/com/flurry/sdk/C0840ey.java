            package com.flurry.sdk;

            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ey */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class C0840ey {

                /* JADX INFO: renamed from: a */
                private static final String f840a = C0840ey.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private final C0806dq<a> f841b = new C0806dq<>();

                /* JADX INFO: renamed from: c */
                private long f842c = 1000;

                /* JADX INFO: renamed from: d */
                private boolean f843d = true;

                /* JADX INFO: renamed from: e */
                private boolean f844e = false;

                /* JADX INFO: renamed from: f */
                private AbstractRunnableC0844fc f845f = new AnonymousClass1();

                /* JADX INFO: renamed from: com.flurry.sdk.ey$a */
                public interface a {
                    /* JADX INFO: renamed from: a */
                    void m938a(C0840ey c0840ey);
                }

                /* JADX INFO: renamed from: a */
/* 14 */        static /* synthetic */ List m923a(C0840ey c0840ey) {
/* 15 */            return c0840ey.m929f();
                }

                /* JADX INFO: renamed from: b */
/* 14 */        static /* synthetic */ boolean m924b(C0840ey c0840ey) {
/* 15 */            return c0840ey.f843d;
                }

                /* JADX INFO: renamed from: c */
/* 14 */        static /* synthetic */ boolean m925c(C0840ey c0840ey) {
/* 15 */            return c0840ey.f844e;
                }

                /* JADX INFO: renamed from: d */
/* 14 */        static /* synthetic */ AbstractRunnableC0844fc m926d(C0840ey c0840ey) {
/* 15 */            return c0840ey.f845f;
                }

                /* JADX INFO: renamed from: e */
/* 14 */        static /* synthetic */ long m927e(C0840ey c0840ey) {
/* 15 */            return c0840ey.f842c;
                }

                /* JADX INFO: renamed from: e */
/* 14 */        static /* synthetic */ String m928e() {
/* 15 */            return f840a;
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ey$1, reason: invalid class name */
/* 26 */        class AnonymousClass1 extends AbstractRunnableC0844fc {
/* 27 */            AnonymousClass1() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 31 */            public void mo576a() {
                        try {
/* 33 */                    Iterator it = C0840ey.m923a(C0840ey.this).iterator();
                            while (it.hasNext()) {
/* 34 */                        ((a) it.next()).m938a(C0840ey.this);
                            }
                        } catch (Throwable th) {
/* 37 */                    C0827el.m886a(6, C0840ey.m928e(), "", th);
                        }
/* 40 */                if (C0840ey.m924b(C0840ey.this) && C0840ey.m925c(C0840ey.this)) {
/* 41 */                    C0801dl.m732a().m735a(C0840ey.m926d(C0840ey.this), C0840ey.m927e(C0840ey.this));
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 49 */        public void m931a(long j) {
/* 50 */            this.f842c = j;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        public void m933a(boolean z) {
/* 54 */            this.f843d = z;
                }

                /* JADX INFO: renamed from: a */
/* 57 */        public synchronized void m930a() {
                    try {
                        if (!this.f844e) {
/* 62 */                    C0801dl.m732a().m735a(this.f845f, this.f842c);
/* 63 */                    this.f844e = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 66 */        public synchronized void m934b() {
                    try {
                        if (this.f844e) {
/* 71 */                    C0801dl.m732a().m737b(this.f845f);
/* 72 */                    this.f844e = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 75 */        public synchronized boolean m936c() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f844e;
                }

                /* JADX INFO: renamed from: a */
/* 79 */        public synchronized void m932a(a aVar) {
                    try {
                        this.f841b.m760a(aVar);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 83 */        public synchronized boolean m935b(a aVar) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m762b(aVar);
                }

                /* JADX INFO: renamed from: d */
/* 95 */        public synchronized int m937d() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m761b();
                }

                /* JADX INFO: renamed from: f */
/* 99 */        private synchronized List<a> m929f() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m759a();
                }
            }
