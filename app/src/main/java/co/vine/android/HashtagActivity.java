            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v7.app.ActionBar;
            import android.view.Menu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class HashtagActivity extends BaseTimelineActivity {
                private static final String FRAGMENT_TAG = "hashtag";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 18 */        protected void onCreate(Bundle savedInstanceState) {
/* 19 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, false);
/* 21 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 23 */            Intent intent = getIntent();
/* 25 */            ActionBar ab = getSupportActionBar();
/* 27 */            if (ab != null) {
/* 28 */                ab.setTitle("#" + intent.getStringExtra("tag"));
                    }
/* 31 */            if (savedInstanceState == null) {
/* 32 */                ExploreTimelineFragment explore = new ExploreTimelineFragment();
/* 33 */                Bundle b = ExploreTimelineFragment.prepareArguments(intent, false);
/* 34 */                b.putBoolean("refresh", true);
/* 35 */                explore.setArguments(b);
/* 36 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, explore, FRAGMENT_TAG).commit();
                    }
                }

                @Override // co.vine.android.BaseTimelineActivity
/* 44 */        protected BaseTimelineFragment getCurrentTimeLineFragment() {
/* 45 */            return (BaseTimelineFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseActionBarActivity, android.app.Activity
/* 49 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 50 */            getMenuInflater().inflate(C0257R.menu.mute, menu);
/* 51 */            return super.onCreateOptionsMenu(menu);
                }

/* 54 */        public static void start(Context context, String tag) {
/* 55 */            Intent i = new Intent(context, (Class<?>) HashtagActivity.class).putExtra(ExploreTimelineFragment.ARG_CATEGORY, "tag").putExtra("tag", tag.substring(1, tag.length()));
/* 59 */            context.startActivity(i);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 63 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 64 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
/* 65 */            if (fragment instanceof BaseTimelineFragment) {
/* 66 */                fragment.onActivityResult(requestCode, resultCode, data);
                    }
/* 68 */            super.onActivityResult(requestCode, resultCode, data);
                }
            }
