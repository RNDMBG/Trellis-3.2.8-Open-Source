            package co.vine.android;

            import android.os.Bundle;
            import android.view.MenuItem;
            
            
    public class SyncControlsActivity extends BaseControllerActionBarActivity {
                private static final String SYNC_CONTROLS_TAG = "sync_controls";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, R.string.sync, (Boolean) true, (Boolean) true);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.getInt("color", 0) != 0) {
                setActionBarColor(extras.getInt("color"));
                    }
            if (savedInstanceState == null) {
                SyncControlsFragment frag = new SyncControlsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag, SYNC_CONTROLS_TAG).commit();
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
