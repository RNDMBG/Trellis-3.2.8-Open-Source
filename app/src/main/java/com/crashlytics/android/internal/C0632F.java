            package com.crashlytics.android.internal;

            import android.annotation.TargetApi;
            import android.app.Application;
            import java.util.concurrent.ScheduledExecutorService;
            
            
            
            @TargetApi(14)
    final class C0632F extends C0641O {

                
                private final Application f222b;

                
                private final Application.ActivityLifecycleCallbacks f223c;

        public C0632F(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, C0701av c0701av) {
            this(application, str, str2, str3, str4, str5, str6, str7, c0637k, C0687ah.m238b("Crashlytics Trace Manager"), c0701av);
                }

        private C0632F(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, ScheduledExecutorService scheduledExecutorService, C0701av c0701av) {
            super(str, str2, str3, str4, str5, str6, str7, c0637k, scheduledExecutorService, c0701av);
            this.f223c = new C0633G(this);
            this.f222b = application;
            C0681ab.m221c("Registering activity lifecycle callbacks for session analytics.");
            application.registerActivityLifecycleCallbacks(this.f223c);
                }

                @Override 
                
        final void mo108a() {
            C0681ab.m221c("Unregistering activity lifecycle callbacks for session analytics");
            this.f222b.unregisterActivityLifecycleCallbacks(this.f223c);
            super.mo108a();
                }
            }
