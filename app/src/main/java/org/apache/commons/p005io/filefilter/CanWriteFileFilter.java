            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class CanWriteFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();
                public static final IOFileFilter CANNOT_WRITE = new NotFileFilter(CAN_WRITE);

/* 65 */        protected CanWriteFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 77 */        public boolean accept(File file) {
/* 78 */            return file.canWrite();
                }
            }
