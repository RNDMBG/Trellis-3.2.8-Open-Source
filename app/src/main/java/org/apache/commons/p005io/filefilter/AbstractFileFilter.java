            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            
            
    public abstract class AbstractFileFilter implements IOFileFilter {
                @Override 
        public boolean accept(File file) {
            return accept(file.getParentFile(), file.getName());
                }

                @Override 
        public boolean accept(File dir, String name) {
            return accept(new File(dir, name));
                }

        public String toString() {
            return getClass().getSimpleName();
                }
            }
