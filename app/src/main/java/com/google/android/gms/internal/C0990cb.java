            package com.google.android.gms.internal;

            import com.google.android.gms.plus.PlusShare;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cb */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0990cb {

                /* JADX INFO: renamed from: fG */
                private C1005cq f1527fG;

                /* JADX INFO: renamed from: gT */
                private String f1528gT;

                /* JADX INFO: renamed from: eJ */
                private final Object f1526eJ = new Object();

                /* JADX INFO: renamed from: gw */
                private int f1531gw = -2;

                /* JADX INFO: renamed from: gU */
                public final InterfaceC0943ai f1529gU = new AnonymousClass1();

                /* JADX INFO: renamed from: gV */
                public final InterfaceC0943ai f1530gV = new AnonymousClass2();

                /* JADX INFO: renamed from: com.google.android.gms.internal.cb$1, reason: invalid class name */
                class AnonymousClass1 implements InterfaceC0943ai {
                    AnonymousClass1() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        synchronized (C0990cb.m1389a(C0990cb.this)) {
                            try {
                                C1002cn.m1458q("Invalid " + map.get("type") + " request error: " + map.get("errors"));
                                C0990cb.m1388a(C0990cb.this, 1);
                                C0990cb.m1389a(C0990cb.this).notify();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cb$2, reason: invalid class name */
                class AnonymousClass2 implements InterfaceC0943ai {
                    AnonymousClass2() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        synchronized (C0990cb.m1389a(C0990cb.this)) {
                            try {
                                String str = map.get(PlusShare.KEY_CALL_TO_ACTION_URL);
                                if (str == null) {
                                    C1002cn.m1458q("URL missing in loadAdUrl GMSG.");
                                } else {
                                    C0990cb.m1390a(C0990cb.this, str);
                                    C0990cb.m1389a(C0990cb.this).notify();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ int m1388a(C0990cb c0990cb, int i) {
                    c0990cb.f1531gw = i;
                    return i;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Object m1389a(C0990cb c0990cb) {
                    return c0990cb.f1526eJ;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ String m1390a(C0990cb c0990cb, String str) {
                    c0990cb.f1528gT = str;
                    return str;
                }

                /* JADX INFO: renamed from: aj */
                public String m1391aj() {
                    String str;
                    synchronized (this.f1526eJ) {
                        while (this.f1528gT == null && this.f1531gw == -2) {
                            try {
                                try {
                                    this.f1526eJ.wait();
                                } catch (InterruptedException e) {
                                    C1002cn.m1458q("Ad request service was interrupted.");
                                    str = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        str = this.f1528gT;
                    }
                    return str;
                }

                /* JADX INFO: renamed from: b */
                public void m1392b(C1005cq c1005cq) {
                    synchronized (this.f1526eJ) {
                        try {
                            this.f1527fG = c1005cq;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public int getErrorCode() {
                    int i;
                    synchronized (this.f1526eJ) {
                        try {
                            i = this.f1531gw;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }
            }
