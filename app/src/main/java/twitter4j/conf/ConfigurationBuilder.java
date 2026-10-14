            package twitter4j.conf;

            import java.util.Properties;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class ConfigurationBuilder {
                private ConfigurationBase configurationBean = new PropertyConfiguration();

/* 33 */        public ConfigurationBuilder setUseSSL(boolean useSSL) {
/* 34 */            checkNotBuilt();
/* 35 */            this.configurationBean.setUseSSL(useSSL);
/* 36 */            return this;
                }

/* 39 */        public ConfigurationBuilder setPrettyDebugEnabled(boolean prettyDebugEnabled) {
/* 40 */            checkNotBuilt();
/* 41 */            this.configurationBean.setPrettyDebugEnabled(prettyDebugEnabled);
/* 42 */            return this;
                }

/* 45 */        public ConfigurationBuilder setGZIPEnabled(boolean gzipEnabled) {
/* 46 */            checkNotBuilt();
/* 47 */            this.configurationBean.setGZIPEnabled(gzipEnabled);
/* 48 */            return this;
                }

/* 51 */        public ConfigurationBuilder setDebugEnabled(boolean debugEnabled) {
/* 52 */            checkNotBuilt();
/* 53 */            this.configurationBean.setDebug(debugEnabled);
/* 54 */            return this;
                }

/* 57 */        public ConfigurationBuilder setApplicationOnlyAuthEnabled(boolean applicationOnlyAuthEnabled) {
/* 58 */            checkNotBuilt();
/* 59 */            this.configurationBean.setApplicationOnlyAuthEnabled(applicationOnlyAuthEnabled);
/* 60 */            return this;
                }

/* 63 */        public ConfigurationBuilder setUser(String user) {
/* 64 */            checkNotBuilt();
/* 65 */            this.configurationBean.setUser(user);
/* 66 */            return this;
                }

/* 69 */        public ConfigurationBuilder setPassword(String password) {
/* 70 */            checkNotBuilt();
/* 71 */            this.configurationBean.setPassword(password);
/* 72 */            return this;
                }

/* 75 */        public ConfigurationBuilder setHttpProxyHost(String httpProxyHost) {
/* 76 */            checkNotBuilt();
/* 77 */            this.configurationBean.setHttpProxyHost(httpProxyHost);
/* 78 */            return this;
                }

/* 81 */        public ConfigurationBuilder setHttpProxyUser(String httpProxyUser) {
/* 82 */            checkNotBuilt();
/* 83 */            this.configurationBean.setHttpProxyUser(httpProxyUser);
/* 84 */            return this;
                }

/* 87 */        public ConfigurationBuilder setHttpProxyPassword(String httpProxyPassword) {
/* 88 */            checkNotBuilt();
/* 89 */            this.configurationBean.setHttpProxyPassword(httpProxyPassword);
/* 90 */            return this;
                }

/* 93 */        public ConfigurationBuilder setHttpProxyPort(int httpProxyPort) {
/* 94 */            checkNotBuilt();
/* 95 */            this.configurationBean.setHttpProxyPort(httpProxyPort);
/* 96 */            return this;
                }

/* 99 */        public ConfigurationBuilder setHttpConnectionTimeout(int httpConnectionTimeout) {
/* 100 */           checkNotBuilt();
/* 101 */           this.configurationBean.setHttpConnectionTimeout(httpConnectionTimeout);
/* 102 */           return this;
                }

/* 105 */       public ConfigurationBuilder setHttpReadTimeout(int httpReadTimeout) {
/* 106 */           checkNotBuilt();
/* 107 */           this.configurationBean.setHttpReadTimeout(httpReadTimeout);
/* 108 */           return this;
                }

/* 111 */       public ConfigurationBuilder setHttpStreamingReadTimeout(int httpStreamingReadTimeout) {
/* 112 */           checkNotBuilt();
/* 113 */           this.configurationBean.setHttpStreamingReadTimeout(httpStreamingReadTimeout);
/* 114 */           return this;
                }

/* 117 */       public ConfigurationBuilder setHttpRetryCount(int httpRetryCount) {
/* 118 */           checkNotBuilt();
/* 119 */           this.configurationBean.setHttpRetryCount(httpRetryCount);
/* 120 */           return this;
                }

/* 124 */       public ConfigurationBuilder setHttpMaxTotalConnections(int httpMaxConnections) {
/* 125 */           checkNotBuilt();
/* 126 */           this.configurationBean.setHttpMaxTotalConnections(httpMaxConnections);
/* 127 */           return this;
                }

/* 130 */       public ConfigurationBuilder setHttpDefaultMaxPerRoute(int httpDefaultMaxPerRoute) {
/* 131 */           checkNotBuilt();
/* 132 */           this.configurationBean.setHttpDefaultMaxPerRoute(httpDefaultMaxPerRoute);
/* 133 */           return this;
                }

/* 136 */       public ConfigurationBuilder setHttpRetryIntervalSeconds(int httpRetryIntervalSeconds) {
/* 137 */           checkNotBuilt();
/* 138 */           this.configurationBean.setHttpRetryIntervalSeconds(httpRetryIntervalSeconds);
/* 139 */           return this;
                }

/* 142 */       public ConfigurationBuilder setOAuthConsumerKey(String oAuthConsumerKey) {
/* 143 */           checkNotBuilt();
/* 144 */           this.configurationBean.setOAuthConsumerKey(oAuthConsumerKey);
/* 145 */           return this;
                }

/* 148 */       public ConfigurationBuilder setOAuthConsumerSecret(String oAuthConsumerSecret) {
/* 149 */           checkNotBuilt();
/* 150 */           this.configurationBean.setOAuthConsumerSecret(oAuthConsumerSecret);
/* 151 */           return this;
                }

/* 154 */       public ConfigurationBuilder setOAuthAccessToken(String oAuthAccessToken) {
/* 155 */           checkNotBuilt();
/* 156 */           this.configurationBean.setOAuthAccessToken(oAuthAccessToken);
/* 157 */           return this;
                }

/* 160 */       public ConfigurationBuilder setOAuthAccessTokenSecret(String oAuthAccessTokenSecret) {
/* 161 */           checkNotBuilt();
/* 162 */           this.configurationBean.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
/* 163 */           return this;
                }

/* 166 */       public ConfigurationBuilder setOAuth2TokenType(String oAuth2TokenType) {
/* 167 */           checkNotBuilt();
/* 168 */           this.configurationBean.setOAuth2TokenType(oAuth2TokenType);
/* 169 */           return this;
                }

/* 172 */       public ConfigurationBuilder setOAuth2AccessToken(String oAuth2AccessToken) {
/* 173 */           checkNotBuilt();
/* 174 */           this.configurationBean.setOAuth2AccessToken(oAuth2AccessToken);
/* 175 */           return this;
                }

/* 178 */       public ConfigurationBuilder setOAuthRequestTokenURL(String oAuthRequestTokenURL) {
/* 179 */           checkNotBuilt();
/* 180 */           this.configurationBean.setOAuthRequestTokenURL(oAuthRequestTokenURL);
/* 181 */           return this;
                }

/* 184 */       public ConfigurationBuilder setOAuthAuthorizationURL(String oAuthAuthorizationURL) {
/* 185 */           checkNotBuilt();
/* 186 */           this.configurationBean.setOAuthAuthorizationURL(oAuthAuthorizationURL);
/* 187 */           return this;
                }

/* 190 */       public ConfigurationBuilder setOAuthAccessTokenURL(String oAuthAccessTokenURL) {
/* 191 */           checkNotBuilt();
/* 192 */           this.configurationBean.setOAuthAccessTokenURL(oAuthAccessTokenURL);
/* 193 */           return this;
                }

/* 196 */       public ConfigurationBuilder setOAuthAuthenticationURL(String oAuthAuthenticationURL) {
/* 197 */           checkNotBuilt();
/* 198 */           this.configurationBean.setOAuthAuthenticationURL(oAuthAuthenticationURL);
/* 199 */           return this;
                }

/* 202 */       public ConfigurationBuilder setOAuth2TokenURL(String oAuth2TokenURL) {
/* 203 */           checkNotBuilt();
/* 204 */           this.configurationBean.setOAuth2TokenURL(oAuth2TokenURL);
/* 205 */           return this;
                }

/* 208 */       public ConfigurationBuilder setOAuth2InvalidateTokenURL(String invalidateTokenURL) {
/* 209 */           checkNotBuilt();
/* 210 */           this.configurationBean.setOAuth2InvalidateTokenURL(invalidateTokenURL);
/* 211 */           return this;
                }

/* 214 */       public ConfigurationBuilder setRestBaseURL(String restBaseURL) {
/* 215 */           checkNotBuilt();
/* 216 */           this.configurationBean.setRestBaseURL(restBaseURL);
/* 217 */           return this;
                }

/* 220 */       public ConfigurationBuilder setStreamBaseURL(String streamBaseURL) {
/* 221 */           checkNotBuilt();
/* 222 */           this.configurationBean.setStreamBaseURL(streamBaseURL);
/* 223 */           return this;
                }

/* 226 */       public ConfigurationBuilder setUserStreamBaseURL(String userStreamBaseURL) {
/* 227 */           checkNotBuilt();
/* 228 */           this.configurationBean.setUserStreamBaseURL(userStreamBaseURL);
/* 229 */           return this;
                }

/* 232 */       public ConfigurationBuilder setSiteStreamBaseURL(String siteStreamBaseURL) {
/* 233 */           checkNotBuilt();
/* 234 */           this.configurationBean.setSiteStreamBaseURL(siteStreamBaseURL);
/* 235 */           return this;
                }

/* 238 */       public ConfigurationBuilder setAsyncNumThreads(int asyncNumThreads) {
/* 239 */           checkNotBuilt();
/* 240 */           this.configurationBean.setAsyncNumThreads(asyncNumThreads);
/* 241 */           return this;
                }

/* 244 */       public ConfigurationBuilder setContributingTo(long contributingTo) {
/* 245 */           checkNotBuilt();
/* 246 */           this.configurationBean.setContributingTo(contributingTo);
/* 247 */           return this;
                }

/* 250 */       public ConfigurationBuilder setClientVersion(String clientVersion) {
/* 251 */           checkNotBuilt();
/* 252 */           this.configurationBean.setClientVersion(clientVersion);
/* 253 */           return this;
                }

/* 256 */       public ConfigurationBuilder setClientURL(String clientURL) {
/* 257 */           checkNotBuilt();
/* 258 */           this.configurationBean.setClientURL(clientURL);
/* 259 */           return this;
                }

/* 262 */       public ConfigurationBuilder setDispatcherImpl(String dispatcherImpl) {
/* 263 */           checkNotBuilt();
/* 264 */           this.configurationBean.setDispatcherImpl(dispatcherImpl);
/* 265 */           return this;
                }

/* 268 */       public ConfigurationBuilder setIncludeRTsEnabled(boolean enabled) {
/* 269 */           checkNotBuilt();
/* 270 */           this.configurationBean.setIncludeRTsEnbled(enabled);
/* 271 */           return this;
                }

/* 274 */       public ConfigurationBuilder setIncludeEntitiesEnabled(boolean enabled) {
/* 275 */           checkNotBuilt();
/* 276 */           this.configurationBean.setIncludeEntitiesEnbled(enabled);
/* 277 */           return this;
                }

/* 280 */       public ConfigurationBuilder setTrimUserEnabled(boolean enabled) {
/* 281 */           checkNotBuilt();
/* 282 */           this.configurationBean.setTrimUserEnabled(enabled);
/* 283 */           return this;
                }

/* 286 */       public ConfigurationBuilder setIncludeMyRetweetEnabled(boolean enabled) {
/* 287 */           checkNotBuilt();
/* 288 */           this.configurationBean.setIncludeMyRetweetEnabled(enabled);
/* 289 */           return this;
                }

/* 292 */       public ConfigurationBuilder setJSONStoreEnabled(boolean enabled) {
/* 293 */           checkNotBuilt();
/* 294 */           this.configurationBean.setJSONStoreEnabled(enabled);
/* 295 */           return this;
                }

/* 298 */       public ConfigurationBuilder setMBeanEnabled(boolean enabled) {
/* 299 */           checkNotBuilt();
/* 300 */           this.configurationBean.setMBeanEnabled(enabled);
/* 301 */           return this;
                }

/* 304 */       public ConfigurationBuilder setUserStreamRepliesAllEnabled(boolean enabled) {
/* 305 */           checkNotBuilt();
/* 306 */           this.configurationBean.setUserStreamRepliesAllEnabled(enabled);
/* 307 */           return this;
                }

/* 310 */       public ConfigurationBuilder setMediaProvider(String mediaProvider) {
/* 311 */           checkNotBuilt();
/* 312 */           this.configurationBean.setMediaProvider(mediaProvider);
/* 313 */           return this;
                }

/* 316 */       public ConfigurationBuilder setMediaProviderAPIKey(String mediaProviderAPIKey) {
/* 317 */           checkNotBuilt();
/* 318 */           this.configurationBean.setMediaProviderAPIKey(mediaProviderAPIKey);
/* 319 */           return this;
                }

/* 322 */       public ConfigurationBuilder setMediaProviderParameters(Properties props) {
/* 323 */           checkNotBuilt();
/* 324 */           this.configurationBean.setMediaProviderParameters(props);
/* 325 */           return this;
                }

/* 328 */       public Configuration build() {
/* 329 */           checkNotBuilt();
/* 330 */           this.configurationBean.cacheInstance();
                    try {
/* 332 */               ConfigurationBase configurationBase = this.configurationBean;
/* 334 */               this.configurationBean = null;
                        return configurationBase;
                    } catch (Throwable th) {
                        this.configurationBean = null;
                        throw th;
                    }
                }

/* 338 */       private void checkNotBuilt() {
/* 339 */           if (this.configurationBean == null) {
/* 340 */               throw new IllegalStateException("Cannot use this builder any longer, build() has already been called");
                    }
                }
            }
