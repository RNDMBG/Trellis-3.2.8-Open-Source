            package co.vine.android;

            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.widget.Button;
            import android.widget.TextView;
            import co.vine.android.util.ViewUtil;
            
            
    public class EditProfileCropActivity extends CropActivity {
                private Button mSaveButton;

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.crop_photo);
            this.mSaveButton = (Button) findViewById(C0257R.id.save_button);
            this.mSaveButton.setEnabled(false);
            this.mView.setCropType(2);
            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(this, 1.1f, 12.0f, (TextView) findViewById(C0257R.id.save_button), (TextView) findViewById(C0257R.id.cancel_button));
            setupActionBar((Boolean) null, (Boolean) true, C0257R.string.crop_photo_title, (Boolean) null, (Boolean) true);
                }

                @Override 
        protected void onBitmapProcessingDone(Bitmap bmp) {
            super.onBitmapProcessingDone(bmp);
            this.mSaveButton.setEnabled(true);
                }
            }
