            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0677aX;
            import com.crashlytics.android.internal.InterfaceC0674aU;
            
            /* JADX INFO: renamed from: com.crashlytics.android.l */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 578 */   final class C0734l implements InterfaceC0674aU<Boolean> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Crashlytics f477a;

/* 579 */       C0734l(Crashlytics crashlytics) {
/* 580 */           this.f477a = crashlytics;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0674aU
                /* JADX INFO: renamed from: a */
/* 579 */       public final /* synthetic */ Boolean mo188a(C0677aX c0677aX) {
/* 580 */           if (!c0677aX.f339d.f312a) {
                        return false;
                    }
                    Crashlytics crashlytics = this.f477a;
                    return Boolean.valueOf(Crashlytics.m24k() ? false : true);
                }
            }
