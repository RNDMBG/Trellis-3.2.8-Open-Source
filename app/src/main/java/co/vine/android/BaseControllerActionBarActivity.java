            package co.vine.android;

            import android.os.Build;
            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ConversationMenuHelper;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public abstract class BaseControllerActionBarActivity extends BaseActionBarActivity implements ScrollListener {
                public static final String EXTRA_TITLE_ID = "title";
                protected AppController mAppController;
                protected AppSessionListener mAppSessionListener;
                private ConversationMenuHelper mConversationSlidingMenuHelper;

/* 25 */        protected void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired) {
/* 26 */            onCreate(savedInstanceState, layout, loginRequired, false);
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 29 */        public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
/* 30 */            super.onCreate(savedInstanceState, layout, loginRequired, readOnly);
/* 32 */            this.mAppController = AppController.getInstance(this);
/* 34 */            if (this.mConversationSlidingMenuHelper != null) {
/* 35 */                this.mConversationSlidingMenuHelper.setupConversationSlidingMenu();
                    }
                }

                @Override // co.vine.android.ScrollListener
/* 41 */        public void onScroll(int delta) {
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 45 */        protected void onResume() {
/* 46 */            super.onResume();
/* 47 */            if (this.mConversationSlidingMenuHelper != null) {
/* 48 */                this.mConversationSlidingMenuHelper.onResume();
                    }
/* 50 */            if (this.mAppSessionListener != null) {
/* 51 */                this.mAppController.addListener(this.mAppSessionListener);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 56 */        protected void onPause() {
/* 57 */            super.onPause();
/* 58 */            if (this.mConversationSlidingMenuHelper != null) {
/* 59 */                this.mConversationSlidingMenuHelper.onPause();
                    }
/* 61 */            if (this.mAppSessionListener != null) {
/* 62 */                this.mAppController.removeListener(this.mAppSessionListener);
                    }
/* 64 */            if (Build.VERSION.SDK_INT >= 14 && !BuildUtil.isExplore()) {
/* 65 */                SdkVideoView.releaseStaticIfNeeded();
                    }
                }

/* 69 */        public AppController getAppController() {
/* 70 */            return this.mAppController;
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 74 */        protected SlidingActivityHelper createSlidingMenuHelper() {
/* 75 */            this.mConversationSlidingMenuHelper = new ConversationMenuHelper(this);
/* 76 */            return this.mConversationSlidingMenuHelper;
                }

/* 79 */        public boolean isConversationSideMenuEnabled() {
/* 80 */            return false;
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 84 */        protected boolean isSlidingMenuEnabled() {
/* 85 */            return isConversationSideMenuEnabled();
                }

/* 88 */        public ConversationMenuHelper getConversationSlidingMenuHelper() {
/* 89 */            return this.mConversationSlidingMenuHelper;
                }

/* 92 */        public void notifyColorChange(int profileBackground) {
/* 93 */            if (this.mConversationSlidingMenuHelper != null) {
/* 94 */                this.mConversationSlidingMenuHelper.setPersonalizedColor(profileBackground);
                    }
                }
            }
