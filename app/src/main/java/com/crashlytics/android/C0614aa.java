            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import java.io.File;
            import java.io.FilenameFilter;
            import java.util.Collections;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.crashlytics.android.aa */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    final class C0614aa {

                /* JADX INFO: renamed from: d */
                private final Object f181d = new Object();

                /* JADX INFO: renamed from: e */
                private final InterfaceC0608V f182e;

                /* JADX INFO: renamed from: f */
                private Thread f183f;

                /* JADX INFO: renamed from: b */
                private static final FilenameFilter f179b = new C0615ab();

                /* JADX INFO: renamed from: a */
                static final Map<String, String> f178a = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");

                /* JADX INFO: renamed from: c */
                private static final short[] f180c = {10, 20, 30, 60, 120, 300};

                /* JADX INFO: renamed from: a */
/* 22 */        static /* synthetic */ Thread m54a(C0614aa c0614aa, Thread thread) {
/* 23 */            c0614aa.f183f = null;
/* 23 */            return null;
                }

                /* JADX INFO: renamed from: b */
/* 22 */        static /* synthetic */ short[] m55b() {
/* 23 */            return f180c;
                }

/* 41 */        public C0614aa(InterfaceC0608V interfaceC0608V) {
/* 43 */            if (interfaceC0608V == null) {
/* 44 */                throw new IllegalArgumentException("createReportCall must not be null.");
                    }
/* 46 */            this.f182e = interfaceC0608V;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        public final synchronized void m57a(float f) {
                    try {
                        if (this.f183f == null) {
/* 55 */                    this.f183f = new Thread(new C0616ac(this, f), "Crashlytics Report Uploader");
/* 56 */                    this.f183f.start();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 73 */        final boolean m58a(C0612Z c0612z) {
/* 74 */            boolean z = false;
                    synchronized (this.f181d) {
                        try {
                            try {
/* 81 */                        boolean zMo39a = this.f182e.mo39a(new C0607U(C0723r.m354a(C0727v.m357a().getContext(), C0727v.m357a().m368f()), c0612z));
/* 83 */                        C0727v.m357a().m365b().mo98b(Crashlytics.TAG, "Crashlytics report upload " + (zMo39a ? "complete: " : "FAILED: ") + c0612z.m48b());
/* 86 */                        if (zMo39a) {
/* 87 */                            c0612z.m47a();
/* 88 */                            z = true;
                                }
                            } catch (Exception e) {
/* 93 */                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error occurred sending report " + c0612z, e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 96 */            return z;
                }

                /* JADX INFO: renamed from: a */
/* 99 */        final List<C0612Z> m56a() {
                    File[] fileArrListFiles;
/* 100 */           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Checking for crash reports...");
                    synchronized (this.f181d) {
                        try {
/* 104 */                   fileArrListFiles = C0727v.m357a().m370h().listFiles(f179b);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 107 */           LinkedList linkedList = new LinkedList();
                    for (File file : fileArrListFiles) {
/* 109 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Found crash report " + file.getPath());
/* 110 */               linkedList.add(new C0612Z(file));
                    }
/* 113 */           if (linkedList.size() == 0) {
/* 114 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No reports found.");
                    }
/* 117 */           return linkedList;
                }
            }
