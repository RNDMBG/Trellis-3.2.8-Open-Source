            package com.crashlytics.android.internal;

            import co.vine.android.ExploreChannelsActivity;
            import com.google.android.gms.plus.PlusShare;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
    public class C0678aY {
        C0678aY() {
                }

                
        public C0677aX m193a(C0687ah c0687ah, JSONObject jSONObject) throws JSONException {
            int iOptInt = jSONObject.optInt("settings_version", 0);
            int iOptInt2 = jSONObject.optInt("cache_duration", 3600);
            JSONObject jSONObject2 = jSONObject.getJSONObject("app");
                    String string = jSONObject2.getString("identifier");
                    String string2 = jSONObject2.getString("status");
                    String string3 = jSONObject2.getString(PlusShare.KEY_CALL_TO_ACTION_URL);
                    String string4 = jSONObject2.getString("reports_url");
                    boolean zOptBoolean = jSONObject2.optBoolean("update_required", false);
                    C0665aL c0665aL = null;
                    if (jSONObject2.has(ExploreChannelsActivity.PARAM_ICON_PATH) && jSONObject2.getJSONObject(ExploreChannelsActivity.PARAM_ICON_PATH).has("hash")) {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject(ExploreChannelsActivity.PARAM_ICON_PATH);
                        c0665aL = new C0665aL(jSONObject3.getString("hash"), jSONObject3.getInt("width"), jSONObject3.getInt("height"));
                    }
                    C0666aM c0666aM = new C0666aM(string, string2, string3, string4, zOptBoolean, c0665aL);
            JSONObject jSONObject4 = jSONObject.getJSONObject("session");
                    C0671aR c0671aR = new C0671aR(jSONObject4.optInt("log_buffer_size", 64000), jSONObject4.optInt("max_chained_exception_depth", 8), jSONObject4.optInt("max_custom_exception_events", 64), jSONObject4.optInt("max_custom_key_value_pairs", 64), jSONObject4.optInt("identifier_mask", 255), jSONObject4.optBoolean("send_session_without_crash", false));
            JSONObject jSONObject5 = jSONObject.getJSONObject("prompt");
                    C0670aQ c0670aQ = new C0670aQ(jSONObject5.optString("title", "Send Crash Report?"), jSONObject5.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), jSONObject5.optString("send_button_title", "Send"), jSONObject5.optBoolean("show_cancel_button", true), jSONObject5.optString("cancel_button_title", "Don't Send"), jSONObject5.optBoolean("show_always_send_button", true), jSONObject5.optString("always_send_button_title", "Always Send"));
            JSONObject jSONObject6 = jSONObject.getJSONObject("features");
                    C0669aP c0669aP = new C0669aP(jSONObject6.optBoolean("prompt_enabled", false), jSONObject6.optBoolean("collect_logged_exceptions", true), jSONObject6.optBoolean("collect_reports", true), jSONObject6.optBoolean("collect_analytics", false));
            JSONObject jSONObject7 = jSONObject.getJSONObject("analytics");
            return new C0677aX(jSONObject.has("expires_at") ? jSONObject.getLong("expires_at") : c0687ah.m240a() + (((long) iOptInt2) * 1000), c0666aM, c0671aR, c0670aQ, c0669aP, new C0664aK(jSONObject7.optString(PlusShare.KEY_CALL_TO_ACTION_URL, "https://e.crashlytics.com/spi/v2/events"), jSONObject7.optInt("flush_interval_secs", 600), jSONObject7.optInt("max_byte_size_per_file", 8000), jSONObject7.optInt("max_file_count_per_send", 1), jSONObject7.optInt("max_pending_send_file_count", 100)), iOptInt, iOptInt2);
                }

                
        public JSONObject m194a(JSONObject jSONObject) throws JSONException {
            JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
            jSONObject2.getJSONObject("features").remove("collect_analytics");
            jSONObject2.remove("analytics");
            return jSONObject2;
                }
            }
