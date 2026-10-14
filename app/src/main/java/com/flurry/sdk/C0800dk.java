            package com.flurry.sdk;

            import java.nio.ByteBuffer;
            import java.util.Collections;
            import java.util.Locale;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dk */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class C0800dk {

                /* JADX INFO: renamed from: a */
                private static final String f686a = C0800dk.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static C0800dk f687b;

                /* JADX INFO: renamed from: a */
/* 24 */        public static synchronized C0800dk m720a() {
                    try {
                        if (f687b == null) {
/* 26 */                    f687b = new C0800dk();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 29 */            return f687b;
                }

                /* JADX INFO: renamed from: b */
/* 32 */        public int m721b() {
/* 33 */            int iIntValue = ((Integer) C0802dm.m742a().m746a("AgentVersion")).intValue();
/* 34 */            C0827el.m885a(4, f686a, "getAgentVersion() = " + iIntValue);
/* 35 */            return iIntValue;
                }

                /* JADX INFO: renamed from: c */
/* 38 */        int m722c() {
/* 39 */            return ((Integer) C0802dm.m742a().m746a("ReleaseMajorVersion")).intValue();
                }

                /* JADX INFO: renamed from: d */
/* 42 */        int m723d() {
/* 43 */            return ((Integer) C0802dm.m742a().m746a("ReleaseMinorVersion")).intValue();
                }

                /* JADX INFO: renamed from: e */
/* 46 */        int m724e() {
/* 47 */            return ((Integer) C0802dm.m742a().m746a("ReleasePatchVersion")).intValue();
                }

                /* JADX INFO: renamed from: f */
/* 50 */        String m725f() {
/* 51 */            return (String) C0802dm.m742a().m746a("ReleaseBetaVersion");
                }

                /* JADX INFO: renamed from: g */
/* 55 */        public String m726g() {
                    String str;
/* 56 */            if (m725f().length() > 0) {
/* 57 */                str = ".";
                    } else {
/* 59 */                str = "";
                    }
                    return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", Integer.valueOf(m721b()), Integer.valueOf(m722c()), Integer.valueOf(m723d()), Integer.valueOf(m724e()), str, m725f());
                }

                /* JADX INFO: renamed from: h */
/* 69 */        public String m727h() {
/* 71 */            C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 72 */            if (c0796dgM715c == null) {
/* 70 */                return null;
                    }
/* 73 */            return c0796dgM715c.m688j();
                }

                /* JADX INFO: renamed from: i */
/* 79 */        public String m728i() {
/* 81 */            C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 82 */            if (c0796dgM715c == null) {
/* 80 */                return null;
                    }
/* 83 */            return c0796dgM715c.m689k();
                }

                /* JADX INFO: renamed from: j */
/* 89 */        public String m729j() {
/* 91 */            C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 92 */            if (c0796dgM715c == null) {
/* 90 */                return null;
                    }
/* 93 */            return c0796dgM715c.m690l();
                }

                /* JADX INFO: renamed from: k */
/* 99 */        public boolean m730k() {
/* 100 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 101 */           if (c0796dgM715c != null) {
/* 102 */               return c0796dgM715c.m693o();
                    }
                    return true;
                }

                /* JADX INFO: renamed from: l */
/* 108 */       public Map<EnumC0804do, ByteBuffer> m731l() {
/* 109 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
                    return c0796dgM715c != null ? c0796dgM715c.m694p() : Collections.emptyMap();
                }
            }
