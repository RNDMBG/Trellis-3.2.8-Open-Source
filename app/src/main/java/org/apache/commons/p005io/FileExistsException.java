            package org.apache.commons.p005io;

            import java.io.File;
            import java.io.IOException;
            
            
    public class FileExistsException extends IOException {
                private static final long serialVersionUID = 1;

        public FileExistsException() {
                }

        public FileExistsException(String message) {
            super(message);
                }

        public FileExistsException(File file) {
            super("File " + file + " exists");
                }
            }
