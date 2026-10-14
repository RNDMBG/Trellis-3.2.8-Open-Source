            package com.flurry.sdk;

            import java.nio.ByteBuffer;
            import java.util.Collections;
            import java.util.Locale;
            import java.util.Map;
            
            
            
    public class C0800dk {

                
                private static final String f686a = C0800dk.class.getSimpleName();

                
                private static C0800dk f687b;

                
        public static synchronized C0800dk m720a() {
                    try {
                        if (f687b == null) {
                    f687b = new C0800dk();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f687b;
                }

                
        public int m721b() {
            int iIntValue = ((Integer) C0802dm.m742a().m746a("AgentVersion")).intValue();
            C0827el.m885a(4, f686a, "getAgentVersion() = " + iIntValue);
            return iIntValue;
                }

                
        int m722c() {
            return ((Integer) C0802dm.m742a().m746a("ReleaseMajorVersion")).intValue();
                }

                
        int m723d() {
            return ((Integer) C0802dm.m742a().m746a("ReleaseMinorVersion")).intValue();
                }

                
        int m724e() {
            return ((Integer) C0802dm.m742a().m746a("ReleasePatchVersion")).intValue();
                }

                
        String m725f() {
            return (String) C0802dm.m742a().m746a("ReleaseBetaVersion");
                }

                
        public String m726g() {
                    String str;
            if (m725f().length() > 0) {
                str = ".";
                    } else {
                str = "";
                    }
                    return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", Integer.valueOf(m721b()), Integer.valueOf(m722c()), Integer.valueOf(m723d()), Integer.valueOf(m724e()), str, m725f());
                }

                
        public String m727h() {
            C0796dg c0796dgM715c = C0798di.m709a().m715c();
            if (c0796dgM715c == null) {
                return null;
                    }
            return c0796dgM715c.m688j();
                }

                
        public String m728i() {
            C0796dg c0796dgM715c = C0798di.m709a().m715c();
            if (c0796dgM715c == null) {
                return null;
                    }
            return c0796dgM715c.m689k();
                }

                
        public String m729j() {
            C0796dg c0796dgM715c = C0798di.m709a().m715c();
            if (c0796dgM715c == null) {
                return null;
                    }
            return c0796dgM715c.m690l();
                }

                
        public boolean m730k() {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               return c0796dgM715c.m693o();
                    }
                    return true;
                }

                
       public Map<EnumC0804do, ByteBuffer> m731l() {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
                    return c0796dgM715c != null ? c0796dgM715c.m694p() : Collections.emptyMap();
                }
            }
