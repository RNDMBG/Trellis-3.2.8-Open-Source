            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import co.vine.android.client.VineAPI;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.plus.PlusClient;
            import com.google.android.gms.plus.model.moments.Moment;
            import com.google.android.gms.plus.model.moments.MomentBuffer;
            import com.google.android.gms.plus.model.people.Person;
            import com.google.android.gms.plus.model.people.PersonBuffer;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            
            
            
            public class C1081fl extends AbstractC1020de<InterfaceC1080fk> implements GooglePlayServicesClient {

                
                private Person f1916ro;

                
                private C1083fn f1917rp;

                
                final class a extends AbstractBinderC1076fg {

                    
                    private final PlusClient.OnMomentsLoadedListener f1918rq;

                    public a(PlusClient.OnMomentsLoadedListener onMomentsLoadedListener) {
                        this.f1918rq = onMomentsLoadedListener;
                    }

                    @Override 
                    
                    public void mo1952a(C0891d c0891d, String str, String str2) {
                        C0891d c0891d2;
                        ConnectionResult connectionResult = new ConnectionResult(c0891d.getStatusCode(), c0891d.m1042aM() != null ? (PendingIntent) c0891d.m1042aM().getParcelable("pendingIntent") : null);
                        if (connectionResult.isSuccess() || c0891d == null) {
                            c0891d2 = c0891d;
                        } else {
                            if (!c0891d.isClosed()) {
                                c0891d.close();
                            }
                            c0891d2 = null;
                        }
                        C1081fl.this.m1543a(C1081fl.this.new b(this.f1918rq, connectionResult, c0891d2, str, str2));
                    }
                }

                
                final class b extends AbstractC1020de<InterfaceC1080fk>.c<PlusClient.OnMomentsLoadedListener> {

                    
                    private final ConnectionResult f1921rs;

                    
                    private final String f1922rt;

                    
                    private final String f1923ru;

                    public b(PlusClient.OnMomentsLoadedListener onMomentsLoadedListener, ConnectionResult connectionResult, C0891d c0891d, String str, String str2) {
                        super(onMomentsLoadedListener, c0891d);
                        this.f1921rs = connectionResult;
                        this.f1922rt = str;
                        this.f1923ru = str2;
                    }

                    
                    protected void a2(PlusClient.OnMomentsLoadedListener onMomentsLoadedListener, C0891d c0891d) {
                        onMomentsLoadedListener.onMomentsLoaded(this.f1921rs, c0891d != null ? new MomentBuffer(c0891d) : null, this.f1922rt, this.f1923ru);
                    }

                }

                
                final class c extends AbstractBinderC1076fg {

                    
                    private final PlusClient.OnPeopleLoadedListener f1925rv;

                    public c(PlusClient.OnPeopleLoadedListener onPeopleLoadedListener) {
                        this.f1925rv = onPeopleLoadedListener;
                    }

                    @Override 
                    
                    public void mo1951a(C0891d c0891d, String str) {
                        C0891d c0891d2;
                        ConnectionResult connectionResult = new ConnectionResult(c0891d.getStatusCode(), c0891d.m1042aM() != null ? (PendingIntent) c0891d.m1042aM().getParcelable("pendingIntent") : null);
                        if (connectionResult.isSuccess() || c0891d == null) {
                            c0891d2 = c0891d;
                        } else {
                            if (!c0891d.isClosed()) {
                                c0891d.close();
                            }
                            c0891d2 = null;
                        }
                        C1081fl.this.m1543a(C1081fl.this.new d(this.f1925rv, connectionResult, c0891d2, str));
                    }
                }

                
                final class d extends AbstractC1020de<InterfaceC1080fk>.c<PlusClient.OnPeopleLoadedListener> {

                    
                    private final ConnectionResult f1927rs;

                    
                    private final String f1928rt;

                    public d(PlusClient.OnPeopleLoadedListener onPeopleLoadedListener, ConnectionResult connectionResult, C0891d c0891d, String str) {
                        super(onPeopleLoadedListener, c0891d);
                        this.f1927rs = connectionResult;
                        this.f1928rt = str;
                    }

                    
                    protected void a2(PlusClient.OnPeopleLoadedListener onPeopleLoadedListener, C0891d c0891d) {
                        onPeopleLoadedListener.onPeopleLoaded(this.f1927rs, c0891d != null ? new PersonBuffer(c0891d) : null, this.f1928rt);
                    }

                }

                
                final class e extends AbstractBinderC1076fg {

                    
                    private final PlusClient.OnAccessRevokedListener f1930rw;

                    public e(PlusClient.OnAccessRevokedListener onAccessRevokedListener) {
                        this.f1930rw = onAccessRevokedListener;
                    }

                    @Override 
                    
                    public void mo1953b(int i, Bundle bundle) {
                        C1081fl.this.m1543a(C1081fl.this.new f(this.f1930rw, new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null)));
                    }
                }

                
                final class f extends AbstractC1020de<InterfaceC1080fk>.b<PlusClient.OnAccessRevokedListener> {

                    
                    private final ConnectionResult f1932rs;

                    public f(PlusClient.OnAccessRevokedListener onAccessRevokedListener, ConnectionResult connectionResult) {
                        super(onAccessRevokedListener);
                        this.f1932rs = connectionResult;
                    }

                    
                    protected void a2(PlusClient.OnAccessRevokedListener onAccessRevokedListener) {
                        C1081fl.this.disconnect();
                        if (onAccessRevokedListener != null) {
                            onAccessRevokedListener.onAccessRevoked(this.f1932rs);
                        }
                    }

                    @Override 
                    
                    protected void mo1503aF() {
                    }
                }

                public C1081fl(Context context, C1083fn c1083fn, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
                    super(context, connectionCallbacks, onConnectionFailedListener, c1083fn.m1982cZ());
                    this.f1917rp = c1083fn;
                }

                
                public boolean m1975Y(String str) {
                    return Arrays.asList(m1544aY()).contains(str);
                }

                @Override 
                
                protected void mo1541a(int i, IBinder iBinder, Bundle bundle) {
                    if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
                        this.f1916ro = C1091fv.m2026e(bundle.getByteArray("loaded_person"));
                    }
                    super.mo1541a(i, iBinder, bundle);
                }

                @Override 
                
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    Bundle bundle = new Bundle();
                    bundle.putString("client_id", this.f1917rp.m1988df());
                    bundle.putStringArray("request_visible_actions", this.f1917rp.m1983da());
                    interfaceC1025dj.mo1585a(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.f1917rp.m1986dd(), this.f1917rp.m1985dc(), m1544aY(), this.f1917rp.getAccountName(), bundle);
                }

                
                public void m1976a(PlusClient.OnPeopleLoadedListener onPeopleLoadedListener, Collection<String> collection) {
                    m1548bc();
                    c cVar = new c(onPeopleLoadedListener);
                    try {
                        m1549bd().mo1966a(cVar, new ArrayList(collection));
                    } catch (RemoteException e2) {
                        cVar.mo1951a(C0891d.m1036r(8), null);
                    }
                }

                
                public void m1977a(PlusClient.OnPeopleLoadedListener onPeopleLoadedListener, String[] strArr) {
                    m1976a(onPeopleLoadedListener, Arrays.asList(strArr));
                }

                @Override 
                
                protected String mo1366ag() {
                    return "com.google.android.gms.plus.service.START";
                }

                @Override 
                
                protected String mo1367ah() {
                    return "com.google.android.gms.plus.internal.IPlusService";
                }

                
                protected InterfaceC1080fk m1978ar(IBinder iBinder) {
                    return InterfaceC1080fk.a.m1974aq(iBinder);
                }

                public void clearDefaultAccount() {
                    m1548bc();
                    try {
                        this.f1916ro = null;
                        m1549bd().clearDefaultAccount();
                    } catch (RemoteException e2) {
                        throw new IllegalStateException(e2);
                    }
                }

                public String getAccountName() {
                    m1548bc();
                    try {
                        return m1549bd().getAccountName();
                    } catch (RemoteException e2) {
                        throw new IllegalStateException(e2);
                    }
                }

                public Person getCurrentPerson() {
                    m1548bc();
                    return this.f1916ro;
                }

                public void loadMoments(PlusClient.OnMomentsLoadedListener listener) {
                    loadMoments(listener, 20, null, null, null, VineAPI.RESOURCE_ME);
                }

                public void loadMoments(PlusClient.OnMomentsLoadedListener listener, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
                    m1548bc();
                    a aVar = listener != null ? new a(listener) : null;
                    try {
                        m1549bd().mo1962a(aVar, maxResults, pageToken, targetUrl, type, userId);
                    } catch (RemoteException e2) {
                        aVar.mo1952a(C0891d.m1036r(8), (String) null, (String) null);
                    }
                }

                public void loadVisiblePeople(PlusClient.OnPeopleLoadedListener listener, int orderBy, String pageToken) {
                    m1548bc();
                    c cVar = new c(listener);
                    try {
                        m1549bd().mo1961a(cVar, 1, orderBy, -1, pageToken);
                    } catch (RemoteException e2) {
                        cVar.mo1951a(C0891d.m1036r(8), null);
                    }
                }

                public void loadVisiblePeople(PlusClient.OnPeopleLoadedListener listener, String pageToken) {
                    loadVisiblePeople(listener, 0, pageToken);
                }

                @Override 
                
                protected  IInterface mo1370p(IBinder iBinder) {
                    return m1978ar(iBinder);
                }

                public void removeMoment(String momentId) {
                    m1548bc();
                    try {
                        m1549bd().removeMoment(momentId);
                    } catch (RemoteException e2) {
                        throw new IllegalStateException(e2);
                    }
                }

                public void revokeAccessAndDisconnect(PlusClient.OnAccessRevokedListener listener) {
                    m1548bc();
                    clearDefaultAccount();
                    e eVar = new e(listener);
                    try {
                        m1549bd().mo1967b(eVar);
                    } catch (RemoteException e2) {
                        eVar.mo1953b(8, null);
                    }
                }

                public void writeMoment(Moment moment) {
                    m1548bc();
                    try {
                        m1549bd().mo1959a(C1045ec.m1705a((C1088fs) moment));
                    } catch (RemoteException e2) {
                        throw new IllegalStateException(e2);
                    }
                }
            }
