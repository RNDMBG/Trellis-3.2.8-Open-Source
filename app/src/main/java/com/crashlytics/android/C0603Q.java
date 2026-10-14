            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            /* JADX INFO: renamed from: com.crashlytics.android.Q */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 112 */   final class C0603Q implements FilenameFilter {
/* 113 */       private C0603Q() {
                }

/* 113 */       /* synthetic */ C0603Q(byte b) {
/* 114 */           this();
                }

                @Override // java.io.FilenameFilter
/* 116 */       public final boolean accept(File file, String fileName) {
                    return !C0744v.f493a.accept(file, fileName) && C0744v.m420i().matcher(fileName).matches();
                }
            }
