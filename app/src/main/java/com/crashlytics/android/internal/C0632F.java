            package com.crashlytics.android.internal;

            import android.annotation.TargetApi;
            import android.app.Application;
            import java.util.concurrent.ScheduledExecutorService;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.F */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 59 */    final class C0632F extends C0641O {

                /* JADX INFO: renamed from: b */
                private final Application f222b;

                /* JADX INFO: renamed from: c */
                private final Application.ActivityLifecycleCallbacks f223c;

/* 60 */        public C0632F(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, C0701av c0701av) {
/* 61 */            this(application, str, str2, str3, str4, str5, str6, str7, c0637k, C0687ah.m238b("Crashlytics Trace Manager"), c0701av);
                }

/* 70 */        private C0632F(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, ScheduledExecutorService scheduledExecutorService, C0701av c0701av) {
/* 71 */            super(str, str2, str3, str4, str5, str6, str7, c0637k, scheduledExecutorService, c0701av);
/* 20 */            this.f223c = new C0633G(this);
/* 74 */            this.f222b = application;
/* 75 */            C0681ab.m221c("Registering activity lifecycle callbacks for session analytics.");
/* 77 */            application.registerActivityLifecycleCallbacks(this.f223c);
                }

                @Override // com.crashlytics.android.internal.C0641O
                /* JADX INFO: renamed from: a */
/* 81 */        final void mo108a() {
/* 82 */            C0681ab.m221c("Unregistering activity lifecycle callbacks for session analytics");
/* 84 */            this.f222b.unregisterActivityLifecycleCallbacks(this.f223c);
/* 85 */            super.mo108a();
                }
            }
