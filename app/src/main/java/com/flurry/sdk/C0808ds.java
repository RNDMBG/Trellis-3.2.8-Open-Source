            package com.flurry.sdk;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.Application;
            import android.os.Build;
            import android.os.Bundle;
            import java.util.Iterator;
            import java.util.List;
            
            
            
    public class C0808ds {

                
                private static C0808ds f726b;

                
                private final C0806dq<InterfaceC0807dr> f727a = new C0806dq<>();

                
        static  List m765a(C0808ds c0808ds) {
            return c0808ds.m766c();
                }

                
        public static synchronized C0808ds m764a() {
                    try {
                        if (f726b == null) {
                    f726b = new C0808ds();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f726b;
                }

                
        public boolean m768b() {
                    return Build.VERSION.SDK_INT >= 14;
                }

                @TargetApi(14)
        private C0808ds() {
            if (Build.VERSION.SDK_INT >= 14) {
                ((Application) C0801dl.m732a().m736b()).registerActivityLifecycleCallbacks(new AnonymousClass1());
                    }
                }

                
        class AnonymousClass1 implements Application.ActivityLifecycleCallbacks {
            AnonymousClass1() {
                    }

                    @Override 
            public void onActivityCreated(Activity activity, Bundle bundle) {
                m769a(activity, InterfaceC0807dr.a.kCreated);
                    }

                    @Override 
            public void onActivityStarted(Activity activity) {
                m769a(activity, InterfaceC0807dr.a.kStarted);
                    }

                    @Override 
            public void onActivityResumed(Activity activity) {
                m769a(activity, InterfaceC0807dr.a.kResumed);
                    }

                    @Override 
            public void onActivityPaused(Activity activity) {
                m769a(activity, InterfaceC0807dr.a.kPaused);
                    }

                    @Override 
            public void onActivityStopped(Activity activity) {
                m769a(activity, InterfaceC0807dr.a.kStopped);
                    }

                    @Override 
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                m769a(activity, InterfaceC0807dr.a.kSaveState);
                    }

                    @Override 
            public void onActivityDestroyed(Activity activity) {
                m769a(activity, InterfaceC0807dr.a.kDestroyed);
                    }

                    
            protected void m769a(Activity activity, InterfaceC0807dr.a aVar) {
                Iterator it = C0808ds.m765a(C0808ds.this).iterator();
                        while (it.hasNext()) {
                    ((InterfaceC0807dr) it.next()).m763a(activity, aVar);
                        }
                    }
                }

                
        public synchronized void m767a(InterfaceC0807dr interfaceC0807dr) {
                    try {
                        this.f727a.m760a(interfaceC0807dr);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        private synchronized List<InterfaceC0807dr> m766c() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f727a.m759a();
                }
            }
