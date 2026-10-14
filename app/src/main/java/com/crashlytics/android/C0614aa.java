            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import java.io.File;
            import java.io.FilenameFilter;
            import java.util.Collections;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Map;
            
            
            
    final class C0614aa {

                
                private final Object f181d = new Object();

                
                private final InterfaceC0608V f182e;

                
                private Thread f183f;

                
                private static final FilenameFilter f179b = new C0615ab();

                
                static final Map<String, String> f178a = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");

                
                private static final short[] f180c = {10, 20, 30, 60, 120, 300};

                
        static  Thread m54a(C0614aa c0614aa, Thread thread) {
            c0614aa.f183f = null;
            return null;
                }

                
        static  short[] m55b() {
            return f180c;
                }

        public C0614aa(InterfaceC0608V interfaceC0608V) {
            if (interfaceC0608V == null) {
                throw new IllegalArgumentException("createReportCall must not be null.");
                    }
            this.f182e = interfaceC0608V;
                }

                
        public final synchronized void m57a(float f) {
                    try {
                        if (this.f183f == null) {
                    this.f183f = new Thread(new C0616ac(this, f), "Crashlytics Report Uploader");
                    this.f183f.start();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        final boolean m58a(C0612Z c0612z) {
            boolean z = false;
                    synchronized (this.f181d) {
                        try {
                            try {
                        boolean zMo39a = this.f182e.mo39a(new C0607U(C0723r.m354a(C0727v.m357a().getContext(), C0727v.m357a().m368f()), c0612z));
                        C0727v.m357a().m365b().mo98b(Crashlytics.TAG, "Crashlytics report upload " + (zMo39a ? "complete: " : "FAILED: ") + c0612z.m48b());
                        if (zMo39a) {
                            c0612z.m47a();
                            z = true;
                                }
                            } catch (Exception e) {
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error occurred sending report " + c0612z, e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            return z;
                }

                
        final List<C0612Z> m56a() {
                    File[] fileArrListFiles;
           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Checking for crash reports...");
                    synchronized (this.f181d) {
                        try {
                   fileArrListFiles = C0727v.m357a().m370h().listFiles(f179b);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           LinkedList linkedList = new LinkedList();
                    for (File file : fileArrListFiles) {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Found crash report " + file.getPath());
               linkedList.add(new C0612Z(file));
                    }
           if (linkedList.size() == 0) {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No reports found.");
                    }
           return linkedList;
                }
            }
