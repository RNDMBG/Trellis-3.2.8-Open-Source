            package com.crashlytics.android.internal;

            import android.os.Looper;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.o */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    final class C0720o implements InterfaceC0718m {
/* 43 */        C0720o() {
                }

                @Override // com.crashlytics.android.internal.InterfaceC0718m
                /* JADX INFO: renamed from: a */
/* 45 */        public final void mo350a(C0706b c0706b) {
/* 46 */            if (Looper.myLooper() != Looper.getMainLooper()) {
/* 47 */                throw new IllegalStateException("Event bus " + c0706b + " accessed from non-main thread " + Looper.myLooper());
                    }
                }
            }
