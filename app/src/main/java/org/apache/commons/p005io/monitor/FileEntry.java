            package org.apache.commons.p005io.monitor;

            import java.io.File;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 59 */    public class FileEntry implements Serializable {
                static final FileEntry[] EMPTY_ENTRIES = new FileEntry[0];
                private FileEntry[] children;
                private boolean directory;
                private boolean exists;
                private final File file;
                private long lastModified;
                private long length;
                private String name;
                private final FileEntry parent;

/* 60 */        public FileEntry(File file) {
/* 61 */            this((FileEntry) null, file);
                }

/* 69 */        public FileEntry(FileEntry parent, File file) {
/* 71 */            if (file == null) {
/* 72 */                throw new IllegalArgumentException("File is missing");
                    }
/* 74 */            this.file = file;
/* 75 */            this.parent = parent;
/* 76 */            this.name = file.getName();
                }

/* 95 */        public boolean refresh(File file) {
                    long length = 0;
/* 96 */            boolean origExists = this.exists;
/* 97 */            long origLastModified = this.lastModified;
/* 98 */            boolean origDirectory = this.directory;
/* 99 */            long origLength = this.length;
/* 102 */           this.name = file.getName();
/* 103 */           this.exists = file.exists();
/* 104 */           this.directory = this.exists ? file.isDirectory() : false;
/* 105 */           this.lastModified = this.exists ? file.lastModified() : 0L;
/* 106 */           if (this.exists && !this.directory) {
                        length = file.length();
                    }
                    this.length = length;
                    return (this.exists == origExists && this.lastModified == origLastModified && this.directory == origDirectory && this.length == origLength) ? false : true;
                }

/* 124 */       public FileEntry newChildInstance(File file) {
/* 125 */           return new FileEntry(this, file);
                }

/* 133 */       public FileEntry getParent() {
/* 134 */           return this.parent;
                }

/* 142 */       public int getLevel() {
/* 143 */           if (this.parent == null) {
                        return 0;
                    }
                    return this.parent.getLevel() + 1;
                }

/* 153 */       public FileEntry[] getChildren() {
                    return this.children != null ? this.children : EMPTY_ENTRIES;
                }

/* 162 */       public void setChildren(FileEntry[] children) {
/* 163 */           this.children = children;
                }

/* 171 */       public File getFile() {
/* 172 */           return this.file;
                }

/* 180 */       public String getName() {
/* 181 */           return this.name;
                }

/* 189 */       public void setName(String name) {
/* 190 */           this.name = name;
                }

/* 199 */       public long getLastModified() {
/* 200 */           return this.lastModified;
                }

/* 209 */       public void setLastModified(long lastModified) {
/* 210 */           this.lastModified = lastModified;
                }

/* 218 */       public long getLength() {
/* 219 */           return this.length;
                }

/* 227 */       public void setLength(long length) {
/* 228 */           this.length = length;
                }

/* 237 */       public boolean isExists() {
/* 238 */           return this.exists;
                }

/* 247 */       public void setExists(boolean exists) {
/* 248 */           this.exists = exists;
                }

/* 256 */       public boolean isDirectory() {
/* 257 */           return this.directory;
                }

/* 265 */       public void setDirectory(boolean directory) {
/* 266 */           this.directory = directory;
                }
            }
