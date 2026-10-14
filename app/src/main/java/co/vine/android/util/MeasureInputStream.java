            package co.vine.android.util;

            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class MeasureInputStream extends InputStream {
                private final InputStream mInputStream;
                private long mReadTimeMs = 0;
                private long mReadTimeNs;

        public MeasureInputStream(InputStream inputStream) {
            this.mInputStream = inputStream;
                }

        public long getReadTime() {
            return this.mReadTimeMs + (this.mReadTimeNs / AudioUtils.MICROS_PER_SECOND);
                }

                @Override 
        public int available() throws IOException {
            return this.mInputStream.available();
                }

                @Override 
        public void close() throws IOException {
            this.mInputStream.close();
                }

                @Override 
        public void mark(int readlimit) {
            this.mInputStream.mark(readlimit);
                }

                @Override 
        public boolean markSupported() {
            return this.mInputStream.markSupported();
                }

                @Override 
        public int read() throws IOException {
            long now = System.nanoTime();
            int result = this.mInputStream.read();
            this.mReadTimeNs += System.nanoTime() - now;
            return result;
                }

                @Override 
        public int read(byte[] buffer) throws IOException {
            long now = System.currentTimeMillis();
            int result = this.mInputStream.read(buffer);
            this.mReadTimeMs += System.currentTimeMillis() - now;
            return result;
                }

                @Override 
        public int read(byte[] buffer, int offset, int length) throws IOException {
            long now = System.currentTimeMillis();
            int result = this.mInputStream.read(buffer, offset, length);
            this.mReadTimeMs += System.currentTimeMillis() - now;
            return result;
                }

                @Override 
        public synchronized void reset() throws IOException {
                    try {
                        this.mInputStream.reset();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public long skip(long byteCount) throws IOException {
            long now = System.currentTimeMillis();
            long result = super.skip(byteCount);
            this.mReadTimeMs += System.currentTimeMillis() - now;
            return result;
                }
            }
