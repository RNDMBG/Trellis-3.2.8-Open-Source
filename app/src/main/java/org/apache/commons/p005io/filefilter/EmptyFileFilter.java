            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public class EmptyFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter EMPTY = new EmptyFileFilter();
                public static final IOFileFilter NOT_EMPTY = new NotFileFilter(EMPTY);

/* 64 */        protected EmptyFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 76 */        public boolean accept(File file) {
/* 77 */            if (!file.isDirectory()) {
                        return file.length() == 0;
                    }
/* 78 */            File[] files = file.listFiles();
                    return files == null || files.length == 0;
                }
            }
