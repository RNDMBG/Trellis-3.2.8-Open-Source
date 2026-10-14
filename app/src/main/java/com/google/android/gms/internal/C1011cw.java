            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.appstate.AppState;
            import com.google.android.gms.appstate.AppStateBuffer;
            import com.google.android.gms.appstate.OnSignOutCompleteListener;
            import com.google.android.gms.appstate.OnStateDeletedListener;
            import com.google.android.gms.appstate.OnStateListLoadedListener;
            import com.google.android.gms.appstate.OnStateLoadedListener;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.Scopes;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cw */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1011cw extends AbstractC1020de<InterfaceC1013cy> {

                /* JADX INFO: renamed from: it */
                private final String f1630it;

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$a */
                final class a extends AbstractBinderC1010cv {

                    /* JADX INFO: renamed from: iA */
                    private final OnStateDeletedListener f1631iA;

                    public a(OnStateDeletedListener onStateDeletedListener) {
                        this.f1631iA = (OnStateDeletedListener) C1028dm.m1601a(onStateDeletedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1010cv, com.google.android.gms.internal.InterfaceC1012cx
                    public void onStateDeleted(int statusCode, int stateKey) {
                        C1011cw.this.m1543a(C1011cw.this.new b(this.f1631iA, statusCode, stateKey));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$b */
                final class b extends AbstractC1020de<InterfaceC1013cy>.b<OnStateDeletedListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1634iC;

                    /* JADX INFO: renamed from: iD */
                    private final int f1635iD;

                    public b(OnStateDeletedListener onStateDeletedListener, int i, int i2) {
                        super(onStateDeletedListener);
                        this.f1634iC = i;
                        this.f1635iD = i2;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnStateDeletedListener onStateDeletedListener) {
                        onStateDeletedListener.onStateDeleted(this.f1634iC, this.f1635iD);
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$c */
                final class c extends AbstractBinderC1010cv {

                    /* JADX INFO: renamed from: iE */
                    private final OnStateListLoadedListener f1637iE;

                    public c(OnStateListLoadedListener onStateListLoadedListener) {
                        this.f1637iE = (OnStateListLoadedListener) C1028dm.m1601a(onStateListLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1010cv, com.google.android.gms.internal.InterfaceC1012cx
                    /* JADX INFO: renamed from: a */
                    public void mo1497a(C0891d c0891d) {
                        C1011cw.this.m1543a(C1011cw.this.new d(this.f1637iE, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$d */
                final class d extends AbstractC1020de<InterfaceC1013cy>.c<OnStateListLoadedListener> {
                    public d(OnStateListLoadedListener onStateListLoadedListener, C0891d c0891d) {
                        super(onStateListLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnStateListLoadedListener onStateListLoadedListener, C0891d c0891d) {
                        onStateListLoadedListener.onStateListLoaded(c0891d.getStatusCode(), new AppStateBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$e */
                final class e extends AbstractBinderC1010cv {

                    /* JADX INFO: renamed from: iF */
                    private final OnStateLoadedListener f1640iF;

                    public e(OnStateLoadedListener onStateLoadedListener) {
                        this.f1640iF = (OnStateLoadedListener) C1028dm.m1601a(onStateLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1010cv, com.google.android.gms.internal.InterfaceC1012cx
                    /* JADX INFO: renamed from: a */
                    public void mo1496a(int i, C0891d c0891d) {
                        C1011cw.this.m1543a(C1011cw.this.new f(this.f1640iF, i, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$f */
                final class f extends AbstractC1020de<InterfaceC1013cy>.c<OnStateLoadedListener> {

                    /* JADX INFO: renamed from: iD */
                    private final int f1642iD;

                    public f(OnStateLoadedListener onStateLoadedListener, int i, C0891d c0891d) {
                        super(onStateLoadedListener, c0891d);
                        this.f1642iD = i;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnStateLoadedListener onStateLoadedListener, C0891d c0891d) {
                        byte[] localData;
                        String conflictVersion;
                        byte[] conflictData = null;
                        AppStateBuffer appStateBuffer = new AppStateBuffer(c0891d);
                        try {
                            if (appStateBuffer.getCount() > 0) {
                                AppState appState = appStateBuffer.get(0);
                                conflictVersion = appState.getConflictVersion();
                                localData = appState.getLocalData();
                                conflictData = appState.getConflictData();
                            } else {
                                localData = null;
                                conflictVersion = null;
                            }
                            appStateBuffer.close();
                            int statusCode = c0891d.getStatusCode();
                            if (statusCode == 2000) {
                                onStateLoadedListener.onStateConflict(this.f1642iD, conflictVersion, localData, conflictData);
                            } else {
                                onStateLoadedListener.onStateLoaded(statusCode, this.f1642iD, localData);
                            }
                        } catch (Throwable th) {
                            appStateBuffer.close();
                            throw th;
                        }
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$g */
                final class g extends AbstractBinderC1010cv {

                    /* JADX INFO: renamed from: iG */
                    private final OnSignOutCompleteListener f1644iG;

                    public g(OnSignOutCompleteListener onSignOutCompleteListener) {
                        this.f1644iG = (OnSignOutCompleteListener) C1028dm.m1601a(onSignOutCompleteListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1010cv, com.google.android.gms.internal.InterfaceC1012cx
                    public void onSignOutComplete() {
                        C1011cw.this.m1543a(C1011cw.this.new h(this.f1644iG));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cw$h */
                final class h extends AbstractC1020de<InterfaceC1013cy>.b<OnSignOutCompleteListener> {
                    public h(OnSignOutCompleteListener onSignOutCompleteListener) {
                        super(onSignOutCompleteListener);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnSignOutCompleteListener onSignOutCompleteListener) {
                        onSignOutCompleteListener.onSignOutComplete();
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                public C1011cw(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
                    super(context, connectionCallbacks, onConnectionFailedListener, strArr);
                    this.f1630it = (String) C1028dm.m1605e(str);
                }

                /* JADX INFO: renamed from: a */
                public void m1499a(OnStateLoadedListener onStateLoadedListener, int i, byte[] bArr) {
                    e eVar;
                    if (onStateLoadedListener == null) {
                        eVar = null;
                    } else {
                        try {
                            eVar = new e(onStateLoadedListener);
                        } catch (RemoteException e2) {
                            Log.w("AppStateClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1509a(eVar, i, bArr);
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    interfaceC1025dj.mo1584a(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.f1630it, m1544aY());
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1500a(String... strArr) {
                    boolean z = false;
                    for (String str : strArr) {
                        if (str.equals(Scopes.APP_STATE)) {
                            z = true;
                        }
                    }
                    C1028dm.m1602a(z, String.format("AppStateClient requires %s to function.", Scopes.APP_STATE));
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ag */
                protected String mo1366ag() {
                    return "com.google.android.gms.appstate.service.START";
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ah */
                protected String mo1367ah() {
                    return "com.google.android.gms.appstate.internal.IAppStateService";
                }

                public void deleteState(OnStateDeletedListener listener, int stateKey) {
                    try {
                        m1549bd().mo1511b(new a(listener), stateKey);
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                    }
                }

                public int getMaxNumKeys() {
                    try {
                        return m1549bd().getMaxNumKeys();
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                        return 2;
                    }
                }

                public int getMaxStateSize() {
                    try {
                        return m1549bd().getMaxStateSize();
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                        return 2;
                    }
                }

                public void listStates(OnStateListLoadedListener listener) {
                    try {
                        m1549bd().mo1506a(new c(listener));
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                    }
                }

                public void loadState(OnStateLoadedListener listener, int stateKey) {
                    try {
                        m1549bd().mo1507a(new e(listener), stateKey);
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: p */
                protected /* synthetic */ IInterface mo1370p(IBinder iBinder) {
                    return m1501r(iBinder);
                }

                /* JADX INFO: renamed from: r */
                protected InterfaceC1013cy m1501r(IBinder iBinder) {
                    return InterfaceC1013cy.a.m1513t(iBinder);
                }

                public void resolveState(OnStateLoadedListener listener, int stateKey, String resolvedVersion, byte[] resolvedData) {
                    try {
                        m1549bd().mo1508a(new e(listener), stateKey, resolvedVersion, resolvedData);
                    } catch (RemoteException e2) {
                        Log.w("AppStateClient", "service died");
                    }
                }

                public void signOut(OnSignOutCompleteListener listener) {
                    g gVar;
                    if (listener == null) {
                        gVar = null;
                    } else {
                        try {
                            gVar = new g(listener);
                        } catch (RemoteException e2) {
                            Log.w("AppStateClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1510b(gVar);
                }
            }
