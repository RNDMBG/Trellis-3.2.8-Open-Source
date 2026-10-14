            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class BrokenInputStream extends InputStream {
                private final IOException exception;

/* 42 */        public BrokenInputStream(IOException exception) {
/* 44 */            this.exception = exception;
                }

/* 50 */        public BrokenInputStream() {
/* 51 */            this(new IOException("Broken input stream"));
                }

                @Override // java.io.InputStream
/* 61 */        public int read() throws IOException {
/* 62 */            throw this.exception;
                }

                @Override // java.io.InputStream
/* 72 */        public int available() throws IOException {
/* 73 */            throw this.exception;
                }

                @Override // java.io.InputStream
/* 84 */        public long skip(long n) throws IOException {
/* 85 */            throw this.exception;
                }

                @Override // java.io.InputStream
/* 94 */        public void reset() throws IOException {
/* 95 */            throw this.exception;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 104 */       public void close() throws IOException {
/* 105 */           throw this.exception;
                }
            }
