            package com.google.android.gms.internal;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
            public final class C0950ap {

                
                public final List<C0949ao> f1167eU;

                
                public final long f1168eV;

                
                public final List<String> f1169eW;

                
                public final List<String> f1170eX;

                
                public final List<String> f1171eY;

                
                public final String f1172eZ;

                
                public final long f1173fa;

                public C0950ap(String str) throws JSONException {
                    JSONObject jSONObject = new JSONObject(str);
                    if (C1002cn.m1453k(2)) {
                        C1002cn.m1457p("Mediation Response JSON: " + jSONObject.toString(2));
                    }
                    JSONArray jSONArray = jSONObject.getJSONArray("ad_networks");
                    ArrayList arrayList = new ArrayList(jSONArray.length());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(new C0949ao(jSONArray.getJSONObject(i)));
                    }
                    this.f1167eU = Collections.unmodifiableList(arrayList);
                    this.f1172eZ = jSONObject.getString("qdata");
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("settings");
                    if (jSONObjectOptJSONObject == null) {
                        this.f1168eV = -1L;
                        this.f1169eW = null;
                        this.f1170eX = null;
                        this.f1171eY = null;
                        this.f1173fa = -1L;
                        return;
                    }
                    this.f1168eV = jSONObjectOptJSONObject.optLong("ad_network_timeout_millis", -1L);
                    this.f1169eW = C0955au.m1276a(jSONObjectOptJSONObject, "click_urls");
                    this.f1170eX = C0955au.m1276a(jSONObjectOptJSONObject, "imp_urls");
                    this.f1171eY = C0955au.m1276a(jSONObjectOptJSONObject, "nofill_urls");
                    long jOptLong = jSONObjectOptJSONObject.optLong("refresh", -1L);
                    this.f1173fa = jOptLong > 0 ? jOptLong * 1000 : -1L;
                }
            }
