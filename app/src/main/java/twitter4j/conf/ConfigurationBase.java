            package twitter4j.conf;

            import co.vine.android.client.VineAPI;
            import java.io.ObjectStreamException;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            import java.util.Properties;
            import twitter4j.Version;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 130 */   class ConfigurationBase implements Configuration, Serializable {
                public static final String DALVIK = "twitter4j.dalvik";
                private static final String DEFAULT_OAUTH2_INVALIDATE_TOKEN_URL = "https://api.twitter.com/oauth2/invalidate_token";
                private static final String DEFAULT_OAUTH2_TOKEN_URL = "https://api.twitter.com/oauth2/token";
                private static final String DEFAULT_OAUTH_ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
                private static final String DEFAULT_OAUTH_AUTHENTICATION_URL = "https://api.twitter.com/oauth/authenticate";
                private static final String DEFAULT_OAUTH_AUTHORIZATION_URL = "https://api.twitter.com/oauth/authorize";
                private static final String DEFAULT_OAUTH_REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
                private static final String DEFAULT_REST_BASE_URL = "https://api.twitter.com/1.1/";
                private static final String DEFAULT_SITE_STREAM_BASE_URL = "https://sitestream.twitter.com/1.1/";
                private static final String DEFAULT_STREAM_BASE_URL = "https://stream.twitter.com/1.1/";
                private static final String DEFAULT_USER_STREAM_BASE_URL = "https://userstream.twitter.com/1.1/";
                public static final String GAE = "twitter4j.gae";
                static String dalvikDetected = null;
                static String gaeDetected = null;
                private static final List<ConfigurationBase> instances;
                private static final long serialVersionUID = -6610497517837844232L;
                private boolean IS_DALVIK;
                private boolean IS_GAE;
                private int asyncNumThreads;
                private String clientURL;
                private String clientVersion;
                private long contributingTo;
                private boolean debug;
                private int defaultMaxPerRoute;
                private String dispatcherImpl;
                private boolean gzipEnabled;
                private int httpConnectionTimeout;
                private String httpProxyHost;
                private String httpProxyPassword;
                private int httpProxyPort;
                private String httpProxyUser;
                private int httpReadTimeout;
                private int httpRetryCount;
                private int httpRetryIntervalSeconds;
                private int httpStreamingReadTimeout;
                private boolean jsonStoreEnabled;
                private String loggerFactory;
                private int maxTotalConnections;
                private boolean mbeanEnabled;
                private String mediaProvider;
                private String mediaProviderAPIKey;
                private Properties mediaProviderParameters;
                private String oAuth2AccessToken;
                private String oAuth2InvalidateTokenURL;
                private String oAuth2TokenType;
                private String oAuth2TokenURL;
                private String oAuthAccessToken;
                private String oAuthAccessTokenSecret;
                private String oAuthAccessTokenURL;
                private String oAuthAuthenticationURL;
                private String oAuthAuthorizationURL;
                private String oAuthConsumerKey;
                private String oAuthConsumerSecret;
                private String oAuthRequestTokenURL;
                private String password;
                private boolean prettyDebug;
                Map<String, String> requestHeaders;
                private String restBaseURL;
                private String siteStreamBaseURL;
                private boolean stallWarningsEnabled;
                private String streamBaseURL;
                private boolean useSSL;
                private String user;
                private String userAgent;
                private String userStreamBaseURL;
                private boolean userStreamRepliesAllEnabled;
                private boolean includeRTsEnabled = true;
                private boolean includeEntitiesEnabled = true;
                private boolean includeMyRetweetEnabled = true;
                private boolean trimUserEnabled = false;
                private boolean applicationOnlyAuthEnabled = false;

                static {
                    try {
/* 132 */               Class.forName("dalvik.system.VMRuntime");
/* 133 */               dalvikDetected = "true";
                    } catch (ClassNotFoundException e) {
/* 135 */               dalvikDetected = "false";
                    }
                    try {
/* 140 */               Class.forName("com.google.appengine.api.urlfetch.URLFetchService");
/* 141 */               gaeDetected = "true";
                    } catch (ClassNotFoundException e2) {
/* 143 */               gaeDetected = "false";
                    }
/* 975 */           instances = new ArrayList();
                }

/* 146 */       protected ConfigurationBase() {
                    String isDalvik;
                    String isGAE;
/* 148 */           setDebug(false);
/* 149 */           setUser(null);
/* 150 */           setPassword(null);
/* 151 */           setUseSSL(true);
/* 152 */           setPrettyDebugEnabled(false);
/* 153 */           setGZIPEnabled(true);
/* 154 */           setHttpProxyHost(null);
/* 155 */           setHttpProxyUser(null);
/* 156 */           setHttpProxyPassword(null);
/* 157 */           setHttpProxyPort(-1);
/* 158 */           setHttpConnectionTimeout(20000);
/* 159 */           setHttpReadTimeout(120000);
/* 160 */           setHttpStreamingReadTimeout(40000);
/* 161 */           setHttpRetryCount(0);
/* 162 */           setHttpRetryIntervalSeconds(5);
/* 163 */           setHttpMaxTotalConnections(20);
/* 164 */           setHttpDefaultMaxPerRoute(2);
/* 165 */           setOAuthConsumerKey(null);
/* 166 */           setOAuthConsumerSecret(null);
/* 167 */           setOAuthAccessToken(null);
/* 168 */           setOAuthAccessTokenSecret(null);
/* 169 */           setAsyncNumThreads(1);
/* 170 */           setContributingTo(-1L);
/* 171 */           setClientVersion(Version.getVersion());
/* 172 */           setClientURL("http://twitter4j.org/en/twitter4j-" + Version.getVersion() + ".xml");
/* 173 */           setUserAgent("twitter4j http://twitter4j.org/ /" + Version.getVersion());
/* 175 */           setJSONStoreEnabled(false);
/* 177 */           setMBeanEnabled(false);
/* 179 */           setOAuthRequestTokenURL(DEFAULT_OAUTH_REQUEST_TOKEN_URL);
/* 180 */           setOAuthAuthorizationURL(DEFAULT_OAUTH_AUTHORIZATION_URL);
/* 181 */           setOAuthAccessTokenURL(DEFAULT_OAUTH_ACCESS_TOKEN_URL);
/* 182 */           setOAuthAuthenticationURL(DEFAULT_OAUTH_AUTHENTICATION_URL);
/* 183 */           setOAuth2TokenURL(DEFAULT_OAUTH2_TOKEN_URL);
/* 184 */           setOAuth2InvalidateTokenURL(DEFAULT_OAUTH2_INVALIDATE_TOKEN_URL);
/* 186 */           setRestBaseURL(DEFAULT_REST_BASE_URL);
/* 187 */           setStreamBaseURL(DEFAULT_STREAM_BASE_URL);
/* 188 */           setUserStreamBaseURL(DEFAULT_USER_STREAM_BASE_URL);
/* 189 */           setSiteStreamBaseURL(DEFAULT_SITE_STREAM_BASE_URL);
/* 191 */           setDispatcherImpl("twitter4j.internal.async.DispatcherImpl");
/* 192 */           setLoggerFactory(null);
/* 194 */           setUserStreamRepliesAllEnabled(false);
/* 195 */           setStallWarningsEnabled(true);
                    try {
/* 198 */               isDalvik = System.getProperty(DALVIK, dalvikDetected);
                    } catch (SecurityException e) {
/* 201 */               isDalvik = dalvikDetected;
                    }
/* 203 */           this.IS_DALVIK = Boolean.valueOf(isDalvik).booleanValue();
                    try {
/* 207 */               isGAE = System.getProperty(GAE, gaeDetected);
                    } catch (SecurityException e2) {
/* 210 */               isGAE = gaeDetected;
                    }
/* 212 */           this.IS_GAE = Boolean.valueOf(isGAE).booleanValue();
/* 214 */           setMediaProvider("TWITTER");
/* 215 */           setMediaProviderAPIKey(null);
/* 216 */           setMediaProviderParameters(null);
                }

                @Override // twitter4j.conf.Configuration
/* 219 */       public final boolean isDalvik() {
/* 220 */           return this.IS_DALVIK;
                }

                @Override // twitter4j.conf.Configuration
/* 223 */       public boolean isGAE() {
/* 224 */           return this.IS_GAE;
                }

                @Override // twitter4j.conf.Configuration
/* 228 */       public final boolean isDebugEnabled() {
/* 229 */           return this.debug;
                }

/* 232 */       protected final void setDebug(boolean debug) {
/* 233 */           this.debug = debug;
                }

                @Override // twitter4j.conf.Configuration
/* 237 */       public final String getUserAgent() {
/* 238 */           return this.userAgent;
                }

/* 241 */       protected final void setUserAgent(String userAgent) {
/* 242 */           this.userAgent = userAgent;
/* 243 */           initRequestHeaders();
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 247 */       public final String getUser() {
/* 248 */           return this.user;
                }

/* 251 */       protected final void setUser(String user) {
/* 252 */           this.user = user;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 256 */       public final String getPassword() {
/* 257 */           return this.password;
                }

/* 260 */       protected final void setPassword(String password) {
/* 261 */           this.password = password;
                }

                @Override // twitter4j.internal.http.HttpClientConfiguration
/* 265 */       public boolean isPrettyDebugEnabled() {
/* 266 */           return this.prettyDebug;
                }

/* 269 */       protected final void setUseSSL(boolean useSSL) {
/* 270 */           this.useSSL = useSSL;
/* 271 */           fixRestBaseURL();
                }

/* 274 */       protected final void setPrettyDebugEnabled(boolean prettyDebug) {
/* 275 */           this.prettyDebug = prettyDebug;
                }

/* 278 */       protected final void setGZIPEnabled(boolean gzipEnabled) {
/* 279 */           this.gzipEnabled = gzipEnabled;
/* 280 */           initRequestHeaders();
                }

                @Override // twitter4j.internal.http.HttpClientConfiguration
/* 284 */       public boolean isGZIPEnabled() {
/* 285 */           return this.gzipEnabled;
                }

/* 291 */       private void initRequestHeaders() {
/* 292 */           this.requestHeaders = new HashMap();
/* 293 */           this.requestHeaders.put("X-Twitter-Client-Version", getClientVersion());
/* 294 */           this.requestHeaders.put("X-Twitter-Client-URL", getClientURL());
/* 295 */           this.requestHeaders.put("X-Twitter-Client", "Twitter4J");
/* 297 */           this.requestHeaders.put("User-Agent", getUserAgent());
/* 298 */           if (this.gzipEnabled) {
/* 299 */               this.requestHeaders.put("Accept-Encoding", "gzip");
                    }
/* 301 */           if (this.IS_DALVIK) {
/* 302 */               this.requestHeaders.put("Connection", "close");
                    }
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientWrapperConfiguration
/* 307 */       public Map<String, String> getRequestHeaders() {
/* 308 */           return this.requestHeaders;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 314 */       public final String getHttpProxyHost() {
/* 315 */           return this.httpProxyHost;
                }

/* 318 */       protected final void setHttpProxyHost(String proxyHost) {
/* 319 */           this.httpProxyHost = proxyHost;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 323 */       public final String getHttpProxyUser() {
/* 324 */           return this.httpProxyUser;
                }

/* 327 */       protected final void setHttpProxyUser(String proxyUser) {
/* 328 */           this.httpProxyUser = proxyUser;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 332 */       public final String getHttpProxyPassword() {
/* 333 */           return this.httpProxyPassword;
                }

/* 336 */       protected final void setHttpProxyPassword(String proxyPassword) {
/* 337 */           this.httpProxyPassword = proxyPassword;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 341 */       public final int getHttpProxyPort() {
/* 342 */           return this.httpProxyPort;
                }

/* 345 */       protected final void setHttpProxyPort(int proxyPort) {
/* 346 */           this.httpProxyPort = proxyPort;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 350 */       public final int getHttpConnectionTimeout() {
/* 351 */           return this.httpConnectionTimeout;
                }

/* 354 */       protected final void setHttpConnectionTimeout(int connectionTimeout) {
/* 355 */           this.httpConnectionTimeout = connectionTimeout;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 359 */       public final int getHttpReadTimeout() {
/* 360 */           return this.httpReadTimeout;
                }

/* 363 */       protected final void setHttpReadTimeout(int readTimeout) {
/* 364 */           this.httpReadTimeout = readTimeout;
                }

                @Override // twitter4j.conf.Configuration
/* 368 */       public int getHttpStreamingReadTimeout() {
/* 369 */           return this.httpStreamingReadTimeout;
                }

/* 372 */       protected final void setHttpStreamingReadTimeout(int httpStreamingReadTimeout) {
/* 373 */           this.httpStreamingReadTimeout = httpStreamingReadTimeout;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 378 */       public final int getHttpRetryCount() {
/* 379 */           return this.httpRetryCount;
                }

/* 382 */       protected final void setHttpRetryCount(int retryCount) {
/* 383 */           this.httpRetryCount = retryCount;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 387 */       public final int getHttpRetryIntervalSeconds() {
/* 388 */           return this.httpRetryIntervalSeconds;
                }

/* 391 */       protected final void setHttpRetryIntervalSeconds(int retryIntervalSeconds) {
/* 392 */           this.httpRetryIntervalSeconds = retryIntervalSeconds;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 396 */       public final int getHttpMaxTotalConnections() {
/* 397 */           return this.maxTotalConnections;
                }

/* 400 */       protected final void setHttpMaxTotalConnections(int maxTotalConnections) {
/* 401 */           this.maxTotalConnections = maxTotalConnections;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.internal.http.HttpClientConfiguration
/* 405 */       public final int getHttpDefaultMaxPerRoute() {
/* 406 */           return this.defaultMaxPerRoute;
                }

/* 409 */       protected final void setHttpDefaultMaxPerRoute(int defaultMaxPerRoute) {
/* 410 */           this.defaultMaxPerRoute = defaultMaxPerRoute;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 416 */       public final String getOAuthConsumerKey() {
/* 417 */           return this.oAuthConsumerKey;
                }

/* 420 */       protected final void setOAuthConsumerKey(String oAuthConsumerKey) {
/* 421 */           this.oAuthConsumerKey = oAuthConsumerKey;
/* 422 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 426 */       public final String getOAuthConsumerSecret() {
/* 427 */           return this.oAuthConsumerSecret;
                }

/* 430 */       protected final void setOAuthConsumerSecret(String oAuthConsumerSecret) {
/* 431 */           this.oAuthConsumerSecret = oAuthConsumerSecret;
/* 432 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 436 */       public String getOAuthAccessToken() {
/* 437 */           return this.oAuthAccessToken;
                }

/* 440 */       protected final void setOAuthAccessToken(String oAuthAccessToken) {
/* 441 */           this.oAuthAccessToken = oAuthAccessToken;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 445 */       public String getOAuthAccessTokenSecret() {
/* 446 */           return this.oAuthAccessTokenSecret;
                }

/* 449 */       protected final void setOAuthAccessTokenSecret(String oAuthAccessTokenSecret) {
/* 450 */           this.oAuthAccessTokenSecret = oAuthAccessTokenSecret;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 454 */       public String getOAuth2TokenType() {
/* 455 */           return this.oAuth2TokenType;
                }

/* 458 */       protected final void setOAuth2TokenType(String oAuth2TokenType) {
/* 459 */           this.oAuth2TokenType = oAuth2TokenType;
                }

                @Override // twitter4j.conf.Configuration, twitter4j.auth.AuthorizationConfiguration
/* 463 */       public String getOAuth2AccessToken() {
/* 464 */           return this.oAuth2AccessToken;
                }

/* 467 */       protected final void setOAuth2AccessToken(String oAuth2AccessToken) {
/* 468 */           this.oAuth2AccessToken = oAuth2AccessToken;
                }

                @Override // twitter4j.conf.Configuration
/* 472 */       public final int getAsyncNumThreads() {
/* 473 */           return this.asyncNumThreads;
                }

/* 476 */       protected final void setAsyncNumThreads(int asyncNumThreads) {
/* 477 */           this.asyncNumThreads = asyncNumThreads;
                }

                @Override // twitter4j.conf.Configuration
/* 481 */       public final long getContributingTo() {
/* 482 */           return this.contributingTo;
                }

/* 485 */       protected final void setContributingTo(long contributingTo) {
/* 486 */           this.contributingTo = contributingTo;
                }

                @Override // twitter4j.conf.Configuration
/* 490 */       public final String getClientVersion() {
/* 491 */           return this.clientVersion;
                }

/* 494 */       protected final void setClientVersion(String clientVersion) {
/* 495 */           this.clientVersion = clientVersion;
/* 496 */           initRequestHeaders();
                }

                @Override // twitter4j.conf.Configuration
/* 500 */       public final String getClientURL() {
/* 501 */           return this.clientURL;
                }

/* 504 */       protected final void setClientURL(String clientURL) {
/* 505 */           this.clientURL = clientURL;
/* 506 */           initRequestHeaders();
                }

                @Override // twitter4j.conf.Configuration
/* 510 */       public String getRestBaseURL() {
/* 511 */           return this.restBaseURL;
                }

/* 514 */       protected final void setRestBaseURL(String restBaseURL) {
/* 515 */           this.restBaseURL = restBaseURL;
/* 516 */           fixRestBaseURL();
                }

/* 519 */       private void fixRestBaseURL() {
/* 520 */           if (DEFAULT_REST_BASE_URL.equals(fixURL(false, this.restBaseURL))) {
/* 521 */               this.restBaseURL = fixURL(this.useSSL, this.restBaseURL);
                    }
/* 523 */           if (DEFAULT_OAUTH_ACCESS_TOKEN_URL.equals(fixURL(false, this.oAuthAccessTokenURL))) {
/* 524 */               this.oAuthAccessTokenURL = fixURL(this.useSSL, this.oAuthAccessTokenURL);
                    }
/* 526 */           if (DEFAULT_OAUTH_AUTHENTICATION_URL.equals(fixURL(false, this.oAuthAuthenticationURL))) {
/* 527 */               this.oAuthAuthenticationURL = fixURL(this.useSSL, this.oAuthAuthenticationURL);
                    }
/* 529 */           if (DEFAULT_OAUTH_AUTHORIZATION_URL.equals(fixURL(false, this.oAuthAuthorizationURL))) {
/* 530 */               this.oAuthAuthorizationURL = fixURL(this.useSSL, this.oAuthAuthorizationURL);
                    }
/* 532 */           if (DEFAULT_OAUTH_REQUEST_TOKEN_URL.equals(fixURL(false, this.oAuthRequestTokenURL))) {
/* 533 */               this.oAuthRequestTokenURL = fixURL(this.useSSL, this.oAuthRequestTokenURL);
                    }
                }

                @Override // twitter4j.conf.Configuration
/* 538 */       public String getStreamBaseURL() {
/* 539 */           return this.streamBaseURL;
                }

/* 542 */       protected final void setStreamBaseURL(String streamBaseURL) {
/* 543 */           this.streamBaseURL = streamBaseURL;
                }

                @Override // twitter4j.conf.Configuration
/* 547 */       public String getUserStreamBaseURL() {
/* 548 */           return this.userStreamBaseURL;
                }

/* 551 */       protected final void setUserStreamBaseURL(String siteStreamBaseURL) {
/* 552 */           this.userStreamBaseURL = siteStreamBaseURL;
                }

                @Override // twitter4j.conf.Configuration
/* 556 */       public String getSiteStreamBaseURL() {
/* 557 */           return this.siteStreamBaseURL;
                }

/* 560 */       protected final void setSiteStreamBaseURL(String siteStreamBaseURL) {
/* 561 */           this.siteStreamBaseURL = siteStreamBaseURL;
                }

                @Override // twitter4j.conf.Configuration
/* 565 */       public String getOAuthRequestTokenURL() {
/* 566 */           return this.oAuthRequestTokenURL;
                }

/* 569 */       protected final void setOAuthRequestTokenURL(String oAuthRequestTokenURL) {
/* 570 */           this.oAuthRequestTokenURL = oAuthRequestTokenURL;
/* 571 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 575 */       public String getOAuthAuthorizationURL() {
/* 576 */           return this.oAuthAuthorizationURL;
                }

/* 579 */       protected final void setOAuthAuthorizationURL(String oAuthAuthorizationURL) {
/* 580 */           this.oAuthAuthorizationURL = oAuthAuthorizationURL;
/* 581 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 585 */       public String getOAuthAccessTokenURL() {
/* 586 */           return this.oAuthAccessTokenURL;
                }

/* 589 */       protected final void setOAuthAccessTokenURL(String oAuthAccessTokenURL) {
/* 590 */           this.oAuthAccessTokenURL = oAuthAccessTokenURL;
/* 591 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 595 */       public String getOAuthAuthenticationURL() {
/* 596 */           return this.oAuthAuthenticationURL;
                }

/* 599 */       protected final void setOAuthAuthenticationURL(String oAuthAuthenticationURL) {
/* 600 */           this.oAuthAuthenticationURL = oAuthAuthenticationURL;
/* 601 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 605 */       public String getOAuth2TokenURL() {
/* 606 */           return this.oAuth2TokenURL;
                }

/* 609 */       protected final void setOAuth2TokenURL(String oAuth2TokenURL) {
/* 610 */           this.oAuth2TokenURL = oAuth2TokenURL;
/* 611 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 615 */       public String getOAuth2InvalidateTokenURL() {
/* 616 */           return this.oAuth2InvalidateTokenURL;
                }

/* 619 */       protected final void setOAuth2InvalidateTokenURL(String oAuth2InvalidateTokenURL) {
/* 620 */           this.oAuth2InvalidateTokenURL = oAuth2InvalidateTokenURL;
/* 621 */           fixRestBaseURL();
                }

                @Override // twitter4j.conf.Configuration
/* 625 */       public String getDispatcherImpl() {
/* 626 */           return this.dispatcherImpl;
                }

/* 629 */       protected final void setDispatcherImpl(String dispatcherImpl) {
/* 630 */           this.dispatcherImpl = dispatcherImpl;
                }

                @Override // twitter4j.conf.Configuration
/* 634 */       public String getLoggerFactory() {
/* 635 */           return this.loggerFactory;
                }

                @Override // twitter4j.conf.Configuration
/* 639 */       public boolean isIncludeRTsEnabled() {
/* 640 */           return this.includeRTsEnabled;
                }

                @Override // twitter4j.conf.Configuration
/* 644 */       public boolean isIncludeEntitiesEnabled() {
/* 645 */           return this.includeEntitiesEnabled;
                }

/* 648 */       protected final void setLoggerFactory(String loggerImpl) {
/* 649 */           this.loggerFactory = loggerImpl;
                }

/* 652 */       protected final void setIncludeRTsEnbled(boolean enabled) {
/* 653 */           this.includeRTsEnabled = enabled;
                }

/* 656 */       protected final void setIncludeEntitiesEnbled(boolean enabled) {
/* 657 */           this.includeEntitiesEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 660 */       public boolean isIncludeMyRetweetEnabled() {
/* 661 */           return this.includeMyRetweetEnabled;
                }

/* 664 */       public void setIncludeMyRetweetEnabled(boolean enabled) {
/* 665 */           this.includeMyRetweetEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 668 */       public boolean isTrimUserEnabled() {
/* 669 */           return this.trimUserEnabled;
                }

/* 672 */       public void setTrimUserEnabled(boolean enabled) {
/* 673 */           this.trimUserEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 676 */       public boolean isJSONStoreEnabled() {
/* 677 */           return this.jsonStoreEnabled;
                }

/* 680 */       protected final void setJSONStoreEnabled(boolean enabled) {
/* 681 */           this.jsonStoreEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 685 */       public boolean isMBeanEnabled() {
/* 686 */           return this.mbeanEnabled;
                }

/* 689 */       protected final void setMBeanEnabled(boolean enabled) {
/* 690 */           this.mbeanEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 694 */       public boolean isUserStreamRepliesAllEnabled() {
/* 695 */           return this.userStreamRepliesAllEnabled;
                }

/* 698 */       protected final void setUserStreamRepliesAllEnabled(boolean enabled) {
/* 699 */           this.userStreamRepliesAllEnabled = enabled;
                }

                @Override // twitter4j.conf.Configuration
/* 703 */       public boolean isStallWarningsEnabled() {
/* 704 */           return this.stallWarningsEnabled;
                }

/* 707 */       protected final void setStallWarningsEnabled(boolean stallWarningsEnabled) {
/* 708 */           this.stallWarningsEnabled = stallWarningsEnabled;
                }

                @Override // twitter4j.conf.Configuration
/* 712 */       public boolean isApplicationOnlyAuthEnabled() {
/* 713 */           return this.applicationOnlyAuthEnabled;
                }

/* 716 */       protected final void setApplicationOnlyAuthEnabled(boolean applicationOnlyAuthEnabled) {
/* 717 */           this.applicationOnlyAuthEnabled = applicationOnlyAuthEnabled;
                }

                @Override // twitter4j.conf.Configuration
/* 721 */       public String getMediaProvider() {
/* 722 */           return this.mediaProvider;
                }

/* 725 */       protected final void setMediaProvider(String mediaProvider) {
/* 726 */           this.mediaProvider = mediaProvider;
                }

                @Override // twitter4j.conf.Configuration
/* 730 */       public String getMediaProviderAPIKey() {
/* 731 */           return this.mediaProviderAPIKey;
                }

/* 734 */       protected final void setMediaProviderAPIKey(String mediaProviderAPIKey) {
/* 735 */           this.mediaProviderAPIKey = mediaProviderAPIKey;
                }

                @Override // twitter4j.conf.Configuration
/* 739 */       public Properties getMediaProviderParameters() {
/* 740 */           return this.mediaProviderParameters;
                }

/* 743 */       protected final void setMediaProviderParameters(Properties props) {
/* 744 */           this.mediaProviderParameters = props;
                }

/* 747 */       static String fixURL(boolean useSSL, String url) {
/* 748 */           if (url == null) {
/* 749 */               return null;
                    }
/* 751 */           int index = url.indexOf("://");
/* 752 */           if (-1 == index) {
/* 753 */               throw new IllegalArgumentException("url should contain '://'");
                    }
/* 755 */           String hostAndLater = url.substring(index + 3);
/* 756 */           if (useSSL) {
/* 757 */               return VineAPI.SCHEME_HTTPS + hostAndLater;
                    }
/* 759 */           return VineAPI.SCHEME_HTTP + hostAndLater;
                }

/* 764 */       public boolean equals(Object o) {
/* 765 */           if (this == o) {
                        return true;
                    }
/* 766 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 768 */           ConfigurationBase that = (ConfigurationBase) o;
/* 770 */           if (this.IS_DALVIK == that.IS_DALVIK && this.IS_GAE == that.IS_GAE && this.asyncNumThreads == that.asyncNumThreads && this.contributingTo == that.contributingTo && this.debug == that.debug && this.defaultMaxPerRoute == that.defaultMaxPerRoute && this.gzipEnabled == that.gzipEnabled && this.httpConnectionTimeout == that.httpConnectionTimeout && this.httpProxyPort == that.httpProxyPort && this.httpReadTimeout == that.httpReadTimeout && this.httpRetryCount == that.httpRetryCount && this.httpRetryIntervalSeconds == that.httpRetryIntervalSeconds && this.httpStreamingReadTimeout == that.httpStreamingReadTimeout && this.includeEntitiesEnabled == that.includeEntitiesEnabled && this.includeMyRetweetEnabled == that.includeMyRetweetEnabled && this.trimUserEnabled == that.trimUserEnabled && this.includeRTsEnabled == that.includeRTsEnabled && this.jsonStoreEnabled == that.jsonStoreEnabled && this.maxTotalConnections == that.maxTotalConnections && this.mbeanEnabled == that.mbeanEnabled && this.prettyDebug == that.prettyDebug && this.stallWarningsEnabled == that.stallWarningsEnabled && this.applicationOnlyAuthEnabled == that.applicationOnlyAuthEnabled && this.useSSL == that.useSSL && this.userStreamRepliesAllEnabled == that.userStreamRepliesAllEnabled) {
/* 795 */               if (this.clientURL == null ? that.clientURL != null : !this.clientURL.equals(that.clientURL)) {
                            return false;
                        }
/* 796 */               if (this.clientVersion == null ? that.clientVersion != null : !this.clientVersion.equals(that.clientVersion)) {
                            return false;
                        }
/* 798 */               if (this.dispatcherImpl == null ? that.dispatcherImpl != null : !this.dispatcherImpl.equals(that.dispatcherImpl)) {
                            return false;
                        }
/* 800 */               if (this.httpProxyHost == null ? that.httpProxyHost != null : !this.httpProxyHost.equals(that.httpProxyHost)) {
                            return false;
                        }
/* 802 */               if (this.httpProxyPassword == null ? that.httpProxyPassword != null : !this.httpProxyPassword.equals(that.httpProxyPassword)) {
                            return false;
                        }
/* 804 */               if (this.httpProxyUser == null ? that.httpProxyUser != null : !this.httpProxyUser.equals(that.httpProxyUser)) {
                            return false;
                        }
/* 806 */               if (this.loggerFactory == null ? that.loggerFactory != null : !this.loggerFactory.equals(that.loggerFactory)) {
                            return false;
                        }
/* 808 */               if (this.mediaProvider == null ? that.mediaProvider != null : !this.mediaProvider.equals(that.mediaProvider)) {
                            return false;
                        }
/* 810 */               if (this.mediaProviderAPIKey == null ? that.mediaProviderAPIKey != null : !this.mediaProviderAPIKey.equals(that.mediaProviderAPIKey)) {
                            return false;
                        }
/* 812 */               if (this.mediaProviderParameters == null ? that.mediaProviderParameters != null : !this.mediaProviderParameters.equals(that.mediaProviderParameters)) {
                            return false;
                        }
/* 814 */               if (this.oAuthAccessToken == null ? that.oAuthAccessToken != null : !this.oAuthAccessToken.equals(that.oAuthAccessToken)) {
                            return false;
                        }
/* 816 */               if (this.oAuthAccessTokenSecret == null ? that.oAuthAccessTokenSecret != null : !this.oAuthAccessTokenSecret.equals(that.oAuthAccessTokenSecret)) {
                            return false;
                        }
/* 818 */               if (this.oAuth2TokenType == null ? that.oAuth2TokenType != null : !this.oAuth2TokenType.equals(that.oAuth2TokenType)) {
                            return false;
                        }
/* 820 */               if (this.oAuth2AccessToken == null ? that.oAuth2AccessToken != null : !this.oAuth2AccessToken.equals(that.oAuth2AccessToken)) {
                            return false;
                        }
/* 822 */               if (this.oAuthAccessTokenURL == null ? that.oAuthAccessTokenURL != null : !this.oAuthAccessTokenURL.equals(that.oAuthAccessTokenURL)) {
                            return false;
                        }
/* 824 */               if (this.oAuthAuthenticationURL == null ? that.oAuthAuthenticationURL != null : !this.oAuthAuthenticationURL.equals(that.oAuthAuthenticationURL)) {
                            return false;
                        }
/* 826 */               if (this.oAuthAuthorizationURL == null ? that.oAuthAuthorizationURL != null : !this.oAuthAuthorizationURL.equals(that.oAuthAuthorizationURL)) {
                            return false;
                        }
/* 828 */               if (this.oAuth2TokenURL == null ? that.oAuth2TokenURL != null : !this.oAuth2TokenURL.equals(that.oAuth2TokenURL)) {
                            return false;
                        }
/* 830 */               if (this.oAuth2InvalidateTokenURL == null ? that.oAuth2InvalidateTokenURL != null : !this.oAuth2InvalidateTokenURL.equals(that.oAuth2InvalidateTokenURL)) {
                            return false;
                        }
/* 832 */               if (this.oAuthConsumerKey == null ? that.oAuthConsumerKey != null : !this.oAuthConsumerKey.equals(that.oAuthConsumerKey)) {
                            return false;
                        }
/* 834 */               if (this.oAuthConsumerSecret == null ? that.oAuthConsumerSecret != null : !this.oAuthConsumerSecret.equals(that.oAuthConsumerSecret)) {
                            return false;
                        }
/* 836 */               if (this.oAuthRequestTokenURL == null ? that.oAuthRequestTokenURL != null : !this.oAuthRequestTokenURL.equals(that.oAuthRequestTokenURL)) {
                            return false;
                        }
/* 838 */               if (this.password == null ? that.password != null : !this.password.equals(that.password)) {
                            return false;
                        }
/* 839 */               if (this.requestHeaders == null ? that.requestHeaders != null : !this.requestHeaders.equals(that.requestHeaders)) {
                            return false;
                        }
/* 841 */               if (this.restBaseURL == null ? that.restBaseURL != null : !this.restBaseURL.equals(that.restBaseURL)) {
                            return false;
                        }
/* 842 */               if (this.siteStreamBaseURL == null ? that.siteStreamBaseURL != null : !this.siteStreamBaseURL.equals(that.siteStreamBaseURL)) {
                            return false;
                        }
/* 844 */               if (this.streamBaseURL == null ? that.streamBaseURL != null : !this.streamBaseURL.equals(that.streamBaseURL)) {
                            return false;
                        }
/* 846 */               if (this.user == null ? that.user != null : !this.user.equals(that.user)) {
                            return false;
                        }
/* 847 */               if (this.userAgent == null ? that.userAgent != null : !this.userAgent.equals(that.userAgent)) {
                            return false;
                        }
/* 848 */               if (this.userStreamBaseURL != null) {
                            if (this.userStreamBaseURL.equals(that.userStreamBaseURL)) {
                                return true;
                            }
                        } else if (that.userStreamBaseURL == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 855 */       public int hashCode() {
/* 856 */           int result = this.debug ? 1 : 0;
/* 910 */           return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + (this.userAgent != null ? this.userAgent.hashCode() : 0)) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + (this.password != null ? this.password.hashCode() : 0)) * 31) + (this.useSSL ? 1 : 0)) * 31) + (this.prettyDebug ? 1 : 0)) * 31) + (this.gzipEnabled ? 1 : 0)) * 31) + (this.httpProxyHost != null ? this.httpProxyHost.hashCode() : 0)) * 31) + (this.httpProxyUser != null ? this.httpProxyUser.hashCode() : 0)) * 31) + (this.httpProxyPassword != null ? this.httpProxyPassword.hashCode() : 0)) * 31) + this.httpProxyPort) * 31) + this.httpConnectionTimeout) * 31) + this.httpReadTimeout) * 31) + this.httpStreamingReadTimeout) * 31) + this.httpRetryCount) * 31) + this.httpRetryIntervalSeconds) * 31) + this.maxTotalConnections) * 31) + this.defaultMaxPerRoute) * 31) + (this.oAuthConsumerKey != null ? this.oAuthConsumerKey.hashCode() : 0)) * 31) + (this.oAuthConsumerSecret != null ? this.oAuthConsumerSecret.hashCode() : 0)) * 31) + (this.oAuthAccessToken != null ? this.oAuthAccessToken.hashCode() : 0)) * 31) + (this.oAuthAccessTokenSecret != null ? this.oAuthAccessTokenSecret.hashCode() : 0)) * 31) + (this.oAuth2TokenType != null ? this.oAuth2TokenType.hashCode() : 0)) * 31) + (this.oAuth2AccessToken != null ? this.oAuth2AccessToken.hashCode() : 0)) * 31) + (this.oAuthRequestTokenURL != null ? this.oAuthRequestTokenURL.hashCode() : 0)) * 31) + (this.oAuthAuthorizationURL != null ? this.oAuthAuthorizationURL.hashCode() : 0)) * 31) + (this.oAuthAccessTokenURL != null ? this.oAuthAccessTokenURL.hashCode() : 0)) * 31) + (this.oAuthAuthenticationURL != null ? this.oAuthAuthenticationURL.hashCode() : 0)) * 31) + (this.oAuth2TokenURL != null ? this.oAuth2TokenURL.hashCode() : 0)) * 31) + (this.oAuth2InvalidateTokenURL != null ? this.oAuth2InvalidateTokenURL.hashCode() : 0)) * 31) + (this.restBaseURL != null ? this.restBaseURL.hashCode() : 0)) * 31) + (this.streamBaseURL != null ? this.streamBaseURL.hashCode() : 0)) * 31) + (this.userStreamBaseURL != null ? this.userStreamBaseURL.hashCode() : 0)) * 31) + (this.siteStreamBaseURL != null ? this.siteStreamBaseURL.hashCode() : 0)) * 31) + (this.dispatcherImpl != null ? this.dispatcherImpl.hashCode() : 0)) * 31) + (this.loggerFactory != null ? this.loggerFactory.hashCode() : 0)) * 31) + this.asyncNumThreads) * 31) + ((int) (this.contributingTo ^ (this.contributingTo >>> 32)))) * 31) + (this.includeRTsEnabled ? 1 : 0)) * 31) + (this.includeEntitiesEnabled ? 1 : 0)) * 31) + (this.includeMyRetweetEnabled ? 1 : 0)) * 31) + (this.trimUserEnabled ? 1 : 0)) * 31) + (this.jsonStoreEnabled ? 1 : 0)) * 31) + (this.mbeanEnabled ? 1 : 0)) * 31) + (this.userStreamRepliesAllEnabled ? 1 : 0)) * 31) + (this.stallWarningsEnabled ? 1 : 0)) * 31) + (this.applicationOnlyAuthEnabled ? 1 : 0)) * 31) + (this.mediaProvider != null ? this.mediaProvider.hashCode() : 0)) * 31) + (this.mediaProviderAPIKey != null ? this.mediaProviderAPIKey.hashCode() : 0)) * 31) + (this.mediaProviderParameters != null ? this.mediaProviderParameters.hashCode() : 0)) * 31) + (this.clientVersion != null ? this.clientVersion.hashCode() : 0)) * 31) + (this.clientURL != null ? this.clientURL.hashCode() : 0)) * 31) + (this.IS_DALVIK ? 1 : 0)) * 31) + (this.IS_GAE ? 1 : 0)) * 31) + (this.requestHeaders != null ? this.requestHeaders.hashCode() : 0);
                }

