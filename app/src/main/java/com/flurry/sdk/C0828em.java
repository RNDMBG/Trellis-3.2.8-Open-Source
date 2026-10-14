            package com.flurry.sdk;

            import android.content.Context;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.em */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class C0828em implements InterfaceC0832eq {

                /* JADX INFO: renamed from: a */
                private static C0828em f820a;

                /* JADX INFO: renamed from: b */
                private final List<InterfaceC0832eq> f821b = m903b();

                /* JADX INFO: renamed from: a */
/* 33 */        public static synchronized C0828em m902a() {
                    try {
                        if (f820a == null) {
/* 35 */                    f820a = new C0828em();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 37 */            return f820a;
                }

/* 39 */        private C0828em() {
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: a */
/* 45 */        public void mo529a(C0796dg c0796dg, Context context) {
/* 46 */            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
/* 47 */                it.next().mo529a(c0796dg, context);
                    }
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: b */
/* 52 */        public void mo530b(C0796dg c0796dg, Context context) {
/* 53 */            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
/* 54 */                it.next().mo530b(c0796dg, context);
                    }
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: c */
/* 59 */        public void mo531c(C0796dg c0796dg, Context context) {
/* 60 */            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
/* 61 */                it.next().mo531c(c0796dg, context);
                    }
                }

                @Override // com.flurry.sdk.InterfaceC0832eq
                /* JADX INFO: renamed from: a */
/* 66 */        public void mo528a(C0796dg c0796dg) {
/* 67 */            Iterator<InterfaceC0832eq> it = this.f821b.iterator();
                    while (it.hasNext()) {
/* 68 */                it.next().mo528a(c0796dg);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 72 */        private static List<InterfaceC0832eq> m903b() {
/* 73 */            ArrayList arrayList = new ArrayList();
/* 74 */            arrayList.add(new C0829en("com.flurry.android.impl.analytics.FlurryAnalyticsModule", 10));
/* 76 */            arrayList.add(new C0829en("com.flurry.android.impl.ads.FlurryAdModule", 10));
/* 78 */            return Collections.unmodifiableList(arrayList);
                }
            }
