            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.C */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    final class RunnableC0629C implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ InterfaceC0725t f211a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ C0628B f212b;

/* 47 */        RunnableC0629C(C0628B c0628b, InterfaceC0725t interfaceC0725t) {
/* 48 */            this.f212b = c0628b;
                    this.f211a = interfaceC0725t;
                }

                @Override // java.lang.Runnable
/* 50 */        public final void run() {
/* 51 */            C0628B.m101a(this.f212b, this.f211a);
                }
            }
