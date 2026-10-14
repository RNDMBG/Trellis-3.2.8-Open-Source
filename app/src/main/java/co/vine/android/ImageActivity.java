            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class ImageActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_IMAGE_URL = "image_url";
                public static final String FRAGMENT_TAG = "image_fragment";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 20 */        protected void onCreate(Bundle savedInstanceState) {
/* 21 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 22 */            setRequestedOrientation(1);
/* 24 */            Intent intent = getIntent();
/* 26 */            int titleResId = intent.getIntExtra("title", 0);
/* 27 */            if (titleResId == 0) {
/* 28 */                throw new IllegalArgumentException("An ActionBar title resourceId must be provided when starting this Activity.");
                    }
/* 32 */            setupActionBar((Boolean) true, (Boolean) true, titleResId, (Boolean) true, (Boolean) true);
/* 34 */            if (savedInstanceState == null) {
/* 35 */                ImageFragment fragment = new ImageFragment();
/* 36 */                intent.putExtra("take_focus", true);
/* 37 */                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
/* 38 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
                }

/* 45 */        public static void start(Context context, String imageUrl, int titleResId) {
/* 46 */            Intent intent = new Intent(context, (Class<?>) ImageActivity.class);
/* 47 */            intent.putExtra("image_url", imageUrl);
/* 48 */            intent.putExtra("title", titleResId);
/* 49 */            context.startActivity(intent);
                }
            }
