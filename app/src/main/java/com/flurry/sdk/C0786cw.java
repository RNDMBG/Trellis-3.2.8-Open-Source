            package com.flurry.sdk;

            import java.net.URLDecoder;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cw */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class C0786cw {

                /* JADX INFO: renamed from: a */
                private static final String f541a = C0786cw.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 20 */        public Map<String, List<String>> m555a(String str) {
/* 21 */            C0827el.m885a(3, f541a, "Parsing referrer map");
/* 23 */            if (str == null) {
/* 24 */                return Collections.emptyMap();
                    }
/* 27 */            HashMap map = new HashMap();
/* 30 */            String[] strArrSplit = str.split("&");
/* 33 */            int length = strArrSplit.length;
/* 34 */            for (int i = 0; i < length; i++) {
/* 35 */                String[] strArrSplit2 = strArrSplit[i].split("=");
/* 36 */                if (strArrSplit2.length != 2) {
/* 37 */                    C0827el.m885a(5, f541a, "Invalid referrer Element: " + strArrSplit[i] + " in referrer tag " + str);
                        } else {
/* 46 */                    String strDecode = URLDecoder.decode(strArrSplit2[0]);
/* 48 */                    String strDecode2 = URLDecoder.decode(strArrSplit2[1]);
/* 50 */                    if (map.get(strDecode) == null) {
/* 51 */                        map.put(strDecode, new ArrayList());
                            }
/* 54 */                    ((List) map.get(strDecode)).add(strDecode2);
                        }
                    }
                    for (Map.Entry entry : map.entrySet()) {
/* 59 */                C0827el.m885a(3, f541a, "entry: " + ((String) entry.getKey()) + "=" + entry.getValue());
                    }
/* 69 */            StringBuilder sb = new StringBuilder();
/* 70 */            if (map.get("utm_source") == null) {
/* 71 */                sb.append("Campaign Source is missing.\n");
                    }
/* 74 */            if (map.get("utm_medium") == null) {
/* 75 */                sb.append("Campaign Medium is missing.\n");
                    }
/* 78 */            if (map.get("utm_campaign") == null) {
/* 79 */                sb.append("Campaign Name is missing.\n");
                    }
/* 83 */            if (sb.length() > 0) {
/* 84 */                C0827el.m885a(5, f541a, "Detected missing referrer keys : " + sb.toString());
                    }
                    return map;
                }
            }
