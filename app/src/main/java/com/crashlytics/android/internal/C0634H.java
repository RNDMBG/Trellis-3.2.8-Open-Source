            package com.crashlytics.android.internal;

            import java.io.File;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.H */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    final class C0634H extends AbstractC0652Z implements InterfaceC0640N {
/* 23 */        public C0634H(String str, String str2, C0701av c0701av) {
/* 24 */            super(str, str2, c0701av, EnumC0703ax.POST);
                }

                @Override // com.crashlytics.android.internal.InterfaceC0640N
                /* JADX INFO: renamed from: a */
/* 41 */        public final boolean mo109a(String str, List<File> list) throws Throwable {
/* 43 */            C0704ay c0704ayM324a = m156b().m324a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m324a("X-CRASHLYTICS-API-CLIENT-VERSION", C0727v.m357a().getVersion()).m324a("X-CRASHLYTICS-API-KEY", str);
                    int i = 0;
                    for (File file : list) {
                        C0681ab.m221c("Adding analytics session file " + file.getName() + " to multipart POST");
                        c0704ayM324a.m325a("session_analytics_file_" + i, file.getName(), "application/vnd.crashlytics.android.events", file);
                        i++;
                    }
/* 46 */            C0681ab.m221c("Sending " + list.size() + " analytics files to " + m155a());
/* 47 */            int iM331b = c0704ayM324a.m331b();
/* 48 */            C0681ab.m221c("Response code for analytics file send is " + iM331b);
                    return C0723r.m352a(iM331b) == 0;
                }
            }
