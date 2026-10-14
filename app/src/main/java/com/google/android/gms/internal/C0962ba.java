            package com.google.android.gms.internal;

            import android.os.RemoteException;
            import com.google.ads.AdRequest;
            import com.google.ads.mediation.MediationBannerAdapter;
            import com.google.ads.mediation.MediationBannerListener;
            import com.google.ads.mediation.MediationInterstitialAdapter;
            import com.google.ads.mediation.MediationInterstitialListener;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.ads.mediation.NetworkExtras;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ba */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0962ba<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {

                /* JADX INFO: renamed from: ft */
                private final InterfaceC0959ay f1407ft;

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).mo1263y();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClicked.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$10, reason: invalid class name */
                class AnonymousClass10 implements Runnable {

                    /* JADX INFO: renamed from: fv */
                    final /* synthetic */ AdRequest.ErrorCode f1410fv;

                    AnonymousClass10(AdRequest.ErrorCode errorCode) {
                        this.f1410fv = errorCode;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdFailedToLoad(C0963bb.m1290a(this.f1410fv));
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdFailedToLoad.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$11, reason: invalid class name */
                class AnonymousClass11 implements Runnable {
                    AnonymousClass11() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdLeftApplication();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLeftApplication.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$2, reason: invalid class name */
                class AnonymousClass2 implements Runnable {
                    AnonymousClass2() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdOpened();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdOpened.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$3, reason: invalid class name */
                class AnonymousClass3 implements Runnable {
                    AnonymousClass3() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdLoaded();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLoaded.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$4, reason: invalid class name */
                class AnonymousClass4 implements Runnable {
                    AnonymousClass4() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdClosed();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClosed.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$5, reason: invalid class name */
                class AnonymousClass5 implements Runnable {

                    /* JADX INFO: renamed from: fv */
                    final /* synthetic */ AdRequest.ErrorCode f1416fv;

                    AnonymousClass5(AdRequest.ErrorCode errorCode) {
                        this.f1416fv = errorCode;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdFailedToLoad(C0963bb.m1290a(this.f1416fv));
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdFailedToLoad.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$6, reason: invalid class name */
                class AnonymousClass6 implements Runnable {
                    AnonymousClass6() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdLeftApplication();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLeftApplication.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$7, reason: invalid class name */
                class AnonymousClass7 implements Runnable {
                    AnonymousClass7() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdOpened();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdOpened.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$8, reason: invalid class name */
                class AnonymousClass8 implements Runnable {
                    AnonymousClass8() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdLoaded();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLoaded.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ba$9, reason: invalid class name */
                class AnonymousClass9 implements Runnable {
                    AnonymousClass9() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            C0962ba.m1289a(C0962ba.this).onAdClosed();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClosed.", e);
                        }
                    }
                }

                public C0962ba(InterfaceC0959ay interfaceC0959ay) {
                    this.f1407ft = interfaceC0959ay;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ InterfaceC0959ay m1289a(C0962ba c0962ba) {
                    return c0962ba.f1407ft;
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onClick(MediationBannerAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onClick.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onClick must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass1());
                    } else {
                        try {
                            this.f1407ft.mo1263y();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClicked.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onDismissScreen(MediationBannerAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onDismissScreen.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onDismissScreen must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass4());
                    } else {
                        try {
                            this.f1407ft.onAdClosed();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClosed.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationInterstitialListener
                public void onDismissScreen(MediationInterstitialAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onDismissScreen.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onDismissScreen must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass9());
                    } else {
                        try {
                            this.f1407ft.onAdClosed();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdClosed.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> adapter, AdRequest.ErrorCode errorCode) {
                    C1002cn.m1454m("Adapter called onFailedToReceiveAd with error. " + errorCode);
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onFailedToReceiveAd must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass5(errorCode));
                    } else {
                        try {
                            this.f1407ft.onAdFailedToLoad(C0963bb.m1290a(errorCode));
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdFailedToLoad.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationInterstitialListener
                public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> adapter, AdRequest.ErrorCode errorCode) {
                    C1002cn.m1454m("Adapter called onFailedToReceiveAd with error " + errorCode + ".");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onFailedToReceiveAd must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass10(errorCode));
                    } else {
                        try {
                            this.f1407ft.onAdFailedToLoad(C0963bb.m1290a(errorCode));
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdFailedToLoad.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onLeaveApplication(MediationBannerAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onLeaveApplication.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onLeaveApplication must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass6());
                    } else {
                        try {
                            this.f1407ft.onAdLeftApplication();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLeftApplication.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationInterstitialListener
                public void onLeaveApplication(MediationInterstitialAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onLeaveApplication.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onLeaveApplication must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass11());
                    } else {
                        try {
                            this.f1407ft.onAdLeftApplication();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLeftApplication.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onPresentScreen(MediationBannerAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onPresentScreen.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onPresentScreen must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass7());
                    } else {
                        try {
                            this.f1407ft.onAdOpened();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdOpened.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationInterstitialListener
                public void onPresentScreen(MediationInterstitialAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onPresentScreen.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onPresentScreen must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass2());
                    } else {
                        try {
                            this.f1407ft.onAdOpened();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdOpened.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationBannerListener
                public void onReceivedAd(MediationBannerAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onReceivedAd.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onReceivedAd must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass8());
                    } else {
                        try {
                            this.f1407ft.onAdLoaded();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLoaded.", e);
                        }
                    }
                }

                @Override // com.google.ads.mediation.MediationInterstitialListener
                public void onReceivedAd(MediationInterstitialAdapter<?, ?> adapter) {
                    C1002cn.m1454m("Adapter called onReceivedAd.");
                    if (!C1001cm.m1447ar()) {
                        C1002cn.m1458q("onReceivedAd must be called on the main UI thread.");
                        C1001cm.f1595hO.post(new AnonymousClass3());
                    } else {
                        try {
                            this.f1407ft.onAdLoaded();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call onAdLoaded.", e);
                        }
                    }
                }
            }
