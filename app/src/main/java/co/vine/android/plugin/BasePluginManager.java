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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class BasePluginManager<T extends Plugin> implements PluginManager<T> {
                protected final Handler mHandler;
                protected int mNextPluginId;
                protected final ArrayList<T> mPlugins;

/* 25 */        public BasePluginManager() {
/* 22 */            this.mPlugins = new ArrayList<>();
/* 27 */            this.mHandler = new Handler(Looper.getMainLooper());
                }

                @Override // co.vine.android.plugin.PluginManager
/* 30 */        public int getDialogIdForPlugin(T plugin, int dialogId) {
/* 31 */            return (plugin.getId() << 10) + dialogId;
                }

/* 34 */        public BasePluginManager(Collection<T> plugins) {
/* 35 */            this();
/* 36 */            addPlugins(this.mNextPluginId, plugins);
                }

/* 39 */        public void addPlugins(int startId, Collection<T> plugins) {
/* 40 */            int id = startId;
                    for (T plugin : plugins) {
/* 42 */                id += plugin.onAdded(this, id);
/* 43 */                SLog.m466i("Plugin enabled: {}, id {}.", plugin.getName(), Integer.valueOf(plugin.getId()));
                    }
/* 45 */            this.mNextPluginId = id;
/* 46 */            this.mPlugins.addAll(plugins);
                }

/* 49 */        public void addPlugins(Collection<T> plugins) {
/* 50 */            addPlugins(this.mNextPluginId, plugins);
                }

/* 53 */        public void addPlugin(T plugin) {
/* 54 */            int id = this.mNextPluginId;
/* 55 */            int id2 = id + plugin.onAdded(this, id);
/* 56 */            SLog.m466i("Plugin enabled: {}, id {}.", plugin.getName(), Integer.valueOf(plugin.getId()));
/* 57 */            this.mNextPluginId = id2;
/* 58 */            this.mPlugins.add(plugin);
                }

/* 61 */        public void onResume(Activity activity) {
                    for (T plugin : this.mPlugins) {
/* 63 */                plugin.onResume(activity);
                    }
                }

/* 67 */        public void onPause() {
                    for (T plugin : this.mPlugins) {
/* 69 */                plugin.onPause();
                    }
                }

/* 73 */        public boolean onBackPressed() {
                    for (T plugin : this.mPlugins) {
/* 75 */                if (plugin.onBackPressed()) {
/* 76 */                    return true;
                        }
                    }
                    return false;
                }

/* 82 */        public void onDestroy() {
                    for (T plugin : this.mPlugins) {
/* 84 */                plugin.onDestroy();
                    }
                }

/* 88 */        public boolean onCreateOptionsMenu(Menu menu) {
                    for (T plugin : this.mPlugins) {
/* 90 */                if (plugin.onCreateOptionsMenu(menu)) {
/* 91 */                    return true;
                        }
                    }
                    return false;
                }

/* 97 */        public boolean onOptionsItemSelected(MenuItem item) {
                    for (T plugin : this.mPlugins) {
/* 99 */                if (plugin.onOptionsItemSelected(item)) {
/* 100 */                   return true;
                        }
                    }
                    return false;
                }

/* 106 */       public void onStart(Activity activity) {
                    for (T plugin : this.mPlugins) {
/* 108 */               plugin.onStart(activity);
                    }
                }

/* 112 */       public void onStop(Activity activity) {
                    for (T plugin : this.mPlugins) {
/* 114 */               plugin.onStop(activity);
                    }
                }

                @Override // co.vine.android.plugin.PluginManager
/* 118 */       public Handler getHandler() {
/* 119 */           return this.mHandler;
                }

/* 122 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
                    for (T plugin : this.mPlugins) {
/* 124 */               if (plugin.onKeyDown(keyCode, event)) {
/* 125 */                   return true;
                        }
                    }
                    return false;
                }

/* 131 */       public boolean onKeyUp(int keyCode, KeyEvent event) {
                    for (T plugin : this.mPlugins) {
/* 133 */               if (plugin.onKeyUp(keyCode, event)) {
/* 134 */                   return true;
                        }
                    }
                    return false;
                }

/* 140 */       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    for (T plugin : this.mPlugins) {
/* 142 */               if (plugin.onActivityResult(activity, requestCode, resultCode, data)) {
/* 143 */                   return true;
                        }
                    }
                    return false;
                }

/* 149 */       public void onActivityCreated(Activity activity) {
                    for (T plugin : this.mPlugins) {
/* 151 */               plugin.onActivityCreated(activity);
                    }
                }

/* 155 */       public ArrayList<T> getEnabledPlugins() {
/* 156 */           return this.mPlugins;
                }
            }
