            package com.facebook.model;

            import android.annotation.SuppressLint;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class JsonUtil {
/* 24 */        JsonUtil() {
                }

/* 27 */        static void jsonObjectClear(JSONObject jsonObject) {
/* 28 */            Iterator<String> keys = jsonObject.keys();
/* 29 */            while (keys.hasNext()) {
/* 30 */                keys.next();
/* 31 */                keys.remove();
                    }
                }

/* 36 */        static boolean jsonObjectContainsValue(JSONObject jsonObject, Object value) {
/* 37 */            Iterator<String> keys = jsonObject.keys();
/* 38 */            while (keys.hasNext()) {
/* 39 */                Object thisValue = jsonObject.opt(keys.next());
/* 40 */                if (thisValue != null && thisValue.equals(value)) {
/* 41 */                    return true;
                        }
                    }
                    return false;
                }

/* 45 */        private static final class JSONObjectEntry implements Map.Entry<String, Object> {
                    private final String key;
                    private final Object value;


/* 50 */            JSONObjectEntry(String key, Object value) {
/* 52 */                this.key = key;
/* 53 */                this.value = value;
                    }

                    @Override // java.util.Map.Entry
                    @SuppressLint({"FieldGetter"})
                    /* JADX INFO: renamed from: getKey, reason: avoid collision after fix types in other method */
/* 58 */            public String getKey() {
/* 59 */                return this.key;
                    }

                    @Override // java.util.Map.Entry
/* 63 */            public Object getValue() {
/* 64 */                return this.value;
                    }

                    @Override // java.util.Map.Entry
/* 68 */            public Object setValue(Object object) {
/* 69 */                throw new UnsupportedOperationException("JSONObjectEntry is immutable");
                    }
                }

/* 74 */        static Set<Map.Entry<String, Object>> jsonObjectEntrySet(JSONObject jsonObject) {
/* 75 */            HashSet<Map.Entry<String, Object>> result = new HashSet<>();
/* 78 */            Iterator<String> keys = jsonObject.keys();
/* 79 */            while (keys.hasNext()) {
/* 80 */                String key = keys.next();
/* 81 */                Object value = jsonObject.opt(key);
/* 82 */                result.add(new JSONObjectEntry(key, value));
                    }
/* 85 */            return result;
                }

/* 88 */        static Set<String> jsonObjectKeySet(JSONObject jsonObject) {
/* 89 */            HashSet<String> result = new HashSet<>();
/* 92 */            Iterator<String> keys = jsonObject.keys();
/* 93 */            while (keys.hasNext()) {
/* 94 */                result.add(keys.next());
                    }
/* 97 */            return result;
                }

/* 100 */       static void jsonObjectPutAll(JSONObject jsonObject, Map<String, Object> map) {
/* 101 */           Set<Map.Entry<String, Object>> entrySet = map.entrySet();
                    for (Map.Entry<String, Object> entry : entrySet) {
                        try {
/* 104 */                   jsonObject.putOpt(entry.getKey(), entry.getValue());
                        } catch (JSONException e) {
/* 106 */                   throw new IllegalArgumentException(e);
                        }
                    }
                }

/* 111 */       static Collection<Object> jsonObjectValues(JSONObject jsonObject) {
/* 112 */           ArrayList<Object> result = new ArrayList<>();
/* 115 */           Iterator<String> keys = jsonObject.keys();
/* 116 */           while (keys.hasNext()) {
/* 117 */               result.add(jsonObject.opt(keys.next()));
                    }
/* 120 */           return result;
                }
            }
