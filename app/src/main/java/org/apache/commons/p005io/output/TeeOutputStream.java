            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class TeeOutputStream extends ProxyOutputStream {
                protected OutputStream branch;

/* 39 */        public TeeOutputStream(OutputStream out, OutputStream branch) {
/* 40 */            super(out);
/* 41 */            this.branch = branch;
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream
/* 50 */        public synchronized void write(byte[] b) throws IOException {
                    try {
                        super.write(b);
/* 52 */                this.branch.write(b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream
/* 63 */        public synchronized void write(byte[] b, int off, int len) throws IOException {
                    try {
                        super.write(b, off, len);
/* 65 */                this.branch.write(b, off, len);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream
/* 74 */        public synchronized void write(int b) throws IOException {
                    try {
                        super.write(b);
/* 76 */                this.branch.write(b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
/* 84 */        public void flush() throws IOException {
/* 85 */            super.flush();
/* 86 */            this.branch.flush();
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 102 */       public void close() throws IOException {
                    try {
/* 103 */               super.close();
/* 105 */               this.branch.close();
                    } catch (Throwable th) {
                        this.branch.close();
                        throw th;
                    }
                }
            }
