            package org.apache.commons.p005io.output;

            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.Writer;
            import java.nio.charset.Charset;
            import org.apache.commons.p005io.Charsets;
            import org.apache.commons.p005io.FileUtils;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 72 */    public class LockableFileWriter extends Writer {
                private static final String LCK = ".lck";
                private final File lockFile;
                private final Writer out;

/* 73 */        public LockableFileWriter(String fileName) throws IOException {
/* 74 */            this(fileName, false, (String) null);
                }

/* 85 */        public LockableFileWriter(String fileName, boolean append) throws IOException {
/* 86 */            this(fileName, append, (String) null);
                }

/* 98 */        public LockableFileWriter(String fileName, boolean append, String lockDir) throws IOException {
/* 99 */            this(new File(fileName), append, lockDir);
                }

/* 110 */       public LockableFileWriter(File file) throws IOException {
/* 111 */           this(file, false, (String) null);
                }

/* 122 */       public LockableFileWriter(File file, boolean append) throws IOException {
/* 123 */           this(file, append, (String) null);
                }

/* 135 */       public LockableFileWriter(File file, boolean append, String lockDir) throws IOException {
/* 136 */           this(file, Charset.defaultCharset(), append, lockDir);
                }

/* 148 */       public LockableFileWriter(File file, Charset encoding) throws IOException {
/* 149 */           this(file, encoding, false, (String) null);
                }

/* 163 */       public LockableFileWriter(File file, String encoding) throws IOException {
/* 164 */           this(file, encoding, false, (String) null);
                }

/* 179 */       public LockableFileWriter(File file, Charset encoding, boolean append, String lockDir) throws IOException {
/* 182 */           File file2 = file.getAbsoluteFile();
/* 183 */           if (file2.getParentFile() != null) {
/* 184 */               FileUtils.forceMkdir(file2.getParentFile());
                    }
/* 186 */           if (file2.isDirectory()) {
/* 187 */               throw new IOException("File specified is a directory");
                    }
/* 194 */           File lockDirFile = new File(lockDir == null ? System.getProperty("java.io.tmpdir") : lockDir);
/* 195 */           FileUtils.forceMkdir(lockDirFile);
/* 196 */           testLockDir(lockDirFile);
/* 197 */           this.lockFile = new File(lockDirFile, file2.getName() + LCK);
/* 200 */           createLock();
/* 203 */           this.out = initWriter(file2, encoding, append);
                }

/* 220 */       public LockableFileWriter(File file, String encoding, boolean append, String lockDir) throws IOException {
/* 221 */           this(file, Charsets.toCharset(encoding), append, lockDir);
                }

/* 232 */       private void testLockDir(File lockDir) throws IOException {
/* 233 */           if (!lockDir.exists()) {
/* 234 */               throw new IOException("Could not find lockDir: " + lockDir.getAbsolutePath());
                    }
/* 237 */           if (!lockDir.canWrite()) {
/* 238 */               throw new IOException("Could not write to lockDir: " + lockDir.getAbsolutePath());
                    }
                }

/* 248 */       private void createLock() throws IOException {
                    synchronized (LockableFileWriter.class) {
                        try {
/* 250 */                   if (!this.lockFile.createNewFile()) {
/* 251 */                       throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
                            }
/* 254 */                   this.lockFile.deleteOnExit();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 268 */       private Writer initWriter(File file, Charset encoding, boolean append) throws IOException {
/* 269 */           boolean fileExistedAlready = file.exists();
/* 270 */           OutputStream stream = null;
                    try {
/* 273 */               OutputStream stream2 = new FileOutputStream(file.getAbsolutePath(), append);
                        try {
/* 274 */                   Writer writer = new OutputStreamWriter(stream2, Charsets.toCharset(encoding));
/* 292 */                   return writer;
                        } catch (IOException e) {
/* 275 */                   ex = e;
                            stream = stream2;
/* 276 */                   IOUtils.closeQuietly((Writer) null);
/* 277 */                   IOUtils.closeQuietly(stream);
/* 278 */                   FileUtils.deleteQuietly(this.lockFile);
/* 279 */                   if (!fileExistedAlready) {
/* 280 */                       FileUtils.deleteQuietly(file);
                            }
/* 282 */                   throw ex;
                        } catch (RuntimeException e2) {
/* 283 */                   ex = e2;
                            stream = stream2;
/* 284 */                   IOUtils.closeQuietly((Writer) null);
/* 285 */                   IOUtils.closeQuietly(stream);
/* 286 */                   FileUtils.deleteQuietly(this.lockFile);
/* 287 */                   if (!fileExistedAlready) {
/* 288 */                       FileUtils.deleteQuietly(file);
                            }
/* 290 */                   throw ex;
                        }
                    } catch (IOException e3) {
/* 275 */               ex = e3;
                    } catch (RuntimeException e4) {
/* 283 */               ex = e4;
                    }
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 303 */       public void close() throws IOException {
                    try {
/* 304 */               this.out.close();
/* 306 */               this.lockFile.delete();
                    } catch (Throwable th) {
                        this.lockFile.delete();
                        throw th;
                    }
                }

                @Override // java.io.Writer
/* 317 */       public void write(int idx) throws IOException {
/* 318 */           this.out.write(idx);
                }

                @Override // java.io.Writer
/* 327 */       public void write(char[] chr) throws IOException {
/* 328 */           this.out.write(chr);
                }

                @Override // java.io.Writer
/* 339 */       public void write(char[] chr, int st, int end) throws IOException {
/* 340 */           this.out.write(chr, st, end);
                }

                @Override // java.io.Writer
/* 349 */       public void write(String str) throws IOException {
/* 350 */           this.out.write(str);
                }

                @Override // java.io.Writer
/* 361 */       public void write(String str, int st, int end) throws IOException {
/* 362 */           this.out.write(str, st, end);
                }

                @Override // java.io.Writer, java.io.Flushable
/* 370 */       public void flush() throws IOException {
/* 371 */           this.out.flush();
                }
            }
