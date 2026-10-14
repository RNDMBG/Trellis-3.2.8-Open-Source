            package com.google.android.gms.internal;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ap */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0950ap {

                /* JADX INFO: renamed from: eU */
                public final List<C0949ao> f1167eU;

                /* JADX INFO: renamed from: eV */
                public final long f1168eV;

                /* JADX INFO: renamed from: eW */
                public final List<String> f1169eW;

                /* JADX INFO: renamed from: eX */
                public final List<String> f1170eX;

                /* JADX INFO: renamed from: eY */
                public final List<String> f1171eY;

                /* JADX INFO: renamed from: eZ */
                public final String f1172eZ;

                /* JADX INFO: renamed from: fa */
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
