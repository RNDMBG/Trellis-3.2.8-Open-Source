            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class SizeFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> SIZE_COMPARATOR = new SizeFileComparator();
                public static final Comparator<File> SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
                public static final Comparator<File> SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
                public static final Comparator<File> SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
                private final boolean sumDirectoryContents;




/* 77 */        public SizeFileComparator() {
/* 79 */            this.sumDirectoryContents = false;
                }

/* 92 */        public SizeFileComparator(boolean sumDirectoryContents) {
/* 94 */            this.sumDirectoryContents = sumDirectoryContents;
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 108 */       public int compare2(File file1, File file2) {
                    long size1;
                    long size2;
/* 110 */           if (file1.isDirectory()) {
/* 111 */               size1 = (this.sumDirectoryContents && file1.exists()) ? FileUtils.sizeOfDirectory(file1) : 0L;
                    } else {
/* 113 */               size1 = file1.length();
                    }
/* 116 */           if (file2.isDirectory()) {
/* 117 */               size2 = (this.sumDirectoryContents && file2.exists()) ? FileUtils.sizeOfDirectory(file2) : 0L;
                    } else {
/* 119 */               size2 = file2.length();
                    }
/* 121 */           long result = size1 - size2;
                    if (result < 0) {
/* 123 */               return -1;
                    }
                    if (result > 0) {
/* 125 */               return 1;
                    }
/* 127 */           return 0;
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 137 */       public String toString() {
/* 138 */           return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
                }
            }
