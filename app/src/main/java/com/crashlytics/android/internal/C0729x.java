            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.os.Bundle;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.x */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 303 */   final class C0729x implements Application.ActivityLifecycleCallbacks {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0728w f471a;

/* 304 */       C0729x(C0728w c0728w) {
/* 305 */           this.f471a = c0728w;
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 308 */       public final void onActivityCreated(Activity activity, Bundle bundle) {
/* 309 */           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 313 */       public final void onActivityStarted(Activity activity) {
/* 314 */           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 318 */       public final void onActivityResumed(Activity activity) {
/* 319 */           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 322 */       public final void onActivityPaused(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 325 */       public final void onActivityStopped(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 328 */       public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
/* 331 */       public final void onActivityDestroyed(Activity activity) {
                }
            }
