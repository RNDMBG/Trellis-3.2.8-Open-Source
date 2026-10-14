            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Bundle;
            import android.os.RemoteException;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            
            
            
            public abstract class AbstractC0980bs extends AbstractC0995cg {

                
                private final C0982bu f1487eI;

                
                private final C0979br.a f1488gx;

                
                public static final class a extends AbstractC0980bs {
                    private final Context mContext;

                    public a(Context context, C0982bu c0982bu, C0979br.a aVar) {
                        super(c0982bu, aVar);
                        this.mContext = context;
                    }

                    @Override 
                    
                    public void mo1363ae() {
                    }

                    @Override 
                    
                    public InterfaceC0986by mo1364af() {
                        return BinderC0987bz.m1381a(this.mContext, new C0947am());
                    }
                }

                
                public static final class b extends AbstractC0980bs implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

                    
                    private final Object f1489eJ;

                    
                    private final C0979br.a f1490gx;

                    
                    private final C0981bt f1491gy;

                    public b(Context context, C0982bu c0982bu, C0979br.a aVar) {
                        super(c0982bu, aVar);
                        this.f1489eJ = new Object();
                        this.f1490gx = aVar;
                        this.f1491gy = new C0981bt(context, this, this, c0982bu.f1494eg.f1598hR);
                        this.f1491gy.connect();
                    }

                    @Override 
                    
                    public void mo1363ae() {
                        synchronized (this.f1489eJ) {
                            try {
                                if (this.f1491gy.isConnected() || this.f1491gy.isConnecting()) {
                                    this.f1491gy.disconnect();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
                    
                    public InterfaceC0986by mo1364af() {
                        InterfaceC0986by interfaceC0986byM1368ai;
                        synchronized (this.f1489eJ) {
                            try {
                                try {
                                    interfaceC0986byM1368ai = this.f1491gy.m1368ai();
                                } catch (IllegalStateException e) {
                                    interfaceC0986byM1368ai = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return interfaceC0986byM1368ai;
                    }

                    @Override 
                    public void onConnected(Bundle connectionHint) {
                        start();
                    }

                    @Override 
                    public void onConnectionFailed(ConnectionResult result) {
                        this.f1490gx.mo1357a(new C0984bw(0));
                    }

                    @Override 
                    public void onDisconnected() {
                        C1002cn.m1454m("Disconnected from remote ad request service.");
                    }
                }

                public AbstractC0980bs(C0982bu c0982bu, C0979br.a aVar) {
                    this.f1487eI = c0982bu;
                    this.f1488gx = aVar;
                }

                
                private static C0984bw m1362a(InterfaceC0986by interfaceC0986by, C0982bu c0982bu) {
                    try {
                        return interfaceC0986by.mo1377a(c0982bu);
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not fetch ad response from ad request service.", e);
                        return null;
                    }
                }

                @Override 
                
                public final void mo1358ac() {
                    C0984bw c0984bwM1362a;
                    try {
                        InterfaceC0986by interfaceC0986byMo1364af = mo1364af();
                        if (interfaceC0986byMo1364af == null || (c0984bwM1362a = m1362a(interfaceC0986byMo1364af, this.f1487eI)) == null) {
                            c0984bwM1362a = new C0984bw(0);
                        }
                        mo1363ae();
                        this.f1488gx.mo1357a(c0984bwM1362a);
                    } catch (Throwable th) {
                        mo1363ae();
                        throw th;
                    }
                }

                
                public abstract void mo1363ae();

                
                public abstract InterfaceC0986by mo1364af();

                @Override 
                public final void onStop() {
                    mo1363ae();
                }
            }
