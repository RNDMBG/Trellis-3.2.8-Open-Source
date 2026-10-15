            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v7.app.ActionBar;
            import android.text.TextUtils;
            import android.view.Menu;
            import android.widget.Toast;
            
            
    public class ExploreVideoListActivity extends BaseTimelineActivity {
                private static final String FRAGMENT_TAG = "explore_video_list";
                private static final String QUERY_TITLE = "title";

                @Override 
        protected BaseTimelineFragment getCurrentTimeLineFragment() {
            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
                    
                    
                    android.app.ActionBar platformActionBar = getActionBar();
                    if (platformActionBar != null) {
                        platformActionBar.hide();
                    }
            Intent intent = getIntent();
            Uri data = getIntent().getData();
            if (data == null) {
                throw new IllegalAccessError("You can't access video list without specifying a url.");
                    }
            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
            this.mShowCaptureIcon = false;
            ActionBar ab = getSupportActionBar();
            if ("tag".equals(data.getHost())) {
                ab.setTitle("#" + data.getLastPathSegment());
                    } else if (ExploreTimelineFragment.CATEGORY_ON_THE_RISE.equals(data.getHost())) {
                ab.setTitle(R.string.on_the_rise);
                    } else if (ExploreTimelineFragment.CATEGORY_POPULAR_NOW.equals(data.getHost())) {
                ab.setTitle(R.string.popular_now);
                    } else if ("timelines".equals(data.getHost())) {
                String title = data.getQueryParameter("title");
                if (!TextUtils.isEmpty(title)) {
                    ab.setTitle(title.replace('+', ' '));
                        } else {
                    ab.setTitle(getString(R.string.tab_title_timeline));
                        }
                    } else {
                Toast.makeText(this, "All post is not supported at this moment, it's just here to tease you. ", 0).show();
                finish();
                return;
                    }
            if (savedInstanceState == null) {
                ExploreTimelineFragment fragment = new ExploreTimelineFragment();
                intent.putExtra("refresh", true);
                fragment.setArguments(ExploreTimelineFragment.prepareArguments(intent, true, data.getHost(), data.getLastPathSegment(), data));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
                }

        public static Intent getIntent(Context context, String category, String item) {
                    return new Intent(context, (Class<?>) ExploreVideoListActivity.class).setData(Uri.parse("vine://" + category + (item != null ? "/" + item : "")));
                }

        public static void start(Context context, Uri arbitraryTimelineUri) {
            Intent intent = new Intent(context, (Class<?>) ExploreVideoListActivity.class);
            intent.setData(arbitraryTimelineUri);
            context.startActivity(intent);
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.mute, menu);
            return super.onCreateOptionsMenu(menu);
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
