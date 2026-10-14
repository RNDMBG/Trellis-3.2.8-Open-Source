            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class LiveApi extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://oauth.live.com/authorize?client_id=%s&redirect_uri=%s&response_type=code";
                private static final String SCOPED_AUTHORIZE_URL = "https://oauth.live.com/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 15 */        public String getAccessTokenEndpoint() {
/* 16 */            return "https://oauth.live.com/token?grant_type=authorization_code";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 21 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 22 */            Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Live does not support OOB");
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 37 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 38 */            return new JsonTokenExtractor();
                }
            }
