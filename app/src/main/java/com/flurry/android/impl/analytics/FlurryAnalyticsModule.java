            package com.flurry.android.impl.analytics;

            import android.content.Context;
            import com.flurry.sdk.C0790da;
            import com.flurry.sdk.C0796dg;
            import com.flurry.sdk.InterfaceC0832eq;
            
            
    public class FlurryAnalyticsModule implements InterfaceC0832eq {

                
                private static FlurryAnalyticsModule f529a;

                
                private C0790da f530b;

        public static synchronized FlurryAnalyticsModule getInstance() {
                    try {
                        if (f529a == null) {
                    f529a = new FlurryAnalyticsModule();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f529a;
                }

        private FlurryAnalyticsModule() {
                }

                @Override 
                
        public void mo529a(C0796dg c0796dg, Context context) {
            if (this.f530b == null) {
                this.f530b = new C0790da();
                    }
                }

                @Override 
                
        public void mo530b(C0796dg c0796dg, Context context) {
                }

                @Override 
                
        public void mo531c(C0796dg c0796dg, Context context) {
                }

                @Override 
                
        public void mo528a(C0796dg c0796dg) {
                }

                
        public C0790da m527a() {
            return this.f530b;
                }
            }
