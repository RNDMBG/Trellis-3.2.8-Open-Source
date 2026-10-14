            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class BrokenOutputStream extends OutputStream {
                private final IOException exception;

/* 42 */        public BrokenOutputStream(IOException exception) {
/* 44 */            this.exception = exception;
                }

/* 50 */        public BrokenOutputStream() {
/* 51 */            this(new IOException("Broken output stream"));
                }

                @Override // java.io.OutputStream
/* 61 */        public void write(int b) throws IOException {
/* 62 */            throw this.exception;
                }

                @Override // java.io.OutputStream, java.io.Flushable
/* 71 */        public void flush() throws IOException {
/* 72 */            throw this.exception;
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 81 */        public void close() throws IOException {
/* 82 */            throw this.exception;
                }
            }
