            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.os.Bundle;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.G */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    final class C0633G implements Application.ActivityLifecycleCallbacks {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0632F f224a;

/* 19 */        C0633G(C0632F c0632f) {
/* 20 */            this.f224a = c0632f;
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 22 */        public final void onActivityCreated(Activity activity, Bundle bundle) {
/* 23 */            this.f224a.m139a(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 27 */        public final void onActivityDestroyed(Activity activity) {
/* 28 */            this.f224a.m143b(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 32 */        public final void onActivityPaused(Activity activity) {
/* 33 */            this.f224a.m146c(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 37 */        public final void onActivityResumed(Activity activity) {
/* 38 */            this.f224a.m147d(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 42 */        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
/* 43 */            this.f224a.m148e(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 47 */        public final void onActivityStarted(Activity activity) {
/* 48 */            this.f224a.m149f(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 52 */        public final void onActivityStopped(Activity activity) {
/* 53 */            this.f224a.m150g(activity);
                }
            }
