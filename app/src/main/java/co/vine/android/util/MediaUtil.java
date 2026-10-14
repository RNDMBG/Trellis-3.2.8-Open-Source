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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class MediaUtil {
/* 29 */        public static Bitmap[] getVideoFrames(String videoPath, long[] frameTime) throws IOException {
/* 30 */            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        try {
/* 32 */                    retriever.setDataSource(videoPath);
/* 33 */                    Bitmap[] r = new Bitmap[frameTime.length];
/* 34 */                    int i = 0;
/* 35 */                    long duration = -1;
                            for (long time : frameTime) {
                                if (time == -1) {
                                    if (duration == -1) {
/* 39 */                                String durationString = retriever.extractMetadata(9);
/* 40 */                                duration = Long.parseLong(durationString);
/* 41 */                                SLog.m447d("Got duration: {}.", Long.valueOf(duration));
                                    }
/* 43 */                            time = duration * 1000;
                                }
/* 45 */                        r[i] = retriever.getFrameAtTime(time, 1);
/* 46 */                        if (r[i] == null) {
/* 47 */                            r[i] = retriever.getFrameAtTime(time, 3);
                                }
/* 50 */                        i++;
/* 51 */                        SLog.m447d("Got frame at {}ms.", Long.valueOf(time));
                            }
                            try {
/* 58 */                        retriever.release();
/* 63 */                        return r;
                            } catch (RuntimeException e) {
/* 60 */                        SLog.m459e("Failed to release.", (Throwable) e);
/* 63 */                        return r;
                            }
                        } catch (RuntimeException e2) {
/* 55 */                    SLog.m459e("Failed to get frame.", (Throwable) e2);
                            try {
/* 58 */                        retriever.release();
                            } catch (RuntimeException e3) {
/* 60 */                        SLog.m459e("Failed to release.", (Throwable) e3);
                            }
/* 63 */                    return null;
                        }
                    } catch (Throwable th) {
                        try {
/* 58 */                    retriever.release();
                        } catch (RuntimeException e4) {
/* 60 */                    SLog.m459e("Failed to release.", (Throwable) e4);
                        }
/* 61 */                throw th;
                    }
                }

/* 66 */        public static void scanFile(Context context, File file, String mimeType) {
/* 67 */            MediaConnectionClient client = new MediaConnectionClient(file.getAbsolutePath(), mimeType);
/* 68 */            MediaScannerConnection connection = new MediaScannerConnection(context, client);
/* 69 */            client.connection = connection;
/* 70 */            connection.connect();
                }

/* 78 */        public static class MediaConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {
                    public MediaScannerConnection connection;
                    private final String mMimeType;
                    private final String mPath;

/* 79 */            public MediaConnectionClient(String mPath, String mimeType) {
/* 81 */                this.mPath = mPath;
/* 82 */                this.mMimeType = mimeType;
                    }

                    @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
/* 86 */            public void onMediaScannerConnected() {
/* 87 */                this.connection.scanFile(this.mPath, this.mMimeType);
/* 88 */                this.connection.disconnect();
                    }

                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
/* 92 */            public void onScanCompleted(String path, Uri uri) {
/* 93 */                SLog.m448d("Scan completed: {}, {}.", path, uri);
                    }
                }

/* 102 */       public static class GenerateThumbnailsRunnable implements Runnable {
                    private final long duration;
                    private final String lastFramePath;
                    private final String thumbnailPath;
                    private final String videoPath;

/* 103 */           public GenerateThumbnailsRunnable(long duration, String videoPath, String thumbnailPath, String lastFramePath) {
/* 105 */               this.duration = duration;
/* 106 */               this.lastFramePath = lastFramePath;
/* 107 */               this.videoPath = videoPath;
/* 108 */               this.thumbnailPath = thumbnailPath;
                    }

                    @Override // java.lang.Runnable
/* 113 */           public void run() {
                        try {
/* 114 */                   MediaUtil.generateThumbnails(this.duration, this.videoPath, this.thumbnailPath, this.lastFramePath);
                        } catch (IOException e) {
/* 116 */                   throw new RuntimeException(e);
                        }
                    }
                }

/* 121 */       public static void generateThumbnails(long duration, String videoPath, String thumbnailPath, String lastFramePath) throws IOException {
/* 122 */           long start = System.currentTimeMillis();
/* 124 */           SLog.m447d("Use duration: {}.", Long.valueOf(duration));
/* 126 */           Bitmap[] bitmaps = getVideoFrames(videoPath, lastFramePath != null ? new long[]{0, duration} : new long[]{0});
/* 131 */           SLog.m442b("Grabbing using MediaUtil took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 133 */           if (bitmaps != null) {
/* 134 */               if (bitmaps.length == 0 || bitmaps[0] == null) {
/* 135 */                   CrashUtil.log("Mayday, mayday! Failed to get thumbnails, this will fail and crash: {}.", videoPath);
                        }
/* 137 */               bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(thumbnailPath));
/* 138 */               if (lastFramePath != null) {
/* 139 */                   if (bitmaps[1] != null) {
/* 140 */                       bitmaps[1].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(lastFramePath));
                            } else {
/* 142 */                       CrashUtil.logException(new VineLoggingException("FAILED TO GENERATE GHOST IMAGE"));
                            }
                        }
                    }
                    try {
/* 147 */               if (!new File(thumbnailPath).exists()) {
/* 148 */                   CrashUtil.log("Retry generating thumbnail.");
/* 149 */                   Bitmap[] bitmaps2 = getVideoFrames(videoPath, new long[]{0});
/* 150 */                   if (bitmaps2 != null && bitmaps2.length == 1) {
/* 151 */                       bitmaps2[0].compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(thumbnailPath));
/* 152 */                       SLog.m464i("First Thumbnail generated.");
/* 160 */                       return;
                            }
/* 154 */                   throw new IllegalStateException();
                        }
                    } catch (Exception e) {
/* 158 */               CrashUtil.log("Failed to generate thumbnail.");
                    }
                }
            }
