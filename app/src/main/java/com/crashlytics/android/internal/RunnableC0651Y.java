            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.Y */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     final class RunnableC0651Y implements Runnable {

                /* JADX INFO: renamed from: a */
                private final C0637K f282a;

                /* JADX INFO: renamed from: b */
                private final InterfaceC0647U f283b;

/* 10 */        public RunnableC0651Y(C0637K c0637k, InterfaceC0647U interfaceC0647U) {
/* 12 */            this.f282a = c0637k;
/* 13 */            this.f283b = interfaceC0647U;
                }

                @Override // java.lang.Runnable
/* 18 */        public final void run() {
                    try {
/* 19 */                C0681ab.m221c("Performing time based analytics file roll over.");
/* 22 */                if (!this.f282a.m124a()) {
/* 25 */                    this.f283b.mo114c();
                        }
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to roll over session analytics file");
                    }
                }
            }
