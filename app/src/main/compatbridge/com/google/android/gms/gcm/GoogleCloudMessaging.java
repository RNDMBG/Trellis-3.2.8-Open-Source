package com.google.android.gms.gcm;

import android.content.Context;
import java.io.IOException;

/** Retired GCM boundary. It never claims a registration succeeded. */
public final class GoogleCloudMessaging {
    private GoogleCloudMessaging() { }
    public static GoogleCloudMessaging getInstance(Context context) { return new GoogleCloudMessaging(); }
    public String register(String senderId) throws IOException { return ""; }
    public void close() { }
}
