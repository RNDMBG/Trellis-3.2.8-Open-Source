            package co.vine.android;

            import android.content.Intent;
            import android.os.Bundle;
            
            
    public class ChannelsListActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_CHANNEL = "channel";
                public static final String EXTRA_CHANNEL_ID = "channel_id";
                public static final String EXTRA_SELECTED_CHANNEL = "selected_channel";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, R.string.select_channel_title, (Boolean) true, (Boolean) true);
            if (savedInstanceState == null) {
                Intent intent = getIntent();
                ChannelsListFragment fragment = new ChannelsListFragment();
                long selectedChannel = intent.getLongExtra("selected_channel", -1L);
                intent.putExtra("refresh", false);
                intent.putExtra("take_focus", true);
                intent.putExtra("selected_channel", selectedChannel);
                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
                    }
                }
            }
