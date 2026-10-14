            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.S */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 92 */    final class C0605S implements FilenameFilter {

                /* JADX INFO: renamed from: a */
                private final String f167a;

/* 93 */        public C0605S(String str) {
/* 95 */            this.f167a = str;
                }

                @Override // java.io.FilenameFilter
/* 99 */        public final boolean accept(File file, String fileName) {
                    return (fileName.equals(new StringBuilder().append(this.f167a).append(".cls").toString()) || !fileName.contains(this.f167a) || fileName.endsWith(".cls_temp")) ? false : true;
                }
            }
