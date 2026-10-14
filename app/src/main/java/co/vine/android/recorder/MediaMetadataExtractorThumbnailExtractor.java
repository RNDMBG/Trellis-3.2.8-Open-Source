            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.media.MediaMetadataRetriever;
            import android.net.Uri;
            import co.vine.android.util.CrashUtil;
            import java.io.IOException;
            import java.util.concurrent.LinkedBlockingDeque;
            
            
            @TargetApi(16)
    public class MediaMetadataExtractorThumbnailExtractor implements ThumbnailExtractorInterface {
                private Context mContext;
                private Uri mInputUri;
                private ThumbnailExtractorInterface.OnThumbnailRetrievedListener mListener;
                private Thread mThumbnailExtractingThread;
                private LinkedBlockingDeque<Long> mTimestamps = new LinkedBlockingDeque<>();
                private boolean mExit = false;
                private long mLastAddedTimestamp = Long.MIN_VALUE;

        static  Context access$100(MediaMetadataExtractorThumbnailExtractor x0) {
            return x0.mContext;
                }

        static  Uri access$200(MediaMetadataExtractorThumbnailExtractor x0) {
            return x0.mInputUri;
                }

        static  ThumbnailExtractorInterface.OnThumbnailRetrievedListener access$300(MediaMetadataExtractorThumbnailExtractor x0) {
            return x0.mListener;
                }

        static  boolean access$400(MediaMetadataExtractorThumbnailExtractor x0) {
            return x0.mExit;
                }

        static  boolean access$402(MediaMetadataExtractorThumbnailExtractor x0, boolean x1) {
            x0.mExit = x1;
                    return x1;
                }

        static  LinkedBlockingDeque access$500(MediaMetadataExtractorThumbnailExtractor x0) {
            return x0.mTimestamps;
                }

                @Override 
        public void start(Context context, Uri input, int targetLongestSize, ThumbnailExtractorInterface.OnThumbnailRetrievedListener listener) {
            this.mContext = context;
            this.mInputUri = input;
            this.mListener = listener;
            this.mThumbnailExtractingThread = new ThumbnailExtractingThread(this, null);
            this.mThumbnailExtractingThread.setPriority(10);
            this.mThumbnailExtractingThread.start();
                }

        private class ThumbnailExtractingThread extends Thread {
            private ThumbnailExtractingThread() {
                    }

             ThumbnailExtractingThread(MediaMetadataExtractorThumbnailExtractor x0, Object x1) {
                this();
                    }

                    @Override 
            public void run() {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        try {
                    mediaMetadataRetriever.setDataSource(MediaMetadataExtractorThumbnailExtractor.access$100(MediaMetadataExtractorThumbnailExtractor.this), MediaMetadataExtractorThumbnailExtractor.access$200(MediaMetadataExtractorThumbnailExtractor.this));
                        } catch (IllegalArgumentException e) {
                    CrashUtil.log("Failed to set data source on " + MediaMetadataExtractorThumbnailExtractor.access$200(MediaMetadataExtractorThumbnailExtractor.this));
                    try {
                    mediaMetadataRetriever.release();
                    } catch (IOException ignored) {
                    }
                    mediaMetadataRetriever = null;
                    if (MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this) != null) {
                        MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this).onError();
                        return;
                            }
                        }
                long timestamp = -1;
                while (!MediaMetadataExtractorThumbnailExtractor.access$400(MediaMetadataExtractorThumbnailExtractor.this)) {
                            try {
                        timestamp = ((Long) MediaMetadataExtractorThumbnailExtractor.access$500(MediaMetadataExtractorThumbnailExtractor.this).take()).longValue();
                            } catch (InterruptedException e2) {
                        MediaMetadataExtractorThumbnailExtractor.access$402(MediaMetadataExtractorThumbnailExtractor.this, true);
                            }
                    Bitmap b = mediaMetadataRetriever.getFrameAtTime(timestamp);
                    if (MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this) != null) {
                        MediaMetadataExtractorThumbnailExtractor.access$300(MediaMetadataExtractorThumbnailExtractor.this).onThumbnailRetrieved(timestamp, b);
                            }
                        }
                    try {
                mediaMetadataRetriever.release();
                    } catch (IOException ignored) {
                    }
                    }
                }

                @Override 
        public void requestThumbnail(long timestamp) {
                    if (timestamp <= this.mLastAddedTimestamp) {
                throw new IllegalArgumentException("Cannot request a thumbnail that is before the last one");
                    }
            this.mLastAddedTimestamp = timestamp;
            this.mTimestamps.add(Long.valueOf(timestamp));
                }

                @Override 
        public void stop() {
            this.mExit = true;
            if (this.mThumbnailExtractingThread != null) {
                this.mThumbnailExtractingThread.interrupt();
                    }
                }
            }
