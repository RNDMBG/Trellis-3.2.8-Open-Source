            package com.flurry.sdk;

            import android.content.Context;
            import java.io.File;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cv */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class C0785cv {

                /* JADX INFO: renamed from: b */
                private static final String f536b = C0785cv.class.getSimpleName();

                /* JADX INFO: renamed from: a */
                boolean f537a;

                /* JADX INFO: renamed from: c */
                private final C0786cw f538c;

                /* JADX INFO: renamed from: d */
                private final File f539d;

                /* JADX INFO: renamed from: e */
                private String f540e;

/* 25 */        public C0785cv() {
/* 26 */            this(C0801dl.m732a().m736b());
                }

/* 29 */        public C0785cv(Context context) {
/* 19 */            this.f538c = new C0786cw();
/* 31 */            this.f539d = context.getFileStreamPath(".flurryinstallreceiver.");
/* 32 */            C0827el.m885a(3, f536b, "Referrer file name if it exists:  " + this.f539d);
                }

                /* JADX INFO: renamed from: a */
/* 36 */        public synchronized void m553a() {
                    try {
                        this.f539d.delete();
/* 38 */                this.f540e = null;
/* 39 */                this.f537a = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 44 */        public synchronized Map<String, List<String>> m552a(boolean z) {
                    Map<String, List<String>> mapM555a;
                    try {
                        m549b();
/* 47 */                mapM555a = this.f538c.m555a(this.f540e);
/* 48 */                if (z) {
/* 49 */                    m553a();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mapM555a;
                }

                /* JADX INFO: renamed from: a */
/* 57 */        public synchronized void m554a(String str) {
                    try {
                        this.f537a = true;
/* 60 */                m550b(str);
/* 61 */                m551c();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 64 */        private void m550b(String str) {
/* 65 */            if (str != null) {
/* 69 */                this.f540e = str;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 73 */        private void m549b() throws Throwable {
/* 74 */            if (!this.f537a) {
/* 78 */                this.f537a = true;
/* 80 */                C0827el.m885a(4, f536b, "Loading referrer info from file: " + this.f539d.getAbsolutePath());
/* 81 */                String strM957c = C0842fa.m957c(this.f539d);
/* 82 */                C0827el.m887a(f536b, "Referrer file contents: " + strM957c);
/* 84 */                m550b(strM957c);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 88 */        private void m551c() throws Throwable {
/* 89 */            C0842fa.m953a(this.f539d, this.f540e);
                }
            }
