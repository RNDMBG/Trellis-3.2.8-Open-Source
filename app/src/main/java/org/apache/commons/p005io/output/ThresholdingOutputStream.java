            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 72 */    public abstract class ThresholdingOutputStream extends OutputStream {
                private final int threshold;
                private boolean thresholdExceeded;
                private long written;

                protected abstract OutputStream getStream() throws IOException;

                protected abstract void thresholdReached() throws IOException;

/* 73 */        public ThresholdingOutputStream(int threshold) {
/* 75 */            this.threshold = threshold;
                }

                @Override // java.io.OutputStream
/* 91 */        public void write(int b) throws IOException {
/* 92 */            checkThreshold(1);
/* 93 */            getStream().write(b);
/* 94 */            this.written++;
                }

                @Override // java.io.OutputStream
/* 108 */       public void write(byte[] b) throws IOException {
/* 109 */           checkThreshold(b.length);
/* 110 */           getStream().write(b);
/* 111 */           this.written += (long) b.length;
                }

                @Override // java.io.OutputStream
/* 127 */       public void write(byte[] b, int off, int len) throws IOException {
/* 128 */           checkThreshold(len);
/* 129 */           getStream().write(b, off, len);
/* 130 */           this.written += (long) len;
                }

                @Override // java.io.OutputStream, java.io.Flushable
/* 142 */       public void flush() throws IOException {
/* 143 */           getStream().flush();
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 157 */       public void close() throws IOException {
                    try {
/* 158 */               flush();
                    } catch (IOException e) {
                    }
/* 164 */           getStream().close();
                }

/* 177 */       public int getThreshold() {
/* 178 */           return this.threshold;
                }

/* 188 */       public long getByteCount() {
/* 189 */           return this.written;
                }

/* 201 */       public boolean isThresholdExceeded() {
                    return this.written > ((long) this.threshold);
                }

/* 220 */       protected void checkThreshold(int count) throws IOException {
/* 221 */           if (!this.thresholdExceeded && this.written + ((long) count) > this.threshold) {
/* 223 */               this.thresholdExceeded = true;
/* 224 */               thresholdReached();
                    }
                }

/* 233 */       protected void resetByteCount() {
/* 234 */           this.thresholdExceeded = false;
/* 235 */           this.written = 0L;
                }
            }
