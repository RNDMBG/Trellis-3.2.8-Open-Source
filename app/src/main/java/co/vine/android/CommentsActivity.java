            package co.vine.android;

            import android.app.ActionBar;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.view.Menu;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            
            
    public class CommentsActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_HIDE_KEYBOARD = "hide_keyboard";
                public static final String EXTRA_POST_AUTHOR_ID = "post_author_id";
                public static final String EXTRA_POST_ID = "post_id";
                private static final String TAG = "commentsthread";

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.capture, menu);
            return super.onCreateOptionsMenu(menu);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            ActionBar platformActionBar = getActionBar();
            if (platformActionBar != null) {
                platformActionBar.hide();
                }
            if (this.mAppController == null) {
                this.mAppController = AppController.getInstance(this);
                CrashUtil.logException(new VineLoggingException(), "App controller is null here, and now? " + this.mAppController, new Object[0]);
                    }
            if (this.mAppController != null && this.mAppController.isLoggedIn()) {
                setupActionBar((Boolean) true, (Boolean) true, R.string.comments_title, (Boolean) true, (Boolean) true);
                Intent intent = getIntent();
                if (savedInstanceState == null) {
                    CommentsFragment fragment = new CommentsFragment();
                    Bundle args = CommentsFragment.prepareArguments(intent, false);
                    boolean hideKeyboard = intent.getBooleanExtra("hide_keyboard", false);
                    args.putLong("post_id", intent.getLongExtra("post_id", -1L));
                    args.putLong("repost_id", intent.getLongExtra("repost_id", -1L));
                    args.putLong("post_author_id", intent.getLongExtra("post_author_id", -1L));
                    args.putInt("empty_desc", R.string.comments_empty);
                    args.putBoolean("hide_keyboard", hideKeyboard);
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, TAG).commit();
                        }
                    }
                }

                @Override 
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

        public static void start(Context context, long postId, long repostId, long postAuthorId, boolean hideKeyboard) {
            Intent i = new Intent(context, (Class<?>) CommentsActivity.class);
            i.putExtra("post_id", postId);
            i.putExtra("repost_id", repostId);
            i.putExtra("post_author_id", postAuthorId);
            i.putExtra("hide_keyboard", hideKeyboard);
            context.startActivity(i);
                }
            }
