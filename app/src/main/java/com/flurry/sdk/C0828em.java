            package com.flurry.sdk;

            import android.content.Context;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Iterator;
            import java.util.List;
            
            
            
    public final class C0828em implements InterfaceC0832eq {

                
                private static C0828em f820a;

                
                private final List<InterfaceC0832eq> f821b = m903b();

                
        public static synchronized C0828em m902a() {
                    try {
                        if (f820a == null) {
                    f820a = new C0828em();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f820a;
                }

        private C0828em() {
                }

                @Override 
                
        public void mo529a(C0796dg c0796dg, Context context) {
            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
                it.next().mo529a(c0796dg, context);
                    }
                }

                @Override 
                
        public void mo530b(C0796dg c0796dg, Context context) {
            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
                it.next().mo530b(c0796dg, context);
                    }
                }

                @Override 
                
        public void mo531c(C0796dg c0796dg, Context context) {
            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
                it.next().mo531c(c0796dg, context);
                    }
                }

                @Override 
                
        public void mo528a(C0796dg c0796dg) {
            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
                it.next().mo528a(c0796dg);
                    }
                }

                
        private static List<InterfaceC0832eq> m903b() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new C0829en("com.flurry.android.impl.analytics.FlurryAnalyticsModule", 10));
            arrayList.add(new C0829en("com.flurry.android.impl.ads.FlurryAdModule", 10));
            return Collections.unmodifiableList(arrayList);
                }
            }
