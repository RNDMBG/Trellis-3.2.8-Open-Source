            package com.google.ads.mediation.admob;

            import android.app.Activity;
            import android.content.Context;
            import android.os.Bundle;
            import android.view.View;
            import com.google.ads.AdSize;
            import com.google.ads.mediation.MediationAdRequest;
            import com.google.ads.mediation.MediationBannerAdapter;
            import com.google.ads.mediation.MediationBannerListener;
            import com.google.ads.mediation.MediationInterstitialAdapter;
            import com.google.ads.mediation.MediationInterstitialListener;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.ads.mediation.NetworkExtras;
            import com.google.android.gms.ads.AdListener;
            import com.google.android.gms.ads.AdRequest;
            import com.google.android.gms.ads.AdView;
            import com.google.android.gms.ads.InterstitialAd;
            import com.google.android.gms.ads.mediation.admob.AdMobExtras;
            import com.google.android.gms.internal.C0963bb;
            import com.google.android.gms.internal.C1001cm;
            import java.util.Date;
            import java.util.Iterator;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdMobAdapter implements MediationBannerAdapter<AdMobExtras, AdMobServerParameters>, MediationInterstitialAdapter<AdMobExtras, AdMobServerParameters> {

                /* JADX INFO: renamed from: h */
                private AdView f860h;

                /* JADX INFO: renamed from: i */
                private InterstitialAd f861i;

                /* JADX INFO: renamed from: com.google.ads.mediation.admob.AdMobAdapter$a */
                private static final class C0846a extends AdListener {

                    /* JADX INFO: renamed from: j */
                    private final AdMobAdapter f862j;

                    /* JADX INFO: renamed from: k */
                    private final MediationBannerListener f863k;

                    public C0846a(AdMobAdapter adMobAdapter, MediationBannerListener mediationBannerListener) {
                        this.f862j = adMobAdapter;
                        this.f863k = mediationBannerListener;
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdClosed() {
                        this.f863k.onDismissScreen(this.f862j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdFailedToLoad(int errorCode) {
                        this.f863k.onFailedToReceiveAd(this.f862j, C0963bb.m1295f(errorCode));
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdLeftApplication() {
                        this.f863k.onLeaveApplication(this.f862j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdLoaded() {
                        this.f863k.onReceivedAd(this.f862j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdOpened() {
                        this.f863k.onClick(this.f862j);
                        this.f863k.onPresentScreen(this.f862j);
                    }
                }

                /* JADX INFO: renamed from: com.google.ads.mediation.admob.AdMobAdapter$b */
                private static final class C0847b extends AdListener {

                    /* JADX INFO: renamed from: j */
                    private final AdMobAdapter f864j;

                    /* JADX INFO: renamed from: l */
                    private final MediationInterstitialListener f865l;

                    public C0847b(AdMobAdapter adMobAdapter, MediationInterstitialListener mediationInterstitialListener) {
                        this.f864j = adMobAdapter;
                        this.f865l = mediationInterstitialListener;
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdClosed() {
                        this.f865l.onDismissScreen(this.f864j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdFailedToLoad(int errorCode) {
                        this.f865l.onFailedToReceiveAd(this.f864j, C0963bb.m1295f(errorCode));
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdLeftApplication() {
                        this.f865l.onLeaveApplication(this.f864j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdLoaded() {
                        this.f865l.onReceivedAd(this.f864j);
                    }

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdOpened() {
                        this.f865l.onPresentScreen(this.f864j);
                    }
                }

                /* JADX INFO: renamed from: a */
                private static AdRequest m978a(Context context, MediationAdRequest mediationAdRequest, AdMobExtras adMobExtras, AdMobServerParameters adMobServerParameters) {
                    AdRequest.Builder builder = new AdRequest.Builder();
                    Date birthday = mediationAdRequest.getBirthday();
                    if (birthday != null) {
                        builder.setBirthday(birthday);
                    }
                    com.google.ads.AdRequest.Gender gender = mediationAdRequest.getGender();
                    if (gender != null) {
                        builder.setGender(C0963bb.m1291a(gender));
                    }
                    Set<String> keywords = mediationAdRequest.getKeywords();
                    if (keywords != null) {
                        Iterator<String> it = keywords.iterator();
                        while (it.hasNext()) {
                            builder.addKeyword(it.next());
                        }
                    }
                    if (mediationAdRequest.isTesting()) {
                        builder.addTestDevice(C1001cm.m1449l(context));
                    }
                    if (adMobServerParameters.tagForChildDirectedTreatment != -1) {
                        builder.tagForChildDirectedTreatment(adMobServerParameters.tagForChildDirectedTreatment == 1);
                    }
                    if (adMobExtras == null) {
                        adMobExtras = new AdMobExtras(new Bundle());
                    }
                    Bundle extras = adMobExtras.getExtras();
                    extras.putInt("gw", 1);
                    extras.putString("mad_hac", adMobServerParameters.allowHouseAds);
                    extras.putBoolean("_noRefresh", true);
                    builder.addNetworkExtras(adMobExtras);
                    return builder.build();
                }

                @Override // com.google.ads.mediation.MediationAdapter
                public void destroy() {
                    if (this.f860h != null) {
                        this.f860h.destroy();
                        this.f860h = null;
                    }
                    if (this.f861i != null) {
                        this.f861i = null;
                    }
                }

                @Override // com.google.ads.mediation.MediationAdapter
                public Class<AdMobExtras> getAdditionalParametersType() {
                    return AdMobExtras.class;
                }

                @Override // com.google.ads.mediation.MediationBannerAdapter
                public View getBannerView() {
                    return this.f860h;
                }

                @Override // com.google.ads.mediation.MediationAdapter
                public Class<AdMobServerParameters> getServerParametersType() {
                    return AdMobServerParameters.class;
                }


                public void requestBannerAd(MediationBannerListener bannerListener, Activity activity, AdMobServerParameters serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, AdMobExtras extras) {
                    this.f860h = new AdView(activity);
                    this.f860h.setAdSize(new com.google.android.gms.ads.AdSize(adSize.getWidth(), adSize.getHeight()));
                    this.f860h.setAdUnitId(serverParameters.adUnitId);
                    this.f860h.setAdListener(new C0846a(this, bannerListener));
                    this.f860h.loadAd(m978a(activity, mediationAdRequest, extras, serverParameters));
                }


                public void requestInterstitialAd(MediationInterstitialListener interstitialListener, Activity activity, AdMobServerParameters serverParameters, MediationAdRequest mediationAdRequest, AdMobExtras extras) {
                    this.f861i = new InterstitialAd(activity);
                    this.f861i.setAdUnitId(serverParameters.adUnitId);
                    this.f861i.setAdListener(new C0847b(this, interstitialListener));
                    this.f861i.loadAd(m978a(activity, mediationAdRequest, extras, serverParameters));
                }

                @Override // com.google.ads.mediation.MediationInterstitialAdapter
                public void showInterstitial() {
                    this.f861i.show();
                }
            }
