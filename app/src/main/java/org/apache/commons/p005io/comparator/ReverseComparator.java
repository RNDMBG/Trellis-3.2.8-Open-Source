            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.Comparator;
            
            
    class ReverseComparator extends AbstractFileComparator implements Serializable {
                private final Comparator<File> delegate;


        public ReverseComparator(Comparator<File> delegate) {
            if (delegate == null) {
                throw new IllegalArgumentException("Delegate comparator is missing");
                    }
            this.delegate = delegate;
                }

                
        public int compare2(File file1, File file2) {
            return this.delegate.compare(file2, file1);
                }

                @Override 
        public String toString() {
            return super.toString() + "[" + this.delegate.toString() + "]";
                }
            }
