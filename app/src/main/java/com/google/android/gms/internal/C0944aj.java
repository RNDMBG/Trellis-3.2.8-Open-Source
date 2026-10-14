            package com.google.android.gms.internal;

            import co.vine.android.HomeTabActivity;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.aj */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0944aj implements InterfaceC0943ai {
                /* JADX INFO: renamed from: a */
                private static boolean m1251a(Map<String, String> map) {
                    return "1".equals(map.get("custom_close"));
                }

                /* JADX INFO: renamed from: b */
                private static int m1252b(Map<String, String> map) {
                    String str = map.get("o");
                    if (str != null) {
                        if ("p".equalsIgnoreCase(str)) {
                            return C0997ci.m1423ao();
                        }
                        if ("l".equalsIgnoreCase(str)) {
                            return C0997ci.m1422an();
                        }
                    }
                    return -1;
                }

                @Override // com.google.android.gms.internal.InterfaceC0943ai
                /* JADX INFO: renamed from: a */
                public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                    String str = map.get("a");
                    if (str == null) {
                        C1002cn.m1458q("Action missing from an open GMSG.");
                        return;
                    }
                    C1006cr c1006crM1473aw = c1005cq.m1473aw();
                    if ("expand".equalsIgnoreCase(str)) {
                        if (c1005cq.m1476az()) {
                            C1002cn.m1458q("Cannot expand WebView that is already expanded.");
                            return;
                        } else {
                            c1006crM1473aw.m1486a(m1251a(map), m1252b(map));
                            return;
                        }
                    }
                    if (!"webapp".equalsIgnoreCase(str)) {
                        c1006crM1473aw.m1482a(new C0966be(map.get("i"), map.get(HomeTabActivity.PREFIX_USER_ID), map.get("m"), map.get("p"), map.get("c"), map.get("f"), map.get("e")));
                        return;
                    }
                    String str2 = map.get(HomeTabActivity.PREFIX_USER_ID);
                    if (str2 != null) {
                        c1006crM1473aw.m1487a(m1251a(map), m1252b(map), str2);
                    } else {
                        c1006crM1473aw.m1488a(m1251a(map), m1252b(map), map.get("html"), map.get("baseurl"));
                    }
                }
            }
