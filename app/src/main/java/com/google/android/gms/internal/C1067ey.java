            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.content.ContentProviderClient;
            import android.content.Context;
            import android.location.Location;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.location.InterfaceC1129a;
            import com.google.android.gms.location.LocationListener;
            import com.google.android.gms.location.LocationRequest;
            import java.util.HashMap;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ey */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1067ey {
                private final Context mContext;

                /* JADX INFO: renamed from: oO */
                private final InterfaceC1072fc<InterfaceC1066ex> f1861oO;

                /* JADX INFO: renamed from: oP */
                private ContentProviderClient f1862oP = null;

                /* JADX INFO: renamed from: oQ */
                private boolean f1863oQ = false;

                /* JADX INFO: renamed from: oR */
                private HashMap<LocationListener, b> f1864oR = new HashMap<>();

                /* JADX INFO: renamed from: com.google.android.gms.internal.ey$a */
                private static class a extends Handler {

                    /* JADX INFO: renamed from: oS */
                    private final LocationListener f1865oS;

                    public a(LocationListener locationListener) {
                        this.f1865oS = locationListener;
                    }

                    public a(LocationListener locationListener, Looper looper) {
                        super(looper);
                        this.f1865oS = locationListener;
                    }

                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                this.f1865oS.onLocationChanged(new Location((Location) msg.obj));
                                break;
                            default:
                                Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ey$b */
                private static class b extends InterfaceC1129a.a {

                    /* JADX INFO: renamed from: oT */
                    private Handler f1866oT;

                    b(LocationListener locationListener, Looper looper) {
                        this.f1866oT = looper == null ? new a(locationListener) : new a(locationListener, looper);
                    }

                    @Override // com.google.android.gms.location.InterfaceC1129a
                    public void onLocationChanged(Location location) {
                        if (this.f1866oT == null) {
                            Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                            return;
                        }
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 1;
                        messageObtain.obj = location;
                        this.f1866oT.sendMessage(messageObtain);
                    }

                    public void release() {
                        this.f1866oT = null;
                    }
                }

                public C1067ey(Context context, InterfaceC1072fc<InterfaceC1066ex> interfaceC1072fc) {
                    this.mContext = context;
                    this.f1861oO = interfaceC1072fc;
                }

                /* JADX INFO: renamed from: cm */
                public void m1909cm() {
                    if (this.f1863oQ) {
                        setMockMode(false);
                    }
                }

                public Location getLastLocation() {
                    this.f1861oO.mo1913bc();
                    try {
                        return ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1907cl();
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void removeAllListeners() {
                    try {
                        synchronized (this.f1864oR) {
                            try {
                                for (b bVar : this.f1864oR.values()) {
                                    if (bVar != null) {
                                        ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1904a(bVar);
                                    }
                                }
                                this.f1864oR.clear();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void removeLocationUpdates(PendingIntent callbackIntent) {
                    this.f1861oO.mo1913bc();
                    try {
                        ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1898a(callbackIntent);
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void removeLocationUpdates(LocationListener listener) {
                    this.f1861oO.mo1913bc();
                    C1028dm.m1601a(listener, "Invalid null listener");
                    synchronized (this.f1864oR) {
                        try {
                            b bVarRemove = this.f1864oR.remove(listener);
                            if (this.f1862oP != null && this.f1864oR.isEmpty()) {
                                this.f1862oP.release();
                                this.f1862oP = null;
                            }
                            if (bVarRemove != null) {
                                bVarRemove.release();
                                try {
                                    ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1904a(bVarRemove);
                                } catch (RemoteException e) {
                                    throw new IllegalStateException(e);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
                    this.f1861oO.mo1913bc();
                    try {
                        ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1901a(request, callbackIntent);
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
                    this.f1861oO.mo1913bc();
                    if (looper == null) {
                        C1028dm.m1601a(Looper.myLooper(), "Can't create handler inside thread that has not called Looper.prepare()");
                    }
                    synchronized (this.f1864oR) {
                        try {
                            b bVar = this.f1864oR.get(listener);
                            b bVar2 = bVar == null ? new b(listener, looper) : bVar;
                            this.f1864oR.put(listener, bVar2);
                            try {
                                ((InterfaceC1066ex) this.f1861oO.mo1914bd()).mo1903a(request, bVar2, this.mContext.getPackageName());
                            } catch (RemoteException e) {
                                throw new IllegalStateException(e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public void setMockLocation(Location mockLocation) {
                    this.f1861oO.mo1913bc();
                    try {
                        ((InterfaceC1066ex) this.f1861oO.mo1914bd()).setMockLocation(mockLocation);
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }

                public void setMockMode(boolean isMockMode) {
                    this.f1861oO.mo1913bc();
                    try {
                        ((InterfaceC1066ex) this.f1861oO.mo1914bd()).setMockMode(isMockMode);
                        this.f1863oQ = isMockMode;
                    } catch (RemoteException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
