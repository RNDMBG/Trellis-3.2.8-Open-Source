            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordController;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class PostActivity extends BaseControllerActionBarActivity {
                public static final String AG_CAMERA_WAIT_TIME = "camera_wait";
                public static final String AG_COMBINE_WAIT_TIME = "combine_wait";
                public static final String AG_FROM_SONY = "f_s";
                public static final String AG_IS_RETRY = "is_retry";
                public static final String AG_PREVIEW_PATH = "preview_path";
                public static final String AG_THUMBNAIL_PATH = "pic_path";
                private static final String POST_TAG = "post";
                private PostFragment mFrag;

/* 34 */        public static Intent getIntent(Context context, String videoPath, String thumbnailPath, String previewPath, boolean isRetry, boolean isFromSony) {
/* 35 */            Intent intent = new Intent(context, (Class<?>) PostActivity.class);
/* 36 */            if (videoPath == null || thumbnailPath == null) {
/* 37 */                throw new IllegalStateException("Well, you should give two paths that are not null you know: " + videoPath + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + thumbnailPath);
                    }
/* 40 */            intent.setData(Uri.fromFile(new File(videoPath)));
/* 41 */            intent.putExtra("pic_path", thumbnailPath);
/* 42 */            intent.putExtra("preview_path", previewPath);
/* 43 */            intent.putExtra(AG_IS_RETRY, isRetry);
/* 44 */            intent.putExtra("f_s", isFromSony);
/* 45 */            intent.putExtra(AG_CAMERA_WAIT_TIME, BasicVineRecorder.sTimeTaken);
/* 46 */            intent.putExtra(AG_COMBINE_WAIT_TIME, RecordController.sMaxKnownStopTime);
/* 47 */            return intent;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 51 */        protected void onCreate(Bundle savedInstanceState) {
/* 52 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 54 */            setRequestedOrientation(1);
/* 56 */            if (savedInstanceState == null) {
/* 58 */                setupActionBar((Boolean) true, (Boolean) true, C0257R.string.share_title, (Boolean) true, (Boolean) true);
/* 60 */                Intent intent = getIntent();
/* 61 */                String path = intent.getData().getPath();
/* 62 */                PostFragment frag = new PostFragment();
/* 64 */                Bundle args = PostFragment.prepareArguments(intent, path, intent.getStringExtra("pic_path"), intent.getStringExtra("preview_path"), intent.getBooleanExtra(AG_IS_RETRY, false));
/* 70 */                SLog.m447d("Post activity started for {}.", path);
/* 71 */                frag.setArguments(args);
/* 73 */                BasicVineRecorder.sTimeTaken = intent.getLongExtra(AG_CAMERA_WAIT_TIME, 0L);
/* 74 */                RecordController.sMaxKnownStopTime = intent.getLongExtra(AG_COMBINE_WAIT_TIME, 0L);
/* 76 */                this.mFrag = frag;
/* 77 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, frag, "post").commit();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 85 */        public void onBackPressed() {
/* 86 */            FlurryUtils.trackBackFromPostScreen();
/* 87 */            super.onBackPressed();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 91 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 92 */            if (this.mFrag == null) {
/* 93 */                this.mFrag = (PostFragment) getSupportFragmentManager().findFragmentByTag("post");
                    }
/* 95 */            if (this.mFrag != null) {
/* 96 */                this.mFrag.onActivityResult(requestCode, resultCode, data);
                    } else {
/* 98 */                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
