            package com.crashlytics.android;

            import android.content.Context;
            import com.crashlytics.android.internal.C0670aQ;
            import com.crashlytics.android.internal.C0681ab;
            
            
            
    final class C0610X {

                
                private final Context f170a;

                
                private final C0670aQ f171b;

        public C0610X(Context context, C0670aQ c0670aQ) {
            this.f170a = context;
            this.f171b = c0670aQ;
                }

                
        public final String m41a() {
            return m40a("com.crashlytics.CrashSubmissionPromptTitle", this.f171b.f315a);
                }

                
        public final String m42b() {
            return m40a("com.crashlytics.CrashSubmissionPromptMessage", this.f171b.f316b);
                }

                
        public final String m43c() {
            return m40a("com.crashlytics.CrashSubmissionSendTitle", this.f171b.f317c);
                }

                
        public final String m44d() {
            return m40a("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.f171b.f321g);
                }

                
        public final String m45e() {
            return m40a("com.crashlytics.CrashSubmissionCancelTitle", this.f171b.f319e);
                }

                
        private String m40a(String str, String str2) {
            String strM202a = C0681ab.m202a(this.f170a, str);
                    return strM202a == null || strM202a.length() == 0 ? str2 : strM202a;
                }
            }
