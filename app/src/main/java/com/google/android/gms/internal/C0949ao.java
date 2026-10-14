            package com.google.android.gms.internal;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ao */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0949ao {

                /* JADX INFO: renamed from: eP */
                public final String f1162eP;

                /* JADX INFO: renamed from: eQ */
                public final List<String> f1163eQ;

                /* JADX INFO: renamed from: eR */
                public final String f1164eR;

                /* JADX INFO: renamed from: eS */
                public final String f1165eS;

                /* JADX INFO: renamed from: eT */
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
