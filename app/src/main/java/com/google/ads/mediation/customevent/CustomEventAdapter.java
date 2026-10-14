            package com.google.ads.mediation.customevent;

            import android.app.Activity;
            import android.view.View;
            import com.google.ads.AdRequest;
            import com.google.ads.AdSize;
            import com.google.ads.mediation.MediationAdRequest;
            import com.google.ads.mediation.MediationBannerAdapter;
            import com.google.ads.mediation.MediationBannerListener;
            import com.google.ads.mediation.MediationInterstitialAdapter;
            import com.google.ads.mediation.MediationInterstitialListener;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.ads.mediation.NetworkExtras;
            import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
            import com.google.android.gms.internal.C1002cn;
            
            
            public final class CustomEventAdapter implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>, MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {

                
                private View f866m;

                
                private CustomEventBanner f867n;

                
                private CustomEventInterstitial f868o;

                
                private static final class C0848a implements CustomEventBannerListener {

                    
                    private final MediationBannerListener f869k;

                    
                    private final CustomEventAdapter f870p;

                    public C0848a(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
                        this.f870p = customEventAdapter;
                        this.f869k = mediationBannerListener;
                    }

                    @Override 
                    public void onClick() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f869k.onClick(this.f870p);
                    }

                    @Override 
                    public void onDismissScreen() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f869k.onDismissScreen(this.f870p);
                    }

                    @Override 
                    public void onFailedToReceiveAd() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f869k.onFailedToReceiveAd(this.f870p, AdRequest.ErrorCode.NO_FILL);
                    }

                    @Override 
                    public void onLeaveApplication() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f869k.onLeaveApplication(this.f870p);
                    }

                    @Override 
                    public void onPresentScreen() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f869k.onPresentScreen(this.f870p);
                    }

                    @Override 
                    public void onReceivedAd(View view) {
                        C1002cn.m1454m("Custom event adapter called onReceivedAd.");
                        CustomEventAdapter.m981a(this.f870p, view);
                        this.f869k.onReceivedAd(this.f870p);
                    }
                }

                
                private class C0849b implements CustomEventInterstitialListener {

                    
                    private final MediationInterstitialListener f871l;

                    
                    private final CustomEventAdapter f872p;

                    public C0849b(CustomEventAdapter customEventAdapter, MediationInterstitialListener mediationInterstitialListener) {
                        this.f872p = customEventAdapter;
                        this.f871l = mediationInterstitialListener;
                    }

                    @Override 
                    public void onDismissScreen() {
                        C1002cn.m1454m("Custom event adapter called onDismissScreen.");
                        this.f871l.onDismissScreen(this.f872p);
                    }

                    @Override 
                    public void onFailedToReceiveAd() {
                        C1002cn.m1454m("Custom event adapter called onFailedToReceiveAd.");
                        this.f871l.onFailedToReceiveAd(this.f872p, AdRequest.ErrorCode.NO_FILL);
                    }

                    @Override 
                    public void onLeaveApplication() {
                        C1002cn.m1454m("Custom event adapter called onLeaveApplication.");
                        this.f871l.onLeaveApplication(this.f872p);
                    }

                    @Override 
                    public void onPresentScreen() {
                        C1002cn.m1454m("Custom event adapter called onPresentScreen.");
                        this.f871l.onPresentScreen(this.f872p);
                    }

                    @Override 
                    public void onReceivedAd() {
                        C1002cn.m1454m("Custom event adapter called onReceivedAd.");
                        this.f871l.onReceivedAd(CustomEventAdapter.this);
                    }
                }

                
                private static <T> T m979a(String str) {
                    try {
                        return (T) Class.forName(str).newInstance();
                    } catch (Throwable th) {
                        C1002cn.m1458q("Could not instantiate custom event adapter: " + str + ". " + th.getMessage());
                        return null;
                    }
                }

                
                private void m980a(View view) {
                    this.f866m = view;
                }

                
                static  void m981a(CustomEventAdapter customEventAdapter, View view) {
                    customEventAdapter.m980a(view);
                }

                @Override 
                public void destroy() {
                    if (this.f867n != null) {
                        this.f867n.destroy();
                    }
                    if (this.f868o != null) {
                        this.f868o.destroy();
                    }
                }

                @Override 
                public Class<CustomEventExtras> getAdditionalParametersType() {
                    return CustomEventExtras.class;
                }

                @Override 
                public View getBannerView() {
                    return this.f866m;
                }

                @Override 
                public Class<CustomEventServerParameters> getServerParametersType() {
                    return CustomEventServerParameters.class;
                }


                public void requestBannerAd(MediationBannerListener listener, Activity activity, CustomEventServerParameters serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
                    this.f867n = (CustomEventBanner) m979a(serverParameters.className);
                    if (this.f867n == null) {
                        listener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
                    } else {
                        this.f867n.requestBannerAd(new C0848a(this, listener), activity, serverParameters.label, serverParameters.parameter, adSize, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(serverParameters.label));
                    }
                }


                public void requestInterstitialAd(MediationInterstitialListener listener, Activity activity, CustomEventServerParameters serverParameters, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
                    this.f868o = (CustomEventInterstitial) m979a(serverParameters.className);
                    if (this.f868o == null) {
                        listener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
                    } else {
                        this.f868o.requestInterstitialAd(new C0849b(this, listener), activity, serverParameters.label, serverParameters.parameter, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(serverParameters.label));
                    }
                }

                @Override 
                public void showInterstitial() {
                    this.f868o.showInterstitial();
                }
            }
