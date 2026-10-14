            package co.vine.android.util;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.media.MediaMetadataRetriever;
            import android.media.MediaScannerConnection;
            import android.net.Uri;
            import co.vine.android.VineLoggingException;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            
            
    public class MediaUtil {
        public static Bitmap[] getVideoFrames(String videoPath, long[] frameTime) throws IOException {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        try {
                    retriever.setDataSource(videoPath);
                    Bitmap[] r = new Bitmap[frameTime.length];
                    int i = 0;
                    long duration = -1;
                            for (long time : frameTime) {
                                if (time == -1) {
                                    if (duration == -1) {
                                String durationString = retriever.extractMetadata(9);
                                duration = Long.parseLong(durationString);
                                SLog.m447d("Got duration: {}.", Long.valueOf(duration));
                                    }
                            time = duration * 1000;
                                }
                        r[i] = retriever.getFrameAtTime(time, 1);
                        if (r[i] == null) {
                            r[i] = retriever.getFrameAtTime(time, 3);
                                }
                        i++;
                        SLog.m447d("Got frame at {}ms.", Long.valueOf(time));
                            }
                            try {
                        retriever.release();
                        return r;
                            } catch (RuntimeException e) {
                        SLog.m459e("Failed to release.", (Throwable) e);
                        return r;
                            }
                        } catch (RuntimeException e2) {
                    SLog.m459e("Failed to get frame.", (Throwable) e2);
                            try {
                        retriever.release();
                            } catch (RuntimeException e3) {
                        SLog.m459e("Failed to release.", (Throwable) e3);
                            }
                    return null;
                        }
                    } catch (Throwable th) {
                        try {
                    retriever.release();
                        } catch (RuntimeException e4) {
                    SLog.m459e("Failed to release.", (Throwable) e4);
                        }
                throw th;
                    }
                }

        public static void scanFile(Context context, File file, String mimeType) {
            MediaConnectionClient client = new MediaConnectionClient(file.getAbsolutePath(), mimeType);
            MediaScannerConnection connection = new MediaScannerConnection(context, client);
            client.connection = connection;
            connection.connect();
                }

        public static class MediaConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {
                    public MediaScannerConnection connection;
                    private final String mMimeType;
                    private final String mPath;

            public MediaConnectionClient(String mPath, String mimeType) {
                this.mPath = mPath;
                this.mMimeType = mimeType;
                    }

                    @Override 
            public void onMediaScannerConnected() {
                this.connection.scanFile(this.mPath, this.mMimeType);
                this.connection.disconnect();
                    }

                    @Override 
            public void onScanCompleted(String path, Uri uri) {
                SLog.m448d("Scan completed: {}, {}.", path, uri);
                    }
                }

       public static class GenerateThumbnailsRunnable implements Runnable {
                    private final long duration;
                    private final String lastFramePath;
                    private final String thumbnailPath;
                    private final String videoPath;

           public GenerateThumbnailsRunnable(long duration, String videoPath, String thumbnailPath, String lastFramePath) {
               this.duration = duration;
               this.lastFramePath = lastFramePath;
               this.videoPath = videoPath;
               this.thumbnailPath = thumbnailPath;
                    }

                    @Override 
           public void run() {
                        try {
                   MediaUtil.generateThumbnails(this.duration, this.videoPath, this.thumbnailPath, this.lastFramePath);
                        } catch (IOException e) {
                   throw new RuntimeException(e);
                        }
                    }
                }

       public static void generateThumbnails(long duration, String videoPath, String thumbnailPath, String lastFramePath) throws IOException {
           long start = System.currentTimeMillis();
           SLog.m447d("Use duration: {}.", Long.valueOf(duration));
           Bitmap[] bitmaps = getVideoFrames(videoPath, lastFramePath != null ? new long[]{0, duration} : new long[]{0});
           SLog.m442b("Grabbing using MediaUtil took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
           if (bitmaps != null) {
               if (bitmaps.length == 0 || bitmaps[0] == null) {
                   CrashUtil.log("Mayday, mayday! Failed to get thumbnails, this will fail and crash: {}.", videoPath);
                        }
               bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(thumbnailPath));
               if (lastFramePath != null) {
                   if (bitmaps[1] != null) {
                       bitmaps[1].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(lastFramePath));
                            } else {
                       CrashUtil.logException(new VineLoggingException("FAILED TO GENERATE GHOST IMAGE"));
                            }
                        }
                    }
                    try {
               if (!new File(thumbnailPath).exists()) {
                   CrashUtil.log("Retry generating thumbnail.");
                   Bitmap[] bitmaps2 = getVideoFrames(videoPath, new long[]{0});
                   if (bitmaps2 != null && bitmaps2.length == 1) {
                       bitmaps2[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(thumbnailPath));
                       SLog.m464i("First Thumbnail generated.");
                       return;
                            }
                   throw new IllegalStateException();
                        }
                    } catch (Exception e) {
               CrashUtil.log("Failed to generate thumbnail.");
                    }
                }
            }
