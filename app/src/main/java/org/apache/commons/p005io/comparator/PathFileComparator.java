            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class PathFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> PATH_COMPARATOR = new PathFileComparator();
                public static final Comparator<File> PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
                public static final Comparator<File> PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);




/* 77 */        public PathFileComparator() {
/* 79 */            this.caseSensitivity = IOCase.SENSITIVE;
                }

/* 86 */        public PathFileComparator(IOCase caseSensitivity) {
/* 88 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 102 */       public int compare2(File file1, File file2) {
/* 103 */           return this.caseSensitivity.checkCompareTo(file1.getPath(), file2.getPath());
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 112 */       public String toString() {
/* 113 */           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
