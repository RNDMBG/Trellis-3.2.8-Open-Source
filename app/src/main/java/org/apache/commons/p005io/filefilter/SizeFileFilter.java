            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class SizeFileFilter extends AbstractFileFilter implements Serializable {
                private final boolean acceptLarger;
                private final long size;

/* 57 */        public SizeFileFilter(long size) {
/* 58 */            this(size, true);
                }

/* 69 */        public SizeFileFilter(long size, boolean acceptLarger) {
                    if (size < 0) {
/* 72 */                throw new IllegalArgumentException("The size must be non-negative");
                    }
/* 74 */            this.size = size;
/* 75 */            this.acceptLarger = acceptLarger;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 91 */        public boolean accept(File file) {
                    boolean smaller = file.length() < this.size;
/* 93 */            if (this.acceptLarger) {
                        return !smaller;
                    }
                    return smaller;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 102 */       public String toString() {
/* 103 */           String condition = this.acceptLarger ? ">=" : "<";
/* 104 */           return super.toString() + "(" + condition + this.size + ")";
                }
            }
