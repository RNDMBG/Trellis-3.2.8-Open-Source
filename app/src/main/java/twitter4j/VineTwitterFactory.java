            package twitter4j;

            import java.io.Serializable;
            import java.lang.reflect.Constructor;
            import java.lang.reflect.InvocationTargetException;
            import twitter4j.auth.AccessToken;
            import twitter4j.auth.Authorization;
            import twitter4j.auth.AuthorizationFactory;
            import twitter4j.auth.OAuthAuthorization;
            import twitter4j.conf.Configuration;
            import twitter4j.conf.ConfigurationContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class VineTwitterFactory implements Serializable {
                static final Authorization DEFAULT_AUTHORIZATION = AuthorizationFactory.getInstance(ConfigurationContext.getInstance());
                private static final Twitter SINGLETON;
                private static final Constructor<Twitter> TWITTER_CONSTRUCTOR;
                private static final long serialVersionUID = 5193900138477709155L;
                private final Configuration conf;

                static {
/* 30 */            String className = null;
/* 31 */            if (ConfigurationContext.getInstance().isGAE()) {
                        try {
/* 34 */                    Class.forName("twitter4j.AppEngineTwitterImpl");
/* 35 */                    className = "twitter4j.AppEngineTwitterImpl";
                        } catch (ClassNotFoundException e) {
                        }
                    }
/* 39 */            if (className == null) {
/* 40 */                className = "twitter4j.VineTwitter4j";
                    }
                    try {
/* 52 */                TWITTER_CONSTRUCTOR = (Constructor<Twitter>) (Constructor<?>) Class.forName(className).getDeclaredConstructor(Configuration.class, Authorization.class);
                        try {
                            SINGLETON = TWITTER_CONSTRUCTOR.newInstance(ConfigurationContext.getInstance(), DEFAULT_AUTHORIZATION);
                        } catch (IllegalAccessException e2) {
/* 59 */                    throw new AssertionError(e2);
                        } catch (InstantiationException e3) {
/* 57 */                    throw new AssertionError(e3);
                        } catch (InvocationTargetException e4) {
/* 61 */                    throw new AssertionError(e4);
                        }
                    } catch (ClassNotFoundException e5) {
/* 50 */                throw new AssertionError(e5);
                    } catch (NoSuchMethodException e6) {
/* 48 */                throw new AssertionError(e6);
                    }
                }

/* 68 */        public VineTwitterFactory() {
/* 69 */            this(ConfigurationContext.getInstance());
                }

/* 77 */        public VineTwitterFactory(Configuration conf) {
/* 79 */            if (conf == null) {
/* 80 */                throw new NullPointerException("configuration cannot be null");
                    }
/* 82 */            this.conf = conf;
                }

/* 90 */        public VineTwitterFactory(String configTreePath) {
/* 91 */            this(ConfigurationContext.getInstance(configTreePath));
                }

/* 99 */        public Twitter getInstance() {
/* 100 */           return getInstance(AuthorizationFactory.getInstance(this.conf));
                }

/* 112 */       public Twitter getInstance(AccessToken accessToken) {
/* 113 */           String consumerKey = this.conf.getOAuthConsumerKey();
/* 114 */           String consumerSecret = this.conf.getOAuthConsumerSecret();
/* 115 */           if (consumerKey == null && consumerSecret == null) {
/* 116 */               throw new IllegalStateException("Consumer key and Consumer secret not supplied.");
                    }
/* 118 */           OAuthAuthorization oauth = new OAuthAuthorization(this.conf);
/* 119 */           oauth.setOAuthAccessToken(accessToken);
/* 120 */           return getInstance(oauth);
                }

/* 124 */       public Twitter getInstance(Authorization auth) {
                    try {
                        return TWITTER_CONSTRUCTOR.newInstance(this.conf, auth);
                    } catch (IllegalAccessException e) {
/* 129 */               throw new AssertionError(e);
                    } catch (InstantiationException e2) {
/* 127 */               throw new AssertionError(e2);
                    } catch (InvocationTargetException e3) {
/* 131 */               throw new AssertionError(e3);
                    }
                }

/* 141 */       public static Twitter getSingleton() {
/* 142 */           return SINGLETON;
                }
            }
