            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLEncoder;
            import org.apache.commons.io.IOUtils;
            
            
    public class CrashlyticsMissingDependencyException extends RuntimeException {
                private static final long serialVersionUID = -1151536370019872859L;

        CrashlyticsMissingDependencyException(String apiKey, String packageName) {
            super(m37a(apiKey, packageName));
                }

                
        private static String m37a(String str, String str2) {
            StringBuilder sb = new StringBuilder();
                    try {
                sb.append("\nThis app relies on Crashlytics. Configure your build environment here: \n");
                        sb.append(String.format("https://crashlytics.com/register/%s/android/%s", URLEncoder.encode(str, IoUtils.UTF_8), URLEncoder.encode(str2, IoUtils.UTF_8)) + IOUtils.LINE_SEPARATOR_UNIX);
                    } catch (UnsupportedEncodingException e) {
                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not find UTF-8 encoding.", e);
                    }
            return sb.toString();
                }
            }
