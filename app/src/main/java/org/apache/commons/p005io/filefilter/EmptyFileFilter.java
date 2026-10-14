            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            
            
    public class EmptyFileFilter extends AbstractFileFilter implements Serializable {
                public static final IOFileFilter EMPTY = new EmptyFileFilter();
                public static final IOFileFilter NOT_EMPTY = new NotFileFilter(EMPTY);

        protected EmptyFileFilter() {
                }

                @Override 
        public boolean accept(File file) {
            if (!file.isDirectory()) {
                        return file.length() == 0;
                    }
            File[] files = file.listFiles();
                    return files == null || files.length == 0;
                }
            }
