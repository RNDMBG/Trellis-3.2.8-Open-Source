            package com.google.android.exoplayer.util;

            import android.annotation.TargetApi;
            import android.os.Trace;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class TraceUtil {
/* 32 */        public static void beginSection(String sectionName) {
/* 33 */            if (Util.SDK_INT >= 18) {
/* 34 */                beginSectionV18(sectionName);
                    }
                }

/* 43 */        public static void endSection() {
/* 44 */            if (Util.SDK_INT >= 18) {
/* 45 */                endSectionV18();
                    }
                }

                @TargetApi(18)
/* 50 */        private static void beginSectionV18(String sectionName) {
/* 51 */            Trace.beginSection(sectionName);
                }

                @TargetApi(18)
/* 55 */        private static void endSectionV18() {
/* 56 */            Trace.endSection();
                }
            }
