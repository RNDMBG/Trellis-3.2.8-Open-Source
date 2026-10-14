            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.R */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 159 */   final class RunnableC0644R implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0664aK f254a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ String f255b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ C0641O f256c;

/* 160 */       RunnableC0644R(C0641O c0641o, C0664aK c0664aK, String str) {
/* 161 */           this.f256c = c0641o;
                    this.f254a = c0664aK;
                    this.f255b = str;
                }

                @Override // java.lang.Runnable
/* 164 */       public final void run() {
                    try {
/* 165 */               this.f256c.f239a.mo112a(this.f254a, this.f255b);
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to set analytics settings data.");
                    }
                }
            }
