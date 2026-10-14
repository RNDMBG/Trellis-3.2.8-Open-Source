            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            
            
    public class ReshareActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_NETWORK = "network";
                public static final String NETWORK_FACEBOOK = "facebook";
                public static final String NETWORK_TWITTER = "twitter";

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
            Intent intent = getIntent();
            int titleResId = intent.getIntExtra("title", 0);
            setupActionBar((Boolean) true, Boolean.valueOf(titleResId > 0), titleResId, (Boolean) null, (Boolean) true);
            if (savedInstanceState == null) {
                ReshareFragment fragment = new ReshareFragment();
                fragment.setArguments(ReshareFragment.prepareArguments(intent));
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, ReshareFragment.FRAGMENT_TAG).commit();
                    }
                }

        public static Intent getReshareIntent(Context context, String network, Bundle data) {
            Intent intent = new Intent(context, (Class<?>) ReshareActivity.class);
            intent.putExtras(data);
            intent.putExtra("network", network);
            if ("twitter".equals(network)) {
                intent.putExtra("title", C0257R.string.share_title_twitter);
                    } else if ("facebook".equals(network)) {
                intent.putExtra("title", C0257R.string.share_title_facebook);
                    }
            return intent;
                }

                @Override 
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ReshareFragment.FRAGMENT_TAG);
            if (fragment instanceof ReshareFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
                    } else {
                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
