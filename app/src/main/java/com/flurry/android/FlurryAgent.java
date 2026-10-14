            package com.flurry.android;

            import android.content.Context;
            import android.location.Criteria;
            import android.os.Build;
            import com.flurry.sdk.C0784cu;
            import com.flurry.sdk.C0798di;
            import com.flurry.sdk.C0800dk;
            import com.flurry.sdk.C0802dm;
            import com.flurry.sdk.C0812dw;
            import com.flurry.sdk.C0827el;
            import com.flurry.sdk.C0843fb;
            import java.util.Date;
            import java.util.Map;
            
            
    public final class FlurryAgent {

                
                private static final String f527a = FlurryAgent.class.getSimpleName();

        private FlurryAgent() {
                }

        public static void setVersionName(String str) {
            if (Build.VERSION.SDK_INT < 10) {
                C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (str == null) {
                C0827el.m893b(f527a, "String versionName passed to setVersionName was null.");
                    } else {
                C0802dm.m742a().m748a("VersionName", (Object) str);
                    }
                }

        public static int getAgentVersion() {
            return C0800dk.m720a().m721b();
                }

        public static String getReleaseVersion() {
            return C0800dk.m720a().m726g();
                }

        public static void setReportLocation(boolean z) {
            if (Build.VERSION.SDK_INT < 10) {
                C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
                C0802dm.m742a().m748a("ReportLocation", (Object) Boolean.valueOf(z));
                    }
                }

       public static void setLocation(float f, float f2) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0812dw.m781a().m789a(f, f2);
                    }
                }

       public static void clearLocation() {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0812dw.m781a().m793e();
                    }
                }

       public static void setLogEnabled(boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (z) {
               C0827el.m890b();
                    } else {
               C0827el.m883a();
                    }
                }

       public static void setLogLevel(int i) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0827el.m884a(i);
                    }
                }

       public static void setContinueSessionMillis(long j) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (j < 5000) {
               C0827el.m893b(f527a, "Invalid time set for session resumption: " + j);
                    } else {
               C0802dm.m742a().m748a("ContinueSessionMillis", (Object) Long.valueOf(j));
                    }
                }

       public static void setLogEvents(boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0802dm.m742a().m748a("LogEvents", (Object) Boolean.valueOf(z));
                    }
                }

       public static void setUseHttps(boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0802dm.m742a().m748a("UseHttps", (Object) Boolean.valueOf(z));
                    }
                }

       public static boolean getUseHttps() {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return false;
                    }
           return ((Boolean) C0802dm.m742a().m746a("UseHttps")).booleanValue();
                }

       public static void setCaptureUncaughtExceptions(boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0802dm.m742a().m748a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
                    }
                }

       public static void addOrigin(String str, String str2) {
           addOrigin(str, str2, null);
                }

       public static void addOrigin(String str, String str2, Map<String, String> map) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null || str.length() == 0) {
               throw new IllegalArgumentException("originName not specified");
                    }
           if (str2 == null || str2.length() == 0) {
               throw new IllegalArgumentException("originVersion not specified");
                    }
                    try {
               C0784cu.m532a().m538a(str, str2, map);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

       public static void onStartSession(Context context, String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (context == null) {
               throw new NullPointerException("Null context");
                    }
           if (str == null || str.length() == 0) {
               throw new IllegalArgumentException("Api key not specified");
                    }
                    try {
               C0798di.m709a().m711a(context, str);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

       public static void onEndSession(Context context) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               if (context == null) {
                   throw new NullPointerException("Null context");
                        }
                        try {
                   C0798di.m709a().m710a(context);
                        } catch (Throwable th) {
                   C0827el.m888a(f527a, "", th);
                        }
                    }
                }

       public static void logEvent(String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m534a(str);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

       public static void logEvent(String str, Map<String, String> map) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
               return;
                    }
           if (map == null) {
               C0827el.m893b(f527a, "String parameters passed to logEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m539a(str, map);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

       public static void logEvent(String str, boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m541a(str, z);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

       public static void logEvent(String str, Map<String, String> map, boolean z) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
               return;
                    }
           if (map == null) {
               C0827el.m893b(f527a, "String parameters passed to logEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m540a(str, map, z);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

       public static void endTimedEvent(String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m544b(str);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to signify the end of event: " + str, th);
                    }
                }

       public static void endTimedEvent(String str, Map<String, String> map) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
               return;
                    }
           if (map == null) {
               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m545b(str, map);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "Failed to signify the end of event: " + str, th);
                    }
                }

                @Deprecated
       public static void onError(String str, String str2, String str3) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String errorId passed to onError was null.");
               return;
                    }
           if (str2 == null) {
               C0827el.m893b(f527a, "String message passed to onError was null.");
               return;
                    }
           if (str3 == null) {
               C0827el.m893b(f527a, "String errorClass passed to onError was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m536a(str, str2, str3);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

       public static void onError(String str, String str2, Throwable th) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String errorId passed to onError was null.");
               return;
                    }
           if (str2 == null) {
               C0827el.m893b(f527a, "String message passed to onError was null.");
               return;
                    }
           if (th == null) {
               C0827el.m893b(f527a, "Throwable passed to onError was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m537a(str, str2, th);
                    } catch (Throwable th2) {
               C0827el.m888a(f527a, "", th2);
                    }
                }

                @Deprecated
       public static void onEvent(String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to onEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m547c(str);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

                @Deprecated
       public static void onEvent(String str, Map<String, String> map) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
           if (str == null) {
               C0827el.m893b(f527a, "String eventId passed to onEvent was null.");
               return;
                    }
           if (map == null) {
               C0827el.m893b(f527a, "Parameters Map passed to onEvent was null.");
               return;
                    }
                    try {
               C0784cu.m532a().m548c(str, map);
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

       public static void onPageView() {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
               return;
                    }
                    try {
               C0784cu.m532a().m546c();
                    } catch (Throwable th) {
               C0827el.m888a(f527a, "", th);
                    }
                }

       public static void setReportUrl(String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0802dm.m742a().m748a("ReportUrl", (Object) str);
                    }
                }

       public static void setLocationCriteria(Criteria criteria) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
               C0802dm.m742a().m748a("LocationCriteria", (Object) criteria);
                    }
                }

       public static void setAge(int i) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (i > 0 && i < 110) {
               C0802dm.m742a().m748a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
                    }
                }

       public static void setGender(byte b) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    }
                    switch (b) {
                        case 0:
                        case 1:
                   C0802dm.m742a().m748a("Gender", (Object) Byte.valueOf(b));
                            break;
                        default:
                   C0802dm.m742a().m748a("Gender", (Object) (byte) -1);
                            break;
                    }
                }

       public static void setUserId(String str) {
           if (Build.VERSION.SDK_INT < 10) {
               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (str == null) {
               C0827el.m893b(f527a, "String userId passed to setUserId was null.");
                    } else {
               C0802dm.m742a().m748a("UserId", (Object) C0843fb.m959a(str));
                    }
                }
            }
