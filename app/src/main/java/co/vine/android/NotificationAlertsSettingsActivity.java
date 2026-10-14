            package co.vine.android;

            import android.content.ActivityNotFoundException;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class NotificationAlertsSettingsActivity extends BaseControllerActionBarActivity {
                private boolean mNotificationWasEnabled;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 21 */        protected void onDestroy() {
/* 22 */            super.onDestroy();
/* 23 */            if (this.mNotificationWasEnabled && !Settings.isNotificationEnabled(this)) {
/* 24 */                FlurryUtils.trackNotificationDisabled();
                    }
                }

                @Override // android.support.v4.app.FragmentActivity
/* 30 */        public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
                    try {
/* 31 */                super.startActivityFromFragment(fragment, intent, requestCode);
                    } catch (ActivityNotFoundException e) {
/* 33 */                CrashUtil.logException(e);
/* 34 */                Util.showCenteredToast(this, C0257R.string.unsupported_feature);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 39 */        protected void onCreate(Bundle savedInstanceState) {
/* 40 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 42 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.settings_notifications_alerts, (Boolean) true, (Boolean) true);
/* 44 */            Bundle extras = getIntent().getExtras();
/* 45 */            if (extras != null && extras.getInt("color", 0) != 0) {
/* 46 */                setActionBarColor(extras.getInt("color"));
                    }
/* 49 */            this.mNotificationWasEnabled = Settings.isNotificationEnabled(this);
/* 51 */            if (savedInstanceState == null) {
/* 52 */                NotificationAlertsSettingsFragment fragment = new NotificationAlertsSettingsFragment();
/* 53 */                getFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment).commit();
                    }
                }
            }
