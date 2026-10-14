            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            
            
    public class DefaultFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> DEFAULT_COMPARATOR = new DefaultFileComparator();
                public static final Comparator<File> DEFAULT_REVERSE = new ReverseComparator(DEFAULT_COMPARATOR);





                
        public int compare2(File file1, File file2) {
            return file1.compareTo(file2);
                }
            }
