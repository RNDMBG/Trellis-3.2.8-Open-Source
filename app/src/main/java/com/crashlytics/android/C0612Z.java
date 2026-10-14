            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.io.File;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.crashlytics.android.Z */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class C0612Z {

                /* JADX INFO: renamed from: a */
                private final File f176a;

                /* JADX INFO: renamed from: b */
                private final Map<String, String> f177b;

/* 25 */        public C0612Z(File file) {
/* 26 */            this(file, Collections.emptyMap());
                }

/* 31 */        public C0612Z(File file, Map<String, String> map) {
/* 33 */            this.f176a = file;
/* 34 */            this.f177b = new HashMap(map);
                    if (this.f176a.length() == 0) {
/* 36 */                this.f177b.putAll(C0614aa.f178a);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 40 */        public File m50d() {
/* 41 */            return this.f176a;
                }

                /* JADX INFO: renamed from: b */
/* 45 */        public String m48b() {
/* 46 */            return m50d().getName();
                }

                /* JADX INFO: renamed from: c */
/* 50 */        public String m49c() {
/* 51 */            String strM48b = m48b();
/* 52 */            return strM48b.substring(0, strM48b.lastIndexOf(46));
                }

                /* JADX INFO: renamed from: e */
/* 56 */        public Map<String, String> m51e() {
/* 57 */            return Collections.unmodifiableMap(this.f177b);
                }

                /* JADX INFO: renamed from: a */
/* 61 */        public boolean m47a() {
/* 62 */            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Removing report at " + this.f176a.getPath());
/* 63 */            return this.f176a.delete();
                }
            }
