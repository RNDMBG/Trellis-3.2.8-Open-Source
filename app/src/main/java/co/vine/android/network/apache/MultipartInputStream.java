            package co.vine.android.network.apache;

            import java.io.IOException;
            import java.io.InputStream;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class MultipartInputStream extends InputStream {
                private InputStream mCurrStream;
                private final ArrayList<MultipartEntity.Entity> mEntities;
                private int mNextEntity = 0;

/* 11 */        public MultipartInputStream(ArrayList<MultipartEntity.Entity> entities) {
/* 13 */            this.mEntities = entities;
                }

                @Override // java.io.InputStream
/* 18 */        public int read(byte[] buffer, int offset, int length) throws IOException {
/* 19 */            int bytesRead = 0;
/* 20 */            int nextEntity = this.mNextEntity;
/* 21 */            InputStream is = this.mCurrStream;
/* 22 */            while (bytesRead < length) {
/* 23 */                if (is == null) {
/* 24 */                    is = this.mEntities.get(nextEntity).getInputStream();
/* 25 */                    this.mCurrStream = is;
                        }
                        while (true) {
/* 28 */                    int read = is.read(buffer, offset + bytesRead, length - bytesRead);
                            if (read == -1) {
                                break;
                            }
/* 29 */                    bytesRead += read;
                        }
/* 34 */                if (bytesRead == length || nextEntity == this.mEntities.size() - 1) {
                            break;
                        }
/* 38 */                nextEntity++;
/* 39 */                this.mNextEntity = nextEntity;
/* 40 */                is.close();
/* 41 */                is = null;
/* 42 */                this.mCurrStream = null;
                    }
/* 45 */            return bytesRead;
                }

                @Override // java.io.InputStream
/* 49 */        public int read() throws IOException {
/* 50 */            byte[] buff = new byte[1];
/* 51 */            if (read(buff, 0, 1) == 1) {
/* 52 */                return buff[0];
                    }
                    return -1;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 59 */        public void close() throws IOException {
/* 60 */            if (this.mCurrStream != null) {
/* 61 */                this.mCurrStream.close();
/* 62 */                this.mCurrStream = null;
                    }
                }
            }
