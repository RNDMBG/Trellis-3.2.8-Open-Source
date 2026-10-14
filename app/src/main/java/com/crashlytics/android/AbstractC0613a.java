            package com.crashlytics.android;

            import android.content.res.Resources;
            import com.crashlytics.android.internal.AbstractC0652Z;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0701av;
            import com.crashlytics.android.internal.C0704ay;
            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.EnumC0703ax;
            import java.io.InputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    abstract class AbstractC0613a extends AbstractC0652Z {
/* 42 */        public AbstractC0613a(String str, String str2, C0701av c0701av, EnumC0703ax enumC0703ax) {
/* 43 */            super(str, str2, c0701av, enumC0703ax);
                }

                /* JADX INFO: renamed from: a */
/* 47 */        public final boolean m53a(C0619b c0619b) {
/* 50 */            C0704ay c0704ayM52a = m52a(m156b().m324a("X-CRASHLYTICS-API-KEY", c0619b.f186a).m324a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m324a("X-CRASHLYTICS-API-CLIENT-VERSION", C0727v.m357a().getVersion()), c0619b);
/* 52 */            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Sending app info to " + m155a());
/* 53 */            if (c0619b.f195j != null) {
/* 54 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "App icon hash is " + c0619b.f195j.f172a);
/* 56 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "App icon size is " + c0619b.f195j.f174c + "x" + c0619b.f195j.f175d);
                    }
/* 60 */            int iM331b = c0704ayM52a.m331b();
/* 63 */            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, ("POST".equals(c0704ayM52a.m334d()) ? "Create" : "Update") + " app request ID: " + c0704ayM52a.m329a("X-REQUEST-ID"));
/* 65 */            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Result was " + iM331b);
                    return C0723r.m352a(iM331b) == 0;
                }

                /* JADX INFO: renamed from: a */
/* 80 */        private static C0704ay m52a(C0704ay c0704ay, C0619b c0619b) {
/* 81 */            C0704ay c0704ayM332b = c0704ay.m332b("app[identifier]", c0619b.f187b).m332b("app[name]", c0619b.f191f).m332b("app[display_version]", c0619b.f188c).m332b("app[build_version]", c0619b.f189d).m323a("app[source]", Integer.valueOf(c0619b.f192g)).m332b("app[minimum_sdk_version]", c0619b.f193h).m332b("app[built_sdk_version]", c0619b.f194i);
/* 89 */            if (!C0681ab.m228e(c0619b.f190e)) {
/* 90 */                c0704ayM332b.m332b("app[instance_identifier]", c0619b.f190e);
                    }
/* 93 */            if (c0619b.f195j != null) {
/* 94 */                InputStream inputStreamOpenRawResource = null;
                        try {
                            try {
/* 97 */                        inputStreamOpenRawResource = C0727v.m357a().getContext().getResources().openRawResource(c0619b.f195j.f173b);
/* 100 */                       c0704ayM332b.m332b("app[icon][hash]", c0619b.f195j.f172a).m326a("app[icon][data]", "icon.png", "application/octet-stream", inputStreamOpenRawResource).m323a("app[icon][width]", Integer.valueOf(c0619b.f195j.f174c)).m323a("app[icon][height]", Integer.valueOf(c0619b.f195j.f175d));
/* 109 */                       C0681ab.m210a(inputStreamOpenRawResource, "Failed to close app icon InputStream.");
                            } catch (Resources.NotFoundException e) {
/* 105 */                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to find app icon with resource ID: " + c0619b.f195j.f173b, e);
/* 109 */                       C0681ab.m210a(inputStreamOpenRawResource, "Failed to close app icon InputStream.");
                            }
                        } catch (Throwable th) {
                            C0681ab.m210a(inputStreamOpenRawResource, "Failed to close app icon InputStream.");
                            throw th;
                        }
                    }
/* 113 */           return c0704ayM332b;
                }
            }
