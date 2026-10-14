            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 76 */    public class CanReadFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter CAN_READ = new CanReadFileFilter();
                public static final IOFileFilter CANNOT_READ = new NotFileFilter(CAN_READ);
                public static final IOFileFilter READ_ONLY = new AndFileFilter(CAN_READ, CanWriteFileFilter.CANNOT_WRITE);

/* 77 */        protected CanReadFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 89 */        public boolean accept(File file) {
/* 90 */            return file.canRead();
                }
            }
