            package com.google.android.gms.internal;

            import java.util.Arrays;
            import java.util.List;
            import java.util.Map;
            
            
            
            public final class C0991cc {

                
                private String f1534gX;

                
                private String f1535gY;

                
                private List<String> f1536gZ;

                
                private List<String> f1537ha;

                
                private List<String> f1541he;

                
                private long f1538hb = -1;

                
                private boolean f1539hc = false;

                
                private final long f1540hd = -1;

                
                private long f1542hf = -1;

                
                private int f1543hg = -1;

                
                private static long m1393a(Map<String, List<String>> map, String str) {
                    List<String> list = map.get(str);
                    if (list != null && !list.isEmpty()) {
                        String str2 = list.get(0);
                        try {
                            return (long) (Float.parseFloat(str2) * 1000.0f);
                        } catch (NumberFormatException e) {
                            C1002cn.m1458q("Could not parse float from " + str + " header: " + str2);
                        }
                    }
                    return -1L;
                }

                
                private static List<String> m1394b(Map<String, List<String>> map, String str) {
                    String str2;
                    List<String> list = map.get(str);
                    if (list == null || list.isEmpty() || (str2 = list.get(0)) == null) {
                        return null;
                    }
                    return Arrays.asList(str2.trim().split("\\s+"));
                }

                
                private void m1395e(Map<String, List<String>> map) {
                    List<String> listM1394b = m1394b(map, "X-Afma-Click-Tracking-Urls");
                    if (listM1394b != null) {
                        this.f1536gZ = listM1394b;
                    }
                }

                
                private void m1396f(Map<String, List<String>> map) {
                    List<String> listM1394b = m1394b(map, "X-Afma-Tracking-Urls");
                    if (listM1394b != null) {
                        this.f1537ha = listM1394b;
                    }
                }

                
                private void m1397g(Map<String, List<String>> map) {
                    long jM1393a = m1393a(map, "X-Afma-Interstitial-Timeout");
                    if (jM1393a != -1) {
                        this.f1538hb = jM1393a;
                    }
                }

                
                private void m1398h(Map<String, List<String>> map) {
                    List<String> list = map.get("X-Afma-Mediation");
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    this.f1539hc = Boolean.valueOf(list.get(0)).booleanValue();
                }

                
                private void m1399i(Map<String, List<String>> map) {
                    List<String> listM1394b = m1394b(map, "X-Afma-Manual-Tracking-Urls");
                    if (listM1394b != null) {
                        this.f1541he = listM1394b;
                    }
                }

                
                private void m1400j(Map<String, List<String>> map) {
                    long jM1393a = m1393a(map, "X-Afma-Refresh-Rate");
                    if (jM1393a != -1) {
                        this.f1542hf = jM1393a;
                    }
                }

                
                private void m1401k(Map<String, List<String>> map) {
                    List<String> list = map.get("X-Afma-Orientation");
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    String str = list.get(0);
                    if ("portrait".equalsIgnoreCase(str)) {
                        this.f1543hg = C0997ci.m1423ao();
                    } else if ("landscape".equalsIgnoreCase(str)) {
                        this.f1543hg = C0997ci.m1422an();
                    }
                }

                
                public void m1402a(String str, Map<String, List<String>> map, String str2) {
                    this.f1534gX = str;
                    this.f1535gY = str2;
                    m1404d(map);
                }

                
                public C0984bw m1403ak() {
                    return new C0984bw(this.f1534gX, this.f1535gY, this.f1536gZ, this.f1537ha, this.f1538hb, this.f1539hc, -1L, this.f1541he, this.f1542hf, this.f1543hg);
                }

                
                public void m1404d(Map<String, List<String>> map) {
                    m1395e(map);
                    m1396f(map);
                    m1397g(map);
                    m1398h(map);
                    m1399i(map);
                    m1400j(map);
                    m1401k(map);
                }
            }
