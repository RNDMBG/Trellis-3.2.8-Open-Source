            package com.crashlytics.android;

            import android.util.Log;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0727v;
            
            
            
    final class C0620c {

                
                private String f196a;

                
                private boolean f197b;

        public C0620c(String str, boolean z) {
            this.f196a = str;
            this.f197b = z;
                }

                
        public final void m61a(String str, String str2) {
            if (C0681ab.m228e(this.f196a) && this.f197b) {
                Log.e(Crashlytics.TAG, ".");
                Log.e(Crashlytics.TAG, ".     |  | ");
                Log.e(Crashlytics.TAG, ".     |  |");
                Log.e(Crashlytics.TAG, ".     |  |");
                Log.e(Crashlytics.TAG, ".   \\ |  | /");
                Log.e(Crashlytics.TAG, ".    \\    /");
                Log.e(Crashlytics.TAG, ".     \\  /");
                Log.e(Crashlytics.TAG, ".      \\/");
                Log.e(Crashlytics.TAG, ".");
                Log.e(Crashlytics.TAG, "This app relies on Crashlytics. Configure your build environment here: ");
                Log.e(Crashlytics.TAG, String.format("https://crashlytics.com/register/%s/android/%s", str, str2));
                Log.e(Crashlytics.TAG, ".");
                Log.e(Crashlytics.TAG, ".      /\\");
                Log.e(Crashlytics.TAG, ".     /  \\");
                Log.e(Crashlytics.TAG, ".    /    \\");
                Log.e(Crashlytics.TAG, ".   / |  | \\");
                Log.e(Crashlytics.TAG, ".     |  |");
                Log.e(Crashlytics.TAG, ".     |  |");
                Log.e(Crashlytics.TAG, ".     |  |");
                Log.e(Crashlytics.TAG, ".");
                throw new CrashlyticsMissingDependencyException(str, str2);
                    }
            if (!this.f197b) {
                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Configured not to require a build ID.");
                    }
                }
            }
