            package co.vine.android;

            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            
            
    public class ReportingActivity extends BaseControllerActionBarActivity {
                private static final String TAG = "reportingmenu";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
            if (this.mAppController == null) {
                this.mAppController = AppController.getInstance(this);
                CrashUtil.logException(new VineLoggingException(), "App controller is null here, and now? " + this.mAppController, new Object[0]);
                    }
            if (savedInstanceState == null) {
                ReportingFragment fragment = ReportingFragment.newFragment(getIntent());
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, TAG).commit();
                    }
                }
            }
