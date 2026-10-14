            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.os.Bundle;
            
            
            
    final class C0633G implements Application.ActivityLifecycleCallbacks {

                
                private  C0632F f224a;

        C0633G(C0632F c0632f) {
            this.f224a = c0632f;
                }

                @Override 
        public final void onActivityCreated(Activity activity, Bundle bundle) {
            this.f224a.m139a(activity);
                }

                @Override 
        public final void onActivityDestroyed(Activity activity) {
            this.f224a.m143b(activity);
                }

                @Override 
        public final void onActivityPaused(Activity activity) {
            this.f224a.m146c(activity);
                }

                @Override 
        public final void onActivityResumed(Activity activity) {
            this.f224a.m147d(activity);
                }

                @Override 
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            this.f224a.m148e(activity);
                }

                @Override 
        public final void onActivityStarted(Activity activity) {
            this.f224a.m149f(activity);
                }

                @Override 
        public final void onActivityStopped(Activity activity) {
            this.f224a.m150g(activity);
                }
            }
