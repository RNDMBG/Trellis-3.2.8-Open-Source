            package com.google.android.gms.auth;

            import android.R;
            import android.accounts.AccountManager;
            import android.app.Notification;
            import android.app.NotificationManager;
            import android.app.PendingIntent;
            import android.content.ComponentName;
            import android.content.ContentResolver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.RemoteException;
            import android.text.TextUtils;
            import android.util.Log;
            import com.google.android.gms.C0881R;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesRepairableException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.ServiceConnectionC0887a;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.InterfaceC1117o;
            import java.io.IOException;
            import java.net.URISyntaxException;
            
            
            public final class GoogleAuthUtil {
                public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
                public static final String KEY_ANDROID_PACKAGE_NAME;
                public static final String KEY_CALLER_UID;
                public static final String KEY_CLIENT_PACKAGE_NAME = "clientPackageName";
                public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";

                @Deprecated
                public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
                public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";

                
                private static final ComponentName f922iH;

                
                private static final ComponentName f923iI;

                
                private static final Intent f924iJ;

                
                private static final Intent f925iK;

                static {
                    KEY_CALLER_UID = Build.VERSION.SDK_INT >= 11 ? "callerUid" : "callerUid";
                    KEY_ANDROID_PACKAGE_NAME = Build.VERSION.SDK_INT >= 14 ? "androidPackageName" : "androidPackageName";
                    f922iH = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.auth.GetToken");
                    f923iI = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.recovery.RecoveryService");
                    f924iJ = new Intent().setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE).setComponent(f922iH);
                    f925iK = new Intent().setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE).setComponent(f923iI);
                }

                private GoogleAuthUtil() {
                }

                
                private static String m1002a(Context context, String str, String str2, Bundle bundle) throws IOException, GoogleAuthException {
                    int i;
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    try {
                        return getToken(context, str, str2, bundle);
                    } catch (GooglePlayServicesAvailabilityException e) {
                        PendingIntent errorPendingIntent = GooglePlayServicesUtil.getErrorPendingIntent(e.getConnectionStatusCode(), context, 0);
                        Resources resources = context.getResources();
                        Notification notification = new Notification(R.drawable.stat_sys_warning, resources.getString(C0881R.string.auth_client_play_services_err_notification_msg), System.currentTimeMillis());
                        notification.flags |= 16;
                        String packageName = context.getApplicationInfo().name;
                        if (TextUtils.isEmpty(packageName)) {
                            packageName = context.getPackageName();
                        }
                        String string = resources.getString(C0881R.string.auth_client_requested_by_msg, packageName);
                        switch (e.getConnectionStatusCode()) {
                            case 1:
                                i = C0881R.string.auth_client_needs_installation_title;
                                break;
                            case 2:
                                i = C0881R.string.auth_client_needs_update_title;
                                break;
                            case 3:
                                i = C0881R.string.auth_client_needs_enabling_title;
                                break;
                            default:
                                i = C0881R.string.auth_client_using_bad_version_title;
                                break;
                        }
                        notification.setLatestEventInfo(context, resources.getString(i), string, errorPendingIntent);
                        ((NotificationManager) context.getSystemService("notification")).notify(39789, notification);
                        throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
                    } catch (UserRecoverableAuthException e2) {
                        throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
                    }
                }

                
                private static void m1003b(Intent intent) {
                    if (intent == null) {
                        throw new IllegalArgumentException("Callack cannot be null.");
                    }
                    try {
                        Intent.parseUri(intent.toUri(1), 1);
                    } catch (URISyntaxException e) {
                        throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
                    }
                }

                public static String getToken(Context context, String accountName, String scope) throws IOException, GoogleAuthException {
                    return getToken(context, accountName, scope, new Bundle());
                }

                public static String getToken(Context context, String accountName, String scope, Bundle extras) throws GoogleAuthException, IOException {
                    Context applicationContext = context.getApplicationContext();
                    C1028dm.m1609x("Calling this from your main thread can lead to deadlock");
                    m1004m(applicationContext);
                    Bundle extras2 = extras == null ? new Bundle() : new Bundle(extras);
                    String str = context.getApplicationInfo().packageName;
                    extras2.putString(KEY_CLIENT_PACKAGE_NAME, str);
                    if (!extras2.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
                        extras2.putString(KEY_ANDROID_PACKAGE_NAME, str);
                    }
                    ServiceConnectionC0887a serviceConnectionC0887a = new ServiceConnectionC0887a();
                    try {
                        if (!applicationContext.bindService(f924iJ, serviceConnectionC0887a, 1)) {
                            throw new IOException("Could not bind to service with the given context.");
                        }
                        try {
                            try {
                                Bundle bundleMo2148a = InterfaceC1117o.a.m2149a(serviceConnectionC0887a.m1028aG()).mo2148a(accountName, scope, extras2);
                                String string = bundleMo2148a.getString("authtoken");
                                if (!TextUtils.isEmpty(string)) {
                                    applicationContext.unbindService(serviceConnectionC0887a);
                                    return string;
                                }
                                String string2 = bundleMo2148a.getString("Error");
                                Intent intent = (Intent) bundleMo2148a.getParcelable("userRecoveryIntent");
                                if (m1006s(string2)) {
                                    throw new UserRecoverableAuthException(string2, intent);
                                }
                                if (m1005r(string2)) {
                                    throw new IOException(string2);
                                }
                                throw new GoogleAuthException(string2);
                            } catch (InterruptedException e) {
                                throw new GoogleAuthException("Interrupted");
                            }
                        } catch (RemoteException e2) {
                            Log.i("GoogleAuthUtil", "GMS remote exception ", e2);
                            throw new IOException("remote exception");
                        }
                    } catch (Throwable th) {
                        applicationContext.unbindService(serviceConnectionC0887a);
                        throw th;
                    }
                }

                public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras) throws IOException, GoogleAuthException {
                    if (extras == null) {
                        extras = new Bundle();
                    }
                    extras.putBoolean("handle_notification", true);
                    return m1002a(context, accountName, scope, extras);
                }

                public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras, Intent callback) throws IOException, GoogleAuthException {
                    m1003b(callback);
                    if (extras == null) {
                        extras = new Bundle();
                    }
                    extras.putParcelable("callback_intent", callback);
                    extras.putBoolean("handle_notification", true);
                    return m1002a(context, accountName, scope, extras);
                }

                public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras, String authority, Bundle syncBundle) throws IOException, GoogleAuthException {
                    if (TextUtils.isEmpty(authority)) {
                        throw new IllegalArgumentException("Authority cannot be empty or null.");
                    }
                    if (extras == null) {
                        extras = new Bundle();
                    }
                    if (syncBundle == null) {
                        syncBundle = new Bundle();
                    }
                    ContentResolver.validateSyncExtrasBundle(syncBundle);
                    extras.putString("authority", authority);
                    extras.putBundle("sync_extras", syncBundle);
                    extras.putBoolean("handle_notification", true);
                    return m1002a(context, accountName, scope, extras);
                }

                public static void invalidateToken(Context context, String token) {
                    AccountManager.get(context).invalidateAuthToken(GOOGLE_ACCOUNT_TYPE, token);
                }

                
                private static void m1004m(Context context) throws GoogleAuthException {
                    try {
                        GooglePlayServicesUtil.m1020m(context);
                    } catch (GooglePlayServicesNotAvailableException e) {
                        throw new GoogleAuthException(e.getMessage());
                    } catch (GooglePlayServicesRepairableException e2) {
                        throw new GooglePlayServicesAvailabilityException(e2.getConnectionStatusCode(), e2.getMessage(), e2.getIntent());
                    }
                }

                
                private static boolean m1005r(String str) {
                    return "NetworkError".equals(str) || "ServiceUnavailable".equals(str) || "Timeout".equals(str);
                }

                
                private static boolean m1006s(String str) {
                    return "BadAuthentication".equals(str) || "CaptchaRequired".equals(str) || "DeviceManagementRequiredOrSyncDisabled".equals(str) || "NeedPermission".equals(str) || "NeedsBrowser".equals(str) || "UserCancel".equals(str) || "AppDownloadRequired".equals(str);
                }
            }
