            package co.vine.android.util;

            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class FlexibleStringHashMap<K, V> extends HashMap<K, V> {
                private static final long serialVersionUID = -1378998403357321549L;

                @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
/* 15 */        public V get(Object obj) {
/* 16 */            if (obj instanceof String) {
/* 17 */                V v = (V) super.get(obj);
/* 18 */                if (v == null) {
                            for (K k : keySet()) {
/* 22 */                        if (((String) obj).contains((CharSequence) k)) {
/* 23 */                            return (V) super.get(k);
                                }
                            }
/* 27 */                    return null;
                        }
/* 29 */                return v;
                    }
/* 29 */            return (V) super.get(obj);
                }
            }
