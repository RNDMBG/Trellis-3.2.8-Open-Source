            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.S */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 175 */   final class RunnableC0645S implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0641O f257a;

/* 176 */       RunnableC0645S(C0641O c0641o) {
/* 177 */           this.f257a = c0641o;
                }

                @Override // java.lang.Runnable
/* 180 */       public final void run() {
                    try {
/* 181 */               this.f257a.f239a.mo110a();
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to send analytics files.");
                    }
                }
            }
