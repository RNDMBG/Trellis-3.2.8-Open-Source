            package com.crashlytics.android.internal;

            import java.io.File;
            import java.util.List;
            
            
            
    final class C0634H extends AbstractC0652Z implements InterfaceC0640N {
        public C0634H(String str, String str2, C0701av c0701av) {
            super(str, str2, c0701av, EnumC0703ax.POST);
                }

                @Override 
                
        public final boolean mo109a(String str, List<File> list) throws Throwable {
            C0704ay c0704ayM324a = m156b().m324a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m324a("X-CRASHLYTICS-API-CLIENT-VERSION", C0727v.m357a().getVersion()).m324a("X-CRASHLYTICS-API-KEY", str);
                    int i = 0;
                    for (File file : list) {
                        C0681ab.m221c("Adding analytics session file " + file.getName() + " to multipart POST");
                        c0704ayM324a.m325a("session_analytics_file_" + i, file.getName(), "application/vnd.crashlytics.android.events", file);
                        i++;
                    }
            C0681ab.m221c("Sending " + list.size() + " analytics files to " + m155a());
            int iM331b = c0704ayM324a.m331b();
            C0681ab.m221c("Response code for analytics file send is " + iM331b);
                    return C0723r.m352a(iM331b) == 0;
                }
            }
