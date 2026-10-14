            package co.vine.android.util;

            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.TransferProgressEvent;
            import java.io.File;
            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import org.apache.http.entity.FileEntity;
            
            
    public class MeasureOutputStream extends FileEntity implements FileNetworkEntity {
                private final String mContentType;
                private final File mFile;
                private final FileNetworkEntity.ProgressListener mProgressListener;

        public MeasureOutputStream(File file, String contentType, FileNetworkEntity.ProgressListener listener) {
            super(file, contentType);
            this.mFile = file;
            this.mContentType = contentType;
            this.mProgressListener = listener;
                }

                @Override 
        public void writeTo(OutputStream out) throws IOException {
            super.writeTo(new CountingOutputStream(out, this.mProgressListener));
                }

                @Override 
        public File getFile() {
            return this.mFile;
                }

                @Override 
        public String getContentTypeString() {
            return this.mContentType;
                }

                @Override 
        public FileNetworkEntity.ProgressListener getUploadProgressListener() {
            return null;
                }

        public static class CountingOutputStream extends FilterOutputStream {
                    private final TransferProgressEvent mProgressEvent;
                    private final FileNetworkEntity.ProgressListener mProgressListener;

            public CountingOutputStream(OutputStream out, FileNetworkEntity.ProgressListener listener) {
                super(out);
                this.mProgressListener = listener;
                this.mProgressEvent = new TransferProgressEvent(1);
                    }

                    @Override 
            public void write(byte[] b, int off, int len) throws IOException {
                this.out.write(b, off, len);
                this.mProgressEvent.setBytesTransfered(len);
                if (this.mProgressListener != null) {
                    this.mProgressListener.progressChanged(this.mProgressEvent);
                        }
                    }

                    @Override 
            public void write(int b) throws IOException {
                this.out.write(b);
                this.mProgressEvent.setBytesTransfered(1L);
                if (this.mProgressListener != null) {
                    this.mProgressListener.progressChanged(this.mProgressEvent);
                        }
                    }
                }
            }
