            package com.crashlytics.android;

            import java.io.File;
            import java.util.Comparator;
            
            /* JADX INFO: renamed from: com.crashlytics.android.H */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 130 */   final class C0594H implements Comparator<File> {
/* 131 */       C0594H() {
                }

                @Override // java.util.Comparator
/* 131 */       public final /* synthetic */ int compare(File file, File file2) {
/* 132 */           return file2.getName().compareTo(file.getName());
                }
            }
