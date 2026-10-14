            package com.crashlytics.android;

            import java.io.File;
            import java.util.Comparator;
            
            /* JADX INFO: renamed from: com.crashlytics.android.J */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 137 */   final class C0596J implements Comparator<File> {
/* 138 */       C0596J() {
                }

                @Override // java.util.Comparator
/* 138 */       public final /* synthetic */ int compare(File file, File file2) {
/* 139 */           return file.getName().compareTo(file2.getName());
                }
            }
