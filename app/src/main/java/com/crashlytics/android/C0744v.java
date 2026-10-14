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
            
            /* JADX INFO: renamed from: com.crashlytics.android.v */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    final class C0744v implements Thread.UncaughtExceptionHandler {

                /* JADX INFO: renamed from: a */
                static final FilenameFilter f493a = new C0745w();

                /* JADX INFO: renamed from: b */
                private static Comparator<File> f494b = new C0594H();

                /* JADX INFO: renamed from: c */
                private static Comparator<File> f495c = new C0596J();

                /* JADX INFO: renamed from: d */
                private static final Pattern f496d;

                /* JADX INFO: renamed from: e */
                private static final Map<String, String> f497e;

                /* JADX INFO: renamed from: f */
                private static final C0621d f498f;

                /* JADX INFO: renamed from: g */
                private final AtomicInteger f499g;

                /* JADX INFO: renamed from: h */
                private final AtomicBoolean f500h;

                /* JADX INFO: renamed from: i */
                private final int f501i;

                /* JADX INFO: renamed from: j */
                private final Thread.UncaughtExceptionHandler f502j;

                /* JADX INFO: renamed from: k */
                private final File f503k;

                /* JADX INFO: renamed from: l */
                private final File f504l;

                /* JADX INFO: renamed from: m */
                private final AtomicBoolean f505m;

                /* JADX INFO: renamed from: n */
                private final String f506n;

                /* JADX INFO: renamed from: o */
                private final BroadcastReceiver f507o;

                /* JADX INFO: renamed from: p */
                private final BroadcastReceiver f508p;

                /* JADX INFO: renamed from: q */
                private final C0621d f509q;

                /* JADX INFO: renamed from: r */
                private final C0621d f510r;

                /* JADX INFO: renamed from: s */
                private final ExecutorService f511s;

                /* JADX INFO: renamed from: t */
                private ActivityManager.RunningAppProcessInfo f512t;

                /* JADX INFO: renamed from: u */
                private C0696aq f513u;

                /* JADX INFO: renamed from: v */
                private boolean f514v;

                /* JADX INFO: renamed from: w */
                private Thread[] f515w;

                /* JADX INFO: renamed from: x */
                private List<StackTraceElement[]> f516x;

                /* JADX INFO: renamed from: y */
                private StackTraceElement[] f517y;

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ AtomicBoolean m390a(C0744v c0744v) {
/* 66 */            return c0744v.f505m;
                }

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ void m401a(C0744v c0744v, Date date, Thread thread, Throwable th) throws Exception {
                    C0623f c0623f;
                    C0623f c0623f2;
                    C0625h c0625hM70a = null;
                    try {
                        try {
/* 66 */                    new File(c0744v.f503k, "crash_marker").createNewFile();
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

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ boolean m407a(C0744v c0744v, boolean z) {
/* 66 */            c0744v.f514v = z;
                    return z;
                }

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ File[] m408a(C0744v c0744v, FilenameFilter filenameFilter) {
/* 66 */            return c0744v.m409a(filenameFilter);
                }

                /* JADX INFO: renamed from: b */
/* 65 */        static /* synthetic */ void m413b(C0744v c0744v) throws Exception {
/* 66 */            c0744v.m424m();
                }

                /* JADX INFO: renamed from: b */
/* 65 */        static /* synthetic */ void m414b(C0744v c0744v, Date date, Thread thread, Throwable th) {
                    C0623f c0623f;
                    C0625h c0625hM70a = null;
/* 66 */            String strM425n = c0744v.m425n();
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

                /* JADX INFO: renamed from: c */
/* 65 */        static /* synthetic */ void m415c(C0744v c0744v) throws Exception {
/* 66 */            c0744v.m423l();
                }

                /* JADX INFO: renamed from: d */
/* 65 */        static /* synthetic */ C0696aq m417d(C0744v c0744v) {
/* 66 */            return c0744v.f513u;
                }

                /* JADX INFO: renamed from: e */
/* 65 */        static /* synthetic */ boolean m418e(C0744v c0744v) {
/* 66 */            return c0744v.m422k();
                }

                /* JADX INFO: renamed from: f */
/* 65 */        static /* synthetic */ File m419f(C0744v c0744v) {
/* 66 */            return c0744v.f504l;
                }

                /* JADX INFO: renamed from: i */
/* 65 */        static /* synthetic */ Pattern m420i() {
/* 66 */            return f496d;
                }

                /* JADX INFO: renamed from: j */
/* 65 */        static /* synthetic */ Map m421j() {
/* 66 */            return f497e;
                }

                static {
/* 149 */           new C0597K();
/* 156 */           f496d = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
/* 159 */           f497e = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
/* 181 */           f498f = C0621d.m62a("0");
                }

/* 235 */       public C0744v(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, CrashlyticsListener crashlyticsListener, String str) {
/* 236 */           this(uncaughtExceptionHandler, crashlyticsListener, C0687ah.m236a("Crashlytics Exception Handler"), str);
                }

/* 243 */       private C0744v(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, CrashlyticsListener crashlyticsListener, ExecutorService executorService, String str) {
/* 192 */           this.f499g = new AtomicInteger(0);
/* 193 */           this.f500h = new AtomicBoolean(false);
/* 245 */           this.f502j = uncaughtExceptionHandler;
/* 246 */           this.f511s = executorService;
/* 247 */           this.f505m = new AtomicBoolean(false);
/* 248 */           this.f503k = C0727v.m357a().m370h();
/* 249 */           this.f504l = new File(this.f503k, "initialization_marker");
                    this.f506n = String.format(Locale.US, "Crashlytics Android SDK/%s", Crashlytics.getInstance().getVersion());
/* 257 */           this.f501i = 8;
/* 259 */           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Checking for previous crash marker.");
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
/* 261 */           this.f509q = C0621d.m62a(Crashlytics.m18d());
/* 262 */           this.f510r = str == null ? null : C0621d.m62a(str.replace("-", ""));
/* 264 */           this.f508p = new C0598L(this);
/* 271 */           IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
/* 273 */           this.f507o = new C0599M(this);
/* 280 */           IntentFilter intentFilter2 = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
/* 282 */           Crashlytics.getInstance().getContext().registerReceiver(this.f508p, intentFilter);
/* 283 */           Crashlytics.getInstance().getContext().registerReceiver(this.f507o, intentFilter2);
/* 284 */           this.f500h.set(true);
                }

                /* JADX INFO: renamed from: a */
/* 287 */       final boolean m434a() {
/* 288 */           return this.f505m.get();
                }

                @Override // java.lang.Thread.UncaughtExceptionHandler
/* 292 */       public final synchronized void uncaughtException(Thread thread, Throwable ex) {
                    try {
                        this.f505m.set(true);
                        try {
                            try {
/* 295 */                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics is handling uncaught exception \"" + ex + "\" from thread " + thread.getName());
/* 297 */                       if (!this.f500h.getAndSet(true)) {
/* 298 */                           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unregistering power receivers.");
/* 299 */                           Crashlytics.getInstance().getContext().unregisterReceiver(this.f508p);
/* 300 */                           Crashlytics.getInstance().getContext().unregisterReceiver(this.f507o);
                                }
/* 307 */                       m387a(new CallableC0600N(this, new Date(), thread, ex));
/* 317 */                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
/* 318 */                       this.f502j.uncaughtException(thread, ex);
/* 319 */                       this.f505m.set(false);
                            } catch (Exception e) {
/* 315 */                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An error occurred in the uncaught exception handler", e);
/* 317 */                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
/* 318 */                       this.f502j.uncaughtException(thread, ex);
/* 319 */                       this.f505m.set(false);
                            }
                        } catch (Throwable th) {
                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
/* 318 */                   this.f502j.uncaughtException(thread, ex);
/* 319 */                   this.f505m.set(false);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 337 */       final boolean m435b() {
/* 338 */           return ((Boolean) m387a(new CallableC0601O(this))).booleanValue();
                }

                /* JADX INFO: renamed from: a */
/* 356 */       final void m432a(Thread thread, Throwable th) {
/* 359 */           m389a(new RunnableC0602P(this, new Date(), thread, th));
                }

                /* JADX INFO: renamed from: a */
/* 372 */       final void m431a(long j, String str) {
/* 373 */           m412b(new CallableC0746x(this, j, str));
                }

                /* JADX INFO: renamed from: a */
/* 401 */       private C0621d m386a(C0696aq c0696aq) {
/* 402 */           if (c0696aq == null) {
/* 403 */               return null;
                    }
/* 409 */           int[] iArr = {0};
/* 411 */           byte[] bArr = new byte[c0696aq.m284a()];
                    try {
/* 414 */               c0696aq.m285a(new C0747y(this, bArr, iArr));
                    } catch (IOException e) {
/* 426 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "A problem occurred while reading the Crashlytics log file.", e);
                    }
/* 429 */           return C0621d.m63a(bArr, 0, iArr[0]);
                }

                /* JADX INFO: renamed from: c */
/* 435 */       final void m436c() {
/* 436 */           m412b(new CallableC0748z(this));
                }

                /* JADX INFO: renamed from: d */
/* 447 */       final void m437d() {
/* 448 */           m412b(new CallableC0587A(this));
                }

                /* JADX INFO: renamed from: e */
/* 463 */       final void m438e() {
/* 464 */           m412b(new CallableC0588B(this));
                }

                /* JADX INFO: renamed from: f */
/* 479 */       final boolean m439f() {
/* 480 */           return ((Boolean) m387a(new CallableC0589C(this))).booleanValue();
                }

                /* JADX INFO: renamed from: a */
/* 531 */       static void m400a(C0696aq c0696aq, int i, long j, String str) {
/* 532 */           if (c0696aq != null) {
/* 536 */               String str2 = str == null ? "null" : str;
                        try {
/* 556 */                   if (str2.length() > 16384) {
/* 559 */                       str2 = "..." + str2.substring(str2.length() - 16384);
                            }
/* 567 */                   c0696aq.m286a(String.format(Locale.US, "%d %s%n", Long.valueOf(j), str2.replaceAll("\r", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).replaceAll(IOUtils.LINE_SEPARATOR_UNIX, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)).getBytes(IoUtils.UTF_8));
/* 570 */                   while (!c0696aq.m288b() && c0696aq.m284a() > 65536) {
/* 571 */                       c0696aq.m289c();
                            }
                        } catch (IOException e) {
/* 574 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "There was a problem writing to the Crashlytics log.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: g */
/* 579 */       final boolean m440g() {
                    return m426o().length > 0;
                }

                /* JADX INFO: renamed from: k */
/* 602 */       private boolean m422k() {
                    File file;
/* 606 */           if (!C0681ab.m213a(Crashlytics.getInstance().getContext(), "com.crashlytics.CollectCustomLogs", true)) {
/* 607 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Preferences requested not to collect custom logs. Aborting log file creation.");
                        return false;
                    }
/* 612 */           C0681ab.m210a(this.f513u, "Could not close log file: " + this.f513u);
                    try {
/* 626 */               file = new File(C0727v.m357a().m370h(), "crashlytics-userlog-" + UUID.randomUUID().toString() + ".temp");
                        try {
/* 627 */                   this.f513u = new C0696aq(file);
/* 628 */                   file.delete();
                            return true;
                        } catch (Exception e) {
/* 629 */                   e = e;
/* 630 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create log file: " + file, e);
                            return false;
                        }
                    } catch (Exception e2) {
/* 629 */               e = e2;
/* 614 */               file = null;
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r1v11 */
                /* JADX WARN: Type inference failed for: r1v13, types: [java.io.Flushable] */
                /* JADX WARN: Type inference failed for: r1v16 */
                /* JADX WARN: Type inference failed for: r1v21 */
                /* JADX WARN: Type inference failed for: r1v23, types: [com.crashlytics.android.h, java.io.Flushable] */
                /* JADX WARN: Type inference failed for: r1v25 */
                /* JADX WARN: Type inference failed for: r1v27, types: [java.io.Closeable] */
                /* JADX WARN: Type inference failed for: r2v14 */
                /* JADX WARN: Type inference failed for: r2v15 */
                /* JADX WARN: Type inference failed for: r2v8 */
                /* JADX WARN: Type inference failed for: r2v9 */
                /* JADX INFO: renamed from: l */
/* 713 */       private void m423l() throws Exception {
                    C0623f c0623f = null;
                    C0625h c0625hM70a = null;
                    C0625h M70a = null;
                    C0623f c0623f2 = null;
                    C0623f c0623f3 = null;
                    C0623f c0623f4 = null;
                    C0625h c0625h = null;
                    C0625h r2 = null;
                    C0623f c0623f5 = null;
/* 714 */           Date date = new Date();
/* 715 */           String string = new C0622e(Crashlytics.getInstance().m27b()).toString();
/* 717 */           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Opening an new session with ID " + string);
                    try {
/* 719 */               c0623f = new C0623f(C0727v.m357a().m370h(), string + "BeginSession");
                        try {
                            c0625hM70a = C0625h.m70a(c0623f);
                            try {
                                c0625hM70a.m86a(1, C0621d.m62a(this.f506n));
                                c0625hM70a.m86a(2, C0621d.m62a(string));
                                c0625hM70a.m85a(3, date.getTime() / 1000);
                                C0681ab.m211a(c0625hM70a, "Failed to flush to session begin file.");
                                C0681ab.m210a((Closeable) c0623f, "Failed to close begin session file.");
                                try {
/* 720 */                           c0623f2 = new C0623f(C0727v.m357a().m370h(), string + "SessionApp");
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
/* 721 */                                           c0623f4 = new C0623f(C0727v.m357a().m370h(), string + "SessionOS");
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
/* 722 */                                               m416c(string);
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
/* 721 */                                       e = e2;
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
/* 720 */                           e = e5;
                                    c0623f3 = null;
                                } catch (Throwable th6) {
/* 720 */                           th = th6;
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
/* 719 */               e = e8;
                        c0625hM70a = null;
                    } catch (Throwable th10) {
/* 719 */               th = th10;
                        c0623f = null;
                    }
                }

                /* JADX INFO: renamed from: m */
/* 728 */       private void m424m() throws Exception {
                    OutputStream c0623f;
                    C0623f c0623f2;
                    C0625h c0625h;
                    C0623f c0623f3;
                    File[] fileArrM409a;
/* 729 */           HashSet hashSet = new HashSet();
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
/* 731 */           String strM425n = m425n();
/* 733 */           if (strM425n != null) {
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
/* 736 */                               if (strM31m == null) {
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
/* 738 */                           C0671aR c0671aRM36r = Crashlytics.getInstance().m36r();
/* 740 */                           if (c0671aRM36r != null) {
/* 741 */                               int i2 = c0671aRM36r.f322a;
/* 743 */                               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Closing all open sessions.");
/* 744 */                               File[] fileArrM426o2 = m426o();
/* 746 */                               if (fileArrM426o2 != null && fileArrM426o2.length > 0) {
                                            for (File file2 : fileArrM426o2) {
/* 748 */                                       String strM388a = m388a(file2);
/* 749 */                                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Closing session: " + strM388a);
/* 750 */                                       C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Collecting session parts for ID " + strM388a);
                                                File[] fileArrM409a2 = m409a(new C0604R(strM388a + "SessionCrash"));
/* 750 */                                       boolean z = fileArrM409a2 != null && fileArrM409a2.length > 0;
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
/* 750 */                                                       th = th2;
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
/* 759 */                                   return;
                                        }
/* 759 */                               return;
                                    }
/* 754 */                           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No session begin files found.");
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
/* 736 */                   c0623f = null;
                        } catch (Throwable th5) {
/* 736 */                   th = th5;
/* 736 */                   c0623f = null;
                            C0681ab.m211a(c0625hM70a, "Failed to flush session user file.");
                            C0681ab.m210a((Closeable) c0623f, "Failed to close session user file.");
                            throw th;
                        }
                    } else {
/* 757 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unable to close session. Settings are not loaded.");
                    }
                }

                /* JADX INFO: renamed from: n */
/* 770 */       private String m425n() {
/* 771 */           File[] fileArrM409a = m409a(new C0604R("BeginSession"));
/* 772 */           Arrays.sort(fileArrM409a, f494b);
/* 773 */           if (fileArrM409a.length > 0) {
                        return m388a(fileArrM409a[0]);
                    }
                    return null;
                }

                /* JADX INFO: renamed from: a */
/* 785 */       private static String m388a(File file) {
/* 786 */           return file.getName().substring(0, 35);
                }

                /* JADX INFO: renamed from: a */
/* 871 */       private static void m391a(C0623f c0623f) {
/* 872 */           if (c0623f != null) {
                        try {
/* 874 */                   c0623f.m68a();
                        } catch (IOException e) {
/* 876 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error closing session file stream in the presence of an exception", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 881 */       private void m399a(C0625h c0625h, File[] fileArr, String str) throws Throwable {
/* 882 */           Arrays.sort(fileArr, C0681ab.f349a);
                    for (File file : fileArr) {
                        try {
/* 886 */                   C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", str, file.getName()));
/* 887 */                   m393a(c0625h, file);
                        } catch (Exception e) {
/* 889 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error writting non-fatal to session.", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 894 */       private void m394a(C0625h c0625h, String str) throws Throwable {
                    for (String str2 : new String[]{"SessionUser", "SessionApp", "SessionOS", "SessionDevice"}) {
/* 898 */               File[] fileArrM409a = m409a(new C0604R(str + str2));
/* 900 */               if (fileArrM409a.length == 0) {
/* 901 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Can't find " + str2 + " data for session ID " + str, (Throwable) null);
                        } else {
/* 903 */                   C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Collecting " + str2 + " data for session ID " + str);
/* 904 */                   m393a(c0625h, fileArrM409a[0]);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 912 */       private void m402a(String str) {
                    for (File file : m409a(new C0605S(str))) {
/* 914 */               file.delete();
                    }
                }

                /* JADX INFO: renamed from: o */
/* 929 */       private File[] m426o() {
/* 930 */           return m409a(new C0604R("BeginSession"));
                }

                /* JADX INFO: renamed from: a */
/* 940 */       private File[] m409a(FilenameFilter filenameFilter) {
/* 941 */           File[] fileArrListFiles = this.f503k.listFiles(filenameFilter);
                    return fileArrListFiles == null ? new File[0] : fileArrListFiles;
                }

                /* JADX INFO: renamed from: p */
/* 1002 */      private void m427p() {
                    for (File file : m409a(f493a)) {
/* 1005 */              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Attempting to send crash report at time of crash...");
/* 1007 */              new Thread(new C0590D(this, file), "Crashlytics Report Uploader").start();
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1088 */      private void m404a(Throwable th, OutputStream outputStream) throws Throwable {
                    PrintWriter printWriter;
/* 1089 */          if (outputStream != null) {
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
/* 1090 */                  e = e2;
/* 1090 */                  printWriter = null;
                        } catch (Throwable th3) {
                            th = th3;
/* 1090 */                  printWriter = null;
                            C0681ab.m210a((Closeable) printWriter, "Failed to close stack trace writer.");
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1121 */      private static void m405a(Throwable th, Writer writer) {
/* 1122 */          boolean z = true;
/* 1124 */          while (th != null) {
                        try {
/* 1125 */                  String localizedMessage = th.getLocalizedMessage();
/* 1125 */                  String strReplaceAll = localizedMessage == null ? null : localizedMessage.replaceAll("(\r\n|\n|\f)", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 1129 */                  writer.write((z ? "" : "Caused by: ") + th.getClass().getName() + ": " + (strReplaceAll != null ? strReplaceAll : "") + IOUtils.LINE_SEPARATOR_UNIX);
                            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
/* 1134 */                      writer.write("\tat " + stackTraceElement.toString() + IOUtils.LINE_SEPARATOR_UNIX);
                            }
/* 1137 */                  th = th.getCause();
                            z = false;
                        } catch (Exception e) {
/* 1140 */                  C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write stack trace", e);
/* 1142 */                  return;
                        }
                    }
                }

                /* JADX INFO: renamed from: q */
/* 1159 */      private static int m428q() {
/* 1163 */          return C0625h.m75b(1, C0621d.m62a(C0723r.m354a(Crashlytics.getInstance().getContext(), C0727v.m357a().m368f()))) + 0;
                }

                /* JADX INFO: renamed from: b */
/* 1209 */      private static C0621d m411b(String str) {
/* 1210 */          if (str == null) {
                        return null;
                    }
                    return C0621d.m62a(str);
                }

                /* JADX INFO: renamed from: c */
/* 1217 */      private void m416c(String str) throws Exception {
                    C0625h c0625hM70a;
                    C0623f c0623f;
/* 1218 */          C0623f c0623f2 = null;
/* 1219 */          C0625h c0625h = null;
                    try {
/* 1221 */              c0623f = new C0623f(C0727v.m357a().m370h(), str + "SessionDevice");
                        try {
/* 1222 */                  c0625hM70a = C0625h.m70a(c0623f);
                            try {
/* 1224 */                      StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
/* 1226 */                      int iM215b = C0681ab.m215b();
/* 1228 */                      C0621d c0621dM411b = m411b(Build.MODEL);
/* 1229 */                      C0621d c0621dM411b2 = m411b(Build.MANUFACTURER);
/* 1230 */                      C0621d c0621dM411b3 = m411b(Build.PRODUCT);
/* 1232 */                      int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
/* 1233 */                      long jM220c = C0681ab.m220c();
/* 1234 */                      long blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
/* 1235 */                      boolean zM224d = C0681ab.m224d();
/* 1237 */                      C0694ao c0694aoM27b = Crashlytics.getInstance().m27b();
/* 1238 */                      C0621d c0621dM62a = C0621d.m62a(c0694aoM27b.m262e());
/* 1240 */                      Map<EnumC0695ap, String> mapM263f = c0694aoM27b.m263f();
/* 1242 */                      int iM229f = C0681ab.m229f();
/* 1244 */                      c0625hM70a.m93g(9, 2);
/* 1246 */                      c0625hM70a.m90b(m379a(iM215b, c0621dM62a, c0621dM411b, iAvailableProcessors, jM220c, blockCount, zM224d, mapM263f, iM229f, c0621dM411b2, c0621dM411b3));
/* 1249 */                      c0625hM70a.m86a(1, c0621dM62a);
/* 1251 */                      c0625hM70a.m91b(3, iM215b);
/* 1252 */                      c0625hM70a.m86a(4, c0621dM411b);
/* 1253 */                      c0625hM70a.m84a(5, iAvailableProcessors);
/* 1254 */                      c0625hM70a.m85a(6, jM220c);
/* 1255 */                      c0625hM70a.m85a(7, blockCount);
/* 1256 */                      c0625hM70a.m88a(10, zM224d);
                                for (Map.Entry<EnumC0695ap, String> entry : mapM263f.entrySet()) {
/* 1259 */                          c0625hM70a.m93g(11, 2);
/* 1260 */                          c0625hM70a.m90b(m380a(entry.getKey(), entry.getValue()));
/* 1262 */                          c0625hM70a.m91b(1, entry.getKey().f397f);
/* 1263 */                          c0625hM70a.m86a(2, C0621d.m62a(entry.getValue()));
                                }
/* 1265 */                      c0625hM70a.m84a(12, iM229f);
/* 1267 */                      if (c0621dM411b2 != null) {
/* 1268 */                          c0625hM70a.m86a(13, c0621dM411b2);
                                }
/* 1270 */                      if (c0621dM411b3 != null) {
/* 1271 */                          c0625hM70a.m86a(14, c0621dM411b3);
                                }
/* 1277 */                      C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
/* 1278 */                      C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                            } catch (Exception e) {
/* 1273 */                      e = e;
                                c0625h = c0625hM70a;
                                c0623f2 = c0623f;
                                try {
/* 1274 */                          m404a(e, c0623f2);
/* 1275 */                          throw e;
                                } catch (Throwable th) {
/* 1277 */                          th = th;
                                    c0625hM70a = c0625h;
                                    c0623f = c0623f2;
                                    C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
/* 1278 */                          C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                C0681ab.m211a(c0625hM70a, "Failed to flush session device info.");
/* 1278 */                      C0681ab.m210a((Closeable) c0623f, "Failed to close session device file.");
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            c0623f2 = c0623f;
                        } catch (Throwable th3) {
                            th = th3;
/* 1219 */                  c0625hM70a = null;
                        }
                    } catch (Exception e3) {
/* 1273 */              e = e3;
                    } catch (Throwable th4) {
/* 1277 */              th = th4;
/* 1219 */              c0625hM70a = null;
/* 1218 */              c0623f = null;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1282 */      private static int m380a(EnumC0695ap enumC0695ap, String str) {
/* 1284 */          return C0625h.m81e(1, enumC0695ap.f397f) + C0625h.m75b(2, C0621d.m62a(str));
                }

                /* JADX INFO: renamed from: a */
/* 1291 */      private int m379a(int i, C0621d c0621d, C0621d c0621d2, int i2, long j, long j2, boolean z, Map<EnumC0695ap, String> map, int i3, C0621d c0621d3, C0621d c0621d4) {
                    int i4;
/* 1300 */          int iM75b = (c0621d2 == null ? 0 : C0625h.m75b(4, c0621d2)) + C0625h.m81e(3, i) + C0625h.m75b(1, c0621d) + 0 + C0625h.m78d(5, i2) + C0625h.m74b(6, j) + C0625h.m74b(7, j2) + C0625h.m76b(10, z);
/* 1301 */          if (map != null) {
/* 1302 */              Iterator<Map.Entry<EnumC0695ap, String>> it = map.entrySet().iterator();
                        while (true) {
                            i4 = iM75b;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<EnumC0695ap, String> next = it.next();
/* 1303 */                  int iM380a = m380a(next.getKey(), next.getValue());
/* 1304 */                  iM75b = iM380a + C0625h.m69a(11) + C0625h.m77c(iM380a) + i4;
                        }
                    } else {
                        i4 = iM75b;
                    }
                    return (c0621d4 == null ? 0 : C0625h.m75b(14, c0621d4)) + i4 + C0625h.m78d(12, i3) + (c0621d3 == null ? 0 : C0625h.m75b(13, c0621d3));
                }

                /* JADX INFO: renamed from: a */
/* 1318 */      private static void m393a(C0625h c0625h, File file) throws Throwable {
                    FileInputStream fileInputStream;
                    int i;
/* 1319 */          if (file.exists()) {
/* 1322 */              byte[] bArr = new byte[(int) file.length()];
                        try {
/* 1327 */                  fileInputStream = new FileInputStream(file);
/* 1329 */                  int i2 = 0;
/* 1332 */                  while (i2 < bArr.length && (i = fileInputStream.read(bArr, i2, bArr.length - i2)) >= 0) {
                                try {
/* 1333 */                          i2 += i;
                                } catch (Throwable th) {
/* 1336 */                          th = th;
                                    C0681ab.m210a(fileInputStream, "Failed to close file input stream.");
                                    throw th;
                                }
                            }
/* 1336 */                  C0681ab.m210a(fileInputStream, "Failed to close file input stream.");
/* 1339 */                  c0625h.m89a(bArr);
                        } catch (Throwable th2) {
/* 1336 */                  th = th2;
                            fileInputStream = null;
                        }
                    } else {
/* 1341 */              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Tried to include a file that doesn't exist: " + file.getName(), (Throwable) null);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1354 */      private void m403a(String str, int i) {
/* 1355 */          C0619b.m60a(this.f503k, new C0604R(str + "SessionEvent"), i, f495c);
                }

                /* JADX INFO: renamed from: a */
/* 1405 */      private void m406a(Date date, C0625h c0625h, Thread thread, Throwable th, String str, boolean z) throws Exception {
                    Map<String, String> treeMap;
/* 1406 */          long time = date.getTime() / 1000;
/* 1407 */          float fM214b = C0681ab.m214b(Crashlytics.getInstance().getContext());
/* 1408 */          int iM196a = C0681ab.m196a(this.f514v);
/* 1409 */          boolean zM222c = C0681ab.m222c(Crashlytics.getInstance().getContext());
/* 1410 */          int i = Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation;
/* 1411 */          long jM220c = C0681ab.m220c() - C0681ab.m197a(Crashlytics.getInstance().getContext());
/* 1412 */          long jM216b = C0681ab.m216b(Environment.getDataDirectory().getPath());
/* 1414 */          this.f512t = C0681ab.m199a(Crashlytics.m18d(), Crashlytics.getInstance().getContext());
/* 1415 */          this.f516x = new LinkedList();
/* 1416 */          this.f517y = th.getStackTrace();
/* 1418 */          if (z) {
/* 1419 */              Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
/* 1420 */              this.f515w = new Thread[allStackTraces.size()];
/* 1421 */              int i2 = 0;
/* 1422 */              Iterator<Map.Entry<Thread, StackTraceElement[]>> it = allStackTraces.entrySet().iterator();
                        while (true) {
                            int i3 = i2;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<Thread, StackTraceElement[]> next = it.next();
/* 1423 */                  this.f515w[i3] = next.getKey();
/* 1424 */                  this.f516x.add(next.getValue());
/* 1425 */                  i2 = i3 + 1;
                        }
                    } else {
/* 1430 */              this.f515w = new Thread[0];
                    }
/* 1433 */          C0621d c0621dM386a = m386a(this.f513u);
/* 1435 */          if (c0621dM386a == null) {
/* 1436 */              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No log data to include with this event.");
                    }
/* 1440 */          C0681ab.m210a(this.f513u, "There was a problem closing the Crashlytics log file.");
/* 1441 */          this.f513u = null;
/* 1444 */          if (!C0681ab.m213a(Crashlytics.getInstance().getContext(), "com.crashlytics.CollectCustomKeys", true)) {
/* 1445 */              treeMap = new TreeMap<>();
                    } else {
/* 1447 */              Map<String, String> mapM26a = Crashlytics.getInstance().m26a();
/* 1452 */              treeMap = (mapM26a == null || mapM26a.size() <= 1) ? mapM26a : new TreeMap<>(mapM26a);
                    }
/* 1456 */          c0625h.m93g(10, 2);
/* 1457 */          int iM74b = C0625h.m74b(1, time) + 0 + C0625h.m75b(2, C0621d.m62a(str));
                    int iM383a = m383a(thread, th, treeMap);
                    int iM69a = iM74b + iM383a + C0625h.m69a(3) + C0625h.m77c(iM383a);
                    int iM378a = m378a(fM214b, iM196a, zM222c, i, jM220c, jM216b);
                    int iM69a2 = iM69a + iM378a + C0625h.m69a(5) + C0625h.m77c(iM378a);
                    if (c0621dM386a != null) {
                        int iM75b = C0625h.m75b(1, c0621dM386a);
                        iM69a2 += iM75b + C0625h.m69a(6) + C0625h.m77c(iM75b);
                    }
                    c0625h.m90b(iM69a2);
/* 1460 */          c0625h.m85a(1, time);
/* 1461 */          c0625h.m86a(2, C0621d.m62a(str));
/* 1463 */          c0625h.m93g(3, 2);
                    c0625h.m90b(m383a(thread, th, treeMap));
                    m395a(c0625h, thread, th);
                    if (treeMap != null && !treeMap.isEmpty()) {
                        m398a(c0625h, treeMap);
                    }
                    if (this.f512t != null) {
/* 1463 */              c0625h.m88a(3, this.f512t.importance != 100);
                    }
                    c0625h.m84a(4, Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation);
/* 1464 */          c0625h.m93g(5, 2);
                    c0625h.m90b(m378a(fM214b, iM196a, zM222c, i, jM220c, jM216b));
                    c0625h.m83a(1, fM214b);
                    c0625h.m92c(2, iM196a);
                    c0625h.m88a(3, zM222c);
                    c0625h.m84a(4, i);
                    c0625h.m85a(5, jM220c);
                    c0625h.m85a(6, jM216b);
/* 1466 */          if (c0621dM386a != null) {
                        c0625h.m93g(6, 2);
                        c0625h.m90b(C0625h.m75b(1, c0621dM386a));
                        c0625h.m86a(1, c0621dM386a);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1490 */      private void m395a(C0625h c0625h, Thread thread, Throwable th) throws Exception {
/* 1491 */          c0625h.m93g(1, 2);
/* 1492 */          c0625h.m90b(m410b(thread, th));
/* 1494 */          m396a(c0625h, thread, this.f517y, 4, true);
/* 1498 */          int length = this.f515w.length;
                    for (int i = 0; i < length; i++) {
/* 1501 */              m396a(c0625h, this.f515w[i], this.f516x.get(i), 0, false);
                    }
/* 1504 */          m397a(c0625h, th, 1, 2);
/* 1506 */          c0625h.m93g(3, 2);
/* 1507 */          c0625h.m90b(m430s());
/* 1508 */          c0625h.m86a(1, f498f);
/* 1509 */          c0625h.m86a(2, f498f);
/* 1510 */          c0625h.m85a(3, 0L);
/* 1512 */          c0625h.m93g(4, 2);
/* 1513 */          c0625h.m90b(m429r());
/* 1514 */          c0625h.m85a(1, 0L);
/* 1515 */          c0625h.m85a(2, 0L);
/* 1516 */          c0625h.m86a(3, this.f509q);
/* 1517 */          if (this.f510r != null) {
/* 1518 */              c0625h.m86a(4, this.f510r);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1524 */      private void m398a(C0625h c0625h, Map<String, String> map) throws Exception {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
/* 1526 */              c0625h.m93g(2, 2);
/* 1527 */              c0625h.m90b(m382a(entry.getKey(), entry.getValue()));
/* 1529 */              c0625h.m86a(1, C0621d.m62a(entry.getKey()));
/* 1530 */              String value = entry.getValue();
/* 1531 */              if (value == null) {
                            value = "";
                        }
                        c0625h.m86a(2, C0621d.m62a(value));
                    }
                }

                /* JADX INFO: renamed from: r */
/* 1535 */      private int m429r() {
/* 1540 */          int iM74b = C0625h.m74b(1, 0L) + 0 + C0625h.m74b(2, 0L) + C0625h.m75b(3, this.f509q);
/* 1541 */          if (this.f510r != null) {
/* 1542 */              return iM74b + C0625h.m75b(4, this.f510r);
                    }
/* 1545 */          return iM74b;
                }

                /* JADX INFO: renamed from: a */
/* 1549 */      private void m397a(C0625h c0625h, Throwable th, int i, int i2) throws Exception {
                    int i3 = 0;
/* 1550 */          c0625h.m93g(i2, 2);
/* 1551 */          c0625h.m90b(m385a(th, 1));
/* 1553 */          c0625h.m86a(1, C0621d.m62a(th.getClass().getName()));
/* 1554 */          String localizedMessage = th.getLocalizedMessage();
/* 1555 */          if (localizedMessage != null) {
/* 1556 */              c0625h.m86a(3, C0621d.m62a(localizedMessage));
                    }
                    for (StackTraceElement stackTraceElement : th.getStackTrace()) {
/* 1560 */              m392a(c0625h, 4, stackTraceElement, true);
                    }
/* 1563 */          Throwable cause = th.getCause();
/* 1564 */          if (cause != null) {
/* 1565 */              if (i < 8) {
/* 1566 */                  m397a(c0625h, cause, i + 1, 6);
/* 1578 */                  return;
                        }
/* 1571 */              while (cause != null) {
/* 1572 */                  cause = cause.getCause();
/* 1573 */                  i3++;
                        }
/* 1575 */              c0625h.m84a(7, i3);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1581 */      private int m384a(Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
/* 1583 */          int iM78d = C0625h.m78d(2, i) + C0625h.m75b(1, C0621d.m62a(thread.getName()));
                    for (StackTraceElement stackTraceElement : stackTraceElementArr) {
/* 1586 */              int iM381a = m381a(stackTraceElement, z);
/* 1587 */              iM78d += iM381a + C0625h.m69a(3) + C0625h.m77c(iM381a);
                    }
/* 1591 */          return iM78d;
                }

                /* JADX INFO: renamed from: a */
/* 1595 */      private void m396a(C0625h c0625h, Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) throws Exception {
/* 1596 */          c0625h.m93g(1, 2);
/* 1598 */          c0625h.m90b(m384a(thread, stackTraceElementArr, i, z));
/* 1599 */          c0625h.m86a(1, C0621d.m62a(thread.getName()));
/* 1600 */          c0625h.m84a(2, i);
                    for (StackTraceElement stackTraceElement : stackTraceElementArr) {
/* 1603 */              m392a(c0625h, 3, stackTraceElement, z);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1607 */      private static int m381a(StackTraceElement stackTraceElement, boolean z) {
                    int iM74b;
/* 1608 */          if (stackTraceElement.isNativeMethod()) {
/* 1612 */              iM74b = C0625h.m74b(1, Math.max(stackTraceElement.getLineNumber(), 0)) + 0;
                    } else {
/* 1614 */              iM74b = C0625h.m74b(1, 0L) + 0;
                    }
/* 1617 */          int iM75b = iM74b + C0625h.m75b(2, C0621d.m62a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
/* 1621 */          if (stackTraceElement.getFileName() != null) {
/* 1622 */              iM75b += C0625h.m75b(3, C0621d.m62a(stackTraceElement.getFileName()));
                    }
/* 1631 */          return C0625h.m78d(5, z ? 2 : 0) + ((stackTraceElement.isNativeMethod() || stackTraceElement.getLineNumber() <= 0) ? iM75b : iM75b + C0625h.m74b(4, stackTraceElement.getLineNumber()));
                }

                /* JADX INFO: renamed from: a */
/* 1637 */      private void m392a(C0625h c0625h, int i, StackTraceElement stackTraceElement, boolean z) throws Exception {
/* 1638 */          c0625h.m93g(i, 2);
/* 1639 */          c0625h.m90b(m381a(stackTraceElement, z));
/* 1641 */          if (stackTraceElement.isNativeMethod()) {
/* 1644 */              c0625h.m85a(1, Math.max(stackTraceElement.getLineNumber(), 0));
                    } else {
/* 1646 */              c0625h.m85a(1, 0L);
                    }
/* 1649 */          c0625h.m86a(2, C0621d.m62a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
/* 1651 */          if (stackTraceElement.getFileName() != null) {
/* 1652 */              c0625h.m86a(3, C0621d.m62a(stackTraceElement.getFileName()));
                    }
/* 1655 */          if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
/* 1659 */              c0625h.m85a(4, stackTraceElement.getLineNumber());
                    }
/* 1666 */          c0625h.m84a(5, z ? 4 : 0);
                }

                /* JADX INFO: renamed from: a */
/* 1713 */      private int m383a(Thread thread, Throwable th, Map<String, String> map) {
                    int iM76b;
/* 1714 */          int iM410b = m410b(thread, th);
/* 1717 */          int iM69a = iM410b + C0625h.m69a(1) + C0625h.m77c(iM410b) + 0;
/* 1720 */          if (map != null) {
/* 1721 */              Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                        while (true) {
                            iM76b = iM69a;
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<String, String> next = it.next();
/* 1722 */                  int iM382a = m382a(next.getKey(), next.getValue());
/* 1723 */                  iM69a = iM382a + C0625h.m69a(2) + C0625h.m77c(iM382a) + iM76b;
                        }
                    } else {
                        iM76b = iM69a;
                    }
/* 1729 */          if (this.f512t != null) {
/* 1730 */              iM76b += C0625h.m76b(3, this.f512t.importance != 100);
                    }
/* 1733 */          return C0625h.m78d(4, Crashlytics.getInstance().getContext().getResources().getConfiguration().orientation) + iM76b;
                }

                /* JADX INFO: renamed from: b */
/* 1739 */      private int m410b(Thread thread, Throwable th) {
/* 1740 */          int iM384a = m384a(thread, this.f517y, 4, true);
/* 1743 */          int iM69a = iM384a + C0625h.m69a(1) + C0625h.m77c(iM384a) + 0;
/* 1745 */          int length = this.f515w.length;
                    int iM69a2 = iM69a;
/* 1746 */          for (int i = 0; i < length; i++) {
/* 1748 */              int iM384a2 = m384a(this.f515w[i], this.f516x.get(i), 0, false);
/* 1749 */              iM69a2 += iM384a2 + C0625h.m69a(1) + C0625h.m77c(iM384a2);
                    }
/* 1753 */          int iM385a = m385a(th, 1);
/* 1754 */          int iM69a3 = iM385a + C0625h.m69a(2) + C0625h.m77c(iM385a) + iM69a2;
/* 1757 */          int iM430s = m430s();
/* 1758 */          int iM69a4 = iM69a3 + iM430s + C0625h.m69a(3) + C0625h.m77c(iM430s);
/* 1760 */          int iM429r = m429r();
/* 1761 */          return iM69a4 + iM429r + C0625h.m69a(3) + C0625h.m77c(iM429r);
                }

                /* JADX INFO: renamed from: a */
/* 1767 */      private static int m382a(String str, String str2) {
/* 1768 */          int iM75b = C0625h.m75b(1, C0621d.m62a(str));
                    if (str2 == null) {
                        str2 = "";
                    }
/* 1769 */          return iM75b + C0625h.m75b(2, C0621d.m62a(str2));
                }

                /* JADX INFO: renamed from: a */
/* 1774 */      private static int m378a(float f, int i, boolean z, int i2, long j, long j2) {
/* 1782 */          return C0625h.m73b(1, f) + 0 + C0625h.m82f(2, i) + C0625h.m76b(3, z) + C0625h.m78d(4, i2) + C0625h.m74b(5, j) + C0625h.m74b(6, j2);
                }

                /* JADX INFO: renamed from: a */
/* 1792 */      private int m385a(Throwable th, int i) {
                    int i2 = 0;
/* 1793 */          int iM75b = C0625h.m75b(1, C0621d.m62a(th.getClass().getName())) + 0;
/* 1798 */          String localizedMessage = th.getLocalizedMessage();
/* 1799 */          if (localizedMessage != null) {
/* 1800 */              iM75b += C0625h.m75b(3, C0621d.m62a(localizedMessage));
                    }
/* 1803 */          StackTraceElement[] stackTrace = th.getStackTrace();
                    int length = stackTrace.length;
                    int i3 = 0;
                    while (i3 < length) {
/* 1804 */              int iM381a = m381a(stackTrace[i3], true);
/* 1803 */              i3++;
/* 1805 */              iM75b = iM381a + C0625h.m69a(4) + C0625h.m77c(iM381a) + iM75b;
                    }
/* 1811 */          Throwable cause = th.getCause();
/* 1812 */          if (cause != null) {
/* 1813 */              if (i < 8) {
/* 1814 */                  int iM385a = m385a(cause, i + 1);
/* 1815 */                  return iM75b + iM385a + C0625h.m69a(6) + C0625h.m77c(iM385a);
                        }
/* 1821 */              while (cause != null) {
/* 1822 */                  cause = cause.getCause();
/* 1823 */                  i2++;
                        }
/* 1826 */              return iM75b + C0625h.m78d(7, i2);
                    }
/* 1830 */          return iM75b;
                }

                /* JADX INFO: renamed from: s */
/* 1833 */      private static int m430s() {
/* 1838 */          return C0625h.m75b(1, f498f) + 0 + C0625h.m75b(2, f498f) + C0625h.m74b(3, 0L);
                }

                /* JADX INFO: renamed from: h */
/* 1860 */      final void m441h() {
/* 1861 */          m389a(new RunnableC0591E(this));
                }

                /* JADX INFO: renamed from: a */
/* 1874 */      final void m433a(File[] fileArr) {
/* 1875 */          File file = new File(C0727v.m357a().m370h(), "invalidClsFiles");
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            for (File file2 : file.listFiles()) {
                                file2.delete();
                            }
                        }
                        file.delete();
                    }
                    for (File file3 : fileArr) {
/* 1881 */              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Found invalid session part file: " + file3);
/* 1882 */              String strM388a = m388a(file3);
/* 1883 */              C0592F c0592f = new C0592F(this, strM388a);
/* 1890 */              C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Deleting all part files for invalid session: " + strM388a);
                        for (File file4 : m409a(c0592f)) {
/* 1892 */                  C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Deleting session file: " + file4);
/* 1893 */                  file4.delete();
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1935 */      private <T> T m387a(Callable<T> callable) {
                    try {
/* 1936 */              return this.f511s.submit(callable).get();
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
                        return null;
                    } catch (Exception e2) {
/* 1941 */              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e2);
                        return null;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 1957 */      private Future<?> m389a(Runnable runnable) {
                    try {
/* 1958 */              return this.f511s.submit(new RunnableC0593G(this, runnable));
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
/* 1970 */              return null;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 1985 */      private <T> Future<T> m412b(Callable<T> callable) {
                    try {
/* 1986 */              return this.f511s.submit(new CallableC0595I(this, callable));
                    } catch (RejectedExecutionException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executor is shut down because we're handling a fatal crash.");
/* 1999 */              return null;
                    }
                }
            }
