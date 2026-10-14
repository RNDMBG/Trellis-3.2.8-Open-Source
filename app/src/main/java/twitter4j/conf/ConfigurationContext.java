            package twitter4j.conf;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class ConfigurationContext {
                public static final String CONFIGURATION_IMPL = "twitter4j.configurationFactory";
                public static final String DEFAULT_CONFIGURATION_FACTORY = "twitter4j.conf.PropertyConfigurationFactory";
                private static final ConfigurationFactory factory;

                static {
                    String CONFIG_IMPL;
                    try {
/* 34 */                CONFIG_IMPL = System.getProperty(CONFIGURATION_IMPL, DEFAULT_CONFIGURATION_FACTORY);
                    } catch (SecurityException e) {
/* 37 */                CONFIG_IMPL = DEFAULT_CONFIGURATION_FACTORY;
                    }
                    try {
/* 41 */                factory = (ConfigurationFactory) Class.forName(CONFIG_IMPL).newInstance();
                    } catch (ClassNotFoundException cnfe) {
/* 43 */                throw new AssertionError(cnfe);
                    } catch (IllegalAccessException iae) {
/* 47 */                throw new AssertionError(iae);
                    } catch (InstantiationException ie) {
/* 45 */                throw new AssertionError(ie);
                    }
                }

/* 52 */        public static Configuration getInstance() {
/* 53 */            return factory.getInstance();
                }

/* 56 */        public static Configuration getInstance(String configTreePath) {
/* 57 */            return factory.getInstance(configTreePath);
                }
            }
