package com.google.android.gms.gcm;
import android.content.Context;
import java.io.IOException;
public final class GoogleCloudMessaging {
    private GoogleCloudMessaging() { }
    public static GoogleCloudMessaging getInstance(Context c) { return new GoogleCloudMessaging(); }
    public String register(String senderId) throws IOException { return ""; }
    public void close() { }
}
