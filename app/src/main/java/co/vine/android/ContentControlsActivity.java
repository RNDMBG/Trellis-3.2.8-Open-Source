            package co.vine.android;

            import android.os.Bundle;
            import android.view.MenuItem;
            
            
    public class ContentControlsActivity extends BaseControllerActionBarActivity {
                private static final String CONTENT_CONTROLS_TAG = "content_controls";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, R.string.content_controls_title, (Boolean) true, (Boolean) true);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.getInt("color", 0) != 0) {
                setActionBarColor(extras.getInt("color"));
                    }
            if (savedInstanceState == null) {
                ContentControlsFragment frag = new ContentControlsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag, CONTENT_CONTROLS_TAG).commit();
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
