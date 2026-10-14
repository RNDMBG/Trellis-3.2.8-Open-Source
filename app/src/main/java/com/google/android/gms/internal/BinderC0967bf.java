            package com.google.android.gms.internal;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Build;
            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.view.Window;
            import android.webkit.WebChromeClient;
            import android.widget.FrameLayout;
            import android.widget.RelativeLayout;
            import com.google.android.gms.ads.AdActivity;
            import com.vandalsoftware.p004io.IoUtils;
            
            
            
            public final class BinderC0967bf extends InterfaceC0975bn.a {

                
                private final Activity f1428fD;

                
                private C0969bh f1429fE;

                
                private C0971bj f1430fF;

                
                private C1005cq f1431fG;

                
                private b f1432fH;

                
                private ViewOnClickListenerC0972bk f1433fI;

                
                private FrameLayout f1434fJ;

                
                private WebChromeClient.CustomViewCallback f1435fK;

                
                private boolean f1436fL = false;

                
                private boolean f1437fM = false;

                
                private RelativeLayout f1438fN;

                
                class AnonymousClass1 implements C1006cr.a {
                    AnonymousClass1() {
                    }

                    @Override 
                    
                    public void mo1312a(C1005cq c1005cq) {
                        c1005cq.m1470at();
                    }
                }

                
                private static final class a extends Exception {
                    public a(String str) {
                        super(str);
                    }
                }

                
                private static final class b {

                    
                    public final ViewGroup.LayoutParams f1440fP;

                    
                    public final ViewGroup f1441fQ;
                    public final int index;

                    public b(C1005cq c1005cq) throws a {
                        this.f1440fP = c1005cq.getLayoutParams();
                        ViewParent parent = c1005cq.getParent();
                        if (!(parent instanceof ViewGroup)) {
                            throw new a("Could not get the parent of the WebView for an overlay.");
                        }
                        this.f1441fQ = (ViewGroup) parent;
                        this.index = this.f1441fQ.indexOfChild(c1005cq);
                        this.f1441fQ.removeView(c1005cq);
                        c1005cq.m1477i(true);
                    }
                }

                public BinderC0967bf(Activity activity) {
                    this.f1428fD = activity;
                }

                
                private void m1300T() {
                    if (!this.f1428fD.isFinishing() || this.f1437fM) {
                        return;
                    }
                    this.f1437fM = true;
                    if (this.f1428fD.isFinishing()) {
                        if (this.f1431fG != null) {
                            this.f1431fG.m1469as();
                            this.f1438fN.removeView(this.f1431fG);
                            if (this.f1432fH != null) {
                                this.f1431fG.m1477i(false);
                                this.f1432fH.f1441fQ.addView(this.f1431fG, this.f1432fH.index, this.f1432fH.f1440fP);
                            }
                        }
                        if (this.f1429fE == null || this.f1429fE.f1445fT == null) {
                            return;
                        }
                        this.f1429fE.f1445fT.mo1323B();
                    }
                }

                
                private static RelativeLayout.LayoutParams m1301a(int i, int i2, int i3, int i4) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i4);
                    layoutParams.setMargins(i, i2, 0, 0);
                    layoutParams.addRule(10);
                    layoutParams.addRule(9);
                    return layoutParams;
                }

                
                public static void m1302a(Context context, C0969bh c0969bh) {
                    Intent intent = new Intent();
                    intent.setClassName(context, AdActivity.CLASS_NAME);
                    intent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", c0969bh.f1442eg.f1599hS);
                    C0969bh.m1317a(intent, c0969bh);
                    intent.addFlags(524288);
                    context.startActivity(intent);
                }

                
                private void m1303e(boolean z) throws a {
                    this.f1428fD.requestWindowFeature(1);
                    Window window = this.f1428fD.getWindow();
                    window.setFlags(1024, 1024);
                    setRequestedOrientation(this.f1429fE.orientation);
                    if (Build.VERSION.SDK_INT >= 11) {
                        C1002cn.m1454m("Enabling hardware acceleration on the AdActivity window.");
                        C0998cj.m1436a(window);
                    }
                    this.f1438fN = new RelativeLayout(this.f1428fD);
                    this.f1438fN.setBackgroundColor(-16777216);
                    this.f1428fD.setContentView(this.f1438fN);
                    boolean zM1489aD = this.f1429fE.f1446fU.m1473aw().m1489aD();
                    if (z) {
                        this.f1431fG = C1005cq.m1462a(this.f1428fD, this.f1429fE.f1446fU.m1472av(), true, zM1489aD, null, this.f1429fE.f1442eg);
                        this.f1431fG.m1473aw().m1484a(null, null, this.f1429fE.f1447fV, this.f1429fE.f1451fZ, true);
                        this.f1431fG.m1473aw().m1483a(new AnonymousClass1());
                        if (this.f1429fE.f1452fz != null) {
                            this.f1431fG.loadUrl(this.f1429fE.f1452fz);
                        } else {
                            if (this.f1429fE.f1450fY == null) {
                                throw new a("No URL or HTML to display in ad overlay.");
                            }
                            this.f1431fG.loadDataWithBaseURL(this.f1429fE.f1448fW, this.f1429fE.f1450fY, "text/html", IoUtils.UTF_8, null);
                        }
                    } else {
                        this.f1431fG = this.f1429fE.f1446fU;
                        this.f1431fG.setContext(this.f1428fD);
                    }
                    this.f1431fG.m1467a(this);
                    this.f1438fN.addView(this.f1431fG, -1, -1);
                    if (!z) {
                        this.f1431fG.m1470at();
                    }
                    m1310c(zM1489aD);
                }

                
                public C0971bj m1304Q() {
                    return this.f1430fF;
                }

                
                public void m1305R() {
                    if (this.f1429fE != null) {
                        setRequestedOrientation(this.f1429fE.orientation);
                    }
                    if (this.f1434fJ != null) {
                        this.f1428fD.setContentView(this.f1438fN);
                        this.f1434fJ.removeAllViews();
                        this.f1434fJ = null;
                    }
                    if (this.f1435fK != null) {
                        this.f1435fK.onCustomViewHidden();
                        this.f1435fK = null;
                    }
                }

                
                public void m1306S() {
                    this.f1438fN.removeView(this.f1433fI);
                    m1310c(true);
                }

                
                public void m1307a(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                    this.f1434fJ = new FrameLayout(this.f1428fD);
                    this.f1434fJ.setBackgroundColor(-16777216);
                    this.f1434fJ.addView(view, -1, -1);
                    this.f1428fD.setContentView(this.f1434fJ);
                    this.f1435fK = customViewCallback;
                }

                
                public void m1308b(int i, int i2, int i3, int i4) {
                    if (this.f1430fF != null) {
                        this.f1430fF.setLayoutParams(m1301a(i, i2, i3, i4));
                    }
                }

                
                public void m1309c(int i, int i2, int i3, int i4) {
                    if (this.f1430fF == null) {
                        this.f1430fF = new C0971bj(this.f1428fD, this.f1431fG);
                        this.f1438fN.addView(this.f1430fF, 0, m1301a(i, i2, i3, i4));
                        this.f1431fG.m1473aw().m1490j(false);
                    }
                }

                
                public void m1310c(boolean z) {
                    this.f1433fI = new ViewOnClickListenerC0972bk(this.f1428fD, z ? 50 : 32);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                    layoutParams.addRule(10);
                    layoutParams.addRule(z ? 11 : 9);
                    this.f1433fI.m1336d(this.f1429fE.f1449fX);
                    this.f1438fN.addView(this.f1433fI, layoutParams);
                }

                public void close() {
                    this.f1428fD.finish();
                }

                
                public void m1311d(boolean z) {
                    if (this.f1433fI != null) {
                        this.f1433fI.m1336d(z);
                    }
                }

                @Override 
                public void onCreate(Bundle savedInstanceState) {
                    this.f1436fL = savedInstanceState != null ? savedInstanceState.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false) : false;
                    try {
                        this.f1429fE = C0969bh.m1316a(this.f1428fD.getIntent());
                        if (this.f1429fE == null) {
                            throw new a("Could not get info for ad overlay.");
                        }
                        if (savedInstanceState == null) {
                            if (this.f1429fE.f1445fT != null) {
                                this.f1429fE.f1445fT.mo1324C();
                            }
                            if (this.f1429fE.f1453ga != 1 && this.f1429fE.f1444fS != null) {
                                this.f1429fE.f1444fS.mo2154y();
                            }
                        }
                        switch (this.f1429fE.f1453ga) {
                            case 1:
                                m1303e(false);
                                return;
                            case 2:
                                this.f1432fH = new b(this.f1429fE.f1446fU);
                                m1303e(false);
                                return;
                            case 3:
                                m1303e(true);
                                return;
                            case 4:
                                if (this.f1436fL) {
                                    this.f1428fD.finish();
                                    return;
                                } else {
                                    if (C0964bc.m1296a(this.f1428fD, this.f1429fE.f1443fR, this.f1429fE.f1451fZ)) {
                                        return;
                                    }
                                    this.f1428fD.finish();
                                    return;
                                }
                            default:
                                throw new a("Could not determine ad overlay type.");
                        }
                    } catch (a e) {
                        C1002cn.m1458q(e.getMessage());
                        this.f1428fD.finish();
                    }
                }

                @Override 
                public void onDestroy() {
                    if (this.f1430fF != null) {
                        this.f1430fF.destroy();
                    }
                    if (this.f1431fG != null) {
                        this.f1438fN.removeView(this.f1431fG);
                    }
                    m1300T();
                }

                @Override 
                public void onPause() {
                    if (this.f1430fF != null) {
                        this.f1430fF.pause();
                    }
                    m1305R();
                    if (this.f1431fG != null && (!this.f1428fD.isFinishing() || this.f1432fH == null)) {
                        C0997ci.m1417a(this.f1431fG);
                    }
                    m1300T();
                }

                @Override 
                public void onRestart() {
                }

                @Override 
                public void onResume() {
                    if (this.f1429fE != null && this.f1429fE.f1453ga == 4) {
                        if (this.f1436fL) {
                            this.f1428fD.finish();
                        } else {
                            this.f1436fL = true;
                        }
                    }
                    if (this.f1431fG != null) {
                        C0997ci.m1426b(this.f1431fG);
                    }
                }

                @Override 
                public void onSaveInstanceState(Bundle outBundle) {
                    outBundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.f1436fL);
                }

                @Override 
                public void onStart() {
                }

                @Override 
                public void onStop() {
                    m1300T();
                }

                public void setRequestedOrientation(int requestedOrientation) {
                    this.f1428fD.setRequestedOrientation(requestedOrientation);
                }
            }
