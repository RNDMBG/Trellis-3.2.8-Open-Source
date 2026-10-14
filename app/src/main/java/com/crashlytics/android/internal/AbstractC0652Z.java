            package com.crashlytics.android.internal;

            import java.util.Collections;
            import java.util.Map;
            import java.util.regex.Pattern;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.Z */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public abstract class AbstractC0652Z {

                /* JADX INFO: renamed from: a */
                private static String f284a = "Crashlytics Android SDK/" + C0727v.m357a().getVersion();

                /* JADX INFO: renamed from: b */
                private static final Pattern f285b = Pattern.compile("http(s?)://[^\\/]+", 2);

                /* JADX INFO: renamed from: c */
                private final String f286c;

                /* JADX INFO: renamed from: d */
                private final C0701av f287d;

                /* JADX INFO: renamed from: e */
                private final EnumC0703ax f288e;

                /* JADX INFO: renamed from: f */
                private final String f289f;

/* 67 */        public AbstractC0652Z(String str, String str2, C0701av c0701av, EnumC0703ax enumC0703ax) {
/* 69 */            if (str2 == null) {
/* 70 */                throw new IllegalArgumentException("url must not be null.");
                    }
/* 72 */            if (c0701av == null) {
/* 73 */                throw new IllegalArgumentException("requestFactory must not be null.");
                    }
/* 75 */            this.f289f = str;
/* 76 */            this.f286c = C0681ab.m228e(this.f289f) ? str2 : f285b.matcher(str2).replaceFirst(this.f289f);
/* 77 */            this.f287d = c0701av;
/* 78 */            this.f288e = enumC0703ax;
                }

                /* JADX INFO: renamed from: a */
/* 81 */        protected final String m155a() {
/* 82 */            return this.f286c;
                }

                /* JADX INFO: renamed from: b */
/* 98 */        protected final C0704ay m156b() {
/* 99 */            return m154a(Collections.emptyMap());
                }

                /* JADX INFO: renamed from: a */
/* 118 */       protected final C0704ay m154a(Map<String, String> map) {
/* 120 */           return this.f287d.m297a(this.f288e, this.f286c, map).m328a(false).m322a(10000).m324a("User-Agent", f284a).m324a("X-CRASHLYTICS-DEVELOPER-TOKEN", "bca6990fc3c15a8105800c0673517a4b579634a1");
                }
            }
