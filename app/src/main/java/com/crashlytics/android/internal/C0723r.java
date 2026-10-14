            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.app.Service;
            import android.content.Context;
            import android.os.Bundle;
            import android.util.Log;
            import com.crashlytics.android.Crashlytics;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.r */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public class C0723r implements InterfaceC0722q {
                /* JADX INFO: renamed from: b */
/* 8 */         private static boolean m356b(int i) {
                    return C0730y.m373a().m369g() <= i;
                }

                /* JADX INFO: renamed from: a */
/* 21 */        public static String m354a(Context context, boolean z) {
                    String string;
                    int iM195a;
                    try {
/* 22 */                Context applicationContext = context.getApplicationContext();
/* 26 */                Bundle bundle = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128).metaData;
/* 28 */                string = bundle != null ? bundle.getString("com.crashlytics.ApiKey") : null;
                    } catch (Exception e) {
/* 33 */                C0730y.m373a().m365b().mo96a(Crashlytics.TAG, "Caught non-fatal exception while retrieving apiKey: " + e);
                    }
/* 36 */            if (C0681ab.m228e(string) && (iM195a = C0681ab.m195a(context, "com.crashlytics.ApiKey", "string")) != 0) {
/* 40 */                string = context.getResources().getString(iM195a);
                    }
/* 44 */            if (C0681ab.m228e(string)) {
/* 45 */                if (z || C0681ab.m230f(context)) {
/* 46 */                    throw new IllegalArgumentException("Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>");
                        }
/* 48 */                C0730y.m373a().m365b().mo97a(Crashlytics.TAG, "Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>", (Throwable) null);
                    }
/* 52 */            return string;
                }

                /* JADX INFO: renamed from: a */
/* 25 */        public static int m352a(int i) {
/* 26 */            if (i >= 200 && i <= 299) {
                        return 0;
                    }
/* 28 */            if (i >= 300 && i <= 399) {
                        return 1;
                    }
/* 30 */            if (i < 400 || i > 499) {
                        return i >= 500 ? 1 : 1;
                    }
                    return 0;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: a */
/* 41 */        public final void mo97a(String str, String str2, Throwable th) {
/* 42 */            if (m356b(6)) {
/* 43 */                Log.e(str, str2, th);
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: a */
/* 48 */        public final void mo96a(String str, String str2) {
/* 49 */            if (m356b(3)) {
                        Log.d(str, str2, null);
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: b */
/* 58 */        public final void mo98b(String str, String str2) {
/* 59 */            if (m356b(4)) {
                        Log.i(str, str2, null);
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: c */
/* 63 */        public final void mo99c(String str, String str2) {
/* 64 */            if (m356b(5)) {
                        Log.w(str, str2, null);
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: d */
/* 68 */        public final void mo100d(String str, String str2) {
/* 69 */            mo97a(str, str2, (Throwable) null);
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: a */
/* 73 */        public final void mo94a(int i, String str, String str2) {
/* 74 */            mo95a(i, str, str2, false);
                }

                @Override // com.crashlytics.android.internal.InterfaceC0722q
                /* JADX INFO: renamed from: a */
/* 78 */        public final void mo95a(int i, String str, String str2, boolean z) {
/* 79 */            if (z || m356b(i)) {
/* 80 */                Log.println(i, str, str2);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 275 */       static /* synthetic */ Activity m353a(Context context) {
/* 276 */           if (context instanceof Activity) {
                        return (Activity) context;
                    }
                    return null;
                }

                /* JADX INFO: renamed from: b */
/* 275 */       static /* synthetic */ Application m355b(Context context) {
/* 276 */           if (context instanceof Application) {
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
