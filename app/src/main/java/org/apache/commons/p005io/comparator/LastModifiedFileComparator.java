            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class LastModifiedFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
                public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);





                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 67 */        public int compare2(File file1, File file2) {
/* 68 */            long result = file1.lastModified() - file2.lastModified();
                    if (result < 0) {
/* 70 */                return -1;
                    }
                    if (result > 0) {
/* 72 */                return 1;
                    }
/* 74 */            return 0;
                }
            }
