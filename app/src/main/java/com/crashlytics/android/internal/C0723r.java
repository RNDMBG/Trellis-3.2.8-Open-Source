            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.app.Service;
            import android.content.Context;
            import android.os.Bundle;
            import android.util.Log;
            import com.crashlytics.android.Crashlytics;
            
            
            
     public class C0723r implements InterfaceC0722q {
                
         private static boolean m356b(int i) {
                    return C0730y.m373a().m369g() <= i;
                }

                
        public static String m354a(Context context, boolean z) {
                    String string;
                    int iM195a;
                    try {
                Context applicationContext = context.getApplicationContext();
                Bundle bundle = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128).metaData;
                string = bundle != null ? bundle.getString("com.crashlytics.ApiKey") : null;
                    } catch (Exception e) {
                C0730y.m373a().m365b().mo96a(Crashlytics.TAG, "Caught non-fatal exception while retrieving apiKey: " + e);
                    }
            if (C0681ab.m228e(string) && (iM195a = C0681ab.m195a(context, "com.crashlytics.ApiKey", "string")) != 0) {
                string = context.getResources().getString(iM195a);
                    }
            if (C0681ab.m228e(string)) {
                if (z || C0681ab.m230f(context)) {
                    throw new IllegalArgumentException("Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>");
                        }
                C0730y.m373a().m365b().mo97a(Crashlytics.TAG, "Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>", (Throwable) null);
                    }
            return string;
                }

                
        public static int m352a(int i) {
            if (i >= 200 && i <= 299) {
                        return 0;
                    }
            if (i >= 300 && i <= 399) {
                        return 1;
                    }
            if (i < 400 || i > 499) {
                        return i >= 500 ? 1 : 1;
                    }
                    return 0;
                }

                @Override 
                
        public final void mo97a(String str, String str2, Throwable th) {
            if (m356b(6)) {
                Log.e(str, str2, th);
                    }
                }

                @Override 
                
        public final void mo96a(String str, String str2) {
            if (m356b(3)) {
                        Log.d(str, str2, null);
                    }
                }

                @Override 
                
        public final void mo98b(String str, String str2) {
            if (m356b(4)) {
                        Log.i(str, str2, null);
                    }
                }

                @Override 
                
        public final void mo99c(String str, String str2) {
            if (m356b(5)) {
                        Log.w(str, str2, null);
                    }
                }

                @Override 
                
        public final void mo100d(String str, String str2) {
            mo97a(str, str2, (Throwable) null);
                }

                @Override 
                
        public final void mo94a(int i, String str, String str2) {
            mo95a(i, str, str2, false);
                }

                @Override 
                
        public final void mo95a(int i, String str, String str2, boolean z) {
            if (z || m356b(i)) {
                Log.println(i, str, str2);
                    }
                }

                
       static  Activity m353a(Context context) {
           if (context instanceof Activity) {
                        return (Activity) context;
                    }
                    return null;
                }

                
       static  Application m355b(Context context) {
           if (context instanceof Application) {
                        return (Application) context;
                    }
                    if (context instanceof Activity) {
                        return ((Activity) context).getApplication();
                    }
                    if (context instanceof Service) {
                        return ((Service) context).getApplication();
                    }
                    if (context.getApplicationContext() instanceof Application) {
                        return (Application) context.getApplicationContext();
                    }
                    return null;
                }
            }
