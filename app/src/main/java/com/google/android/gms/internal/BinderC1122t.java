            package com.google.android.gms.internal;

            import com.google.android.gms.ads.AdListener;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.t */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class BinderC1122t extends InterfaceC0936ab.a {

                /* JADX INFO: renamed from: dT */
                private final AdListener f2157dT;

                public BinderC1122t(AdListener adListener) {
                    this.f2157dT = adListener;
                }

                @Override // com.google.android.gms.internal.InterfaceC0936ab
                public void onAdClosed() {
                    this.f2157dT.onAdClosed();
                }

                @Override // com.google.android.gms.internal.InterfaceC0936ab
                public void onAdFailedToLoad(int errorCode) {
                    this.f2157dT.onAdFailedToLoad(errorCode);
                }

                @Override // com.google.android.gms.internal.InterfaceC0936ab
                public void onAdLeftApplication() {
                    this.f2157dT.onAdLeftApplication();
                }

                @Override // com.google.android.gms.internal.InterfaceC0936ab
                public void onAdLoaded() {
                    this.f2157dT.onAdLoaded();
                }

                @Override // com.google.android.gms.internal.InterfaceC0936ab
                public void onAdOpened() {
                    this.f2157dT.onAdOpened();
                }
            }
