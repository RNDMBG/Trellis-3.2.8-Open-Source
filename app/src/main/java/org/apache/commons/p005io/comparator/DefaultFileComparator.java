            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class DefaultFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> DEFAULT_COMPARATOR = new DefaultFileComparator();
                public static final Comparator<File> DEFAULT_REVERSE = new ReverseComparator(DEFAULT_COMPARATOR);





                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 63 */        public int compare2(File file1, File file2) {
/* 64 */            return file1.compareTo(file2);
                }
            }
