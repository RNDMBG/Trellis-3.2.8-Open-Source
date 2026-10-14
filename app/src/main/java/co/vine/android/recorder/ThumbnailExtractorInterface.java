            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.net.Uri;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ThumbnailExtractorInterface {

                public interface OnThumbnailRetrievedListener {
                    void onError();

                    void onThumbnailRetrieved(long j, Bitmap bitmap);
                }

                void requestThumbnail(long j);

                void start(Context context, Uri uri, int i, OnThumbnailRetrievedListener onThumbnailRetrievedListener);

                void stop();
            }
