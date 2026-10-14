            package com.google.android.gms.internal;

            import android.content.Context;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Iterator;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
            public final class C0955au {
                
                public static List<String> m1276a(JSONObject jSONObject, String str) throws JSONException {
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str);
                    if (jSONArrayOptJSONArray == null) {
                        return null;
                    }
                    ArrayList arrayList = new ArrayList(jSONArrayOptJSONArray.length());
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        arrayList.add(jSONArrayOptJSONArray.getString(i));
                    }
                    return Collections.unmodifiableList(arrayList);
                }

                
                public static void m1277a(Context context, String str, C0993ce c0993ce, String str2, boolean z, List<String> list) {
                    String str3 = z ? "1" : "0";
                    Iterator<String> it = list.iterator();
                    while (it.hasNext()) {
                        String strReplaceAll = it.next().replaceAll("@gw_adlocid@", str2).replaceAll("@gw_adnetrefresh@", str3).replaceAll("@gw_qdata@", c0993ce.f1577hA.f1172eZ).replaceAll("@gw_sdkver@", str).replaceAll("@gw_sessid@", C0994cf.f1579hB).replaceAll("@gw_seqnum@", c0993ce.f1572gE);
                        if (c0993ce.f1567fm != null) {
                            strReplaceAll = strReplaceAll.replaceAll("@gw_adnetid@", c0993ce.f1567fm.f1162eP).replaceAll("@gw_allocid@", c0993ce.f1567fm.f1164eR);
                        }
                        new C1000cl(context, str, strReplaceAll).start();
                    }
                }
            }
