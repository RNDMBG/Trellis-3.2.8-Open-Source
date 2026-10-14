            package co.vine.android.service;

            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.TransferProgressEvent;
            import co.vine.android.recorder.RecordConfigUtils;
            
            
    public abstract class UploadProgressListener implements FileNetworkEntity.ProgressListener {
                public long currentSize;
                public int lastProgress;
                public String path;
                public long size;
                public String thumbnail;

                public abstract void onPostFinished(boolean z);

                public abstract void onTranscodeStarted();

                @Override 
                public abstract void progressChanged(TransferProgressEvent transferProgressEvent);

                public abstract void updateTranscodeProgress(int i);

        public UploadProgressListener(String path) {
            String thumbnail = RecordConfigUtils.getThumbnailPath(path);
            this.path = path;
            this.thumbnail = thumbnail;
                }

        public void showPostNotification(String thumbnail) {
                }
            }
