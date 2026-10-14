            package co.vine.android;

            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class NotificationSettingsActivity extends BaseControllerActionBarActivity {
                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 12 */        protected void onCreate(Bundle savedInstanceState) {
/* 13 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 15 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.settings_notifications, (Boolean) true, (Boolean) true);
/* 17 */            Bundle extras = getIntent().getExtras();
/* 18 */            if (extras != null && extras.getInt("color", 0) != 0) {
/* 19 */                setActionBarColor(extras.getInt("color"));
                    }
/* 22 */            if (savedInstanceState == null) {
/* 23 */                NotificationSettingsFragment fragment = new NotificationSettingsFragment();
/* 24 */                if (extras != null) {
/* 25 */                    fragment.setArguments(extras);
                        }
/* 27 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment).commit();
                    }
                }
            }
