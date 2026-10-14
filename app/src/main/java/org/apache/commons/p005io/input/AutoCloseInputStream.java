            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class AutoCloseInputStream extends ProxyInputStream {
/* 44 */        public AutoCloseInputStream(InputStream in) {
/* 45 */            super(in);
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 62 */        public void close() throws IOException {
/* 63 */            this.in.close();
/* 64 */            this.in = new ClosedInputStream();
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream
/* 75 */        protected void afterRead(int n) throws IOException {
/* 76 */            if (n == -1) {
/* 77 */                close();
                    }
                }

/* 88 */        protected void finalize() throws Throwable {
/* 89 */            close();
/* 90 */            super.finalize();
                }
            }
