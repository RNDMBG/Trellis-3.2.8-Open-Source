            package org.scribe.utils;

            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class MapUtils {
/* 9 */         private MapUtils() {
                }

/* 13 */        public static <K, V> String toString(Map<K, V> map) {
/* 14 */            if (map == null) {
                        return "";
                    }
/* 15 */            if (map.isEmpty()) {
                        return "{}";
                    }
/* 17 */            StringBuilder result = new StringBuilder();
                    for (Map.Entry<K, V> entry : map.entrySet()) {
/* 20 */                result.append(String.format(", %s -> %s ", entry.getKey().toString(), entry.getValue().toString()));
                    }
/* 22 */            return "{" + result.substring(1) + "}";
                }
            }
