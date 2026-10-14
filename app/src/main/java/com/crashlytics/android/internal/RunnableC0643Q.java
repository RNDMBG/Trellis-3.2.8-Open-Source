            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.Q */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 139 */   final class RunnableC0643Q implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0648V f251a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ boolean f252b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ C0641O f253c;

/* 140 */       RunnableC0643Q(C0641O c0641o, C0648V c0648v, boolean z) {
/* 141 */           this.f253c = c0641o;
                    this.f251a = c0648v;
                    this.f252b = z;
                }

                @Override // java.lang.Runnable
/* 144 */       public final void run() {
                    try {
/* 145 */               this.f253c.f239a.mo111a(this.f251a);
/* 147 */               if (this.f252b) {
/* 149 */                   this.f253c.f239a.mo115d();
                        }
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to record session event.");
                    }
                }
            }
