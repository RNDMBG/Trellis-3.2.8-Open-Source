            package com.google.android.gms.internal;

            import android.os.RemoteException;
            import com.google.ads.mediation.MediationAdapter;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.android.gms.ads.mediation.NetworkExtras;
            import java.util.Map;
            
            
            
            public final class BinderC0956av extends InterfaceC0957aw.a {

                
                private Map<Class<? extends NetworkExtras>, NetworkExtras> f1193fq;

                
                private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> InterfaceC0958ax m1278h(String str) throws RemoteException {
                    try {
                        MediationAdapter mediationAdapter = (MediationAdapter) Class.forName(str).newInstance();
                        return new BinderC0960az(mediationAdapter, (com.google.ads.mediation.NetworkExtras) this.f1193fq.get(mediationAdapter.getAdditionalParametersType()));
                    } catch (Throwable th) {
                        C1002cn.m1458q("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
                        throw new RemoteException();
                    }
                }

                
                public void m1279c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
                    this.f1193fq = map;
                }

                @Override 
                
                public InterfaceC0958ax mo1280g(String str) throws RemoteException {
                    return m1278h(str);
                }
            }
