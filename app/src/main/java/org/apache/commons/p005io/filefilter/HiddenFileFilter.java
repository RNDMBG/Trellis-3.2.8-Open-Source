            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 60 */    public class HiddenFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter HIDDEN = new HiddenFileFilter();
                public static final IOFileFilter VISIBLE = new NotFileFilter(HIDDEN);

/* 61 */        protected HiddenFileFilter() {
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 73 */        public boolean accept(File file) {
/* 74 */            return file.isHidden();
                }
            }
