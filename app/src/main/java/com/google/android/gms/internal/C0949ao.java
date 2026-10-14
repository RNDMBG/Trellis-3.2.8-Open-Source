            package com.google.android.gms.internal;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
            public final class C0949ao {

                
                public final String f1162eP;

                
                public final List<String> f1163eQ;

                
                public final String f1164eR;

                
                public final String f1165eS;

                
                public final List<String> f1166eT;

                public C0949ao(JSONObject jSONObject) throws JSONException {
                    this.f1162eP = jSONObject.getString("id");
                    JSONArray jSONArray = jSONObject.getJSONArray("adapters");
                    ArrayList arrayList = new ArrayList(jSONArray.length());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(jSONArray.getString(i));
                    }
                    this.f1163eQ = Collections.unmodifiableList(arrayList);
                    this.f1164eR = jSONObject.optString("allocation_id", null);
                    this.f1166eT = C0955au.m1276a(jSONObject, "imp_urls");
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    this.f1165eS = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.toString() : null;
                }
            }
