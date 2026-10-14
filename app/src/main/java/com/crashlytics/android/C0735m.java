            package com.crashlytics.android;

            import android.app.Activity;
            import com.crashlytics.android.internal.C0677aX;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.InterfaceC0674aU;
            
            
            
   final class C0735m implements InterfaceC0674aU<Boolean> {

                
                private  Crashlytics f478a;

       C0735m(Crashlytics crashlytics) {
           this.f478a = crashlytics;
                }

                @Override 
                
       public final  Boolean mo188a(C0677aX c0677aX) {
           boolean zM14a = true;
                    Activity activityM367e = C0727v.m357a().m367e();
                    if (activityM367e != null && !activityM367e.isFinishing() && this.f478a.m29j()) {
                        zM14a = Crashlytics.m14a(this.f478a, activityM367e, c0677aX.f338c);
                    }
                    return Boolean.valueOf(zM14a);
                }
            }
