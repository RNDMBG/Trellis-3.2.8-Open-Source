            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0677aX;
            import com.crashlytics.android.internal.InterfaceC0674aU;
            
            
            
   final class C0734l implements InterfaceC0674aU<Boolean> {

                
                private  Crashlytics f477a;

       C0734l(Crashlytics crashlytics) {
           this.f477a = crashlytics;
                }

                @Override 
                
       public final  Boolean mo188a(C0677aX c0677aX) {
           if (!c0677aX.f339d.f312a) {
                        return false;
                    }
                    Crashlytics crashlytics = this.f477a;
                    return Boolean.valueOf(Crashlytics.m24k() ? false : true);
                }
            }
