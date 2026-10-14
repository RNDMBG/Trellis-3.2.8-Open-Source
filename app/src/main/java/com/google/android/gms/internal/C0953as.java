            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.RemoteException;
            import android.os.SystemClock;
            import com.google.android.gms.dynamic.BinderC0905c;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.as */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0953as implements C0954at.a {

                /* JADX INFO: renamed from: dZ */
                private final InterfaceC0957aw f1177dZ;

                /* JADX INFO: renamed from: em */
                private final C1124v f1179em;

                /* JADX INFO: renamed from: fd */
                private final String f1180fd;

                /* JADX INFO: renamed from: fe */
                private final long f1181fe;

                /* JADX INFO: renamed from: ff */
                private final C0949ao f1182ff;

                /* JADX INFO: renamed from: fg */
                private final C1126x f1183fg;

                /* JADX INFO: renamed from: fh */
                private InterfaceC0958ax f1184fh;
                private final Context mContext;

                /* JADX INFO: renamed from: eJ */
                private final Object f1178eJ = new Object();

                /* JADX INFO: renamed from: fi */
                private int f1185fi = -2;

                /* JADX INFO: renamed from: com.google.android.gms.internal.as$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {

                    /* JADX INFO: renamed from: fj */
                    final /* synthetic */ BinderC0952ar f1186fj;

                    AnonymousClass1(BinderC0952ar binderC0952ar) {
                        this.f1186fj = binderC0952ar;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (C0953as.m1266a(C0953as.this)) {
                            try {
                                if (C0953as.m1270b(C0953as.this) != -2) {
                                    return;
                                }
                                C0953as.m1265a(C0953as.this, C0953as.m1272c(C0953as.this));
                                if (C0953as.m1273d(C0953as.this) == null) {
                                    C0953as.this.mo1275d(4);
                                } else {
                                    this.f1186fj.m1262a(C0953as.this);
                                    C0953as.m1269a(C0953as.this, this.f1186fj);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                public C0953as(Context context, String str, InterfaceC0957aw interfaceC0957aw, C0950ap c0950ap, C0949ao c0949ao, C1124v c1124v, C1126x c1126x) {
                    this.mContext = context;
                    this.f1180fd = str;
                    this.f1177dZ = interfaceC0957aw;
                    this.f1181fe = c0950ap.f1168eV != -1 ? c0950ap.f1168eV : 10000L;
                    this.f1182ff = c0949ao;
                    this.f1179em = c1124v;
                    this.f1183fg = c1126x;
                }

                /* JADX INFO: renamed from: P */
                private InterfaceC0958ax m1264P() {
                    C1002cn.m1456o("Instantiating mediation adapter: " + this.f1180fd);
                    try {
                        return this.f1177dZ.mo1280g(this.f1180fd);
                    } catch (RemoteException e) {
                        C1002cn.m1451a("Could not instantiate mediation adapter: " + this.f1180fd, e);
                        return null;
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ InterfaceC0958ax m1265a(C0953as c0953as, InterfaceC0958ax interfaceC0958ax) {
                    c0953as.f1184fh = interfaceC0958ax;
                    return interfaceC0958ax;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Object m1266a(C0953as c0953as) {
                    return c0953as.f1178eJ;
                }

                /* JADX INFO: renamed from: a */
                private void m1267a(long j, long j2, long j3, long j4) {
                    while (this.f1185fi == -2) {
                        m1271b(j, j2, j3, j4);
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1268a(BinderC0952ar binderC0952ar) {
                    try {
                        if (this.f1183fg.f2164ex) {
                            this.f1184fh.mo1282a(BinderC0905c.m1166g(this.mContext), this.f1179em, this.f1182ff.f1165eS, binderC0952ar);
                        } else {
                            this.f1184fh.mo1283a(BinderC0905c.m1166g(this.mContext), this.f1183fg, this.f1179em, this.f1182ff.f1165eS, binderC0952ar);
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not request ad from mediation adapter.", e);
                        mo1275d(5);
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ void m1269a(C0953as c0953as, BinderC0952ar binderC0952ar) {
                    c0953as.m1268a(binderC0952ar);
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ int m1270b(C0953as c0953as) {
                    return c0953as.f1185fi;
                }

                /* JADX INFO: renamed from: b */
                private void m1271b(long j, long j2, long j3, long j4) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    long j5 = j2 - (jElapsedRealtime - j);
                    long j6 = j4 - (jElapsedRealtime - j3);
                    if (j5 <= 0 || j6 <= 0) {
                        C1002cn.m1456o("Timed out waiting for adapter.");
                        this.f1185fi = 3;
                    } else {
                        try {
                            this.f1178eJ.wait(Math.min(j5, j6));
                        } catch (InterruptedException e) {
                            this.f1185fi = -1;
                        }
                    }
                }

                /* JADX INFO: renamed from: c */
                static /* synthetic */ InterfaceC0958ax m1272c(C0953as c0953as) {
                    return c0953as.m1264P();
                }

                /* JADX INFO: renamed from: d */
                static /* synthetic */ InterfaceC0958ax m1273d(C0953as c0953as) {
                    return c0953as.f1184fh;
                }

                /* JADX INFO: renamed from: b */
                public C0954at m1274b(long j, long j2) {
                    C0954at c0954at;
                    synchronized (this.f1178eJ) {
                        try {
                            long jElapsedRealtime = SystemClock.elapsedRealtime();
                            BinderC0952ar binderC0952ar = new BinderC0952ar();
                            C1001cm.f1595hO.post(new AnonymousClass1(binderC0952ar));
                            m1267a(jElapsedRealtime, this.f1181fe, j, j2);
                            c0954at = new C0954at(this.f1182ff, this.f1184fh, this.f1180fd, binderC0952ar, this.f1185fi);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return c0954at;
                }

                public void cancel() {
                    synchronized (this.f1178eJ) {
                        try {
                            try {
                                if (this.f1184fh != null) {
                                    this.f1184fh.destroy();
                                }
                            } catch (RemoteException e) {
                                C1002cn.m1452b("Could not destroy mediation adapter.", e);
                            }
                            this.f1185fi = -1;
                            this.f1178eJ.notify();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // com.google.android.gms.internal.C0954at.a
                /* JADX INFO: renamed from: d */
                public void mo1275d(int i) {
                    synchronized (this.f1178eJ) {
                        try {
                            this.f1185fi = i;
                            this.f1178eJ.notify();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
