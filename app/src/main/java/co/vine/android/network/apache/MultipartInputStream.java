            package co.vine.android.network.apache;

            import java.io.IOException;
            import java.io.InputStream;
            import java.util.ArrayList;
            
            
    public class MultipartInputStream extends InputStream {
                private InputStream mCurrStream;
                private final ArrayList<MultipartEntity.Entity> mEntities;
                private int mNextEntity = 0;

        public MultipartInputStream(ArrayList<MultipartEntity.Entity> entities) {
            this.mEntities = entities;
                }

                @Override 
        public int read(byte[] buffer, int offset, int length) throws IOException {
            int bytesRead = 0;
            int nextEntity = this.mNextEntity;
            InputStream is = this.mCurrStream;
            while (bytesRead < length) {
                if (is == null) {
                    is = this.mEntities.get(nextEntity).getInputStream();
                    this.mCurrStream = is;
                        }
                        while (true) {
                    int read = is.read(buffer, offset + bytesRead, length - bytesRead);
                            if (read == -1) {
                                break;
                            }
                    bytesRead += read;
                        }
                if (bytesRead == length || nextEntity == this.mEntities.size() - 1) {
                            break;
                        }
                nextEntity++;
                this.mNextEntity = nextEntity;
                is.close();
                is = null;
                this.mCurrStream = null;
                    }
            return bytesRead;
                }

                @Override 
        public int read() throws IOException {
            byte[] buff = new byte[1];
            if (read(buff, 0, 1) == 1) {
                return buff[0];
                    }
                    return -1;
                }

                @Override 
        public void close() throws IOException {
            if (this.mCurrStream != null) {
                this.mCurrStream.close();
                this.mCurrStream = null;
                    }
                }
            }
