            package com.google.android.gms.internal;

            import android.content.Context;
            import android.text.TextUtils;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.io.InputStreamReader;
            import java.net.HttpURLConnection;
            import java.net.URL;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            
            
            
            public final class BinderC0987bz extends InterfaceC0986by.a {

                
                private static final Object f1518gL = new Object();

                
                private static BinderC0987bz f1519gM;

                
                private final InterfaceC0946al f1520gN;
                private final Context mContext;

                
                static class AnonymousClass1 implements Runnable {

                    
                    final  Context f1521gO;

                    
                    final  C0982bu f1522gP;

                    
                    final  C0990cb f1523gQ;

                    
                    final  String f1524gR;

                    AnonymousClass1(Context context, C0982bu c0982bu, C0990cb c0990cb, String str) {
                        this.f1521gO = context;
                        this.f1522gP = c0982bu;
                        this.f1523gQ = c0990cb;
                        this.f1524gR = str;
                    }

                    @Override 
                    public void run() {
                        C1005cq c1005cqM1462a = C1005cq.m1462a(this.f1521gO, new C1126x(), false, false, null, this.f1522gP.f1494eg);
                        c1005cqM1462a.setWillNotDraw(true);
                        this.f1523gQ.m1392b(c1005cqM1462a);
                        C1006cr c1006crM1473aw = c1005cqM1462a.m1473aw();
                        c1006crM1473aw.m1485a("/invalidRequest", this.f1523gQ.f1529gU);
                        c1006crM1473aw.m1485a("/loadAdURL", this.f1523gQ.f1530gV);
                        c1006crM1473aw.m1485a("/log", C0942ah.f1149eE);
                        C1002cn.m1454m("Getting the ad request URL.");
                        c1005cqM1462a.loadDataWithBaseURL("http://googleads.g.doubleclick.net", "<!DOCTYPE html><html><head><script src=\"http://googleads.g.doubleclick.net/mads/static/sdk/native/sdk-core-v40.js\"></script><script>AFMA_buildAdURL(" + this.f1524gR + ");</script></head><body></body></html>", "text/html", IoUtils.UTF_8, null);
                    }
                }

                private BinderC0987bz(Context context, InterfaceC0946al interfaceC0946al) {
                    this.mContext = context;
                    this.f1520gN = interfaceC0946al;
                }

                
                private static C0984bw m1379a(Context context, InterfaceC0946al interfaceC0946al, C0982bu c0982bu) {
                    C1002cn.m1454m("Starting ad request from service.");
                    interfaceC0946al.init();
                    C0992cd c0992cd = new C0992cd(context);
                    if (c0992cd.f1555hs == -1) {
                        C1002cn.m1454m("Device is offline.");
                        return new C0984bw(2);
                    }
                    C0990cb c0990cb = new C0990cb();
                    String strM1383a = C0989ca.m1383a(c0982bu, c0992cd, interfaceC0946al.mo1254a(250L));
                    if (strM1383a == null) {
                        return new C0984bw(0);
                    }
                    C1001cm.f1595hO.post(new AnonymousClass1(context, c0982bu, c0990cb, strM1383a));
                    String strM1391aj = c0990cb.m1391aj();
                    return TextUtils.isEmpty(strM1391aj) ? new C0984bw(c0990cb.getErrorCode()) : m1380a(context, c0982bu.f1494eg.f1596hP, strM1391aj);
                }

                
                private static C0984bw m1380a(Context context, String str, String str2) {
                    try {
                        C0991cc c0991cc = new C0991cc();
                        URL url = new URL(str2);
                        int i = 0;
                        while (true) {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            try {
                                C0997ci.m1416a(context, str, false, httpURLConnection);
                                int responseCode = httpURLConnection.getResponseCode();
                                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                                if (responseCode >= 200 && responseCode < 300) {
                                    String string = url.toString();
                                    String strM1410a = C0997ci.m1410a(new InputStreamReader(httpURLConnection.getInputStream()));
                                    m1382a(string, headerFields, strM1410a, responseCode);
                                    c0991cc.m1402a(string, headerFields, strM1410a);
                                    C0984bw c0984bwM1403ak = c0991cc.m1403ak();
                                    httpURLConnection.disconnect();
                                    return c0984bwM1403ak;
                                }
                                m1382a(url.toString(), headerFields, null, responseCode);
                                if (responseCode < 300 || responseCode >= 400) {
                                    C1002cn.m1458q("Received error HTTP response code: " + responseCode);
                                    C0984bw c0984bw = new C0984bw(0);
                                    httpURLConnection.disconnect();
                                    return c0984bw;
                                }
                                String headerField = httpURLConnection.getHeaderField("Location");
                                if (TextUtils.isEmpty(headerField)) {
                                    C1002cn.m1458q("No location header to follow redirect.");
                                    C0984bw c0984bw2 = new C0984bw(0);
                                    httpURLConnection.disconnect();
                                    return c0984bw2;
                                }
                                url = new URL(headerField);
                                i++;
                                if (i > 5) {
                                    C1002cn.m1458q("Too many redirects.");
                                    C0984bw c0984bw3 = new C0984bw(0);
                                    httpURLConnection.disconnect();
                                    return c0984bw3;
                                }
                                c0991cc.m1404d(headerFields);
                                httpURLConnection.disconnect();
                            } catch (Throwable th) {
                                httpURLConnection.disconnect();
                                throw th;
                            }
                        }
                    } catch (IOException e) {
                        C1002cn.m1458q("Error while connecting to ad server: " + e.getMessage());
                        return new C0984bw(2);
                    }
                }

                
                public static BinderC0987bz m1381a(Context context, InterfaceC0946al interfaceC0946al) {
                    BinderC0987bz binderC0987bz;
                    synchronized (f1518gL) {
                        try {
                            if (f1519gM == null) {
                                f1519gM = new BinderC0987bz(context.getApplicationContext(), interfaceC0946al);
                            }
                            binderC0987bz = f1519gM;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return binderC0987bz;
                }

                
                private static void m1382a(String str, Map<String, List<String>> map, String str2, int i) {
                    if (C1002cn.m1453k(2)) {
                        C1002cn.m1457p("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
                        if (map != null) {
                            for (String str3 : map.keySet()) {
                                C1002cn.m1457p("    " + str3 + ":");
                                Iterator<String> it = map.get(str3).iterator();
                                while (it.hasNext()) {
                                    C1002cn.m1457p("      " + it.next());
                                }
                            }
                        }
                        C1002cn.m1457p("  Body:");
                        if (str2 != null) {
                            for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += 1000) {
                                C1002cn.m1457p(str2.substring(i2, Math.min(str2.length(), i2 + 1000)));
                            }
                        } else {
                            C1002cn.m1457p("    null");
                        }
                        C1002cn.m1457p("  Response Code:\n    " + i + "\n}");
                    }
                }

                @Override 
                
                public C0984bw mo1377a(C0982bu c0982bu) {
                    return m1379a(this.mContext, this.f1520gN, c0982bu);
                }
            }
