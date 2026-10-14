            package com.crashlytics.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.ContextWrapper;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import com.crashlytics.android.internal.AbstractC0726u;
            import com.crashlytics.android.internal.C0627A;
            import com.crashlytics.android.internal.C0630D;
            import com.crashlytics.android.internal.C0666aM;
            import com.crashlytics.android.internal.C0670aQ;
            import com.crashlytics.android.internal.C0671aR;
            import com.crashlytics.android.internal.C0672aS;
            import com.crashlytics.android.internal.C0677aX;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0685af;
            import com.crashlytics.android.internal.C0686ag;
            import com.crashlytics.android.internal.C0694ao;
            import com.crashlytics.android.internal.C0701av;
            import com.crashlytics.android.internal.C0704ay;
            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.EnumC0688ai;
            import com.crashlytics.android.internal.EnumC0703ax;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.net.URL;
            import java.util.Collections;
            import java.util.Map;
            import java.util.concurrent.ConcurrentHashMap;
            import java.util.concurrent.CountDownLatch;
            import java.util.concurrent.TimeUnit;
            import javax.net.ssl.HttpsURLConnection;
            
            
    public final class Crashlytics extends AbstractC0726u {
                public static final String TAG = "Crashlytics";

                
                private static ContextWrapper f128j;

                
                private static String f129k;

                
                private static String f130l;

                
                private static String f131m;

                
                private static String f132n;

                
                private static String f133o;

                
                private static String f134p;

                
                private static String f135q;

                
                private static boolean f136r = false;

                
                private static PinningInfoProvider f137s = null;

                
                private static C0701av f138t;

                
                private static float f139u;

                
                private static Crashlytics f140v;

                
                private CrashlyticsListener f143c;

                
                private C0744v f144d;

                
                private String f149i;

                
                private C0694ao f145e = null;

                
                private String f146f = null;

                
                private String f147g = null;

                
                private String f148h = null;

                
                private final ConcurrentHashMap<String, String> f142b = new ConcurrentHashMap<>();

                
                private final long f141a = System.currentTimeMillis();

                
        static  int m6a(Crashlytics crashlytics, float f, int i) {
            return (int) (i * f);
                }

                
        static  C0744v m8a(Crashlytics crashlytics) {
            return crashlytics.f144d;
                }

                
        static  boolean m14a(Crashlytics crashlytics, Activity activity, C0670aQ c0670aQ) {
            C0610X c0610x = new C0610X(activity, c0670aQ);
                    C0743u c0743u = new C0743u(crashlytics, (byte) 0);
                    activity.runOnUiThread(new RunnableC0738p(crashlytics, activity, c0743u, c0610x, c0670aQ));
                    C0727v.m357a().m365b().mo96a(TAG, "Waiting for user opt-in.");
                    c0743u.m377b();
                    return c0743u.m376a();
                }

                
        static  boolean m15a(Crashlytics crashlytics, Context context, float f) {
            return crashlytics.m13a(context, f);
                }

                
        static  C0701av m25s() {
            return f138t;
                }

       public static void start(Context context) {
           start(context, 1.0f);
                }

       public static void start(Context context, float delay) {
           f139u = delay;
           if (!C0681ab.m225d(context)) {
               C0727v.m357a().m362a(new C0627A());
                    }
           C0727v.m360a(context, getInstance(), new C0630D());
                }

       public static synchronized Crashlytics getInstance() {
                    Crashlytics crashlytics;
                    try {
                        crashlytics = (Crashlytics) C0727v.m357a().m361a(Crashlytics.class);
               if (crashlytics == null) {
                   if (f140v == null) {
                       f140v = new Crashlytics();
                            }
                   crashlytics = f140v;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return crashlytics;
                }

       public static void logException(Throwable throwable) {
           Crashlytics crashlytics = getInstance();
           if (crashlytics == null || crashlytics.f144d == null) {
               C0727v.m357a().m365b().mo97a(TAG, "Crashlytics must be initialized by calling Crashlytics.start(Context) prior to logging exceptions.", (Throwable) null);
                    } else if (throwable == null) {
               C0727v.m357a().m365b().mo94a(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
                    } else {
               crashlytics.f144d.m432a(Thread.currentThread(), throwable);
                    }
                }

                
       static void m10a(String str) {
           C0630D c0630d = (C0630D) C0727v.m357a().m361a(C0630D.class);
           if (c0630d != null) {
               c0630d.m107a(new C0686ag(str));
                    }
                }

                
       static void m16b(String str) {
           C0630D c0630d = (C0630D) C0727v.m357a().m361a(C0630D.class);
           if (c0630d != null) {
               c0630d.m106a(new C0685af(str));
                    }
                }

       public static void log(String msg) {
           m9a(3, TAG, msg);
                }

                
       private static void m9a(int i, String str, String str2) {
           Crashlytics crashlytics = getInstance();
           if (crashlytics == null || crashlytics.f144d == null) {
               C0727v.m357a().m365b().mo97a(str, "Crashlytics must be initialized by calling Crashlytics.start(Context) prior to logging messages.", (Throwable) null);
                    } else {
               crashlytics.f144d.m431a(System.currentTimeMillis() - crashlytics.f141a, C0681ab.m217b(i) + "/" + str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str2);
                    }
                }

       public static void log(int priority, String tag, String msg) {
           m9a(priority, tag, msg);
           C0727v.m357a().m365b().mo95a(priority, tag, msg, true);
                }

       public static void setUserIdentifier(String identifier) {
           getInstance().f146f = m17c(identifier);
                }

       public static void setUserName(String name) {
           getInstance().f148h = m17c(name);
                }

       public static void setUserEmail(String email) {
           getInstance().f147g = m17c(email);
                }

       public static void setApplicationInstallationIdentifier(String identifier) {
           C0727v.m357a().m363a(m17c(identifier));
                }

       public static void setString(String key, String value) {
                    String value2;
           if (key == null) {
               if (f128j != null && C0681ab.m230f(f128j)) {
                   throw new IllegalArgumentException("Custom attribute key cannot be null.");
                        }
               C0727v.m357a().m365b().mo97a(TAG, "Attempting to set custom attribute with null key, ignoring.", (Throwable) null);
               return;
                    }
           String key2 = m17c(key);
           if (getInstance().f142b.size() < 64 || getInstance().f142b.containsKey(key2)) {
               if (value == null) {
                   value2 = "";
                        } else {
                   value2 = m17c(value);
                        }
               getInstance().f142b.put(key2, value2);
               return;
                    }
           C0727v.m357a().m365b().mo96a(TAG, "Exceeded maximum number of custom attributes (64)");
                }

       public static void setBool(String key, boolean value) {
           setString(key, Boolean.toString(value));
                }

       public static void setDouble(String key, double value) {
           setString(key, Double.toString(value));
                }

       public static void setFloat(String key, float value) {
           setString(key, Float.toString(value));
                }

       public static void setInt(String key, int value) {
           setString(key, Integer.toString(value));
                }

       public static void setLong(String key, long value) {
           setString(key, Long.toString(value));
                }

                
       final Map<String, String> m26a() {
           return Collections.unmodifiableMap(this.f142b);
                }

       public final void setListener(CrashlyticsListener listener) {
           this.f143c = listener;
                }

       public final void setDebugMode(boolean debug) {
           C0727v.m357a().m364a(debug);
                }

       public final boolean getDebugMode() {
           return C0727v.m357a().m368f();
                }

       public static void setPinningInfoProvider(PinningInfoProvider pinningInfo) {
           if (f137s != pinningInfo) {
               f137s = pinningInfo;
               if (f138t != null) {
                   if (pinningInfo == null) {
                       f138t.m298a(null);
                            } else {
                       f138t.m298a(new C0733k(pinningInfo));
                            }
                        }
                    }
                }

       public static PinningInfoProvider getPinningInfoProvider() {
           return f137s;
                }

       public final boolean verifyPinning(URL url) {
                    try {
               if (getPinningInfoProvider() == null) {
                            return false;
                        }
                        C0704ay c0704ayM296a = f138t.m296a(EnumC0703ax.GET, url.toString());
                        ((HttpsURLConnection) c0704ayM296a.m330a()).setInstanceFollowRedirects(false);
                        c0704ayM296a.m331b();
                        return true;
                    } catch (Exception e) {
               C0727v.m357a().m365b().mo97a(TAG, "Could not verify SSL pinning", e);
                        return false;
                    }
                }

                @Deprecated
       public static String getCrashlyticsVersion() {
           return getInstance().getVersion();
                }

       public final void crash() {
           new CrashTest().indexOutOfBounds();
                }

                
       final C0694ao m27b() {
           return this.f145e;
                }

                @Override 
                
       protected final void mo28c() {
           Context context = super.getContext();
           String strM354a = C0723r.m354a(context, false);
           if (strM354a != null) {
                        try {
                   m11a(strM354a, context, f139u);
                        } catch (CrashlyticsMissingDependencyException e) {
                   throw e;
                        } catch (Exception e2) {
                   C0727v.m357a().m365b().mo97a(TAG, "Crashlytics was not started due to an exception during initialization", e2);
                        }
                    }
                }

                @Override 
       public final String getVersion() {
           return C0727v.m357a().getVersion();
                }

                
       static String m18d() {
           return f129k;
                }

                
       static String m19e() {
           return f130l;
                }

                
       static String m20f() {
           return f133o;
                }

                
       static String m21g() {
           return f132n;
                }

                
       static String m22h() {
           return f131m;
                }

                
       static String m23i() {
           return C0681ab.m202a(f128j, "com.crashlytics.ApiEndpoint");
                }

                
       final boolean m29j() {
           return ((Boolean) C0672aS.m180a().m183a(new C0734l(this), false)).booleanValue();
                }

                
       static boolean m24k() {
           return C0681ab.m200a().getBoolean("always_send_reports_opt_in", false);
                }

                
       static void m12a(boolean z) {
           C0681ab.m200a().edit().putBoolean("always_send_reports_opt_in", true).commit();
                }

                
       final C0744v m30l() {
           return this.f144d;
                }

                
       final String m31m() {
           if (this.f145e.m258a()) {
                        return this.f146f;
                    }
                    return null;
                }

                
       final String m32n() {
           if (this.f145e.m258a()) {
                        return this.f147g;
                    }
                    return null;
                }

                
       final String m33o() {
           if (this.f145e.m258a()) {
                        return this.f148h;
                    }
                    return null;
                }

                
       final boolean m34p() {
           return ((Boolean) C0672aS.m180a().m183a(new C0735m(this), true)).booleanValue();
                }

                
       final InterfaceC0608V m35q() {
           return (InterfaceC0608V) C0672aS.m180a().m183a(new C0736n(this), null);
                }

                
       final C0671aR m36r() {
           return (C0671aR) C0672aS.m180a().m183a(new C0737o(this), null);
                }

                
       private static String m17c(String str) {
           if (str != null) {
               String strTrim = str.trim();
               if (strTrim.length() > 1024) {
                   return strTrim.substring(0, 1024);
                        }
               return strTrim;
                    }
           return str;
                }

                
       private synchronized void m11a(String str, Context context, float f) {
                    boolean zM439f = false;
                    synchronized (this) {
                        try {
                            if (f128j != null) {
                       C0727v.m357a().m365b().mo96a(TAG, "Crashlytics already started, ignoring re-initialization attempt.");
                            } else {
                       f134p = str;
                       f128j = new ContextWrapper(context.getApplicationContext());
                       f138t = new C0701av(C0727v.m357a().m365b());
                       C0727v.m357a().m365b().mo98b(TAG, "Initializing Crashlytics " + getCrashlyticsVersion());
                                try {
                           f129k = f128j.getPackageName();
                           PackageManager packageManager = f128j.getPackageManager();
                           f130l = packageManager.getInstallerPackageName(f129k);
                           C0727v.m357a().m365b().mo96a(TAG, "Installer package name is: " + f130l);
                           PackageInfo packageInfo = packageManager.getPackageInfo(f129k, 0);
                           f132n = Integer.toString(packageInfo.versionCode);
                           f133o = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
                           f131m = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
                           f135q = Integer.toString(context.getApplicationInfo().targetSdkVersion);
                           this.f149i = C0681ab.m233i(context);
                                } catch (Exception e) {
                           C0727v.m357a().m365b().mo97a(TAG, "Error setting up app properties", e);
                                }
                       this.f145e = new C0694ao(f128j);
                       this.f145e.m265h();
                       new C0620c(this.f149i, C0681ab.m213a((Context) f128j, "com.crashlytics.RequireBuildId", true)).m61a(str, f129k);
                                try {
                           C0727v.m357a().m365b().mo96a(TAG, "Installing exception handler...");
                           this.f144d = new C0744v(Thread.getDefaultUncaughtExceptionHandler(), this.f143c, this.f149i);
                           zM439f = this.f144d.m439f();
                           this.f144d.m437d();
                           this.f144d.m436c();
                           this.f144d.m441h();
                           Thread.setDefaultUncaughtExceptionHandler(this.f144d);
                           C0727v.m357a().m365b().mo96a(TAG, "Successfully installed exception handler.");
                                } catch (Exception e2) {
                           C0727v.m357a().m365b().mo97a(TAG, "There was a problem installing the exception handler.", e2);
                                }
                       CountDownLatch countDownLatch = new CountDownLatch(1);
                       new Thread(new C0742t(this, context, f, countDownLatch), "Crashlytics Initializer").start();
                       if (zM439f) {
                           C0727v.m357a().m365b().mo96a(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
                                    try {
                               if (!countDownLatch.await(4000L, TimeUnit.MILLISECONDS)) {
                                   C0727v.m357a().m365b().mo99c(TAG, "Crashlytics initialization was not completed in the allotted time.");
                                        }
                                    } catch (InterruptedException e3) {
                               C0727v.m357a().m365b().mo97a(TAG, "Crashlytics was interrupted during initialization.", e3);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       private boolean m13a(Context context, float f) throws Throwable {
                    C0677aX c0677aXM184b;
                    boolean z;
                    boolean z2;
                    boolean zM186d;
                    boolean zM435b = true;
                    boolean z3 = false;
           String strM231g = C0681ab.m231g(getContext());
                    try {
               C0672aS.m180a().m182a(context, f138t, f132n, f133o, m23i()).m185c();
               c0677aXM184b = C0672aS.m180a().m184b();
                    } catch (Exception e) {
               C0727v.m357a().m365b().mo97a(TAG, "Error dealing with settings", e);
                        c0677aXM184b = null;
                    }
           if (c0677aXM184b != null) {
                        try {
                   C0666aM c0666aM = c0677aXM184b.f336a;
                            if ("new".equals(c0666aM.f308a)) {
                                if (new C0606T(m23i(), c0666aM.f309b, f138t).m53a(m7a(C0611Y.m46a(getContext(), strM231g)))) {
                                    zM186d = C0672aS.m180a().m186d();
                                } else {
                           C0727v.m357a().m365b().mo97a(TAG, "Failed to create app with Crashlytics service.", (Throwable) null);
                                    zM186d = false;
                                }
                            } else if ("configured".equals(c0666aM.f308a)) {
                                zM186d = C0672aS.m180a().m186d();
                            } else {
                                if (c0666aM.f311d) {
                                    C0727v.m357a().m365b().mo96a(TAG, "Server says an update is required - forcing a full App update.");
                                    new C0617ad(m23i(), c0666aM.f309b, f138t).m53a(m7a(C0611Y.m46a(getContext(), strM231g)));
                                }
                                zM186d = true;
                            }
                            z = zM186d;
                        } catch (Exception e2) {
                   C0727v.m357a().m365b().mo97a(TAG, "Error performing auto configuration.", e2);
                            z = false;
                        }
                        try {
                   z2 = c0677aXM184b.f339d.f313b;
                        } catch (Exception e3) {
                   C0727v.m357a().m365b().mo97a(TAG, "Error getting collect reports setting.", e3);
                            z2 = false;
                        }
                    } else {
                        z2 = false;
                        z = false;
                    }
           if (z && z2) {
                        try {
                   zM435b = this.f144d.m435b() & true;
                   InterfaceC0608V interfaceC0608VM35q = m35q();
                   if (interfaceC0608VM35q != null) {
                       new C0614aa(interfaceC0608VM35q).m57a(f);
                            }
                        } catch (Exception e4) {
                   C0727v.m357a().m365b().mo97a(TAG, "Error sending crash report", e4);
                        }
                    } else {
                        z3 = true;
                    }
           if (z3) {
               C0727v.m357a().m365b().mo96a(TAG, "Crash reporting disabled.");
                    }
           return zM435b;
                }

                
       private C0619b m7a(C0611Y c0611y) {
           return new C0619b(f134p, f129k, f133o, f132n, C0681ab.m208a(this.f149i), f131m, EnumC0688ai.m241a(f130l).m242a(), f135q, "0", c0611y);
                }
            }
