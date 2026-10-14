            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.FilenameUtils;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class ExtensionFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> EXTENSION_COMPARATOR = new ExtensionFileComparator();
                public static final Comparator<File> EXTENSION_REVERSE = new ReverseComparator(EXTENSION_COMPARATOR);
                public static final Comparator<File> EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(EXTENSION_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> EXTENSION_SYSTEM_REVERSE = new ReverseComparator(EXTENSION_SYSTEM_COMPARATOR);




/* 81 */        public ExtensionFileComparator() {
/* 83 */            this.caseSensitivity = IOCase.SENSITIVE;
                }

/* 90 */        public ExtensionFileComparator(IOCase caseSensitivity) {
/* 92 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 106 */       public int compare2(File file1, File file2) {
/* 107 */           String suffix1 = FilenameUtils.getExtension(file1.getName());
/* 108 */           String suffix2 = FilenameUtils.getExtension(file2.getName());
/* 109 */           return this.caseSensitivity.checkCompareTo(suffix1, suffix2);
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 118 */       public String toString() {
/* 119 */           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
