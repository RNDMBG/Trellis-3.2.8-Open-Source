            package com.flurry.sdk;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.Application;
            import android.os.Build;
            import android.os.Bundle;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ds */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class C0808ds {

                /* JADX INFO: renamed from: b */
                private static C0808ds f726b;

                /* JADX INFO: renamed from: a */
                private final C0806dq<InterfaceC0807dr> f727a = new C0806dq<>();

                /* JADX INFO: renamed from: a */
/* 17 */        static /* synthetic */ List m765a(C0808ds c0808ds) {
/* 18 */            return c0808ds.m766c();
                }

                /* JADX INFO: renamed from: a */
/* 23 */        public static synchronized C0808ds m764a() {
                    try {
                        if (f726b == null) {
/* 25 */                    f726b = new C0808ds();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 28 */            return f726b;
                }

                /* JADX INFO: renamed from: b */
/* 31 */        public boolean m768b() {
                    return Build.VERSION.SDK_INT >= 14;
                }

                @TargetApi(14)
/* 35 */        private C0808ds() {
/* 37 */            if (Build.VERSION.SDK_INT >= 14) {
/* 38 */                ((Application) C0801dl.m732a().m736b()).registerActivityLifecycleCallbacks(new AnonymousClass1());
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ds$1, reason: invalid class name */
/* 36 */        class AnonymousClass1 implements Application.ActivityLifecycleCallbacks {
/* 37 */            AnonymousClass1() {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 40 */            public void onActivityCreated(Activity activity, Bundle bundle) {
/* 41 */                m769a(activity, InterfaceC0807dr.a.kCreated);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 45 */            public void onActivityStarted(Activity activity) {
/* 46 */                m769a(activity, InterfaceC0807dr.a.kStarted);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 50 */            public void onActivityResumed(Activity activity) {
/* 51 */                m769a(activity, InterfaceC0807dr.a.kResumed);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 55 */            public void onActivityPaused(Activity activity) {
/* 56 */                m769a(activity, InterfaceC0807dr.a.kPaused);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 60 */            public void onActivityStopped(Activity activity) {
/* 61 */                m769a(activity, InterfaceC0807dr.a.kStopped);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 65 */            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
/* 66 */                m769a(activity, InterfaceC0807dr.a.kSaveState);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
/* 70 */            public void onActivityDestroyed(Activity activity) {
/* 71 */                m769a(activity, InterfaceC0807dr.a.kDestroyed);
                    }

                    /* JADX INFO: renamed from: a */
/* 74 */            protected void m769a(Activity activity, InterfaceC0807dr.a aVar) {
/* 76 */                Iterator it = C0808ds.m765a(C0808ds.this).iterator();
                        while (it.hasNext()) {
/* 77 */                    ((InterfaceC0807dr) it.next()).m763a(activity, aVar);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 84 */        public synchronized void m767a(InterfaceC0807dr interfaceC0807dr) {
                    try {
                        this.f727a.m760a(interfaceC0807dr);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 96 */        private synchronized List<InterfaceC0807dr> m766c() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f727a.m759a();
                }
            }
