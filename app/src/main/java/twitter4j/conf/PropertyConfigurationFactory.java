            package twitter4j.conf;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class PropertyConfigurationFactory implements ConfigurationFactory {
                private static final PropertyConfiguration ROOT_CONFIGURATION = new PropertyConfiguration();

/* 24 */        PropertyConfigurationFactory() {
                }

                @Override // twitter4j.conf.ConfigurationFactory
/* 38 */        public Configuration getInstance() {
/* 39 */            return ROOT_CONFIGURATION;
                }

                @Override // twitter4j.conf.ConfigurationFactory
/* 48 */        public Configuration getInstance(String configTreePath) {
/* 49 */            PropertyConfiguration conf = new PropertyConfiguration(configTreePath);
/* 51 */            return conf;
                }

                @Override // twitter4j.conf.ConfigurationFactory
/* 59 */        public void dispose() {
                }
            }
