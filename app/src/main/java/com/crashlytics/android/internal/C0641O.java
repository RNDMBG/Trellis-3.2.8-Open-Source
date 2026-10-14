            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.os.Looper;
            import co.vine.android.HomeTabActivity;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.UUID;
            import java.util.concurrent.ScheduledExecutorService;
            
            
            
    class C0641O implements InterfaceC0690ak {

                
                InterfaceC0647U f239a;

                
                private final String f240b;

                
                private final String f241c;

                
                private final String f242d;

                
                private final String f243e;

                
                private final String f244f;

                
                private final String f245g;

                
                private final String f246h;

                
                private final String f247i;

                
                private final ScheduledExecutorService f248j;

                
        static  String m128a(C0641O c0641o) {
            return c0641o.f240b;
                }

                
        static  String m132b(C0641O c0641o) {
            return c0641o.f247i;
                }

                
        static  String m133c(C0641O c0641o) {
            return c0641o.f241c;
                }

                
        static  String m134d(C0641O c0641o) {
            return c0641o.f242d;
                }

                
        static  String m135e(C0641O c0641o) {
            return c0641o.f243e;
                }

                
        static  String m136f(C0641O c0641o) {
            return c0641o.f244f;
                }

                
        static  String m137g(C0641O c0641o) {
            return c0641o.f245g;
                }

                
        static  String m138h(C0641O c0641o) {
            return c0641o.f246h;
                }

        public C0641O(String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, C0701av c0701av) {
            this(str, str2, str3, str4, str5, str6, str7, c0637k, C0687ah.m238b("Crashlytics SAM"), c0701av);
                }

        C0641O(String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, ScheduledExecutorService scheduledExecutorService, C0701av c0701av) {
            this.f240b = str;
            this.f241c = str2;
            this.f242d = str3;
            this.f243e = str4;
            this.f244f = str5;
            this.f245g = str6;
            this.f246h = str7;
            this.f247i = UUID.randomUUID().toString();
            this.f248j = scheduledExecutorService;
            this.f239a = new C0636J(scheduledExecutorService, c0637k, c0701av);
            c0637k.m122a(this);
                }

                
        public final void m141a(String str) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalStateException("onCrash called from main thread!!!");
                    }
                    try {
                        this.f248j.submit(new RunnableC0642P(this, str)).get();
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to run analytics task");
                    }
                }

                
        public final void m142b() {
            m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, EnumC0649W.INSTALL, new HashMap()), true);
                }

                
        public final void m139a(Activity activity) {
           m130a(EnumC0649W.CREATE, activity, false);
                }

                
       public final void m143b(Activity activity) {
           m130a(EnumC0649W.DESTROY, activity, false);
                }

                
       public final void m144b(String str) {
                    m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, EnumC0649W.ERROR, Collections.singletonMap("sessionId", str)), false);
                }

                
       public final void m146c(Activity activity) {
           m130a(EnumC0649W.PAUSE, activity, false);
                }

                
       public final void m147d(Activity activity) {
           m130a(EnumC0649W.RESUME, activity, false);
                }

                
       public final void m148e(Activity activity) {
           m130a(EnumC0649W.SAVE_INSTANCE_STATE, activity, false);
                }

                
       public final void m149f(Activity activity) {
           m130a(EnumC0649W.START, activity, false);
                }

                
       public final void m150g(Activity activity) {
           m130a(EnumC0649W.STOP, activity, false);
                }

                
       private void m130a(EnumC0649W enumC0649W, Activity activity, boolean z) {
           m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, enumC0649W, Collections.singletonMap(HomeTabActivity.TAG_ACTIVITY, activity.getClass().getName())), false);
                }

                
       private void m129a(C0648V c0648v, boolean z) {
           m131a(new RunnableC0643Q(this, c0648v, z));
                }

                
       final void m140a(C0664aK c0664aK, String str) {
           m131a(new RunnableC0644R(this, c0664aK, str));
                }

                @Override 
                
       public final void mo145c() {
           m131a(new RunnableC0645S(this));
                }

                
       void mo108a() {
           m131a(new RunnableC0646T(this));
                }

                
       private void m131a(Runnable runnable) {
                    try {
               this.f248j.submit(runnable);
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to submit analytics task");
                    }
                }
            }
