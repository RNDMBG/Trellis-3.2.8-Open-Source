            package co.vine.android;

            import android.app.ActionBar;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class CommentsActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_HIDE_KEYBOARD = "hide_keyboard";
                public static final String EXTRA_POST_AUTHOR_ID = "post_author_id";
                public static final String EXTRA_POST_ID = "post_id";
                private static final String TAG = "commentsthread";

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 24 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 25 */            getMenuInflater().inflate(C0257R.menu.capture, menu);
/* 26 */            return super.onCreateOptionsMenu(menu);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 30 */        public void onCreate(Bundle savedInstanceState) {
/* 31 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 32 */            ActionBar platformActionBar = getActionBar();
/* 33 */            if (platformActionBar != null) {
/* 34 */                platformActionBar.hide();
                }
/* 33 */            if (this.mAppController == null) {
/* 34 */                this.mAppController = AppController.getInstance(this);
/* 35 */                CrashUtil.logException(new VineLoggingException(), "App controller is null here, and now? " + this.mAppController, new Object[0]);
                    }
/* 38 */            if (this.mAppController != null && this.mAppController.isLoggedIn()) {
/* 42 */                setupActionBar((Boolean) true, (Boolean) true, C0257R.string.comments_title, (Boolean) true, (Boolean) true);
/* 44 */                Intent intent = getIntent();
/* 46 */                if (savedInstanceState == null) {
/* 47 */                    CommentsFragment fragment = new CommentsFragment();
/* 48 */                    Bundle args = CommentsFragment.prepareArguments(intent, false);
/* 49 */                    boolean hideKeyboard = intent.getBooleanExtra("hide_keyboard", false);
/* 51 */                    args.putLong("post_id", intent.getLongExtra("post_id", -1L));
/* 53 */                    args.putLong("repost_id", intent.getLongExtra("repost_id", -1L));
/* 55 */                    args.putLong("post_author_id", intent.getLongExtra("post_author_id", -1L));
/* 57 */                    args.putInt("empty_desc", C0257R.string.comments_empty);
/* 58 */                    args.putBoolean("hide_keyboard", hideKeyboard);
/* 59 */                    fragment.setArguments(args);
/* 61 */                    getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, TAG).commit();
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 68 */        public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 69 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
/* 70 */            if (fragment != null) {
/* 71 */                fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

/* 75 */        public static void start(Context context, long postId, long repostId, long postAuthorId, boolean hideKeyboard) {
/* 76 */            Intent i = new Intent(context, (Class<?>) CommentsActivity.class);
/* 77 */            i.putExtra("post_id", postId);
/* 78 */            i.putExtra("repost_id", repostId);
/* 79 */            i.putExtra("post_author_id", postAuthorId);
/* 80 */            i.putExtra("hide_keyboard", hideKeyboard);
/* 81 */            context.startActivity(i);
                }
            }
