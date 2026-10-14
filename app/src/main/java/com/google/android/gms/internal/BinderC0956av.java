            package com.google.android.gms.internal;

            import android.os.RemoteException;
            import com.google.ads.mediation.MediationAdapter;
            import com.google.ads.mediation.MediationServerParameters;
            import com.google.android.gms.ads.mediation.NetworkExtras;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.av */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class BinderC0956av extends InterfaceC0957aw.a {

                /* JADX INFO: renamed from: fq */
                private Map<Class<? extends NetworkExtras>, NetworkExtras> f1193fq;

                /* JADX INFO: renamed from: h */
                private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> InterfaceC0958ax m1278h(String str) throws RemoteException {
                    try {
                        MediationAdapter mediationAdapter = (MediationAdapter) Class.forName(str).newInstance();
                        return new BinderC0960az(mediationAdapter, (com.google.ads.mediation.NetworkExtras) this.f1193fq.get(mediationAdapter.getAdditionalParametersType()));
                    } catch (Throwable th) {
                        C1002cn.m1458q("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
                        throw new RemoteException();
                    }
                }

                /* JADX INFO: renamed from: c */
                public void m1279c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
                    this.f1193fq = map;
                }

                @Override // com.google.android.gms.internal.InterfaceC0957aw
                /* JADX INFO: renamed from: g */
                public InterfaceC0958ax mo1280g(String str) throws RemoteException {
                    return m1278h(str);
                }
            }
