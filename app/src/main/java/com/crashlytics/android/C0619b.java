            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            import java.util.Arrays;
            import java.util.Comparator;
            
            /* JADX INFO: renamed from: com.crashlytics.android.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    class C0619b {

                /* JADX INFO: renamed from: a */
                public final String f186a;

                /* JADX INFO: renamed from: b */
                public final String f187b;

                /* JADX INFO: renamed from: c */
                public final String f188c;

                /* JADX INFO: renamed from: d */
                public final String f189d;

                /* JADX INFO: renamed from: e */
                public final String f190e;

                /* JADX INFO: renamed from: f */
                public final String f191f;

                /* JADX INFO: renamed from: g */
                public final int f192g;

                /* JADX INFO: renamed from: h */
                public final String f193h;

                /* JADX INFO: renamed from: i */
                public final String f194i;

                /* JADX INFO: renamed from: j */
                public final C0611Y f195j;

                /* JADX INFO: renamed from: a */
/* 17 */        public static void m60a(File file, FilenameFilter filenameFilter, int i, Comparator<File> comparator) {
/* 18 */            File[] fileArrListFiles = file.listFiles(filenameFilter);
/* 19 */            if (fileArrListFiles != null && fileArrListFiles.length > i) {
/* 21 */                Arrays.sort(fileArrListFiles, comparator);
/* 22 */                int length = fileArrListFiles.length;
                        for (File file2 : fileArrListFiles) {
/* 25 */                    if (length > i) {
/* 28 */                        file2.delete();
/* 29 */                        length--;
                            } else {
/* 32 */                        return;
                            }
                        }
                    }
                }

/* 24 */        public C0619b(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, C0611Y c0611y) {
/* 26 */            this.f186a = str;
/* 27 */            this.f187b = str2;
/* 28 */            this.f188c = str3;
/* 29 */            this.f189d = str4;
/* 31 */            this.f190e = str5;
/* 32 */            this.f191f = str6;
/* 33 */            this.f192g = i;
/* 34 */            this.f193h = str7;
/* 35 */            this.f194i = str8;
/* 36 */            this.f195j = c0611y;
                }
            }
