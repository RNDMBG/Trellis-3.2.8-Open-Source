            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class SingleActivity extends BaseTimelineActivity {
                private static final String EXTRA_LOCKED = "locked";
                private static final String EXTRA_POST_ID = "post_id";
                private static final String EXTRA_SHARE_ID = "post_share_id";
                private static final String EXTRA_USERNAME = "username";
                private static final String EXTRA_USER_ID = "userId";
                private static final String FRAGMENT_TAG = "single_post";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 24 */        protected void onCreate(Bundle savedInstanceState) {
/* 25 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 26 */            setRequestedOrientation(1);
/* 28 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.timeline_post_title, (Boolean) true, (Boolean) true);
/* 30 */            Intent intent = getIntent();
/* 32 */            if (savedInstanceState == null) {
/* 33 */                Fragment fragment = new SingleFragment();
/* 34 */                intent.putExtra("take_focus", true);
/* 35 */                fragment.setArguments(BaseArrayListFragment.prepareArguments(intent, false));
/* 36 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
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

/* 54 */        public static Intent getIntent(Context context, long postId) {
/* 55 */            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
/* 56 */            i.putExtra("post_id", postId);
/* 57 */            return i;
                }

/* 60 */        public static void start(Context context, long postId) {
/* 61 */            context.startActivity(getIntent(context, postId));
                }

/* 64 */        public static void start(Context context, String shareId) {
/* 65 */            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
/* 66 */            i.putExtra("post_share_id", shareId);
/* 67 */            context.startActivity(i);
                }

/* 70 */        public static void startLocked(Context context, long postId, String username, long userId) {
/* 71 */            Intent i = new Intent(context, (Class<?>) SingleActivity.class);
/* 72 */            i.putExtra("post_id", postId);
/* 73 */            i.putExtra("locked", true);
/* 74 */            i.putExtra("username", username);
/* 75 */            i.putExtra("userId", userId);
/* 76 */            context.startActivity(i);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 80 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 81 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
/* 82 */            if (fragment instanceof BaseTimelineFragment) {
/* 83 */                fragment.onActivityResult(requestCode, resultCode, data);
                    }
/* 85 */            super.onActivityResult(requestCode, resultCode, data);
                }
            }
