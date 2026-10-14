            package co.vine.android.plugin;

            import android.os.Handler;
            import co.vine.android.plugin.Plugin;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PluginManager<T extends Plugin> {
                int getDialogIdForPlugin(T t, int i);

                Handler getHandler();
            }
