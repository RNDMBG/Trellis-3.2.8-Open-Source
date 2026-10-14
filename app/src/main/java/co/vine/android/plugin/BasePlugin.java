            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Handler;
            import android.support.annotation.Nullable;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class BasePlugin implements Plugin {
                private WeakReference<Activity> mActivity;
                private int mId = -1;
                protected PluginManager mManager;
                public final String name;

/* 23 */        public BasePlugin(String name) {
/* 25 */            this.name = name;
                }

                @Override // co.vine.android.plugin.Plugin
/* 30 */        public int getId() {
/* 31 */            return this.mId;
                }

                @Override // co.vine.android.plugin.Plugin
/* 35 */        public String getName() {
/* 36 */            return this.name;
                }

                @Override // co.vine.android.plugin.Plugin
/* 41 */        public int onAdded(PluginManager manager, int id) {
/* 42 */            this.mManager = manager;
/* 43 */            this.mId = id;
/* 44 */            return 1;
                }

                @Override // co.vine.android.plugin.Plugin
/* 49 */        public Handler getHandler() {
/* 50 */            if (this.mManager != null) {
                        return this.mManager.getHandler();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.Plugin
/* 54 */        public void onActivityCreated(Activity activity) {
/* 55 */            this.mActivity = new WeakReference<>(activity);
                }

                @Nullable
/* 59 */        public Activity getActivity() {
/* 60 */            if (this.mActivity != null) {
                        return this.mActivity.get();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.Plugin
/* 65 */        public void onResume(Activity activity) {
                }

                @Override // co.vine.android.plugin.Plugin
/* 70 */        public void onPause() {
                }

                @Override // co.vine.android.plugin.Plugin
/* 74 */        public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 75 */            return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 79 */        public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 80 */            return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 84 */        public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
/* 85 */            return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 89 */        public boolean onBackPressed() {
/* 90 */            return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 95 */        public void onDestroy() {
                }

                @Override // co.vine.android.plugin.Plugin
/* 99 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 100 */           return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 104 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 105 */           return false;
                }

                @Override // co.vine.android.plugin.Plugin
/* 110 */       public void onStart(Activity activity) {
                }

                @Override // co.vine.android.plugin.Plugin
/* 115 */       public void onStop(Activity activity) {
                }
            }
