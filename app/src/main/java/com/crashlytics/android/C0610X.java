            package com.crashlytics.android;

            import android.content.Context;
            import com.crashlytics.android.internal.C0670aQ;
            import com.crashlytics.android.internal.C0681ab;
            
            /* JADX INFO: renamed from: com.crashlytics.android.X */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    final class C0610X {

                /* JADX INFO: renamed from: a */
                private final Context f170a;

                /* JADX INFO: renamed from: b */
                private final C0670aQ f171b;

/* 38 */        public C0610X(Context context, C0670aQ c0670aQ) {
/* 40 */            this.f170a = context;
/* 41 */            this.f171b = c0670aQ;
                }

                /* JADX INFO: renamed from: a */
/* 47 */        public final String m41a() {
/* 48 */            return m40a("com.crashlytics.CrashSubmissionPromptTitle", this.f171b.f315a);
                }

                /* JADX INFO: renamed from: b */
/* 54 */        public final String m42b() {
/* 55 */            return m40a("com.crashlytics.CrashSubmissionPromptMessage", this.f171b.f316b);
                }

                /* JADX INFO: renamed from: c */
/* 62 */        public final String m43c() {
/* 63 */            return m40a("com.crashlytics.CrashSubmissionSendTitle", this.f171b.f317c);
                }

                /* JADX INFO: renamed from: d */
/* 70 */        public final String m44d() {
/* 71 */            return m40a("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.f171b.f321g);
                }

                /* JADX INFO: renamed from: e */
/* 78 */        public final String m45e() {
/* 79 */            return m40a("com.crashlytics.CrashSubmissionCancelTitle", this.f171b.f319e);
                }

                /* JADX INFO: renamed from: a */
/* 91 */        private String m40a(String str, String str2) {
/* 92 */            String strM202a = C0681ab.m202a(this.f170a, str);
                    return strM202a == null || strM202a.length() == 0 ? str2 : strM202a;
                }
            }
