package com.google.android.gms.common;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
public final class GooglePlayServicesUtil {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int SERVICE_MISSING = 1;
    private GooglePlayServicesUtil() { }
    public static int isGooglePlayServicesAvailable(Context c) { return SERVICE_MISSING; }
    public static Dialog getErrorDialog(int code, Context c, int requestCode) {
        return new AlertDialog.Builder(c).setTitle("Optional service unavailable")
                .setMessage("Google Play Services is not included in this open build.")
                .setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).create();
    }
}
