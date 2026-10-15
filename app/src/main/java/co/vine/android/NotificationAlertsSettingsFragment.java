            package co.vine.android;

            import android.os.Bundle;
            import android.preference.PreferenceFragment;
            
            
    public class NotificationAlertsSettingsFragment extends PreferenceFragment {
                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.notification_preferences);
                }
            }
