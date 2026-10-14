            package com.crashlytics.android.internal;

            import java.util.Collections;
            import java.util.Map;
            import java.util.regex.Pattern;
            
            
            
    public abstract class AbstractC0652Z {

                
                private static String f284a = "Crashlytics Android SDK/" + C0727v.m357a().getVersion();

                
                private static final Pattern f285b = Pattern.compile("http(s?)://[^\\/]+", 2);

                
                private final String f286c;

                
                private final C0701av f287d;

                
                private final EnumC0703ax f288e;

                
                private final String f289f;

        public AbstractC0652Z(String str, String str2, C0701av c0701av, EnumC0703ax enumC0703ax) {
            if (str2 == null) {
                throw new IllegalArgumentException("url must not be null.");
                    }
            if (c0701av == null) {
                throw new IllegalArgumentException("requestFactory must not be null.");
                    }
            this.f289f = str;
            this.f286c = C0681ab.m228e(this.f289f) ? str2 : f285b.matcher(str2).replaceFirst(this.f289f);
            this.f287d = c0701av;
            this.f288e = enumC0703ax;
                }

                
        protected final String m155a() {
            return this.f286c;
                }

                
        protected final C0704ay m156b() {
            return m154a(Collections.emptyMap());
                }

                
       protected final C0704ay m154a(Map<String, String> map) {
           return this.f287d.m297a(this.f288e, this.f286c, map).m328a(false).m322a(10000).m324a("User-Agent", f284a).m324a("X-CRASHLYTICS-DEVELOPER-TOKEN", "bca6990fc3c15a8105800c0673517a4b579634a1");
                }
            }
