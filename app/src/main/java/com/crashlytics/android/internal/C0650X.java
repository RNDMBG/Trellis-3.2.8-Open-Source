            package com.crashlytics.android.internal;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.util.Map;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.X */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    final class C0650X {
/* 11 */        C0650X() {
                }

                /* JADX INFO: renamed from: a */
/* 26 */        public final byte[] m153a(C0648V c0648v) throws IOException {
                    try {
/* 27 */                JSONObject jSONObject = new JSONObject();
/* 28 */                jSONObject.put("appBundleId", c0648v.f259a);
/* 29 */                jSONObject.put("executionId", c0648v.f260b);
/* 30 */                jSONObject.put("installationId", c0648v.f261c);
/* 31 */                jSONObject.put("androidId", c0648v.f262d);
/* 32 */                jSONObject.put("osVersion", c0648v.f263e);
/* 33 */                jSONObject.put("deviceModel", c0648v.f264f);
/* 34 */                jSONObject.put("appVersionCode", c0648v.f265g);
/* 35 */                jSONObject.put("appVersionName", c0648v.f266h);
/* 36 */                jSONObject.put("timestamp", c0648v.f267i);
/* 37 */                jSONObject.put("type", c0648v.f268j.toString());
/* 38 */                jSONObject.put("details", m152a(c0648v.f269k));
/* 40 */                return jSONObject.toString().getBytes(IoUtils.UTF_8);
                    } catch (JSONException e) {
/* 42 */                throw new IOException(e.getMessage());
                    }
                }

                /* JADX INFO: renamed from: a */
/* 46 */        private static JSONObject m152a(Map<String, String> map) throws JSONException {
/* 47 */            JSONObject jSONObject = new JSONObject();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
/* 50 */                jSONObject.put(entry.getKey(), entry.getValue());
                    }
/* 53 */            return jSONObject;
                }
            }
