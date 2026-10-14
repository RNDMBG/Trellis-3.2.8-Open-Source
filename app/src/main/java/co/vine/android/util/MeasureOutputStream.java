            package co.vine.android.util;

            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.TransferProgressEvent;
            import java.io.File;
            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import org.apache.http.entity.FileEntity;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class MeasureOutputStream extends FileEntity implements FileNetworkEntity {
                private final String mContentType;
                private final File mFile;
                private final FileNetworkEntity.ProgressListener mProgressListener;

/* 23 */        public MeasureOutputStream(File file, String contentType, FileNetworkEntity.ProgressListener listener) {
/* 24 */            super(file, contentType);
/* 25 */            this.mFile = file;
/* 26 */            this.mContentType = contentType;
/* 27 */            this.mProgressListener = listener;
                }

                @Override // org.apache.http.entity.FileEntity, org.apache.http.HttpEntity
/* 30 */        public void writeTo(OutputStream out) throws IOException {
/* 31 */            super.writeTo(new CountingOutputStream(out, this.mProgressListener));
                }

                @Override // co.vine.android.network.FileNetworkEntity
/* 35 */        public File getFile() {
/* 36 */            return this.mFile;
                }

                @Override // co.vine.android.network.FileNetworkEntity
/* 40 */        public String getContentTypeString() {
/* 41 */            return this.mContentType;
                }

                @Override // co.vine.android.network.FileNetworkEntity
/* 45 */        public FileNetworkEntity.ProgressListener getUploadProgressListener() {
/* 46 */            return null;
                }

/* 54 */        public static class CountingOutputStream extends FilterOutputStream {
                    private final TransferProgressEvent mProgressEvent;
                    private final FileNetworkEntity.ProgressListener mProgressListener;

/* 55 */            public CountingOutputStream(OutputStream out, FileNetworkEntity.ProgressListener listener) {
/* 56 */                super(out);
/* 57 */                this.mProgressListener = listener;
/* 58 */                this.mProgressEvent = new TransferProgressEvent(1);
                    }

                    @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 61 */            public void write(byte[] b, int off, int len) throws IOException {
/* 62 */                this.out.write(b, off, len);
/* 63 */                this.mProgressEvent.setBytesTransfered(len);
/* 64 */                if (this.mProgressListener != null) {
/* 65 */                    this.mProgressListener.progressChanged(this.mProgressEvent);
                        }
                    }

                    @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 69 */            public void write(int b) throws IOException {
/* 70 */                this.out.write(b);
/* 71 */                this.mProgressEvent.setBytesTransfered(1L);
/* 72 */                if (this.mProgressListener != null) {
/* 73 */                    this.mProgressListener.progressChanged(this.mProgressEvent);
                        }
                    }
                }
            }
