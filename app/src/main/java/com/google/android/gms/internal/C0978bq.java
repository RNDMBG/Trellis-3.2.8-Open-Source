            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.SystemClock;
            import android.text.TextUtils;
            import co.vine.android.util.CommonUtil;
            import com.vandalsoftware.p004io.IoUtils;
            import org.json.JSONException;
            
            
            
            public final class C0978bq extends AbstractC0995cg implements C0979br.a, C1006cr.a {

                
                private final InterfaceC0957aw f1470dZ;

                
                private C0950ap f1472eK;

                
                private final C1005cq f1473fG;

                
                private final C0977bp.a f1474gm;

                
                private final C0982bu.a f1475gn;

                
                private final C1110h f1476go;

                
                private AbstractC0995cg f1477gp;

                
                private C0984bw f1478gq;

                
                private C0948an f1480gs;

                
                private C0954at f1481gt;
                private final Context mContext;

                
                private final Object f1471eJ = new Object();

                
                private boolean f1479gr = false;

                
                class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override 
                    public void run() {
                        C0978bq.this.onStop();
                    }
                }

                
                class AnonymousClass2 implements Runnable {

                    
                    final  C0993ce f1484gv;

                    AnonymousClass2(C0993ce c0993ce) {
                        this.f1484gv = c0993ce;
                    }

                    @Override 
                    public void run() {
                        synchronized (C0978bq.m1347a(C0978bq.this)) {
                            try {
                                C0978bq.m1350b(C0978bq.this).mo1346a(this.f1484gv);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
                class AnonymousClass3 implements Runnable {
                    AnonymousClass3() {
                    }

                    @Override 
                    public void run() {
                        synchronized (C0978bq.m1347a(C0978bq.this)) {
                            try {
                                if (C0978bq.m1352c(C0978bq.this).errorCode != -2) {
                                    return;
                                }
                                C0978bq.m1354d(C0978bq.this).m1473aw().m1483a(C0978bq.this);
                                if (C0978bq.m1352c(C0978bq.this).errorCode == -3) {
                                    C1002cn.m1457p("Loading URL in WebView: " + C0978bq.m1352c(C0978bq.this).f1510fW);
                                    C0978bq.m1354d(C0978bq.this).loadUrl(C0978bq.m1352c(C0978bq.this).f1510fW);
                                } else {
                                    C1002cn.m1457p("Loading HTML in WebView.");
                                    C0978bq.m1354d(C0978bq.this).loadDataWithBaseURL(C0997ci.m1431j(C0978bq.m1352c(C0978bq.this).f1510fW), C0978bq.m1352c(C0978bq.this).f1512gG, "text/html", IoUtils.UTF_8, null);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
                private static final class a extends Exception {

                    
                    private final int f1486gw;

                    public a(String str, int i) {
                        super(str);
                        this.f1486gw = i;
                    }

                    public int getErrorCode() {
                        return this.f1486gw;
                    }
                }

                public C0978bq(Context context, C0982bu.a aVar, C1110h c1110h, C1005cq c1005cq, InterfaceC0957aw interfaceC0957aw, C0977bp.a aVar2) {
                    this.f1470dZ = interfaceC0957aw;
                    this.f1474gm = aVar2;
                    this.f1473fG = c1005cq;
                    this.mContext = context;
                    this.f1475gn = aVar;
                    this.f1476go = c1110h;
                }

                
                static  Object m1347a(C0978bq c0978bq) {
                    return c0978bq.f1471eJ;
                }

                
                private void m1348a(C0982bu c0982bu, long j) throws a {
                    this.f1480gs = new C0948an(this.mContext, c0982bu, this.f1470dZ, this.f1472eK);
                    this.f1481gt = this.f1480gs.m1255a(j, CommonUtil.MINUTE_MILLIS);
                    switch (this.f1481gt.f1188fl) {
                        case 0:
                            return;
                        case 1:
                            throw new a("No fill from any mediation ad networks.", 3);
                        default:
                            throw new a("Unexpected mediation result: " + this.f1481gt.f1188fl, 0);
                    }
                }

                
                private void m1349ad() throws a {
                    if (this.f1478gq.errorCode == -3) {
                        return;
                    }
                    if (TextUtils.isEmpty(this.f1478gq.f1512gG)) {
                        throw new a("No fill from ad server.", 3);
                    }
                    if (this.f1478gq.f1514gI) {
                        try {
                            this.f1472eK = new C0950ap(this.f1478gq.f1512gG);
                        } catch (JSONException e) {
                            throw new a("Could not parse mediation config: " + this.f1478gq.f1512gG, 0);
                        }
                    }
                }

                
                static  C0977bp.a m1350b(C0978bq c0978bq) {
                    return c0978bq.f1474gm;
                }

                
                private void m1351b(long j) throws a {
                    C1001cm.f1595hO.post(new AnonymousClass3());
                    m1355d(j);
                }

                
                static  C0984bw m1352c(C0978bq c0978bq) {
                    return c0978bq.f1478gq;
                }

                
                private void m1353c(long j) throws a {
                    while (m1356e(j)) {
                        if (this.f1478gq != null) {
                            this.f1477gp = null;
                            if (this.f1478gq.errorCode != -2 && this.f1478gq.errorCode != -3) {
                                throw new a("There was a problem getting an ad response. ErrorCode: " + this.f1478gq.errorCode, this.f1478gq.errorCode);
                            }
                            return;
                        }
                    }
                    throw new a("Timed out waiting for ad response.", 2);
                }

                
                static  C1005cq m1354d(C0978bq c0978bq) {
                    return c0978bq.f1473fG;
                }

                
                private void m1355d(long j) throws a {
                    while (m1356e(j)) {
                        if (this.f1479gr) {
                            return;
                        }
                    }
                    throw new a("Timed out waiting for WebView to finish loading.", 2);
                }

                
                private boolean m1356e(long j) throws a {
                    long jElapsedRealtime = CommonUtil.MINUTE_MILLIS - (SystemClock.elapsedRealtime() - j);
                    if (jElapsedRealtime <= 0) {
                        return false;
                    }
                    try {
                        this.f1471eJ.wait(jElapsedRealtime);
                        return true;
                    } catch (InterruptedException e) {
                        throw new a("Ad request cancelled.", -1);
                    }
                }

                @Override 
                
                public void mo1357a(C0984bw c0984bw) {
                    synchronized (this.f1471eJ) {
                        try {
                            C1002cn.m1454m("Received ad response.");
                            this.f1478gq = c0984bw;
                            this.f1471eJ.notify();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                
                public void mo1312a(C1005cq c1005cq) {
                    synchronized (this.f1471eJ) {
                        try {
                            C1002cn.m1454m("WebView finished loading.");
                            this.f1479gr = true;
                            this.f1471eJ.notify();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                
                public void mo1358ac() {
                    synchronized (this.f1471eJ) {
                        try {
                            C1002cn.m1454m("AdLoaderBackgroundTask started.");
                            C0982bu c0982bu = new C0982bu(this.f1475gn, this.f1476go.m2128g().mo1516a(this.mContext));
                            int errorCode = -2;
                            try {
                                long jElapsedRealtime = SystemClock.elapsedRealtime();
                                this.f1477gp = C0979br.m1359a(this.mContext, c0982bu, this);
                                if (this.f1477gp == null) {
                                    throw new a("Could not start the ad request service.", 0);
                                }
                                m1353c(jElapsedRealtime);
                                m1349ad();
                                if (this.f1478gq.f1514gI) {
                                    m1348a(c0982bu, jElapsedRealtime);
                                } else {
                                    m1351b(jElapsedRealtime);
                                }
                                C1001cm.f1595hO.post(new AnonymousClass2(new C0993ce(c0982bu.f1496gB, this.f1473fG, this.f1478gq.f1508eW, errorCode, this.f1478gq.f1509eX, this.f1478gq.f1516gK, this.f1478gq.orientation, this.f1478gq.f1511fa, c0982bu.f1499gE, this.f1478gq.f1514gI, this.f1481gt != null ? this.f1481gt.f1189fm : null, this.f1481gt != null ? this.f1481gt.f1190fn : null, this.f1481gt != null ? this.f1481gt.f1191fo : null, this.f1472eK, this.f1481gt != null ? this.f1481gt.f1192fp : null, this.f1478gq.f1515gJ, this.f1478gq.f1513gH)));
                            } catch (a e) {
                                errorCode = e.getErrorCode();
                                if (errorCode == 3 || errorCode == -1) {
                                    C1002cn.m1456o(e.getMessage());
                                } else {
                                    C1002cn.m1458q(e.getMessage());
                                }
                                this.f1478gq = new C0984bw(errorCode);
                                C1001cm.f1595hO.post(new AnonymousClass1());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onStop() {
                    synchronized (this.f1471eJ) {
                        try {
                            if (this.f1477gp != null) {
                                this.f1477gp.cancel();
                            }
                            this.f1473fG.stopLoading();
                            C0997ci.m1417a(this.f1473fG);
                            if (this.f1480gs != null) {
                                this.f1480gs.cancel();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
