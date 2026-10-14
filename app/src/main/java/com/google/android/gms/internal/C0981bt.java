            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import com.google.android.gms.common.GooglePlayServicesClient;
            
            
            
            public class C0981bt extends AbstractC1020de<InterfaceC0986by> {

                
                private final int f1492gz;

                public C0981bt(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, int i) {
                    super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
                    this.f1492gz = i;
                }

                @Override 
                
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    interfaceC1025dj.mo1592g(dVar, this.f1492gz, getContext().getPackageName(), new Bundle());
                }

                @Override 
                
                protected String mo1366ag() {
                    return "com.google.android.gms.ads.service.START";
                }

                @Override 
                
                protected String mo1367ah() {
                    return "com.google.android.gms.ads.internal.request.IAdRequestService";
                }

                
                public InterfaceC0986by m1368ai() {
                    return (InterfaceC0986by) super.m1549bd();
                }

                
                protected InterfaceC0986by m1369o(IBinder iBinder) {
                    return InterfaceC0986by.a.m1378q(iBinder);
                }

                @Override 
                
                protected  IInterface mo1370p(IBinder iBinder) {
                    return m1369o(iBinder);
                }
            }
