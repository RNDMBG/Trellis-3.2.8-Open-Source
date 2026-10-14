            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.os.Looper;
            import co.vine.android.HomeTabActivity;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.UUID;
            import java.util.concurrent.ScheduledExecutorService;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.O */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    class C0641O implements InterfaceC0690ak {

                /* JADX INFO: renamed from: a */
                InterfaceC0647U f239a;

                /* JADX INFO: renamed from: b */
                private final String f240b;

                /* JADX INFO: renamed from: c */
                private final String f241c;

                /* JADX INFO: renamed from: d */
                private final String f242d;

                /* JADX INFO: renamed from: e */
                private final String f243e;

                /* JADX INFO: renamed from: f */
                private final String f244f;

                /* JADX INFO: renamed from: g */
                private final String f245g;

                /* JADX INFO: renamed from: h */
                private final String f246h;

                /* JADX INFO: renamed from: i */
                private final String f247i;

                /* JADX INFO: renamed from: j */
                private final ScheduledExecutorService f248j;

                /* JADX INFO: renamed from: a */
/* 16 */        static /* synthetic */ String m128a(C0641O c0641o) {
/* 17 */            return c0641o.f240b;
                }

                /* JADX INFO: renamed from: b */
/* 16 */        static /* synthetic */ String m132b(C0641O c0641o) {
/* 17 */            return c0641o.f247i;
                }

                /* JADX INFO: renamed from: c */
/* 16 */        static /* synthetic */ String m133c(C0641O c0641o) {
/* 17 */            return c0641o.f241c;
                }

                /* JADX INFO: renamed from: d */
/* 16 */        static /* synthetic */ String m134d(C0641O c0641o) {
/* 17 */            return c0641o.f242d;
                }

                /* JADX INFO: renamed from: e */
/* 16 */        static /* synthetic */ String m135e(C0641O c0641o) {
/* 17 */            return c0641o.f243e;
                }

                /* JADX INFO: renamed from: f */
/* 16 */        static /* synthetic */ String m136f(C0641O c0641o) {
/* 17 */            return c0641o.f244f;
                }

                /* JADX INFO: renamed from: g */
/* 16 */        static /* synthetic */ String m137g(C0641O c0641o) {
/* 17 */            return c0641o.f245g;
                }

                /* JADX INFO: renamed from: h */
/* 16 */        static /* synthetic */ String m138h(C0641O c0641o) {
/* 17 */            return c0641o.f246h;
                }

/* 36 */        public C0641O(String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, C0701av c0701av) {
/* 37 */            this(str, str2, str3, str4, str5, str6, str7, c0637k, C0687ah.m238b("Crashlytics SAM"), c0701av);
                }

/* 46 */        C0641O(String str, String str2, String str3, String str4, String str5, String str6, String str7, C0637K c0637k, ScheduledExecutorService scheduledExecutorService, C0701av c0701av) {
/* 48 */            this.f240b = str;
/* 49 */            this.f241c = str2;
/* 50 */            this.f242d = str3;
/* 51 */            this.f243e = str4;
/* 52 */            this.f244f = str5;
/* 53 */            this.f245g = str6;
/* 54 */            this.f246h = str7;
/* 55 */            this.f247i = UUID.randomUUID().toString();
/* 56 */            this.f248j = scheduledExecutorService;
/* 58 */            this.f239a = new C0636J(scheduledExecutorService, c0637k, c0701av);
/* 61 */            c0637k.m122a(this);
                }

                /* JADX INFO: renamed from: a */
/* 71 */        public final void m141a(String str) {
/* 72 */            if (Looper.myLooper() == Looper.getMainLooper()) {
/* 73 */                throw new IllegalStateException("onCrash called from main thread!!!");
                    }
                    try {
                        this.f248j.submit(new RunnableC0642P(this, str)).get();
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to run analytics task");
                    }
                }

                /* JADX INFO: renamed from: b */
/* 93 */        public final void m142b() {
/* 94 */            m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, EnumC0649W.INSTALL, new HashMap()), true);
                }

                /* JADX INFO: renamed from: a */
/* 99 */        public final void m139a(Activity activity) {
/* 100 */           m130a(EnumC0649W.CREATE, activity, false);
                }

                /* JADX INFO: renamed from: b */
/* 103 */       public final void m143b(Activity activity) {
/* 104 */           m130a(EnumC0649W.DESTROY, activity, false);
                }

                /* JADX INFO: renamed from: b */
/* 107 */       public final void m144b(String str) {
                    m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, EnumC0649W.ERROR, Collections.singletonMap("sessionId", str)), false);
                }

                /* JADX INFO: renamed from: c */
/* 114 */       public final void m146c(Activity activity) {
/* 115 */           m130a(EnumC0649W.PAUSE, activity, false);
                }

                /* JADX INFO: renamed from: d */
/* 118 */       public final void m147d(Activity activity) {
/* 119 */           m130a(EnumC0649W.RESUME, activity, false);
                }

                /* JADX INFO: renamed from: e */
/* 122 */       public final void m148e(Activity activity) {
/* 123 */           m130a(EnumC0649W.SAVE_INSTANCE_STATE, activity, false);
                }

                /* JADX INFO: renamed from: f */
/* 126 */       public final void m149f(Activity activity) {
/* 127 */           m130a(EnumC0649W.START, activity, false);
                }

                /* JADX INFO: renamed from: g */
/* 130 */       public final void m150g(Activity activity) {
/* 131 */           m130a(EnumC0649W.STOP, activity, false);
                }

                /* JADX INFO: renamed from: a */
/* 134 */       private void m130a(EnumC0649W enumC0649W, Activity activity, boolean z) {
/* 135 */           m129a(C0648V.m151a(this.f240b, this.f247i, this.f241c, this.f242d, this.f243e, this.f244f, this.f245g, this.f246h, enumC0649W, Collections.singletonMap(HomeTabActivity.TAG_ACTIVITY, activity.getClass().getName())), false);
                }

                /* JADX INFO: renamed from: a */
/* 140 */       private void m129a(C0648V c0648v, boolean z) {
/* 141 */           m131a(new RunnableC0643Q(this, c0648v, z));
                }

                /* JADX INFO: renamed from: a */
/* 160 */       final void m140a(C0664aK c0664aK, String str) {
/* 161 */           m131a(new RunnableC0644R(this, c0664aK, str));
                }

                @Override // com.crashlytics.android.internal.InterfaceC0690ak
                /* JADX INFO: renamed from: c */
/* 176 */       public final void mo145c() {
/* 177 */           m131a(new RunnableC0645S(this));
                }

                /* JADX INFO: renamed from: a */
/* 190 */       void mo108a() {
/* 191 */           m131a(new RunnableC0646T(this));
                }

                /* JADX INFO: renamed from: a */
/* 214 */       private void m131a(Runnable runnable) {
                    try {
/* 215 */               this.f248j.submit(runnable);
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to submit analytics task");
                    }
                }
            }
