            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class VkontakteApi extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize?client_id=%s&redirect_uri=%s&response_type=code";
                private static final String SCOPED_AUTHORIZE_URL = String.format("%s&scope=%%s", AUTHORIZE_URL);

                @Override // org.scribe.builder.api.DefaultApi20
/* 18 */        public String getAccessTokenEndpoint() {
/* 19 */            return "https://api.vkontakte.ru/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 24 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 25 */            Preconditions.checkValidUrl(config.getCallback(), "Valid url is required for a callback. Vkontakte does not support OOB");
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 38 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 39 */            return new JsonTokenExtractor();
                }
            }
