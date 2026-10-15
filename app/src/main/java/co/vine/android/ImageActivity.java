            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            
            
    public class ImageActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_IMAGE_URL = "image_url";
                public static final String FRAGMENT_TAG = "image_fragment";

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setRequestedOrientation(1);
            Intent intent = getIntent();
            int titleResId = intent.getIntExtra("title", 0);
            if (titleResId == 0) {
                throw new IllegalArgumentException("An ActionBar title resourceId must be provided when starting this Activity.");
                    }
            setupActionBar((Boolean) true, (Boolean) true, titleResId, (Boolean) true, (Boolean) true);
            if (savedInstanceState == null) {
                ImageFragment fragment = new ImageFragment();
                intent.putExtra("take_focus", true);
                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
                }

        public static void start(Context context, String imageUrl, int titleResId) {
            Intent intent = new Intent(context, (Class<?>) ImageActivity.class);
            intent.putExtra("image_url", imageUrl);
            intent.putExtra("title", titleResId);
            context.startActivity(intent);
                }
            }
