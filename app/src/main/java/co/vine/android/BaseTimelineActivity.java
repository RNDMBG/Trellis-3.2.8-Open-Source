            package co.vine.android;

            import android.view.Menu;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.LoopManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public abstract class BaseTimelineActivity extends BaseControllerActionBarActivity {
                protected boolean mShowCaptureIcon = true;

                protected abstract BaseTimelineFragment getCurrentTimeLineFragment();

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 16 */        public void onBackPressed() {
                    BaseTimelineFragment fragment;
/* 17 */            if (!BuildUtil.isExplore() || (fragment = getCurrentTimeLineFragment()) == null || !fragment.onBackPressed()) {
/* 25 */                super.onBackPressed();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 29 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 30 */            if (this.mShowCaptureIcon && isAutoCaptureIconEnabled() && !BuildUtil.isExplore()) {
/* 31 */                getMenuInflater().inflate(C0257R.menu.capture, menu);
                    }
/* 33 */            return super.onCreateOptionsMenu(menu);
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 37 */        protected void onPause() {
/* 38 */            super.onPause();
/* 39 */            LoopManager.get(this).save();
                }

/* 42 */        protected boolean isAutoCaptureIconEnabled() {
/* 43 */            return true;
                }
            }
