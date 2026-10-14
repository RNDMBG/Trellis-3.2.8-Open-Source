            package co.vine.android.network;

            import java.io.File;
            
            
            public interface FileNetworkEntity {

                public interface ProgressListener {
                    void progressChanged(TransferProgressEvent transferProgressEvent);
                }

                String getContentTypeString();

                File getFile();

                ProgressListener getUploadProgressListener();
            }
