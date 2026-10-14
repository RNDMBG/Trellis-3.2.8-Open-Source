            package com.crashlytics.android.internal;

            import android.app.Application;
            
            
            
   final class C0728w {

                
                final  C0727v f470a;

       private C0728w(C0727v c0727v) {
           this.f470a = c0727v;
                }

        C0728w(C0727v c0727v, byte b) {
           this(c0727v);
                }

                
       static  void m372a(C0728w c0728w, Application application) {
           if (application != null) {
                        application.registerActivityLifecycleCallbacks(new C0729x(c0728w));
                    }
                }
            }
