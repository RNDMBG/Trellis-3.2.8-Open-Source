            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.os.Bundle;
            
            
            
   final class C0729x implements Application.ActivityLifecycleCallbacks {

                
                private  C0728w f471a;

       C0729x(C0728w c0728w) {
           this.f471a = c0728w;
                }

                @Override 
       public final void onActivityCreated(Activity activity, Bundle bundle) {
           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override 
       public final void onActivityStarted(Activity activity) {
           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override 
       public final void onActivityResumed(Activity activity) {
           C0727v.m359a(this.f471a.f470a, activity);
                }

                @Override 
       public final void onActivityPaused(Activity activity) {
                }

                @Override 
       public final void onActivityStopped(Activity activity) {
                }

                @Override 
       public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override 
       public final void onActivityDestroyed(Activity activity) {
                }
            }
