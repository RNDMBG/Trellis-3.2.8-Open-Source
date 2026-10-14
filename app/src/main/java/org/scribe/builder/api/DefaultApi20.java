            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.TokenExtractor20Impl;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.Verb;
            import org.scribe.oauth.OAuth20ServiceImpl;
            import org.scribe.oauth.OAuthService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public abstract class DefaultApi20 implements Api {
                public abstract String getAccessTokenEndpoint();

                public abstract String getAuthorizationUrl(OAuthConfig oAuthConfig);

/* 32 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 33 */            return new TokenExtractor20Impl();
                }

/* 42 */        public Verb getAccessTokenVerb() {
/* 43 */            return Verb.GET;
                }

                @Override // org.scribe.builder.api.Api
/* 66 */        public OAuthService createService(OAuthConfig config) {
/* 67 */            return new OAuth20ServiceImpl(this, config);
                }
            }
