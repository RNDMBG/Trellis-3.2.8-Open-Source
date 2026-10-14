            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import com.googlecode.javacv.cpp.avformat;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class UploadsListActivity extends BaseControllerActionBarActivity {
                private Fragment mFragment;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 17 */        protected void onCreate(Bundle savedInstanceState) {
/* 18 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 20 */            setRequestedOrientation(1);
/* 22 */            Intent intent = getIntent();
/* 24 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.upload_list_title, (Boolean) false, (Boolean) true);
/* 26 */            if (savedInstanceState == null) {
/* 27 */                this.mFragment = new UploadsListFragment();
/* 28 */                intent.putExtra("take_focus", true);
/* 29 */                intent.putExtra("refresh", false);
/* 30 */                intent.putExtra("empty_desc", C0257R.string.no_pending_uploads);
/* 31 */                this.mFragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
/* 32 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, this.mFragment).commit();
                    }
                }

/* 39 */        public static void start(Context context) {
/* 40 */            Intent intent = new Intent(context, (Class<?>) UploadsListActivity.class);
/* 41 */            intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
/* 42 */            context.startActivity(intent);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 45 */        public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 46 */            if (this.mFragment != null) {
/* 47 */                this.mFragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
