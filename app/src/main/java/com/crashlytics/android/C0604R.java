            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.R */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    final class C0604R implements FilenameFilter {

                /* JADX INFO: renamed from: a */
                private final String f166a;

/* 74 */        public C0604R(String str) {
/* 76 */            this.f166a = str;
                }

                @Override // java.io.FilenameFilter
/* 80 */        public final boolean accept(File file, String filename) {
                    return filename.contains(this.f166a) && !filename.endsWith(".cls_temp");
                }
            }
