            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            
    public class SizeFileFilter extends AbstractFileFilter implements Serializable {
                private final boolean acceptLarger;
                private final long size;

        public SizeFileFilter(long size) {
            this(size, true);
                }

        public SizeFileFilter(long size, boolean acceptLarger) {
                    if (size < 0) {
                throw new IllegalArgumentException("The size must be non-negative");
                    }
            this.size = size;
            this.acceptLarger = acceptLarger;
                }

                @Override 
        public boolean accept(File file) {
                    boolean smaller = file.length() < this.size;
            if (this.acceptLarger) {
                        return !smaller;
                    }
                    return smaller;
                }

                @Override 
       public String toString() {
           String condition = this.acceptLarger ? ">=" : "<";
           return super.toString() + "(" + condition + this.size + ")";
                }
            }
