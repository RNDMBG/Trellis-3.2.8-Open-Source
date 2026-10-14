            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            
    public class NameFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> NAME_COMPARATOR = new NameFileComparator();
                public static final Comparator<File> NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
                public static final Comparator<File> NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);




        public NameFileComparator() {
            this.caseSensitivity = IOCase.SENSITIVE;
                }

        public NameFileComparator(IOCase caseSensitivity) {
            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                
       public int compare2(File file1, File file2) {
           return this.caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
                }

                @Override 
       public String toString() {
           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
