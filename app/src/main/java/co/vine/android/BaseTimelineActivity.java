            package co.vine.android;

            import android.view.Menu;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.LoopManager;
            
            
    public abstract class BaseTimelineActivity extends BaseControllerActionBarActivity {
                protected boolean mShowCaptureIcon = true;

                protected abstract BaseTimelineFragment getCurrentTimeLineFragment();

                @Override 
        public void onBackPressed() {
                    BaseTimelineFragment fragment;
            if (!BuildUtil.isExplore() || (fragment = getCurrentTimeLineFragment()) == null || !fragment.onBackPressed()) {
                super.onBackPressed();
                    }
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            if (this.mShowCaptureIcon && isAutoCaptureIconEnabled() && !BuildUtil.isExplore()) {
                getMenuInflater().inflate(C0257R.menu.capture, menu);
                    }
            return super.onCreateOptionsMenu(menu);
                }

                @Override 
        protected void onPause() {
            super.onPause();
            LoopManager.get(this).save();
                }

        protected boolean isAutoCaptureIconEnabled() {
            return true;
                }
            }
