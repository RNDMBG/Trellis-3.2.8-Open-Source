            package com.google.android.gms.maps;

            import android.content.Context;
            import android.os.RemoteException;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1173q;
            import com.google.android.gms.maps.internal.InterfaceC1159c;
            import com.google.android.gms.maps.model.BitmapDescriptorFactory;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            
            public final class MapsInitializer {
                private MapsInitializer() {
                }

                public static void initialize(Context context) throws GooglePlayServicesNotAvailableException {
                    C1028dm.m1605e(context);
                    InterfaceC1159c interfaceC1159cM2262u = C1173q.m2262u(context);
                    try {
                        CameraUpdateFactory.m2195a(interfaceC1159cM2262u.mo2239cG());
                        BitmapDescriptorFactory.m2267a(interfaceC1159cM2262u.mo2240cH());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
