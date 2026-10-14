            package com.google.android.gms.ads;

            import android.content.Context;
            import android.os.RemoteException;
            import com.google.android.gms.internal.BinderC0956av;
            import com.google.android.gms.internal.BinderC1122t;
            import com.google.android.gms.internal.BinderC1128z;
            import com.google.android.gms.internal.C1002cn;
            import com.google.android.gms.internal.C1123u;
            import com.google.android.gms.internal.C1124v;
            import com.google.android.gms.internal.C1126x;
            import com.google.android.gms.internal.InterfaceC0937ac;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class InterstitialAd {

                /* JADX INFO: renamed from: dS */
                private final BinderC0956av f901dS = new BinderC0956av();

                /* JADX INFO: renamed from: dT */
                private AdListener f902dT;

                /* JADX INFO: renamed from: dU */
                private InterfaceC0937ac f903dU;

                /* JADX INFO: renamed from: dV */
                private String f904dV;

                /* JADX INFO: renamed from: dW */
                private InterfaceC0883a f905dW;
                private final Context mContext;

                public InterstitialAd(Context context) {
                    this.mContext = context;
                }

                /* JADX INFO: renamed from: c */
                private void m993c(String str) throws RemoteException {
                    if (this.f904dV == null) {
                        m994d(str);
                    }
                    this.f903dU = C1123u.m2172a(this.mContext, new C1126x(), this.f904dV, this.f901dS);
                    if (this.f902dT != null) {
                        this.f903dU.mo1240a(new BinderC1122t(this.f902dT));
                    }
                    if (this.f905dW != null) {
                        this.f903dU.mo1241a(new BinderC1128z(this.f905dW));
                    }
                }

                /* JADX INFO: renamed from: d */
                private void m994d(String str) {
                    if (this.f903dU == null) {
                        throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + str + " is called.");
                    }
                }

                public AdListener getAdListener() {
                    return this.f902dT;
                }

                public String getAdUnitId() {
                    return this.f904dV;
                }

                public boolean isLoaded() {
                    try {
                        if (this.f903dU == null) {
                            return false;
                        }
                        return this.f903dU.isReady();
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to check if ad is ready.", e);
                        return false;
                    }
                }

                public void loadAd(AdRequest adRequest) {
                    try {
                        if (this.f903dU == null) {
                            m993c("loadAd");
                        }
                        if (this.f903dU.mo1242a(new C1124v(this.mContext, adRequest))) {
                            this.f901dS.m1279c(adRequest.m982v());
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to load ad.", e);
                    }
                }

                public void setAdListener(AdListener adListener) {
                    try {
                        this.f902dT = adListener;
                        if (this.f903dU != null) {
                            this.f903dU.mo1240a(adListener != null ? new BinderC1122t(adListener) : null);
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to set the AdListener.", e);
                    }
                }

                public void setAdUnitId(String adUnitId) {
                    if (this.f904dV != null) {
                        throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
                    }
                    this.f904dV = adUnitId;
                }

                public void show() {
                    try {
                        m994d("show");
                        this.f903dU.showInterstitial();
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to show interstitial.", e);
                    }
                }
            }
