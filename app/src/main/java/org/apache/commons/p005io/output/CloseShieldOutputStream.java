            package org.apache.commons.p005io.output;

            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class CloseShieldOutputStream extends ProxyOutputStream {
/* 39 */        public CloseShieldOutputStream(OutputStream out) {
/* 40 */            super(out);
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 49 */        public void close() {
/* 50 */            this.out = new ClosedOutputStream();
                }
            }
