            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.F */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 1881 */  final class C0592F implements FilenameFilter {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ String f152a;

/* 1882 */      C0592F(C0744v c0744v, String str) {
/* 1883 */          this.f152a = str;
                }

                @Override // java.io.FilenameFilter
/* 1885 */      public final boolean accept(File file, String name) {
/* 1886 */          return name.startsWith(this.f152a);
                }
            }
