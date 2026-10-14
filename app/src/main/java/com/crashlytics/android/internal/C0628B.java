            package com.crashlytics.android.internal;

            import android.os.Handler;
            import android.os.Looper;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.B */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public final class C0628B extends C0706b {

                /* JADX INFO: renamed from: b */
                private final Handler f210b;

                /* JADX INFO: renamed from: a */
/* 12 */        static /* synthetic */ void m101a(C0628B c0628b, Object obj) {
/* 13 */            super.mo102c(obj);
                }

/* 16 */        public C0628B(InterfaceC0718m interfaceC0718m) {
/* 17 */            super(interfaceC0718m);
/* 14 */            this.f210b = new Handler(Looper.getMainLooper());
                }

                @Override // com.crashlytics.android.internal.C0706b
                /* JADX INFO: renamed from: c */
/* 34 */        public final void mo102c(Object obj) {
/* 35 */            if (obj instanceof InterfaceC0725t) {
/* 36 */                InterfaceC0725t interfaceC0725t = (InterfaceC0725t) obj;
                        if (Looper.myLooper() != Looper.getMainLooper()) {
/* 36 */                    this.f210b.post(new RunnableC0629C(this, interfaceC0725t));
/* 42 */                    return;
                        } else {
                            super.mo102c(interfaceC0725t);
/* 42 */                    return;
                        }
                    }
/* 37 */            if ((obj instanceof InterfaceC0724s) || (obj instanceof C0711f)) {
/* 38 */                super.mo102c(obj);
/* 42 */                return;
                    }
/* 40 */            throw new IllegalArgumentException("Posted argument must implement Event interface");
                }
            }
