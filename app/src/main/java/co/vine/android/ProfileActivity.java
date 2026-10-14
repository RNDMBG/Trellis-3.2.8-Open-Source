package co.vine.android;

            import android.app.ActionBar;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            import java.util.ArrayList;
            
            
    public class ProfileActivity extends BaseTimelineActivity {
                private static final String EXTRA_FOLLOW_EVENT_SOURCE = "event_source";
                private static final String EXTRA_SHOW_PROFILE_ACTIONS = "show_profile_actions";
                private static final String EXTRA_USER_ID = "user_id";
                private static final String EXTRA_VANITY_URL = "vanity_url";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
                    
                    
                    
                    ActionBar platformActionBar = getActionBar();
                    if (platformActionBar != null) {
                        platformActionBar.hide();
                    }
            setRequestedOrientation(1);
            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.profile_screen_title, (Boolean) true, (Boolean) true);
            Intent intent = getIntent();
            if (savedInstanceState == null) {
                ProfileFragment fragment = new ProfileFragment();
                intent.putExtra("refresh", true);
                intent.putExtra("take_focus", true);
                fragment.setArguments(BaseArrayListFragment.prepareArguments(intent, true));
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, "profile").commit();
                    }
                }

                @Override 
        protected BaseTimelineFragment getCurrentTimeLineFragment() {
            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag("profile");
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(C0257R.menu.mute, menu);
            this.mShowCaptureIcon = false;
            return super.onCreateOptionsMenu(menu);
                }

        public static void start(Context context, long userId, String followEventSource) {
            start(context, userId, followEventSource, true);
                }

        public static void start(Context context, long userId, String followEventSource, boolean showProfileActions) {
            Intent i = new Intent(context, (Class<?>) ProfileActivity.class);
            i.putExtra("user_id", userId);
            i.putExtra("event_source", followEventSource);
            i.putExtra("show_profile_actions", showProfileActions);
            context.startActivity(i);
                }

        public static void start(Context context, ArrayList<String> pathSegments, String followEventSource) {
            Intent i = new Intent(context, (Class<?>) ProfileActivity.class);
            i.putStringArrayListExtra("vanity_url", pathSegments);
            i.putExtra("event_source", followEventSource);
            context.startActivity(i);
                }

                @Override 
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("profile");
            if (fragment instanceof BaseTimelineFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
                    }
            super.onActivityResult(requestCode, resultCode, data);
                }
            }
