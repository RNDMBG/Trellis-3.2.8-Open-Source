            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public abstract class AbstractFileFilter implements IOFileFilter {
                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 39 */        public boolean accept(File file) {
/* 40 */            return accept(file.getParentFile(), file.getName());
                }

                @Override // org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 50 */        public boolean accept(File dir, String name) {
/* 51 */            return accept(new File(dir, name));
                }

/* 60 */        public String toString() {
/* 61 */            return getClass().getSimpleName();
                }
            }
