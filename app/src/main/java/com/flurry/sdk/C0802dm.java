            package com.flurry.sdk;

            import android.location.Criteria;
            import com.googlecode.javacv.cpp.avcodec;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dm */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public class C0802dm {

                /* JADX INFO: renamed from: a */
                public static final Integer f692a = Integer.valueOf(avcodec.AV_CODEC_ID_AIC);

                /* JADX INFO: renamed from: b */
                public static final Integer f693b = 4;

                /* JADX INFO: renamed from: c */
                public static final Integer f694c = 0;

                /* JADX INFO: renamed from: d */
                public static final Integer f695d = 0;

                /* JADX INFO: renamed from: e */
                public static final String f696e = null;

                /* JADX INFO: renamed from: f */
                public static final Boolean f697f = true;

                /* JADX INFO: renamed from: g */
                public static final Boolean f698g = true;

                /* JADX INFO: renamed from: h */
                public static final String f699h = null;

                /* JADX INFO: renamed from: i */
                public static final Boolean f700i = true;

                /* JADX INFO: renamed from: j */
                public static final Criteria f701j = null;

                /* JADX INFO: renamed from: k */
                public static final Long f702k = 10000L;

                /* JADX INFO: renamed from: l */
                public static final Boolean f703l = true;

                /* JADX INFO: renamed from: m */
                public static final Long f704m = 0L;

                /* JADX INFO: renamed from: n */
                public static final Byte f705n = (byte) -1;

                /* JADX INFO: renamed from: o */
                private static C0803dn f706o;

                /* JADX INFO: renamed from: a */
/* 63 */        public static synchronized C0803dn m742a() {
                    try {
                        if (f706o == null) {
/* 65 */                    f706o = new C0803dn();
/* 66 */                    m743b();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 69 */            return f706o;
                }

                /* JADX INFO: renamed from: b */
/* 76 */        private static void m743b() {
/* 77 */            if (f706o == null) {
/* 78 */                f706o = new C0803dn();
                    }
/* 81 */            f706o.m748a("AgentVersion", (Object) f692a);
/* 82 */            f706o.m748a("ReleaseMajorVersion", (Object) f693b);
/* 83 */            f706o.m748a("ReleaseMinorVersion", (Object) f694c);
/* 84 */            f706o.m748a("ReleasePatchVersion", (Object) f695d);
/* 85 */            f706o.m748a("ReleaseBetaVersion", (Object) "");
/* 87 */            f706o.m748a("VersionName", (Object) f696e);
/* 89 */            f706o.m748a("CaptureUncaughtExceptions", (Object) f697f);
/* 91 */            f706o.m748a("UseHttps", (Object) f698g);
/* 92 */            f706o.m748a("ReportUrl", (Object) f699h);
/* 94 */            f706o.m748a("ReportLocation", (Object) f700i);
/* 95 */            f706o.m748a("LocationCriteria", (Object) f701j);
/* 97 */            f706o.m748a("ContinueSessionMillis", (Object) f702k);
/* 98 */            f706o.m748a("LogEvents", (Object) f703l);
/* 99 */            f706o.m748a("Age", (Object) f704m);
/* 100 */           f706o.m748a("Gender", (Object) f705n);
/* 101 */           f706o.m748a("UserId", (Object) "");
                }
            }
