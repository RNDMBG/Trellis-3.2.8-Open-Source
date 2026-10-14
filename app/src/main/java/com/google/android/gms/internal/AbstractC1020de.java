            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.ServiceConnection;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Looper;
            import android.os.Message;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.data.C0891d;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.de */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC1020de<T extends IInterface> implements GooglePlayServicesClient {

                /* JADX INFO: renamed from: kO */
                public static final String[] f1676kO = {"service_esmobile", "service_googleme"};

                /* JADX INFO: renamed from: is */
                private final String[] f1677is;

                /* JADX INFO: renamed from: kD */
                private T f1678kD;

                /* JADX INFO: renamed from: kH */
                private ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> f1682kH;

                /* JADX INFO: renamed from: kK */
                private AbstractC1020de<T>.e f1685kK;
                private final Context mContext;
                final Handler mHandler;

                /* JADX INFO: renamed from: kF */
                final ArrayList<GooglePlayServicesClient.ConnectionCallbacks> f1680kF = new ArrayList<>();

                /* JADX INFO: renamed from: kG */
                private boolean f1681kG = false;

                /* JADX INFO: renamed from: kI */
                private boolean f1683kI = false;

                /* JADX INFO: renamed from: kJ */
                private final ArrayList<AbstractC1020de<T>.b<?>> f1684kJ = new ArrayList<>();

                /* JADX INFO: renamed from: kL */
                boolean f1686kL = false;

                /* JADX INFO: renamed from: kM */
                boolean f1687kM = false;

                /* JADX INFO: renamed from: kN */
                private final Object f1688kN = new Object();

                /* JADX INFO: renamed from: kE */
                private ArrayList<GooglePlayServicesClient.ConnectionCallbacks> f1679kE = new ArrayList<>();

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$a */
                final class a extends Handler {
                    public a(Looper looper) {
                        super(looper);
                    }

                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        if (msg.what == 1 && !AbstractC1020de.this.isConnecting()) {
                            b bVar = (b) msg.obj;
                            bVar.mo1503aF();
                            bVar.unregister();
                            return;
                        }
                        synchronized (AbstractC1020de.m1535a(AbstractC1020de.this)) {
                            try {
                                AbstractC1020de.this.f1687kM = false;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (msg.what == 3) {
                            AbstractC1020de.this.mo1542a(new ConnectionResult(((Integer) msg.obj).intValue(), null));
                            return;
                        }
                        if (msg.what == 4) {
                            synchronized (AbstractC1020de.m1536b(AbstractC1020de.this)) {
                                try {
                                    if (AbstractC1020de.this.f1686kL && AbstractC1020de.this.isConnected() && AbstractC1020de.m1536b(AbstractC1020de.this).contains(msg.obj)) {
                                        ((GooglePlayServicesClient.ConnectionCallbacks) msg.obj).onConnected(AbstractC1020de.this.mo1546ba());
                                    }
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                            return;
                        }
                        if (msg.what == 2 && !AbstractC1020de.this.isConnected()) {
                            b bVar2 = (b) msg.obj;
                            bVar2.mo1503aF();
                            bVar2.unregister();
                        } else if (msg.what == 2 || msg.what == 1) {
                            ((b) msg.obj).mo1551be();
                        } else {
                            Log.wtf("GmsClient", "Don't know how to handle this message.");
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$b */
                protected abstract class b<TListener> {

                    /* JADX INFO: renamed from: kQ */
                    private boolean f1691kQ = false;
                    private TListener mListener;

                    public b(TListener tlistener) {
                        this.mListener = tlistener;
                    }

                    /* JADX INFO: renamed from: a */
                    protected abstract void mo1502a(TListener tlistener);

                    /* JADX INFO: renamed from: aF */
                    protected abstract void mo1503aF();

                    /* JADX INFO: renamed from: be */
                    public void mo1551be() {
                        TListener tlistener;
                        synchronized (this) {
                            try {
                                tlistener = this.mListener;
                                if (this.f1691kQ) {
                                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (tlistener != null) {
                            try {
                                mo1502a(tlistener);
                            } catch (RuntimeException e) {
                                mo1503aF();
                                throw e;
                            }
                        } else {
                            mo1503aF();
                        }
                        synchronized (this) {
                            try {
                                this.f1691kQ = true;
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                        unregister();
                    }

                    /* JADX INFO: renamed from: bf */
                    public void mo1552bf() {
                        synchronized (this) {
                            try {
                                this.mListener = null;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public void unregister() {
                        mo1552bf();
                        synchronized (AbstractC1020de.m1537c(AbstractC1020de.this)) {
                            try {
                                AbstractC1020de.m1537c(AbstractC1020de.this).remove(this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$c */
                public abstract class c<TListener> extends AbstractC1020de<T>.b<TListener> {

                    /* JADX INFO: renamed from: jf */
                    private final C0891d f1692jf;

                    public c(TListener tlistener, C0891d c0891d) {
                        super(tlistener);
                        this.f1692jf = c0891d;
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: a */
                    protected final void mo1502a(TListener tlistener) {
                        mo1504a(tlistener, this.f1692jf);
                    }

                    /* JADX INFO: renamed from: a */
                    protected abstract void mo1504a(TListener tlistener, C0891d c0891d);

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                        if (this.f1692jf != null) {
                            this.f1692jf.close();
                        }
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$d */
                public static final class d extends InterfaceC1024di.a {

                    /* JADX INFO: renamed from: kR */
                    private AbstractC1020de f1694kR;

                    public d(AbstractC1020de abstractC1020de) {
                        this.f1694kR = abstractC1020de;
                    }

                    @Override // com.google.android.gms.internal.InterfaceC1024di
                    /* JADX INFO: renamed from: b */
                    public void mo1553b(int i, IBinder iBinder, Bundle bundle) {
                        C1028dm.m1601a("onPostInitComplete can be called only once per call to getServiceFromBroker", this.f1694kR);
                        this.f1694kR.mo1541a(i, iBinder, bundle);
                        this.f1694kR = null;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$e */
                final class e implements ServiceConnection {
                    e() {
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName component, IBinder binder) {
                        AbstractC1020de.this.m1550u(binder);
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName component) {
                        AbstractC1020de.m1533a(AbstractC1020de.this, (IInterface) null);
                        AbstractC1020de.this.m1547bb();
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.de$f */
                protected final class f extends AbstractC1020de<T>.b<Boolean> {

                    /* JADX INFO: renamed from: kS */
                    public final Bundle f1697kS;

                    /* JADX INFO: renamed from: kT */
                    public final IBinder f1698kT;
                    public final int statusCode;

                    public f(int i, IBinder iBinder, Bundle bundle) {
                        super(true);
                        this.statusCode = i;
                        this.f1698kT = iBinder;
                        this.f1697kS = bundle;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(Boolean bool) {
                        if (bool == null) {
                            return;
                        }
                        switch (this.statusCode) {
                            case 0:
                                try {
                                    if (AbstractC1020de.this.mo1367ah().equals(this.f1698kT.getInterfaceDescriptor())) {
                                        AbstractC1020de.m1533a(AbstractC1020de.this, AbstractC1020de.this.mo1370p(this.f1698kT));
                                        if (AbstractC1020de.m1538d(AbstractC1020de.this) != null) {
                                            AbstractC1020de.this.mo1545aZ();
                                            return;
                                        }
                                    }
                                } catch (RemoteException e) {
                                }
                                C1021df.m1555s(AbstractC1020de.m1540f(AbstractC1020de.this)).m1557b(AbstractC1020de.this.mo1366ag(), AbstractC1020de.m1539e(AbstractC1020de.this));
                                AbstractC1020de.m1534a(AbstractC1020de.this, (e) null);
                                AbstractC1020de.m1533a(AbstractC1020de.this, (IInterface) null);
                                AbstractC1020de.this.mo1542a(new ConnectionResult(8, null));
                                return;
                            case 10:
                                throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                            default:
                                PendingIntent pendingIntent = this.f1697kS != null ? (PendingIntent) this.f1697kS.getParcelable("pendingIntent") : null;
                                if (AbstractC1020de.m1539e(AbstractC1020de.this) != null) {
                                    C1021df.m1555s(AbstractC1020de.m1540f(AbstractC1020de.this)).m1557b(AbstractC1020de.this.mo1366ag(), AbstractC1020de.m1539e(AbstractC1020de.this));
                                    AbstractC1020de.m1534a(AbstractC1020de.this, (e) null);
                                }
                                AbstractC1020de.m1533a(AbstractC1020de.this, (IInterface) null);
                                AbstractC1020de.this.mo1542a(new ConnectionResult(this.statusCode, pendingIntent));
                                return;
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                protected AbstractC1020de(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
                    this.mContext = (Context) C1028dm.m1605e(context);
                    this.f1679kE.add((GooglePlayServicesClient.ConnectionCallbacks) C1028dm.m1605e(connectionCallbacks));
                    this.f1682kH = new ArrayList<>();
                    this.f1682kH.add((GooglePlayServicesClient.OnConnectionFailedListener) C1028dm.m1605e(onConnectionFailedListener));
                    this.mHandler = new a(context.getMainLooper());
                    mo1500a(strArr);
                    this.f1677is = strArr;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: a */
                static /* synthetic */ IInterface m1533a(AbstractC1020de abstractC1020de, IInterface iInterface) {
                    abstractC1020de.f1678kD = iInterface;
                    return iInterface;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ AbstractC1020de<?>.e m1534a(AbstractC1020de<?> abstractC1020de, AbstractC1020de<?>.e eVar) {
                    abstractC1020de.f1685kK = eVar;
                    return eVar;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Object m1535a(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1688kN;
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ ArrayList m1536b(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1679kE;
                }

                /* JADX INFO: renamed from: c */
                static /* synthetic */ ArrayList m1537c(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1684kJ;
                }

                /* JADX INFO: renamed from: d */
                static /* synthetic */ IInterface m1538d(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1678kD;
                }

                /* JADX INFO: renamed from: e */
                static /* synthetic */ AbstractC1020de<?>.e m1539e(AbstractC1020de<?> abstractC1020de) {
                    return abstractC1020de.f1685kK;
                }

                /* JADX INFO: renamed from: f */
                static /* synthetic */ Context m1540f(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.mContext;
                }

                /* JADX INFO: renamed from: a */
                protected void mo1541a(int i, IBinder iBinder, Bundle bundle) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new f(i, iBinder, bundle)));
                }

                /* JADX INFO: renamed from: a */
                protected void mo1542a(ConnectionResult connectionResult) {
                    this.mHandler.removeMessages(4);
                    synchronized (this.f1682kH) {
                        try {
                            this.f1683kI = true;
                            ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> arrayList = this.f1682kH;
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                if (!this.f1686kL) {
                                    return;
                                }
                                if (this.f1682kH.contains(arrayList.get(i))) {
                                    arrayList.get(i).onConnectionFailed(connectionResult);
                                }
                            }
                            this.f1683kI = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                public final void m1543a(AbstractC1020de<T>.b<?> bVar) {
                    synchronized (this.f1684kJ) {
                        try {
                            this.f1684kJ.add(bVar);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(2, bVar));
                }

                /* JADX INFO: renamed from: a */
                protected abstract void mo1365a(InterfaceC1025dj interfaceC1025dj, d dVar) throws RemoteException;

                /* JADX INFO: renamed from: a */
                protected void mo1500a(String... strArr) {
                }

                /* JADX INFO: renamed from: aY */
                public final String[] m1544aY() {
                    return this.f1677is;
                }

                /* JADX INFO: renamed from: aZ */
                protected void mo1545aZ() {
                    synchronized (this.f1679kE) {
                        try {
                            C1028dm.m1606k(!this.f1681kG);
                            this.mHandler.removeMessages(4);
                            this.f1681kG = true;
                            C1028dm.m1606k(this.f1680kF.size() == 0);
                            Bundle bundleMo1546ba = mo1546ba();
                            ArrayList<GooglePlayServicesClient.ConnectionCallbacks> arrayList = this.f1679kE;
                            int size = arrayList.size();
                            for (int i = 0; i < size && this.f1686kL && isConnected(); i++) {
                                this.f1680kF.size();
                                if (!this.f1680kF.contains(arrayList.get(i))) {
                                    arrayList.get(i).onConnected(bundleMo1546ba);
                                }
                            }
                            this.f1680kF.clear();
                            this.f1681kG = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: ag */
                protected abstract String mo1366ag();

                /* JADX INFO: renamed from: ah */
                protected abstract String mo1367ah();

                /* JADX INFO: renamed from: ba */
                protected Bundle mo1546ba() {
                    return null;
                }

                /* JADX INFO: renamed from: bb */
                protected final void m1547bb() {
                    this.mHandler.removeMessages(4);
                    synchronized (this.f1679kE) {
                        try {
                            this.f1681kG = true;
                            ArrayList<GooglePlayServicesClient.ConnectionCallbacks> arrayList = this.f1679kE;
                            int size = arrayList.size();
                            for (int i = 0; i < size && this.f1686kL; i++) {
                                if (this.f1679kE.contains(arrayList.get(i))) {
                                    arrayList.get(i).onDisconnected();
                                }
                            }
                            this.f1681kG = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: bc */
                protected final void m1548bc() {
                    if (!isConnected()) {
                        throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
                    }
                }

                /* JADX INFO: renamed from: bd */
                protected final T m1549bd() {
                    m1548bc();
                    return this.f1678kD;
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f1686kL = true;
                    synchronized (this.f1688kN) {
                        try {
                            this.f1687kM = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
                    if (iIsGooglePlayServicesAvailable != 0) {
                        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(iIsGooglePlayServicesAvailable)));
                        return;
                    }
                    if (this.f1685kK != null) {
                        Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
                        this.f1678kD = null;
                        C1021df.m1555s(this.mContext).m1557b(mo1366ag(), this.f1685kK);
                    }
                    this.f1685kK = new e();
                    if (C1021df.m1555s(this.mContext).m1556a(mo1366ag(), this.f1685kK)) {
                        return;
                    }
                    Log.e("GmsClient", "unable to connect to service: " + mo1366ag());
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(3, 9));
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f1686kL = false;
                    synchronized (this.f1688kN) {
                        try {
                            this.f1687kM = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    synchronized (this.f1684kJ) {
                        try {
                            int size = this.f1684kJ.size();
                            for (int i = 0; i < size; i++) {
                                this.f1684kJ.get(i).mo1552bf();
                            }
                            this.f1684kJ.clear();
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    this.f1678kD = null;
                    if (this.f1685kK != null) {
                        C1021df.m1555s(this.mContext).m1557b(mo1366ag(), this.f1685kK);
                        this.f1685kK = null;
                    }
                }

                public final Context getContext() {
                    return this.mContext;
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f1678kD != null;
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    boolean z;
                    synchronized (this.f1688kN) {
                        try {
                            z = this.f1687kM;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    boolean zContains;
                    C1028dm.m1605e(listener);
                    synchronized (this.f1679kE) {
                        try {
                            zContains = this.f1679kE.contains(listener);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zContains;
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    boolean zContains;
                    C1028dm.m1605e(listener);
                    synchronized (this.f1682kH) {
                        try {
                            zContains = this.f1682kH.contains(listener);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zContains;
                }

                /* JADX INFO: renamed from: p */
                protected abstract T mo1370p(IBinder iBinder);

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    C1028dm.m1605e(listener);
                    synchronized (this.f1679kE) {
                        try {
                            if (this.f1679kE.contains(listener)) {
                                Log.w("GmsClient", "registerConnectionCallbacks(): listener " + listener + " is already registered");
                            } else {
                                if (this.f1681kG) {
                                    this.f1679kE = new ArrayList<>(this.f1679kE);
                                }
                                this.f1679kE.add(listener);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (isConnected()) {
                        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, listener));
                    }
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    C1028dm.m1605e(listener);
                    synchronized (this.f1682kH) {
                        try {
                            if (this.f1682kH.contains(listener)) {
                                Log.w("GmsClient", "registerConnectionFailedListener(): listener " + listener + " is already registered");
                            } else {
                                if (this.f1683kI) {
                                    this.f1682kH = new ArrayList<>(this.f1682kH);
                                }
                                this.f1682kH.add(listener);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: u */
                protected final void m1550u(IBinder iBinder) {
                    try {
                        mo1365a(InterfaceC1025dj.a.m1596w(iBinder), new d(this));
                    } catch (RemoteException e2) {
                        Log.w("GmsClient", "service died");
                    }
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    C1028dm.m1605e(listener);
                    synchronized (this.f1679kE) {
                        try {
                            if (this.f1679kE != null) {
                                if (this.f1681kG) {
                                    this.f1679kE = new ArrayList<>(this.f1679kE);
                                }
                                if (!this.f1679kE.remove(listener)) {
                                    Log.w("GmsClient", "unregisterConnectionCallbacks(): listener " + listener + " not found");
                                } else if (this.f1681kG && !this.f1680kF.contains(listener)) {
                                    this.f1680kF.add(listener);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    C1028dm.m1605e(listener);
                    synchronized (this.f1682kH) {
                        try {
                            if (this.f1682kH != null) {
                                if (this.f1683kI) {
                                    this.f1682kH = new ArrayList<>(this.f1682kH);
                                }
                                if (!this.f1682kH.remove(listener)) {
                                    Log.w("GmsClient", "unregisterConnectionFailedListener(): listener " + listener + " not found");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
