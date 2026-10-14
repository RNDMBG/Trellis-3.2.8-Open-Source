            package co.vine.android.network;

            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class CountingOutputStream extends FilterOutputStream {
                private final long mChunk;
                private final long mLength;
                private final ProgressListener mListener;
                private long mNext;
                private long mTransferred;

/* 24 */        public CountingOutputStream(OutputStream out, long length, ProgressListener listener) {
/* 25 */            super(out);
/* 26 */            this.mListener = listener;
/* 27 */            this.mLength = 2 * length;
/* 28 */            this.mTransferred = 0L;
/* 29 */            this.mChunk = this.mLength / 5;
/* 30 */            this.mNext = this.mChunk;
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 34 */        public void write(byte[] b, int off, int len) throws IOException {
/* 35 */            super.write(b, off, len);
/* 36 */            this.mTransferred += (long) len;
                    if (this.mTransferred >= this.mNext) {
/* 38 */                super.flush();
/* 39 */                if (this.mListener != null) {
/* 40 */                    this.mListener.onProgress(this.mTransferred, this.mLength);
                        }
/* 42 */                this.mNext += this.mChunk;
                    }
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 47 */        public void write(int b) throws IOException {
/* 48 */            super.write(b);
/* 49 */            this.mTransferred++;
                    if (this.mTransferred >= this.mNext) {
/* 51 */                super.flush();
/* 52 */                if (this.mListener != null) {
/* 53 */                    this.mListener.onProgress(this.mTransferred, this.mLength);
                        }
/* 55 */                this.mNext += this.mChunk;
                    }
                }
            }
