            package com.crashlytics.android.internal;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.util.Map;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
    final class C0650X {
        C0650X() {
                }

                
        public final byte[] m153a(C0648V c0648v) throws IOException {
                    try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appBundleId", c0648v.f259a);
                jSONObject.put("executionId", c0648v.f260b);
                jSONObject.put("installationId", c0648v.f261c);
                jSONObject.put("androidId", c0648v.f262d);
                jSONObject.put("osVersion", c0648v.f263e);
                jSONObject.put("deviceModel", c0648v.f264f);
                jSONObject.put("appVersionCode", c0648v.f265g);
                jSONObject.put("appVersionName", c0648v.f266h);
                jSONObject.put("timestamp", c0648v.f267i);
                jSONObject.put("type", c0648v.f268j.toString());
                jSONObject.put("details", m152a(c0648v.f269k));
                return jSONObject.toString().getBytes(IoUtils.UTF_8);
                    } catch (JSONException e) {
                throw new IOException(e.getMessage());
                    }
                }

                
        private static JSONObject m152a(Map<String, String> map) throws JSONException {
            JSONObject jSONObject = new JSONObject();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                jSONObject.put(entry.getKey(), entry.getValue());
                    }
            return jSONObject;
                }
            }
