            package com.crashlytics.android;

            import android.app.Activity;
            import com.crashlytics.android.internal.C0677aX;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.InterfaceC0674aU;
            
            /* JADX INFO: renamed from: com.crashlytics.android.m */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 615 */   final class C0735m implements InterfaceC0674aU<Boolean> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Crashlytics f478a;

/* 616 */       C0735m(Crashlytics crashlytics) {
/* 617 */           this.f478a = crashlytics;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0674aU
                /* JADX INFO: renamed from: a */
/* 616 */       public final /* synthetic */ Boolean mo188a(C0677aX c0677aX) {
/* 617 */           boolean zM14a = true;
                    Activity activityM367e = C0727v.m357a().m367e();
                    if (activityM367e != null && !activityM367e.isFinishing() && this.f478a.m29j()) {
                        zM14a = Crashlytics.m14a(this.f478a, activityM367e, c0677aX.f338c);
                    }
                    return Boolean.valueOf(zM14a);
                }
            }
