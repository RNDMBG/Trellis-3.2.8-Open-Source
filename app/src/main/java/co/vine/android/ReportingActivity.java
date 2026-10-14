            package co.vine.android;

            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class ReportingActivity extends BaseControllerActionBarActivity {
                private static final String TAG = "reportingmenu";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 18 */        protected void onCreate(Bundle savedInstanceState) {
/* 19 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 20 */            if (this.mAppController == null) {
/* 21 */                this.mAppController = AppController.getInstance(this);
/* 22 */                CrashUtil.logException(new VineLoggingException(), "App controller is null here, and now? " + this.mAppController, new Object[0]);
                    }
/* 24 */            if (savedInstanceState == null) {
/* 25 */                ReportingFragment fragment = ReportingFragment.newFragment(getIntent());
/* 26 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, TAG).commit();
                    }
                }
            }
