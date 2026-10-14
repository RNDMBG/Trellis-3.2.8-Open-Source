package com.flurry.android;

import android.content.Context;
import android.util.Log;
import java.util.Map;

/**
 * Local no-network replacement for the obsolete Flurry integration.
 *
 * <p>Analytics is optional to the Vine client. Keeping these calls as a
 * logging facade preserves application flow without embedding a closed,
 * obsolete analytics SDK or silently sending data.</p>
 */
public final class FlurryAgent {
    private static final String TAG = "VineAnalytics";

    private FlurryAgent() {
    }

    public static void onStartSession(Context context, String apiKey) {
        Log.d(TAG, "analytics session started");
    }

    public static void onEndSession(Context context) {
        Log.d(TAG, "analytics session ended");
    }

    public static void onPageView() {
        Log.d(TAG, "page view");
    }

    public static void setUseHttps(boolean useHttps) {
        // Retained for source compatibility. This facade never performs network I/O.
    }

    public static void setUserId(String userId) {
        Log.d(TAG, "user id set");
    }

    public static void setVersionName(String versionName) {
        Log.d(TAG, "version=" + versionName);
    }

    public static void logEvent(String eventName) {
        Log.d(TAG, eventName);
    }

    public static void logEvent(String eventName, Map<String, String> parameters) {
        Log.d(TAG, eventName + " " + parameters);
    }
}
