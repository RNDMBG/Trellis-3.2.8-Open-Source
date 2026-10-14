            package twitter4j.conf;
            
            
    class PropertyConfigurationFactory implements ConfigurationFactory {
                private static final PropertyConfiguration ROOT_CONFIGURATION = new PropertyConfiguration();

        PropertyConfigurationFactory() {
                }

                @Override 
        public Configuration getInstance() {
            return ROOT_CONFIGURATION;
                }

                @Override 
        public Configuration getInstance(String configTreePath) {
            PropertyConfiguration conf = new PropertyConfiguration(configTreePath);
            return conf;
                }

                @Override 
        public void dispose() {
                }
            }
