            package org.apache.commons.p005io.input;

            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class CloseShieldInputStream extends ProxyInputStream {
/* 39 */        public CloseShieldInputStream(InputStream in) {
/* 40 */            super(in);
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 49 */        public void close() {
/* 50 */            this.in = new ClosedInputStream();
                }
            }
