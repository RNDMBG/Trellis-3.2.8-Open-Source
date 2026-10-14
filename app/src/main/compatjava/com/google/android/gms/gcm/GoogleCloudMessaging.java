package com.google.android.gms.gcm;

import android.content.Context;
import java.io.IOException;

/**
 * Compatibility facade for the retired GCM client.
 *
 * <p>Registration returns an empty token so callers can complete their
 * existing lifecycle without claiming that push registration succeeded.</p>
 */
public final class GoogleCloudMessaging {
    private GoogleCloudMessaging() {
    }

    public static GoogleCloudMessaging getInstance(Context context) {
        return new GoogleCloudMessaging();
    }

    public String register(String senderId) throws IOException {
        return "";
    }

    public void close() {
    }
}
