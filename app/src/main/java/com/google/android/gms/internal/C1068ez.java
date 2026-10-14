            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.location.Location;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Looper;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.location.LocationClient;
            import com.google.android.gms.location.LocationListener;
            import com.google.android.gms.location.LocationRequest;
            import com.google.android.gms.location.LocationStatusCodes;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ez */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1068ez extends AbstractC1020de<InterfaceC1066ex> {

                /* JADX INFO: renamed from: oO */
                private final InterfaceC1072fc<InterfaceC1066ex> f1867oO;

                /* JADX INFO: renamed from: oU */
                private final C1067ey f1868oU;

                /* JADX INFO: renamed from: oV */
                private final String f1869oV;

                /* JADX INFO: renamed from: com.google.android.gms.internal.ez$a */
                private final class a extends AbstractC1020de<InterfaceC1066ex>.b<LocationClient.OnAddGeofencesResultListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1870iC;

                    /* JADX INFO: renamed from: oW */
                    private final String[] f1871oW;

                    public a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, int i, String[] strArr) {
                        super(onAddGeofencesResultListener);
                        this.f1870iC = LocationStatusCodes.m2192Z(i);
                        this.f1871oW = strArr;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener) {
                        if (onAddGeofencesResultListener != null) {
                            onAddGeofencesResultListener.onAddGeofencesResult(this.f1870iC, this.f1871oW);
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ez$b */
                private static final class b extends InterfaceC1065ew.a {

                    /* JADX INFO: renamed from: oY */
                    private LocationClient.OnAddGeofencesResultListener f1873oY;

                    /* JADX INFO: renamed from: oZ */
                    private LocationClient.OnRemoveGeofencesResultListener f1874oZ;

                    /* JADX INFO: renamed from: pa */
                    private C1068ez f1875pa;

                    public b(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, C1068ez c1068ez) {
                        this.f1873oY = onAddGeofencesResultListener;
                        this.f1874oZ = null;
                        this.f1875pa = c1068ez;
                    }

                    public b(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, C1068ez c1068ez) {
                        this.f1874oZ = onRemoveGeofencesResultListener;
                        this.f1873oY = null;
                        this.f1875pa = c1068ez;
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1065ew
                    public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) throws RemoteException {
                        if (this.f1875pa == null) {
                            Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                            return;
                        }
                        C1068ez c1068ez = this.f1875pa;
                        C1068ez c1068ez2 = this.f1875pa;
                        c1068ez2.getClass();
                        c1068ez.m1543a(c1068ez2.new a(this.f1873oY, statusCode, geofenceRequestIds));
                        this.f1875pa = null;
                        this.f1873oY = null;
                        this.f1874oZ = null;
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1065ew
                    public void onRemoveGeofencesByPendingIntentResult(int statusCode, PendingIntent pendingIntent) {
                        if (this.f1875pa == null) {
                            Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
                            return;
                        }
                        C1068ez c1068ez = this.f1875pa;
                        C1068ez c1068ez2 = this.f1875pa;
                        c1068ez2.getClass();
                        c1068ez.m1543a(c1068ez2.new d(1, this.f1874oZ, statusCode, pendingIntent));
                        this.f1875pa = null;
                        this.f1873oY = null;
                        this.f1874oZ = null;
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1065ew
                    public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] geofenceRequestIds) {
                        if (this.f1875pa == null) {
                            Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
                            return;
                        }
                        C1068ez c1068ez = this.f1875pa;
                        C1068ez c1068ez2 = this.f1875pa;
                        c1068ez2.getClass();
                        c1068ez.m1543a(c1068ez2.new d(2, this.f1874oZ, statusCode, geofenceRequestIds));
                        this.f1875pa = null;
                        this.f1873oY = null;
                        this.f1874oZ = null;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ez$c */
                private final class c implements InterfaceC1072fc<InterfaceC1066ex> {
                    private c() {
                    }

                    /* synthetic */ c(C1068ez c1068ez, Object anonymousClass1) {
                        this();
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1072fc
                    /* JADX INFO: renamed from: bc */
                    public void mo1913bc() {
                        C1068ez.m1910a(C1068ez.this);
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1072fc
                    /* JADX INFO: renamed from: bd */
                    public /* synthetic */ IInterface mo1914bd() {
                        return m1915cn();
                    }

                    /* JADX INFO: renamed from: cn */
                    public InterfaceC1066ex m1915cn() {
                        return (InterfaceC1066ex) C1068ez.m1911b(C1068ez.this);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ez$d */
                private final class d extends AbstractC1020de<InterfaceC1066ex>.b<LocationClient.OnRemoveGeofencesResultListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1877iC;
                    private final PendingIntent mPendingIntent;

                    /* JADX INFO: renamed from: oW */
                    private final String[] f1878oW;

                    /* JADX INFO: renamed from: pb */
                    private final int f1880pb;

                    public d(int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, PendingIntent pendingIntent) {
                        super(onRemoveGeofencesResultListener);
                        C1017db.m1525k(i == 1);
                        this.f1880pb = i;
                        this.f1877iC = LocationStatusCodes.m2192Z(i2);
                        this.mPendingIntent = pendingIntent;
                        this.f1878oW = null;
                    }

                    public d(int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, String[] strArr) {
                        super(onRemoveGeofencesResultListener);
                        C1017db.m1525k(i == 2);
                        this.f1880pb = i;
                        this.f1877iC = LocationStatusCodes.m2192Z(i2);
                        this.f1878oW = strArr;
                        this.mPendingIntent = null;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
                        if (onRemoveGeofencesResultListener != null) {
                            switch (this.f1880pb) {
                                case 1:
                                    onRemoveGeofencesResultListener.onRemoveGeofencesByPendingIntentResult(this.f1877iC, this.mPendingIntent);
                                    break;
                                case 2:
                                    onRemoveGeofencesResultListener.onRemoveGeofencesByRequestIdsResult(this.f1877iC, this.f1878oW);
                                    break;
                                default:
                                    Log.wtf("LocationClientImpl", "Unsupported action: " + this.f1880pb);
                                    break;
                            }
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                public C1068ez(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str) {
                    super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
                    this.f1867oO = new c(this, null);
                    this.f1868oU = new C1067ey(context, this.f1867oO);
                    this.f1869oV = str;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ void m1910a(C1068ez c1068ez) {
                    c1068ez.m1548bc();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ IInterface m1911b(C1068ez c1068ez) {
                    return c1068ez.m1549bd();
                }

                /* JADX INFO: renamed from: G */
                protected InterfaceC1066ex m1912G(IBinder iBinder) {
                    return InterfaceC1066ex.a.m1908F(iBinder);
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    Bundle bundle = new Bundle();
                    bundle.putString("client_name", this.f1869oV);
                    interfaceC1025dj.mo1590e(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), bundle);
                }

                public void addGeofences(List<C1070fa> geofences, PendingIntent pendingIntent, LocationClient.OnAddGeofencesResultListener listener) {
                    b bVar;
                    m1548bc();
                    C1028dm.m1604b(geofences != null && geofences.size() > 0, "At least one geofence must be specified.");
                    C1028dm.m1601a(pendingIntent, "PendingIntent must be specified.");
                    C1028dm.m1601a(listener, "OnAddGeofencesResultListener not provided.");
                    if (listener == null) {
                        bVar = null;
                    } else {
                        try {
                            bVar = new b(listener, this);
                        } catch (RemoteException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    m1549bd().mo1905a(geofences, pendingIntent, bVar, getContext().getPackageName());
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ag */
                protected String mo1366ag() {
                    return "com.google.android.location.internal.GoogleLocationManagerService.START";
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ah */
                protected String mo1367ah() {
                    return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
                }

                @Override // com.google.android.gms.internal.AbstractC1020de, com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    synchronized (this.f1868oU) {
                        try {
                            if (isConnected()) {
                                this.f1868oU.removeAllListeners();
                                this.f1868oU.m1909cm();
                            }
                            super.disconnect();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public Location getLastLocation() {
                    return this.f1868oU.getLastLocation();
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: p */
                protected /* synthetic */ IInterface mo1370p(IBinder iBinder) {
                    return m1912G(iBinder);
                }

                public void removeActivityUpdates(PendingIntent callbackIntent) {
                    m1548bc();
                    C1028dm.m1605e(callbackIntent);
                    try {
                        m1549bd().removeActivityUpdates(callbackIntent);
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void removeGeofences(PendingIntent pendingIntent, LocationClient.OnRemoveGeofencesResultListener listener) {
                    b bVar;
                    m1548bc();
                    C1028dm.m1601a(pendingIntent, "PendingIntent must be specified.");
                    C1028dm.m1601a(listener, "OnRemoveGeofencesResultListener not provided.");
                    if (listener == null) {
                        bVar = null;
                    } else {
                        try {
                            bVar = new b(listener, this);
                        } catch (RemoteException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    m1549bd().mo1899a(pendingIntent, bVar, getContext().getPackageName());
                }

                public void removeGeofences(List<String> geofenceRequestIds, LocationClient.OnRemoveGeofencesResultListener listener) {
                    b bVar;
                    m1548bc();
                    C1028dm.m1604b(geofenceRequestIds != null && geofenceRequestIds.size() > 0, "geofenceRequestIds can't be null nor empty.");
                    C1028dm.m1601a(listener, "OnRemoveGeofencesResultListener not provided.");
                    String[] strArr = (String[]) geofenceRequestIds.toArray(new String[0]);
                    if (listener == null) {
                        bVar = null;
                    } else {
                        try {
                            bVar = new b(listener, this);
                        } catch (RemoteException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    m1549bd().mo1906a(strArr, bVar, getContext().getPackageName());
                }

                public void removeLocationUpdates(PendingIntent callbackIntent) {
                    this.f1868oU.removeLocationUpdates(callbackIntent);
                }

                public void removeLocationUpdates(LocationListener listener) {
                    this.f1868oU.removeLocationUpdates(listener);
                }

                public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
                    m1548bc();
                    C1028dm.m1605e(callbackIntent);
                    C1028dm.m1604b(detectionIntervalMillis >= 0, "detectionIntervalMillis must be >= 0");
                    try {
                        m1549bd().mo1897a(detectionIntervalMillis, true, callbackIntent);
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
                    this.f1868oU.requestLocationUpdates(request, callbackIntent);
                }

                public void requestLocationUpdates(LocationRequest request, LocationListener listener) {
                    requestLocationUpdates(request, listener, null);
                }

                public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
                    synchronized (this.f1868oU) {
                        try {
                            this.f1868oU.requestLocationUpdates(request, listener, looper);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public void setMockLocation(Location mockLocation) {
                    this.f1868oU.setMockLocation(mockLocation);
                }

                public void setMockMode(boolean isMockMode) {
                    this.f1868oU.setMockMode(isMockMode);
                }
            }
