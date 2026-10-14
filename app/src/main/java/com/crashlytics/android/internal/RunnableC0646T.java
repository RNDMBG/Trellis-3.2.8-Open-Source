            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.T */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 189 */   final class RunnableC0646T implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0641O f258a;

/* 190 */       RunnableC0646T(C0641O c0641o) {
/* 191 */           this.f258a = c0641o;
                }

                @Override // java.lang.Runnable
/* 194 */       public final void run() {
                    try {
/* 195 */               InterfaceC0647U interfaceC0647U = this.f258a.f239a;
/* 196 */               this.f258a.f239a = new C0635I();
/* 197 */               interfaceC0647U.mo113b();
                    } catch (Exception e) {
/* 199 */               C0681ab.m223d("Crashlytics failed to disable analytics.");
                    }
                }
            }
