            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Handler;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Plugin {
                Handler getHandler();

                int getId();

                String getName();

                void onActivityCreated(Activity activity);

                boolean onActivityResult(Activity activity, int i, int i2, Intent intent);

                int onAdded(PluginManager pluginManager, int i);

                boolean onBackPressed();

                boolean onCreateOptionsMenu(Menu menu);

                void onDestroy();

                boolean onKeyDown(int i, KeyEvent keyEvent);

                boolean onKeyUp(int i, KeyEvent keyEvent);

                boolean onOptionsItemSelected(MenuItem menuItem);

                void onPause();

                void onResume(Activity activity);

                void onStart(Activity activity);

                void onStop(Activity activity);
            }
