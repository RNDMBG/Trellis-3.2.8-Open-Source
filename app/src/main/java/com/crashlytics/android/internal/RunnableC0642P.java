            package com.crashlytics.android.internal;

            import java.util.Collections;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.P */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 77 */    final class RunnableC0642P implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ String f249a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ C0641O f250b;

/* 78 */        RunnableC0642P(C0641O c0641o, String str) {
/* 79 */            this.f250b = c0641o;
                    this.f249a = str;
                }

                @Override // java.lang.Runnable
/* 82 */        public final void run() {
                    try {
/* 83 */                this.f250b.f239a.mo111a(C0648V.m151a(C0641O.m128a(this.f250b), C0641O.m132b(this.f250b), C0641O.m133c(this.f250b), C0641O.m134d(this.f250b), C0641O.m135e(this.f250b), C0641O.m136f(this.f250b), C0641O.m137g(this.f250b), C0641O.m138h(this.f250b), EnumC0649W.CRASH, Collections.singletonMap("sessionId", this.f249a)));
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to record crash event");
                    }
                }
            }
