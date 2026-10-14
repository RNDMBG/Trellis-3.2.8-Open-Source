            package co.vine.android;

            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.widget.Button;
            import android.widget.TextView;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class EditProfileCropActivity extends CropActivity {
                private Button mSaveButton;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 17 */        public void onCreate(Bundle savedInstanceState) {
/* 18 */            super.onCreate(savedInstanceState, C0257R.layout.crop_photo);
/* 19 */            this.mSaveButton = (Button) findViewById(C0257R.id.save_button);
/* 20 */            this.mSaveButton.setEnabled(false);
/* 21 */            this.mView.setCropType(2);
/* 23 */            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(this, 1.1f, 12.0f, (TextView) findViewById(C0257R.id.save_button), (TextView) findViewById(C0257R.id.cancel_button));
/* 27 */            setupActionBar((Boolean) null, (Boolean) true, C0257R.string.crop_photo_title, (Boolean) null, (Boolean) true);
                }

                @Override // co.vine.android.CropActivity
/* 32 */        protected void onBitmapProcessingDone(Bitmap bmp) {
/* 33 */            super.onBitmapProcessingDone(bmp);
/* 34 */            this.mSaveButton.setEnabled(true);
                }
            }
