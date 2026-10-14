            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.K */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 147 */   final class C0597K implements FilenameFilter {
/* 148 */       C0597K() {
                }

                @Override // java.io.FilenameFilter
/* 151 */       public final boolean accept(File file, String filename) {
/* 152 */           return C0744v.m420i().matcher(filename).matches();
                }
            }
