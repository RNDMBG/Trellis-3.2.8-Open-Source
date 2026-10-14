            package twitter4j.conf;

            import java.io.Serializable;
            import java.util.Map;
            import java.util.Properties;
            import twitter4j.auth.AuthorizationConfiguration;
            import twitter4j.internal.http.HttpClientConfiguration;
            import twitter4j.internal.http.HttpClientWrapperConfiguration;
            
            
            public interface Configuration extends HttpClientConfiguration, HttpClientWrapperConfiguration, AuthorizationConfiguration, Serializable {
                int getAsyncNumThreads();

                String getClientURL();

                String getClientVersion();

                long getContributingTo();

                String getDispatcherImpl();

                @Override 
                int getHttpConnectionTimeout();

                @Override 
                int getHttpDefaultMaxPerRoute();

                @Override 
                int getHttpMaxTotalConnections();

                @Override 
                String getHttpProxyHost();

                @Override 
                String getHttpProxyPassword();

                @Override 
                int getHttpProxyPort();

                @Override 
                String getHttpProxyUser();

                @Override 
                int getHttpReadTimeout();

                @Override 
                int getHttpRetryCount();

                @Override 
                int getHttpRetryIntervalSeconds();

                int getHttpStreamingReadTimeout();

                String getLoggerFactory();

                String getMediaProvider();

                String getMediaProviderAPIKey();

                Properties getMediaProviderParameters();

                @Override 
                String getOAuth2AccessToken();

                String getOAuth2InvalidateTokenURL();

                @Override 
                String getOAuth2TokenType();

                String getOAuth2TokenURL();

                @Override 
                String getOAuthAccessToken();

                @Override 
                String getOAuthAccessTokenSecret();

                String getOAuthAccessTokenURL();

                String getOAuthAuthenticationURL();

                String getOAuthAuthorizationURL();

                @Override 
                String getOAuthConsumerKey();

                @Override 
                String getOAuthConsumerSecret();

                String getOAuthRequestTokenURL();

                @Override 
                String getPassword();

                @Override 
                Map<String, String> getRequestHeaders();

                String getRestBaseURL();

                String getSiteStreamBaseURL();

                String getStreamBaseURL();

                @Override 
                String getUser();

                String getUserAgent();

                String getUserStreamBaseURL();

                boolean isApplicationOnlyAuthEnabled();

                boolean isDalvik();

                boolean isDebugEnabled();

                boolean isGAE();

                boolean isIncludeEntitiesEnabled();

                boolean isIncludeMyRetweetEnabled();

                boolean isIncludeRTsEnabled();

                boolean isJSONStoreEnabled();

                boolean isMBeanEnabled();

                boolean isStallWarningsEnabled();

                boolean isTrimUserEnabled();

                boolean isUserStreamRepliesAllEnabled();
            }
