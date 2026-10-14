            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.am */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    final class C0692am extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Runnable f380a;

/* 40 */        C0692am(ThreadFactoryC0691al threadFactoryC0691al, Runnable runnable) {
/* 41 */            this.f380a = runnable;
                }

                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 43 */        public final void mo38a() {
/* 44 */            this.f380a.run();
                }
            }
