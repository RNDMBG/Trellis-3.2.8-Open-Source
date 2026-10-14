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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ExploreVideoListActivity extends BaseTimelineActivity {
                private static final String FRAGMENT_TAG = "explore_video_list";
                private static final String QUERY_TITLE = "title";

                @Override // co.vine.android.BaseTimelineActivity
/* 28 */        protected BaseTimelineFragment getCurrentTimeLineFragment() {
/* 29 */            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 33 */        public void onCreate(Bundle savedInstanceState) {
/* 34 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
                    // This screen uses the Vine/fake toolbar. Hide the platform
                    // ActionBar so it does not stack a second back button above it.
                    android.app.ActionBar platformActionBar = getActionBar();
                    if (platformActionBar != null) {
                        platformActionBar.hide();
                    }
/* 35 */            Intent intent = getIntent();
/* 36 */            Uri data = getIntent().getData();
/* 37 */            if (data == null) {
/* 38 */                throw new IllegalAccessError("You can't access video list without specifying a url.");
                    }
/* 41 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 42 */            this.mShowCaptureIcon = false;
/* 44 */            ActionBar ab = getSupportActionBar();
/* 46 */            if ("tag".equals(data.getHost())) {
/* 47 */                ab.setTitle("#" + data.getLastPathSegment());
                    } else if (ExploreTimelineFragment.CATEGORY_ON_THE_RISE.equals(data.getHost())) {
/* 49 */                ab.setTitle(C0257R.string.on_the_rise);
                    } else if (ExploreTimelineFragment.CATEGORY_POPULAR_NOW.equals(data.getHost())) {
/* 51 */                ab.setTitle(C0257R.string.popular_now);
                    } else if ("timelines".equals(data.getHost())) {
/* 53 */                String title = data.getQueryParameter("title");
/* 54 */                if (!TextUtils.isEmpty(title)) {
/* 56 */                    ab.setTitle(title.replace('+', ' '));
                        } else {
/* 58 */                    ab.setTitle(getString(C0257R.string.tab_title_timeline));
                        }
                    } else {
/* 61 */                Toast.makeText(this, "All post is not supported at this moment, it's just here to tease you. ", 0).show();
/* 63 */                finish();
/* 77 */                return;
                    }
/* 67 */            if (savedInstanceState == null) {
/* 68 */                ExploreTimelineFragment fragment = new ExploreTimelineFragment();
/* 69 */                intent.putExtra("refresh", true);
/* 70 */                fragment.setArguments(ExploreTimelineFragment.prepareArguments(intent, true, data.getHost(), data.getLastPathSegment(), data));
/* 72 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
                }

/* 79 */        public static Intent getIntent(Context context, String category, String item) {
                    return new Intent(context, (Class<?>) ExploreVideoListActivity.class).setData(Uri.parse("vine://" + category + (item != null ? "/" + item : "")));
                }

/* 84 */        public static void start(Context context, Uri arbitraryTimelineUri) {
/* 85 */            Intent intent = new Intent(context, (Class<?>) ExploreVideoListActivity.class);
/* 86 */            intent.setData(arbitraryTimelineUri);
/* 87 */            context.startActivity(intent);
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseActionBarActivity, android.app.Activity
/* 91 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 92 */            getMenuInflater().inflate(C0257R.menu.mute, menu);
/* 93 */            return super.onCreateOptionsMenu(menu);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 97 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 98 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
/* 99 */            if (fragment instanceof BaseTimelineFragment) {
/* 100 */               fragment.onActivityResult(requestCode, resultCode, data);
                    }
/* 102 */           super.onActivityResult(requestCode, resultCode, data);
                }
            }
