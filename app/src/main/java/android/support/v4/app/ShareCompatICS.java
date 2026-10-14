            package android.support.v4.app;

            import android.app.Activity;
            import android.content.Intent;
            import android.view.ActionProvider;
            import android.view.MenuItem;
            import android.widget.ShareActionProvider;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class ShareCompatICS {
                private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

/* 24 */        ShareCompatICS() {
                }

/* 28 */        public static void configureMenuItem(MenuItem item, Activity callingActivity, Intent intent) {
                    ShareActionProvider provider;
/* 29 */            ActionProvider itemProvider = item.getActionProvider();
/* 31 */            if (!(itemProvider instanceof ShareActionProvider)) {
/* 32 */                provider = new ShareActionProvider(callingActivity);
                    } else {
/* 34 */                provider = (ShareActionProvider) itemProvider;
                    }
/* 36 */            provider.setShareHistoryFileName(HISTORY_FILENAME_PREFIX + callingActivity.getClass().getName());
/* 38 */            provider.setShareIntent(intent);
/* 39 */            item.setActionProvider(provider);
                }
            }
