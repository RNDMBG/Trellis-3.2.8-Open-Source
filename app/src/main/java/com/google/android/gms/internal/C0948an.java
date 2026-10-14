            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.an */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0948an {

                /* JADX INFO: renamed from: dZ */
                private final InterfaceC0957aw f1154dZ;

                /* JADX INFO: renamed from: eI */
                private final C0982bu f1155eI;

                /* JADX INFO: renamed from: eK */
                private final C0950ap f1157eK;

                /* JADX INFO: renamed from: eM */
                private C0953as f1159eM;
                private final Context mContext;

                /* JADX INFO: renamed from: eJ */
                private final Object f1156eJ = new Object();

                /* JADX INFO: renamed from: eL */
                private boolean f1158eL = false;

                /* JADX INFO: renamed from: com.google.android.gms.internal.an$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {

                    /* JADX INFO: renamed from: eN */
                    final /* synthetic */ C0954at f1160eN;

                    AnonymousClass1(C0954at c0954at) {
                        this.f1160eN = c0954at;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            this.f1160eN.f1190fn.destroy();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not destroy mediation adapter.", e);
                        }
                    }
                }

                public C0948an(Context context, C0982bu c0982bu, InterfaceC0957aw interfaceC0957aw, C0950ap c0950ap) {
                    this.mContext = context;
                    this.f1155eI = c0982bu;
                    this.f1154dZ = interfaceC0957aw;
                    this.f1157eK = c0950ap;
                }

                /* JADX INFO: renamed from: a */
                public C0954at m1255a(long j, long j2) {
                    C1002cn.m1454m("Starting mediation.");
                    for (C0949ao c0949ao : this.f1157eK.f1167eU) {
                        C1002cn.m1456o("Trying mediation network: " + c0949ao.f1162eP);
                        for (String str : c0949ao.f1163eQ) {
                            synchronized (this.f1156eJ) {
                                try {
                                    if (this.f1158eL) {
                                        return new C0954at(-1);
                                    }
                                    this.f1159eM = new C0953as(this.mContext, str, this.f1154dZ, this.f1157eK, c0949ao, this.f1155eI.f1496gB, this.f1155eI.f1493ed);
                                    C0954at c0954atM1274b = this.f1159eM.m1274b(j, j2);
                                    if (c0954atM1274b.f1188fl == 0) {
                                        C1002cn.m1454m("Adapter succeeded.");
                                        return c0954atM1274b;
                                    }
                                    if (c0954atM1274b.f1190fn != null) {
                                        C1001cm.f1595hO.post(new AnonymousClass1(c0954atM1274b));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                    return new C0954at(1);
                }

                public void cancel() {
                    synchronized (this.f1156eJ) {
                        try {
                            this.f1158eL = true;
                            if (this.f1159eM != null) {
                                this.f1159eM.cancel();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
