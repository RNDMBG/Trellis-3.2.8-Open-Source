package com.flurry.android;
import android.content.Context;
import java.util.Map;
public final class FlurryAgent {
    private FlurryAgent() { }
    public static void onStartSession(Context c, String k) { FlurryBridge.start(c, k); }
    public static void onEndSession(Context c) { FlurryBridge.end(c); }
    public static void onPageView() { FlurryBridge.page(); }
    public static void setUseHttps(boolean ignored) { }
    public static void setUserId(String id) { FlurryBridge.user(id); }
    public static void setVersionName(String v) { FlurryBridge.version(v); }
    public static void logEvent(String e) { FlurryBridge.event(e); }
    public static void logEvent(String e, Map<String,String> p) { FlurryBridge.event(e, p); }
}
