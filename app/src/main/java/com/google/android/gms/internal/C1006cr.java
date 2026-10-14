            package com.google.android.gms.internal;

            import android.net.Uri;
            import android.net.UrlQuerySanitizer;
            import android.webkit.WebView;
            import android.webkit.WebViewClient;
            import java.util.HashMap;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cr */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1006cr extends WebViewClient {

                /* JADX INFO: renamed from: ey */
                private InterfaceC0941ag f1610ey;

                /* JADX INFO: renamed from: fG */
                protected final C1005cq f1611fG;

                /* JADX INFO: renamed from: ia */
                private InterfaceC1119q f1613ia;

                /* JADX INFO: renamed from: ib */
                private InterfaceC0970bi f1614ib;

                /* JADX INFO: renamed from: ic */
                private a f1615ic;

                /* JADX INFO: renamed from: ie */
                private boolean f1617ie;

                /* JADX INFO: renamed from: if */
                private InterfaceC0973bl f1618if;

                /* JADX INFO: renamed from: hZ */
                private final HashMap<String, InterfaceC0943ai> f1612hZ = new HashMap<>();

                /* JADX INFO: renamed from: eJ */
                private final Object f1609eJ = new Object();

                /* JADX INFO: renamed from: id */
                private boolean f1616id = false;

                /* JADX INFO: renamed from: com.google.android.gms.internal.cr$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {

                    /* JADX INFO: renamed from: ig */
                    final /* synthetic */ BinderC0967bf f1619ig;

                    AnonymousClass1(BinderC0967bf binderC0967bf) {
                        this.f1619ig = binderC0967bf;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        this.f1619ig.m1306S();
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cr$a */
                public interface a {
                    /* JADX INFO: renamed from: a */
                    void mo1312a(C1005cq c1005cq);
                }

                public C1006cr(C1005cq c1005cq, boolean z) {
                    this.f1611fG = c1005cq;
                    this.f1617ie = z;
                }

                /* JADX INFO: renamed from: a */
                private void m1478a(C0969bh c0969bh) {
                    BinderC0967bf.m1302a(this.f1611fG.getContext(), c0969bh);
                }

                /* JADX INFO: renamed from: b */
                private static boolean m1479b(Uri uri) {
                    String scheme = uri.getScheme();
                    return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
                }

                /* JADX INFO: renamed from: c */
                private void m1480c(Uri uri) {
                    String path = uri.getPath();
                    InterfaceC0943ai interfaceC0943ai = this.f1612hZ.get(path);
                    if (interfaceC0943ai == null) {
                        C1002cn.m1458q("No GMSG handler found for GMSG: " + uri);
                        return;
                    }
                    HashMap map = new HashMap();
                    UrlQuerySanitizer urlQuerySanitizer = new UrlQuerySanitizer();
                    urlQuerySanitizer.setAllowUnregisteredParamaters(true);
                    urlQuerySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
                    urlQuerySanitizer.parseUrl(uri.toString());
                    for (UrlQuerySanitizer.ParameterValuePair parameterValuePair : urlQuerySanitizer.getParameterList()) {
                        map.put(parameterValuePair.mParameter, parameterValuePair.mValue);
                    }
                    if (C1002cn.m1453k(2)) {
                        C1002cn.m1457p("Received GMSG: " + path);
                        for (String str : map.keySet()) {
                            C1002cn.m1457p("  " + str + ": " + ((String) map.get(str)));
                        }
                    }
                    interfaceC0943ai.mo1249a(this.f1611fG, map);
                }

                /* JADX INFO: renamed from: S */
                public final void m1481S() {
                    synchronized (this.f1609eJ) {
                        try {
                            this.f1616id = false;
                            this.f1617ie = true;
                            BinderC0967bf binderC0967bfM1471au = this.f1611fG.m1471au();
                            if (binderC0967bfM1471au != null) {
                                if (C1001cm.m1447ar()) {
                                    binderC0967bfM1471au.m1306S();
                                } else {
                                    C1001cm.f1595hO.post(new AnonymousClass1(binderC0967bfM1471au));
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                public final void m1482a(C0966be c0966be) {
                    boolean zM1476az = this.f1611fG.m1476az();
                    m1478a(new C0969bh(c0966be, (!zM1476az || this.f1611fG.m1472av().f2164ex) ? this.f1613ia : null, zM1476az ? null : this.f1614ib, this.f1618if, this.f1611fG.m1475ay()));
                }

                /* JADX INFO: renamed from: a */
                public final void m1483a(a aVar) {
                    this.f1615ic = aVar;
                }

                /* JADX INFO: renamed from: a */
                public void m1484a(InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0941ag interfaceC0941ag, InterfaceC0973bl interfaceC0973bl, boolean z) {
                    m1485a("/appEvent", new C0940af(interfaceC0941ag));
                    m1485a("/canOpenURLs", C0942ah.f1153ez);
                    m1485a("/click", C0942ah.f1145eA);
                    m1485a("/close", C0942ah.f1146eB);
                    m1485a("/customClose", C0942ah.f1147eC);
                    m1485a("/httpTrack", C0942ah.f1148eD);
                    m1485a("/log", C0942ah.f1149eE);
                    m1485a("/open", C0942ah.f1150eF);
                    m1485a("/touch", C0942ah.f1151eG);
                    m1485a("/video", C0942ah.f1152eH);
                    this.f1613ia = interfaceC1119q;
                    this.f1614ib = interfaceC0970bi;
                    this.f1610ey = interfaceC0941ag;
                    this.f1618if = interfaceC0973bl;
                    m1490j(z);
                }

                /* JADX INFO: renamed from: a */
                public final void m1485a(String str, InterfaceC0943ai interfaceC0943ai) {
                    this.f1612hZ.put(str, interfaceC0943ai);
                }

                /* JADX INFO: renamed from: a */
                public final void m1486a(boolean z, int i) {
                    m1478a(new C0969bh((!this.f1611fG.m1476az() || this.f1611fG.m1472av().f2164ex) ? this.f1613ia : null, this.f1614ib, this.f1618if, this.f1611fG, z, i, this.f1611fG.m1475ay()));
                }

                /* JADX INFO: renamed from: a */
                public final void m1487a(boolean z, int i, String str) {
                    boolean zM1476az = this.f1611fG.m1476az();
                    m1478a(new C0969bh((!zM1476az || this.f1611fG.m1472av().f2164ex) ? this.f1613ia : null, zM1476az ? null : this.f1614ib, this.f1610ey, this.f1618if, this.f1611fG, z, i, str, this.f1611fG.m1475ay()));
                }

                /* JADX INFO: renamed from: a */
                public final void m1488a(boolean z, int i, String str, String str2) {
                    boolean zM1476az = this.f1611fG.m1476az();
                    m1478a(new C0969bh((!zM1476az || this.f1611fG.m1472av().f2164ex) ? this.f1613ia : null, zM1476az ? null : this.f1614ib, this.f1610ey, this.f1618if, this.f1611fG, z, i, str, str2, this.f1611fG.m1475ay()));
                }

                /* JADX INFO: renamed from: aD */
                public boolean m1489aD() {
                    boolean z;
                    synchronized (this.f1609eJ) {
                        try {
                            z = this.f1617ie;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                /* JADX INFO: renamed from: j */
                public final void m1490j(boolean z) {
                    this.f1616id = z;
                }

                @Override // android.webkit.WebViewClient
                public final void onPageFinished(WebView webView, String url) {
                    if (this.f1615ic != null) {
                        this.f1615ic.mo1312a(this.f1611fG);
                        this.f1615ic = null;
                    }
                }

                public final void reset() {
                    synchronized (this.f1609eJ) {
                        try {
                            this.f1612hZ.clear();
                            this.f1613ia = null;
                            this.f1614ib = null;
                            this.f1615ic = null;
                            this.f1610ey = null;
                            this.f1616id = false;
                            this.f1617ie = false;
                            this.f1618if = null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.webkit.WebViewClient
                public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
                    Uri uri;
                    C1002cn.m1457p("AdWebView shouldOverrideUrlLoading: " + url);
                    Uri uriM2125a = Uri.parse(url);
                    if ("gmsg".equalsIgnoreCase(uriM2125a.getScheme()) && "mobileads.google.com".equalsIgnoreCase(uriM2125a.getHost())) {
                        m1480c(uriM2125a);
                    } else {
                        if (this.f1616id && m1479b(uriM2125a)) {
                            return super.shouldOverrideUrlLoading(webView, url);
                        }
                        if (this.f1611fG.willNotDraw()) {
                            C1002cn.m1458q("AdWebView unable to handle URL: " + url);
                        } else {
                            try {
                                C1110h c1110hM1474ax = this.f1611fG.m1474ax();
                                if (c1110hM1474ax != null && c1110hM1474ax.m2127a(uriM2125a)) {
                                    uriM2125a = c1110hM1474ax.m2125a(uriM2125a, this.f1611fG.getContext());
                                }
                                uri = uriM2125a;
                            } catch (C1111i e) {
                                C1002cn.m1458q("Unable to append parameter to URL: " + url);
                                uri = uriM2125a;
                            }
                            m1482a(new C0966be("android.intent.action.VIEW", uri.toString(), null, null, null, null, null));
                        }
                    }
                    return true;
                }
            }
