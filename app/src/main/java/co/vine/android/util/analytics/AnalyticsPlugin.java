            package co.vine.android.util.analytics;

            import android.app.Activity;
            import co.vine.android.plugin.BasePlugin;
            
            
    public class AnalyticsPlugin extends BasePlugin {
                private Boolean mIsStarted;

        public AnalyticsPlugin() {
            super("analytics");
                }

        public Boolean isStarted() {
            return this.mIsStarted;
                }

                @Override 
        public void onStart(Activity activity) {
            AnalyticsManager.onStart(activity);
            this.mIsStarted = true;
                }

                @Override 
        public void onStop(Activity activity) {
            AnalyticsManager.onStop(activity);
            this.mIsStarted = false;
                }
            }
