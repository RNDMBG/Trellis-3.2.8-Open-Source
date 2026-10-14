            package com.google.android.gms.internal;

            import java.util.HashMap;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ej */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1052ej {
                /* JADX INFO: renamed from: a */
                public static void m1734a(StringBuilder sb, HashMap<String, String> map) {
                    boolean z;
                    sb.append("{");
                    boolean z2 = true;
                    for (String str : map.keySet()) {
                        if (z2) {
                            z = false;
                        } else {
                            sb.append(",");
                            z = z2;
                        }
                        String str2 = map.get(str);
                        sb.append("\"").append(str).append("\":");
                        if (str2 == null) {
                            sb.append("null");
                        } else {
                            sb.append("\"").append(str2).append("\"");
                        }
                        z2 = z;
                    }
                    sb.append("}");
                }
            }
