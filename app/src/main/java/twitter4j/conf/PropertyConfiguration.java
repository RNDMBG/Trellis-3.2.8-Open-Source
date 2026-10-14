            package twitter4j.conf;

            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.ObjectStreamException;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.Map;
            import java.util.Properties;
            import java.util.Set;
            import twitter4j.internal.util.z_T4JInternalStringUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public final class PropertyConfiguration extends ConfigurationBase implements Serializable {
                public static final String APPLICATION_ONLY_AUTH_ENABLED = "enableApplicationOnlyAuth";
                public static final String ASYNC_DISPATCHER_IMPL = "async.dispatcherImpl";
                public static final String ASYNC_NUM_THREADS = "async.numThreads";
                public static final String CLIENT_URL = "clientURL";
                public static final String CLIENT_VERSION = "clientVersion";
                public static final String CONTRIBUTING_TO = "contributingTo";
                public static final String DEBUG = "debug";
                public static final String HTTP_CONNECTION_TIMEOUT = "http.connectionTimeout";
                public static final String HTTP_DEFAULT_MAX_PER_ROUTE = "http.defaultMaxPerRoute";
                public static final String HTTP_GZIP = "http.gzip";
                public static final String HTTP_MAX_TOTAL_CONNECTIONS = "http.maxTotalConnections";
                public static final String HTTP_PRETTY_DEBUG = "http.prettyDebug";
                public static final String HTTP_PROXY_HOST = "http.proxyHost";
                public static final String HTTP_PROXY_HOST_FALLBACK = "http.proxyHost";
                public static final String HTTP_PROXY_PASSWORD = "http.proxyPassword";
                public static final String HTTP_PROXY_PORT = "http.proxyPort";
                public static final String HTTP_PROXY_PORT_FALLBACK = "http.proxyPort";
                public static final String HTTP_PROXY_USER = "http.proxyUser";
                public static final String HTTP_READ_TIMEOUT = "http.readTimeout";
                public static final String HTTP_RETRY_COUNT = "http.retryCount";
                public static final String HTTP_RETRY_INTERVAL_SECS = "http.retryIntervalSecs";
                public static final String HTTP_STREAMING_READ_TIMEOUT = "http.streamingReadTimeout";
                public static final String HTTP_USER_AGENT = "http.userAgent";
                public static final String HTTP_USE_SSL = "http.useSSL";
                public static final String INCLUDE_ENTITIES = "includeEntities";
                public static final String INCLUDE_MY_RETWEET = "includeMyRetweet";
                public static final String INCLUDE_RTS = "includeRTs";
                public static final String JSON_STORE_ENABLED = "jsonStoreEnabled";
                public static final String LOGGER_FACTORY = "loggerFactory";
                public static final String MBEAN_ENABLED = "mbeanEnabled";
                public static final String MEDIA_PROVIDER = "media.provider";
                public static final String MEDIA_PROVIDER_API_KEY = "media.providerAPIKey";
                public static final String MEDIA_PROVIDER_PARAMETERS = "media.providerParameters";
                public static final String OAUTH2_ACCESS_TOKEN = "oauth2.accessToken";
                public static final String OAUTH2_INVALIDATE_TOKEN_URL = "oauth2.invalidateTokenURL";
                public static final String OAUTH2_TOKEN_TYPE = "oauth2.tokenType";
                public static final String OAUTH2_TOKEN_URL = "oauth2.tokenURL";
                public static final String OAUTH_ACCESS_TOKEN = "oauth.accessToken";
                public static final String OAUTH_ACCESS_TOKEN_SECRET = "oauth.accessTokenSecret";
                public static final String OAUTH_ACCESS_TOKEN_URL = "oauth.accessTokenURL";
                public static final String OAUTH_AUTHENTICATION_URL = "oauth.authenticationURL";
                public static final String OAUTH_AUTHORIZATION_URL = "oauth.authorizationURL";
                public static final String OAUTH_CONSUMER_KEY = "oauth.consumerKey";
                public static final String OAUTH_CONSUMER_SECRET = "oauth.consumerSecret";
                public static final String OAUTH_REQUEST_TOKEN_URL = "oauth.requestTokenURL";
                public static final String PASSWORD = "password";
                public static final String REST_BASE_URL = "restBaseURL";
                public static final String SITE_STREAM_BASE_URL = "siteStreamBaseURL";
                public static final String STREAM_BASE_URL = "streamBaseURL";
                public static final String STREAM_STALL_WARNINGS_ENABLED = "stream.enableStallWarnings";
                public static final String STREAM_USER_REPLIES_ALL = "stream.user.repliesAll";
                public static final String USER = "user";
                public static final String USER_STREAM_BASE_URL = "userStreamBaseURL";
                private static final long serialVersionUID = 6458764415636588373L;







































/* 98 */        public PropertyConfiguration(InputStream is) {
/* 100 */           Properties props = new Properties();
/* 101 */           loadProperties(props, is);
/* 102 */           setFieldsWithTreePath(props, "/");
                }

/* 105 */       public PropertyConfiguration(Properties props) {
/* 106 */           this(props, "/");
                }

/* 109 */       public PropertyConfiguration(Properties props, String treePath) {
/* 111 */           setFieldsWithTreePath(props, treePath);
                }

/* 114 */       PropertyConfiguration(String treePath) {
                    Properties props;
                    try {
/* 119 */               props = (Properties) System.getProperties().clone();
                        try {
/* 121 */                   Map<String, String> envMap = System.getenv();
                            for (String key : envMap.keySet()) {
/* 123 */                       props.setProperty(key, envMap.get(key));
                            }
                        } catch (SecurityException e) {
                        }
/* 126 */               normalize(props);
                    } catch (SecurityException e2) {
/* 129 */               props = new Properties();
                    }
/* 133 */           loadProperties(props, "." + File.separatorChar + "twitter4j.properties");
/* 135 */           loadProperties(props, Configuration.class.getResourceAsStream("/twitter4j.properties"));
/* 137 */           loadProperties(props, Configuration.class.getResourceAsStream("/WEB-INF/twitter4j.properties"));
                    try {
/* 140 */               loadProperties(props, new FileInputStream("WEB-INF/twitter4j.properties"));
                    } catch (FileNotFoundException e3) {
                    } catch (SecurityException e4) {
                    }
/* 145 */           setFieldsWithTreePath(props, treePath);
                }

/* 151 */       PropertyConfiguration() {
/* 152 */           this("/");
                }

/* 155 */       private boolean notNull(Properties props, String prefix, String name) {
                    return props.getProperty(new StringBuilder().append(prefix).append(name).toString()) != null;
                }

/* 159 */       private boolean loadProperties(Properties props, String path) {
/* 160 */           FileInputStream fis = null;
                    try {
/* 162 */               File file = new File(path);
/* 163 */               if (!file.exists() || !file.isFile()) {
/* 172 */                   if (0 != 0) {
                                try {
/* 173 */                           fis.close();
                                } catch (IOException e) {
                                }
                            }
/* 179 */                   return false;
                        }
/* 164 */               FileInputStream fis2 = new FileInputStream(file);
                        try {
/* 165 */                   props.load(fis2);
/* 166 */                   normalize(props);
/* 172 */                   if (fis2 != null) {
                                try {
/* 173 */                           fis2.close();
                                } catch (IOException e2) {
                                }
                            }
/* 167 */                   return true;
                        } catch (Exception e3) {
                            fis = fis2;
                        } catch (Throwable th) {
/* 171 */                   th = th;
                            fis = fis2;
/* 172 */                   if (fis != null) {
                                try {
/* 173 */                           fis.close();
                                } catch (IOException e4) {
                                }
                            }
/* 177 */                   throw th;
                        }
                    } catch (Exception e5) {
                    } catch (Throwable th2) {
                        return false;
                    }
/* 172 */           if (fis != null) {
                        try {
/* 173 */                   fis.close();
                        } catch (IOException e6) {
                        }
                    }
/* 179 */           return false;
                }

/* 183 */       private boolean loadProperties(Properties props, InputStream is) {
                    try {
/* 184 */               props.load(is);
/* 185 */               normalize(props);
/* 186 */               return true;
                    } catch (Exception e) {
/* 189 */               return false;
                    }
                }

/* 192 */       private void normalize(Properties props) {
/* 193 */           Set keys = props.keySet();
/* 194 */           ArrayList<String> toBeNormalized = new ArrayList<>(10);
                    for (Object key : keys) {
/* 196 */               String keyStr = (String) key;
/* 197 */               if (-1 != keyStr.indexOf("twitter4j.")) {
/* 198 */                   toBeNormalized.add(keyStr);
                        }
                    }
                    for (String keyStr2 : toBeNormalized) {
/* 202 */               String property = props.getProperty(keyStr2);
/* 203 */               int index = keyStr2.indexOf("twitter4j.");
/* 204 */               String newKey = keyStr2.substring(0, index) + keyStr2.substring(index + 10);
/* 205 */               props.setProperty(newKey, property);
                    }
                }

/* 218 */       private void setFieldsWithTreePath(Properties props, String treePath) {
/* 219 */           setFieldsWithPrefix(props, "");
/* 220 */           String[] splitArray = z_T4JInternalStringUtil.split(treePath, "/");
/* 221 */           String prefix = null;
                    for (String split : splitArray) {
/* 223 */               if (!"".equals(split)) {
/* 224 */                   if (prefix == null) {
/* 225 */                       prefix = split + ".";
                            } else {
/* 227 */                       prefix = prefix + split + ".";
                            }
/* 229 */                   setFieldsWithPrefix(props, prefix);
                        }
                    }
                }

/* 234 */       private void setFieldsWithPrefix(Properties props, String prefix) {
/* 235 */           if (notNull(props, prefix, DEBUG)) {
/* 236 */               setDebug(getBoolean(props, prefix, DEBUG));
                    }
/* 239 */           if (notNull(props, prefix, "user")) {
/* 240 */               setUser(getString(props, prefix, "user"));
                    }
/* 242 */           if (notNull(props, prefix, "password")) {
/* 243 */               setPassword(getString(props, prefix, "password"));
                    }
/* 245 */           if (notNull(props, prefix, HTTP_USE_SSL)) {
/* 246 */               setUseSSL(getBoolean(props, prefix, HTTP_USE_SSL));
                    }
/* 248 */           if (notNull(props, prefix, HTTP_PRETTY_DEBUG)) {
/* 249 */               setPrettyDebugEnabled(getBoolean(props, prefix, HTTP_PRETTY_DEBUG));
                    }
/* 251 */           if (notNull(props, prefix, HTTP_GZIP)) {
/* 252 */               setGZIPEnabled(getBoolean(props, prefix, HTTP_GZIP));
                    }
/* 254 */           if (notNull(props, prefix, "http.proxyHost") || notNull(props, prefix, "http.proxyHost")) {
/* 257 */               setHttpProxyHost(getString(props, prefix, "http.proxyHost"));
                    }
/* 259 */           if (notNull(props, prefix, HTTP_PROXY_USER)) {
/* 260 */               setHttpProxyUser(getString(props, prefix, HTTP_PROXY_USER));
                    }
/* 262 */           if (notNull(props, prefix, HTTP_PROXY_PASSWORD)) {
/* 263 */               setHttpProxyPassword(getString(props, prefix, HTTP_PROXY_PASSWORD));
                    }
/* 265 */           if (notNull(props, prefix, "http.proxyPort") || notNull(props, prefix, "http.proxyPort")) {
/* 268 */               setHttpProxyPort(getIntProperty(props, prefix, "http.proxyPort"));
                    }
/* 270 */           if (notNull(props, prefix, HTTP_CONNECTION_TIMEOUT)) {
/* 271 */               setHttpConnectionTimeout(getIntProperty(props, prefix, HTTP_CONNECTION_TIMEOUT));
                    }
/* 273 */           if (notNull(props, prefix, HTTP_READ_TIMEOUT)) {
/* 274 */               setHttpReadTimeout(getIntProperty(props, prefix, HTTP_READ_TIMEOUT));
                    }
/* 276 */           if (notNull(props, prefix, HTTP_STREAMING_READ_TIMEOUT)) {
/* 277 */               setHttpStreamingReadTimeout(getIntProperty(props, prefix, HTTP_STREAMING_READ_TIMEOUT));
                    }
/* 279 */           if (notNull(props, prefix, HTTP_RETRY_COUNT)) {
/* 280 */               setHttpRetryCount(getIntProperty(props, prefix, HTTP_RETRY_COUNT));
                    }
/* 282 */           if (notNull(props, prefix, HTTP_RETRY_INTERVAL_SECS)) {
/* 283 */               setHttpRetryIntervalSeconds(getIntProperty(props, prefix, HTTP_RETRY_INTERVAL_SECS));
                    }
/* 285 */           if (notNull(props, prefix, HTTP_MAX_TOTAL_CONNECTIONS)) {
/* 286 */               setHttpMaxTotalConnections(getIntProperty(props, prefix, HTTP_MAX_TOTAL_CONNECTIONS));
                    }
/* 288 */           if (notNull(props, prefix, HTTP_DEFAULT_MAX_PER_ROUTE)) {
/* 289 */               setHttpDefaultMaxPerRoute(getIntProperty(props, prefix, HTTP_DEFAULT_MAX_PER_ROUTE));
                    }
/* 291 */           if (notNull(props, prefix, OAUTH_CONSUMER_KEY)) {
/* 292 */               setOAuthConsumerKey(getString(props, prefix, OAUTH_CONSUMER_KEY));
                    }
/* 294 */           if (notNull(props, prefix, OAUTH_CONSUMER_SECRET)) {
/* 295 */               setOAuthConsumerSecret(getString(props, prefix, OAUTH_CONSUMER_SECRET));
                    }
/* 297 */           if (notNull(props, prefix, OAUTH_ACCESS_TOKEN)) {
/* 298 */               setOAuthAccessToken(getString(props, prefix, OAUTH_ACCESS_TOKEN));
                    }
/* 300 */           if (notNull(props, prefix, OAUTH_ACCESS_TOKEN_SECRET)) {
/* 301 */               setOAuthAccessTokenSecret(getString(props, prefix, OAUTH_ACCESS_TOKEN_SECRET));
                    }
/* 303 */           if (notNull(props, prefix, OAUTH2_TOKEN_TYPE)) {
/* 304 */               setOAuth2TokenType(getString(props, prefix, OAUTH2_TOKEN_TYPE));
                    }
/* 306 */           if (notNull(props, prefix, OAUTH2_ACCESS_TOKEN)) {
/* 307 */               setOAuth2AccessToken(getString(props, prefix, OAUTH2_ACCESS_TOKEN));
                    }
/* 309 */           if (notNull(props, prefix, ASYNC_NUM_THREADS)) {
/* 310 */               setAsyncNumThreads(getIntProperty(props, prefix, ASYNC_NUM_THREADS));
                    }
/* 312 */           if (notNull(props, prefix, CONTRIBUTING_TO)) {
/* 313 */               setContributingTo(getLongProperty(props, prefix, CONTRIBUTING_TO));
                    }
/* 315 */           if (notNull(props, prefix, ASYNC_DISPATCHER_IMPL)) {
/* 316 */               setDispatcherImpl(getString(props, prefix, ASYNC_DISPATCHER_IMPL));
                    }
/* 318 */           if (notNull(props, prefix, CLIENT_VERSION)) {
/* 319 */               setClientVersion(getString(props, prefix, CLIENT_VERSION));
                    }
/* 321 */           if (notNull(props, prefix, CLIENT_URL)) {
/* 322 */               setClientURL(getString(props, prefix, CLIENT_URL));
                    }
/* 324 */           if (notNull(props, prefix, HTTP_USER_AGENT)) {
/* 325 */               setUserAgent(getString(props, prefix, HTTP_USER_AGENT));
                    }
/* 328 */           if (notNull(props, prefix, OAUTH_REQUEST_TOKEN_URL)) {
/* 329 */               setOAuthRequestTokenURL(getString(props, prefix, OAUTH_REQUEST_TOKEN_URL));
                    }
/* 332 */           if (notNull(props, prefix, OAUTH_AUTHORIZATION_URL)) {
/* 333 */               setOAuthAuthorizationURL(getString(props, prefix, OAUTH_AUTHORIZATION_URL));
                    }
/* 336 */           if (notNull(props, prefix, OAUTH_ACCESS_TOKEN_URL)) {
/* 337 */               setOAuthAccessTokenURL(getString(props, prefix, OAUTH_ACCESS_TOKEN_URL));
                    }
/* 340 */           if (notNull(props, prefix, OAUTH_AUTHENTICATION_URL)) {
/* 341 */               setOAuthAuthenticationURL(getString(props, prefix, OAUTH_AUTHENTICATION_URL));
                    }
/* 344 */           if (notNull(props, prefix, OAUTH2_TOKEN_URL)) {
/* 345 */               setOAuth2TokenURL(getString(props, prefix, OAUTH2_TOKEN_URL));
                    }
/* 348 */           if (notNull(props, prefix, OAUTH2_INVALIDATE_TOKEN_URL)) {
/* 349 */               setOAuth2InvalidateTokenURL(getString(props, prefix, OAUTH2_INVALIDATE_TOKEN_URL));
                    }
/* 352 */           if (notNull(props, prefix, REST_BASE_URL)) {
/* 353 */               setRestBaseURL(getString(props, prefix, REST_BASE_URL));
                    }
/* 356 */           if (notNull(props, prefix, STREAM_BASE_URL)) {
/* 357 */               setStreamBaseURL(getString(props, prefix, STREAM_BASE_URL));
                    }
/* 359 */           if (notNull(props, prefix, USER_STREAM_BASE_URL)) {
/* 360 */               setUserStreamBaseURL(getString(props, prefix, USER_STREAM_BASE_URL));
                    }
/* 362 */           if (notNull(props, prefix, SITE_STREAM_BASE_URL)) {
/* 363 */               setSiteStreamBaseURL(getString(props, prefix, SITE_STREAM_BASE_URL));
                    }
/* 365 */           if (notNull(props, prefix, INCLUDE_RTS)) {
/* 366 */               setIncludeRTsEnbled(getBoolean(props, prefix, INCLUDE_RTS));
                    }
/* 368 */           if (notNull(props, prefix, INCLUDE_ENTITIES)) {
/* 369 */               setIncludeEntitiesEnbled(getBoolean(props, prefix, INCLUDE_ENTITIES));
                    }
/* 371 */           if (notNull(props, prefix, INCLUDE_MY_RETWEET)) {
/* 372 */               setIncludeMyRetweetEnabled(getBoolean(props, prefix, INCLUDE_MY_RETWEET));
                    }
/* 374 */           if (notNull(props, prefix, LOGGER_FACTORY)) {
/* 375 */               setLoggerFactory(getString(props, prefix, LOGGER_FACTORY));
                    }
/* 377 */           if (notNull(props, prefix, JSON_STORE_ENABLED)) {
/* 378 */               setJSONStoreEnabled(getBoolean(props, prefix, JSON_STORE_ENABLED));
                    }
/* 380 */           if (notNull(props, prefix, MBEAN_ENABLED)) {
/* 381 */               setMBeanEnabled(getBoolean(props, prefix, MBEAN_ENABLED));
                    }
/* 383 */           if (notNull(props, prefix, STREAM_USER_REPLIES_ALL)) {
/* 384 */               setUserStreamRepliesAllEnabled(getBoolean(props, prefix, STREAM_USER_REPLIES_ALL));
                    }
/* 386 */           if (notNull(props, prefix, STREAM_STALL_WARNINGS_ENABLED)) {
/* 387 */               setStallWarningsEnabled(getBoolean(props, prefix, STREAM_STALL_WARNINGS_ENABLED));
                    }
/* 389 */           if (notNull(props, prefix, APPLICATION_ONLY_AUTH_ENABLED)) {
/* 390 */               setApplicationOnlyAuthEnabled(getBoolean(props, prefix, APPLICATION_ONLY_AUTH_ENABLED));
                    }
/* 392 */           if (notNull(props, prefix, MEDIA_PROVIDER)) {
/* 393 */               setMediaProvider(getString(props, prefix, MEDIA_PROVIDER));
                    }
/* 395 */           if (notNull(props, prefix, MEDIA_PROVIDER_API_KEY)) {
/* 396 */               setMediaProviderAPIKey(getString(props, prefix, MEDIA_PROVIDER_API_KEY));
                    }
/* 398 */           if (notNull(props, prefix, MEDIA_PROVIDER_PARAMETERS)) {
/* 399 */               String[] propsAry = z_T4JInternalStringUtil.split(getString(props, prefix, MEDIA_PROVIDER_PARAMETERS), "&");
/* 400 */               Properties p = new Properties();
                        for (String str : propsAry) {
/* 402 */                   String[] parameter = z_T4JInternalStringUtil.split(str, "=");
/* 403 */                   p.setProperty(parameter[0], parameter[1]);
                        }
/* 405 */               setMediaProviderParameters(p);
                    }
/* 407 */           cacheInstance();
                }

/* 410 */       protected boolean getBoolean(Properties props, String prefix, String name) {
/* 411 */           String value = props.getProperty(prefix + name);
/* 412 */           return Boolean.valueOf(value).booleanValue();
                }

/* 415 */       protected int getIntProperty(Properties props, String prefix, String name) {
/* 416 */           String value = props.getProperty(prefix + name);
                    try {
/* 418 */               return Integer.parseInt(value);
                    } catch (NumberFormatException e) {
/* 420 */               return -1;
                    }
                }

/* 424 */       protected long getLongProperty(Properties props, String prefix, String name) {
/* 425 */           String value = props.getProperty(prefix + name);
                    try {
/* 427 */               return Long.parseLong(value);
                    } catch (NumberFormatException e) {
/* 429 */               return -1L;
                    }
                }

/* 433 */       protected String getString(Properties props, String prefix, String name) {
/* 434 */           return props.getProperty(prefix + name);
                }

                @Override // twitter4j.conf.ConfigurationBase
/* 438 */       protected Object readResolve() throws ObjectStreamException {
/* 439 */           return super.readResolve();
                }
            }
