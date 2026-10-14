            package com.flurry.sdk;

            import java.util.Iterator;
            import java.util.List;
            
            
            
    public class C0840ey {

                
                private static final String f840a = C0840ey.class.getSimpleName();

                
                private final C0806dq<a> f841b = new C0806dq<>();

                
                private long f842c = 1000;

                
                private boolean f843d = true;

                
                private boolean f844e = false;

                
                private AbstractRunnableC0844fc f845f = new AnonymousClass1();

                
                public interface a {
                    
                    void m938a(C0840ey c0840ey);
                }

                
        static  List m923a(C0840ey c0840ey) {
            return c0840ey.m929f();
                }

                
        static  boolean m924b(C0840ey c0840ey) {
            return c0840ey.f843d;
                }

                
        static  boolean m925c(C0840ey c0840ey) {
            return c0840ey.f844e;
                }

                
        static  AbstractRunnableC0844fc m926d(C0840ey c0840ey) {
            return c0840ey.f845f;
                }

                
        static  long m927e(C0840ey c0840ey) {
            return c0840ey.f842c;
                }

                
        static  String m928e() {
            return f840a;
                }

                
        class AnonymousClass1 extends AbstractRunnableC0844fc {
            AnonymousClass1() {
                    }

                    @Override 
                    
            public void mo576a() {
                        try {
                    Iterator it = C0840ey.m923a(C0840ey.this).iterator();
                            while (it.hasNext()) {
                        ((a) it.next()).m938a(C0840ey.this);
                            }
                        } catch (Throwable th) {
                    C0827el.m886a(6, C0840ey.m928e(), "", th);
                        }
                if (C0840ey.m924b(C0840ey.this) && C0840ey.m925c(C0840ey.this)) {
                    C0801dl.m732a().m735a(C0840ey.m926d(C0840ey.this), C0840ey.m927e(C0840ey.this));
                        }
                    }
                }

                
        public void m931a(long j) {
            this.f842c = j;
                }

                
        public void m933a(boolean z) {
            this.f843d = z;
                }

                
        public synchronized void m930a() {
                    try {
                        if (!this.f844e) {
                    C0801dl.m732a().m735a(this.f845f, this.f842c);
                    this.f844e = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m934b() {
                    try {
                        if (this.f844e) {
                    C0801dl.m732a().m737b(this.f845f);
                    this.f844e = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized boolean m936c() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f844e;
                }

                
        public synchronized void m932a(a aVar) {
                    try {
                        this.f841b.m760a(aVar);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized boolean m935b(a aVar) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m762b(aVar);
                }

                
        public synchronized int m937d() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m761b();
                }

                
        private synchronized List<a> m929f() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f841b.m759a();
                }
            }
