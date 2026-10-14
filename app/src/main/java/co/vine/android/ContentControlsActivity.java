            package co.vine.android;

            import android.os.Bundle;
            import android.view.MenuItem;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ContentControlsActivity extends BaseControllerActionBarActivity {
                private static final String CONTENT_CONTROLS_TAG = "content_controls";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 14 */        protected void onCreate(Bundle savedInstanceState) {
/* 15 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 17 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.content_controls_title, (Boolean) true, (Boolean) true);
/* 19 */            Bundle extras = getIntent().getExtras();
/* 20 */            if (extras != null && extras.getInt("color", 0) != 0) {
/* 21 */                setActionBarColor(extras.getInt("color"));
                    }
/* 24 */            if (savedInstanceState == null) {
/* 25 */                ContentControlsFragment frag = new ContentControlsFragment();
/* 26 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, frag, CONTENT_CONTROLS_TAG).commit();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 34 */        public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
/* 37 */                    onBackPressed();
/* 38 */                    return true;
                        default:
/* 41 */                    return super.onOptionsItemSelected(item);
                    }
                }
            }
