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
            
            
    public class PostActivity extends BaseControllerActionBarActivity {
                public static final String AG_CAMERA_WAIT_TIME = "camera_wait";
                public static final String AG_COMBINE_WAIT_TIME = "combine_wait";
                public static final String AG_FROM_SONY = "f_s";
                public static final String AG_IS_RETRY = "is_retry";
                public static final String AG_PREVIEW_PATH = "preview_path";
                public static final String AG_THUMBNAIL_PATH = "pic_path";
                private static final String POST_TAG = "post";
                private PostFragment mFrag;

        public static Intent getIntent(Context context, String videoPath, String thumbnailPath, String previewPath, boolean isRetry, boolean isFromSony) {
            Intent intent = new Intent(context, (Class<?>) PostActivity.class);
            if (videoPath == null || thumbnailPath == null) {
                throw new IllegalStateException("Well, you should give two paths that are not null you know: " + videoPath + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + thumbnailPath);
                    }
            intent.setData(Uri.fromFile(new File(videoPath)));
            intent.putExtra("pic_path", thumbnailPath);
            intent.putExtra("preview_path", previewPath);
            intent.putExtra(AG_IS_RETRY, isRetry);
            intent.putExtra("f_s", isFromSony);
            intent.putExtra(AG_CAMERA_WAIT_TIME, BasicVineRecorder.sTimeTaken);
            intent.putExtra(AG_COMBINE_WAIT_TIME, RecordController.sMaxKnownStopTime);
            return intent;
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setRequestedOrientation(1);
            if (savedInstanceState == null) {
                setupActionBar((Boolean) true, (Boolean) true, R.string.share_title, (Boolean) true, (Boolean) true);
                Intent intent = getIntent();
                String path = intent.getData().getPath();
                PostFragment frag = new PostFragment();
                Bundle args = PostFragment.prepareArguments(intent, path, intent.getStringExtra("pic_path"), intent.getStringExtra("preview_path"), intent.getBooleanExtra(AG_IS_RETRY, false));
                SLog.m447d("Post activity started for {}.", path);
                frag.setArguments(args);
                BasicVineRecorder.sTimeTaken = intent.getLongExtra(AG_CAMERA_WAIT_TIME, 0L);
                RecordController.sMaxKnownStopTime = intent.getLongExtra(AG_COMBINE_WAIT_TIME, 0L);
                this.mFrag = frag;
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag, "post").commit();
                    }
                }

                @Override 
        public void onBackPressed() {
            FlurryUtils.trackBackFromPostScreen();
            super.onBackPressed();
                }

                @Override 
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (this.mFrag == null) {
                this.mFrag = (PostFragment) getSupportFragmentManager().findFragmentByTag("post");
                    }
            if (this.mFrag != null) {
                this.mFrag.onActivityResult(requestCode, resultCode, data);
                    } else {
                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
