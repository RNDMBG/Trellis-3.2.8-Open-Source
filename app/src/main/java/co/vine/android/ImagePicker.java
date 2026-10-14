            package co.vine.android;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.ContentValues;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.net.Uri;
            import android.provider.MediaStore;
            import android.widget.Toast;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import java.io.ByteArrayOutputStream;
            import java.io.File;
            import java.io.FileOutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class ImagePicker {
                private long mActiveUserId;
                private Activity mActivity;
                private Listener mListener;
                private Uri mSavedImageUri;

                public interface Listener {
                    void setAvatarUrl(Uri uri);
                }

/* 24 */        static /* synthetic */ Activity access$000(ImagePicker x0) {
/* 25 */            return x0.mActivity;
                }

/* 30 */        public ImagePicker(Activity activity, Listener listener, long activeUserId) {
/* 32 */            this.mActivity = activity;
/* 33 */            this.mActiveUserId = activeUserId;
/* 34 */            this.mListener = listener;
                }

/* 37 */        public void captureImage(int requestCode) {
/* 39 */            File profileFile = ImageUtils.getTempPic(this.mActivity, false, this.mActiveUserId);
/* 41 */            if (profileFile == null || !ImageUtils.hasEnoughExternalStorageForNewPhoto(this.mActivity)) {
/* 43 */                Toast.makeText(this.mActivity, C0257R.string.camera_photo_error, 0).show();
/* 62 */                return;
                    }
/* 47 */            String profile = this.mActivity.getString(C0257R.string.profile_title);
/* 48 */            ContentValues values = new ContentValues();
/* 49 */            values.put("title", profile);
/* 50 */            values.put("description", profile);
/* 51 */            Uri uri = Uri.fromFile(profileFile);
/* 52 */            this.mListener.setAvatarUrl(uri);
/* 53 */            Intent i = new Intent("android.media.action.IMAGE_CAPTURE").putExtra("output", uri);
                    try {
/* 56 */                this.mActivity.startActivityForResult(i, requestCode);
                    } catch (ActivityNotFoundException e) {
/* 58 */                Util.showCenteredToast(this.mActivity, C0257R.string.unsupported_feature);
/* 59 */                ImageUtils.deleteTempPic(uri);
/* 60 */                this.mListener.setAvatarUrl(null);
                    }
                }

/* 64 */        public void chooseImage(int requestCode) {
/* 65 */            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    try {
/* 68 */                this.mActivity.startActivityForResult(intent, requestCode);
                    } catch (ActivityNotFoundException e) {
/* 70 */                Util.showCenteredToast(this.mActivity, C0257R.string.unsupported_feature);
                    }
                }

/* 74 */        public Uri getSavedImageUri() {
/* 75 */            return this.mSavedImageUri;
                }

/* 78 */        public void saveProfileImage(Bitmap bm) {
/* 79 */            File f = new File(Util.getExternalCacheDir(this.mActivity), "twitter_profile.jpg");
/* 80 */            ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 81 */            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
/* 82 */            byte[] compressed = bos.toByteArray();
                    try {
/* 85 */                FileOutputStream fos = new FileOutputStream(f);
/* 86 */                fos.write(compressed);
/* 87 */                fos.close();
/* 88 */                this.mSavedImageUri = Uri.fromFile(f);
/* 89 */                SLog.m447d("Pending Uri for profile photo is {}.", this.mSavedImageUri);
/* 90 */                this.mListener.setAvatarUrl(this.mSavedImageUri);
                    } catch (Exception e) {
/* 92 */                if (this.mActivity != null) {
/* 93 */                    this.mActivity.runOnUiThread(new RunnableC02231());
                        }
/* 101 */               this.mListener.setAvatarUrl(null);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImagePicker$1 */
/* 91 */        class RunnableC02231 implements Runnable {
/* 92 */            RunnableC02231() {
                    }

                    @Override // java.lang.Runnable
/* 95 */            public void run() {
/* 96 */                Toast.makeText(ImagePicker.access$000(ImagePicker.this), C0257R.string.profile_image_save_error, 0).show();
                    }
                }
            }
