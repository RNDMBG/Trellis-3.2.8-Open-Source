            package org.apache.commons.p005io;

            import java.io.File;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class FileExistsException extends IOException {
                private static final long serialVersionUID = 1;

/* 38 */        public FileExistsException() {
                }

/* 47 */        public FileExistsException(String message) {
/* 48 */            super(message);
                }

/* 56 */        public FileExistsException(File file) {
/* 57 */            super("File " + file + " exists");
                }
            }