/* 915 */       public String toString() {
/* 916 */           return "ConfigurationBase{debug=" + this.debug + ", userAgent='" + this.userAgent + "', user='" + this.user + "', password='" + this.password + "', useSSL=" + this.useSSL + ", prettyDebug=" + this.prettyDebug + ", gzipEnabled=" + this.gzipEnabled + ", httpProxyHost='" + this.httpProxyHost + "', httpProxyUser='" + this.httpProxyUser + "', httpProxyPassword='" + this.httpProxyPassword + "', httpProxyPort=" + this.httpProxyPort + ", httpConnectionTimeout=" + this.httpConnectionTimeout + ", httpReadTimeout=" + this.httpReadTimeout + ", httpStreamingReadTimeout=" + this.httpStreamingReadTimeout + ", httpRetryCount=" + this.httpRetryCount + ", httpRetryIntervalSeconds=" + this.httpRetryIntervalSeconds + ", maxTotalConnections=" + this.maxTotalConnections + ", defaultMaxPerRoute=" + this.defaultMaxPerRoute + ", oAuthConsumerKey='" + this.oAuthConsumerKey + "', oAuthConsumerSecret='" + this.oAuthConsumerSecret + "', oAuthAccessToken='" + this.oAuthAccessToken + "', oAuthAccessTokenSecret='" + this.oAuthAccessTokenSecret + "', oAuth2TokenType='" + this.oAuth2TokenType + "', oAuth2AccessToken='" + this.oAuth2AccessToken + "', oAuthRequestTokenURL='" + this.oAuthRequestTokenURL + "', oAuthAuthorizationURL='" + this.oAuthAuthorizationURL + "', oAuthAccessTokenURL='" + this.oAuthAccessTokenURL + "', oAuthAuthenticationURL='" + this.oAuthAuthenticationURL + "', oAuth2TokenURL='" + this.oAuth2TokenURL + "', oAuth2InvalidateTokenURL='" + this.oAuth2InvalidateTokenURL + "', restBaseURL='" + this.restBaseURL + "', streamBaseURL='" + this.streamBaseURL + "', userStreamBaseURL='" + this.userStreamBaseURL + "', siteStreamBaseURL='" + this.siteStreamBaseURL + "', dispatcherImpl='" + this.dispatcherImpl + "', loggerFactory='" + this.loggerFactory + "', asyncNumThreads=" + this.asyncNumThreads + ", contributingTo=" + this.contributingTo + ", includeRTsEnabled=" + this.includeRTsEnabled + ", includeEntitiesEnabled=" + this.includeEntitiesEnabled + ", includeMyRetweetEnabled=" + this.includeMyRetweetEnabled + ", trimUserEnabled=" + this.trimUserEnabled + ", jsonStoreEnabled=" + this.jsonStoreEnabled + ", mbeanEnabled=" + this.mbeanEnabled + ", userStreamRepliesAllEnabled=" + this.userStreamRepliesAllEnabled + ", stallWarningsEnabled=" + this.stallWarningsEnabled + ", applicationOnlyAuthEnabled=" + this.applicationOnlyAuthEnabled + ", mediaProvider='" + this.mediaProvider + "', mediaProviderAPIKey='" + this.mediaProviderAPIKey + "', mediaProviderParameters=" + this.mediaProviderParameters + ", clientVersion='" + this.clientVersion + "', clientURL='" + this.clientURL + "', IS_DALVIK=" + this.IS_DALVIK + ", IS_GAE=" + this.IS_GAE + ", requestHeaders=" + this.requestHeaders + '}';
                }

/* 977 */       private static void cacheInstance(ConfigurationBase conf) {
/* 978 */           if (!instances.contains(conf)) {
/* 979 */               instances.add(conf);
                    }
                }

/* 983 */       protected void cacheInstance() {
/* 984 */           cacheInstance(this);
                }

/* 988 */       private static ConfigurationBase getInstance(ConfigurationBase configurationBase) {
/* 989 */           int index = instances.indexOf(configurationBase);
                    if (index != -1) {
                        return instances.get(index);
                    }
/* 990 */           instances.add(configurationBase);
/* 993 */           return configurationBase;
                }

/* 998 */       protected Object readResolve() throws ObjectStreamException {
/* 999 */           return getInstance(this);
                }
            }
