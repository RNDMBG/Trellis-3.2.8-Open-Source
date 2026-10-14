            package co.vine.android.service;

            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.TransferProgressEvent;
            import co.vine.android.recorder.RecordConfigUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public abstract class UploadProgressListener implements FileNetworkEntity.ProgressListener {
                public long currentSize;
                public int lastProgress;
                public String path;
                public long size;
                public String thumbnail;

                public abstract void onPostFinished(boolean z);

                public abstract void onTranscodeStarted();

                @Override // co.vine.android.network.FileNetworkEntity.ProgressListener
                public abstract void progressChanged(TransferProgressEvent transferProgressEvent);

                public abstract void updateTranscodeProgress(int i);

/* 17 */        public UploadProgressListener(String path) {
/* 19 */            String thumbnail = RecordConfigUtils.getThumbnailPath(path);
/* 20 */            this.path = path;
/* 21 */            this.thumbnail = thumbnail;
                }

/* 27 */        public void showPostNotification(String thumbnail) {
                }
            }
