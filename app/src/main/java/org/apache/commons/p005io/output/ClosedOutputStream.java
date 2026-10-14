            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class ClosedOutputStream extends OutputStream {
                public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();

                @Override // java.io.OutputStream
/* 47 */        public void write(int b) throws IOException {
/* 48 */            throw new IOException("write(" + b + ") failed: stream is closed");
                }
            }
