            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class NullOutputStream extends OutputStream {
                public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

                @Override // java.io.OutputStream
/* 45 */        public void write(byte[] b, int off, int len) {
                }

                @Override // java.io.OutputStream
/* 54 */        public void write(int b) {
                }

                @Override // java.io.OutputStream
/* 64 */        public void write(byte[] b) throws IOException {
                }
            }
