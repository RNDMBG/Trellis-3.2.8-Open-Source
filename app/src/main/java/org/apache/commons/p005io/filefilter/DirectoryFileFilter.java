            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();
                public static final IOFileFilter INSTANCE = DIRECTORY;

/* 58 */        protected DirectoryFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 69 */        public boolean accept(File file) {
/* 70 */            return file.isDirectory();
                }
            }
