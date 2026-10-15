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
            
            
    public class ImagePicker {
                private long mActiveUserId;
                private Activity mActivity;
                private Listener mListener;
                private Uri mSavedImageUri;

                public interface Listener {
                    void setAvatarUrl(Uri uri);
                }

        static  Activity access$000(ImagePicker x0) {
            return x0.mActivity;
                }

        public ImagePicker(Activity activity, Listener listener, long activeUserId) {
            this.mActivity = activity;
            this.mActiveUserId = activeUserId;
            this.mListener = listener;
                }

        public void captureImage(int requestCode) {
            File profileFile = ImageUtils.getTempPic(this.mActivity, false, this.mActiveUserId);
            if (profileFile == null || !ImageUtils.hasEnoughExternalStorageForNewPhoto(this.mActivity)) {
                Toast.makeText(this.mActivity, R.string.camera_photo_error, 0).show();
                return;
                    }
            String profile = this.mActivity.getString(R.string.profile_title);
            ContentValues values = new ContentValues();
            values.put("title", profile);
            values.put("description", profile);
            Uri uri = Uri.fromFile(profileFile);
            this.mListener.setAvatarUrl(uri);
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE").putExtra("output", uri);
                    try {
                this.mActivity.startActivityForResult(i, requestCode);
                    } catch (ActivityNotFoundException e) {
                Util.showCenteredToast(this.mActivity, R.string.unsupported_feature);
                ImageUtils.deleteTempPic(uri);
                this.mListener.setAvatarUrl(null);
                    }
                }

        public void chooseImage(int requestCode) {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    try {
                this.mActivity.startActivityForResult(intent, requestCode);
                    } catch (ActivityNotFoundException e) {
                Util.showCenteredToast(this.mActivity, R.string.unsupported_feature);
                    }
                }

        public Uri getSavedImageUri() {
            return this.mSavedImageUri;
                }

        public void saveProfileImage(Bitmap bm) {
            File f = new File(Util.getExternalCacheDir(this.mActivity), "twitter_profile.jpg");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] compressed = bos.toByteArray();
                    try {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(compressed);
                fos.close();
                this.mSavedImageUri = Uri.fromFile(f);
                SLog.m447d("Pending Uri for profile photo is {}.", this.mSavedImageUri);
                this.mListener.setAvatarUrl(this.mSavedImageUri);
                    } catch (Exception e) {
                if (this.mActivity != null) {
                    this.mActivity.runOnUiThread(new RunnableC02231());
                        }
               this.mListener.setAvatarUrl(null);
                    }
                }

                
        class RunnableC02231 implements Runnable {
            RunnableC02231() {
                    }

                    @Override 
            public void run() {
                Toast.makeText(ImagePicker.access$000(ImagePicker.this), R.string.profile_image_save_error, 0).show();
                    }
                }
            }
