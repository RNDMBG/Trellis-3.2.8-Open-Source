package com.flurry.android;

import android.content.Context;
import android.util.Log;
import java.util.Map;


public final class FlurryAgent {
    private static final String TAG = "VineAnalytics";
    private FlurryAgent() { }
    public static void onStartSession(Context context, String apiKey) { Log.d(TAG, "session started"); }
    public static void onEndSession(Context context) { Log.d(TAG, "session ended"); }
    public static void onPageView() { Log.d(TAG, "page view"); }
    public static void setUseHttps(boolean useHttps) { }
    public static void setUserId(String userId) { Log.d(TAG, "user id set"); }
    public static void setVersionName(String versionName) { Log.d(TAG, "version=" + versionName); }
    public static void logEvent(String eventName) { Log.d(TAG, eventName); }
    public static void logEvent(String eventName, Map<String, String> parameters) { Log.d(TAG, eventName + " " + parameters); }
}
