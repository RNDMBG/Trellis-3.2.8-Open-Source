            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Handler;
            import android.os.Looper;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import co.vine.android.plugin.Plugin;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            import java.util.Collection;
            
            
    public class BasePluginManager<T extends Plugin> implements PluginManager<T> {
                protected final Handler mHandler;
                protected int mNextPluginId;
                protected final ArrayList<T> mPlugins;

        public BasePluginManager() {
            this.mPlugins = new ArrayList<>();
            this.mHandler = new Handler(Looper.getMainLooper());
                }

                @Override 
        public int getDialogIdForPlugin(T plugin, int dialogId) {
            return (plugin.getId() << 10) + dialogId;
                }

        public BasePluginManager(Collection<T> plugins) {
            this();
            addPlugins(this.mNextPluginId, plugins);
                }

        public void addPlugins(int startId, Collection<T> plugins) {
            int id = startId;
                    for (T plugin : plugins) {
                id += plugin.onAdded(this, id);
                SLog.m466i("Plugin enabled: {}, id {}.", plugin.getName(), Integer.valueOf(plugin.getId()));
                    }
            this.mNextPluginId = id;
            this.mPlugins.addAll(plugins);
                }

        public void addPlugins(Collection<T> plugins) {
            addPlugins(this.mNextPluginId, plugins);
                }

        public void addPlugin(T plugin) {
            int id = this.mNextPluginId;
            int id2 = id + plugin.onAdded(this, id);
            SLog.m466i("Plugin enabled: {}, id {}.", plugin.getName(), Integer.valueOf(plugin.getId()));
            this.mNextPluginId = id2;
            this.mPlugins.add(plugin);
                }

        public void onResume(Activity activity) {
                    for (T plugin : this.mPlugins) {
                plugin.onResume(activity);
                    }
                }

        public void onPause() {
                    for (T plugin : this.mPlugins) {
                plugin.onPause();
                    }
                }

        public boolean onBackPressed() {
                    for (T plugin : this.mPlugins) {
                if (plugin.onBackPressed()) {
                    return true;
                        }
                    }
                    return false;
                }

        public void onDestroy() {
                    for (T plugin : this.mPlugins) {
                plugin.onDestroy();
                    }
                }

        public boolean onCreateOptionsMenu(Menu menu) {
                    for (T plugin : this.mPlugins) {
                if (plugin.onCreateOptionsMenu(menu)) {
                    return true;
                        }
                    }
                    return false;
                }

        public boolean onOptionsItemSelected(MenuItem item) {
                    for (T plugin : this.mPlugins) {
                if (plugin.onOptionsItemSelected(item)) {
                   return true;
                        }
                    }
                    return false;
                }

       public void onStart(Activity activity) {
                    for (T plugin : this.mPlugins) {
               plugin.onStart(activity);
                    }
                }

       public void onStop(Activity activity) {
                    for (T plugin : this.mPlugins) {
               plugin.onStop(activity);
                    }
                }

                @Override 
       public Handler getHandler() {
           return this.mHandler;
                }

       public boolean onKeyDown(int keyCode, KeyEvent event) {
                    for (T plugin : this.mPlugins) {
               if (plugin.onKeyDown(keyCode, event)) {
                   return true;
                        }
                    }
                    return false;
                }

       public boolean onKeyUp(int keyCode, KeyEvent event) {
                    for (T plugin : this.mPlugins) {
               if (plugin.onKeyUp(keyCode, event)) {
                   return true;
                        }
                    }
                    return false;
                }

       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    for (T plugin : this.mPlugins) {
               if (plugin.onActivityResult(activity, requestCode, resultCode, data)) {
                   return true;
                        }
                    }
                    return false;
                }

       public void onActivityCreated(Activity activity) {
                    for (T plugin : this.mPlugins) {
               plugin.onActivityCreated(activity);
                    }
                }

       public ArrayList<T> getEnabledPlugins() {
           return this.mPlugins;
                }
            }
