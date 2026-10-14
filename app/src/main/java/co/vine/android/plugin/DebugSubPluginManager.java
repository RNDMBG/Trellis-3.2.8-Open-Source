            package co.vine.android.plugin;

            import java.util.Collection;
            import twitter4j.conf.PropertyConfiguration;
            
            
    public class DebugSubPluginManager extends BasePluginManager<Plugin> implements Plugin {
                private int mId;
                private PluginManager mParent;
                private final String name = PropertyConfiguration.DEBUG;
                private final Collection<Plugin> plugins;

        public DebugSubPluginManager(Collection<Plugin> plugins) {
            this.plugins = plugins;
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
            super.addPlugins(id, this.plugins);
            this.mId = this.mNextPluginId;
            this.mParent = manager;
            return (this.mId - id) + 1;
                }

        public PluginManager getParent() {
            return this.mParent;
                }
            }
