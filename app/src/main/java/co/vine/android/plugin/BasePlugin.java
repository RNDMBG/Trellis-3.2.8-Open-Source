            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Handler;
            import android.support.annotation.Nullable;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import java.lang.ref.WeakReference;
            
            
    public class BasePlugin implements Plugin {
                private WeakReference<Activity> mActivity;
                private int mId = -1;
                protected PluginManager mManager;
                public final String name;

        public BasePlugin(String name) {
            this.name = name;
                }

                @Override 
        public int getId() {
            return this.mId;
                }

                @Override 
        public String getName() {
            return this.name;
                }

                @Override 
        public int onAdded(PluginManager manager, int id) {
            this.mManager = manager;
            this.mId = id;
            return 1;
                }

                @Override 
        public Handler getHandler() {
            if (this.mManager != null) {
                        return this.mManager.getHandler();
                    }
                    return null;
                }

                @Override 
        public void onActivityCreated(Activity activity) {
            this.mActivity = new WeakReference<>(activity);
                }

                @Nullable
        public Activity getActivity() {
            if (this.mActivity != null) {
                        return this.mActivity.get();
                    }
                    return null;
                }

                @Override 
        public void onResume(Activity activity) {
                }

                @Override 
        public void onPause() {
                }

                @Override 
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return false;
                }

                @Override 
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return false;
                }

                @Override 
        public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            return false;
                }

                @Override 
        public boolean onBackPressed() {
            return false;
                }

                @Override 
        public void onDestroy() {
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
           return false;
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           return false;
                }

                @Override 
       public void onStart(Activity activity) {
                }

                @Override 
       public void onStop(Activity activity) {
                }
            }
