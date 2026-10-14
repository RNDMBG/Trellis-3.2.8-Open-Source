            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class SonyLaunchActivity extends Activity {
                @Override // android.app.Activity
/* 16 */        protected void onCreate(Bundle savedInstanceState) {
/* 17 */            super.onCreate(savedInstanceState);
/* 19 */            // The Sony-specific recorder is intentionally outside this build because
/* 20 */            // its proprietary camera SDK is not present. Keep the launcher valid and
/* 21 */            // fall back to Vine's standard recorder when that integration is absent.
/* 22 */            Intent intent = new Intent(this, (Class<?>) RecordingActivity.class);
/* 20 */            intent.setFlags(268468224);
/* 21 */            startActivity(intent);
/* 22 */            finish();
                }

                @Override // android.app.Activity
/* 26 */        protected void onNewIntent(Intent intent) {
/* 27 */            super.onNewIntent(intent);
                }
            }
