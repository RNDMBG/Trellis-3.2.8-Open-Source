            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.g */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 97 */    final class C0624g implements FilenameFilter {
/* 98 */        C0624g() {
                }

                @Override // java.io.FilenameFilter
/* 101 */       public final boolean accept(File file, String filename) {
/* 102 */           return filename.endsWith(".cls_temp");
                }
            }
