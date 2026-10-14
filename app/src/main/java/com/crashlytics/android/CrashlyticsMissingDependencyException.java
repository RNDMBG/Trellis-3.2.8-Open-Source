            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLEncoder;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class CrashlyticsMissingDependencyException extends RuntimeException {
                private static final long serialVersionUID = -1151536370019872859L;

/* 17 */        CrashlyticsMissingDependencyException(String apiKey, String packageName) {
/* 18 */            super(m37a(apiKey, packageName));
                }

                /* JADX INFO: renamed from: a */
/* 21 */        private static String m37a(String str, String str2) {
/* 22 */            StringBuilder sb = new StringBuilder();
                    try {
/* 25 */                sb.append("\nThis app relies on Crashlytics. Configure your build environment here: \n");
                        sb.append(String.format("https://crashlytics.com/register/%s/android/%s", URLEncoder.encode(str, IoUtils.UTF_8), URLEncoder.encode(str2, IoUtils.UTF_8)) + IOUtils.LINE_SEPARATOR_UNIX);
                    } catch (UnsupportedEncodingException e) {
/* 28 */                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not find UTF-8 encoding.", e);
                    }
/* 31 */            return sb.toString();
                }
            }
