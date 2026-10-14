            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import com.google.android.gms.common.GooglePlayServicesClient;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bt */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0981bt extends AbstractC1020de<InterfaceC0986by> {

                /* JADX INFO: renamed from: gz */
                private final int f1492gz;

                public C0981bt(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, int i) {
                    super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
                    this.f1492gz = i;
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    interfaceC1025dj.mo1592g(dVar, this.f1492gz, getContext().getPackageName(), new Bundle());
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ag */
                protected String mo1366ag() {
                    return "com.google.android.gms.ads.service.START";
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ah */
                protected String mo1367ah() {
                    return "com.google.android.gms.ads.internal.request.IAdRequestService";
                }

                /* JADX INFO: renamed from: ai */
                public InterfaceC0986by m1368ai() {
                    return (InterfaceC0986by) super.m1549bd();
                }

                /* JADX INFO: renamed from: o */
                protected InterfaceC0986by m1369o(IBinder iBinder) {
                    return InterfaceC0986by.a.m1378q(iBinder);
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: p */
                protected /* synthetic */ IInterface mo1370p(IBinder iBinder) {
                    return m1369o(iBinder);
                }
            }
