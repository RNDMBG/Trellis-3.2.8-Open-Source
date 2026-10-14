            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            import java.util.List;
            
            
    public class DirectoryFileComparator extends AbstractFileComparator implements Serializable {
                public static final Comparator<File> DIRECTORY_COMPARATOR = new DirectoryFileComparator();
                public static final Comparator<File> DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);





                
        public int compare2(File file1, File file2) {
            return getType(file1) - getType(file2);
                }

        private int getType(File file) {
                    return file.isDirectory() ? 1 : 2;
                }
            }
