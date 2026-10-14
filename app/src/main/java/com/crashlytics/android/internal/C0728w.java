            package com.crashlytics.android.internal;

            import android.app.Application;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.w */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 297 */   final class C0728w {

                /* JADX INFO: renamed from: a */
                final /* synthetic */ C0727v f470a;

/* 298 */       private C0728w(C0727v c0727v) {
/* 299 */           this.f470a = c0727v;
                }

/* 298 */       /* synthetic */ C0728w(C0727v c0727v, byte b) {
/* 299 */           this(c0727v);
                }

                /* JADX INFO: renamed from: a */
/* 298 */       static /* synthetic */ void m372a(C0728w c0728w, Application application) {
/* 299 */           if (application != null) {
                        application.registerActivityLifecycleCallbacks(new C0729x(c0728w));
                    }
                }
            }
