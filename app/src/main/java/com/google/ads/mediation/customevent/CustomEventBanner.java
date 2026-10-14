            package com.google.ads.mediation.customevent;

            import android.app.Activity;
            import com.google.ads.AdSize;
            import com.google.ads.mediation.MediationAdRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public interface CustomEventBanner extends CustomEvent {
                void requestBannerAd(CustomEventBannerListener customEventBannerListener, Activity activity, String str, String str2, AdSize adSize, MediationAdRequest mediationAdRequest, Object obj);
            }
