            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            
    public class PathFileComparator extends AbstractFileComparator implements Serializable {
                private final IOCase caseSensitivity;
                public static final Comparator<File> PATH_COMPARATOR = new PathFileComparator();
                public static final Comparator<File> PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
                public static final Comparator<File> PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
                public static final Comparator<File> PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
                public static final Comparator<File> PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
                public static final Comparator<File> PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);




        public PathFileComparator() {
            this.caseSensitivity = IOCase.SENSITIVE;
                }

        public PathFileComparator(IOCase caseSensitivity) {
            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                
       public int compare2(File file1, File file2) {
           return this.caseSensitivity.checkCompareTo(file1.getPath(), file2.getPath());
                }

                @Override 
       public String toString() {
           return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
                }
            }
