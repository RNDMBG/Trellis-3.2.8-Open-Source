            package com.google.android.gms.internal;

            import co.vine.android.util.CommonUtil;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.s */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1121s {

                /* JADX INFO: renamed from: el */
                private final Runnable f2151el;

                /* JADX INFO: renamed from: em */
                private C1124v f2152em;

                /* JADX INFO: renamed from: en */
                private boolean f2153en = false;

                /* JADX INFO: renamed from: com.google.android.gms.internal.s$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {

                    /* JADX INFO: renamed from: eo */
                    private final WeakReference<BinderC1120r> f2154eo;

                    /* JADX INFO: renamed from: ep */
                    final /* synthetic */ BinderC1120r f2155ep;

                    AnonymousClass1(BinderC1120r binderC1120r) {
                        this.f2155ep = binderC1120r;
                        this.f2154eo = new WeakReference<>(this.f2155ep);
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        C1121s.m2169a(C1121s.this, false);
                        BinderC1120r binderC1120r = this.f2154eo.get();
                        if (binderC1120r != null) {
                            binderC1120r.m2167b(C1121s.m2168a(C1121s.this));
                        }
                    }
                }

                public C1121s(BinderC1120r binderC1120r) {
                    this.f2151el = new AnonymousClass1(binderC1120r);
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ C1124v m2168a(C1121s c1121s) {
                    return c1121s.f2152em;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ boolean m2169a(C1121s c1121s, boolean z) {
                    c1121s.f2153en = z;
                    return z;
                }

                /* JADX INFO: renamed from: a */
                public void m2170a(C1124v c1124v, long j) {
                    if (this.f2153en) {
                        C1002cn.m1458q("An ad refresh is already scheduled.");
                        return;
                    }
                    C1002cn.m1456o("Scheduling ad refresh " + j + " milliseconds from now.");
                    this.f2152em = c1124v;
                    this.f2153en = true;
                    C1001cm.f1595hO.postDelayed(this.f2151el, j);
                }

                public void cancel() {
                    C1001cm.f1595hO.removeCallbacks(this.f2151el);
                }

                /* JADX INFO: renamed from: d */
                public void m2171d(C1124v c1124v) {
                    m2170a(c1124v, CommonUtil.MINUTE_MILLIS);
                }
            }
