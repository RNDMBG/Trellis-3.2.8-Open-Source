            package org.apache.commons.p005io;

            import java.io.File;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class FileDeleteStrategy {
                private final String name;
                public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
                public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();

/* 55 */        protected FileDeleteStrategy(String name) {
/* 57 */            this.name = name;
                }

/* 71 */        public boolean deleteQuietly(File fileToDelete) {
/* 72 */            if (fileToDelete == null || !fileToDelete.exists()) {
/* 73 */                return true;
                    }
                    try {
/* 76 */                return doDelete(fileToDelete);
                    } catch (IOException e) {
/* 78 */                return false;
                    }
                }

/* 92 */        public void delete(File fileToDelete) throws IOException {
/* 93 */            if (fileToDelete.exists() && !doDelete(fileToDelete)) {
/* 94 */                throw new IOException("Deletion failed: " + fileToDelete);
                    }
                }

/* 114 */       protected boolean doDelete(File fileToDelete) throws IOException {
/* 115 */           return fileToDelete.delete();
                }

/* 125 */       public String toString() {
/* 126 */           return "FileDeleteStrategy[" + this.name + "]";
                }

/* 134 */       static class ForceFileDeleteStrategy extends FileDeleteStrategy {
/* 135 */           ForceFileDeleteStrategy() {
/* 136 */               super("Force");
                    }

                    @Override // org.apache.commons.p005io.FileDeleteStrategy
/* 151 */           protected boolean doDelete(File fileToDelete) throws IOException {
/* 152 */               FileUtils.forceDelete(fileToDelete);
/* 153 */               return true;
                    }
                }
            }
