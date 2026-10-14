package com.flurry.android;

import android.content.Context;
import android.util.Log;
import java.util.Map;

final class FlurryBridge {
    private FlurryBridge() { }
    static final String TAG = "VineAnalytics";
    static void start(Context c, String key) { Log.d(TAG, "session started"); }
    static void end(Context c) { Log.d(TAG, "session ended"); }
    static void page() { Log.d(TAG, "page view"); }
    static void user(String id) { Log.d(TAG, "user id set"); }
    static void version(String v) { Log.d(TAG, "version=" + v); }
    static void event(String e) { Log.d(TAG, e); }
    static void event(String e, Map<String,String> p) { Log.d(TAG, e + " " + p); }
}
