            package com.google.android.gms.internal;

            import android.app.Activity;
            import android.os.RemoteException;
            import com.google.ads.mediation.MediationAdapter;
            import com.google.ads.mediation.MediationBannerAdapter;
            import com.google.ads.mediation.MediationInterstitialAdapter;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.ads.mediation.NetworkExtras;
            import com.google.ads.mediation.admob.AdMobServerParameters;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import java.util.HashMap;
            import java.util.Iterator;
            import org.json.JSONObject;
            
            
            
            public final class BinderC0960az<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> extends InterfaceC0958ax.a {

                
                private final MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> f1197fr;

                
                private final NETWORK_EXTRAS f1198fs;

                public BinderC0960az(MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> mediationAdapter, NETWORK_EXTRAS network_extras) {
                    this.f1197fr = mediationAdapter;
                    this.f1198fs = network_extras;
                }

                
                private SERVER_PARAMETERS m1286a(String str, int i) throws RemoteException {
                    HashMap map;
                    try {
                        if (str != null) {
                            JSONObject jSONObject = new JSONObject(str);
                            HashMap map2 = new HashMap(jSONObject.length());
                            Iterator<String> itKeys = jSONObject.keys();
                            while (itKeys.hasNext()) {
                                String next = itKeys.next();
                                map2.put(next, jSONObject.getString(next));
                            }
                            map = map2;
                        } else {
                            map = new HashMap(0);
                        }
                        Class<SERVER_PARAMETERS> serverParametersType = this.f1197fr.getServerParametersType();
                        SERVER_PARAMETERS server_parameters = null;
                        if (serverParametersType != null) {
                            SERVER_PARAMETERS server_parametersNewInstance = serverParametersType.newInstance();
                            server_parametersNewInstance.load(map);
                            server_parameters = server_parametersNewInstance;
                        }
                        if (server_parameters instanceof AdMobServerParameters) {
                            ((AdMobServerParameters) server_parameters).tagForChildDirectedTreatment = i;
                        }
                        return server_parameters;
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not get MediationServerParameters.", th);
                        throw new RemoteException();
                    }
                }

                
                @Override 
                
                public void mo1282a(InterfaceC0904b interfaceC0904b, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException {
                    if (!(this.f1197fr instanceof MediationInterstitialAdapter)) {
                        C1002cn.m1458q("MediationAdapter is not a MediationInterstitialAdapter: " + this.f1197fr.getClass().getCanonicalName());
                        throw new RemoteException();
                    }
                    C1002cn.m1454m("Requesting interstitial ad from adapter.");
                    try {
                        ((MediationInterstitialAdapter) this.f1197fr).requestInterstitialAd(new C0962ba(interfaceC0959ay), (Activity) BinderC0905c.m1165b(interfaceC0904b), m1286a(str, c1124v.tagForChildDirectedTreatment), C0963bb.m1294e(c1124v), this.f1198fs);
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not request interstitial ad from adapter.", th);
                        throw new RemoteException();
                    }
                }

                
                @Override 
                
                public void mo1283a(InterfaceC0904b interfaceC0904b, C1126x c1126x, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException {
                    if (!(this.f1197fr instanceof MediationBannerAdapter)) {
                        C1002cn.m1458q("MediationAdapter is not a MediationBannerAdapter: " + this.f1197fr.getClass().getCanonicalName());
                        throw new RemoteException();
                    }
                    C1002cn.m1454m("Requesting banner ad from adapter.");
                    try {
                        ((MediationBannerAdapter) this.f1197fr).requestBannerAd(new C0962ba(interfaceC0959ay), (Activity) BinderC0905c.m1165b(interfaceC0904b), m1286a(str, c1124v.tagForChildDirectedTreatment), C0963bb.m1292a(c1126x), C0963bb.m1294e(c1124v), this.f1198fs);
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not request banner ad from adapter.", th);
                        throw new RemoteException();
                    }
                }

                @Override 
                public void destroy() throws RemoteException {
                    try {
                        this.f1197fr.destroy();
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not destroy adapter.", th);
                        throw new RemoteException();
                    }
                }

                @Override 
                public InterfaceC0904b getView() throws RemoteException {
                    if (!(this.f1197fr instanceof MediationBannerAdapter)) {
                        C1002cn.m1458q("MediationAdapter is not a MediationBannerAdapter: " + this.f1197fr.getClass().getCanonicalName());
                        throw new RemoteException();
                    }
                    try {
                        return BinderC0905c.m1166g(((MediationBannerAdapter) this.f1197fr).getBannerView());
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not get banner view from adapter.", th);
                        throw new RemoteException();
                    }
                }

                @Override 
                public void showInterstitial() throws RemoteException {
                    if (!(this.f1197fr instanceof MediationInterstitialAdapter)) {
                        C1002cn.m1458q("MediationAdapter is not a MediationInterstitialAdapter: " + this.f1197fr.getClass().getCanonicalName());
                        throw new RemoteException();
                    }
                    C1002cn.m1454m("Showing interstitial from adapter.");
                    try {
                        ((MediationInterstitialAdapter) this.f1197fr).showInterstitial();
                    } catch (Throwable th) {
                        C1002cn.m1452b("Could not show interstitial from adapter.", th);
                        throw new RemoteException();
                    }
                }
            }
