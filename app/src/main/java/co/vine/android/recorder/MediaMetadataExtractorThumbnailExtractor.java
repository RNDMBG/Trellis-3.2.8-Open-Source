            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.media.MediaMetadataRetriever;
            import android.net.Uri;
            import co.vine.android.util.CrashUtil;
            import java.io.IOException;
            import java.util.concurrent.LinkedBlockingDeque;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 17 */    public class MediaMetadataExtractorThumbnailExtractor implements ThumbnailExtractorInterface {
                private Context mContext;
                private Uri mInputUri;
                private ThumbnailExtractorInterface.OnThumbnailRetrievedListener mListener;
                private Thread mThumbnailExtractingThread;
                private LinkedBlockingDeque<Long> mTimestamps = new LinkedBlockingDeque<>();
                private boolean mExit = false;
                private long mLastAddedTimestamp = Long.MIN_VALUE;

/* 18 */        static /* synthetic */ Context access$100(MediaMetadataExtractorThumbnailExtractor x0) {
/* 19 */            return x0.mContext;
                }

/* 18 */        static /* synthetic */ Uri access$200(MediaMetadataExtractorThumbnailExtractor x0) {
/* 19 */            return x0.mInputUri;
                }

/* 18 */        static /* synthetic */ ThumbnailExtractorInterface.OnThumbnailRetrievedListener access$300(MediaMetadataExtractorThumbnailExtractor x0) {
/* 19 */            return x0.mListener;
                }

/* 18 */        static /* synthetic */ boolean access$400(MediaMetadataExtractorThumbnailExtractor x0) {
/* 19 */            return x0.mExit;
                }

/* 18 */        static /* synthetic */ boolean access$402(MediaMetadataExtractorThumbnailExtractor x0, boolean x1) {
/* 19 */            x0.mExit = x1;
                    return x1;
                }

/* 18 */        static /* synthetic */ LinkedBlockingDeque access$500(MediaMetadataExtractorThumbnailExtractor x0) {
/* 19 */            return x0.mTimestamps;
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 29 */        public void start(Context context, Uri input, int targetLongestSize, ThumbnailExtractorInterface.OnThumbnailRetrievedListener listener) {
/* 30 */            this.mContext = context;
/* 31 */            this.mInputUri = input;
/* 32 */            this.mListener = listener;
/* 33 */            this.mThumbnailExtractingThread = new ThumbnailExtractingThread(this, null);
/* 34 */            this.mThumbnailExtractingThread.setPriority(10);
/* 35 */            this.mThumbnailExtractingThread.start();
                }

/* 36 */        private class ThumbnailExtractingThread extends Thread {
/* 37 */            private ThumbnailExtractingThread() {
                    }

/* 37 */            /* synthetic */ ThumbnailExtractingThread(MediaMetadataExtractorThumbnailExtractor x0, Object x1) {
/* 38 */                this();
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 40 */            public void run() {
/* 41 */                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        try {
/* 43 */                    mediaMetadataRetriever.setDataSource(MediaMetadataExtractorThumbnailExtractor.access$100(MediaMetadataExtractorThumbnailExtractor.this), MediaMetadataExtractorThumbnailExtractor.access$200(MediaMetadataExtractorThumbnailExtractor.this));
                        } catch (IllegalArgumentException e) {
/* 45 */                    CrashUtil.log("Failed to set data source on " + MediaMetadataExtractorThumbnailExtractor.access$200(MediaMetadataExtractorThumbnailExtractor.this));
                    try {
/* 46 */                    mediaMetadataRetriever.release();
                    } catch (IOException ignored) {
                    }
/* 47 */                    mediaMetadataRetriever = null;
/* 48 */                    if (MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this) != null) {
/* 49 */                        MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this).onError();
/* 70 */                        return;
                            }
                        }
/* 54 */                long timestamp = -1;
/* 55 */                while (!MediaMetadataExtractorThumbnailExtractor.access$400(MediaMetadataExtractorThumbnailExtractor.this)) {
                            try {
/* 57 */                        timestamp = ((Long) MediaMetadataExtractorThumbnailExtractor.access$500(MediaMetadataExtractorThumbnailExtractor.this).take()).longValue();
                            } catch (InterruptedException e2) {
/* 59 */                        MediaMetadataExtractorThumbnailExtractor.access$402(MediaMetadataExtractorThumbnailExtractor.this, true);
                            }
/* 62 */                    Bitmap b = mediaMetadataRetriever.getFrameAtTime(timestamp);
/* 63 */                    if (MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this) != null) {
/* 64 */                        MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this).onThumbnailRetrieved(timestamp, b);
                            }
                        }
                    try {
/* 68 */                mediaMetadataRetriever.release();
                    } catch (IOException ignored) {
                    }
                    }
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 73 */        public void requestThumbnail(long timestamp) {
                    if (timestamp <= this.mLastAddedTimestamp) {
/* 75 */                throw new IllegalArgumentException("Cannot request a thumbnail that is before the last one");
                    }
/* 77 */            this.mLastAddedTimestamp = timestamp;
/* 78 */            this.mTimestamps.add(Long.valueOf(timestamp));
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 81 */        public void stop() {
/* 82 */            this.mExit = true;
/* 83 */            if (this.mThumbnailExtractingThread != null) {
/* 84 */                this.mThumbnailExtractingThread.interrupt();
                    }
                }
            }
