            package com.flurry.sdk;

            import android.location.Criteria;
            import com.googlecode.javacv.cpp.avcodec;
            
            
            
    public class C0802dm {

                
                public static final Integer f692a = Integer.valueOf(avcodec.AV_CODEC_ID_AIC);

                
                public static final Integer f693b = 4;

                
                public static final Integer f694c = 0;

                
                public static final Integer f695d = 0;

                
                public static final String f696e = null;

                
                public static final Boolean f697f = true;

                
                public static final Boolean f698g = true;

                
                public static final String f699h = null;

                
                public static final Boolean f700i = true;

                
                public static final Criteria f701j = null;

                
                public static final Long f702k = 10000L;

                
                public static final Boolean f703l = true;

                
                public static final Long f704m = 0L;

                
                public static final Byte f705n = (byte) -1;

                
                private static C0803dn f706o;

                
        public static synchronized C0803dn m742a() {
                    try {
                        if (f706o == null) {
                    f706o = new C0803dn();
                    m743b();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f706o;
                }

                
        private static void m743b() {
            if (f706o == null) {
                f706o = new C0803dn();
                    }
            f706o.m748a("AgentVersion", (Object) f692a);
            f706o.m748a("ReleaseMajorVersion", (Object) f693b);
            f706o.m748a("ReleaseMinorVersion", (Object) f694c);
            f706o.m748a("ReleasePatchVersion", (Object) f695d);
            f706o.m748a("ReleaseBetaVersion", (Object) "");
            f706o.m748a("VersionName", (Object) f696e);
            f706o.m748a("CaptureUncaughtExceptions", (Object) f697f);
            f706o.m748a("UseHttps", (Object) f698g);
            f706o.m748a("ReportUrl", (Object) f699h);
            f706o.m748a("ReportLocation", (Object) f700i);
            f706o.m748a("LocationCriteria", (Object) f701j);
            f706o.m748a("ContinueSessionMillis", (Object) f702k);
            f706o.m748a("LogEvents", (Object) f703l);
            f706o.m748a("Age", (Object) f704m);
           f706o.m748a("Gender", (Object) f705n);
           f706o.m748a("UserId", (Object) "");
                }
            }
