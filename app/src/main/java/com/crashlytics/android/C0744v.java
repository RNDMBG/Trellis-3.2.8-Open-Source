            package com.crashlytics.android;

            import android.app.ActivityManager;
            import android.content.BroadcastReceiver;
            import android.content.IntentFilter;
            import android.os.Build;
            import android.os.Environment;
            import android.os.StatFs;
            import com.crashlytics.android.internal.C0671aR;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0687ah;
            import com.crashlytics.android.internal.C0694ao;
            import com.crashlytics.android.internal.C0696aq;
            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.EnumC0688ai;
            import com.crashlytics.android.internal.EnumC0695ap;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FilenameFilter;
            import java.io.Flushable;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.PrintWriter;
            import java.io.Writer;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.Date;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Locale;
            import java.util.Map;
            import java.util.TreeMap;
            import java.util.UUID;
            import java.util.concurrent.Callable;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Future;
            import java.util.concurrent.RejectedExecutionException;
            import java.util.concurrent.atomic.AtomicBoolean;
            import java.util.concurrent.atomic.AtomicInteger;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.apache.commons.io.IOUtils;
            
            
            
    final class C0744v implements Thread.UncaughtExceptionHandler {

                
                static final FilenameFilter f493a = new C0745w();

                
                private static Comparator<File> f494b = new C0594H();

                
                private static Comparator<File> f495c = new C0596J();

                
                private static final Pattern f496d;

                
                private static final Map<String, String> f497e;

                
                private static final C0621d f498f;

                
                private final AtomicInteger f499g;

                
                private final AtomicBoolean f500h;

                
                private final int f501i;

                
                private final Thread.UncaughtExceptionHandler f502j;

                
                private final File f503k;

                
                private final File f504l;

                
                private final AtomicBoolean f505m;

                
                private final String f506n;

                
                private final BroadcastReceiver f507o;

                
                private final BroadcastReceiver f508p;

                
                private final C0621d f509q;

                
                private final C0621d f510r;

                
                private final ExecutorService f511s;

                
                private ActivityManager.RunningAppProcessInfo f512t;

                
                private C0696aq f513u;

                
                private boolean f514v;

                
                private Thread[] f515w;

                
                private List<StackTraceElement[]> f516x;

                
                private StackTraceElement[] f517y;

                
        static  AtomicBoolean m390a(C0744v c0744v) {
            return c0744v.f505m;
                }

                
        static  void m401a(C0744v c0744v, Date date, Thread thread, Throwable th) throws Exception {
                    C0623f c0623f;
                    C0623f c0623f2;
                    C0625h c0625hM70a = null;
                    try {
                        try {
                    new File(c0744v.f503k, "crash_marker").createNewFile();
                            String strM425n = c0744v.m425n();
                            if (strM425n != null) {
                                Crashlytics.m16b(strM425n);
                                c0623f = new C0623f(c0744v.f503k, strM425n + "SessionCrash");
                                try {
                                    c0625hM70a = C0625h.m70a(c0623f);
                                    c0744v.m406a(date, c0625hM70a, thread, th, "crash", true);
                                    c0623f2 = c0623f;
                                } catch (Exception e) {
                                    e = e;
                                    C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An error occurred in the fatal exception logger", e);
                                    c0744v.m404a(e, c0623f);
                                    C0681ab.m211a(c0625hM70a, "Failed to flush to session begin file.");
                                    C0681ab.m210a((Closeable) c0623f, "Failed to close fatal exception file output stream.");
                                }
                            } else {
                                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Tried to write a fatal exception while no session was open.", (Throwable) null);
                                c0623f2 = null;
                            }
                            C0681ab.m211a(c0625hM70a, "Failed to flush to session begin file.");
                            C0681ab.m210a((Closeable) c0623f2, "Failed to close fatal exception file output stream.");
                        } catch (Throwable th2) {
                            th = th2;
                            C0681ab.m211a((Flushable) null, "Failed to flush to session begin file.");
                            C0681ab.m210a((Closeable) null, "Failed to close fatal exception file output stream.");
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        c0623f = null;
                    } catch (Throwable th3) {
                        th = th3;
                        C0681ab.m211a((Flushable) null, "Failed to flush to session begin file.");
                        C0681ab.m210a((Closeable) null, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                    c0744v.m424m();
                    c0744v.m423l();
                    C0619b.m60a(c0744v.f503k, f493a, 4, f495c);
                    if (Crashlytics.getInstance().m29j()) {
                        return;
                    }
                    c0744v.m427p();
                }

                
        static  boolean m407a(C0744v c0744v, boolean z) {
            c0744v.f514v = z;
                    return z;
                }

                
        static  File[] m408a(C0744v c0744v, FilenameFilter filenameFilter) {
            return c0744v.m409a(filenameFilter);
                }

                
        static  void m413b(C0744v c0744v) throws Exception {
            c0744v.m424m();
                }

                
        static  void m414b(C0744v c0744v, Date date, Thread thread, Throwable th) {
                    C0623f c0623f;
                    C0625h c0625hM70a = null;
            String strM425n = c0744v.m425n();
                    if (strM425n == null) {
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Tried to write a non-fatal exception while no session was open.", (Throwable) null);
                        return;
                    }
                    Crashlytics.m10a(strM425n);
                    try {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics is logging non-fatal exception \"" + th + "\" from thread " + thread.getName());
                        C0623f c0623f2 = new C0623f(c0744v.f503k, strM425n + "SessionEvent" + C0681ab.m201a(c0744v.f499g.getAndIncrement()));
                        try {
                            c0625hM70a = C0625h.m70a(c0623f2);
                            c0744v.m406a(date, c0625hM70a, thread, th, "error", false);
                            C0681ab.m211a(c0625hM70a, "Failed to flush to non-fatal file.");
                            C0681ab.m210a((Closeable) c0623f2, "Failed to close non-fatal file output stream.");
                        } catch (Exception e) {
                            e = e;
                            c0623f = c0623f2;
                            try {
                                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An error occurred in the non-fatal exception logger", e);
                                c0744v.m404a(e, c0623f);
                                C0681ab.m211a(c0625hM70a, "Failed to flush to non-fatal file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close non-fatal file output stream.");
                            } catch (Throwable th2) {
                                th = th2;
                                C0681ab.m211a(c0625hM70a, "Failed to flush to non-fatal file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close non-fatal file output stream.");
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            c0623f = c0623f2;
                            C0681ab.m211a(c0625hM70a, "Failed to flush to non-fatal file.");
                            C0681ab.m210a((Closeable) c0623f, "Failed to close non-fatal file output stream.");
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        c0623f = null;
                    } catch (Throwable th4) {
                        th = th4;
                        c0623f = null;
                    }
                    try {
                        c0744v.m403a(strM425n, 64);
                    } catch (Exception e3) {
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An error occurred when trimming non-fatal files.", e3);
                    }
                }

                
        static  void m415c(C0744v c0744v) throws Exception {
            c0744v.m423l();
                }

                
        static  C0696aq m417d(C0744v c0744v) {
            return c0744v.f513u;
                }

                
        static  boolean m418e(C0744v c0744v) {
            return c0744v.m422k();
                }

                
        static  File m419f(C0744v c0744v) {
            return c0744v.f504l;
                }

                
        static  Pattern m420i() {
            return f496d;
                }

                
        static  Map m421j() {
            return f497e;
                }

                static {
           new C0597K();
           f496d = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
           f497e = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
           f498f = C0621d.m62a("0");
                }

       public C0744v(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, CrashlyticsListener crashlyticsListener, String str) {
           this(uncaughtExceptionHandler, crashlyticsListener, C0687ah.m236a("Crashlytics Exception Handler"), str);
                }

       private C0744v(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, CrashlyticsListener crashlyticsListener, ExecutorService executorService, String str) {
           this.f499g = new AtomicInteger(0);
           this.f500h = new AtomicBoolean(false);
           this.f502j = uncaughtExceptionHandler;
           this.f511s = executorService;
           this.f505m = new AtomicBoolean(false);
           this.f503k = C0727v.m357a().m370h();
           this.f504l = new File(this.f503k, "initialization_marker");
                    this.f506n = String.format(Locale.US, "Crashlytics Android SDK/%s", Crashlytics.getInstance().getVersion());
           this.f501i = 8;
           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Checking for previous crash marker.");
                    File file = new File(C0727v.m357a().m370h(), "crash_marker");
                    if (file.exists()) {
                        file.delete();
                        if (crashlyticsListener != null) {
                            try {
                                crashlyticsListener.crashlyticsDidDetectCrashDuringPreviousExecution();
                            } catch (Exception e) {
                                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
                            }
                        }
                    }
           this.f509q = C0621d.m62a(Crashlytics.m18d());
           this.f510r = str == null ? null : C0621d.m62a(str.replace("-", ""));
           this.f508p = new C0598L(this);
           IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
           this.f507o = new C0599M(this);
           IntentFilter intentFilter2 = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
           Crashlytics.getInstance().getContext().registerReceiver(this.f508p, intentFilter);
           Crashlytics.getInstance().getContext().registerReceiver(this.f507o, intentFilter2);
           this.f500h.set(true);
                }

                
       final boolean m434a() {
           return this.f505m.get();
                }

                @Override 
       public final synchronized void uncaughtException(Thread thread, Throwable ex) {
                    try {
                        this.f505m.set(true);
                        try {
                            try {
                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics is handling uncaught exception \"" + ex + "\" from thread " + thread.getName());
                       if (!this.f500h.getAndSet(true)) {
                           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unregistering power receivers.");
                           Crashlytics.getInstance().getContext().unregisterReceiver(this.f508p);
                           Crashlytics.getInstance().getContext().unregisterReceiver(this.f507o);
                                }
                       m387a(new CallableC0600N(this, new Date(), thread, ex));
                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
                       this.f502j.uncaughtException(thread, ex);
                       this.f505m.set(false);
                            } catch (Exception e) {
                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An error occurred in the uncaught exception handler", e);
                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
                       this.f502j.uncaughtException(thread, ex);
                       this.f505m.set(false);
                            }
                        } catch (Throwable th) {
                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
                   this.f502j.uncaughtException(thread, ex);
                   this.f505m.set(false);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                
       final boolean m435b() {
           return ((Boolean) m387a(new CallableC0601O(this))).booleanValue();
                }

                
       final void m432a(Thread thread, Throwable th) {
           m389a(new RunnableC0602P(this, new Date(), thread, th));
                }

                
       final void m431a(long j, String str) {
           m412b(new CallableC0746x(this, j, str));
                }

                
       private C0621d m386a(C0696aq c0696aq) {
           if (c0696aq == null) {
               return null;
                    }
           int[] iArr = {0};
           byte[] bArr = new byte[c0696aq.m284a()];
                    try {
               c0696aq.m285a(new C0747y(this, bArr, iArr));
                    } catch (IOException e) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "A problem occurred while reading the Crashlytics log file.", e);
                    }
           return C0621d.m63a(bArr, 0, iArr[0]);
                }

                
       final void m436c() {
           m412b(new CallableC0748z(this));
                }

                
       final void m437d() {
           m412b(new CallableC0587A(this));
                }

                
       final void m438e() {
           m412b(new CallableC0588B(this));
                }

                
       final boolean m439f() {
           return ((Boolean) m387a(new CallableC0589C(this))).booleanValue();
                }

                
       static void m400a(C0696aq c0696aq, int i, long j, String str) {
           if (c0696aq != null) {
               String str2 = str == null ? "null" : str;
                        try {
                   if (str2.length() > 16384) {
                       str2 = "..." + str2.substring(str2.length() - 16384);
                            }
                   c0696aq.m286a(String.format(Locale.US, "%d %s%n", Long.valueOf(j), str2.replaceAll("\r", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).replaceAll(IOUtils.LINE_SEPARATOR_UNIX, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)).getBytes(IoUtils.UTF_8));
                   while (!c0696aq.m288b() && c0696aq.m284a() > 65536) {
                       c0696aq.m289c();
                            }
                        } catch (IOException e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "There was a problem writing to the Crashlytics log.", e);
                        }
                    }
                }

                
       final boolean m440g() {
                    return m426o().length > 0;
                }

                
       private boolean m422k() {
                    File file;
           if (!C0681ab.m213a(Crashlytics.getInstance().getContext(), "com.crashlytics.CollectCustomLogs", true)) {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Preferences requested not to collect custom logs. Aborting log file creation.");
                        return false;
                    }
           C0681ab.m210a(this.f513u, "Could not close log file: " + this.f513u);
                    try {
               file = new File(C0727v.m357a().m370h(), "crashlytics-userlog-" + UUID.randomUUID().toString() + ".temp");
                        try {
                   this.f513u = new C0696aq(file);
                   file.delete();
                            return true;
                        } catch (Exception e) {
                   e = e;
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create log file: " + file, e);
                            return false;
                        }
                    } catch (Exception e2) {
               e = e2;
               file = null;
                    }
                }

                
                
                
                
                
                
                
                
                
                
                
                
                
       private void m423l() throws Exception {
                    C0623f c0623f = null;
                    C0625h c0625hM70a = null;
                    C0625h M70a = null;
                    C0623f c0623f2 = null;
                    C0623f c0623f3 = null;
                    C0623f c0623f4 = null;
                    C0625h c0625h = null;
                    C0625h r2 = null;
                    C0623f c0623f5 = null;
           Date date = new Date();
           String string = new C0622e(Crashlytics.getInstance().m27b()).toString();
           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Opening an new session with ID " + string);
                    try {
               c0623f = new C0623f(C0727v.m357a().m370h(), string + "BeginSession");
                        try {
                            c0625hM70a = C0625h.m70a(c0623f);
                            try {
                                c0625hM70a.m86a(1, C0621d.m62a(this.f506n));
                                c0625hM70a.m86a(2, C0621d.m62a(string));
                                c0625hM70a.m85a(3, date.getTime() / 1000);
                                C0681ab.m211a(c0625hM70a, "Failed to flush to session begin file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close begin session file.");
                                try {
                           c0623f2 = new C0623f(C0727v.m357a().m370h(), string + "SessionApp");
                                    try {
                                        M70a = C0625h.m70a(c0623f2);
                                        try {
                                            C0621d c0621dM62a = C0621d.m62a(Crashlytics.m18d());
                                            C0621d c0621dM62a2 = C0621d.m62a(Crashlytics.m21g());
                                            C0621d c0621dM62a3 = C0621d.m62a(Crashlytics.m20f());
                                            C0621d.m62a(Crashlytics.m22h());
                                            C0621d.m62a(Crashlytics.getInstance().getContext().getPackageCodePath());
                                            C0621d c0621dM62a4 = C0621d.m62a(Crashlytics.getInstance().m27b().m259b());
                                            int iM242a = EnumC0688ai.m241a(Crashlytics.m19e()).m242a();
                                            M70a.m93g(7, 2);
                                            int iM75b = C0625h.m75b(1, c0621dM62a) + 0 + C0625h.m75b(2, c0621dM62a2) + C0625h.m75b(3, c0621dM62a3);
                                            int iM428q = m428q();
                                            M70a.m90b(iM75b + iM428q + C0625h.m69a(5) + C0625h.m77c(iM428q) + C0625h.m75b(6, c0621dM62a4) + C0625h.m81e(10, iM242a));
                                            M70a.m86a(1, c0621dM62a);
                                            M70a.m86a(2, c0621dM62a2);
                                            M70a.m86a(3, c0621dM62a3);
                                            M70a.m93g(5, 2);
                                            M70a.m90b(m428q());
                                            M70a.m87a(1, C0723r.m354a(Crashlytics.getInstance().getContext(), false));
                                            M70a.m86a(6, c0621dM62a4);
                                            M70a.m91b(10, iM242a);
                                            C0681ab.m211a((Flushable) M70a, "Failed to flush to session app file.");
                                            C0681ab.m210a((Closeable) c0623f2, "Failed to close session app file.");
                                            try {
                                                try {
                                           c0623f4 = new C0623f(C0727v.m357a().m370h(), string + "SessionOS");
                                                    try {
                                                        C0625h c0625hM70a2 = C0625h.m70a(c0623f4);
                                                        C0621d c0621dM62a5 = C0621d.m62a(Build.VERSION.RELEASE);
                                                        C0621d c0621dM62a6 = C0621d.m62a(Build.VERSION.CODENAME);
                                                        boolean zM226e = C0681ab.m226e();
                                                        c0625hM70a2.m93g(8, 2);
                                                        c0625hM70a2.m90b(C0625h.m81e(1, 3) + 0 + C0625h.m75b(2, c0621dM62a5) + C0625h.m75b(3, c0621dM62a6) + C0625h.m76b(4, zM226e));
                                                        c0625hM70a2.m91b(1, 3);
                                                        c0625hM70a2.m86a(2, c0621dM62a5);
                                                        c0625hM70a2.m86a(3, c0621dM62a6);
                                                        c0625hM70a2.m88a(4, zM226e);
                                                        C0681ab.m211a(c0625hM70a2, "Failed to flush to session OS file.");
                                                        C0681ab.m210a((Closeable) c0623f4, "Failed to close session OS file.");
                                               m416c(string);
                                                    } catch (Exception e) {
                                                        e = e;
                                                        m404a(e, c0623f4);
                                                        throw e;
                                                    }
                                                } catch (Throwable th) {
                                                    th = th;
                                                    C0681ab.m211a((Flushable) null, "Failed to flush to session OS file.");
                                                    C0681ab.m210a((Closeable) M70a, "Failed to close session OS file.");
                                                    throw th;
                                                }
                                            } catch (Exception e2) {
                                       e = e2;
                                                c0623f4 = null;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                M70a = null;
                                                C0681ab.m211a((Flushable) null, "Failed to flush to session OS file.");
                                                C0681ab.m210a((Closeable) M70a, "Failed to close session OS file.");
                                                throw th;
                                            }
                                        } catch (Exception e3) {
                                            e = e3;
                                            r2 = M70a;
                                            c0623f3 = c0623f2;
                                            try {
                                                m404a(e, c0623f3);
                                                throw e;
                                            } catch (Throwable th3) {
                                                th = th3;
                                                c0623f2 = c0623f3;
                                                M70a = r2;
                                                C0681ab.m211a((Flushable) M70a, "Failed to flush to session app file.");
                                                C0681ab.m210a((Closeable) c0623f2, "Failed to close session app file.");
                                                throw th;
                                            }
                                        } catch (Throwable th4) {
                                            th = th4;
                                            C0681ab.m211a((Flushable) M70a, "Failed to flush to session app file.");
                                            C0681ab.m210a((Closeable) c0623f2, "Failed to close session app file.");
                                            throw th;
                                        }
                                    } catch (Exception e4) {
                                        e = e4;
                                        c0623f3 = c0623f2;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        M70a = null;
                                    }
                                } catch (Exception e5) {
                           e = e5;
                                    c0623f3 = null;
                                } catch (Throwable th6) {
                           th = th6;
                                    M70a = null;
                                    c0623f2 = null;
                                }
                            } catch (Exception e6) {
                                e = e6;
                                c0623f5 = c0623f;
                                try {
                                    m404a(e, c0623f5);
                                    throw e;
                                } catch (Throwable th7) {
                                    th = th7;
                                    c0623f = c0623f5;
                                    c0625h = c0625hM70a;
                                    C0681ab.m211a(c0625h, "Failed to flush to session begin file.");
                                    C0681ab.m210a((Closeable) c0623f, "Failed to close begin session file.");
                                    throw th;
                                }
                            } catch (Throwable th8) {
                                th = th8;
                                c0625h = c0625hM70a;
                                C0681ab.m211a(c0625h, "Failed to flush to session begin file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close begin session file.");
                                throw th;
                            }
                        } catch (Exception e7) {
                            e = e7;
                            c0625hM70a = null;
                            c0623f5 = c0623f;
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Exception e8) {
               e = e8;
                        c0625hM70a = null;
                    } catch (Throwable th10) {
               th = th10;
                        c0623f = null;
                    }
                }

                
       private void m424m() throws Exception {
                    OutputStream c0623f;
                    C0623f c0623f2;
                    C0625h c0625h;
                    C0623f c0623f3;
                    File[] fileArrM409a;
           HashSet hashSet = new HashSet();
                    File[] fileArrM426o = m426o();
                    Arrays.sort(fileArrM426o, f494b);
                    int iMin = Math.min(8, fileArrM426o.length);
                    for (int i = 0; i < iMin; i++) {
                        hashSet.add(m388a(fileArrM426o[i]));
                    }
                    for (File file : m409a(new C0603Q((byte) 0))) {
                        String name = file.getName();
                        Matcher matcher = f496d.matcher(name);
                        matcher.matches();
                        if (!hashSet.contains(matcher.group(1))) {
                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Trimming open session file: " + name);
                            file.delete();
                        }
                    }
           String strM425n = m425n();
           if (strM425n != null) {
                        C0625h c0625hM70a = null;
                        try {
                            c0623f = new C0623f(this.f503k, strM425n + "SessionUser");
                            try {
                                try {
                                    c0625hM70a = C0625h.m70a(c0623f);
                                    String strM31m = Crashlytics.getInstance().m31m();
                                    String strM33o = Crashlytics.getInstance().m33o();
                                    String strM32n = Crashlytics.getInstance().m32n();
                                    if (strM31m == null && strM33o == null && strM32n == null) {
                                        C0681ab.m211a(c0625hM70a, "Failed to flush session user file.");
                                        C0681ab.m210a((Closeable) c0623f, "Failed to close session user file.");
                                    } else {
                               if (strM31m == null) {
                                            strM31m = "";
                                        }
                                        C0621d c0621dM62a = C0621d.m62a(strM31m);
                                        C0621d c0621dM62a2 = strM33o == null ? null : C0621d.m62a(strM33o);
                                        C0621d c0621dM62a3 = strM32n == null ? null : C0621d.m62a(strM32n);
                                        int iM75b = C0625h.m75b(1, c0621dM62a) + 0;
                                        if (c0621dM62a2 != null) {
                                            iM75b += C0625h.m75b(2, c0621dM62a2);
                                        }
                                        if (c0621dM62a3 != null) {
                                            iM75b += C0625h.m75b(3, c0621dM62a3);
                                        }
                                        c0625hM70a.m93g(6, 2);
                                        c0625hM70a.m90b(iM75b);
                                        c0625hM70a.m86a(1, c0621dM62a);
                                        if (c0621dM62a2 != null) {
                                            c0625hM70a.m86a(2, c0621dM62a2);
                                        }
                                        if (c0621dM62a3 != null) {
                                            c0625hM70a.m86a(3, c0621dM62a3);
                                        }
                                        C0681ab.m211a(c0625hM70a, "Failed to flush session user file.");
                                        C0681ab.m210a((Closeable) c0623f, "Failed to close session user file.");
                                    }
                           C0671aR c0671aRM36r = Crashlytics.getInstance().m36r();
                           if (c0671aRM36r != null) {
                               int i2 = c0671aRM36r.f322a;
                               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Closing all open sessions.");
                               File[] fileArrM426o2 = m426o();
                               if (fileArrM426o2 != null && fileArrM426o2.length > 0) {
                                            for (File file2 : fileArrM426o2) {
                                       String strM388a = m388a(file2);
                                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Closing session: " + strM388a);
                                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Collecting session parts for ID " + strM388a);
                                                File[] fileArrM409a2 = m409a(new C0604R(strM388a + "SessionCrash"));
                                       boolean z = fileArrM409a2 != null && fileArrM409a2.length > 0;
                                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Session %s has fatal exception: %s", strM388a, Boolean.valueOf(z)));
                                                File[] fileArrM409a3 = m409a(new C0604R(strM388a + "SessionEvent"));
                                                boolean z2 = fileArrM409a3 != null && fileArrM409a3.length > 0;
                                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Session %s has non-fatal exceptions: %s", strM388a, Boolean.valueOf(z2)));
                                                if (z || z2) {
                                                    C0625h c0625h2 = null;
                                                    try {
                                                        c0623f2 = new C0623f(this.f503k, strM388a);
                                                        try {
                                                            try {
                                                                C0625h c0625hM70a2 = C0625h.m70a(c0623f2);
                                                                try {
                                                                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Collecting SessionStart data for session ID " + strM388a);
                                                                    m393a(c0625hM70a2, file2);
                                                                    c0625hM70a2.m85a(4, new Date().getTime() / 1000);
                                                                    c0625hM70a2.m88a(5, z);
                                                                    m394a(c0625hM70a2, strM388a);
                                                                    if (z2) {
                                                                        if (fileArrM409a3.length > i2) {
                                                                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Trimming down to %d logged exceptions.", Integer.valueOf(i2)));
                                                                            m403a(strM388a, i2);
                                                                            fileArrM409a = m409a(new C0604R(strM388a + "SessionEvent"));
                                                                        } else {
                                                                            fileArrM409a = fileArrM409a3;
                                                                        }
                                                                        m399a(c0625hM70a2, fileArrM409a, strM388a);
                                                                    }
                                                                    if (z) {
                                                                        m393a(c0625hM70a2, fileArrM409a2[0]);
                                                                    }
                                                                    c0625hM70a2.m84a(11, 1);
                                                                    c0625hM70a2.m91b(12, 3);
                                                                    C0681ab.m211a(c0625hM70a2, "Error flushing session file stream");
                                                                    C0681ab.m210a((Closeable) c0623f2, "Failed to close CLS file");
                                                                } catch (Exception e) {
                                                                    e = e;
                                                                    c0625h = c0625hM70a2;
                                                                    c0623f3 = c0623f2;
                                                                    try {
                                                                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to write session file for session ID: " + strM388a, e);
                                                                        m404a(e, c0623f3);
                                                                        C0681ab.m211a(c0625h, "Error flushing session file stream");
                                                                        m391a(c0623f3);
                                                                    } catch (Throwable th) {
                                                                        th = th;
                                                                        c0623f2 = c0623f3;
                                                                        c0625h2 = c0625h;
                                                                        C0681ab.m211a(c0625h2, "Error flushing session file stream");
                                                                        C0681ab.m210a((Closeable) c0623f2, "Failed to close CLS file");
                                                                        throw th;
                                                                    }
                                                                }
                                                            } catch (Throwable th2) {
                                                       th = th2;
                                                                C0681ab.m211a(c0625h2, "Error flushing session file stream");
                                                                C0681ab.m210a((Closeable) c0623f2, "Failed to close CLS file");
                                                                throw th;
                                                            }
                                                        } catch (Exception e2) {
                                                            e = e2;
                                                            c0625h = null;
                                                            c0623f3 = c0623f2;
                                                        }
                                                    } catch (Exception e3) {
                                                        e = e3;
                                                        c0625h = null;
                                                        c0623f3 = null;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                        c0623f2 = null;
                                                    }
                                                } else {
                                                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No events present for session ID " + strM388a);
                                                }
                                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Removing session part files for ID " + strM388a);
                                                m402a(strM388a);
                                            }
                                   return;
                                        }
                               return;
                                    }
                           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No session begin files found.");
                                } catch (Exception e4) {
                                    e = e4;
                                    m404a(e, c0623f);
                                    throw e;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                C0681ab.m211a(c0625hM70a, "Failed to flush session user file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close session user file.");
                                throw th;
                            }
                        } catch (Exception e5) {
                            e = e5;
                   c0623f = null;
                        } catch (Throwable th5) {
                   th = th5;
                   c0623f = null;
                            C0681ab.m211a(c0625hM70a, "Failed to flush session user file.");
                            C0681ab.m210a((Closeable) c0623f, "Failed to close session user file.");
                            throw th;
                        }
                    } else {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unable to close session. Settings are not loaded.");
                    }
                }

                
       private String m425n() {
           File[] fileArrM409a = m409a(new C0604R("BeginSession"));
           Arrays.sort(fileArrM409a, f494b);
           if (fileArrM409a.length > 0) {
                        return m388a(fileArrM409a[0]);
                    }
                    return null;
                }

                
       private static String m388a(File file) {
           return file.getName().substring(0, 35);
                }

                
       private static void m391a(C0623f c0623f) {
           if (c0623f != null) {
                        try {
                   c0623f.m68a();
                        } catch (IOException e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error closing session file stream in the presence of an exception", e);
                        }
                    }
                }

                
       private void m399a(C0625h c0625h, File[] fileArr, String str) throws Throwable {
           Arrays.sort(fileArr, C0681ab.f349a);
                    for (File file : fileArr) {
                        try {
                   C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", str, file.getName()));
                   m393a(c0625h, file);
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error writting non-fatal to session.", e);
                        }
                    }
                }

                
       private void m394a(C0625h c0625h, String str) throws Throwable {
                    for (String str2 : new String[]{"SessionUser", "SessionApp", "SessionOS", "SessionDevice"}) {
               File[] fileArrM409a = m409a(new C0604R(str + str2));
               if (fileArrM409a.length == 0) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Can't find " + str2 + " data for session ID " + str, (Throwable) null);
                        } else {
                   C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Collecting " + str2 + " data for session ID " + str);
                   m393a(c0625h, fileArrM409a[0]);
                        }
                    }
                }

                
       private void m402a(String str) {
                    for (File file : m409a(new C0605S(str))) {
               file.delete();
                    }
                }

                
       private File[] m426o() {
           return m409a(new C0604R("BeginSession"));
                }

                
       private File[] m409a(FilenameFilter filenameFilter) {
           File[] fileArrListFiles = this.f503k.listFiles(filenameFilter);
                    return fileArrListFiles == null ? new File[0] : fileArrListFiles;
                }

                
      private void m427p() {
                    for (File file : m409a(f493a)) {
              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Attempting to send crash report at time of crash...");
              new Thread(new C0590D(this, file), "Crashlytics Report Uploader").start();
                    }
                }

                
      private void m404a(Throwable th, OutputStream outputStream) throws Throwable {
                    PrintWriter printWriter;
          if (outputStream != null) {
                        try {
                            try {
                                printWriter = new PrintWriter(outputStream);
                                try {
                                    m405a(th, printWriter);
                                    C0681ab.m210a((Closeable) printWriter, "Failed to close stack trace writer.");
                                } catch (Exception e) {
                                    e = e;
                                    C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to create PrintWriter", e);
                                    C0681ab.m210a((Closeable) printWriter, "Failed to close stack trace writer.");
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                C0681ab.m210a((Closeable) printWriter, "Failed to close stack trace writer.");
                                throw th;
                            }
                        } catch (Exception e2) {
                  e = e2;
                  printWriter = null;
                        } catch (Throwable th3) {
                            th = th3;
                  printWriter = null;
                            C0681ab.m210a((Closeable) printWriter, "Failed to close stack trace writer.");
                            throw th;
                        }
                    }
                }

                
      private static void m405a(Throwable th, Writer writer) {
          boolean z = true;
          while (th != null) {
                        try {
                  String localizedMessage = th.getLocalizedMessage();
                  String strReplaceAll = localizedMessage == null ? null : localizedMessage.replaceAll("(\r\n|\n|\f)", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                  writer.write((z ? "" : "Caused by: ") + th.getClass().getName() + ": " + (strReplaceAll != null ? strReplaceAll : "") + IOUtils.LINE_SEPARATOR_UNIX);
                            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                      writer.write("\tat " + stackTraceElement.toString() + IOUtils.LINE_SEPARATOR_UNIX);
                            }
                  th = th.getCause();
                            z = false;
                        } catch (Exception e) {
                  C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write stack trace", e);
                  return;
                        }
                    }
                }

                
      private static int m428q() {
          return C0625h.m75b(1, C0621d.m62a(C0723r.m354a(Crashlytics.getInstance().getContext(), C0727v.m357a().m368f()))) + 0;
                }

                
      private static C0621d m411b(String str) {
          if (str == null) {
                        return null;
                    }
                    return C0621d.m62a(str);
                }

                
      private void m416c(String str) throws Exception {
                    C0625h c0625hM70a;
                    C0623f c0623f;
          C0623f c0623f2 = null;
          C0625h c0625h = null;
                    try {
              c0623f = new C0623f(C0727v.m357a().m370h(), str + "SessionDevice");
                        try {
                  c0625hM70a = C0625h.m70a(c0623f);
                            try {
                      StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                      int iM215b = C0681ab.m215b();
                      C0621d c0621dM411b = m411b(Build.MODEL);
                      C0621d c0621dM411b2 = m411b(Build.MANUFACTURER);
                      C0621d c0621dM411b3 = m411b(Build.PRODUCT);
                      int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
                      long jM220c = C0681ab.m220c();
                      long blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
                      boolean zM224d = C0681ab.m224d();
                      C0694ao c0694aoM27b = Crashlytics.getInstance().m27b();
                      C0621d c0621dM62a = C0621d.m62a(c0694aoM27b.m262e());
                      Map<EnumC0695ap, String> mapM263f = c0694aoM27b.m263f();
                      int iM229f = C0681ab.m229f();
                      c0625hM70a.m93g(9, 2);
                      c0625hM70a.m90b(m379a(iM215b, c0621dM62a, c0621dM411b, iAvailableProcessors, jM220c, blockCount, zM224d, mapM263f, iM229f, c0621dM411b2, c0621dM411b3));
                      c0625hM70a.m86a(1, c0621dM62a);
                      c0625hM70a.m91b(3, iM215b);
                      c0625hM70a.m86a(4, c0621dM411b);
                      c0625hM70a.m84a(5, iAvailableProcessors);
                      c0625hM70a.m85a(6, jM220c);
                      c0625hM70a.m85a(7, blockCount);
                      c0625hM70a.m88a(10, zM224d);
                                for (Map.Entry<EnumC0695ap, String> entry : mapM263f.entrySet()) {
                          c0625hM70a.m93g(11, 2);
                          c0625hM70a.m90b(m380a(entry.getKey(), entry.getValue()));
                          c0625hM70a.m91b(1, entry.getKey().f397f);
                          c0625hM70a.m86a(2, C0621d.m62a(entry.getValue()));
                                }
                      c0625hM70a.m84a(12, iM229f);
                      if (c0621dM411b2 != null) {
                          c0625hM70a.m86a(13, c0621dM411b2);
                                }
                      if (c0621dM411b3 != null) {
                          c0625hM70a.m86a(14, c0621dM411b3);
                                }
                      C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
                      C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                            } catch (Exception e) {
                      e = e;
                                c0625h = c0625hM70a;
                                c0623f2 = c0623f;
                                try {
                          m404a(e, c0623f2);
                          throw e;
                                } catch (Throwable th) {
                          th = th;
                                    c0625hM70a = c0625h;
                                    c0623f = c0623f2;
                                    C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
                          C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
                      C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            c0623f2 = c0623f;
                        } catch (Throwable th3) {
                            th = th3;
                  c0625hM70a = null;
                        }
                    } catch (Exception e3) {
              e = e3;
                    } catch (Throwable th4) {
              th = th4;
              c0625hM70a = null;
              c0623f = null;
                    }
                }

                
      private static int m380a(EnumC0695ap enumC0695ap, String str) {
          return C0625h.m81e(1, enumC0695ap.f397f) + C0625h.m75b(2, C0621d.m62a(str));
                }

                
      private int m379a(int i, C0621d c0621d, C0621d c0621d2, int i2, long j, long j2, boolean z, Map<EnumC0695ap, String> map, int i3, C0621d c0621d3, C0621d c0621d4) {
                    int i4;
          int iM75b = (c0621d2 == null ? 0 : C0625h.m75b(4, c0621d2)) + C0625h.m81e(3, i) + C0625h.m75b(1, c0621d) + 0 + C0625h.m78d(5, i2) + C0625h.m74b(6, j) + C0625h.m74b(7, j2) + C0625h.m76b(10, z);
          if (map != null) {
              Iterator<Map.Entry<EnumC0695ap, String>> it = map.entrySet().iterator();
                        while (true) {
                            i4 = iM75b;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<EnumC0695ap, String> next = it.next();
                  int iM380a = m380a(next.getKey(), next.getValue());
                  iM75b = iM380a + C0625h.m69a(11) + C0625h.m77c(iM380a) + i4;
                        }
                    } else {
                        i4 = iM75b;
                    }
                    return (c0621d4 == null ? 0 : C0625h.m75b(14, c0621d4)) + i4 + C0625h.m78d(12, i3) + (c0621d3 == null ? 0 : C0625h.m75b(13, c0621d3));
                }

                
      private static void m393a(C0625h c0625h, File file) throws Throwable {
                    FileInputStream fileInputStream;
                    int i;
          if (file.exists()) {
              byte[] bArr = new byte[(int) file.length()];
                        try {
                  fileInputStream = new FileInputStream(file);
                  int i2 = 0;
                  while (i2 < bArr.length && (i = fileInputStream.read(bArr, i2, bArr.length - i2)) >= 0) {
                                try {
                          i2 += i;
                                } catch (Throwable th) {
                          th = th;
                                    C0681ab.m210a(fileInputStream, "Failed to close file input stream.");
                                    throw th;
                                }
                            }
                  C0681ab.m210a(fileInputStream, "Failed to close file input stream.");
                  c0625h.m89a(bArr);
                        } catch (Throwable th2) {
                  th = th2;
                            fileInputStream = null;
                        }
                    } else {
              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Tried to include a file that doesn't exist: " + file.getName(), (Throwable) null);
                    }
                }

                
      private void m403a(String str, int i) {
          C0619b.m60a(this.f503k, new C0604R(str + "SessionEvent"), i, f495c);
                }

                
      private void m406a(Date date, C0625h c0625h, Thread thread, Throwable th, String str, boolean z) throws Exception {
                    Map<String, String> treeMap;
          long time = date.getTime() / 1000;
          float fM214b = C0681ab.m214b(Crashlytics.getInstance().getContext());
          int iM196a = C0681ab.m196a(this.f514v);
          boolean zM222c = C0681ab.m222c(Crashlytics.getInstance().getContext());
          int i = Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation;
          long jM220c = C0681ab.m220c() - C0681ab.m197a(Crashlytics.getInstance().getContext());
          long jM216b = C0681ab.m216b(Environment.getDataDirectory().getPath());
          this.f512t = C0681ab.m199a(Crashlytics.m18d(), Crashlytics.getInstance().getContext());
          this.f516x = new LinkedList();
          this.f517y = th.getStackTrace();
          if (z) {
              Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
              this.f515w = new Thread[allStackTraces.size()];
              int i2 = 0;
              Iterator<Map.Entry<Thread, StackTraceElement[]>> it = allStackTraces.entrySet().iterator();
                        while (true) {
                            int i3 = i2;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<Thread, StackTraceElement[]> next = it.next();
                  this.f515w[i3] = next.getKey();
                  this.f516x.add(next.getValue());
                  i2 = i3 + 1;
                        }
                    } else {
              this.f515w = new Thread[0];
                    }
          C0621d c0621dM386a = m386a(this.f513u);
          if (c0621dM386a == null) {
              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No log data to include with this event.");
                    }
          C0681ab.m210a(this.f513u, "There was a problem closing the Crashlytics log file.");
          this.f513u = null;
          if (!C0681ab.m213a(Crashlytics.getInstance().getContext(), "com.crashlytics.CollectCustomKeys", true)) {
              treeMap = new TreeMap<>();
                    } else {
              Map<String, String> mapM26a = Crashlytics.getInstance().m26a();
              treeMap = (mapM26a == null || mapM26a.size() <= 1) ? mapM26a : new TreeMap<>(mapM26a);
                    }
          c0625h.m93g(10, 2);
          int iM74b = C0625h.m74b(1, time) + 0 + C0625h.m75b(2, C0621d.m62a(str));
                    int iM383a = m383a(thread, th, treeMap);
                    int iM69a = iM74b + iM383a + C0625h.m69a(3) + C0625h.m77c(iM383a);
                    int iM378a = m378a(fM214b, iM196a, zM222c, i, jM220c, jM216b);
                    int iM69a2 = iM69a + iM378a + C0625h.m69a(5) + C0625h.m77c(iM378a);
                    if (c0621dM386a != null) {
                        int iM75b = C0625h.m75b(1, c0621dM386a);
                        iM69a2 += iM75b + C0625h.m69a(6) + C0625h.m77c(iM75b);
                    }
                    c0625h.m90b(iM69a2);
          c0625h.m85a(1, time);
          c0625h.m86a(2, C0621d.m62a(str));
          c0625h.m93g(3, 2);
                    c0625h.m90b(m383a(thread, th, treeMap));
                    m395a(c0625h, thread, th);
                    if (treeMap != null && !treeMap.isEmpty()) {
                        m398a(c0625h, treeMap);
                    }
                    if (this.f512t != null) {
              c0625h.m88a(3, this.f512t.importance != 100);
                    }
                    c0625h.m84a(4, Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation);
          c0625h.m93g(5, 2);
                    c0625h.m90b(m378a(fM214b, iM196a, zM222c, i, jM220c, jM216b));
                    c0625h.m83a(1, fM214b);
                    c0625h.m92c(2, iM196a);
                    c0625h.m88a(3, zM222c);
                    c0625h.m84a(4, i);
                    c0625h.m85a(5, jM220c);
                    c0625h.m85a(6, jM216b);
          if (c0621dM386a != null) {
                        c0625h.m93g(6, 2);
                        c0625h.m90b(C0625h.m75b(1, c0621dM386a));
                        c0625h.m86a(1, c0621dM386a);
                    }
                }

                
      private void m395a(C0625h c0625h, Thread thread, Throwable th) throws Exception {
          c0625h.m93g(1, 2);
          c0625h.m90b(m410b(thread, th));
          m396a(c0625h, thread, this.f517y, 4, true);
          int length = this.f515w.length;
                    for (int i = 0; i < length; i++) {
              m396a(c0625h, this.f515w[i], this.f516x.get(i), 0, false);
                    }
          m397a(c0625h, th, 1, 2);
          c0625h.m93g(3, 2);
          c0625h.m90b(m430s());
          c0625h.m86a(1, f498f);
          c0625h.m86a(2, f498f);
          c0625h.m85a(3, 0L);
          c0625h.m93g(4, 2);
          c0625h.m90b(m429r());
          c0625h.m85a(1, 0L);
          c0625h.m85a(2, 0L);
          c0625h.m86a(3, this.f509q);
          if (this.f510r != null) {
              c0625h.m86a(4, this.f510r);
                    }
                }

                
      private void m398a(C0625h c0625h, Map<String, String> map) throws Exception {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
              c0625h.m93g(2, 2);
              c0625h.m90b(m382a(entry.getKey(), entry.getValue()));
              c0625h.m86a(1, C0621d.m62a(entry.getKey()));
              String value = entry.getValue();
              if (value == null) {
                            value = "";
                        }
                        c0625h.m86a(2, C0621d.m62a(value));
                    }
                }

                
      private int m429r() {
          int iM74b = C0625h.m74b(1, 0L) + 0 + C0625h.m74b(2, 0L) + C0625h.m75b(3, this.f509q);
          if (this.f510r != null) {
              return iM74b + C0625h.m75b(4, this.f510r);
                    }
          return iM74b;
                }

                
      private void m397a(C0625h c0625h, Throwable th, int i, int i2) throws Exception {
                    int i3 = 0;
          c0625h.m93g(i2, 2);
          c0625h.m90b(m385a(th, 1));
          c0625h.m86a(1, C0621d.m62a(th.getClass().getName()));
          String localizedMessage = th.getLocalizedMessage();
          if (localizedMessage != null) {
              c0625h.m86a(3, C0621d.m62a(localizedMessage));
                    }
                    for (StackTraceElement stackTraceElement : th.getStackTrace()) {
              m392a(c0625h, 4, stackTraceElement, true);
                    }
          Throwable cause = th.getCause();
          if (cause != null) {
              if (i < 8) {
                  m397a(c0625h, cause, i + 1, 6);
                  return;
                        }
              while (cause != null) {
                  cause = cause.getCause();
                  i3++;
                        }
              c0625h.m84a(7, i3);
                    }
                }

                
      private int m384a(Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
          int iM78d = C0625h.m78d(2, i) + C0625h.m75b(1, C0621d.m62a(thread.getName()));
                    for (StackTraceElement stackTraceElement : stackTraceElementArr) {
              int iM381a = m381a(stackTraceElement, z);
              iM78d += iM381a + C0625h.m69a(3) + C0625h.m77c(iM381a);
                    }
          return iM78d;
                }

                
      private void m396a(C0625h c0625h, Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) throws Exception {
          c0625h.m93g(1, 2);
          c0625h.m90b(m384a(thread, stackTraceElementArr, i, z));
          c0625h.m86a(1, C0621d.m62a(thread.getName()));
          c0625h.m84a(2, i);
                    for (StackTraceElement stackTraceElement : stackTraceElementArr) {
              m392a(c0625h, 3, stackTraceElement, z);
                    }
                }

                
      private static int m381a(StackTraceElement stackTraceElement, boolean z) {
                    int iM74b;
          if (stackTraceElement.isNativeMethod()) {
              iM74b = C0625h.m74b(1, Math.max(stackTraceElement.getLineNumber(), 0)) + 0;
                    } else {
              iM74b = C0625h.m74b(1, 0L) + 0;
                    }
          int iM75b = iM74b + C0625h.m75b(2, C0621d.m62a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
          if (stackTraceElement.getFileName() != null) {
              iM75b += C0625h.m75b(3, C0621d.m62a(stackTraceElement.getFileName()));
                    }
          return C0625h.m78d(5, z ? 2 : 0) + ((stackTraceElement.isNativeMethod() || stackTraceElement.getLineNumber() <= 0) ? iM75b : iM75b + C0625h.m74b(4, stackTraceElement.getLineNumber()));
                }

                
      private void m392a(C0625h c0625h, int i, StackTraceElement stackTraceElement, boolean z) throws Exception {
          c0625h.m93g(i, 2);
          c0625h.m90b(m381a(stackTraceElement, z));
          if (stackTraceElement.isNativeMethod()) {
              c0625h.m85a(1, Math.max(stackTraceElement.getLineNumber(), 0));
                    } else {
              c0625h.m85a(1, 0L);
                    }
          c0625h.m86a(2, C0621d.m62a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
          if (stackTraceElement.getFileName() != null) {
              c0625h.m86a(3, C0621d.m62a(stackTraceElement.getFileName()));
                    }
          if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
              c0625h.m85a(4, stackTraceElement.getLineNumber());
                    }
          c0625h.m84a(5, z ? 4 : 0);
                }

                
      private int m383a(Thread thread, Throwable th, Map<String, String> map) {
                    int iM76b;
          int iM410b = m410b(thread, th);
          int iM69a = iM410b + C0625h.m69a(1) + C0625h.m77c(iM410b) + 0;
          if (map != null) {
              Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                        while (true) {
                            iM76b = iM69a;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<String, String> next = it.next();
                  int iM382a = m382a(next.getKey(), next.getValue());
                  iM69a = iM382a + C0625h.m69a(2) + C0625h.m77c(iM382a) + iM76b;
                        }
                    } else {
                        iM76b = iM69a;
                    }
          if (this.f512t != null) {
              iM76b += C0625h.m76b(3, this.f512t.importance != 100);
                    }
          return C0625h.m78d(4, Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation) + iM76b;
                }

                
      private int m410b(Thread thread, Throwable th) {
          int iM384a = m384a(thread, this.f517y, 4, true);
          int iM69a = iM384a + C0625h.m69a(1) + C0625h.m77c(iM384a) + 0;
          int length = this.f515w.length;
                    int iM69a2 = iM69a;
          for (int i = 0; i < length; i++) {
              int iM384a2 = m384a(this.f515w[i], this.f516x.get(i), 0, false);
              iM69a2 += iM384a2 + C0625h.m69a(1) + C0625h.m77c(iM384a2);
                    }
          int iM385a = m385a(th, 1);
          int iM69a3 = iM385a + C0625h.m69a(2) + C0625h.m77c(iM385a) + iM69a2;
          int iM430s = m430s();
          int iM69a4 = iM69a3 + iM430s + C0625h.m69a(3) + C0625h.m77c(iM430s);
          int iM429r = m429r();
          return iM69a4 + iM429r + C0625h.m69a(3) + C0625h.m77c(iM429r);
                }

                
      private static int m382a(String str, String str2) {
          int iM75b = C0625h.m75b(1, C0621d.m62a(str));
                    if (str2 == null) {
                        str2 = "";
                    }
          return iM75b + C0625h.m75b(2, C0621d.m62a(str2));
                }

                
      private static int m378a(float f, int i, boolean z, int i2, long j, long j2) {
          return C0625h.m73b(1, f) + 0 + C0625h.m82f(2, i) + C0625h.m76b(3, z) + C0625h.m78d(4, i2) + C0625h.m74b(5, j) + C0625h.m74b(6, j2);
                }

                
      private int m385a(Throwable th, int i) {
                    int i2 = 0;
          int iM75b = C0625h.m75b(1, C0621d.m62a(th.getClass().getName())) + 0;
          String localizedMessage = th.getLocalizedMessage();
          if (localizedMessage != null) {
              iM75b += C0625h.m75b(3, C0621d.m62a(localizedMessage));
                    }
          StackTraceElement[] stackTrace = th.getStackTrace();
                    int length = stackTrace.length;
                    int i3 = 0;
                    while (i3 < length) {
              int iM381a = m381a(stackTrace[i3], true);
              i3++;
              iM75b = iM381a + C0625h.m69a(4) + C0625h.m77c(iM381a) + iM75b;
                    }
          Throwable cause = th.getCause();
          if (cause != null) {
              if (i < 8) {
                  int iM385a = m385a(cause, i + 1);
                  return iM75b + iM385a + C0625h.m69a(6) + C0625h.m77c(iM385a);
                        }
              while (cause != null) {
                  cause = cause.getCause();
                  i2++;
                        }
              return iM75b + C0625h.m78d(7, i2);
                    }
          return iM75b;
                }

                
      private static int m430s() {
          return C0625h.m75b(1, f498f) + 0 + C0625h.m75b(2, f498f) + C0625h.m74b(3, 0L);
                }

                
      final void m441h() {
          m389a(new RunnableC0591E(this));
                }

                
      final void m433a(File[] fileArr) {
          File file = new File(C0727v.m357a().m370h(), "invalidClsFiles");
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            for (File file2 : file.listFiles()) {
                                file2.delete();
                            }
                        }
                        file.delete();
                    }
                    for (File file3 : fileArr) {
              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Found invalid session part file: " + file3);
              String strM388a = m388a(file3);
              C0592F c0592f = new C0592F(this, strM388a);
              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Deleting all part files for invalid session: " + strM388a);
                        for (File file4 : m409a(c0592f)) {
                  C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Deleting session file: " + file4);
                  file4.delete();
                        }
                    }
                }

                
      private <T> T m387a(Callable<T> callable) {
                    try {
              return this.f511s.submit(callable).get();
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
                        return null;
                    } catch (Exception e2) {
              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e2);
                        return null;
                    }
                }

                
      private Future<?> m389a(Runnable runnable) {
                    try {
              return this.f511s.submit(new RunnableC0593G(this, runnable));
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
              return null;
                    }
                }

                
      private <T> Future<T> m412b(Callable<T> callable) {
                    try {
              return this.f511s.submit(new CallableC0595I(this, callable));
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
              return null;
                    }
                }
            }
