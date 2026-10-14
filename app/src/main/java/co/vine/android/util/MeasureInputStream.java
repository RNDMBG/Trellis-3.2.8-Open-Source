            package co.vine.android.util;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class MeasureInputStream extends InputStream {
                private final InputStream mInputStream;
                private long mReadTimeMs = 0;
                private long mReadTimeNs;

/* 18 */        public MeasureInputStream(InputStream inputStream) {
/* 20 */            this.mInputStream = inputStream;
                }

/* 24 */        public long getReadTime() {
/* 25 */            return this.mReadTimeMs + (this.mReadTimeNs / AudioUtils.MICROS_PER_SECOND);
                }

                @Override // java.io.InputStream
/* 29 */        public int available() throws IOException {
/* 30 */            return this.mInputStream.available();
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 34 */        public void close() throws IOException {
/* 35 */            this.mInputStream.close();
                }

                @Override // java.io.InputStream
/* 39 */        public void mark(int readlimit) {
/* 40 */            this.mInputStream.mark(readlimit);
                }

                @Override // java.io.InputStream
/* 44 */        public boolean markSupported() {
/* 45 */            return this.mInputStream.markSupported();
                }

                @Override // java.io.InputStream
/* 49 */        public int read() throws IOException {
/* 50 */            long now = System.nanoTime();
/* 51 */            int result = this.mInputStream.read();
/* 52 */            this.mReadTimeNs += System.nanoTime() - now;
/* 53 */            return result;
                }

                @Override // java.io.InputStream
/* 57 */        public int read(byte[] buffer) throws IOException {
/* 58 */            long now = System.currentTimeMillis();
/* 59 */            int result = this.mInputStream.read(buffer);
/* 60 */            this.mReadTimeMs += System.currentTimeMillis() - now;
/* 61 */            return result;
                }

                @Override // java.io.InputStream
/* 65 */        public int read(byte[] buffer, int offset, int length) throws IOException {
/* 66 */            long now = System.currentTimeMillis();
/* 67 */            int result = this.mInputStream.read(buffer, offset, length);
/* 68 */            this.mReadTimeMs += System.currentTimeMillis() - now;
/* 69 */            return result;
                }

                @Override // java.io.InputStream
/* 73 */        public synchronized void reset() throws IOException {
                    try {
                        this.mInputStream.reset();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 78 */        public long skip(long byteCount) throws IOException {
/* 79 */            long now = System.currentTimeMillis();
/* 80 */            long result = super.skip(byteCount);
/* 81 */            this.mReadTimeMs += System.currentTimeMillis() - now;
/* 82 */            return result;
                }
            }
