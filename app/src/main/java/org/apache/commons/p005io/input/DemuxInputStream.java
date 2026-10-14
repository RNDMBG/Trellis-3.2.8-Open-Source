            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class DemuxInputStream extends InputStream {
                private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal<>();

/* 40 */        public InputStream bindStream(InputStream input) {
/* 41 */            InputStream oldValue = this.m_streams.get();
/* 42 */            this.m_streams.set(input);
/* 43 */            return oldValue;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 54 */        public void close() throws IOException {
/* 55 */            InputStream input = this.m_streams.get();
/* 56 */            if (input != null) {
/* 58 */                input.close();
                    }
                }

                @Override // java.io.InputStream
/* 71 */        public int read() throws IOException {
/* 72 */            InputStream input = this.m_streams.get();
/* 73 */            if (input != null) {
/* 75 */                return input.read();
                    }
                    return -1;
                }
            }
