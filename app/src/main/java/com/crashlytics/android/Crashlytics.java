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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public final class Crashlytics extends AbstractC0726u {
                public static final String TAG = "Crashlytics";

                /* JADX INFO: renamed from: j */
                private static ContextWrapper f128j;

                /* JADX INFO: renamed from: k */
                private static String f129k;

                /* JADX INFO: renamed from: l */
                private static String f130l;

                /* JADX INFO: renamed from: m */
                private static String f131m;

                /* JADX INFO: renamed from: n */
                private static String f132n;

                /* JADX INFO: renamed from: o */
                private static String f133o;

                /* JADX INFO: renamed from: p */
                private static String f134p;

                /* JADX INFO: renamed from: q */
                private static String f135q;

                /* JADX INFO: renamed from: r */
                private static boolean f136r = false;

                /* JADX INFO: renamed from: s */
                private static PinningInfoProvider f137s = null;

                /* JADX INFO: renamed from: t */
                private static C0701av f138t;

                /* JADX INFO: renamed from: u */
                private static float f139u;

                /* JADX INFO: renamed from: v */
                private static Crashlytics f140v;

                /* JADX INFO: renamed from: c */
                private CrashlyticsListener f143c;

                /* JADX INFO: renamed from: d */
                private C0744v f144d;

                /* JADX INFO: renamed from: i */
                private String f149i;

                /* JADX INFO: renamed from: e */
                private C0694ao f145e = null;

                /* JADX INFO: renamed from: f */
                private String f146f = null;

                /* JADX INFO: renamed from: g */
                private String f147g = null;

                /* JADX INFO: renamed from: h */
                private String f148h = null;

                /* JADX INFO: renamed from: b */
                private final ConcurrentHashMap<String, String> f142b = new ConcurrentHashMap<>();

                /* JADX INFO: renamed from: a */
                private final long f141a = System.currentTimeMillis();

                /* JADX INFO: renamed from: a */
/* 53 */        static /* synthetic */ int m6a(Crashlytics crashlytics, float f, int i) {
/* 54 */            return (int) (i * f);
                }

                /* JADX INFO: renamed from: a */
/* 53 */        static /* synthetic */ C0744v m8a(Crashlytics crashlytics) {
/* 54 */            return crashlytics.f144d;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        static /* synthetic */ boolean m14a(Crashlytics crashlytics, Activity activity, C0670aQ c0670aQ) {
/* 54 */            C0610X c0610x = new C0610X(activity, c0670aQ);
                    C0743u c0743u = new C0743u(crashlytics, (byte) 0);
                    activity.runOnUiThread(new RunnableC0738p(crashlytics, activity, c0743u, c0610x, c0670aQ));
                    C0727v.m357a().m365b().mo96a(TAG, "Waiting for user opt-in.");
                    c0743u.m377b();
                    return c0743u.m376a();
                }

                /* JADX INFO: renamed from: a */
/* 53 */        static /* synthetic */ boolean m15a(Crashlytics crashlytics, Context context, float f) {
/* 54 */            return crashlytics.m13a(context, f);
                }

                /* JADX INFO: renamed from: s */
/* 53 */        static /* synthetic */ C0701av m25s() {
/* 54 */            return f138t;
                }

/* 123 */       public static void start(Context context) {
/* 124 */           start(context, 1.0f);
                }

/* 134 */       public static void start(Context context, float delay) {
/* 135 */           f139u = delay;
/* 138 */           if (!C0681ab.m225d(context)) {
/* 139 */               C0727v.m357a().m362a(new C0627A());
                    }
/* 143 */           C0727v.m360a(context, getInstance(), new C0630D());
                }

/* 152 */       public static synchronized Crashlytics getInstance() {
                    Crashlytics crashlytics;
                    try {
                        crashlytics = (Crashlytics) C0727v.m357a().m361a(Crashlytics.class);
/* 155 */               if (crashlytics == null) {
/* 161 */                   if (f140v == null) {
/* 162 */                       f140v = new Crashlytics();
                            }
/* 164 */                   crashlytics = f140v;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return crashlytics;
                }

/* 175 */       public static void logException(Throwable throwable) {
/* 176 */           Crashlytics crashlytics = getInstance();
/* 177 */           if (crashlytics == null || crashlytics.f144d == null) {
/* 178 */               C0727v.m357a().m365b().mo97a(TAG, "Crashlytics must be initialized by calling Crashlytics.start(Context) prior to logging exceptions.", (Throwable) null);
                    } else if (throwable == null) {
/* 184 */               C0727v.m357a().m365b().mo94a(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
                    } else {
/* 188 */               crashlytics.f144d.m432a(Thread.currentThread(), throwable);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 194 */       static void m10a(String str) {
/* 195 */           C0630D c0630d = (C0630D) C0727v.m357a().m361a(C0630D.class);
/* 196 */           if (c0630d != null) {
/* 197 */               c0630d.m107a(new C0686ag(str));
                    }
                }

                /* JADX INFO: renamed from: b */
/* 205 */       static void m16b(String str) {
/* 206 */           C0630D c0630d = (C0630D) C0727v.m357a().m361a(C0630D.class);
/* 207 */           if (c0630d != null) {
/* 208 */               c0630d.m106a(new C0685af(str));
                    }
                }

/* 223 */       public static void log(String msg) {
/* 224 */           m9a(3, TAG, msg);
                }

                /* JADX INFO: renamed from: a */
/* 227 */       private static void m9a(int i, String str, String str2) {
/* 228 */           Crashlytics crashlytics = getInstance();
/* 229 */           if (crashlytics == null || crashlytics.f144d == null) {
/* 230 */               C0727v.m357a().m365b().mo97a(str, "Crashlytics must be initialized by calling Crashlytics.start(Context) prior to logging messages.", (Throwable) null);
                    } else {
/* 235 */               crashlytics.f144d.m431a(System.currentTimeMillis() - crashlytics.f141a, C0681ab.m217b(i) + "/" + str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str2);
                    }
                }

/* 251 */       public static void log(int priority, String tag, String msg) {
/* 252 */           m9a(priority, tag, msg);
/* 255 */           C0727v.m357a().m365b().mo95a(priority, tag, msg, true);
                }

/* 283 */       public static void setUserIdentifier(String identifier) {
/* 284 */           getInstance().f146f = m17c(identifier);
                }

/* 292 */       public static void setUserName(String name) {
/* 293 */           getInstance().f148h = m17c(name);
                }

/* 301 */       public static void setUserEmail(String email) {
/* 302 */           getInstance().f147g = m17c(email);
                }

/* 308 */       public static void setApplicationInstallationIdentifier(String identifier) {
/* 309 */           C0727v.m357a().m363a(m17c(identifier));
                }

/* 320 */       public static void setString(String key, String value) {
                    String value2;
/* 321 */           if (key == null) {
/* 322 */               if (f128j != null && C0681ab.m230f(f128j)) {
/* 323 */                   throw new IllegalArgumentException("Custom attribute key cannot be null.");
                        }
/* 325 */               C0727v.m357a().m365b().mo97a(TAG, "Attempting to set custom attribute with null key, ignoring.", (Throwable) null);
/* 341 */               return;
                    }
/* 329 */           String key2 = m17c(key);
/* 330 */           if (getInstance().f142b.size() < 64 || getInstance().f142b.containsKey(key2)) {
/* 332 */               if (value == null) {
/* 333 */                   value2 = "";
                        } else {
/* 335 */                   value2 = m17c(value);
                        }
/* 337 */               getInstance().f142b.put(key2, value2);
/* 341 */               return;
                    }
/* 339 */           C0727v.m357a().m365b().mo96a(TAG, "Exceeded maximum number of custom attributes (64)");
                }

/* 348 */       public static void setBool(String key, boolean value) {
/* 349 */           setString(key, Boolean.toString(value));
                }

/* 357 */       public static void setDouble(String key, double value) {
/* 358 */           setString(key, Double.toString(value));
                }

/* 366 */       public static void setFloat(String key, float value) {
/* 367 */           setString(key, Float.toString(value));
                }

/* 375 */       public static void setInt(String key, int value) {
/* 376 */           setString(key, Integer.toString(value));
                }

/* 384 */       public static void setLong(String key, long value) {
/* 385 */           setString(key, Long.toString(value));
                }

                /* JADX INFO: renamed from: a */
/* 389 */       final Map<String, String> m26a() {
/* 390 */           return Collections.unmodifiableMap(this.f142b);
                }

/* 398 */       public final void setListener(CrashlyticsListener listener) {
/* 399 */           this.f143c = listener;
                }

/* 407 */       public final void setDebugMode(boolean debug) {
/* 408 */           C0727v.m357a().m364a(debug);
                }

/* 416 */       public final boolean getDebugMode() {
/* 417 */           return C0727v.m357a().m368f();
                }

/* 424 */       public static void setPinningInfoProvider(PinningInfoProvider pinningInfo) {
/* 425 */           if (f137s != pinningInfo) {
/* 426 */               f137s = pinningInfo;
/* 428 */               if (f138t != null) {
/* 429 */                   if (pinningInfo == null) {
/* 430 */                       f138t.m298a(null);
                            } else {
/* 432 */                       f138t.m298a(new C0733k(pinningInfo));
                            }
                        }
                    }
                }

/* 459 */       public static PinningInfoProvider getPinningInfoProvider() {
/* 460 */           return f137s;
                }

/* 472 */       public final boolean verifyPinning(URL url) {
                    try {
/* 473 */               if (getPinningInfoProvider() == null) {
                            return false;
                        }
                        C0704ay c0704ayM296a = f138t.m296a(EnumC0703ax.GET, url.toString());
                        ((HttpsURLConnection) c0704ayM296a.m330a()).setInstanceFollowRedirects(false);
                        c0704ayM296a.m331b();
                        return true;
                    } catch (Exception e) {
/* 475 */               C0727v.m357a().m365b().mo97a(TAG, "Could not verify SSL pinning", e);
                        return false;
                    }
                }

                @Deprecated
/* 506 */       public static String getCrashlyticsVersion() {
/* 507 */           return getInstance().getVersion();
                }

/* 513 */       public final void crash() {
/* 514 */           new CrashTest().indexOutOfBounds();
                }

                /* JADX INFO: renamed from: b */
/* 517 */       final C0694ao m27b() {
/* 518 */           return this.f145e;
                }

                @Override // com.crashlytics.android.internal.AbstractC0721p
                /* JADX INFO: renamed from: c */
/* 522 */       protected final void mo28c() {
/* 523 */           Context context = super.getContext();
/* 524 */           String strM354a = C0723r.m354a(context, false);
/* 526 */           if (strM354a != null) {
                        try {
/* 531 */                   m11a(strM354a, context, f139u);
                        } catch (CrashlyticsMissingDependencyException e) {
/* 534 */                   throw e;
                        } catch (Exception e2) {
/* 536 */                   C0727v.m357a().m365b().mo97a(TAG, "Crashlytics was not started due to an exception during initialization", e2);
                        }
                    }
                }

                @Override // com.crashlytics.android.internal.AbstractC0721p
/* 541 */       public final String getVersion() {
/* 542 */           return C0727v.m357a().getVersion();
                }

                /* JADX INFO: renamed from: d */
/* 545 */       static String m18d() {
/* 546 */           return f129k;
                }

                /* JADX INFO: renamed from: e */
/* 555 */       static String m19e() {
/* 556 */           return f130l;
                }

                /* JADX INFO: renamed from: f */
/* 559 */       static String m20f() {
/* 560 */           return f133o;
                }

                /* JADX INFO: renamed from: g */
/* 563 */       static String m21g() {
/* 564 */           return f132n;
                }

                /* JADX INFO: renamed from: h */
/* 567 */       static String m22h() {
/* 568 */           return f131m;
                }

                /* JADX INFO: renamed from: i */
/* 571 */       static String m23i() {
/* 572 */           return C0681ab.m202a(f128j, "com.crashlytics.ApiEndpoint");
                }

                /* JADX INFO: renamed from: j */
/* 579 */       final boolean m29j() {
/* 580 */           return ((Boolean) C0672aS.m180a().m183a(new C0734l(this), false)).booleanValue();
                }

                /* JADX INFO: renamed from: k */
/* 592 */       static boolean m24k() {
/* 593 */           return C0681ab.m200a().getBoolean("always_send_reports_opt_in", false);
                }

                /* JADX INFO: renamed from: a */
/* 596 */       static void m12a(boolean z) {
/* 597 */           C0681ab.m200a().edit().putBoolean("always_send_reports_opt_in", true).commit();
                }

                /* JADX INFO: renamed from: l */
/* 600 */       final C0744v m30l() {
/* 601 */           return this.f144d;
                }

                /* JADX INFO: renamed from: m */
/* 604 */       final String m31m() {
/* 605 */           if (this.f145e.m258a()) {
                        return this.f146f;
                    }
                    return null;
                }

                /* JADX INFO: renamed from: n */
/* 608 */       final String m32n() {
/* 609 */           if (this.f145e.m258a()) {
                        return this.f147g;
                    }
                    return null;
                }

                /* JADX INFO: renamed from: o */
/* 612 */       final String m33o() {
/* 613 */           if (this.f145e.m258a()) {
                        return this.f148h;
                    }
                    return null;
                }

                /* JADX INFO: renamed from: p */
/* 616 */       final boolean m34p() {
/* 617 */           return ((Boolean) C0672aS.m180a().m183a(new C0735m(this), true)).booleanValue();
                }

                /* JADX INFO: renamed from: q */
/* 636 */       final InterfaceC0608V m35q() {
/* 637 */           return (InterfaceC0608V) C0672aS.m180a().m183a(new C0736n(this), null);
                }

                /* JADX INFO: renamed from: r */
/* 649 */       final C0671aR m36r() {
/* 650 */           return (C0671aR) C0672aS.m180a().m183a(new C0737o(this), null);
                }

                /* JADX INFO: renamed from: c */
/* 754 */       private static String m17c(String str) {
/* 755 */           if (str != null) {
/* 756 */               String strTrim = str.trim();
/* 757 */               if (strTrim.length() > 1024) {
/* 758 */                   return strTrim.substring(0, 1024);
                        }
/* 761 */               return strTrim;
                    }
/* 761 */           return str;
                }

                /* JADX INFO: renamed from: a */
/* 764 */       private synchronized void m11a(String str, Context context, float f) {
                    boolean zM439f = false;
                    synchronized (this) {
                        try {
                            if (f128j != null) {
/* 766 */                       C0727v.m357a().m365b().mo96a(TAG, "Crashlytics already started, ignoring re-initialization attempt.");
                            } else {
/* 770 */                       f134p = str;
/* 771 */                       f128j = new ContextWrapper(context.getApplicationContext());
/* 772 */                       f138t = new C0701av(C0727v.m357a().m365b());
/* 774 */                       C0727v.m357a().m365b().mo98b(TAG, "Initializing Crashlytics " + getCrashlyticsVersion());
                                try {
/* 777 */                           f129k = f128j.getPackageName();
/* 778 */                           PackageManager packageManager = f128j.getPackageManager();
/* 780 */                           f130l = packageManager.getInstallerPackageName(f129k);
/* 781 */                           C0727v.m357a().m365b().mo96a(TAG, "Installer package name is: " + f130l);
/* 783 */                           PackageInfo packageInfo = packageManager.getPackageInfo(f129k, 0);
/* 784 */                           f132n = Integer.toString(packageInfo.versionCode);
/* 785 */                           f133o = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
/* 786 */                           f131m = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
/* 788 */                           f135q = Integer.toString(context.getApplicationInfo().targetSdkVersion);
/* 791 */                           this.f149i = C0681ab.m233i(context);
                                } catch (Exception e) {
/* 794 */                           C0727v.m357a().m365b().mo97a(TAG, "Error setting up app properties", e);
                                }
/* 797 */                       this.f145e = new C0694ao(f128j);
/* 800 */                       this.f145e.m265h();
/* 803 */                       new C0620c(this.f149i, C0681ab.m213a((Context) f128j, "com.crashlytics.RequireBuildId", true)).m61a(str, f129k);
                                try {
/* 808 */                           C0727v.m357a().m365b().mo96a(TAG, "Installing exception handler...");
/* 809 */                           this.f144d = new C0744v(Thread.getDefaultUncaughtExceptionHandler(), this.f143c, this.f149i);
/* 816 */                           zM439f = this.f144d.m439f();
/* 819 */                           this.f144d.m437d();
/* 821 */                           this.f144d.m436c();
/* 822 */                           this.f144d.m441h();
/* 823 */                           Thread.setDefaultUncaughtExceptionHandler(this.f144d);
/* 824 */                           C0727v.m357a().m365b().mo96a(TAG, "Successfully installed exception handler.");
                                } catch (Exception e2) {
/* 826 */                           C0727v.m357a().m365b().mo97a(TAG, "There was a problem installing the exception handler.", e2);
                                }
/* 833 */                       CountDownLatch countDownLatch = new CountDownLatch(1);
/* 835 */                       new Thread(new C0742t(this, context, f, countDownLatch), "Crashlytics Initializer").start();
/* 855 */                       if (zM439f) {
/* 856 */                           C0727v.m357a().m365b().mo96a(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
                                    try {
/* 860 */                               if (!countDownLatch.await(4000L, TimeUnit.MILLISECONDS)) {
/* 861 */                                   C0727v.m357a().m365b().mo99c(TAG, "Crashlytics initialization was not completed in the allotted time.");
                                        }
                                    } catch (InterruptedException e3) {
/* 864 */                               C0727v.m357a().m365b().mo97a(TAG, "Crashlytics was interrupted during initialization.", e3);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 880 */       private boolean m13a(Context context, float f) throws Throwable {
                    C0677aX c0677aXM184b;
                    boolean z;
                    boolean z2;
                    boolean zM186d;
                    boolean zM435b = true;
                    boolean z3 = false;
/* 881 */           String strM231g = C0681ab.m231g(getContext());
                    try {
/* 884 */               C0672aS.m180a().m182a(context, f138t, f132n, f133o, m23i()).m185c();
/* 888 */               c0677aXM184b = C0672aS.m180a().m184b();
                    } catch (Exception e) {
/* 890 */               C0727v.m357a().m365b().mo97a(TAG, "Error dealing with settings", e);
                        c0677aXM184b = null;
                    }
/* 893 */           if (c0677aXM184b != null) {
                        try {
/* 895 */                   C0666aM c0666aM = c0677aXM184b.f336a;
                            if ("new".equals(c0666aM.f308a)) {
                                if (new C0606T(m23i(), c0666aM.f309b, f138t).m53a(m7a(C0611Y.m46a(getContext(), strM231g)))) {
                                    zM186d = C0672aS.m180a().m186d();
                                } else {
/* 895 */                           C0727v.m357a().m365b().mo97a(TAG, "Failed to create app with Crashlytics service.", (Throwable) null);
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
/* 897 */                   C0727v.m357a().m365b().mo97a(TAG, "Error performing auto configuration.", e2);
                            z = false;
                        }
                        try {
/* 900 */                   z2 = c0677aXM184b.f339d.f313b;
                        } catch (Exception e3) {
/* 902 */                   C0727v.m357a().m365b().mo97a(TAG, "Error getting collect reports setting.", e3);
                            z2 = false;
                        }
                    } else {
                        z2 = false;
                        z = false;
                    }
/* 907 */           if (z && z2) {
                        try {
/* 909 */                   zM435b = this.f144d.m435b() & true;
/* 911 */                   InterfaceC0608V interfaceC0608VM35q = m35q();
/* 912 */                   if (interfaceC0608VM35q != null) {
/* 913 */                       new C0614aa(interfaceC0608VM35q).m57a(f);
                            }
                        } catch (Exception e4) {
/* 917 */                   C0727v.m357a().m365b().mo97a(TAG, "Error sending crash report", e4);
                        }
                    } else {
                        z3 = true;
                    }
/* 920 */           if (z3) {
/* 921 */               C0727v.m357a().m365b().mo96a(TAG, "Crash reporting disabled.");
                    }
/* 923 */           return zM435b;
                }

                /* JADX INFO: renamed from: a */
/* 976 */       private C0619b m7a(C0611Y c0611y) {
/* 980 */           return new C0619b(f134p, f129k, f133o, f132n, C0681ab.m208a(this.f149i), f131m, EnumC0688ai.m241a(f130l).m242a(), f135q, "0", c0611y);
                }
            }
