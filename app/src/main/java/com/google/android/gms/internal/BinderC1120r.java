            package com.google.android.gms.internal;

            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.Bundle;
            import android.os.RemoteException;
            import android.util.DisplayMetrics;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ViewSwitcher;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public final class BinderC1120r extends InterfaceC0937ac.a implements InterfaceC0941ag, InterfaceC0951aq, InterfaceC0970bi, InterfaceC0973bl, C0977bp.a, InterfaceC1119q {

                
                private final InterfaceC0957aw f2139dZ;

                
                private final a f2140ea;

                
                private final C1121s f2141eb = new C1121s(this);

                
                private static final class a {
                    public final String adUnitId;

                    
                    public final ViewSwitcher f2142ec;

                    
                    public final C1126x f2143ed;

                    
                    public final Context f2144ee;

                    
                    public final C1110h f2145ef;

                    
                    public final C1003co f2146eg;

                    
                    public InterfaceC0936ab f2147eh;

                    
                    public AbstractC0995cg f2148ei;

                    
                    public C0993ce f2149ej;

                    
                    public InterfaceC0939ae f2150ek;

                    public a(Context context, C1126x c1126x, String str, C1003co c1003co) {
                        if (c1126x.f2164ex) {
                            this.f2142ec = null;
                        } else {
                            this.f2142ec = new ViewSwitcher(context);
                            this.f2142ec.setMinimumWidth(c1126x.widthPixels);
                            this.f2142ec.setMinimumHeight(c1126x.heightPixels);
                            this.f2142ec.setVisibility(4);
                        }
                        this.f2143ed = c1126x;
                        this.adUnitId = str;
                        this.f2144ee = context;
                        this.f2145ef = new C1110h(C1096g.m2070a(c1003co.f1596hP, context));
                        this.f2146eg = c1003co;
                    }
                }

                public BinderC1120r(Context context, C1126x c1126x, String str, InterfaceC0957aw interfaceC0957aw, C1003co c1003co) {
                    this.f2140ea = new a(context, c1126x, str, c1003co);
                    this.f2139dZ = interfaceC0957aw;
                    C1002cn.m1456o("Use AdRequest.Builder.addTestDevice(\"" + C1001cm.m1449l(context) + "\") to get test ads on this device.");
                    C0997ci.m1429i(context);
                }

                
                private void m2155I() {
                    C1002cn.m1456o("Ad closing.");
                    if (this.f2140ea.f2147eh != null) {
                        try {
                            this.f2140ea.f2147eh.onAdClosed();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call AdListener.onAdClosed().", e);
                        }
                    }
                }

                
                private void m2156J() {
                    C1002cn.m1456o("Ad leaving application.");
                    if (this.f2140ea.f2147eh != null) {
                        try {
                            this.f2140ea.f2147eh.onAdLeftApplication();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call AdListener.onAdLeftApplication().", e);
                        }
                    }
                }

                
                private void m2157K() {
                    C1002cn.m1456o("Ad opening.");
                    if (this.f2140ea.f2147eh != null) {
                        try {
                            this.f2140ea.f2147eh.onAdOpened();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call AdListener.onAdOpened().", e);
                        }
                    }
                }

                
                private void m2158L() {
                    C1002cn.m1456o("Ad finished loading.");
                    if (this.f2140ea.f2147eh != null) {
                        try {
                            this.f2140ea.f2147eh.onAdLoaded();
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call AdListener.onAdLoaded().", e);
                        }
                    }
                }

                
                private boolean m2159M() {
                    boolean z = true;
                    if (!C0997ci.m1420a(this.f2140ea.f2144ee.getPackageManager(), this.f2140ea.f2144ee.getPackageName(), "android.permission.INTERNET")) {
                        if (!this.f2140ea.f2143ed.f2164ex) {
                            C1001cm.m1444a(this.f2140ea.f2142ec, this.f2140ea.f2143ed, "Missing internet permission in AndroidManifest.xml.");
                        }
                        z = false;
                    }
                    if (!C0997ci.m1427h(this.f2140ea.f2144ee)) {
                        if (!this.f2140ea.f2143ed.f2164ex) {
                            C1001cm.m1444a(this.f2140ea.f2142ec, this.f2140ea.f2143ed, "Missing AdActivity with android:configChanges in AndroidManifest.xml.");
                        }
                        z = false;
                    }
                    if (!z) {
                        this.f2140ea.f2142ec.setVisibility(0);
                    }
                    return z;
                }

                
                private void m2160N() {
                    if (this.f2140ea.f2149ej == null) {
                        C1002cn.m1458q("Ad state was null when trying to ping click URLs.");
                        return;
                    }
                    C1002cn.m1454m("Pinging click URLs.");
                    if (this.f2140ea.f2149ej.f1563eW != null) {
                        C0997ci.m1415a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, this.f2140ea.f2149ej.f1563eW);
                    }
                    if (this.f2140ea.f2149ej.f1577hA == null || this.f2140ea.f2149ej.f1577hA.f1169eW == null) {
                        return;
                    }
                    C0955au.m1277a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, this.f2140ea.f2149ej, this.f2140ea.adUnitId, false, this.f2140ea.f2149ej.f1577hA.f1169eW);
                }

                
                private void m2161O() {
                    if (this.f2140ea.f2149ej != null) {
                        this.f2140ea.f2149ej.f1565fU.destroy();
                        this.f2140ea.f2149ej = null;
                    }
                }

                
                private void m2162a(int i) {
                    C1002cn.m1458q("Failed to load ad: " + i);
                    if (this.f2140ea.f2147eh != null) {
                        try {
                            this.f2140ea.f2147eh.onAdFailedToLoad(i);
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call AdListener.onAdFailedToLoad().", e);
                        }
                    }
                }

                
                private void m2163b(View view) {
                    this.f2140ea.f2142ec.addView(view, new ViewGroup.LayoutParams(-2, -2));
                }

                
                private void m2164b(boolean z) {
                    if (this.f2140ea.f2149ej == null) {
                        C1002cn.m1458q("Ad state was null when trying to ping impression URLs.");
                        return;
                    }
                    C1002cn.m1454m("Pinging Impression URLs.");
                    if (this.f2140ea.f2149ej.f1564eX != null) {
                        C0997ci.m1415a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, this.f2140ea.f2149ej.f1564eX);
                    }
                    if (this.f2140ea.f2149ej.f1577hA != null && this.f2140ea.f2149ej.f1577hA.f1170eX != null) {
                        C0955au.m1277a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, this.f2140ea.f2149ej, this.f2140ea.adUnitId, z, this.f2140ea.f2149ej.f1577hA.f1170eX);
                    }
                    if (this.f2140ea.f2149ej.f1567fm == null || this.f2140ea.f2149ej.f1567fm.f1166eT == null) {
                        return;
                    }
                    C0955au.m1277a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, this.f2140ea.f2149ej, this.f2140ea.adUnitId, z, this.f2140ea.f2149ej.f1567fm.f1166eT);
                }

                
                private boolean m2165b(C0993ce c0993ce) {
                    if (c0993ce.f1574gI) {
                        try {
                            View view = (View) BinderC0905c.m1165b(c0993ce.f1568fn.getView());
                            View nextView = this.f2140ea.f2142ec.getNextView();
                            if (nextView != null) {
                                this.f2140ea.f2142ec.removeView(nextView);
                            }
                            try {
                                m2163b(view);
                            } catch (Throwable th) {
                                C1002cn.m1452b("Could not add mediation view to view hierarchy.", th);
                                return false;
                            }
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not get View from mediation adapter.", e);
                            return false;
                        }
                    }
                    if (this.f2140ea.f2142ec.getChildCount() > 1) {
                        this.f2140ea.f2142ec.showNext();
                    }
                    if (this.f2140ea.f2149ej != null) {
                        View nextView2 = this.f2140ea.f2142ec.getNextView();
                        if (nextView2 instanceof C1005cq) {
                            ((C1005cq) nextView2).m1466a(this.f2140ea.f2144ee, this.f2140ea.f2143ed);
                        } else if (nextView2 != null) {
                            this.f2140ea.f2142ec.removeView(nextView2);
                        }
                        if (this.f2140ea.f2149ej.f1568fn != null) {
                            try {
                                this.f2140ea.f2149ej.f1568fn.destroy();
                            } catch (RemoteException e2) {
                                C1002cn.m1458q("Could not destroy previous mediation adapter.");
                            }
                        }
                    }
                    this.f2140ea.f2142ec.setVisibility(0);
                    return true;
                }

                
                private C0982bu.a m2166c(C1124v c1124v) {
                    PackageInfo packageInfo;
                    Bundle bundle;
                    ApplicationInfo applicationInfo = this.f2140ea.f2144ee.getApplicationInfo();
                    try {
                        packageInfo = this.f2140ea.f2144ee.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        packageInfo = null;
                    }
                    if (this.f2140ea.f2143ed.f2164ex || this.f2140ea.f2142ec.getParent() == null) {
                        bundle = null;
                    } else {
                        int[] iArr = new int[2];
                        this.f2140ea.f2142ec.getLocationOnScreen(iArr);
                        int i = iArr[0];
                        int i2 = iArr[1];
                        DisplayMetrics displayMetrics = this.f2140ea.f2144ee.getResources().getDisplayMetrics();
                        int width = this.f2140ea.f2142ec.getWidth();
                        int height = this.f2140ea.f2142ec.getHeight();
                        int i3 = (!this.f2140ea.f2142ec.isShown() || i + width <= 0 || i2 + height <= 0 || i > displayMetrics.widthPixels || i2 > displayMetrics.heightPixels) ? 0 : 1;
                        bundle = new Bundle(5);
                        bundle.putInt("x", i);
                        bundle.putInt("y", i2);
                        bundle.putInt("width", width);
                        bundle.putInt("height", height);
                        bundle.putInt("visible", i3);
                    }
                    return new C0982bu.a(bundle, c1124v, this.f2140ea.f2143ed, this.f2140ea.adUnitId, applicationInfo, packageInfo, C0994cf.m1408al(), C0994cf.f1579hB, this.f2140ea.f2146eg);
                }

                @Override 
                
                public void mo1337A() {
                    m2156J();
                }

                @Override 
                
                public void mo1323B() {
                    if (this.f2140ea.f2143ed.f2164ex) {
                        m2161O();
                    }
                    m2155I();
                }

                @Override 
                
                public void mo1324C() {
                    if (this.f2140ea.f2143ed.f2164ex) {
                        m2164b(false);
                    }
                    m2157K();
                }

                @Override 
                
                public void mo1256D() {
                    mo2154y();
                }

                @Override 
                
                public void mo1257E() {
                    mo1323B();
                }

                @Override 
                
                public void mo1258F() {
                    mo1337A();
                }

                @Override 
                
                public void mo1259G() {
                    mo1324C();
                }

                @Override 
                
                public void mo1260H() {
                    if (this.f2140ea.f2149ej != null) {
                        C1002cn.m1458q("Mediation adapter " + this.f2140ea.f2149ej.f1569fo + " refreshed, but mediation adapters should never refresh.");
                    }
                    m2164b(true);
                    m2158L();
                }

                @Override 
                
                public void mo1240a(InterfaceC0936ab interfaceC0936ab) {
                    C1028dm.m1608w("setAdListener must be called on the main UI thread.");
                    this.f2140ea.f2147eh = interfaceC0936ab;
                }

                @Override 
                
                public void mo1241a(InterfaceC0939ae interfaceC0939ae) {
                    C1028dm.m1608w("setAppEventListener must be called on the main UI thread.");
                    this.f2140ea.f2150ek = interfaceC0939ae;
                }

                @Override 
                
                public void mo1346a(C0993ce c0993ce) {
                    this.f2140ea.f2148ei = null;
                    if (c0993ce.errorCode == -1) {
                        return;
                    }
                    boolean z = c0993ce.f1571gB.extras != null ? c0993ce.f1571gB.extras.getBoolean("_noRefresh", false) : false;
                    if (this.f2140ea.f2143ed.f2164ex) {
                        C0997ci.m1417a(c0993ce.f1565fU);
                    } else if (!z) {
                        if (c0993ce.f1566fa > 0) {
                            this.f2141eb.m2170a(c0993ce.f1571gB, c0993ce.f1566fa);
                        } else if (c0993ce.f1577hA != null && c0993ce.f1577hA.f1173fa > 0) {
                            this.f2141eb.m2170a(c0993ce.f1571gB, c0993ce.f1577hA.f1173fa);
                        } else if (!c0993ce.f1574gI && c0993ce.errorCode == 2) {
                            this.f2141eb.m2171d(c0993ce.f1571gB);
                        }
                    }
                    if (c0993ce.errorCode == 3 && c0993ce.f1577hA != null && c0993ce.f1577hA.f1171eY != null) {
                        C1002cn.m1454m("Pinging no fill URLs.");
                        C0955au.m1277a(this.f2140ea.f2144ee, this.f2140ea.f2146eg.f1596hP, c0993ce, this.f2140ea.adUnitId, false, c0993ce.f1577hA.f1171eY);
                    }
                    if (c0993ce.errorCode != -2) {
                        m2162a(c0993ce.errorCode);
                        return;
                    }
                    if (!this.f2140ea.f2143ed.f2164ex && !m2165b(c0993ce)) {
                        m2162a(0);
                        return;
                    }
                    if (this.f2140ea.f2149ej != null && this.f2140ea.f2149ej.f1570fp != null) {
                        this.f2140ea.f2149ej.f1570fp.m1261a((InterfaceC0951aq) null);
                    }
                    if (c0993ce.f1570fp != null) {
                        c0993ce.f1570fp.m1261a(this);
                    }
                    this.f2140ea.f2149ej = c0993ce;
                    if (!this.f2140ea.f2143ed.f2164ex) {
                        m2164b(false);
                    }
                    m2158L();
                }

                @Override 
                
                public void mo1250a(String str, String str2) {
                    if (this.f2140ea.f2150ek != null) {
                        try {
                            this.f2140ea.f2150ek.mo1247a(str, str2);
                        } catch (RemoteException e) {
                            C1002cn.m1452b("Could not call the AppEventListener.", e);
                        }
                    }
                }

                @Override 
                
                public boolean mo1242a(C1124v c1124v) {
                    C1005cq c1005cqM1462a;
                    C1005cq c1005cq;
                    C1028dm.m1608w("loadAd must be called on the main UI thread.");
                    if (this.f2140ea.f2148ei != null) {
                        C1002cn.m1458q("An ad request is already in progress. Aborting.");
                        return false;
                    }
                    if (this.f2140ea.f2143ed.f2164ex && this.f2140ea.f2149ej != null) {
                        C1002cn.m1458q("An interstitial is already loading. Aborting.");
                        return false;
                    }
                    if (!m2159M()) {
                        return false;
                    }
                    C1002cn.m1456o("Starting ad request.");
                    this.f2141eb.cancel();
                    C0982bu.a aVarM2166c = m2166c(c1124v);
                    if (this.f2140ea.f2143ed.f2164ex) {
                        C1005cq c1005cqM1462a2 = C1005cq.m1462a(this.f2140ea.f2144ee, this.f2140ea.f2143ed, false, false, this.f2140ea.f2145ef, this.f2140ea.f2146eg);
                        c1005cqM1462a2.m1473aw().m1484a(this, null, this, this, true);
                        c1005cq = c1005cqM1462a2;
                    } else {
                        View nextView = this.f2140ea.f2142ec.getNextView();
                        if (nextView instanceof C1005cq) {
                            c1005cqM1462a = (C1005cq) nextView;
                            c1005cqM1462a.m1466a(this.f2140ea.f2144ee, this.f2140ea.f2143ed);
                        } else {
                            if (nextView != null) {
                                this.f2140ea.f2142ec.removeView(nextView);
                            }
                            c1005cqM1462a = C1005cq.m1462a(this.f2140ea.f2144ee, this.f2140ea.f2143ed, false, false, this.f2140ea.f2145ef, this.f2140ea.f2146eg);
                            m2163b(c1005cqM1462a);
                        }
                        c1005cqM1462a.m1473aw().m1484a(this, this, this, this, false);
                        c1005cq = c1005cqM1462a;
                    }
                    this.f2140ea.f2148ei = C0977bp.m1345a(this.f2140ea.f2144ee, aVarM2166c, this.f2140ea.f2145ef, c1005cq, this.f2139dZ, this);
                    return true;
                }

                
                public void m2167b(C1124v c1124v) {
                    Object parent = this.f2140ea.f2142ec.getParent();
                    if ((parent instanceof View) && ((View) parent).isShown() && C0997ci.m1421am()) {
                        mo1242a(c1124v);
                    } else {
                        C1002cn.m1456o("Ad is not visible. Not refreshing ad.");
                        this.f2141eb.m2171d(c1124v);
                    }
                }

                @Override 
                public void destroy() {
                    C1028dm.m1608w("destroy must be called on the main UI thread.");
                    this.f2140ea.f2147eh = null;
                    this.f2140ea.f2150ek = null;
                    this.f2141eb.cancel();
                    stopLoading();
                    if (this.f2140ea.f2142ec != null) {
                        this.f2140ea.f2142ec.removeAllViews();
                    }
                    if (this.f2140ea.f2149ej == null || this.f2140ea.f2149ej.f1565fU == null) {
                        return;
                    }
                    this.f2140ea.f2149ej.f1565fU.destroy();
                }

                @Override 
                public boolean isReady() {
                    C1028dm.m1608w("isReady must be called on the main UI thread.");
                    return this.f2140ea.f2148ei == null && this.f2140ea.f2149ej != null;
                }

                @Override 
                public void pause() {
                    C1028dm.m1608w("pause must be called on the main UI thread.");
                    if (this.f2140ea.f2149ej != null) {
                        C0997ci.m1417a(this.f2140ea.f2149ej.f1565fU);
                    }
                }

                @Override 
                public void resume() {
                    C1028dm.m1608w("resume must be called on the main UI thread.");
                    if (this.f2140ea.f2149ej != null) {
                        C0997ci.m1426b(this.f2140ea.f2149ej.f1565fU);
                    }
                }

                @Override 
                public void showInterstitial() {
                    C1028dm.m1608w("showInterstitial must be called on the main UI thread.");
                    if (!this.f2140ea.f2143ed.f2164ex) {
                        C1002cn.m1458q("Cannot call showInterstitial on a banner ad.");
                        return;
                    }
                    if (this.f2140ea.f2149ej == null) {
                        C1002cn.m1458q("The interstitial has not loaded.");
                        return;
                    }
                    if (this.f2140ea.f2149ej.f1565fU.m1476az()) {
                        C1002cn.m1458q("The interstitial is already showing.");
                        return;
                    }
                    this.f2140ea.f2149ej.f1565fU.m1477i(true);
                    if (!this.f2140ea.f2149ej.f1574gI) {
                        BinderC0967bf.m1302a(this.f2140ea.f2144ee, new C0969bh(this, this, this, this.f2140ea.f2149ej.f1565fU, this.f2140ea.f2149ej.orientation, this.f2140ea.f2146eg));
                        return;
                    }
                    try {
                        this.f2140ea.f2149ej.f1568fn.showInterstitial();
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not show interstitial.", e);
                        m2161O();
                    }
                }

                @Override 
                public void stopLoading() {
                    C1028dm.m1608w("stopLoading must be called on the main UI thread.");
                    if (this.f2140ea.f2149ej != null) {
                        this.f2140ea.f2149ej.f1565fU.stopLoading();
                        this.f2140ea.f2149ej = null;
                    }
                    if (this.f2140ea.f2148ei != null) {
                        this.f2140ea.f2148ei.cancel();
                    }
                }

                @Override 
                
                public void mo2154y() {
                    m2160N();
                }

                @Override 
                
                public InterfaceC0904b mo1243z() {
                    C1028dm.m1608w("getAdFrame must be called on the main UI thread.");
                    return BinderC0905c.m1166g(this.f2140ea.f2142ec);
                }
            }
