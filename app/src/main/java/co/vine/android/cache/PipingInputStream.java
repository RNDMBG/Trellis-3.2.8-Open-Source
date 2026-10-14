            package co.vine.android.cache;

            import java.io.FilterInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class PipingInputStream extends FilterInputStream {
                private InputStream mInputStream;
                private OutputStream mOutputStream;

/* 15 */        public PipingInputStream(InputStream inputStream, OutputStream outputStream) {
/* 16 */            super(inputStream);
/* 17 */            if (inputStream == null) {
/* 18 */                throw new IllegalArgumentException("InputStream cannot be null");
                    }
/* 20 */            this.mInputStream = inputStream;
/* 21 */            this.mOutputStream = outputStream;
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 25 */        public int read(byte[] buffer) throws IOException {
/* 26 */            return read(buffer, 0, buffer.length);
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 30 */        public int read(byte[] buffer, int offset, int count) throws IOException {
/* 31 */            int readBytes = this.mInputStream.read(buffer, offset, count);
/* 32 */            if (readBytes != -1 && this.mOutputStream != null) {
/* 33 */                this.mOutputStream.write(buffer, offset, readBytes);
                    }
/* 35 */            return readBytes;
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 39 */        public int read() throws IOException {
/* 40 */            int oneByte = this.mInputStream.read();
/* 41 */            if (oneByte != -1 && this.mOutputStream != null) {
/* 42 */                this.mOutputStream.write(oneByte);
                    }
/* 44 */            return oneByte;
                }
            }
