            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Date;
            import org.apache.commons.p005io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public class AgeFileFilter extends AbstractFileFilter implements Serializable {
                private final boolean acceptOlder;
                private final long cutoff;

/* 64 */        public AgeFileFilter(long cutoff) {
/* 65 */            this(cutoff, true);
                }

/* 75 */        public AgeFileFilter(long cutoff, boolean acceptOlder) {
/* 77 */            this.acceptOlder = acceptOlder;
/* 78 */            this.cutoff = cutoff;
                }

/* 87 */        public AgeFileFilter(Date cutoffDate) {
/* 88 */            this(cutoffDate, true);
                }

/* 99 */        public AgeFileFilter(Date cutoffDate, boolean acceptOlder) {
/* 100 */           this(cutoffDate.getTime(), acceptOlder);
                }

/* 110 */       public AgeFileFilter(File cutoffReference) {
/* 111 */           this(cutoffReference, true);
                }

/* 124 */       public AgeFileFilter(File cutoffReference, boolean acceptOlder) {
/* 125 */           this(cutoffReference.lastModified(), acceptOlder);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 142 */       public boolean accept(File file) {
/* 143 */           boolean newer = FileUtils.isFileNewer(file, this.cutoff);
/* 144 */           if (this.acceptOlder) {
                        return !newer;
                    }
                    return newer;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 153 */       public String toString() {
/* 154 */           String condition = this.acceptOlder ? "<=" : ">";
/* 155 */           return super.toString() + "(" + condition + this.cutoff + ")";
                }
            }
