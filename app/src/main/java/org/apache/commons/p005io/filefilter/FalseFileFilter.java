            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class FalseFileFilter implements IOFileFilter, Serializable {
                public static final IOFileFilter FALSE = new FalseFileFilter();
                public static final IOFileFilter INSTANCE = FALSE;

/* 47 */        protected FalseFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 57 */        public boolean accept(File file) {
/* 58 */            return false;
                }

                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 68 */        public boolean accept(File dir, String name) {
/* 69 */            return false;
                }
            }
