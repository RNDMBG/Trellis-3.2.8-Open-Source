            package com.flurry.android.impl.analytics;

            import android.content.Context;
            import com.flurry.sdk.C0790da;
            import com.flurry.sdk.C0796dg;
            import com.flurry.sdk.InterfaceC0832eq;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class FlurryAnalyticsModule implements InterfaceC0832eq {

                /* JADX INFO: renamed from: a */
                private static FlurryAnalyticsModule f529a;

                /* JADX INFO: renamed from: b */
                private C0790da f530b;

/* 14 */        public static synchronized FlurryAnalyticsModule getInstance() {
                    try {
                        if (f529a == null) {
/* 16 */                    f529a = new FlurryAnalyticsModule();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 18 */            return f529a;
                }

/* 22 */        private FlurryAnalyticsModule() {
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: a */
/* 35 */        public void mo529a(C0796dg c0796dg, Context context) {
/* 36 */            if (this.f530b == null) {
/* 37 */                this.f530b = new C0790da();
                    }
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: b */
/* 42 */        public void mo530b(C0796dg c0796dg, Context context) {
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: c */
/* 46 */        public void mo531c(C0796dg c0796dg, Context context) {
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: a */
/* 51 */        public void mo528a(C0796dg c0796dg) {
                }

                /* JADX INFO: renamed from: a */
/* 54 */        public C0790da m527a() {
/* 55 */            return this.f530b;
                }
            }
