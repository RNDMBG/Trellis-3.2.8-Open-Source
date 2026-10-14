            package co.vine.android;

            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class ChannelsListActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_CHANNEL = "channel";
                public static final String EXTRA_CHANNEL_ID = "channel_id";
                public static final String EXTRA_SELECTED_CHANNEL = "selected_channel";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 16 */        protected void onCreate(Bundle savedInstanceState) {
/* 17 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 18 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.select_channel_title, (Boolean) true, (Boolean) true);
/* 20 */            if (savedInstanceState == null) {
/* 21 */                Intent intent = getIntent();
/* 22 */                ChannelsListFragment fragment = new ChannelsListFragment();
/* 23 */                long selectedChannel = intent.getLongExtra("selected_channel", -1L);
/* 24 */                intent.putExtra("refresh", false);
/* 25 */                intent.putExtra("take_focus", true);
/* 26 */                intent.putExtra("selected_channel", selectedChannel);
/* 27 */                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
/* 28 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment).commit();
                    }
                }
            }
