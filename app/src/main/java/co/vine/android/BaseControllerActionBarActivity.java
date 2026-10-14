            package co.vine.android;

            import android.os.Build;
            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ConversationMenuHelper;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper;
            
            
    public abstract class BaseControllerActionBarActivity extends BaseActionBarActivity implements ScrollListener {
                public static final String EXTRA_TITLE_ID = "title";
                protected AppController mAppController;
                protected AppSessionListener mAppSessionListener;
                private ConversationMenuHelper mConversationSlidingMenuHelper;

        protected void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired) {
            onCreate(savedInstanceState, layout, loginRequired, false);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
            super.onCreate(savedInstanceState, layout, loginRequired, readOnly);
            this.mAppController = AppController.getInstance(this);
            if (this.mConversationSlidingMenuHelper != null) {
                this.mConversationSlidingMenuHelper.setupConversationSlidingMenu();
                    }
                }

                @Override 
        public void onScroll(int delta) {
                }

                @Override 
        protected void onResume() {
            super.onResume();
            if (this.mConversationSlidingMenuHelper != null) {
                this.mConversationSlidingMenuHelper.onResume();
                    }
            if (this.mAppSessionListener != null) {
                this.mAppController.addListener(this.mAppSessionListener);
                    }
                }

                @Override 
        protected void onPause() {
            super.onPause();
            if (this.mConversationSlidingMenuHelper != null) {
                this.mConversationSlidingMenuHelper.onPause();
                    }
            if (this.mAppSessionListener != null) {
                this.mAppController.removeListener(this.mAppSessionListener);
                    }
            if (Build.VERSION.SDK_INT >= 14 && !BuildUtil.isExplore()) {
                SdkVideoView.releaseStaticIfNeeded();
                    }
                }

        public AppController getAppController() {
            return this.mAppController;
                }

                @Override 
        protected SlidingActivityHelper createSlidingMenuHelper() {
            this.mConversationSlidingMenuHelper = new ConversationMenuHelper(this);
            return this.mConversationSlidingMenuHelper;
                }

        public boolean isConversationSideMenuEnabled() {
            return false;
                }

                @Override 
        protected boolean isSlidingMenuEnabled() {
            return isConversationSideMenuEnabled();
                }

        public ConversationMenuHelper getConversationSlidingMenuHelper() {
            return this.mConversationSlidingMenuHelper;
                }

        public void notifyColorChange(int profileBackground) {
            if (this.mConversationSlidingMenuHelper != null) {
                this.mConversationSlidingMenuHelper.setPersonalizedColor(profileBackground);
                    }
                }
            }
