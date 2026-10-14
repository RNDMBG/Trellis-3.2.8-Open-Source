            package com.flurry.sdk;

            import java.net.URLDecoder;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            
            
            
    public class C0786cw {

                
                private static final String f541a = C0786cw.class.getSimpleName();

                
        public Map<String, List<String>> m555a(String str) {
            C0827el.m885a(3, f541a, "Parsing referrer map");
            if (str == null) {
                return Collections.emptyMap();
                    }
            HashMap map = new HashMap();
            String[] strArrSplit = str.split("&");
            int length = strArrSplit.length;
            for (int i = 0; i < length; i++) {
                String[] strArrSplit2 = strArrSplit[i].split("=");
                if (strArrSplit2.length != 2) {
                    C0827el.m885a(5, f541a, "Invalid referrer Element: " + strArrSplit[i] + " in referrer tag " + str);
                        } else {
                    String strDecode = URLDecoder.decode(strArrSplit2[0]);
                    String strDecode2 = URLDecoder.decode(strArrSplit2[1]);
                    if (map.get(strDecode) == null) {
                        map.put(strDecode, new ArrayList());
                            }
                    ((List) map.get(strDecode)).add(strDecode2);
                        }
                    }
                    for (Map.Entry entry : map.entrySet()) {
                C0827el.m885a(3, f541a, "entry: " + ((String) entry.getKey()) + "=" + entry.getValue());
                    }
            StringBuilder sb = new StringBuilder();
            if (map.get("utm_source") == null) {
                sb.append("Campaign Source is missing.\n");
                    }
            if (map.get("utm_medium") == null) {
                sb.append("Campaign Medium is missing.\n");
                    }
            if (map.get("utm_campaign") == null) {
                sb.append("Campaign Name is missing.\n");
                    }
            if (sb.length() > 0) {
                C0827el.m885a(5, f541a, "Detected missing referrer keys : " + sb.toString());
                    }
                    return map;
                }
            }
