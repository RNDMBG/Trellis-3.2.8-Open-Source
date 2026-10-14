            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class ViadeoApi extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://secure.viadeo.com/oauth-provider/authorize2?client_id=%s&redirect_uri=%s&response_type=code";
                private static final String SCOPED_AUTHORIZE_URL = "https://secure.viadeo.com/oauth-provider/authorize2?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 17 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 18 */            return new JsonTokenExtractor();
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 23 */        public String getAccessTokenEndpoint() {
/* 24 */            return "https://secure.viadeo.com/oauth-provider/access_token2?grant_type=authorization_code";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 29 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 30 */            Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Viadeo does not support OOB");
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }
            }
