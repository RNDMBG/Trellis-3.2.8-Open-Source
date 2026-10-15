            package co.vine.android;

            import android.os.Bundle;
            
            
    public class NotificationSettingsActivity extends BaseControllerActionBarActivity {
                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, R.string.settings_notifications, (Boolean) true, (Boolean) true);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.getInt("color", 0) != 0) {
                setActionBarColor(extras.getInt("color"));
                    }
            if (savedInstanceState == null) {
                NotificationSettingsFragment fragment = new NotificationSettingsFragment();
                if (extras != null) {
                    fragment.setArguments(extras);
                        }
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
                    }
                }
            }
