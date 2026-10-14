            package co.vine.android.util.analytics;

            import android.app.Activity;
            import co.vine.android.plugin.BasePlugin;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class AnalyticsPlugin extends BasePlugin {
                private Boolean mIsStarted;

/* 14 */        public AnalyticsPlugin() {
/* 15 */            super("analytics");
                }

/* 18 */        public Boolean isStarted() {
/* 19 */            return this.mIsStarted;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 23 */        public void onStart(Activity activity) {
/* 24 */            AnalyticsManager.onStart(activity);
/* 25 */            this.mIsStarted = true;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 29 */        public void onStop(Activity activity) {
/* 30 */            AnalyticsManager.onStop(activity);
/* 31 */            this.mIsStarted = false;
                }
            }
