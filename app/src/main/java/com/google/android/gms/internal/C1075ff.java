            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.panorama.PanoramaClient;
            
            
            
            public class C1075ff extends AbstractC1020de<InterfaceC1074fe> {

                
                final class a extends AbstractC1020de<InterfaceC1074fe>.b<PanoramaClient.InterfaceC1196a> {

                    
                    public final ConnectionResult f1902qV;

                    
                    public final Intent f1903qW;
                    public final int type;

                    public a(PanoramaClient.InterfaceC1196a interfaceC1196a, ConnectionResult connectionResult, int i, Intent intent) {
                        super(interfaceC1196a);
                        this.f1902qV = connectionResult;
                        this.type = i;
                        this.f1903qW = intent;
                    }

                    
                    protected void a2(PanoramaClient.InterfaceC1196a interfaceC1196a) {
                        if (interfaceC1196a != null) {
                            interfaceC1196a.m2329a(this.f1902qV, this.type, this.f1903qW);
                        }
                    }

                    @Override 
                    
                    protected void mo1503aF() {
                    }
                }

                
                final class b extends InterfaceC1073fd.a {

                    
                    private final PanoramaClient.InterfaceC1196a f1906qY = null;

                    
                    private final PanoramaClient.OnPanoramaInfoLoadedListener f1907qZ;

                    
                    private final Uri f1908ra;

                    public b(PanoramaClient.OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri) {
                        this.f1907qZ = onPanoramaInfoLoadedListener;
                        this.f1908ra = uri;
                    }

                    @Override 
                    
                    public void mo1939a(int i, Bundle bundle, int i2, Intent intent) {
                        if (this.f1908ra != null) {
                            C1075ff.this.getContext().revokeUriPermission(this.f1908ra, 1);
                        }
                        ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null);
                        if (this.f1906qY != null) {
                            C1075ff.this.m1543a(C1075ff.this.new a(this.f1906qY, connectionResult, i2, intent));
                        } else {
                            C1075ff.this.m1543a(C1075ff.this.new c(this.f1907qZ, connectionResult, intent));
                        }
                    }
                }

                
                final class c extends AbstractC1020de<InterfaceC1074fe>.b<PanoramaClient.OnPanoramaInfoLoadedListener> {

                    
                    private final ConnectionResult f1909qV;

                    
                    private final Intent f1910qW;

                    public c(PanoramaClient.OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, ConnectionResult connectionResult, Intent intent) {
                        super(onPanoramaInfoLoadedListener);
                        this.f1909qV = connectionResult;
                        this.f1910qW = intent;
                    }

                    
                    protected void a2(PanoramaClient.OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener) {
                        if (onPanoramaInfoLoadedListener != null) {
                            onPanoramaInfoLoadedListener.onPanoramaInfoLoaded(this.f1909qV, this.f1910qW);
                        }
                    }

                    @Override 
                    
                    protected void mo1503aF() {
                    }
                }

                public C1075ff(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
                    super(context, connectionCallbacks, onConnectionFailedListener, (String[]) null);
                }

                @Override 
                
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    interfaceC1025dj.mo1583a(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), new Bundle());
                }

                
                public void m1943a(b bVar, Uri uri, Bundle bundle, boolean z) {
                    m1548bc();
                    if (z) {
                        getContext().grantUriPermission(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, uri, 1);
                    }
                    try {
                        m1549bd().mo1941a(bVar, uri, bundle, z);
                    } catch (RemoteException e) {
                        bVar.mo1939a(8, null, 0, null);
                    }
                }

                
                public void m1944a(PanoramaClient.OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri, boolean z) {
                    m1943a(new b(onPanoramaInfoLoadedListener, z ? uri : null), uri, null, z);
                }

                @Override 
                
                protected String mo1366ag() {
                    return "com.google.android.gms.panorama.service.START";
                }

                @Override 
                
                protected String mo1367ah() {
                    return "com.google.android.gms.panorama.internal.IPanoramaService";
                }

                
                public InterfaceC1074fe m1945am(IBinder iBinder) {
                    return InterfaceC1074fe.a.m1942al(iBinder);
                }

                @Override 
                
                public  IInterface mo1370p(IBinder iBinder) {
                    return m1945am(iBinder);
                }
            }
