            package com.google.android.gms.internal;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            
            
            
            public final class C1021df implements Handler.Callback {

                
                private static final Object f1699kU = new Object();

                
                private static C1021df f1700kV;

                
                private final Context f1701kW;

                
                private final HashMap<String, a> f1702kX = new HashMap<>();
                private final Handler mHandler;

                
                final class a {

                    
                    private final String f1703kY;

                    
                    private boolean f1706lb;

                    
                    private IBinder f1707lc;

                    
                    private ComponentName f1708ld;

                    
                    private final ServiceConnectionC1329a f1704kZ = new ServiceConnectionC1329a();

                    
                    private final HashSet<AbstractC1020de<?>.e> f1705la = new HashSet<>();
                    private int mState = 0;

                    
                    public class ServiceConnectionC1329a implements ServiceConnection {
                        public ServiceConnectionC1329a() {
                        }

                        @Override 
                        public void onServiceConnected(ComponentName component, IBinder binder) {
                            synchronized (C1021df.m1554a(C1021df.this)) {
                                try {
                                    a.m1560a(a.this, binder);
                                    a.m1559a(a.this, component);
                                    Iterator it = a.m1561a(a.this).iterator();
                                    while (it.hasNext()) {
                                        ((AbstractC1020de.e) it.next()).onServiceConnected(component, binder);
                                    }
                                    a.m1558a(a.this, 1);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }

                        @Override 
                        public void onServiceDisconnected(ComponentName component) {
                            synchronized (C1021df.m1554a(C1021df.this)) {
                                try {
                                    a.m1560a(a.this, (IBinder) null);
                                    a.m1559a(a.this, component);
                                    Iterator it = a.m1561a(a.this).iterator();
                                    while (it.hasNext()) {
                                        ((AbstractC1020de.e) it.next()).onServiceDisconnected(component);
                                    }
                                    a.m1558a(a.this, 2);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    public a(String str) {
                        this.f1703kY = str;
                    }

                    
                    static  int m1558a(a aVar, int i) {
                        aVar.mState = i;
                        return i;
                    }

                    
                    static  ComponentName m1559a(a aVar, ComponentName componentName) {
                        aVar.f1708ld = componentName;
                        return componentName;
                    }

                    
                    static  IBinder m1560a(a aVar, IBinder iBinder) {
                        aVar.f1707lc = iBinder;
                        return iBinder;
                    }

                    
                    static  HashSet m1561a(a aVar) {
                        return aVar.f1705la;
                    }

                    
                    public void m1562a(AbstractC1020de<?>.e eVar) {
                        this.f1705la.add(eVar);
                    }

                    
                    public void m1563b(AbstractC1020de<?>.e eVar) {
                        this.f1705la.remove(eVar);
                    }

                    
                    public ServiceConnectionC1329a m1564bg() {
                        return this.f1704kZ;
                    }

                    
                    public String m1565bh() {
                        return this.f1703kY;
                    }

                    
                    public boolean m1566bi() {
                        return this.f1705la.isEmpty();
                    }

                    
                    public boolean m1567c(AbstractC1020de<?>.e eVar) {
                        return this.f1705la.contains(eVar);
                    }

                    public IBinder getBinder() {
                        return this.f1707lc;
                    }

                    public ComponentName getComponentName() {
                        return this.f1708ld;
                    }

                    public int getState() {
                        return this.mState;
                    }

                    public boolean isBound() {
                        return this.f1706lb;
                    }

                    
                    public void m1568l(boolean z) {
                        this.f1706lb = z;
                    }
                }

                private C1021df(Context context) {
                    this.mHandler = new Handler(context.getMainLooper(), this);
                    this.f1701kW = context.getApplicationContext();
                }

                
                static  HashMap m1554a(C1021df c1021df) {
                    return c1021df.f1702kX;
                }

                
                public static C1021df m1555s(Context context) {
                    synchronized (f1699kU) {
                        try {
                            if (f1700kV == null) {
                                f1700kV = new C1021df(context.getApplicationContext());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return f1700kV;
                }

                
                public boolean m1556a(String str, AbstractC1020de<?>.e eVar) {
                    boolean zIsBound;
                    synchronized (this.f1702kX) {
                        try {
                            a aVar = this.f1702kX.get(str);
                            if (aVar != null) {
                                this.mHandler.removeMessages(0, aVar);
                                if (!aVar.m1567c(eVar)) {
                                    aVar.m1562a(eVar);
                                    switch (aVar.getState()) {
                                        case 1:
                                            eVar.onServiceConnected(aVar.getComponentName(), aVar.getBinder());
                                            break;
                                        case 2:
                                            aVar.m1568l(this.f1701kW.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), aVar.m1564bg(), 129));
                                            break;
                                    }
                                } else {
                                    throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  startServiceAction=" + str);
                                }
                            } else {
                                aVar = new a(str);
                                aVar.m1562a(eVar);
                                aVar.m1568l(this.f1701kW.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), aVar.m1564bg(), 129));
                                this.f1702kX.put(str, aVar);
                            }
                            zIsBound = aVar.isBound();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zIsBound;
                }

                
                public void m1557b(String str, AbstractC1020de<?>.e eVar) {
                    synchronized (this.f1702kX) {
                        try {
                            a aVar = this.f1702kX.get(str);
                            if (aVar == null) {
                                throw new IllegalStateException("Nonexistent connection status for service action: " + str);
                            }
                            if (!aVar.m1567c(eVar)) {
                                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  startServiceAction=" + str);
                            }
                            aVar.m1563b(eVar);
                            if (aVar.m1566bi()) {
                                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, aVar), 5000L);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            a aVar = (a) msg.obj;
                            synchronized (this.f1702kX) {
                                try {
                                    if (aVar.m1566bi()) {
                                        this.f1701kW.unbindService(aVar.m1564bg());
                                        this.f1702kX.remove(aVar.m1565bh());
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                                break;
                            }
                            return true;
                        default:
                            return false;
                    }
                }
            }
