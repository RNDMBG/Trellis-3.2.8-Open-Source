            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.ab */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    final class C0615ab implements FilenameFilter {
/* 24 */        C0615ab() {
                }

                @Override // java.io.FilenameFilter
/* 27 */        public final boolean accept(File file, String filename) {
                    return filename.endsWith(".cls") && !filename.contains("Session");
                }
            }
