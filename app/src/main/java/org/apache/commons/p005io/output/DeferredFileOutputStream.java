            package org.apache.commons.p005io.output;

            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 99 */    public class DeferredFileOutputStream extends ThresholdingOutputStream {
                private boolean closed;
                private OutputStream currentOutputStream;
                private final File directory;
                private ByteArrayOutputStream memoryOutputStream;
                private File outputFile;
                private final String prefix;
                private final String suffix;

/* 100 */       public DeferredFileOutputStream(int threshold, File outputFile) {
/* 101 */           this(threshold, outputFile, null, null, null);
                }

/* 117 */       public DeferredFileOutputStream(int threshold, String prefix, String suffix, File directory) {
/* 118 */           this(threshold, null, prefix, suffix, directory);
/* 119 */           if (prefix == null) {
/* 120 */               throw new IllegalArgumentException("Temporary file prefix is missing");
                    }
                }

/* 134 */       private DeferredFileOutputStream(int threshold, File outputFile, String prefix, String suffix, File directory) {
/* 135 */           super(threshold);
/* 87 */            this.closed = false;
/* 136 */           this.outputFile = outputFile;
/* 138 */           this.memoryOutputStream = new ByteArrayOutputStream();
/* 139 */           this.currentOutputStream = this.memoryOutputStream;
/* 140 */           this.prefix = prefix;
/* 141 */           this.suffix = suffix;
/* 142 */           this.directory = directory;
                }

                @Override // org.apache.commons.p005io.output.ThresholdingOutputStream
/* 159 */       protected OutputStream getStream() throws IOException {
/* 160 */           return this.currentOutputStream;
                }

                @Override // org.apache.commons.p005io.output.ThresholdingOutputStream
/* 174 */       protected void thresholdReached() throws IOException {
/* 175 */           if (this.prefix != null) {
/* 176 */               this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
                    }
/* 178 */           FileOutputStream fos = new FileOutputStream(this.outputFile);
/* 179 */           this.memoryOutputStream.writeTo(fos);
/* 180 */           this.currentOutputStream = fos;
/* 181 */           this.memoryOutputStream = null;
                }

/* 196 */       public boolean isInMemory() {
                    return !isThresholdExceeded();
                }

/* 210 */       public byte[] getData() {
/* 211 */           if (this.memoryOutputStream != null) {
/* 213 */               return this.memoryOutputStream.toByteArray();
                    }
                    return null;
                }

/* 234 */       public File getFile() {
/* 235 */           return this.outputFile;
                }

                @Override // org.apache.commons.p005io.output.ThresholdingOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 246 */       public void close() throws IOException {
/* 247 */           super.close();
/* 248 */           this.closed = true;
                }

/* 263 */       public void writeTo(OutputStream out) throws IOException {
/* 264 */           if (!this.closed) {
/* 266 */               throw new IOException("Stream not closed");
                    }
/* 269 */           if (isInMemory()) {
/* 271 */               this.memoryOutputStream.writeTo(out);
/* 282 */               return;
                    }
/* 275 */           FileInputStream fis = new FileInputStream(this.outputFile);
                    try {
/* 277 */               IOUtils.copy(fis, out);
/* 279 */               IOUtils.closeQuietly((InputStream) fis);
                    } catch (Throwable th) {
                        IOUtils.closeQuietly((InputStream) fis);
                        throw th;
                    }
                }
            }
