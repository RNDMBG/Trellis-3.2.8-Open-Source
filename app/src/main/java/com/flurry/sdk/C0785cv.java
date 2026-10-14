            package com.flurry.sdk;

            import android.content.Context;
            import java.io.File;
            import java.util.List;
            import java.util.Map;
            
            
            
    public class C0785cv {

                
                private static final String f536b = C0785cv.class.getSimpleName();

                
                boolean f537a;

                
                private final C0786cw f538c;

                
                private final File f539d;

                
                private String f540e;

        public C0785cv() {
            this(C0801dl.m732a().m736b());
                }

        public C0785cv(Context context) {
            this.f538c = new C0786cw();
            this.f539d = context.getFileStreamPath(".flurryinstallreceiver.");
            C0827el.m885a(3, f536b, "Referrer file name if it exists:  " + this.f539d);
                }

                
        public synchronized void m553a() {
                    try {
                        this.f539d.delete();
                this.f540e = null;
                this.f537a = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized Map<String, List<String>> m552a(boolean z) {
                    Map<String, List<String>> mapM555a;
                    try {
                        m549b();
                mapM555a = this.f538c.m555a(this.f540e);
                if (z) {
                    m553a();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mapM555a;
                }

                
        public synchronized void m554a(String str) {
                    try {
                        this.f537a = true;
                m550b(str);
                m551c();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        private void m550b(String str) {
            if (str != null) {
                this.f540e = str;
                    }
                }

                
        private void m549b() throws Throwable {
            if (!this.f537a) {
                this.f537a = true;
                C0827el.m885a(4, f536b, "Loading referrer info from file: " + this.f539d.getAbsolutePath());
                String strM957c = C0842fa.m957c(this.f539d);
                C0827el.m887a(f536b, "Referrer file contents: " + strM957c);
                m550b(strM957c);
                    }
                }

                
        private void m551c() throws Throwable {
            C0842fa.m953a(this.f539d, this.f540e);
                }
            }
