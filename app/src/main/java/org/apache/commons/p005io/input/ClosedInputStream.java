            package org.apache.commons.p005io.input;

            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class ClosedInputStream extends InputStream {
                public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

                @Override // java.io.InputStream
/* 45 */        public int read() {
/* 46 */            return -1;
                }
            }
