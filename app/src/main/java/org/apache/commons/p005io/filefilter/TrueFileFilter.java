            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class TrueFileFilter implements IOFileFilter, Serializable {
                public static final IOFileFilter TRUE = new TrueFileFilter();
                public static final IOFileFilter INSTANCE = TRUE;

/* 46 */        protected TrueFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 56 */        public boolean accept(File file) {
/* 57 */            return true;
                }

                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 67 */        public boolean accept(File dir, String name) {
/* 68 */            return true;
                }
            }
