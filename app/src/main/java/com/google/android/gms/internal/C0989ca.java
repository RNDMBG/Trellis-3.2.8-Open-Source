            package com.google.android.gms.internal;

            import android.location.Location;
            import android.text.TextUtils;
            import co.vine.android.recorder.BuildConfig;
            import co.vine.android.service.VineService;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.HashMap;
            import org.json.JSONException;
            
            
            
            public final class C0989ca {

                
                private static final SimpleDateFormat f1525gS = new SimpleDateFormat("yyyyMMdd");

                
                public static String m1383a(C0982bu c0982bu, C0992cd c0992cd, Location location) {
                    try {
                        HashMap map = new HashMap();
                        if (c0982bu.f1495gA != null) {
                            map.put("ad_pos", c0982bu.f1495gA);
                        }
                        m1386a((HashMap<String, Object>) map, c0982bu.f1496gB);
                        map.put("format", c0982bu.f1493ed.f2163ew);
                        if (c0982bu.f1493ed.width == -1) {
                            map.put("smart_w", "full");
                        }
                        if (c0982bu.f1493ed.height == -2) {
                            map.put("smart_h", "auto");
                        }
                        map.put("slotname", c0982bu.adUnitId);
                        map.put("pn", c0982bu.applicationInfo.packageName);
                        if (c0982bu.f1497gC != null) {
                            map.put("vc", Integer.valueOf(c0982bu.f1497gC.versionCode));
                        }
                        map.put("ms", c0982bu.f1498gD);
                        map.put("seq_num", c0982bu.f1499gE);
                        map.put("session_id", c0982bu.f1500gF);
                        map.put("js", c0982bu.f1494eg.f1596hP);
                        m1385a((HashMap<String, Object>) map, c0992cd);
                        m1384a((HashMap<String, Object>) map, location);
                        if (C1002cn.m1453k(2)) {
                            C1002cn.m1457p("Ad Request JSON: " + C0997ci.m1434l(map).toString(2));
                        }
                        return C0997ci.m1434l(map).toString();
                    } catch (JSONException e) {
                        C1002cn.m1458q("Problem serializing ad request to JSON: " + e.getMessage());
                        return null;
                    }
                }

                
                private static void m1384a(HashMap<String, Object> map, Location location) {
                    if (location == null) {
                        return;
                    }
                    HashMap map2 = new HashMap();
                    Float fValueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
                    Long lValueOf = Long.valueOf(location.getTime() * 1000);
                    Long lValueOf2 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
                    Long lValueOf3 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
                    map2.put("radius", fValueOf);
                    map2.put("lat", lValueOf2);
                    map2.put("long", lValueOf3);
                    map2.put(VineService.EXTRA_ANCHOR, lValueOf);
                    map.put("loc", map2);
                }

                
                private static void m1385a(HashMap<String, Object> map, C0992cd c0992cd) {
                    map.put("am", Integer.valueOf(c0992cd.f1544hh));
                    map.put("cog", m1387g(c0992cd.f1545hi));
                    map.put("coh", m1387g(c0992cd.f1546hj));
                    if (!TextUtils.isEmpty(c0992cd.f1547hk)) {
                        map.put("carrier", c0992cd.f1547hk);
                    }
                    map.put("gl", c0992cd.f1548hl);
                    if (c0992cd.f1549hm) {
                        map.put("simulator", 1);
                    }
                    map.put("ma", m1387g(c0992cd.f1550hn));
                    map.put("sp", m1387g(c0992cd.f1551ho));
                    map.put("hl", c0992cd.f1552hp);
                    if (!TextUtils.isEmpty(c0992cd.f1553hq)) {
                        map.put("mv", c0992cd.f1553hq);
                    }
                    map.put("muv", Integer.valueOf(c0992cd.f1554hr));
                    if (c0992cd.f1555hs != -2) {
                        map.put("cnt", Integer.valueOf(c0992cd.f1555hs));
                    }
                    map.put("gnt", Integer.valueOf(c0992cd.f1556ht));
                    map.put("pt", Integer.valueOf(c0992cd.f1557hu));
                    map.put("rm", Integer.valueOf(c0992cd.f1558hv));
                    map.put("riv", Integer.valueOf(c0992cd.f1559hw));
                    map.put("u_sd", Float.valueOf(c0992cd.f1560hx));
                    map.put("sh", Integer.valueOf(c0992cd.f1562hz));
                    map.put(BuildConfig.ENCODING, Integer.valueOf(c0992cd.f1561hy));
                }

                
                private static void m1386a(HashMap<String, Object> map, C1124v c1124v) {
                    if (c1124v.f2159es != -1) {
                        map.put("cust_age", f1525gS.format(new Date(c1124v.f2159es)));
                    }
                    if (c1124v.extras != null) {
                        map.put("extras", c1124v.extras);
                    }
                    if (c1124v.f2160et != -1) {
                        map.put("cust_gender", Integer.valueOf(c1124v.f2160et));
                    }
                    if (c1124v.f2161eu != null) {
                        map.put("kw", c1124v.f2161eu);
                    }
                    if (c1124v.tagForChildDirectedTreatment != -1) {
                        map.put("tag_for_child_directed_treatment", Integer.valueOf(c1124v.tagForChildDirectedTreatment));
                    }
                    if (c1124v.f2162ev) {
                        map.put("adtest", "on");
                    }
                }

                
                private static Integer m1387g(boolean z) {
                    return Integer.valueOf(z ? 1 : 0);
                }
            }
