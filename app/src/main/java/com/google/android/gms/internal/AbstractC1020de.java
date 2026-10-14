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
            
            
            
            public abstract class AbstractC1020de<T extends IInterface> implements GooglePlayServicesClient {

                
                public static final String[] f1676kO = {"service_esmobile", "service_googleme"};

                
                private final String[] f1677is;

                
                private T f1678kD;

                
                private ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> f1682kH;

                
                private AbstractC1020de<T>.e f1685kK;
                private final Context mContext;
                final Handler mHandler;

                
                final ArrayList<GooglePlayServicesClient.ConnectionCallbacks> f1680kF = new ArrayList<>();

                
                private boolean f1681kG = false;

                
                private boolean f1683kI = false;

                
                private final ArrayList<AbstractC1020de<T>.b<?>> f1684kJ = new ArrayList<>();

                
                boolean f1686kL = false;

                
                boolean f1687kM = false;

                
                private final Object f1688kN = new Object();

                
                private ArrayList<GooglePlayServicesClient.ConnectionCallbacks> f1679kE = new ArrayList<>();

                
                final class a extends Handler {
                    public a(Looper looper) {
                        super(looper);
                    }

                    @Override 
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

                
                protected abstract class b<TListener> {

                    
                    private boolean f1691kQ = false;
                    private TListener mListener;

                    public b(TListener tlistener) {
                        this.mListener = tlistener;
                    }

                    
                    protected abstract void mo1502a(TListener tlistener);

                    
                    protected abstract void mo1503aF();

                    
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

                
                public abstract class c<TListener> extends AbstractC1020de<T>.b<TListener> {

                    
                    private final C0891d f1692jf;

                    public c(TListener tlistener, C0891d c0891d) {
                        super(tlistener);
                        this.f1692jf = c0891d;
                    }

                    @Override 
                    
                    protected final void mo1502a(TListener tlistener) {
                        mo1504a(tlistener, this.f1692jf);
                    }

                    
                    protected abstract void mo1504a(TListener tlistener, C0891d c0891d);

                    @Override 
                    
                    protected void mo1503aF() {
                        if (this.f1692jf != null) {
                            this.f1692jf.close();
                        }
                    }

                }

                
                public static final class d extends InterfaceC1024di.a {

                    
                    private AbstractC1020de f1694kR;

                    public d(AbstractC1020de abstractC1020de) {
                        this.f1694kR = abstractC1020de;
                    }

                    @Override 
                    
                    public void mo1553b(int i, IBinder iBinder, Bundle bundle) {
                        C1028dm.m1601a("onPostInitComplete can be called only once per call to getServiceFromBroker", this.f1694kR);
                        this.f1694kR.mo1541a(i, iBinder, bundle);
                        this.f1694kR = null;
                    }
                }

                
                final class e implements ServiceConnection {
                    e() {
                    }

                    @Override 
                    public void onServiceConnected(ComponentName component, IBinder binder) {
                        AbstractC1020de.this.m1550u(binder);
                    }

                    @Override 
                    public void onServiceDisconnected(ComponentName component) {
                        AbstractC1020de.m1533a(AbstractC1020de.this, (IInterface) null);
                        AbstractC1020de.this.m1547bb();
                    }
                }

                
                protected final class f extends AbstractC1020de<T>.b<Boolean> {

                    
                    public final Bundle f1697kS;

                    
                    public final IBinder f1698kT;
                    public final int statusCode;

                    public f(int i, IBinder iBinder, Bundle bundle) {
                        super(true);
                        this.statusCode = i;
                        this.f1698kT = iBinder;
                        this.f1697kS = bundle;
                    }

                    
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

                    @Override 
                    
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

                
                
                static  IInterface m1533a(AbstractC1020de abstractC1020de, IInterface iInterface) {
                    abstractC1020de.f1678kD = iInterface;
                    return iInterface;
                }

                
                static  AbstractC1020de<?>.e m1534a(AbstractC1020de<?> abstractC1020de, AbstractC1020de<?>.e eVar) {
                    abstractC1020de.f1685kK = eVar;
                    return eVar;
                }

                
                static  Object m1535a(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1688kN;
                }

                
                static  ArrayList m1536b(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1679kE;
                }

                
                static  ArrayList m1537c(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1684kJ;
                }

                
                static  IInterface m1538d(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.f1678kD;
                }

                
                static  AbstractC1020de<?>.e m1539e(AbstractC1020de<?> abstractC1020de) {
                    return abstractC1020de.f1685kK;
                }

                
                static  Context m1540f(AbstractC1020de abstractC1020de) {
                    return abstractC1020de.mContext;
                }

                
                protected void mo1541a(int i, IBinder iBinder, Bundle bundle) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new f(i, iBinder, bundle)));
                }

                
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

                
                protected abstract void mo1365a(InterfaceC1025dj interfaceC1025dj, d dVar) throws RemoteException;

                
                protected void mo1500a(String... strArr) {
                }

                
                public final String[] m1544aY() {
                    return this.f1677is;
                }

                
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

                
                protected abstract String mo1366ag();

                
                protected abstract String mo1367ah();

                
                protected Bundle mo1546ba() {
                    return null;
                }

                
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

                
                protected final void m1548bc() {
                    if (!isConnected()) {
                        throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
                    }
                }

                
                protected final T m1549bd() {
                    m1548bc();
                    return this.f1678kD;
                }

                @Override 
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

                @Override 
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

                @Override 
                public boolean isConnected() {
                    return this.f1678kD != null;
                }

                @Override 
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

                @Override 
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

                @Override 
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

                
                protected abstract T mo1370p(IBinder iBinder);

                @Override 
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

                @Override 
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

                
                protected final void m1550u(IBinder iBinder) {
                    try {
                        mo1365a(InterfaceC1025dj.a.m1596w(iBinder), new d(this));
                    } catch (RemoteException e2) {
                        Log.w("GmsClient", "service died");
                    }
                }

                @Override 
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

                @Override 
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
