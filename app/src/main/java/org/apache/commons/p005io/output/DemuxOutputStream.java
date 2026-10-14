            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class DemuxOutputStream extends OutputStream {
                private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal<>();

/* 40 */        public OutputStream bindStream(OutputStream output) {
/* 41 */            OutputStream stream = this.m_streams.get();
/* 42 */            this.m_streams.set(output);
/* 43 */            return stream;
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 54 */        public void close() throws IOException {
/* 55 */            OutputStream output = this.m_streams.get();
/* 56 */            if (output != null) {
/* 58 */                output.close();
                    }
                }

                @Override // java.io.OutputStream, java.io.Flushable
/* 70 */        public void flush() throws IOException {
/* 71 */            OutputStream output = this.m_streams.get();
/* 72 */            if (output != null) {
/* 74 */                output.flush();
                    }
                }

                @Override // java.io.OutputStream
/* 87 */        public void write(int ch) throws IOException {
/* 88 */            OutputStream output = this.m_streams.get();
/* 89 */            if (output != null) {
/* 91 */                output.write(ch);
                    }
                }
            }
