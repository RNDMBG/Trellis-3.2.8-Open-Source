            package android.support.v4.util;

            import android.util.Log;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class AtomicFile {
                private final File mBackupName;
                private final File mBaseName;

/* 50 */        public AtomicFile(File baseName) {
/* 52 */            this.mBaseName = baseName;
/* 53 */            this.mBackupName = new File(baseName.getPath() + ".bak");
                }

/* 60 */        public File getBaseFile() {
/* 61 */            return this.mBaseName;
                }

/* 67 */        public void delete() {
/* 68 */            this.mBaseName.delete();
/* 69 */            this.mBackupName.delete();
                }

/* 87 */        public FileOutputStream startWrite() throws IOException {
/* 88 */            if (this.mBaseName.exists()) {
/* 89 */                if (!this.mBackupName.exists()) {
/* 90 */                    if (!this.mBaseName.renameTo(this.mBackupName)) {
/* 91 */                        Log.w("AtomicFile", "Couldn't rename file " + this.mBaseName + " to backup file " + this.mBackupName);
                            }
                        } else {
/* 95 */                    this.mBaseName.delete();
                        }
                    }
                    try {
/* 100 */               FileOutputStream str = new FileOutputStream(this.mBaseName);
/* 112 */               return str;
                    } catch (FileNotFoundException e) {
/* 102 */               File parent = this.mBaseName.getParentFile();
/* 103 */               if (!parent.mkdir()) {
/* 104 */                   throw new IOException("Couldn't create directory " + this.mBaseName);
                        }
                        try {
/* 107 */                   FileOutputStream str2 = new FileOutputStream(this.mBaseName);
/* 112 */                   return str2;
                        } catch (FileNotFoundException e2) {
/* 109 */                   throw new IOException("Couldn't create " + this.mBaseName);
                        }
                    }
                }

/* 121 */       public void finishWrite(FileOutputStream str) {
/* 122 */           if (str != null) {
/* 123 */               sync(str);
                        try {
/* 125 */                   str.close();
/* 126 */                   this.mBackupName.delete();
                        } catch (IOException e) {
/* 128 */                   Log.w("AtomicFile", "finishWrite: Got exception:", e);
                        }
                    }
                }

/* 138 */       public void failWrite(FileOutputStream str) {
/* 139 */           if (str != null) {
/* 140 */               sync(str);
                        try {
/* 142 */                   str.close();
/* 143 */                   this.mBaseName.delete();
/* 144 */                   this.mBackupName.renameTo(this.mBaseName);
                        } catch (IOException e) {
/* 146 */                   Log.w("AtomicFile", "failWrite: Got exception:", e);
                        }
                    }
                }

/* 163 */       public FileInputStream openRead() throws FileNotFoundException {
/* 164 */           if (this.mBackupName.exists()) {
/* 165 */               this.mBaseName.delete();
/* 166 */               this.mBackupName.renameTo(this.mBaseName);
                    }
/* 168 */           return new FileInputStream(this.mBaseName);
                }

/* 175 */       public byte[] readFully() throws IOException {
/* 176 */           FileInputStream stream = openRead();
/* 178 */           int pos = 0;
                    try {
/* 180 */               byte[] data = new byte[stream.available()];
                        while (true) {
/* 182 */                   int amt = stream.read(data, pos, data.length - pos);
/* 185 */                   if (amt > 0) {
/* 190 */                       pos += amt;
/* 191 */                       int avail = stream.available();
/* 192 */                       if (avail > data.length - pos) {
/* 193 */                           byte[] newData = new byte[pos + avail];
/* 194 */                           System.arraycopy(data, 0, newData, 0, pos);
/* 195 */                           data = newData;
                                }
                            } else {
/* 199 */                       stream.close();
                                return data;
                            }
                        }
                    } catch (Throwable th) {
                        stream.close();
                        throw th;
                    }
                }

/* 204 */       static boolean sync(FileOutputStream stream) {
/* 205 */           if (stream != null) {
                        try {
/* 206 */                   stream.getFD().sync();
                        } catch (IOException e) {
/* 211 */                   return false;
                        }
                    }
/* 208 */           return true;
                }
            }
