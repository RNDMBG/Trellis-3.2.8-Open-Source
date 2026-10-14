            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.Verb;
            import org.scribe.utils.OAuthEncoder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class SinaWeiboApi20 extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";
                private static final String SCOPED_AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 17 */        public Verb getAccessTokenVerb() {
/* 18 */            return Verb.POST;
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 23 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 24 */            return new JsonTokenExtractor();
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 29 */        public String getAccessTokenEndpoint() {
/* 30 */            return "https://api.weibo.com/oauth2/access_token?grant_type=authorization_code";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 36 */        public String getAuthorizationUrl(OAuthConfig config) {
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }
            }
