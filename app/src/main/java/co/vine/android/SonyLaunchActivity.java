            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            
            
    public class SonyLaunchActivity extends Activity {
                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            
            
            Intent intent = new Intent(this, (Class<?>) RecordingActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            finish();
                }

                @Override 
        protected void onNewIntent(Intent intent) {
            super.onNewIntent(intent);
                }
            }
