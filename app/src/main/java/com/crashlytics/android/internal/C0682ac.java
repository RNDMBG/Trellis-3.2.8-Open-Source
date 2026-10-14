            package com.crashlytics.android.internal;

            import java.io.File;
            import java.util.Comparator;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ac */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 680 */   final class C0682ac implements Comparator<File> {
/* 681 */       C0682ac() {
                }

                @Override // java.util.Comparator
/* 681 */       public final /* synthetic */ int compare(File file, File file2) {
/* 682 */           return (int) (file.lastModified() - file2.lastModified());
                }
            }
