            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.w */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 122 */   final class C0745w implements FilenameFilter {
/* 123 */       C0745w() {
                }

                @Override // java.io.FilenameFilter
/* 126 */       public final boolean accept(File file, String filename) {
                    return filename.length() == 39 && filename.endsWith(".cls");
                }
            }
