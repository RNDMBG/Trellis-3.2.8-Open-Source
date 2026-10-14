            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v7.app.ActionBar;
            import android.view.Menu;
            
            
    public class HashtagActivity extends BaseTimelineActivity {
                private static final String FRAGMENT_TAG = "hashtag";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, false);
            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
            Intent intent = getIntent();
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle("#" + intent.getStringExtra("tag"));
                    }
            if (savedInstanceState == null) {
                ExploreTimelineFragment explore = new ExploreTimelineFragment();
                Bundle b = ExploreTimelineFragment.prepareArguments(intent, false);
                b.putBoolean("refresh", true);
                explore.setArguments(b);
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, explore, FRAGMENT_TAG).commit();
                    }
                }

                @Override 
        protected BaseTimelineFragment getCurrentTimeLineFragment() {
            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(C0257R.menu.mute, menu);
            return super.onCreateOptionsMenu(menu);
                }

        public static void start(Context context, String tag) {
            Intent i = new Intent(context, (Class<?>) HashtagActivity.class).putExtra(ExploreTimelineFragment.ARG_CATEGORY, "tag").putExtra("tag", tag.substring(1, tag.length()));
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
