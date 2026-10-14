            package co.vine.android.network;

            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface FileNetworkEntity {

                public interface ProgressListener {
                    void progressChanged(TransferProgressEvent transferProgressEvent);
                }

                String getContentTypeString();

                File getFile();

                ProgressListener getUploadProgressListener();
            }
