            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ReshareActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_NETWORK = "network";
                public static final String NETWORK_FACEBOOK = "facebook";
                public static final String NETWORK_TWITTER = "twitter";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 19 */        public void onCreate(Bundle savedInstanceState) {
/* 20 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 22 */            Intent intent = getIntent();
/* 23 */            int titleResId = intent.getIntExtra("title", 0);
/* 25 */            setupActionBar((Boolean) true, Boolean.valueOf(titleResId > 0), titleResId, (Boolean) null, (Boolean) true);
/* 27 */            if (savedInstanceState == null) {
/* 28 */                ReshareFragment fragment = new ReshareFragment();
/* 29 */                fragment.setArguments(ReshareFragment.prepareArguments(intent));
/* 30 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, ReshareFragment.FRAGMENT_TAG).commit();
                    }
                }

/* 37 */        public static Intent getReshareIntent(Context context, String network, Bundle data) {
/* 38 */            Intent intent = new Intent(context, (Class<?>) ReshareActivity.class);
/* 39 */            intent.putExtras(data);
/* 40 */            intent.putExtra("network", network);
/* 41 */            if ("twitter".equals(network)) {
/* 42 */                intent.putExtra("title", C0257R.string.share_title_twitter);
                    } else if ("facebook".equals(network)) {
/* 44 */                intent.putExtra("title", C0257R.string.share_title_facebook);
                    }
/* 46 */            return intent;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 50 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 51 */            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ReshareFragment.FRAGMENT_TAG);
/* 52 */            if (fragment instanceof ReshareFragment) {
/* 53 */                fragment.onActivityResult(requestCode, resultCode, data);
                    } else {
/* 55 */                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
