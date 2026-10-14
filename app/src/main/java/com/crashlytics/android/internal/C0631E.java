            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.E */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 87 */    final class C0631E extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0630D f221a;

/* 88 */        C0631E(C0630D c0630d) {
/* 89 */            this.f221a = c0630d;
                }

                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 92 */        public final void mo38a() {
                    try {
/* 93 */                C0630D.m104a(this.f221a);
                    } catch (Exception e) {
/* 95 */                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered during Crashlytics initialization.", e);
                    }
                }
            }
