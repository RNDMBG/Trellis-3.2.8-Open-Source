            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import com.googlecode.javacv.cpp.avformat;
            
            
    public class UploadsListActivity extends BaseControllerActionBarActivity {
                private Fragment mFragment;

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setRequestedOrientation(1);
            Intent intent = getIntent();
            setupActionBar((Boolean) true, (Boolean) true, R.string.upload_list_title, (Boolean) false, (Boolean) true);
            if (savedInstanceState == null) {
                this.mFragment = new UploadsListFragment();
                intent.putExtra("take_focus", true);
                intent.putExtra("refresh", false);
                intent.putExtra("empty_desc", R.string.no_pending_uploads);
                this.mFragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.mFragment).commit();
                    }
                }

        public static void start(Context context) {
            Intent intent = new Intent(context, (Class<?>) UploadsListActivity.class);
            intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
            context.startActivity(intent);
                }

                @Override 
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (this.mFragment != null) {
                this.mFragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
