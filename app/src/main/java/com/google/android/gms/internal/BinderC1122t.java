            package com.google.android.gms.internal;

            import com.google.android.gms.ads.AdListener;
            
            
            
            public final class BinderC1122t extends InterfaceC0936ab.a {

                
                private final AdListener f2157dT;

                public BinderC1122t(AdListener adListener) {
                    this.f2157dT = adListener;
                }

                @Override 
                public void onAdClosed() {
                    this.f2157dT.onAdClosed();
                }

                @Override 
                public void onAdFailedToLoad(int errorCode) {
                    this.f2157dT.onAdFailedToLoad(errorCode);
                }

                @Override 
                public void onAdLeftApplication() {
                    this.f2157dT.onAdLeftApplication();
                }

                @Override 
                public void onAdLoaded() {
                    this.f2157dT.onAdLoaded();
                }

                @Override 
                public void onAdOpened() {
                    this.f2157dT.onAdOpened();
                }
            }
