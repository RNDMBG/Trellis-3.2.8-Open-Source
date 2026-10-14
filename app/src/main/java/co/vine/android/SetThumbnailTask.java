            package co.vine.android;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.Fragment;
            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.net.Uri;
            import android.os.AsyncTask;
            import co.vine.android.util.ImageUtils;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class SetThumbnailTask extends AsyncTask<Uri, Void, Bitmap> {
                private final WeakReference<Fragment> mFragmentRef;
                private final WeakReference<android.support.v4.app.Fragment> mSupportFragmentRef;

                public interface SetThumbnailListener {
                    void setThumbnailImage(Bitmap bitmap);
                }



/* 27 */        public SetThumbnailTask(android.support.v4.app.Fragment fragment) {
/* 29 */            this.mSupportFragmentRef = new WeakReference<>(fragment);
/* 30 */            this.mFragmentRef = null;
                }

/* 32 */        public SetThumbnailTask(Fragment f) {
/* 34 */            this.mFragmentRef = new WeakReference<>(f);
/* 35 */            this.mSupportFragmentRef = null;
                }

                @TargetApi(11)
/* 39 */        protected Activity getFragmentActivity() {
/* 40 */            Fragment f = this.mFragmentRef.get();
/* 41 */            if (f == null) {
/* 42 */                return null;
                    }
                    return f.getActivity();
                }

/* 47 */        protected Activity getSupportFragmentActivity() {
/* 48 */            android.support.v4.app.Fragment f = this.mSupportFragmentRef.get();
/* 49 */            if (f == null) {
/* 50 */                return null;
                    }
                    return f.getActivity();
                }

                /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 56 */        protected Bitmap doInBackground(Uri... params) {
/* 57 */            Context context = null;
/* 58 */            if (this.mSupportFragmentRef != null) {
/* 59 */                context = getSupportFragmentActivity();
                    } else if (this.mFragmentRef != null) {
/* 61 */                context = getFragmentActivity();
                    }
/* 63 */            if (context == null) {
                        return null;
                    }
/* 67 */            Uri photoUri = params[0];
/* 68 */            Resources resources = context.getResources();
/* 69 */            float size = resources.getDimensionPixelSize(C0257R.dimen.user_image_size) * resources.getDisplayMetrics().density;
/* 71 */            Bitmap resizedBitmap = ImageUtils.resizeBitmap(context, photoUri, size, size, 0);
/* 72 */            if (resizedBitmap != null) {
/* 73 */                resizedBitmap = ImageUtils.getCroppedBitmap(resizedBitmap, (int) size);
                    }
/* 75 */            if (resizedBitmap != null) {
/* 76 */                return ImageUtils.adjustRotation(context, photoUri, resizedBitmap);
                    }
                    return null;
                }

                /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 83 */        protected void onPostExecute(Bitmap bitmap) {
/* 84 */            Object fragment = null;
/* 85 */            if (this.mSupportFragmentRef != null) {
/* 86 */                fragment = this.mSupportFragmentRef.get();
                    } else if (this.mFragmentRef != null) {
/* 88 */                fragment = this.mFragmentRef.get();
                    }
/* 91 */            if (fragment != null) {
/* 92 */                ((SetThumbnailListener) fragment).setThumbnailImage(bitmap);
                    }
                }
            }
