            package com.crashlytics.android;

            import android.util.Log;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0727v;
            
            /* JADX INFO: renamed from: com.crashlytics.android.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    final class C0620c {

                /* JADX INFO: renamed from: a */
                private String f196a;

                /* JADX INFO: renamed from: b */
                private boolean f197b;

/* 18 */        public C0620c(String str, boolean z) {
/* 20 */            this.f196a = str;
/* 21 */            this.f197b = z;
                }

                /* JADX INFO: renamed from: a */
/* 31 */        public final void m61a(String str, String str2) {
/* 32 */            if (C0681ab.m228e(this.f196a) && this.f197b) {
/* 33 */                Log.e(Crashlytics.TAG, ".");
/* 34 */                Log.e(Crashlytics.TAG, ".     |  | ");
/* 35 */                Log.e(Crashlytics.TAG, ".     |  |");
/* 36 */                Log.e(Crashlytics.TAG, ".     |  |");
/* 37 */                Log.e(Crashlytics.TAG, ".   \\ |  | /");
/* 38 */                Log.e(Crashlytics.TAG, ".    \\    /");
/* 39 */                Log.e(Crashlytics.TAG, ".     \\  /");
/* 40 */                Log.e(Crashlytics.TAG, ".      \\/");
/* 41 */                Log.e(Crashlytics.TAG, ".");
/* 42 */                Log.e(Crashlytics.TAG, "This app relies on Crashlytics. Configure your build environment here: ");
/* 43 */                Log.e(Crashlytics.TAG, String.format("https://crashlytics.com/register/%s/android/%s", str, str2));
/* 44 */                Log.e(Crashlytics.TAG, ".");
/* 45 */                Log.e(Crashlytics.TAG, ".      /\\");
/* 46 */                Log.e(Crashlytics.TAG, ".     /  \\");
/* 47 */                Log.e(Crashlytics.TAG, ".    /    \\");
/* 48 */                Log.e(Crashlytics.TAG, ".   / |  | \\");
/* 49 */                Log.e(Crashlytics.TAG, ".     |  |");
/* 50 */                Log.e(Crashlytics.TAG, ".     |  |");
/* 51 */                Log.e(Crashlytics.TAG, ".     |  |");
/* 52 */                Log.e(Crashlytics.TAG, ".");
/* 54 */                throw new CrashlyticsMissingDependencyException(str, str2);
                    }
/* 55 */            if (!this.f197b) {
/* 56 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Configured not to require a build ID.");
                    }
                }
            }
