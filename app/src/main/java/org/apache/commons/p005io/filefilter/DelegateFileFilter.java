            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.FileFilter;
            import java.io.FilenameFilter;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class DelegateFileFilter extends AbstractFileFilter implements Serializable {
                private final FileFilter fileFilter;
                private final FilenameFilter filenameFilter;

/* 44 */        public DelegateFileFilter(FilenameFilter filter) {
/* 46 */            if (filter == null) {
/* 47 */                throw new IllegalArgumentException("The FilenameFilter must not be null");
                    }
/* 49 */            this.filenameFilter = filter;
/* 50 */            this.fileFilter = null;
                }

/* 57 */        public DelegateFileFilter(FileFilter filter) {
/* 59 */            if (filter == null) {
/* 60 */                throw new IllegalArgumentException("The FileFilter must not be null");
                    }
/* 62 */            this.fileFilter = filter;
/* 63 */            this.filenameFilter = null;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 73 */        public boolean accept(File file) {
                    return this.fileFilter != null ? this.fileFilter.accept(file) : super.accept(file);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 89 */        public boolean accept(File dir, String name) {
                    return this.filenameFilter != null ? this.filenameFilter.accept(dir, name) : super.accept(dir, name);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 103 */       public String toString() {
/* 104 */           String delegate = this.fileFilter != null ? this.fileFilter.toString() : this.filenameFilter.toString();
/* 105 */           return super.toString() + "(" + delegate + ")";
                }
            }
