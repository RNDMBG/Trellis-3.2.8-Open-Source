            package com.crashlytics.android.internal;

            import co.vine.android.util.CommonUtil;
            import com.crashlytics.android.Crashlytics;
            import java.util.HashMap;
            import org.json.JSONObject;
            
            
            
    final class C0668aO extends AbstractC0652Z implements InterfaceC0707ba {
        public C0668aO(String str, String str2, C0701av c0701av) {
            this(str, str2, c0701av, EnumC0703ax.GET);
                }

        private C0668aO(String str, String str2, C0701av c0701av, EnumC0703ax enumC0703ax) {
            super(str, str2, c0701av, enumC0703ax);
                }

                
                
                @Override 
                
        public final JSONObject mo179a(C0679aZ c0679aZ) throws Throwable {
                    C0704ay c0704ay;
                    C0704ay c0704ayA;
                    JSONObject jSONObject;
                    try {
                        try {
                    HashMap map = new HashMap();
                            map.put("build_version", c0679aZ.f346e);
                            map.put("display_version", c0679aZ.f345d);
                            map.put("source", Integer.toString(c0679aZ.f347f));
                            if (c0679aZ.f348g != null) {
                                map.put("icon_hash", c0679aZ.f348g);
                            }
                            String str = c0679aZ.f344c;
                            if (!C0681ab.m228e(str)) {
                                map.put("instance", str);
                            }
                    c0704ayA = m154a(map);
                            try {
                        c0704ayA = c0704ayA.m324a("X-CRASHLYTICS-API-KEY", c0679aZ.f342a).m324a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m324a("X-CRASHLYTICS-D", c0679aZ.f343b).m324a("X-CRASHLYTICS-API-CLIENT-VERSION", C0727v.m357a().getVersion()).m324a("Accept", CommonUtil.ContentType.APPLICATION_JSON);
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Requesting settings from " + m155a());
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Settings query params were: " + map);
                        jSONObject = new JSONObject(c0704ayA.m333c());
                                c0704ay = c0704ayA;
                        if (c0704ayA != null) {
                            InterfaceC0722q interfaceC0722qM365b = C0727v.m357a().m365b();
                                    String str2 = "Settings request ID: " + c0704ayA.m329a("X-REQUEST-ID");
                                    interfaceC0722qM365b.mo96a(Crashlytics.TAG, str2);
                                    c0704ay = str2;
                                }
                            } catch (Exception e) {
                        e = e;
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to retrieve settings from " + m155a(), e);
                        if (c0704ayA != null) {
                            InterfaceC0722q interfaceC0722qM365b2 = C0727v.m357a().m365b();
                                    String str3 = "Settings request ID: " + c0704ayA.m329a("X-REQUEST-ID");
                                    interfaceC0722qM365b2.mo96a(Crashlytics.TAG, str3);
                                    jSONObject = null;
                                    c0704ay = str3;
                                } else {
                                    jSONObject = null;
                                    c0704ay = c0704ayA;
                                }
                            }
                        } catch (Throwable th) {
                    th = th;
                            if (c0704ay != 0) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Settings request ID: " + c0704ay.m329a("X-REQUEST-ID"));
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                e = e2;
                        c0704ayA = null;
                    } catch (Throwable th2) {
                th = th2;
                        c0704ay = 0;
                        if (c0704ay != 0) {
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Settings request ID: " + c0704ay.m329a("X-REQUEST-ID"));
                        }
                        throw th;
                    }
            return jSONObject;
                }
            }
