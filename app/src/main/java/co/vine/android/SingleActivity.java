            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            
            
    public class SingleActivity extends BaseTimelineActivity {
                private static final String EXTRA_LOCKED = "locked";
                private static final String EXTRA_POST_ID = "post_id";
                private static final String EXTRA_SHARE_ID = "post_share_id";
                private static final String EXTRA_USERNAME = "username";
                private static final String EXTRA_USER_ID = "userId";
                private static final String FRAGMENT_TAG = "single_post";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setRequestedOrientation(1);
            setupActionBar((Boolean) true, (Boolean) true, R.string.timeline_post_title, (Boolean) true, (Boolean) true);
            Intent intent = getIntent();
            if (savedInstanceState == null) {
                Fragment fragment = new SingleFragment();
                intent.putExtra("take_focus", true);
                fragment.setArguments(BaseArrayListFragment.prepareArguments(intent, false));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
                }

                @Override 
        protected BaseTimelineFragment getCurrentTimeLineFragment() {
            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.mute, menu);
            return super.onCreateOptionsMenu(menu);
                }

        public static Intent getIntent(Context context, long postId) {
            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
            i.putExtra("post_id", postId);
            return i;
                }

        public static void start(Context context, long postId) {
            context.startActivity(getIntent(context, postId));
                }

        public static void start(Context context, String shareId) {
            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
            i.putExtra("post_share_id", shareId);
            context.startActivity(i);
                }

        public static void startLocked(Context context, long postId, String username, long userId) {
            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
            i.putExtra("post_id", postId);
            i.putExtra("locked", true);
            i.putExtra("username", username);
            i.putExtra("userId", userId);
            context.startActivity(i);
                }

                @Override 
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (fragment instanceof BaseTimelineFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
                    }
            super.onActivityResult(requestCode, resultCode, data);
                }
            }
