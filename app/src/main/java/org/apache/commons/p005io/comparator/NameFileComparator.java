            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class NameFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> NAME_COMPARATOR = new NameFileComparator();
                public static final Comparator<File> NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
                public static final Comparator<File> NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);




/* 77 */        public NameFileComparator() {
/* 79 */            this.caseSensitivity = IOCase.SENSITIVE;
                }

/* 86 */        public NameFileComparator(IOCase caseSensitivity) {
/* 88 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 101 */       public int compare2(File file1, File file2) {
/* 102 */           return this.caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 111 */       public String toString() {
/* 112 */           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
