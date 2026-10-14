            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    class ReverseComparator extends AbstractFileComparator implements Serializable {
                private final Comparator<File> delegate;


/* 38 */        public ReverseComparator(Comparator<File> delegate) {
/* 40 */            if (delegate == null) {
/* 41 */                throw new IllegalArgumentException("Delegate comparator is missing");
                    }
/* 43 */            this.delegate = delegate;
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 54 */        public int compare2(File file1, File file2) {
/* 55 */            return this.delegate.compare(file2, file1);
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 64 */        public String toString() {
/* 65 */            return super.toString() + "[" + this.delegate.toString() + "]";
                }
            }
