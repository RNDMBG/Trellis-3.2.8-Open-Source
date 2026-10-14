            package co.vine.android;

            import android.os.Bundle;
            import android.view.MenuItem;
            
            
    public class PrivacyControlsActivity extends BaseControllerActionBarActivity {
                private static final String PRIVACY_CONTROLS_TAG = "privacy_controls";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.privacy, (Boolean) true, (Boolean) true);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.getInt("color", 0) != 0) {
                setActionBarColor(extras.getInt("color"));
                    }
            if (savedInstanceState == null) {
                PrivacyControlsFragment frag = new PrivacyControlsFragment();
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, frag, PRIVACY_CONTROLS_TAG).commit();
                    }
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
                    onBackPressed();
                    return true;
                        default:
                    return super.onOptionsItemSelected(item);
                    }
                }
            }
