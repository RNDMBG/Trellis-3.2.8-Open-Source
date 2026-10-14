            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class DirectoryFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> DIRECTORY_COMPARATOR = new DirectoryFileComparator();
                public static final Comparator<File> DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);





                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 62 */        public int compare2(File file1, File file2) {
/* 63 */            return getType(file1) - getType(file2);
                }

/* 72 */        private int getType(File file) {
                    return file.isDirectory() ? 1 : 2;
                }
            }
