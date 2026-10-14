package co.vine.android;

            import android.app.ActionBar;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class ProfileActivity extends BaseTimelineActivity {
                private static final String EXTRA_FOLLOW_EVENT_SOURCE = "event_source";
                private static final String EXTRA_SHOW_PROFILE_ACTIONS = "show_profile_actions";
                private static final String EXTRA_USER_ID = "user_id";
                private static final String EXTRA_VANITY_URL = "vanity_url";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 23 */        protected void onCreate(Bundle savedInstanceState) {
/* 24 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
                    // ProfileActivity uses the same fake Vine toolbar as the main
                    // timeline. Hide the platform bar so it cannot stack above it
                    // and introduce a differently styled back button.
                    ActionBar platformActionBar = getActionBar();
                    if (platformActionBar != null) {
                        platformActionBar.hide();
                    }
/* 25 */            setRequestedOrientation(1);
/* 27 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.profile_screen_title, (Boolean) true, (Boolean) true);
/* 29 */            Intent intent = getIntent();
/* 31 */            if (savedInstanceState == null) {
/* 32 */                ProfileFragment fragment = new ProfileFragment();
/* 33 */                intent.putExtra("refresh", true);
/* 34 */                intent.putExtra("take_focus", true);
/* 35 */                fragment.setArguments(BaseArrayListFragment.prepareArguments(intent, true));
/* 36 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, "profile").commit();
                    }
                }

                @Override // co.vine.android.BaseTimelineActivity
/* 44 */        protected BaseTimelineFragment getCurrentTimeLineFragment() {
/* 45 */            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag("profile");
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseActionBarActivity, android.app.Activity
/* 49 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 50 */            getMenuInflater().inflate(C0257R.menu.mute, menu);
/* 51 */            this.mShowCaptureIcon = false;
/* 52 */            return super.onCreateOptionsMenu(menu);
                }

/* 55 */        public static void start(Context context, long userId, String followEventSource) {
/* 56 */            start(context, userId, followEventSource, true);
                }

/* 60 */        public static void start(Context context, long userId, String followEventSource, boolean showProfileActions) {
/* 61 */            Intent i = new Intent(context, (Class<?>) ProfileActivity.class);
/* 62 */            i.putExtra("user_id", userId);
/* 63 */            i.putExtra("event_source", followEventSource);
/* 64 */            i.putExtra("show_profile_actions", showProfileActions);
/* 65 */            context.startActivity(i);
                }

/* 69 */        public static void start(Context context, ArrayList<String> pathSegments, String followEventSource) {
/* 70 */            Intent i = new Intent(context, (Class<?>) ProfileActivity.class);
/* 71 */            i.putStringArrayListExtra("vanity_url", pathSegments);
/* 72 */            i.putExtra("event_source", followEventSource);
/* 73 */            context.startActivity(i);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 77 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 78 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag("profile");
/* 79 */            if (fragment instanceof BaseTimelineFragment) {
/* 80 */                fragment.onActivityResult(requestCode, resultCode, data);
                    }
/* 82 */            super.onActivityResult(requestCode, resultCode, data);
                }
            }
