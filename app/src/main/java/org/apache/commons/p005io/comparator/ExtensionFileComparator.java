            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.FilenameUtils;
            import org.apache.commons.p005io.IOCase;
            
            
    public class ExtensionFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> EXTENSION_COMPARATOR = new ExtensionFileComparator();
                public static final Comparator<File> EXTENSION_REVERSE = new ReverseComparator(EXTENSION_COMPARATOR);
                public static final Comparator<File> EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(EXTENSION_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> EXTENSION_SYSTEM_REVERSE = new ReverseComparator(EXTENSION_SYSTEM_COMPARATOR);




        public ExtensionFileComparator() {
            this.caseSensitivity = IOCase.SENSITIVE;
                }

        public ExtensionFileComparator(IOCase caseSensitivity) {
            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                
       public int compare2(File file1, File file2) {
           String suffix1 = FilenameUtils.getExtension(file1.getName());
           String suffix2 = FilenameUtils.getExtension(file2.getName());
           return this.caseSensitivity.checkCompareTo(suffix1, suffix2);
                }

                @Override 
       public String toString() {
           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
