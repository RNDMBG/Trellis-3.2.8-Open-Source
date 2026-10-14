            package com.google.ads.mediation;

            import com.google.ads.AdRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public interface MediationBannerListener {
                void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter);

                void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter);

                void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, AdRequest.ErrorCode errorCode);

                void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter);

                void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter);

                void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter);
            }
