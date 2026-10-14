            package co.vine.android.plugin;

            import java.util.Collection;
            import twitter4j.conf.PropertyConfiguration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class DebugSubPluginManager extends BasePluginManager<Plugin> implements Plugin {
                private int mId;
                private PluginManager mParent;
                private final String name = PropertyConfiguration.DEBUG;
                private final Collection<Plugin> plugins;

/* 15 */        public DebugSubPluginManager(Collection<Plugin> plugins) {
/* 18 */            this.plugins = plugins;
                }

                @Override // co.vine.android.plugin.Plugin
/* 22 */        public int getId() {
/* 23 */            return this.mId;
                }

                @Override // co.vine.android.plugin.Plugin
/* 27 */        public String getName() {
/* 28 */            return this.name;
                }

                @Override // co.vine.android.plugin.Plugin
/* 32 */        public int onAdded(PluginManager manager, int id) {
/* 33 */            super.addPlugins(id, this.plugins);
/* 34 */            this.mId = this.mNextPluginId;
/* 35 */            this.mParent = manager;
/* 36 */            return (this.mId - id) + 1;
                }

/* 39 */        public PluginManager getParent() {
/* 40 */            return this.mParent;
                }
            }
