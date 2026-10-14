            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.io.File;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.Map;
            
            
            
    class C0612Z {

                
                private final File f176a;

                
                private final Map<String, String> f177b;

        public C0612Z(File file) {
            this(file, Collections.emptyMap());
                }

        public C0612Z(File file, Map<String, String> map) {
            this.f176a = file;
            this.f177b = new HashMap(map);
                    if (this.f176a.length() == 0) {
                this.f177b.putAll(C0614aa.f178a);
                    }
                }

                
        public File m50d() {
            return this.f176a;
                }

                
        public String m48b() {
            return m50d().getName();
                }

                
        public String m49c() {
            String strM48b = m48b();
            return strM48b.substring(0, strM48b.lastIndexOf(46));
                }

                
        public Map<String, String> m51e() {
            return Collections.unmodifiableMap(this.f177b);
                }

                
        public boolean m47a() {
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Removing report at " + this.f176a.getPath());
            return this.f176a.delete();
                }
            }
