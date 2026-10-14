            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class NotFileFilter extends AbstractFileFilter implements Serializable {
                private final IOFileFilter filter;

/* 39 */        public NotFileFilter(IOFileFilter filter) {
/* 41 */            if (filter == null) {
/* 42 */                throw new IllegalArgumentException("The filter must not be null");
                    }
/* 44 */            this.filter = filter;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 54 */        public boolean accept(File file) {
                    return !this.filter.accept(file);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 66 */        public boolean accept(File file, String name) {
                    return !this.filter.accept(file, name);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 76 */        public String toString() {
/* 77 */            return super.toString() + "(" + this.filter.toString() + ")";
                }
            }
