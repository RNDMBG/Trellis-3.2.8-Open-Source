            package com.crashlytics.android;

            import com.crashlytics.android.internal.AbstractC0652Z;
            import com.crashlytics.android.internal.C0701av;
            import com.crashlytics.android.internal.C0704ay;
            import com.crashlytics.android.internal.C0723r;
            import com.crashlytics.android.internal.C0727v;
            import com.crashlytics.android.internal.EnumC0703ax;
            import java.util.Iterator;
            import java.util.Map;
            
            
            
    final class C0609W extends AbstractC0652Z implements InterfaceC0608V {
        public C0609W(String str, String str2, C0701av c0701av) {
            super(str, str2, c0701av, EnumC0703ax.POST);
                }

                @Override 
                
        public final boolean mo39a(C0607U c0607u) {
                    C0704ay c0704ay;
            C0704ay c0704ayM324a = m156b().m324a("X-CRASHLYTICS-API-KEY", c0607u.f168a).m324a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m324a("X-CRASHLYTICS-API-CLIENT-VERSION", Crashlytics.getInstance().getVersion());
                    Iterator<Map.Entry<String, String>> it = c0607u.f169b.m51e().entrySet().iterator();
                    while (true) {
                        c0704ay = c0704ayM324a;
                        if (!it.hasNext()) {
                            break;
                        }
                        c0704ayM324a = c0704ay.m327a(it.next());
                    }
            C0612Z c0612z = c0607u.f169b;
                    C0704ay c0704ayM332b = c0704ay.m325a("report[file]", c0612z.m48b(), "application/octet-stream", c0612z.m50d()).m332b("report[identifier]", c0612z.m49c());
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Sending report to: " + m155a());
            int iM331b = c0704ayM332b.m331b();
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Create report request ID: " + c0704ayM332b.m329a("X-REQUEST-ID"));
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Result was: " + iM331b);
                    return C0723r.m352a(iM331b) == 0;
                }
            }
