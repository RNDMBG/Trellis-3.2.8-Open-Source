            package com.google.ads.mediation.customevent;

            import android.app.Activity;
            import com.google.ads.mediation.MediationAdRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public interface CustomEventInterstitial extends CustomEvent {
                void requestInterstitialAd(CustomEventInterstitialListener customEventInterstitialListener, Activity activity, String str, String str2, MediationAdRequest mediationAdRequest, Object obj);

                void showInterstitial();
            }
