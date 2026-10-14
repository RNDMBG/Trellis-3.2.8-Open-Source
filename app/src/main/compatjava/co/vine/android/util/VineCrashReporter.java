package co.vine.android.util;

import android.content.Context;
import android.util.Log;

/**
 * Source-compatible diagnostic sink for Vine's old embedded Crashlytics calls.
 * The recovered third-party SDK is not usable source, so keep app diagnostics
 * safe and local until a supported reporting backend is deliberately chosen.
 */
public final class VineCrashReporter {
    private static final String TAG = "Crashlytics";

    private VineCrashReporter() {
    }

    public static void start(Context context) {
        Log.i(TAG, "Compatibility crash logging enabled");
    }

    public static void log(String message) {
        Log.i(TAG, message == null ? "null" : message);
    }

    public static void log(int priority, String tag, String message) {
        Log.println(priority, tag == null ? TAG : tag, message == null ? "null" : message);
    }

    public static void logException(Throwable throwable) {
        Log.e(TAG, "Vine exception", throwable);
    }

    public static void setString(String key, String value) {
        Log.d(TAG, String.valueOf(key) + "=" + String.valueOf(value));
    }

    public static void setInt(String key, int value) {
        Log.d(TAG, String.valueOf(key) + "=" + value);
    }
}
