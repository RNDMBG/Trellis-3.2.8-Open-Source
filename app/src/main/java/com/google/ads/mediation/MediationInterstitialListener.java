            package com.google.ads.mediation;

            import com.google.ads.AdRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public interface MediationInterstitialListener {
                void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter);

                void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, AdRequest.ErrorCode errorCode);

                void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter);

                void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter);

                void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter);
            }
